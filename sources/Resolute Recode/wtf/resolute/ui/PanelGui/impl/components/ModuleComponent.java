package wtf.resolute.ui.PanelGui.impl.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.settings.Setting;
import wtf.resolute.utiled.client.KeyStorage;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.math.Vector4i;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.Cursors;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.Stencil;
import wtf.resolute.utiled.render.font.Fonts;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;
import wtf.resolute.moduled.settings.impl.*;
import wtf.resolute.ui.PanelGui.impl.components.settings.*;

import java.awt.*;

@Getter
public class ModuleComponent extends wtf.resolute.ui.PanelGui.impl.Component {
    private final Vector4f ROUNDING_VECTOR = new Vector4f(0,0,0,0);
    private final Vector4i BORDER_COLOR = new Vector4i(ColorUtils.rgb(45, 46, 53), ColorUtils.rgb(25, 26, 31), ColorUtils.rgb(45, 46, 53), ColorUtils.rgb(25, 26, 31));

    private final Module function;
    public Animation animation = new Animation();
    public boolean open;
    private boolean bind;

    private final ObjectArrayList<wtf.resolute.ui.PanelGui.impl.Component> components = new ObjectArrayList<>();

    public ModuleComponent(Module function) {
        this.function = function;
        for (Setting<?> setting : function.getSettings()) {
            if (setting instanceof BooleanSetting bool) {
                components.add(new BooleanComponent(bool));
            }
            if (setting instanceof SliderSetting slider) {
                components.add(new SliderComponent(slider));
            }
            if (setting instanceof BindSetting bind) {
                components.add(new BindComponent(bind));
            }
            if (setting instanceof ModeSetting mode) {
                components.add(new ModeComponent(mode));
            }
            if (setting instanceof ModeListSetting mode) {
                components.add(new MultiBoxComponent(mode));
            }
            if (setting instanceof StringSetting string) {
                components.add(new StringComponent(string));
            }

        }
        animation = animation.animate(open ? 1 : 0, 0.3);
    }

    // draw components
    public void drawComponents(MatrixStack stack, float mouseX, float mouseY) {
        if (animation.getValue() > 0) {
            Stencil.initStencilToWrite();
            DisplayUtils.drawRoundedRect(getX() + 0.5f, getY() + 0.5f, getWidth() - 1, getHeight() - 1, ROUNDING_VECTOR, ColorUtils.rgba(23, 23, 23, (int) (255 * 0.33)));
            Stencil.readStencilBuffer(1);

            float y = getY() + 20;
            for (wtf.resolute.ui.PanelGui.impl.Component component : components) {
                if (component.isVisible()) {
                    component.setX(getX());
                    component.setY(y);
                    component.setWidth(getWidth());
                    component.render(stack, mouseX, mouseY);
                    y += component.getHeight();
                }
            }
            Stencil.uninitStencilBuffer();

        }
    }

    @Override
    public void mouseRelease(float mouseX, float mouseY, int mouse) {
        // TODO Auto-generated method stub

        for (wtf.resolute.ui.PanelGui.impl.Component component : components) {
            component.mouseRelease(mouseX, mouseY, mouse);
        }

        super.mouseRelease(mouseX, mouseY, mouse);
    }

    private boolean hovered = false;

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        int color = ColorUtils.interpolate(-1, new Color(161, 164, 177).getRGB(), (float) function.getAnimation().getValue());


        function.getAnimation().update();
        super.render(stack, mouseX, mouseY);

        drawOutlinedRect(mouseX, mouseY, color);


        drawText(stack, color);
        drawComponents(stack, mouseX, mouseY);
    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int button) {
        if (isHovered(mouseX, mouseY, 20)) {
            if (button == 0) function.toggle();
            if (button == 1) {
                open = !open;
                animation = animation.animate(open ? 1 : 0, 0.2, Easings.CIRC_OUT);
            }
            if (button == 2) {
                bind = !bind;
            }
        }
        if (isHovered(mouseX, mouseY)) {
            if (open) {
                for (wtf.resolute.ui.PanelGui.impl.Component component : components) {
                    if (component.isVisible()) component.mouseClick(mouseX, mouseY, button);
                }
            }
        }
        super.mouseClick(mouseX, mouseY, button);
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        for (wtf.resolute.ui.PanelGui.impl.Component component : components) {
            if (component.isVisible()) component.charTyped(codePoint, modifiers);
        }
        super.charTyped(codePoint, modifiers);
    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {
        for (wtf.resolute.ui.PanelGui.impl.Component component : components) {
            if (component.isVisible()) component.keyPressed(key, scanCode, modifiers);
        }
        if (bind) {
            if (key == GLFW.GLFW_KEY_DELETE) {
                function.setBind(0);
            } else function.setBind(key);
            bind = false;
        }
        super.keyPressed(key, scanCode, modifiers);
    }

    private void drawOutlinedRect(float mouseX, float mouseY, int color) {
        DisplayUtils.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 1, new Color(37, 37, 37).getRGB());
        if (MathUtil.isHovered(mouseX, mouseY, getX(), getY(), getWidth(), 20)) {
            //ClientUtil.playSound2(("zvuk11"), 90);
            DisplayUtils.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 1, new Color(42, 42, 42).getRGB());
            if (!hovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
                hovered = true;
            }
        } else {
            if (hovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                hovered = false;
            }
        }

    }

    private void drawText(MatrixStack stack, int color) {
      //  DisplayUtils.drawShadow(getX() + 30, getY() + 6.5f, Fonted.rubik[18].getWidth(function.getName()) + 4, Fonts.montserrat.getHeight(7), 10, ColorUtils.setAlpha(ColorUtils.getColor(90), (int) (150 * function.getAnimation().getValue())));
     //   Fonted.sfbold[15].drawCenteredString(stack, function.getServer(), getX() + getWidth() - 25, getY() + 6.5f, Color.GREEN.getRGB());
        Fonted.rubik[18].drawCenteredString(stack, function.getName(), getX() + 60, getY() + 6.5f, color);
        if (components.stream().filter(wtf.resolute.ui.PanelGui.impl.Component::isVisible).count() >= 1) {
            if (bind) {
                Fonted.sfbold[15].drawString(stack, function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), getX() + getWidth() - 6 - Fonts.montserrat.getWidth(function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), 6, 0.1f), getY() + Fonts.icons.getHeight(6) + 1, ColorUtils.rgb(161, 164, 177));
            } else
                Fonts.icons.drawText(stack, !open ? "B" : "C", getX() + getWidth() - 6 - Fonts.icons.getWidth(!open ? "B" : "C", 6), getY() + Fonts.icons.getHeight(6) + 1, ColorUtils.rgb(161, 164, 177), 6);
        } else {
            if (bind) {
                Fonted.sfbold[15].drawString(stack, function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), getX() + getWidth() - 6 - Fonts.montserrat.getWidth(function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), 6, 0.1f), getY() + Fonts.icons.getHeight(6) + 1, ColorUtils.rgb(161, 164, 177));
            }
        }
    }
}
