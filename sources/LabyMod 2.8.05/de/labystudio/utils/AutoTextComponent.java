package de.labystudio.utils;

public class AutoTextComponent
{
    private String autoTextName;
    private boolean keyShift;
    private boolean keyCtrl;
    private boolean keyAlt;
    private int keyCode;

    public AutoTextComponent(String autoTextName, boolean keyShift, boolean keyCtrl, boolean keyAlt, int keyCode)
    {
        this.autoTextName = autoTextName;
        this.keyShift = keyShift;
        this.keyCtrl = keyCtrl;
        this.keyAlt = keyAlt;
        this.keyCode = keyCode;
    }

    public AutoTextComponent(AutoTextComponent component)
    {
        this(component.getAutoTextName(), component.isKeyShift(), component.isKeyCtrl(), component.isKeyAlt(), component.getKeyCode());
    }

    public String getAutoTextName()
    {
        return this.autoTextName;
    }

    public int getKeyCode()
    {
        return this.keyCode;
    }

    public boolean isKeyAlt()
    {
        return this.keyAlt;
    }

    public boolean isKeyCtrl()
    {
        return this.keyCtrl;
    }

    public boolean isKeyShift()
    {
        return this.keyShift;
    }

    public void setAutoTextName(String autoTextName)
    {
        this.autoTextName = autoTextName;
    }

    public void setKeyAlt(boolean keyAlt)
    {
        this.keyAlt = keyAlt;
    }

    public void setKeyCode(int keyCode)
    {
        this.keyCode = keyCode;
    }

    public void setKeyCtrl(boolean keyCtrl)
    {
        this.keyCtrl = keyCtrl;
    }

    public void setKeyShift(boolean keyShift)
    {
        this.keyShift = keyShift;
    }
}
