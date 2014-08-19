package bgpasservice.login

import bgpasservice.ScaffoldPage;

class LoginPage extends ScaffoldPage {

	static at = {
		title == "Login"
	}
	
	static content = {
		loginButton() { $("input", type: "submit", id: "submit") }
	}

}