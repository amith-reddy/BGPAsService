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
class ConfigWithoutAuthSpec extends GebReportingSpec {
	private static final long sleep_time = 1000;
	
	def "Login Page"() {
		when:
		go "http://192.168.10.25:8080/BGPAsService/"
//		then:
//		at LoginPage
//
//		when:
//		Thread.sleep(sleep_time)
//		j_username = "aravuru"
//		Thread.sleep(sleep_time)
//		j_password = "wrong"
//		loginButton.click()
//		then:
//		at LoginPage
//		
//		when:
//		Thread.sleep(sleep_time)
//		j_username = "aravuru"
//		Thread.sleep(sleep_time)
//		j_password = "Testdemo1"
//		loginButton.click()
		then:
		at HomePage
	}
	
	def "Navigate to Create Neighbor Page"() {
		when:
		Thread.sleep(sleep_time)
		def neighborLink = $("a", text: "Neighbor")
		neighborLink.click()
		then:
		at ListNeighborPage
	}
	
	def "Add Neighbor1"() {
		when:
		Thread.sleep(sleep_time)
		newNeighborButton.click()
		then:
		at CreateNeighborPage
	}
	
	def "Enter Neighbor1 details"() {
		when:
			Thread.sleep(sleep_time)
			neighborDescription = "edge1.sjc-lab"
			neighborIp = "173.199.62.2"
			routerId = "10.22.6.12"
			localIp  = "10.22.6.12"
			localAS	= 16815;
			peerAS	= 16815;
			gracefulRestart = 5;
			md5 = "flowspec"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
		at ShowNeighborPage
	}
	
	
	def "Add Neighbor2"() {
		when:
		Thread.sleep(sleep_time)
		newNeighborButton.click()
		then:
		at CreateNeighborPage
	}
	
	def "Enter Neighbor2 details"() {
		when:
			Thread.sleep(sleep_time)
			neighborDescription = "edge1.las-lab"
			neighborIp = "173.199.62.4"
			routerId = "10.22.6.12"
			localIp  = "10.22.6.12"
			localAS	= 16815;
			peerAS	= 16815;
			gracefulRestart = 5;
			md5 = "flowspec"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
			at ShowNeighborPage
	}

	
	def "Navigate to Create Protocol Page"() {
		when:
		Thread.sleep(sleep_time)
		def protocolLink = $("a", text: "Protocol")
		protocolLink.click()
		then:
		at ListProtocolPage
	}
	
	def "Add Protocol1"() {
		when:
		Thread.sleep(sleep_time)
		newProtocolButton.click()
		then:
		at CreateProtocolPage
	}
	
	def "Enter Protocol1 details"() {
		when:
		Thread.sleep(sleep_time)
		$('input',type:'checkbox', id: 'check0').value("tcp")
		createButton.click()
		then:
		at ShowProtocolPage
	}
	
	def "Add Protocol2"() {
		when:
		Thread.sleep(sleep_time)
		newProtocolButton.click()
		then:
		at CreateProtocolPage
	}
	
	def "Enter Protocol2 details"() {
		when:
			Thread.sleep(sleep_time)
			$('input',type:'checkbox', id: 'check0').value("tcp")
			$('input',type:'checkbox', id: 'check1').value("udp")
			$('input',type:'checkbox', id: 'check2').value("icmp")
			createButton.click()
		then:
		at ShowProtocolPage
	}

	
	def "Navigate to Create Port Page"() {
		when:
		Thread.sleep(sleep_time)
		def portLink = $("a", text: "Port")
		portLink.click()
		then:
		at ListPortPage
	}
	
	def "Add Port1"() {
		when:
		Thread.sleep(sleep_time)
		newPortButton.click()
		then:
		at CreatePortPage
	}
	
	def "Enter the Port1 details"() {
		when:
		Thread.sleep(sleep_time)
		portStart1 = "1234"
		createButton.click()
		then:
		at ShowPortPage
	}
	
	def "Add Port2"() {
		when:
		Thread.sleep(sleep_time)
		newPortButton.click()
		then:
		at CreatePortPage
	}
	
	def "Enter Port2 details"() {
		when:
		Thread.sleep(sleep_time)
		portStart1 = "5000"
		$('#btnAdd').click()
		Thread.sleep(sleep_time)
		PortOption2 = "range"
		portStart2 = "8080"
		portEnd2 = "8088"
		Thread.sleep(sleep_time)
		createButton.click()
		then:
		at ShowPortPage
	}
	
	def "Navigate to Create Exaaction Page"() {
		when:
		Thread.sleep(sleep_time)
		def exaactionLink = $("a", text: "Exaaction")
		exaactionLink.click()
		then:
		at ListExaactionPage
	}
	
	def "Add Exaaction1"() {
		when:
		Thread.sleep(sleep_time)
		newExaactionButton.click()
		then:
		at CreateExaactionPage
	}
	
	def "Enter Exaaction1 details"() {
		when:
		Thread.sleep(sleep_time)
		actionName = "discard"
		createButton.click()
		then:
		at ShowExaactionPage
	}
	
	def "Add Exaaction2"() {
		when:
		Thread.sleep(sleep_time)
		newExaactionButton.click()
		then:
		at CreateExaactionPage
	}
	
	def "Enter Exaaction2 details"() {
		when:
		Thread.sleep(sleep_time)
		actionName = "rate-limit"
		actionParameter = "50000"
		createButton.click()
		then:
		at ShowExaactionPage
	}
	
	def "Add Exaaction3"() {
		when:
		Thread.sleep(sleep_time)
		newExaactionButton.click()
		then:
		at CreateExaactionPage
	}
	
	def "Enter Exaaction3 details"() {
		when:
		Thread.sleep(sleep_time)
		actionName = "redirect"
        actionParameter = "10.10.10.10:8080"
		createButton.click()
		then:
		at ShowExaactionPage
	}
	
	def "Navigate to Create Flow Page"() {
		when:
		Thread.sleep(sleep_time)
		def flowLink = $("a", text: "Flow")
		flowLink.click()
		then:
		at ListFlowPage
	}
	
	def "Add Flow1"() {
		when:
		Thread.sleep(sleep_time)
		newFlowButton.click()
		then:
		at CreateFlowPage
	}
	
	def "Enter Flow1 details"() {
		when:
			Thread.sleep(sleep_time)
			$(id: 'neighbors').value("edge1.sjc-lab")
			sourceSubnet = "10.10.10.10/24"
			destinationSubnet = "20.20.20.20/24"
			$(id: 'protocol').value("tcp")
			$(id: 'exaaction').value("discard")
			$(id: 'sourceport').value("1234")
			$(id: 'destinationport').value("5000 OR Between 8080 and 8088")
			status = "new"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
			at ShowFlowPage
	}
	
	def "Add Flow2"() {
		when:
		Thread.sleep(sleep_time)
		newFlowButton.click()
		then:
		at CreateFlowPage
	}
	
	def "Enter Flow2 details"() {
		when:
			Thread.sleep(sleep_time)
			$(id: 'neighbors').value("edge1.sjc-lab")
			sourceSubnet = "30.30.30.30/24"
			destinationSubnet = "40.40.40.40/24"
			$(id: 'protocol').value("tcp udp icmp")
			$(id: 'exaaction').value("rate-limit:50000")
			$(id: 'sourceport').value("any")
			$(id: 'destinationport').value("any")
			status = "new"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
			at ShowFlowPage
	}
	
	def "Add Flow3"() {
		when:
		Thread.sleep(sleep_time)
		newFlowButton.click()
		then:
		at CreateFlowPage
	}
	
	def "Enter Flow3 details"() {
		when:
			Thread.sleep(sleep_time)
			$(id: 'neighbors').value("edge1.sjc-lab")
			sourceSubnet = "50.50.50.50/24"
			destinationSubnet = "60.60.60.60/24"
			$(id: 'protocol').value("tcp udp icmp")
			$(id: 'exaaction').value("redirect:10.10.10.10:8080")
			$(id: 'sourceport').value("any")
			$(id: 'destinationport').value("any")
			status = "new"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
			at ShowFlowPage
	}
	
//	def "Add Flow4"() {
//		when:
//		Thread.sleep(sleep_time)
//		newFlowButton.click()
//		then:
//		at CreateFlowPage
//	}
//	
//	def "Enter Flow4 details"() {
//		when:
//			Thread.sleep(sleep_time)
//			$(id: 'neighbors').value("edge1.las-lab")
//			sourceSubnet = "70.70.70.70/24"
//			destinationSubnet = "80.80.80.80/24"
//			$(id: 'protocol').value("tcp udp icmp")
//			$(id: 'exaaction').value("redirect:10.10.10.10:8080")
//			$(id: 'sourceport').value("any")
//			$(id: 'destinationport').value("any")
//			status = "new"
//			Thread.sleep(sleep_time)
//			createButton.click()
//		then:
//			at ShowFlowPage
//	}
	
	def "Navigate to Create Scheduler Page"() {
		when:
		Thread.sleep(sleep_time)
		def schedulerLink = $("a", text: "Scheduler")
		schedulerLink.click()
		then:
		at ListSchedulerPage
	}
	
	def "Add Scheduler1"() {
		when:
		Thread.sleep(sleep_time)
		newSchedulerButton.click()
		then:
		at CreateSchedulerPage
	}
	
	def "Enter Scheduler1 details"() {
		when:
			Thread.sleep(sleep_time)
			$(id: 'newFlows').value("Flow:10.10.10.10/24:20.20.20.20/24")
			scheduleOption = "Execute Now"
			deleteScheduleOption = "Dont Delete"
			name = "Amith"
			email = "amith.ravuru@citrix.com"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
			at ShowSchedulerPage
	}
	
	def "Add Scheduler2"() {
		when:
		Thread.sleep(sleep_time)
		newSchedulerButton.click()
		then:
		at CreateSchedulerPage
	}
	
	def "Enter Scheduler2 details"() {
		when:
			Thread.sleep(sleep_time)
			$(id: 'newFlows').value("Flow:30.30.30.30/24:40.40.40.40/24")
			scheduleOption = "Schedule Once"
				
			$(id: 'scheduletimedateid_year').value("2015")
			deleteScheduleOption = "Dont Delete"
			name = "Amith"
			email = "amith.ravuru@citrix.com"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
			at ShowSchedulerPage
	}
	
	def "Add Scheduler3"() {
		when:
		Thread.sleep(sleep_time)
		newSchedulerButton.click()
		then:
		at CreateSchedulerPage
	}
	
	def "Enter Scheduler3 details"() {
		when:
			Thread.sleep(sleep_time)
			$(id: 'newFlows').value("Flow:50.50.50.50/24:60.60.60.60/24")
			scheduleOption = "Schedule Periodically"
			Thread.sleep(sleep_time)
			$('#activationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-dow .jqCron-selector.jqCron-selector-1 .jqCron-selector-title').click()
			$('#activationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-dow .jqCron-selector.jqCron-selector-1 .jqCron-selector-list li').eq(3).click()
//			$('#activationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-1 .jqCron-selector-title').click()
//			$('#activationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-1 .jqCron-selector-list li').eq(0).click()
			$('#activationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-2 .jqCron-selector-title').click()
			for(int i=0;i<60;i=i+5)
			{
				$('#activationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-2 .jqCron-selector-list li').eq(i).click()
			}
			
			$('#deactivationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-dow .jqCron-selector.jqCron-selector-1 .jqCron-selector-title').click()
			$('#deactivationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-dow .jqCron-selector.jqCron-selector-1 .jqCron-selector-list li').eq(3).click()
//			$('#deactivationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-1 .jqCron-selector-title').click()
//			$('#deactivationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-1 .jqCron-selector-list li').eq(23).click()
			$('#deactivationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-2 .jqCron-selector-title').click()
			for(int i=3;i<60;i=i+5)
			{
				$('#deactivationCronDivId').find('.jqCron .jqCron-container .jqCron-blocks .jqCron-time .jqCron-selector.jqCron-selector-2 .jqCron-selector-list li').eq(i).click()
			}
			
			deleteScheduleOption = "Delete Later"
			$(id: 'deletescheduletimedateid_year').value("2015")
			name = "Amith"
			email = "amith.ravuru@citrix.com"
			Thread.sleep(sleep_time)
			createButton.click()
		then:
			at ShowSchedulerPage
	}
	
//	def "Add Scheduler4"() {
//		when:
//		Thread.sleep(sleep_time)
//		newSchedulerButton.click()
//		then:
//		at CreateSchedulerPage
//	}
//	
//	def "Enter Scheduler4 details"() {
//		when:
//			Thread.sleep(sleep_time)
//			$(id: 'newFlows').value("Flow:70.70.70.70/24:80.80.80.80/24")
//			scheduleOption = "Execute Now"
//			deleteScheduleOption = "Dont Delete"
//			name = "Amith"
//			email = "amith.ravuru@citrix.com"
//			Thread.sleep(sleep_time)
//			createButton.click()
//		then:
//			at ShowSchedulerPage
//	}
	
//	def "Navigate to the list Scheduler"() {
//		when:
//			Thread.sleep(sleep_time)
//			listSchedulerButton.click()
//		then:
//			at ListSchedulerPage
//	}
//	
//	def "show Scheduler"() {
//		when:
//		Thread.sleep(sleep_time)
//		schedulerRow(0).showLink.click()
//		then:
//		at ShowSchedulerPage
//	}
//	
//	def "Edit the Scheduler details"() {
//		when:
//		Thread.sleep(sleep_time)
//		editButton.click()
//		then:
//		at EditSchedulerPage
//		
//		when:
//		Thread.sleep(sleep_time)
//		deleteScheduleOption = "Delete Now"
//		updateButton.click()
//		then:
//		at ShowSchedulerPage
//	}
//	
//	def "check the list Scheduler"() {
//		when:
//			Thread.sleep(sleep_time)
//			listSchedulerButton.click()
//		then:
//			at ListSchedulerPage
//			Thread.sleep(sleep_time)
//	}
	
	def "Navigate to Home Page - Logout"() {
		when:
		Thread.sleep(sleep_time)
		def homeLink = $("a", text: "Home")
		homeLink.click()
		then:
		at HomePage
	}
	
//	def "Logout"() {
//		when:
//		Thread.sleep(sleep_time)
//		def logoutLink = $("a", text: "sign off")
//		logoutLink.click()
//		then:
//		at LoginPage
//	}
}