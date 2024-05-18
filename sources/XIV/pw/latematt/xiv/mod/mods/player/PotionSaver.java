package pw.latematt.xiv.mod.mods.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.PotionIncrementEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Rederpz
 */
public class PotionSaver extends Mod implements Listener<SendPacketEvent> {
    private final Listener potionIncrementListener;

    public PotionSaver() {
        super("PotionSaver", ModType.PLAYER, Keyboard.KEY_NONE, 0xFF008800);

        potionIncrementListener = new Listener<PotionIncrementEvent>() {
            @Override
            public void onEventCalled(PotionIncrementEvent event) {
                if (canSave())
                    event.setIncrement(0);
            }
        };
    }

    @Override
    public void onEventCalled(SendPacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (canSave())
                event.setCancelled(true);
        }
    }

    private boolean canSave() {
        boolean usingItem = mc.thePlayer.isUsingItem();
        boolean swinging = mc.thePlayer.isSwingInProgress;
        boolean moving = mc.thePlayer.motionX != 0 || !mc.thePlayer.isCollidedVertically || mc.gameSettings.keyBindJump.getIsKeyPressed() || mc.thePlayer.motionZ != 0;
        boolean hasPotions = mc.thePlayer.getActivePotionEffects().size() > 0;
        return !usingItem && !swinging && !moving && hasPotions;
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, potionIncrementListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, potionIncrementListener);
    }
}
