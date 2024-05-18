package net.minecraft.src;

import net.minecraft.client.*;
import java.io.*;
import java.net.*;
import org.lwjgl.*;

public class GuiTexturePacks extends GuiScreen
{
    protected GuiScreen guiScreen;
    private int refreshTimer;
    private String fileLocation;
    private GuiTexturePackSlot guiTexturePackSlot;
    private GameSettings field_96146_n;
    
    public GuiTexturePacks(final GuiScreen par1, final GameSettings par2) {
        this.refreshTimer = -1;
        this.fileLocation = "";
        this.guiScreen = par1;
        this.field_96146_n = par2;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(new GuiSmallButton(5, this.width / 2 - 154, this.height - 48, var1.translateKey("texturePack.openFolder")));
        this.buttonList.add(new GuiSmallButton(6, this.width / 2 + 4, this.height - 48, var1.translateKey("gui.done")));
        this.mc.texturePackList.updateAvaliableTexturePacks();
        this.fileLocation = new File(Minecraft.getMinecraftDir(), "texturepacks").getAbsolutePath();
        (this.guiTexturePackSlot = new GuiTexturePackSlot(this)).registerScrollButtons(this.buttonList, 7, 8);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 5) {
                Label_0112: {
                    if (Minecraft.getOs() == EnumOS.MACOS) {
                        try {
                            this.mc.getLogAgent().logInfo(this.fileLocation);
                            Runtime.getRuntime().exec(new String[] { "/usr/bin/open", this.fileLocation });
                            return;
                        }
                        catch (IOException var7) {
                            var7.printStackTrace();
                            break Label_0112;
                        }
                    }
                    if (Minecraft.getOs() == EnumOS.WINDOWS) {
                        final String var8 = String.format("cmd.exe /C start \"Open file\" \"%s\"", this.fileLocation);
                        try {
                            Runtime.getRuntime().exec(var8);
                            return;
                        }
                        catch (IOException var9) {
                            var9.printStackTrace();
                        }
                    }
                }
                boolean var10 = false;
                try {
                    final Class var11 = Class.forName("java.awt.Desktop");
                    final Object var12 = var11.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
                    var11.getMethod("browse", URI.class).invoke(var12, new File(Minecraft.getMinecraftDir(), "texturepacks").toURI());
                }
                catch (Throwable var13) {
                    var13.printStackTrace();
                    var10 = true;
                }
                if (var10) {
                    this.mc.getLogAgent().logInfo("Opening via system class!");
                    Sys.openURL("file://" + this.fileLocation);
                }
            }
            else if (par1GuiButton.id == 6) {
                this.mc.displayGuiScreen(this.guiScreen);
            }
            else {
                this.guiTexturePackSlot.actionPerformed(par1GuiButton);
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    protected void mouseMovedOrUp(final int par1, final int par2, final int par3) {
        super.mouseMovedOrUp(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.guiTexturePackSlot.drawScreen(par1, par2, par3);
        if (this.refreshTimer <= 0) {
            this.mc.texturePackList.updateAvaliableTexturePacks();
            this.refreshTimer += 20;
        }
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("texturePack.title"), this.width / 2, 16, 16777215);
        this.drawCenteredString(this.fontRenderer, var4.translateKey("texturePack.folderInfo"), this.width / 2 - 77, this.height - 26, 8421504);
        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        --this.refreshTimer;
    }
    
    static Minecraft func_73950_a(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73955_b(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73958_c(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73951_d(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73952_e(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73962_f(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73959_g(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73957_h(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73956_i(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73953_j(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_73961_k(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_96143_l(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static Minecraft func_96142_m(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.mc;
    }
    
    static FontRenderer func_73954_n(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.fontRenderer;
    }
    
    static FontRenderer func_96145_o(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.fontRenderer;
    }
    
    static FontRenderer func_96144_p(final GuiTexturePacks par0GuiTexturePacks) {
        return par0GuiTexturePacks.fontRenderer;
    }
}
