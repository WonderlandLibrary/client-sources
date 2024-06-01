package io.github.liticane.clients.feature.module.impl.movement;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.BoundingEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.misc.ChatUtil;
import io.github.liticane.clients.util.misc.TimerUtil;
import io.github.liticane.clients.util.player.MoveUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Module.Info(name = "Flight", category = Module.Category.MOVEMENT)
public class Flight extends Module {
    public StringProperty mode = new StringProperty("Mode", this, "Motion", "Motion","Sparky$$$","Dev", "Vulcan");
    public NumberProperty timer = new NumberProperty("Timer", this, 1, 1, 10, 1);
    int flags = 0;
    private boolean damaged = false;
    private TimerUtil Timer = new TimerUtil();
    @Override
    protected void onEnable() {
        if(mode.is("Dev")) {
            if (mc.player.onGround) {
                mc.player.jump();
            }
            mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
            mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(mc.player.posX, mc.player.posY - 1.5, mc.player.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.world, new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
            mc.player.connection.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY + 3.001, mc.player.posZ, false));
            mc.player.connection.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        }
        damaged = false;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        flags = 0;
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
    private void setings() {
        //speed.setVisible(() -> mode.is("Motion"));
        //timer.setVisible(() -> mode.is("Vulcan"));
    }

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        mc.player.cameraYaw = 0.05F;
        setSuffix(mode.getMode());
        setings();
        if(mc.player.hurtTime > 1) {
            damaged = true;
        }
        switch (mode.getMode()) {
            case "Motion":
                mc.player.motionY = (mc.settings.keyBindJump.isKeyDown() ? 1 : mc.settings.keyBindSneak.isKeyDown() ? -1 : 0);
                MoveUtil.strafe(3);
                break;
            case "Vulcan":
                if(flags >= 3) {
                    mc.timer.timerSpeed = (float)timer.getValue();
                }
                break;
            case "Dev":
                ChatUtil.display(Timer.getTime());
                if(damaged) {
                    if(!Timer.hasTimeElapsed(1100)) {
                        MoveUtil.strafe(3);
                        } else {
                        MoveUtil.strafe(0.36f);
                        }
                        mc.player.motionY = 0;
                        mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                        mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(mc.player.posX, mc.player.posY - 1.5, mc.player.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.world, new BlockPos(-1, -1, -1))), 0, 0.94f, 0));

                } else {
                    Timer.reset();
                }
                break;
        }
    };
    @SubscribeEvent
    private final EventListener<PacketEvent> onPacketEvent = event -> {
        switch (mode.getMode()) {
            case "Vulcan":
                if(event.getPacket() instanceof S08PacketPlayerPosLook) {
                    flags++;
                    event.setCancelled(true);
                }
                break;
        }

    };
    @SubscribeEvent
    private final EventListener<BoundingEvent> onBoundingEvent = event -> {
        final AxisAlignedBB axisAlignedBB = AxisAlignedBB.fromBounds(-5, -1, -5, 5, 1, 5).offset(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ());
        if(mode.is("Vulcan") || mode.is("Sparky$$$")) {
            event.setBoundingBox(axisAlignedBB);
        }
    };
}
