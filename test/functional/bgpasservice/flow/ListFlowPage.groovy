package bgpasservice.flow


import bgpasservice.ScaffoldPage;
import geb.Module

class ListFlowPage extends ScaffoldPage {
	static url = "flow/index"
	
	static at = {
		title ==~ /Flow List/
	}
	
	static content = {
		newFlowButton(to: CreateFlowPage) { $("a", text: "New Flow") }
		flowTable { $("div.content table", 0) }
		flowRow { module FlowRow, flowRows[it] }
		flowRows(required: false) { flowTable.find("tbody").find("tr") }
	}
}

class FlowRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		neighborsId { cellText(0) }
		sourceSubnet { cellText(1) }
		destinationSubnet { cellText(2) }
		protocol { cellText(3) }
		exaaction	{ cellText(4) }
		sourceport	{ cellText(5) }
		destinationport { cellText(6) }
		status { cellText(7) }
		showLink(to: ShowFlowPage) { cell(0).find("a") }
	}
}