package pw.latematt.xiv.mod.mods.player;

import net.minecraft.entity.player.EnumPlayerModelParts;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

import java.util.Random;

/**
 * @author Jack
 */

public class SkinFlash extends Mod implements Listener<MotionUpdateEvent> {
    private final Random random = new Random();

    public SkinFlash() {
        super("SkinFlash", ModType.PLAYER, Keyboard.KEY_NONE);
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        EnumPlayerModelParts[] parts = EnumPlayerModelParts.values();

        if (parts != null) {
            for (EnumPlayerModelParts part : parts) {
                mc.gameSettings.func_178878_a(part, random.nextBoolean());
            }
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);

        if (mc.thePlayer != null) {
            EnumPlayerModelParts[] parts = EnumPlayerModelParts.values();

            if (parts != null) {
                for (EnumPlayerModelParts part : parts) {
                    mc.gameSettings.func_178878_a(part, true);
                }
            }
        }
    }
}
