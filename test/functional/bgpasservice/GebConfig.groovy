import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.Dimension

driver = {
	//Required because the scaffolding plugin create a responsive table which hides columns if the screen size is too small
	ChromeDriver driver = new ChromeDriver() 
	driver.manage().window().setSize(new Dimension(1920, 1200))
	return driver
}


environments {

	// run as “grails -Dgeb.env=chrome test-app”
	// See: http://code.google.com/p/selenium/wiki/ChromeDriver
	chrome {
		System.setProperty("webdriver.chrome.driver", "F:\\CitrixIntern\\chromedriver_win32\\chromedriver.exe");

		driver = { new ChromeDriver() }

	}

}