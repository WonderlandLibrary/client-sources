package wtf.diablo.auth;

public final class DiabloAPI {
    private DiabloAPI() {}

    public static final String API_URL = "https://diablo.wtf/api/v1";

    public static final String AUTH_ENDPOINT = API_URL + "/auth";
    public static final String USER_ENDPOINT = AUTH_ENDPOINT + "/user";
}
