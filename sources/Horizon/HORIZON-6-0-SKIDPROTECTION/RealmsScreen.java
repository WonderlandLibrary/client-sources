package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.util.UUIDTypeAdapter;
import java.util.List;

public class RealmsScreen
{
    public static final int HorizonCode_Horizon_È = 8;
    public static final int Â = 8;
    public static final int Ý = 8;
    public static final int Ø­áŒŠá = 8;
    public static final int Âµá€ = 40;
    public static final int Ó = 8;
    public static final int à = 8;
    public static final int Ø = 8;
    public static final int áŒŠÆ = 64;
    public static final int áˆºÑ¢Õ = 64;
    protected Minecraft ÂµÈ;
    public int á;
    public int ˆÏ­;
    private GuiScreenRealmsProxy £á;
    private static final String Å = "CL_00001898";
    
    public RealmsScreen() {
        this.£á = new GuiScreenRealmsProxy(this);
    }
    
    public GuiScreenRealmsProxy HorizonCode_Horizon_È() {
        return this.£á;
    }
    
    public void init() {
    }
    
    public void HorizonCode_Horizon_È(final Minecraft p_init_1_, final int p_init_2_, final int p_init_3_) {
    }
    
    public void HorizonCode_Horizon_È(final String p_drawCenteredString_1_, final int p_drawCenteredString_2_, final int p_drawCenteredString_3_, final int p_drawCenteredString_4_) {
        this.£á.HorizonCode_Horizon_È(p_drawCenteredString_1_, p_drawCenteredString_2_, p_drawCenteredString_3_, p_drawCenteredString_4_);
    }
    
    public void Â(final String p_drawString_1_, final int p_drawString_2_, final int p_drawString_3_, final int p_drawString_4_) {
        this.£á.Â(p_drawString_1_, p_drawString_2_, p_drawString_3_, p_drawString_4_);
    }
    
    public void HorizonCode_Horizon_È(final int p_blit_1_, final int p_blit_2_, final int p_blit_3_, final int p_blit_4_, final int p_blit_5_, final int p_blit_6_) {
        this.£á.Â(p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_, p_blit_6_);
    }
    
    public static void HorizonCode_Horizon_È(final int p_blit_0_, final int p_blit_1_, final float p_blit_2_, final float p_blit_3_, final int p_blit_4_, final int p_blit_5_, final int p_blit_6_, final int p_blit_7_, final float p_blit_8_, final float p_blit_9_) {
        Gui_1808253012.HorizonCode_Horizon_È(p_blit_0_, p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_, p_blit_6_, p_blit_7_, p_blit_8_, p_blit_9_);
    }
    
    public static void HorizonCode_Horizon_È(final int p_blit_0_, final int p_blit_1_, final float p_blit_2_, final float p_blit_3_, final int p_blit_4_, final int p_blit_5_, final float p_blit_6_, final float p_blit_7_) {
        Gui_1808253012.HorizonCode_Horizon_È(p_blit_0_, p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_, p_blit_6_, p_blit_7_);
    }
    
    public void Â() {
        this.£á.£á();
    }
    
    public boolean Ý() {
        return this.£á.Ø­áŒŠá();
    }
    
    public void HorizonCode_Horizon_È(final int p_renderBackground_1_) {
        this.£á.Â(p_renderBackground_1_);
    }
    
    public void render(final int p_render_1_, final int p_render_2_, final float p_render_3_) {
        for (int var4 = 0; var4 < this.£á.áŒŠÆ().size(); ++var4) {
            this.£á.áŒŠÆ().get(var4).HorizonCode_Horizon_È(p_render_1_, p_render_2_);
        }
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_renderTooltip_1_, final int p_renderTooltip_2_, final int p_renderTooltip_3_) {
        this.£á.HorizonCode_Horizon_È(p_renderTooltip_1_, p_renderTooltip_2_, p_renderTooltip_3_);
    }
    
    public void HorizonCode_Horizon_È(final String p_renderTooltip_1_, final int p_renderTooltip_2_, final int p_renderTooltip_3_) {
        this.£á.HorizonCode_Horizon_È(p_renderTooltip_1_, p_renderTooltip_2_, p_renderTooltip_3_);
    }
    
    public void HorizonCode_Horizon_È(final List p_renderTooltip_1_, final int p_renderTooltip_2_, final int p_renderTooltip_3_) {
        this.£á.HorizonCode_Horizon_È(p_renderTooltip_1_, p_renderTooltip_2_, p_renderTooltip_3_);
    }
    
    public static void HorizonCode_Horizon_È(final String p_bindFace_0_, final String p_bindFace_1_) {
        ResourceLocation_1975012498 var2 = AbstractClientPlayer.HorizonCode_Horizon_È(p_bindFace_1_);
        if (var2 == null) {
            var2 = DefaultPlayerSkin.HorizonCode_Horizon_È(UUIDTypeAdapter.fromString(p_bindFace_0_));
        }
        AbstractClientPlayer.HorizonCode_Horizon_È(var2, p_bindFace_1_);
        Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(var2);
    }
    
    public static void HorizonCode_Horizon_È(final String p_bind_0_) {
        final ResourceLocation_1975012498 var1 = new ResourceLocation_1975012498(p_bind_0_);
        Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(var1);
    }
    
    public void tick() {
    }
    
    public int Ø­áŒŠá() {
        return GuiScreenRealmsProxy.Çªà¢;
    }
    
    public int Âµá€() {
        return GuiScreenRealmsProxy.Ê;
    }
    
    public int Ó() {
        return this.£á.à();
    }
    
    public int Â(final String p_fontWidth_1_) {
        return this.£á.HorizonCode_Horizon_È(p_fontWidth_1_);
    }
    
    public void Ý(final String p_fontDrawShadow_1_, final int p_fontDrawShadow_2_, final int p_fontDrawShadow_3_, final int p_fontDrawShadow_4_) {
        this.£á.Ý(p_fontDrawShadow_1_, p_fontDrawShadow_2_, p_fontDrawShadow_3_, p_fontDrawShadow_4_);
    }
    
    public List HorizonCode_Horizon_È(final String p_fontSplit_1_, final int p_fontSplit_2_) {
        return this.£á.HorizonCode_Horizon_È(p_fontSplit_1_, p_fontSplit_2_);
    }
    
    public void buttonClicked(final RealmsButton p_buttonClicked_1_) {
    }
    
    public static RealmsButton HorizonCode_Horizon_È(final int p_newButton_0_, final int p_newButton_1_, final int p_newButton_2_, final String p_newButton_3_) {
        return new RealmsButton(p_newButton_0_, p_newButton_1_, p_newButton_2_, p_newButton_3_);
    }
    
    public static RealmsButton HorizonCode_Horizon_È(final int p_newButton_0_, final int p_newButton_1_, final int p_newButton_2_, final int p_newButton_3_, final int p_newButton_4_, final String p_newButton_5_) {
        return new RealmsButton(p_newButton_0_, p_newButton_1_, p_newButton_2_, p_newButton_3_, p_newButton_4_, p_newButton_5_);
    }
    
    public void à() {
        this.£á.Ø();
    }
    
    public void HorizonCode_Horizon_È(final RealmsButton p_buttonsAdd_1_) {
        this.£á.HorizonCode_Horizon_È(p_buttonsAdd_1_);
    }
    
    public List Ø() {
        return this.£á.áŒŠÆ();
    }
    
    public void Â(final RealmsButton p_buttonsRemove_1_) {
        this.£á.Â(p_buttonsRemove_1_);
    }
    
    public RealmsEditBox HorizonCode_Horizon_È(final int p_newEditBox_1_, final int p_newEditBox_2_, final int p_newEditBox_3_, final int p_newEditBox_4_, final int p_newEditBox_5_) {
        return new RealmsEditBox(p_newEditBox_1_, p_newEditBox_2_, p_newEditBox_3_, p_newEditBox_4_, p_newEditBox_5_);
    }
    
    public void mouseClicked(final int p_mouseClicked_1_, final int p_mouseClicked_2_, final int p_mouseClicked_3_) {
    }
    
    public void mouseEvent() {
    }
    
    public void áŒŠÆ() {
    }
    
    public void HorizonCode_Horizon_È(final int p_mouseReleased_1_, final int p_mouseReleased_2_, final int p_mouseReleased_3_) {
    }
    
    public void HorizonCode_Horizon_È(final int p_mouseDragged_1_, final int p_mouseDragged_2_, final int p_mouseDragged_3_, final long p_mouseDragged_4_) {
    }
    
    public void keyPressed(final char p_keyPressed_1_, final int p_keyPressed_2_) {
    }
    
    public void confirmResult(final boolean p_confirmResult_1_, final int p_confirmResult_2_) {
    }
    
    public static String Ý(final String p_getLocalizedString_0_) {
        return I18n.HorizonCode_Horizon_È(p_getLocalizedString_0_, new Object[0]);
    }
    
    public static String HorizonCode_Horizon_È(final String p_getLocalizedString_0_, final Object... p_getLocalizedString_1_) {
        return I18n.HorizonCode_Horizon_È(p_getLocalizedString_0_, p_getLocalizedString_1_);
    }
    
    public RealmsAnvilLevelStorageSource áˆºÑ¢Õ() {
        return new RealmsAnvilLevelStorageSource(Minecraft.áŒŠà().à());
    }
    
    public void removed() {
    }
}
