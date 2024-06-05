package Ascii4UwUWareClient.Module.Modules.Combat;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPostUpdate;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Manager.FriendManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Misc.Teams;
import Ascii4UwUWareClient.Module.Modules.Move.Scaffold;
import Ascii4UwUWareClient.Util.Math.RandomUtil;
import Ascii4UwUWareClient.Util.Math.RotationUtil;
import Ascii4UwUWareClient.Util.RotationUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
import Ascii4UwUWareClient.Util.player.RaycastUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KillauraOld extends Module {

    public Mode <Enum> RotationMode = new Mode ( "Rotation ", "Rotation", Rotation.values (), Rotation.Normal );
    public Mode <Enum> mode = new Mode ( "Mode", "Mode", KAMode.values (), KAMode.Switch );
    public Mode <Enum> sortingMode = new Mode ( "Sorting Mode", "Sorting Mode", SortingMode.values (), SortingMode.Health );
    public Mode <Enum> autoBlockMode = new Mode ( "Block Mode", "Block Mode", ABMode.values (), ABMode.Hypixel );

    private final Numbers <Double> minAps = new Numbers <Double> ( "Min APS", "Min APS", 9.0, 0.1, 20.0, 0.1 );
    private final Numbers <Double> maxAps = new Numbers <Double> ( "Max APS", "Max APS", 10.0, 0.1, 20.0, 0.1 );
    public static Numbers <Double> range = new Numbers <Double> ( "Range", "Range", 4.4, 0.1, 7.0, 0.1 );
    private final Numbers <Double> switchDelay = new Numbers <Double> ( "Switch Delay", "Switch Delay", 100.0, 1.0, 1000.0, 1.0 );
    public Option <Boolean> player = new Option ( "Players", "Players", true );
    public Option <Boolean> mobs = new Option ( "Mobs", "Mobs", false );
    public Option <Boolean> animals = new Option ( "Animals", "Animals", false );
    public Option <Boolean> invis = new Option ( "Invisible", "Invisible", false );
    public Option <Boolean> wall = new Option ( "Walls", "Walls", true );
    public Option <Boolean> autoBlock = new Option ( "AutoBlock", "AutoBlock", true );
    public Option <Boolean> unSprint = new Option ( "Un Sprint", "Un Sprint", false );
    public Option <Boolean> rotation = new Option ( "Rotation", "Rotation", true );
    public Option <Boolean> lockView = new Option ( "LockView", "LockView", false );
    public Option <Boolean> shadowSex = new Option ( "Sex Shadow", "Sex Shadow", false );
    public Option <Boolean> urMomAim = new Option ( "Ur Mom Aim", "Ur Mom Aim", true );

    public static EntityLivingBase target;
    private final List <EntityLivingBase> targetList = new ArrayList <> ();
    public static boolean blocking;
    private int targetIndex;

    public TimerUtil switchTimer = new TimerUtil ();
    public TimerUtil attackTimer = new TimerUtil ();


    public KillauraOld() {
        super ( "Killaura", new String[]{"Killaura", "ka"}, ModuleType.Combat );
        addValues ( RotationMode ,mode, sortingMode, autoBlockMode, maxAps, minAps, range, switchDelay, player, mobs, animals, invis, wall, autoBlock, unSprint, rotation, lockView, shadowSex, urMomAim );

    }

    @EventHandler
    public void onPreUpdate(EventPreUpdate event) {

        if (Client.instance.getModuleManager().getModuleByClass(Scaffold.class).isEnabled())return;

        setSuffix ( mode.getModeAsString () + " | " + RotationMode.getModeAsString() + " | "+ autoBlockMode.getModeAsString() +" | "+ range.getValue () );

        collectTargets ();

        sortTargets ();

        if (unSprint.getValue ()) {
            if (target != null) {
                Minecraft.thePlayer.setSprinting ( false );
            }
        }

        if (switchTimer.hasReached ( switchDelay.getValue () ) && mode.getModeAsString ().equals ( "Switch" )) {
            targetIndex++;
            switchTimer.reset ();
        }

        if (targetIndex >= targetList.size ())
            targetIndex = 0;

        target = !targetList.isEmpty () &&
                targetIndex < targetList.size () ?
                targetList.get ( targetIndex ) :
                null;

        if (!isHoldingSword ())
            blocking = false;

        if (target == null) {
            if (blocking)
                unblock ();
            return;
        }

        if (rotation.getValue ()) {
            float yaw = 0;
            float pitch = 0;
            if (urMomAim.getValue()){
                yaw = (float) (RotationUtils.faceTarget(target, 500F, 500F, true)[0] + RandomUtil.getRandomInRange(-10,10));
                pitch = (float) (RotationUtils.faceTarget(target, 500F, 500F, true)[1] + RandomUtil.getRandomInRange(-10,10));
            }else {
                switch (RotationMode.getModeAsString ()) {
                    case "Normal" :
                    yaw = RotationUtils.faceTarget ( target, 1000F, 1000F, false )[0];
                    pitch = RotationUtils.faceTarget ( target, 1000F, 1000F, false )[1];
                    break;
                    case "AAC":
                        yaw = this.getRotations(target)[0] + (float)this.random.nextInt(20) - 10.0F;
                        pitch = this.getRotations(target)[1] + (float)this.random.nextInt(20) - 10.0F;
                        break;
                    case"Verus":
                        yaw = (this.getRotations((Entity)((Object)target))[0] + (float)this.random.nextInt(26) - 13.0f);
                        pitch = (this.getRotations((Entity)((Object)target))[1] + (float)this.random.nextInt(26) - 13.0f);
                        if (target == null) {
                            this.mc.thePlayer.setSprinting(false);
                        }
                       break;
                }
            }
            if (lockView.getValue ()) {
                Minecraft.thePlayer.rotationYaw = yaw;
                Minecraft.thePlayer.rotationPitch = pitch;
            } else {
                //event.setYaw ( yaw );
                //event.setPitch ( pitch );
                event.setYaw(yaw);
                event.setPitch(pitch);
                //TODO: Client Side Rotation
                Minecraft.thePlayer.rotationYawHead = yaw;
                Minecraft.thePlayer.renderYawOffset = yaw;
                //mc.thePlayer.rotationPitch = pitch;
                //mc.thePlayer.renderPitchOffset = pitch;
            }
        }

        if (attackTimer.hasReached ( (long) (1000 / randomNumber ( minAps.getValue (), maxAps.getValue () )) )) {
            if (isValidEntity ( target )) {

                MovingObjectPosition ray = RaycastUtil.rayCast (
                        Minecraft.thePlayer,
                        target.posX,
                        target.posY + target.getEyeHeight (),
                        target.posZ );

                if (ray != null) {
                    Entity entityHit = ray.entityHit;
                    if (entityHit instanceof EntityLivingBase)
                        if (isValidEntity ( (EntityLivingBase) entityHit ))
                            target = (EntityLivingBase) entityHit;
                }

                if (isHoldingSword ())
                    unblock ();
                Minecraft.thePlayer.swingItem ();
                attack ( target );
            }
            attackTimer.reset ();
        }
    }

    @EventHandler
    public void onPostUpdate(EventPostUpdate event) {
        if (target != null) {
            if (isHoldingSword () && target != null)
                block ();
            else
                unblock ();
        }
    }

    private void attack(EntityLivingBase entityLivingBase) {
        final float sharpLevel = EnchantmentHelper.func_152377_a (
                Minecraft.thePlayer.getHeldItem (),
                EnumCreatureAttribute.UNDEFINED );

        if (sharpLevel > 0.0F)
            Minecraft.thePlayer.onEnchantmentCritical ( entityLivingBase );

        mc.getNetHandler ().getNetworkManager ().sendPacket (
                new C02PacketUseEntity ( entityLivingBase,
                        C02PacketUseEntity.Action.ATTACK ) );
    }

    @Override
    public void onEnable() {
        targetList.clear ();
        target = null;
        blocking = false;
        super.onEnable ();
    }

    @Override
    public void onDisable() {
        targetList.clear ();
        target = null;
        blocking = false;
        super.onDisable ();
    }

    private void collectTargets() {
        targetList.clear ();

        for (Entity entity : Minecraft.thePlayer.getEntityWorld ().loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (isValidEntity ( entityLivingBase ))
                    targetList.add ( entityLivingBase );
            }
        }
    }

    public float[] getRotations(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - this.mc.thePlayer.posX;
        double deltaY = e.posY - 3.5 + (double)e.getEyeHeight() - this.mc.thePlayer.posY + (double)this.mc.thePlayer.getEyeHeight();
        double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - this.mc.thePlayer.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
        float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance)));
        if (deltaX < 0.0 && deltaZ < 0.0) {
            yaw = (float)(90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0.0 && deltaZ < 0.0) {
            yaw = (float)(-90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[]{yaw, pitch};
    }

    private void block() {
        switch (autoBlockMode.getModeAsString ()) {
            case "Fake":
                if (autoBlock.getValue () && !mc.gameSettings.keyBindUseItem.isPressed () && !blocking) {
                    blocking = true;
                }
                break;
            case "Hypixel":
                if (autoBlock.getValue () && !mc.gameSettings.keyBindUseItem.isPressed () && !blocking) {
                    double value;
                    if (shadowSex.getValue()){
                        value = RandomUtil.getRandomInRange(Double.MIN_VALUE, Double.MAX_VALUE);
                    }else {value = 1;}
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(value, value, value), 255, mc.thePlayer.inventory.getCurrentItem(), 0, 0, 0));                    blocking = true;
                }
                break;
            case "AAC":
                if (autoBlock.getValue () && !mc.gameSettings.keyBindUseItem.isPressed () && !blocking) {
                    if (!mc.gameSettings.keyBindUseItem.isPressed ()) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue ( new C02PacketUseEntity ( target, new Vec3 ( RandomUtils.nextInt ( 50, 50 ) / 100.0, RandomUtils.nextInt ( 0, 200 ) / 100.0, RandomUtils.nextInt ( 50, 50 ) / 100.0 ) ) );
                        Minecraft.thePlayer.sendQueue.addToSendQueue ( new C02PacketUseEntity ( target, C02PacketUseEntity.Action.INTERACT ) );
                        blocking = true;

                        break;
                    }
                }
            case "Redesky":
                if (autoBlock.getValue () && !mc.gameSettings.keyBindUseItem.isPressed () && !blocking) {
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), 255, mc.thePlayer.inventory.getCurrentItem(), 0, 0, 0));
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE), 255, mc.thePlayer.inventory.getCurrentItem(), 0, 0, 0));
                    mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                    blocking = true;
                }
                break;

        }
    }




    private void unblock() {
                switch (autoBlockMode.getModeAsString ()) {
                    case "Hypixel":
                        Minecraft.playerController.syncCurrentPlayItem ();
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                                C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                                new BlockPos(-1, -1, -1),
                                EnumFacing.DOWN));
                        blocking = false;
                        break;
                    case "Redesky":
                        Minecraft.playerController.syncCurrentPlayItem ();
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                                C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                                new BlockPos(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
                                EnumFacing.DOWN));
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                                C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                                new BlockPos(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE),
                                EnumFacing.DOWN));
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        blocking = false;
                        break;
                    case "Fake":
                    case "AAC":
                        if (blocking) {
                            blocking = false;
                        }
                        break;
                }
            }



    

    private void sortTargets() {
        switch (sortingMode.getModeAsString()) {
            case "Angle":
                targetList.sort(
                        Comparator.comparingDouble(
                                RotationUtil::getAngleChange));
                break;
            case "Distance":
                targetList.sort(
                        Comparator.comparingDouble(
                                RotationUtil::getDistanceToEntity));
                break;
            case "Health":
                targetList.sort(
                        Comparator.comparingDouble(
                                EntityLivingBase::getHealth));
                break;
        }
    }

    private boolean isHoldingSword() {
        return Minecraft.thePlayer.getCurrentEquippedItem() != null && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }

    private boolean isValidEntity(EntityLivingBase ent) {
        if (Minecraft.thePlayer.isDead)return false;
        if (!ent.canEntityBeSeen(mc.thePlayer) && !wall.getValue())
            return false;
        if (ent instanceof EntityArmorStand) return false;
        AntiBot ab = (AntiBot) Client.instance.getModuleManager().getModuleByClass(AntiBot.class);
        return ent != null && (ent != Minecraft.thePlayer && ((!(ent instanceof EntityPlayer) || this.player.getValue()) && (((!(ent instanceof EntityAnimal) && !(ent instanceof EntitySquid))
                || this.animals.getValue()) && (((!(ent instanceof EntityMob) && !(ent instanceof EntityVillager) && !(ent instanceof EntitySnowman)
                && !(ent instanceof EntityBat)) || this.mobs.getValue()) && (!((double) Minecraft.thePlayer.getDistanceToEntity(
                ent) > range.getValue() + 0.4) && ((!(ent instanceof EntityPlayer)
                || !FriendManager
                .isFriend(ent.getName())) && (!ent.isDead)
                /*&& ent.getHealth() > 0.0F Mineplex &&*/  &&((!ent.isInvisible()
                || this.invis.getValue()) && (!ab.isEnabled() || !ab.isServerBot(ent)) && (!Minecraft.thePlayer.isDead &&(!(ent instanceof EntityPlayer) || !Teams.isOnSameTeam(ent))))))))));
    }
    public enum KAMode{
        Single,Switch
    }
    public enum SortingMode{
        Angle,Distance, Health
    }

    public enum Rotation{
        Normal,Verus,AAC
    }
    public enum ABMode {
        Hypixel, AAC, Redesky, Fake
    }

    public static int randomNumber(double min, double max) {
        return (int) Math.round(min + (float) Math.random() * ((max - min)));
    }
}
