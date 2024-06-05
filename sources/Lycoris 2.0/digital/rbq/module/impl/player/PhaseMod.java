/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.player;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import digital.rbq.annotations.Label;
import digital.rbq.events.game.TickEvent;
import digital.rbq.events.packet.ReceivePacketEvent;
import digital.rbq.events.player.BlockRenderEvent;
import digital.rbq.events.player.BoundingBoxEvent;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.events.player.MoveEvent;
import digital.rbq.events.player.PushEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Bind;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.MovementUtils;
import digital.rbq.utils.PlayerUtils;

@Label(value="Phase")
@Bind(value="X")
@Category(value=ModuleCategory.PLAYER)
public final class PhaseMod
extends Module {
    private final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.NORMAL);
    private int moveUnder;

    public PhaseMod() {
        this.setMode(this.mode);
        this.addOptions(this.mode);
    }

    @Override
    public void onEnabled() {
    }

    @Listener(value=TickEvent.class)
    public final void onTick(TickEvent event) {
        if (PhaseMod.mc.thePlayer != null && this.moveUnder == 1) {
            PhaseMod.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PhaseMod.mc.thePlayer.posX, PhaseMod.mc.thePlayer.posY - 2.0, PhaseMod.mc.thePlayer.posZ, false));
            PhaseMod.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, true));
            this.moveUnder = 0;
        }
        if (PhaseMod.mc.thePlayer != null && this.moveUnder == 1488) {
            double mx = -Math.sin(Math.toRadians(PhaseMod.mc.thePlayer.rotationYaw));
            double mz = Math.cos(Math.toRadians(PhaseMod.mc.thePlayer.rotationYaw));
            double x = (double)PhaseMod.mc.thePlayer.movementInput.moveForward * mx + (double)PhaseMod.mc.thePlayer.movementInput.moveStrafe * mz;
            double z = (double)PhaseMod.mc.thePlayer.movementInput.moveForward * mz - (double)PhaseMod.mc.thePlayer.movementInput.moveStrafe * mx;
            PhaseMod.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PhaseMod.mc.thePlayer.posX + x, PhaseMod.mc.thePlayer.posY, PhaseMod.mc.thePlayer.posZ + z, false));
            PhaseMod.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Double.NEGATIVE_INFINITY, PhaseMod.mc.thePlayer.posY, Double.NEGATIVE_INFINITY, true));
            this.moveUnder = 0;
        }
    }

    @Listener(value=BoundingBoxEvent.class)
    public final void onBoundingBox(BoundingBoxEvent event) {
        switch ((Mode)((Object)this.mode.getValue())) {
            case NORMAL: {
                if (!PlayerUtils.isInsideBlock()) break;
                event.setBoundingBox(null);
                break;
            }
            case VANILLA: {
                if (PhaseMod.mc.thePlayer.isCollidedHorizontally && !PlayerUtils.isInsideBlock()) {
                    double mx = -Math.sin(Math.toRadians(PhaseMod.mc.thePlayer.rotationYaw));
                    double mz = Math.cos(Math.toRadians(PhaseMod.mc.thePlayer.rotationYaw));
                    double x = (double)PhaseMod.mc.thePlayer.movementInput.moveForward * mx + (double)PhaseMod.mc.thePlayer.movementInput.moveStrafe * mz;
                    double z = (double)PhaseMod.mc.thePlayer.movementInput.moveForward * mz - (double)PhaseMod.mc.thePlayer.movementInput.moveStrafe * mx;
                    event.setBoundingBox(null);
                    PhaseMod.mc.thePlayer.setPosition(PhaseMod.mc.thePlayer.posX + x, PhaseMod.mc.thePlayer.posY, PhaseMod.mc.thePlayer.posZ + z);
                    this.moveUnder = 69;
                }
                if (!PlayerUtils.isInsideBlock()) break;
                event.setBoundingBox(null);
            }
        }
    }

    @Listener(value=PushEvent.class)
    public final void onPush(PushEvent event) {
        event.setCancelled();
    }

    @Listener(value=BlockRenderEvent.class)
    public final void onBlockRender(BlockRenderEvent event) {
        event.setCancelled();
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        switch ((Mode)((Object)this.mode.getValue())) {
            case NORMAL: {
                if (event.isPre()) break;
                double multiplier = 0.3;
                double mx = -Math.sin(Math.toRadians(PhaseMod.mc.thePlayer.rotationYaw));
                double mz = Math.cos(Math.toRadians(PhaseMod.mc.thePlayer.rotationYaw));
                double x = (double)PhaseMod.mc.thePlayer.movementInput.moveForward * multiplier * mx + (double)PhaseMod.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
                double z = (double)PhaseMod.mc.thePlayer.movementInput.moveForward * multiplier * mz - (double)PhaseMod.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
                if (!PhaseMod.mc.thePlayer.isCollidedHorizontally || PhaseMod.mc.thePlayer.isOnLadder()) break;
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(PhaseMod.mc.thePlayer.posX + x, PhaseMod.mc.thePlayer.posY + 0.001, PhaseMod.mc.thePlayer.posZ + z, false));
                for (int i = 1; i < 10; ++i) {
                    mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(PhaseMod.mc.thePlayer.posX, PhaseMod.mc.thePlayer.posY - 0.22, PhaseMod.mc.thePlayer.posZ, false));
                }
                PhaseMod.mc.thePlayer.setPosition(PhaseMod.mc.thePlayer.posX + x, PhaseMod.mc.thePlayer.posY, PhaseMod.mc.thePlayer.posZ + z);
                break;
            }
            case VANILLA: {
                if (this.mode.getValue() != Mode.VANILLA || !PhaseMod.mc.gameSettings.keyBindSneak.isPressed() || PlayerUtils.isInsideBlock()) break;
                PhaseMod.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(PhaseMod.mc.thePlayer.posX, PhaseMod.mc.thePlayer.posY - 2.0, PhaseMod.mc.thePlayer.posZ, true));
                this.moveUnder = 2;
            }
        }
    }

    @Listener(value=ReceivePacketEvent.class)
    public final void onReceive(ReceivePacketEvent event) {
        S02PacketChat packet;
        if (event.getPacket() instanceof S02PacketChat && (packet = (S02PacketChat)event.getPacket()).getChatComponent().getUnformattedText().contains("You cannot go past the border.")) {
            event.setCancelled();
        }
        if (this.mode.getValue() == Mode.VANILLA && event.getPacket() instanceof S08PacketPlayerPosLook && this.moveUnder == 2) {
            this.moveUnder = 1;
        }
        if (this.mode.getValue() == Mode.VANILLA && event.getPacket() instanceof S08PacketPlayerPosLook && this.moveUnder == 69) {
            this.moveUnder = 1488;
        }
    }

    @Listener(value=MoveEvent.class)
    public final void onMove(MoveEvent event) {
        switch ((Mode)((Object)this.mode.getValue())) {
            case NORMAL: {
                if (!PlayerUtils.isInsideBlock()) break;
                if (PhaseMod.mc.gameSettings.keyBindJump.isKeyDown()) {
                    event.y = PhaseMod.mc.thePlayer.motionY += (double)0.09f;
                } else if (PhaseMod.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    event.y = PhaseMod.mc.thePlayer.motionY -= 0.0;
                } else {
                    PhaseMod.mc.thePlayer.motionY = 0.0;
                    event.y = 0.0;
                }
                MovementUtils.setSpeed(event, 0.3);
                if (PhaseMod.mc.thePlayer.ticksExisted % 2 != 0) break;
                event.y = PhaseMod.mc.thePlayer.motionY += (double)0.09f;
                break;
            }
            case VANILLA: {
                if (!PlayerUtils.isInsideBlock()) break;
                if (PhaseMod.mc.gameSettings.keyBindJump.isKeyDown()) {
                    PhaseMod.mc.thePlayer.motionY = 0.5;
                    event.y = 0.5;
                } else if (PhaseMod.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    PhaseMod.mc.thePlayer.motionY = -0.5;
                    event.y = -0.5;
                } else {
                    PhaseMod.mc.thePlayer.motionY = 0.0;
                    event.y = 0.0;
                }
                MovementUtils.setSpeed(event, 0.28);
            }
        }
    }

    private static enum Mode {
        NORMAL,
        VANILLA;

    }
}

