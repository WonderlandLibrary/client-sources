package club.bluezenith.core.user;

public enum ClientRank { //todo fetch client rank from server
    USER("§b"),
    DEVELOPER("§с");

    private final String colorCode;

    ClientRank(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return this.colorCode;
    }
}
