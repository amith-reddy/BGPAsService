class JdbcPoolGrailsPlugin {
	def version = '7.0.47'
	def grailsVersion = '2.0 > *'
	def title = 'Tomcat JDBC Pool plugin'
	def description = 'Replaces default Grails Commons DBCP Pool with Tomcat JDBC Pool (https://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html)'

	def loadAfter = ['dataSource']

	def license = 'APACHE'
	def developers = [[name: 'Lari Hotari', email: 'lari.hotari@sagire.fi'],
	                  [name: 'Burt Beckwith', email: 'burt@burtbeckwith.com']]
	def issueManagement = [system: 'GitHub', url: 'https://github.com/burtbeckwith/grails-jdbc-pool/issues']
	def scm = [url: 'https://github.com/burtbeckwith/grails-jdbc-pool']

	def doWithSpring = {
		for (String beanName in springConfig.beanNames) {
			def beanconf = springConfig.getBeanConfig(beanName)
			def beandef = beanconf ? beanconf.beanDefinition : springConfig.getBeanDefinition(beanName)
			if (beandef?.beanClassName == 'org.apache.commons.dbcp.BasicDataSource') {
				beandef.beanClassName = 'org.apache.tomcat.jdbc.pool.DataSource'
			}
		}
	}
}
