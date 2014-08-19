package bgpasservice.exaaction

import bgpasservice.ScaffoldPage;


class EditExaactionPage extends ScaffoldPage {

	static at = {
		$("div#edit-exaaction h1").text() ==~ /Edit.+/
	}
	
	static content = {
		updateButton(to: ShowExaactionPage) { $("input", value: "Update") }
		deleteButton(to: ListExaactionPage) { $("input", value: "Delete") }
	}

}