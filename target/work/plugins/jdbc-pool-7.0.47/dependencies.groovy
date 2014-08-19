grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {

		String tomcatVersion = '7.0.47'

		runtime "org.apache.tomcat:tomcat-jdbc:$tomcatVersion", {
			excludes 'tomcat-juli'
		}

		runtime "org.apache.tomcat:tomcat-juli:$tomcatVersion", {
			transitive = false
		}
	}

	plugins {
		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
