package pw.latematt.xiv.mod.mods.misc;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.KeyPressEvent;
import pw.latematt.xiv.event.events.MouseClickEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Matthew
 */
public class Keybinds extends Mod implements Listener<KeyPressEvent> {
    private Listener mouseClickListener;

    public Keybinds() {
        super("Keybinds", ModType.MISCELLANEOUS);

        XIV.getInstance().getListenerManager().add(new Listener<KeyPressEvent>() {
            @Override
            public void onEventCalled(KeyPressEvent event) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                    if (event.getKeyCode() == Keyboard.KEY_INSERT && XIV.getInstance().getModManager().find("commands") != null)
                        XIV.getInstance().getModManager().find("commands").setEnabled(true);
            }
        });

        mouseClickListener = new Listener<MouseClickEvent>() {
            @Override
            public void onEventCalled(MouseClickEvent event) {
                /* mod mousebinds */
                XIV.getInstance().getModManager().getContents().stream()
                        .filter(mod -> mod.getKeybind() >= 256)
                        .filter(mod -> mod.getKeybind() - 256 == event.getButton())
                        .forEach(Mod::toggle);

                /* macro mousebinds */
                XIV.getInstance().getMacroManager().getContents().stream()
                        .filter(macro -> macro.getKeybind() >= 256)
                        .filter(macro -> macro.getKeybind() - 256 == event.getButton())
                        .forEach(macro -> XIV.getInstance().getCommandManager().parseCommand(XIV.getInstance().getCommandManager().getPrefix() + macro.getCommand()));
            }
        };
        setEnabled(true);
    }

    @Override
    public void onEventCalled(KeyPressEvent event) {
        /* mod keybinds */
        XIV.getInstance().getModManager().getContents().stream()
                .filter(mod -> mod.getKeybind() < 256 && mod.getKeybind() != Keyboard.KEY_NONE)
                .filter(mod -> mod.getKeybind() == event.getKeyCode())
                .forEach(Mod::toggle);

        /* macro keybinds */
        XIV.getInstance().getMacroManager().getContents().stream()
                .filter(macro -> macro.getKeybind() != Keyboard.KEY_NONE)
                .filter(macro -> macro.getKeybind() == event.getKeyCode())
                .forEach(macro -> XIV.getInstance().getCommandManager().parseCommand(XIV.getInstance().getCommandManager().getPrefix() + macro.getCommand()));
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, mouseClickListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, mouseClickListener);
    }
}
