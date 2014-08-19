package bgpasservice 

import bgpasservice.exaaction.CreateExaactionPage;
import bgpasservice.exaaction.EditExaactionPage;
import bgpasservice.exaaction.ListExaactionPage;
import bgpasservice.exaaction.ShowExaactionPage;
import bgpasservice.login.*;
import geb.spock.GebReportingSpec
import spock.lang.*

//import pages.*

@Stepwise
class ExaactionCRUDSpec extends GebReportingSpec {
	
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
	
	def "Empty Exaaction"() {
		when:
		to ListExaactionPage
		then:
		exaactionRows.size() == 0
	}
	
	def "Add a Exaaction"() {
		when:
		newExaactionButton.click()
		then:
		at CreateExaactionPage
	}
	
	def "Enter the exaaction details"() {
		when:
		actionName = "rate-limit"
		actionParameter = "150000"
		createButton.click()
		then:
		at ShowExaactionPage
	}
	
	def "Check the entered exaaction details"() {
		expect:
		assert actionName == "rate-limit"
        assert actionParameter == "150000"
	}

	def "Edit the exaaction details"() {
		when:
		editButton.click()
		then:
		at EditExaactionPage
		when:
		Thread.sleep(1000)
		actionName = "redirect"
        actionParameter = "10.10.10.20:8080"
        updateButton.click()
		then:
		at ShowExaactionPage
	}
	
	def "check the list exaaction"() {
		when:
		to ListExaactionPage
		then:
		exaactionRows.size() == 1
		def row = exaactionRow(0)
		row.actionName == "redirect"
        row.actionParameter == "10.10.10.20:8080"
	}
	
	def "show exaaction-discard"() {
		when:
		exaactionRow(0).showLink.click()
		then:
		at ShowExaactionPage
	}
	
	def "Edit the exaaction details - discard"() {
		when:
		editButton.click()
		then:
		at EditExaactionPage
		when:
		Thread.sleep(1000)
		actionName = "discard"
		Thread.sleep(1000)
		//actionParameter = "10.10.10.10:8080"
		updateButton.click()
		then:
		at ShowExaactionPage
	}
	
	def "check the list exaaction-discard"() {
		when:
		to ListExaactionPage
		then:
		exaactionRows.size() == 1
		def row = exaactionRow(0)
		row.actionName == "discard"
		row.actionParameter == "null"
	}
	
	def "show exaaction"() {
		when:
		exaactionRow(0).showLink.click()
		then:
		at ShowExaactionPage
	}
	
	def "delete exaaction"() {
		given:
		def deletedId = 1
		when:
		withConfirm { deleteButton.click() }
		then:
		at ListExaactionPage
		//message == "Exaaction $deletedId deleted"
		exaactionRows.size() == 0
	}
}