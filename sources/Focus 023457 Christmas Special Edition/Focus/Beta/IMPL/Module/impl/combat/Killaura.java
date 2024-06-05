package Focus.Beta.IMPL.Module.impl.combat;

import Focus.Beta.API.GUI.notifications.Notification;
import Focus.Beta.API.GUI.notifications.NotificationRenderer;
import Focus.Beta.Client;
import Focus.Beta.NOT.NotificationType;
import Focus.Beta.UTILS.AStarCustomPathFinder;
import Focus.Beta.UTILS.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.render.EventRender3D;
import Focus.Beta.API.events.world.EventPostUpdate;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.UTILS.Math.MathUtils;
import Focus.Beta.UTILS.Math.RandomUtil;
import Focus.Beta.UTILS.Math.RotationUtil;
import Focus.Beta.UTILS.Math.RotationUtils;
import Focus.Beta.UTILS.Math.Vec3d;
import Focus.Beta.UTILS.world.Timer;
import Focus.Beta.UTILS.world.TimerUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Killaura extends Module {

    public Mode<Enum> mode = new Mode ( "Mode", "Mode", TMode.values (), TMode.Switch );
    public Mode <Enum> sortingMode = new Mode ( "Sorting Mode", "Sorting Mode", SortingMode.values (), SortingMode.Health );
    public Mode <Enum> rotationMode = new Mode( "Rotation Mode", "Rotation Mode", RMode.values (), RMode.Normal );
    public Mode <Enum> autoBlockMode = new Mode ( "AutoBlock Mode", "AutoBlock Mode", ABMode.values (), ABMode.Fake );
    public Mode <Enum> EspMode = new Mode ( "Esp Mode", "Esp Mode", ESPMode.values (), ESPMode.Jello );
    public Mode <Enum> attackMode = new Mode ( "Attack Mode", "Attack Mode", AttackMode.values (), AttackMode.Mc );

    public Option<Boolean> player = new Option<>("Player", "Player", true);
    public Option<Boolean> animals = new Option<>("Animals", "Animals", false);
    public Option<Boolean> mobs = new Option<>("Mobs", "Mobs", false);
    public Option<Boolean> invis = new Option<>("Invisible", "Invisible", false);
    public Option<Boolean> RenderCircle = new Option<>("Render Circle", "Render Circle", false);
    public Option<Boolean> wall = new Option<>("Wall", "Wall", true);
    public Option<Boolean> unSprint = new Option<>("Un Sprint", "Un Sprint", false);
    public Option<Boolean> Esp = new Option<>("Esp", "Esp", false);
    public Option<Boolean> vsirange = new Option<>("Visualize Range", "Visualize Range", false);
    public Option<Boolean> rotation = new Option<>("Rotation", "Rotation", true);
    public Option<Boolean> silentrotation = new Option<>("Silent-Rotations", "Silent-Rotations", true);
    public Option<Boolean> autoBlock = new Option<>("Auto Block", "AutoBlock", true);
    public Option<Boolean> strafe = new Option<>("Strafe", "Strafe", false);
    public Option<Boolean> randomCps = new Option<>("Random Cps", "Random Cps", false);
    float yaw = 0;
    float pitch = 0;
    private ArrayList<Vec3> points = new ArrayList<>();
    public static Numbers<Double> range = new Numbers <Double> ( "Range", "Range", 4.4, 0.1, 7.0, 0.1 );
    public static Numbers <Double> minCps = new Numbers<Double>( "Low Cps", "Low Cps", 7D, 1D, 20D, 0.01 );
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

    public Timer renderTimer = new Timer();
    public Killaura() {
        super("KillAura", new String[]{"Killaura", "AutoKill"}, Type.COMBAT, "Automatically attacks entities around you");
        addValues(mode, sortingMode, rotationMode, autoBlockMode, attackMode, range, maxCps, mainCps, minCps, switchDelay, zitterValue,vsirange, Esp, player, animals, mobs, invis, wall, randomCps, unSprint, strafe, autoBlock, rotation, silentrotation);
    }

    @Override
    public void onEnable() {
        target = null;
        targetIndex = 0;
        points.clear();;
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
        setSuffix ( mode.getModeAsString () );
        if (unSprint.getValue()) {
            if (target != null) {
                mc.thePlayer.setSprinting(false);
            }
        }

        if (attackTimer.hasReached ( (long)1000 / getCps()) && target != null) {
            if (isValidEntity ( target )) {
                if (attackMode.getValue() == AttackMode.Mc)
                    MCAttack();
                else
                    Packet1Attack();

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
        if ( silentrotation.getValue() && rotation.getValue() && target != null){
           
            float[] rot = new float[]{0, 0};
            switch (rotationMode.getModeAsString()){
                case "Normal":
                    rot = rotationsToEntity(target);
                    yaw = (float) rot[0];
                    pitch = (float) rot[1];
                    break;
                case "Smooth":
                	smoothAim(event);
                    break;
                case "Zitter":
                    rot = rotationsToEntity(target);
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
            if(!(rotationMode.getValue() == RMode.Smooth)) {
            event.setYaw(yaw);
            event.setPitch(pitch);
            }
        }

        if ( !silentrotation.getValue() && rotation.getValue() && target != null){
            float yaw = 0;
            float pitch = 0;
            float[] rot = new float[]{0, 0};
            switch (rotationMode.getModeAsString()){
                case "Normal":
                    rot = rotationsToEntity(target);
                    yaw = (float) rot[0];
                    pitch = (float) rot[1];
                    break;
                case "Zitter":
                    rot = rotationsToEntity(target);
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
           mc.thePlayer.rotationYaw = yaw;
            mc.thePlayer.rotationPitch = pitch;
        }
    }
    public void MCAttack() {
        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, target);
    }
    public void Packet1Attack(){
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
    }
    public void Packet2Attack(){
        
    }
    boolean up = true;
    float height = 0;
	private void smoothAim(EventPreUpdate em){
		double randomYaw = 0.05;
		double randomPitch = 0.05;
		float targetYaw = RotationUtils.getYawChange(yaw, target.posX + randomNumber(1,-1) * randomYaw, target.posZ + randomNumber(1,-1) * randomYaw);
		float yawFactor = targetYaw / 2.3F;
		em.setYaw(yaw + yawFactor);
		yaw += yawFactor;
		float targetPitch = RotationUtils.getPitchChange(pitch, target, target.posY + randomNumber(1,-1) * randomPitch);
		float pitchFactor = targetPitch / 2.3F;
		em.setPitch(pitch + pitchFactor);
		pitch += pitchFactor;
	}
	public static int randomNumber(int max, int min) {
		return Math.round(min + (float)Math.random() * ((max - min)));
	}
    @EventHandler
    public void renderEsp(EventRender3D event) {

        if(vsirange.getValue()){
            RenderUtil.pre3D();
            GL11.glLineWidth(6.0f);
            GL11.glBegin(3);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
            final double n = 0.0;
            if (n < 6.283185307179586) {
                GL11.glVertex3d(this.mc.thePlayer.lastTickPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) * event.getPartialTicks() + Math.sin(n) * (this.range).getValue() - this.mc.getRenderManager().renderPosX, this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * event.getPartialTicks() - this.mc.getRenderManager().renderPosY, this.mc.thePlayer.lastTickPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ) * event.getPartialTicks() + Math.cos(n) * (this.range).getValue() - this.mc.getRenderManager().renderPosZ);
            }
            GL11.glEnd();
            GL11.glLineWidth(3.0f);
            GL11.glBegin(3);
            GL11.glColor4f(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 255.0f);
            final double n2 = 0.0;
            if (n2 < 6.283185307179586) {
                GL11.glVertex3d(this.mc.thePlayer.lastTickPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) * event.getPartialTicks() + Math.sin(n2) * (this.range).getValue() - this.mc.getRenderManager().renderPosX, this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * event.getPartialTicks() - this.mc.getRenderManager().renderPosY, this.mc.thePlayer.lastTickPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ) * event.getPartialTicks() + Math.cos(n2) * (this.range).getValue() - this.mc.getRenderManager().renderPosZ);
            }
            GL11.glEnd();
            RenderUtil.post3D();
        }
        if (Esp.getValue()) {
            if (EspMode.getModeAsString().equalsIgnoreCase("Jello")) {
                if (target != null) {
                    if (up) {
                        if (renderTimer.hasElapsed(45, true)) {
                            height += 0.1f;
                            if (height > 2) {
                                up = false;
                            }
                        }
                    } else {
                        if (renderTimer.hasElapsed(45, true)) {
                            height -= 0.1f;
                            if (height <= 0) {
                                up = true;
                            }
                        }
                    }

                    if (!up) {
                        renderCircle(event, height, 1);
                        renderCircle(event, height + 0.01f, 0.95f);
                        renderCircle(event, height + 0.02f, 0.9f);
                        renderCircle(event, height + 0.03f, 0.85f);
                        renderCircle(event, height + 0.04f, 0.8f);
                        renderCircle(event, height + 0.05f, 0.75f);
                        renderCircle(event, height + 0.06f, 0.7f);
                        renderCircle(event, height + 0.07f, 0.65f);
                        renderCircle(event, height + 0.08f, 0.6f);
                        renderCircle(event, height + 0.09f, 0.55f);
                        renderCircle(event, height + 0.10f, 0.5f);
                        renderCircle(event, height + 0.11f, 0.45f);
                        renderCircle(event, height + 0.12f, 0.4f);
                        renderCircle(event, height + 0.13f, 0.35f);
                        renderCircle(event, height + 0.14f, 0.3f);
                        renderCircle(event, height + 0.15f, 0.25f);
                        renderCircle(event, height + 0.16f, 0.2f);
                        renderCircle(event, height + 0.17f, 0.15f);
                        renderCircle(event, height + 0.18f, 0.1f);
                        renderCircle(event, height + 0.19f, 0.05f);
                        renderCircle(event, height + 0.20f, 0.03f);
                        renderCircle(event, height + 0.21f, 0.02f);
                        renderCircle(event, height + 0.22f, 0.01f);
                    } else {
                        renderCircle(event, height, 0.1f);
                        renderCircle(event, height + 0.01f, 0.15f);
                        renderCircle(event, height + 0.02f, 0.2f);
                        renderCircle(event, height + 0.03f, 0.25f);
                        renderCircle(event, height + 0.04f, 0.3f);
                        renderCircle(event, height + 0.05f, 0.35f);
                        renderCircle(event, height + 0.06f, 0.4f);
                        renderCircle(event, height + 0.07f, 0.45f);
                        renderCircle(event, height + 0.08f, 0.5f);
                        renderCircle(event, height + 0.09f, 0.55f);
                        renderCircle(event, height + 0.10f, 0.6f);
                        renderCircle(event, height + 0.11f, 0.65f);
                        renderCircle(event, height + 0.12f, 0.7f);
                        renderCircle(event, height + 0.13f, 0.75f);
                        renderCircle(event, height + 0.14f, 0.8f);
                        renderCircle(event, height + 0.15f, 0.85f);
                        renderCircle(event, height + 0.16f, 0.9f);
                        renderCircle(event, height + 0.17f, 0.95f);
                        renderCircle(event, height + 0.18f, 1f);
                    }
                }
            }
        }else {
            if (target != null){
                ArrayList<Focus.Beta.UTILS.Vec3> path = findBlinkPath(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, target.posX, target.posY, target.posZ, 12);
            if (points.isEmpty()) {

            }
        }
        }
    }

        public void renderCircle(EventRender3D event, float height, float alpha) {
            Entity entity = target;
            double rad = 0.6;
            float points = 90.0F;
            GlStateManager.enableDepth();
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glEnable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3042);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glBlendFunc(770, 771);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
            GL11.glHint(3153, 4354);
            GL11.glColor4f(10, 107, 204, alpha);
            GL11.glDisable(2929);
            GL11.glLineWidth(1.3F);
            GL11.glBegin(3);
            GlStateManager.color(10, 107, 204, alpha);

            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks()
                    - RenderManager.renderPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks()
                    - RenderManager.renderPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks()
                    - RenderManager.renderPosZ;
            for (int i = 0; i <= 90; i++) {
                GL11.glColor4f(84, 95, 255, alpha);
                GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / points), y + height,
                        z + rad * Math.sin(i * 6.283185307179586D / points));
            }

            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(2881);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(2832);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
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
                    mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                    break;
                case "Redesky":
                    if (  !mc.gameSettings.keyBindUseItem.isPressed () && !block) {
                        mc.thePlayer.sendQueue.addToSendQueue ( new C02PacketUseEntity ( target, new Vec3( RandomUtils.nextInt ( 50, 50 ) / 100.0, RandomUtils.nextInt ( 0, 200 ) / 100.0, RandomUtils.nextInt ( 50, 50 ) / 100.0 ) ) );
                        mc.thePlayer.sendQueue.addToSendQueue ( new C02PacketUseEntity ( target, C02PacketUseEntity.Action.INTERACT ) );
                        block = true;
                    }
                    break;
            }
        }
    }

    public void unBlock(){
        if (block) {
            if (autoBlockMode.getValue() == ABMode.Normal || autoBlockMode.getValue() == ABMode.Legit) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }else if (autoBlockMode.getValue() == ABMode.Redesky){
                mc.playerController.syncCurrentPlayItem ();
            }
            block = false;
        }
    }

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

        for (Entity entity : mc.thePlayer.getEntityWorld ().loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (isValidEntity ( entityLivingBase ))
                    targetList.add ( entityLivingBase );
            }
        }
    }
    private boolean isValidEntity(EntityLivingBase ent) {
        if (mc.thePlayer.isDead)return false;
        if (!ent.canEntityBeSeen(mc.thePlayer) && !wall.getValue())
            return false;
        if (ent instanceof EntityArmorStand) return false;
        return ent != null && (ent != mc.thePlayer && ((!(ent instanceof EntityPlayer) || this.player.getValue()) && (((!(ent instanceof EntityAnimal) && !(ent instanceof EntitySquid))
                || this.animals.getValue()) && (((!(ent instanceof EntityMob) && !(ent instanceof EntityVillager) && !(ent instanceof EntitySnowman)
                && !(ent instanceof EntityBat)) || this.mobs.getValue()) && (!((double) mc.thePlayer.getDistanceToEntity(
                ent) > range.getValue() + 0.4) && (((ent instanceof EntityPlayer) || (!ent.isDead)
                /*&& ent.getHealth() > 0.0F Mineplex &&*/  &&((!ent.isInvisible()
                || this.invis.getValue()) && ((!mc.thePlayer.isDead &&(!(ent instanceof EntityPlayer))))))))))));
    }


    private boolean isHoldingSword() {
        return mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }


    public enum TMode{
        Single,Switch
    }
    public enum SortingMode{
        Angle,Distance, Health
    }

    public enum RMode{
        Normal,
        Smooth,
        Zitter,
        AAC,
        Ghostly
    }

    public enum ABMode{
        Fake, Normal, Legit , Redesky
    }

    public enum AttackMode{
        Mc, Packet, Redesky
    }
    enum ESPMode{
        Jello, Line
    }
    public static List<Focus.Beta.UTILS.Vec3> findBlinkPath(final double tpX, final double tpY, final double tpZ){
        return findBlinkPath(tpX, tpY, tpZ,5);
    }

    public static List<Focus.Beta.UTILS.Vec3> findBlinkPath(final double tpX, final double tpY, final double tpZ, final double dist){
        return findBlinkPath(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,tpX,tpY,tpZ,dist);
    }
    private static boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new net.minecraft.util.BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }
    public static ArrayList<Focus.Beta.UTILS.Vec3> findBlinkPath(double curX, double curY, double curZ, final double tpX, final double tpY, final double tpZ, final double dashDistance) {
        Focus.Beta.UTILS.Vec3 topFrom=new Focus.Beta.UTILS.Vec3(curX,curY,curZ);
        Focus.Beta.UTILS.Vec3 to=new Focus.Beta.UTILS.Vec3(tpX,tpY,tpZ);

        if (!canPassThrow(new BlockPos(topFrom))) {
            topFrom = topFrom.addVector(0, 1, 0);
        }
        AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();

        int i = 0;
        Focus.Beta.UTILS.Vec3 lastLoc = null;
        Focus.Beta.UTILS.Vec3 lastDashLoc = null;
        ArrayList<Focus.Beta.UTILS.Vec3> path = new ArrayList<>();
        ArrayList<Focus.Beta.UTILS.Vec3> pathFinderPath = pathfinder.getPath();
        for (Focus.Beta.UTILS.Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > dashDistance * dashDistance) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    cordsLoop:
                    for (int x = (int) smallX; x <= bigX; x++) {
                        for (int y = (int) smallY; y <= bigY; y++) {
                            for (int z = (int) smallZ; z <= bigZ; z++) {
                                if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break cordsLoop;
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            i++;
        }

        return path;
    }

}
