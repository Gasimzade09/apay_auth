package az.ms.apay_auth.constants;

public class Endpoints {
    public static final String API = "/api";

                    /* Card operations */
    public static final String CARD_TRANSFER = "/card/transfer";
    public static final String GET_CARD = "/card/{cardId}";
    public static final String CARD_REG = "/card/reg/{label}";
    public static final String GET_CARDS = "/cards";


                    /* User operations */
    public static final String GET_USER = "/user/{userId}";
    public static final String USER_INFO = "/info";

                    /* Security endpoints */
    public static final String AUTH = "/auth";
    public static final String USER_REG = "/reg";
}
