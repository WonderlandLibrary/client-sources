// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.gui.click;

import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import com.krazzzzymonkey.catalyst.managers.FileManager;
import com.krazzzzymonkey.catalyst.command.Command;
import com.krazzzzymonkey.catalyst.managers.CommandManager;
import org.lwjgl.input.Keyboard;
import java.io.IOException;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiScreen extends GuiScreen
{
    private static final /* synthetic */ String[] lIlIllI;
    private static /* synthetic */ GuiTextField console;
    public static /* synthetic */ ClickGui clickGui;
    /* synthetic */ ArrayList cmds;
    public static /* synthetic */ int[] mouse;
    public static /* synthetic */ String title;
    private static final /* synthetic */ int[] lIllIIl;
    
    private static String lIIIlIlII(final String llIllIllIIIlIII, final String llIllIllIIIIlll) {
        try {
            final SecretKeySpec llIllIllIIIllIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIllIllIIIIlll.getBytes(StandardCharsets.UTF_8)), ClickGuiScreen.lIllIIl[12]), "DES");
            final Cipher llIllIllIIIllII = Cipher.getInstance("DES");
            llIllIllIIIllII.init(ClickGuiScreen.lIllIIl[3], llIllIllIIIllIl);
            return new String(llIllIllIIIllII.doFinal(Base64.getDecoder().decode(llIllIllIIIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIllIIIlIll) {
            llIllIllIIIlIll.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIlIIIII(final int llIllIllIIIIIlI, final int llIllIllIIIIIIl) {
        return llIllIllIIIIIlI == llIllIllIIIIIIl;
    }
    
    private static void lIIIlllIl() {
        (lIllIIl = new int[13])[0] = ((0x9C ^ 0x8D) & ~(0x18 ^ 0x9));
        ClickGuiScreen.lIllIIl[1] = 243 + 97 - 271 + 186;
        ClickGuiScreen.lIllIIl[2] = " ".length();
        ClickGuiScreen.lIllIIl[3] = "  ".length();
        ClickGuiScreen.lIllIIl[4] = (191 + 77 - 229 + 195 ^ 124 + 111 - 184 + 91);
        ClickGuiScreen.lIllIIl[5] = 174 + 153 - 181 + 54;
        ClickGuiScreen.lIllIIl[6] = (0x60 ^ 0x6E);
        ClickGuiScreen.lIllIIl[7] = (124 + 78 - 63 + 0 ^ 145 + 81 - 157 + 82);
        ClickGuiScreen.lIllIIl[8] = "   ".length();
        ClickGuiScreen.lIllIIl[9] = -" ".length();
        ClickGuiScreen.lIllIIl[10] = (0xAC ^ 0xA8);
        ClickGuiScreen.lIllIIl[11] = (0x8D ^ 0xC7 ^ (0x31 ^ 0x7E));
        ClickGuiScreen.lIllIIl[12] = (0x6D ^ 0x65);
    }
    
    static {
        lIIIlllIl();
        lIIIllIII();
        ClickGuiScreen.title = ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[10]];
        ClickGuiScreen.mouse = new int[ClickGuiScreen.lIllIIl[3]];
    }
    
    private static boolean lIIIlllll(final int llIllIlIlllllIl) {
        return llIllIlIlllllIl == 0;
    }
    
    public void updateScreen() {
        "".length();
        ClickGuiScreen.console.updateCursorCounter();
        ClickGuiScreen.clickGui.onUpdate();
        super.updateScreen();
    }
    
    public void drawScreen(final int llIllIlllIIIIIl, final int llIllIlllIIIIII, final float llIllIllIllllll) {
        ClickGuiScreen.clickGui.render();
        int n;
        if (lIIIllllI(com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui.isLight ? 1 : 0)) {
            n = ColorUtils.color(ClickGuiScreen.lIllIIl[1], ClickGuiScreen.lIllIIl[1], ClickGuiScreen.lIllIIl[1], ClickGuiScreen.lIllIIl[1]);
            "".length();
            if (" ".length() >= "  ".length()) {
                return;
            }
        }
        else {
            n = ColorUtils.color(ClickGuiScreen.lIllIIl[0], ClickGuiScreen.lIllIIl[0], ClickGuiScreen.lIllIIl[0], ClickGuiScreen.lIllIIl[1]);
        }
        final int llIllIlllIIIIll = n;
        "".length();
        ClickGuiScreen.console.drawTextBox(com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui.getColor(), llIllIlllIIIIll);
        "".length();
        ClickGuiScreen.console.setTextColor(com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui.getColor());
        super.drawScreen(llIllIlllIIIIIl, llIllIlllIIIIII, llIllIllIllllll);
    }
    
    private static String lIIIlIIll(final String llIllIllIIlIlll, final String llIllIllIIlIllI) {
        try {
            final SecretKeySpec llIllIllIIllIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIllIllIIlIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIllIllIIllIIl = Cipher.getInstance("Blowfish");
            llIllIllIIllIIl.init(ClickGuiScreen.lIllIIl[3], llIllIllIIllIlI);
            return new String(llIllIllIIllIIl.doFinal(Base64.getDecoder().decode(llIllIllIIlIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIllIIllIII) {
            llIllIllIIllIII.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIIllllI(final int llIllIlIlllllll) {
        return llIllIlIlllllll != 0;
    }
    
    private static void lIIIllIII() {
        (lIlIllI = new String[ClickGuiScreen.lIllIIl[11]])[ClickGuiScreen.lIllIIl[0]] = lIIIlIIll("hro0Ln8sZ6E=", "hIziW");
        ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[2]] = lIIIlIlII("/ZCwSGTPANsOka3Pqbqr5MQGAAHO3lEf", "DjAIT");
        ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[3]] = lIIIlIIll("zg0weyKn8O0=", "FkNKZ");
        ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[8]] = lIIIlIIll("eR/h/0k1w+Q=", "MOSkF");
        ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[10]] = lIIIlIlII("TydcFbt0BVzmI9qTu2QCYH92RmEU+cHL", "ohdaf");
    }
    
    protected void mouseClicked(final int llIllIlllIIllll, final int llIllIlllIlIIlI, final int llIllIlllIlIIIl) throws IOException {
        super.mouseClicked(llIllIlllIIllll, llIllIlllIlIIlI, llIllIlllIlIIIl);
        "".length();
        ClickGuiScreen.console.mouseClicked(llIllIlllIIllll, llIllIlllIlIIlI, llIllIlllIlIIIl);
        "".length();
    }
    
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)(ClickGuiScreen.lIllIIl[0] != 0));
        super.onGuiClosed();
    }
    
    public ClickGuiScreen() {
        this.cmds = new ArrayList();
        this.cmds.clear();
        final short llIllIlllIllIlI = (short)CommandManager.commands.iterator();
        while (lIIIllllI(((Iterator)llIllIlllIllIlI).hasNext() ? 1 : 0)) {
            final Command llIllIlllIlllIl = ((Iterator<Command>)llIllIlllIllIlI).next();
            this.cmds.add(String.valueOf(new StringBuilder().append(llIllIlllIlllIl.getCommand()).append(ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[0]]).append(llIllIlllIlllIl.getDescription())));
            "".length();
            "".length();
            if (((0x3A ^ 0x15) & ~(0x8B ^ 0xA4)) > 0) {
                throw null;
            }
        }
    }
    
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)(ClickGuiScreen.lIllIIl[2] != 0));
        "".length();
        ClickGuiScreen.console = new GuiTextField(ClickGuiScreen.lIllIIl[0], this.fontRenderer, this.width / ClickGuiScreen.lIllIIl[3] - ClickGuiScreen.lIllIIl[4], ClickGuiScreen.lIllIIl[0], ClickGuiScreen.lIllIIl[5], ClickGuiScreen.lIllIIl[6]);
        "".length();
        ClickGuiScreen.console.setMaxStringLength(ClickGuiScreen.lIllIIl[4]);
        "".length();
        ClickGuiScreen.console.setText(ClickGuiScreen.title);
        "".length();
        ClickGuiScreen.console.setFocused((boolean)(ClickGuiScreen.lIllIIl[2] != 0));
        super.initGui();
    }
    
    void setTitle() {
        if (lIIIlllll(ClickGuiScreen.console.getText().equals(ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[2]]) ? 1 : 0)) {
            ClickGuiScreen.title = ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[3]];
        }
    }
    
    public void handleInput() throws IOException {
        final int llIllIllIlIIllI = this.mc.gameSettings.guiScale;
        this.mc.gameSettings.guiScale = ClickGuiScreen.lIllIIl[3];
        if (lIIIllllI(Keyboard.isCreated() ? 1 : 0)) {
            Keyboard.enableRepeatEvents((boolean)(ClickGuiScreen.lIllIIl[2] != 0));
            while (lIIIllllI(Keyboard.next() ? 1 : 0)) {
                if (lIIIllllI(Keyboard.getEventKeyState() ? 1 : 0)) {
                    ClickGuiScreen.console.textboxKeyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
                    "".length();
                    if (lIIlIIIII(Keyboard.getEventKey(), ClickGuiScreen.lIllIIl[7])) {
                        this.setTitle();
                        CommandManager.getInstance().runCommands(String.valueOf(new StringBuilder().append(ClickGuiScreen.lIlIllI[ClickGuiScreen.lIllIIl[8]]).append(ClickGuiScreen.console.getText())));
                        this.mc.displayGuiScreen((GuiScreen)null);
                        FileManager.saveHacks();
                        "".length();
                        if ((0x52 ^ 0x56) < 0) {
                            return;
                        }
                        continue;
                    }
                    else if (lIIlIIIII(Keyboard.getEventKey(), ClickGuiScreen.lIllIIl[2])) {
                        this.setTitle();
                        this.mc.displayGuiScreen((GuiScreen)null);
                        FileManager.saveHacks();
                        "".length();
                        if ("  ".length() <= -" ".length()) {
                            return;
                        }
                        continue;
                    }
                    else {
                        ClickGuiScreen.clickGui.onkeyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                        "".length();
                        if ("   ".length() > (0xD8 ^ 0x8B ^ (0x66 ^ 0x31))) {
                            return;
                        }
                        continue;
                    }
                }
                else {
                    ClickGuiScreen.clickGui.onKeyRelease(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                    "".length();
                    if (" ".length() > (0xC5 ^ 0xC1)) {
                        return;
                    }
                    continue;
                }
            }
        }
        if (lIIIllllI(Mouse.isCreated() ? 1 : 0)) {
            while (lIIIllllI(Mouse.next() ? 1 : 0)) {
                final ScaledResolution llIllIllIlIlIlI = new ScaledResolution(this.mc);
                final int llIllIllIlIlIIl = Mouse.getEventX() * llIllIllIlIlIlI.getScaledWidth() / this.mc.displayWidth;
                final int llIllIllIlIlIII = llIllIllIlIlIlI.getScaledHeight() - Mouse.getEventY() * llIllIllIlIlIlI.getScaledHeight() / this.mc.displayHeight - ClickGuiScreen.lIllIIl[2];
                if (lIIlIIIII(Mouse.getEventButton(), ClickGuiScreen.lIllIIl[9])) {
                    if (lIIIllllI(Mouse.getEventDWheel())) {
                        final int llIllIllIlIllII = llIllIllIlIlIIl;
                        final int llIllIllIlIlIll = llIllIllIlIlIII;
                        ClickGuiScreen.clickGui.onMouseScroll(Mouse.getEventDWheel() / ClickGuiScreen.lIllIIl[4] * ClickGuiScreen.lIllIIl[8]);
                    }
                    ClickGuiScreen.clickGui.onMouseUpdate(llIllIllIlIlIIl, llIllIllIlIlIII);
                    ClickGuiScreen.mouse[ClickGuiScreen.lIllIIl[0]] = llIllIllIlIlIIl;
                    ClickGuiScreen.mouse[ClickGuiScreen.lIllIIl[2]] = llIllIllIlIlIII;
                    "".length();
                    if ((0x64 ^ 0x61) == 0x0) {
                        return;
                    }
                }
                else if (lIIIllllI(Mouse.getEventButtonState() ? 1 : 0)) {
                    ClickGuiScreen.clickGui.onMouseClick(llIllIllIlIlIIl, llIllIllIlIlIII, Mouse.getEventButton());
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
                else {
                    ClickGuiScreen.clickGui.onMouseRelease(llIllIllIlIlIIl, llIllIllIlIlIII);
                }
                "".length();
                if ((0x75 ^ 0x71) == "  ".length()) {
                    return;
                }
            }
        }
        this.mc.gameSettings.guiScale = llIllIllIlIIllI;
        super.handleInput();
    }
}
