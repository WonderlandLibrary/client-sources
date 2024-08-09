/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.play.client.CEditBookPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class EditBookScreen
extends Screen {
    private static final ITextComponent field_243340_a = new TranslationTextComponent("book.editTitle");
    private static final ITextComponent field_243341_b = new TranslationTextComponent("book.finalizeWarning");
    private static final IReorderingProcessor field_243342_c = IReorderingProcessor.fromString("_", Style.EMPTY.setFormatting(TextFormatting.BLACK));
    private static final IReorderingProcessor field_243343_p = IReorderingProcessor.fromString("_", Style.EMPTY.setFormatting(TextFormatting.GRAY));
    private final PlayerEntity editingPlayer;
    private final ItemStack book;
    private boolean bookIsModified;
    private boolean bookGettingSigned;
    private int updateCount;
    private int currPage;
    private final List<String> bookPages = Lists.newArrayList();
    private String bookTitle = "";
    private final TextInputUtil field_238748_u_ = new TextInputUtil(this::getCurrPageText, this::func_214217_j, this::func_238773_g_, this::func_238760_a_, this::lambda$new$0);
    private final TextInputUtil field_238749_v_ = new TextInputUtil(this::lambda$new$1, this::lambda$new$2, this::func_238773_g_, this::func_238760_a_, EditBookScreen::lambda$new$3);
    private long lastClickTime;
    private int cachedPage = -1;
    private ChangePageButton buttonNextPage;
    private ChangePageButton buttonPreviousPage;
    private Button buttonDone;
    private Button buttonSign;
    private Button buttonFinalize;
    private Button buttonCancel;
    private final Hand hand;
    @Nullable
    private BookPage field_238747_F_ = BookPage.field_238779_a_;
    private ITextComponent field_243338_K = StringTextComponent.EMPTY;
    private final ITextComponent field_243339_L;

    public EditBookScreen(PlayerEntity playerEntity, ItemStack itemStack, Hand hand) {
        super(NarratorChatListener.EMPTY);
        this.editingPlayer = playerEntity;
        this.book = itemStack;
        this.hand = hand;
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null) {
            ListNBT listNBT = compoundNBT.getList("pages", 8).copy();
            for (int i = 0; i < listNBT.size(); ++i) {
                this.bookPages.add(listNBT.getString(i));
            }
        }
        if (this.bookPages.isEmpty()) {
            this.bookPages.add("");
        }
        this.field_243339_L = new TranslationTextComponent("book.byAuthor", playerEntity.getName()).mergeStyle(TextFormatting.DARK_GRAY);
    }

    private void func_238760_a_(String string) {
        if (this.minecraft != null) {
            TextInputUtil.setClipboardText(this.minecraft, string);
        }
    }

    private String func_238773_g_() {
        return this.minecraft != null ? TextInputUtil.getClipboardText(this.minecraft) : "";
    }

    private int getPageCount() {
        return this.bookPages.size();
    }

    @Override
    public void tick() {
        super.tick();
        ++this.updateCount;
    }

    @Override
    protected void init() {
        this.func_238751_C_();
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.buttonSign = this.addButton(new Button(this.width / 2 - 100, 196, 98, 20, new TranslationTextComponent("book.signButton"), this::lambda$init$4));
        this.buttonDone = this.addButton(new Button(this.width / 2 + 2, 196, 98, 20, DialogTexts.GUI_DONE, this::lambda$init$5));
        this.buttonFinalize = this.addButton(new Button(this.width / 2 - 100, 196, 98, 20, new TranslationTextComponent("book.finalizeButton"), this::lambda$init$6));
        this.buttonCancel = this.addButton(new Button(this.width / 2 + 2, 196, 98, 20, DialogTexts.GUI_CANCEL, this::lambda$init$7));
        int n = (this.width - 192) / 2;
        int n2 = 2;
        this.buttonNextPage = this.addButton(new ChangePageButton(n + 116, 159, true, this::lambda$init$8, true));
        this.buttonPreviousPage = this.addButton(new ChangePageButton(n + 43, 159, false, this::lambda$init$9, true));
        this.updateButtons();
    }

    private void previousPage() {
        if (this.currPage > 0) {
            --this.currPage;
        }
        this.updateButtons();
        this.func_238752_D_();
    }

    private void nextPage() {
        if (this.currPage < this.getPageCount() - 1) {
            ++this.currPage;
        } else {
            this.addNewPage();
            if (this.currPage < this.getPageCount() - 1) {
                ++this.currPage;
            }
        }
        this.updateButtons();
        this.func_238752_D_();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void updateButtons() {
        this.buttonPreviousPage.visible = !this.bookGettingSigned && this.currPage > 0;
        this.buttonNextPage.visible = !this.bookGettingSigned;
        this.buttonDone.visible = !this.bookGettingSigned;
        this.buttonSign.visible = !this.bookGettingSigned;
        this.buttonCancel.visible = this.bookGettingSigned;
        this.buttonFinalize.visible = this.bookGettingSigned;
        this.buttonFinalize.active = !this.bookTitle.trim().isEmpty();
    }

    private void trimEmptyPages() {
        ListIterator<String> listIterator2 = this.bookPages.listIterator(this.bookPages.size());
        while (listIterator2.hasPrevious() && listIterator2.previous().isEmpty()) {
            listIterator2.remove();
        }
    }

    private void sendBookToServer(boolean bl) {
        if (this.bookIsModified) {
            this.trimEmptyPages();
            ListNBT listNBT = new ListNBT();
            this.bookPages.stream().map(StringNBT::valueOf).forEach(listNBT::add);
            if (!this.bookPages.isEmpty()) {
                this.book.setTagInfo("pages", listNBT);
            }
            if (bl) {
                this.book.setTagInfo("author", StringNBT.valueOf(this.editingPlayer.getGameProfile().getName()));
                this.book.setTagInfo("title", StringNBT.valueOf(this.bookTitle.trim()));
            }
            int n = this.hand == Hand.MAIN_HAND ? this.editingPlayer.inventory.currentItem : 40;
            this.minecraft.getConnection().sendPacket(new CEditBookPacket(this.book, bl, n));
        }
    }

    private void addNewPage() {
        if (this.getPageCount() < 100) {
            this.bookPages.add("");
            this.bookIsModified = true;
        }
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (this.bookGettingSigned) {
            return this.keyPressedInTitle(n, n2, n3);
        }
        boolean bl = this.keyPressedInBook(n, n2, n3);
        if (bl) {
            this.func_238751_C_();
            return false;
        }
        return true;
    }

    @Override
    public boolean charTyped(char c, int n) {
        if (super.charTyped(c, n)) {
            return false;
        }
        if (this.bookGettingSigned) {
            boolean bl = this.field_238749_v_.putChar(c);
            if (bl) {
                this.updateButtons();
                this.bookIsModified = true;
                return false;
            }
            return true;
        }
        if (SharedConstants.isAllowedCharacter(c)) {
            this.field_238748_u_.putText(Character.toString(c));
            this.func_238751_C_();
            return false;
        }
        return true;
    }

    private boolean keyPressedInBook(int n, int n2, int n3) {
        if (Screen.isSelectAll(n)) {
            this.field_238748_u_.selectAll();
            return false;
        }
        if (Screen.isCopy(n)) {
            this.field_238748_u_.copySelectedText();
            return false;
        }
        if (Screen.isPaste(n)) {
            this.field_238748_u_.insertClipboardText();
            return false;
        }
        if (Screen.isCut(n)) {
            this.field_238748_u_.cutText();
            return false;
        }
        switch (n) {
            case 257: 
            case 335: {
                this.field_238748_u_.putText("\n");
                return false;
            }
            case 259: {
                this.field_238748_u_.deleteCharAtSelection(-1);
                return false;
            }
            case 261: {
                this.field_238748_u_.deleteCharAtSelection(1);
                return false;
            }
            case 262: {
                this.field_238748_u_.moveCursorByChar(1, Screen.hasShiftDown());
                return false;
            }
            case 263: {
                this.field_238748_u_.moveCursorByChar(-1, Screen.hasShiftDown());
                return false;
            }
            case 264: {
                this.func_238776_x_();
                return false;
            }
            case 265: {
                this.func_238775_w_();
                return false;
            }
            case 266: {
                this.buttonPreviousPage.onPress();
                return false;
            }
            case 267: {
                this.buttonNextPage.onPress();
                return false;
            }
            case 268: {
                this.func_238777_y_();
                return false;
            }
            case 269: {
                this.func_238778_z_();
                return false;
            }
        }
        return true;
    }

    private void func_238775_w_() {
        this.func_238755_a_(-1);
    }

    private void func_238776_x_() {
        this.func_238755_a_(1);
    }

    private void func_238755_a_(int n) {
        int n2 = this.field_238748_u_.getEndIndex();
        int n3 = this.func_238750_B_().func_238788_a_(n2, n);
        this.field_238748_u_.moveCursorTo(n3, Screen.hasShiftDown());
    }

    private void func_238777_y_() {
        int n = this.field_238748_u_.getEndIndex();
        int n2 = this.func_238750_B_().func_238787_a_(n);
        this.field_238748_u_.moveCursorTo(n2, Screen.hasShiftDown());
    }

    private void func_238778_z_() {
        BookPage bookPage = this.func_238750_B_();
        int n = this.field_238748_u_.getEndIndex();
        int n2 = bookPage.func_238791_b_(n);
        this.field_238748_u_.moveCursorTo(n2, Screen.hasShiftDown());
    }

    private boolean keyPressedInTitle(int n, int n2, int n3) {
        switch (n) {
            case 257: 
            case 335: {
                if (!this.bookTitle.isEmpty()) {
                    this.sendBookToServer(false);
                    this.minecraft.displayGuiScreen(null);
                }
                return false;
            }
            case 259: {
                this.field_238749_v_.deleteCharAtSelection(-1);
                this.updateButtons();
                this.bookIsModified = true;
                return false;
            }
        }
        return true;
    }

    private String getCurrPageText() {
        return this.currPage >= 0 && this.currPage < this.bookPages.size() ? this.bookPages.get(this.currPage) : "";
    }

    private void func_214217_j(String string) {
        if (this.currPage >= 0 && this.currPage < this.bookPages.size()) {
            this.bookPages.set(this.currPage, string);
            this.bookIsModified = true;
            this.func_238751_C_();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.setListener(null);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(ReadBookScreen.BOOK_TEXTURES);
        int n3 = (this.width - 192) / 2;
        int n4 = 2;
        this.blit(matrixStack, n3, 2, 0, 0, 192, 192);
        if (this.bookGettingSigned) {
            boolean bl = this.updateCount / 6 % 2 == 0;
            IReorderingProcessor iReorderingProcessor = IReorderingProcessor.func_242234_a(IReorderingProcessor.fromString(this.bookTitle, Style.EMPTY), bl ? field_243342_c : field_243343_p);
            int n5 = this.font.getStringPropertyWidth(field_243340_a);
            this.font.func_243248_b(matrixStack, field_243340_a, n3 + 36 + (114 - n5) / 2, 34.0f, 0);
            int n6 = this.font.func_243245_a(iReorderingProcessor);
            this.font.func_238422_b_(matrixStack, iReorderingProcessor, n3 + 36 + (114 - n6) / 2, 50.0f, 0);
            int n7 = this.font.getStringPropertyWidth(this.field_243339_L);
            this.font.func_243248_b(matrixStack, this.field_243339_L, n3 + 36 + (114 - n7) / 2, 60.0f, 0);
            this.font.func_238418_a_(field_243341_b, n3 + 36, 82, 114, 0);
        } else {
            int n8 = this.font.getStringPropertyWidth(this.field_243338_K);
            this.font.func_243248_b(matrixStack, this.field_243338_K, n3 - n8 + 192 - 44, 18.0f, 0);
            BookPage bookPage = this.func_238750_B_();
            for (BookLine bookLine : bookPage.field_238784_f_) {
                this.font.func_243248_b(matrixStack, bookLine.field_238797_c_, bookLine.field_238798_d_, bookLine.field_238799_e_, -16777216);
            }
            this.func_238764_a_(bookPage.field_238785_g_);
            this.func_238756_a_(matrixStack, bookPage.field_238781_c_, bookPage.field_238782_d_);
        }
        super.render(matrixStack, n, n2, f);
    }

    private void func_238756_a_(MatrixStack matrixStack, Point point, boolean bl) {
        if (this.updateCount / 6 % 2 == 0) {
            point = this.func_238767_b_(point);
            if (!bl) {
                AbstractGui.fill(matrixStack, point.x, point.y - 1, point.x + 1, point.y + 9, -16777216);
            } else {
                this.font.drawString(matrixStack, "_", point.x, point.y, 0);
            }
        }
    }

    private void func_238764_a_(Rectangle2d[] rectangle2dArray) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.color4f(0.0f, 0.0f, 255.0f, 255.0f);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        for (Rectangle2d rectangle2d : rectangle2dArray) {
            int n = rectangle2d.getX();
            int n2 = rectangle2d.getY();
            int n3 = n + rectangle2d.getWidth();
            int n4 = n2 + rectangle2d.getHeight();
            bufferBuilder.pos(n, n4, 0.0).endVertex();
            bufferBuilder.pos(n3, n4, 0.0).endVertex();
            bufferBuilder.pos(n3, n2, 0.0).endVertex();
            bufferBuilder.pos(n, n2, 0.0).endVertex();
        }
        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    private Point func_238758_a_(Point point) {
        return new Point(point.x - (this.width - 192) / 2 - 36, point.y - 32);
    }

    private Point func_238767_b_(Point point) {
        return new Point(point.x + (this.width - 192) / 2 + 36, point.y + 32);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (super.mouseClicked(d, d2, n)) {
            return false;
        }
        if (n == 0) {
            long l = Util.milliTime();
            BookPage bookPage = this.func_238750_B_();
            int n2 = bookPage.func_238789_a_(this.font, this.func_238758_a_(new Point((int)d, (int)d2)));
            if (n2 >= 0) {
                if (n2 == this.cachedPage && l - this.lastClickTime < 250L) {
                    if (!this.field_238748_u_.hasSelection()) {
                        this.func_238765_b_(n2);
                    } else {
                        this.field_238748_u_.selectAll();
                    }
                } else {
                    this.field_238748_u_.moveCursorTo(n2, Screen.hasShiftDown());
                }
                this.func_238751_C_();
            }
            this.cachedPage = n2;
            this.lastClickTime = l;
        }
        return false;
    }

    private void func_238765_b_(int n) {
        String string = this.getCurrPageText();
        this.field_238748_u_.setSelection(CharacterManager.func_238351_a_(string, -1, n, false), CharacterManager.func_238351_a_(string, 1, n, false));
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (super.mouseDragged(d, d2, n, d3, d4)) {
            return false;
        }
        if (n == 0) {
            BookPage bookPage = this.func_238750_B_();
            int n2 = bookPage.func_238789_a_(this.font, this.func_238758_a_(new Point((int)d, (int)d2)));
            this.field_238748_u_.moveCursorTo(n2, false);
            this.func_238751_C_();
        }
        return false;
    }

    private BookPage func_238750_B_() {
        if (this.field_238747_F_ == null) {
            this.field_238747_F_ = this.func_238753_E_();
            this.field_243338_K = new TranslationTextComponent("book.pageIndicator", this.currPage + 1, this.getPageCount());
        }
        return this.field_238747_F_;
    }

    private void func_238751_C_() {
        this.field_238747_F_ = null;
    }

    private void func_238752_D_() {
        this.field_238748_u_.moveCursorToEnd();
        this.func_238751_C_();
    }

    private BookPage func_238753_E_() {
        int n;
        Point point;
        boolean bl;
        String string = this.getCurrPageText();
        if (string.isEmpty()) {
            return BookPage.field_238779_a_;
        }
        int n2 = this.field_238748_u_.getEndIndex();
        int n3 = this.field_238748_u_.getStartIndex();
        IntArrayList intArrayList = new IntArrayList();
        ArrayList arrayList = Lists.newArrayList();
        MutableInt mutableInt = new MutableInt();
        MutableBoolean mutableBoolean = new MutableBoolean();
        CharacterManager characterManager = this.font.getCharacterManager();
        characterManager.func_238353_a_(string, 114, Style.EMPTY, true, (arg_0, arg_1, arg_2) -> this.lambda$func_238753_E_$10(mutableInt, string, mutableBoolean, intArrayList, arrayList, arg_0, arg_1, arg_2));
        int[] nArray = intArrayList.toIntArray();
        boolean bl2 = bl = n2 == string.length();
        if (bl && mutableBoolean.isTrue()) {
            point = new Point(0, arrayList.size() * 9);
        } else {
            int n4 = EditBookScreen.func_238768_b_(nArray, n2);
            n = this.font.getStringWidth(string.substring(nArray[n4], n2));
            point = new Point(n, n4 * 9);
        }
        ArrayList<Rectangle2d> arrayList2 = Lists.newArrayList();
        if (n2 != n3) {
            int n5;
            n = Math.min(n2, n3);
            int n6 = Math.max(n2, n3);
            int n7 = EditBookScreen.func_238768_b_(nArray, n);
            if (n7 == (n5 = EditBookScreen.func_238768_b_(nArray, n6))) {
                int n8 = n7 * 9;
                int n9 = nArray[n7];
                arrayList2.add(this.func_238761_a_(string, characterManager, n, n6, n8, n9));
            } else {
                int n10 = n7 + 1 > nArray.length ? string.length() : nArray[n7 + 1];
                arrayList2.add(this.func_238761_a_(string, characterManager, n, n10, n7 * 9, nArray[n7]));
                for (int i = n7 + 1; i < n5; ++i) {
                    int n11 = i * 9;
                    String string2 = string.substring(nArray[i], nArray[i + 1]);
                    int n12 = (int)characterManager.func_238350_a_(string2);
                    arrayList2.add(this.func_238759_a_(new Point(0, n11), new Point(n12, n11 + 9)));
                }
                arrayList2.add(this.func_238761_a_(string, characterManager, nArray[n5], n6, n5 * 9, nArray[n5]));
            }
        }
        return new BookPage(string, point, bl, nArray, arrayList.toArray(new BookLine[0]), arrayList2.toArray(new Rectangle2d[0]));
    }

    private static int func_238768_b_(int[] nArray, int n) {
        int n2 = Arrays.binarySearch(nArray, n);
        return n2 < 0 ? -(n2 + 2) : n2;
    }

    private Rectangle2d func_238761_a_(String string, CharacterManager characterManager, int n, int n2, int n3, int n4) {
        String string2 = string.substring(n4, n);
        String string3 = string.substring(n4, n2);
        Point point = new Point((int)characterManager.func_238350_a_(string2), n3);
        Point point2 = new Point((int)characterManager.func_238350_a_(string3), n3 + 9);
        return this.func_238759_a_(point, point2);
    }

    private Rectangle2d func_238759_a_(Point point, Point point2) {
        Point point3 = this.func_238767_b_(point);
        Point point4 = this.func_238767_b_(point2);
        int n = Math.min(point3.x, point4.x);
        int n2 = Math.max(point3.x, point4.x);
        int n3 = Math.min(point3.y, point4.y);
        int n4 = Math.max(point3.y, point4.y);
        return new Rectangle2d(n, n3, n2 - n, n4 - n3);
    }

    private void lambda$func_238753_E_$10(MutableInt mutableInt, String string, MutableBoolean mutableBoolean, IntList intList, List list, Style style, int n, int n2) {
        int n3 = mutableInt.getAndIncrement();
        String string2 = string.substring(n, n2);
        mutableBoolean.setValue(string2.endsWith("\n"));
        String string3 = StringUtils.stripEnd(string2, " \n");
        int n4 = n3 * 9;
        Point point = this.func_238767_b_(new Point(0, n4));
        intList.add(n);
        list.add(new BookLine(style, string3, point.x, point.y));
    }

    private void lambda$init$9(Button button) {
        this.previousPage();
    }

    private void lambda$init$8(Button button) {
        this.nextPage();
    }

    private void lambda$init$7(Button button) {
        if (this.bookGettingSigned) {
            this.bookGettingSigned = false;
        }
        this.updateButtons();
    }

    private void lambda$init$6(Button button) {
        if (this.bookGettingSigned) {
            this.sendBookToServer(false);
            this.minecraft.displayGuiScreen(null);
        }
    }

    private void lambda$init$5(Button button) {
        this.minecraft.displayGuiScreen(null);
        this.sendBookToServer(true);
    }

    private void lambda$init$4(Button button) {
        this.bookGettingSigned = true;
        this.updateButtons();
    }

    private static boolean lambda$new$3(String string) {
        return string.length() < 16;
    }

    private void lambda$new$2(String string) {
        this.bookTitle = string;
    }

    private String lambda$new$1() {
        return this.bookTitle;
    }

    private boolean lambda$new$0(String string) {
        return string.length() < 1024 && this.font.getWordWrappedHeight(string, 114) <= 128;
    }

    static class BookPage {
        private static final BookPage field_238779_a_ = new BookPage("", new Point(0, 0), true, new int[]{0}, new BookLine[]{new BookLine(Style.EMPTY, "", 0, 0)}, new Rectangle2d[0]);
        private final String field_238780_b_;
        private final Point field_238781_c_;
        private final boolean field_238782_d_;
        private final int[] field_238783_e_;
        private final BookLine[] field_238784_f_;
        private final Rectangle2d[] field_238785_g_;

        public BookPage(String string, Point point, boolean bl, int[] nArray, BookLine[] bookLineArray, Rectangle2d[] rectangle2dArray) {
            this.field_238780_b_ = string;
            this.field_238781_c_ = point;
            this.field_238782_d_ = bl;
            this.field_238783_e_ = nArray;
            this.field_238784_f_ = bookLineArray;
            this.field_238785_g_ = rectangle2dArray;
        }

        public int func_238789_a_(FontRenderer fontRenderer, Point point) {
            int n = point.y / 9;
            if (n < 0) {
                return 1;
            }
            if (n >= this.field_238784_f_.length) {
                return this.field_238780_b_.length();
            }
            BookLine bookLine = this.field_238784_f_[n];
            return this.field_238783_e_[n] + fontRenderer.getCharacterManager().func_238352_a_(bookLine.field_238796_b_, point.x, bookLine.field_238795_a_);
        }

        public int func_238788_a_(int n, int n2) {
            int n3;
            int n4 = EditBookScreen.func_238768_b_(this.field_238783_e_, n);
            int n5 = n4 + n2;
            if (0 <= n5 && n5 < this.field_238783_e_.length) {
                int n6 = n - this.field_238783_e_[n4];
                int n7 = this.field_238784_f_[n5].field_238796_b_.length();
                n3 = this.field_238783_e_[n5] + Math.min(n6, n7);
            } else {
                n3 = n;
            }
            return n3;
        }

        public int func_238787_a_(int n) {
            int n2 = EditBookScreen.func_238768_b_(this.field_238783_e_, n);
            return this.field_238783_e_[n2];
        }

        public int func_238791_b_(int n) {
            int n2 = EditBookScreen.func_238768_b_(this.field_238783_e_, n);
            return this.field_238783_e_[n2] + this.field_238784_f_[n2].field_238796_b_.length();
        }
    }

    static class BookLine {
        private final Style field_238795_a_;
        private final String field_238796_b_;
        private final ITextComponent field_238797_c_;
        private final int field_238798_d_;
        private final int field_238799_e_;

        public BookLine(Style style, String string, int n, int n2) {
            this.field_238795_a_ = style;
            this.field_238796_b_ = string;
            this.field_238798_d_ = n;
            this.field_238799_e_ = n2;
            this.field_238797_c_ = new StringTextComponent(string).setStyle(style);
        }
    }

    static class Point {
        public final int x;
        public final int y;

        Point(int n, int n2) {
            this.x = n;
            this.y = n2;
        }
    }
}

