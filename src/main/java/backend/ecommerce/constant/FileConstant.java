package backend.ecommerce.constant;

public class FileConstant {
        public static final String USER_IMAGE_PATH = "/api/v1/user/image";
        public static final String PRODUCT_IMAGE_PATH = "/api/v1/product/image";
        public static final String JPG_EXTENSION = "jpg";

        public static final String USER_FOLDER = System.getProperty("user.home")
                        + "/Desktop/Springboot Ecommerce Rest Api/backend/src/main/resources/static/images/user/";
        public static final String PRODUCT_FOLDER = System.getProperty("user.home")
                        + "/Desktop/Springboot Ecommerce Rest Api/backend/src/main/resources/static/images/product/";

        public static final String DEFAULT_USER_IMAGE_PATH = "/api/v1/user/image/profile";

        public static final String DIRECTORY_CREATED = "Created directory for: ";
        public static final String FILE_SAVED_IN_FILE_SYSTEM = "Saved file in file system by name: ";
        public static final String DOT = ".";
        public static final String FORWARD_SLASH = "/";
        public static final String TEMP_PROFILE_IMAGE_BASE_URL = "https://robohash.org/";
}
