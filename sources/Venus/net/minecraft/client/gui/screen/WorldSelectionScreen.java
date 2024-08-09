/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldSelectionList;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.TranslationTextComponent;

public class WorldSelectionScreen
extends Screen {
    protected final Screen prevScreen;
    private List<IReorderingProcessor> worldVersTooltip;
    private Button deleteButton;
    private Button selectButton;
    private Button renameButton;
    private Button copyButton;
    protected TextFieldWidget searchField;
    private WorldSelectionList selectionList;

    public WorldSelectionScreen(Screen screen) {
        super(new TranslationTextComponent("selectWorld.title"));
        this.prevScreen = screen;
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        return super.mouseScrolled(d, d2, d3);
    }

    @Override
    public void tick() {
        this.searchField.tick();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.searchField = new TextFieldWidget(this.font, this.width / 2 - 100, 22, 200, 20, this.searchField, new TranslationTextComponent("selectWorld.search"));
        this.searchField.setResponder(this::lambda$init$1);
        this.selectionList = new WorldSelectionList(this, this.minecraft, this.width, this.height, 48, this.height - 64, 36, this::lambda$init$2, this.selectionList);
        this.children.add(this.searchField);
        this.children.add(this.selectionList);
        this.selectButton = this.addButton(new Button(this.width / 2 - 154, this.height - 52, 150, 20, new TranslationTextComponent("selectWorld.select"), this::lambda$init$3));
        this.addButton(new Button(this.width / 2 + 4, this.height - 52, 150, 20, new TranslationTextComponent("selectWorld.create"), this::lambda$init$4));
        this.renameButton = this.addButton(new Button(this.width / 2 - 154, this.height - 28, 72, 20, new TranslationTextComponent("selectWorld.edit"), this::lambda$init$5));
        this.deleteButton = this.addButton(new Button(this.width / 2 - 76, this.height - 28, 72, 20, new TranslationTextComponent("selectWorld.delete"), this::lambda$init$6));
        this.copyButton = this.addButton(new Button(this.width / 2 + 4, this.height - 28, 72, 20, new TranslationTextComponent("selectWorld.recreate"), this::lambda$init$7));
        this.addButton(new Button(this.width / 2 + 82, this.height - 28, 72, 20, DialogTexts.GUI_CANCEL, this::lambda$init$8));
        this.func_214324_a(true);
        this.setFocusedDefault(this.searchField);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        return super.keyPressed(n, n2, n3) ? true : this.searchField.keyPressed(n, n2, n3);
    }

    @Override
    public void closeScreen() {
        this.minecraft.displayGuiScreen(this.prevScreen);
    }

    @Override
    public boolean charTyped(char c, int n) {
        return this.searchField.charTyped(c, n);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.worldVersTooltip = null;
        this.selectionList.render(matrixStack, n, n2, f);
        this.searchField.render(matrixStack, n, n2, f);
        WorldSelectionScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
        if (this.worldVersTooltip != null) {
            this.renderTooltip(matrixStack, this.worldVersTooltip, n, n2);
        }
    }

    public void func_239026_b_(List<IReorderingProcessor> list) {
        this.worldVersTooltip = list;
    }

    public void func_214324_a(boolean bl) {
        this.selectButton.active = bl;
        this.deleteButton.active = bl;
        this.renameButton.active = bl;
        this.copyButton.active = bl;
    }

    @Override
    public void onClose() {
        if (this.selectionList != null) {
            this.selectionList.getEventListeners().forEach(WorldSelectionList.Entry::close);
        }
    }

    private void lambda$init$8(Button button) {
        this.minecraft.displayGuiScreen(this.prevScreen);
    }

    private void lambda$init$7(Button button) {
        this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214445_d);
    }

    private void lambda$init$6(Button button) {
        this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214442_b);
    }

    private void lambda$init$5(Button button) {
        this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214444_c);
    }

    private void lambda$init$4(Button button) {
        this.minecraft.displayGuiScreen(CreateWorldScreen.func_243425_a(this));
    }

    private void lambda$init$3(Button button) {
        this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214438_a);
    }

    private String lambda$init$2() {
        return this.searchField.getText();
    }

    private void lambda$init$1(String string) {
        this.selectionList.func_212330_a(() -> WorldSelectionScreen.lambda$init$0(string), true);
    }

    private static String lambda$init$0(String string) {
        return string;
    }
}

