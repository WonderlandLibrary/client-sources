package us.dev.direkt.module.internal.movement.speed.internal.modes;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockStairs;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.internal.movement.Step;
import us.dev.direkt.module.internal.movement.Timer;
import us.dev.direkt.module.internal.movement.speed.internal.AbstractSpeedMode;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author Foundry
 */
public class NGroundMode extends AbstractSpeedMode {

    private int airTicks;
    private boolean groundBoolean, isSpeeding;

    public NGroundMode() {
        super("NGround");
    }

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        if (Math.abs(Wrapper.getPlayer().moveForward) + Math.abs(Wrapper.getPlayer().moveStrafing) > 0.1f && !Wrapper.getPlayer().isCollidedHorizontally && !(Wrapper.getBlock(new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 1, Wrapper.getPlayer().posZ)) instanceof BlockStairs)) {
            Wrapper.getPlayer().motionY = -9F;
        }
        if ((Wrapper.getBlockUnderPlayer(Wrapper.getPlayer()) instanceof BlockIce) || (Wrapper.getBlockUnderPlayer(Wrapper.getPlayer()) instanceof BlockPackedIce)) {
            Wrapper.getBlockUnderPlayer(Wrapper.getPlayer()).slipperiness = 0.65F;
        }
        if ((Wrapper.getPlayer().onGround || (Wrapper.getPlayer().isCollidedVertically)) && (Wrapper.getBlock(new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 2, Wrapper.getPlayer().posZ)) instanceof BlockAir)) {
            if (Wrapper.getGameSettings().keyBindForward.isKeyDown())
                this.airTicks++;
            else
                this.airTicks = 0;
            if (this.airTicks >= 4 && Wrapper.onGround()) {
                isSpeeding = true;
                double[] dir = Wrapper.moveLooking(0);
                double xD = dir[0];
                double zD = dir[1];
                if (!this.groundBoolean) {
                    if (Direkt.getInstance().getModuleManager().getModule(Timer.class).isRunning())
                        Wrapper.getMinecraft().getTimer().timerSpeed = Keyboard.isKeyDown(Keyboard.KEY_LMENU) ? 2.5F : 1.55F;

                    Wrapper.getPlayer().motionX *= 3.3F;
                    Wrapper.getPlayer().motionZ *= 3.3F;
                } else {
                    if (Direkt.getInstance().getModuleManager().getModule(Timer.class).isRunning())
                        Wrapper.getMinecraft().getTimer().timerSpeed = 0.8F;
                    Wrapper.getPlayer().motionX /= 1.4; //this number is really fucking sensitive.
                    Wrapper.getPlayer().motionZ /= 1.4;
                }
            } else {
                isSpeeding = false;
                Wrapper.getMinecraft().getTimer().timerSpeed = 1.0F;
                this.groundBoolean = true;
            }
        } else {
            this.airTicks = 3;
            Wrapper.getPlayer().motionX /= 1.3F;
            Wrapper.getPlayer().motionZ /= 1.3F;
            Wrapper.getMinecraft().getTimer().timerSpeed = 1F;
        }

    }, Link.VERY_HIGH_PRIORITY);

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        if (Step.cancelSomePackets) {
            this.airTicks = 4;
        }
        if (Wrapper.getPlayer().onGround && Wrapper.getPlayer().isCollidedVertically
                && this.airTicks > 5 && Wrapper.getBlock(new BlockPos(Wrapper.getPlayer().posX,
                Wrapper.getPlayer().posY + 2, Wrapper.getPlayer().posZ)) instanceof BlockAir
                && this.airTicks > 5) {
            this.groundBoolean = !this.groundBoolean;
            if (groundBoolean) {
                Wrapper.getPlayer().stepHeight = Direkt.getInstance().getModuleManager().getModule(Step.class)
                        .isRunning() ? 1.0F : 0F;
                event.setPacket(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().onGround));
            } else {
                Wrapper.getPlayer().stepHeight = Direkt.getInstance().getModuleManager().getModule(Step.class)
                        .isRunning() ? 0F : 0.5F;
                event.setPacket(new CPacketPlayer.PositionRotation(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.425, Wrapper.getPlayer().posZ, Wrapper.getPlayer().rotationYaw, Wrapper.getPlayer().rotationPitch, Wrapper.getPlayer().onGround));
            }
        }
    }, Link.VERY_HIGH_PRIORITY, new PacketFilter<>(CPacketPlayer.class));

    @Override
    public void onDisable() {
/*		for(int i = 0; i < 500000; i++) {
			Wrapper.updatePosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (i * 0.00001), Wrapper.getPlayer().posZ);
		}*/
    	Wrapper.getPlayer().stepHeight = 0.5F;
        isSpeeding = false;
        Wrapper.getMinecraft().getTimer().timerSpeed = 1.0F;
    }

    @Override
    public boolean isSpeeding() {
        return isSpeeding;
    }
}
