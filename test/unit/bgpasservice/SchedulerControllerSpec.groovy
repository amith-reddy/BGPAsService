package bgpasservice



import grails.test.mixin.*
import spock.lang.*

@TestFor(SchedulerController)
@Mock(Scheduler)
class SchedulerControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        
		def e = new Exaaction(actionName:"discard", actionParameter:"")
		def n = new Neighbor(neighborIp: "10.10.10.10",neighborDescription: "test", localIp:"10.10.10.10", routerId:"10.10.10.10", localAS:"12345", peerAS:"12345")
		def spt = new Port(portRule: "=12345")
		def dpt = new Port(portRule: "=12345")
		def pl = new Protocol(protocolRule:"tcp")
		def fl = new Flow(sourceSubnet: "126.54.36.25/24", destinationSubnet:"145.25.33.14/24",status:"new", neighbors:n, protocol:pl, exaaction:e, sourceport:spt, destinationport:dpt)
		params["newFlows"] = fl
		params["scheduleOption"] = 'Schedule Once'
		params["scheduleTime"] = new Date().parse("d/M/yyyy H:m:s","21/3/2015 17:30:20");
		params["activationSchedule"] = '';
		params["deactivationSchedule"] = '';
		params["deleteScheduleOption"] = 'Dont Delete';
		params["deleteScheduleTime"] = new Date().parse("d/M/yyyy H:m:s","21/3/2015 17:30:20");
		params["name"] = ''
		params["email"] = ''
    }
	
	def populateParams(params, scheduleOption, scheduleTime,activationSchedule, deactivationSchedule, deleteScheduleOption, deleteScheduleTime, name, email) {
		assert params != null
		
		def e = new Exaaction(actionName:"discard", actionParameter:"")
		def n = new Neighbor(neighborIp: "10.10.10.10",neighborDescription: "test", localIp:"10.10.10.10", routerId:"10.10.10.10", localAS:"12345", peerAS:"12345")
		def spt = new Port(portRule: "=12345")
		def dpt = new Port(portRule: "=12345")
		def pl = new Protocol(protocolRule:"tcp")
		def fl = new Flow(sourceSubnet: "126.54.36.25/24", destinationSubnet:"145.25.33.14/24",status:"new", neighbors:n, protocol:pl, exaaction:e, sourceport:spt, destinationport:dpt)
		params["newFlows"] = fl
		params["scheduleOption"] = scheduleOption
		if(scheduleTime != '')
		{
			params["scheduleTime"] = new Date().parse("d/M/yyyy H:m:s",scheduleTime);
		}
		else
		{
			params["scheduleTime"] = null
		}
		params["activationSchedule"] = activationSchedule
		params["deactivationSchedule"] = deactivationSchedule
		params["deleteScheduleOption"] = deleteScheduleOption
		if(deleteScheduleTime != '')
		{
			params["deleteScheduleTime"] = new Date().parse("d/M/yyyy H:m:s",deleteScheduleTime);
		}
		else
		{
			params["deleteScheduleTime"] = null
		}
		params["name"] = name
		params["email"] = email
	}

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.schedulerInstanceList
            model.schedulerInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.schedulerInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
            def scheduler = new Scheduler()
			scheduler.validate()
            controller.save(scheduler)

        then:"The create view is rendered again with the correct model"
            model.schedulerInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            scheduler = new Scheduler(params)
            controller.save(scheduler)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/scheduler/show/1'
            controller.flash.message != null
            Scheduler.count() == 1
    }
	
	@Unroll
	void "Test the save action with invalid parameters - scheduleoption-'#tmpscheduleoption' | scheduletime-'#tmpscheduletime' | activationschedule-'#tmpactivationschedule' | deactivationschedule-'#tmpdeactivationschedule' | name-'#tmpname' | email-'#tmpemail'"() {
		given:
			def code
		when:"The save action is executed with invalid parameters"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
			populateParams(params, tmpscheduleoption, tmpscheduletime, tmpactivationschedule, tmpdeactivationschedule, tmpdeletescheduleoption, tmpdeletescheduletime, tmpname, tmpemail)
			def scheduler = new Scheduler(params)
			scheduler.validate()
					
			if(scheduler.hasErrors())
			{
				println "\nErrors:"
				println scheduler.errors ?: "no errors found"
				
				def badField = scheduler.errors.getFieldError(tmpfieldname)
				println "\nBadField:"
				println badField ?: tmpfieldname+" wasn't a bad field"
				
				code = badField?.codes.find {
					it == tmpCode
				}
					
				println "\nCode:"
				println code
			}
			controller.save(scheduler)

		then:"The create view is rendered again with the correct model"
			code == tmpCode
			model.schedulerInstance!= null
			view == 'create'
			
		where:
		
		tmpfieldname      	  	| tmpscheduleoption  		| tmpscheduletime    	| tmpactivationschedule		| tmpdeactivationschedule	|	tmpdeletescheduleoption	| 	tmpdeletescheduletime	| 	tmpname				| 		tmpemail					|tmpCode
		'scheduleOption'  		| ''    					|  ''					| ''			    		| ''			    		|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.scheduleOption.nullable'				//scheduleOption cannot be null
		'scheduleOption'  		| 'other'    				|  ''					| ''			    		| ''			    		|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.scheduleOption.not.inList'				//scheduleOption is not in the list
		'scheduleTime'    		| 'Schedule Once'    		|  '21/3/2014 17:30:20'	| ''			    		| ''			    		|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.scheduleTime.invalid.scheduledate'				//scheduleTime is not valid
		'activationSchedule'	| 'Schedule Periodically'	|  ''					| ''    					| '0 * * ? * *'  			|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.activationSchedule.invalid.blank'				//activationSchedule is not valid
		'activationSchedule'	| 'Execute Now'    			|  ''					| '0 * * ? * *'				| '0 * * ? * *'  			|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.activationSchedule.invalid.notblank'				//activationSchedule is not valid
		'activationSchedule'	| 'Execute Now'    			|  ''					| '0 * * ? * *'				| '0 * * ? * *'  			|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.activationSchedule.invalid.notblank'				//activationSchedule is not valid
		'activationSchedule'	| 'Schedule Periodically'	|  ''					| '* * * * *12'    			| '0 * * ? * *'				|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.activationSchedule.invalid.cronExpression'				//activationSchedule is not valid
		'deactivationSchedule'	| 'Schedule Periodically'	|  ''					|  '0 * * ? * *'     		| ''    					|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.deactivationSchedule.invalid.blank'				//deactivationSchedule is not valid
		'deactivationSchedule'	| 'Schedule Periodically'	|  ''					|  '0 * * ? * *'     		| '* * * ? *12'				|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.deactivationSchedule.invalid.cronExpression'				//deactivationSchedule is not valid
		'deleteScheduleOption' 	| 'Execute Now'    			|  ''					| ''			    		| ''			    		|	'' 						|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.deleteScheduleOption.nullable'				//scheduleOption cannot be null
		'deleteScheduleOption'  | 'Execute Now'    			|  ''					| ''			    		| ''			    		|	'other' 				|			''              |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.deleteScheduleOption.not.inList'				//scheduleOption is not in the list
		'deleteScheduleTime'    | 'Execute Now'    			|  ''					| ''			    		| ''			    		|	'Delete Later' 			|	'21/3/2014 17:30:20'    |		'Amith'			|	'aravuru@ncsu.edu'				|'scheduler.deleteScheduleTime.invalid.deletescheduledate'				//scheduleOption is not in the list
		'name' 			  		| 'Schedule Periodically'	|  ''					|  '0 * * ? * *'     		| '0 2 2 ? * *'				|	'Dont Delete' 			|			''              |		'amithrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr'			|	'aravuru@ncsu.edu'				|'scheduler.name.maxSize.exceeded'				//name is not valid
		'email'			  		| 'Schedule Periodically'	|  ''					|  '0 * * ? * *'    		| '0 2 2 ? * *'		    	|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru'						|'scheduler.email.email.invalid'						//email is not valid
		'email'			  		| 'Schedule Periodically'	|  ''					|  '0 * * ? * *'    		| '0 2 2 ? * *'	  		 	|	'Dont Delete' 			|			''              |		'Amith'			|	'aravuru@ncsu. '				|'scheduler.email.email.invalid'						//email is not valid
		'email'			  		| 'Schedule Periodically'	|  ''					|  '0 * * ? * *'    		| '0 2 2 ? * *'		    	|	'Dont Delete' 			|			''              |		'Amith'			|   'ara@vuru@ncsu.edu'				|'scheduler.email.email.invalid'						//email is not valid
		'email'			  		| 'Schedule Periodically'	|  ''					|  '0 * * ? * *'    		| '0 2 2 ? * *'		    	|	'Dont Delete' 			|			''              |		'Amith'			|	'dddddddddddddddddddddaravurudedddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddsssssdddddddddddddddddddddaravurudedddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddsssssaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddddddaaaaaa@ncsu.edu'						|'scheduler.email.maxSize.exceeded'						//email is not valid
	}
	
	@Unroll
	void "Test the save action with valid parameters - scheduleoption-'#tmpscheduleoption' | scheduletime-'#tmpscheduletime' | activationschedule-'#tmpactivationschedule' | deactivationschedule-'#tmpdeactivationschedule' | name-'#tmpname' | email-'#tmpemail'"() {
		
		when:"The save action is executed with valid parameters"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
			populateParams(params, tmpscheduleoption, tmpscheduletime, tmpactivationschedule, tmpdeactivationschedule, tmpdeletescheduleoption, tmpdeletescheduletime, tmpname, tmpemail)
			def scheduler = new Scheduler(params)
			scheduler.validate()
			if(scheduler.hasErrors())
			{
				println "\nErrors:"
				println scheduler.errors ?: "no errors found"
			}
			controller.save(scheduler)

		then:"A redirect is issued to the show action"
			response.redirectedUrl == '/scheduler/show/1'
			controller.flash.message != null
			Scheduler.count() == 1
			
		where:
			tmpscheduleoption  			| tmpscheduletime    	| tmpactivationschedule		| tmpdeactivationschedule	|	tmpdeletescheduleoption	| 	tmpdeletescheduletime	| 	tmpname			| 		tmpemail		
			'Execute Now'  				|  ''					| ''			    		| ''			    		|	'Dont Delete' 			|			''              |	'Amith'			|	'aravuru@ncsu.edu'	
			'Schedule Once'				|  '21/3/2099 17:30:20'	| ''			    		| ''			    		|	'Dont Delete' 			|			''              |	'Amith'			|	'aravuru@ncsu.edu'	
			'Schedule Once'				|  '21/3/2099 17:30:20'	| ''			    		| ''			    		|	'Dont Delete' 			|			''              |	''				|	''	
			'Schedule Periodically'		|  ''					| '2 2 * * * ?'    			| '3 3 * * * ?'  			|	'Delete Later' 			|	'21/3/2099 17:30:20'    |	'Amith'			|	'aravuru@ncsu.edu'	
				
	}

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 302 error is returned"
			response.status == 302
			

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def scheduler = new Scheduler(params).save(flush: true)
            controller.show(scheduler)

        then:"A model is populated containing the domain instance"
            model.schedulerInstance == scheduler
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 302 error is returned"
			response.status == 302
            

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def scheduler = new Scheduler(params).save(flush: true)
            controller.edit(scheduler)

        then:"A model is populated containing the domain instance"
            model.schedulerInstance == scheduler
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/scheduler/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def scheduler = new Scheduler()
            scheduler.validate()
            controller.update(scheduler)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.schedulerInstance == scheduler

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            scheduler = new Scheduler(params).save(flush: true)
            controller.update(scheduler)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/scheduler/show/$scheduler.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/scheduler/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def scheduler = new Scheduler(params).save(flush: true)

        then:"It exists"
            Scheduler.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(scheduler)

        then:"The instance is deleted"
            Scheduler.count() == 0
            response.redirectedUrl == '/scheduler/index'
            flash.message != null
    }
}
