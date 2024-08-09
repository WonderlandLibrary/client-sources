package im.expensive.ui.dropdown.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.functions.api.Function;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.*;
import im.expensive.ui.dropdown.components.settings.*;
import im.expensive.ui.dropdown.impl.Component;
import im.expensive.utils.client.KeyStorage;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.Cursors;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Stencil;
import im.expensive.utils.render.font.Fonts;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.awt.*;

@Getter
public class ModuleComponent extends Component {
    private final Vector4f ROUNDING_VECTOR = new Vector4f(8, 8, 8, 8);
    private final Vector4i BORDER_COLOR = new Vector4i(ColorUtils.rgb(45, 46, 53), ColorUtils.rgb(25, 26, 31), ColorUtils.rgb(45, 46, 53), ColorUtils.rgb(25, 26, 31));

    private final Function function;
    public Animation animation = new Animation();
    public boolean open;
    private boolean bind;

    private final ObjectArrayList<Component> components = new ObjectArrayList<>();

    public ModuleComponent(Function function) {
        this.function = function;
        for (Setting<?> setting : function.getSettings()) {
            if (setting instanceof BooleanSetting bool) {
                components.add(new im.abstractinc.ui.dropdown.components.settings.BooleanComponent(bool));
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
            if (animation.getValue() > 0.1 && components.stream().filter(Component::isVisible).count() >= 1) {
            }
            Stencil.initStencilToWrite();
              DisplayUtils.drawRoundedRect(getX() + 0.5f, getY() + 0.5f, getWidth() - 1, getHeight() - 1, ROUNDING_VECTOR, ColorUtils.rgba(23, 23, 23, (int) (255 * 0.33)));
            Stencil.readStencilBuffer(1);
            float y = getY() + 20;
            for (Component component : components) {
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

        for (Component component : components) {
            component.mouseRelease(mouseX, mouseY, mouse);
        }

        super.mouseRelease(mouseX, mouseY, mouse);
    }

    private boolean hovered = false;

    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        int color = ColorUtils.interpolate(-1, ColorUtils.rgb(161, 164, 177), (float) function.getAnimation().getValue());

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
                for (Component component : components) {
                    if (component.isVisible()) component.mouseClick(mouseX, mouseY, button);
                }
            }
        }
        super.mouseClick(mouseX, mouseY, button);
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        for (Component component : components) {
            if (component.isVisible()) component.charTyped(codePoint, modifiers);
        }
        super.charTyped(codePoint, modifiers);
    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {
        for (Component component : components) {
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
        Stencil.initStencilToWrite();
        DisplayUtils.drawRoundedRect(getX() + 0.5f, getY() + 0.5f, getWidth() - 1, getHeight() - 1, ROUNDING_VECTOR, ColorUtils.rgba(23, 23, 23, (int) (255 * 0.33)));

        Stencil.readStencilBuffer(0);
        Stencil.uninitStencilBuffer();
        DisplayUtils.drawRoundedRect(getX() + 5, getY(), getWidth() - 10, getHeight(), ROUNDING_VECTOR , new Vector4i(ColorUtils.rgba(25, 25, 25, (int) (255)), ColorUtils.rgba(25, 25, 25, (int) (255)), ColorUtils.rgba(25, 25, 25, (int) (255)), ColorUtils.rgba(25, 25, 25, (int) (255 ))));

        if (MathUtil.isHovered(mouseX, mouseY, getX(), getY(), getWidth(), 20)) {
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

        DisplayUtils.drawRoundedRect(getX() + 103, getY() + 5.5f, 10, 10, 5, new Color(44, 44, 44).getRGB());
        DisplayUtils.drawRoundedRect(getX() + 103, getY() + 5.5f, 10, 10, 5, ColorUtils.setAlpha(new Color(17, 255, 0).getRGB(), (int) (255 * function.getAnimation().getValue())));

        Fonts.sfMedium.drawText(stack, function.getName(), getX() + 15, getY() + 6.5f, color, 7, 0.1f);
        if (components.stream().filter(Component::isVisible).count() >= 1) {
            DisplayUtils.drawRoundedRect(getX() + 7, getY() + 3.5f, 5, 5, 2, new Color(255, 209, 0, 0).getRGB());
            if (bind) {
                Fonts.sfMedium.drawText(stack, function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), getX() + 100 - Fonts.sfMedium.getWidth(function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), 6, 0.1f), getY() + Fonts.icons.getHeight(6) + 2, ColorUtils.rgb(161, 164, 177), 6, 0.1f);
            }
            if (open) {
                DisplayUtils.drawRoundedRect(getX() + 103, getY() + 5.5f, 10, 10, 5, ColorUtils.setAlpha(new Color(255, 207, 0).getRGB(), (int)  (255 * animation.getValue())));
            }
            if (!open) {
                DisplayUtils.drawRoundedRect(getX() + 103, getY() + 5.5f, 10, 10, 5, ColorUtils.setAlpha(new Color(255, 207, 0).getRGB(), (int)  (255 * animation.getValue())));
            }
        } else {
            if (bind) {
                Fonts.sfui.drawText(stack, function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), getX() +  100 - Fonts.montserrat.getWidth(function.getBind() == 0 ? "..." : KeyStorage.getReverseKey(function.getBind()), 6, 0.1f), getY() + Fonts.icons.getHeight(6) + 2, ColorUtils.rgb(161, 164, 177), 6, 0.1f);
            }
        }
    }
}
