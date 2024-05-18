package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.client.resources.*;

public class ScreenChatOptions extends GuiScreen
{
    private final GuiScreen parentScreen;
    private static final String[] I;
    private static final GameSettings.Options[] field_146399_a;
    private final GameSettings game_settings;
    private String field_146401_i;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("'\u0015#\u001f\u001a&\u0016y\u0015\u001d)\u0011y\u0002\u001c<\t2", "HeWvu");
        ScreenChatOptions.I[" ".length()] = I("\u000e6>g>\u0006-2", "iCWIZ");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146401_i, this.width / "  ".length(), 0x63 ^ 0x77, 3811150 + 12463705 - 8874685 + 9377045);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < (0xFE ^ 0x9A) && guiButton instanceof GuiOptionButton) {
                this.game_settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 81 + 63 - 15 + 71) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ScreenChatOptions(final GuiScreen parentScreen, final GameSettings game_settings) {
        this.parentScreen = parentScreen;
        this.game_settings = game_settings;
    }
    
    static {
        I();
        final GameSettings.Options[] field_146399_a2 = new GameSettings.Options[0x7E ^ 0x74];
        field_146399_a2["".length()] = GameSettings.Options.CHAT_VISIBILITY;
        field_146399_a2[" ".length()] = GameSettings.Options.CHAT_COLOR;
        field_146399_a2["  ".length()] = GameSettings.Options.CHAT_LINKS;
        field_146399_a2["   ".length()] = GameSettings.Options.CHAT_OPACITY;
        field_146399_a2[0x81 ^ 0x85] = GameSettings.Options.CHAT_LINKS_PROMPT;
        field_146399_a2[0xAA ^ 0xAF] = GameSettings.Options.CHAT_SCALE;
        field_146399_a2[0xA0 ^ 0xA6] = GameSettings.Options.CHAT_HEIGHT_FOCUSED;
        field_146399_a2[0xA1 ^ 0xA6] = GameSettings.Options.CHAT_HEIGHT_UNFOCUSED;
        field_146399_a2[0x27 ^ 0x2F] = GameSettings.Options.CHAT_WIDTH;
        field_146399_a2[0xAB ^ 0xA2] = GameSettings.Options.REDUCED_DEBUG_INFO;
        field_146399_a = field_146399_a2;
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        this.field_146401_i = I18n.format(ScreenChatOptions.I["".length()], new Object["".length()]);
        final GameSettings.Options[] field_146399_a;
        final int length2 = (field_146399_a = ScreenChatOptions.field_146399_a).length;
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = field_146399_a[i];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), this.width / "  ".length() - (113 + 113 - 209 + 138) + length % "  ".length() * (128 + 135 - 107 + 4), this.height / (0x9E ^ 0x98) + (0x1F ^ 0x7) * (length >> " ".length()), options));
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), this.width / "  ".length() - (68 + 51 - 1 + 37) + length % "  ".length() * (119 + 54 - 91 + 78), this.height / (0x9B ^ 0x9D) + (0x37 ^ 0x2F) * (length >> " ".length()), options, this.game_settings.getKeyBinding(options)));
            }
            ++length;
            ++i;
        }
        this.buttonList.add(new GuiButton(43 + 9 + 79 + 69, this.width / "  ".length() - (0xF9 ^ 0x9D), this.height / (0x71 ^ 0x77) + (0xDD ^ 0xA5), I18n.format(ScreenChatOptions.I[" ".length()], new Object["".length()])));
    }
}
