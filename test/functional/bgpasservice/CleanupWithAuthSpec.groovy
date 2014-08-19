package bgpasservice 

import bgpasservice.protocol.*;
import bgpasservice.port.*;
import bgpasservice.neighbor.*;
import bgpasservice.exaaction.*;
import bgpasservice.flow.*;
import bgpasservice.scheduler.*;
import bgpasservice.login.*;

import geb.spock.GebReportingSpec
import spock.lang.*

//import pages.*

@Stepwise
class CleanupWithAuthSpec extends GebReportingSpec {
	private static final long sleep_time = 0;
	
	def "Login Page"() {
		when:
		go "http://localhost:8080/BGPAsService/"
		then:
		at LoginPage
		
		when:
		Thread.sleep(sleep_time)
		j_username = "aravuru"
		Thread.sleep(sleep_time)
		j_password = "Testdemo1"
		loginButton.click()
		then:
		at HomePage
	}
	
	def "Delete the Scheduler details"() {
		when:
		Thread.sleep(sleep_time)
		def listScheduler = $("a", text: "Scheduler")
		listScheduler.click()
		then:
		at ListSchedulerPage
		
		
		while ($("table tr td a").size() != 0)
		{
			when:
			Thread.sleep(sleep_time)
			schedulerRow(0).showLink.click()
			then:
			at ShowSchedulerPage
			
			when:
			Thread.sleep(sleep_time)
			editButton.click()
			then:
			at EditSchedulerPage
		
			when:
			Thread.sleep(sleep_time)
			deleteScheduleOption = "Delete Now"
			updateButton.click()
			then:
			at ShowSchedulerPage
		}
	}
	

	def "delete Neighbor"() {
		when:
		Thread.sleep(sleep_time)
		def neighborLink = $("a", text: "Neighbor")
		neighborLink.click()
		then:
		at ListNeighborPage
		
		
		while ($("table tr td a").size() != 0)
		{
		when:
		neighborRow(0).showLink.click()
		then:
		at ShowNeighborPage

		when:
		withConfirm { deleteButton.click() }
		then:
		at ListNeighborPage
		}

	}
	
	def "Navigate to List Protocol Page"() {
		
	}
	
	def "delete Protocol"() {
		when:
		Thread.sleep(sleep_time)
		def protocolLink = $("a", text: "Protocol")
		protocolLink.click()
		then:
		at ListProtocolPage
		
		while ($("table tr td a").size() != 0)
		{
			when:
			protocolRow(0).showLink.click()
			then:
			at ShowProtocolPage
	
			when:
			withConfirm { deleteButton.click() }
			then:
			at ListProtocolPage
		}

	}
	
	def "delete Port"() {
		when:
		Thread.sleep(sleep_time)
		def portLink = $("a", text: "Port")
		portLink.click()
		then:
		at ListPortPage
		
		while ($("table tr td a").size() != 0)
		{
			when:
			portRow(1).showLink.click()
			then:
			at ShowPortPage
	
			when:
			withConfirm { deleteButton.click() }
			then:
			at ListPortPage
		}

	}
		
	def "delete Exaaction"() {
		when:
		Thread.sleep(sleep_time)
		def exaactionLink = $("a", text: "Exaaction")
		exaactionLink.click()
		then:
		at ListExaactionPage
		
		while ($("table tr td a").size() != 0)
		{
			when:
			exaactionRow(0).showLink.click()
			then:
			at ShowExaactionPage
	
			when:
			withConfirm { deleteButton.click() }
			then:
			at ListExaactionPage
		}

	}
	
	def "Logout"() {
		when:
		Thread.sleep(sleep_time)
		def logoutLink = $("a", text: "sign off")
		logoutLink.click()
		then:
		at LoginPage
	}
	
}