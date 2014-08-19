package bgpasservice



import grails.test.mixin.*
import spock.lang.*

@TestFor(ExaactionController)
@Mock(Exaaction)
class ExaactionControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
		params["actionName"] = 'discard'
		params["actionParameter"] = 'null'
    }
	
	def populateParams(params, actionname, actionparameter ) {
		assert params != null

		params["actionName"] = actionname
		params["actionParameter"] = actionparameter
	}

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.exaactionInstanceList
            model.exaactionInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.exaactionInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
            def exaaction = new Exaaction()
            exaaction.validate()
            controller.save(exaaction)

        then:"The create view is rendered again with the correct model"
            model.exaactionInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            exaaction = new Exaaction(params)

            controller.save(exaaction)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/exaaction/show/1'
            controller.flash.message != null
            Exaaction.count() == 1
    }
	
	@Unroll
	void "Test the save action with invalid Params - actionName '#tmpActionName' and actionParameter '#tmpActionParameter' "() {
		
				given:
					def code
				when:"The save action is executed with an invalid instance"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'POST'
					populateParams(params, tmpActionName,tmpActionParameter)

					
					def exaaction = new Exaaction(params)
					exaaction.validate()
					
					if(exaaction.hasErrors())
					{
						println "\nErrors:"
						println exaaction.errors ?: "no errors found"
						
						def badField = exaaction.errors.getFieldError(tmpfieldname)
						println "\nBadField:"
						println badField ?: tmpfieldname+" wasn't a bad field"
						
						code = badField?.codes.find {
							it == tmpCode
						}
							
						println "\nCode:"
						println code
					}
						
					controller.save(exaaction)
		
				then:"The create view is rendered again with the correct model"
					code == tmpCode
					model.exaactionInstance!= null
					view == 'create'
					
					
				where:
					  tmpfieldname      | tmpActionName    | tmpActionParameter | tmpCode
					  'actionName'      |     ''	       |	 ''				|'exaaction.actionName.nullable'				//action name and action parameter cannot be null 
					  'actionName'      |     ''		   |      'test'		|'exaaction.actionName.nullable'				//action name cannot be null
					  'actionName'      |  'other'		   |	  'test'		|'exaaction.actionName.not.inList' 				//action name has to be either discard, rate-limit, redirect
					  'actionParameter' |  'discard'	   |	  'test'		|'exaaction.actionParameter.invalid.discard.value'  //If the action name is 'discard', action parameter should be NULL
					  'actionParameter' |  'discard'	   |	  ''			|'exaaction.actionParameter.invalid.discard.value'  //If the action name is 'discard', action parameter should be NULL
					  'actionParameter' |  'rate-limit'	   |	  ''			|'exaaction.actionParameter.invalid.ratelimit.range'	//If the action name is 'rate-limit', action parameter should not be NULL
					  'actionParameter' |  'rate-limit'	   |	  '1234.55'		|'exaaction.actionParameter.invalid.ratelimit.range'	//If the action name is 'rate-limit', action parameter should be not be decimal
					  'actionParameter' |  'rate-limit'	   |	  'test!!!'		|'exaaction.actionParameter.invalid.ratelimit.range'  //If the action name is 'rate-limit', action parameter should be not contain special characters
					  'actionParameter' |  'rate-limit'	   |	  '-1'		    |'exaaction.actionParameter.invalid.ratelimit.range'  //If the action name is 'rate-limit', action parameter should be not be negative
					  'actionParameter' |  'rate-limit'	   |	  '4294967297'	|'exaaction.actionParameter.invalid.ratelimit.range'  //If the action name is 'rate-limit', action parameter should be not be out of range
					  'actionParameter' |  'redirect'	   |	  ''			|'exaaction.actionParameter.invalid.redirect.format'		   //If the action name is 'rediect', action parameter should be not be null
					  'actionParameter' |  'redirect'	   |'10.10.10'	        |'exaaction.actionParameter.invalid.redirect.format' //invalid IP
					  'actionParameter' |  'redirect'	   |'10.10.10.10:66555'	|'exaaction.actionParameter.invalid.redirect.format' //invalid Port
					  'actionParameter' |  'redirect'	   |'12%456:2345'	    |'exaaction.actionParameter.invalid.redirect.format' //invalid AS
					  'actionParameter' |  'redirect'	   |'12"456:2345'	    |'exaaction.actionParameter.invalid.redirect.format' //invalid AS		  
					  'actionParameter' |  'redirect'	   |'12s456:66555'	    |'exaaction.actionParameter.invalid.redirect.format' //invalid AS
	}
	
	@Unroll
	void "Test the save action with valid Params - actionName '#tmpActionName' and actionParameter '#tmpActionParameter' "() {
		
				when:"The save action is executed with an Valid Params"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'POST'
					populateParams(params, tmpActionName,tmpActionParameter)

					def exaaction = new Exaaction(params)
					exaaction.validate()
					
									
					controller.save(exaaction)
		
				then:"A redirect is issued to the show action"
					Exaaction.count() == 1
					response.redirectedUrl == '/exaaction/show/1'
					controller.flash.message != null
					Exaaction.count() == 1
				
					
				where:
					  tmpActionName    | tmpActionParameter 	
					   'discard'	   |	  'null'					//If the action name is 'discard', action parameter should be NULL
					   'rate-limit'	   |	  '0'			    	//If the action name is 'rate-limit', action parameter is valid integer
					   'rate-limit'	   |	  '9600'			   	//If the action name is 'rate-limit', action parameter is valid integer - min value
					   'rate-limit'	   |	  '4294967296'		    //If the action name is 'rate-limit', action parameter is valid integer	- max value
					   'redirect'	   |	  '10.10.10.10'		    //If the action name is 'redirect', action parameter should be valid IP	
					   'redirect'	   |	  '10.10.10.10:1234'	//If the action name is 'redirect', action parameter should be "ValidIP:ValidPort"  
					   'redirect'	   |	  '12345:54445'			//If the action name is 'redirect', action parameter is valid "ValidAS:ValidPort"
	}

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def exaaction = new Exaaction(params)
            controller.show(exaaction)

        then:"A model is populated containing the domain instance"
            model.exaactionInstance == exaaction
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def exaaction = new Exaaction(params)
            controller.edit(exaaction)

        then:"A model is populated containing the domain instance"
            model.exaactionInstance == exaaction
    }
	

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/exaaction/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def exaaction = new Exaaction()
            exaaction.validate()
            controller.update(exaaction)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.exaactionInstance == exaaction

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            exaaction = new Exaaction(params).save(flush: true)
            controller.update(exaaction)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/exaaction/show/$exaaction.id"
            flash.message != null
    }
	
	@Unroll
	void "Test the update action with invalid Params - actionName '#tmpActionName' and actionParameter '#tmpActionParameter'"() {
		
				given:
						def code
				when:"An invalid domain parameter is passed to the update action"
						request.contentType = FORM_CONTENT_TYPE
						request.method = 'PUT'
						populateParams(params, tmpActionName,tmpActionParameter)
						
						def exaaction = new Exaaction(params)
						exaaction.validate()
						
						println "\nErrors:"
						println exaaction.errors ?: "no errors found"
						
						def badField = exaaction.errors.getFieldError(tmpfieldname)
						println "\nBadField:"
						println badField ?: tmpfieldname+" wasn't a bad field"
						
						code = badField?.codes.find {
							it == tmpCode
						}
							
						println "\nCode:"
						println code
							
						controller.update(exaaction)
	
				then:"The edit view is rendered again with the invalid instance"
						code == tmpCode
						view == 'edit'
						model.exaactionInstance == exaaction
								
					
				where:
					  tmpfieldname      | tmpActionName    | tmpActionParameter | tmpCode
					  'actionName'      |     ''	       |	 ''				|'exaaction.actionName.nullable'				//action name and action parameter cannot be null 
					  'actionName'      |     ''		   |      'test'		|'exaaction.actionName.nullable'				//action name cannot be null
					  'actionName'      |  'other'		   |	  'test'		|'exaaction.actionName.not.inList' 				//action name has to be either discard, rate-limit, redirect
					  'actionParameter' |  'discard'	   |	  'test'		|'exaaction.actionParameter.invalid.discard.value'  //If the action name is 'discard', action parameter should be NULL
					  'actionParameter' |  'discard'	   |	  ''			|'exaaction.actionParameter.invalid.discard.value'  //If the action name is 'discard', action parameter should be NULL
					  'actionParameter' |  'rate-limit'	   |	  ''			|'exaaction.actionParameter.invalid.ratelimit.range'	//If the action name is 'rate-limit', action parameter should not be NULL
					  'actionParameter' |  'rate-limit'	   |	  '1234.55'		|'exaaction.actionParameter.invalid.ratelimit.range'	//If the action name is 'rate-limit', action parameter should be not be decimal
					  'actionParameter' |  'rate-limit'	   |	  'test!!!'		|'exaaction.actionParameter.invalid.ratelimit.range'  //If the action name is 'rate-limit', action parameter should be not contain special characters
					  'actionParameter' |  'rate-limit'	   |	  '-1'		    |'exaaction.actionParameter.invalid.ratelimit.range'  //If the action name is 'rate-limit', action parameter should be not be negative
					  'actionParameter' |  'rate-limit'	   |	  '4294967297'	|'exaaction.actionParameter.invalid.ratelimit.range'  //If the action name is 'rate-limit', action parameter should be not be out of range
					  'actionParameter' |  'redirect'	   |	  ''			|'exaaction.actionParameter.invalid.redirect.format'		   //If the action name is 'rediect', action parameter should be not be null
					  'actionParameter' |  'redirect'	   |'10.10.10'	        |'exaaction.actionParameter.invalid.redirect.format' //invalid IP
					  'actionParameter' |  'redirect'	   |'10.10.10.10:66555'	|'exaaction.actionParameter.invalid.redirect.format' //invalid Port
					  'actionParameter' |  'redirect'	   |'12%456:2345'	    |'exaaction.actionParameter.invalid.redirect.format' //invalid AS
					  'actionParameter' |  'redirect'	   |'12"456:2345'	    |'exaaction.actionParameter.invalid.redirect.format' //invalid AS		  
					  'actionParameter' |  'redirect'	   |'12s456:66555'	    |'exaaction.actionParameter.invalid.redirect.format' //invalid AS
	}
	
	@Unroll
	void "Test the update action with Valid Params - actionName '#tmpActionName' and actionParameter '#tmpActionParameter'"() {
		
				when:"A valid domain instance is passed to the update action"
			            request.contentType = FORM_CONTENT_TYPE
						request.method = 'PUT'
			            populateParams(params,tmpActionName,tmpActionParameter)
			            def exaaction = new Exaaction(params).save(flush: true)
			            controller.update(exaaction)

				then:"A redirect is issues to the show action"
						exaaction != null
			            response.redirectedUrl == "/exaaction/show/$exaaction.id"
			            flash.message != null
				
				where:
					  tmpActionName    | tmpActionParameter
					   'discard'	   |	  'null'					//If the action name is 'discard', action parameter should be NULL
					   'rate-limit'	   |	  '0'			    	//If the action name is 'rate-limit', action parameter is valid integer
					   'rate-limit'	   |	  '9600'			   	//If the action name is 'rate-limit', action parameter is valid integer - min value
					   'rate-limit'	   |	  '4294967296'		    //If the action name is 'rate-limit', action parameter is valid integer	- max value
					   'redirect'	   |	  '10.10.10.10'		    //If the action name is 'redirect', action parameter should be valid IP
					   'redirect'	   |	  '10.10.10.10:1234'	//If the action name is 'redirect', action parameter should be "ValidIP:ValidPort"
					   'redirect'	   |	  '12345:54445'			//If the action name is 'redirect', action parameter is valid "ValidAS:ValidPort"
	}
	
    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/exaaction/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def exaaction = new Exaaction(params).save(flush: true)

        then:"It exists"
            Exaaction.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(exaaction)

        then:"The instance is deleted"
            Exaaction.count() == 0
            response.redirectedUrl == '/exaaction/index'
            flash.message != null
    }
}
