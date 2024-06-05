package net.shoreline.client.impl.gui.click.impl.config;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.macro.Macro;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.gui.click.component.Button;
import net.shoreline.client.init.Modules;
import net.minecraft.client.util.math.MatrixStack;
import net.shoreline.client.impl.gui.click.impl.config.setting.*;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Module
 * @see CategoryFrame
 */
public class ModuleButton extends Button
{
    //
    private boolean open;
    private final Module module;
    //
    private final List<ConfigButton<?>> configComponents =
            new CopyOnWriteArrayList<>();

    /**
     *
     *
     * @param module
     * @param frame
     * @param x
     * @param y
     */
    @SuppressWarnings("unchecked")
    public ModuleButton(Module module, CategoryFrame frame, float x, float y)
    {
        super(frame, x, y, 86.0f, 15.0f);
        this.module = module;
        for (Config<?> config : module.getConfigs())
        {
            if (config.getName().equalsIgnoreCase("Enabled"))
            {
                continue;
            }
            if (config.getValue() instanceof Boolean)
            {
                configComponents.add(new CheckboxButton(frame,
                        (Config<Boolean>) config, x, y));
            }
            else if (config.getValue() instanceof Double)
            {
                configComponents.add(new SliderButton<>(frame,
                        (Config<Double>) config, x, y));
            }
            else if (config.getValue() instanceof Float)
            {
                configComponents.add(new SliderButton<>(frame,
                        (Config<Float>) config, x, y));
            }
            else if (config.getValue() instanceof Integer)
            {
                configComponents.add(new SliderButton<>(frame,
                        (Config<Integer>) config, x, y));
            }
            else if (config.getValue() instanceof Enum<?>)
            {
                configComponents.add(new DropdownButton(frame,
                        (Config<Enum<?>>) config, x, y));
            }
            else if (config.getValue() instanceof String)
            {
                configComponents.add(new TextButton(frame,
                        (Config<String>) config, x, y));
            }
            else if (config.getValue() instanceof Macro)
            {
                configComponents.add(new BindButton(frame,
                        (Config<Macro>) config, x, y));
            }
            else if (config.getValue() instanceof Color)
            {
                configComponents.add(new ColorButton(frame,
                        (Config<Color>) config, x, y));
            }
        }
        open = false;
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
        render(matrices, x, y, mouseX, mouseY, delta);
    }

    /**
     *
     *
     * @param matrices
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    public void render(MatrixStack matrices, float ix, float iy, float mouseX,
                       float mouseY, float delta)
    {
        x = ix;
        y = iy;
        boolean fill = !(module instanceof ToggleModule t) || t.isEnabled();
        rect(matrices, fill ? Modules.COLORS.getRGB() : 0x55555555);
        RenderManager.renderText(matrices, module.getName(), ix + 2, iy + 4, -1);
        if (open)
        {
            float off = y + height + 0.5f;
            for (ConfigButton<?> configButton : configComponents)
            {
                // run draw event
                configButton.render(matrices, ix + 0.5f, off, mouseX, mouseY, delta);
                ((CategoryFrame) frame).offset(15.0f);
                off += 15.0f;
            }
        }
    }

    /**
     *
     *
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button)
    {
        if (isWithin(mouseX, mouseY))
        {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT
                    && module instanceof ToggleModule t)
            {
                t.toggle();
                //
                // ToggleGuiEvent toggleGuiEvent = new ToggleGuiEvent(t);
                // Caspian.EVENT_HANDLER.dispatch(toggleGuiEvent);
            }
            else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
            {
                open = !open;
            }
        }
        if (open)
        {
            for (ConfigButton<?> component : configComponents)
            {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    /**
     *
     *
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseReleased(double mouseX, double mouseY, int button)
    {
        if (open)
        {
            for (ConfigButton<?> component : configComponents)
            {
                component.mouseReleased(mouseX, mouseY, button);
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
        if (open)
        {
            for (ConfigButton<?> component : configComponents)
            {
                component.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    /**
     *
     *
     * @return
     */
    public boolean isOpen()
    {
        return open;
    }

    /**
     *
     *
     * @return
     */
    public Module getModule()
    {
        return module;
    }

    /**
     *
     *
     * @return
     */
    public List<ConfigButton<?>> getConfigButtons()
    {
        return configComponents;
    }
}
