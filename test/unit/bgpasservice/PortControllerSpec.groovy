package bgpasservice



import grails.test.mixin.*
import spock.lang.*

@TestFor(PortController)
@Mock(Port)
class PortControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
		params["portRule"] = '=12345'
    }

	def populateParams(params, portrule) {
		assert params != null
		
		params["portRule"] = portrule
	}
	
    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.portInstanceList
            model.portInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.portInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
            def port = new Port()
            port.validate()
            controller.save(port)

        then:"The create view is rendered again with the correct model"
            model.portInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            port = new Port(params)

            controller.save(port)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/port/show/1'
            controller.flash.message != null
            Port.count() == 1
    }
	
	@Unroll
	void "Test the save action with invalid parameters - portrule '#tmpPortRule'"() {
		given:
			def code
		
		when:"The save action is executed with an invalid instance"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
			populateParams(params,tmpPortRule)
			def port = new Port(params)
			
			port.validate()
			
			if(port.hasErrors())
			{
				println "\nErrors:"
				println port.errors ?: "no errors found"
				
				def badField = port.errors.getFieldError(tmpfieldname)
				println "\nBadField:"
				println badField ?: tmpfieldname+" wasn't a bad field"
				
				code = badField?.codes.find {
					it == tmpCode
				}
					
				println "\nCode:"
				println code
			}
			
			controller.save(port)

		then:"The create view is rendered again with the correct model"
			code == tmpCode
			model.portInstance!= null
			view == 'create'

		where:
			tmpfieldname  | tmpPortRule    									| tmpCode
			'portRule'    |     ''	       	   								|'port.portRule.nullable'		   //portRule cannot be null
			'portRule'    |     'test'    	   								|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |     '%%%%'	  									|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '<=12345&>=12344'   							|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '<=12345'   									|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '==1234'	  									|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '=67890'	  			    					|'port.portRule.validator.invalid'  //Invalid portValue
			'portRule'    |  '>=12345&<=12344'   							|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '=12345 >=12345&<=12346 =12349 >=12345&<=12346'|'port.portRule.validator.invalid'  //portRule cannot have duplicate rules
			'portRule'    |  '=12345 >=8080&<=8088 =9600 =4500 =7800 =6666' |'port.portRule.validator.invalid'  //portRule cannot have more than 5 rules
			'portRule'    |  '>=18080&<=18088 >=28080&<=28088 >=38080&<=38088 >=48080&<=48088 >=58080&<=58088 >=8080&<=8088' |'port.portRule.maxSize.exceeded'  //portRule cannot have more than 5 rules
	}
	
	@Unroll
	void "Test the save action with valid parameters - portrule '#tmpPortRule'"() {
		
		when:"The save action is executed with an invalid instance"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
			populateParams(params, tmpPortRule)
			def port = new Port(params)
			port.validate()
			
			if(port.hasErrors())
			{
				println "\nErrors:"
				println port.errors ?: "no errors found"
			}
			
			controller.save(port)

		then:"A redirect is issued to the show action"
			response.redirectedUrl == '/port/show/1'
			controller.flash.message != null
			Port.count() == 1
			
		where:
		tmpPortRule << ['any',
						'=8080',
						'>=8080&<=8088',
						'=12345 >=8080&<=8088 =9600 =4500 =7800']
			
	}
	

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def port = new Port(params)
            controller.show(port)

        then:"A model is populated containing the domain instance"
            model.portInstance == port
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def port = new Port(params)
            controller.edit(port)

        then:"A model is populated containing the domain instance"
            model.portInstance == port
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/port/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def port = new Port()
            port.validate()
            controller.update(port)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.portInstance == port

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            port = new Port(params).save(flush: true)
            controller.update(port)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/port/show/$port.id"
            flash.message != null
    }
	
	@Unroll
	void "Test the update action with invalid parameters - portrule '#tmpPortRule'"() {
		given:
			def code
		
		when:"The update action is executed with invalid parameters"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
			populateParams(params,tmpPortRule)
			def port = new Port(params)
			
			port.validate()
			
			if(port.hasErrors())
			{
				println "\nErrors:"
				println port.errors ?: "no errors found"
				
				def badField = port.errors.getFieldError(tmpfieldname)
				println "\nBadField:"
				println badField ?: tmpfieldname+" wasn't a bad field"
				
				code = badField?.codes.find {
					it == tmpCode
				}
					
				println "\nCode:"
				println code
			}
			
			controller.update(port)

		then:"The edit view is rendered again with the invalid instance"
			code == tmpCode
            view == 'edit'
            model.portInstance == port

		where:
			tmpfieldname  | tmpPortRule    									| tmpCode
			'portRule'    |     ''	       	   								|'port.portRule.nullable'		   //portRule cannot be null
			'portRule'    |     'test'    	   								|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |     '%%%%'	  									|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '<=12345&>=12344'   							|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '<=12345'   									|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '==1234'	  									|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '=67890'	  			    					|'port.portRule.validator.invalid'  //Invalid portValue
			'portRule'    |  '>=12345&<=12344'   							|'port.portRule.validator.invalid'  //portRule cannot be other than =portValue and >=portValue&<=portValue
			'portRule'    |  '=12345 >=12345&<=12346 =12349 >=12345&<=12346'|'port.portRule.validator.invalid'  //portRule cannot have duplicate rules
			'portRule'    |  '=12345 >=8080&<=8088 =9600 =4500 =7800 =6666' |'port.portRule.validator.invalid'  //portRule cannot have more than 5 rules
			'portRule'    |  '>=18080&<=18088 >=28080&<=28088 >=38080&<=38088 >=48080&<=48088 >=58080&<=58088 >=8080&<=8088' |'port.portRule.maxSize.exceeded'  //portRule cannot have more than 5 rules
	}
	
	@Unroll
	void "Test the update action with valid parameters - portrule '#tmpPortRule'"() {
		
		when:"The update action is executed with valid parameters"
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
			populateParams(params, tmpPortRule)
			def port = new Port(params).save(flush: true)
			port.validate()
			
			if(port.hasErrors())
			{
				println "\nErrors:"
				println port.errors ?: "no errors found"
			}
			
			controller.update(port)

		then:"A redirect is issues to the show action"
			port != null
            response.redirectedUrl == "/port/show/$port.id"
            flash.message != null
			
		where:
		tmpPortRule << ['any',
						'=8080',
						'>=8080&<=8088',
						'=12345 >=8080&<=8088 =9600 =4500 =7800']
			
	}

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/port/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def port = new Port(params).save(flush: true)

        then:"It exists"
            Port.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(port)

        then:"The instance is deleted"
            Port.count() == 0
            response.redirectedUrl == '/port/index'
            flash.message != null
    }
}
