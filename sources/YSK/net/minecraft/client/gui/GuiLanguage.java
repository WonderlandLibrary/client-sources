package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import java.util.*;

public class GuiLanguage extends GuiScreen
{
    private final GameSettings game_settings_3;
    private List list;
    private GuiOptionButton confirmSettingsBtn;
    private GuiOptionButton forceUnicodeFontBtn;
    private final LanguageManager languageManager;
    protected GuiScreen parentScreen;
    private static final String[] I;
    
    static LanguageManager access$0(final GuiLanguage guiLanguage) {
        return guiLanguage.languageManager;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.list.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, I18n.format(GuiLanguage.I[" ".length()], new Object["".length()]), this.width / "  ".length(), 0x30 ^ 0x20, 3716238 + 15846581 - 9562214 + 6776610);
        this.drawCenteredString(this.fontRendererObj, GuiLanguage.I["  ".length()] + I18n.format(GuiLanguage.I["   ".length()], new Object["".length()]) + GuiLanguage.I[0x89 ^ 0x8D], this.width / "  ".length(), this.height - (0x49 ^ 0x71), 4443731 + 8040159 - 4189473 + 127087);
        super.drawScreen(n, n2, n3);
    }
    
    static GameSettings access$1(final GuiLanguage guiLanguage) {
        return guiLanguage.game_settings_3;
    }
    
    static GuiOptionButton access$2(final GuiLanguage guiLanguage) {
        return guiLanguage.confirmSettingsBtn;
    }
    
    private static void I() {
        (I = new String[0x80 ^ 0x85])["".length()] = I("\u0015\u001c\u000fK\"\u001d\u0007\u0003", "rifeF");
        GuiLanguage.I[" ".length()] = I("\u0005\u0017\u0017$\u001a\u0004\u0014M!\u0014\u0004\u0000\u0016,\u0012\u000f", "jgcMu");
        GuiLanguage.I["  ".length()] = I("J", "bFkWt");
        GuiLanguage.I["   ".length()] = I("%\b\u0018\u0006\u001b$\u000bB\u0003\u0015$\u001f\u0019\u000e\u0013//\r\u001d\u001a#\u0016\u000b", "Jxlot");
        GuiLanguage.I[0x6C ^ 0x68] = I("k", "BSQFT");
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(this.forceUnicodeFontBtn = new GuiOptionButton(0x59 ^ 0x3D, this.width / "  ".length() - (131 + 15 - 5 + 14), this.height - (0x4E ^ 0x68), GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
        this.buttonList.add(this.confirmSettingsBtn = new GuiOptionButton(0xB ^ 0xD, this.width / "  ".length() - (58 + 29 - 15 + 83) + (25 + 140 - 65 + 60), this.height - (0x22 ^ 0x4), I18n.format(GuiLanguage.I["".length()], new Object["".length()])));
        (this.list = new List(this.mc)).registerScrollButtons(0x3C ^ 0x3B, 0x16 ^ 0x1E);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 5: {
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.parentScreen);
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                    break;
                }
                case 100: {
                    if (!(guiButton instanceof GuiOptionButton)) {
                        break;
                    }
                    this.game_settings_3.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), " ".length());
                    guiButton.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                    final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                    this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.list.actionPerformed(guiButton);
                    break;
                }
            }
        }
    }
    
    static GuiOptionButton access$3(final GuiLanguage guiLanguage) {
        return guiLanguage.forceUnicodeFontBtn;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }
    
    public GuiLanguage(final GuiScreen parentScreen, final GameSettings game_settings_3, final LanguageManager languageManager) {
        this.parentScreen = parentScreen;
        this.game_settings_3 = game_settings_3;
        this.languageManager = languageManager;
    }
    
    static {
        I();
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    class List extends GuiSlot
    {
        private final Map<String, Language> languageMap;
        private static final String[] I;
        private final java.util.List<String> langCodeList;
        final GuiLanguage this$0;
        
        @Override
        protected boolean isSelected(final int n) {
            return this.langCodeList.get(n).equals(GuiLanguage.access$0(this.this$0).getCurrentLanguage().getLanguageCode());
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            final Language currentLanguage = this.languageMap.get(this.langCodeList.get(n));
            GuiLanguage.access$0(this.this$0).setCurrentLanguage(currentLanguage);
            GuiLanguage.access$1(this.this$0).language = currentLanguage.getLanguageCode();
            this.mc.refreshResources();
            final FontRenderer fontRendererObj = this.this$0.fontRendererObj;
            int unicodeFlag;
            if (!GuiLanguage.access$0(this.this$0).isCurrentLocaleUnicode() && !GuiLanguage.access$1(this.this$0).forceUnicodeFont) {
                unicodeFlag = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                unicodeFlag = " ".length();
            }
            fontRendererObj.setUnicodeFlag(unicodeFlag != 0);
            this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$0(this.this$0).isCurrentLanguageBidirectional());
            GuiLanguage.access$2(this.this$0).displayString = I18n.format(List.I["".length()], new Object["".length()]);
            GuiLanguage.access$3(this.this$0).displayString = GuiLanguage.access$1(this.this$0).getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
            GuiLanguage.access$1(this.this$0).saveOptions();
        }
        
        public List(final GuiLanguage this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, this$0.width, this$0.height, 0xE1 ^ 0xC1, this$0.height - (0xD3 ^ 0x92) + (0x7F ^ 0x7B), 0xAC ^ 0xBE);
            this.langCodeList = (java.util.List<String>)Lists.newArrayList();
            this.languageMap = (Map<String, Language>)Maps.newHashMap();
            final Iterator<Language> iterator = GuiLanguage.access$0(this$0).getLanguages().iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Language language = iterator.next();
                this.languageMap.put(language.getLanguageCode(), language);
                this.langCodeList.add(language.getLanguageCode());
            }
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * (0x71 ^ 0x63);
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected int getSize() {
            return this.langCodeList.size();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.this$0.fontRendererObj.setBidiFlag(" ".length() != 0);
            this.this$0.drawCenteredString(this.this$0.fontRendererObj, this.languageMap.get(this.langCodeList.get(n)).toString(), this.width / "  ".length(), n3 + " ".length(), 1373366 + 11928820 - 2576355 + 6051384);
            this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$0(this.this$0).getCurrentLanguage().isBidirectional());
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("1\f>T%9\u00172", "VyWzA");
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
        
        static {
            I();
        }
    }
}
