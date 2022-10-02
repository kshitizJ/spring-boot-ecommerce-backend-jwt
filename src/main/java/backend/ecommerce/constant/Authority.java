package backend.ecommerce.constant;

/**
 * 
 * Creating array of authorities which a User will have with the following Role.
 * 
 */
public class Authority {
        public static final String[] USER_AUTHORITIES = { "user:read", "user:update", "product:read" };
        public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete",
                        "product:read", "product:update", "product:delete" };
        public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update",
                        "user:delete", "admin:read", "admin:create", "admin:update", "admin:delete", "product:read",
                        "product:create", "product:update", "product:delete" };
}
