package bgpasservice.neighbor

import bgpasservice.ScaffoldPage;


class EditNeighborPage extends ScaffoldPage {

	static at = {
		$("div#edit-neighbor h1").text() ==~ /Edit.+/
	}
	
	static content = {
		updateButton(to: ShowNeighborPage) { $("input", value: "Update") }
		deleteButton(to: ListNeighborPage) { $("input", value: "Delete") }
	}

}