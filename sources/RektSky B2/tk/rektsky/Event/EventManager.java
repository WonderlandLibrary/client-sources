package tk.rektsky.Event;

import net.minecraft.network.play.client.*;
import tk.rektsky.Files.*;
import tk.rektsky.Utils.*;
import tk.rektsky.*;
import tk.rektsky.Commands.*;
import tk.rektsky.Event.Events.*;
import tk.rektsky.Module.*;
import java.util.*;

public class EventManager
{
    static int ticks;
    
    private static void onEvent(final Event e) {
        ++EventManager.ticks;
        if (!(e instanceof PacketSentEvent) || ((PacketSentEvent)e).getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {}
        if (e instanceof WorldTickEvent) {}
        if (EventManager.ticks % 200 == 0 && e instanceof WorldTickEvent) {
            new Thread() {
                @Override
                public void run() {
                    FileManager.replaceAndSaveSettings();
                }
            }.start();
        }
        if (e instanceof RenderEvent && RotationUtil.doReset) {
            RotationUtil.reset();
        }
        for (final Module module : ModulesManager.getModules()) {
            if (module.isToggled()) {
                module.onEvent(e);
                if (e instanceof WorldTickEvent) {
                    final Module module2 = module;
                    ++module2.enabledTicks;
                }
            }
            if (e instanceof HUDRenderEvent) {
                Client.hud.draw(((HUDRenderEvent)e).getGui());
            }
            if (e instanceof KeyPressedEvent) {
                final KeyPressedEvent kpe = (KeyPressedEvent)e;
                if (kpe.getKey() == module.keyCode) {
                    module.toggle();
                }
            }
        }
        for (final Command command : CommandsManager.COMMANDS) {
            command.onEvent(e);
        }
        if (e instanceof ChatEvent) {
            CommandsManager.processCommand((ChatEvent)e);
        }
    }
    
    public static void callEvent(final Event event) {
        onEvent(event);
    }
    
    static {
        EventManager.ticks = 0;
    }
}
