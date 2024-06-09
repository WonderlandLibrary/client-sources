package us.dev.direkt.module.internal.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.math.BlockPos;
import us.dev.api.property.Property;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPostMotionUpdate;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.internal.movement.speed.Speed;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "No Slow", aliases = "noslowdown", category = ModCategory.MOVEMENT)
public class NoSlow extends ToggleableModule {

    @Exposed(description = "Should you move faster while sneaking")
    private final Property<Boolean> speedSneak = new Property<>("Fast Sneak", false);

    @Exposed(description = "Should you not slow down while using items")
    private final Property<Boolean> speedUse = new Property<>("Fast Use", true);

	private int packetTicks;
    private boolean wasSneaking;

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        if (!Direkt.getInstance().getModuleManager().getModule(Speed.class).isSpeeding()) {
            if (Wrapper.getPlayer().isHandActive() && speedUse.getValue() && MovementUtils.isMoving(Wrapper.getPlayer()) && (Wrapper.getGameSettings().keyBindForward.isKeyDown() || Wrapper.getGameSettings().keyBindLeft.isKeyDown() || Wrapper.getGameSettings().keyBindRight.isKeyDown() || Wrapper.getGameSettings().keyBindBack.isKeyDown())) {
                double[] dir = Wrapper.moveLooking(0);
                double xDir = dir[0];
                double zDir = dir[1];
                Wrapper.getPlayer().motionX = xDir * 0.241F;
                Wrapper.getPlayer().motionZ = zDir * 0.241F;
                if (Wrapper.onGround() && Wrapper.getPlayer().isActiveItemStackBlocking())
                    Wrapper.getPlayer().motionY = 0.17F;
            }
        }
    });

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        if (!Direkt.getInstance().getModuleManager().getModule(Speed.class).isSpeeding()) {
            if (Wrapper.getBlock(new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 0.875, Wrapper.getPlayer().posZ)) instanceof BlockSoulSand) {
                if (Wrapper.onGround() && MovementUtils.isMoving(Wrapper.getPlayer()) && (Wrapper.getGameSettings().keyBindForward.isKeyDown() || Wrapper.getGameSettings().keyBindLeft.isKeyDown() || Wrapper.getGameSettings().keyBindRight.isKeyDown() || Wrapper.getGameSettings().keyBindBack.isKeyDown())) {
                    double[] dir = Wrapper.moveLooking(0);
                    double xDir = dir[0];
                    double zDir = dir[1];
                    if (!Wrapper.getPlayer().isSprinting()) {
                        Wrapper.getPlayer().motionX = xDir * 0.221 / 2F;
                        Wrapper.getPlayer().motionZ = zDir * 0.221 / 2F;
                    } else {
                        Wrapper.getPlayer().motionX = xDir * 0.221 / 1.5F;
                        Wrapper.getPlayer().motionZ = zDir * 0.221 / 1.5F;
                    }
                }
            }
            if (Wrapper.getPlayer().isSneaking()) {
                if (MovementUtils.isMoving(Wrapper.getPlayer())) {
                    if (speedSneak.getValue())
                        Wrapper.sendPacket(new CPacketEntityAction(Wrapper.getPlayer(), Action.STOP_SNEAKING));
                }
                wasSneaking = true;
            } else {
                if (wasSneaking) {
                    if (!Direkt.getInstance().getModuleManager().getModule(Sprint.class).isRunning() && speedSneak.getValue()) {
                        Wrapper.getPlayer().setSprinting(false);
                    }
                    wasSneaking = false;
                }
            }
            if (Block.getIdFromBlock(Wrapper.getBlock(new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 0.5, Wrapper.getPlayer().posZ))) == 30 && Wrapper.getGameSettings().keyBindForward.isKeyDown()) {
                double[] dir = Wrapper.moveLooking(0);
                double xDir = dir[0];
                double zDir = dir[1];
                Wrapper.getPlayer().motionX = xDir * 0.43F;
                Wrapper.getPlayer().motionZ = zDir * 0.43F;
                if (Wrapper.getPlayer().onGround && Wrapper.getPlayer().isCollidedVertically)
                    Wrapper.getPlayer().motionY = 1F;
            }
        }
    });

    @Listener
    protected Link<EventPostMotionUpdate> onPostMotionUpdate = new Link<>(event -> {
        if (Wrapper.getPlayer().isSneaking() && MovementUtils.isMoving(Wrapper.getPlayer())) {
            wasSneaking = true;
            if (speedSneak.getValue()) {
                Wrapper.getPlayer().setSprinting(Wrapper.getPlayer().getFoodStats().getFoodLevel() > 6);

                Wrapper.getBlockUnderPlayer(Wrapper.getPlayer()).slipperiness = 0.35F;
                Wrapper.sendPacket(new CPacketEntityAction(Wrapper.getPlayer(), Action.START_SNEAKING));
                Wrapper.sendPacket(new CPacketEntityAction(Wrapper.getPlayer(), Action.START_SNEAKING));
            }
        } else {
            Wrapper.getBlockUnderPlayer(Wrapper.getPlayer()).slipperiness = 0.6F;
        }
    });

	@Override
	public void onEnable() {
        wasSneaking = false;
		Blocks.PACKED_ICE.slipperiness = 0.5F;
		Blocks.ICE.slipperiness = 0.5F;
	}
	
	@Override
	public void onDisable() {
		Blocks.PACKED_ICE.slipperiness = 0.98F;
		Blocks.ICE.slipperiness = 0.98F;
	}
	
}

