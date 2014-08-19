package bgpasservice



import grails.test.mixin.*
import spock.lang.*

@TestFor(NeighborController)
@Mock(Neighbor)
class NeighborControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
		params["neighborIp"] = '72.54.47.12'
		params["neighborDescription"] = 'ORD'
		params["routerId"] = '122.54.57.35'
		params["localIp"] = '122.22.32.21'
		params["localAS"] = 12345
		params["peerAS"] = 12345
		params["gracefulRestart"] = 5
		params["md5"] = 'flowspec'
    }
	
	def populateParams(params, neighborip, neighbordesc, routerid, localip, localas, peeras, gracefulrestart,md5) {
		assert params != null

		params["neighborIp"] = neighborip
		params["neighborDescription"] = neighbordesc
		params["routerId"] = routerid
		params["localIp"] = localip
		params["localAS"] = localas
		params["peerAS"] = peeras
		params["gracefulRestart"] = gracefulrestart
		params["md5"] = md5
	}

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.neighborInstanceList
            model.neighborInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.neighborInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
            def neighbor = new Neighbor()
            neighbor.validate()
            controller.save(neighbor)

        then:"The create view is rendered again with the correct model"
            model.neighborInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            neighbor = new Neighbor(params)

            controller.save(neighbor)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/neighbor/show/1'
            controller.flash.message != null
            Neighbor.count() == 1
    }
	
	@Unroll
	void "Test the save action with invalid parameters - '#tmpneighbordesc' | '#tmpneighborip' | '#tmprouterid' | '#tmplocalip'| '#tmplocalas' | '#tmppeeras'| '#tmpgracefulrestart'| '#tmpmd5'"() {
				given:
					def code
					
				when:"The save action is executed with invalid parameters"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'POST'
					populateParams(params,tmpneighborip, tmpneighbordesc, tmprouterid, tmplocalip, tmplocalas, tmppeeras, tmpgracefulrestart,tmpmd5)
					def neighbor = new Neighbor(params)
					neighbor.validate()
					
					if(neighbor.hasErrors())
					{
						println "\nErrors:"
						println neighbor.errors ?: "no errors found"
						
						def badField = neighbor.errors.getFieldError(tmpfieldname)
						println "\nBadField:"
						println badField ?: tmpfieldname+" wasn't a bad field"
						
						code = badField?.codes.find {
							it == tmpCode
						}
							
						println "\nCode:"
						println code
					}
					
					controller.save(neighbor)
		
				then:"The create view is rendered again with the correct model"
					code == tmpCode
					model.neighborInstance!= null
					view == 'create'
					
				where:
					tmpfieldname		  	|tmpneighbordesc	|tmpneighborip		|tmprouterid		|tmplocalip			|tmplocalas	|tmppeeras	|tmpgracefulrestart	|tmpmd5		|tmpCode
					 'neighborDescription'	| ''				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborDescription.nullable'
					 'neighborDescription'	| 'Neighbor description length is more than forty characters.'| '72.145.23.43'| '10.25.45.24'| '10.25.45.80'|16815|16815|5	| 'flowspec'| 'neighbor.neighborDescription.maxSize.exceeded'
					 'neighborIp'			| 'ORD'				| ''				| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.nullable'
					 'neighborIp'			| 'ORD'				| '10.10.544.11'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.invalid.ip'
					 'neighborIp'			| 'ORD'				| '10.10.11'		| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.invalid.ip'
					 'neighborIp'			| 'ORD'				| '10.10.4411.44444'| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.maxSize.exceeded'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| ''				| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.nullable'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| '10.10.544.11'	| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.invalid.ip'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| '10.10.11'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.invalid.ip'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| '10.10.4411.44444'| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.maxSize.exceeded'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| ''				|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.nullable'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.10.544.11'	|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.invalid.ip'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.10.11'		|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.invalid.ip'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.10.4411.44444'|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.maxSize.exceeded'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		| ''		|16815		|5					| 'flowspec'| 'neighbor.localAS.nullable'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		| 'test'	|16815		|5					| 'flowspec'| 'neighbor.localAS.typeMismatch'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		| '1 test'	|16815		|5					| 'flowspec'| 'neighbor.localAS.typeMismatch'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|0			|16815		|5					| 'flowspec'| 'neighbor.localAS.min.error'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|4294967297	|16815		|5					| 'flowspec'| 'neighbor.localAS.max.error'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		| ''		|5					| 'flowspec'| 'neighbor.peerAS.nullable'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		| 'test'	|5					| 'flowspec'| 'neighbor.peerAS.typeMismatch'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		| '1 test'	|5					| 'flowspec'| 'neighbor.peerAS.typeMismatch'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|0			|5					| 'flowspec'| 'neighbor.peerAS.min.error'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|4294967297	|5					| 'flowspec'| 'neighbor.peerAS.max.error'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		| ''				| 'flowspec'| 'neighbor.gracefulRestart.nullable'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		| 'test'			| 'flowspec'| 'neighbor.gracefulRestart.typeMismatch'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		| '1 test'			| 'flowspec'| 'neighbor.gracefulRestart.typeMismatch'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|0					| 'flowspec'| 'neighbor.gracefulRestart.min.error'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|3601				| 'flowspec'| 'neighbor.gracefulRestart.max.error'
					 'md5'					| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| ''		| 'neighbor.md5.nullable'
					 'md5'					| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'MD5 field length is more than forty characters.'| 'neighbor.md5.maxSize.exceeded'	
	}
	
	@Unroll
	void "Test the save action with valid parameters - '#tmpneighbordesc' | '#tmpneighborip' | '#tmprouterid' | '#tmplocalip'| '#tmplocalas' | '#tmppeeras'| '#tmpgracefulrestart'| '#tmpmd5'"() {
				
				when:"The save action is executed with valid parameters"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'POST'
					populateParams(params,tmpneighborip, tmpneighbordesc, tmprouterid, tmplocalip, tmplocalas, tmppeeras, tmpgracefulrestart,tmpmd5)
					def neighbor = new Neighbor(params)
					neighbor.validate()
		
					if(neighbor.hasErrors())
					{
						println "\nErrors:"
						println neighbor.errors ?: "no errors found"
					}
					controller.save(neighbor)
		
				then:"A redirect is issued to the show action"
					response.redirectedUrl == '/neighbor/show/1'
					controller.flash.message != null
					Neighbor.count() == 1
					
				where:
				tmpneighbordesc |tmpneighborip	|tmprouterid	|tmplocalip		|tmplocalas	|tmppeeras	|tmpgracefulrestart	|tmpmd5
				'ORD'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|1			|1			|1					| 'flowspec'
				'ORD'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|4294967296	|4294967296	|3600				| 'flowspec'
				'ORD'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|16815		|16815		|5					| 'flowspec'
				'ORD-1'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|16815		|16815		|5					| 'flowspec-1'
			   
	}

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def neighbor = new Neighbor(params)
            controller.show(neighbor)

        then:"A model is populated containing the domain instance"
            model.neighborInstance == neighbor
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def neighbor = new Neighbor(params)
            controller.edit(neighbor)

        then:"A model is populated containing the domain instance"
            model.neighborInstance == neighbor
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/neighbor/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def neighbor = new Neighbor()
            neighbor.validate()
            controller.update(neighbor)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.neighborInstance == neighbor

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            neighbor = new Neighbor(params).save(flush: true)
            controller.update(neighbor)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/neighbor/show/$neighbor.id"
            flash.message != null
    }
	
	@Unroll
	void "Test the update action with invalid parameters - '#tmpneighbordesc' | '#tmpneighborip' | '#tmprouterid' | '#tmplocalip'| '#tmplocalas' | '#tmppeeras'| '#tmpgracefulrestart'| '#tmpmd5'"() {
				given:
					def code
					
				when:"The update action is executed with invalid parameters"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'PUT'
					populateParams(params,tmpneighborip, tmpneighbordesc, tmprouterid, tmplocalip, tmplocalas, tmppeeras, tmpgracefulrestart,tmpmd5)
					def neighbor = new Neighbor(params)
					neighbor.validate()
					
					if(neighbor.hasErrors())
					{
						println "\nErrors:"
						println neighbor.errors ?: "no errors found"
						
						def badField = neighbor.errors.getFieldError(tmpfieldname)
						println "\nBadField:"
						println badField ?: tmpfieldname+" wasn't a bad field"
						
						code = badField?.codes.find {
							it == tmpCode
						}
							
						println "\nCode:"
						println code
					}
					
					 controller.update(neighbor)
		
				 then:"The edit view is rendered again with the invalid instance"
				 		code == tmpCode
			            view == 'edit'
			            model.neighborInstance == neighbor
					
				where:
					tmpfieldname		  	|tmpneighbordesc	|tmpneighborip		|tmprouterid		|tmplocalip			|tmplocalas	|tmppeeras	|tmpgracefulrestart	|tmpmd5		|tmpCode
					 'neighborDescription'	| ''				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborDescription.nullable'
					 'neighborDescription'	| 'Neighbor description length is more than forty characters.'| '72.145.23.43'| '10.25.45.24'| '10.25.45.80'|16815|16815|5	| 'flowspec'| 'neighbor.neighborDescription.maxSize.exceeded'
					 'neighborIp'			| 'ORD'				| ''				| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.nullable'
					 'neighborIp'			| 'ORD'				| '10.10.544.11'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.invalid.ip'
					 'neighborIp'			| 'ORD'				| '10.10.11'		| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.invalid.ip'
					 'neighborIp'			| 'ORD'				| '10.10.4411.44444'| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.neighborIp.maxSize.exceeded'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| ''				| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.nullable'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| '10.10.544.11'	| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.invalid.ip'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| '10.10.11'		| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.invalid.ip'
					 'routerId'				| 'ORD'				| '72.145.23.43'	| '10.10.4411.44444'| '10.25.45.80'		|16815		|16815		|5					| 'flowspec'| 'neighbor.routerId.maxSize.exceeded'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| ''				|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.nullable'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.10.544.11'	|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.invalid.ip'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.10.11'		|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.invalid.ip'
					 'localIp'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.10.4411.44444'|16815		|16815		|5					| 'flowspec'| 'neighbor.localIp.maxSize.exceeded'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		| ''		|16815		|5					| 'flowspec'| 'neighbor.localAS.nullable'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		| 'test'	|16815		|5					| 'flowspec'| 'neighbor.localAS.typeMismatch'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		| '1 test'	|16815		|5					| 'flowspec'| 'neighbor.localAS.typeMismatch'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|0			|16815		|5					| 'flowspec'| 'neighbor.localAS.min.error'
					 'localAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|4294967297	|16815		|5					| 'flowspec'| 'neighbor.localAS.max.error'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		| ''		|5					| 'flowspec'| 'neighbor.peerAS.nullable'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		| 'test'	|5					| 'flowspec'| 'neighbor.peerAS.typeMismatch'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		| '1 test'	|5					| 'flowspec'| 'neighbor.peerAS.typeMismatch'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|0			|5					| 'flowspec'| 'neighbor.peerAS.min.error'
					 'peerAS'				| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|4294967297	|5					| 'flowspec'| 'neighbor.peerAS.max.error'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		| ''				| 'flowspec'| 'neighbor.gracefulRestart.nullable'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		| 'test'			| 'flowspec'| 'neighbor.gracefulRestart.typeMismatch'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		| '1 test'			| 'flowspec'| 'neighbor.gracefulRestart.typeMismatch'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|0					| 'flowspec'| 'neighbor.gracefulRestart.min.error'
					 'gracefulRestart'		| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|3601				| 'flowspec'| 'neighbor.gracefulRestart.max.error'
					 'md5'					| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| ''		| 'neighbor.md5.nullable'
					 'md5'					| 'ORD'				| '72.145.23.43'	| '10.25.45.24'		| '10.25.45.80'		|16815		|16815		|5					| 'MD5 field length is more than forty characters.'| 'neighbor.md5.maxSize.exceeded'
		
}
	
	@Unroll
	void "Test the update action with valid parameters - '#tmpneighbordesc' | '#tmpneighborip' | '#tmprouterid' | '#tmplocalip'| '#tmplocalas' | '#tmppeeras'| '#tmpgracefulrestart'| '#tmpmd5'"() {
				
				when:"The update action is executed with valid parameters"
					request.contentType = FORM_CONTENT_TYPE
					request.method = 'PUT'
					populateParams(params,tmpneighborip, tmpneighbordesc, tmprouterid, tmplocalip, tmplocalas, tmppeeras, tmpgracefulrestart,tmpmd5)
					def neighbor = new Neighbor(params).save(flush: true)
					neighbor.validate()
		
					if(neighbor.hasErrors())
					{
						println "\nErrors:"
						println neighbor.errors ?: "no errors found"
					}
					
					controller.update(neighbor)
		
				then:"A redirect is issues to the show action"
					neighbor != null
					response.redirectedUrl == "/neighbor/show/$neighbor.id"
					flash.message != null
					
				where:
				tmpneighbordesc |tmpneighborip	|tmprouterid	|tmplocalip		|tmplocalas	|tmppeeras	|tmpgracefulrestart	|tmpmd5
				'ORD'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|1			|1			|1					| 'flowspec'
				'ORD'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|4294967296	|4294967296	|3600				| 'flowspec'
				'ORD'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|16815		|16815		|5					| 'flowspec'
				'ORD-1'			| '72.145.23.43'| '10.25.45.24'	| '10.25.45.80'	|16815		|16815		|5					| 'flowspec-1'
			   
	}

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
			request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/neighbor/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def neighbor = new Neighbor(params).save(flush: true)

        then:"It exists"
            Neighbor.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(neighbor)

        then:"The instance is deleted"
            Neighbor.count() == 0
            response.redirectedUrl == '/neighbor/index'
            flash.message != null
    }
}
