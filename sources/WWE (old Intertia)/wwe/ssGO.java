package wwe;

import wwe.utils.*;
import net.minecraft.util.text.*;
import java.util.*;
import java.awt.*;
import java.net.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;

public class ssGO extends GuiScreen
{
    public static boolean jMLq;
    
    public ssGO() {
        super();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawBackground(1);
        Wrapper.fontRenderer.drawString("WWE currently has " + vlwv.hNxW.ijZl.size() + " modules", this.width / 2 - Wrapper.fontRenderer.getStringWidth("WWE currently has " + vlwv.hNxW.ijZl.size() + " modules") / 2, this.height / 4 - this.height / 8, -1);
        Wrapper.fontRenderer.drawString("WWE Mod's website, wweclient.com", this.width / 2 - Wrapper.fontRenderer.getStringWidth("WWE Mod's website, wweclient.com") / 2, this.height / 4 - this.height / 8 + 10, -1);
        Wrapper.fontRenderer.drawStringWithShadow("Join discord for help", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Join discord for help") / 2), (float)(this.height / 4 + 144), -1);
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73866_w_() {
        final int n = this.width / 2 - 205;
        final int n2 = this.width / 2 + 5;
        this.buttonList.add(new GuiButton(1, n, this.height / 4, "Alt Manager"));
        this.buttonList.add(new GuiButton(2, n, this.height / 4 + 25, "Discord"));
        this.buttonList.add(new GuiButton(3, n, this.height / 4 + 50, "Youtube"));
        this.buttonList.add(new GuiButton(5, n, this.height / 4 + 75, "Reddit"));
        this.buttonList.add(new GuiButton(6, n2, this.height / 4, "Credits"));
        this.buttonList.add(new GuiButton(7, n2, this.height / 4 + 25, "Color Manager"));
        this.buttonList.add(new GuiButton(8, n2, this.height / 4 + 50, "Add Custom Background Image"));
        final List buttonList = this.buttonList;
        final int n3 = 9;
        final int n4 = n2;
        final int n5 = this.height / 4 + 75;
        final StringBuilder append = new StringBuilder().append("Main Menu Picture Cycle ");
        String s;
        if (qdlI.bppf) {
            s = TextFormatting.GREEN + "True";
            if (ssGO.\u2007\u200b) {
                throw null;
            }
        }
        else {
            s = TextFormatting.RED + "False";
        }
        buttonList.add(new GuiButton(n3, n4, n5, append.append(s).toString()));
        this.buttonList.add(new GuiButton(21, this.width / 2 - 100, this.height / 4 + 175, "Back"));
        this.updateScreen();
    }
    
    protected void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)wGez.PrrU);
        }
        if (guiButton.id == 2 && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/QWcrZwP"));
                if (ssGO.\u2007\u200b) {
                    throw null;
                }
            }
            catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
        if (guiButton.id == 3 && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/channel/UCJGCNPEjvsCn0FKw3zso0TA"));
                if (ssGO.\u2007\u200b) {
                    throw null;
                }
            }
            catch (URISyntaxException ex2) {
                ex2.printStackTrace();
            }
        }
        if (guiButton.id == 5 && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://reddit.com/r/WWEClient"));
                if (ssGO.\u2007\u200b) {
                    throw null;
                }
            }
            catch (URISyntaxException ex3) {
                ex3.printStackTrace();
            }
        }
        if (guiButton.id == 6) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new eSDC(this));
        }
        if (guiButton.id == 7) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new rHwk());
        }
        if (guiButton.id == 8) {
            this.mc.displayGuiScreen((GuiScreen)new gbIL(this, Wrapper.ypJK().XeLH, false, ssGO::uvYE, new String[] { "png", "jpg", "jpeg" }));
        }
        if (guiButton.id == 9) {
            boolean bppf;
            if (!qdlI.bppf) {
                bppf = true;
                if (ssGO.\u2007\u200b) {
                    throw null;
                }
            }
            else {
                bppf = false;
            }
            qdlI.bppf = bppf;
            Wrapper.ypJK().OsgT();
            this.buttonList.clear();
            this.initGui();
        }
        if (guiButton.id == 21) {
            final Minecraft mc = Wrapper.mc;
            Object o;
            if (ssGO.jMLq) {
                o = new qdlI();
                if (ssGO.\u2007\u200b) {
                    throw null;
                }
            }
            else {
                o = new GuiMainMenu();
            }
            mc.displayGuiScreen((GuiScreen)o);
        }
    }
    
    public void func_73869_a(final char c, final int n) {
        if (n == 1) {
            final Minecraft mc = Wrapper.mc;
            Object o;
            if (ssGO.jMLq) {
                o = new qdlI();
                if (ssGO.\u2007\u200b) {
                    throw null;
                }
            }
            else {
                o = new GuiMainMenu();
            }
            mc.displayGuiScreen((GuiScreen)o);
        }
    }
    
    private static void uvYE(final File srcFile) {
        if (srcFile != null) {
            try {
                FileUtils.copyFile(srcFile, new File(Wrapper.ypJK().XeLH + File.separator + "Custom Image.png"));
                if (ssGO.\u2007\u200b) {
                    throw null;
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            final ResourceLocation mpCo = FIwW.MPCo(new File(Wrapper.ypJK().XeLH + File.separator + "Custom Image.png"));
            if (mpCo != null) {
                if (qdlI.JTTe.size() > qdlI.GHWo) {
                    qdlI.JTTe.set(qdlI.JTTe.size() - 1, mpCo);
                    if (ssGO.\u2007\u200b) {
                        throw null;
                    }
                }
                else {
                    qdlI.JTTe.add(mpCo);
                }
                qdlI.kbXN = qdlI.JTTe.size() - 1;
                Wrapper.ypJK().OsgT();
            }
        }
    }
    
    static {
        ssGO.jMLq = false;
    }
}
