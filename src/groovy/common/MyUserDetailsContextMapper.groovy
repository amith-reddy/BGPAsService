package common

import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper

class MyUserDetailsContextMapper implements UserDetailsContextMapper {
	UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection authorities) {

	//Grab the specific Active Directory information you want
    String fullname = ctx.originalAttrs.attrs['cn'].values[0]
   
    def userDetails = new MyUserDetails(username, '', true, true, true, true,
            authorities, fullname)

    return userDetails
}

void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
    throw new IllegalStateException("Only retrieving data from LDAP is currently supported")
}

}