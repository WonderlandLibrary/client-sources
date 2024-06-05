package client.metaware.impl.module.combat;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.api.shader.implementations.BlurShader;
import client.metaware.client.Logger;
import client.metaware.impl.event.impl.player.PlayerStrafeEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.event.impl.render.Render2DEvent;
import client.metaware.impl.event.impl.render.Render3DEvent;
import client.metaware.impl.module.movmeent.Flight;
import client.metaware.impl.module.movmeent.Longjump;
import client.metaware.impl.module.movmeent.Speed;
import client.metaware.impl.module.player.Scaffold;
import client.metaware.impl.module.render.TestOverlay;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.ScaffoldUtils;
import client.metaware.impl.utils.util.Stencil;
import client.metaware.impl.utils.util.StencilUtil;
import client.metaware.impl.utils.util.other.PlayerUtil;
import client.metaware.impl.utils.util.player.Rotation;
import client.metaware.impl.utils.util.player.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(name = "KillAura", renderName = "Kill Aura", category = Category.COMBAT, keybind = Keyboard.KEY_R)
public class KillAura extends Module {

    public Property<Boolean> attackSettings = new Property<>("Attack Settings", false);
    public Property<Boolean> modeSettings = new Property<>("Mode Settings", false);
    public Property<Boolean> targetSettings = new Property<>("Target Settings", false);

    public EnumProperty<RotationsMode> rotationsMode = new EnumProperty<>("Rotations Mode", RotationsMode.Normal, () -> this.doRotations.getValue() && modeSettings.getValue());

    public EnumProperty<Mode> killauraMode = new EnumProperty<>("KillAura Mode", Mode.Switch, modeSettings::getValue);

    public EnumProperty<BlockMode> blockMode = new EnumProperty<>("Autoblock Mode", BlockMode.NCP, () -> this.autoblock.getValue() && modeSettings.getValue());

    public EnumProperty<SortingMethod> sortingMode = new EnumProperty<>("Sorting Mode", SortingMethod.Health, modeSettings::getValue);

    public EnumProperty<EventMode> eventMode = new EnumProperty<>("Attack Event", EventMode.Post, modeSettings::getValue);

    public EnumProperty<TargetHUD> targetHud = new EnumProperty<>("TargetHUD", TargetHUD.Whiz, modeSettings::getValue);

    public DoubleProperty maxCPS = new DoubleProperty("Maximum CPS", 13, 1, 20, 1, attackSettings::getValue);
    public DoubleProperty minCPS = new DoubleProperty("Minimum CPS", 9, 1, 20, 1, attackSettings::getValue);


    public DoubleProperty range = new DoubleProperty("Attack Range", 4.2f, 1.0f, 7.0f, 0.2f, attackSettings::getValue);
    public DoubleProperty blockRange = new DoubleProperty("Block Range", 6.0f, 1.0f, 8.0f, 0.1f, attackSettings::getValue);

    public DoubleProperty maxAngleChangeProperty = new DoubleProperty("Max angle step", 45.0f, 1.0f, 201.25f, 0.25f, attackSettings::getValue);

    public DoubleProperty xCord = new DoubleProperty("TargetHudX", 45.0f, 0, 1920, 0.25f, () -> false);
    public DoubleProperty yCord = new DoubleProperty("TargetHudY", 45.0f, 0, 1080, 0.25f, () -> false);

    public Property<Boolean> players = new Property<>("Players", true, targetSettings::getValue);
    public Property<Boolean> teams = new Property<>("Teams", false, targetSettings::getValue);
    public Property<Boolean> invisibles = new Property<>("Invisibles", false, targetSettings::getValue);
    public Property<Boolean> monsters = new Property<>("Monsters", false, targetSettings::getValue);
    public Property<Boolean> animals = new Property<>("Animals", false, targetSettings::getValue);

    public enum Targets{
        Teams, Players, Animals, Monsters, Invisibles;
    }
    public Property<Boolean> autoblock = new Property<Boolean>("Autoblock", true, attackSettings::getValue);
    public Property<Boolean> moveFixTest = new Property<Boolean>("Move Fix(T)", false, () -> rotationsMode.getValue() != RotationsMode.Hypixel_Funny && attackSettings.getValue());
    public Property<Boolean> moveFixSilent = new Property<Boolean>("Move Fix(Silent)", false, () -> moveFixTest.getValue() && attackSettings.getValue());
    public Property<Boolean> lockview = new Property<Boolean>("Lock View", false, attackSettings::getValue);
    public Property<Boolean> noSwing = new Property<Boolean>("No Swing", false, attackSettings::getValue);
    public Property<Boolean> doRotations = new Property<Boolean>("Rotations", true, attackSettings::getValue);
    public BlurShader blurShader = new BlurShader(25);

    public List<EntityLivingBase> targets = new ArrayList<>();

    private List<Packet> packets = new ArrayList<>();

    private float rotationYaw, rotationPitch;
    public EntityLivingBase target;

    public int index;
    private double x, y, groundTicks, waitDelay;
    public int tick;
    public float animateHealthBar, animatedArmorBar;

    public TimerUtil attackTimer = new TimerUtil();
    public boolean block, canBlocko, flip, targetInBlock, blockingStatus;

    @Override
    public void onEnable() {
        super.onEnable();
        rotationPitch = 0;
        rotationYaw = 0;
        flip = false;
        targetInBlock = false;
        animateHealthBar = 0;
        target = null;
        blockingStatus = false;
        animatedArmorBar = 0;
        block = false;
        attackTimer.reset();
        tick = 100;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if(block && autoblock.getValue()){
            unblock();
            block =  false;
        }
        target = null;
        animateHealthBar = 0;
        flip = false;
        blockingStatus = false;
        rotationPitch = 0;
        targetInBlock = false;
        rotationYaw = 0;
        attackTimer.reset();
    }

    private enum Mode {
        Switch, Single, Multi;
    }

    private enum TargetHUD {
        Whiz, Clean, Other
    }

    private enum EventMode{
        Pre, Post;
    }

    private enum RotationsMode {
        Normal, Hypixel_Funny, AAC, Karhu;
    }

    private enum BlockMode {
        NCP, Vanilla, WatchdogTest, Verus;
    }

    @EventHandler
    private final Listener<PlayerStrafeEvent> playerStrafeEventListener = event -> {
        if(moveFixTest.getValue() && rotationsMode.getValue() != RotationsMode.Hypixel_Funny && target != null){
            if(rotationYaw != 0 && rotationPitch != 0){
                Logger.printWithoutPrefix("test");
                event.setYaw(rotationYaw);
                event.setPitch(rotationPitch);
                event.setSilent(moveFixSilent.getValue());
            }
        }
    };

    @EventHandler
    private final Listener<UpdatePlayerEvent> eventListener = event -> {
        setSuffix(killauraMode.getValue() + ", " + blockMode.getValue());
        if(mc.thePlayer.ticksExisted <= 5){
            if(block){
                unblock();
                block = false;
            }
            if(this.isToggled()){
                toggle();
            }
        }


        if(event.isPre()) {

            if (this.isOccupied()) {
                this.target = null;
                block = false;
                this.targetInBlock = false;
                return;
            }
            this.targetInBlock = false;
            EntityLivingBase testTarget = null;

            final List<EntityLivingBase> entities = PlayerUtil.getLivingEntities(this::isValid);

            if (entities.size() > 1)
                entities.sort(this.sortingMode.getValue().getSorter());

            for (final EntityLivingBase entity : entities) {
                final double dist = entity.getDistanceToEntity(this.mc.thePlayer);

                if (!this.targetInBlock && dist <= this.blockRange.getValue())
                    this.targetInBlock = true;

                if (dist < this.range.getValue()) {
                    if (testTarget == null)
                        testTarget = entity;
                }

                if (testTarget == null && dist <= this.range.getValue())
                    testTarget = entity;
            }

            this.target = testTarget;

            if(!targetInBlock){
                blockingStatus = false;
            }

            if(block && autoblock.getValue() && PlayerUtil.isHoldingSword() && mc.thePlayer.ticksExisted % 5 == 2 && blockMode.getValue() == BlockMode.NCP){
                unblock();
                block = false;
            }


            if (testTarget != null) {
                if(doRotations.getValue()){
                    doRots(event, testTarget);
                }

                if(eventMode.getValue() == EventMode.Pre){
                    doAttack(target, event);
                }
            }
        }else if(event.isPost()) {
            if(eventMode.getValue() == EventMode.Post){
                doAttack(target, event);
            }


            if(PlayerUtil.isHoldingSword()) {
                if (autoblock.getValue() && targetInBlock) {
                    block();
                }
            }
        }
    };

    public boolean isValid(EntityLivingBase entLiving) {
        return PlayerUtil.isValid(entLiving, players.getValue(), monsters.getValue(), animals.getValue(), teams.getValue(), invisibles.getValue(), range.getValue().floatValue(), blockRange.getValue().floatValue());
    }

    public void doAttack(EntityLivingBase entity, UpdatePlayerEvent event){
        if(entity == null) return;
        if (attackTimer.delay(1000L / ThreadLocalRandom.current().nextInt(minCPS.getValue().intValue(), maxCPS.getValue().intValue()))) {
            if(Metaware.INSTANCE.getModuleManager().getModuleByClass(Criticals.class).isToggled()){
                boolean cancritical = !Metaware.INSTANCE.getModuleManager().getModuleByClass(Longjump.class).isToggled() && !Metaware.INSTANCE.getModuleManager().getModuleByClass(Flight.class).isToggled() && !Metaware.INSTANCE.getModuleManager().getModuleByClass(Speed.class).isToggled() && targets.size() > 0 && mc.thePlayer.fallDistance == 0.0 && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround && !mc.thePlayer.isInWater();
                if (ScaffoldUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).isFullBlock() && !ScaffoldUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ)).isFullBlock()) {
                    if (waitDelay <= 0) {
                        waitDelay = 0;

                        if (cancritical) {
                            groundTicks += 1;
                            if (groundTicks == 1) {
                                event.setOnGround(false);
                                event.setPosY(event.getPosY() + ThreadLocalRandom.current().nextDouble(0.01, 0.02));
                            } else if (groundTicks == 2) {
                                event.setOnGround(false);
                                event.setPosY(event.getPosY() + ThreadLocalRandom.current().nextDouble(0.008, 0.02));
                            } else if (groundTicks == 3) {

                                event.setOnGround(false);
                                event.setPosY(event.getPosY() + ThreadLocalRandom.current().nextDouble(0.01, 0.02));
                            } else if (groundTicks >= 4) {
                                event.setOnGround(false);
                                event.setPosY(event.getPosY() + ThreadLocalRandom.current().nextDouble(0.03, 0.04));
                                groundTicks = 0;
                            }
                        } else {
                            waitDelay = 2;
                        }
                    } else {
                        waitDelay -= 1;
                    }
                } else if (groundTicks != 0) {
                    waitDelay = 4;
                    groundTicks = 0;
                }
            }
            tick = 1;

            if (noSwing.getValue()) {
                mc.getNetHandler().addToSendQueueNoEvent(new C0APacketAnimation());
            } else {
                mc.thePlayer.swingItem();
            }
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
            attackTimer.reset();
        }
    }

    private boolean isLookingAtEntity(final float yaw, final float pitch) {
        if (!this.doRotations.getValue()) return true;
        final double range = this.range.getValue();
        final Vec3 src = this.mc.thePlayer.getPositionEyes(1.0F);
        final Vec3 rotationVec = Entity.getVectorForRotation(pitch, yaw);
        final Vec3 dest = src.addVector(rotationVec.xCoord * range, rotationVec.yCoord * range, rotationVec.zCoord * range);
        return this.target.getEntityBoundingBox().expand(0.1F, 0.1F, 0.1F).calculateIntercept(src, dest) != null;
    }

    private enum SortingMethod {
        Distance(new DistanceSorting()),
        Health(new HealthSorting()),
        Hurt_Time(new HurtTimeSorting()),
        Angle(new AngleSorting()),
        Crosshair(new CrosshairSorting()),
        Combined(new CombinedSorting());

        private final Comparator<EntityLivingBase> sorter;

        SortingMethod(Comparator<EntityLivingBase> sorter) {
            this.sorter = sorter;
        }

        public Comparator<EntityLivingBase> getSorter() {
            return sorter;
        }
    }

    public void doRots(UpdatePlayerEvent event, EntityLivingBase entity){
        switch(rotationsMode.getValue()){
            case Normal:
                Rotation rotation11 = RotationUtils.getRotationsToEntity(entity);
                float frac = MathHelper.clamp_float(1f - maxAngleChangeProperty.getValue().floatValue() / 100, 0.1f, 1f);

                rotationYaw = rotationYaw + (rotation11.getRotationYaw() - rotationYaw) * frac;
                rotationPitch = rotationPitch + (rotation11.getRotationPitch() - rotationPitch) * frac;
                RotationUtils.rotate(event, RotationUtils.getRotationsRandom(entity));
                break;
            case AAC:
                Rotation rotation = new Rotation(mc.thePlayer.rotationYaw, RotationUtils.getRotationsRandom(entity).getRotationPitch());
                event.setPitch(rotation.getRotationPitch());
                event.setYaw(rotation.getRotationYaw());
                rotationYaw = rotation.getRotationYaw();
                rotationPitch = rotation.getRotationPitch();
                break;
            case Karhu:
                Rotation rotation1 = RotationUtils.getRotationsKarhu(entity);
                RotationUtils.rotate(event, rotation1);
                rotationYaw = rotation1.getRotationYaw();
                rotationPitch = rotation1.getRotationPitch();
                break;
            case Hypixel_Funny:
                RotationUtils.rotate(event, derpDerp());
                break;
        }
    }

    public Rotation derpDerp(){
        rotationYaw += 30;
        flip = rotationPitch == 80 || (rotationPitch != -80 && flip);
        if (flip) rotationPitch -= 5;
        else rotationPitch += 5;
        if(rotationPitch >= 80) rotationPitch = 80; else if (rotationPitch <= -80) rotationPitch = -80;
        return new Rotation(rotationYaw, rotationPitch);
    }

    public static double getEffectiveHealth(final EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) entity;
            return player.getHealth() * (PlayerUtil.getTotalArmorProtection(player) / 20.0);
        } else return 0;
    }

    private static abstract class AngleBasedSorting implements Comparator<EntityLivingBase> {
        protected abstract float getCurrentAngle();

        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            final float yaw = this.getCurrentAngle();

            return Double.compare(
                    Math.abs(RotationUtils.getYawToEntity(o1, false) - yaw),
                    Math.abs(RotationUtils.getYawToEntity(o2, false) - yaw));
        }
    }

    private static class AngleSorting extends AngleBasedSorting {
        @Override
        protected float getCurrentAngle() {
            return PlayerUtil.getLocalPlayer().currentEvent.getYaw();
        }
    }

    private static class CrosshairSorting extends AngleBasedSorting {
        @Override
        protected float getCurrentAngle() {
            return PlayerUtil.getLocalPlayer().rotationYaw;
        }
    }

    private static class CombinedSorting implements Comparator<EntityLivingBase> {
        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            int t1 = 0;
            for (SortingMethod sortingMethod : SortingMethod.values()) {
                Comparator<EntityLivingBase> sorter = sortingMethod.getSorter();
                if (sorter == this) continue;
                t1 += sorter.compare(o1, o2);
            }
            return t1;
        }
    }

    private static class DistanceSorting implements Comparator<EntityLivingBase> {
        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            final EntityPlayerSP player = PlayerUtil.getLocalPlayer();
            return Double.compare(o1.getDistanceToEntity(player), o2.getDistanceToEntity(player));
        }
    }

    private static class HealthSorting implements Comparator<EntityLivingBase> {
        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Double.compare(getEffectiveHealth(o1), getEffectiveHealth(o2));
        }
    }

    private static class HurtTimeSorting implements Comparator<EntityLivingBase> {
        @Override
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Integer.compare(
                    EntityLivingBase.maxHurtResistantTime - o2.hurtResistantTime,
                    EntityLivingBase.maxHurtResistantTime - o1.hurtResistantTime);
        }
    }


    private boolean isInMenu() {
        return this.mc.currentScreen != null;
    }

    private boolean isOccupied() {
        return isInMenu() || Metaware.INSTANCE.getModuleManager().getModuleByClass(Scaffold.class).isToggled();
    }

    private void block(){
        int currentItem = mc.thePlayer.inventory.currentItem;
        if(autoblock.getValue() && targetInBlock && PlayerUtil.isHoldingSword()) {
            if(blockMode.getValue() != BlockMode.Verus && !block) {
                if(target != null) {
                    mc.playerController.interactWithEntitySendPacket(mc.thePlayer, target);
                    PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                    block = true;
                }
                blockingStatus = true;
            }
            if(blockMode.getValue() == BlockMode.Verus){

                blockingStatus = true;
            }
        }
    }


    private void unblock() {
        if(block && autoblock.getValue() && PlayerUtil.isHoldingSword()) {
            if(blockMode.getValue() != BlockMode.Verus && !targetInBlock){
                PacketUtil.packetNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                block = false;
                blockingStatus = false;
            }
        }
    }

    @EventHandler
    private Listener<Render2DEvent> eventTarget = event -> {
        ScaledResolution sr = new ScaledResolution(mc);
        EntityLivingBase entityLivingBase = mc.currentScreen instanceof GuiChat ? mc.thePlayer : target;
        if(entityLivingBase != null && entityLivingBase instanceof EntityPlayer) {
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            x = xCord.getValue();
            y = yCord.getValue();

            int var141 = scaledResolution.getScaledWidth();
            int var151 = scaledResolution.getScaledHeight();
            scaledResolution.scaledHeight = scaledResolution.scaledHeight * scaledResolution.getScaleFactor();
            scaledResolution.scaledWidth = scaledResolution.scaledWidth * scaledResolution.getScaleFactor();
            int mouseX = Mouse.getX() * var141 / mc.displayWidth;
            int mouseY = var151 - Mouse.getY() * var151 / mc.displayHeight;
            if (Mouse.isButtonDown(0) && mc.currentScreen instanceof GuiChat && RenderUtil.isHovered((float)x, (float)y, 120, 45, mouseX, mouseY)) {
                xCord.setValue((double) (mouseX - 50));
                yCord.setValue((double) (mouseY - 20));
                if(mouseX >= 120){
                    mouseX = 120;
                }

                if(mouseX <= 2){
                    mouseX = 2;
                }

                if(mouseY >= 45){
                    mouseY = 45;
                }

                if(mouseY <= 2){
                    mouseY = 2;
                }
            }


            switch (targetHud.getValue()){
                case Whiz:{
//                    width = 105F;
//                    height = 37.0F;
//                    float x = scaledResolution.getScaledWidth() / 2 - (width / 2);
//                    float y = scaledResolution.getScaledHeight() / 2 - (height / 2) + width;
//                    float healthBarWidth = 60.0F;
//                    float 30 = 30;
//                    width = Math.max(width, font15.getWidth(target.getName()));
                    CustomFontRenderer font15 = Metaware.INSTANCE.getFontManager().currentFont().size(15);
                    CustomFontRenderer font12 = Metaware.INSTANCE.getFontManager().currentFont().size(12);
                    float healthBarWidth = 60;
                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y, 0);
                    float health = healthBarWidth * (entityLivingBase.getHealth() / entityLivingBase.getMaxHealth());
                    animateHealthBar = MathHelper.clamp_float((float) RenderUtil.progressiveAnimation(animateHealthBar, health, 0.09f), 0, healthBarWidth);
                    Gui.drawRect(2, 2, 105.5f, 37.5f, new Color(10, 10, 10).getRGB());
                    Gui.drawRect(1, 1.2f, 105.5F, 37.5f, new Color(40, 40, 40).getRGB());
                    Gui.drawRect(2 - 1.4f, 2 - 1.5f, 105.5F - 1.5f, 37.5f, new Color(74, 74, 74).getRGB());
                    Gui.drawGradientRect(2 - 1f, 2 - 1f, 105.5F, 37.5f, new Color(32, 32, 32).getRGB(), new Color(10, 10, 10).getRGB());
                    Gui.drawRect(2, 2, 105.5F, 37.5f, new Color(105, 105, 105, 40).getRGB());
                    Gui.drawRect(2 +  30, 2 + 11, 2 +  30 + healthBarWidth, 2 + 14, entityLivingBase.getHealth() < entityLivingBase.getMaxHealth() / 1.8 ? entityLivingBase.getHealth() < entityLivingBase.getMaxHealth() / 2.5 ? new Color(255, 51, 0).darker().darker().getRGB() : new Color(213, 193, 16).darker().darker().getRGB() : Color.GREEN.darker().darker().getRGB());
                    Gui.drawRect(2 +  30, 2 + 11, 2 +  30 + animateHealthBar, 2 + 14, entityLivingBase.getHealth() < entityLivingBase.getMaxHealth() / 1.8 ? entityLivingBase.getHealth() < entityLivingBase.getMaxHealth() / 2.5 ? new Color(255, 51, 0).getRGB() : new Color(213, 193, 16).getRGB() : Color.GREEN.getRGB());
                    float xOffset = 0;
                    for (int i = 0; i < entityLivingBase.getMaxHealth() / 2; i++) {
                        Gui.drawRect(2 +  30 + xOffset, 2 + 11, 2 +  30 + xOffset + 0.5F, 2 + 14, Color.BLACK.getRGB());
                        xOffset += healthBarWidth / (entityLivingBase.getMaxHealth() / 2 - 1);
                    }
                    font15.drawStringWithShadow(entityLivingBase.getName(), 2 +  30, 2 + 2, new Color(255, 255, 255).getRGB());
                    font12.drawStringWithShadow("HP: " + (int) entityLivingBase.getHealth() + " | Dist: " + (int) entityLivingBase.getDistanceToEntity(mc.thePlayer), 2 +  30, 2 + 17, new Color(255, 255, 255).getRGB());
                    if (entityLivingBase instanceof EntityPlayer) {
                        int xAdd = 0;
                        float multiplier = 0.90F;
                        glPushMatrix();
                        GlStateManager.enableBlend();
                        RenderHelper.enableGUIStandardItemLighting();
                        glScalef(multiplier, multiplier, multiplier);
                        if (entityLivingBase.getCurrentArmor(3) != null) {
                            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(3), (2 + 30 - 3 + xAdd) / multiplier, (2 + 20) / multiplier);
                            xAdd += 15;
                        }
                        if (entityLivingBase.getCurrentArmor(2) != null) {
                            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(2), (2 + 30 - 3 + xAdd) / multiplier, (2 + 20) / multiplier);
                            xAdd += 15;
                        }
                        if (entityLivingBase.getCurrentArmor(1) != null) {
                            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(1), (2 + 30 - 3 + xAdd) / multiplier, (2 + 20) / multiplier);
                            xAdd += 15;
                        }
                        if (entityLivingBase.getCurrentArmor(0) != null) {
                            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(0), (2 + 30 - 3 + xAdd) / multiplier, (2 + 20) / multiplier);
                            xAdd += 15;
                        }
                        if (entityLivingBase.getHeldItem() != null) {
                            mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getHeldItem(), (2 + 30 - 3 + xAdd) / multiplier, (2 + 20) / multiplier);
                            xAdd += 15;
                        }
                        GlStateManager.disableBlend();
                        RenderHelper.disableStandardItemLighting();
                        glPopMatrix();
                    }
                    glPushMatrix();
                    GlStateManager.enableBlend();
                    RenderHelper.enableGUIStandardItemLighting();
                    //RenderUtil.makeCropBox(2, 2 + 3F, 2 + 35, 2 + 35);
                    GuiInventory.drawEntityOnScreen(2 + 12, 2 + 30, 15, entityLivingBase.rotationYaw, entityLivingBase.rotationPitch, entityLivingBase);
                    //RenderUtil.destroyCropBox();
                    GlStateManager.disableBlend();
                    RenderHelper.disableStandardItemLighting();
                    glPopMatrix();
                    GL11.glPopMatrix();
                    break;
                }
                case Clean:{
                    CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(19);
                    CustomFontRenderer smallfr = Metaware.INSTANCE.getFontManager().currentFont().size(12);
                    float width = (float) (45 + fr.getWidth(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName()));
                    GL11.glPushMatrix();
                    StencilUtil.initStencilToWrite();
                    RenderUtil.glDrawRoundedRectEllipse(x + 2, y + 2, width, 35, RenderUtil.RoundingMode.FULL, 60, 6, -1);
//                    RenderUtil.drawRoundedRect(x + 2, y + 2, width, 35, 6, new Color(65, 65, 65).getRGB());
                    StencilUtil.readStencilBuffer(1);
                    Metaware.INSTANCE.getBlurShader(10).blur();
                    StencilUtil.uninitStencilBuffer();
                    ///RenderUtil.glDrawRoundedRectEllipse(x + 2, y + 2, width, 4, 6, RenderUtil.rainbow(3, 0.69f, 0.9f, 8));
                    int hurtTimeOpacity = entityLivingBase.hurtTime;
                    Color hurtTimeRed = new Color(255, 0, 0, hurtTimeOpacity * 15);
                   // drawHead((AbstractClientPlayer) entityLivingBase, 4, 8, 28, 28);
                    GL11.glPushMatrix();
                    GL11.glColor4f(1, 1, 1, 1);
                    drawHead((AbstractClientPlayer) entityLivingBase, (int)x+ 4, (int)y + 6, 28, 28);
                    GL11.glPopMatrix();
                    GL11.glColor4f(1, 1, 1, 1);
                    GL11.glPushMatrix();
//                    if(entityLivingBase.hurtTime > 0){
//                        RenderUtil.glDrawRoundedRectEllipse(x + 4, y + 6, 28, 28, RenderUtil.RoundingMode.FULL, 360, 15, hurtTimeRed.getRGB());
////                        Gui.drawRect(x + 3, y + 6, x + 31, y + 34, hurtTimeRed.getRGB());
//                    }
                    float health = (float) ((3 + fr.getWidth(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName())) * (entityLivingBase.getHealth() / entityLivingBase.getMaxHealth()));
                    animateHealthBar = MathHelper.clamp_float(RenderUtil.animate(health, animateHealthBar, 0.1f), 0, (float) ((3 + fr.getWidth(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName()))));
                    int color = RenderUtil.getRainbowFelix(6000, (int) (30), 0.69f);

                    float armor = (float) ((3 + fr.getWidth(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName())) * (entityLivingBase.getTotalArmorValue() / 20));
                    animatedArmorBar = MathHelper.clamp_float(RenderUtil.animate(armor, animatedArmorBar, 0.1f), 0, (float) ((3 + fr.getWidth(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName()))));


                    fr.drawStringWithShadow(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName(), (float)x + 33, (float)y + -3 + fr.getHeight(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName()), -1);
                    RenderUtil.drawImage12(new ResourceLocation("whiz/heart2.png"), (float) (x + 33), (float) (y + 26), 8, 8);
                    RenderUtil.drawImage12(new ResourceLocation("whiz/shield.png"), (float) (x + 33), (float) (y + 17), 8, 8);
                    RenderUtil.drawGradientSideways(x + 42, y + 20,  x + 42 + (3 + fr.getWidth(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName())), y + 22, new Color(40, 40, 40).getRGB(), new Color(40, 40, 40).getRGB());
                    RenderUtil.drawGradientSideways(x + 42, y + 20, x + 42 + animatedArmorBar, y + 22, RenderUtil.fade(Color.BLUE.brighter(), 3, 8), color);
                    RenderUtil.drawGradientSideways(x + 42, y + 29,  x + 42 + (3 + fr.getWidth(((AbstractClientPlayer) entityLivingBase).getGameProfile().getName())), y + 31, new Color(40, 40, 40).getRGB(), new Color(40, 40, 40).getRGB());
                    RenderUtil.drawGradientSideways(x + 42, y + 29, x + 42 + animateHealthBar, y + 31, new Color(RenderUtil.drawHealth(entityLivingBase)).darker().getRGB(), color);

//                    RenderUtil.drawOutlinedString(fr, ((AbstractClientPlayer) entityLivingBase).getGameProfile().getName(), 49, 9, Color.BLACK.getRGB(), -1);
//                    RenderUtil.drawOutlinedString(smallfr, "Distance: " + MathUtils.round(mc.thePlayer.getDistanceToEntity(entityLivingBase), 1), 55, 20.5f, Color.BLACK.getRGB(), -1);
//                    //RenderUtil.drawOutlinedString(smallfr, "HP: " + MathUtils.round(entityLivingBase.getHealth(), 1), 42 + smallfr.getWidth("Distance: " + MathUtils.round(mc.thePlayer.getDistanceToEntity(target), 1)), 20.5f, Color.BLACK.getRGB(), -1);
//                    RenderUtil.drawBorderedRect(40, 26, 70, 6, 0.5f, 0x90000000, new Color(0, 0, 0, 90).getRGB());
//                    RenderUtil.drawOutlinedString(smallfr, "" +  Math.round(100 * (entityLivingBase.getHealth() / entityLivingBase.getMaxHealth())) + "%", 30 + animateHealthBar, 32, Color.BLACK.getRGB(), -1);
//                    RenderUtil.drawBorderedRect(40, 26, animateHealthBar, 6, 1.2f, new Color(85, 85, 255).getRGB(), RenderUtil.drawHealth(entityLivingBase));
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    break;
                }
            }
        }
    };

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = event -> {
        if(target != null){
            EntityLivingBase it = (EntityLivingBase) target;
                double everyTime = 3000;
                double drawTime = (System.currentTimeMillis() % everyTime);
                boolean drawMode = drawTime > (everyTime / 2);
                double drawPercent = drawTime / (everyTime / 2.0);
                // true when goes up
                if (!drawMode) {
                    drawPercent = 1 - drawPercent;
                } else {
                    drawPercent -= 1;
                }
                drawPercent = RenderUtil.easeInOutQuad(drawPercent);
                mc.entityRenderer.disableLightmap();
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glShadeModel(7425);
                mc.entityRenderer.disableLightmap();

                AxisAlignedBB bb = it.getEntityBoundingBox();
                float radius = (float) (((bb.maxX - bb.minX) + (bb.maxZ - bb.minZ)) * 0.5f);
                double height = bb.maxY - bb.minY;
                double x = it.lastTickPosX + (it.posX - it.lastTickPosX) * event.getPartialTicks() - RenderManager.viewerPosX;
                double y = (it.lastTickPosY + (it.posY - it.lastTickPosY) * event.getPartialTicks() - RenderManager.viewerPosY) + height * drawPercent;
                double z = it.lastTickPosZ + (it.posZ - it.lastTickPosZ) * event.getPartialTicks() - RenderManager.viewerPosZ;
                double eased = height / 3 * drawPercent > 0.5 ? 1 - drawPercent : drawPercent * (drawMode ? -1 : 1);
                for (int i = 5; i < 360; i++) {
                    int color = RenderUtil.getRainbowFelix(6000, (int) (i * 30), 0.636f);
                    double x1 = x - Math.sin(i * Math.PI / 180F) * radius;
                    double z1 = z + Math.cos(i * Math.PI / 180F) * radius;
                    double x2 = x - Math.sin((i - 5) * Math.PI / 180F) * radius;
                    double z2 = z + Math.cos((i - 5) * Math.PI / 180F) * radius;
                    GL11.glBegin(GL11.GL_QUADS);
                    RenderUtil.color(color, 0);
                    GL11.glVertex3d(x1, y + eased, z1);
                    GL11.glVertex3d(x2, y + eased, z2);
                    RenderUtil.color(color, 150);
                    GL11.glVertex3d(x2, y, z2);
                    GL11.glVertex3d(x1, y, z1);
                    GL11.glEnd();
                }

                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glShadeModel(7424);
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glPopMatrix();
        }
    };

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static void drawHead(final AbstractClientPlayer target, final int x, final int y, final int width, final int height) {
        final ResourceLocation skin = target.getLocationSkin();
        StencilUtil.initStencilToWrite();
        GL11.glPushMatrix();
        RenderUtil.glDrawRoundedRectEllipse(x, y, width, height, RenderUtil.RoundingMode.FULL, 360, 15, new Color(255, 255, 255).getRGB());
        GL11.glPopMatrix();
        StencilUtil.readStencilBuffer(1);
        GL11.glColor4f(1.0f, 1, 1, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        StencilUtil.uninitStencilBuffer();
    }
}