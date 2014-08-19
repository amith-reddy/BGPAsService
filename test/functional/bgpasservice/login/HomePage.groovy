package bgpasservice.login

import bgpasservice.ScaffoldPage;

class HomePage extends ScaffoldPage {
	static url = "BGPAsService/"
	static at = {
		title ==~ /Welcome to.+/
	}
	
	static content = {
		//listScheduler(to: ListSchedulerPage) { $("a", text: "Scheduler") }
//		updateButton(to: ShowProtocolPage) { $("input", value: "Update") }
//		deleteButton(to: ListProtocolPage) { $("input", value: "Delete") }
	}

}