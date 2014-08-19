package bgpasservice.neighbor

import bgpasservice.ScaffoldPage;


class CreateNeighborPage extends ScaffoldPage {

	static at = {
		title ==~ /Create.+/
	}
	
	static content = {
		createButton(to: ShowNeighborPage) { create() }
	}

}