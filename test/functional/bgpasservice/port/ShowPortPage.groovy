package bgpasservice.port
import bgpasservice.ScaffoldPage;

class ShowPortPage extends ScaffoldPage {

	static at = {
		$("div#show-port h1").text() ==~ /Show Port/
	}
	
	static content = {
		newPortButton(to: CreatePortPage) { $("a", text: "New Port") }
		editButton(to: EditPortPage) { $("a", text: "Edit") }
		deleteButton(to: ListPortPage) { $("input", value: "Delete") }
		row { $("li.fieldcontain span.property-label", text: it).parent() }
		value { row(it).find("span.property-value").text() }
		portRule { value("Port Rule") }
	}
}
