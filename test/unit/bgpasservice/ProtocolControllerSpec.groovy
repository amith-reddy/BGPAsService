package bgpasservice



import grails.test.mixin.*
import spock.lang.*

@TestFor(ProtocolController)
@Mock(Protocol)
class ProtocolControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
		params["protocolRule"] = 'tcp'
		
    }
	
	def populateParams(params, protocolrule) {
		assert params != null
		
		params["protocolRule"] = protocolrule
		
	}

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.protocolInstanceList
            model.protocolInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.protocolInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
            def protocol = new Protocol()
            protocol.validate()
            controller.save(protocol)

        then:"The create view is rendered again with the correct model"
            model.protocolInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            protocol = new Protocol(params)

            controller.save(protocol)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/protocol/show/1'
            controller.flash.message != null
            Protocol.count() == 1
    }
	
	@Unroll
	void "Test the save action with invalid Params - protocolRule '#tmpProtocolRule'"() {
				given:
					def code
					
				when:"The save action is executed with an invalid parameters"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'POST'
					populateParams(params, tmpProtocolRule)
					def protocol = new Protocol(params)
					protocol.validate()
					
					if(protocol.hasErrors())
					{
						println "\nErrors:"
						println protocol.errors ?: "no errors found"
						
						def badField = protocol.errors.getFieldError(tmpfieldname)
						println "\nBadField:"
						println badField ?: tmpfieldname+" wasn't a bad field"
						
						code = badField?.codes.find {
							it == tmpCode
						}
							
						println "\nCode:"
						println code
					}
					
					controller.save(protocol)
		
				then:"The create view is rendered again with the correct model"
					code == tmpCode
					model.protocolInstance!= null
					view == 'create'
				
				where:
					tmpfieldname      | tmpProtocolRule    | tmpCode
					'protocolRule'    |     ''	       	   |'protocol.protocolRule.nullable'		   //protocolRule cannot be null
					'protocolRule'    |     'other'    	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be other than 'TCP, 'UDP' and 'ICMP'
					'protocolRule'    |     'tcpudp'	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be other than 'TCP, 'UDP' and 'ICMP'
					'protocolRule'    |  '123&&&---'	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot contain special characters
					'protocolRule'    |  'tcp udp other'   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be other than 'TCP, 'UDP' and 'ICMP'
					'protocolRule'    |  'tcp tcp'   	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be have duplicate values

	}
	
	@Unroll
	void "Test the save action with valid Params - protocolRule '#tmpProtocolRule'"() {
				given:
					def code
					
				when:"The save action is executed with valid parameters"
		            request.contentType = FORM_CONTENT_TYPE
					request.method = 'POST'
					populateParams(params, tmpProtocolRule)
					def protocol = new Protocol(params)
					protocol.validate()
					
					if(protocol.hasErrors())
					{
						println "\nErrors:"
						println protocol.errors ?: "no errors found"
						
						def badField = protocol.errors.getFieldError(tmpfieldname)
						println "\nBadField:"
						println badField ?: tmpfieldname+" wasn't a bad field"
						
						code = badField?.codes.find {
							it == tmpCode
						}
							
						println "\nCode:"
						println code
					}
					
					controller.save(protocol)

				then:"A redirect is issued to the show action"
		            response.redirectedUrl == '/protocol/show/1'
		            controller.flash.message != null
		            Protocol.count() == 1
				
				where:
					tmpfieldname      | tmpProtocolRule  
					'protocolRule'    |     'tcp'	       	 
					'protocolRule'    |     'udp'    	 
					'protocolRule'    |     'tcp udp'	 
					'protocolRule'    |     'icmp'	 
					'protocolRule'    |  'tcp udp icmp' 
					'protocolRule'    |  'icmp udp tcp'


	}

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def protocol = new Protocol(params)
            controller.show(protocol)

        then:"A model is populated containing the domain instance"
            model.protocolInstance == protocol
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def protocol = new Protocol(params)
            controller.edit(protocol)

        then:"A model is populated containing the domain instance"
            model.protocolInstance == protocol
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/protocol/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def protocol = new Protocol()
            protocol.validate()
            controller.update(protocol)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.protocolInstance == protocol

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            protocol = new Protocol(params).save(flush: true)
            controller.update(protocol)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/protocol/show/$protocol.id"
            flash.message != null
    }

	@Unroll
	void "Test the update action with invalid parameters - protocolRule '#tmpProtocolRule'"() {
		given:
			def code

		when:"Invalid parameters are passed to the update action"
				request.contentType = FORM_CONTENT_TYPE
				request.method = 'PUT'
				populateParams(params, tmpProtocolRule)
				def protocol = new Protocol(params)
				protocol.validate()
			
			if(protocol.hasErrors())
			{
				println "\nErrors:"
				println protocol.errors ?: "no errors found"
				
				def badField = protocol.errors.getFieldError(tmpfieldname)
				println "\nBadField:"
				println badField ?: tmpfieldname+" wasn't a bad field"
				
				code = badField?.codes.find {
					it == tmpCode
				}
					
				println "\nCode:"
				println code
			}
			
			controller.update(protocol)

		then:"The edit view is rendered again with the invalid instance"
			code == tmpCode
			view == 'edit'
			model.protocolInstance == protocol
		
		where:
			tmpfieldname      | tmpProtocolRule    | tmpCode
			'protocolRule'    |     ''	       	   |'protocol.protocolRule.nullable'		   //protocolRule cannot be null
			'protocolRule'    |     'other'    	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be other than 'TCP, 'UDP' and 'ICMP'
			'protocolRule'    |     'tcpudp'	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be other than 'TCP, 'UDP' and 'ICMP'
			'protocolRule'    |  '123&&&---'	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot contain special characters
			'protocolRule'    |  'tcp udp other'   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be other than 'TCP, 'UDP' and 'ICMP'
			'protocolRule'    |  'tcp tcp'   	   |'protocol.protocolRule.validator.invalid'  //protocolRule cannot be have duplicate values
			'protocolRule'    |  'tcp udp icmp tcp udp icmp tcp udp icmp tcp udp icmp'   	   |'protocol.protocolRule.maxSize.exceeded'  //protocolRule cannot be have duplicate values
	
	}
	
	@Unroll
	void "Test the update action with valid parameters - protocolRule '#tmpProtocolRule'"() {
		given:
			def code
			
		when:"Valid parameters are passed to the update action"
				request.contentType = FORM_CONTENT_TYPE
				request.method = 'PUT'
				populateParams(params, tmpProtocolRule)
				def protocol = new Protocol(params)
				protocol.validate()
			
			if(protocol.hasErrors())
			{
				println "\nErrors:"
				println protocol.errors ?: "no errors found"
				
				def badField = protocol.errors.getFieldError(tmpfieldname)
				println "\nBadField:"
				println badField ?: tmpfieldname+" wasn't a bad field"
				
				code = badField?.codes.find {
					it == tmpCode
				}
					
				println "\nCode:"
				println code
			}
			
			controller.update(protocol)

		then:"A redirect is issues to the show action"
			protocol != null
			response.redirectedUrl == "/protocol/show/$protocol.id"
			flash.message != null
			
		where:
			tmpfieldname      | tmpProtocolRule
			'protocolRule'    |     'tcp'
			'protocolRule'    |     'udp'
			'protocolRule'    |     'tcp udp'
			'protocolRule'    |     'icmp'
			'protocolRule'    |  'tcp udp icmp'
			'protocolRule'    |  'icmp udp tcp'
	}
	
    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/protocol/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def protocol = new Protocol(params).save(flush: true)

        then:"It exists"
            Protocol.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(protocol)

        then:"The instance is deleted"
            Protocol.count() == 0
            response.redirectedUrl == '/protocol/index'
            flash.message != null
    }
}
