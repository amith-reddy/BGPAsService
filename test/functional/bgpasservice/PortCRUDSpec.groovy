package bgpasservice 

import bgpasservice.port.CreatePortPage;
import bgpasservice.port.EditPortPage;
import bgpasservice.port.ListPortPage;
import bgpasservice.port.ShowPortPage;
import bgpasservice.login.*;
import geb.spock.GebReportingSpec
import spock.lang.*

//import pages.*

@Stepwise
class PortCRUDSpec extends GebReportingSpec {
	
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
	
	def "Empty Port"() {
		when:
		to ListPortPage
		then:
		portRows.size() == 1 //Default Entry "any" is present
	}
	
	def "Add a Port"() {
		when:
		newPortButton.click()
		then:
		at CreatePortPage
	}
	
	def "Enter the Port details"() {
		when:
		portStart1 = "5000"
		$('#btnAdd').click()
		Thread.sleep(1000)
		PortOption2 = "range"
		portStart2 = "8080"
		portEnd2 = "8088"
		Thread.sleep(1000)
		createButton.click()
		then:
		at ShowPortPage
	}
	
	def "Check the entered Port details"() {
		expect:
		assert portRule == "5000 OR Between 8080 and 8088"
	}

	def "Edit the Port details"() {
		when:
		editButton.click()
		then:
		at EditPortPage
		when:
		//$('input',type:'checkbox', id: 'check0').value("tcp")
		withConfirm { $('#btnDel').click() }
		Thread.sleep(1000)  //required
        updateButton.click()
		then:
		at ShowPortPage
	}
	
	def "Check the Port list"() {
		when:
		to ListPortPage
		then:
		portRows.size() == 2
		def row = portRow(1)
		row.portRule == "5000"
	}
	
	def "Show Port"() {
		when:
		portRow(1).showLink.click()
		then:
		at ShowPortPage
	}
	
	def "Delete Port"() {
		given:
		def deletedId = 1
		when:
		withConfirm { deleteButton.click() }
		then:
		at ListPortPage
		//message == "Exaaction $deletedId deleted"
		portRows.size() == 1
	}
}