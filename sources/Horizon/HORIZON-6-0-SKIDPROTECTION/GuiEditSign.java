package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiEditSign extends GuiScreen
{
    private TileEntitySign HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private GuiButton Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000764";
    
    public GuiEditSign(final TileEntitySign p_i1097_1_) {
        this.HorizonCode_Horizon_È = p_i1097_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.add(this.Ø­áŒŠá = new GuiButton(0, GuiEditSign.Çªà¢ / 2 - 100, GuiEditSign.Ê / 4 + 120, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(false);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
        final NetHandlerPlayClient var1 = GuiEditSign.Ñ¢á.µÕ();
        if (var1 != null) {
            var1.HorizonCode_Horizon_È(new C12PacketUpdateSign(this.HorizonCode_Horizon_È.á(), this.HorizonCode_Horizon_È.Âµá€));
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Ý() {
        ++this.Â;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà && button.£à == 0) {
            this.HorizonCode_Horizon_È.ŠÄ();
            GuiEditSign.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 200) {
            this.Ý = (this.Ý - 1 & 0x3);
        }
        if (keyCode == 208 || keyCode == 28 || keyCode == 156) {
            this.Ý = (this.Ý + 1 & 0x3);
        }
        String var3 = this.HorizonCode_Horizon_È.Âµá€[this.Ý].Ø();
        if (keyCode == 14 && var3.length() > 0) {
            var3 = var3.substring(0, var3.length() - 1);
        }
        if (ChatAllowedCharacters.HorizonCode_Horizon_È(typedChar) && this.É.HorizonCode_Horizon_È(String.valueOf(var3) + typedChar) <= 90) {
            var3 = String.valueOf(var3) + typedChar;
        }
        this.HorizonCode_Horizon_È.Âµá€[this.Ý] = new ChatComponentText(var3);
        if (keyCode == 1) {
            this.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("sign.edit", new Object[0]), GuiEditSign.Çªà¢ / 2, 40, 16777215);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Çªà¢();
        GlStateManager.Â(GuiEditSign.Çªà¢ / 2, 0.0f, 50.0f);
        final float var4 = 93.75f;
        GlStateManager.HorizonCode_Horizon_È(-var4, -var4, -var4);
        GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
        final Block var5 = this.HorizonCode_Horizon_È.ˆÏ­();
        if (var5 == Blocks.£Õ) {
            final float var6 = this.HorizonCode_Horizon_È.áˆºÑ¢Õ() * 360 / 16.0f;
            GlStateManager.Â(var6, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int var7 = this.HorizonCode_Horizon_È.áˆºÑ¢Õ();
            float var8 = 0.0f;
            if (var7 == 2) {
                var8 = 180.0f;
            }
            if (var7 == 4) {
                var8 = 90.0f;
            }
            if (var7 == 5) {
                var8 = -90.0f;
            }
            GlStateManager.Â(var8, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(0.0f, -1.0625f, 0.0f);
        }
        if (this.Â / 6 % 2 == 0) {
            this.HorizonCode_Horizon_È.Ó = this.Ý;
        }
        TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, -0.5, -0.75, -0.5, 0.0f);
        this.HorizonCode_Horizon_È.Ó = -1;
        GlStateManager.Ê();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
