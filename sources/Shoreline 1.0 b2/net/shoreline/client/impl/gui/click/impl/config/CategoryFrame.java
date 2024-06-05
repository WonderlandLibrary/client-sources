package net.shoreline.client.impl.gui.click.impl.config;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.gui.click.ClickGuiScreen;
import net.shoreline.client.impl.gui.click.component.Frame;
import net.shoreline.client.impl.gui.click.impl.config.setting.ConfigButton;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.util.string.EnumFormatter;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Configuration {@link Frame} (commonly referred to as the "ClickGui") which
 * allows the user to configure a {@link Module}'s {@link Config} values.
 *
 * @author linus
 * @since 1.0
 *
 * @see Frame
 * @see Module
 * @see Config
 */
public class CategoryFrame extends Frame
{
    //
    private final String name;
    private final ModuleCategory category;
    // global module offset
    private float off;
    private boolean open;
    private boolean drag;
    // module components
    private final List<ModuleButton> moduleButtons =
            new CopyOnWriteArrayList<>();

    /**
     *
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public CategoryFrame(ModuleCategory category, float x, float y,
                        float width, float height)
    {
        super(x, y, width, height);
        this.category = category;
        this.name = EnumFormatter.formatEnum(category);
        for (Module module : Managers.MODULE.getModules())
        {
            if (module.getCategory() == category)
            {
                moduleButtons.add(new ModuleButton(module, this, x, y));
            }
        }
        open = true;
    }

    /**
     *
     *
     * @param category
     * @param x
     * @param y
     */
    public CategoryFrame(ModuleCategory category, float x, float y)
    {
        this(category, x, y, 88.0f, 14.0f);
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
    public void render(MatrixStack matrices, float mouseX, float mouseY,
                       float delta)
    {
        if (isWithin(mouseX, mouseY) && ClickGuiScreen.MOUSE_LEFT_HOLD)
        {
            drag = true;
        }
        if (drag)
        {
            x += ClickGuiScreen.MOUSE_X - px;
            y += ClickGuiScreen.MOUSE_Y - py;
        }
        // draw the component
        rect(matrices, Modules.COLORS.getRGB());
        RenderManager.renderText(matrices, name, x + 3.0f, y + 3.0f, -1);
        if (open)
        {
            fheight = 3.0f;
            for (ModuleButton moduleButton : moduleButtons)
            {
                // account for button height
                fheight += 15.5f;
                if (moduleButton.isOpen())
                {
                    fheight += 0.5f;
                    for (ConfigButton<?> configButton :
                            moduleButton.getConfigButtons())
                    {
                        // config button height may vary
                        fheight += configButton.getHeight();
                    }
                }
            }
            fill(matrices, x, y + height, 88.0f, fheight, 0x77000000);
            off = y + height + 1.0f;
            for (ModuleButton moduleButton : moduleButtons)
            {
                moduleButton.render(matrices, x + 1.0f, off + 1, mouseX,
                        mouseY, delta);
                off += 15.5f;
            }
        }
        // update previous position
        px = ClickGuiScreen.MOUSE_X;
        py = ClickGuiScreen.MOUSE_Y;
    }

    /**
     *
     *
     * @param mouseX
     * @param mouseY
     * @param mouseButton
     */
    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == GLFW.GLFW_MOUSE_BUTTON_RIGHT && isWithin(mouseX, mouseY))
        {
            open = !open;
        }
        if (open)
        {
            for (ModuleButton button : moduleButtons)
            {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    /**
     *
     *
     * @param mouseX
     * @param mouseY
     * @param mouseButton
     */
    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton)
    {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        drag = false;
        if (open)
        {
            for (ModuleButton button : moduleButtons)
            {
                button.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    /**
     *
     *
     * @param keyCode
     * @param scanCode
     * @param modifiers
     */
    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers)
    {
        super.keyPressed(keyCode, scanCode, modifiers);
        if (open)
        {
            for (ModuleButton button : moduleButtons)
            {
                button.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    /**
     *
     * @param mx
     * @param my
     * @return
     */
    public boolean isWithinTotal(float mx, float my)
    {
        return isMouseOver(mx, my, x, y, width, getTotalHeight());
    }

    /**
     * Update global offset
     *
     * @param in The offset
     */
    public void offset(float in)
    {
        off += in;
    }

    /**
     *
     *
     * @return
     */
    public ModuleCategory getCategory()
    {
        return category;
    }

    /**
     * Gets the total height of the frame
     *
     * @return The total height
     */
    public float getTotalHeight()
    {
        return height + fheight;
    }

    /**
     *
     *
     * @return
     */
    public List<ModuleButton> getModuleButtons()
    {
        return moduleButtons;
    }
}
