package bgpasservice.scheduler

import bgpasservice.ScaffoldPage;


class ShowSchedulerPage extends ScaffoldPage {

	static at = {
		$("div#show-scheduler h1").text() ==~ /Show Scheduler/
	}
	
	static content = {
		newSchedulerButton(to: CreateSchedulerPage) { $("a", text: "New Scheduler") }
		listSchedulerButton(to: ListSchedulerPage) { $("a", text: "Scheduler List") }
		editButton(to: EditSchedulerPage) { $("a", text: "Edit") }
		deleteButton(to: ListSchedulerPage) { $("input", value: "Delete") }
		row { $("li.fieldcontain span.property-label", text: it).parent() }
		value { row(it).find("span.property-value").text() }
		newFlows { value("New Flows") }
		scheduleOption { value("Schedule Option") }
		scheduleTime  { value("Schedule Time") }
		activationSchedule { value("Activation Schedule") }
		deactivationSchedule { value("Deactivation Schedule") }
		deleteScheduleOption { value("Delete Option") }
		deleteScheduleTime { value("Delete Time") }
		name { value("Name") }
		email { value("Email") }
		
	}
}
