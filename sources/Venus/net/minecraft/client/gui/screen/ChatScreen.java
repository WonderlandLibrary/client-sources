/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.concurrent.atomic.AtomicBoolean;
import mpp.venusfr.utils.drag.DragManager;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.Cursors;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

public class ChatScreen
extends Screen {
    private String historyBuffer = "";
    private int sentHistoryCursor = -1;
    protected TextFieldWidget inputField;
    private String defaultInputFieldText = "";
    private CommandSuggestionHelper commandSuggestionHelper;
    public static boolean hide;
    Button button;

    public ChatScreen(String string) {
        super(NarratorChatListener.EMPTY);
        this.defaultInputFieldText = string;
    }

    @Override
    protected void init() {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        this.button = new Button(mainWindow.getScaledWidth() - 152, mainWindow.getScaledHeight() - 35, 150, 20, new StringTextComponent("\u0421\u043a\u0440\u044b\u0442\u044c \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e: " + hide), ChatScreen::lambda$init$0);
        this.addButton(this.button);
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.sentHistoryCursor = this.minecraft.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new TextFieldWidget(this, this.font, 4, this.height - 12, this.width - 4, 12, (ITextComponent)new TranslationTextComponent("chat.editBox")){
            final ChatScreen this$0;
            {
                this.this$0 = chatScreen;
                super(fontRenderer, n, n2, n3, n4, iTextComponent);
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return super.getNarrationMessage().appendString(this.this$0.commandSuggestionHelper.getSuggestionMessage());
            }
        };
        this.inputField.setMaxStringLength(256);
        this.inputField.setEnableBackgroundDrawing(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setResponder(this::func_212997_a);
        this.children.add(this.inputField);
        this.commandSuggestionHelper = new CommandSuggestionHelper(this.minecraft, this, this.inputField, this.font, false, false, 1, 10, true, -805306368);
        this.commandSuggestionHelper.init();
        this.setFocusedDefault(this.inputField);
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.inputField.getText();
        this.init(minecraft, n, n2);
        this.setChatLine(string);
        this.commandSuggestionHelper.init();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.minecraft.ingameGUI.getChatGUI().resetScroll();
        for (Dragging dragging : DragManager.draggables.values()) {
            if (!dragging.getModule().isState()) continue;
            dragging.onRelease(0);
        }
    }

    @Override
    public void tick() {
        this.inputField.tick();
    }

    private void func_212997_a(String string) {
        String string2 = this.inputField.getText();
        this.commandSuggestionHelper.shouldAutoSuggest(!string2.equals(this.defaultInputFieldText));
        this.commandSuggestionHelper.init();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (this.commandSuggestionHelper.onKeyPressed(n, n2, n3)) {
            return false;
        }
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (n == 256) {
            this.minecraft.displayGuiScreen(null);
            return false;
        }
        if (n != 257 && n != 335) {
            if (n == 265) {
                this.getSentHistory(-1);
                return false;
            }
            if (n == 264) {
                this.getSentHistory(1);
                return false;
            }
            if (n == 266) {
                this.minecraft.ingameGUI.getChatGUI().addScrollPos(this.minecraft.ingameGUI.getChatGUI().getLineCount() - 1);
                return false;
            }
            if (n == 267) {
                this.minecraft.ingameGUI.getChatGUI().addScrollPos(-this.minecraft.ingameGUI.getChatGUI().getLineCount() + 1);
                return false;
            }
            return true;
        }
        String string = this.inputField.getText().trim();
        if (!string.isEmpty()) {
            this.sendMessage(string);
        }
        this.minecraft.displayGuiScreen(null);
        return false;
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        if (d3 > 1.0) {
            d3 = 1.0;
        }
        if (d3 < -1.0) {
            d3 = -1.0;
        }
        if (this.commandSuggestionHelper.onScroll(d3)) {
            return false;
        }
        if (!ChatScreen.hasShiftDown()) {
            d3 *= 7.0;
        }
        this.minecraft.ingameGUI.getChatGUI().addScrollPos(d3);
        return false;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        Object object;
        Object object2;
        if (this.commandSuggestionHelper.onClick((int)d, (int)d2, n)) {
            return false;
        }
        if (n == 0) {
            object2 = this.minecraft.ingameGUI.getChatGUI();
            if (((NewChatGui)object2).func_238491_a_(d, d2)) {
                return false;
            }
            object = ((NewChatGui)object2).func_238494_b_(d, d2);
            if (object != null && this.handleComponentClicked((Style)object)) {
                return false;
            }
        }
        object2 = DragManager.draggables.values().iterator();
        while (!(!object2.hasNext() || ((Dragging)(object = (Dragging)object2.next())).getModule().isState() && ((Dragging)object).onClick(d, d2, n))) {
        }
        return this.inputField.mouseClicked(d, d2, n) ? true : super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        for (Dragging dragging : DragManager.draggables.values()) {
            if (!dragging.getModule().isState()) continue;
            dragging.onRelease(n);
        }
        return super.mouseReleased(d, d2, n);
    }

    @Override
    protected void insertText(String string, boolean bl) {
        if (bl) {
            this.inputField.setText(string);
        } else {
            this.inputField.writeText(string);
        }
    }

    public void getSentHistory(int n) {
        int n2 = this.sentHistoryCursor + n;
        int n3 = this.minecraft.ingameGUI.getChatGUI().getSentMessages().size();
        if ((n2 = MathHelper.clamp(n2, 0, n3)) != this.sentHistoryCursor) {
            if (n2 == n3) {
                this.sentHistoryCursor = n3;
                this.inputField.setText(this.historyBuffer);
            } else {
                if (this.sentHistoryCursor == n3) {
                    this.historyBuffer = this.inputField.getText();
                }
                this.inputField.setText(this.minecraft.ingameGUI.getChatGUI().getSentMessages().get(n2));
                this.commandSuggestionHelper.shouldAutoSuggest(true);
                this.sentHistoryCursor = n2;
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.button != null) {
            this.button.setMessage(new StringTextComponent("\u0421\u043a\u0440\u044b\u0442\u044c \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e: " + hide));
        }
        this.setListener(this.inputField);
        this.inputField.setFocused2(false);
        ChatScreen.fill(matrixStack, 2, this.height - 14, this.width - 2, this.height - 2, this.minecraft.gameSettings.getChatBackgroundColor(Integer.MIN_VALUE));
        this.inputField.render(matrixStack, n, n2, f);
        this.commandSuggestionHelper.drawSuggestionList(matrixStack, n, n2);
        Style style = this.minecraft.ingameGUI.getChatGUI().func_238494_b_(n, n2);
        if (style != null && style.getHoverEvent() != null) {
            this.renderComponentHoverEffect(matrixStack, style, n, n2);
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DragManager.draggables.values().forEach(arg_0 -> ChatScreen.lambda$render$1(n, n2, atomicBoolean, arg_0));
        if (atomicBoolean.get()) {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
        } else {
            GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
        }
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private void setChatLine(String string) {
        this.inputField.setText(string);
    }

    private static void lambda$render$1(int n, int n2, AtomicBoolean atomicBoolean, Dragging dragging) {
        if (dragging.getModule().isState()) {
            if (MathUtil.isHovered(n, n2, dragging.getX(), dragging.getY(), dragging.getWidth(), dragging.getHeight())) {
                atomicBoolean.set(false);
            }
            dragging.onDraw(n, n2, Minecraft.getInstance().getMainWindow());
        }
    }

    private static void lambda$init$0(Button button) {
        hide = !hide;
    }
}

