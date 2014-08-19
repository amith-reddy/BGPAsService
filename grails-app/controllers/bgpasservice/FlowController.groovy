package bgpasservice



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import common.*;
import org.grails.plugin.filterpane.FilterPaneUtils;

@Transactional(readOnly = true)
class FlowController {

	def filterPaneService
	
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
	    redirect(action:list,params:params)
	}
	
//    def index(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        //respond Flow.list(params), model:[flowInstanceCount: Flow.count()]
//		respond Flow.list(params), model:[flowInstanceCount: Flow.count()]
//    }
	
	def list = {
		if(!params.max) params.max = 10
		[ flowList: Flow.list( params ), filterParams: FilterPaneUtils.extractFilterParams(params) ]
	}
	
	def filter = {
		if(!params.max) params.max = 10
		render( view:'list', model:[ flowList: filterPaneService.filter( params, Flow ), flowCount: filterPaneService.count( params, Flow ), filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params), params:params ] )
	}
	

    def show(Flow flowInstance) {

		if (flowInstance?.id == null) {
			notFound()
			return
		}
		
        respond flowInstance
    }

    def create() {
        respond new Flow(params)
    }
	
	def copy(Flow flowInstance) {
		//render(view:"create", model:new Flow(params))
		params["sourceSubnet"] = flowInstance.sourceSubnet
		params["destinationSubnet"] = flowInstance.destinationSubnet
		params["status"] = flowInstance.status
		params["neighbors"] = flowInstance.neighbors
		params["protocol"] = flowInstance.protocol
		params["exaaction"] = flowInstance.exaaction
		params["sourceport"] = flowInstance.sourceport
		params["destinationport"] = flowInstance.destinationport
		def newflow = new Flow(params)
		respond newflow.errors, view:'create'
	}

    @Transactional
    def save(Flow flowInstance) {
        if (flowInstance == null) {
            notFound()
            return
        }

        if (flowInstance.hasErrors()) {
            respond flowInstance.errors, view:'create'
            return
        }

        flowInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'flow.label', default: 'Flow'), flowInstance.id])
                redirect flowInstance
            }
            '*' { respond flowInstance, [status: CREATED] }
        }
    }

    def edit(Flow flowInstance) {
        respond flowInstance
    }

    @Transactional
    def update(Flow flowInstance) {
        if (flowInstance == null) {
            notFound()
            return
        }

        if (flowInstance.hasErrors()) {
            respond flowInstance.errors, view:'edit'
            return
        }

        flowInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Flow.label', default: 'Flow'), flowInstance.id])
                redirect flowInstance
            }
            '*'{ respond flowInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Flow flowInstance) {

        if (flowInstance == null) {
            notFound()
            return
        }
		
		//do this only if the status is done
//		if(flowInstance.status == 'done')
//		{
//			request.withFormat {
//				form multipartForm {
//					flash.message = message(code: 'default.configScheduler.message', args: [message(code: 'Flow.label', default: 'Flow'), flowInstance.id])
//					redirect action: 'create', controller: 'Scheduler', params: ["newFlows":flowInstance.id, "scheduleOption":'execute', "name": '',"email":'',operation:"delete"]
//				}
//				'*'{ render status: NO_CONTENT }
//			}
//		}
//		else
//		{
			def schedulerInstance = Scheduler.findByNewFlows(flowInstance)
			if(schedulerInstance != null)
			{
//				println "scheduler id:"+schedulerInstance.id
//				schedulerInstance.delete flush:true
				request.withFormat {
					form multipartForm {
						flash.message = message(code: 'default.deleteFail.message', args: [message(code: 'Flow.label', default: 'Flow'), flowInstance.id])
						redirect action:"index", method:"GET"
					}
					'*'{ render status: NO_CONTENT }
				}
			}
			else
			{
			
				flowInstance.delete flush:true
				
				request.withFormat {
					form multipartForm {
						flash.message = message(code: 'default.deleted.message', args: [message(code: 'Flow.label', default: 'Flow'), flowInstance.id])
						redirect action:"index", method:"GET"
					}
					'*'{ render status: NO_CONTENT }
				}
			}
//		}
		//respond new Scheduler(newFlows:flowInstance,schedulerOption:'execute',scheduleTime:new Date(), name:'amith',email:'')
		
    }
	
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'flow.label', default: 'Flow'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
