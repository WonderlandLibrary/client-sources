// =============================================== //
// Recompile disabled. Please run Recaf with a JDK //
// =============================================== //

// Decompiled with: Procyon 0.5.36
// Class Version: 8
package cc.novoline.modules.combat;

import cc.novoline.modules.configurations.property.AbstractProperty;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import java.util.function.Consumer;
import cc.novoline.utils.DebugUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.network.play.server.S45PacketTitle$Type;
import net.minecraft.network.play.server.S45PacketTitle;
import org.lwjgl.opengl.GL11;
import cc.novoline.events.events.Render3DEvent;
import net.minecraft.client.gui.ScaledResolution;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import cc.novoline.utils.RenderUtils;
import net.aHM;
import cc.novoline.events.events.Render2DEvent;
import net.minecraft.item.ItemArmor;
import java.util.function.Function;
import java.util.Comparator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import cc.novoline.modules.exploits.Blink;
import cc.novoline.modules.player.Freecam;
import net.minecraft.command.ICommandSender;
import cc.novoline.utils.PlayerUtils;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityAnimal;
import cc.novoline.modules.visual.ClickGUI;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import cc.novoline.utils.notifications.NotificationType;
import net.minecraft.network.play.client.C03PacketPlayer;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.SlowdownEvent;
import cc.novoline.events.events.TickUpdateEvent;
import net.minecraft.item.Item;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cc.novoline.modules.move.Speed;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import cc.novoline.events.EventTarget;
import java.util.Iterator;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import net.minecraft.init.Items;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import net.minecraft.util.IChatComponent;
import cc.novoline.utils.Servers;
import cc.novoline.utils.ServerUtils;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.utils.RotationUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.entity.player.EntityPlayer;
import cc.novoline.gui.screen.setting.Setting$ColorPickerMode;
import java.util.EnumSet;
import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.util.concurrent.CopyOnWriteArrayList;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.StringProperty;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import java.awt.Dimension;
import cc.novoline.utils.Timer;
import java.util.List;
import net.minecraft.entity.Entity;
import cc.novoline.modules.AbstractModule;

public final class KillAura extends AbstractModule
{
    private Entity target;
    private int switchIndex;
    private List<Entity> targetList;
    private final Timer timerAttack;
    private final Timer timerSwitch;
    private final Timer lossTimer;
    private final Dimension screenSize;
    private final List<Packet<?>> toDispatch;
    private float iyaw;
    private float ipitch;
    private float prevIYaw;
    private float prevIPitch;
    private boolean blinking;
    private boolean A;
    private C08PacketPlayerBlockPlacement y;
    @Property("mode")
    private final StringProperty mode;
    @Property("switch-delay")
    private final FloatProperty switchDelay;
    @Property("min-aps")
    private final DoubleProperty V;
    @Property("max-aps")
    private final DoubleProperty aps;
    @Property("range")
    private final DoubleProperty range;
    @Property("wall-range")
    private final DoubleProperty wallRange;
    @Property("block-range")
    private final DoubleProperty blockRange;
    @Property("fov-check")
    private final IntProperty fovCheck;
    @Property("rotations-smoothness")
    private final IntProperty smoothness;
    @Property("targets")
    private final ListProperty<String> targets;
    @Property("aura-sort")
    private final StringProperty sort;
    @Property("filters")
    private final ListProperty<String> filters;
    @Property("particles")
    private final ListProperty<String> particles;
    @Property("auto-block")
    private final BooleanProperty autoBlock;
    @Property("target-hud")
    private final BooleanProperty targetHud;
    @Property("th-mode")
    private final StringProperty ao;
    @Property("autodisable")
    private final ListProperty<String> autoDisable;
    @Property("blink")
    private final BooleanProperty blink;
    @Property("raytrace")
    private final BooleanProperty raytrace;
    @Property("lag-check")
    private final BooleanProperty lagCheck;
    @Property("visualize-range")
    private final BooleanProperty visRange;
    @Property("color")
    private final ColorProperty color;
    @Property("th-x")
    private final IntProperty thx;
    @Property("th-y")
    private final IntProperty thy;
    @Property("particles-mode")
    private final StringProperty G;
    @Property("particles-amount")
    private final IntProperty Y;
    @Property("auto-weapon")
    private final BooleanProperty B;
    @Property("keep-sprint")
    private final BooleanProperty C;
    @Property("auto-block-mode")
    private final StringProperty aq;
    @Property("attack-event")
    private final StringProperty P;
    @Property("autoblock-event")
    private final StringProperty ab;
    private static int[] X;
    
    public KillAura(final ModuleManager moduleManager) {
        Q();
        super(moduleManager, "Killaura", "Kill Aura", EnumModuleType.COMBAT, "Automatically attacks entities around you");
        this.targetList = new CopyOnWriteArrayList<Entity>();
        this.timerAttack = new Timer();
        this.timerSwitch = new Timer();
        this.lossTimer = new Timer();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.toDispatch = new ArrayList<Packet<?>>();
        this.mode = PropertyFactory.createString("Switch").acceptableValues("Multi", "Switch", "Single");
        this.switchDelay = PropertyFactory.createFloat(400.0f).minimum(50.0f).maximum(1000.0f);
        this.V = PropertyFactory.createDouble(5.0).minimum(1.0).maximum(20.0);
        this.aps = PropertyFactory.createDouble(5.0).minimum(1.0).maximum(20.0);
        this.range = PropertyFactory.createDouble(3.5).minimum(1.0).maximum(7.0);
        this.wallRange = PropertyFactory.createDouble(3.5).minimum(1.0).maximum(7.0);
        this.blockRange = PropertyFactory.createDouble(3.5).minimum(1.0).maximum(10.0);
        this.fovCheck = PropertyFactory.createInt(180).minimum(1).maximum(180);
        this.smoothness = PropertyFactory.createInt(60).minimum(1).maximum(100);
        this.targets = PropertyFactory.createList("Players").acceptableValues("Players", "Animals", "Mobs", "Invisibles");
        this.sort = PropertyFactory.createString("Distance").acceptableValues("Distance", "Health", "Armor", "FOV", "HurtTime");
        this.filters = PropertyFactory.createList("Teams").acceptableValues("Teams", "Armor");
        this.particles = PropertyFactory.createList("Enchant").acceptableValues("Enchant", "Critical");
        this.autoBlock = PropertyFactory.booleanFalse();
        this.targetHud = PropertyFactory.booleanFalse();
        this.ao = PropertyFactory.createString("Pretty").acceptableValues("Trash", "Less Pretty", "Pretty", "Prettier", "Prettiest");
        this.autoDisable = PropertyFactory.createList("Death").acceptableValues("World Change", "Game End", "Death");
        this.blink = PropertyFactory.booleanFalse();
        this.raytrace = PropertyFactory.booleanFalse();
        this.lagCheck = PropertyFactory.booleanFalse();
        this.visRange = PropertyFactory.booleanFalse();
        this.color = PropertyFactory.createColor(-1);
        this.thx = PropertyFactory.createInt((int)this.screenSize.getWidth() / 4).minimum(1).maximum((int)this.screenSize.getWidth() / 2);
        this.thy = PropertyFactory.createInt((int)this.screenSize.getHeight() / 4).minimum(1).maximum((int)this.screenSize.getHeight() / 2);
        this.G = PropertyFactory.createString("Hit").acceptableValues("Hit", "Always");
        this.Y = PropertyFactory.createInt(2).minimum(1).maximum(5);
        this.B = PropertyFactory.booleanFalse();
        this.C = PropertyFactory.booleanFalse();
        this.aq = PropertyFactory.createString("Packet").acceptableValues("Packet", "Vanilla");
        this.P = PropertyFactory.createString("POST").acceptableValues("PRE", "POST");
        this.ab = PropertyFactory.createString("POST").acceptableValues("PRE", "POST");
        Manager.put(new Setting("AURA_MODE", "Mode", SettingType.COMBOBOX, this, this.mode));
        Manager.put(new Setting("AURA_SORT", "Sort by", SettingType.COMBOBOX, this, this.sort, this::lambda$new$0));
        Manager.put(new Setting("SWITCH_DELAY", "Switch Delay", SettingType.SLIDER, this, this.switchDelay, 50.0, this::lambda$new$2));
        Manager.put(new Setting("KA_MIN_APS", "Min APS", SettingType.SLIDER, this, this.V, 1.0, KillAura::lambda$new$2));
        Manager.put(new Setting("KA_MAX_APS", "Max APS", SettingType.SLIDER, this, this.aps, 1.0, KillAura::lambda$new$3));
        Manager.put(new Setting("KA_ATTACK_EVENT", "Attack Event", SettingType.COMBOBOX, this, this.P));
        Manager.put(new Setting("RANGE", "Range", SettingType.SLIDER, this, this.range, 0.1));
        Manager.put(new Setting("BLOCK_RANGE", "Block Range", SettingType.SLIDER, this, this.blockRange, 0.1));
        Manager.put(new Setting("WALL_RANGE", "Wall Range", SettingType.SLIDER, this, this.wallRange, 0.1));
        Manager.put(new Setting("AURA_FOV", "FOV Check", SettingType.SLIDER, this, this.fovCheck, 1.0));
        Manager.put(new Setting("AURA_FOV", "Angle Smoothing", SettingType.SLIDER, this, this.smoothness, 5.0));
        Manager.put(new Setting("TARGETS", "Targets", SettingType.SELECTBOX, this, this.targets));
        Manager.put(new Setting("KA_FILTER", "Filters", SettingType.SELECTBOX, this, this.filters));
        Manager.put(new Setting("KA_PARTICLES", "Particles", SettingType.SELECTBOX, this, this.particles));
        Manager.put(new Setting("KA_PARTICLES_MODE", "Particles Mode", SettingType.COMBOBOX, this, this.G, this::lambda$new$4));
        Manager.put(new Setting("KA_PARTICLES_AMOUNT", "Particles Amount", SettingType.SLIDER, this, this.Y, 1.0, this::lambda$new$5));
        Manager.put(new Setting("KA_KEEP_SPRINT", "Keep Sprint", SettingType.CHECKBOX, this, this.C));
        Manager.put(new Setting("KA_LAG_CHECK", "Lag Check", SettingType.CHECKBOX, this, this.lagCheck));
        Manager.put(new Setting("KA_RAY_TRACE", "Ray Trace", SettingType.CHECKBOX, this, this.raytrace));
        Manager.put(new Setting("KA_VIS_RANGE", "Visualize Range", SettingType.CHECKBOX, this, this.visRange));
        Manager.put(new Setting("KA_RANGE_COLOR", "Range Color", SettingType.COLOR_PICKER, this, this.color, null, this.visRange::get));
        Manager.put(new Setting("KA_BLINK", "Blink", SettingType.CHECKBOX, this, this.blink));
        Manager.put(new Setting("KA_AUTOBLOCK", "Auto Block", SettingType.CHECKBOX, this, this.autoBlock));
        Manager.put(new Setting("KA_AB_MODE", "Auto Block Mode", SettingType.COMBOBOX, this, this.aq));
        Manager.put(new Setting("KA_AB_EVENT", "Auto Block Event", SettingType.COMBOBOX, this, this.ab));
        Manager.put(new Setting("KA_AUTO_WEAPON", "Auto Weapon", SettingType.CHECKBOX, this, this.B));
        Manager.put(new Setting("TAR_HUD", "Target HUD", SettingType.CHECKBOX, this, this.targetHud));
        Manager.put(new Setting("TAR_HUD_MODE", "Style", SettingType.COMBOBOX, this, this.ao, this.targetHud::get));
        Manager.put(new Setting("KADISABLE", "Disable On", SettingType.SELECTBOX, this, this.autoDisable));
    }
    
    @Override
    public void onDisable() {
        this.iyaw = this.mc.player.rotationYaw;
        this.ipitch = this.mc.player.rotationPitch;
        this.target = null;
        this.y = null;
        this.targetList.clear();
        this.dispatchPackets();
    }
    
    @Override
    public void onEnable() {
        Q();
        this.setSuffix(this.mode.get());
        this.iyaw = this.mc.player.rotationYaw;
        this.ipitch = this.mc.player.rotationPitch;
        this.lossTimer.reset();
        if (this.blink.get()) {
            this.toDispatch.clear();
            this.blinking = true;
        }
        this.blinking = false;
    }
    
    private boolean hasArmor(final EntityPlayer entityPlayer) {
        Q();
        return entityPlayer.inventory.armorInventory[0] != null || entityPlayer.inventory.armorInventory[1] != null || entityPlayer.inventory.armorInventory[2] != null || entityPlayer.inventory.armorInventory[3] != null;
    }
    
    private boolean heldSword() {
        Q();
        return this.mc.player.getHeldItem() != null && this.mc.player.getHeldItem().getItem() instanceof ItemSword;
    }
    
    private boolean isInFOV() {
        Q();
        if (this.target != null) {
            final float[] b = RotationUtil.b((EntityLivingBase)this.target);
            final int n = (int)b[1];
            final int n2 = (int)b[0];
            final int n3 = (int)this.mc.player.rotationYaw;
            final int n4 = (int)this.mc.player.rotationPitch;
            final int abs = Math.abs(n3 - n2);
            final int abs2 = Math.abs(n4 - n);
            return abs <= ((AbstractProperty<Integer>)this.fovCheck).get() && abs2 <= ((AbstractProperty<Integer>)this.fovCheck).get();
        }
        return false;
    }
    
    public boolean serverLag() {
        Q();
        return this.lagCheck.get() && this.lossTimer.getLastDelay() >= 100.0;
    }
    
    public boolean shouldAttack() {
        Q();
        return this.isEnabled() && this.target != null && this.target.isEntityAlive() && this.mc.player.getDistanceToEntity(this.target) <= (this.mc.player.canEntityBeSeen(this.target) ? this.x() : this.N()) && this.isInFOV() && !this.isEnabled((Class<? extends AbstractModule>)Scaffold.class);
    }
    
    public boolean shouldRotate() {
        Q();
        return this.shouldAttack() && !this.serverLag() && !this.isEnabled((Class<? extends AbstractModule>)Scaffold.class) && !this.mc.at.f();
    }
    
    public boolean shouldBlock() {
        Q();
        return this.autoBlock.get() && this.target != null && this.heldSword() && this.target.isEntityAlive() && this.isEnabled() && !this.mc.at.f() && this.mc.player.getDistanceToEntity(this.target) <= (this.mc.player.canEntityBeSeen(this.target) ? this.j() : this.N()) && !this.isEnabled((Class<? extends AbstractModule>)Scaffold.class);
    }
    
    private boolean isAutismShopKeeperCheck(final Entity entity) {
        final IChatComponent displayName = entity.getDisplayName();
        Q();
        final String formattedText = displayName.getFormattedText();
        displayName.getUnformattedText();
        final boolean b = !formattedText.substring(0, formattedText.length() - 2).contains("§");
        final boolean contains = formattedText.substring(formattedText.length() - 2).contains("§");
        return ServerUtils.isHypixel() && ServerUtils.serverIs(Servers.BW) && b && contains;
    }
    
    private double getYawDifference(final Entity entity) {
        final float[] b = RotationUtil.b((EntityLivingBase)entity);
        Q();
        final float n = (float)(int)b[0];
        return (this.mc.player.rotationYaw > n) ? (this.mc.player.rotationYaw - n) : (n - this.mc.player.rotationYaw);
    }
    
    @EventTarget
    public void targetSwitching(final MotionUpdateEvent motionUpdateEvent) {
        Q();
        if (motionUpdateEvent.getState().equals(EventState.PRE)) {
            if (this.mc.player.inventory.getStackInSlot(0) != null && this.mc.player.inventory.getStackInSlot(0).getItem() == Items.compass && this.mc.player.inventory.getStackInSlot(0).getDisplayName().contains("Teleporter")) {
                this.target = null;
            }
            if (this.targetsInRange() > 0) {
                this.targetList = this.getTargetsFromRange();
            }
            this.targetList = this.getEntityList(this.sort.get());
            if (this.target != null && !this.target.isEntityAlive()) {
                this.target = null;
            }
            Label_0521: {
                if (this.targetList != null) {
                    if (this.mode.get().equals("Multi")) {
                        this.target = this.targetList.get(0);
                    }
                    if (this.timerSwitch.delay(((AbstractProperty<Float>)this.switchDelay).get())) {
                        this.switchAndReset();
                    }
                    if (!this.isContainsTarget(this.mode().equalsIgnoreCase("Switch"))) {
                        if (this.target != null) {
                            if (this.mc.player.getDistanceToEntity(this.target) > this.x() && this.targetsInRange() > 0) {
                                this.target = this.getTargetFromRange();
                            }
                            if (this.mc.player.getDistanceToEntity(this.target) > this.j()) {
                                this.target = null;
                                this.switchAndReset();
                            }
                            if (!this.target.isEntityAlive() && ((EntityLivingBase)this.target).getHealth() <= 0.0f) {
                                this.target = null;
                                this.switchAndReset();
                            }
                            if (!this.mode().equalsIgnoreCase("Switch")) {
                                break Label_0521;
                            }
                            this.target = this.targetList.get(this.switchIndex);
                        }
                        this.target = this.targetList.get(this.mode().equalsIgnoreCase("Switch") ? this.switchIndex : 0);
                    }
                    final Iterator<Entity> iterator = this.targetList.iterator();
                    if (iterator.hasNext()) {
                        final Entity target = iterator.next();
                        if (this.novoline.getPlayerManager().hasType(target.getName(), PlayerManager$EnumPlayerType.TARGET)) {
                            this.target = target;
                        }
                    }
                }
            }
            this.prevIPitch = this.ipitch;
            this.prevIYaw = this.iyaw;
        }
    }
    
    public float getPrevIPitch() {
        return this.prevIPitch;
    }
    
    public float getPrevIYaw() {
        return this.prevIYaw;
    }
    
    private void switchAndReset() {
        this.switchIndex = (this.switchIndex + 1) % this.targetList.size();
        this.timerSwitch.reset();
    }
    
    private boolean rayTraycing(final Entity entity) {
        Q();
        return !this.raytrace.get() || this.novoline.getPlayerManager().hasType(entity.getName(), PlayerManager$EnumPlayerType.TARGET) || this.getPosition() != null || this.mc.at.f();
    }
    
    private void g(final Entity entity) {
        Q();
        if (this.shouldBlock()) {
            if (ServerUtils.isHypixel()) {
                this.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
            if (this.aq.equals("Packet")) {
                this.I();
            }
            this.A = false;
        }
        this.mc.player.swingItem();
        this.sendPacket(new C02PacketUseEntity(entity, C02PacketUseEntity$Action.ATTACK));
        if (!this.C.get() && entity.hurtResistantTime >= 19) {
            final EntityPlayerSP player = this.mc.player;
            player.motionX *= 0.6;
            final EntityPlayerSP player2 = this.mc.player;
            player2.motionZ *= 0.6;
        }
        if (this.G.equals("Always")) {
            if (this.particles.contains("Enchant")) {
                this.mc.player.onEnchantmentCritical(entity);
            }
            if (this.particles.contains("Critical")) {
                this.mc.player.onCriticalHit(entity);
            }
        }
    }
    
    @EventTarget
    public void a(final MotionUpdateEvent motionUpdateEvent) {
        Q();
        if (motionUpdateEvent.getState().equals(EventState.PRE)) {
            if (!this.shouldBlock() || (this.mc.player.isMoving() && this.aq.equals("Packet"))) {
                this.I();
            }
            if (this.shouldAttack()) {
                if (this.B.get() && !this.mc.at.f()) {
                    this.F();
                }
                if (this.G.equals("Hit") && !this.particles.isEmpty() && this.target.hurtResistantTime == 20) {
                    int n = 0;
                    if (n < ((AbstractProperty<Integer>)this.Y).get()) {
                        this.mc.player.onEnchantmentCritical(this.target);
                        this.mc.player.onCriticalHit(this.target);
                        ++n;
                    }
                }
            }
        }
        if (motionUpdateEvent.getState().equals(EventState.valueOf(ServerUtils.isHypixel() ? "PRE" : this.P.get())) && this.shouldAttack() && this.rayTraycing(this.target) && !this.serverLag() && this.timerAttack.delay((double)(1000L / this.p())) && (!this.getModule((Class<? extends Speed>)Speed.class).c() || this.mc.player.ticksExisted % 2 == 0)) {
            if (this.mode.get().equals("Multi")) {
                final Iterator<Entity> iterator = this.getTargetsFromRange().iterator();
                if (iterator.hasNext()) {
                    this.g(iterator.next());
                }
            }
            this.g(this.target);
            this.timerAttack.reset();
        }
        if (motionUpdateEvent.getState().equals(EventState.valueOf(this.ab.get())) && this.shouldBlock()) {
            this.interactAutoBlock();
            this.mc.player.getHeldItem().useItemRightClick(this.mc.world, this.mc.player);
            this.S();
        }
    }
    
    public void F() {
        Q();
        final float n = 1.0f;
        int currentItem = -1;
        int n2 = 0;
        if (n2 < 9) {
            final ItemStack stackInSlot = this.mc.player.inventory.getStackInSlot(n2);
            if (stackInSlot != null && this.a(stackInSlot) > n) {
                currentItem = n2;
                this.a(stackInSlot);
            }
            ++n2;
        }
        if (currentItem != -1 && this.mc.player.inventory.getStackInSlot(this.mc.player.inventory.currentItem) != this.mc.player.inventory.getStackInSlot(currentItem)) {
            this.mc.player.inventory.currentItem = currentItem;
        }
    }
    
    private float a(final ItemStack itemStack) {
        Q();
        float n = 0.0f;
        final Item item = itemStack.getItem();
        if (item instanceof ItemTool) {
            n += ((ItemTool)item).getDamage();
        }
        if (item instanceof ItemSword) {
            n += ((ItemSword)item).getAttackDamage();
        }
        return n + (EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.3f);
    }
    
    private void S() {
        Q();
        if (!this.A) {
            this.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.player.getHeldItem()));
            this.A = true;
        }
    }
    
    private void I() {
        Q();
        if (this.A) {
            this.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.A = false;
        }
    }
    
    @EventTarget
    public void onTick(final TickUpdateEvent tickUpdateEvent) {
        this.setSuffix(this.mode());
    }
    
    @EventTarget
    public void onSlowDown(final SlowdownEvent slowdownEvent) {
        if (this.shouldBlock()) {
            slowdownEvent.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onSend(final PacketEvent packetEvent) {
        Q();
        if (packetEvent.getDirection().equals(PacketDirection.OUTGOING)) {
            if (this.blinking) {
                if (packetEvent.getPacket() instanceof C03PacketPlayer && ((C03PacketPlayer)packetEvent.getPacket()).isMoving()) {
                    this.toDispatch.add(packetEvent.getPacket());
                    packetEvent.setCancelled(true);
                }
                if (packetEvent.getPacket() instanceof C02PacketUseEntity) {
                    final C02PacketUseEntity c02PacketUseEntity = (C02PacketUseEntity)packetEvent.getPacket();
                    if (c02PacketUseEntity.getAction().equals(C02PacketUseEntity$Action.ATTACK) && c02PacketUseEntity.getEntityFromWorld(this.mc.world).equals(this.target)) {
                        this.dispatchPackets();
                        this.blinking = false;
                    }
                }
                if (this.toDispatch.size() > 60) {
                    this.dispatchPackets();
                    this.novoline.getNotificationManager().pop(this.getDisplayName(), "Packet buffer overloaded!", 2000, NotificationType.ERROR);
                    this.blinking = false;
                }
            }
            if (this.shouldBlock() || this.A) {
                if (packetEvent.getPacket() instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)packetEvent.getPacket()).getStatus().equals(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM)) {
                    packetEvent.setCancelled(true);
                }
                if (packetEvent.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                    final C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement)packetEvent.getPacket();
                    if (c08PacketPlayerBlockPlacement.getPlacedBlockDirection() == 255 && c08PacketPlayerBlockPlacement.getStack() != null && c08PacketPlayerBlockPlacement.getStack().getItem() instanceof ItemSword) {
                        packetEvent.setCancelled(true);
                    }
                }
            }
            if (!this.shouldRotate() || !ServerUtils.isHypixel() || !(packetEvent.getPacket() instanceof C08PacketPlayerBlockPlacement)) {
                return;
            }
            final C08PacketPlayerBlockPlacement y = (C08PacketPlayerBlockPlacement)packetEvent.getPacket();
            if (y.getPlacedBlockDirection() != 255 || y.getStack() == null || !(y.getStack().getItem() instanceof ItemSword)) {
                this.y = y;
                packetEvent.setCancelled(true);
            }
        }
        this.lossTimer.reset();
    }
    
    @EventTarget
    public void b(final MotionUpdateEvent motionUpdateEvent) {
        Q();
        if (motionUpdateEvent.getState().equals(EventState.PRE)) {
            if (this.shouldRotate()) {
                final float[] a = RotationUtil.a(RotationUtil.b((EntityLivingBase)this.target), ((AbstractProperty<Integer>)this.smoothness).get());
                this.iyaw = a[0];
                this.ipitch = a[1];
                if (this.y == null && !this.getModule((Class<? extends AutoPot>)AutoPot.class).d() && (!this.mode.get().equals("Multi") || this.getTargetsFromRange().size() == 1)) {
                    motionUpdateEvent.setYaw(this.iyaw);
                    motionUpdateEvent.setPitch(this.ipitch);
                }
            }
            this.iyaw = this.mc.player.rotationYaw;
            this.ipitch = this.mc.player.rotationPitch;
        }
        if (this.y != null) {
            this.sendPacketNoEvent(this.y);
            this.y = null;
        }
    }
    
    public float getIYaw() {
        return this.iyaw;
    }
    
    public float getIPitch() {
        return this.ipitch;
    }
    
    private boolean interactable(final Block block) {
        Q();
        return block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.crafting_table || block == Blocks.furnace || block == Blocks.ender_chest || block == Blocks.enchanting_table;
    }
    
    private void interactAutoBlock() {
        Q();
        if (this.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (this.mc.objectMouseOver.entityHit != null) {
                this.sendPacket(new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, C02PacketUseEntity$Action.INTERACT));
            }
            if (this.interactable(this.mc.world.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock())) {
                this.mc.at.onPlayerRightClick(this.mc.player, this.mc.world, this.mc.player.getHeldItem(), this.mc.objectMouseOver.getBlockPos(), Block.getFacingDirection(this.mc.objectMouseOver.getBlockPos()), this.mc.objectMouseOver.hitVec);
            }
        }
    }
    
    public boolean isValidEntity(final Entity entity) {
        final boolean contains = this.targets.contains("Mobs");
        Q();
        final boolean contains2 = this.targets.contains("Animals");
        this.targets.contains("Players");
        final boolean contains3 = this.targets.contains("Invisibles");
        if (entity.isEntityAlive() && this.mc.player.getHealth() > 0.0f && !this.novoline.getPlayerManager().hasType(entity.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
            if (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityGolem) {
                return contains && ((!ServerUtils.serverIs(Servers.SW) && !ServerUtils.serverIs(Servers.BW)) || !this.getModule((Class<? extends ClickGUI>)ClickGUI.class).k().contains(entity.getEntityID())) && (!entity.isInvisible() || contains3);
            }
            if (entity instanceof EntityAnimal || entity instanceof EntityVillager) {
                return contains2 && ((!ServerUtils.serverIs(Servers.SW) && !ServerUtils.serverIs(Servers.BW)) || !this.getModule((Class<? extends ClickGUI>)ClickGUI.class).k().contains(entity.getEntityID())) && (!entity.isInvisible() || contains3);
            }
            if (entity instanceof EntityPlayer) {
                return entity != this.mc.player && (!this.filters.contains("Armor") || this.hasArmor((EntityPlayer)entity)) && (!this.filters.contains("Teams") || !PlayerUtils.inTeamWithMinecraftPlayer(entity)) && (!entity.isInvisible() || contains3) && !this.isAutismShopKeeperCheck(entity) && entity != this.getModule((Class<? extends Freecam>)Freecam.class).getFreecamEntity() && entity != this.getModule((Class<? extends Blink>)Blink.class).getBlinkEntity();
            }
        }
        return false;
    }
    
    private boolean isContainsTarget(final boolean b) {
        Q();
        final ObjectListIterator iterator = this.getEntityList(this.sort.get()).stream().filter((Predicate<? super Object>)this::lambda$isContainsTarget$3).collect((Collector<? super Object, ?, ObjectArrayList>)Collectors.toCollection((Supplier<R>)ObjectArrayList::new)).iterator();
        return ((Iterator)iterator).hasNext() && this.novoline.getPlayerManager().hasType(((Iterator<Entity>)iterator).next().getName(), PlayerManager$EnumPlayerType.TARGET);
    }
    
    private List<Entity> getEntityList(final String s) {
        Q();
        List<? super Object> list = this.mc.world.getLoadedEntityList().stream().filter((Predicate<? super Object>)this::isValidEntity).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        if (this.mode().equalsIgnoreCase("Single")) {
            int n = -1;
            Label_0192: {
                switch (s.hashCode()) {
                    case 353103893: {
                        if (s.equals("Distance")) {
                            n = 0;
                            break Label_0192;
                        }
                        break;
                    }
                    case 63533343: {
                        if (s.equals("Armor")) {
                            n = 1;
                            break Label_0192;
                        }
                        break;
                    }
                    case -2137395588: {
                        if (s.equals("Health")) {
                            n = 2;
                            break Label_0192;
                        }
                        break;
                    }
                    case 69805: {
                        if (s.equals("FOV")) {
                            n = 3;
                            break Label_0192;
                        }
                        break;
                    }
                    case 765499292: {
                        if (s.equals("HurtTime")) {
                            n = 4;
                            break;
                        }
                        break;
                    }
                }
            }
            switch (n) {
                case 0: {
                    list = list.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)this::lambda$getEntityList$4)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toCollection(ObjectArrayList::new));
                }
                case 1: {
                    list = list.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)this::lambda$getEntityList$5)).collect((Collector<? super Object, ?, List<Object>>)Collectors.toCollection((Supplier<R>)ObjectArrayList::new));
                }
                case 2: {
                    list = list.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)KillAura::lambda$getEntityList$6)).collect((Collector<? super Object, ?, List<Object>>)Collectors.toCollection((Supplier<R>)ObjectArrayList::new));
                }
                case 3: {
                    list = list.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)this::getYawDifference)).collect((Collector<? super Object, ?, List<Object>>)Collectors.toCollection((Supplier<R>)ObjectArrayList::new));
                }
                case 4: {
                    list = list.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)KillAura::lambda$getEntityList$7)).collect((Collector<? super Object, ?, List<Object>>)Collectors.toCollection((Supplier<R>)ObjectArrayList::new));
                    break;
                }
            }
        }
        return list.stream().filter((Predicate<? super Object>)this::isInReach).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
    }
    
    public List<Entity> getTargetsFromRange() {
        return this.getEntityList(this.sort.get()).stream().filter((Predicate<? super Object>)this::lambda$getTargetsFromRange$9).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
    }
    
    private int targetsInRange() {
        Q();
        int n = 0;
        if (this.getEntityList(this.sort.get()).isEmpty()) {
            return 0;
        }
        final Iterator<Entity> iterator = this.getEntityList(this.sort.get()).iterator();
        if (iterator.hasNext() && this.mc.player.getDistanceToEntity(iterator.next()) <= this.x()) {
            ++n;
            goto Label_0098;
        }
        return n;
    }
    
    private Entity getTargetFromRange() {
        Q();
        final Iterator<Entity> iterator = this.getEntityList(this.sort.get()).iterator();
        if (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if (this.mc.player.getDistanceToEntity(entity) <= this.x()) {
                return entity;
            }
        }
        return null;
    }
    
    private float interpolateAngles(final float n) {
        return 0.0f;
    }
    
    private float getProtection(final ItemStack[] array) {
        Q();
        float n = 0.0f;
        final int length = array.length;
        int n2 = 0;
        if (n2 < length) {
            final ItemStack itemStack = array[n2];
            if (itemStack.getItem() instanceof ItemArmor) {
                final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
                n = (float)((float)((float)((float)((float)((float)(n + (itemArmor.damageReduceAmount + (100 - itemArmor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack) * 0.0075)) + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, itemStack) / 100.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, itemStack) / 100.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack) / 100.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 50.0) + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack) / 100.0);
            }
            ++n2;
        }
        return n;
    }
    
    private boolean isInReach(final Entity entity) {
        Q();
        return this.mc.player.getDistanceToEntity(entity) <= (this.mc.player.canEntityBeSeen(entity) ? Math.max(this.x(), this.j()) : this.N());
    }
    
    @EventTarget
    public void onRender2D(final Render2DEvent render2DEvent) {
        Q();
        if (this.targetHud.get() && this.shouldAttack() && this.target instanceof EntityPlayer && !(this.mc.currentScreen instanceof aHM)) {
            RenderUtils.a(this, (EntityLivingBase)this.target);
        }
        if (this.blinking) {
            final ScaledResolution resolution = render2DEvent.getResolution();
            final int size = this.toDispatch.size();
            String s = "";
            if (size > 40) {
                s = "§8";
            }
            if (size > 20) {
                s = "§7";
            }
            Fonts$SF$SF_18.SF_18.drawString("Buffer size: " + (s + this.toDispatch.size()), resolution.getScaledWidthStatic(this.mc) / 2 - 26, resolution.getScaledHeightStatic(this.mc) / 2 + 10, 16777215, true);
        }
    }
    
    @EventTarget
    public void onRender3D(final Render3DEvent render3DEvent) {
        Q();
        if (this.visRange.get()) {
            RenderUtils.pre3D();
            GL11.glLineWidth(6.0f);
            GL11.glBegin(3);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
            final double n = 0.0;
            if (n < 6.283185307179586) {
                GL11.glVertex3d(this.mc.player.lastTickPosX + (this.mc.player.posX - this.mc.player.lastTickPosX) * render3DEvent.getPartialTicks() + Math.sin(n) * ((AbstractProperty<Double>)this.range).get() - this.mc.getRenderManager().renderPosX, this.mc.player.lastTickPosY + (this.mc.player.posY - this.mc.player.lastTickPosY) * render3DEvent.getPartialTicks() - this.mc.getRenderManager().renderPosY, this.mc.player.lastTickPosZ + (this.mc.player.posZ - this.mc.player.lastTickPosZ) * render3DEvent.getPartialTicks() + Math.cos(n) * ((AbstractProperty<Double>)this.range).get() - this.mc.getRenderManager().renderPosZ);
            }
            GL11.glEnd();
            GL11.glLineWidth(3.0f);
            GL11.glBegin(3);
            GL11.glColor4f(this.color.getAwtColor().getRed() / 255.0f, this.color.getAwtColor().getGreen() / 255.0f, this.color.getAwtColor().getBlue() / 255.0f, 1.0f);
            final double n2 = 0.0;
            if (n2 < 6.283185307179586) {
                GL11.glVertex3d(this.mc.player.lastTickPosX + (this.mc.player.posX - this.mc.player.lastTickPosX) * render3DEvent.getPartialTicks() + Math.sin(n2) * ((AbstractProperty<Double>)this.range).get() - this.mc.getRenderManager().renderPosX, this.mc.player.lastTickPosY + (this.mc.player.posY - this.mc.player.lastTickPosY) * render3DEvent.getPartialTicks() - this.mc.getRenderManager().renderPosY, this.mc.player.lastTickPosZ + (this.mc.player.posZ - this.mc.player.lastTickPosZ) * render3DEvent.getPartialTicks() + Math.cos(n2) * ((AbstractProperty<Double>)this.range).get() - this.mc.getRenderManager().renderPosZ);
            }
            GL11.glEnd();
            RenderUtils.post3D();
        }
    }
    
    @EventTarget
    public void onAutoDisable(final PacketEvent packetEvent) {
        Q();
        if (packetEvent.getDirection().equals(PacketDirection.INCOMING) && ServerUtils.serverIs(Servers.DUELS) && packetEvent.getPacket() instanceof S45PacketTitle) {
            final S45PacketTitle s45PacketTitle = (S45PacketTitle)packetEvent.getPacket();
            if (s45PacketTitle.getType().equals(S45PacketTitle$Type.TITLE)) {
                final String unformattedText = s45PacketTitle.getMessage().getUnformattedText();
                if (unformattedText.equals("VICTORY!")) {
                    this.novoline.getNotificationManager().pop("Check chat for stats", 1000, NotificationType.INFO);
                    DebugUtil.log(true, "[" + EnumChatFormatting.RED + "Stats" + EnumChatFormatting.GRAY + "] " + EnumChatFormatting.RESET + "Your hp: " + EnumChatFormatting.GREEN + this.mc.player.getHealth());
                }
                if (unformattedText.equals("GAME OVER!")) {
                    this.novoline.getNotificationManager().pop("Check chat for stats", 1000, NotificationType.INFO);
                    DebugUtil.log(true, "[" + EnumChatFormatting.RED + "Stats" + EnumChatFormatting.GRAY + "] " + EnumChatFormatting.RESET + "Opponent hp: " + EnumChatFormatting.GREEN + ((EntityLivingBase)this.target).getHealth());
                }
            }
        }
    }
    
    private void dispatchPackets() {
        this.blinking = false;
        this.toDispatch.forEach(this::sendPacketNoEvent);
        this.toDispatch.clear();
    }
    
    public MovingObjectPosition getPosition() {
        final float collisionBorderSize = this.target.getCollisionBorderSize();
        final Vec3 positionEyes = RotationUtil.getPositionEyes(1.0f);
        final Vec3 vectorForRotation = RotationUtil.getVectorForRotation(this.getIPitch(), this.getIYaw());
        return this.target.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize).calculateIntercept(positionEyes, positionEyes.addVector(vectorForRotation.xCoord * this.x(), vectorForRotation.yCoord * this.x(), vectorForRotation.zCoord * this.x()));
    }
    
    public Entity getTarget() {
        return this.target;
    }
    
    public void setTarget(final Entity target) {
        this.target = target;
    }
    
    public StringProperty getMode() {
        return this.mode;
    }
    
    public String mode() {
        return this.mode.get();
    }
    
    public FloatProperty getSwitchDelay() {
        return this.switchDelay;
    }
    
    public long p() {
        return ServerUtils.a() ? 2L : ThreadLocalRandom.current().nextLong(((AbstractProperty<Double>)this.V).get().longValue(), ((AbstractProperty<Double>)this.aps).get().longValue() + 1L);
    }
    
    public DoubleProperty getRange() {
        return this.range;
    }
    
    public DoubleProperty getWallRange() {
        return this.wallRange;
    }
    
    public DoubleProperty getBlockRange() {
        return this.blockRange;
    }
    
    public double x() {
        return ((AbstractProperty<Double>)this.range).get();
    }
    
    public double j() {
        return ((AbstractProperty<Double>)this.blockRange).get();
    }
    
    public double N() {
        return ((AbstractProperty<Double>)this.wallRange).get();
    }
    
    public IntProperty getFovCheck() {
        return this.fovCheck;
    }
    
    public ListProperty<String> getTargets() {
        return this.targets;
    }
    
    public ListProperty<String> getFilters() {
        return this.filters;
    }
    
    public ListProperty<String> getParticles() {
        return this.particles;
    }
    
    public BooleanProperty getAutoBlock() {
        return this.autoBlock;
    }
    
    public BooleanProperty getTargetHud() {
        return this.targetHud;
    }
    
    public ListProperty getAutoDisable() {
        return this.autoDisable;
    }
    
    public IntProperty getThx() {
        return this.thx;
    }
    
    public IntProperty getThy() {
        return this.thy;
    }
    
    public List<Entity> getTargetList() {
        return this.targetList;
    }
    
    public StringProperty getSort() {
        return this.sort;
    }
    
    public boolean lagCheck() {
        return this.lagCheck.get();
    }
    
    public StringProperty B() {
        return this.ao;
    }
    
    public DoubleProperty R() {
        return this.V;
    }
    
    public DoubleProperty getAps() {
        return this.aps;
    }
    
    public boolean r() {
        return this.C.get();
    }
    
    public StringProperty w() {
        return this.P;
    }
    
    public StringProperty i() {
        return this.aq;
    }
    
    public StringProperty g() {
        return this.ab;
    }
    
    private boolean lambda$getTargetsFromRange$9(final Entity entity) {
        Q();
        return this.mc.player.getDistanceToEntity(entity) <= this.x();
    }
    
    private static Integer lambda$getEntityList$7(final Entity entity) {
        return entity.hurtResistantTime;
    }
    
    private static Float lambda$getEntityList$6(final Entity entity) {
        return ((EntityLivingBase)entity).getHealth();
    }
    
    private Float lambda$getEntityList$5(final Entity entity) {
        return (entity instanceof EntityPlayer) ? this.getProtection(((EntityPlayer)entity).inventory.armorInventory) : 99999.0f;
    }
    
    private Float lambda$getEntityList$4(final Entity entity) {
        return entity.getDistanceToEntity(this.mc.player);
    }
    
    private boolean lambda$isContainsTarget$3(final Entity entity) {
        Q();
        return this.mc.player.getDistanceToEntity(entity) < this.x();
    }
    
    private Boolean lambda$new$5() {
        Q();
        return !this.particles.isEmpty() && this.G.equals("Hit");
    }
    
    private Boolean lambda$new$4() {
        Q();
        return !this.particles.isEmpty();
    }
    
    private static Boolean lambda$new$3() {
        Q();
        return !ServerUtils.a();
    }
    
    private static Boolean lambda$new$2() {
        Q();
        return !ServerUtils.a();
    }
    
    private Boolean lambda$new$2() {
        return this.mode().equalsIgnoreCase("Switch");
    }
    
    private Boolean lambda$new$0() {
        return this.mode().equalsIgnoreCase("Single");
    }
    
    public static void a(final int[] x) {
        KillAura.X = x;
    }
    
    public static int[] Q() {
        return KillAura.X;
    }
    
    static {
        a(new int[3]);
    }
}
 