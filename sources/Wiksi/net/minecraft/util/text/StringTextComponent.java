package net.minecraft.util.text;

public class StringTextComponent extends TextComponent
{
    public static final ITextComponent EMPTY = new StringTextComponent("");
    private final String text;

    public StringTextComponent(String msg)
    {
        this.text = msg;
    }

    /**

     */
    public String getText()
    {
        return this.text;
    }

    /**
     */
    public String getUnformattedComponentText()
    {
        return this.text;
    }

    public StringTextComponent copyRaw()
    {
        return new StringTextComponent(this.text);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof StringTextComponent))
        {
            return false;
        }
        else
        {
            StringTextComponent stringtextcomponent = (StringTextComponent)p_equals_1_;
            return this.text.equals(stringtextcomponent.getText()) && super.equals(p_equals_1_);
        }
    }

    public String toString()
    {
        return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
    }
}
