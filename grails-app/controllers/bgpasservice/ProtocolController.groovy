package bgpasservice



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProtocolController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Protocol.list(params), model:[protocolInstanceCount: Protocol.count()]
    }

    def show(Protocol protocolInstance) {
        respond protocolInstance
    }

    def create() {
        respond new Protocol(params)
    }

    @Transactional
    def save(Protocol protocolInstance) {

        if (protocolInstance == null) {
            notFound()
            return
        }

        if (protocolInstance.hasErrors()) {
            respond protocolInstance.errors, view:'create'
            return
        }

        protocolInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'protocol.label', default: 'Protocol'), protocolInstance.id])
                redirect protocolInstance
            }
            '*' { respond protocolInstance, [status: CREATED] }
        }
    }

    def edit(Protocol protocolInstance) {
        respond protocolInstance
    }

    @Transactional
    def update(Protocol protocolInstance) {
        if (protocolInstance == null) {
            notFound()
            return
        }

        if (protocolInstance.hasErrors()) {
            respond protocolInstance.errors, view:'edit'
            return
        }

        protocolInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Protocol.label', default: 'Protocol'), protocolInstance.id])
                redirect protocolInstance
            }
            '*'{ respond protocolInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Protocol protocolInstance) {

        if (protocolInstance == null) {
            notFound()
            return
        }

        protocolInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Protocol.label', default: 'Protocol'), protocolInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'protocol.label', default: 'Protocol'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
