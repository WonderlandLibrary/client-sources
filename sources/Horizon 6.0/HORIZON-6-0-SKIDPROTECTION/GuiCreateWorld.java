package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen
{
    private GuiScreen Â;
    private GuiTextField Ý;
    private GuiTextField Ø­áŒŠá;
    private String Âµá€;
    private String Ó;
    private String à;
    private boolean Ø;
    private boolean áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private boolean á;
    private boolean ˆÏ­;
    private boolean £á;
    private GuiButton Å;
    private GuiButton £à;
    private GuiButton µà;
    private GuiButton ˆà;
    private GuiButton ¥Æ;
    private GuiButton Ø­à;
    private GuiButton µÕ;
    private String Æ;
    private String áƒ;
    private String á€;
    private String Õ;
    private int à¢;
    public String HorizonCode_Horizon_È;
    private static final String[] ŠÂµà;
    private static final String ¥à = "CL_00000689";
    
    static {
        ŠÂµà = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
    }
    
    public GuiCreateWorld(final GuiScreen p_i46320_1_) {
        this.Ó = "survival";
        this.Ø = true;
        this.HorizonCode_Horizon_È = "";
        this.Â = p_i46320_1_;
        this.á€ = "";
        this.Õ = I18n.HorizonCode_Horizon_È("selectWorld.newWorld", new Object[0]);
    }
    
    @Override
    public void Ý() {
        this.Ý.Â();
        this.Ø­áŒŠá.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiMenuButton(0, GuiCreateWorld.Çªà¢ / 2 - 155, GuiCreateWorld.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.create", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(1, GuiCreateWorld.Çªà¢ / 2 + 5, GuiCreateWorld.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.ÇŽÉ.add(this.Å = new GuiButton(2, GuiCreateWorld.Çªà¢ / 2 - 75, 115, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.gameMode", new Object[0])));
        this.ÇŽÉ.add(this.£à = new GuiButton(3, GuiCreateWorld.Çªà¢ / 2 - 75, 187, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.moreWorldOptions", new Object[0])));
        this.ÇŽÉ.add(this.µà = new GuiButton(4, GuiCreateWorld.Çªà¢ / 2 - 155, 100, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.mapFeatures", new Object[0])));
        this.µà.ˆà = false;
        this.ÇŽÉ.add(this.ˆà = new GuiButton(7, GuiCreateWorld.Çªà¢ / 2 + 5, 151, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.bonusItems", new Object[0])));
        this.ˆà.ˆà = false;
        this.ÇŽÉ.add(this.¥Æ = new GuiButton(5, GuiCreateWorld.Çªà¢ / 2 + 5, 100, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.mapType", new Object[0])));
        this.¥Æ.ˆà = false;
        this.ÇŽÉ.add(this.Ø­à = new GuiButton(6, GuiCreateWorld.Çªà¢ / 2 - 155, 151, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.allowCommands", new Object[0])));
        this.Ø­à.ˆà = false;
        this.ÇŽÉ.add(this.µÕ = new GuiButton(8, GuiCreateWorld.Çªà¢ / 2 + 5, 120, 150, 20, I18n.HorizonCode_Horizon_È("selectWorld.customizeType", new Object[0])));
        this.µÕ.ˆà = false;
        (this.Ý = new GuiFlatField(9, this.É, GuiCreateWorld.Çªà¢ / 2 - 100, 60, 200, 20)).Â(true);
        this.Ý.HorizonCode_Horizon_È(this.Õ);
        (this.Ø­áŒŠá = new GuiFlatField(10, this.É, GuiCreateWorld.Çªà¢ / 2 - 100, 60, 200, 20)).HorizonCode_Horizon_È(this.á€);
        this.HorizonCode_Horizon_È(this.£á);
        this.Ó();
        this.à();
    }
    
    private void Ó() {
        this.Âµá€ = this.Ý.Ý().trim();
        for (final char var4 : ChatAllowedCharacters.HorizonCode_Horizon_È) {
            this.Âµá€ = this.Âµá€.replace(var4, '_');
        }
        if (StringUtils.isEmpty((CharSequence)this.Âµá€)) {
            this.Âµá€ = "World";
        }
        this.Âµá€ = HorizonCode_Horizon_È(GuiCreateWorld.Ñ¢á.à(), this.Âµá€);
    }
    
    private void à() {
        this.Å.Å = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.gameMode", new Object[0])) + ": " + I18n.HorizonCode_Horizon_È("selectWorld.gameMode." + this.Ó, new Object[0]);
        this.Æ = I18n.HorizonCode_Horizon_È("selectWorld.gameMode." + this.Ó + ".line1", new Object[0]);
        this.áƒ = I18n.HorizonCode_Horizon_È("selectWorld.gameMode." + this.Ó + ".line2", new Object[0]);
        this.µà.Å = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.mapFeatures", new Object[0])) + " ";
        if (this.Ø) {
            this.µà.Å = String.valueOf(this.µà.Å) + I18n.HorizonCode_Horizon_È("options.on", new Object[0]);
        }
        else {
            this.µà.Å = String.valueOf(this.µà.Å) + I18n.HorizonCode_Horizon_È("options.off", new Object[0]);
        }
        this.ˆà.Å = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.bonusItems", new Object[0])) + " ";
        if (this.ÂµÈ && !this.á) {
            this.ˆà.Å = String.valueOf(this.ˆà.Å) + I18n.HorizonCode_Horizon_È("options.on", new Object[0]);
        }
        else {
            this.ˆà.Å = String.valueOf(this.ˆà.Å) + I18n.HorizonCode_Horizon_È("options.off", new Object[0]);
        }
        this.¥Æ.Å = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.mapType", new Object[0])) + " " + I18n.HorizonCode_Horizon_È(WorldType.HorizonCode_Horizon_È[this.à¢].Â(), new Object[0]);
        this.Ø­à.Å = String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.allowCommands", new Object[0])) + " ";
        if (this.áŒŠÆ && !this.á) {
            this.Ø­à.Å = String.valueOf(this.Ø­à.Å) + I18n.HorizonCode_Horizon_È("options.on", new Object[0]);
        }
        else {
            this.Ø­à.Å = String.valueOf(this.Ø­à.Å) + I18n.HorizonCode_Horizon_È("options.off", new Object[0]);
        }
    }
    
    public static String HorizonCode_Horizon_È(final ISaveFormat p_146317_0_, String p_146317_1_) {
        p_146317_1_ = p_146317_1_.replaceAll("[\\./\"]", "_");
        for (final String var5 : GuiCreateWorld.ŠÂµà) {
            if (p_146317_1_.equalsIgnoreCase(var5)) {
                p_146317_1_ = "_" + p_146317_1_ + "_";
            }
        }
        while (p_146317_0_.Ý(p_146317_1_) != null) {
            p_146317_1_ = String.valueOf(p_146317_1_) + "-";
        }
        return p_146317_1_;
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 1) {
                GuiCreateWorld.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            else if (button.£à == 0) {
                GuiCreateWorld.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                if (this.ˆÏ­) {
                    return;
                }
                this.ˆÏ­ = true;
                long var2 = new Random().nextLong();
                final String var3 = this.Ø­áŒŠá.Ý();
                if (!StringUtils.isEmpty((CharSequence)var3)) {
                    try {
                        final long var4 = Long.parseLong(var3);
                        if (var4 != 0L) {
                            var2 = var4;
                        }
                    }
                    catch (NumberFormatException var7) {
                        var2 = var3.hashCode();
                    }
                }
                final WorldSettings.HorizonCode_Horizon_È var5 = WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ó);
                final WorldSettings var6 = new WorldSettings(var2, var5, this.Ø, this.á, WorldType.HorizonCode_Horizon_È[this.à¢]);
                var6.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                if (this.ÂµÈ && !this.á) {
                    var6.HorizonCode_Horizon_È();
                }
                if (this.áŒŠÆ && !this.á) {
                    var6.Â();
                }
                GuiCreateWorld.Ñ¢á.HorizonCode_Horizon_È(this.Âµá€, this.Ý.Ý().trim(), var6);
            }
            else if (button.£à == 3) {
                this.áŒŠÆ();
            }
            else if (button.£à == 2) {
                if (this.Ó.equals("survival")) {
                    if (!this.áˆºÑ¢Õ) {
                        this.áŒŠÆ = false;
                    }
                    this.á = false;
                    this.Ó = "hardcore";
                    this.á = true;
                    this.Ø­à.µà = false;
                    this.ˆà.µà = false;
                    this.à();
                }
                else if (this.Ó.equals("hardcore")) {
                    if (!this.áˆºÑ¢Õ) {
                        this.áŒŠÆ = true;
                    }
                    this.á = false;
                    this.Ó = "creative";
                    this.à();
                    this.á = false;
                    this.Ø­à.µà = true;
                    this.ˆà.µà = true;
                }
                else {
                    if (!this.áˆºÑ¢Õ) {
                        this.áŒŠÆ = false;
                    }
                    this.Ó = "survival";
                    this.à();
                    this.Ø­à.µà = true;
                    this.ˆà.µà = true;
                    this.á = false;
                }
                this.à();
            }
            else if (button.£à == 4) {
                this.Ø = !this.Ø;
                this.à();
            }
            else if (button.£à == 7) {
                this.ÂµÈ = !this.ÂµÈ;
                this.à();
            }
            else if (button.£à == 5) {
                ++this.à¢;
                if (this.à¢ >= WorldType.HorizonCode_Horizon_È.length) {
                    this.à¢ = 0;
                }
                while (!this.Ø()) {
                    ++this.à¢;
                    if (this.à¢ >= WorldType.HorizonCode_Horizon_È.length) {
                        this.à¢ = 0;
                    }
                }
                this.HorizonCode_Horizon_È = "";
                this.à();
                this.HorizonCode_Horizon_È(this.£á);
            }
            else if (button.£à == 6) {
                this.áˆºÑ¢Õ = true;
                this.áŒŠÆ = !this.áŒŠÆ;
                this.à();
            }
            else if (button.£à == 8) {
                if (WorldType.HorizonCode_Horizon_È[this.à¢] == WorldType.Ø­áŒŠá) {
                    GuiCreateWorld.Ñ¢á.HorizonCode_Horizon_È(new GuiCreateFlatWorld(this, this.HorizonCode_Horizon_È));
                }
                else {
                    GuiCreateWorld.Ñ¢á.HorizonCode_Horizon_È(new GuiCustomizeWorldScreen(this, this.HorizonCode_Horizon_È));
                }
            }
        }
    }
    
    private boolean Ø() {
        final WorldType var1 = WorldType.HorizonCode_Horizon_È[this.à¢];
        return var1 != null && var1.Âµá€() && (var1 != WorldType.Ø || GuiScreen.£à());
    }
    
    private void áŒŠÆ() {
        this.HorizonCode_Horizon_È(!this.£á);
    }
    
    private void HorizonCode_Horizon_È(final boolean p_146316_1_) {
        this.£á = p_146316_1_;
        if (WorldType.HorizonCode_Horizon_È[this.à¢] == WorldType.Ø) {
            this.Å.ˆà = !this.£á;
            this.Å.µà = false;
            if (this.à == null) {
                this.à = this.Ó;
            }
            this.Ó = "spectator";
            this.µà.ˆà = false;
            this.ˆà.ˆà = false;
            this.¥Æ.ˆà = this.£á;
            this.Ø­à.ˆà = false;
            this.µÕ.ˆà = false;
        }
        else {
            this.Å.ˆà = !this.£á;
            this.Å.µà = true;
            if (this.à != null) {
                this.Ó = this.à;
                this.à = null;
            }
            this.µà.ˆà = (this.£á && WorldType.HorizonCode_Horizon_È[this.à¢] != WorldType.à);
            this.ˆà.ˆà = this.£á;
            this.¥Æ.ˆà = this.£á;
            this.Ø­à.ˆà = this.£á;
            this.µÕ.ˆà = (this.£á && (WorldType.HorizonCode_Horizon_È[this.à¢] == WorldType.Ø­áŒŠá || WorldType.HorizonCode_Horizon_È[this.à¢] == WorldType.à));
        }
        this.à();
        if (this.£á) {
            this.£à.Å = I18n.HorizonCode_Horizon_È("gui.done", new Object[0]);
        }
        else {
            this.£à.Å = I18n.HorizonCode_Horizon_È("selectWorld.moreWorldOptions", new Object[0]);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (this.Ý.ÂµÈ() && !this.£á) {
            this.Ý.HorizonCode_Horizon_È(typedChar, keyCode);
            this.Õ = this.Ý.Ý();
        }
        else if (this.Ø­áŒŠá.ÂµÈ() && this.£á) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(typedChar, keyCode);
            this.á€ = this.Ø­áŒŠá.Ý();
        }
        if (keyCode == 28 || keyCode == 156) {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
        this.ÇŽÉ.get(0).µà = (this.Ý.Ý().length() > 0);
        this.Ó();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        if (this.£á) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        }
        else {
            this.Ý.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("selectWorld.create", new Object[0]), GuiCreateWorld.Çªà¢ / 2, 20, -1);
        if (this.£á) {
            Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("selectWorld.enterSeed", new Object[0]), GuiCreateWorld.Çªà¢ / 2 - 100, 47, -6250336);
            Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("selectWorld.seedInfo", new Object[0]), GuiCreateWorld.Çªà¢ / 2 - 100, 85, -6250336);
            if (this.µà.ˆà) {
                Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("selectWorld.mapFeatures.info", new Object[0]), GuiCreateWorld.Çªà¢ / 2 - 150, 122, -6250336);
            }
            if (this.Ø­à.ˆà) {
                Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("selectWorld.allowCommands.info", new Object[0]), GuiCreateWorld.Çªà¢ / 2 - 150, 172, -6250336);
            }
            this.Ø­áŒŠá.HorizonCode_Horizon_È();
            if (WorldType.HorizonCode_Horizon_È[this.à¢].Ø()) {
                this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È(WorldType.HorizonCode_Horizon_È[this.à¢].Ý(), new Object[0]), this.¥Æ.ˆÏ­ + 2, this.¥Æ.£á + 22, this.¥Æ.Ø­áŒŠá(), 10526880);
            }
        }
        else {
            Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("selectWorld.enterName", new Object[0]), GuiCreateWorld.Çªà¢ / 2 - 100, 47, -6250336);
            Gui_1808253012.Â(this.É, String.valueOf(I18n.HorizonCode_Horizon_È("selectWorld.resultFolder", new Object[0])) + " " + this.Âµá€, GuiCreateWorld.Çªà¢ / 2 - 100, 85, -6250336);
            this.Ý.HorizonCode_Horizon_È();
            Gui_1808253012.Â(this.É, this.Æ, GuiCreateWorld.Çªà¢ / 2 - 100, 137, -6250336);
            Gui_1808253012.Â(this.É, this.áƒ, GuiCreateWorld.Çªà¢ / 2 - 100, 149, -6250336);
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    public void HorizonCode_Horizon_È(final WorldInfo p_146318_1_) {
        this.Õ = I18n.HorizonCode_Horizon_È("selectWorld.newWorld.copyOf", p_146318_1_.áˆºÑ¢Õ());
        this.á€ = new StringBuilder(String.valueOf(p_146318_1_.Â())).toString();
        this.à¢ = p_146318_1_.Ø­à().à();
        this.HorizonCode_Horizon_È = p_146318_1_.Ñ¢á();
        this.Ø = p_146318_1_.ˆà();
        this.áŒŠÆ = p_146318_1_.µÕ();
        if (p_146318_1_.¥Æ()) {
            this.Ó = "hardcore";
        }
        else if (p_146318_1_.µà().Âµá€()) {
            this.Ó = "survival";
        }
        else if (p_146318_1_.µà().Ø­áŒŠá()) {
            this.Ó = "creative";
        }
    }
}
