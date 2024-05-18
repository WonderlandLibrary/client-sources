package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.client.*;
import net.minecraft.client.stream.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.audio.*;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback
{
    protected String field_146442_a;
    private static final String[] I;
    private GuiButton field_175357_i;
    private final GuiScreen field_146441_g;
    private static final GameSettings.Options[] field_146440_f;
    private final GameSettings game_settings_1;
    private GuiLockIconButton field_175356_r;
    
    private static void I() {
        (I = new String[0x2E ^ 0x3C])["".length()] = I("\u000b$:.\b*'", "DTNGg");
        GuiOptions.I[" ".length()] = I("\n\u0007\u001a\u000f\u001b\u000b\u0004@\u0012\u001d\u0011\u001b\u000b", "ewnft");
        GuiOptions.I["  ".length()] = I("\u00156\u0003\u000e\u000b\u00145Y\u0014\u000f\u0013(4\u0012\u0017\u000e)\u001a\u000e\u0017\u001b2\u001e\b\n", "zFwgd");
        GuiOptions.I["   ".length()] = I("\u0016=52\u001ae\u001b 4\u001a <e\u0004\r1<,9\u000f6fky", "EHEWh");
        GuiOptions.I[0xE ^ 0xA] = I("\u0002\u001e\u0010\u0005(\u0003\u001dJ\u001f(\u0018\u0000\u0000\u001f", "mndlG");
        GuiOptions.I[0x4C ^ 0x49] = I("- \u0018\u0005\u000e,#B\u001f\u001505\r\u0001", "BPlla");
        GuiOptions.I[0xA7 ^ 0xA1] = I("'7#\u0006\u0019&4y\u0019\u001f,\"8", "HGWov");
        GuiOptions.I[0xB6 ^ 0xB1] = I("\u0018\u0005\u0016/\b\u0019\u0006L%\b\u0019\u0001\u0010)\u000b\u0004", "wubFg");
        GuiOptions.I[0x2F ^ 0x27] = I("\u0017\u001e%(\u0017\u0016\u001d\u007f-\u0019\u0016\t$ \u001f\u001d", "xnQAx");
        GuiOptions.I[0xB4 ^ 0xBD] = I("#\u0019\u001f=\u0017\"\u001aE7\u0010-\u001dE \u00118\u0005\u000e", "LikTx");
        GuiOptions.I[0xCF ^ 0xC5] = I("(\u0016\u0001>#)\u0015[%)4\t\u0000%/\"\u0016\u00144'", "GfuWL");
        GuiOptions.I[0x85 ^ 0x8E] = I("\u0019#1%\u0002\u0018 k?\u0003\u0019<5)\u001fX%,)\u001a", "vSELm");
        GuiOptions.I[0xBC ^ 0xB0] = I("&\u0005*C\u0017.\u001e&", "ApCms");
        GuiOptions.I[0x50 ^ 0x5D] = I("", "MDATE");
        GuiOptions.I[0xB1 ^ 0xBF] = I("\u001f\u0013!\r\u0018\u001e\u0010{\u0000\u001e\u0016\u0005<\u0007\u0002\u001c\u0017,", "pcUdw");
        GuiOptions.I[0x38 ^ 0x37] = I("NJ", "tjcIJ");
        GuiOptions.I[0x6D ^ 0x7D] = I("=#\u0000/*:?\n=:w&\t*(w>\u000f=/<", "YJfIC");
        GuiOptions.I[0xB ^ 0x1A] = I("\u0013\u0018\f+?\u0014\u0004\u00069/Y\u001d\u0005.=Y\u0000\u001f(%\u0003\u0018\u0005#", "wqjMV");
    }
    
    public String func_175355_a(final EnumDifficulty enumDifficulty) {
        final ChatComponentText chatComponentText = new ChatComponentText(GuiOptions.I[0x9B ^ 0x96]);
        chatComponentText.appendSibling(new ChatComponentTranslation(GuiOptions.I[0x95 ^ 0x9B], new Object["".length()]));
        chatComponentText.appendText(GuiOptions.I[0x63 ^ 0x6C]);
        chatComponentText.appendSibling(new ChatComponentTranslation(enumDifficulty.getDifficultyResourceKey(), new Object["".length()]));
        return chatComponentText.getFormattedText();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < (0xEC ^ 0x88) && guiButton instanceof GuiOptionButton) {
                this.game_settings_1.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                guiButton.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == (0xC2 ^ 0xAE)) {
                this.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.theWorld.getDifficulty().getDifficultyId() + " ".length()));
                this.field_175357_i.displayString = this.func_175355_a(this.mc.theWorld.getDifficulty());
            }
            if (guiButton.id == (0x43 ^ 0x2E)) {
                final Minecraft mc = this.mc;
                final String formattedText = new ChatComponentTranslation(GuiOptions.I[0x0 ^ 0x10], new Object["".length()]).getFormattedText();
                final String s = GuiOptions.I[0x2A ^ 0x3B];
                final Object[] array = new Object[" ".length()];
                array["".length()] = new ChatComponentTranslation(this.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object["".length()]);
                mc.displayGuiScreen(new GuiYesNo(this, formattedText, new ChatComponentTranslation(s, array).getFormattedText(), 0x1E ^ 0x73));
            }
            if (guiButton.id == (0xE9 ^ 0x87)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
            }
            if (guiButton.id == 1271468 + 6778064 - 7670547 + 8296324) {
                this.mc.entityRenderer.activateNextShader();
            }
            if (guiButton.id == (0x66 ^ 0x3)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.game_settings_1));
            }
            if (guiButton.id == (0x54 ^ 0x30)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
            }
            if (guiButton.id == (0xE0 ^ 0x86)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.getLanguageManager()));
            }
            if (guiButton.id == (0x50 ^ 0x37)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
            }
            if (guiButton.id == (0x1B ^ 0x73)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
            }
            if (guiButton.id == 170 + 26 - 33 + 37) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146441_g);
            }
            if (guiButton.id == (0x50 ^ 0x39)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }
            if (guiButton.id == (0xA8 ^ 0xC2)) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
            }
            if (guiButton.id == (0x2F ^ 0x44)) {
                this.mc.gameSettings.saveOptions();
                final IStream twitchStream = this.mc.getTwitchStream();
                if (twitchStream.func_152936_l() && twitchStream.func_152928_D()) {
                    this.mc.displayGuiScreen(new GuiStreamOptions(this, this.game_settings_1));
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                else {
                    GuiStreamUnavailable.func_152321_a(this);
                }
            }
        }
    }
    
    static {
        I();
        final GameSettings.Options[] field_146440_f2 = new GameSettings.Options[" ".length()];
        field_146440_f2["".length()] = GameSettings.Options.FOV;
        field_146440_f = field_146440_f2;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146442_a, this.width / "  ".length(), 0x1B ^ 0x14, 5692691 + 6251331 - 3601373 + 8434566);
        super.drawScreen(n, n2, n3);
    }
    
    public GuiOptions(final GuiScreen field_146441_g, final GameSettings game_settings_1) {
        this.field_146442_a = GuiOptions.I["".length()];
        this.field_146441_g = field_146441_g;
        this.game_settings_1 = game_settings_1;
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        this.field_146442_a = I18n.format(GuiOptions.I[" ".length()], new Object["".length()]);
        final GameSettings.Options[] field_146440_f;
        final int length2 = (field_146440_f = GuiOptions.field_146440_f).length;
        int i = "".length();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (i < length2) {
            final GameSettings.Options options = field_146440_f[i];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), this.width / "  ".length() - (120 + 130 - 239 + 144) + length % "  ".length() * (105 + 27 - 42 + 70), this.height / (0x80 ^ 0x86) - (0x16 ^ 0x1A) + (0x3C ^ 0x24) * (length >> " ".length()), options));
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), this.width / "  ".length() - (4 + 94 + 44 + 13) + length % "  ".length() * (101 + 114 - 171 + 116), this.height / (0xB6 ^ 0xB0) - (0x87 ^ 0x8B) + (0x50 ^ 0x48) * (length >> " ".length()), options, this.game_settings_1.getKeyBinding(options)));
            }
            ++length;
            ++i;
        }
        if (this.mc.theWorld != null) {
            this.field_175357_i = new GuiButton(0xE8 ^ 0x84, this.width / "  ".length() - (117 + 123 - 190 + 105) + length % "  ".length() * (26 + 96 - 0 + 38), this.height / (0xAA ^ 0xAC) - (0x4 ^ 0x8) + (0x89 ^ 0x91) * (length >> " ".length()), 62 + 111 - 25 + 2, 0x86 ^ 0x92, this.func_175355_a(this.mc.theWorld.getDifficulty()));
            this.buttonList.add(this.field_175357_i);
            if (this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - (0x90 ^ 0x84));
                this.field_175356_r = new GuiLockIconButton(0x4E ^ 0x23, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
                this.buttonList.add(this.field_175356_r);
                this.field_175356_r.func_175229_b(this.mc.theWorld.getWorldInfo().isDifficultyLocked());
                final GuiLockIconButton field_175356_r = this.field_175356_r;
                int enabled;
                if (this.field_175356_r.func_175230_c()) {
                    enabled = "".length();
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                else {
                    enabled = " ".length();
                }
                field_175356_r.enabled = (enabled != 0);
                final GuiButton field_175357_i = this.field_175357_i;
                int enabled2;
                if (this.field_175356_r.func_175230_c()) {
                    enabled2 = "".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    enabled2 = " ".length();
                }
                field_175357_i.enabled = (enabled2 != 0);
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                this.field_175357_i.enabled = ("".length() != 0);
            }
        }
        this.buttonList.add(new GuiButton(0x44 ^ 0x2A, this.width / "  ".length() - (129 + 111 - 89 + 4), this.height / (0xC ^ 0xA) + (0x12 ^ 0x22) - (0xC4 ^ 0xC2), 144 + 44 - 148 + 110, 0xB6 ^ 0xA2, I18n.format(GuiOptions.I["  ".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton(this, 1867342 + 4911918 + 119021 + 1777028, this.width / "  ".length() + (0xBE ^ 0xBB), this.height / (0x3C ^ 0x3A) + (0x4E ^ 0x7E) - (0x7B ^ 0x7D), 12 + 70 + 52 + 16, 0x82 ^ 0x96, GuiOptions.I["   ".length()]) {
            final GuiOptions this$0;
            
            @Override
            public void playPressSound(final SoundHandler soundHandler) {
                final SoundCategory[] array = new SoundCategory[0xA6 ^ 0xA3];
                array["".length()] = SoundCategory.ANIMALS;
                array[" ".length()] = SoundCategory.BLOCKS;
                array["  ".length()] = SoundCategory.MOBS;
                array["   ".length()] = SoundCategory.PLAYERS;
                array[0x68 ^ 0x6C] = SoundCategory.WEATHER;
                final SoundEventAccessorComposite randomSoundFromCategories = soundHandler.getRandomSoundFromCategories(array);
                if (randomSoundFromCategories != null) {
                    soundHandler.playSound(PositionedSoundRecord.create(randomSoundFromCategories.getSoundEventLocation(), 0.5f));
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
                    if (0 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.buttonList.add(new GuiButton(0x39 ^ 0x53, this.width / "  ".length() - (52 + 86 - 135 + 152), this.height / (0x42 ^ 0x44) + (0x2A ^ 0x62) - (0x0 ^ 0x6), 22 + 57 + 31 + 40, 0xA5 ^ 0xB1, I18n.format(GuiOptions.I[0x9D ^ 0x99], new Object["".length()])));
        this.buttonList.add(new GuiButton(0xD0 ^ 0xBB, this.width / "  ".length() + (0x9E ^ 0x9B), this.height / (0x7D ^ 0x7B) + (0xEC ^ 0xA4) - (0x20 ^ 0x26), 123 + 100 - 216 + 143, 0x9A ^ 0x8E, I18n.format(GuiOptions.I[0x2B ^ 0x2E], new Object["".length()])));
        this.buttonList.add(new GuiButton(0xF1 ^ 0x94, this.width / "  ".length() - (7 + 44 + 17 + 87), this.height / (0xC ^ 0xA) + (0xC3 ^ 0xA3) - (0x49 ^ 0x4F), 97 + 111 - 84 + 26, 0x5B ^ 0x4F, I18n.format(GuiOptions.I[0x9 ^ 0xF], new Object["".length()])));
        this.buttonList.add(new GuiButton(0xF4 ^ 0x90, this.width / "  ".length() + (0x81 ^ 0x84), this.height / (0x1A ^ 0x1C) + (0x47 ^ 0x27) - (0x93 ^ 0x95), 13 + 101 - 35 + 71, 0x57 ^ 0x43, I18n.format(GuiOptions.I[0xBD ^ 0xBA], new Object["".length()])));
        this.buttonList.add(new GuiButton(0xC5 ^ 0xA3, this.width / "  ".length() - (140 + 63 - 116 + 68), this.height / (0x3A ^ 0x3C) + (0x67 ^ 0x1F) - (0xB8 ^ 0xBE), 67 + 12 - 32 + 103, 0xBD ^ 0xA9, I18n.format(GuiOptions.I[0x90 ^ 0x98], new Object["".length()])));
        this.buttonList.add(new GuiButton(0xE3 ^ 0x84, this.width / "  ".length() + (0x10 ^ 0x15), this.height / (0xA3 ^ 0xA5) + (0xC5 ^ 0xBD) - (0x74 ^ 0x72), 91 + 23 + 13 + 23, 0xD4 ^ 0xC0, I18n.format(GuiOptions.I[0x87 ^ 0x8E], new Object["".length()])));
        this.buttonList.add(new GuiButton(0x6C ^ 0x5, this.width / "  ".length() - (153 + 44 - 78 + 36), this.height / (0x6 ^ 0x0) + (38 + 108 - 18 + 16) - (0x9 ^ 0xF), 27 + 11 - 15 + 127, 0x70 ^ 0x64, I18n.format(GuiOptions.I[0x30 ^ 0x3A], new Object["".length()])));
        this.buttonList.add(new GuiButton(0x30 ^ 0x58, this.width / "  ".length() + (0x10 ^ 0x15), this.height / (0xC2 ^ 0xC4) + (65 + 129 - 106 + 56) - (0x3D ^ 0x3B), 93 + 138 - 105 + 24, 0x36 ^ 0x22, I18n.format(GuiOptions.I[0x32 ^ 0x39], new Object["".length()])));
        this.buttonList.add(new GuiButton(36 + 185 - 208 + 187, this.width / "  ".length() - (0xE9 ^ 0x8D), this.height / (0x2E ^ 0x28) + (88 + 125 - 82 + 37), I18n.format(GuiOptions.I[0xB ^ 0x7], new Object["".length()])));
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        this.mc.displayGuiScreen(this);
        if (n == (0xDB ^ 0xB6) && b && this.mc.theWorld != null) {
            this.mc.theWorld.getWorldInfo().setDifficultyLocked(" ".length() != 0);
            this.field_175356_r.func_175229_b(" ".length() != 0);
            this.field_175356_r.enabled = ("".length() != 0);
            this.field_175357_i.enabled = ("".length() != 0);
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
