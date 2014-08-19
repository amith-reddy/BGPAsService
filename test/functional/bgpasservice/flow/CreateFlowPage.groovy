package bgpasservice.flow

import bgpasservice.ScaffoldPage;


class CreateFlowPage extends ScaffoldPage {

	static at = {
		title ==~ /Create.+/
	}
	
	static content = {
		createButton(to: ShowFlowPage) { create() }
	}

}