package digital.rbq.gui.clickgui.classic.component;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import digital.rbq.Lycoris;
import digital.rbq.gui.clickgui.classic.ClickGUI;
import digital.rbq.gui.clickgui.classic.GuiRenderUtils;
import digital.rbq.gui.clickgui.classic.window.Window;
import digital.rbq.gui.fontRenderer.FontManager;
import digital.rbq.gui.other.BindScreen;
import digital.rbq.module.Module;
import digital.rbq.module.implement.Render.Hud;
import digital.rbq.module.value.ValueManager;
import digital.rbq.utility.AnimationTimer;
import digital.rbq.utility.ColorUtils;
import digital.rbq.utility.MathUtils;

import java.awt.*;

public class ModuleButton extends Button {
    public Module module;
    private boolean hasSettings;
    private boolean isHoveredMod;
    private boolean isHoveredSetting;
    private AnimationTimer expandAnimation = new AnimationTimer(15);

    public ModuleButton(Window window, int offX, int offY, String title, String tooltip, Module module) {
        super(window, offX, offY, title);
        this.hasSettings = module.isHasSubModule() || !ValueManager.getValueByModNameForRender(module.getName()).isEmpty();

        this.width = Math.max(ClickGUI.defaultWidth, window.width);
        this.height = ClickGUI.buttonHeight;
        this.module = module;
        this.type = "ModuleButton";
    }

    @Override
    public void render(int mouseX, int mouseY) {
        int whiteColor = Hud.isLightMode ? new Color(ColorUtils.GREY.c).brighter().getRGB() : new Color(0xD5D5D5).getRGB();

        this.expandAnimation.update(Lycoris.INSTANCE.getClickGUI().isVisivleComponetsByGroup(this.module.getName() + "_setting"));

        if (this.hasSettings) {
            whiteColor = this.isHoveredSetting ? GuiRenderUtils.darker(whiteColor, 30) : whiteColor;

            final float x = this.x + 6.5f;
            final float y = this.y + (height / 2.0f);

            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, 0);
            GlStateManager.rotate((float) this.expandAnimation.getValue() * 90.0F, 0, 0, 1);

            GuiRenderUtils.drawLine2D(-1.0, -2.0, 1.0d, 0d, 1.0f, whiteColor);
            GuiRenderUtils.drawLine2D(-1.0, 2.0, 1.0d, 0d, 1.0f, whiteColor);

            GlStateManager.translate(-x, -y, 0);
            GlStateManager.popMatrix();
        }

        int color = this.module.isEnabled() ? Hud.isLightMode ? ClickGUI.lightMainColor : ClickGUI.mainColor : Hud.isLightMode ? ColorUtils.GREY.c : 16777215;
        GuiRenderUtils.setColor(color);

        FontManager.small.drawString(this.title, this.x + 12, this.y + this.height / 2 - FontManager.small.getHeight(this.title) / 2 + 1.0f, color);
    }

    public void update(int mouseX, int mouseY) {
        super.update(mouseX, mouseY);
        if (this.module != null) {
            this.isToggled = this.module.isEnabled();
        } else {
            this.isToggled = false;
        }
    }

    @Override
    protected void pressed() {
        if (this.isHoveredMod) {
            if (this.module != null) {
                if (Mouse.isButtonDown(1)) {
                    Minecraft.getMinecraft().displayGuiScreen(new BindScreen(this.module,  Lycoris.INSTANCE.getClickGUI()));
                } else {
                    this.module.toggle();
                }
            }
        }

        if (this.isHoveredSetting && hasSettings) {
            if (!Lycoris.INSTANCE.getClickGUI().isVisivleComponetsByGroup(this.module.getName() + "_setting")) {
                Lycoris.INSTANCE.getClickGUI().toggleBox(this.module.getName() + "_setting", true);
            } else {
                Lycoris.INSTANCE.getClickGUI().toggleBox(this.module.getName() + "_setting", false);
            }

            parent.repositionComponents();
        }
    }

    public void mouseUpdates(int mouseX, int mouseY, boolean isPressed) {
        this.isHoveredMod = this.containsMod(mouseX, mouseY) && this.parent.mouseOver(mouseX, mouseY) && this.isHovered;
        this.isHoveredSetting = this.containsSettings(mouseX, mouseY) && this.parent.mouseOver(mouseX, mouseY) && this.isHovered;

        super.mouseUpdates(mouseX, mouseY, isPressed);
    }

    private boolean containsMod(int mouseX, int mouseY) {
        return MathUtils.contains(mouseX, mouseY, this.x + 11, this.y, this.x + this.width, this.y + this.height);
    }

    private boolean containsSettings(int mouseX, int mouseY) {
        return MathUtils.contains(mouseX, mouseY, this.x, this.y, this.x + 10, this.y + this.height);
    }
}
