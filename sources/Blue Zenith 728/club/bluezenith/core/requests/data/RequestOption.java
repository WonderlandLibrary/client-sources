package club.bluezenith.core.requests.data;

public class RequestOption {
    public final OptionType type;
    public final String name;
    public final String value;

    public RequestOption(OptionType type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public static RequestOption postOf(String name, String value) {
        return new RequestOption(OptionType.POST_BODY_PARAM, name, value);
    }

    public static RequestOption queryOf(String name, String value) {
        return new RequestOption(OptionType.QUERY_PARAMETER, name, value);
    }

    public static RequestOption headerOf(String name, String value) {
        return new RequestOption(OptionType.QUERY_PARAMETER, name, value);
    }
}
