package de.resourcepacks24.utils;

public class RPTag
{
    private String tagName;
    private boolean enabled;

    public RPTag(String name, boolean enabled)
    {
        this.tagName = name;
        this.enabled = enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    public String getTagName()
    {
        return this.tagName;
    }

    public boolean hasTag(String tagName, int status)
    {
        return tagName.equals(this.tagName) && status == 1;
    }
}
