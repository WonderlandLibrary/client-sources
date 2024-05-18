package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiScreenOptionsSounds extends GuiScreen
{
    private final GuiScreen Â;
    private final GameSettings Ý;
    protected String HorizonCode_Horizon_È;
    private String Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000716";
    
    public GuiScreenOptionsSounds(final GuiScreen p_i45025_1_, final GameSettings p_i45025_2_) {
        this.HorizonCode_Horizon_È = "Options";
        this.Â = p_i45025_1_;
        this.Ý = p_i45025_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        final byte var1 = 0;
        this.HorizonCode_Horizon_È = I18n.HorizonCode_Horizon_È("options.sounds.title", new Object[0]);
        this.Ø­áŒŠá = I18n.HorizonCode_Horizon_È("options.off", new Object[0]);
        this.ÇŽÉ.add(new HorizonCode_Horizon_È(SoundCategory.HorizonCode_Horizon_È.Â(), GuiScreenOptionsSounds.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiScreenOptionsSounds.Ê / 6 - 12 + 24 * (var1 >> 1), SoundCategory.HorizonCode_Horizon_È, true));
        int var2 = var1 + 2;
        for (final SoundCategory var6 : SoundCategory.values()) {
            if (var6 != SoundCategory.HorizonCode_Horizon_È) {
                this.ÇŽÉ.add(new HorizonCode_Horizon_È(var6.Â(), GuiScreenOptionsSounds.Çªà¢ / 2 - 155 + var2 % 2 * 160, GuiScreenOptionsSounds.Ê / 6 - 12 + 24 * (var2 >> 1), var6, false));
                ++var2;
            }
        }
        this.ÇŽÉ.add(new GuiMenuButton(200, GuiScreenOptionsSounds.Çªà¢ / 2 - 100, GuiScreenOptionsSounds.Ê / 6 + 168, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà && button.£à == 200) {
            GuiScreenOptionsSounds.Ñ¢á.ŠÄ.Â();
            GuiScreenOptionsSounds.Ñ¢á.HorizonCode_Horizon_È(this.Â);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        this.HorizonCode_Horizon_È(UIFonts.áŒŠÆ, this.HorizonCode_Horizon_È, GuiScreenOptionsSounds.Çªà¢ / 2, 15, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiScreenOptionsSounds.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    protected String HorizonCode_Horizon_È(final SoundCategory p_146504_1_) {
        final float var2 = this.Ý.HorizonCode_Horizon_È(p_146504_1_);
        return (var2 == 0.0f) ? this.Ø­áŒŠá : (String.valueOf((int)(var2 * 100.0f)) + "%");
    }
    
    class HorizonCode_Horizon_È extends GuiMenuButtonNoSlide
    {
        private final SoundCategory Ø­áŒŠá;
        private final String Âµá€;
        public float HorizonCode_Horizon_È;
        public boolean Â;
        private static final String Ø­à = "CL_00000717";
        
        public HorizonCode_Horizon_È(final int p_i45024_2_, final int p_i45024_3_, final int p_i45024_4_, final SoundCategory p_i45024_5_, final boolean p_i45024_6_) {
            super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
            this.HorizonCode_Horizon_È = 1.0f;
            this.Ø­áŒŠá = p_i45024_5_;
            this.Âµá€ = I18n.HorizonCode_Horizon_È("soundCategory." + p_i45024_5_.HorizonCode_Horizon_È(), new Object[0]);
            this.Å = String.valueOf(this.Âµá€) + ": " + GuiScreenOptionsSounds.this.HorizonCode_Horizon_È(p_i45024_5_);
            this.HorizonCode_Horizon_È = GuiScreenOptionsSounds.this.Ý.HorizonCode_Horizon_È(p_i45024_5_);
        }
        
        @Override
        protected int HorizonCode_Horizon_È(final boolean mouseOver) {
            return 0;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final Minecraft mc, final int mouseX, final int mouseY) {
            if (this.ˆà) {
                if (this.Â) {
                    this.HorizonCode_Horizon_È = (mouseX - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
                    this.HorizonCode_Horizon_È = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 0.0f, 1.0f);
                    mc.ŠÄ.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.HorizonCode_Horizon_È);
                    mc.ŠÄ.Â();
                    this.Å = String.valueOf(this.Âµá€) + ": " + GuiScreenOptionsSounds.this.HorizonCode_Horizon_È(this.Ø­áŒŠá);
                }
                if (this.¥Æ || this.Â) {
                    Gui_1808253012.HorizonCode_Horizon_È(this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)), this.£á, this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)) + 8, this.£á + 20, 2130706431);
                }
                else {
                    Gui_1808253012.HorizonCode_Horizon_È(this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)), this.£á, this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)) + 8, this.£á + 20, Integer.MIN_VALUE);
                }
            }
        }
        
        @Override
        public boolean Â(final Minecraft mc, final int mouseX, final int mouseY) {
            if (super.Â(mc, mouseX, mouseY)) {
                this.HorizonCode_Horizon_È = (mouseX - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
                this.HorizonCode_Horizon_È = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 0.0f, 1.0f);
                mc.ŠÄ.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.HorizonCode_Horizon_È);
                mc.ŠÄ.Â();
                this.Å = String.valueOf(this.Âµá€) + ": " + GuiScreenOptionsSounds.this.HorizonCode_Horizon_È(this.Ø­áŒŠá);
                return this.Â = true;
            }
            return false;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final SoundHandler soundHandlerIn) {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
            if (this.Â) {
                if (this.Ø­áŒŠá != SoundCategory.HorizonCode_Horizon_È) {
                    GuiScreenOptionsSounds.this.Ý.HorizonCode_Horizon_È(this.Ø­áŒŠá);
                }
                GuiScreenOptionsSounds.Ñ¢á.£ÂµÄ().HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("gui.button.press"), 1.0f));
            }
            this.Â = false;
        }
    }
}
