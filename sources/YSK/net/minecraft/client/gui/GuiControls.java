package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;

public class GuiControls extends GuiScreen
{
    private static final GameSettings.Options[] optionsArr;
    public KeyBinding buttonId;
    private static final String[] I;
    private GuiKeyBindingList keyBindingList;
    protected String screenTitle;
    private GameSettings options;
    private GuiButton buttonReset;
    private GuiScreen parentScreen;
    public long time;
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (n3 != 0 || !this.keyBindingList.mouseReleased(n, n2, n3)) {
            super.mouseReleased(n, n2, n3);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (this.buttonId != null) {
            this.options.setOptionKeyBinding(this.buttonId, -(0x12 ^ 0x76) + n3);
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (n3 != 0 || !this.keyBindingList.mouseClicked(n, n2, n3)) {
            super.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.buttonId != null) {
            if (n == " ".length()) {
                this.options.setOptionKeyBinding(this.buttonId, "".length());
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (n != 0) {
                this.options.setOptionKeyBinding(this.buttonId, n);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else if (c > '\0') {
                this.options.setOptionKeyBinding(this.buttonId, c + (120 + 167 - 267 + 236));
            }
            this.buttonId = null;
            this.time = Minecraft.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            super.keyTyped(c, n);
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiControls(final GuiScreen parentScreen, final GameSettings options) {
        this.screenTitle = GuiControls.I["".length()];
        this.buttonId = null;
        this.parentScreen = parentScreen;
        this.options = options;
    }
    
    static {
        I();
        final GameSettings.Options[] optionsArr2 = new GameSettings.Options["   ".length()];
        optionsArr2["".length()] = GameSettings.Options.INVERT_MOUSE;
        optionsArr2[" ".length()] = GameSettings.Options.SENSITIVITY;
        optionsArr2["  ".length()] = GameSettings.Options.TOUCHSCREEN;
        optionsArr = optionsArr2;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.keyBindingList.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 133 + 183 - 255 + 139) {
            this.mc.displayGuiScreen(this.parentScreen);
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else if (guiButton.id == 54 + 136 - 135 + 146) {
            final KeyBinding[] keyBindings;
            final int length = (keyBindings = this.mc.gameSettings.keyBindings).length;
            int i = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
            while (i < length) {
                final KeyBinding keyBinding = keyBindings[i];
                keyBinding.setKeyCode(keyBinding.getKeyCodeDefault());
                ++i;
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (guiButton.id < (0xCB ^ 0xAF) && guiButton instanceof GuiOptionButton) {
            this.options.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
            guiButton.displayString = this.options.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
        }
    }
    
    @Override
    public void initGui() {
        this.keyBindingList = new GuiKeyBindingList(this, this.mc);
        this.buttonList.add(new GuiButton(37 + 122 - 42 + 83, this.width / "  ".length() - (148 + 62 - 162 + 107), this.height - (0x5A ^ 0x47), 59 + 18 - 1 + 74, 0xAE ^ 0xBA, I18n.format(GuiControls.I[" ".length()], new Object["".length()])));
        this.buttonList.add(this.buttonReset = new GuiButton(90 + 53 - 116 + 174, this.width / "  ".length() - (46 + 91 - 97 + 115) + (118 + 148 - 237 + 131), this.height - (0x29 ^ 0x34), 33 + 97 + 3 + 17, 0x56 ^ 0x42, I18n.format(GuiControls.I["  ".length()], new Object["".length()])));
        this.screenTitle = I18n.format(GuiControls.I["   ".length()], new Object["".length()]);
        int length = "".length();
        final GameSettings.Options[] optionsArr;
        final int length2 = (optionsArr = GuiControls.optionsArr).length;
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = optionsArr[i];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), this.width / "  ".length() - (62 + 86 - 60 + 67) + length % "  ".length() * (95 + 9 - 29 + 85), (0xD6 ^ 0xC4) + (0x3B ^ 0x23) * (length >> " ".length()), options));
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), this.width / "  ".length() - (63 + 64 - 122 + 150) + length % "  ".length() * (44 + 59 - 37 + 94), (0xB6 ^ 0xA4) + (0x2F ^ 0x37) * (length >> " ".length()), options, this.options.getKeyBinding(options)));
            }
            ++length;
            ++i;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / "  ".length(), 0x7D ^ 0x75, 3302105 + 14046409 - 15491804 + 14920505);
        int n4 = " ".length();
        final KeyBinding[] keyBindings;
        final int length = (keyBindings = this.options.keyBindings).length;
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < length) {
            final KeyBinding keyBinding = keyBindings[i];
            if (keyBinding.getKeyCode() != keyBinding.getKeyCodeDefault()) {
                n4 = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        final GuiButton buttonReset = this.buttonReset;
        int enabled;
        if (n4 != 0) {
            enabled = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            enabled = " ".length();
        }
        buttonReset.enabled = (enabled != 0);
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x6C ^ 0x68])["".length()] = I("\u0004*\u00075+()\u001a", "GEiAY");
        GuiControls.I[" ".length()] = I("\u0004\f9v\u0011\f\u00175", "cyPXu");
        GuiControls.I["  ".length()] = I("\u00167\u001f\"5\u001a4\u0002x5\u0010+\u0014\"\u0006\u00194", "uXqVG");
        GuiControls.I["   ".length()] = I("\u0000\u0006?\u001e \f\u0005\"D&\n\u001d=\u000f", "ciQjR");
    }
}
