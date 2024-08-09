/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class AbstractCommandBlockScreen
extends Screen {
    private static final ITextComponent field_243330_s = new TranslationTextComponent("advMode.setCommand");
    private static final ITextComponent field_243331_t = new TranslationTextComponent("advMode.command");
    private static final ITextComponent field_243332_u = new TranslationTextComponent("advMode.previousOutput");
    protected TextFieldWidget commandTextField;
    protected TextFieldWidget resultTextField;
    protected Button doneButton;
    protected Button cancelButton;
    protected Button trackOutputButton;
    protected boolean trackOutput;
    private CommandSuggestionHelper suggestionHelper;

    public AbstractCommandBlockScreen() {
        super(NarratorChatListener.EMPTY);
    }

    @Override
    public void tick() {
        this.commandTextField.tick();
    }

    abstract CommandBlockLogic getLogic();

    abstract int func_195236_i();

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.doneButton = this.addButton(new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$0));
        this.cancelButton = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        this.trackOutputButton = this.addButton(new Button(this.width / 2 + 150 - 20, this.func_195236_i(), 20, 20, new StringTextComponent("O"), this::lambda$init$2));
        this.commandTextField = new TextFieldWidget(this, this.font, this.width / 2 - 150, 50, 300, 20, (ITextComponent)new TranslationTextComponent("advMode.command")){
            final AbstractCommandBlockScreen this$0;
            {
                this.this$0 = abstractCommandBlockScreen;
                super(fontRenderer, n, n2, n3, n4, iTextComponent);
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return super.getNarrationMessage().appendString(this.this$0.suggestionHelper.getSuggestionMessage());
            }
        };
        this.commandTextField.setMaxStringLength(32500);
        this.commandTextField.setResponder(this::func_214185_b);
        this.children.add(this.commandTextField);
        this.resultTextField = new TextFieldWidget(this.font, this.width / 2 - 150, this.func_195236_i(), 276, 20, new TranslationTextComponent("advMode.previousOutput"));
        this.resultTextField.setMaxStringLength(32500);
        this.resultTextField.setEnabled(true);
        this.resultTextField.setText("-");
        this.children.add(this.resultTextField);
        this.setFocusedDefault(this.commandTextField);
        this.commandTextField.setFocused2(false);
        this.suggestionHelper = new CommandSuggestionHelper(this.minecraft, this, this.commandTextField, this.font, true, true, 0, 7, false, Integer.MIN_VALUE);
        this.suggestionHelper.shouldAutoSuggest(false);
        this.suggestionHelper.init();
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.commandTextField.getText();
        this.init(minecraft, n, n2);
        this.commandTextField.setText(string);
        this.suggestionHelper.init();
    }

    protected void updateTrackOutput() {
        if (this.getLogic().shouldTrackOutput()) {
            this.trackOutputButton.setMessage(new StringTextComponent("O"));
            this.resultTextField.setText(this.getLogic().getLastOutput().getString());
        } else {
            this.trackOutputButton.setMessage(new StringTextComponent("X"));
            this.resultTextField.setText("-");
        }
    }

    protected void func_195234_k() {
        CommandBlockLogic commandBlockLogic = this.getLogic();
        this.func_195235_a(commandBlockLogic);
        if (!commandBlockLogic.shouldTrackOutput()) {
            commandBlockLogic.setLastOutput(null);
        }
        this.minecraft.displayGuiScreen(null);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    protected abstract void func_195235_a(CommandBlockLogic var1);

    @Override
    public void closeScreen() {
        this.getLogic().setTrackOutput(this.trackOutput);
        this.minecraft.displayGuiScreen(null);
    }

    private void func_214185_b(String string) {
        this.suggestionHelper.init();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (this.suggestionHelper.onKeyPressed(n, n2, n3)) {
            return false;
        }
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (n != 257 && n != 335) {
            return true;
        }
        this.func_195234_k();
        return false;
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        return this.suggestionHelper.onScroll(d3) ? true : super.mouseScrolled(d, d2, d3);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        return this.suggestionHelper.onClick(d, d2, n) ? true : super.mouseClicked(d, d2, n);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        AbstractCommandBlockScreen.drawCenteredString(matrixStack, this.font, field_243330_s, this.width / 2, 20, 0xFFFFFF);
        AbstractCommandBlockScreen.drawString(matrixStack, this.font, field_243331_t, this.width / 2 - 150, 40, 0xA0A0A0);
        this.commandTextField.render(matrixStack, n, n2, f);
        int n3 = 75;
        if (!this.resultTextField.getText().isEmpty()) {
            AbstractCommandBlockScreen.drawString(matrixStack, this.font, field_243332_u, this.width / 2 - 150, (n3 += 46 + this.func_195236_i() - 135) + 4, 0xA0A0A0);
            this.resultTextField.render(matrixStack, n, n2, f);
        }
        super.render(matrixStack, n, n2, f);
        this.suggestionHelper.drawSuggestionList(matrixStack, n, n2);
    }

    private void lambda$init$2(Button button) {
        CommandBlockLogic commandBlockLogic;
        commandBlockLogic.setTrackOutput(!(commandBlockLogic = this.getLogic()).shouldTrackOutput());
        this.updateTrackOutput();
    }

    private void lambda$init$1(Button button) {
        this.closeScreen();
    }

    private void lambda$init$0(Button button) {
        this.func_195234_k();
    }
}

