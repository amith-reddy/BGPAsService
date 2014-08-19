package bgpasservice



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import common.*;

@Transactional(readOnly = true)
class SchedulerController {
	def grailsApplication
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Scheduler.list(params), model:[schedulerInstanceCount: Scheduler.count()]
    }

    def show(Scheduler schedulerInstance) {
		
		if (schedulerInstance?.id == null) {
			deleteNotFound()
			return
		}
		
        respond schedulerInstance
    }

    def create() {
		
		respond new Scheduler(params)
    }

    @Transactional
    def save(Scheduler schedulerInstance) {
        if (schedulerInstance == null) {
            notFound()
            return
        }

        if (schedulerInstance.hasErrors()) {
            respond schedulerInstance.errors, view:'create'
            return
        }
		
        schedulerInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'scheduler.label', default: 'Scheduler'), schedulerInstance.id])
                redirect schedulerInstance
            }
            '*' { respond schedulerInstance, [status: CREATED] }
        }
		
		def flowid = schedulerInstance.newFlows.id
		//def flowschedulejob = new ScheduleFlowHelper()
		ScheduleFlowHelper.getInstance().scheduleflow(schedulerInstance,"create",flowid,true,true,grailsApplication.config.grails.exabgp.path)
		//flowschedulejob.scheduleflow(schedulerInstance,params["operation"],flowid)
		
    }

    def edit(Scheduler schedulerInstance) {
		params["operation"] = 'edit'

		if (schedulerInstance?.id == null) {
			deleteNotFound()
			return
		}
		
        respond schedulerInstance
    }

    @Transactional
    def update(Scheduler schedulerInstance) {
		
		def scheduleflag=false,deleteflag=false
		
        if (schedulerInstance == null) {
            notFound()
            return
        }
		
        if (schedulerInstance.hasErrors()) {
            respond schedulerInstance.errors, view:'edit'
            return
        }
		
		
		if(schedulerInstance.isDirty('scheduleOption')  || schedulerInstance.isDirty('scheduleTime') 
			|| schedulerInstance.isDirty('activationSchedule')  || schedulerInstance.isDirty('deactivationSchedule'))
		{
			scheduleflag = true
		}
		else if(schedulerInstance.isDirty('deleteScheduleOption') || schedulerInstance.isDirty('deleteScheduleTime'))
		{
			deleteflag=true
		}
		
        schedulerInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Scheduler.label', default: 'Scheduler'), schedulerInstance.id])
                redirect schedulerInstance
            }
            '*'{ respond schedulerInstance, [status: OK] }
        }
		
		if(scheduleflag || deleteflag)
		{
			def flowid = schedulerInstance.newFlows.id
			//def flowschedulejob = new ScheduleFlowHelper()
			//flowschedulejob.scheduleflow(schedulerInstance,"edit",flowid)
			ScheduleFlowHelper.getInstance().scheduleflow(schedulerInstance,"edit",flowid,scheduleflag,deleteflag,grailsApplication.config.grails.exabgp.path)
		}
    }

    @Transactional
    def delete(Scheduler schedulerInstance) {

        if (schedulerInstance == null) {
            notFound()
            return
        }
		
		def flowid = schedulerInstance.newFlows.id
		def name = schedulerInstance.name
		def email = schedulerInstance.email
		
        schedulerInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Scheduler.label', default: 'Scheduler'), schedulerInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
		
		//def flowschedulejob = new ScheduleFlowHelper()
		//flowschedulejob.unscheduleflow(flowid)
		//ScheduleFlowHelper.getInstance().unscheduleflow(flowid,name,email)
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'scheduler.label', default: 'Scheduler'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	protected void deleteNotFound() {

		flash.message = message(code: 'default.not.found.message', args: [message(code: 'scheduler.label', default: 'Scheduler'), params.id])
		redirect action: "index", method: "GET"

	}
	
}
