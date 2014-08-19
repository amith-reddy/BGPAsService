package bgpasservice.protocol

import bgpasservice.ScaffoldPage;

class EditProtocolPage extends ScaffoldPage {

	static at = {
		$("div#edit-protocol h1").text() ==~ /Edit.+/
	}
	
	static content = {
		updateButton(to: ShowProtocolPage) { $("input", value: "Update") }
		deleteButton(to: ListProtocolPage) { $("input", value: "Delete") }
	}

}