package wtf.diablo.client.module.impl.combat.killaura;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.friend.FriendRepository;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.render.ColorModule;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.*;
import wtf.diablo.client.util.math.MathUtil;
import wtf.diablo.client.util.math.time.TimerUtil;
import wtf.diablo.client.util.mc.entity.EntityUtil;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;
import wtf.diablo.client.util.render.RenderUtil;
import wtf.diablo.client.util.render.gl.GLUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModuleMetaData(
        name = "Kill Aura",
        category = ModuleCategoryEnum.COMBAT
)
public final class KillAuraModule extends AbstractModule {
    private final MultiModeSetting<EnumKillAuraEntity> entities = new MultiModeSetting<>("Entities", EnumKillAuraEntity.values());
    private final ModeSetting<EnumHitPoint> hitPoint = new ModeSetting<>("Hit Point", EnumHitPoint.CHEST);
    private final ModeSetting<EnumAttackEvent> attackEvent = new ModeSetting<>("Attack Event", EnumAttackEvent.POST);
    private final NumberSetting<Float> randomRotation = new NumberSetting<>("Random Rotation", 0.0F, 0.0F, 5.0F, 0.05F);
    private final NumberSetting<Float> range = new NumberSetting<>("Range", 4.0F, 0.0F, 6.0F, 0.05F);
    private final NumberSetting<Integer> maxCPS = new NumberSetting<>("Max CPS", 10, 1, 20, 1);
    private final NumberSetting<Integer> minCPS = new NumberSetting<>("Min CPS", 8, 1, 20, 1);
    private final ModeSetting<AutoBlockMode> autoBlockMode = new ModeSetting<>("Auto Block", AutoBlockMode.FAKE);
    private final BooleanSetting targetTracers = new BooleanSetting("Target Tracer", true);
    private final ColorSetting targetTracerColor = new ColorSetting("Target Tracer Color", new Color(0, 0, 0));
    private final BooleanSetting attackRange = new BooleanSetting("Attack Range", true);
    private final ColorSetting attackRangeColor = new ColorSetting("Attack Range Color", new Color(0, 0, 0));
    private final BooleanSetting targetCircle = new BooleanSetting("Target Circle", false);
    private final BooleanSetting targetInvisible = new BooleanSetting("Target Invisible", false);

    private boolean blocking;

    private EntityLivingBase target;

    private final TimerUtil timerUtil = new TimerUtil();

    private double chance;

    private final List<Packet<?>> packets = new ArrayList<>();

    private boolean glowDown;
    private long deltaTime;
    private long lastMS;
    private double time2;

    public KillAuraModule() {
        this.registerSettings(this.entities, this.attackEvent, this.hitPoint, this.randomRotation, this.range, this.maxCPS, this.minCPS, this.autoBlockMode, this.targetTracers, this.targetTracerColor, this.attackRange, this.attackRangeColor, this.targetCircle, this.targetInvisible);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        this.blocking = false;
        this.target = null;
        this.timerUtil.reset();

        if ((autoBlockMode.getValue() == AutoBlockMode.WATCHDOG) && this.blocking) {
            mc.getNetHandler().addToSendQueueNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }

        this.emptyPackets();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        this.chance = generateClickChance(MathUtil.getRandomFloat(this.minCPS.getValue(), this.maxCPS.getValue()));
        this.blocking = false;
        this.target = null;
        this.timerUtil.reset();
    }

    @EventHandler
    private final Listener<MotionEvent> updateListener = e -> {
        this.setSuffix(this.range.getValue() + " | " + this.minCPS.getValue() + "/" + this.maxCPS.getValue());

        if (target != null) {
            if (target.isDead || target.getDistanceToEntity(this.mc.thePlayer) > range.getValue()) {
                if (autoBlockMode.getValue() == AutoBlockMode.WATCHDOG) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
            }
        }

        this.target = null;
        this.blocking = false;
        this.target = getClosestEntity();

        if (this.target != null) {
            if (e.getEventType() == EventTypeEnum.PRE) {
                this.rotate(e);
            }

            if (this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                this.autoBlock(e);
            }

            if (this.attackEvent.getValue() == EnumAttackEvent.PRE && e.getEventType() == EventTypeEnum.PRE) {
                this.attack(e);
            } else if (this.attackEvent.getValue() == EnumAttackEvent.POST && e.getEventType() == EventTypeEnum.POST) {
                this.attack(e);
            }
        }
    };

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = e -> {
        if (this.target != null) {
            if (this.targetTracers.getValue()) {
                RenderUtil.drawLineToPosition(this.target.posX, this.target.posY, this.target.posZ, 1, this.targetTracerColor.getValue().getRGB());
            }
        }

        if (this.attackRange.getValue()) {
            RenderUtil.drawCircle(e, this.range.getValue(), this.attackRangeColor.getValue());
        }

        if (target != null) {
            if (this.targetCircle.getValue()) {
                this.setDeltaTime();

                GL11.glPushMatrix();
                this.drawCircle(this.target, e.getPartialTicks());
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
    };

    private void drawCircle(final Entity entity, final float partialTicks) {

        // height of the circle
        final double height = 0.5 * (1 + Math.sin(4 * Math.PI * (time2 * .2)));

        // increment time 2 by 0.01 * delta time * 0.1
        time2 += .01 * deltaTime * .1;

        // if the height is greater than 1 than we want to start glowing down
        if (height > .995) {
            glowDown = true;
        }
        // entities x position
        final double entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - RenderManager.renderPosX;

        // entities y position
        final double entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - RenderManager.renderPosY;

        // entities z position.
        final double entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - RenderManager.renderPosZ;

        // enable blend
        GLUtils.enableBlend();

        // setup line smoothing
        GLUtils.setupLineSmooth();

        // disable alpha
        GlStateManager.disableAlpha();

        // set line width to 2
        GL11.glLineWidth(2.0F);

        // enable shade model
        GL11.glShadeModel(GL11.GL_SMOOTH);

        // disable cull face
        GL11.glDisable(GL11.GL_CULL_FACE);

        // size of the circle
        final double size = entity.width * 1.1;

        // offset of the circle
        final double yOffset = ((entity.height * 1) + .2) * height;

        // start triangle strip
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        // Full 360 degrees for the circle isnt a complete circle so we use 361
        for (int i = 0; i < 361; i++) {
            // set default color to white
            final Color color = new Color(ColorModule.getColor(300));

            // set the actual color.
            color(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (glowDown ? 255 * (height) : 255 * ((1 - height)))));

            // entityX position
            final double xRadians = Math.cos(Math.toRadians(i)) * size;

            // entityZ position
            final double zRadians = Math.sin(Math.toRadians(i)) * size;

            // draw first circle (no glow)
            GL11.glVertex3d(entityX + xRadians, entityY + yOffset, entityZ - zRadians);

            // set glow color
            color(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0));

            // draw second circle (glow)
            GL11.glVertex3d(entityX + xRadians, entityY + yOffset + ((glowDown ? -.5 * (1 - height) : .5 * height)), entityZ - zRadians);
        }

        // end triangle strip
        GL11.glEnd();

        // enable alpha
        GlStateManager.enableAlpha();

        // shade model flat
        GL11.glShadeModel(GL11.GL_FLAT);

        // disable line smooth
        GLUtils.disableLineSmooth();

        // enable cull face
        GL11.glEnable(GL11.GL_CULL_FACE);

        // disable blend
        GLUtils.disableBlend();
    }

    private void color(final double red, final double green, final double blue, final double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    private void color(final Color color) {
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public void setDeltaTime() {
        final long currentMS = System.currentTimeMillis();
        final long delta = currentMS - this.lastMS;
        this.lastMS = currentMS;
        this.deltaTime = delta;
    }

    private boolean shouldBlock;


    private void autoBlock(final MotionEvent event) {
        switch (autoBlockMode.getValue()) {
            case VANILLA:
                if (event.getEventType() == EventTypeEnum.POST)
                    return;

                if (!mc.thePlayer.isBlocking()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                }
                this.blocking = true;
                break;
            case WATCHDOG:
                if (event.getEventType() == EventTypeEnum.PRE) {
                    final int currentSlot = mc.thePlayer.inventory.currentItem;
                    final int newSlot = mc.thePlayer.inventory.currentItem % 7 + 2;

                    mc.getNetHandler().addToSendQueueNoEvent(new C09PacketHeldItemChange(newSlot));
                    mc.getNetHandler().addToSendQueueNoEvent(new C09PacketHeldItemChange(currentSlot));
                    mc.getNetHandler().addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                }
                this.blocking = true;
                break;
            case NCP:
                if (event.getEventType() == EventTypeEnum.PRE) {
                    mc.getNetHandler().addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                } else {
                    mc.getNetHandler().addToSendQueueNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }

                this.blocking = true;
                break;
            case FAKE:
                this.blocking = true;
                break;
            case VERUS:
                // idk someone said this worked
                mc.gameSettings.keyBindUseItem.pressed = true;
                break;
            default:
                this.blocking = false;
                break;
        }
    }

    private EntityLivingBase getClosestEntity() {
        final float range = this.range.getValue();

        final FriendRepository repository = Diablo.getInstance().getFriendRepository();

        for (final Entity entity : this.mc.theWorld.loadedEntityList) {
            if (entity.getDistanceToEntity(this.mc.thePlayer) <= range) {
                if (!canAttack(entity))
                    continue;

                if (entity == this.mc.thePlayer)
                    continue;

                if (!this.entities.getValue().contains(EnumKillAuraEntity.PLAYERS) && entity instanceof EntityPlayer)
                    continue;

                if (!this.entities.getValue().contains(EnumKillAuraEntity.MOBS) && entity instanceof EntityMob)
                    continue;

                if (!this.entities.getValue().contains(EnumKillAuraEntity.ANIMALS) && entity instanceof EntityAnimal)
                    continue;

                if (!this.entities.getValue().contains(EnumKillAuraEntity.INVISIBLE) && entity.isInvisible())
                    continue;

                if (entity instanceof EntityPlayer && repository.isFriend(entity.getName()))
                    continue;

                return (EntityLivingBase) entity;
            }
        }
        return null;
    }

    private boolean canAttack(final Entity entity) {
        return entity.isEntityAlive() && !entity.isDead && (this.targetInvisible.getValue() || !entity.isInvisible());
    }

    private void rotate(final MotionEvent eventUpdate) {
        final float[] rotations = EntityUtil.getAnglesFromHitPoint(this.target, this.hitPoint.getValue());

        final float yaw = rotations[0] + MathUtil.getRandomFloat(-this.randomRotation.getValue(), this.randomRotation.getValue());
        final float pitch = rotations[1] + MathUtil.getRandomFloat(-this.randomRotation.getValue(), this.randomRotation.getValue());

        EntityUtil.rotate(eventUpdate, yaw, pitch);
    }

    private void attack(final MotionEvent event) {
        if (this.target == null) {
            this.blocking = false;
            return;
        }

        final boolean shouldClick = chance > Math.random();

        if (!shouldClick) {
            chance = generateClickChance(MathUtil.getRandomFloat(this.minCPS.getValue(), this.maxCPS.getValue()));

            return;
        }

        if (this.timerUtil.hasTimeElapsed(chance)) {
            if (this.target.isDead)
                return;

            mc.thePlayer.swingItem();
            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
            chance = generateClickChance(MathUtil.getRandomFloat(this.minCPS.getValue(), this.maxCPS.getValue()));
            this.timerUtil.reset();
        }
    }

    private void unblock() {
        //if (currentlyBlocking) {
        mc.getNetHandler().addToSendQueueNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        //  this.currentlyBlocking = false;
        //}
    }

    private void block() {
        //if (!currentlyBlocking) {
        mc.getNetHandler().addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
        //   this.currentlyBlocking = true;
        //}
    }

    public enum EnumHitPoint implements IMode {
        HEAD("Head"),
        CHEST("Chest"),
        LOWER_CHEST("Lower Chest"),
        STOMACH("Stomach"),
        COCK("Cock"),
        LEGS("Legs"),
        FEET("Feet"),
        TOES("Toes");

        EnumHitPoint(final String name) {
            this.name = name;
        }

        private final String name;

        @Override
        public String getName() {
            return this.name;
        }
    }


    public enum EnumAttackEvent implements IMode {
        PRE("Pre"),
        POST("Post");

        private final String name;

        EnumAttackEvent(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public enum EnumKillAuraEntity implements IMode {
        PLAYERS("Players"),
        MOBS("Mobs"),
        ANIMALS("Animals"),
        INVISIBLE("Invisible");

        private final String name;

        EnumKillAuraEntity(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    enum AutoBlockMode implements IMode {
        FAKE("Fake"),
        VANILLA("Vanilla"),
        WATCHDOG("Watchdog"),
        NCP("NCP"),
        VERUS("Verus"),
        NONE("None");

        private final String name;

        AutoBlockMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public boolean isBlocking() {
        return blocking;
    }

    public Entity getTarget() {
        return target;
    }

    private double generateClickChance(final double averageCps) {
        final double rand1 = Math.random();
        final double rand2 = Math.random();

        double clickProb = averageCps * 0.95F * (1 + (rand1 - 0.5) / 2);
        clickProb = clickProb * (1 + (rand2 - 0.5) / 50);
        clickProb -= 0.5;
        return clickProb / 20;
    }

    private int getNewSlot(final int currentSlot) {
        final int selectedSlot = MathUtil.getRandomInt(0, 8);

        if (selectedSlot == currentSlot) {
            return getNewSlot(currentSlot);
        }

        return selectedSlot;
    }

    private void emptyPackets() {
        for (final Packet<?> packet : packets) {
            mc.getNetHandler().addToSendQueueNoEvent(packet);
        }
        packets.clear();
    }
}