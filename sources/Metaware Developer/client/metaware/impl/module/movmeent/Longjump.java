package client.metaware.impl.module.movmeent;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.gui.notis.NotificationType;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.client.Logger;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.event.impl.system.TickEvent;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ModuleInfo(renderName = "LongJump", name = "LongJump", category = Category.MOVEMENT)
public class Longjump extends Module {

    private EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Watchdog);
    private double verticalSpeed = 0.0;
    private int tick, stage;
    private boolean shouldBoost, wait;
    private double baseSpeed, moveSpeed;
    private List<Packet> packetList = new CopyOnWriteArrayList();

    public enum Mode{
        Watchdog, BlocksMC
    }

    @EventHandler
    private final Listener<TickEvent> tickEventListener = event -> {
      tick++;
    };

    @EventHandler
    private final Listener<PacketEvent> ee = event -> {
      if(wait){
          if (event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C08PacketPlayerBlockPlacement
                  || event.getPacket() instanceof C07PacketPlayerDigging || event.getPacket() instanceof C09PacketHeldItemChange) {
              event.setCancelled(true);
          }
      }
    };

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(mode.getValue() == Mode.Watchdog){
            if (mc.thePlayer.hurtResistantTime == 19) {
                wait = false;
                stage = 1;
            }

            if (event.isPre()) {
                if (tick == 1) {
                    PacketUtil.packetNoEvent(new C03PacketPlayer());
                    PacketUtil.packetNoEvent(new C09PacketHeldItemChange(bowSlot()));
                    PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bowSlot())));
                } else if (tick == 2) {
                    PacketUtil.packetNoEvent(new C03PacketPlayer());
                } else if (tick == 3) {
                    PacketUtil.packetNoEvent(new C03PacketPlayer());
                } else if (tick == 4) {
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(event.getPosX(), event.getPosY(), event.getPosZ(), event.getYaw(), -90, event.isOnGround()));
                    PacketUtil.packetNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    PacketUtil.packetNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }

            if (tick > 35) {
                if (mc.thePlayer.onGround) {
                    mc.timer.timerSpeed = 1;
                    toggle();
                }
            }
        }
    };

    @EventHandler
    private final Listener<MovePlayerEvent> eventMoveListener = event -> {
        switch (mode.getValue()) {
            case Watchdog: {
                baseSpeed = MovementUtils.getSpeed();
                double baseAmplifier = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1 ? 1.86 : 1.34 : 1.34;
                double jumpAmplifier = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1 ? 1.69 : 2.35 : 2.2;
                if (wait) {
                    MovementUtils.setSpeed(event, 0);
                } else if (mc.thePlayer.isMoving()) {
                    if (mc.thePlayer.onGround) {
                        if (shouldBoost) {
                            event.setY(mc.thePlayer.motionY = 0.42F + 0.1 * (4 - (mc.thePlayer.isPotionActive(Potion.jump) ?
                                    mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0)));
                            moveSpeed *= 0.9;
                        } else {
                            moveSpeed = MovementUtils.getSpeed() * 2;
                        }
                    } else {
                        Logger.print("Y: " + mc.thePlayer.motionY);
                        if(mc.thePlayer.motionY == 0.03196443960654492){
                            mc.thePlayer.motionY = -0.13;
                        }
                        moveSpeed *= 0.97;
                    }

                    MovementUtils.setSpeed(event, moveSpeed = Math.max(moveSpeed, baseSpeed));
                    shouldBoost = mc.thePlayer.onGround;
                }
                break;
            }
            case BlocksMC:{
                switch(this.stage){
                    case 0:{
                        if(!MovementUtils.isOnGround()) this.toggle();
                        damage(damageMode.FAKE);
                        stage++;
                    }
                    case 1: {
                        Logger.print("Jumping");
                        if(mc.thePlayer.onGround)
                            event.setY(mc.thePlayer.motionY = 0.61f);  // 61
                        stage++;
                    }
                    case 2:{
                        if(mc.thePlayer.fallDistance < 0.05f){
                            MovementUtils.setSpeed(event, Math.max(MovementUtils.getSpeed() * 4.8f, this.moveSpeed));

                        }
                        if(mc.thePlayer.fallDistance > 0.1){
                            MovementUtils.setSpeed(event, Math.max(MovementUtils.getSpeed() * 5.8f, this.moveSpeed));
                            this.toggle();
                            stage++;
                            break;
                        }
                    }
                }
                break;
            }


        }

    };

    @Override
    public void onEnable() {
        switch(mode.getValue()){
            case BlocksMC:{
                stage =0;
                break;
            }
            case Watchdog:{
                if (bowSlot() == -1 || !mc.thePlayer.inventory.hasItem(Items.arrow)) {
                    Metaware.INSTANCE.getNotificationManager().pop("Warning!", "You need bow in your hotbar and arrows", NotificationType.WARNING);
                    toggle();
                    return;
                }

                wait = true;
                tick = 0;
                stage = 0;
                moveSpeed = 0;

                break;
            }

        }
        super.onEnable();
    }

    public float getMaxFallDist() {
        PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return mc.thePlayer.getMaxFallHeight() + f;
    }

    public double getBaseJumpMotion() {
        double baseJumpMotion = 0.42f;
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            baseJumpMotion += (double)((float)mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        return baseJumpMotion;
    }




    public void damage(damageMode dm) {
        int stage1 = 0;
        double offset = 0.0625;
        EntityPlayerSP player = mc.thePlayer;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        int i = 0;
        if(dm == damageMode.NORMAL){
            while ((double)i < (double)getMaxFallDist() / 0.0625 + 1.0) {
                PacketUtil.packet(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 0.0625, z, false));
                PacketUtil.packet(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
                ++i;
            }
            PacketUtil.packet(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        }
        if(dm == damageMode.VERUS){

        }
        if(dm == damageMode.FAKE){
            mc.thePlayer.performHurtAnimation();
        }

    }

    private int bowSlot() {
        return mc.thePlayer.getSlotByItem(Items.bow);
    }

    public enum damageMode{
        NORMAL,
        VERUS,
        FAKE
    }
}
