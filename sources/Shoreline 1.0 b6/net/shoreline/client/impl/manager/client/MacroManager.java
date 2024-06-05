package net.shoreline.client.impl.manager.client;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.file.ConfigFile;
import net.shoreline.client.api.macro.Macro;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.keyboard.KeyboardInputEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.Globals;
import org.lwjgl.glfw.GLFW;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author linus
 * @see Macro
 * @since 1.0
 */
public class MacroManager implements Globals {
    // For handling macros
    //
    private final Set<Macro> macros = new HashSet<>();

    /**
     *
     */
    public MacroManager() {
        Shoreline.EVENT_HANDLER.subscribe(this);
    }

    /**
     * @param event
     */
    @EventListener
    public void onKeyboardInput(KeyboardInputEvent event) {
        if (mc.player == null || mc.world == null
                || mc.currentScreen != null) {
            return;
        }
        // module keybind impl
        for (Module module : Managers.MODULE.getModules()) {
            if (module instanceof ToggleModule toggle) {
                final Macro keybind = toggle.getKeybinding();
                if (event.getKeycode() != GLFW.GLFW_KEY_UNKNOWN
                        && event.getKeycode() == keybind.getKeycode()) {
                    keybind.runMacro();
                }
            }
        }
        //
        if (macros.isEmpty()) {
            return;
        }
        for (Macro macro : macros) {
            if (event.getKeycode() != GLFW.GLFW_KEY_UNKNOWN
                    && event.getKeycode() == macro.getKeycode()) {
                macro.runMacro();
            }
        }
    }

    /**
     * Loads custom macros from the
     * {@link ConfigFile} system
     */
    public void postInit() {
        // TODO
    }

    /**
     * @param macros
     */
    public void register(Macro... macros) {
        for (Macro macro : macros) {
            register(macro);
        }
    }

    /**
     * @param macro
     */
    public void register(Macro macro) {
        macros.add(macro);
    }

    /**
     * @return
     */
    public Collection<Macro> getMacros() {
        return macros;
    }
}
