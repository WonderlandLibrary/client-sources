package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.awt.Color;
import java.awt.Font;

public class UnicodeFontRenderer extends FontRenderer
{
    private final UnicodeFont Ø;
    
    public UnicodeFontRenderer(final Font awtFont) {
        super(Minecraft.áŒŠà().ŠÄ, new ResourceLocation_1975012498("textures/font/ascii.png"), Minecraft.áŒŠà().¥à(), false);
        (this.Ø = new UnicodeFont(awtFont)).Â();
        this.Ø.µÕ().add(new ColorEffect(Color.WHITE));
        try {
            this.Ø.Ø­áŒŠá();
        }
        catch (SlickException exception) {
            throw new RuntimeException(exception);
        }
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        this.HorizonCode_Horizon_È = this.Ø.Â(alphabet) / 2;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final String string, int x, int y, final int color) {
        if (string == null) {
            return 0;
        }
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        final boolean blend = GL11.glIsEnabled(3042);
        final boolean lighting = GL11.glIsEnabled(2896);
        final boolean texture = GL11.glIsEnabled(3553);
        if (!blend) {
            GL11.glEnable(3042);
        }
        if (lighting) {
            GL11.glDisable(2896);
        }
        if (texture) {
            GL11.glDisable(3553);
        }
        x *= 2;
        y *= 2;
        this.Ø.HorizonCode_Horizon_È(x, y, string, new HORIZON-6-0-SKIDPROTECTION.Color(color));
        if (texture) {
            GL11.glEnable(3553);
        }
        if (lighting) {
            GL11.glEnable(2896);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
        return x;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final String string, final float x, final float y, final int color) {
        return this.HorizonCode_Horizon_È(string, (int)x, (int)y, color);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final char c) {
        return this.HorizonCode_Horizon_È(Character.toString(c));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final String string) {
        return this.Ø.Ý(string) / 2;
    }
    
    public int Â(final String string) {
        return this.Ø.Â(string) / 2;
    }
}
