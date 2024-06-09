package HORIZON-6-0-SKIDPROTECTION;

import java.io.File;
import java.util.Collections;
import org.lwjgl.Sys;
import java.net.URI;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class GuiScreenResourcePacks extends GuiScreen
{
    private static final Logger HorizonCode_Horizon_È;
    private GuiScreen Â;
    private List Ý;
    private List Ø­áŒŠá;
    private GuiResourcePackAvailable Âµá€;
    private GuiResourcePackSelected Ó;
    private boolean à;
    private static final String Ø = "CL_00000820";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public GuiScreenResourcePacks(final GuiScreen p_i45050_1_) {
        this.à = false;
        this.Â = p_i45050_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiOptionButton(2, GuiScreenResourcePacks.Çªà¢ / 2 - 154, GuiScreenResourcePacks.Ê - 48, I18n.HorizonCode_Horizon_È("resourcePack.openFolder", new Object[0])));
        this.ÇŽÉ.add(new GuiOptionButton(1, GuiScreenResourcePacks.Çªà¢ / 2 + 4, GuiScreenResourcePacks.Ê - 48, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Lists.newArrayList();
        final ResourcePackRepository var1 = GuiScreenResourcePacks.Ñ¢á.Ç();
        var1.HorizonCode_Horizon_È();
        final ArrayList var2 = Lists.newArrayList((Iterable)var1.Â());
        var2.removeAll(var1.Ý());
        for (final ResourcePackRepository.HorizonCode_Horizon_È var4 : var2) {
            this.Ý.add(new ResourcePackListEntryFound(this, var4));
        }
        for (final ResourcePackRepository.HorizonCode_Horizon_È var4 : Lists.reverse(var1.Ý())) {
            this.Ø­áŒŠá.add(new ResourcePackListEntryFound(this, var4));
        }
        this.Ø­áŒŠá.add(new ResourcePackListEntryDefault(this));
        (this.Âµá€ = new GuiResourcePackAvailable(GuiScreenResourcePacks.Ñ¢á, 200, GuiScreenResourcePacks.Ê, this.Ý)).à(GuiScreenResourcePacks.Çªà¢ / 2 - 4 - 200);
        this.Âµá€.Ø­áŒŠá(7, 8);
        (this.Ó = new GuiResourcePackSelected(GuiScreenResourcePacks.Ñ¢á, 200, GuiScreenResourcePacks.Ê, this.Ø­áŒŠá)).à(GuiScreenResourcePacks.Çªà¢ / 2 + 4);
        this.Ó.Ø­áŒŠá(7, 8);
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Ó.Ø();
        this.Âµá€.Ø();
    }
    
    public boolean HorizonCode_Horizon_È(final ResourcePackListEntry p_146961_1_) {
        return this.Ø­áŒŠá.contains(p_146961_1_);
    }
    
    public List Â(final ResourcePackListEntry p_146962_1_) {
        return this.HorizonCode_Horizon_È(p_146962_1_) ? this.Ø­áŒŠá : this.Ý;
    }
    
    public List Ó() {
        return this.Ý;
    }
    
    public List à() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 2) {
                final File var2 = GuiScreenResourcePacks.Ñ¢á.Ç().Ø­áŒŠá();
                final String var3 = var2.getAbsolutePath();
                Label_0134: {
                    if (Util_1252169911.HorizonCode_Horizon_È() == Util_1252169911.HorizonCode_Horizon_È.Ø­áŒŠá) {
                        try {
                            GuiScreenResourcePacks.HorizonCode_Horizon_È.info(var3);
                            Runtime.getRuntime().exec(new String[] { "/usr/bin/open", var3 });
                            return;
                        }
                        catch (IOException var4) {
                            GuiScreenResourcePacks.HorizonCode_Horizon_È.error("Couldn't open file", (Throwable)var4);
                            break Label_0134;
                        }
                    }
                    if (Util_1252169911.HorizonCode_Horizon_È() == Util_1252169911.HorizonCode_Horizon_È.Ý) {
                        final String var5 = String.format("cmd.exe /C start \"Open file\" \"%s\"", var3);
                        try {
                            Runtime.getRuntime().exec(var5);
                            return;
                        }
                        catch (IOException var6) {
                            GuiScreenResourcePacks.HorizonCode_Horizon_È.error("Couldn't open file", (Throwable)var6);
                        }
                    }
                }
                boolean var7 = false;
                try {
                    final Class var8 = Class.forName("java.awt.Desktop");
                    final Object var9 = var8.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
                    var8.getMethod("browse", URI.class).invoke(var9, var2.toURI());
                }
                catch (Throwable var10) {
                    GuiScreenResourcePacks.HorizonCode_Horizon_È.error("Couldn't open link", var10);
                    var7 = true;
                }
                if (var7) {
                    GuiScreenResourcePacks.HorizonCode_Horizon_È.info("Opening via system class!");
                    Sys.openURL("file://" + var3);
                }
            }
            else if (button.£à == 1) {
                if (this.à) {
                    final ArrayList var11 = Lists.newArrayList();
                    for (final ResourcePackListEntry var13 : this.Ø­áŒŠá) {
                        if (var13 instanceof ResourcePackListEntryFound) {
                            var11.add(((ResourcePackListEntryFound)var13).áŒŠÆ());
                        }
                    }
                    Collections.reverse(var11);
                    GuiScreenResourcePacks.Ñ¢á.Ç().HorizonCode_Horizon_È(var11);
                    GuiScreenResourcePacks.Ñ¢á.ŠÄ.ÇŽá.clear();
                    for (final ResourcePackRepository.HorizonCode_Horizon_È var14 : var11) {
                        GuiScreenResourcePacks.Ñ¢á.ŠÄ.ÇŽá.add(var14.Ø­áŒŠá());
                    }
                    GuiScreenResourcePacks.Ñ¢á.ŠÄ.Â();
                    GuiScreenResourcePacks.Ñ¢á.Ó();
                }
                GuiScreenResourcePacks.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Âµá€.Â(mouseX, mouseY, mouseButton);
        this.Ó.Â(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void Â(final int mouseX, final int mouseY, final int state) {
        super.Â(mouseX, mouseY, state);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.Ý(0);
        this.Âµá€.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.Ó.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("resourcePack.title", new Object[0]), GuiScreenResourcePacks.Çªà¢ / 2, 16, 16777215);
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("resourcePack.folderInfo", new Object[0]), GuiScreenResourcePacks.Çªà¢ / 2 - 77, GuiScreenResourcePacks.Ê - 26, 8421504);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiScreenResourcePacks.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    public void Ø() {
        this.à = true;
    }
}
