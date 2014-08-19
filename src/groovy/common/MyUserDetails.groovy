package common

import org.springframework.security.core.userdetails.User

class MyUserDetails extends User {
    // extra instance variables final String fullname final String email final String title
    String fullname
//    String email
//    String title
//    String phone

    MyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked, Collection authorities,
                  String fullname) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)

        this.fullname = fullname
//        this.email = email
//        this.title = title
//        this.phone = phone
    }
}