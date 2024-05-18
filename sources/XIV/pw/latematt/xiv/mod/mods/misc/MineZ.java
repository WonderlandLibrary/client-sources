package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.input.Keyboard;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.PlayerDeathEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.BlockUtils;
import pw.latematt.xiv.utils.InventoryUtils;

/**
 * @author Matthew
 */
public class MineZ extends Mod {
    private final Timer deathTimer = new Timer(), waterTimer = new Timer();
    private final Listener playerDeathListener, motionUpdateListener;
    private boolean needsToRespawn;

    public MineZ() {
        super("MineZ", ModType.MISCELLANEOUS, Keyboard.KEY_NONE, 0xFFBDFC2B);

        motionUpdateListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                if (event.getCurrentState() == MotionUpdateEvent.State.PRE) {
                    if (needsToRespawn && deathTimer.hasReached(1000)) {
                        mc.thePlayer.sendChatMessage("/minez spawn");
                        needsToRespawn = false;
                    }
                } else if (event.getCurrentState() == MotionUpdateEvent.State.POST) {
                    if (mc.thePlayer.experienceLevel < 20 && waterTimer.hasReached(500) && InventoryUtils.hotbarHasPotion(null, false)) {
                        if (mc.thePlayer.onGround || BlockUtils.isOnLadder(mc.thePlayer) || BlockUtils.isInLiquid(mc.thePlayer) || BlockUtils.isOnLiquid(mc.thePlayer)) {
                            final boolean wasSprinting = mc.thePlayer.isSprinting();
                            if (wasSprinting)
                                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                            InventoryUtils.instantUseFirstPotion(null);
                            if (wasSprinting)
                                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                            mc.thePlayer.setSprinting(wasSprinting);
                            waterTimer.reset();
                        }
                    }
                }
            }
        };

        playerDeathListener = new Listener<PlayerDeathEvent>() {
            @Override
            public void onEventCalled(PlayerDeathEvent event) {
                mc.thePlayer.respawnPlayer();
                deathTimer.reset();
                needsToRespawn = true;
            }
        };
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(motionUpdateListener, playerDeathListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(motionUpdateListener, playerDeathListener);
    }
}
