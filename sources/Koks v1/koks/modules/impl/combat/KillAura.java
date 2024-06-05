package koks.modules.impl.combat;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.*;
import koks.modules.Module;
import koks.utilities.*;
import koks.utilities.value.Value;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import koks.utilities.value.values.TitleValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 21:08
 */
public class KillAura extends Module {

    public BooleanValue<Boolean> player = new BooleanValue<>("Player", true, this);
    public BooleanValue<Boolean> animals = new BooleanValue<>("Animals", false, this);
    public BooleanValue<Boolean> mobs = new BooleanValue<>("Mobs", false, this);
    public BooleanValue<Boolean> invisible = new BooleanValue<>("Invisible", false, this);
    public BooleanValue<Boolean> ignoreTeam = new BooleanValue<>("Ignore Team", true, this);
    public BooleanValue<Boolean> ignoreFriend = new BooleanValue<>("Ignore Friends", true, this);
    public ModeValue<String> targets = new ModeValue<>("Targets", new BooleanValue[]{player, animals, mobs, invisible, ignoreTeam, ignoreFriend}, this);
    public ModeValue<String> targetMode = new ModeValue<>("Target Mode", "Hybrid", new String[]{"Single", "Switch", "Hybrid"}, this);
    public ModeValue<String> preferTarget = new ModeValue<>("Prefer Check", "Distance", new String[]{"Distance", "Health"}, this);

    public TitleValue targetSettings = new TitleValue("Target Settings", true, new Value[]{targets, targetMode, preferTarget}, this);

    public NumberValue<Double> range = new NumberValue<>("Hit Range", 4.0D, 6.0D, 3.0D, this);
    public BooleanValue<Boolean> preAim = new BooleanValue<>("Pre Aiming", false, this);
    public NumberValue<Double> preAimRange = new NumberValue<>("Aiming Range", 0.0D, 1.0D, 0.0D, this);
    public NumberValue<Integer> cps = new NumberValue<>("CPS", 7, 12, 20, 1, this);
    public BooleanValue<Boolean> smoothRotation = new BooleanValue<>("Smooth Rotation", false, this);
    public NumberValue<Integer> failingChance = new NumberValue<>("FailHit Percent", 0, 20, 0, this);
    public BooleanValue<Boolean> autoBlock = new BooleanValue<>("AutoBlock", false, this);
    public BooleanValue<Boolean> blockAlways = new BooleanValue<>("Block Always", true, this);
    public BooleanValue<Boolean> legitMovement = new BooleanValue<>("Legit Movement", false, this);
    public BooleanValue<Boolean> stopSprinting = new BooleanValue<>("Stop Sprinting", false, this);
    public BooleanValue<Boolean> hitSlow = new BooleanValue<>("Hit Slow", false, this);

    public TitleValue generalSettings = new TitleValue("General", true, new Value[]{range, preAim, preAimRange, cps, smoothRotation, failingChance, autoBlock, blockAlways, legitMovement, stopSprinting, hitSlow}, this);

    public BooleanValue<Boolean> needNaNHealth = new BooleanValue<>("NaN Health", false, this);
    public BooleanValue<Boolean> checkName = new BooleanValue<>("Check Name", true, this);
    public NumberValue<Integer> ticksExisting = new NumberValue<>("Ticks Existing", 25, 100, 0, this);
    public TitleValue antiBotSettings = new TitleValue("AntiBot Settings", false, new Value[]{needNaNHealth, checkName, ticksExisting}, this);

    public BooleanValue<Boolean> fakeBlocking = new BooleanValue<>("Fake Blocking", false, this);
    public BooleanValue<Boolean> silentBlocking = new BooleanValue<>("Silent Blocking", false, this);
    public BooleanValue<Boolean> silentSwing = new BooleanValue<>("Silent Swing", false, this);
    public BooleanValue<Boolean> serverSideSwing = new BooleanValue<>("Send SwingPacket", true, this);
    public NumberValue<Integer> swingChance = new NumberValue<>("ClientSide SwingChance", 100, 100, 0, this);
    public TitleValue visualSettings = new TitleValue("Visual Settings", false, new Value[]{fakeBlocking, silentBlocking, silentSwing, serverSideSwing, swingChance}, this);

    public ModeValue<String> attackEvent = new ModeValue<>("Attack Event", "Pre Motion", new String[]{"Pre Motion", "On Tick"}, this);
    public NumberValue<Double> animationSpeed = new NumberValue<>("Animation Speed", 0.35D, 1D, 0D, this);

    public TitleValue adjustmentSettings = new TitleValue("Fine Tuning Settings", false, new Value[]{attackEvent, animationSpeed}, this);

    public List<Entity> entities = new ArrayList<>();
    FriendManager friendManager = new FriendManager();
    public RandomUtil randomUtil = new RandomUtil();
    public AuraUtil auraUtil = new AuraUtil();
    public TimeUtil timeUtil = new TimeUtil();
    public TimeUtil cpsTimer = new TimeUtil();
    public RayCastUtil rayCastUtil = new RayCastUtil();
    public RotationUtil rotationUtil = new RotationUtil();
    public EntityUtil entityUtil = new EntityUtil();
    public Entity finalEntity;
    public boolean isFailing, canSwing;
    public float yaw, pitch, animation;
    public int listCount, finalCPS, shouldCPS;

    public KillAura() {
        super("KillAura", "Destroy all on the world.", Category.COMBAT);

        addValue(targetSettings);
        addValue(targets);
        addValue(targetMode);
        addValue(preferTarget);

        addValue(generalSettings);

        addValue(range);
        addValue(preAim);
        addValue(preAimRange);
        addValue(cps);
        addValue(smoothRotation);
        addValue(failingChance);
        addValue(autoBlock);
        addValue(blockAlways);
        addValue(legitMovement);
        addValue(stopSprinting);
        addValue(hitSlow);
        addValue(antiBotSettings);

        addValue(needNaNHealth);
        addValue(checkName);
        addValue(ticksExisting);

        addValue(visualSettings);
        addValue(fakeBlocking);
        fakeBlocking.setShouldSave(false);
        addValue(silentBlocking);
        silentBlocking.setShouldSave(false);
        addValue(silentSwing);
        silentSwing.setShouldSave(false);
        addValue(serverSideSwing);
        serverSideSwing.setShouldSave(false);
        addValue(swingChance);
        swingChance.setShouldSave(false);

        addValue(adjustmentSettings);
        addValue(attackEvent);
        addValue(animationSpeed);
        animationSpeed.setShouldSave(false);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MotionEvent) {
            MotionEvent e = (MotionEvent) event;

            if (e.getType() == MotionEvent.Type.PRE) {

                if (finalEntity != null) {

                    e.setYaw(yaw);
                    e.setPitch(pitch);

                    if (mc.thePlayer.getDistanceToEntity(finalEntity) <= range.getDefaultValue() && attackEvent.getSelectedMode().equals("Pre Motion"))
                        attackEntity();
                }

            }

            if (e.getType() == MotionEvent.Type.POST) {
                if (finalEntity != null && autoBlock.isToggled()) {
                    if (autoBlock.isToggled() && blockAlways.isToggled() && mc.thePlayer.getCurrentEquippedItem().getItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
                    }
                }
            }

        }

        if (event instanceof EventRender3D) {
            float partialTicks = ((EventRender3D) event).getPartialTicks();

            if (finalEntity != null) {
                drawESP(finalEntity, partialTicks, 1);
            }
        }

        if (event instanceof EventUpdate) {
            setModuleInfo(targetMode.getSelectedMode() + (targetMode.getSelectedMode().equals("Switch") ? "" : ", " + preferTarget.getSelectedMode()));
            manageEntities();
            setRotations(finalEntity);
            isFailing = new Random().nextInt(100) <= failingChance.getDefaultValue();
            canSwing = new Random().nextInt(100) <= swingChance.getDefaultValue();

            if (stopSprinting.isToggled() && mc.thePlayer.rotationYaw != yaw && finalEntity != null) {
                mc.gameSettings.keyBindSprint.pressed = false;
                mc.thePlayer.setSprinting(false);
            }

            if (finalEntity != null) {
                if (mc.thePlayer.getDistanceToEntity(finalEntity) <= range.getDefaultValue() && attackEvent.getSelectedMode().equals("On Tick"))
                    attackEntity();
            }
        }

        if (legitMovement.isToggled() && finalEntity != null) {
            if (event instanceof MoveFlyingEvent) {
                MoveFlyingEvent e = (MoveFlyingEvent) event;
                e.setYaw(yaw);
            }

            if (event instanceof JumpEvent) {
                JumpEvent e = (JumpEvent) event;
                e.setYaw(yaw);
            }
        }

    }

    public void attackEntity() {
        int maxCps = cps.getDefaultValue() < 10 ? cps.getDefaultValue() : cps.getDefaultValue() + 5;
        int minCps = cps.getMinDefaultValue() < 10 ? cps.getMinDefaultValue() : cps.getMinDefaultValue() + 5;
        if (cpsTimer.hasReached(500)) {
            shouldCPS = maxCps == minCps ? maxCps : randomUtil.randomInt(minCps, maxCps);
            cpsTimer.reset();
        }

        if (finalCPS < shouldCPS)
            finalCPS++;
        if (finalCPS > shouldCPS)
            finalCPS--;

        Entity rayCast = rayCastUtil.getMouseOver(range.getDefaultValue(), yaw, pitch);

        for (int i = 0; i < 1; i++)
            mc.effectRenderer.emitParticleAtEntity(rayCast, EnumParticleTypes.SNOWBALL);

        if (timeUtil.hasReached(1000 / finalCPS)) {

            if (rayCast != null && !isFailing) {

                if (silentSwing.isToggled()) {
                    if (canSwing) {
                        mc.thePlayer.swingItem();
                    } else {
                        if (serverSideSwing.isToggled())
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                } else
                    mc.thePlayer.swingItem();

                if (autoBlock.isToggled() && !blockAlways.isToggled() && mc.thePlayer.getCurrentEquippedItem().getItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
                }

                if (autoBlock.isToggled())
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

                if (hitSlow.isToggled())
                    mc.playerController.attackEntity(mc.thePlayer, rayCast);
                else
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(rayCast, C02PacketUseEntity.Action.ATTACK));
            } else {
                //if (isFailing) mc.thePlayer.addChatMessage(new ChatComponentText("Failing HitChance"));

                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    if (silentSwing.isToggled()) {
                        if (canSwing) {
                            mc.thePlayer.swingItem();
                        } else {
                            if (serverSideSwing.isToggled())
                                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        }
                    } else
                        mc.thePlayer.swingItem();
                }
            }

            if (listCount < entities.size() - 1 && !entities.isEmpty())
                listCount++;
            else
                listCount = 0;

            timeUtil.reset();
        }
    }

    public void setRotations(Entity entity) {
        float[] rotations = rotationUtil.faceEntityWithVector(entity, yaw, pitch, smoothRotation.isToggled(), isFailing);
        yaw = rotations[0];
        pitch = rotations[1];
    }

    public void manageEntities() {

        if (!entities.isEmpty()) {

            entities.removeIf(entity -> !isValidEntity(entity));
            Entity entityToSet = preferTarget.getSelectedMode().equals("Distance") ? auraUtil.getNearest(entities) : preferTarget.getSelectedMode().equals("Health") ? auraUtil.getLowest(entities) : null;
            switch (targetMode.getSelectedMode()) {
                case "Single":
                    if (finalEntity == null) finalEntity = entityToSet;
                    break;
                case "Switch":
                    finalEntity = entities.get(listCount);
                    break;
                case "Hybrid":
                    finalEntity = entityToSet;
                    break;
                default:
                    finalEntity = entities.get(0);
                    break;
            }
        }

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (isValidEntity(entity)) {
                if (!entities.contains(entity))
                    entities.add(entity);
            } else {
                entities.remove(entity);
            }
        }

        if (finalEntity != null && !entities.contains(finalEntity))
            finalEntity = null;

        if (finalEntity == null || listCount > entities.size() - 1)
            listCount = 0;
    }

    public boolean isValidEntity(Entity entity) {
        if (entity == null)
            return false;
        if (!(entity instanceof EntityLivingBase))
            return false;
        if (checkName.isToggled() && entity instanceof EntityPlayer && !isValidEntityName(entity))
            return false;
        if (entity instanceof EntityPlayer && entity == mc.thePlayer)
            return false;
        if (entity.isDead)
            return false;
        if (!mc.thePlayer.canEntityBeSeen(entity))
            return false;
        if (!player.isToggled() && entity instanceof EntityPlayer)
            return false;
        if (!animals.isToggled() && entity instanceof EntityAnimal)
            return false;
        if (!animals.isToggled() && entity instanceof EntityVillager)
            return false;
        if (!mobs.isToggled() && entity instanceof EntityMob)
            return false;
        if (entity instanceof EntityArmorStand)
            return false;
        if (!invisible.isToggled() && entity.isInvisible())
            return false;
        if (entity.ticksExisted < ticksExisting.getDefaultValue())
            return false;
        if (friendManager.isFriend(entity.getName()) && !ignoreFriend.isToggled())
            return false;
        if (!Float.isNaN(((EntityLivingBase) entity).getHealth()) && needNaNHealth.isToggled())
            return false;
        if (!ignoreTeam.isToggled() && entityUtil.isTeam(mc.thePlayer, (EntityPlayer) entity))
            return false;
        if (mc.thePlayer.getDistanceToEntity(entity) > range.getDefaultValue() + (preAim.isToggled() ? 0 : preAimRange.getDefaultValue()))
            return false;
        return true;
    }

    public boolean isValidEntityName(Entity entity) {
        String name = entity.getName();
        if (name.length() < 3 || name.length() > 16)
            return false;
        if (name.contains(".") || name.contains("-") || name.contains("§") || name.contains("&") || name.contains("/"))
            return false;
        return true;
    }

    @Override
    public void onEnable() {
        listCount = 0;
        timeUtil.reset();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
    }

    @Override
    public void onDisable() {
        finalEntity = null;
        entities.clear();
        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }

    public void drawESP(Entity entity, float partialTicks, double radius) {
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - (mc.getRenderManager()).viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - (mc.getRenderManager()).viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - (mc.getRenderManager()).viewerPosZ;

        animation += animationSpeed.getDefaultValue() * DeltaTime.getDeltaTime();

        int sections = 1920;
        double dAngle = 2 * Math.PI / sections;

        float[] yTest = {0, 0, 0, 0, 0, 0};

        GL11.glPushMatrix();
        double yMid = mc.thePlayer.posY + 1 / 2 + 1;

        GL11.glTranslated(x, yMid, z);
        GL11.glRotatef(animation, 0, (float) yMid, 0);
        GL11.glTranslated(-x, -yMid, -z);

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < sections; i++) {
            GL11.glColor4f(Koks.getKoks().client_color.getRed() / 255F, Koks.getKoks().client_color.getGreen() / 255F, Koks.getKoks().client_color.getBlue() / 255F, Koks.getKoks().client_color.getAlpha() / 255F);
            GL11.glVertex3d(x + radius * Math.cos((i * dAngle)), y + 0.1 + yTest[0], z - radius * Math.sin((i * dAngle)));
            yTest[0] += 0.0011;
        }
        GlStateManager.color(0, 0, 0);
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < sections; i++) {
            GL11.glColor4f(Koks.getKoks().client_color.getRed() / 255F, Koks.getKoks().client_color.getGreen() / 255F, Koks.getKoks().client_color.getBlue() / 255F, Koks.getKoks().client_color.getAlpha() / 255F);
            GL11.glVertex3d(x + radius * Math.cos((i * dAngle)), y + 0.12 + yTest[1], z - radius * Math.sin((i * dAngle)));
            yTest[1] += 0.0011;
        }
        GlStateManager.color(0, 0, 0);
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < sections; i++) {
            GL11.glColor4f(Koks.getKoks().client_color.getRed() / 255F, Koks.getKoks().client_color.getGreen() / 255F, Koks.getKoks().client_color.getBlue() / 255F, Koks.getKoks().client_color.getAlpha() / 255F);
            GL11.glVertex3d(x + radius * Math.cos((i * dAngle)), y + 0.14 + yTest[2], z - radius * Math.sin((i * dAngle)));
            yTest[2] += 0.0011;
        }
        GlStateManager.color(0, 0, 0);
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();

        // OTHER

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < sections; i++) {
            GL11.glColor4f(Koks.getKoks().client_color.getRed() / 255F, Koks.getKoks().client_color.getGreen() / 255F, Koks.getKoks().client_color.getBlue() / 255F, Koks.getKoks().client_color.getAlpha() / 255F);
            GL11.glVertex3d(x + radius * -Math.cos((i * dAngle)), y + 0.1 + yTest[3], z - radius * -Math.sin((i * dAngle)));
            yTest[3] += 0.0011;
        }
        GlStateManager.color(0, 0, 0);
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < sections; i++) {
            GL11.glColor4f(Koks.getKoks().client_color.getRed() / 255F, Koks.getKoks().client_color.getGreen() / 255F, Koks.getKoks().client_color.getBlue() / 255F, Koks.getKoks().client_color.getAlpha() / 255F);
            GL11.glVertex3d(x + radius * -Math.cos((i * dAngle)), y + 0.12 + yTest[4], z - radius * -Math.sin((i * dAngle)));
            yTest[4] += 0.0011;
        }
        GlStateManager.color(0, 0, 0);
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();


        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < sections; i++) {
            GL11.glColor4f(Koks.getKoks().client_color.getRed() / 255F, Koks.getKoks().client_color.getGreen() / 255F, Koks.getKoks().client_color.getBlue() / 255F, Koks.getKoks().client_color.getAlpha() / 255F);
            GL11.glVertex3d(x + radius * -Math.cos((i * dAngle)), y + 0.14 + yTest[5], z - radius * -Math.sin((i * dAngle)));
            yTest[5] += 0.0011;
        }
        GlStateManager.color(0, 0, 0);
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();

        GL11.glPopMatrix();

    }

}