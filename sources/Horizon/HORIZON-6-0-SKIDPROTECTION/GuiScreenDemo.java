package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiScreenDemo extends GuiScreen
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    private static final String Ý = "CL_00000691";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ResourceLocation_1975012498("textures/gui/demo_background.png");
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        final byte var1 = -16;
        this.ÇŽÉ.add(new GuiButton(1, GuiScreenDemo.Çªà¢ / 2 - 116, GuiScreenDemo.Ê / 2 + 62 + var1, 114, 20, I18n.HorizonCode_Horizon_È("demo.help.buy", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(2, GuiScreenDemo.Çªà¢ / 2 + 2, GuiScreenDemo.Ê / 2 + 62 + var1, 114, 20, I18n.HorizonCode_Horizon_È("demo.help.later", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 1: {
                button.µà = false;
                try {
                    final Class var2 = Class.forName("java.awt.Desktop");
                    final Object var3 = var2.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
                    var2.getMethod("browse", URI.class).invoke(var3, new URI("http://www.minecraft.net/store?source=demo"));
                }
                catch (Throwable var4) {
                    GuiScreenDemo.HorizonCode_Horizon_È.error("Couldn't open link", var4);
                }
                break;
            }
            case 2: {
                GuiScreenDemo.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                GuiScreenDemo.Ñ¢á.Å();
                break;
            }
        }
    }
    
    @Override
    public void Ý() {
        super.Ý();
    }
    
    @Override
    public void £á() {
        super.£á();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenDemo.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiScreenDemo.Â);
        final int var1 = (GuiScreenDemo.Çªà¢ - 248) / 2;
        final int var2 = (GuiScreenDemo.Ê - 166) / 2;
        this.Â(var1, var2, 0, 0, 248, 166);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        final int var4 = (GuiScreenDemo.Çªà¢ - 248) / 2 + 10;
        int var5 = (GuiScreenDemo.Ê - 166) / 2 + 8;
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("demo.help.title", new Object[0]), var4, var5, 2039583);
        var5 += 12;
        final GameSettings var6 = GuiScreenDemo.Ñ¢á.ŠÄ;
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("demo.help.movementShort", GameSettings.HorizonCode_Horizon_È(var6.ÇªÉ.áŒŠÆ()), GameSettings.HorizonCode_Horizon_È(var6.ŠÏ­áˆºá.áŒŠÆ()), GameSettings.HorizonCode_Horizon_È(var6.ÇŽà.áŒŠÆ()), GameSettings.HorizonCode_Horizon_È(var6.ŠáˆºÂ.áŒŠÆ())), var4, var5, 5197647);
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("demo.help.movementMouse", new Object[0]), var4, var5 + 12, 5197647);
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("demo.help.jump", GameSettings.HorizonCode_Horizon_È(var6.Ø­Ñ¢Ï­Ø­áˆº.áŒŠÆ())), var4, var5 + 24, 5197647);
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("demo.help.inventory", GameSettings.HorizonCode_Horizon_È(var6.Ï­Ï.áŒŠÆ())), var4, var5 + 36, 5197647);
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("demo.help.fullWrapped", new Object[0]), var4, var5 + 68, 218, 2039583);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
