package bgpasservice.neighbor


import bgpasservice.ScaffoldPage;
import geb.Module

class ListNeighborPage extends ScaffoldPage {
	static url = "neighbor/index"
	
	static at = {
		title ==~ /Neighbor List/
	}
	
	static content = {
		newNeighborButton(to: CreateNeighborPage) { $("a", text: "New Neighbor") }
		neighborTable { $("div.content table", 0) }
		neighborRow { module NeighborRow, neighborRows[it] }
		neighborRows(required: false) { neighborTable.find("tbody").find("tr") }
	}
}

class NeighborRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		neighborDescription { cellText(0) }
		neighborIp { cellText(1) }
		routerId { cellText(2) }
		localIp { cellText(3) }
		localAS	{ cellText(4) }
		peerAS	{ cellText(5) }
		gracefulRestart { cellText(6) }
		md5 { cellText(7) }
		showLink(to: ShowNeighborPage) { cell(0).find("a") }
	}
}