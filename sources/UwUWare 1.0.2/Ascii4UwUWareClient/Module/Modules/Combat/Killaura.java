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
import Ascii4UwUWareClient.Util.Math.RandomUtil;
import Ascii4UwUWareClient.Util.Math.RotationUtil;
import Ascii4UwUWareClient.Util.Math.Vec3d;
import Ascii4UwUWareClient.Util.MoveUtils;
import Ascii4UwUWareClient.Util.RotationUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
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
import net.minecraft.util.Vec3;
import net.optifine.MathUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Killaura extends Module {

    public Mode<Enum> mode = new Mode ( "Mode", "Mode", TMode.values (), TMode.Switch );
    public Mode <Enum> sortingMode = new Mode ( "Sorting Mode", "Sorting Mode", SortingMode.values (), SortingMode.Health );
    public Mode <Enum> rotationMode = new Mode ( "Rotation Mode", "Rotation Mode", RMode.values (), RMode.Normal1 );
    public Mode <Enum> autoBlockMode = new Mode ( "AutoBlock Mode", "AutoBlock Mode", ABMode.values (), ABMode.Fake );
    public Mode <Enum> attackMode = new Mode ( "Attack Mode", "Attack Mode", AttackMode.values (), AttackMode.Mc );

    public Option<Boolean> player = new Option<>("Player", "Player", true);
    public Option<Boolean> animals = new Option<>("Animals", "Animals", false);
    public Option<Boolean> mobs = new Option<>("Mobs", "Mobs", false);
    public Option<Boolean> invis = new Option<>("Invisible", "Invisible", false);
    public Option<Boolean> wall = new Option<>("Wall", "Wall", true);
    public Option<Boolean> unSprint = new Option<>("Un Sprint", "Un Sprint", false);
    public Option<Boolean> rotation = new Option<>("Rotation", "Rotation", true);
    public Option<Boolean> autoBlock = new Option<>("Auto Block", "AutoBlock", true);
    public Option<Boolean> strafe = new Option<>("Strafe", "Strafe", false);
    public Option<Boolean> randomCps = new Option<>("Random Cps", "Random Cps", false);

    public static Numbers<Double> range = new Numbers <Double> ( "Range", "Range", 4.4, 0.1, 7.0, 0.1 );
    public static Numbers <Double> minCps = new Numbers <Double> ( "Low Cps", "Low Cps", 7D, 1D, 20D, 0.01 );
    public static Numbers <Double> mainCps = new Numbers <Double> ( "Main Cps", "Main Cps", 7D, 1D, 20D, 0.01 );
    public static Numbers <Double> maxCps = new Numbers <Double> ( "Max Cps", "Max Cps", 8D, 1D, 20D, 0.01 );
    private final Numbers <Double> switchDelay = new Numbers <Double> ( "Switch Delay", "Switch Delay", 100.0, 1.0, 1000.0, 1.0 );
    private final Numbers <Double> zitterValue = new Numbers <Double> ( "Zitter Value", "Zitter Value", 3D, 0.1, 10D, 0.01 );

    public static EntityLivingBase target;
    private List<EntityLivingBase> targetList = new ArrayList<>();
    private int targetIndex;
    public static boolean block;

    public TimerUtil switchTimer = new TimerUtil();
    public TimerUtil attackTimer = new TimerUtil();

    public Killaura() {
        super("Killaura", new String[]{"Killaura", "AutoKill"}, ModuleType.Combat);
        addValues(mode, sortingMode, rotationMode, autoBlockMode, attackMode, range, maxCps, mainCps, minCps, switchDelay, zitterValue, player, animals, mobs, invis, wall, randomCps, unSprint, strafe, autoBlock, rotation);
    }

    @Override
    public void onEnable() {
        target = null;
        targetIndex = 0;
        targetList.clear();
        unBlock();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        target = null;
        targetIndex = 0;
        targetList.clear();
        unBlock();
        super.onDisable();
    }

    @EventHandler
    public void onUpdatePre(EventPreUpdate event) {
        getAllTarget();
        sortTargets();
        slotTargetSwitch();
        rotation(event);
        setSuffix ( mode.getModeAsString () +" | " +sortingMode.getModeAsString ()+" | " + rotationMode.getModeAsString () +" | " + autoBlockMode.getModeAsString () +" | " + range.getValue () );
        if (unSprint.getValue()) {
            if (target != null) {
                Minecraft.thePlayer.setSprinting(false);
            }
        }

        if (strafe.getValue()){
            if (target != null) {
                MoveUtils.strafe();
            }
        }

        if (attackTimer.hasReached ( (long)1000 / getCps()) && target != null) {
            if (isValidEntity ( target )) {
                if (attackMode.getValue() == AttackMode.Mc)
                    attack(false);
                else
                    attack(true);
                //Helper.sendMessage("CPS: " + getCps());
            }
            attackTimer.reset ();
        }

    }

    @EventHandler
    public void onUpdatePost(EventPostUpdate event){
        if (autoBlock.getValue()) {
            if (target != null) {
                if (isHoldingSword() && target != null)
                    block();
                else
                    unBlock();
            } else unBlock();
        }
    }


    public void rotation(EventPreUpdate event){
        if (rotation.getValue() && target != null){
            float yaw = 0;
            float pitch = 0;
            float[] rot = new float[]{0, 0};
            switch (rotationMode.getModeAsString()){
                case "Normal1":
                    rot = rotationsToEntity(target);
                    yaw = (float) rot[0];
                    pitch = (float) rot[1];
                    break;
                case "Normal2":
                    rot = RotationUtils.getRotations(target);
                    yaw = (float) rot[0];
                    pitch = (float) rot[1];
                    break;
                case "Zitter1":
                    rot = rotationsToEntity(target);
                    yaw = rot[0] + (float) RandomUtil.getRandomInRange(-zitterValue.getValue(),zitterValue.getValue());
                    pitch = rot[1] + (float) RandomUtil.getRandomInRange(-zitterValue.getValue(),zitterValue.getValue());
                    break;
                case "Zitter2":
                    rot = RotationUtils.getRotations(target);
                    yaw = rot[0] + (float) RandomUtil.getRandomInRange(-zitterValue.getValue(),zitterValue.getValue());
                    pitch = rot[1] + (float) RandomUtil.getRandomInRange(-zitterValue.getValue(),zitterValue.getValue());
                    break;
                case "Ghostly":
                    rot = RotationUtils.getRotations(target);
                    yaw = rot[0] + (float)this.random.nextInt(26) - 13.0f;
                    pitch = rot[1] + (float)this.random.nextInt(26) - 13.0f;
                    break;
                case"AAC":
                    rot = rotationsToEntity(target);
                    yaw = (float) (rot[0] + MathUtils.getRandomInRange(-0.15F, 0.15F));
                    pitch = (float) (rot[1] + MathUtils.getRandomInRange(-0.15F, 0.15F));
                    break;
            }
            event.setYaw(yaw);
            event.setPitch(pitch);
            //TODO: Client Side Rotation
            Minecraft.thePlayer.rotationYawHead = yaw;
            Minecraft.thePlayer.renderYawOffset = yaw;
        }
    }

    public void attack(boolean packet){
        Minecraft.thePlayer.swingItem();
        if (!packet) {
            Minecraft.playerController.attackEntity(Minecraft.thePlayer, target);
        } else {
            final float sharpLevel = EnchantmentHelper.func_152377_a (
                    Minecraft.thePlayer.getHeldItem (),
                    EnumCreatureAttribute.UNDEFINED );

            if (sharpLevel > 0.0F)
                Minecraft.thePlayer.onEnchantmentCritical ( target );

            mc.getNetHandler ().getNetworkManager ().sendPacket (
                    new C02PacketUseEntity( target,
                            C02PacketUseEntity.Action.ATTACK ) );
        }
    }

    public void block() {
        if (!mc.gameSettings.keyBindUseItem.isPressed() && !block) {
            switch (autoBlockMode.getModeAsString()){
                case "Fake":
                case "Legit":
                    block = true;
                    break;
                case "Normal":
                    block = true;
                    Minecraft.thePlayer.setItemInUse(Minecraft.thePlayer.getCurrentEquippedItem(), Minecraft.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                    break;
                case "Redesky":
                    if (  !mc.gameSettings.keyBindUseItem.isPressed () && !block) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue ( new C02PacketUseEntity ( target, new Vec3( RandomUtils.nextInt ( 50, 50 ) / 100.0, RandomUtils.nextInt ( 0, 200 ) / 100.0, RandomUtils.nextInt ( 50, 50 ) / 100.0 ) ) );
                        Minecraft.thePlayer.sendQueue.addToSendQueue ( new C02PacketUseEntity ( target, C02PacketUseEntity.Action.INTERACT ) );
                        block = true;
                    }
                    break;
            }
        }
    }

    public void unBlock(){
        if (block) {
            if (autoBlockMode.getValue() == ABMode.Normal || autoBlockMode.getValue() == ABMode.Legit) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }else if (autoBlockMode.getValue() == ABMode.Redesky){
                Minecraft.playerController.syncCurrentPlayItem ();
            }
            block = false;
        }
    }

    /*
        Credits to domi domi is such a nice person :D
    */
    public static float[] rotationsToEntity(EntityLivingBase paramEntityLivingBase) {
        Vec3d vec31 = new Vec3d(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight() + mc.thePlayer.motionY, mc.thePlayer.posZ + mc.thePlayer.motionZ);
        Vec3d vec32 = new Vec3d(0.0D, paramEntityLivingBase.posY + paramEntityLivingBase.getEyeHeight(), 0.0D);
        for (float f = 0.0F; f < paramEntityLivingBase.getEyeHeight(); f = (float)(f + 0.05D)) {
            Vec3d vec3 = new Vec3d(0.0D, paramEntityLivingBase.posY + f + paramEntityLivingBase.posY - paramEntityLivingBase.prevPosY, 0.0D);
            if (vec3.distanceTo(vec31) < vec32.distanceTo(vec31))
                vec32 = vec3;
        }
        double d1 = Math.abs((paramEntityLivingBase.getEntityBoundingBox()).maxX - (paramEntityLivingBase.getEntityBoundingBox()).minX) / 2.0D;
        double d2 = Math.abs((paramEntityLivingBase.getEntityBoundingBox()).maxZ - (paramEntityLivingBase.getEntityBoundingBox()).minZ) / 2.0D;
        double d3 = paramEntityLivingBase.posX - vec31.xCoord;
        double d4 = vec32.yCoord - vec31.yCoord;
        double d5 = paramEntityLivingBase.posZ - vec31.zCoord;
        return applyMouseSensFix((float)Math.toDegrees(Math.atan2(d5, d3)) - 90.0F, (float)-Math.toDegrees(Math.atan2(d4, Math.hypot(d3, d5))));
    }

    public double getCps(){
        return randomCps.getValue() ? RandomUtil.getRandomInRange(minCps.getValue(), maxCps.getValue()) : mainCps.getValue();
    }

    public void slotTargetSwitch(){
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

    }

    private static float[] applyMouseSensFix(float paramFloat1, float paramFloat2) {
        float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f2 = f1 * f1 * f1 * 1.2F;
        return new float[] { paramFloat1, paramFloat2 };
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

    private void getAllTarget() {
        targetList.clear ();

        for (Entity entity : Minecraft.thePlayer.getEntityWorld ().loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (isValidEntity ( entityLivingBase ))
                    targetList.add ( entityLivingBase );
            }
        }
    }
    private boolean isValidEntity(EntityLivingBase ent) {
        if (Minecraft.thePlayer.isDead)return false;
        if (!ent.canEntityBeSeen(Minecraft.thePlayer) && !wall.getValue())
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


    private boolean isHoldingSword() {
        return Minecraft.thePlayer.getCurrentEquippedItem() != null && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }


    public enum TMode{
        Single,Switch
    }
    public enum SortingMode{
        Angle,Distance, Health
    }

    public enum RMode{
         Normal1,
        //Normal2,
         Zitter1,
         AAC ,
        //Zitter2
         Ghostly
    }

    public enum ABMode{
        Fake, Normal, Legit , Redesky
    }

    public enum AttackMode{
        Mc, Packet
    }

}
