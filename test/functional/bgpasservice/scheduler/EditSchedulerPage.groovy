package bgpasservice.scheduler

import bgpasservice.ScaffoldPage;


class EditSchedulerPage extends ScaffoldPage {

	static at = {
		$("div#edit-scheduler h1").text() ==~ /Edit.+/
	}
	
	static content = {
		updateButton(to: ShowSchedulerPage) { $("input", value: "Update") }
		//deleteButton(to: ListSchedulerPage) { $("input", value: "Delete") }
	}

}