package ClassSub;

import net.minecraft.util.*;
import cn.Hanabi.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import java.awt.*;
import java.util.*;

public class Class7 extends GuiScreen
{
    private GuiButton login;
    private GuiButton remove;
    private GuiButton rename;
    private Class82 loginThread;
    private int offset;
    public Class309 selectedAlt;
    private String status;
    private ResourceLocation background;
    private GuiTextField seatchField;
    
    
    public Class7() {
        this.background = new ResourceLocation("textures/mainmenubackground.png");
        this.selectedAlt = null;
        this.status = EnumChatFormatting.GRAY + "Idle...";
    }
    
    public void actionPerformed(final GuiButton guiButton) {
        switch (guiButton.id) {
            case 1: {
                (this.loginThread = new Class82(this.selectedAlt)).start();
                Hanabi.INSTANCE.altFileMgr.saveFiles();
                break;
            }
            case 2: {
                if (this.loginThread != null) {
                    this.loginThread = null;
                }
                Class206.registry.remove(this.selectedAlt);
                this.status = "§aRemoved.";
                try {
                    Hanabi.INSTANCE.altFileMgr.getFile(Class85.class).saveFile();
                }
                catch (Exception ex2) {}
                this.selectedAlt = null;
                break;
            }
            case 3: {
                this.mc.displayGuiScreen((GuiScreen)new Class24(this));
                break;
            }
            case 4: {
                this.mc.displayGuiScreen((GuiScreen)new Class179(this));
                break;
            }
            case 5: {
                final ArrayList<Class309> registry = Class206.registry;
                final Random random = new Random();
                if (registry.size() > 1) {
                    (this.loginThread = new Class82(registry.get(random.nextInt(Class206.registry.size())))).start();
                    break;
                }
                this.status = EnumChatFormatting.RED + "There's no any alts!";
                break;
            }
            case 6: {
                this.mc.displayGuiScreen((GuiScreen)new Class189(this));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen((GuiScreen)null);
                break;
            }
            case 8: {
                Class206.registry.clear();
                try {
                    Hanabi.INSTANCE.altFileMgr.getFile(Class85.class).loadFile();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.status = "§bReloaded!";
                break;
            }
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        if (Mouse.hasWheel()) {
            final int dWheel = Mouse.getDWheel();
            if (dWheel < 0) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
            else if (dWheel > 0) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        scaledResolution.getScaledWidth();
        scaledResolution.getScaledHeight();
        GlStateManager.bindTexture(0);
        Class246.drawRect(0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), getColor(0, 50));
        this.drawString(this.fontRendererObj, this.mc.getSession().getUsername(), 10, 10, 14540253);
        this.drawCenteredString(this.fontRendererObj, "Account Manager - " + Class206.registry.size() + " alts", this.width / 2, 10, -1);
        this.drawCenteredString(this.fontRendererObj, (this.loginThread == null) ? this.status : this.loginThread.getStatus(), this.width / 2, 20, -1);
        Class246.drawOutlinedRect(50.0f, 33.0f, this.width - 50, this.height - 50, 1.0f, getColor(225, 50), getColor(160, 150));
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, this.width, this.height - 50);
        GL11.glEnable(3089);
        int n4 = 38;
        for (final Class309 class309 : this.getAlts()) {
            if (this.isAltInArea(n4)) {
                String s;
                if (class309.getMask().equals("")) {
                    s = class309.getUsername();
                }
                else {
                    s = class309.getMask();
                }
                String replaceAll;
                if (class309.getPassword().equals("")) {
                    replaceAll = "§cCracked";
                }
                else {
                    replaceAll = class309.getPassword().replaceAll(".", "*");
                }
                if (class309 == this.selectedAlt) {
                    if (this.isMouseOverAlt(n, n2, n4 - this.offset) && Mouse.isButtonDown(0)) {
                        Class246.drawOutlinedRect(52.0f, n4 - this.offset - 4, this.width - 52, n4 - this.offset + 20, 1.0f, getColor(145, 50), -2142943931);
                    }
                    else if (this.isMouseOverAlt(n, n2, n4 - this.offset)) {
                        Class246.drawOutlinedRect(52.0f, n4 - this.offset - 4, this.width - 52, n4 - this.offset + 20, 1.0f, getColor(145, 50), -2142088622);
                    }
                    else {
                        Class246.drawOutlinedRect(52.0f, n4 - this.offset - 4, this.width - 52, n4 - this.offset + 20, 1.0f, getColor(145, 50), -2144259791);
                    }
                }
                else if (this.isMouseOverAlt(n, n2, n4 - this.offset) && Mouse.isButtonDown(0)) {
                    Class246.drawOutlinedRect(52.0f, n4 - this.offset - 4, this.width - 52, n4 - this.offset + 20, 1.0f, -getColor(145, 50), -2146101995);
                }
                else if (this.isMouseOverAlt(n, n2, n4 - this.offset)) {
                    Class246.drawOutlinedRect(52.0f, n4 - this.offset - 4, this.width - 52, n4 - this.offset + 20, 1.0f, getColor(145, 50), -2145180893);
                }
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, n4 - this.offset, -1);
                this.drawCenteredString(this.fontRendererObj, replaceAll, this.width / 2, n4 - this.offset + 10, getColor(110));
                n4 += 26;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(n, n2, n3);
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
        }
        else if (Keyboard.isKeyDown(208)) {
            this.offset += 26;
        }
        if (this.offset < 0) {
            this.offset = 0;
        }
        this.seatchField.drawTextBox();
        if (this.seatchField.getText().isEmpty() && !this.seatchField.isFocused()) {
            this.drawString(this.mc.fontRendererObj, "Search Alt", this.width / 2 - 264, this.height - 18, -1);
        }
    }
    
    public static int getColor(final int n, final int n2, final int n3, final int n4) {
        return 0x0 | n4 << 24 | n << 16 | n2 << 8 | n3;
    }
    
    public static int getColor(final Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static int getColor(final int n) {
        return getColor(n, n, n, 255);
    }
    
    public static int getColor(final int n, final int n2) {
        return getColor(n, n, n, n2);
    }
    
    public void initGui() {
        this.buttonList.add(this.login = new GuiButton(1, this.width / 2 - 122, this.height - 48, 100, 20, "Login"));
        this.buttonList.add(this.remove = new GuiButton(2, this.width / 2 - 16, this.height - 24, 100, 20, "Remove"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 86, this.height - 48, 100, 20, "Add"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 16, this.height - 48, 100, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 122, this.height - 24, 100, 20, "Random"));
        this.buttonList.add(this.rename = new GuiButton(6, this.width / 2 + 90, this.height - 24, 100, 20, "Edit"));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 190, this.height - 24, 60, 20, "Back"));
        this.buttonList.add(new GuiButton(8, this.width / 2 - 190, this.height - 48, 60, 20, "Reload"));
        this.seatchField = new GuiTextField(99998, this.mc.fontRendererObj, this.width / 2 - 268, this.height - 21, 68, 14);
        this.login.enabled = false;
        this.remove.enabled = false;
        this.rename.enabled = false;
    }
    
    protected void keyTyped(final char c, final int n) {
        this.seatchField.textboxKeyTyped(c, n);
        if ((c == '\t' || c == '\r') && this.seatchField.isFocused()) {
            this.seatchField.setFocused(!this.seatchField.isFocused());
        }
        try {
            super.keyTyped(c, n);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean isAltInArea(final int n) {
        return n - this.offset <= this.height - 50;
    }
    
    private boolean isMouseOverAlt(final int n, final int n2, final int n3) {
        return n >= 52 && n2 >= n3 - 4 && n <= this.width - 52 && n2 <= n3 + 20 && n >= 0 && n2 >= 33 && n <= this.width && n2 <= this.height - 50;
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) {
        this.seatchField.mouseClicked(n, n2, n3);
        if (this.offset < 0) {
            this.offset = 0;
        }
        int n4 = 38 - this.offset;
        for (final Class309 selectedAlt : this.getAlts()) {
            if (this.isMouseOverAlt(n, n2, n4)) {
                if (selectedAlt == this.selectedAlt) {
                    this.actionPerformed(this.buttonList.get(0));
                    return;
                }
                this.selectedAlt = selectedAlt;
            }
            n4 += 26;
        }
        try {
            super.mouseClicked(n, n2, n3);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private List<Class309> getAlts() {
        final ArrayList<Class309> list = new ArrayList<Class309>();
        for (final Class309 class309 : Class206.registry) {
            if (this.seatchField.getText().isEmpty() || class309.getMask().toLowerCase().contains(this.seatchField.getText().toLowerCase()) || class309.getUsername().toLowerCase().contains(this.seatchField.getText().toLowerCase())) {
                list.add(class309);
            }
        }
        return list;
    }
    
    public void prepareScissorBox(final float n, final float n2, final float n3, final float n4) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final int getScaleFactor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(n * getScaleFactor), (int)((scaledResolution.getScaledHeight() - n4) * getScaleFactor), (int)((n3 - n) * getScaleFactor), (int)((n4 - n2) * getScaleFactor));
    }
}
