package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Keyboard;
import java.awt.Color;

public class ItemButton extends Item
{
    private Mod HorizonCode_Horizon_È;
    private Category à;
    private int Ø;
    private long áŒŠÆ;
    private long áˆºÑ¢Õ;
    
    public ItemButton(final Mod module, final Category category) {
        this.Ø = 5592405;
        this.áŒŠÆ = -1L;
        this.áˆºÑ¢Õ = 50L;
        this.HorizonCode_Horizon_È(module);
        this.HorizonCode_Horizon_È(category);
    }
    
    private Long Å() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB();
        if (Horizon.Âµá€.equalsIgnoreCase("red")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.áŒŠÆ() ? new Color(0.90588236f, 0.29803923f, 0.23529412f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.áŒŠÆ() ? new Color(0.08627451f, 0.627451f, 0.52156866f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.áŒŠÆ() ? new Color(0.15294118f, 0.68235296f, 0.3764706f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.áŒŠÆ() ? new Color(0.8f, 0.2f, 1.0f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.áŒŠÆ() ? new Color(1.0f, 0.67058825f, 0.0627451f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.áŒŠÆ() ? color : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        if (Horizon.à.equalsIgnoreCase("Arial")) {
            UIFonts.Ý.HorizonCode_Horizon_È(this.Ó(), this.Â() + this.Ø­áŒŠá() / 2 - UIFonts.Ý.HorizonCode_Horizon_È(this.Ó()) / 2, this.Ý() + 3, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("SegoeUI")) {
            final int textWidth = UIFonts.µà.HorizonCode_Horizon_È(this.Ó());
            UIFonts.µà.HorizonCode_Horizon_È(this.Ó(), this.Â(), this.Ý() - 0, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Helvetica")) {
            final int textWidth = UIFonts.Ø­à.HorizonCode_Horizon_È(this.Ó());
            UIFonts.Ø­à.HorizonCode_Horizon_È(this.Ó(), this.Â() + this.Ø­áŒŠá() / 2 - UIFonts.Ø­à.HorizonCode_Horizon_È(this.Ó()) / 2 - 0, this.Ý() + 5, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Comfortaa")) {
            final int textWidth = UIFonts.µÕ.HorizonCode_Horizon_È(this.Ó());
            UIFonts.µÕ.HorizonCode_Horizon_È(this.Ó(), this.Â() + this.Ø­áŒŠá() / 2 - UIFonts.µÕ.HorizonCode_Horizon_È(this.Ó()) / 2 + 0, this.Ý() + 4, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Vibes")) {
            final int textWidth = UIFonts.Æ.HorizonCode_Horizon_È(this.Ó());
            UIFonts.Æ.HorizonCode_Horizon_È(this.Ó(), this.Â() + this.Ø­áŒŠá(), this.Ý(), -1);
        }
        if (this.Â(i, j)) {
            final String s = "Key: " + Keyboard.getKeyName(this.HorizonCode_Horizon_È().Âµá€());
            if (this.HorizonCode_Horizon_È().áˆºÑ¢Õ()) {
                if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                    GuiUtils.HorizonCode_Horizon_È().Â(this.Â + 100, this.Ý - 1 + 3, this.Â + UIFonts.Ý.HorizonCode_Horizon_È(s) + 104, this.Ý + 14 + 3, -16777216, -2132325316);
                }
                else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                    GuiUtils.HorizonCode_Horizon_È().Â(this.Â + 100, this.Ý - 1 + 3, this.Â + UIFonts.Ý.HorizonCode_Horizon_È(s) + 104, this.Ý + 14 + 3, -16777216, -2146000763);
                }
                else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                    GuiUtils.HorizonCode_Horizon_È().Â(this.Â + 100, this.Ý - 1 + 3, this.Â + UIFonts.Ý.HorizonCode_Horizon_È(s) + 104, this.Ý + 14 + 3, -16777216, -2144883104);
                }
                else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
                    GuiUtils.HorizonCode_Horizon_È().Â(this.Â + 100, this.Ý - 1 + 3, this.Â + UIFonts.Ý.HorizonCode_Horizon_È(s) + 104, this.Ý + 14 + 3, -16777216, -2134100993);
                }
                else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
                    GuiUtils.HorizonCode_Horizon_È().Â(this.Â + 100, this.Ý - 1 + 3, this.Â + UIFonts.Ý.HorizonCode_Horizon_È(s) + 104, this.Ý + 14 + 3, -16777216, -2130728176);
                }
                else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                    color = ColorUtil.HorizonCode_Horizon_È(color, 0.5);
                    GuiUtils.HorizonCode_Horizon_È().Â(this.Â + 100, this.Ý - 1 + 3, this.Â + UIFonts.Ý.HorizonCode_Horizon_È(s) + 104, this.Ý + 14 + 3, -16777216, color);
                }
            }
            else {
                GuiUtils.HorizonCode_Horizon_È().Â(this.Â + 100, this.Ý - 1 + 3, this.Â + UIFonts.Ý.HorizonCode_Horizon_È(s) + 104, this.Ý + 14 + 5, -16777216, 808661811);
            }
            UIFonts.Ý.HorizonCode_Horizon_È(s, this.Â + 101, this.Ý + 3, -1);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final int k) {
        if (k == 1 && this.Â(i, j)) {
            Minecraft.áŒŠà().HorizonCode_Horizon_È(new GuiKeyboard(this.HorizonCode_Horizon_È()));
        }
        if (k == 0 && this.Â(i, j)) {
            this.HorizonCode_Horizon_È().ˆÏ­();
        }
    }
    
    @Override
    public int Âµá€() {
        return 19;
    }
    
    public Mod HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Ó() {
        return this.HorizonCode_Horizon_È().Ý();
    }
    
    public Category à() {
        return this.à;
    }
    
    public int Ø() {
        return this.Ø;
    }
    
    public boolean áŒŠÆ() {
        return this.HorizonCode_Horizon_È().áˆºÑ¢Õ();
    }
    
    public boolean Â(final int i, final int j) {
        return i >= this.Â() && i <= this.Â() + this.Ø­áŒŠá() && j >= this.Ý() && j <= this.Ý() + this.Âµá€();
    }
    
    public void HorizonCode_Horizon_È(final Mod module) {
        this.HorizonCode_Horizon_È = module;
    }
    
    public void HorizonCode_Horizon_È(final Category category) {
        this.à = category;
    }
    
    public void Ý(final int hoverColor) {
        this.Ø = hoverColor;
    }
}
