package pw.latematt.xiv.mod.mods.example;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * Use this template when making modules
 *
 * @author Matthew
 */
public class Example extends Mod implements Listener<SendPacketEvent>, CommandHandler {
    public Example() {
        super("Example", ModType.PLAYER, Keyboard.KEY_NONE, 0xFF696969);
        Command.newCommand().cmd("example").description("An example command").arguments("<ex>", "[am]", "<pl>", "[e]").aliases("ex", "am", "pl", "e").handler(this).build();
    }

    public void onEventCalled(SendPacketEvent event) {

    }

    @Override
    public void onCommandRan(String message) {

    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
