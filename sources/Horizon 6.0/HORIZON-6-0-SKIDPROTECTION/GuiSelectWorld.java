package HORIZON-6-0-SKIDPROTECTION;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import java.util.Collections;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.text.DateFormat;
import org.apache.logging.log4j.Logger;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger Ý;
    private final DateFormat Ø­áŒŠá;
    protected GuiScreen HorizonCode_Horizon_È;
    protected String Â;
    private boolean Âµá€;
    private int Ó;
    private List à;
    private HorizonCode_Horizon_È Ø;
    private String áŒŠÆ;
    private String áˆºÑ¢Õ;
    private String[] ÂµÈ;
    private boolean á;
    private GuiButton ˆÏ­;
    private GuiButton £á;
    private GuiButton Å;
    private GuiButton £à;
    private static final String µà = "CL_00000711";
    private int ˆà;
    
    static {
        Ý = LogManager.getLogger();
    }
    
    public GuiSelectWorld(final GuiScreen p_i1054_1_) {
        this.Ø­áŒŠá = new SimpleDateFormat();
        this.Â = "Select world";
        this.ÂµÈ = new String[4];
        this.HorizonCode_Horizon_È = p_i1054_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Â = I18n.HorizonCode_Horizon_È("selectWorld.title", new Object[0]);
        try {
            this.à();
        }
        catch (AnvilConverterException var2) {
            GuiSelectWorld.Ý.error("Couldn't load level list", (Throwable)var2);
            GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
            return;
        }
        this.áŒŠÆ = I18n.HorizonCode_Horizon_È("selectWorld.world", new Object[0]);
        this.áˆºÑ¢Õ = I18n.HorizonCode_Horizon_È("selectWorld.conversion", new Object[0]);
        this.ÂµÈ[WorldSettings.HorizonCode_Horizon_È.Â.HorizonCode_Horizon_È()] = I18n.HorizonCode_Horizon_È("gameMode.survival", new Object[0]);
        this.ÂµÈ[WorldSettings.HorizonCode_Horizon_È.Ý.HorizonCode_Horizon_È()] = I18n.HorizonCode_Horizon_È("gameMode.creative", new Object[0]);
        this.ÂµÈ[WorldSettings.HorizonCode_Horizon_È.Ø­áŒŠá.HorizonCode_Horizon_È()] = I18n.HorizonCode_Horizon_È("gameMode.adventure", new Object[0]);
        this.ÂµÈ[WorldSettings.HorizonCode_Horizon_È.Âµá€.HorizonCode_Horizon_È()] = I18n.HorizonCode_Horizon_È("gameMode.spectator", new Object[0]);
        (this.Ø = new HorizonCode_Horizon_È(GuiSelectWorld.Ñ¢á)).Ø­áŒŠá(4, 5);
        this.Ó();
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Ø.Ø();
    }
    
    private void à() throws AnvilConverterException {
        final ISaveFormat var1 = GuiSelectWorld.Ñ¢á.à();
        Collections.sort((List<Comparable>)(this.à = var1.Â()));
        this.Ó = -1;
    }
    
    protected String HorizonCode_Horizon_È(final int p_146621_1_) {
        return this.à.get(p_146621_1_).HorizonCode_Horizon_È();
    }
    
    protected String Ø(final int p_146614_1_) {
        String var2 = this.à.get(p_146614_1_).Â();
        if (StringUtils.isEmpty((CharSequence)var2)) {
            var2 = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.world", new Object[0])) + " " + (p_146614_1_ + 1);
        }
        return var2;
    }
    
    public void Ó() {
        this.ÇŽÉ.add(this.£á = new GuiMenuButton(1, GuiSelectWorld.Çªà¢ / 2 - 154, GuiSelectWorld.Ê - 52, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.select", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(3, GuiSelectWorld.Çªà¢ / 2 + 4, GuiSelectWorld.Ê - 52, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.create", new Object[0])));
        this.ÇŽÉ.add(this.Å = new GuiMenuButton(6, GuiSelectWorld.Çªà¢ / 2 - 154, GuiSelectWorld.Ê - 28, 72, 20, I18n.HorizonCode_Horizon_È("selectWorld.rename", new Object[0])));
        this.ÇŽÉ.add(this.ˆÏ­ = new GuiMenuButton(2, GuiSelectWorld.Çªà¢ / 2 - 76, GuiSelectWorld.Ê - 28, 72, 20, I18n.HorizonCode_Horizon_È("selectWorld.delete", new Object[0])));
        this.ÇŽÉ.add(this.£à = new GuiMenuButton(7, GuiSelectWorld.Çªà¢ / 2 + 4, GuiSelectWorld.Ê - 28, 72, 20, I18n.HorizonCode_Horizon_È("selectWorld.recreate", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(0, GuiSelectWorld.Çªà¢ / 2 + 82, GuiSelectWorld.Ê - 28, 72, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.£á.µà = false;
        this.ˆÏ­.µà = false;
        this.Å.µà = false;
        this.£à.µà = false;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 2) {
                final String var2 = this.Ø(this.Ó);
                if (var2 != null) {
                    this.á = true;
                    final GuiYesNo var3 = HorizonCode_Horizon_È(this, var2, this.Ó);
                    GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(var3);
                }
            }
            else if (button.£à == 1) {
                this.áŒŠÆ(this.Ó);
            }
            else if (button.£à == 3) {
                GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(new GuiCreateWorld(this));
            }
            else if (button.£à == 6) {
                GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(new GuiRenameWorld(this, this.HorizonCode_Horizon_È(this.Ó)));
            }
            else if (button.£à == 0) {
                GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            }
            else if (button.£à == 7) {
                final GuiCreateWorld var4 = new GuiCreateWorld(this);
                final ISaveHandler var5 = GuiSelectWorld.Ñ¢á.à().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(this.Ó), false);
                final WorldInfo var6 = var5.Ý();
                var5.HorizonCode_Horizon_È();
                var4.HorizonCode_Horizon_È(var6);
                GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(var4);
            }
            else {
                this.Ø.HorizonCode_Horizon_È(button);
            }
        }
    }
    
    public void áŒŠÆ(final int p_146615_1_) {
        GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        if (!this.Âµá€) {
            this.Âµá€ = true;
            String var2 = this.HorizonCode_Horizon_È(p_146615_1_);
            if (var2 == null) {
                var2 = "World" + p_146615_1_;
            }
            String var3 = this.Ø(p_146615_1_);
            if (var3 == null) {
                var3 = "World" + p_146615_1_;
            }
            if (GuiSelectWorld.Ñ¢á.à().Ó(var2)) {
                GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(var2, var3, null);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean result, final int id) {
        if (this.á) {
            this.á = false;
            if (result) {
                final ISaveFormat var3 = GuiSelectWorld.Ñ¢á.à();
                var3.Ø­áŒŠá();
                var3.Âµá€(this.HorizonCode_Horizon_È(id));
                try {
                    this.à();
                }
                catch (AnvilConverterException var4) {
                    GuiSelectWorld.Ý.error("Couldn't load level list", (Throwable)var4);
                }
            }
            GuiSelectWorld.Ñ¢á.HorizonCode_Horizon_È(this);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        ++this.ˆà;
        this.Ø.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, this.Â, GuiSelectWorld.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    public static GuiYesNo HorizonCode_Horizon_È(final GuiYesNoCallback p_152129_0_, final String p_152129_1_, final int p_152129_2_) {
        final String var3 = I18n.HorizonCode_Horizon_È("selectWorld.deleteQuestion", new Object[0]);
        final String var4 = "'" + p_152129_1_ + "' " + I18n.HorizonCode_Horizon_È("selectWorld.deleteWarning", new Object[0]);
        final String var5 = I18n.HorizonCode_Horizon_È("selectWorld.deleteButton", new Object[0]);
        final String var6 = I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0]);
        final GuiYesNo var7 = new GuiYesNo(p_152129_0_, var3, var4, var5, var6, p_152129_2_);
        return var7;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final GuiSelectWorld guiSelectWorld, final int ó) {
        guiSelectWorld.Ó = ó;
    }
    
    class HorizonCode_Horizon_È extends GuiSlot
    {
        private static final String Â = "CL_00000712";
        
        public HorizonCode_Horizon_È(final Minecraft mcIn) {
            super(mcIn, GuiSelectWorld.Çªà¢, GuiSelectWorld.Ê, 32, GuiSelectWorld.Ê - 64, 36);
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return GuiSelectWorld.this.à.size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            GuiSelectWorld.HorizonCode_Horizon_È(GuiSelectWorld.this, slotIndex);
            final boolean var5 = GuiSelectWorld.this.Ó >= 0 && GuiSelectWorld.this.Ó < this.HorizonCode_Horizon_È();
            GuiSelectWorld.this.£á.µà = var5;
            GuiSelectWorld.this.ˆÏ­.µà = var5;
            GuiSelectWorld.this.Å.µà = var5;
            GuiSelectWorld.this.£à.µà = var5;
            if (isDoubleClick && var5) {
                GuiSelectWorld.this.áŒŠÆ(slotIndex);
            }
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return slotIndex == GuiSelectWorld.this.Ó;
        }
        
        @Override
        protected int Ó() {
            return GuiSelectWorld.this.à.size() * 36;
        }
        
        @Override
        protected void Â() {
            GuiSelectWorld.this.£á();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final SaveFormatComparator var7 = GuiSelectWorld.this.à.get(p_180791_1_);
            String var8 = var7.Â();
            if (StringUtils.isEmpty((CharSequence)var8)) {
                var8 = String.valueOf(GuiSelectWorld.this.áŒŠÆ) + " " + (p_180791_1_ + 1);
            }
            String var9 = var7.HorizonCode_Horizon_È();
            var9 = String.valueOf(var9) + " (" + GuiSelectWorld.this.Ø­áŒŠá.format(new Date(var7.Âµá€()));
            var9 = String.valueOf(var9) + ")";
            String var10 = "";
            if (var7.Ø­áŒŠá()) {
                var10 = String.valueOf(GuiSelectWorld.this.áˆºÑ¢Õ) + " " + var10;
            }
            else {
                var10 = GuiSelectWorld.this.ÂµÈ[var7.Ó().HorizonCode_Horizon_È()];
                if (var7.à()) {
                    var10 = EnumChatFormatting.Âµá€ + I18n.HorizonCode_Horizon_È("gameMode.hardcore", new Object[0]) + EnumChatFormatting.Æ;
                }
                if (var7.Ø()) {
                    var10 = String.valueOf(var10) + ", " + I18n.HorizonCode_Horizon_È("selectWorld.cheats", new Object[0]);
                }
            }
            Gui_1808253012.Â(GuiSelectWorld.this.É, var8, p_180791_2_ + 2, p_180791_3_ + 1, 16777215);
            Gui_1808253012.Â(GuiSelectWorld.this.É, var9, p_180791_2_ + 2, p_180791_3_ + 12, 8421504);
            Gui_1808253012.Â(GuiSelectWorld.this.É, var10, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 8421504);
        }
    }
}
