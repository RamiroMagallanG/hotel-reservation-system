package org.rmagallangonzalez.hotel_reservation_system.common;

public final class ApiRoutes {
    public final static String API_VERSION = "v0.1";
    public final static String BASE_URL = "/api/" + API_VERSION;

    // ##### Base URLs #####
    public final static String AUTH_URL = BASE_URL + "/auth";
    public final static String USER_URL = BASE_URL + "/users";
    public final static String ADMIN_URL = BASE_URL + "/admins";
    public final static String EMPLOYEE_URL = BASE_URL + "/employees";

    // ##### USER #####
    public final static String LOGIN = "/login";
    public final static String LOGIN_URL = USER_URL + LOGIN;

    public final static String LOGOUT = "/logout";
    public final static String LOGOUT_URL = USER_URL + LOGOUT;
    
    public final static String REGISTER = "/register";
    public final static String REGISTER_URL = USER_URL + REGISTER;
}
