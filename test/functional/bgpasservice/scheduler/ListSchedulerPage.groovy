package bgpasservice.scheduler


import bgpasservice.ScaffoldPage;
import geb.Module

class ListSchedulerPage extends ScaffoldPage {
	static url = "scheduler/index"
	
	static at = {
		title ==~ /Scheduler List/
	}
	
	static content = {
		newSchedulerButton(to: CreateSchedulerPage) { $("a", text: "New Scheduler") }
		schedulerTable { $("div.content table", 0) }
		schedulerRow { module SchedulerRow, schedulerRows[it] }
		schedulerRows(required: false) { schedulerTable.find("tbody").find("tr") }
	}
}

class SchedulerRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		newFlows { cellText(0) }
		scheduleOption { cellText(1) }
		scheduleTime  { cellText(2) }
		activationSchedule { cellText(3) }
		deactivationSchedule { cellText(4) }
		deleteScheduleOption { cellText(5) }
		deleteScheduleTime { cellText(6) }
		name { cellText(7) }
		email { cellText(8) }
		showLink(to: ShowSchedulerPage) { cell(0).find("a") }
	}
}