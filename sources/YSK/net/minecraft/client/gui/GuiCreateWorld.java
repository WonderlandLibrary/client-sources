package net.minecraft.client.gui;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.*;

public class GuiCreateWorld extends GuiScreen
{
    private GuiButton btnBonusItems;
    private GuiButton btnAllowCommands;
    private String gameMode;
    private boolean field_146344_y;
    private String field_175300_s;
    private GuiTextField field_146333_g;
    private GuiButton btnMapType;
    private GuiButton btnGameMode;
    private String field_146336_i;
    private String field_146330_J;
    private GuiTextField field_146335_h;
    private boolean field_146337_w;
    private String field_146328_H;
    private String field_146329_I;
    private GuiButton btnCustomizeType;
    private String field_146323_G;
    private boolean field_146341_s;
    private boolean allowCheats;
    private boolean field_146345_x;
    private GuiButton btnMapFeatures;
    private GuiScreen parentScreen;
    private static final String[] disallowedFilenames;
    private static final String[] I;
    public String chunkProviderSettingsJson;
    private int selectedIndex;
    private boolean field_146338_v;
    private GuiButton btnMoreOptions;
    private boolean field_146339_u;
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.field_146344_y) {
            this.field_146335_h.mouseClicked(n, n2, n3);
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            this.field_146333_g.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.field_146333_g.isFocused() && !this.field_146344_y) {
            this.field_146333_g.textboxKeyTyped(c, n);
            this.field_146330_J = this.field_146333_g.getText();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (this.field_146335_h.isFocused() && this.field_146344_y) {
            this.field_146335_h.textboxKeyTyped(c, n);
            this.field_146329_I = this.field_146335_h.getText();
        }
        if (n == (0x85 ^ 0x99) || n == 48 + 60 - 46 + 94) {
            this.actionPerformed(this.buttonList.get("".length()));
        }
        final GuiButton guiButton = this.buttonList.get("".length());
        int enabled;
        if (this.field_146333_g.getText().length() > 0) {
            enabled = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            enabled = "".length();
        }
        guiButton.enabled = (enabled != 0);
        this.func_146314_g();
    }
    
    private boolean func_175299_g() {
        final WorldType worldType = WorldType.worldTypes[this.selectedIndex];
        int n;
        if (worldType != null && worldType.getCanBeCreated()) {
            if (worldType == WorldType.DEBUG_WORLD) {
                n = (GuiScreen.isShiftKeyDown() ? 1 : 0);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                n = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == " ".length()) {
                this.mc.displayGuiScreen(this.parentScreen);
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else if (guiButton.id == 0) {
                this.mc.displayGuiScreen(null);
                if (this.field_146345_x) {
                    return;
                }
                this.field_146345_x = (" ".length() != 0);
                long nextLong = new Random().nextLong();
                final String text = this.field_146335_h.getText();
                if (!StringUtils.isEmpty((CharSequence)text)) {
                    try {
                        final long long1 = Long.parseLong(text);
                        if (long1 != 0L) {
                            nextLong = long1;
                            "".length();
                            if (4 < 1) {
                                throw null;
                            }
                        }
                    }
                    catch (NumberFormatException ex) {
                        nextLong = text.hashCode();
                    }
                }
                final WorldSettings worldSettings = new WorldSettings(nextLong, WorldSettings.GameType.getByName(this.gameMode), this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.selectedIndex]);
                worldSettings.setWorldName(this.chunkProviderSettingsJson);
                if (this.field_146338_v && !this.field_146337_w) {
                    worldSettings.enableBonusChest();
                }
                if (this.allowCheats && !this.field_146337_w) {
                    worldSettings.enableCommands();
                }
                this.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), worldSettings);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else if (guiButton.id == "   ".length()) {
                this.func_146315_i();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (guiButton.id == "  ".length()) {
                if (this.gameMode.equals(GuiCreateWorld.I[0xFE ^ 0xBE])) {
                    if (!this.field_146339_u) {
                        this.allowCheats = ("".length() != 0);
                    }
                    this.field_146337_w = ("".length() != 0);
                    this.gameMode = GuiCreateWorld.I[0x7C ^ 0x3D];
                    this.field_146337_w = (" ".length() != 0);
                    this.btnAllowCommands.enabled = ("".length() != 0);
                    this.btnBonusItems.enabled = ("".length() != 0);
                    this.func_146319_h();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (this.gameMode.equals(GuiCreateWorld.I[0xD ^ 0x4F])) {
                    if (!this.field_146339_u) {
                        this.allowCheats = (" ".length() != 0);
                    }
                    this.field_146337_w = ("".length() != 0);
                    this.gameMode = GuiCreateWorld.I[0xC6 ^ 0x85];
                    this.func_146319_h();
                    this.field_146337_w = ("".length() != 0);
                    this.btnAllowCommands.enabled = (" ".length() != 0);
                    this.btnBonusItems.enabled = (" ".length() != 0);
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
                else {
                    if (!this.field_146339_u) {
                        this.allowCheats = ("".length() != 0);
                    }
                    this.gameMode = GuiCreateWorld.I[0x27 ^ 0x63];
                    this.func_146319_h();
                    this.btnAllowCommands.enabled = (" ".length() != 0);
                    this.btnBonusItems.enabled = (" ".length() != 0);
                    this.field_146337_w = ("".length() != 0);
                }
                this.func_146319_h();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x39 ^ 0x3D)) {
                int field_146341_s;
                if (this.field_146341_s) {
                    field_146341_s = "".length();
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                }
                else {
                    field_146341_s = " ".length();
                }
                this.field_146341_s = (field_146341_s != 0);
                this.func_146319_h();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x82 ^ 0x85)) {
                int field_146338_v;
                if (this.field_146338_v) {
                    field_146338_v = "".length();
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
                else {
                    field_146338_v = " ".length();
                }
                this.field_146338_v = (field_146338_v != 0);
                this.func_146319_h();
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x89 ^ 0x8C)) {
                this.selectedIndex += " ".length();
                if (this.selectedIndex >= WorldType.worldTypes.length) {
                    this.selectedIndex = "".length();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                while (!this.func_175299_g()) {
                    this.selectedIndex += " ".length();
                    if (this.selectedIndex >= WorldType.worldTypes.length) {
                        this.selectedIndex = "".length();
                    }
                }
                this.chunkProviderSettingsJson = GuiCreateWorld.I[0x63 ^ 0x26];
                this.func_146319_h();
                this.func_146316_a(this.field_146344_y);
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x88 ^ 0x8E)) {
                this.field_146339_u = (" ".length() != 0);
                int allowCheats;
                if (this.allowCheats) {
                    allowCheats = "".length();
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else {
                    allowCheats = " ".length();
                }
                this.allowCheats = (allowCheats != 0);
                this.func_146319_h();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x85 ^ 0x8D)) {
                if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
                    this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
                }
            }
        }
    }
    
    public void func_146318_a(final WorldInfo worldInfo) {
        final String s = GuiCreateWorld.I[0x66 ^ 0x37];
        final Object[] array = new Object[" ".length()];
        array["".length()] = worldInfo.getWorldName();
        this.field_146330_J = I18n.format(s, array);
        this.field_146329_I = new StringBuilder(String.valueOf(worldInfo.getSeed())).toString();
        this.selectedIndex = worldInfo.getTerrainType().getWorldTypeID();
        this.chunkProviderSettingsJson = worldInfo.getGeneratorOptions();
        this.field_146341_s = worldInfo.isMapFeaturesEnabled();
        this.allowCheats = worldInfo.areCommandsAllowed();
        if (worldInfo.isHardcoreModeEnabled()) {
            this.gameMode = GuiCreateWorld.I[0x6B ^ 0x39];
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (worldInfo.getGameType().isSurvivalOrAdventure()) {
            this.gameMode = GuiCreateWorld.I[0xB ^ 0x58];
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else if (worldInfo.getGameType().isCreative()) {
            this.gameMode = GuiCreateWorld.I[0x75 ^ 0x21];
        }
    }
    
    @Override
    public void updateScreen() {
        this.field_146333_g.updateCursorCounter();
        this.field_146335_h.updateCursorCounter();
    }
    
    private void func_146316_a(final boolean field_146344_y) {
        this.field_146344_y = field_146344_y;
        if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
            final GuiButton btnGameMode = this.btnGameMode;
            int visible;
            if (this.field_146344_y) {
                visible = "".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                visible = " ".length();
            }
            btnGameMode.visible = (visible != 0);
            this.btnGameMode.enabled = ("".length() != 0);
            if (this.field_175300_s == null) {
                this.field_175300_s = this.gameMode;
            }
            this.gameMode = GuiCreateWorld.I[0x55 ^ 0x13];
            this.btnMapFeatures.visible = ("".length() != 0);
            this.btnBonusItems.visible = ("".length() != 0);
            this.btnMapType.visible = this.field_146344_y;
            this.btnAllowCommands.visible = ("".length() != 0);
            this.btnCustomizeType.visible = ("".length() != 0);
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            final GuiButton btnGameMode2 = this.btnGameMode;
            int visible2;
            if (this.field_146344_y) {
                visible2 = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                visible2 = " ".length();
            }
            btnGameMode2.visible = (visible2 != 0);
            this.btnGameMode.enabled = (" ".length() != 0);
            if (this.field_175300_s != null) {
                this.gameMode = this.field_175300_s;
                this.field_175300_s = null;
            }
            final GuiButton btnMapFeatures = this.btnMapFeatures;
            int visible3;
            if (this.field_146344_y && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED) {
                visible3 = " ".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                visible3 = "".length();
            }
            btnMapFeatures.visible = (visible3 != 0);
            this.btnBonusItems.visible = this.field_146344_y;
            this.btnMapType.visible = this.field_146344_y;
            this.btnAllowCommands.visible = this.field_146344_y;
            final GuiButton btnCustomizeType = this.btnCustomizeType;
            int visible4;
            if (this.field_146344_y && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED)) {
                visible4 = " ".length();
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else {
                visible4 = "".length();
            }
            btnCustomizeType.visible = (visible4 != 0);
        }
        this.func_146319_h();
        if (this.field_146344_y) {
            this.btnMoreOptions.displayString = I18n.format(GuiCreateWorld.I[0xD ^ 0x4A], new Object["".length()]);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            this.btnMoreOptions.displayString = I18n.format(GuiCreateWorld.I[0x34 ^ 0x7C], new Object["".length()]);
        }
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    private void func_146319_h() {
        this.btnGameMode.displayString = String.valueOf(I18n.format(GuiCreateWorld.I[0x5F ^ 0x79], new Object["".length()])) + GuiCreateWorld.I[0x1A ^ 0x3D] + I18n.format(GuiCreateWorld.I[0x8E ^ 0xA6] + this.gameMode, new Object["".length()]);
        this.field_146323_G = I18n.format(GuiCreateWorld.I[0x2D ^ 0x4] + this.gameMode + GuiCreateWorld.I[0xBC ^ 0x96], new Object["".length()]);
        this.field_146328_H = I18n.format(GuiCreateWorld.I[0x31 ^ 0x1A] + this.gameMode + GuiCreateWorld.I[0xAB ^ 0x87], new Object["".length()]);
        this.btnMapFeatures.displayString = String.valueOf(I18n.format(GuiCreateWorld.I[0x57 ^ 0x7A], new Object["".length()])) + GuiCreateWorld.I[0x9F ^ 0xB1];
        if (this.field_146341_s) {
            this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format(GuiCreateWorld.I[0x4F ^ 0x60], new Object["".length()]);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format(GuiCreateWorld.I[0x2C ^ 0x1C], new Object["".length()]);
        }
        this.btnBonusItems.displayString = String.valueOf(I18n.format(GuiCreateWorld.I[0xAE ^ 0x9F], new Object["".length()])) + GuiCreateWorld.I[0x1C ^ 0x2E];
        if (this.field_146338_v && !this.field_146337_w) {
            this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format(GuiCreateWorld.I[0x1D ^ 0x2E], new Object["".length()]);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format(GuiCreateWorld.I[0x70 ^ 0x44], new Object["".length()]);
        }
        this.btnMapType.displayString = String.valueOf(I18n.format(GuiCreateWorld.I[0x7D ^ 0x48], new Object["".length()])) + GuiCreateWorld.I[0x92 ^ 0xA4] + I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslateName(), new Object["".length()]);
        this.btnAllowCommands.displayString = String.valueOf(I18n.format(GuiCreateWorld.I[0x17 ^ 0x20], new Object["".length()])) + GuiCreateWorld.I[0xA5 ^ 0x9D];
        if (this.allowCheats && !this.field_146337_w) {
            this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format(GuiCreateWorld.I[0x21 ^ 0x18], new Object["".length()]);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format(GuiCreateWorld.I[0x75 ^ 0x4F], new Object["".length()]);
        }
    }
    
    static {
        I();
        final String[] disallowedFilenames2 = new String[0x74 ^ 0x6C];
        disallowedFilenames2["".length()] = GuiCreateWorld.I["".length()];
        disallowedFilenames2[" ".length()] = GuiCreateWorld.I[" ".length()];
        disallowedFilenames2["  ".length()] = GuiCreateWorld.I["  ".length()];
        disallowedFilenames2["   ".length()] = GuiCreateWorld.I["   ".length()];
        disallowedFilenames2[0x8 ^ 0xC] = GuiCreateWorld.I[0xC ^ 0x8];
        disallowedFilenames2[0x89 ^ 0x8C] = GuiCreateWorld.I[0x45 ^ 0x40];
        disallowedFilenames2[0xA ^ 0xC] = GuiCreateWorld.I[0xC5 ^ 0xC3];
        disallowedFilenames2[0x9D ^ 0x9A] = GuiCreateWorld.I[0x99 ^ 0x9E];
        disallowedFilenames2[0x5C ^ 0x54] = GuiCreateWorld.I[0x7B ^ 0x73];
        disallowedFilenames2[0xA5 ^ 0xAC] = GuiCreateWorld.I[0x4A ^ 0x43];
        disallowedFilenames2[0x83 ^ 0x89] = GuiCreateWorld.I[0x73 ^ 0x79];
        disallowedFilenames2[0xA7 ^ 0xAC] = GuiCreateWorld.I[0x24 ^ 0x2F];
        disallowedFilenames2[0x61 ^ 0x6D] = GuiCreateWorld.I[0x71 ^ 0x7D];
        disallowedFilenames2[0x37 ^ 0x3A] = GuiCreateWorld.I[0xBD ^ 0xB0];
        disallowedFilenames2[0x59 ^ 0x57] = GuiCreateWorld.I[0x4 ^ 0xA];
        disallowedFilenames2[0x4 ^ 0xB] = GuiCreateWorld.I[0x8E ^ 0x81];
        disallowedFilenames2[0xB7 ^ 0xA7] = GuiCreateWorld.I[0xB7 ^ 0xA7];
        disallowedFilenames2[0x83 ^ 0x92] = GuiCreateWorld.I[0x88 ^ 0x99];
        disallowedFilenames2[0x91 ^ 0x83] = GuiCreateWorld.I[0x73 ^ 0x61];
        disallowedFilenames2[0xA3 ^ 0xB0] = GuiCreateWorld.I[0x53 ^ 0x40];
        disallowedFilenames2[0x7B ^ 0x6F] = GuiCreateWorld.I[0x77 ^ 0x63];
        disallowedFilenames2[0x8E ^ 0x9B] = GuiCreateWorld.I[0x0 ^ 0x15];
        disallowedFilenames2[0x92 ^ 0x84] = GuiCreateWorld.I[0x2E ^ 0x38];
        disallowedFilenames2[0xD6 ^ 0xC1] = GuiCreateWorld.I[0xBD ^ 0xAA];
        disallowedFilenames = disallowedFilenames2;
    }
    
    public GuiCreateWorld(final GuiScreen parentScreen) {
        this.gameMode = GuiCreateWorld.I[0x30 ^ 0x28];
        this.field_146341_s = (" ".length() != 0);
        this.chunkProviderSettingsJson = GuiCreateWorld.I[0x8E ^ 0x97];
        this.parentScreen = parentScreen;
        this.field_146329_I = GuiCreateWorld.I[0x2D ^ 0x37];
        this.field_146330_J = I18n.format(GuiCreateWorld.I[0xA ^ 0x11], new Object["".length()]);
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_146314_g() {
        this.field_146336_i = this.field_146333_g.getText().trim();
        final char[] allowedCharactersArray;
        final int length = (allowedCharactersArray = ChatAllowedCharacters.allowedCharactersArray).length;
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < length) {
            this.field_146336_i = this.field_146336_i.replace(allowedCharactersArray[i], (char)(0x26 ^ 0x79));
            ++i;
        }
        if (StringUtils.isEmpty((CharSequence)this.field_146336_i)) {
            this.field_146336_i = GuiCreateWorld.I[0x44 ^ 0x61];
        }
        this.field_146336_i = func_146317_a(this.mc.getSaveLoader(), this.field_146336_i);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiCreateWorld.I[0xC1 ^ 0x88], new Object["".length()]), this.width / "  ".length(), 0x94 ^ 0x80, -" ".length());
        if (this.field_146344_y) {
            this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.I[0xDA ^ 0x90], new Object["".length()]), this.width / "  ".length() - (0x34 ^ 0x50), 0xAD ^ 0x82, -(3919620 + 2348723 - 2486351 + 2468344));
            this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.I[0x16 ^ 0x5D], new Object["".length()]), this.width / "  ".length() - (0x6B ^ 0xF), 0xDA ^ 0x8F, -(3489000 + 6118682 - 5934610 + 2577264));
            if (this.btnMapFeatures.visible) {
                this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.I[0xB ^ 0x47], new Object["".length()]), this.width / "  ".length() - (34 + 99 - 103 + 120), 0x45 ^ 0x3F, -(4139338 + 4616698 - 4258672 + 1752972));
            }
            if (this.btnAllowCommands.visible) {
                this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.I[0x7B ^ 0x36], new Object["".length()]), this.width / "  ".length() - (41 + 124 - 161 + 146), 11 + 91 - 87 + 157, -(1706610 + 622313 + 183699 + 3737714));
            }
            this.field_146335_h.drawTextBox();
            if (WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice()) {
                this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.selectedIndex].func_151359_c(), new Object["".length()]), this.btnMapType.xPosition + "  ".length(), this.btnMapType.yPosition + (0x55 ^ 0x43), this.btnMapType.getButtonWidth(), 4851941 + 4019089 - 6305168 + 7961018);
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
        }
        else {
            this.drawString(this.fontRendererObj, I18n.format(GuiCreateWorld.I[0xC ^ 0x42], new Object["".length()]), this.width / "  ".length() - (0x66 ^ 0x2), 0x25 ^ 0xA, -(2447054 + 4779607 - 1802913 + 826588));
            this.drawString(this.fontRendererObj, String.valueOf(I18n.format(GuiCreateWorld.I[0xF7 ^ 0xB8], new Object["".length()])) + GuiCreateWorld.I[0x91 ^ 0xC1] + this.field_146336_i, this.width / "  ".length() - (0x25 ^ 0x41), 0x95 ^ 0xC0, -(3308430 + 287478 - 3291629 + 5946057));
            this.field_146333_g.drawTextBox();
            this.drawString(this.fontRendererObj, this.field_146323_G, this.width / "  ".length() - (0xF3 ^ 0x97), 4 + 81 + 15 + 37, -(4110306 + 4834370 - 6186959 + 3492619));
            this.drawString(this.fontRendererObj, this.field_146328_H, this.width / "  ".length() - (0xB ^ 0x6F), 111 + 30 - 104 + 112, -(3842443 + 2653855 - 5589562 + 5343600));
        }
        super.drawScreen(n, n2, n3);
    }
    
    private void func_146315_i() {
        int n;
        if (this.field_146344_y) {
            n = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        this.func_146316_a(n != 0);
    }
    
    public static String func_146317_a(final ISaveFormat saveFormat, String s) {
        s = s.replaceAll(GuiCreateWorld.I[0x45 ^ 0x7E], GuiCreateWorld.I[0x9C ^ 0xA0]);
        final String[] disallowedFilenames;
        final int length = (disallowedFilenames = GuiCreateWorld.disallowedFilenames).length;
        int i = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (i < length) {
            if (s.equalsIgnoreCase(disallowedFilenames[i])) {
                s = GuiCreateWorld.I[0x3F ^ 0x2] + s + GuiCreateWorld.I[0xAD ^ 0x93];
            }
            ++i;
        }
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (saveFormat.getWorldInfo(s) != null) {
            s = String.valueOf(s) + GuiCreateWorld.I[0x6C ^ 0x53];
        }
        return s;
    }
    
    private static void I() {
        (I = new String[0xDF ^ 0x8A])["".length()] = I(",\n\u000b", "oEEfe");
        GuiCreateWorld.I[" ".length()] = I("\u0007\t$", "DFiCQ");
        GuiCreateWorld.I["  ".length()] = I("=\u0019(", "mKfcq");
        GuiCreateWorld.I["   ".length()] = I("\u0010\u0018\r", "QMUer");
        GuiCreateWorld.I[0x3C ^ 0x38] = I(",.\"\n\fK", "obmIG");
        GuiCreateWorld.I[0x9D ^ 0x98] = I("\u0002:\u0019", "LoURW");
        GuiCreateWorld.I[0xA6 ^ 0xA0] = I("\b>8`", "KquQY");
        GuiCreateWorld.I[0x29 ^ 0x2E] = I("5\u0017\f~", "vXALQ");
        GuiCreateWorld.I[0xB3 ^ 0xBB] = I("\n\u0007=Y", "IHpjh");
        GuiCreateWorld.I[0x71 ^ 0x78] = I("\n,/A", "Icbuz");
        GuiCreateWorld.I[0xBF ^ 0xB5] = I(":.&\u007f", "yakJL");
        GuiCreateWorld.I[0x42 ^ 0x49] = I("$74f", "gxyPS");
        GuiCreateWorld.I[0x47 ^ 0x4B] = I("%\u0000*y", "fOgNm");
        GuiCreateWorld.I[0xC ^ 0x1] = I("(;8l", "ktuTN");
        GuiCreateWorld.I[0x9F ^ 0x91] = I("\u0015\u001b$\\", "VTiet");
        GuiCreateWorld.I[0x1A ^ 0x15] = I("\u0019\u001d\u0007h", "UMSYn");
        GuiCreateWorld.I[0x59 ^ 0x49] = I("\u000f\u001f,Z", "COxhO");
        GuiCreateWorld.I[0xB2 ^ 0xA3] = I("\u0003\u0015>]", "OEjnt");
        GuiCreateWorld.I[0x1E ^ 0xC] = I(")\u00177w", "eGcCj");
        GuiCreateWorld.I[0xA1 ^ 0xB2] = I("\u001c%.v", "PuzCG");
        GuiCreateWorld.I[0x7 ^ 0x13] = I("\b5'W", "DesaM");
        GuiCreateWorld.I[0x8C ^ 0x99] = I("\"\u001c\u0010v", "nLDAU");
        GuiCreateWorld.I[0x11 ^ 0x7] = I("%\u0015:P", "iEnhC");
        GuiCreateWorld.I[0x39 ^ 0x2E] = I("+\u0016\u0002a", "gFVXo");
        GuiCreateWorld.I[0x2A ^ 0x32] = I("\u0011#'\u000f\u001a\u001479", "bVUys");
        GuiCreateWorld.I[0xB9 ^ 0xA0] = I("", "QiBtz");
        GuiCreateWorld.I[0x11 ^ 0xB] = I("", "kRDEI");
        GuiCreateWorld.I[0x4B ^ 0x50] = I("\u0012\u0007\u0005&1\u00155\u00061>\u0005L\u0007&%6\r\u001b/6", "abiCR");
        GuiCreateWorld.I[0xDA ^ 0xC6] = I("2<\t\u0013\n5\u000e\n\u0004\u0005%w\u0006\u0004\f -\u0000", "AYevi");
        GuiCreateWorld.I[0x88 ^ 0x95] = I("7;%G\u00121 /\f\u001d", "PNLiq");
        GuiCreateWorld.I[0x22 ^ 0x3C] = I("9\u000f:'\u000e>=90\u0001.D1#\u0000/'9&\b", "JjVBm");
        GuiCreateWorld.I[0xB5 ^ 0xAA] = I("\u00152\u0005\u0016\u0012\u0012\u0000\u0006\u0001\u001d\u0002y\u0004\u001c\u0003\u0003\u0000\u0006\u0001\u001d\u0002\u0018\u0019\u0007\u0018\t9\u001a", "fWisq");
        GuiCreateWorld.I[0x47 ^ 0x67] = I("6\t/\u001d\u00111;,\n\u001e!B.\u0019\u0002\u0003\t\"\f\u00077\t0", "ElCxr");
        GuiCreateWorld.I[0x9F ^ 0xBE] = I("\u0006\f\u000569\u0001>\u0006!6\u0011G\u000b<4\u0000\u001a '?\u0018\u001a", "uiiSZ");
        GuiCreateWorld.I[0x8F ^ 0xAD] = I("\u0019\u0014?\r9\u001e&<\u001a6\u000e_>\t*>\b#\r", "jqShZ");
        GuiCreateWorld.I[0x27 ^ 0x4] = I("7\t?\u001f\u00020;<\b\r B2\u0016\r+\u001b\u0010\u0015\f)\r=\u001e\u0012", "DlSza");
        GuiCreateWorld.I[0xA5 ^ 0x81] = I("%3\u001a4\n\"\u0001\u0019#\u00052x\u0015$\u001a\"9\u001b8\u00133\u0002\u000f!\f", "VVvQi");
        GuiCreateWorld.I[0x7D ^ 0x58] = I("\u0013\u0015;$ ", "DzIHD");
        GuiCreateWorld.I[0xB7 ^ 0x91] = I("\u001d\u0010? 9\u001a\"<76\n[4$7\u000b8<!?", "nuSEZ");
        GuiCreateWorld.I[0x33 ^ 0x14] = I("vY", "LyzEa");
        GuiCreateWorld.I[0x42 ^ 0x6A] = I("\u001f6\t#\u0010\u0018\u0004\n4\u001f\b}\u0002'\u001e\t\u001e\n\"\u0016B", "lSeFs");
        GuiCreateWorld.I[0x89 ^ 0xA0] = I("\t7\u0004&;\u000e\u0005\u000714\u001e|\u000f\"5\u001f\u001f\u0007'=T", "zRhCX");
        GuiCreateWorld.I[0x21 ^ 0xB] = I("o=\u0018\"\u0007p", "AQqLb");
        GuiCreateWorld.I[0xBF ^ 0x94] = I("9+% \u0015>\u0019&7\u001a.`.$\u001b/\u0003&!\u0013d", "JNIEv");
        GuiCreateWorld.I[0x68 ^ 0x44] = I("j4.\u0003\u001fv", "DXGmz");
        GuiCreateWorld.I[0x5B ^ 0x76] = I("!\u000e\u001e7!&<\u001d .6E\u001f32\u0014\u000e\u0013&7 \u000e\u0001", "RkrRB");
        GuiCreateWorld.I[0x1D ^ 0x33] = I("e", "EDRRa");
        GuiCreateWorld.I[0x2B ^ 0x4] = I("6\u00013\u000f\f7\u0002i\t\r", "YqGfc");
        GuiCreateWorld.I[0x22 ^ 0x12] = I(".\u00048%\u001f/\u0007b#\u0016'", "AtLLp");
        GuiCreateWorld.I[0xA5 ^ 0x94] = I("\u001a\u0004\u0006\u00147\u001d6\u0005\u00038\rO\b\u001e:\u001c\u0012#\u00051\u0004\u0012", "iajqT");
        GuiCreateWorld.I[0x9C ^ 0xAE] = I("i", "IDiWl");
        GuiCreateWorld.I[0xB5 ^ 0x86] = I("\u0017 \u0003\u0002-\u0016#Y\u0004,", "xPwkB");
        GuiCreateWorld.I[0x24 ^ 0x10] = I("\u0000<1,\u001c\u0001?k*\u0015\t", "oLEEs");
        GuiCreateWorld.I[0xB1 ^ 0x84] = I("\u0018 \u0005\b0\u001f\u0012\u0006\u001f?\u000fk\u0004\f#?<\u0019\b", "kEimS");
        GuiCreateWorld.I[0x9E ^ 0xA8] = I("B", "bkYSg");
        GuiCreateWorld.I[0x4E ^ 0x79] = I(")/\u001f?\u0000.\u001d\u001c(\u000f>d\u00126\u000f5=05\u000e7+\u001d>\u0010", "ZJsZc");
        GuiCreateWorld.I[0xAB ^ 0x93] = I("T", "teMjj");
        GuiCreateWorld.I[0x6E ^ 0x57] = I(":\u0017\u0013\u000f\u0007;\u0014I\t\u0006", "Uggfh");
        GuiCreateWorld.I[0x7B ^ 0x41] = I("\u0018\u001c\u001e\f%\u0019\u001fD\n,\u0011", "wljeJ");
        GuiCreateWorld.I[0xE ^ 0x35] = I("\u001d\rMAa\u001b", "FQcnC");
        GuiCreateWorld.I[0x36 ^ 0xA] = I(">", "aQSqw");
        GuiCreateWorld.I[0x2F ^ 0x12] = I("\u0013", "LAMjc");
        GuiCreateWorld.I[0x87 ^ 0xB9] = I("\f", "SzrWM");
        GuiCreateWorld.I[0x22 ^ 0x1D] = I("A", "lrGKy");
        GuiCreateWorld.I[0x40 ^ 0x0] = I("\u001f\u0003<\f\u0004\u001a\u0017\"", "lvNzm");
        GuiCreateWorld.I[0xFB ^ 0xBA] = I("=,\u0014\".:?\u0003", "UMfFM");
        GuiCreateWorld.I[0x23 ^ 0x61] = I("8\u0014*\t0?\u0007=", "PuXmS");
        GuiCreateWorld.I[0x60 ^ 0x23] = I("\u00175)9;\u001d1)", "tGLXO");
        GuiCreateWorld.I[0xE3 ^ 0xA7] = I(";\"*\u0002\u000f>64", "HWXtf");
        GuiCreateWorld.I[0xC5 ^ 0x80] = I("", "CKXej");
        GuiCreateWorld.I[0x2E ^ 0x68] = I("\u0007\u0006\u0002(\r\u0015\u0002\b9", "tvgKy");
        GuiCreateWorld.I[0xCA ^ 0x8D] = I("7\u00029j\u0015?\u00195", "PwPDq");
        GuiCreateWorld.I[0x8B ^ 0xC3] = I("5.#$\u000f2\u001c 3\u0000\"e\".\u001e#\u001c 3\u0000\"\u0004?5\u0005)%<", "FKOAl");
        GuiCreateWorld.I[0x9 ^ 0x40] = I("\u001a'\r\"\r\u001d\u0015\u000e5\u0002\rl\u00025\u000b\b6\u0004", "iBaGn");
        GuiCreateWorld.I[0x2C ^ 0x66] = I("\u001c$\u0000\u0014\u000e\u001b\u0016\u0003\u0003\u0001\u000bo\t\u001f\u0019\n3?\u0014\b\u000b", "oAlqm");
        GuiCreateWorld.I[0x2E ^ 0x65] = I("\u0017\u001d\u001e#7\u0010/\u001d48\u0000V\u0001#1\u00001\u001c ;", "dxrFT");
        GuiCreateWorld.I[0x64 ^ 0x28] = I(":1\u001f\u00161=\u0003\u001c\u0001>-z\u001e\u0012\"\u000f1\u0012\u0007';1\u0000];'2\u001c", "ITssR");
        GuiCreateWorld.I[0xFE ^ 0xB3] = I("\u00000660\u0007\u00025!?\u0017{;??\u001c\"\u0019<>\u001e447 ]<45<", "sUZSS");
        GuiCreateWorld.I[0xC7 ^ 0x89] = I("\u0018\u001d+\u000b\u0013\u001f/(\u001c\u001c\u000fV\"\u0000\u0004\u000e\n\t\u000f\u001d\u000e", "kxGnp");
        GuiCreateWorld.I[0x69 ^ 0x26] = I("\u0007\u0002\u001a+\"\u00000\u0019<-\u0010I\u0004+2\u0001\u000b\u0002\b.\u0018\u0003\u0013<", "tgvNA");
        GuiCreateWorld.I[0xE8 ^ 0xB8] = I("Q", "qDsqw");
        GuiCreateWorld.I[0x94 ^ 0xC5] = I("6?4\u001d11\r7\n>!t6\u001d%\u00125*\u00146k97\b+\n<", "EZXxR");
        GuiCreateWorld.I[0x13 ^ 0x41] = I("\n\u000f<\u0005\u0019\r\u001c+", "bnNaz");
        GuiCreateWorld.I[0x9 ^ 0x5A] = I(">26:$;&(", "MGDLM");
        GuiCreateWorld.I[0x79 ^ 0x2D] = I("\b'7\u000e\u001a\u0002#7", "kURon");
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.buttonList.clear();
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (56 + 55 - 19 + 63), this.height - (0xBC ^ 0xA0), 75 + 24 - 38 + 89, 0x83 ^ 0x97, I18n.format(GuiCreateWorld.I[0x86 ^ 0x9A], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() + (0x2 ^ 0x7), this.height - (0xB1 ^ 0xAD), 65 + 17 - 25 + 93, 0x52 ^ 0x46, I18n.format(GuiCreateWorld.I[0xA6 ^ 0xBB], new Object["".length()])));
        this.buttonList.add(this.btnGameMode = new GuiButton("  ".length(), this.width / "  ".length() - (0x7B ^ 0x30), 0xCB ^ 0xB8, 0 + 23 + 103 + 24, 0x83 ^ 0x97, I18n.format(GuiCreateWorld.I[0x89 ^ 0x97], new Object["".length()])));
        this.buttonList.add(this.btnMoreOptions = new GuiButton("   ".length(), this.width / "  ".length() - (0x5B ^ 0x10), 87 + 99 - 17 + 18, 44 + 29 + 71 + 6, 0x87 ^ 0x93, I18n.format(GuiCreateWorld.I[0x39 ^ 0x26], new Object["".length()])));
        this.buttonList.add(this.btnMapFeatures = new GuiButton(0x95 ^ 0x91, this.width / "  ".length() - (38 + 29 + 23 + 65), 0xA4 ^ 0xC0, 38 + 53 - 24 + 83, 0xAE ^ 0xBA, I18n.format(GuiCreateWorld.I[0xAD ^ 0x8D], new Object["".length()])));
        this.btnMapFeatures.visible = ("".length() != 0);
        this.buttonList.add(this.btnBonusItems = new GuiButton(0xAA ^ 0xAD, this.width / "  ".length() + (0xC ^ 0x9), 75 + 109 - 49 + 16, 77 + 115 - 71 + 29, 0x36 ^ 0x22, I18n.format(GuiCreateWorld.I[0x75 ^ 0x54], new Object["".length()])));
        this.btnBonusItems.visible = ("".length() != 0);
        this.buttonList.add(this.btnMapType = new GuiButton(0xF ^ 0xA, this.width / "  ".length() + (0xB0 ^ 0xB5), 0x7 ^ 0x63, 22 + 141 - 16 + 3, 0x31 ^ 0x25, I18n.format(GuiCreateWorld.I[0x3B ^ 0x19], new Object["".length()])));
        this.btnMapType.visible = ("".length() != 0);
        this.buttonList.add(this.btnAllowCommands = new GuiButton(0xA ^ 0xC, this.width / "  ".length() - (56 + 129 - 151 + 121), 114 + 23 - 98 + 112, 61 + 4 - 61 + 146, 0x43 ^ 0x57, I18n.format(GuiCreateWorld.I[0x97 ^ 0xB4], new Object["".length()])));
        this.btnAllowCommands.visible = ("".length() != 0);
        this.buttonList.add(this.btnCustomizeType = new GuiButton(0x63 ^ 0x6B, this.width / "  ".length() + (0x24 ^ 0x21), 0x26 ^ 0x5E, 88 + 81 - 41 + 22, 0x44 ^ 0x50, I18n.format(GuiCreateWorld.I[0x8A ^ 0xAE], new Object["".length()])));
        this.btnCustomizeType.visible = ("".length() != 0);
        (this.field_146333_g = new GuiTextField(0x34 ^ 0x3D, this.fontRendererObj, this.width / "  ".length() - (0x58 ^ 0x3C), 0x12 ^ 0x2E, 163 + 146 - 289 + 180, 0x61 ^ 0x75)).setFocused(" ".length() != 0);
        this.field_146333_g.setText(this.field_146330_J);
        (this.field_146335_h = new GuiTextField(0x30 ^ 0x3A, this.fontRendererObj, this.width / "  ".length() - (0x12 ^ 0x76), 0x1A ^ 0x26, 2 + 187 - 186 + 197, 0x6B ^ 0x7F)).setText(this.field_146329_I);
        this.func_146316_a(this.field_146344_y);
        this.func_146314_g();
        this.func_146319_h();
    }
}
