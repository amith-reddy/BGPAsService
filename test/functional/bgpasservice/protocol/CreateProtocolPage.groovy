package bgpasservice.protocol

import bgpasservice.ScaffoldPage;

class CreateProtocolPage extends ScaffoldPage {

	static at = {
		title ==~ /Create.+/
	}
	
	static content = {
		createButton(to: ShowProtocolPage) { create() }
	}

}