package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.Map;
import java.util.List;
import java.io.IOException;

public class GuiLanguage extends GuiScreen
{
    protected GuiScreen HorizonCode_Horizon_È;
    private HorizonCode_Horizon_È Â;
    private final GameSettings Ý;
    private final LanguageManager Ø­áŒŠá;
    private GuiOptionButton Âµá€;
    private GuiOptionButton Ó;
    private static final String à = "CL_00000698";
    
    public GuiLanguage(final GuiScreen p_i1043_1_, final GameSettings p_i1043_2_, final LanguageManager p_i1043_3_) {
        this.HorizonCode_Horizon_È = p_i1043_1_;
        this.Ý = p_i1043_2_;
        this.Ø­áŒŠá = p_i1043_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(this.Âµá€ = new GuiOptionButton(100, GuiLanguage.Çªà¢ / 2 - 155, GuiLanguage.Ê - 38, GameSettings.HorizonCode_Horizon_È.ÇŽÉ, this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.ÇŽÉ)));
        this.ÇŽÉ.add(this.Ó = new GuiOptionButton(6, GuiLanguage.Çªà¢ / 2 - 155 + 160, GuiLanguage.Ê - 38, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        (this.Â = new HorizonCode_Horizon_È(GuiLanguage.Ñ¢á)).Ø­áŒŠá(7, 8);
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Â.Ø();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            switch (button.£à) {
                case 5: {
                    break;
                }
                case 6: {
                    GuiLanguage.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                    break;
                }
                case 100: {
                    if (button instanceof GuiOptionButton) {
                        this.Ý.HorizonCode_Horizon_È(((GuiOptionButton)button).HorizonCode_Horizon_È(), 1);
                        button.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.ÇŽÉ);
                        final ScaledResolution var2 = new ScaledResolution(GuiLanguage.Ñ¢á, GuiLanguage.Ñ¢á.Ó, GuiLanguage.Ñ¢á.à);
                        final int var3 = var2.HorizonCode_Horizon_È();
                        final int var4 = var2.Â();
                        this.HorizonCode_Horizon_È(GuiLanguage.Ñ¢á, var3, var4);
                        break;
                    }
                    break;
                }
                default: {
                    this.Â.HorizonCode_Horizon_È(button);
                    break;
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.Â.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("options.language", new Object[0]), GuiLanguage.Çªà¢ / 2, 16, 16777215);
        this.HorizonCode_Horizon_È(this.É, "(" + I18n.HorizonCode_Horizon_È("options.languageWarning", new Object[0]) + ")", GuiLanguage.Çªà¢ / 2, GuiLanguage.Ê - 56, 8421504);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiLanguage.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    class HorizonCode_Horizon_È extends GuiSlot
    {
        private final List Â;
        private final Map Šáƒ;
        private static final String Ï­Ðƒà = "CL_00000699";
        
        public HorizonCode_Horizon_È(final Minecraft mcIn) {
            super(mcIn, GuiLanguage.Çªà¢, GuiLanguage.Ê, 32, GuiLanguage.Ê - 65 + 4, 18);
            this.Â = Lists.newArrayList();
            this.Šáƒ = Maps.newHashMap();
            for (final Language var4 : GuiLanguage.this.Ø­áŒŠá.Ø­áŒŠá()) {
                this.Šáƒ.put(var4.HorizonCode_Horizon_È(), var4);
                this.Â.add(var4.HorizonCode_Horizon_È());
            }
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return this.Â.size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            final Language var5 = this.Šáƒ.get(this.Â.get(slotIndex));
            GuiLanguage.this.Ø­áŒŠá.HorizonCode_Horizon_È(var5);
            GuiLanguage.this.Ý.Ï­Ó = var5.HorizonCode_Horizon_È();
            this.Ý.Ó();
            GuiLanguage.this.É.HorizonCode_Horizon_È(GuiLanguage.this.Ø­áŒŠá.HorizonCode_Horizon_È() || GuiLanguage.this.Ý.ŠáŒŠà¢);
            GuiLanguage.this.É.Â(GuiLanguage.this.Ø­áŒŠá.Â());
            GuiLanguage.this.Ó.Å = I18n.HorizonCode_Horizon_È("gui.done", new Object[0]);
            GuiLanguage.this.Âµá€.Å = GuiLanguage.this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.ÇŽÉ);
            GuiLanguage.this.Ý.Â();
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return this.Â.get(slotIndex).equals(GuiLanguage.this.Ø­áŒŠá.Ý().HorizonCode_Horizon_È());
        }
        
        @Override
        protected int Ó() {
            return this.HorizonCode_Horizon_È() * 18;
        }
        
        @Override
        protected void Â() {
            GuiLanguage.this.£á();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            GuiLanguage.this.É.Â(true);
            GuiLanguage.this.HorizonCode_Horizon_È(GuiLanguage.this.É, this.Šáƒ.get(this.Â.get(p_180791_1_)).toString(), this.Ø­áŒŠá / 2, p_180791_3_ + 1, 16777215);
            GuiLanguage.this.É.Â(GuiLanguage.this.Ø­áŒŠá.Ý().Â());
        }
    }
}
