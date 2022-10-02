package backend.ecommerce.enumeration;

import static backend.ecommerce.constant.Authority.ADMIN_AUTHORITIES;
import static backend.ecommerce.constant.Authority.SUPER_ADMIN_AUTHORITIES;
import static backend.ecommerce.constant.Authority.USER_AUTHORITIES;

public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }

}
