package bgpasservice.port

import bgpasservice.ScaffoldPage;

class EditPortPage extends ScaffoldPage {

	static at = {
		$("div#edit-port h1").text() ==~ /Edit.+/
	}
	
	static content = {
		updateButton(to: ShowPortPage) { $("input", value: "Update") }
		deleteButton(to: ListPortPage) { $("input", value: "Delete") }
	}

}