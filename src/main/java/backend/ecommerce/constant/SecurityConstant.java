package backend.ecommerce.constant;

/**
 * SecurityConstant
 */
public class SecurityConstant {
    public static final Long EXPIRATION_TIME = 432_000_000L; // 5 Days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "access_token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified!";
    public static final String KHZ_LLC = "Kshitiz Jain, LLC";
    public static final String KHZ_ADMINISTRATION = "E-commerce Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page.";
    public static final String ACCESS_DENIED = "You do not have permission to access this page.";
    public static final String OPTIONS_HTTP_METHOD = "OPTION";
    public static final String[] PUBLIC_URLS = { "/api/v1/user/login", "/api/v1/user/register",
            "/api/v1/user/resetPassword/**", "/api/v1/user/image/**", "/api/v1/products**", "/v2/api-docs",
            "/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };
}