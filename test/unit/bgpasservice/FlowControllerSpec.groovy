package bgpasservice

import grails.test.mixin.*
import spock.lang.*
import common.*;

@TestFor(FlowController)
@Mock([Flow,Scheduler])
class FlowControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'

		params["sourceSubnet"] = '126.54.36.25/24'
		params["destinationSubnet"] = '145.25.33.14/24'
		params["status"] = 'new'
//		params["neighbors.id"] = 1
//		params["protocol.id"] = 1
//		params["exaaction.id"] = 1
//		params["sourceport.id"] = 1
//		params["destinationport.id"] = 1
		def e = new Exaaction(actionName:"discard", actionParameter:"")
		def n = new Neighbor(neighborIp: "10.10.10.10",neighborDescription: "test", localIp:"10.10.10.10", routerId:"10.10.10.10", localAS:"12345", peerAS:"12345")
		def spt = new Port(portRule: "=12345")
		def dpt = new Port(portRule: "=12345")
		def pl = new Protocol(protocolRule:"tcp")
		params["neighbors"] = n
		params["protocol"] = pl
		params["exaaction"] = e
		params["sourceport"] = spt
		params["destinationport"] = dpt
		
    }
	
	def populateParams(params, sourcesubnet, destinationsubnet,statusfield ) {
		assert params != null

		params["sourceSubnet"] = sourcesubnet
		params["destinationSubnet"] = destinationsubnet
		params["status"] = statusfield
		
		def e = new Exaaction(actionName:"discard", actionParameter:"")
		def n = new Neighbor(neighborIp: "10.10.10.10",neighborDescription: "test", localIp:"10.10.10.10", routerId:"10.10.10.10", localAS:"12345", peerAS:"12345")
		def spt = new Port(portRule: "=12345")
		def dpt = new Port(portRule: "=12345")
		def pl = new Protocol(protocolRule:"tcp")
		
		params["neighbors"] = n
		params["protocol"] = pl
		params["exaaction"] = e
		params["sourceport"] = spt
		params["destinationport"] = dpt
	}

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.flowInstanceList
            //model.flowInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.flowInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
            def flow = new Flow()
            flow.validate()
            controller.save(flow)

        then:"The create view is rendered again with the correct model"
            model.flowInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            flow = new Flow(params)
			flow.validate()
			
            controller.save(flow)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/flow/show/1'
            controller.flash.message != null
            Flow.count() == 1
    }
	
	@Unroll
	void "Test the save action with Invalid Params - SourceSubnet '#tmpSourceSubnet' DestinationSubnet '#tmpDestinationSubnet' status '#tmpStatusField'"() {
				given:
					def code
				when:"The save action is executed with invalid parameters"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'POST'
					populateParams(params, tmpSourceSubnet, tmpDestinationSubnet,tmpStatusField)

					def flow = new Flow(params)

					flow.validate()
					
					if(flow.hasErrors())
					{
						println "\nErrors:"
						println flow.errors ?: "no errors found"
						
						def badField = flow.errors.getFieldError(tmpfieldname)
						println "\nBadField:"
						println badField ?: tmpfieldname+" wasn't a bad field"
						
						code = badField?.codes.find {
							it == tmpCode
						}
							
						println "\nCode:"
						println code
					}
					
					
					controller.save(flow)
		
				then:"The create view is rendered again with the correct model"
					code == tmpCode
					model.flowInstance!= null
					view == 'create'
				
				where:
				tmpfieldname      	| tmpSourceSubnet    	| tmpDestinationSubnet | tmpStatusField |tmpCode
				'sourceSubnet'    	|     ''	       		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.nullable'					//source subnet cannot be null
				'sourceSubnet'    	|    '10.10.10.'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
				'sourceSubnet'    	|    '10.10.256.10'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
				'sourceSubnet'    	|    '10.10.10.10'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
				'sourceSubnet'    	|    '10.10.10.10/'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
				'sourceSubnet'    	|    '10.10.10.10/-1'	|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
				'sourceSubnet'    	|    '10.10.10.10/33'	|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
				'sourceSubnet'    	|    '10.10.10.10/%'	|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
				'sourceSubnet'    	|'255.255.255.255/44444'|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.maxSize.exceeded'			//source subnet format should not be more than 20 characters
				'destinationSubnet' |    '10.10.10.10/24'	|	 		''  	   |	'new'		|'flow.destinationSubnet.nullable'				//destination subnet cannot be null
				'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.'  	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
				'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.256.10'	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
				'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10' 	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
				'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/'	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
				'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/-1'  |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
				'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/33'  |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
				'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/%'   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
				'destinationSubnet' |    '10.10.10.10/24'	|'255.255.255.255/44444'|	'new'		|'flow.destinationSubnet.maxSize.exceeded'		//destination subnet format should be validIP/mask
				'status' 			|    '10.10.10.10/24'	|	 '10.10.10.10/24'  |	''			|'flow.status.nullable'							//status cannot be null
				'status' 			|    '10.10.10.10/24'	|	 '10.10.10.10/24'  |	'other'		|'flow.status.not.inList'						//status cannot be other than 'new' or 'done'
				
					
	}
	
	@Unroll
	void "Test the save action with Valid Params - SourceSubnet '#tmpSourceSubnet' DestinationSubnet '#tmpDestinationSubnet' status '#tmpStatusField'"() {

		when:"The save action is executed with valid parameters"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
			populateParams(params, tmpSourceSubnet, tmpDestinationSubnet,tmpStatusField)

			def flow = new Flow(params)
			flow.validate()
			
			if(flow.hasErrors())
			{
				println "\nErrors:"
				println flow.errors ?: "no errors found"	
			}
			
			controller.save(flow)

		then:"A redirect is issued to the show action"
            response.redirectedUrl == '/flow/show/1'
            controller.flash.message != null
            Flow.count() == 1
		
		where:
			tmpSourceSubnet  	| tmpDestinationSubnet | tmpStatusField
		    '10.10.10.10/24'	|	 '10.10.10.10/24'  |	'new'		
		    '10.10.10.10/0'		|	 '10.10.10.10/0'   |	'new'		
			'10.10.10.10/32'	|	 '10.10.10.10/32'  |	'new'		
			'10.10.10.10/32'	|	 '10.10.10.10/32'  |	'done'	
}

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def flow = new Flow(params).save(flush: true)
            controller.show(flow)

        then:"A model is populated containing the domain instance"
            model.flowInstance == flow
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def flow = new Flow(params)
            controller.edit(flow)

        then:"A model is populated containing the domain instance"
            model.flowInstance == flow
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/flow/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def flow = new Flow()
            flow.validate()
            controller.update(flow)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.flowInstance == flow

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            flow = new Flow(params).save(flush: true)
            controller.update(flow)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/flow/show/$flow.id"
            flash.message != null
    }
	
	@Unroll
	void "Test the update action with Invalid Params - SourceSubnet '#tmpSourceSubnet' DestinationSubnet '#tmpDestinationSubnet' status '#tmpStatusField'"() {
		
		given:
			def code
		when:"The update action is executed with invalid parameters"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
			populateParams(params, tmpSourceSubnet, tmpDestinationSubnet,tmpStatusField)

			def flow = new Flow(params)

			flow.validate()
			
			if(flow.hasErrors())
			{
				println "\nErrors:"
				println flow.errors ?: "no errors found"
				
				def badField = flow.errors.getFieldError(tmpfieldname)
				println "\nBadField:"
				println badField ?: tmpfieldname+" wasn't a bad field"
				
				code = badField?.codes.find {
					it == tmpCode
				}
					
				println "\nCode:"
				println code
			}
			
			controller.update(flow)

		then:"The create view is rendered again with the correct model"
			code == tmpCode
			view == 'edit'
			model.flowInstance == flow
		
		where:
			tmpfieldname      	| tmpSourceSubnet    	| tmpDestinationSubnet | tmpStatusField |tmpCode
			'sourceSubnet'    	|     ''	       		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.nullable'					//source subnet cannot be null
			'sourceSubnet'    	|    '10.10.10.'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
			'sourceSubnet'    	|    '10.10.256.10'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
			'sourceSubnet'    	|    '10.10.10.10'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
			'sourceSubnet'    	|    '10.10.10.10/'		|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
			'sourceSubnet'    	|    '10.10.10.10/-1'	|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
			'sourceSubnet'    	|    '10.10.10.10/33'	|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
			'sourceSubnet'    	|    '10.10.10.10/%'	|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.invalid.ipsubnet'			//source subnet format should be validIP/mask
			'sourceSubnet'    	|'255.255.255.255/44444'|	 '10.10.10.10/24'  |	'new'		|'flow.sourceSubnet.maxSize.exceeded'			//source subnet format should not be more than 20 characters
			'destinationSubnet' |    '10.10.10.10/24'	|	 		''  	   |	'new'		|'flow.destinationSubnet.nullable'				//destination subnet cannot be null
			'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.'  	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
			'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.256.10'	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
			'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10' 	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
			'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/'	   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
			'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/-1'  |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
			'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/33'  |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
			'destinationSubnet' |    '10.10.10.10/24'	|	 '10.10.10.10/%'   |	'new'		|'flow.destinationSubnet.invalid.ipsubnet'		//destination subnet format should be validIP/mask
			'destinationSubnet' |    '10.10.10.10/24'	|'255.255.255.255/44444'|	'new'		|'flow.destinationSubnet.maxSize.exceeded'		//destination subnet format should be validIP/mask
			'status' 			|    '10.10.10.10/24'	|	 '10.10.10.10/24'  |	''			|'flow.status.nullable'							//status cannot be null
			'status' 			|    '10.10.10.10/24'	|	 '10.10.10.10/24'  |	'other'		|'flow.status.not.inList'						//status cannot be other than 'new' or 'done'
	}
	
	@Unroll
	void "Test the update action with Valid Params - SourceSubnet '#tmpSourceSubnet' DestinationSubnet '#tmpDestinationSubnet' status '#tmpStatusField'"() {
		when:"The up action is executed with valid parameters"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
			populateParams(params, tmpSourceSubnet, tmpDestinationSubnet,tmpStatusField)
	
			def flow = new Flow(params).save(flush: true)
			flow.validate()
			
			if(flow.hasErrors())
			{
				println "\nErrors:"
				println flow.errors ?: "no errors found"
			}
			
			
            controller.update(flow)

		then:"A redirect is issued to the show action"
			response.redirectedUrl == "/flow/show/$flow.id"
				flash.message != null
		
		where:
			tmpSourceSubnet  	| tmpDestinationSubnet | tmpStatusField
			'10.10.10.10/24'	|	 '10.10.10.10/24'  |	'new'
			'10.10.10.10/0'		|	 '10.10.10.10/0'   |	'new'
			'10.10.10.10/32'	|	 '10.10.10.10/32'  |	'new'
			'10.10.10.10/32'	|	 '10.10.10.10/32'  |	'done'
		
	}

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/flow/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def flow = new Flow(params).save(flush: true)

        then:"It exists"
            Flow.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(flow)

        then:"The instance is deleted"
            Flow.count() == 0
            response.redirectedUrl == '/flow/index'
            flash.message != null
    }
}
