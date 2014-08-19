package bgpasservice.neighbor

import bgpasservice.ScaffoldPage;


class ShowNeighborPage extends ScaffoldPage {

	static at = {
		$("div#show-neighbor h1").text() ==~ /Show Neighbor/
	}
	
	static content = {
		newNeighborButton(to: CreateNeighborPage) { $("a", text: "New Neighbor") }
		editButton(to: EditNeighborPage) { $("a", text: "Edit") }
		deleteButton(to: ListNeighborPage) { $("input", value: "Delete") }
		row { $("li.fieldcontain span.property-label", text: it).parent() }
		value { row(it).find("span.property-value").text() }
		neighborDescription { value("Neighbor Description") }
		neighborIp { value("Neighbor Ip") }
		routerId { value("Router Id") }
		localIp { value("Local Ip") }
		localAS	{ value("Local AS") }
		peerAS	{ value("Peer AS") }
		gracefulRestart { value("Graceful Restart") }
		md5 { value("MD5") }
	}
}
