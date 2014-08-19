package bgpasservice.exaaction

import bgpasservice.ScaffoldPage;


class ShowExaactionPage extends ScaffoldPage {

	static at = {
		$("div#show-exaaction h1").text() ==~ /Show Exaaction/
	}
	
	static content = {
		newExaactionButton(to: CreateExaactionPage) { $("a", text: "New Exaaction") }
		editButton(to: EditExaactionPage) { $("a", text: "Edit") }
		deleteButton(to: ListExaactionPage) { $("input", value: "Delete") }
		row { $("li.fieldcontain span.property-label", text: it).parent() }
		value { row(it).find("span.property-value").text() }
		actionName { value("Action Name") }
		actionParameter { value("Action Parameter") }
	}
}
