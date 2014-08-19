package bgpasservice 

import bgpasservice.neighbor.CreateNeighborPage;
import bgpasservice.neighbor.EditNeighborPage;
import bgpasservice.neighbor.ListNeighborPage;
import bgpasservice.neighbor.ShowNeighborPage;
import bgpasservice.login.*;
import geb.spock.GebReportingSpec
import spock.lang.*

//import pages.*

@Stepwise
class NeighborCRUDSpec extends GebReportingSpec {
	
	def "Login Page"() {
		when:
		go "http://localhost:8080/BGPAsService/"
//		then:
//		at LoginPage
//		when:
//		j_username = "aravuru"
//		j_password = "Testdemo1"
//		loginButton.click()
		then:
		at HomePage
	}
	
	def "Empty Neighbor Page"() {
		when:
		to ListNeighborPage
		then:
		neighborRows.size() == 0
	}
	
	def "Add a Neighbor"() {
		when:
		newNeighborButton.click()
		then:
		at CreateNeighborPage
	}
	
	def "Enter the Neighbor details"() {
		when:
			neighborIp = "173.199.62.2"
			neighborDescription = "edge1.sjc-lab"
			routerId = "10.22.6.12"
			localIp  = "10.22.6.12"
			localAS	= 16815;
			peerAS	= 16815;
			gracefulRestart = 5;
			md5 = "flowspec"
			
			createButton.click()
		then:
		at ShowNeighborPage
	}
	
	def "Check the entered Neighbor details"() {
		expect:
		assert neighborIp == "173.199.62.2"
		assert neighborDescription == "edge1.sjc-lab"
		assert routerId == "10.22.6.12"
		assert localIp  == "10.22.6.12"
		assert localAS	== "16815";
		assert peerAS	== "16815";
		assert gracefulRestart == "5";
		assert md5 == "flowspec"
	}

	def "Edit the Neighbor details"() {
		when:
		editButton.click()
		then:
		at EditNeighborPage
		when:
		Thread.sleep(1000)
		neighborIp = "173.199.62.4"
		neighborDescription = "edge1.las-lab"
        updateButton.click()
		then:
		at ShowNeighborPage
	}
	
	def "check the list Neighbor"() {
		when:
		to ListNeighborPage
		then:
		neighborRows.size() == 1
		def row = neighborRow(0)
		row.neighborIp == "173.199.62.4"
		row.neighborDescription == "edge1.las-lab"
	}
	
	def "show Neighbor"() {
		when:
		neighborRow(0).showLink.click()
		then:
		at ShowNeighborPage
	}
	
	def "delete Neighbor"() {
		given:
		def deletedId = 1
		when:
		withConfirm { deleteButton.click() }
		then:
		at ListNeighborPage
		//message == "Exaaction $deletedId deleted"
		neighborRows.size() == 0
	}
}