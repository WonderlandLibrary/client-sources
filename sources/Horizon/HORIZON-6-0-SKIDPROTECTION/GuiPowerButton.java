package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class GuiPowerButton extends GuiButton
{
    public GuiPowerButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiPowerButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3042);
            mc.Ø­áŒŠá.HorizonCode_Horizon_È(new ResourceLocation_1975012498("textures/sappire/buttons/power.png"));
            this.Â(this.ÂµÈ / 2, this.á / 2, 100, 100, 70, 70);
            GL11.glDisable(3042);
            this.HorizonCode_Horizon_È(mc, mouseX, mouseY);
        }
    }
}
