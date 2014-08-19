// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
import org.apache.log4j.*
import org.apache.log4j.DailyRollingFileAppender;

import grails.plugin.springsecurity.SecurityConfigType;
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
	
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}

grails {
	
	mail {
		host = "smtp.gmail.com"
		port = 465
		username = "exabgpnotifier@gmail.com"
		password = "exabgpnotifier1"
		props = ["mail.smtp.auth":"true",
				 "mail.smtp.socketFactory.port":"465",
				 "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
				 "mail.smtp.socketFactory.fallback":"false"]
	  }
}

grails.config.locations = ["classpath:conf/Quartz.properties"]
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
	
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
	test {
		grails.logging.jul.usebridge = true
	}
}

// log4j configuration
log4j.main = {
//	debug 'org.springframework.security'
	
	root {
		error 'bgpasservicelog', 'console'
		//debug 'filelog', 'console'
		additivity = false
	}
		
	appenders {
		appender new DailyRollingFileAppender(
			//file name:'file', file:'C:\\Users\\t_amithr\\workspace_2.4\\BGPAsService\\myapp.log', append: true
				name: 'bgpasservicelog',
				file: 'bgpasservice.log',
				datePattern: "'.'yyyy-MM",
				layout: pattern(conversionPattern:'%d [%t] %-5p %c{2} %x - %m%n')
			 )
		
		console name:'stdout'
	}
	
	
//	appenders {
//		appender new DailyRollingFileAppender(name: "customAppender",
//					 threshold: Level.toLevel("ERROR"),
//					 file: 'C:\\Users\\t_amithr\\workspace_2.4\\BGPAsService\\myapp.log',
//					 datePattern: "'.'yyyy-MM",   //Rollover at midnight each day.
//					 layout:pattern(conversionPattern: '%c{2} %m%n')
//				  )
//			 }
	
    error 'bgpasservicelog':
		   ['org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
		   'org.quartz',
           'net.sf.ehcache.hibernate',
		   'grails.app',
		   'common'] , additivity: false
	   
}




grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations = false

grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.interceptUrlMap = [
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/assets/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll'],
	//'/logout/**' :     ['IS_AUTHENTICATED_REMEMBERED'],
	'/logout/**' :     ['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/login/**' :     ['IS_AUTHENTICATED_ANONYMOUSLY'],
	'/**':        ['IS_AUTHENTICATED_ANONYMOUSLY']
	//'/**':        ['IS_AUTHENTICATED_REMEMBERED']
]

grails.plugin.springsecurity.providerNames = [ 'ldapAuthProvider','rememberMeAuthenticationProvider','anonymousAuthenticationProvider']

grails.plugin.springsecurity.ldap.context.managerDn = 'CN=Amith Ravuru,OU=New Hires,OU=Departments,DC=ad,DC=corp,DC=expertcity,DC=com'
grails.plugin.springsecurity.ldap.context.managerPassword = 'Testdemo1'
grails.plugin.springsecurity.ldap.context.server = 'ldap://sbapdc04.ad.corp.expertcity.com:389'
//grails.plugin.springsecurity.ldap.context.anonymousReadOnly = true
grails.plugin.springsecurity.ldap.authorities.groupSearchBase = 'OU=Departments,DC=ad,DC=corp,DC=expertcity,DC=com'
grails.plugin.springsecurity.ldap.authorities.retrieveGroupRoles = true
grails.plugin.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
grails.plugin.springsecurity.ldap.mapper.userDetailsClass = 'person'
grails.plugin.springsecurity.ldap.search.filter = '(sAMAccountName={0})' // for Active Directory you need this
grails.plugin.springsecurity.ldap.search.base = 'OU=Departments,DC=ad,DC=corp,DC=expertcity,DC=com'
grails.plugin.springsecurity.ldap.search.searchSubtree = true
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.logout.afterLogoutUrl = '/'


//Minimum and Maximum Values
grails.port.minvalue = 0
grails.port.maxvalue = 65535
grails.asnumber.minvalue = 0
grails.asnumber.maxvalue = 65535
grails.gracefulRestart.minvalue = 1
grails.gracefulRestart.maxvalue = 3600

//Default Values


//Application level settings

grails {
	protocollist = ["tcp","udp","icmp"]
}

grails {
	exaaction{
		name = 'List Flow Actions'
	}
}

grails {
	emailList = []
}

//ExaBGP Tool Path
grails.exabgp.path = '/home/nsg/exabgp-3.3.0/sbin/exabgp'
