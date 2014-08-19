package bgpasservice



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class NeighborController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Neighbor.list(params), model:[neighborInstanceCount: Neighbor.count()]
    }

    def show(Neighbor neighborInstance) {
        respond neighborInstance
    }

    def create() {
        respond new Neighbor(params)
    }

    @Transactional
    def save(Neighbor neighborInstance) {
        if (neighborInstance == null) {
            notFound()
            return
        }

        if (neighborInstance.hasErrors()) {
            respond neighborInstance.errors, view:'create'
            return
        }

        neighborInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'neighbor.label', default: 'Neighbor'), neighborInstance.id])
                redirect neighborInstance
            }
            '*' { respond neighborInstance, [status: CREATED] }
        }
    }

    def edit(Neighbor neighborInstance) {
        respond neighborInstance
    }

    @Transactional
    def update(Neighbor neighborInstance) {
        if (neighborInstance == null) {
            notFound()
            return
        }

        if (neighborInstance.hasErrors()) {
            respond neighborInstance.errors, view:'edit'
            return
        }

        neighborInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Neighbor.label', default: 'Neighbor'), neighborInstance.id])
                redirect neighborInstance
            }
            '*'{ respond neighborInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Neighbor neighborInstance) {

        if (neighborInstance == null) {
            notFound()
            return
        }

        neighborInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Neighbor.label', default: 'Neighbor'), neighborInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'neighbor.label', default: 'Neighbor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
