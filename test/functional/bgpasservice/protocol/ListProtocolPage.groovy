package bgpasservice.protocol
import bgpasservice.ScaffoldPage;


import geb.Module

class ListProtocolPage extends ScaffoldPage {
	static url = "protocol/index"
	
	static at = {
		title ==~ /Protocol List/
	}
	
	static content = {
		newProtocolButton(to: CreateProtocolPage) { $("a", text: "New Protocol") }
		protocolTable { $("div.content table", 0) }
		protocolRow { module ProtocolRow, protocolRows[it] }
		protocolRows(required: false) { protocolTable.find("tbody").find("tr") }
	}
}

class ProtocolRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		protocolRule { cellText(0) }
		showLink(to: ShowProtocolPage) { cell(0).find("a") }
	}
}