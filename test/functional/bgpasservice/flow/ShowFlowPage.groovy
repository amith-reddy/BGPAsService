package bgpasservice.flow

import bgpasservice.ScaffoldPage;


class ShowFlowPage extends ScaffoldPage {

	static at = {
		$("div#show-flow h1").text() ==~ /Show Flow/
	}
	
	static content = {
		newFlowButton(to: CreateFlowPage) { $("a", text: "New Flow") }
		editButton(to: EditFlowPage) { $("a", text: "Edit") }
		deleteButton(to: ListFlowPage) { $("input", value: "Delete") }
		row { $("li.fieldcontain span.property-label", text: it).parent() }
		value { row(it).find("span.property-value").text() }
		neighbors { value("Neighbor ID") }
		sourceSubnet { value("Source Subnet") }
		destinationSubnet { value("Destination Subnet") }
		protocol { value("Protocol") }
		exaaction	{ value("Action") }
		sourceport	{ value("Source Port") }
		destinationport { value("Destination Port") }
		status { value("Status") }
	}
}
