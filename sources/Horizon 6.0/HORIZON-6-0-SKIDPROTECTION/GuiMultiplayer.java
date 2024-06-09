package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import com.google.common.base.Splitter;
import java.util.List;
import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback
{
    private final OldServerPinger Ý;
    private GuiScreen Ø­áŒŠá;
    private ServerSelectionList Âµá€;
    private ServerList Ó;
    private GuiButton à;
    private GuiButton Ø;
    private GuiButton áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private boolean á;
    public static boolean HorizonCode_Horizon_È;
    private String ˆÏ­;
    public static ServerData Â;
    private LanServerDetector.Â £á;
    private LanServerDetector.Ý Å;
    private boolean £à;
    private static final String µà = "CL_00000814";
    private int ˆà;
    
    public GuiMultiplayer(final GuiScreen parentScreen) {
        this.Ý = new OldServerPinger();
        this.Ø­áŒŠá = parentScreen;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.clear();
        if (!this.£à) {
            this.£à = true;
            (this.Ó = new ServerList(GuiMultiplayer.Ñ¢á)).HorizonCode_Horizon_È();
            this.£á = new LanServerDetector.Â();
            try {
                (this.Å = new LanServerDetector.Ý(this.£á)).start();
            }
            catch (Exception ex) {}
            (this.Âµá€ = new ServerSelectionList(this, GuiMultiplayer.Ñ¢á, GuiMultiplayer.Çªà¢, GuiMultiplayer.Ê, 32, GuiMultiplayer.Ê - 64, 36)).HorizonCode_Horizon_È(this.Ó);
        }
        else {
            this.Âµá€.Â(GuiMultiplayer.Çªà¢, GuiMultiplayer.Ê, 32, GuiMultiplayer.Ê - 64);
        }
        this.Ó();
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Âµá€.Ø();
    }
    
    public void Ó() {
        this.ÇŽÉ.add(this.à = new GuiMenuButton(7, GuiMultiplayer.Çªà¢ / 2 - 154, GuiMultiplayer.Ê - 28, 70, 20, I18n.HorizonCode_Horizon_È("selectServer.edit", new Object[0])));
        this.ÇŽÉ.add(this.áŒŠÆ = new GuiMenuButton(2, GuiMultiplayer.Çªà¢ / 2 - 74, GuiMultiplayer.Ê - 28, 70, 20, I18n.HorizonCode_Horizon_È("selectServer.delete", new Object[0])));
        this.ÇŽÉ.add(this.Ø = new GuiMenuButton(1, GuiMultiplayer.Çªà¢ / 2 - 154, GuiMultiplayer.Ê - 52, 100, 20, I18n.HorizonCode_Horizon_È("selectServer.select", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(4, GuiMultiplayer.Çªà¢ / 2 - 50, GuiMultiplayer.Ê - 52, 100, 20, I18n.HorizonCode_Horizon_È("selectServer.direct", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(3, GuiMultiplayer.Çªà¢ / 2 + 4 + 50, GuiMultiplayer.Ê - 52, 100, 20, I18n.HorizonCode_Horizon_È("selectServer.add", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(8, GuiMultiplayer.Çªà¢ / 2 + 4, GuiMultiplayer.Ê - 28, 70, 20, I18n.HorizonCode_Horizon_È("selectServer.refresh", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(0, GuiMultiplayer.Çªà¢ / 2 + 4 + 76, GuiMultiplayer.Ê - 28, 75, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.HorizonCode_Horizon_È(this.Âµá€.Ø­áŒŠá());
    }
    
    @Override
    public void Ý() {
        super.Ý();
        if (this.£á.HorizonCode_Horizon_È()) {
            final List var1 = this.£á.Ý();
            this.£á.Â();
            this.Âµá€.HorizonCode_Horizon_È(var1);
        }
        this.Ý.HorizonCode_Horizon_È();
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
        if (this.Å != null) {
            this.Å.interrupt();
            this.Å = null;
        }
        this.Ý.Â();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            final GuiListExtended.HorizonCode_Horizon_È var2 = (this.Âµá€.Ø­áŒŠá() < 0) ? null : this.Âµá€.Â(this.Âµá€.Ø­áŒŠá());
            if (button.£à == 2 && var2 instanceof ServerListEntryNormal) {
                final String var3 = ((ServerListEntryNormal)var2).HorizonCode_Horizon_È().HorizonCode_Horizon_È;
                if (var3 != null) {
                    this.áˆºÑ¢Õ = true;
                    final String var4 = I18n.HorizonCode_Horizon_È("selectServer.deleteQuestion", new Object[0]);
                    final String var5 = "'" + var3 + "' " + I18n.HorizonCode_Horizon_È("selectServer.deleteWarning", new Object[0]);
                    final String var6 = I18n.HorizonCode_Horizon_È("selectServer.deleteButton", new Object[0]);
                    final String var7 = I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0]);
                    final GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, this.Âµá€.Ø­áŒŠá());
                    GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(var8);
                }
            }
            else if (button.£à == 1) {
                this.à();
            }
            else if (button.£à == 4) {
                GuiMultiplayer.HorizonCode_Horizon_È = true;
                GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(new GuiScreenServerList(this, GuiMultiplayer.Â = new ServerData(I18n.HorizonCode_Horizon_È("selectServer.defaultName", new Object[0]), "")));
            }
            else if (button.£à == 3) {
                this.ÂµÈ = true;
                GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(new GuiScreenAddServer(this, GuiMultiplayer.Â = new ServerData(I18n.HorizonCode_Horizon_È("selectServer.defaultName", new Object[0]), "")));
            }
            else if (button.£à == 7 && var2 instanceof ServerListEntryNormal) {
                this.á = true;
                final ServerData var9 = ((ServerListEntryNormal)var2).HorizonCode_Horizon_È();
                (GuiMultiplayer.Â = new ServerData(var9.HorizonCode_Horizon_È, var9.Â)).HorizonCode_Horizon_È(var9);
                GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(new GuiScreenAddServer(this, GuiMultiplayer.Â));
            }
            else if (button.£à == 0) {
                GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(this.Ø­áŒŠá);
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            }
            else if (button.£à == 8) {
                this.ˆà();
            }
        }
    }
    
    private void ˆà() {
        GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(new GuiMultiplayer(this.Ø­áŒŠá));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean result, final int id) {
        final GuiListExtended.HorizonCode_Horizon_È var3 = (this.Âµá€.Ø­áŒŠá() < 0) ? null : this.Âµá€.Â(this.Âµá€.Ø­áŒŠá());
        if (this.áˆºÑ¢Õ) {
            this.áˆºÑ¢Õ = false;
            if (result && var3 instanceof ServerListEntryNormal) {
                this.Ó.Â(this.Âµá€.Ø­áŒŠá());
                this.Ó.Â();
                this.Âµá€.Ý(-1);
                this.Âµá€.HorizonCode_Horizon_È(this.Ó);
            }
            GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(this);
        }
        else if (GuiMultiplayer.HorizonCode_Horizon_È) {
            GuiMultiplayer.HorizonCode_Horizon_È = false;
            if (result) {
                HorizonCode_Horizon_È(GuiMultiplayer.Â);
            }
            else {
                GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(this);
            }
        }
        else if (this.ÂµÈ) {
            this.ÂµÈ = false;
            if (result) {
                this.Ó.HorizonCode_Horizon_È(GuiMultiplayer.Â);
                this.Ó.Â();
                this.Âµá€.Ý(-1);
                this.Âµá€.HorizonCode_Horizon_È(this.Ó);
            }
            GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(this);
        }
        else if (this.á) {
            this.á = false;
            if (result && var3 instanceof ServerListEntryNormal) {
                final ServerData var4 = ((ServerListEntryNormal)var3).HorizonCode_Horizon_È();
                var4.HorizonCode_Horizon_È = GuiMultiplayer.Â.HorizonCode_Horizon_È;
                var4.Â = GuiMultiplayer.Â.Â;
                var4.HorizonCode_Horizon_È(GuiMultiplayer.Â);
                this.Ó.Â();
                this.Âµá€.HorizonCode_Horizon_È(this.Ó);
            }
            GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(this);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        final int var3 = this.Âµá€.Ø­áŒŠá();
        final GuiListExtended.HorizonCode_Horizon_È var4 = (var3 < 0) ? null : this.Âµá€.Â(var3);
        if (keyCode == 63) {
            this.ˆà();
        }
        else if (var3 >= 0) {
            if (keyCode == 200) {
                if (£à()) {
                    if (var3 > 0 && var4 instanceof ServerListEntryNormal) {
                        this.Ó.HorizonCode_Horizon_È(var3, var3 - 1);
                        this.HorizonCode_Horizon_È(this.Âµá€.Ø­áŒŠá() - 1);
                        this.Âµá€.Ó(-this.Âµá€.£à());
                        this.Âµá€.HorizonCode_Horizon_È(this.Ó);
                    }
                }
                else if (var3 > 0) {
                    this.HorizonCode_Horizon_È(this.Âµá€.Ø­áŒŠá() - 1);
                    this.Âµá€.Ó(-this.Âµá€.£à());
                    if (this.Âµá€.Â(this.Âµá€.Ø­áŒŠá()) instanceof ServerListEntryLanScan) {
                        if (this.Âµá€.Ø­áŒŠá() > 0) {
                            this.HorizonCode_Horizon_È(this.Âµá€.HorizonCode_Horizon_È() - 1);
                            this.Âµá€.Ó(-this.Âµá€.£à());
                        }
                        else {
                            this.HorizonCode_Horizon_È(-1);
                        }
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(-1);
                }
            }
            else if (keyCode == 208) {
                if (£à()) {
                    if (var3 < this.Ó.Ý() - 1) {
                        this.Ó.HorizonCode_Horizon_È(var3, var3 + 1);
                        this.HorizonCode_Horizon_È(var3 + 1);
                        this.Âµá€.Ó(this.Âµá€.£à());
                        this.Âµá€.HorizonCode_Horizon_È(this.Ó);
                    }
                }
                else if (var3 < this.Âµá€.HorizonCode_Horizon_È()) {
                    this.HorizonCode_Horizon_È(this.Âµá€.Ø­áŒŠá() + 1);
                    this.Âµá€.Ó(this.Âµá€.£à());
                    if (this.Âµá€.Â(this.Âµá€.Ø­áŒŠá()) instanceof ServerListEntryLanScan) {
                        if (this.Âµá€.Ø­áŒŠá() < this.Âµá€.HorizonCode_Horizon_È() - 1) {
                            this.HorizonCode_Horizon_È(this.Âµá€.HorizonCode_Horizon_È() + 1);
                            this.Âµá€.Ó(this.Âµá€.£à());
                        }
                        else {
                            this.HorizonCode_Horizon_È(-1);
                        }
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(-1);
                }
            }
            else if (keyCode != 28 && keyCode != 156) {
                super.HorizonCode_Horizon_È(typedChar, keyCode);
            }
            else {
                this.HorizonCode_Horizon_È(this.ÇŽÉ.get(2));
            }
        }
        else {
            super.HorizonCode_Horizon_È(typedChar, keyCode);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        ++this.ˆà;
        this.ˆÏ­ = null;
        this.£á();
        this.Âµá€.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("multiplayer.title", new Object[0]), GuiMultiplayer.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
        if (this.ˆÏ­ != null) {
            this.HorizonCode_Horizon_È(Lists.newArrayList(Splitter.on("\n").split((CharSequence)this.ˆÏ­)), mouseX, mouseY);
        }
    }
    
    public void à() {
        final GuiListExtended.HorizonCode_Horizon_È var1 = (this.Âµá€.Ø­áŒŠá() < 0) ? null : this.Âµá€.Â(this.Âµá€.Ø­áŒŠá());
        if (var1 instanceof ServerListEntryNormal) {
            HorizonCode_Horizon_È(((ServerListEntryNormal)var1).HorizonCode_Horizon_È());
        }
        else if (var1 instanceof ServerListEntryLanDetected) {
            final LanServerDetector.HorizonCode_Horizon_È var2 = ((ServerListEntryLanDetected)var1).HorizonCode_Horizon_È();
            HorizonCode_Horizon_È(new ServerData(var2.HorizonCode_Horizon_È(), var2.Â()));
        }
    }
    
    public static void HorizonCode_Horizon_È(final ServerData server) {
        GuiMultiplayer.Ñ¢á.HorizonCode_Horizon_È(new GuiConnecting(new GuiMainMenu(), GuiMultiplayer.Ñ¢á, server));
    }
    
    public void HorizonCode_Horizon_È(final int index) {
        this.Âµá€.Ý(index);
        final GuiListExtended.HorizonCode_Horizon_È var2 = (index < 0) ? null : this.Âµá€.Â(index);
        this.Ø.µà = false;
        this.à.µà = false;
        this.áŒŠÆ.µà = false;
        if (var2 != null && !(var2 instanceof ServerListEntryLanScan)) {
            this.Ø.µà = true;
            if (var2 instanceof ServerListEntryNormal) {
                this.à.µà = true;
                this.áŒŠÆ.µà = true;
            }
        }
    }
    
    public OldServerPinger Ø() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final String p_146793_1_) {
        this.ˆÏ­ = p_146793_1_;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Âµá€.Â(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void Â(final int mouseX, final int mouseY, final int state) {
        super.Â(mouseX, mouseY, state);
        this.Âµá€.Ý(mouseX, mouseY, state);
    }
    
    public ServerList áŒŠÆ() {
        return this.Ó;
    }
    
    public boolean HorizonCode_Horizon_È(final ServerListEntryNormal p_175392_1_, final int p_175392_2_) {
        return p_175392_2_ > 0;
    }
    
    public boolean Â(final ServerListEntryNormal p_175394_1_, final int p_175394_2_) {
        return p_175394_2_ < this.Ó.Ý() - 1;
    }
    
    public void HorizonCode_Horizon_È(final ServerListEntryNormal p_175391_1_, final int p_175391_2_, final boolean p_175391_3_) {
        final int var4 = p_175391_3_ ? 0 : (p_175391_2_ - 1);
        this.Ó.HorizonCode_Horizon_È(p_175391_2_, var4);
        if (this.Âµá€.Ø­áŒŠá() == p_175391_2_) {
            this.HorizonCode_Horizon_È(var4);
        }
        this.Âµá€.HorizonCode_Horizon_È(this.Ó);
    }
    
    public void Â(final ServerListEntryNormal p_175393_1_, final int p_175393_2_, final boolean p_175393_3_) {
        final int var4 = p_175393_3_ ? (this.Ó.Ý() - 1) : (p_175393_2_ + 1);
        this.Ó.HorizonCode_Horizon_È(p_175393_2_, var4);
        if (this.Âµá€.Ø­áŒŠá() == p_175393_2_) {
            this.HorizonCode_Horizon_È(var4);
        }
        this.Âµá€.HorizonCode_Horizon_È(this.Ó);
    }
}
