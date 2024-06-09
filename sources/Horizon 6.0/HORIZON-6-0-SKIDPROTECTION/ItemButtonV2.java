package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;

public class ItemButtonV2 extends Item
{
    private Panel HorizonCode_Horizon_È;
    
    public ItemButtonV2(final Panel panel) {
        this.HorizonCode_Horizon_È = panel;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB();
        if (Horizon.Âµá€.equalsIgnoreCase("red")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.à() ? new Color(0.90588236f, 0.29803923f, 0.23529412f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.à() ? new Color(0.08627451f, 0.627451f, 0.52156866f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.à() ? new Color(0.15294118f, 0.68235296f, 0.3764706f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("magenta")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.à() ? new Color(0.8f, 0.2f, 1.0f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("orange")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.à() ? new Color(1.0f, 0.67058825f, 0.0627451f, 0.35f + MathHelper.Âµá€(MathHelper.HorizonCode_Horizon_È(Minecraft.áƒ() % 5000L / 5000.0f * 3.1415927f * 1.0f) * 0.4f)).getRGB() : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
            OGLManager.HorizonCode_Horizon_È(this.Â(), (float)this.Ý(), this.Â() + this.Ø­áŒŠá(), this.Ý() + this.Âµá€(), -0.25f, this.à() ? color : new Color(1.0f, 1.0f, 1.0f, 0.1f).getRGB(), -1);
        }
        if (Horizon.à.equalsIgnoreCase("Arial")) {
            final int textWidth = UIFonts.Ý.HorizonCode_Horizon_È(this.Ó());
            UIFonts.Ý.HorizonCode_Horizon_È(this.Ó(), this.Â() + this.Ø­áŒŠá / 2 - UIFonts.Ý.HorizonCode_Horizon_È(this.Ó()) / 2 - 1, this.Ý() + 3, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("SegoeUI")) {
            final int textWidth = UIFonts.µà.HorizonCode_Horizon_È(this.Ó());
            UIFonts.µà.HorizonCode_Horizon_È(this.Ó(), this.Â(), this.Ý() - 0, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Helvetica")) {
            final int textWidth = UIFonts.Ø­à.HorizonCode_Horizon_È(this.Ó());
            UIFonts.Ø­à.HorizonCode_Horizon_È(this.Ó(), this.Â() + this.Ø­áŒŠá / 2 - UIFonts.Ø­à.HorizonCode_Horizon_È(this.Ó()) / 2 - 1, this.Ý() + 5, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Comfortaa")) {
            final int textWidth = UIFonts.µÕ.HorizonCode_Horizon_È(this.Ó());
            UIFonts.µÕ.HorizonCode_Horizon_È(this.Ó(), this.Â() + this.Ø­áŒŠá() / 2 - UIFonts.µÕ.HorizonCode_Horizon_È(this.Ó()) / 2, this.Ý() + 4, -1);
        }
        else if (Horizon.à.equalsIgnoreCase("Vibes")) {
            final int textWidth = UIFonts.Æ.HorizonCode_Horizon_È(this.Ó());
            UIFonts.Æ.HorizonCode_Horizon_È(this.Ó(), this.Â(), this.Ý() + 3, -1);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final int k) {
        if (k == 0 && this.Â(i, j)) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(!this.HorizonCode_Horizon_È.Â());
            if (this.à()) {
                Minecraft.áŒŠà().á.HorizonCode_Horizon_È("random.pop", 10.0f, 1.0f);
            }
            else {
                Minecraft.áŒŠà().á.HorizonCode_Horizon_È("random.pop", 10.0f, 0.8f);
            }
        }
    }
    
    @Override
    public int Âµá€() {
        return 19;
    }
    
    public Panel HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Ó() {
        return this.HorizonCode_Horizon_È().Â;
    }
    
    public void HorizonCode_Horizon_È(final Panel panel) {
        this.HorizonCode_Horizon_È = panel;
    }
    
    public boolean à() {
        return this.HorizonCode_Horizon_È.Â();
    }
    
    public boolean Â(final int i, final int j) {
        return i >= this.Â() && i <= this.Â() + this.Ø­áŒŠá() && j >= this.Ý() && j <= this.Ý() + this.Âµá€();
    }
}
