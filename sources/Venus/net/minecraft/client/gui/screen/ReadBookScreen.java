/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;

public class ReadBookScreen
extends Screen {
    public static final IBookInfo EMPTY_BOOK = new IBookInfo(){

        @Override
        public int getPageCount() {
            return 1;
        }

        @Override
        public ITextProperties func_230456_a_(int n) {
            return ITextProperties.field_240651_c_;
        }
    };
    public static final ResourceLocation BOOK_TEXTURES = new ResourceLocation("textures/gui/book.png");
    private IBookInfo bookInfo;
    private int currPage;
    private List<IReorderingProcessor> cachedPageLines = Collections.emptyList();
    private int cachedPage = -1;
    private ITextComponent field_243344_s = StringTextComponent.EMPTY;
    private ChangePageButton buttonNextPage;
    private ChangePageButton buttonPreviousPage;
    private final boolean pageTurnSounds;

    public ReadBookScreen(IBookInfo iBookInfo) {
        this(iBookInfo, true);
    }

    public ReadBookScreen() {
        this(EMPTY_BOOK, false);
    }

    private ReadBookScreen(IBookInfo iBookInfo, boolean bl) {
        super(NarratorChatListener.EMPTY);
        this.bookInfo = iBookInfo;
        this.pageTurnSounds = bl;
    }

    public void func_214155_a(IBookInfo iBookInfo) {
        this.bookInfo = iBookInfo;
        this.currPage = MathHelper.clamp(this.currPage, 0, iBookInfo.getPageCount());
        this.updateButtons();
        this.cachedPage = -1;
    }

    public boolean showPage(int n) {
        int n2 = MathHelper.clamp(n, 0, this.bookInfo.getPageCount() - 1);
        if (n2 != this.currPage) {
            this.currPage = n2;
            this.updateButtons();
            this.cachedPage = -1;
            return false;
        }
        return true;
    }

    protected boolean showPage2(int n) {
        return this.showPage(n);
    }

    @Override
    protected void init() {
        this.addDoneButton();
        this.addChangePageButtons();
    }

    protected void addDoneButton() {
        this.addButton(new Button(this.width / 2 - 100, 196, 200, 20, DialogTexts.GUI_DONE, this::lambda$addDoneButton$0));
    }

    protected void addChangePageButtons() {
        int n = (this.width - 192) / 2;
        int n2 = 2;
        this.buttonNextPage = this.addButton(new ChangePageButton(n + 116, 159, true, this::lambda$addChangePageButtons$1, this.pageTurnSounds));
        this.buttonPreviousPage = this.addButton(new ChangePageButton(n + 43, 159, false, this::lambda$addChangePageButtons$2, this.pageTurnSounds));
        this.updateButtons();
    }

    private int getPageCount() {
        return this.bookInfo.getPageCount();
    }

    protected void previousPage() {
        if (this.currPage > 0) {
            --this.currPage;
        }
        this.updateButtons();
    }

    protected void nextPage() {
        if (this.currPage < this.getPageCount() - 1) {
            ++this.currPage;
        }
        this.updateButtons();
    }

    private void updateButtons() {
        this.buttonNextPage.visible = this.currPage < this.getPageCount() - 1;
        this.buttonPreviousPage.visible = this.currPage > 0;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        switch (n) {
            case 266: {
                this.buttonPreviousPage.onPress();
                return false;
            }
            case 267: {
                this.buttonNextPage.onPress();
                return false;
            }
        }
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BOOK_TEXTURES);
        int n3 = (this.width - 192) / 2;
        int n4 = 2;
        this.blit(matrixStack, n3, 2, 0, 0, 192, 192);
        if (this.cachedPage != this.currPage) {
            ITextProperties iTextProperties = this.bookInfo.func_238806_b_(this.currPage);
            this.cachedPageLines = this.font.trimStringToWidth(iTextProperties, 114);
            this.field_243344_s = new TranslationTextComponent("book.pageIndicator", this.currPage + 1, Math.max(this.getPageCount(), 1));
        }
        this.cachedPage = this.currPage;
        int n5 = this.font.getStringPropertyWidth(this.field_243344_s);
        this.font.func_243248_b(matrixStack, this.field_243344_s, n3 - n5 + 192 - 44, 18.0f, 0);
        int n6 = Math.min(14, this.cachedPageLines.size());
        for (int i = 0; i < n6; ++i) {
            IReorderingProcessor iReorderingProcessor = this.cachedPageLines.get(i);
            this.font.func_238422_b_(matrixStack, iReorderingProcessor, n3 + 36, 32 + i * 9, 0);
        }
        Style style = this.func_238805_a_(n, n2);
        if (style != null) {
            this.renderComponentHoverEffect(matrixStack, style, n, n2);
        }
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        Style style;
        if (n == 0 && (style = this.func_238805_a_(d, d2)) != null && this.handleComponentClicked(style)) {
            return false;
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean handleComponentClicked(Style style) {
        ClickEvent clickEvent = style.getClickEvent();
        if (clickEvent == null) {
            return true;
        }
        if (clickEvent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String string = clickEvent.getValue();
            try {
                int n = Integer.parseInt(string) - 1;
                return this.showPage2(n);
            } catch (Exception exception) {
                return true;
            }
        }
        boolean bl = super.handleComponentClicked(style);
        if (bl && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.minecraft.displayGuiScreen(null);
        }
        return bl;
    }

    @Nullable
    public Style func_238805_a_(double d, double d2) {
        if (this.cachedPageLines.isEmpty()) {
            return null;
        }
        int n = MathHelper.floor(d - (double)((this.width - 192) / 2) - 36.0);
        int n2 = MathHelper.floor(d2 - 2.0 - 30.0);
        if (n >= 0 && n2 >= 0) {
            int n3 = Math.min(14, this.cachedPageLines.size());
            if (n <= 114 && n2 < 9 * n3 + n3) {
                int n4 = n2 / 9;
                if (n4 >= 0 && n4 < this.cachedPageLines.size()) {
                    IReorderingProcessor iReorderingProcessor = this.cachedPageLines.get(n4);
                    return this.minecraft.fontRenderer.getCharacterManager().func_243239_a(iReorderingProcessor, n);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public static List<String> nbtPagesToStrings(CompoundNBT compoundNBT) {
        ListNBT listNBT = compoundNBT.getList("pages", 8).copy();
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < listNBT.size(); ++i) {
            builder.add(listNBT.getString(i));
        }
        return builder.build();
    }

    private void lambda$addChangePageButtons$2(Button button) {
        this.previousPage();
    }

    private void lambda$addChangePageButtons$1(Button button) {
        this.nextPage();
    }

    private void lambda$addDoneButton$0(Button button) {
        this.minecraft.displayGuiScreen(null);
    }

    public static interface IBookInfo {
        public int getPageCount();

        public ITextProperties func_230456_a_(int var1);

        default public ITextProperties func_238806_b_(int n) {
            return n >= 0 && n < this.getPageCount() ? this.func_230456_a_(n) : ITextProperties.field_240651_c_;
        }

        public static IBookInfo func_216917_a(ItemStack itemStack) {
            Item item = itemStack.getItem();
            if (item == Items.WRITTEN_BOOK) {
                return new WrittenBookInfo(itemStack);
            }
            return item == Items.WRITABLE_BOOK ? new UnwrittenBookInfo(itemStack) : EMPTY_BOOK;
        }
    }

    public static class WrittenBookInfo
    implements IBookInfo {
        private final List<String> pages;

        public WrittenBookInfo(ItemStack itemStack) {
            this.pages = WrittenBookInfo.func_216921_b(itemStack);
        }

        private static List<String> func_216921_b(ItemStack itemStack) {
            CompoundNBT compoundNBT = itemStack.getTag();
            return compoundNBT != null && WrittenBookItem.validBookTagContents(compoundNBT) ? ReadBookScreen.nbtPagesToStrings(compoundNBT) : ImmutableList.of(ITextComponent.Serializer.toJson(new TranslationTextComponent("book.invalid.tag").mergeStyle(TextFormatting.DARK_RED)));
        }

        @Override
        public int getPageCount() {
            return this.pages.size();
        }

        @Override
        public ITextProperties func_230456_a_(int n) {
            String string = this.pages.get(n);
            try {
                IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(string);
                if (iFormattableTextComponent != null) {
                    return iFormattableTextComponent;
                }
            } catch (Exception exception) {
                // empty catch block
            }
            return ITextProperties.func_240652_a_(string);
        }
    }

    public static class UnwrittenBookInfo
    implements IBookInfo {
        private final List<String> pages;

        public UnwrittenBookInfo(ItemStack itemStack) {
            this.pages = UnwrittenBookInfo.func_216919_b(itemStack);
        }

        private static List<String> func_216919_b(ItemStack itemStack) {
            CompoundNBT compoundNBT = itemStack.getTag();
            return compoundNBT != null ? ReadBookScreen.nbtPagesToStrings(compoundNBT) : ImmutableList.of();
        }

        @Override
        public int getPageCount() {
            return this.pages.size();
        }

        @Override
        public ITextProperties func_230456_a_(int n) {
            return ITextProperties.func_240652_a_(this.pages.get(n));
        }
    }
}

