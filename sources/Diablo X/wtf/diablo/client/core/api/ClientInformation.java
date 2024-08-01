package wtf.diablo.client.core.api;

public final class ClientInformation
{
    private final String name;
    private final BuildTypeEnum version;
    private final String build;

    private String displayName;

    public ClientInformation(final String name, final String build, final BuildTypeEnum version)
    {
        this.name = name;
        this.version = version;
        this.build = build;
        this.displayName = name;
    }

    public String getName()
    {
        return name;
    }

    public BuildTypeEnum getVersion()
    {
        return version;
    }

    public String getBuild()
    {
        return build;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
}