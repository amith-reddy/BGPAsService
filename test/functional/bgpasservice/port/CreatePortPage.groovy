package bgpasservice.port

import bgpasservice.ScaffoldPage;

class CreatePortPage extends ScaffoldPage {

	static at = {
		title ==~ /Create.+/
	}
	
	static content = {
		createButton(to: ShowPortPage) { create() }
	}

}