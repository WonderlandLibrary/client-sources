package net.shoreline.client.impl.module.client;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.config.NumberDisplay;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.impl.gui.click.ClickGuiScreen;
import org.lwjgl.glfw.GLFW;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see ClickGuiScreen
 */
public class ClickGuiModule extends ToggleModule
{
    //
    Config<Float> scaleConfig = new NumberConfig<>("Scale", "ClickGui " +
            "component scaling factor.", 0.1f, 1.0f, 3.0f, NumberDisplay.PERCENT);
    //
    private static ClickGuiScreen CLICK_GUI_SCREEN;

    /**
     *
     */
    public ClickGuiModule()
    {
        super("ClickGui", "Opens the clickgui screen", ModuleCategory.CLIENT,
                GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    /**
     *
     */
    @Override
    public void onEnable()
    {
        if (mc.player == null || mc.world == null)
        {
            toggle();
            return;
        }
        // initialize the null gui screen instance
        if (CLICK_GUI_SCREEN == null)
        {
            CLICK_GUI_SCREEN = new ClickGuiScreen(this);
        }
        mc.setScreen(CLICK_GUI_SCREEN);
    }

    /**
     *
     */
    @Override
    public void onDisable()
    {
        if (mc.player == null || mc.world == null)
        {
            toggle();
            return;
        }
        mc.player.closeScreen();
    }

    /**
     *
     *
     * @return
     */
    public Float getScale()
    {
        return scaleConfig.getValue();
    }
}
