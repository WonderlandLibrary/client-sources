package client.metaware.impl.module.combat;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.pathfinder.GotoAI;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.other.PlayerUtil;
import client.metaware.impl.utils.util.player.Rotation;
import client.metaware.impl.utils.util.player.RotationUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "InfiniteAura", renderName = "Infinite Aura", category = Category.COMBAT)
public class InfiniteAura extends Module {
    public final DoubleProperty reach = new DoubleProperty("Reach", 100, 1, 300, 0.1, Property.Representation.DISTANCE);
    public final DoubleProperty minCps = new DoubleProperty("Min CPS", 8, 1, 19, 1, Property.Representation.INT);
    public final DoubleProperty maxCps = new DoubleProperty("Max CPS", 13, 1, 20, 1, Property.Representation.INT);
    public final DoubleProperty maxTargets = new DoubleProperty("Max Targets", 1, 1, 50, 1, Property.Representation.INT);
    public final Property<Boolean> tpBack = new Property<>("Tp Back", true);
    public final Property<Boolean> autoBlock = new Property<>("AutoBlock", false);
    public final Property<Boolean> lockView = new Property<>("Lock View", false);
    private GotoAI ai;
    public EntityLivingBase target;
    private int entityCounter = 0;
    private TimerUtil timer = new TimerUtil();
    private double startPosX;
    private double startPosY;
    private double startPosZ;


    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(event.isPre()){
            if (!isMultiTarget()) {
                target = PlayerUtil.getClosestEntity();
                if (target != null && mc.thePlayer.getDistanceToEntity(target) <= reach.getValue().floatValue()) {
                    //event.setOnGround(true);
                    if (lockView.getValue()) {
                        faceTarget(target);
                    }
                    else {
                        RotationUtils.rotate(event, RotationUtils.getRotationsKarhu(target));
                    }
                }
                else {
                    target = null;
                }
            }
            else {
                if (!timer.delay(1000 / ThreadLocalRandom.current().nextInt(minCps.getValue().intValue(), maxCps.getValue().intValue()))) {
                    return;
                }
                final ArrayList<EntityLivingBase> entities = PlayerUtil.getValidEntities();
                for (final EntityLivingBase entity : entities) {
                    if (entityCounter >= maxTargets.getValue()) {
                        break;
                    }
                    if (entity != null && mc.thePlayer.getDistanceToEntity(entity) <= reach.getValue().floatValue()) {
                        target = entity;
                        //event.setOnGround(true);
                        RotationUtils.rotate(event, RotationUtils.getRotationsKarhu(target));
                        tpToEntity(entity);
                    }
                    else {
                        target = null;
                    }
                    ++entityCounter;
                }
                entityCounter = 0;
                timer.reset();
            }
        } else if (event.isPost()) {
            if (!isMultiTarget() && target != null && mc.thePlayer.getDistanceToEntity(target) < reach.getValue().floatValue() && timer.delay(1000 / ThreadLocalRandom.current().nextInt(minCps.getValue().intValue(), maxCps.getValue().intValue()))) {
                tpToEntity(target);
                timer.reset();
            }
            if (!PlayerUtil.getValidEntities().isEmpty()) {
                if (mc.thePlayer.getHeldItem() != null && target != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && autoBlock.getValue()) {
                    mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                }
            }
        }
    };

    private void faceTarget(final EntityLivingBase entity) {
        Rotation rotations = RotationUtils.getRotationsKarhu(entity);
        mc.thePlayer.rotationYaw = rotations.getRotationYaw();
        mc.thePlayer.rotationPitch = rotations.getRotationPitch() + 1.0f;
    }

    public void tpToEntity(final EntityLivingBase entity) {
        if (entity != null) {
            final double oldPosX = mc.thePlayer.posX;
            final double oldPosY = mc.thePlayer.posY;
            final double oldPosZ = mc.thePlayer.posZ;
            (ai = new GotoAI(entity)).update("infiniteaura");
            if (ai.isDone() || ai.isFailed()) {
                disable();
            }
            PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(oldPosX, oldPosY, oldPosZ, true));
        }
    }

    private void disable() {
        ai.stop();
    }
    

    @Override
    public void onEnable() {
        timer.reset();
        entityCounter = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        entityCounter = 0;
        super.onDisable();
    }

    public boolean isMultiTarget(){
        return maxTargets.getValue() == 1;
    }
}
