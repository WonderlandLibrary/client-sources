package xyz.mathax.mathaxclient.systems.accounts;

public enum AccountType {
    Cracked("Cracked"),
    Microsoft("Microsoft"),
    The_Altening("The Altening");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
