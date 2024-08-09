/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nullable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class LanguageScreen
extends SettingsScreen {
    private static final ITextComponent field_243292_c = new StringTextComponent("(").append(new TranslationTextComponent("options.languageWarning")).appendString(")").mergeStyle(TextFormatting.GRAY);
    private List list;
    private final LanguageManager languageManager;
    private OptionButton field_211832_i;
    private Button confirmSettingsBtn;

    public LanguageScreen(Screen screen, GameSettings gameSettings, LanguageManager languageManager) {
        super(screen, gameSettings, new TranslationTextComponent("options.language"));
        this.languageManager = languageManager;
    }

    @Override
    protected void init() {
        this.list = new List(this, this.minecraft);
        this.children.add(this.list);
        this.field_211832_i = this.addButton(new OptionButton(this.width / 2 - 155, this.height - 38, 150, 20, AbstractOption.FORCE_UNICODE_FONT, AbstractOption.FORCE_UNICODE_FONT.func_238152_c_(this.gameSettings), this::lambda$init$0));
        this.confirmSettingsBtn = this.addButton(new Button(this.width / 2 - 155 + 160, this.height - 38, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$1));
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.list.render(matrixStack, n, n2, f);
        LanguageScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 16, 0xFFFFFF);
        LanguageScreen.drawCenteredString(matrixStack, this.font, field_243292_c, this.width / 2, this.height - 56, 0x808080);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        List.LanguageEntry languageEntry = (List.LanguageEntry)this.list.getSelected();
        if (languageEntry != null && !languageEntry.field_214398_b.getCode().equals(this.languageManager.getCurrentLanguage().getCode())) {
            this.languageManager.setCurrentLanguage(languageEntry.field_214398_b);
            this.gameSettings.language = languageEntry.field_214398_b.getCode();
            this.minecraft.reloadResources();
            this.confirmSettingsBtn.setMessage(DialogTexts.GUI_DONE);
            this.field_211832_i.setMessage(AbstractOption.FORCE_UNICODE_FONT.func_238152_c_(this.gameSettings));
            this.gameSettings.saveOptions();
        }
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$init$0(Button button) {
        AbstractOption.FORCE_UNICODE_FONT.nextValue(this.gameSettings);
        this.gameSettings.saveOptions();
        button.setMessage(AbstractOption.FORCE_UNICODE_FONT.func_238152_c_(this.gameSettings));
        this.minecraft.updateWindowSize();
    }

    class List
    extends ExtendedList<LanguageEntry> {
        final LanguageScreen this$0;

        public List(LanguageScreen languageScreen, Minecraft minecraft) {
            this.this$0 = languageScreen;
            super(minecraft, languageScreen.width, languageScreen.height, 32, languageScreen.height - 65 + 4, 18);
            for (Language language : languageScreen.languageManager.getLanguages()) {
                LanguageEntry languageEntry = new LanguageEntry(this, language);
                this.addEntry(languageEntry);
                if (!languageScreen.languageManager.getCurrentLanguage().getCode().equals(language.getCode())) continue;
                this.setSelected(languageEntry);
            }
            if (this.getSelected() != null) {
                this.centerScrollOn((LanguageEntry)this.getSelected());
            }
        }

        @Override
        protected int getScrollbarPosition() {
            return super.getScrollbarPosition() + 20;
        }

        @Override
        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        @Override
        public void setSelected(@Nullable LanguageEntry languageEntry) {
            super.setSelected(languageEntry);
            if (languageEntry != null) {
                NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.select", languageEntry.field_214398_b).getString());
            }
        }

        @Override
        protected void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        @Override
        protected boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((LanguageEntry)abstractListEntry);
        }

        static int access$000(List list) {
            return list.width;
        }

        public class LanguageEntry
        extends AbstractList.AbstractListEntry<LanguageEntry> {
            private final Language field_214398_b;
            final List this$1;

            public LanguageEntry(List list, Language language) {
                this.this$1 = list;
                this.field_214398_b = language;
            }

            @Override
            public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
                String string = this.field_214398_b.toString();
                this.this$1.this$0.font.func_238406_a_(matrixStack, string, List.access$000(this.this$1) / 2 - this.this$1.this$0.font.getStringWidth(string) / 2, n2 + 1, 0xFFFFFF, false);
            }

            @Override
            public boolean mouseClicked(double d, double d2, int n) {
                if (n == 0) {
                    this.func_214395_a();
                    return false;
                }
                return true;
            }

            private void func_214395_a() {
                this.this$1.setSelected(this);
            }
        }
    }
}

