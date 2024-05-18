package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;

public class UI_CookieClicker extends GuiScreen
{
    public TimeHelper HorizonCode_Horizon_È;
    public GuiMenuButton Â;
    public GuiMenuButton Ý;
    
    public UI_CookieClicker() {
        this.HorizonCode_Horizon_È = new TimeHelper();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Display.setResizable(false);
        this.ÇŽÉ.add(this.Â = new GuiMenuButtonNoSlide(1, 1, 220, 100, 20, "AutoClickers : " + Horizon.à¢.È));
        this.ÇŽÉ.add(this.Ý = new GuiMenuButtonNoSlide(2, 100, 220, 100, 20, "ClickMultiplier : x" + Horizon.à¢.áŠ));
        if (Minecraft.áŒŠà().á€()) {
            Minecraft.áŒŠà().ˆà();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.HorizonCode_Horizon_È.Â(1200L)) {
            for (int i = 0; i < Horizon.à¢.È; ++i) {
                final Horizon à¢ = Horizon.à¢;
                ++à¢.Ç;
                UI_CookieClicker.Ñ¢á.£ÂµÄ().HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("random.click"), 1.0f));
            }
            this.HorizonCode_Horizon_È.Ø­áŒŠá();
        }
        final ScaledResolution scaledRes = new ScaledResolution(UI_CookieClicker.Ñ¢á, UI_CookieClicker.Ñ¢á.Ó, UI_CookieClicker.Ñ¢á.à);
        UI_CookieClicker.Ñ¢á.¥à().HorizonCode_Horizon_È(new ResourceLocation_1975012498("textures/horizon/gui/bg.png"));
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        UIFonts.áŒŠÆ.HorizonCode_Horizon_È(String.valueOf(Horizon.à¢.Ç) + " Cookies", UI_CookieClicker.Çªà¢ / 2 - UIFonts.áŒŠÆ.HorizonCode_Horizon_È(String.valueOf(Horizon.à¢.Ç) + " Cookies") / 2, 40, -1);
        GL11.glPushMatrix();
        GL11.glScaled(6.0, 6.0, 6.0);
        Minecraft.áŒŠà().áˆºÏ().Â(new ItemStack(Item_1028566121.HorizonCode_Horizon_È("cookie")), UI_CookieClicker.Çªà¢ / 15, UI_CookieClicker.Ê / 20);
        GL11.glPopMatrix();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (Horizon.à¢.Ï.HorizonCode_Horizon_È != null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        if (mouseX >= UI_CookieClicker.Çªà¢ / 2 - 40 && mouseX <= UI_CookieClicker.Çªà¢ / 2 + 46 && mouseY >= UI_CookieClicker.Ê / 2 - 37 && mouseY <= UI_CookieClicker.Ê / 2 + 37) {
            if (Horizon.à¢.áŠ == 0) {
                final Horizon à¢ = Horizon.à¢;
                ++à¢.Ç;
            }
            else {
                final Horizon à¢2 = Horizon.à¢;
                à¢2.Ç += 1 * Horizon.à¢.áŠ;
            }
            UI_CookieClicker.Ñ¢á.£ÂµÄ().HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("random.click"), 1.0f));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 1: {
                if (Horizon.à¢.Ç >= 360) {
                    final Horizon à¢ = Horizon.à¢;
                    ++à¢.È;
                    final Horizon à¢2 = Horizon.à¢;
                    à¢2.Ç -= 360;
                    this.Â.HorizonCode_Horizon_È("AutoClickers : " + Horizon.à¢.È);
                    UI_CookieClicker.Ñ¢á.£ÂµÄ().HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("random.explode"), 0.3f));
                    break;
                }
                break;
            }
            case 2: {
                if (Horizon.à¢.Ç < 500) {
                    break;
                }
                if (Horizon.à¢.áŠ >= 50) {
                    return;
                }
                final Horizon à¢3 = Horizon.à¢;
                à¢3.áŠ += 2;
                final Horizon à¢4 = Horizon.à¢;
                à¢4.Ç -= 500;
                this.Ý.HorizonCode_Horizon_È("ClickMultiplier : x" + Horizon.à¢.áŠ);
                UI_CookieClicker.Ñ¢á.£ÂµÄ().HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("random.explode"), 0.3f));
                break;
            }
        }
    }
    
    @Override
    public void q_() {
        Display.setResizable(true);
        super.q_();
    }
}
