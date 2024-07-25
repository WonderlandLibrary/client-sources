package club.bluezenith.core.data.alt.info;

public enum AccountType {
    MICROSOFT("Microsoft"),
    MOJANG("Mojang"),
    OFFLINE("Offline");

    String typeName;

    AccountType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
