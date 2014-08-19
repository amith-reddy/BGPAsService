package bgpasservice.exaaction


import bgpasservice.ScaffoldPage;
import geb.Module

class ListExaactionPage extends ScaffoldPage {
	static url = "exaaction/index"
	
	static at = {
		title ==~ /Exaaction List/
	}
	
	static content = {
		newExaactionButton(to: CreateExaactionPage) { $("a", text: "New Exaaction") }
		exaactionTable { $("div.content table", 0) }
		exaactionRow { module ExaactionRow, exaactionRows[it] }
		exaactionRows(required: false) { exaactionTable.find("tbody").find("tr") }
	}
}

class ExaactionRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
        cellHrefText{ cell(it).find('a').text() }
		actionName { cellText(0) }
		actionParameter { cellText(1) }
		showLink(to: ShowExaactionPage) { cell(0).find("a") }
	}
}