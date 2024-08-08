// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.init.Blocks;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import me.perry.mcdonalds.util.EntityUtil;
import me.perry.mcdonalds.util.RenderUtil;
import java.awt.Color;
import me.perry.mcdonalds.util.ColorUtil;
import me.perry.mcdonalds.features.modules.client.ClickGui;
import me.perry.mcdonalds.event.events.Render3DEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.network.play.server.SPacketSpawnObject;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.MobEffects;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.features.modules.misc.AutoGG;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import java.util.Comparator;
import java.util.function.Predicate;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import me.perry.mcdonalds.util.MathUtil;
import me.perry.mcdonalds.util.Util;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import me.perry.mcdonalds.event.events.PacketEvent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.modules.Module;

public class AutoCrystal extends Module
{
    private final Timer placeTimer;
    private final Timer breakTimer;
    private final Timer preditTimer;
    private final Timer manualTimer;
    private final Setting<Integer> attackFactor;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> alpha;
    private final Setting<Integer> boxAlpha;
    private final Setting<Float> lineWidth;
    public Setting<Boolean> place;
    public Setting<Float> placeDelay;
    public Setting<Float> placeRange;
    public Setting<Boolean> explode;
    public Setting<Boolean> packetBreak;
    public Setting<Boolean> predicts;
    public Setting<Boolean> rotate;
    public Setting<Float> breakDelay;
    public Setting<Float> breakRange;
    public Setting<Float> breakWallRange;
    public Setting<Boolean> opPlace;
    public Setting<Boolean> suicide;
    public Setting<Boolean> autoswitch;
    public Setting<Boolean> ignoreUseAmount;
    public Setting<Integer> wasteAmount;
    public Setting<Boolean> facePlaceSword;
    public Setting<Float> targetRange;
    public Setting<Float> minDamage;
    public Setting<Float> facePlace;
    public Setting<Float> breakMaxSelfDamage;
    public Setting<Float> breakMinDmg;
    public Setting<Float> minArmor;
    public Setting<SwingMode> swingMode;
    public Setting<Boolean> render;
    public Setting<Boolean> renderDmg;
    public Setting<Boolean> box;
    public Setting<Boolean> outline;
    private final Setting<Integer> cRed;
    private final Setting<Integer> cGreen;
    private final Setting<Integer> cBlue;
    private final Setting<Integer> cAlpha;
    EntityEnderCrystal crystal;
    private EntityLivingBase target;
    private BlockPos pos;
    private int hotBarSlot;
    private boolean armor;
    private boolean armorTarget;
    private int crystalCount;
    private int predictWait;
    private int predictPackets;
    private boolean packetCalc;
    private float yaw;
    private EntityLivingBase realTarget;
    private int predict;
    private float pitch;
    private boolean rotating;
    
    public AutoCrystal() {
        super("AutoCrystal", "Automatically places crystals.", Category.COMBAT, true, false, false);
        this.placeTimer = new Timer();
        this.breakTimer = new Timer();
        this.preditTimer = new Timer();
        this.manualTimer = new Timer();
        this.attackFactor = (Setting<Integer>)this.register(new Setting("PredictDelay", (T)0, (T)0, (T)250));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)125, (T)0, (T)255));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.place = (Setting<Boolean>)this.register(new Setting("Place", (T)true));
        this.placeDelay = (Setting<Float>)this.register(new Setting("PlaceDelay", (T)4.0f, (T)0.0f, (T)500.0f));
        this.placeRange = (Setting<Float>)this.register(new Setting("PlaceRange", (T)4.0f, (T)0.1f, (T)7.0f));
        this.explode = (Setting<Boolean>)this.register(new Setting("Break", (T)true));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.predicts = (Setting<Boolean>)this.register(new Setting("Predict", (T)true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.breakDelay = (Setting<Float>)this.register(new Setting("BreakDelay", (T)4.0f, (T)0.0f, (T)500.0f));
        this.breakRange = (Setting<Float>)this.register(new Setting("BreakRange", (T)4.0f, (T)0.1f, (T)7.0f));
        this.breakWallRange = (Setting<Float>)this.register(new Setting("BreakWallRange", (T)4.0f, (T)0.1f, (T)7.0f));
        this.opPlace = (Setting<Boolean>)this.register(new Setting("1.13 Place", (T)false));
        this.suicide = (Setting<Boolean>)this.register(new Setting("AntiSuicide", (T)true));
        this.autoswitch = (Setting<Boolean>)this.register(new Setting("AutoSwitch", (T)true));
        this.ignoreUseAmount = (Setting<Boolean>)this.register(new Setting("IgnoreUseAmount", (T)true));
        this.wasteAmount = (Setting<Integer>)this.register(new Setting("UseAmount", (T)4, (T)1, (T)5));
        this.facePlaceSword = (Setting<Boolean>)this.register(new Setting("FacePlaceSword", (T)true));
        this.targetRange = (Setting<Float>)this.register(new Setting("TargetRange", (T)4.0f, (T)1.0f, (T)15.0f));
        this.minDamage = (Setting<Float>)this.register(new Setting("MinDamage", (T)4.0f, (T)0.1f, (T)20.0f));
        this.facePlace = (Setting<Float>)this.register(new Setting("FacePlaceHP", (T)4.0f, (T)0.0f, (T)36.0f));
        this.breakMaxSelfDamage = (Setting<Float>)this.register(new Setting("BreakMaxSelf", (T)4.0f, (T)0.1f, (T)36.0f));
        this.breakMinDmg = (Setting<Float>)this.register(new Setting("BreakMinDmg", (T)4.0f, (T)0.1f, (T)12.0f));
        this.minArmor = (Setting<Float>)this.register(new Setting("MinArmor", (T)4.0f, (T)0.1f, (T)90.0f));
        this.swingMode = (Setting<SwingMode>)this.register(new Setting("Swing", (T)SwingMode.MainHand));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.renderDmg = (Setting<Boolean>)this.register(new Setting("RenderDmg", (T)true));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.cRed = (Setting<Integer>)this.register(new Setting("OL-Red", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.cGreen = (Setting<Integer>)this.register(new Setting("OL-Green", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.cBlue = (Setting<Integer>)this.register(new Setting("OL-Blue", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.cAlpha = (Setting<Integer>)this.register(new Setting("OL-Alpha", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.rotating = false;
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f2;
                    final float f = f2 = (sphere ? (cy + r) : ((float)(cy + h)));
                    if (y >= f) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getStage() == 0 && this.rotate.getValue() && this.rotating && event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = event.getPacket();
            packet.yaw = this.yaw;
            packet.pitch = this.pitch;
            this.rotating = false;
        }
    }
    
    private void rotateTo(final Entity entity) {
        if (this.rotate.getValue()) {
            final float[] angle = MathUtil.calcAngle(AutoCrystal.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), entity.getPositionVector());
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.rotating = true;
        }
    }
    
    private void rotateToPos(final BlockPos pos) {
        if (this.rotate.getValue()) {
            final float[] angle = MathUtil.calcAngle(AutoCrystal.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() - 0.5f), (double)(pos.getZ() + 0.5f)));
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.rotating = true;
        }
    }
    
    @Override
    public void onEnable() {
        this.placeTimer.reset();
        this.breakTimer.reset();
        this.predictWait = 0;
        this.hotBarSlot = -1;
        this.pos = null;
        this.crystal = null;
        this.predict = 0;
        this.predictPackets = 1;
        this.target = null;
        this.packetCalc = false;
        this.realTarget = null;
        this.armor = false;
        this.armorTarget = false;
    }
    
    @Override
    public void onDisable() {
        this.rotating = false;
    }
    
    @Override
    public void onTick() {
        this.onCrystal();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.realTarget != null) {
            return this.realTarget.getName();
        }
        return null;
    }
    
    public void onCrystal() {
        if (AutoCrystal.mc.world == null || AutoCrystal.mc.player == null) {
            return;
        }
        this.realTarget = null;
        this.manualBreaker();
        this.crystalCount = 0;
        if (!this.ignoreUseAmount.getValue()) {
            for (final Entity crystal : AutoCrystal.mc.world.loadedEntityList) {
                if (crystal instanceof EntityEnderCrystal) {
                    if (!this.IsValidCrystal(crystal)) {
                        continue;
                    }
                    boolean count = false;
                    final double damage = this.calculateDamage(this.target.getPosition().getX() + 0.5, this.target.getPosition().getY() + 1.0, this.target.getPosition().getZ() + 0.5, (Entity)this.target);
                    if (damage >= this.minDamage.getValue()) {
                        count = true;
                    }
                    if (!count) {
                        continue;
                    }
                    ++this.crystalCount;
                }
            }
        }
        this.hotBarSlot = -1;
        if (AutoCrystal.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
            final int n;
            int crystalSlot = n = ((AutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? AutoCrystal.mc.player.inventory.currentItem : -1);
            if (crystalSlot == -1) {
                for (int l = 0; l < 9; ++l) {
                    if (AutoCrystal.mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                        crystalSlot = l;
                        this.hotBarSlot = l;
                        break;
                    }
                }
            }
            if (crystalSlot == -1) {
                this.pos = null;
                this.target = null;
                return;
            }
        }
        if (AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && AutoCrystal.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
            this.pos = null;
            this.target = null;
            return;
        }
        if (this.target == null) {
            this.target = (EntityLivingBase)this.getTarget();
        }
        if (this.target == null) {
            this.crystal = null;
            return;
        }
        if (this.target.getDistance((Entity)AutoCrystal.mc.player) > 12.0f) {
            this.crystal = null;
            this.target = null;
        }
        this.crystal = (EntityEnderCrystal)AutoCrystal.mc.world.loadedEntityList.stream().filter(this::IsValidCrystal).map(p_Entity -> p_Entity).min(Comparator.comparing(p_Entity -> this.target.getDistance(p_Entity))).orElse(null);
        if (this.crystal != null && this.explode.getValue() && this.breakTimer.passedMs(this.breakDelay.getValue().longValue())) {
            this.breakTimer.reset();
            if (this.packetBreak.getValue()) {
                this.rotateTo((Entity)this.crystal);
                AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)this.crystal));
            }
            else {
                this.rotateTo((Entity)this.crystal);
                AutoCrystal.mc.playerController.attackEntity((EntityPlayer)AutoCrystal.mc.player, (Entity)this.crystal);
            }
            if (this.swingMode.getValue() == SwingMode.MainHand) {
                AutoCrystal.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            else if (this.swingMode.getValue() == SwingMode.OffHand) {
                AutoCrystal.mc.player.swingArm(EnumHand.OFF_HAND);
            }
        }
        if (this.placeTimer.passedMs(this.placeDelay.getValue().longValue()) && this.place.getValue()) {
            this.placeTimer.reset();
            double damage2 = 0.5;
            for (final BlockPos blockPos : this.placePostions(this.placeRange.getValue())) {
                final double targetRange;
                if (blockPos != null && this.target != null && AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty() && (targetRange = this.target.getDistance((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ())) <= this.targetRange.getValue() && !this.target.isDead) {
                    if (this.target.getHealth() + this.target.getAbsorptionAmount() <= 0.0f) {
                        continue;
                    }
                    final double targetDmg = this.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5, (Entity)this.target);
                    this.armor = false;
                    for (final ItemStack is : this.target.getArmorInventoryList()) {
                        final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
                        final float red = 1.0f - green;
                        final int dmg = 100 - (int)(red * 100.0f);
                        if (dmg > this.minArmor.getValue()) {
                            continue;
                        }
                        this.armor = true;
                    }
                    Label_1211: {
                        if (targetDmg < this.minDamage.getValue()) {
                            if (this.facePlaceSword.getValue()) {
                                if (this.target.getAbsorptionAmount() + this.target.getHealth() <= this.facePlace.getValue()) {
                                    break Label_1211;
                                }
                            }
                            else if (!(AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && this.target.getAbsorptionAmount() + this.target.getHealth() <= this.facePlace.getValue()) {
                                break Label_1211;
                            }
                            if (this.facePlaceSword.getValue()) {
                                if (!this.armor) {
                                    continue;
                                }
                            }
                            else if (AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || !this.armor) {
                                continue;
                            }
                        }
                    }
                    final double selfDmg;
                    if ((selfDmg = this.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5, (Entity)AutoCrystal.mc.player)) + (this.suicide.getValue() ? 2.0 : 0.5) >= AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount() && selfDmg >= targetDmg && targetDmg < this.target.getHealth() + this.target.getAbsorptionAmount()) {
                        continue;
                    }
                    if (damage2 >= targetDmg) {
                        continue;
                    }
                    this.pos = blockPos;
                    damage2 = targetDmg;
                }
            }
            if (damage2 == 0.5) {
                this.pos = null;
                this.target = null;
                this.realTarget = null;
                return;
            }
            this.realTarget = this.target;
            if (AutoGG.getINSTANCE().isOn()) {
                final AutoGG autoGG = (AutoGG)McDonalds.moduleManager.getModuleByName("AutoGG");
                autoGG.addTargetedPlayer(this.target.getName());
            }
            if (this.hotBarSlot != -1 && this.autoswitch.getValue() && !AutoCrystal.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                AutoCrystal.mc.player.inventory.currentItem = this.hotBarSlot;
            }
            if (!this.ignoreUseAmount.getValue()) {
                int crystalLimit = this.wasteAmount.getValue();
                if (this.crystalCount >= crystalLimit) {
                    return;
                }
                if (damage2 < this.minDamage.getValue()) {
                    crystalLimit = 1;
                }
                if (this.crystalCount < crystalLimit && this.pos != null) {
                    this.rotateToPos(this.pos);
                    AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.pos, EnumFacing.UP, (AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
            else if (this.pos != null) {
                this.rotateToPos(this.pos);
                AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.pos, EnumFacing.UP, (AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onPacketReceive(final PacketEvent.Receive event) {
        final SPacketSpawnObject packet;
        if (event.getPacket() instanceof SPacketSpawnObject && (packet = event.getPacket()).getType() == 51 && this.predicts.getValue() && this.preditTimer.passedMs(this.attackFactor.getValue()) && this.predicts.getValue() && this.explode.getValue() && this.packetBreak.getValue() && this.target != null) {
            if (!this.isPredicting(packet)) {
                return;
            }
            final CPacketUseEntity predict = new CPacketUseEntity();
            predict.entityId = packet.getEntityID();
            predict.action = CPacketUseEntity.Action.ATTACK;
            AutoCrystal.mc.player.connection.sendPacket((Packet)predict);
        }
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.pos != null && this.render.getValue() && this.target != null) {
            RenderUtil.drawBoxESP(this.pos, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), this.outline.getValue(), ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), this.cAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
            if (this.renderDmg.getValue()) {
                final double renderDamage = this.calculateDamage(this.pos.getX() + 0.5, this.pos.getY() + 1.0, this.pos.getZ() + 0.5, (Entity)this.target);
                RenderUtil.drawText(this.pos, ((Math.floor(renderDamage) == renderDamage) ? Integer.valueOf((int)renderDamage) : String.format("%.1f", renderDamage)) + "");
            }
        }
    }
    
    private boolean isPredicting(final SPacketSpawnObject packet) {
        final BlockPos packPos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
        if (AutoCrystal.mc.player.getDistance(packet.getX(), packet.getY(), packet.getZ()) > this.breakRange.getValue()) {
            return false;
        }
        if (!this.canSeePos(packPos) && AutoCrystal.mc.player.getDistance(packet.getX(), packet.getY(), packet.getZ()) > this.breakWallRange.getValue()) {
            return false;
        }
        final double targetDmg = this.calculateDamage(packet.getX() + 0.5, packet.getY() + 1.0, packet.getZ() + 0.5, (Entity)this.target);
        if (EntityUtil.isInHole((Entity)AutoCrystal.mc.player) && targetDmg >= 1.0) {
            return true;
        }
        final double selfDmg = this.calculateDamage(packet.getX() + 0.5, packet.getY() + 1.0, packet.getZ() + 0.5, (Entity)AutoCrystal.mc.player);
        final double d = this.suicide.getValue() ? 2.0 : 0.5;
        if (selfDmg + d < AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount() && targetDmg >= this.target.getAbsorptionAmount() + this.target.getHealth()) {
            return true;
        }
        this.armorTarget = false;
        for (final ItemStack is : this.target.getArmorInventoryList()) {
            final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            if (dmg > this.minArmor.getValue()) {
                continue;
            }
            this.armorTarget = true;
        }
        return (targetDmg >= this.breakMinDmg.getValue() && selfDmg <= this.breakMaxSelfDamage.getValue()) || (EntityUtil.isInHole((Entity)this.target) && this.target.getHealth() + this.target.getAbsorptionAmount() <= this.facePlace.getValue());
    }
    
    private boolean IsValidCrystal(final Entity p_Entity) {
        if (p_Entity == null) {
            return false;
        }
        if (!(p_Entity instanceof EntityEnderCrystal)) {
            return false;
        }
        if (this.target == null) {
            return false;
        }
        if (p_Entity.getDistance((Entity)AutoCrystal.mc.player) > this.breakRange.getValue()) {
            return false;
        }
        if (!AutoCrystal.mc.player.canEntityBeSeen(p_Entity) && p_Entity.getDistance((Entity)AutoCrystal.mc.player) > this.breakWallRange.getValue()) {
            return false;
        }
        if (this.target.isDead || this.target.getHealth() + this.target.getAbsorptionAmount() <= 0.0f) {
            return false;
        }
        final double targetDmg = this.calculateDamage(p_Entity.getPosition().getX() + 0.5, p_Entity.getPosition().getY() + 1.0, p_Entity.getPosition().getZ() + 0.5, (Entity)this.target);
        if (EntityUtil.isInHole((Entity)AutoCrystal.mc.player) && targetDmg >= 1.0) {
            return true;
        }
        final double selfDmg = this.calculateDamage(p_Entity.getPosition().getX() + 0.5, p_Entity.getPosition().getY() + 1.0, p_Entity.getPosition().getZ() + 0.5, (Entity)AutoCrystal.mc.player);
        final double d = this.suicide.getValue() ? 2.0 : 0.5;
        if (selfDmg + d < AutoCrystal.mc.player.getHealth() + AutoCrystal.mc.player.getAbsorptionAmount() && targetDmg >= this.target.getAbsorptionAmount() + this.target.getHealth()) {
            return true;
        }
        this.armorTarget = false;
        for (final ItemStack is : this.target.getArmorInventoryList()) {
            final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            if (dmg > this.minArmor.getValue()) {
                continue;
            }
            this.armorTarget = true;
        }
        return (targetDmg >= this.breakMinDmg.getValue() && selfDmg <= this.breakMaxSelfDamage.getValue()) || (EntityUtil.isInHole((Entity)this.target) && this.target.getHealth() + this.target.getAbsorptionAmount() <= this.facePlace.getValue());
    }
    
    EntityPlayer getTarget() {
        EntityPlayer closestPlayer = null;
        for (final EntityPlayer entity : AutoCrystal.mc.world.playerEntities) {
            if (AutoCrystal.mc.player != null && !AutoCrystal.mc.player.isDead && !entity.isDead && entity != AutoCrystal.mc.player && !McDonalds.friendManager.isFriend(entity.getName())) {
                if (entity.getDistance((Entity)AutoCrystal.mc.player) > 12.0f) {
                    continue;
                }
                this.armorTarget = false;
                for (final ItemStack is : entity.getArmorInventoryList()) {
                    final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
                    final float red = 1.0f - green;
                    final int dmg = 100 - (int)(red * 100.0f);
                    if (dmg > this.minArmor.getValue()) {
                        continue;
                    }
                    this.armorTarget = true;
                }
                if (EntityUtil.isInHole((Entity)entity) && entity.getAbsorptionAmount() + entity.getHealth() > this.facePlace.getValue() && !this.armorTarget && this.minDamage.getValue() > 2.2f) {
                    continue;
                }
                if (closestPlayer == null) {
                    closestPlayer = entity;
                }
                else {
                    if (closestPlayer.getDistance((Entity)AutoCrystal.mc.player) <= entity.getDistance((Entity)AutoCrystal.mc.player)) {
                        continue;
                    }
                    closestPlayer = entity;
                }
            }
        }
        return closestPlayer;
    }
    
    private void manualBreaker() {
        final RayTraceResult result;
        if (this.manualTimer.passedMs(200L) && AutoCrystal.mc.gameSettings.keyBindUseItem.isKeyDown() && AutoCrystal.mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE && AutoCrystal.mc.player.inventory.getCurrentItem().getItem() != Items.GOLDEN_APPLE && AutoCrystal.mc.player.inventory.getCurrentItem().getItem() != Items.BOW && AutoCrystal.mc.player.inventory.getCurrentItem().getItem() != Items.EXPERIENCE_BOTTLE && (result = AutoCrystal.mc.objectMouseOver) != null) {
            if (result.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY)) {
                final Entity entity = result.entityHit;
                if (entity instanceof EntityEnderCrystal) {
                    if (this.packetBreak.getValue()) {
                        AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                    }
                    else {
                        AutoCrystal.mc.playerController.attackEntity((EntityPlayer)AutoCrystal.mc.player, entity);
                    }
                    this.manualTimer.reset();
                }
            }
            else if (result.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK)) {
                final BlockPos mousePos = new BlockPos((double)AutoCrystal.mc.objectMouseOver.getBlockPos().getX(), AutoCrystal.mc.objectMouseOver.getBlockPos().getY() + 1.0, (double)AutoCrystal.mc.objectMouseOver.getBlockPos().getZ());
                for (final Entity target : AutoCrystal.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(mousePos))) {
                    if (!(target instanceof EntityEnderCrystal)) {
                        continue;
                    }
                    if (this.packetBreak.getValue()) {
                        AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(target));
                    }
                    else {
                        AutoCrystal.mc.playerController.attackEntity((EntityPlayer)AutoCrystal.mc.player, target);
                    }
                    this.manualTimer.reset();
                }
            }
        }
    }
    
    private boolean canSeePos(final BlockPos pos) {
        return AutoCrystal.mc.world.rayTraceBlocks(new Vec3d(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.posY + AutoCrystal.mc.player.getEyeHeight(), AutoCrystal.mc.player.posZ), new Vec3d((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), false, true, false) == null;
    }
    
    private NonNullList<BlockPos> placePostions(final float placeRange) {
        final NonNullList positions = NonNullList.create();
        positions.addAll((Collection)getSphere(new BlockPos(Math.floor(AutoCrystal.mc.player.posX), Math.floor(AutoCrystal.mc.player.posY), Math.floor(AutoCrystal.mc.player.posZ)), placeRange, (int)placeRange, false, true, 0).stream().filter(pos -> this.canPlaceCrystal(pos, true)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (NonNullList<BlockPos>)positions;
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos, final boolean specialEntityCheck) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        try {
            if (!this.opPlace.getValue()) {
                if (AutoCrystal.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && AutoCrystal.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
                if (AutoCrystal.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || AutoCrystal.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) {
                    return false;
                }
                if (!specialEntityCheck) {
                    return AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
                }
                for (final Entity entity : AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
                    if (entity instanceof EntityEnderCrystal) {
                        continue;
                    }
                    return false;
                }
                for (final Entity entity : AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2))) {
                    if (entity instanceof EntityEnderCrystal) {
                        continue;
                    }
                    return false;
                }
            }
            else {
                if (AutoCrystal.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && AutoCrystal.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
                if (AutoCrystal.mc.world.getBlockState(boost).getBlock() != Blocks.AIR) {
                    return false;
                }
                if (!specialEntityCheck) {
                    return AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty();
                }
                for (final Entity entity : AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
                    if (entity instanceof EntityEnderCrystal) {
                        continue;
                    }
                    return false;
                }
            }
        }
        catch (Exception ignored) {
            return false;
        }
        return true;
    }
    
    private float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = this.getBlastReduction((EntityLivingBase)entity, this.getDamageMultiplied(damage), new Explosion((World)AutoCrystal.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    private float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            }
            catch (Exception ex) {}
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    private float getDamageMultiplied(final float damage) {
        final int diff = AutoCrystal.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public enum SwingMode
    {
        MainHand, 
        OffHand, 
        None;
    }
}
