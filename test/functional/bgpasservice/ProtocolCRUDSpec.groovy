package bgpasservice 

import bgpasservice.protocol.CreateProtocolPage;
import bgpasservice.protocol.EditProtocolPage;
import bgpasservice.protocol.ListProtocolPage;
import bgpasservice.protocol.ShowProtocolPage;
import bgpasservice.login.*;
import geb.spock.GebReportingSpec
import spock.lang.*

//import pages.*

@Stepwise
class ProtocolCRUDSpec extends GebReportingSpec {
	
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
	
	def "Empty Protocol"() {
		when:
		to ListProtocolPage
		then:
		protocolRows.size() == 0
	}
	
	def "Add a Protocol"() {
		when:
		newProtocolButton.click()
		then:
		at CreateProtocolPage
	}
	
	def "Enter the Protocol details"() {
		when:
		$('input',type:'checkbox', id: 'check0').value("tcp")
		createButton.click()
		then:
		at ShowProtocolPage
	}
	
	def "Check the entered Protocol details"() {
		expect:
		assert protocolRule == "tcp"
	}

	def "Edit the Protocol details"() {
		when:
		editButton.click()
		then:
		at EditProtocolPage
		when:
		Thread.sleep(1000)
		//$('input',type:'checkbox', id: 'check0').value("tcp")
		$('input',type:'checkbox', id: 'check1').value("udp")
		$('input',type:'checkbox', id: 'check2').value("icmp")
        updateButton.click()
		then:
		at ShowProtocolPage
	}
	
	def "check the list Protocol"() {
		when:
		to ListProtocolPage
		then:
		protocolRows.size() == 1
		def row = protocolRow(0)
		row.protocolRule == "tcp udp icmp"
	}
	
	def "show Protocol"() {
		when:
		protocolRow(0).showLink.click()
		then:
		at ShowProtocolPage
	}
	
	def "delete Protocol"() {
		given:
		def deletedId = 1
		when:
		withConfirm { deleteButton.click() }
		then:
		at ListProtocolPage
		//message == "Exaaction $deletedId deleted"
		protocolRows.size() == 0
	}
}