package wtf.diablo.client.core.api;

public enum BuildTypeEnum {
    RELEASE("Release"),
    BETA("Beta"),
    DEBUG("Debug");

    private final String name;

    BuildTypeEnum(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
