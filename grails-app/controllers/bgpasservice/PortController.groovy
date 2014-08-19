package bgpasservice



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PortController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Port.list(params), model:[portInstanceCount: Port.count()]
    }

    def show(Port portInstance) {
        respond portInstance
    }

    def create() {
        respond new Port(params)
    }

    @Transactional
    def save(Port portInstance) {
        if (portInstance == null) {
            notFound()
            return
        }

        if (portInstance.hasErrors()) {
            respond portInstance.errors, view:'create'
            return
        }

        portInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'port.label', default: 'Port'), portInstance.id])
                redirect portInstance
            }
            '*' { respond portInstance, [status: CREATED] }
        }
    }

    def edit(Port portInstance) {
        respond portInstance
    }

    @Transactional
    def update(Port portInstance) {
        if (portInstance == null) {
            notFound()
            return
        }

        if (portInstance.hasErrors()) {
            respond portInstance.errors, view:'edit'
            return
        }

        portInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Port.label', default: 'Port'), portInstance.id])
                redirect portInstance
            }
            '*'{ respond portInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Port portInstance) {

        if (portInstance == null) {
            notFound()
            return
        }

        portInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Port.label', default: 'Port'), portInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'port.label', default: 'Port'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
