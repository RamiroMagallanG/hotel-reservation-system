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

    public static final class Hotel {
        public static final String BASE = BASE_URL + "/hotel";

        public static final String CREATE = "/create";
        public static final String CREATE_HOTEL = BASE + CREATE;

        public static final String BY_ID = "/{hotelId}";

        public static final String RESERVATIONS = "/reservations";

        public static final String GET_BY_ID_HOTEL = BASE + BY_ID;
        public static final String MODIFY_HOTEL = BASE + BY_ID;
        public static final String DELETE_HOTEL = BASE + BY_ID;
        public static final String GET_RESERVATIONS_BY_HOTEL_ID = GET_BY_ID_HOTEL + RESERVATIONS;

        public static final class Room {
            public static final String BASE = Hotel.GET_BY_ID_HOTEL + "/rooms";

            public static final String BY_ID  = "/{roomId}";
            public static final String GET_BY_ID_ROOM = BASE + BY_ID;
        }

    }

    public static final class Reservation {
        public static final String BASE = BASE_URL + "/reservation";

        public static final String BY_ID = "/{reservationId}";
        public static final String BY_ME = "/me";

        public static final String GET_BY_ID_RESERVATION = BASE + BY_ID;
        public static final String GET_BY_ME_RESERVATION = BASE + BY_ME;
        public static final String CANCEL_RESERVATION_BY_ID = BASE + BY_ID;

    }

    public static final class Admin {
        public static final String BASE = BASE_URL + "/admins";
    }

    public static final class employee {
        public static final String BASE = BASE_URL + "/employees";
    }
}
