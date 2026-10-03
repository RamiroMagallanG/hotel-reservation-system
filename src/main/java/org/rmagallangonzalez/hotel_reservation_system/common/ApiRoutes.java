package org.rmagallangonzalez.hotel_reservation_system.common;

public final class ApiRoutes {

    public static final String API_VERSION = "v0.1";
    public static final String BASE_URL = "/api/" + API_VERSION;

    public static final class Auth {
        public static final String BASE = BASE_URL + "/auth";
    }
    
    public static final class User {
        public static final String BASE = BASE_URL + "/users";

        public static final String LOGIN = "/login";
        public static final String LOGIN_URL = BASE + LOGIN;

        public static final String LOGOUT = "/logout";
        public static final String LOGOUT_URL = BASE + LOGOUT;
        
        public static final String REGISTER = "/register";
        public static final String REGISTER_URL = BASE + REGISTER;
    }

    public static final class Admin {
        public static final String BASE = BASE_URL + "/admins";
    }

    public static final class employee {
        public static final String BASE = BASE_URL + "/employees";
    }
}
