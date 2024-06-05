package net.shoreline.client.impl.gui.click;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.impl.gui.click.impl.config.CategoryFrame;
import net.shoreline.client.impl.gui.click.impl.config.ModuleButton;
import net.shoreline.client.impl.gui.click.impl.config.setting.ConfigButton;
import net.shoreline.client.impl.module.client.ClickGuiModule;
import net.shoreline.client.util.Globals;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see ClickGuiModule
 */
public class ClickGuiScreen extends Screen implements Globals
{
    //
    private CategoryFrame focus;
    private final List<CategoryFrame> frames = new CopyOnWriteArrayList<>();
    private final ClickGuiModule module;
    // mouse position
    public static int MOUSE_X;
    public static int MOUSE_Y;
    // mouse states
    public static boolean MOUSE_RIGHT_CLICK;
    public static boolean MOUSE_RIGHT_HOLD;
    public static boolean MOUSE_LEFT_CLICK;
    public static boolean MOUSE_LEFT_HOLD;

    /**
     *
     */
    public ClickGuiScreen(ClickGuiModule module)
    {
        super(Text.literal("ClickGui"));
        this.module = module;
        float x = 2.0f;
        for (ModuleCategory category : ModuleCategory.values())
        {
            frames.add(new CategoryFrame(category, x, 10.0f));
            x += 90.0f;
        }
    }

    /**
     *
     *
     * @param matrices
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        super.render(matrices, mouseX, mouseY, delta);
        for (CategoryFrame frame : frames)
        {
            if (frame.isWithinTotal(mouseX, mouseY))
            {
                focus = frame;
            }
            frame.render(matrices, mouseX, mouseY, delta);
            float scale = module.getScale();
            if (scale != 1.0f)
            {
                frame.setDimensions(frame.getWidth() * scale,
                        frame.getHeight() * scale);
                for (ModuleButton button : frame.getModuleButtons())
                {
                    button.setDimensions(button.getWidth() * scale,
                            button.getHeight() * scale);
                    for (ConfigButton<?> component : button.getConfigButtons())
                    {
                        component.setDimensions(component.getWidth() * scale,
                                component.getHeight() * scale);
                    }
                }
            }
        }
        // update mouse state
        MOUSE_LEFT_CLICK = false;
        MOUSE_RIGHT_CLICK = false;
        // update mouse position
        MOUSE_X = mouseX;
        MOUSE_Y = mouseY;
    }

    /**
     *
     *
     * @param mouseX
     * @param mouseY
     * @param mouseButton
     * @return
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
    {
        if (mouseButton == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            MOUSE_LEFT_CLICK = true;
            MOUSE_LEFT_HOLD = true;
        }
        else if (mouseButton == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
        {
            MOUSE_RIGHT_CLICK = true;
            MOUSE_RIGHT_HOLD = true;
        }
        for (CategoryFrame frame : frames)
        {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     *
     *
     * @param mouseX the X coordinate of the mouse
     * @param mouseY the Y coordinate of the mouse
     * @param button the mouse button number
     * @return
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if (button == 0)
        {
            MOUSE_LEFT_HOLD = false;
        }
        else if (button == 1)
        {
            MOUSE_RIGHT_HOLD = false;
        }
        for (CategoryFrame frame : frames)
        {
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    /**
     *
     *
     * @param mouseX the X coordinate of the mouse
     * @param mouseY the Y coordinate of the mouse
     * @param amount value is {@code < 0} if scrolled down, {@code > 0} if scrolled up
     * @return
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount)
    {
        if (focus != null)
        {
            focus.setPos(focus.getX(), (float) (focus.getY() + amount));
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    /**
     *
     *
     * @param keyCode
     * @param scanCode
     * @param modifiers
     * @return
     */
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        for (CategoryFrame frame : frames)
        {
            frame.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public boolean shouldPause()
    {
        return false;
    }

    /**
     *
     */
    @Override
    public void close()
    {
        module.disable();
        //
        MOUSE_LEFT_CLICK = false;
        MOUSE_LEFT_HOLD = false;
        MOUSE_RIGHT_CLICK = false;
        MOUSE_RIGHT_HOLD = false;
        super.close();
    }
}
