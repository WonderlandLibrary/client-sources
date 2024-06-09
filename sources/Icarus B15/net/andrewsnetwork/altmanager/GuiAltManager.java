// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.altmanager;

import java.io.IOException;
import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import org.lwjgl.input.Mouse;
import java.util.Random;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.utilities.Alt;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltManager extends GuiScreen
{
    private static Minecraft mc;
    private GuiButton login;
    private GuiButton remove;
    private GuiButton rename;
    private AltLoginThread loginThread;
    private int offset;
    public Alt selectedAlt;
    private String status;
    
    static {
        GuiAltManager.mc = Minecraft.getMinecraft();
    }
    
    public GuiAltManager() {
        this.selectedAlt = null;
        this.status = "§7Waiting...";
        Icarus.getAltManager().getAlts().clear();
        Icarus.getFileManager().getFileByName("alts").loadFile();
    }
    
    public void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                if (this.loginThread == null) {
                    GuiAltManager.mc.displayGuiScreen(null);
                    break;
                }
                if (!this.loginThread.getStatus().equals("§eLogging in...") && !this.loginThread.getStatus().equals("§cDo not hit back! §eLogging in...")) {
                    GuiAltManager.mc.displayGuiScreen(null);
                    break;
                }
                this.loginThread.setStatus("§cDo not hit back! §eLogging in...");
                break;
            }
            case 1: {
                final String user = this.selectedAlt.getUsername();
                final String pass = this.selectedAlt.getPassword();
                (this.loginThread = new AltLoginThread(user, pass)).start();
                break;
            }
            case 2: {
                if (this.loginThread != null) {
                    this.loginThread = null;
                }
                Icarus.getAltManager().getAlts().remove(this.selectedAlt);
                this.status = "§aRemoved.";
                this.selectedAlt = null;
                Icarus.getFileManager().getFileByName("alts").saveFile();
                break;
            }
            case 3: {
                GuiAltManager.mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            }
            case 4: {
                GuiAltManager.mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            }
            case 5: {
                final Alt randomAlt = Icarus.getAltManager().getAlts().get(new Random().nextInt(Icarus.getAltManager().getAlts().size()));
                final String user2 = randomAlt.getUsername();
                final String pass2 = randomAlt.getPassword();
                (this.loginThread = new AltLoginThread(user2, pass2)).start();
                break;
            }
            case 6: {
                GuiAltManager.mc.displayGuiScreen(new GuiRenameAlt(this));
                break;
            }
            case 7: {
                final Alt lastAlt = Icarus.getAltManager().getLastAlt();
                if (lastAlt != null) {
                    final String user3 = lastAlt.getUsername();
                    final String pass3 = lastAlt.getPassword();
                    (this.loginThread = new AltLoginThread(user3, pass3)).start();
                    break;
                }
                if (this.loginThread == null) {
                    this.status = "§cThere is no last used alt!";
                    break;
                }
                this.loginThread.setStatus("§cThere is no last used alt!");
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        if (Mouse.hasWheel()) {
            final int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
            else if (wheel > 0) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
        this.drawDefaultBackground();
        this.drawString(this.fontRendererObj, GuiAltManager.mc.session.getUsername(), 10, 10, -7829368);
        this.drawCenteredString(this.fontRendererObj, "Account Manager - " + Icarus.getAltManager().getAlts().size() + " alts", this.width / 2, 10, -1);
        this.drawCenteredString(this.fontRendererObj, (this.loginThread == null) ? this.status : this.loginThread.getStatus(), this.width / 2, 20, -1);
        RenderHelper.drawBorderedRect(50.0f, 33.0f, this.width - 50, this.height - 50, 1.0f, -16777216, Integer.MIN_VALUE);
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, this.width, this.height - 50);
        GL11.glEnable(3089);
        int y = 38;
        for (final Alt alt : Icarus.getAltManager().getAlts()) {
            if (!this.isAltInArea(y)) {
                continue;
            }
            String name;
            if (alt.getMask().equals("")) {
                name = alt.getUsername();
            }
            else {
                name = alt.getMask();
            }
            String pass;
            if (alt.getPassword().equals("")) {
                pass = "§cCracked";
            }
            else {
                pass = alt.getPassword().replaceAll(".", "*");
            }
            if (alt == this.selectedAlt) {
                if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
                    RenderHelper.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20, 1.0f, -16777216, -2142943931);
                }
                else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
                    RenderHelper.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20, 1.0f, -16777216, -2142088622);
                }
                else {
                    RenderHelper.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20, 1.0f, -16777216, -2144259791);
                }
            }
            else if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
                RenderHelper.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20, 1.0f, -16777216, -2146101995);
            }
            else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
                RenderHelper.drawBorderedRect(52.0f, y - this.offset - 4, this.width - 52, y - this.offset + 20, 1.0f, -16777216, -2145180893);
            }
            this.drawCenteredString(this.fontRendererObj, name, this.width / 2, y - this.offset, -1);
            this.drawCenteredString(this.fontRendererObj, pass, this.width / 2, y - this.offset + 10, 5592405);
            y += 26;
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
        if (this.selectedAlt == null) {
            this.login.enabled = false;
            this.remove.enabled = false;
            this.rename.enabled = false;
        }
        else {
            this.login.enabled = true;
            this.remove.enabled = true;
            this.rename.enabled = true;
        }
        if (Keyboard.isKeyDown(200)) {
            this.offset -= 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
        else if (Keyboard.isKeyDown(208)) {
            this.offset += 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 24, 75, 20, "Cancel"));
        this.buttonList.add(this.login = new GuiButton(1, this.width / 2 - 154, this.height - 48, 100, 20, "Login"));
        this.buttonList.add(this.remove = new GuiButton(2, this.width / 2 - 74, this.height - 24, 70, 20, "Remove"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 48, 100, 20, "Add"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 48, 100, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 154, this.height - 24, 70, 20, "Random"));
        this.buttonList.add(this.rename = new GuiButton(6, this.width / 2 + 4, this.height - 24, 70, 20, "Rename"));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 208, this.height - 24, 50, 20, "Last Alt"));
        this.login.enabled = false;
        this.remove.enabled = false;
        this.rename.enabled = false;
    }
    
    private boolean isAltInArea(final int y) {
        return y - this.offset <= this.height - 50;
    }
    
    private boolean isMouseOverAlt(final int x, final int y, final int y1) {
        return x >= 52 && y >= y1 - 4 && x <= this.width - 52 && y <= y1 + 20 && x >= 0 && y >= 33 && x <= this.width && y <= this.height - 50;
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        if (this.offset < 0) {
            this.offset = 0;
        }
        int y = 38 - this.offset;
        for (final Alt alt : Icarus.getAltManager().getAlts()) {
            if (this.isMouseOverAlt(par1, par2, y)) {
                if (alt == this.selectedAlt) {
                    this.actionPerformed((GuiButton) this.buttonList.get(1));
                    return;
                }
                this.selectedAlt = alt;
            }
            y += 26;
        }
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final int factor = RenderHelper.getScaledRes().getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((RenderHelper.getScaledRes().getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
}
