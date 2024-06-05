package net.shoreline.client.api.macro;

import net.shoreline.client.api.Identifiable;
import net.shoreline.client.api.manager.client.MacroManager;
import org.lwjgl.glfw.GLFW;

/**
 *
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see MacroManager
 */
public class Macro implements Identifiable
{
    //
    private final String name;
    // Runnable macro which represents the functionality of the keybind. This
    // code block will run when the key is pressed.
    private final Runnable macro;
    // The GLFW keycode which represents the macro keybind.
    private int keycode;

    /**
     *
     * @param name
     * @param keycode
     * @param macro
     */
    public Macro(String name, int keycode, Runnable macro)
    {
        this.name = name;
        this.keycode = keycode;
        this.macro = macro;
    }

    /**
     *
     *
     * @see Runnable#run()
     */
    public void runMacro()
    {
        macro.run();
    }

    /**
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return
     */
    public Runnable getRunnable()
    {
        return macro;
    }

    /**
     *
     * @param keycode
     */
    public void setKeycode(int keycode)
    {
        this.keycode = keycode;
    }

    /**
     * Returns the macro keybind represented as {@link GLFW} keycode integer
     * between <b>0 and 348</b>.
     *
     * @return The macro keycode
     * @see #keycode
     */
    public int getKeycode()
    {
        return keycode;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String getId()
    {
        return String.format("%s-macro", name.toLowerCase());
    }

    /**
     * Returns the name associated with the macro keycode. Equivalent to
     * {@link GLFW#glfwGetKeyName(int, int)} on {@link #keycode}.
     *
     * @return The macro key name
     * @see #keycode
     * @see GLFW#glfwGetKeyScancode(int)
     */
    public String getKeyName()
    {
        if (keycode != GLFW.GLFW_KEY_UNKNOWN)
        {
            final String name = GLFW.glfwGetKeyName(keycode,
                    GLFW.glfwGetKeyScancode(keycode));
            return name != null ? name.toUpperCase() : "NONE";
        }
        return "NONE";
    }
}
