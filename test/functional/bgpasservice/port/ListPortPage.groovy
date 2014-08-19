package bgpasservice.port
import bgpasservice.ScaffoldPage;


import geb.Module

class ListPortPage extends ScaffoldPage {
	static url = "port/index"
	
	static at = {
		title ==~ /Port List/
	}
	
	static content = {
		newPortButton(to: CreatePortPage) { $("a", text: "New Port") }
		portTable { $("div.content table", 0) }
		portRow { module PortRow, portRows[it] }
		portRows(required: false) { portTable.find("tbody").find("tr") }
	}
}

class PortRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		portRule { cellText(0) }
		showLink(to: ShowPortPage) { cell(0).find("a") }
	}
}