package bgpasservice.exaaction

import bgpasservice.ScaffoldPage;


class CreateExaactionPage extends ScaffoldPage {

	static at = {
		title ==~ /Create.+/
	}
	
	static content = {
		createButton(to: ShowExaactionPage) { create() }
	}

}