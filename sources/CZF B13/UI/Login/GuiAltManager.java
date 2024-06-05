/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package gq.vapu.czfclient.UI.Login;

import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.FileManager;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.RamdonUtil;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Random;

public class GuiAltManager extends GuiScreen {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public Alt selectedAlt = null;
    private GuiButton login;
    private GuiButton remove;
    private GuiButton rename;
    private GuiButton lastalt;
    private AltLoginThread loginThread;
    private int offset;
    private String status = "\u00a7eWaiting...";
    private String username;
    private String val = "";

    public GuiAltManager() {
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                if (this.loginThread == null) {
                    mc.displayGuiScreen(null);
                    break;
                }
                if (!this.loginThread.getStatus().equals("Logging in...")
                        && !this.loginThread.getStatus().equals("Do not hit back! Logging in...")) {
                    mc.displayGuiScreen(null);
                    break;
                }
                this.loginThread.setStatus("Do not hit back! Logging in...");
                break;
            }
            case 1: {
                String user = this.selectedAlt.getUsername();
                String pass = this.selectedAlt.getPassword();
                this.loginThread = new AltLoginThread(user, pass);
                this.loginThread.start();
                break;
            }
            case 2: {
                if (this.loginThread != null) {
                    this.loginThread = null;
                }
                Client.instance.getAltManager();
                AltManager.getAlts().remove(this.selectedAlt);
                this.status = "\u00a7cRemoved.";
                this.selectedAlt = null;
                FileManager.saveAlts();
                break;
            }
            case 3: {
                mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            }
            case 4: {
                mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            }
            case 5: {
                RamdonUtil test = new RamdonUtil();
                this.username = "Czf" + test.getStringRandom(8);
                mc.session = new Session(this.username, "", "", "mojang");
                break;
            }
            case 6: {
                mc.displayGuiScreen(new GuiRenameAlt(this));
                break;
            }
            case 7: {
                Client.instance.getAltManager();
                Alt lastAlt = AltManager.lastAlt;
                if (lastAlt == null) {
                    if (this.loginThread == null) {
                        this.status = "?cThere is no last used alt!";
                        break;
                    }
                    this.loginThread.setStatus("?cThere is no last used alt!");
                    break;
                }
                String user2 = lastAlt.getUsername();
                String pass2 = lastAlt.getPassword();
                this.loginThread = new AltLoginThread(user2, pass2);
                this.loginThread.start();
            }
        }
    }

    public String getStringRandom(int length) {
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            } else if (wheel > 0) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
        this.drawDefaultBackground();
        Helper.mc.fontRendererObj.drawStringWithShadow(GuiAltManager.mc.session.getUsername(), 10.0f, 10.0f, -7829368);
        Client.instance.getAltManager();
        Helper.mc.fontRendererObj.drawCenteredString("Account Manager - " + AltManager.getAlts().size() + " alts",
                width / 2, 10, -1);
        Helper.mc.fontRendererObj.drawCenteredString(
                this.loginThread == null ? this.status : this.loginThread.getStatus(), width / 2, 20, -1);
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, width, height - 50);
        GL11.glEnable(3089);
        int y = 38;
        Client.instance.getAltManager();
        for (Alt alt : AltManager.getAlts()) {
            if (!this.isAltInArea(y))
                continue;
            String name = alt.getMask().equals("") ? alt.getUsername() : alt.getMask();
            String pass = alt.getPassword().equals("") ? "\u00a7cCracked" : alt.getPassword().replaceAll(".", "*");
            if (alt == this.selectedAlt) {
                if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
                    RenderUtil.drawBorderedRect(52.0f, y - this.offset - 4, width - 52, y - this.offset + 20, 1.0f,
                            -16777216, -2142943931);
                } else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
                    RenderUtil.drawBorderedRect(52.0f, y - this.offset - 4, width - 52, y - this.offset + 20, 1.0f,
                            -16777216, -2142088622);
                } else {
                    RenderUtil.drawBorderedRect(52.0f, y - this.offset - 4, width - 52, y - this.offset + 20, 1.0f,
                            -16777216, -2144259791);
                }
            } else if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
                RenderUtil.drawBorderedRect(52.0f, y - this.offset - 4, width - 52, y - this.offset + 20, 1.0f,
                        -16777216, -2146101995);
            } else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
                RenderUtil.drawBorderedRect(52.0f, y - this.offset - 4, width - 52, y - this.offset + 20, 1.0f,
                        -16777216, -2145180893);
            }
            Helper.mc.fontRendererObj.drawCenteredString(name, width / 2, y - this.offset, -1);
            Helper.mc.fontRendererObj.drawCenteredString(pass, width / 2, y - this.offset + 10, 5592405);
            y += 26;
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
        if (this.selectedAlt == null) {
            this.login.enabled = false;
            this.remove.enabled = false;
            this.rename.enabled = false;
        } else {
            this.login.enabled = true;
            this.remove.enabled = true;
            this.rename.enabled = true;
        }
        if (Keyboard.isKeyDown(200)) {
            this.offset -= 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        } else if (Keyboard.isKeyDown(208)) {
            this.offset += 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, width / 2 + 4 + 76, height - 24, 75, 20, "Cancel"));
        this.login = new GuiButton(1, width / 2 - 154, height - 48, 70, 20, "Login");
        this.buttonList.add(this.login);
        this.remove = new GuiButton(2, width / 2 - 74, height - 24, 70, 20, "Remove");
        this.buttonList.add(this.remove);
        this.buttonList.add(new GuiButton(3, width / 2 + 4 + 76, height - 48, 75, 20, "Add"));
        this.buttonList.add(new GuiButton(4, width / 2 - 74, height - 48, 70, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(5, width / 2 + 4, height - 48, 70, 20, "Random Login"));
        this.rename = new GuiButton(6, width / 2 + 4, height - 24, 70, 20, "Edit");
        this.buttonList.add(this.rename);
        this.lastalt = new GuiButton(7, width / 2 - 154, height - 24, 70, 20, "Last Alt");
        this.buttonList.add(this.lastalt);
        this.login.enabled = false;
        this.remove.enabled = false;
        this.rename.enabled = false;
    }

    private boolean isAltInArea(int y) {
        return y - this.offset <= height - 50;
    }

    private boolean isMouseOverAlt(int x, int y, int y1) {
        return x >= 52 && y >= y1 - 4 && x <= width - 52 && y <= y1 + 20 && x >= 0 && y >= 33 && x <= width
                && y <= height - 50;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (this.offset < 0) {
            this.offset = 0;
        }
        int y = 38 - this.offset;
        Client.instance.getAltManager();
        for (Alt alt : AltManager.getAlts()) {
            if (this.isMouseOverAlt(par1, par2, y)) {
                if (alt == this.selectedAlt) {
                    this.actionPerformed(this.buttonList.get(1));
                    return;
                }
                this.selectedAlt = alt;
            }
            y += 26;
        }
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareScissorBox(float x, float y, float x2, float y2) {
        int factor = new ScaledResolution(mc).getScaleFactor();
        GL11.glScissor((int) (x * (float) factor),
                (int) (((float) new ScaledResolution(mc).getScaledHeight() - y2) * (float) factor),
                (int) ((x2 - x) * (float) factor), (int) ((y2 - y) * (float) factor));
    }

    public void renderBackground(int par1, int par2) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        this.drawDefaultBackground();
        Tessellator var3 = Tessellator.instance;
        var3.getWorldRenderer().startDrawingQuads();
        var3.getWorldRenderer().addVertexWithUV(0.0, par2, -90.0, 0.0, 1.0);
        var3.getWorldRenderer().addVertexWithUV(par1, par2, -90.0, 1.0, 1.0);
        var3.getWorldRenderer().addVertexWithUV(par1, 0.0, -90.0, 1.0, 0.0);
        var3.getWorldRenderer().addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
