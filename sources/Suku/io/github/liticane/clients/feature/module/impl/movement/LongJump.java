package io.github.liticane.clients.feature.module.impl.movement;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.MoveEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.misc.ChatUtil;
import io.github.liticane.clients.util.player.MoveUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.util.Random;

/**
 * @author watchdog longjump by frap or pluh
 * @since i have no idea when he made it
 */
@Module.Info(name = "LongJump", category = Module.Category.MOVEMENT)
public class LongJump extends Module{
    public StringProperty mode = new StringProperty("Mode", this, "Vanilla", "Vanilla","Watchdog","Watchdog FB","Verus High", "Verus");
    public NumberProperty amount = new NumberProperty("Amount", this, 0.5, 0.1, 10, 0.1);
    private int jumps, ticks;
    public boolean jumping;
    private boolean shouldGlide = false;
    private boolean damage = false;

    @Override
    protected void onEnable() {
        shouldGlide = false;
        jumping = false;
        damage = false;
        jumps = 0;
        ticks = 0;
        if(mode.is("Verus")) {
            mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
            mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(mc.player.posX, mc.player.posY - 1.5, mc.player.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.world, new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
            mc.player.connection.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY + 3.001, mc.player.posZ, false));
            mc.player.connection.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        }
        super.onEnable();
    }
    @SubscribeEvent
    private final EventListener<MoveEvent> moveent = event -> {
        if (!jumping && mode.is("Watchdog")) {
            event.setX(0);
            event.setZ(0);
        }
    };
    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = event -> {
        this.setSuffix(mode.getMode());
        ticks++;
        if(mc.player.hurtTime > 1) {
            damage = true;
        }
        switch (mode.getMode()) {
            case "Watchdog":
            event.setOnGround(ticks == 39);

            if (ticks == 35 || ticks == 39) {
                jumping = true;
            }

            if (ticks > 40 && mc.player.onGround) {
                mc.player.motionX = mc.player.motionZ = 0;
                this.setToggled(false);
            }

            if (jumps < 4 && mc.player.onGround) {
                mc.player.jump();
                jumps++;
            }
                break;
            case "Vanilla":
                if(mc.player.onGround) {
                    mc.player.jump();
                }
                MoveUtil.strafe(amount.getValue());
                break;
            case "Verus High":
                mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(mc.player.posX, mc.player.posY - 1.5, mc.player.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.world, new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
                if(mc.player.onGround) {
                    //3.0
                    mc.player.motionY = 2.88;
                }
                MoveUtil.strafe(MoveUtil.baseSpeed());
                break;
            case "Watchdog FB":
                if(mc.player.hurtTime >= 5) {
                   ChatUtil.display(MathHelper.randomFloatClamp(new Random(),1f,1.1f));
                   MoveUtil.strafe(MathHelper.randomFloatClamp(new Random(),1f,1.1f));
                }
                mc.player.setSprinting(true);
                break;
            case "Verus":
                if(damage) {
                    if(!shouldGlide) {
                        mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
                        mc.player.connection.sendSilent(new C08PacketPlayerBlockPlacement(new BlockPos(mc.player.posX, mc.player.posY - 1.5, mc.player.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.world, new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
                        if (mc.player.onGround) {
                            mc.player.motionY = 0.7;
                        }
                        if (mc.player.motionY > 0.5) {
                            MoveUtil.strafe(5);
                        }
                    } else {
                        if(ticks > 25) {
                            MoveUtil.strafe(MoveUtil.baseSpeed());
                        }
                        mc.player.motionY = -0.0980000019;
                    }
                    if(ticks > 15) {
                       shouldGlide = true;
                    }
                }
                break;
        }
    };
    @SubscribeEvent
    private final EventListener<PacketEvent> onpacketo = event -> {
        switch (mode.getMode()) {
            case "Watchdog":
            if (event.getPacket() instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

                if (mc.player.getEntityId() == packet.getEntityID()) {
                    event.setCancelled(true);

                    MoveUtil.strafe(0.32);

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    MoveUtil.strafe(0.3);

                    mc.player.motionY = packet.getMotionY() / 8000D;
                }
            }
                break;
            case "Watchdog FB": {
               // if(mc.player.hurtTime != 0) {
                //                    if (event.getPacket() instanceof S12PacketEntityVelocity) {
                //                        S12PacketEntityVelocity velocityPacket = (S12PacketEntityVelocity) event.getPacket();
                //
                //                        velocityPacket.motionX = velocityPacket.motionX * 2;
                //                        velocityPacket.motionZ = velocityPacket.motionZ * 2;
                //                        ChatUtil.display("X " + velocityPacket.getMotionX() + " Y " + velocityPacket.getMotionY() + " Z " + velocityPacket.getMotionZ());
                //                    }
                //                }
            }
        }
    };
}
