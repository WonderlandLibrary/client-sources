package pw.latematt.xiv.macro;

/**
 * @author Matthew
 */
public final class Macro {
    private final int keybind;
    private final String command;

    public Macro(int keybind, String command) {
        this.keybind = keybind;
        this.command = command;
    }

    public int getKeybind() {
        return keybind;
    }

    public String getCommand() {
        return command;
    }
}
