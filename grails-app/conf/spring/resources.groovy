// Place your Spring DSL code here
import grails.plugin.springsecurity.ldap.core.SimpleAuthenticationSource;
import grails.plugin.springsecurity.SpringSecurityUtils;
import grails.plugin.springsecurity.SecurityFilterPosition;

beans = {
//	ldapUserDetailsMapper(common.MyUserDetailsContextMapper) {
//     
//    }

	def config = SpringSecurityUtils.securityConfig
	if (config.ldap.context.server) {
		SpringSecurityUtils.loadSecondaryConfig 'DefaultLdapSecurityConfig'
		config = SpringSecurityUtils.securityConfig
 
		initialDirContextFactory(org.springframework.security.ldap.DefaultSpringSecurityContextSource,
		   config.ldap.context.server){
			userDn = config.ldap.context.managerDn
			password = config.ldap.context.managerPassword
		}
 
		ldapUserSearch(org.springframework.security.ldap.search.FilterBasedLdapUserSearch,
		   config.ldap.search.base,
		   config.ldap.search.filter,
			initialDirContextFactory){
		}
			
		ldapAuthoritiesPopulator(org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator,
			initialDirContextFactory,
		   config.ldap.authorities.groupSearchBase){
			  groupRoleAttribute = config.ldap.authorities.groupRoleAttribute
			  groupSearchFilter = config.ldap.authorities.groupSearchFilter
			  searchSubtree = config.ldap.authorities.searchSubtree
			  rolePrefix = "ROLE_"
			  convertToUpperCase = config.ldap.mapper.convertToUpperCase
			  ignorePartialResultException = config.ldap.authorities.ignorePartialResultException
		}
		   
	   userDetailsService(org.springframework.security.ldap.userdetails.LdapUserDetailsService,
		   ldapUserSearch,
		   ldapAuthoritiesPopulator){
	   }
		   
	   ldapUserDetailsMapper(common.MyUserDetailsContextMapper) {
		   
	   }
	   
//	   ldapRememberMeUserDetailsService(org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices) {
//		   
//				 def conf = SpringSecurityUtils.securityConfig
//		   
//				 userDetailsService = ref('userDetailsService')
//				 key = conf.rememberMe.key
//				 cookieName = conf.rememberMe.cookieName
//				 alwaysRemember = conf.rememberMe.alwaysRemember
//				 tokenValiditySeconds = conf.rememberMe.tokenValiditySeconds
//				 parameter = conf.rememberMe.parameter
//			  }
//	ldapAuthenticator(org.springframework.security.ldap.authentication.BindAuthenticator, ref("contextSource")){
//		userDnPatterns = ['uid={0},OU=Departments,DC=ad,DC=corp,DC=expertcity,DC=com']
//		}
//		// LDAP Authentication Provider with our authenticator and authorities populator
//		ldapAuthProvider(org.springframework.security.ldap.authentication.LdapAuthenticationProvider,
//		ref("ldapAuthenticator"),
//		ref("authoritiesPopulator")
//		)
//		authoritiesPopulator(common.CustomAuthoritiesPopulator)
//	}
	}
	
}
