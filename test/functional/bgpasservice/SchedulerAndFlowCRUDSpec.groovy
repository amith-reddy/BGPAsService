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
class SchedulerAndFlowCRUDSpec extends GebReportingSpec {
	
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
	
	def "Navigate to List Neighbor Page"() {
		when:
		def neighborLink = $("a", text: "Neighbor")
		neighborLink.click()
		then:
		at ListNeighborPage
	}
	
	def "Navigate to create Neighbor Page"() {
		when:
		newNeighborButton.click()
		then:
		at CreateNeighborPage
	}
	
	def "Enter Neighbor details"() {
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
	
	def "Navigate to List Protocol Page"() {
		when:
		def protocolLink = $("a", text: "Protocol")
		protocolLink.click()
		then:
		at ListProtocolPage
	}
	
	def "Navigate to Create Protocol Page"() {
		when:
		newProtocolButton.click()
		then:
		at CreateProtocolPage
	}
	
	def "Enter Protocol details"() {
		when:
		$('input',type:'checkbox', id: 'check0').value("tcp")
		createButton.click()
		then:
		at ShowProtocolPage
	}
	
	def "Navigate to List Port Page"() {
		when:
		def portLink = $("a", text: "Port")
		portLink.click()
		then:
		at ListPortPage
	}
	
	def "Navigate to Create Port Page"() {
		when:
		newPortButton.click()
		then:
		at CreatePortPage
	}
	
	def "Enter the Port details"() {
		when:
		portStart1 = "1234"
		createButton.click()
		then:
		at ShowPortPage
	}

	
	def "Navigate to List Exaaction Page"() {
		when:
		def exaactionLink = $("a", text: "Exaaction")
		exaactionLink.click()
		then:
		at ListExaactionPage
	}
	
	def "Navigate to Create Exaaction Page"() {
		when:
		newExaactionButton.click()
		then:
		at CreateExaactionPage
	}
	
	def "Enter Exaaction details"() {
		when:
		actionName = "discard"
		createButton.click()
		then:
		at ShowExaactionPage
	}

	
	def "Navigate to List Flow Page"() {
		when:
		def flowLink = $("a", text: "Flow")
		flowLink.click()
		then:
		at ListFlowPage
	}
	
	def "Navigate to Create Flow Page"() {
		when:
		newFlowButton.click()
		then:
		at CreateFlowPage
	}
	
	def "Enter Flow details"() {
		when:
			$(id: 'neighbors').value("edge1.sjc-lab")
			sourceSubnet = "10.10.10.10/24"
			destinationSubnet = "20.20.20.20/24"
			$(id: 'protocol').value("tcp")
			$(id: 'exaaction').value("discard")
			$(id: 'sourceport').value("1234")
			$(id: 'destinationport').value("any")
			status = "new"
			Thread.sleep(1000)
			createButton.click()
		then:
			at ShowFlowPage
	}
	
	def "Check the entered Flow details"() {
		expect:
		assert neighbors == "edge1.sjc-lab"
		assert sourceSubnet == "10.10.10.10/24"
		assert destinationSubnet == "20.20.20.20/24"
		assert protocol == "tcp"
		assert exaaction == "discard"
		assert sourceport == "1234"
		assert destinationport == "any"
		assert status == "new"
	}

	def "Edit the Flow details"() {
		when:
		editButton.click()
		then:
		at EditFlowPage
		when:
		Thread.sleep(1000)
		sourceSubnet = "30.30.30.30/24"
		destinationSubnet = "40.40.40.40/24"
        updateButton.click()
		then:
		at ShowFlowPage
	}
	
	def "Check the list Flow"() {
		when:
		to ListFlowPage
		then:
		flowRows.size() == 1
		def row = flowRow(0)
		row.sourceSubnet == "30.30.30.30/24"
		row.destinationSubnet == "40.40.40.40/24"
	}
	
		
	def "Navigate to List Scheduler Page"() {
		when:
		def schedulerLink = $("a", text: "Scheduler")
		schedulerLink.click()
		then:
		at ListSchedulerPage
	}
	
	def "Navigate to Create Scheduler Page"() {
		when:
		newSchedulerButton.click()
		then:
		at CreateSchedulerPage
	}
	
	def "Enter Scheduler details"() {
		when:
			$(id: 'newFlows').value("Flow:30.30.30.30/24:40.40.40.40/24")
			scheduleOption = "Execute Now"
			deleteScheduleOption = "Dont Delete"
			name = "Amith"
			email = "amith.ravuru@gmail.com"
			Thread.sleep(1000)
			createButton.click()
		then:
			at ShowSchedulerPage
	}
	
	def "Check the entered Scheduler details"() {
		expect:
		assert newFlows == "Flow:30.30.30.30/24:40.40.40.40/24"
		assert scheduleOption == "Execute Now"
		assert deleteScheduleOption == "Dont Delete"
		assert name == "Amith"
		assert email == "amith.ravuru@gmail.com"
	}
	
	def "Edit the Scheduler details"() {
		when:
		editButton.click()
		then:
		at EditSchedulerPage
		
		when:
		Thread.sleep(1000)
		deleteScheduleOption = "Delete Now"
		updateButton.click()
		then:
		at ShowSchedulerPage
	}
	
	def "Check the Scheduler List"() {
		when:
			Thread.sleep(2000)
			listSchedulerButton.click()
		then:
			at ListSchedulerPage
			Thread.sleep(2000)
	}
	
	def "Delete Flow Page"() {
		when:
		def flowLink = $("a", text: "Flow")
		flowLink.click()
		then:
		at ListFlowPage
		
		when:
		flowRow(0).showLink.click()
		then:
		at ShowFlowPage

		when:
		withConfirm { deleteButton.click() }
		then:
		at ListFlowPage
		//message == "Exaaction $deletedId deleted"
		flowRows.size() == 0
	}
	
	def "Delete Port Page"() {
		when:
		def portLink = $("a", text: "Port")
		portLink.click()
		then:
		at ListPortPage
		
		when:
		portRow(1).showLink.click()
		then:
		at ShowPortPage

		when:
		withConfirm { deleteButton.click() }
		then:
		at ListPortPage
		//message == "Exaaction $deletedId deleted"
		portRows.size() == 1
	}
	
	def "Delete Protocol Page"() {
		when:
		def protocolLink = $("a", text: "Protocol")
		protocolLink.click()
		then:
		at ListProtocolPage
		
		when:
		protocolRow(0).showLink.click()
		then:
		at ShowProtocolPage

		when:
		withConfirm { deleteButton.click() }
		then:
		at ListProtocolPage
		//message == "Exaaction $deletedId deleted"
		protocolRows.size() == 0
	}
	
	def "Delete Exaaction Page"() {
		when:
		def exaactionLink = $("a", text: "Exaaction")
		exaactionLink.click()
		then:
		at ListExaactionPage
		
		when:
		exaactionRow(0).showLink.click()
		then:
		at ShowExaactionPage

		when:
		withConfirm { deleteButton.click() }
		then:
		at ListExaactionPage
		//message == "Exaaction $deletedId deleted"
		exaactionRows.size() == 0
	}
	
	def "Delete Neighbor Page"() {
		when:
		def neighborLink = $("a", text: "Neighbor")
		neighborLink.click()
		then:
		at ListNeighborPage
		
		when:
		neighborRow(0).showLink.click()
		then:
		at ShowNeighborPage

		when:
		withConfirm { deleteButton.click() }
		then:
		at ListNeighborPage
		//message == "Exaaction $deletedId deleted"
		neighborRows.size() == 0
	}
	

}