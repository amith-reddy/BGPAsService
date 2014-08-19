package bgpasservice.scheduler

import bgpasservice.ScaffoldPage;


class CreateSchedulerPage extends ScaffoldPage {

	static at = {
		title ==~ /Create.+/
	}
	
	static content = {
		createButton(to: ShowSchedulerPage) { create() }
	}

}