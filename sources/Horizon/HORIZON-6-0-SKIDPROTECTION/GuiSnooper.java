package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import com.google.common.collect.Lists;
import java.util.List;

public class GuiSnooper extends GuiScreen
{
    private final GuiScreen HorizonCode_Horizon_È;
    private final GameSettings Â;
    private final List Ý;
    private final List Ø­áŒŠá;
    private String Âµá€;
    private String[] Ó;
    private HorizonCode_Horizon_È à;
    private GuiButton Ø;
    private static final String áŒŠÆ = "CL_00000714";
    
    public GuiSnooper(final GuiScreen p_i1061_1_, final GameSettings p_i1061_2_) {
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i1061_1_;
        this.Â = p_i1061_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Âµá€ = I18n.HorizonCode_Horizon_È("options.snooper.title", new Object[0]);
        final String var1 = I18n.HorizonCode_Horizon_È("options.snooper.desc", new Object[0]);
        final ArrayList var2 = Lists.newArrayList();
        for (final String var4 : this.É.Ý(var1, GuiSnooper.Çªà¢ - 30)) {
            var2.add(var4);
        }
        this.Ó = var2.toArray(new String[0]);
        this.Ý.clear();
        this.Ø­áŒŠá.clear();
        this.ÇŽÉ.add(this.Ø = new GuiButton(1, GuiSnooper.Çªà¢ / 2 - 152, GuiSnooper.Ê - 30, 150, 20, this.Â.Ý(GameSettings.HorizonCode_Horizon_È.µÕ)));
        this.ÇŽÉ.add(new GuiButton(2, GuiSnooper.Çªà¢ / 2 + 2, GuiSnooper.Ê - 30, 150, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        final boolean var5 = GuiSnooper.Ñ¢á.ˆá() != null && GuiSnooper.Ñ¢á.ˆá().£Ó() != null;
        for (final Map.Entry var7 : new TreeMap(GuiSnooper.Ñ¢á.É().Ý()).entrySet()) {
            this.Ý.add(String.valueOf(var5 ? "C " : "") + var7.getKey());
            this.Ø­áŒŠá.add(this.É.HorizonCode_Horizon_È(var7.getValue(), GuiSnooper.Çªà¢ - 220));
        }
        if (var5) {
            for (final Map.Entry var7 : new TreeMap(GuiSnooper.Ñ¢á.ˆá().£Ó().Ý()).entrySet()) {
                this.Ý.add("S " + var7.getKey());
                this.Ø­áŒŠá.add(this.É.HorizonCode_Horizon_È(var7.getValue(), GuiSnooper.Çªà¢ - 220));
            }
        }
        this.à = new HorizonCode_Horizon_È();
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.à.Ø();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 2) {
                this.Â.Â();
                this.Â.Â();
                GuiSnooper.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            }
            if (button.£à == 1) {
                this.Â.HorizonCode_Horizon_È(GameSettings.HorizonCode_Horizon_È.µÕ, 1);
                this.Ø.Å = this.Â.Ý(GameSettings.HorizonCode_Horizon_È.µÕ);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        this.à.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, this.Âµá€, GuiSnooper.Çªà¢ / 2, 8, 16777215);
        int var4 = 22;
        for (final String var8 : this.Ó) {
            this.HorizonCode_Horizon_È(this.É, var8, GuiSnooper.Çªà¢ / 2, var4, 8421504);
            var4 += this.É.HorizonCode_Horizon_È;
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiSnooper.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    class HorizonCode_Horizon_È extends GuiSlot
    {
        private static final String Â = "CL_00000715";
        
        public HorizonCode_Horizon_È() {
            super(GuiSnooper.Ñ¢á, GuiSnooper.Çªà¢, GuiSnooper.Ê, 80, GuiSnooper.Ê - 40, GuiSnooper.this.É.HorizonCode_Horizon_È + 1);
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return GuiSnooper.this.Ý.size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return false;
        }
        
        @Override
        protected void Â() {
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            GuiSnooper.this.É.HorizonCode_Horizon_È(GuiSnooper.this.Ý.get(p_180791_1_), 10, p_180791_3_, 16777215);
            GuiSnooper.this.É.HorizonCode_Horizon_È(GuiSnooper.this.Ø­áŒŠá.get(p_180791_1_), 230, p_180791_3_, 16777215);
        }
        
        @Override
        protected int à() {
            return this.Ø­áŒŠá - 10;
        }
    }
}
