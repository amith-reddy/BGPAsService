package bgpasservice



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ExaactionController {
	def grailsApplication
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Exaaction.list(params), model:[exaactionInstanceCount: Exaaction.count()]
    }

    def show(Exaaction exaactionInstance) {
        respond exaactionInstance
    }

    def create() {
		//println grailsApplication.config.protocollist
			respond new Exaaction(params)
    }

    @Transactional
    def save(Exaaction exaactionInstance) {
        if (exaactionInstance == null) {
            notFound()
            return
        }

        if (exaactionInstance.hasErrors()) {
            respond exaactionInstance.errors, view:'create'
            return
        }
		
        exaactionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'exaaction.label', default: 'Exaaction'), exaactionInstance.id])
                redirect exaactionInstance
            }
            '*' { respond exaactionInstance, [status: CREATED] }
        }
    }

    def edit(Exaaction exaactionInstance) {
        respond exaactionInstance
    }

    @Transactional
    def update(Exaaction exaactionInstance) {
        if (exaactionInstance == null) {
            notFound()
            return
        }

        if (exaactionInstance.hasErrors()) {
            respond exaactionInstance.errors, view:'edit'
            return
        }

        exaactionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Exaaction.label', default: 'Exaaction'), exaactionInstance.id])
                redirect exaactionInstance
            }
            '*'{ respond exaactionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Exaaction exaactionInstance) {

        if (exaactionInstance == null) {
            notFound()
            return
        }

        exaactionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Exaaction.label', default: 'Exaaction'), exaactionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'exaaction.label', default: 'Exaaction'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
