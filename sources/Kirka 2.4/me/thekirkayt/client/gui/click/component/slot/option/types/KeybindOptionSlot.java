/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.slot.option.types;

import me.thekirkayt.client.gui.click.ClickGui;
import me.thekirkayt.client.gui.click.component.slot.option.OptionSlotComponent;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.render.Gui;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import me.thekirkayt.utils.minecraft.FontRenderer;
import org.lwjgl.input.Keyboard;

public class KeybindOptionSlot
extends OptionSlotComponent {
    private static final double VALUE_WINDOW_PADDING = 6.0;
    private static final double PADDING = 4.0;
    private Module module;

    public KeybindOptionSlot(Module module, double x, double y, double width, double height) {
        super(null, x, y, width, height);
        this.module = module;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        boolean useDarkTheme = ((Gui)new Gui().getInstance()).isDarkTheme();
        RenderUtils.rectangle(this.getX() - 3.0, this.getY(), this.getX() + this.getWidth() + 3.0, this.getY() + this.getHeight(), this.hovering(mouseX, mouseY) ? -1 : 0);
        ClientUtils.clientFont().drawCenteredString("Bind", this.getX() + this.getWidth() / 2.0, this.getY() + 0.5 + this.getHeight() / 2.0 - (double)(ClientUtils.clientFont().FONT_HEIGHT / 2), -1);
        double width = (double)ClientUtils.clientFont().getStringWidth(this.module.getKeybind() < 0 ? "None" : Keyboard.getKeyName((int)this.module.getKeybind())) + 16.0;
        double x = this.getX() + this.getWidth() + 6.0;
        double x2 = this.getX() + this.getWidth() + 6.0 + width;
        int[] fillGradient = new int[]{-1, -1, RenderUtils.blend(-1, -11184641, 0.95f), RenderUtils.blend(-1, -11184641, 0.95f)};
        int[] outlineGradient = new int[]{RenderUtils.blend(-11184641, -11184641, 0.95f), RenderUtils.blend(-11184641, -11184641, 0.95f), -11184641, -11184641};
        RenderUtils.rectangle(x, this.getY(), x2, this.getY() + this.getHeight(), -11184641);
        RenderUtils.rectangle(x + 1.0, this.getY() + 0.5, x2 - 1.0, this.getY() + 1.0, -11184641);
        ClientUtils.clientFont().drawCenteredString(this.module.getKeybind() < 0 ? "None" : Keyboard.getKeyName((int)this.module.getKeybind()), x + width / 2.0, this.getY() + this.getHeight() / 2.0 - 3.5, -1);
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        if (this.hovering(mouseX, mouseY) && button == 0) {
            ClickGui.getInstance().setBinding(!ClickGui.getInstance().isBinding());
        }
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
        if (ClickGui.getInstance().isBinding() && keyInt == 1) {
            this.module.setKeybind(-1);
            ClickGui.getInstance().setBinding(false);
        } else if (ClickGui.getInstance().isBinding()) {
            this.module.setKeybind(keyInt);
            ClickGui.getInstance().setBinding(false);
        }
    }
}

