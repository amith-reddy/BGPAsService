package bgpasservice.protocol
import bgpasservice.ScaffoldPage;

class ShowProtocolPage extends ScaffoldPage {

	static at = {
		$("div#show-protocol h1").text() ==~ /Show Protocol/
	}
	
	static content = {
		newProtocolButton(to: CreateProtocolPage) { $("a", text: "New Protocol") }
		editButton(to: EditProtocolPage) { $("a", text: "Edit") }
		deleteButton(to: ListProtocolPage) { $("input", value: "Delete") }
		row { $("li.fieldcontain span.property-label", text: it).parent() }
		value { row(it).find("span.property-value").text() }
		protocolRule { value("Protocol Rule") }
	}
}
