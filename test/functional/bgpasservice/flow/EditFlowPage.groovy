package bgpasservice.flow

import bgpasservice.ScaffoldPage;


class EditFlowPage extends ScaffoldPage {

	static at = {
		$("div#edit-flow h1").text() ==~ /Edit.+/
	}
	
	static content = {
		updateButton(to: ShowFlowPage) { $("input", value: "Update") }
		deleteButton(to: ListFlowPage) { $("input", value: "Delete") }
	}

}