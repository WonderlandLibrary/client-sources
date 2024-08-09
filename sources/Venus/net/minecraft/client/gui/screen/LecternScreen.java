/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.LecternContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LecternScreen
extends ReadBookScreen
implements IHasContainer<LecternContainer> {
    private final LecternContainer field_214182_c;
    private final IContainerListener field_214183_d = new IContainerListener(this){
        final LecternScreen this$0;
        {
            this.this$0 = lecternScreen;
        }

        @Override
        public void sendAllContents(Container container, NonNullList<ItemStack> nonNullList) {
            this.this$0.func_214175_g();
        }

        @Override
        public void sendSlotContents(Container container, int n, ItemStack itemStack) {
            this.this$0.func_214175_g();
        }

        @Override
        public void sendWindowProperty(Container container, int n, int n2) {
            if (n == 0) {
                this.this$0.func_214176_h();
            }
        }
    };

    public LecternScreen(LecternContainer lecternContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        this.field_214182_c = lecternContainer;
    }

    @Override
    public LecternContainer getContainer() {
        return this.field_214182_c;
    }

    @Override
    protected void init() {
        super.init();
        this.field_214182_c.addListener(this.field_214183_d);
    }

    @Override
    public void closeScreen() {
        this.minecraft.player.closeScreen();
        super.closeScreen();
    }

    @Override
    public void onClose() {
        super.onClose();
        this.field_214182_c.removeListener(this.field_214183_d);
    }

    @Override
    protected void addDoneButton() {
        if (this.minecraft.player.isAllowEdit()) {
            this.addButton(new Button(this.width / 2 - 100, 196, 98, 20, DialogTexts.GUI_DONE, this::lambda$addDoneButton$0));
            this.addButton(new Button(this.width / 2 + 2, 196, 98, 20, new TranslationTextComponent("lectern.take_book"), this::lambda$addDoneButton$1));
        } else {
            super.addDoneButton();
        }
    }

    @Override
    protected void previousPage() {
        this.func_214179_c(1);
    }

    @Override
    protected void nextPage() {
        this.func_214179_c(2);
    }

    @Override
    protected boolean showPage2(int n) {
        if (n != this.field_214182_c.getPage()) {
            this.func_214179_c(100 + n);
            return false;
        }
        return true;
    }

    private void func_214179_c(int n) {
        this.minecraft.playerController.sendEnchantPacket(this.field_214182_c.windowId, n);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private void func_214175_g() {
        ItemStack itemStack = this.field_214182_c.getBook();
        this.func_214155_a(ReadBookScreen.IBookInfo.func_216917_a(itemStack));
    }

    private void func_214176_h() {
        this.showPage(this.field_214182_c.getPage());
    }

    @Override
    public Container getContainer() {
        return this.getContainer();
    }

    private void lambda$addDoneButton$1(Button button) {
        this.func_214179_c(3);
    }

    private void lambda$addDoneButton$0(Button button) {
        this.minecraft.displayGuiScreen(null);
    }
}

