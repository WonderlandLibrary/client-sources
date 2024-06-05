package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;

public class GuiControls extends GuiScreen
{
    private static final GameSettings.Options[] optionsArr;
    public KeyBinding buttonId;
    public long time;
    protected String screenTitle;
    private GuiScreen parentScreen;
    private GameSettings options;
    private GuiKeyBindingList keyBindingList;
    private GuiButton buttonReset;
    
    public GuiControls(final GuiScreen p_i1027_1_, final GameSettings p_i1027_2_) {
        this.buttonId = null;
        this.screenTitle = "Controls";
        this.parentScreen = p_i1027_1_;
        this.options = p_i1027_2_;
    }
    
    @Override
    public void initGui() {
        this.keyBindingList = new GuiKeyBindingList(this, GuiControls.mc);
        this.buttonList.add(new GuiButton(200, GuiControls.width / 2 - 155, GuiControls.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.buttonReset = new GuiButton(201, GuiControls.width / 2 - 155 + 160, GuiControls.height - 29, 150, 20, I18n.format("controls.resetAll", new Object[0])));
        this.screenTitle = I18n.format("controls.title", new Object[0]);
        int var1 = 0;
        for (final GameSettings.Options var5 : GuiControls.optionsArr) {
            if (var5.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), GuiControls.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
            }
            else {
                this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), GuiControls.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.options.getKeyBinding(var5)));
            }
            ++var1;
        }
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.keyBindingList.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 200) {
            GuiControls.mc.displayGuiScreen(this.parentScreen);
        }
        else if (button.id == 201) {
            for (final KeyBinding var5 : GuiControls.mc.gameSettings.keyBindings) {
                var5.setKeyCode(var5.getKeyCodeDefault());
            }
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (button.id < 100 && button instanceof GuiOptionButton) {
            this.options.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
            button.displayString = this.options.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.buttonId != null) {
            this.options.setOptionKeyBinding(this.buttonId, -100 + mouseButton);
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (mouseButton != 0 || !this.keyBindingList.func_148179_a(mouseX, mouseY, mouseButton)) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state != 0 || !this.keyBindingList.func_148181_b(mouseX, mouseY, state)) {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        if (this.buttonId != null) {
            if (keyCode == 1) {
                this.options.setOptionKeyBinding(this.buttonId, 0);
            }
            else if (keyCode != 0) {
                this.options.setOptionKeyBinding(this.buttonId, keyCode);
            }
            else if (typedChar > '\0') {
                this.options.setOptionKeyBinding(this.buttonId, typedChar + '\u0100');
            }
            this.buttonId = null;
            this.time = Minecraft.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
        final ScaledResolution scaledRes = new ScaledResolution(GuiControls.mc, GuiControls.mc.displayWidth, GuiControls.mc.displayHeight);
        Gui.drawCenteredString(this.fontRendererObj, this.screenTitle, GuiControls.width / 2, 8, 16777215);
        boolean var4 = true;
        for (final KeyBinding var8 : this.options.keyBindings) {
            if (var8.getKeyCode() != var8.getKeyCodeDefault()) {
                var4 = false;
                break;
            }
        }
        this.buttonReset.enabled = !var4;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        optionsArr = new GameSettings.Options[] { GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };
    }
}
