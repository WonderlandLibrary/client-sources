/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.vecmath.Vector2f;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.govno.client.Client;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventPostMove;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.event.events.EventSafeWalk;
import ru.govno.client.event.events.EventStep;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClickTeleport;
import ru.govno.client.module.modules.EntityBox;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.MoveHelper;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;

public abstract class Entity
implements ICommandSender {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final List<ItemStack> field_190535_b = Collections.emptyList();
    private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    private static double renderDistanceWeight = 1.0;
    private static int nextEntityID;
    private int entityId = nextEntityID++;
    public boolean rayGround;
    public boolean preventEntitySpawning;
    private final List<Entity> riddenByEntities = Lists.newArrayList();
    protected int rideCooldown;
    public Entity ridingEntity;
    public boolean forceSpawn;
    public World world;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public AxisAlignedBB boundingBox = ZERO_AABB;
    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided;
    public boolean velocityChanged;
    public boolean isInWeb;
    private boolean isOutsideBorder;
    public boolean isDead;
    public float width = 0.6f;
    public float height = 1.8f;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    public float distanceWalkedOnStepModified;
    public float fallDistance;
    private int nextStepDistance = 1;
    private float field_191959_ay = 1.0f;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    protected Random rand = new Random();
    public int ticksExisted;
    private int field_190534_ay = -this.func_190531_bD();
    protected boolean inWater;
    public int hurtResistantTime;
    protected boolean firstUpdate = true;
    protected boolean isImmuneToFire;
    protected EntityDataManager dataManager;
    protected static final DataParameter<Byte> FLAGS;
    private static final DataParameter<Integer> AIR;
    private static final DataParameter<String> CUSTOM_NAME;
    private static final DataParameter<Boolean> CUSTOM_NAME_VISIBLE;
    private static final DataParameter<Boolean> SILENT;
    private static final DataParameter<Boolean> NO_GRAVITY;
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public long serverPosX;
    public long serverPosY;
    public long serverPosZ;
    public boolean ignoreFrustumCheck;
    public boolean isAirBorne;
    public int timeUntilPortal;
    protected boolean inPortal;
    protected int portalCounter;
    public int dimension;
    protected BlockPos lastPortalPos;
    protected Vec3d lastPortalVec;
    protected EnumFacing teleportDirection;
    private boolean invulnerable;
    protected UUID entityUniqueID = MathHelper.getRandomUUID(this.rand);
    protected String cachedUniqueIdString = this.entityUniqueID.toString();
    private final CommandResultStats cmdResultStats = new CommandResultStats();
    protected boolean glowing;
    private final Set<String> tags = Sets.newHashSet();
    private boolean isPositionDirty;
    private final double[] field_191505_aI = new double[]{0.0, 0.0, 0.0};
    private long field_191506_aJ;
    public static double motionx;
    public static double motiony;
    public static double motionz;
    public static double Getmotionx;
    public static double Getmotiony;
    public static double Getmotionz;
    boolean antiFired = false;

    public Entity(World worldIn) {
        this.world = worldIn;
        this.setPosition(0.0, 0.0, 0.0);
        if (worldIn != null) {
            this.dimension = worldIn.provider.getDimensionType().getId();
        }
        this.dataManager = new EntityDataManager(this);
        this.dataManager.register(FLAGS, (byte)0);
        this.dataManager.register(AIR, 300);
        this.dataManager.register(CUSTOM_NAME_VISIBLE, false);
        this.dataManager.register(CUSTOM_NAME, "");
        this.dataManager.register(SILENT, false);
        this.dataManager.register(NO_GRAVITY, false);
        this.entityInit();
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int id) {
        this.entityId = id;
    }

    public Set<String> getTags() {
        return this.tags;
    }

    public boolean addTag(String tag) {
        if (this.tags.size() >= 1024) {
            return false;
        }
        this.tags.add(tag);
        return true;
    }

    public boolean removeTag(String tag) {
        return this.tags.remove(tag);
    }

    public void onKillCommand() {
        this.setDead();
    }

    protected abstract void entityInit();

    public EntityDataManager getDataManager() {
        return this.dataManager;
    }

    public boolean equals(Object p_equals_1_) {
        if (p_equals_1_ instanceof Entity) {
            return ((Entity)p_equals_1_).entityId == this.entityId;
        }
        return false;
    }

    public int hashCode() {
        return this.entityId;
    }

    protected void preparePlayerToSpawn() {
        if (this.world != null) {
            while (this.posY > 0.0 && this.posY < 256.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()) break;
                this.posY += 1.0;
            }
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.rotationPitch = 0.0f;
        }
    }

    public void setDead() {
        this.isDead = true;
    }

    public void setDead(boolean death) {
        this.isDead = death;
    }

    public void setDropItemsWhenDead(boolean dropWhenDead) {
    }

    public void setSize2(float width, float height) {
        if (width != this.width || height != this.height) {
            float f = this.width;
            this.width = width;
            this.height = height;
            if (this.width < f) {
                double d0 = (double)width / 2.0;
                this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + (double)this.height, this.posZ + d0));
                return;
            }
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            this.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)this.width, axisalignedbb.minY + (double)this.height, axisalignedbb.minZ + (double)this.width));
            if (this.width > f && !this.firstUpdate && !this.world.isRemote) {
                this.moveEntity(MoverType.SELF, f - this.width, 0.0, f - this.width);
            }
        }
    }

    protected void setSize(float width, float height) {
        if (width != this.width || height != this.height) {
            float f = this.width;
            this.width = width;
            this.height = height;
            if (this.width < f) {
                double d0 = (double)width / 2.0;
                this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + (double)this.height, this.posZ + d0));
                return;
            }
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            this.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)this.width, axisalignedbb.minY + (double)this.height, axisalignedbb.minZ + (double)this.width));
            if (this.width > f && !this.firstUpdate && !this.world.isRemote) {
                this.moveEntity(MoverType.SELF, f - this.width, 0.0, f - this.width);
            }
        }
    }

    public static double getRenderX(Entity entityIn) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double xp = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)pTicks;
        double xf = xp - (entityIn.lastTickPosX - xp) * 13.0;
        if (entityIn != null && !entityIn.isDead) {
            return xf;
        }
        return 0.0;
    }

    public static double getRenderY(Entity entityIn) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double yp = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)pTicks;
        double yf = yp - (entityIn.lastTickPosY - yp) * 13.0;
        if (entityIn != null && !entityIn.isDead) {
            return yf;
        }
        return 0.0;
    }

    public static double getRenderZ(Entity entityIn) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double zp = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)pTicks;
        double zf = zp - (entityIn.lastTickPosZ - zp) * 13.0;
        if (entityIn != null && !entityIn.isDead) {
            return zf;
        }
        return 0.0;
    }

    public static Vec3d getRenderVecPosEnt(Entity entityIn) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double xp = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)pTicks;
        double yp = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)pTicks;
        double zp = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)pTicks;
        double xf = xp - (entityIn.lastTickPosX - xp) * 3.0;
        double yf = yp - (entityIn.lastTickPosY - yp) * 3.0;
        double zf = zp - (entityIn.lastTickPosZ - zp) * 3.0;
        if (entityIn != null && !entityIn.isDead) {
            return new Vec3d(xf, yf, zf);
        }
        return null;
    }

    protected void setRotation(float yaw, float pitch) {
        this.rotationYaw = yaw % 360.0f;
        this.rotationPitch = pitch % 360.0f;
    }

    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        float f = this.width / 2.0f;
        float f1 = this.height;
        this.setEntityBoundingBox(new AxisAlignedBB(x - (double)f, y, z - (double)f, x + (double)f, y + (double)f1, z + (double)f));
    }

    public void setSilentPos(double x, double y, double z, AxisAlignedBB entityBox) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.setEntityBoundingBox(entityBox);
    }

    public void setPosY(double y) {
        this.posY = y;
        float f = this.width / 2.0f;
        float f1 = this.height;
        this.setEntityBoundingBox(new AxisAlignedBB(this.posX - (double)f, y, this.posZ - (double)f, this.posX + (double)f, y + (double)f1, this.posZ + (double)f));
    }

    public void setAngles(float yaw, float pitch) {
        float f = this.rotationPitch;
        float f1 = this.rotationYaw;
        this.rotationYaw = (float)((double)this.rotationYaw + (double)yaw * 0.15);
        this.rotationPitch = MathHelper.clamp(this.rotationPitch - pitch * 0.15f, -90.0f, 90.0f);
        this.prevRotationPitch += this.rotationPitch - f;
        this.prevRotationYaw += this.rotationYaw - f1;
        if (this.ridingEntity != null) {
            this.ridingEntity.applyOrientationToEntity(this);
        }
    }

    public void onUpdate() {
        if (!this.world.isRemote) {
            this.setFlag(6, this.isGlowing());
        }
        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        this.world.theProfiler.startSection("entityBaseTick");
        if (this.isRiding() && this.getRidingEntity().isDead) {
            this.dismountRidingEntity();
        }
        if (this.rideCooldown > 0) {
            --this.rideCooldown;
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (!this.world.isRemote && this.world instanceof WorldServer) {
            this.world.theProfiler.startSection("portal");
            if (this.inPortal) {
                MinecraftServer minecraftserver = this.world.getMinecraftServer();
                if (minecraftserver.getAllowNether()) {
                    int i;
                    if (!this.isRiding() && this.portalCounter++ >= (i = this.getMaxInPortalTime())) {
                        this.portalCounter = i;
                        this.timeUntilPortal = this.getPortalCooldown();
                        int j = this.world.provider.getDimensionType().getId() == -1 ? 0 : -1;
                        this.changeDimension(j);
                    }
                    this.inPortal = false;
                }
            } else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }
            this.decrementTimeUntilPortal();
            this.world.theProfiler.endSection();
        }
        this.spawnRunningParticles();
        this.handleWaterMovement();
        if (this.world.isRemote) {
            this.extinguish();
        } else if (this.field_190534_ay > 0) {
            if (this.isImmuneToFire) {
                this.field_190534_ay -= 4;
                if (this.field_190534_ay < 0) {
                    this.extinguish();
                }
            } else {
                if (this.field_190534_ay % 20 == 0) {
                    this.attackEntityFrom(DamageSource.onFire, 1.0f);
                }
                --this.field_190534_ay;
            }
        }
        if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.world.isRemote) {
            this.setFlag(0, this.field_190534_ay > 0);
        }
        this.firstUpdate = false;
        this.world.theProfiler.endSection();
    }

    protected void decrementTimeUntilPortal() {
        if (this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
        }
    }

    public int getMaxInPortalTime() {
        return 1;
    }

    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.lava, 4.0f);
            this.setFire(15);
        }
    }

    public void setFire(int seconds) {
        int i = seconds * 20;
        if (this instanceof EntityLivingBase) {
            i = EnchantmentProtection.getFireTimeForEntity((EntityLivingBase)this, i);
        }
        if (this.field_190534_ay < i) {
            this.field_190534_ay = i;
        }
    }

    public void extinguish() {
        this.field_190534_ay = 0;
    }

    protected void kill() {
        this.setDead();
    }

    public boolean isOffsetPositionInLiquid(double x, double y, double z) {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().offset(x, y, z);
        return this.isLiquidPresentInAABB(axisalignedbb);
    }

    private boolean isLiquidPresentInAABB(AxisAlignedBB bb) {
        return this.world.getCollisionBoxes(this, bb).isEmpty() && !this.world.containsAnyLiquid(bb);
    }

    public void setMotionXZ(float motionx, float motionz) {
        this.motionX = motionx;
        this.motionZ = motionz;
    }

    public void multiplyMotionXZ(float motion) {
        this.motionX *= (double)motion;
        this.motionZ *= (double)motion;
    }

    public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
        EventStep step = new EventStep(true, this.stepHeight);
        if (this instanceof EntityPlayerSP) {
            step.call();
        }
        double preX = this.posX;
        double preZ = this.posZ;
        AxisAlignedBB before = this.getEntityBoundingBox().addCoord(0.0, 0.0, 0.0);
        Minecraft mc = Minecraft.getMinecraft();
        if (this instanceof EntityPlayerSP) {
            if (Getmotionx != p_70091_2_) {
                Getmotionx = p_70091_2_;
            }
            if (Getmotiony != p_70091_4_) {
                Getmotiony = p_70091_4_;
            }
            if (Getmotionz != p_70091_6_) {
                Getmotionz = p_70091_6_;
            }
            if (this != null && mc.world != null && this instanceof EntityPlayerSP) {
                for (Module module : Client.moduleManager.modules) {
                    if (!module.actived || Panic.stop) continue;
                    module.onMovement();
                    module.onUpdateMovement();
                }
                if (this != Minecraft.player) {
                    return;
                }
                if (motionx != 0.0) {
                    p_70091_2_ = motionx * 1.06;
                    if (motionx == 1.0E-10 || motionx == -1.0E-10) {
                        p_70091_2_ = 0.0;
                    }
                    motionx = 0.0;
                }
                if (motiony != 0.0) {
                    p_70091_4_ = motiony;
                    motiony = 0.0;
                }
                if (motionz != 0.0) {
                    p_70091_6_ = motionz * 1.06;
                    if (motionz == 1.0E-10 || motionz == -1.0E-10) {
                        p_70091_6_ = 0.0;
                    }
                    motionz = 0.0;
                }
            }
        }
        EventSafeWalk safeEvent = new EventSafeWalk();
        if (this instanceof EntityPlayerSP) {
            safeEvent.call();
        }
        if (this.noClip) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(p_70091_2_, p_70091_4_, p_70091_6_));
            this.resetPositionToBB();
        } else {
            BlockPos blockpos1;
            IBlockState iblockstate1;
            Block block1;
            int k6;
            int i1;
            int j6;
            BlockPos blockpos;
            IBlockState iblockstate;
            boolean flag;
            List<AxisAlignedBB> bb;
            if (x == MoverType.PISTON) {
                long i = this.world.getTotalWorldTime();
                if (i != this.field_191506_aJ) {
                    Arrays.fill(this.field_191505_aI, 0.0);
                    this.field_191506_aJ = i;
                }
                if (p_70091_2_ != 0.0) {
                    int j = EnumFacing.Axis.X.ordinal();
                    double d0 = MathHelper.clamp(p_70091_2_ + this.field_191505_aI[j], -0.51, 0.51);
                    p_70091_2_ = d0 - this.field_191505_aI[j];
                    this.field_191505_aI[j] = d0;
                    if (Math.abs(p_70091_2_) <= (double)1.0E-5f) {
                        return;
                    }
                } else if (p_70091_4_ != 0.0) {
                    int l4 = EnumFacing.Axis.Y.ordinal();
                    double d12 = MathHelper.clamp(p_70091_4_ + this.field_191505_aI[l4], -0.51, 0.51);
                    p_70091_4_ = d12 - this.field_191505_aI[l4];
                    this.field_191505_aI[l4] = d12;
                    if (Math.abs(p_70091_4_) <= (double)1.0E-5f) {
                        return;
                    }
                } else {
                    if (p_70091_6_ == 0.0) {
                        return;
                    }
                    int i5 = EnumFacing.Axis.Z.ordinal();
                    double d13 = MathHelper.clamp(p_70091_6_ + this.field_191505_aI[i5], -0.51, 0.51);
                    p_70091_6_ = d13 - this.field_191505_aI[i5];
                    this.field_191505_aI[i5] = d13;
                    if (Math.abs(p_70091_6_) <= (double)1.0E-5f) {
                        return;
                    }
                }
            }
            this.world.theProfiler.startSection("move");
            double d10 = this.posX;
            double d11 = this.posY;
            double d1 = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                p_70091_2_ *= 0.25;
                p_70091_4_ *= (double)0.05f;
                p_70091_6_ *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double d2 = p_70091_2_;
            double d3 = p_70091_4_;
            double d4 = p_70091_6_;
            if ((x == MoverType.SELF || x == MoverType.PLAYER) && this.onGround && this.isSneaking() || safeEvent.isCancelled()) {
                double d5 = 0.05;
                while (p_70091_2_ != 0.0 && this.world.getCollisionBoxes(this, this.getEntityBoundingBox().offset(p_70091_2_, -this.stepHeight, 0.0)).isEmpty()) {
                    p_70091_2_ = p_70091_2_ < 0.05 && p_70091_2_ >= -0.05 ? 0.0 : (p_70091_2_ > 0.0 ? (p_70091_2_ -= 0.05) : (p_70091_2_ += 0.05));
                    d2 = p_70091_2_;
                }
                while (p_70091_6_ != 0.0 && this.world.getCollisionBoxes(this, this.getEntityBoundingBox().offset(0.0, -this.stepHeight, p_70091_6_)).isEmpty()) {
                    p_70091_6_ = p_70091_6_ < 0.05 && p_70091_6_ >= -0.05 ? 0.0 : (p_70091_6_ > 0.0 ? (p_70091_6_ -= 0.05) : (p_70091_6_ += 0.05));
                    d4 = p_70091_6_;
                }
                while (p_70091_2_ != 0.0 && p_70091_6_ != 0.0 && this.world.getCollisionBoxes(this, this.getEntityBoundingBox().offset(p_70091_2_, -this.stepHeight, p_70091_6_)).isEmpty()) {
                    p_70091_2_ = p_70091_2_ < 0.05 && p_70091_2_ >= -0.05 ? 0.0 : (p_70091_2_ > 0.0 ? (p_70091_2_ -= 0.05) : (p_70091_2_ += 0.05));
                    d2 = p_70091_2_;
                    p_70091_6_ = p_70091_6_ < 0.05 && p_70091_6_ >= -0.05 ? 0.0 : (p_70091_6_ > 0.0 ? (p_70091_6_ -= 0.05) : (p_70091_6_ += 0.05));
                    d4 = p_70091_6_;
                }
            }
            List<AxisAlignedBB> list1 = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().addCoord(p_70091_2_, p_70091_4_, p_70091_6_));
            Vec3d collisionOffset = new Vec3d(0.0, 0.0, 0.0);
            Vec3d backUp = new Vec3d(this.posX, this.posY, this.posZ);
            boolean ignoreVertical = GuiIngameMenu.respawnKey && this instanceof EntityPlayerSP;
            boolean ignoreHorizontal = GuiIngameMenu.respawnKey && this instanceof EntityPlayerSP;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            if (this instanceof EntityPlayerSP) {
                boolean flag2;
                double bpMX = p_70091_2_;
                double bpMY = p_70091_4_;
                double bpMZ = p_70091_6_;
                double predictX = this.posX;
                double predictY = this.posY;
                double predictZ = this.posZ;
                if (p_70091_4_ != 0.0) {
                    int l = list1.size();
                    for (int k = 0; k < l; ++k) {
                        p_70091_4_ = list1.get(k).calculateYOffset(this.getEntityBoundingBox(), p_70091_4_);
                    }
                    predictY += p_70091_4_;
                }
                if (p_70091_2_ != 0.0) {
                    int l5 = list1.size();
                    for (int j5 = 0; j5 < l5; ++j5) {
                        p_70091_2_ = list1.get(j5).calculateXOffset(this.getEntityBoundingBox(), p_70091_2_);
                    }
                    if (p_70091_2_ != 0.0) {
                        predictX += p_70091_2_;
                    }
                }
                if (p_70091_6_ != 0.0) {
                    int i6 = list1.size();
                    for (int k5 = 0; k5 < i6; ++k5) {
                        p_70091_6_ = list1.get(k5).calculateZOffset(this.getEntityBoundingBox(), p_70091_6_);
                    }
                    if (p_70091_6_ != 0.0) {
                        predictZ += p_70091_6_;
                    }
                }
                boolean predictGround = false;
                boolean bl = flag2 = this.onGround || d3 != p_70091_4_ && d3 < 0.0;
                if (this.stepHeight > 0.0f && flag2 && (d2 != p_70091_2_ || d4 != p_70091_6_)) {
                    double d14 = p_70091_2_;
                    double d6 = p_70091_4_;
                    double d7 = p_70091_6_;
                    if (!step.isCancelled() || !(this instanceof EntityPlayerSP)) {
                        p_70091_4_ = step.getStepHeight();
                    }
                    if (this instanceof EntityPlayerSP && p_70091_4_ == (double)0.6f) {
                        MoveHelper.stairTick = true;
                    }
                    List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().addCoord(d2, p_70091_4_, d4));
                    AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox();
                    AxisAlignedBB axisalignedbb3 = axisalignedbb2.addCoord(d2, 0.0, d4);
                    double d8 = p_70091_4_;
                    int k1 = list.size();
                    for (int j1 = 0; j1 < k1; ++j1) {
                        d8 = list.get(j1).calculateYOffset(axisalignedbb3, d8);
                    }
                    axisalignedbb2 = axisalignedbb2.offset(0.0, d8, 0.0);
                    double d18 = d2;
                    int i2 = list.size();
                    for (int l1 = 0; l1 < i2; ++l1) {
                        d18 = list.get(l1).calculateXOffset(axisalignedbb2, d18);
                    }
                    axisalignedbb2 = axisalignedbb2.offset(d18, 0.0, 0.0);
                    double d19 = d4;
                    int k2 = list.size();
                    for (int j2 = 0; j2 < k2; ++j2) {
                        d19 = list.get(j2).calculateZOffset(axisalignedbb2, d19);
                    }
                    axisalignedbb2 = axisalignedbb2.offset(0.0, 0.0, d19);
                    AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
                    double d20 = p_70091_4_;
                    int i3 = list.size();
                    for (int l2 = 0; l2 < i3; ++l2) {
                        d20 = list.get(l2).calculateYOffset(axisalignedbb4, d20);
                    }
                    axisalignedbb4 = axisalignedbb4.offset(0.0, d20, 0.0);
                    double d21 = d2;
                    int k3 = list.size();
                    for (int j3 = 0; j3 < k3; ++j3) {
                        d21 = list.get(j3).calculateXOffset(axisalignedbb4, d21);
                    }
                    axisalignedbb4 = axisalignedbb4.offset(d21, 0.0, 0.0);
                    double d22 = d4;
                    int i4 = list.size();
                    for (int l3 = 0; l3 < i4; ++l3) {
                        d22 = list.get(l3).calculateZOffset(axisalignedbb4, d22);
                    }
                    axisalignedbb4 = axisalignedbb4.offset(0.0, 0.0, d22);
                    double d23 = d18 * d18 + d19 * d19;
                    double d9 = d21 * d21 + d22 * d22;
                    if (d23 > d9) {
                        p_70091_2_ = d18;
                        p_70091_6_ = d19;
                        p_70091_4_ = -d8;
                    } else {
                        p_70091_2_ = d21;
                        p_70091_6_ = d22;
                        p_70091_4_ = -d20;
                    }
                    int k4 = list.size();
                    for (int j4 = 0; j4 < k4; ++j4) {
                        p_70091_4_ = list.get(j4).calculateYOffset(this.getEntityBoundingBox(), p_70091_4_);
                    }
                    if (d14 * d14 + d7 * d7 >= p_70091_2_ * p_70091_2_ + p_70091_6_ * p_70091_6_) {
                        p_70091_2_ = d14;
                        p_70091_4_ = d6;
                        p_70091_6_ = d7;
                    }
                }
                boolean isCollidedHorizontally = d2 != p_70091_2_ || d4 != p_70091_6_;
                boolean isCollidedVertically = d3 != p_70091_4_;
                predictGround = isCollidedVertically && d3 < 0.0;
                p_70091_2_ = bpMX;
                p_70091_4_ = bpMY;
                p_70091_6_ = bpMZ;
                EventMove2 move = new EventMove2(new Vec3d(this.posX, this.posY, this.posZ), new Vec3d(predictX, predictY, predictZ), new Vec3d(p_70091_2_, p_70091_4_, p_70091_6_), collisionOffset, predictGround, isCollidedHorizontally, isCollidedVertically, before);
                move.call();
                ignoreVertical = (move.isIgnoreVertical() || GuiIngameMenu.respawnKey) && this instanceof EntityPlayerSP;
                boolean bl2 = ignoreHorizontal = (move.isIgnoreHorizontal() || GuiIngameMenu.respawnKey) && this instanceof EntityPlayerSP;
                if (move.motion().xCoord != 0.0) {
                    p_70091_2_ = move.motion().xCoord;
                }
                p_70091_4_ = move.motion().yCoord;
                if (move.motion().zCoord != 0.0) {
                    p_70091_6_ = move.motion().zCoord;
                }
                d2 = p_70091_2_;
                d3 = p_70091_4_;
                d4 = p_70091_6_;
            }
            list1 = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().addCoord(p_70091_2_, p_70091_4_, p_70091_6_));
            if (this instanceof EntityPlayerSP && !(bb = this.world.getCollisionBoxes2(this, this.getEntityBoundingBox().addCoord(p_70091_2_, p_70091_4_, p_70091_6_).offset(collisionOffset.xCoord, collisionOffset.yCoord, collisionOffset.zCoord))).isEmpty() && collisionOffset.lengthVector() > 0.0) {
                for (AxisAlignedBB aabb : bb) {
                    AxisAlignedBB aabb2 = this.getEntityBoundingBox().offset(collisionOffset.xCoord, collisionOffset.yCoord, collisionOffset.zCoord);
                    if (p_70091_2_ != 0.0) {
                        int l5 = bb.size();
                        for (int j5 = 0; j5 < l5; ++j5) {
                            p_70091_2_ = bb.get(j5).calculateXOffset(aabb2, p_70091_2_);
                        }
                        if (p_70091_2_ != 0.0) {
                            aabb2 = aabb2.offset(p_70091_2_, 0.0, 0.0);
                        }
                        d2 = p_70091_2_;
                    }
                    if (p_70091_6_ == 0.0) continue;
                    int i6 = bb.size();
                    for (int k5 = 0; k5 < i6; ++k5) {
                        p_70091_6_ = bb.get(k5).calculateZOffset(aabb2, p_70091_6_);
                    }
                    if (p_70091_6_ != 0.0) {
                        aabb2 = aabb2.offset(0.0, 0.0, p_70091_6_);
                    }
                    d4 = p_70091_6_;
                }
            }
            if (p_70091_4_ != 0.0) {
                if (!ignoreVertical) {
                    int l = list1.size();
                    for (int k = 0; k < l; ++k) {
                        p_70091_4_ = list1.get(k).calculateYOffset(this.getEntityBoundingBox(), p_70091_4_);
                    }
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, p_70091_4_, 0.0));
            }
            if (p_70091_2_ != 0.0) {
                if (!ignoreHorizontal) {
                    int l5 = list1.size();
                    for (int j5 = 0; j5 < l5; ++j5) {
                        p_70091_2_ = list1.get(j5).calculateXOffset(this.getEntityBoundingBox(), p_70091_2_);
                    }
                }
                if (p_70091_2_ != 0.0) {
                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(p_70091_2_, 0.0, 0.0));
                }
            }
            if (p_70091_6_ != 0.0) {
                if (!ignoreHorizontal) {
                    int i6 = list1.size();
                    for (int k5 = 0; k5 < i6; ++k5) {
                        p_70091_6_ = list1.get(k5).calculateZOffset(this.getEntityBoundingBox(), p_70091_6_);
                    }
                }
                if (p_70091_6_ != 0.0) {
                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, p_70091_6_));
                }
            }
            boolean bl = flag = this.onGround || d3 != p_70091_4_ && d3 < 0.0;
            if (!(!flag || d2 == p_70091_2_ && d4 == p_70091_6_ || ignoreHorizontal || ignoreVertical)) {
                double d14 = p_70091_2_;
                double d6 = p_70091_4_;
                double d7 = p_70091_6_;
                AxisAlignedBB axisalignedbb1 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(axisalignedbb);
                p_70091_4_ = this.stepHeight;
                List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().addCoord(d2, p_70091_4_, d4));
                AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox();
                AxisAlignedBB axisalignedbb3 = axisalignedbb2.addCoord(d2, 0.0, d4);
                double d8 = p_70091_4_;
                int k1 = list.size();
                for (int j1 = 0; j1 < k1; ++j1) {
                    d8 = list.get(j1).calculateYOffset(axisalignedbb3, d8);
                }
                axisalignedbb2 = axisalignedbb2.offset(0.0, d8, 0.0);
                double d18 = d2;
                int i2 = list.size();
                for (int l1 = 0; l1 < i2; ++l1) {
                    d18 = list.get(l1).calculateXOffset(axisalignedbb2, d18);
                }
                axisalignedbb2 = axisalignedbb2.offset(d18, 0.0, 0.0);
                double d19 = d4;
                int k2 = list.size();
                for (int j2 = 0; j2 < k2; ++j2) {
                    d19 = list.get(j2).calculateZOffset(axisalignedbb2, d19);
                }
                axisalignedbb2 = axisalignedbb2.offset(0.0, 0.0, d19);
                AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
                double d20 = p_70091_4_;
                int i3 = list.size();
                for (int l2 = 0; l2 < i3; ++l2) {
                    d20 = list.get(l2).calculateYOffset(axisalignedbb4, d20);
                }
                axisalignedbb4 = axisalignedbb4.offset(0.0, d20, 0.0);
                double d21 = d2;
                int k3 = list.size();
                for (int j3 = 0; j3 < k3; ++j3) {
                    d21 = list.get(j3).calculateXOffset(axisalignedbb4, d21);
                }
                axisalignedbb4 = axisalignedbb4.offset(d21, 0.0, 0.0);
                double d22 = d4;
                int i4 = list.size();
                for (int l3 = 0; l3 < i4; ++l3) {
                    d22 = list.get(l3).calculateZOffset(axisalignedbb4, d22);
                }
                axisalignedbb4 = axisalignedbb4.offset(0.0, 0.0, d22);
                double d23 = d18 * d18 + d19 * d19;
                double d9 = d21 * d21 + d22 * d22;
                if (d23 > d9) {
                    p_70091_2_ = d18;
                    p_70091_6_ = d19;
                    p_70091_4_ = -d8;
                    this.setEntityBoundingBox(axisalignedbb2);
                } else {
                    p_70091_2_ = d21;
                    p_70091_6_ = d22;
                    p_70091_4_ = -d20;
                    this.setEntityBoundingBox(axisalignedbb4);
                }
                int k4 = list.size();
                for (int j4 = 0; j4 < k4; ++j4) {
                    p_70091_4_ = list.get(j4).calculateYOffset(this.getEntityBoundingBox(), p_70091_4_);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, p_70091_4_, 0.0));
                if (d14 * d14 + d7 * d7 >= p_70091_2_ * p_70091_2_ + p_70091_6_ * p_70091_6_) {
                    p_70091_2_ = d14;
                    p_70091_4_ = d6;
                    p_70091_6_ = d7;
                    this.setEntityBoundingBox(axisalignedbb1);
                }
            }
            this.world.theProfiler.endSection();
            this.world.theProfiler.startSection("rest");
            this.resetPositionToBB();
            this.isCollidedHorizontally = d2 != p_70091_2_ || d4 != p_70091_6_;
            this.isCollidedVertically = d3 != p_70091_4_;
            this.onGround = this.isCollidedVertically && d3 < 0.0;
            boolean bl3 = this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
            if (this instanceof EntityPlayerSP) {
                double deltaX = this.posX - preX;
                double deltaZ = this.posZ - preZ;
                EventPostMove ev = new EventPostMove(Math.sqrt(deltaX * deltaX + deltaZ * deltaZ));
                ev.call();
            }
            if ((iblockstate = this.world.getBlockState(blockpos = new BlockPos(j6 = MathHelper.floor(this.posX), i1 = MathHelper.floor(this.posY - (double)0.2f), k6 = MathHelper.floor(this.posZ)))).getMaterial() == Material.AIR && ((block1 = (iblockstate1 = this.world.getBlockState(blockpos1 = blockpos.down())).getBlock()) instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate)) {
                iblockstate = iblockstate1;
                blockpos = blockpos1;
            }
            this.updateFallState(p_70091_4_, this.onGround, iblockstate, blockpos);
            if (d2 != p_70091_2_) {
                this.motionX = 0.0;
            }
            if (d4 != p_70091_6_) {
                this.motionZ = 0.0;
            }
            Block block = iblockstate.getBlock();
            if (d3 != p_70091_4_) {
                block.onLanded(this.world, this);
            }
            if (!(!this.canTriggerWalking() || this.onGround && this.isSneaking() && this instanceof EntityPlayer || this.isRiding())) {
                double d15 = this.posX - d10;
                double d16 = this.posY - d11;
                double d17 = this.posZ - d1;
                if (block != Blocks.LADDER) {
                    d16 = 0.0;
                }
                if (block != null && this.onGround) {
                    block.onEntityWalk(this.world, blockpos, this);
                }
                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt(d15 * d15 + d17 * d17) * 0.6);
                this.distanceWalkedOnStepModified = (float)((double)this.distanceWalkedOnStepModified + (double)MathHelper.sqrt(d15 * d15 + d16 * d16 + d17 * d17) * 0.6);
                if (this.distanceWalkedOnStepModified > (float)this.nextStepDistance && iblockstate.getMaterial() != Material.AIR) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        Entity entity = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
                        float f = entity == this ? 0.35f : 0.4f;
                        float f1 = MathHelper.sqrt(entity.motionX * entity.motionX * (double)0.2f + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * (double)0.2f) * f;
                        if (f1 > 1.0f) {
                            f1 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), f1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    } else {
                        this.playStepSound(blockpos, block);
                    }
                } else if (this.distanceWalkedOnStepModified > this.field_191959_ay && this.func_191957_ae() && iblockstate.getMaterial() == Material.AIR) {
                    this.field_191959_ay = this.func_191954_d(this.distanceWalkedOnStepModified);
                }
            }
            try {
                this.doBlockCollisions();
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(crashreportcategory);
                throw new ReportedException(crashreport);
            }
            boolean flag1 = this.isWet();
            if (this.world.isFlammableWithin(this.getEntityBoundingBox().contract(0.001))) {
                this.dealFireDamage(1);
                if (!flag1) {
                    ++this.field_190534_ay;
                    if (this.field_190534_ay == 0) {
                        this.setFire(8);
                    }
                }
            } else if (this.field_190534_ay <= 0) {
                this.field_190534_ay = -this.func_190531_bD();
            }
            if (flag1 && this.isBurning()) {
                this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.field_190534_ay = -this.func_190531_bD();
            }
            this.world.theProfiler.endSection();
        }
    }

    public void resetPositionToBB() {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        this.posX = (axisalignedbb.minX + axisalignedbb.maxX) / 2.0;
        this.posY = axisalignedbb.minY;
        this.posZ = (axisalignedbb.minZ + axisalignedbb.maxZ) / 2.0;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_GENERIC_SWIM;
    }

    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_GENERIC_SPLASH;
    }

    protected void doBlockCollisions() {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001, axisalignedbb.minY + 0.001, axisalignedbb.minZ + 0.001);
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001, axisalignedbb.maxY - 0.001, axisalignedbb.maxZ - 0.001);
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain();
        if (this.world.isAreaLoaded((BlockPos)blockpos$pooledmutableblockpos, blockpos$pooledmutableblockpos1)) {
            for (int i = blockpos$pooledmutableblockpos.getX(); i <= blockpos$pooledmutableblockpos1.getX(); ++i) {
                for (int j = blockpos$pooledmutableblockpos.getY(); j <= blockpos$pooledmutableblockpos1.getY(); ++j) {
                    for (int k = blockpos$pooledmutableblockpos.getZ(); k <= blockpos$pooledmutableblockpos1.getZ(); ++k) {
                        blockpos$pooledmutableblockpos2.setPos(i, j, k);
                        IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos2);
                        try {
                            iblockstate.getBlock().onEntityCollidedWithBlock(this.world, blockpos$pooledmutableblockpos2, iblockstate, this);
                            this.func_191955_a(iblockstate);
                            continue;
                        } catch (Throwable throwable) {
                            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
                            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
                            CrashReportCategory.addBlockInfo(crashreportcategory, blockpos$pooledmutableblockpos2, iblockstate);
                            throw new ReportedException(crashreport);
                        }
                    }
                }
            }
        }
        blockpos$pooledmutableblockpos.release();
        blockpos$pooledmutableblockpos1.release();
        blockpos$pooledmutableblockpos2.release();
    }

    protected void func_191955_a(IBlockState p_191955_1_) {
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        SoundType soundtype = blockIn.getSoundType();
        if (this.world.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER) {
            soundtype = Blocks.SNOW_LAYER.getSoundType();
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15f, soundtype.getPitch());
        } else if (!blockIn.getDefaultState().getMaterial().isLiquid()) {
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15f, soundtype.getPitch());
        }
    }

    protected float func_191954_d(float p_191954_1_) {
        return 0.0f;
    }

    protected boolean func_191957_ae() {
        return false;
    }

    public void playSound(SoundEvent soundIn, float volume, float pitch) {
        if (!this.isSilent()) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, soundIn, this.getSoundCategory(), volume, pitch);
        }
    }

    public boolean isSilent() {
        return this.dataManager.get(SILENT);
    }

    public void setSilent(boolean isSilent) {
        this.dataManager.set(SILENT, isSilent);
    }

    public boolean hasNoGravity() {
        return this.dataManager.get(NO_GRAVITY);
    }

    public void setNoGravity(boolean noGravity) {
        this.dataManager.set(NO_GRAVITY, noGravity);
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
        if (onGroundIn) {
            if (this.fallDistance > 0.0f) {
                state.getBlock().onFallenUpon(this.world, pos, this, this.fallDistance);
            }
            this.fallDistance = 0.0f;
        } else if (y < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - y);
        }
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }

    protected void dealFireDamage(int amount) {
        if (!this.isImmuneToFire) {
            if (this instanceof EntityPlayerSP) {
                EntityLivingBase.isSunRiseDamaged = false;
                EntityLivingBase.isMatrixDamaged = false;
                EntityPlayerSP.fallTickers.reset();
            }
            this.attackEntityFrom(DamageSource.inFire, amount);
        }
    }

    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }

    public void fall(float distance, float damageMultiplier) {
        if (this.isBeingRidden()) {
            for (Entity entity : this.getPassengers()) {
                entity.fall(distance, damageMultiplier);
            }
        }
    }

    public boolean isWet() {
        if (this.inWater) {
            return true;
        }
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.posY, this.posZ);
        if (!this.world.isRainingAt(blockpos$pooledmutableblockpos) && !this.world.isRainingAt(blockpos$pooledmutableblockpos.setPos(this.posX, this.posY + (double)this.height, this.posZ))) {
            blockpos$pooledmutableblockpos.release();
            return false;
        }
        blockpos$pooledmutableblockpos.release();
        return true;
    }

    public boolean isInWater() {
        return this.inWater;
    }

    public boolean func_191953_am() {
        return this.world.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -20.0, 0.0).contract(0.001), Material.WATER, this);
    }

    public boolean handleWaterMovement() {
        if (this.getRidingEntity() instanceof EntityBoat) {
            this.inWater = false;
        } else if (this.world.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -0.4f, 0.0).contract(0.001), Material.WATER, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.extinguish();
        } else {
            this.inWater = false;
        }
        return this.inWater;
    }

    protected void resetHeight() {
        Entity entity = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
        float f = entity == this ? 0.2f : 0.9f;
        float f1 = MathHelper.sqrt(entity.motionX * entity.motionX * (double)0.2f + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * (double)0.2f) * f;
        if (f1 > 1.0f) {
            f1 = 1.0f;
        }
        this.playSound(this.getSplashSound(), f1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
        float f2 = MathHelper.floor(this.getEntityBoundingBox().minY);
        int i = 0;
        while ((float)i < 1.0f + this.width * 20.0f) {
            float f3 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
            float f4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
            this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (double)f3, (double)(f2 + 1.0f), this.posZ + (double)f4, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2f), this.motionZ, new int[0]);
            ++i;
        }
        int j = 0;
        while ((float)j < 1.0f + this.width * 20.0f) {
            float f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
            float f6 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
            this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (double)f5, (double)(f2 + 1.0f), this.posZ + (double)f6, this.motionX, this.motionY, this.motionZ, new int[0]);
            ++j;
        }
    }

    public void spawnRunningParticles() {
        if (this.isSprinting() && !this.isInWater()) {
            this.createRunningParticles();
        }
    }

    protected void createRunningParticles() {
        int k;
        int j;
        int i = MathHelper.floor(this.posX);
        BlockPos blockpos = new BlockPos(i, j = MathHelper.floor(this.posY - (double)0.2f), k = MathHelper.floor(this.posZ));
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ((double)this.rand.nextFloat() - 0.5) * (double)this.width, this.getEntityBoundingBox().minY + 0.1, this.posZ + ((double)this.rand.nextFloat() - 0.5) * (double)this.width, -this.motionX * 4.0, 1.5, -this.motionZ * 4.0, Block.getStateId(iblockstate));
        }
    }

    public boolean isInsideOfMaterial(Material materialIn) {
        if (this.getRidingEntity() instanceof EntityBoat) {
            return false;
        }
        double d0 = this.posY + (double)this.getEyeHeight();
        BlockPos blockpos = new BlockPos(this.posX, d0, this.posZ);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        if (iblockstate.getMaterial() == materialIn) {
            float f = BlockLiquid.getLiquidHeightPercent(iblockstate.getBlock().getMetaFromState(iblockstate)) - 0.11111111f;
            float f1 = (float)(blockpos.getY() + 1) - f;
            boolean flag = d0 < (double)f1;
            return (flag || !(this instanceof EntityPlayer)) && flag;
        }
        return false;
    }

    public boolean isInLava() {
        return this.world.isMaterialInBB(this.getEntityBoundingBox().expand(-0.1f, -0.4f, -0.1f), Material.LAVA);
    }

    public EntityLivingBase getLivingBaseOf() {
        return this instanceof EntityLivingBase ? (EntityLivingBase)this : null;
    }

    public EntityPlayer getPlayerOf() {
        return this instanceof EntityPlayer ? (EntityPlayer)this : null;
    }

    public EntityOtherPlayerMP getOtherPlayerOf() {
        return this instanceof EntityOtherPlayerMP ? (EntityOtherPlayerMP)this : null;
    }

    public void func_191958_b(float strafe, float up, float forward, float friction) {
        float frict;
        EventRotationStrafe eventStrafe = new EventRotationStrafe(this.rotationYaw, strafe, forward, friction);
        if (this instanceof EntityPlayerSP) {
            eventStrafe.call();
        }
        if (eventStrafe.isCancelled()) {
            return;
        }
        float f = this instanceof EntityPlayerSP ? eventStrafe.getStrafe() * eventStrafe.getStrafe() + up * up + eventStrafe.getForward() * eventStrafe.getForward() : strafe * strafe + up * up + forward * forward;
        float f2 = frict = this instanceof EntityPlayerSP ? eventStrafe.getFriction() : friction;
        if (f >= 1.0E-4f) {
            float f22;
            float f1;
            if ((f = MathHelper.sqrt(f)) < 1.0f) {
                f = 1.0f;
            }
            f = frict / f;
            strafe *= f;
            up *= f;
            forward *= f;
            if (this instanceof EntityPlayerSP) {
                f1 = MathHelper.sin(eventStrafe.getYaw() * (float)Math.PI / 180.0f);
                f22 = MathHelper.cos(eventStrafe.getYaw() * (float)Math.PI / 180.0f);
            } else {
                f1 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0f);
                f22 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0f);
            }
            if (this instanceof EntityPlayerSP && eventStrafe.isCancelled()) {
                this.motionX += (double)(eventStrafe.getStrafe() * f22 - eventStrafe.getForward() * f1);
                this.motionY += (double)up;
                this.motionZ += (double)(eventStrafe.getForward() * f22 + eventStrafe.getStrafe() * f1);
            } else {
                this.motionX += (double)(strafe * f22 - forward * f1);
                this.motionY += (double)up;
                this.motionZ += (double)(forward * f22 + strafe * f1);
            }
        }
    }

    public int getBrightnessForRender(float partialTicks) {
        BlockPos blockpos = new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
        return this.world.isBlockLoaded(blockpos) ? this.world.getCombinedLight(blockpos, 0) : 0;
    }

    public int getBrightnessForRender() {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        if (this.world.isBlockLoaded(blockpos$mutableblockpos)) {
            blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double)this.getEyeHeight()));
            return this.world.getCombinedLight(blockpos$mutableblockpos, 0);
        }
        return 0;
    }

    public float getBrightness() {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        if (this.world.isBlockLoaded(blockpos$mutableblockpos)) {
            blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double)this.getEyeHeight()));
            return this.world.getLightBrightness(blockpos$mutableblockpos);
        }
        return 0.0f;
    }

    public void setWorld(World worldIn) {
        this.world = worldIn;
    }

    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        this.posX = MathHelper.clamp(x, -3.0E7, 3.0E7);
        this.posY = y;
        this.posZ = MathHelper.clamp(z, -3.0E7, 3.0E7);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        double d0 = this.prevRotationYaw - yaw;
        if (d0 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (d0 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(yaw, pitch);
    }

    public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
        this.setLocationAndAngles((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, rotationYawIn, rotationPitchIn);
    }

    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public float getDistanceToEntity(Entity entityIn) {
        float f = (float)(this.posX - entityIn.posX);
        float f1 = (float)(this.posY - entityIn.posY);
        float f2 = (float)(this.posZ - entityIn.posZ);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public float getDistanceToTileEntity(TileEntity tileentityIn) {
        float f = (float)(this.posX - (double)tileentityIn.getPos().getX() + 0.5);
        float f1 = (float)(this.posY - (double)tileentityIn.getPos().getY());
        float f2 = (float)(this.posZ - (double)tileentityIn.getPos().getZ() + 0.5);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public float getSmoothDistanceToTileEntity(TileEntity tileentityIn) {
        Minecraft mc = Minecraft.getMinecraft();
        float x = (float)(this.lastTickPosX + (this.posX - this.lastTickPosX) * (double)mc.getRenderPartialTicks());
        float y = (float)(this.lastTickPosY + (this.posY - this.lastTickPosY) * (double)mc.getRenderPartialTicks());
        float z = (float)(this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * (double)mc.getRenderPartialTicks());
        float f = (float)((double)(x - (float)tileentityIn.getPos().getX()) + 0.5);
        float f1 = y - (float)tileentityIn.getPos().getY();
        float f2 = (float)((double)(z - (float)tileentityIn.getPos().getZ()) + 0.5);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public float getSmoothDistanceToEntity(Entity entityIn) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double xposme = this.lastTickPosX + (this.posX - this.lastTickPosX) * (double)pTicks;
        double yposme = this.lastTickPosY + (this.posY - this.lastTickPosY) * (double)pTicks;
        double zposme = this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * (double)pTicks;
        double xposent = 0.0;
        double yposent = 0.0;
        double zposent = 0.0;
        if (entityIn != null) {
            xposent = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)pTicks;
            yposent = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)pTicks;
            zposent = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)pTicks;
        }
        float f = (float)(xposme - xposent);
        float f1 = (float)(yposme - yposent);
        float f2 = (float)(zposme - zposent);
        return entityIn != null ? MathHelper.sqrt(f * f + f1 * f1 + f2 * f2) : 0.0f;
    }

    public float getSmoothDistanceToEntityXZ(Entity entityIn) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double xposme = this.lastTickPosX + (this.posX - this.lastTickPosX) * (double)pTicks;
        double zposme = this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * (double)pTicks;
        double xposent = 0.0;
        double yposent = 0.0;
        double zposent = 0.0;
        if (entityIn != null) {
            xposent = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)pTicks;
            zposent = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)pTicks;
        }
        float f = (float)(xposme - xposent);
        float f2 = (float)(zposme - zposent);
        return entityIn != null ? MathHelper.sqrt(f * f + f2 * f2) : 0.0f;
    }

    public float getSmoothDistanceToCoord(float x, float y, float z) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double xposme = this.lastTickPosX + (this.posX - this.lastTickPosX) * (double)pTicks;
        double yposme = this.lastTickPosY + (this.posY - this.lastTickPosY) * (double)pTicks;
        double zposme = this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * (double)pTicks;
        float f = (float)(xposme - (double)x);
        float f1 = (float)(yposme - (double)y);
        float f2 = (float)(zposme - (double)z);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public float getSmoothDistanceToCoordAtEntity(Entity entityIn, float x, float y, float z) {
        float pTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        double xposme = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)pTicks;
        double yposme = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)pTicks;
        double zposme = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)pTicks;
        float f = (float)(xposme - (double)x);
        float f1 = (float)(yposme - (double)y);
        float f2 = (float)(zposme - (double)z);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public float getPredDistanceToEntityWithCustomPos(Entity entityIn, double x, double y, double z) {
        Minecraft mc = Minecraft.getMinecraft();
        float meTicks = 1.0f;
        if (Minecraft.player.getUniqueID() != null && Minecraft.player != null && mc.world != null && Minecraft.player.ticksExisted > 200) {
            meTicks += (float)(Minecraft.getMinecraft().getConnection().getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime() / 50);
        }
        float entTicks = 1.0f;
        for (Entity entity : GuiPlayerTabOverlay.getPlayers()) {
            if (entity != entityIn || !(entityIn instanceof EntityPlayer) || entity.getUniqueID() == null || entityIn.ticksExisted <= 200 || Minecraft.getMinecraft().getConnection().getPlayerInfo(entity.getUniqueID()).getResponseTime() == 0) continue;
            entTicks += (float)(Minecraft.getMinecraft().getConnection().getPlayerInfo(entity.getUniqueID()).getResponseTime() / 50);
        }
        meTicks = MathUtils.clamp(meTicks, 1.0f, 4.0f);
        entTicks = MathUtils.clamp(entTicks, 1.0f, 4.0f);
        float pTicks = mc.getRenderPartialTicks();
        double d = Minecraft.player.lastTickPosX + (Minecraft.player.posX - Minecraft.player.lastTickPosX) * (double)pTicks;
        double yposme = Minecraft.player.lastTickPosY + (Minecraft.player.posY - Minecraft.player.lastTickPosY) * (double)pTicks;
        double zposme = Minecraft.player.lastTickPosZ + (Minecraft.player.posZ - Minecraft.player.lastTickPosZ) * (double)pTicks;
        double xposent = x;
        double yposent = y;
        double zposent = x;
        double xme = d - (this.lastTickPosX - d) * (double)meTicks;
        double yme = yposme - (this.lastTickPosY - yposme) * (double)meTicks;
        double zme = zposme - (this.lastTickPosZ - zposme) * (double)meTicks;
        double xent = xposent - (entityIn.lastTickPosX - xposent) * (double)entTicks;
        double yent = yposent - (entityIn.lastTickPosY - yposent) * (double)entTicks;
        double zent = zposent - (entityIn.lastTickPosZ - zposent) * (double)entTicks;
        float f = (float)(xme - xent);
        float f1 = (float)(yme - yent);
        float f2 = (float)(zme - zent);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public double getSmartDistanceToAABB(float[] rotate, Entity entityIn) {
        boolean virtualCamera = FreeCam.get.actived && FreeCam.fakePlayer != null;
        return MathUtils.getDistanceToPointedEntity(new Vector2f(rotate[0], rotate[1]), virtualCamera ? FreeCam.fakePlayer : Minecraft.player, entityIn);
    }

    public double getDistanceToVec3d(Vec3d vec3) {
        return this.getDistanceAtEye(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public double getDistanceToBlockPos(BlockPos pos) {
        Vec3d vec3 = new Vec3d(pos.add(0.5, 0.5, 0.5).getX(), pos.add(0.5, 0.5, 0.5).getY(), pos.add(0.5, 0.5, 0.5).getZ());
        return this.getDistanceAtEye(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public double getDistanceToVec3dXZ(Vec3d vec3) {
        return this.getDistanceAtEyeXZ(vec3.xCoord, vec3.zCoord);
    }

    public boolean canEntityBeSeenCoords(double x, double y, double z) {
        return Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), new Vec3d(x, y, z), false, true, false) == null;
    }

    public boolean canEntityBeSeenVec3d(Vec3d vec3) {
        return this.canEntityBeSeenCoords(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    private boolean localSeen(EntityPlayer selfEntity, double[] xyz, float scale) {
        return scale == 0.0f ? selfEntity.canEntityBeSeenCoords(xyz[0], xyz[1], xyz[2]) : selfEntity.canEntityBeSeenCoords(xyz[0], xyz[1], xyz[2]) && selfEntity.canEntityBeSeenCoords(xyz[0], xyz[1] + (double)scale, xyz[2]) && selfEntity.canEntityBeSeenCoords(xyz[0], xyz[1] - (double)scale, xyz[2]) && selfEntity.canEntityBeSeenCoords(xyz[0] + (double)scale, xyz[1], xyz[2]) && selfEntity.canEntityBeSeenCoords(xyz[0] - (double)scale, xyz[1], xyz[2]) && selfEntity.canEntityBeSeenCoords(xyz[0], xyz[1], xyz[2] + (double)scale) && selfEntity.canEntityBeSeenCoords(xyz[0], xyz[1], xyz[2] - (double)scale);
    }

    public List<Vec3d> entityBoxVec3dsAlternate(Entity entityIn, boolean addOnSeenNoDontSeen) {
        EntityPlayerSP me;
        if (entityIn == null) {
            return null;
        }
        ArrayList<Vec3d> vecs = new ArrayList<Vec3d>();
        double accuracyXZ = 5.0;
        double accuracyY = 10.0;
        double offsetXYZ = 0.03f;
        accuracyXZ = 1.0 / accuracyXZ;
        accuracyY = 1.0 / accuracyY;
        AxisAlignedBB aabb = entityIn.getRenderBoundingBox();
        double[] whh = new double[]{(double)this.width - offsetXYZ * 2.0, (double)this.height - offsetXYZ * 2.0, ((double)this.height - offsetXYZ * 2.0) / 1.05};
        double[] xyz = new double[]{this.posX, this.posY, this.posZ};
        double[] xyz1 = new double[]{xyz[0] - whh[0] / 2.0, xyz[1], xyz[2] - whh[0] / 2.0};
        double[] xyz2 = new double[]{xyz[0] + whh[0] / 2.0, xyz[1] + whh[1], xyz[2] + whh[0] / 2.0};
        if (aabb != null) {
            aabb = aabb.expandXyz(-offsetXYZ);
            whh = new double[]{aabb.maxX - aabb.minX, aabb.maxY - aabb.minY, (aabb.maxY - aabb.minY) / 1.05};
            xyz = new double[]{aabb.minX + whh[0] / 2.0, aabb.minY, aabb.minZ + whh[0] / 2.0};
            xyz1 = new double[]{aabb.minX, aabb.minY, aabb.minZ};
            xyz2 = new double[]{aabb.maxX, aabb.maxY, aabb.maxZ};
        }
        if ((me = Minecraft.player) == null || aabb == null) {
            return null;
        }
        float scale = 0.0f;
        for (double xs = xyz1[0]; xs <= xyz2[0]; xs += accuracyXZ * (whh[0] * 2.0)) {
            for (double zs = xyz1[2]; zs <= xyz2[2]; zs += accuracyXZ * (whh[0] * 2.0)) {
                for (double ys = xyz2[1]; ys > xyz1[1]; ys -= accuracyY * (whh[1] * 2.0)) {
                    double[] xyz3;
                    if (xs != xyz2[0] && xs != xyz1[0] && ys != xyz2[1] && ys != xyz1[1] && zs != xyz2[2] && zs != xyz1[2] || !this.localSeen(me, xyz3 = new double[]{xs, ys, zs}, scale)) continue;
                    vecs.add(new Vec3d(xyz3[0], xyz3[1], xyz3[2]));
                }
            }
        }
        return vecs;
    }

    public double getDistanceAtVec3dToVec3d(Vec3d first, Vec3d second) {
        double xDiff = first.xCoord - second.xCoord;
        double yDiff = first.yCoord - second.yCoord;
        double zDiff = first.zCoord - second.zCoord;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public Vec3d getBestVec3dOnEntityBox() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB aabb = this.getEntityBoundingBox();
        double[] whh = new double[]{this.width, this.height, this.height / 1.05f};
        double[] xyz = new double[]{this.posX, this.posY, this.posZ};
        double[] xyz1 = new double[]{xyz[0] - whh[0] / 2.0, xyz[1], xyz[2] - whh[0] / 2.0};
        double[] xyz2 = new double[]{xyz[0] + whh[0] / 2.0, xyz[1] + whh[1], xyz[2] + whh[0] / 2.0};
        if (aabb != null) {
            whh = new double[]{aabb.maxX - aabb.minX, aabb.maxY - aabb.minY, (aabb.maxY - aabb.minY) / (double)1.05f};
            xyz = new double[]{aabb.minX + whh[0] / 2.0, aabb.minY, aabb.minZ + whh[0] / 2.0};
            xyz1 = new double[]{aabb.minX, aabb.minY, aabb.minZ};
            xyz2 = new double[]{aabb.maxX, aabb.maxY, aabb.maxZ};
        }
        double[] diffs = new double[]{Minecraft.player.posY - xyz[1], Minecraft.player.getDistanceXZ(xyz[0], xyz[2])};
        double ddtn = MathUtils.clamp(diffs[1] / (2.9 + whh[0] / 2.0), 0.4, 0.95);
        double pca = MathUtils.clamp(ddtn * ddtn, 0.0, 1.0);
        double pitchPointHeight = MathUtils.clamp(whh[2] / 2.0 * pca + whh[2] / 2.0 * MathUtils.clamp(diffs[0] + pca, 0.0, 1.0), 0.0, whh[2]);
        Vec3d defaultVec = new Vec3d(xyz[0], xyz[1] + pitchPointHeight, xyz[2]);
        if (whh[1] <= 1.0 || Minecraft.player.canEntityBeSeenVec3d(defaultVec)) {
            return defaultVec;
        }
        List<Vec3d> normalVecs = this.entityBoxVec3dsAlternate(this, true);
        Vec3d toSortVec = defaultVec;
        if (normalVecs != null && normalVecs.size() > 1) {
            normalVecs.sort(Comparator.comparing(vec3 -> this.getDistanceAtVec3dToVec3d(toSortVec, (Vec3d)vec3)));
        }
        return normalVecs != null && normalVecs.size() > 0 ? normalVecs.get(0) : defaultVec;
    }

    public double getDistanceToPointedAABB(Entity entityIn) {
        return this.getBestVec3dOnEntityBox() != null ? this.getDistanceToVec3d(entityIn.getBestVec3dOnEntityBox()) : this.getSmartDistanceToAABB(RotationUtil.getLookRotations(entityIn, false), entityIn);
    }

    public double getDistanceSq(double x, double y, double z) {
        double d0 = this.posX - x;
        double d1 = this.posY - y;
        double d2 = this.posZ - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double getDistanceSq(BlockPos pos) {
        return pos.distanceSq(this.posX, this.posY, this.posZ);
    }

    public double getDistanceSqToCenter(BlockPos pos) {
        return pos.distanceSqToCenter(this.posX, this.posY, this.posZ);
    }

    public static BlockPos getBlockLocation(Block blocks, int range) {
        BlockPos block = null;
        int bestRange = range;
        for (int x = -range; x < range; ++x) {
            for (int y = range; y > -range; --y) {
                for (int z = -range; z < range; ++z) {
                    int rangeFromBlockToPlayer;
                    BlockPos blockPos = new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ).add(x, y, z);
                    if (Minecraft.getMinecraft().world.getBlockState(blockPos).getBlock() != blocks || (rangeFromBlockToPlayer = (int)blockPos.getDistance((int)Minecraft.player.posX, (int)Minecraft.player.posY, (int)Minecraft.player.posZ)) >= bestRange) continue;
                    bestRange = rangeFromBlockToPlayer;
                    block = blockPos;
                }
            }
        }
        return block;
    }

    public double getDistance(double x, double y, double z) {
        double d0 = this.posX - x;
        double d1 = this.posY - y;
        double d2 = this.posZ - z;
        return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double getDistanceAtEye(double x, double y, double z) {
        double d0 = this.posX - x;
        double d1 = this.posY + (double)this.getEyeHeight() - y;
        double d2 = this.posZ - z;
        return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double getDistanceAtEye(double x, double y, double z, float eyePC) {
        double d0 = this.posX - x;
        double d1 = this.posY + (double)(this.getEyeHeight() * eyePC) - y;
        double d2 = this.posZ - z;
        return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double getDistanceAtEyeXZ(double x, double z) {
        double d0 = this.posX - x;
        double d2 = this.posZ - z;
        return MathHelper.sqrt(d0 * d0 + d2 * d2);
    }

    public double getDistanceXZ(double x, double z) {
        double d0 = this.posX - x;
        double d1 = this.posZ - z;
        return MathHelper.sqrt(d0 * d0 + d1 * d1);
    }

    public double getDistanceY(double y) {
        double d0 = this.posY - y;
        return MathHelper.sqrt(d0 * d0);
    }

    public double getDistanceSqToEntity(Entity entityIn) {
        double d0 = this.posX - entityIn.posX;
        double d1 = this.posY - entityIn.posY;
        double d2 = this.posZ - entityIn.posZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public void onCollideWithPlayer(EntityPlayer entityIn) {
    }

    public void applyEntityCollision(Entity entityIn) {
        double d1;
        double d0;
        double d2;
        if (!this.isRidingSameEntity(entityIn) && !entityIn.noClip && !this.noClip && (d2 = MathHelper.absMax(d0 = entityIn.posX - this.posX, d1 = entityIn.posZ - this.posZ)) >= (double)0.01f) {
            d2 = MathHelper.sqrt(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
                d3 = 1.0;
            }
            d0 *= d3;
            d1 *= d3;
            d0 *= (double)0.05f;
            d1 *= (double)0.05f;
            d0 *= (double)(1.0f - this.entityCollisionReduction);
            d1 *= (double)(1.0f - this.entityCollisionReduction);
            if (!this.isBeingRidden()) {
                this.addVelocity(-d0, 0.0, -d1);
            }
            if (!entityIn.isBeingRidden()) {
                entityIn.addVelocity(d0, 0.0, d1);
            }
        }
    }

    public void addVelocity(double x, double y, double z) {
        this.motionX += x;
        this.motionY += y;
        this.motionZ += z;
        this.isAirBorne = true;
    }

    public void setBeenAttacked() {
        this.velocityChanged = true;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        this.setBeenAttacked();
        return false;
    }

    public Vec3d getLook(float partialTicks) {
        if (partialTicks == 1.0f) {
            return Entity.getVectorForRotation(this.rotationPitch, this.rotationYaw);
        }
        float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
        float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;
        return Entity.getVectorForRotation(f, f1);
    }

    public static final Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * ((float)Math.PI / 180));
        float f3 = MathHelper.sin(-pitch * ((float)Math.PI / 180));
        return new Vec3d(f1 * f2, f3, f * f2);
    }

    public Vec3d getPositionEyes(float partialTicks) {
        if (partialTicks == 1.0f) {
            return new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
        }
        double d0 = this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks;
        double d1 = this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks + (double)this.getEyeHeight();
        double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks;
        return new Vec3d(d0, d1, d2);
    }

    @Nullable
    public RayTraceResult rayTrace(double blockReachDistance, float partialTicks) {
        Vec3d vec3d = this.getPositionEyes(partialTicks);
        Vec3d vec3d1 = this.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * blockReachDistance, vec3d1.yCoord * blockReachDistance, vec3d1.zCoord * blockReachDistance);
        double reach = blockReachDistance;
        if (EntityBox.hitboxModState()) {
            reach += (double)EntityBox.hitboxModReachBlocks();
        }
        if (ClickTeleport.get != null && ClickTeleport.get.actived) {
            reach = 100.0;
        }
        if (reach != blockReachDistance) {
            vec3d2 = vec3d.addVector(vec3d1.xCoord * reach, vec3d1.yCoord * reach, vec3d1.zCoord * reach);
        }
        return this.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean canBePushed() {
        return false;
    }

    public void func_191956_a(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
        if (p_191956_1_ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192123_c.func_192211_a((EntityPlayerMP)p_191956_1_, this, p_191956_3_);
        }
    }

    public boolean isInRangeToRender3d(double x, double y, double z) {
        double d0 = this.posX - x;
        double d1 = this.posY - y;
        double d2 = this.posZ - z;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        return this.isInRangeToRenderDist(d3);
    }

    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength();
        if (Double.isNaN(d0)) {
            d0 = 1.0;
        }
        return distance < (d0 = d0 * 64.0 * renderDistanceWeight) * d0;
    }

    public boolean writeToNBTAtomically(NBTTagCompound compound) {
        String s = this.getEntityString();
        if (!this.isDead && s != null) {
            compound.setString("id", s);
            this.writeToNBT(compound);
            return true;
        }
        return false;
    }

    public boolean writeToNBTOptional(NBTTagCompound compound) {
        String s = this.getEntityString();
        if (!this.isDead && s != null && !this.isRiding()) {
            compound.setString("id", s);
            this.writeToNBT(compound);
            return true;
        }
        return false;
    }

    public static void func_190533_a(DataFixer p_190533_0_) {
        p_190533_0_.registerWalker(FixTypes.ENTITY, new IDataWalker(){

            @Override
            public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
                if (compound.hasKey("Passengers", 9)) {
                    NBTTagList nbttaglist = compound.getTagList("Passengers", 10);
                    for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                        nbttaglist.set(i, fixer.process(FixTypes.ENTITY, nbttaglist.getCompoundTagAt(i), versionIn));
                    }
                }
                return compound;
            }
        });
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        try {
            compound.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
            compound.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
            compound.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
            compound.setFloat("FallDistance", this.fallDistance);
            compound.setShort("Fire", (short)this.field_190534_ay);
            compound.setShort("Air", (short)this.getAir());
            compound.setBoolean("OnGround", this.onGround);
            compound.setInteger("Dimension", this.dimension);
            compound.setBoolean("Invulnerable", this.invulnerable);
            compound.setInteger("PortalCooldown", this.timeUntilPortal);
            compound.setUniqueId("UUID", this.getUniqueID());
            if (this.hasCustomName()) {
                compound.setString("CustomName", this.getCustomNameTag());
            }
            if (this.getAlwaysRenderNameTag()) {
                compound.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
            }
            this.cmdResultStats.writeStatsToNBT(compound);
            if (this.isSilent()) {
                compound.setBoolean("Silent", this.isSilent());
            }
            if (this.hasNoGravity()) {
                compound.setBoolean("NoGravity", this.hasNoGravity());
            }
            if (this.glowing) {
                compound.setBoolean("Glowing", this.glowing);
            }
            if (!this.tags.isEmpty()) {
                NBTTagList nbttaglist = new NBTTagList();
                for (String s : this.tags) {
                    nbttaglist.appendTag(new NBTTagString(s));
                }
                compound.setTag("Tags", nbttaglist);
            }
            this.writeEntityToNBT(compound);
            if (this.isBeingRidden()) {
                NBTTagList nbttaglist1 = new NBTTagList();
                for (Entity entity : this.getPassengers()) {
                    NBTTagCompound nbttagcompound;
                    if (!entity.writeToNBTAtomically(nbttagcompound = new NBTTagCompound())) continue;
                    nbttaglist1.appendTag(nbttagcompound);
                }
                if (!nbttaglist1.hasNoTags()) {
                    compound.setTag("Passengers", nbttaglist1);
                }
            }
            return compound;
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
            this.addEntityCrashInfo(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    public void readFromNBT(NBTTagCompound compound) {
        try {
            NBTTagList nbttaglist = compound.getTagList("Pos", 6);
            NBTTagList nbttaglist2 = compound.getTagList("Motion", 6);
            NBTTagList nbttaglist3 = compound.getTagList("Rotation", 5);
            this.motionX = nbttaglist2.getDoubleAt(0);
            this.motionY = nbttaglist2.getDoubleAt(1);
            this.motionZ = nbttaglist2.getDoubleAt(2);
            if (Math.abs(this.motionX) > 10.0) {
                this.motionX = 0.0;
            }
            if (Math.abs(this.motionY) > 10.0) {
                this.motionY = 0.0;
            }
            if (Math.abs(this.motionZ) > 10.0) {
                this.motionZ = 0.0;
            }
            this.posX = nbttaglist.getDoubleAt(0);
            this.posY = nbttaglist.getDoubleAt(1);
            this.posZ = nbttaglist.getDoubleAt(2);
            this.lastTickPosX = this.posX;
            this.lastTickPosY = this.posY;
            this.lastTickPosZ = this.posZ;
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.rotationYaw = nbttaglist3.getFloatAt(0);
            this.rotationPitch = nbttaglist3.getFloatAt(1);
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
            this.setRotationYawHead(this.rotationYaw);
            this.setRenderYawOffset(this.rotationYaw);
            this.fallDistance = compound.getFloat("FallDistance");
            this.field_190534_ay = compound.getShort("Fire");
            this.setAir(compound.getShort("Air"));
            this.onGround = compound.getBoolean("OnGround");
            if (compound.hasKey("Dimension")) {
                this.dimension = compound.getInteger("Dimension");
            }
            this.invulnerable = compound.getBoolean("Invulnerable");
            this.timeUntilPortal = compound.getInteger("PortalCooldown");
            if (compound.hasUniqueId("UUID")) {
                this.entityUniqueID = compound.getUniqueId("UUID");
                this.cachedUniqueIdString = this.entityUniqueID.toString();
            }
            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (compound.hasKey("CustomName", 8)) {
                this.setCustomNameTag(compound.getString("CustomName"));
            }
            this.setAlwaysRenderNameTag(compound.getBoolean("CustomNameVisible"));
            this.cmdResultStats.readStatsFromNBT(compound);
            this.setSilent(compound.getBoolean("Silent"));
            this.setNoGravity(compound.getBoolean("NoGravity"));
            this.setGlowing(compound.getBoolean("Glowing"));
            if (compound.hasKey("Tags", 9)) {
                this.tags.clear();
                NBTTagList nbttaglist1 = compound.getTagList("Tags", 8);
                int i = Math.min(nbttaglist1.tagCount(), 1024);
                for (int j = 0; j < i; ++j) {
                    this.tags.add(nbttaglist1.getStringTagAt(j));
                }
            }
            this.readEntityFromNBT(compound);
            if (this.shouldSetPosAfterLoading()) {
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
            this.addEntityCrashInfo(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    protected boolean shouldSetPosAfterLoading() {
        return true;
    }

    @Nullable
    protected final String getEntityString() {
        ResourceLocation resourcelocation = EntityList.func_191301_a(this);
        return resourcelocation == null ? null : resourcelocation.toString();
    }

    protected abstract void readEntityFromNBT(NBTTagCompound var1);

    protected abstract void writeEntityToNBT(NBTTagCompound var1);

    protected NBTTagList newDoubleNBTList(double ... numbers) {
        NBTTagList nbttaglist = new NBTTagList();
        for (double d0 : numbers) {
            nbttaglist.appendTag(new NBTTagDouble(d0));
        }
        return nbttaglist;
    }

    protected NBTTagList newFloatNBTList(float ... numbers) {
        NBTTagList nbttaglist = new NBTTagList();
        for (float f : numbers) {
            nbttaglist.appendTag(new NBTTagFloat(f));
        }
        return nbttaglist;
    }

    @Nullable
    public EntityItem dropItem(Item itemIn, int size) {
        return this.dropItemWithOffset(itemIn, size, 0.0f);
    }

    @Nullable
    public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
        return this.entityDropItem(new ItemStack(itemIn, size, 0), offsetY);
    }

    @Nullable
    public EntityItem entityDropItem(ItemStack stack, float offsetY) {
        if (stack.func_190926_b()) {
            return null;
        }
        EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + (double)offsetY, this.posZ, stack);
        entityitem.setDefaultPickupDelay();
        this.world.spawnEntityInWorld(entityitem);
        return entityitem;
    }

    public boolean isEntityAlive() {
        return !this.isDead;
    }

    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return false;
        }
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
        for (int i = 0; i < 8; ++i) {
            int j = MathHelper.floor(this.posY + (double)(((float)((i >> 0) % 2) - 0.5f) * 0.1f) + (double)this.getEyeHeight());
            int k = MathHelper.floor(this.posX + (double)(((float)((i >> 1) % 2) - 0.5f) * this.width * 0.8f));
            int l = MathHelper.floor(this.posZ + (double)(((float)((i >> 2) % 2) - 0.5f) * this.width * 0.8f));
            if (blockpos$pooledmutableblockpos.getX() == k && blockpos$pooledmutableblockpos.getY() == j && blockpos$pooledmutableblockpos.getZ() == l) continue;
            blockpos$pooledmutableblockpos.setPos(k, j, l);
            if (!this.world.getBlockState(blockpos$pooledmutableblockpos).func_191058_s()) continue;
            blockpos$pooledmutableblockpos.release();
            return true;
        }
        blockpos$pooledmutableblockpos.release();
        return false;
    }

    public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
        return false;
    }

    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return null;
    }

    public void updateRidden() {
        Entity entity = this.getRidingEntity();
        if (this.isRiding() && entity.isDead) {
            this.dismountRidingEntity();
        } else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            if (this.isRiding()) {
                entity.updatePassenger(this);
            }
        }
    }

    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            passenger.setPosition(this.posX, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ);
        }
    }

    public void applyOrientationToEntity(Entity entityToUpdate) {
    }

    public double getYOffset() {
        return 0.0;
    }

    public double getMountedYOffset() {
        return (double)this.height * 0.75;
    }

    public boolean startRiding(Entity entityIn) {
        return this.startRiding(entityIn, false);
    }

    public boolean startRiding(Entity entityIn, boolean force) {
        Entity entity = entityIn;
        while (entity.ridingEntity != null) {
            if (entity.ridingEntity == this) {
                return false;
            }
            entity = entity.ridingEntity;
        }
        if (force || this.canBeRidden(entityIn) && entityIn.canFitPassenger(this)) {
            if (this.isRiding()) {
                this.dismountRidingEntity();
            }
            this.ridingEntity = entityIn;
            this.ridingEntity.addPassenger(this);
            return true;
        }
        return false;
    }

    protected boolean canBeRidden(Entity entityIn) {
        return this.rideCooldown <= 0;
    }

    public void removePassengers() {
        for (int i = this.riddenByEntities.size() - 1; i >= 0; --i) {
            this.riddenByEntities.get(i).dismountRidingEntity();
        }
    }

    public void dismountRidingEntity() {
        if (this.ridingEntity != null) {
            Entity entity = this.ridingEntity;
            this.ridingEntity = null;
            entity.removePassenger(this);
        }
    }

    protected void addPassenger(Entity passenger) {
        if (passenger.getRidingEntity() != this) {
            throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
        }
        if (!this.world.isRemote && passenger instanceof EntityPlayer && !(this.getControllingPassenger() instanceof EntityPlayer)) {
            this.riddenByEntities.add(0, passenger);
        } else {
            this.riddenByEntities.add(passenger);
        }
    }

    protected void removePassenger(Entity passenger) {
        if (passenger.getRidingEntity() == this) {
            throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
        }
        this.riddenByEntities.remove(passenger);
        passenger.rideCooldown = 60;
    }

    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 1;
    }

    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    public float getCollisionBorderSize() {
        return 0.0f;
    }

    public Vec3d getLookVec() {
        return Entity.getVectorForRotation(this.rotationPitch, this.rotationYaw);
    }

    public Vec2f getPitchYaw() {
        return new Vec2f(this.rotationPitch, this.rotationYaw);
    }

    public Vec3d getForward() {
        return Vec3d.fromPitchYawVector(this.getPitchYaw());
    }

    public void setPortal(BlockPos pos) {
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
        } else {
            if (!this.world.isRemote && !pos.equals(this.lastPortalPos)) {
                this.lastPortalPos = new BlockPos(pos);
                BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.PORTAL.createPatternHelper(this.world, this.lastPortalPos);
                double d0 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX();
                double d1 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? this.posZ : this.posX;
                d1 = Math.abs(MathHelper.pct(d1 - (double)(blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0), d0, d0 - (double)blockpattern$patternhelper.getWidth()));
                double d2 = MathHelper.pct(this.posY - 1.0, blockpattern$patternhelper.getFrontTopLeft().getY(), blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight());
                this.lastPortalVec = new Vec3d(d1, d2, 0.0);
                this.teleportDirection = blockpattern$patternhelper.getForwards();
            }
            this.inPortal = true;
        }
    }

    public int getPortalCooldown() {
        return 300;
    }

    public void setVelocity(double x, double y, double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    public void handleStatusUpdate(byte id) {
    }

    public void performHurtAnimation() {
    }

    public Iterable<ItemStack> getHeldEquipment() {
        return field_190535_b;
    }

    public Iterable<ItemStack> getArmorInventoryList() {
        return field_190535_b;
    }

    public Iterable<ItemStack> getEquipmentAndArmor() {
        return Iterables.concat(this.getHeldEquipment(), this.getArmorInventoryList());
    }

    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
    }

    public boolean isBurning() {
        boolean flag;
        boolean bl = flag = this.world != null && this.world.isRemote;
        if (this.antiFired) {
            this.antiFired = false;
            return this.antiFired;
        }
        return !this.isImmuneToFire && (this.field_190534_ay > 0 || flag && this.getFlag(0));
    }

    public void setBurning(boolean fired) {
        this.antiFired = !fired;
    }

    public boolean isRiding() {
        return this.getRidingEntity() != null;
    }

    public boolean isBeingRidden() {
        return !this.getPassengers().isEmpty();
    }

    public boolean isSneaking() {
        return this.getFlag(1);
    }

    public void setSneaking(boolean sneaking) {
        this.setFlag(1, sneaking);
    }

    public boolean isSprinting() {
        return this.getFlag(3);
    }

    public void setSprinting(boolean sprinting) {
        this.setFlag(3, sprinting);
    }

    public boolean isGlowing() {
        return this.glowing || this.world.isRemote && this.getFlag(6);
    }

    public void setGlowing(boolean glowingIn) {
        this.glowing = glowingIn;
        if (!this.world.isRemote) {
            this.setFlag(6, this.glowing);
        }
    }

    public boolean isInvisible() {
        return this.getFlag(5);
    }

    public boolean isInvisibleToPlayer(EntityPlayer player) {
        if (player.isSpectator()) {
            return false;
        }
        Team team = this.getTeam();
        return (team == null || player == null || player.getTeam() != team || !team.getSeeFriendlyInvisiblesEnabled()) && this.isInvisible();
    }

    @Nullable
    public Team getTeam() {
        return this.world.getScoreboard().getPlayersTeam(this.getCachedUniqueIdString());
    }

    public boolean isOnSameTeam(Entity entityIn) {
        return this.isOnScoreboardTeam(entityIn.getTeam());
    }

    public boolean isOnScoreboardTeam(Team teamIn) {
        return this.getTeam() != null && this.getTeam().isSameTeam(teamIn);
    }

    public void setInvisible(boolean invisible) {
        this.setFlag(5, invisible);
    }

    public boolean getFlag(int flag) {
        return (this.dataManager.get(FLAGS) & 1 << flag) != 0;
    }

    public void setFlag(int flag, boolean set) {
        byte b0 = this.dataManager.get(FLAGS);
        if (set) {
            this.dataManager.set(FLAGS, (byte)(b0 | 1 << flag));
        } else {
            this.dataManager.set(FLAGS, (byte)(b0 & ~(1 << flag)));
        }
    }

    public int getAir() {
        return this.dataManager.get(AIR);
    }

    public void setAir(int air) {
        this.dataManager.set(AIR, air);
    }

    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        this.attackEntityFrom(DamageSource.lightningBolt, 5.0f);
        ++this.field_190534_ay;
        if (this.field_190534_ay == 0) {
            this.setFire(8);
        }
    }

    public void onKillEntity(EntityLivingBase entityLivingIn) {
    }

    protected boolean pushOutOfBlocks(double x, double y, double z) {
        BlockPos blockpos = new BlockPos(x, y, z);
        double d0 = x - (double)blockpos.getX();
        double d1 = y - (double)blockpos.getY();
        double d2 = z - (double)blockpos.getZ();
        if (!this.world.collidesWithAnyBlock(this.getEntityBoundingBox())) {
            return false;
        }
        EnumFacing enumfacing = EnumFacing.UP;
        double d3 = Double.MAX_VALUE;
        if (!this.world.isBlockFullCube(blockpos.west()) && d0 < d3) {
            d3 = d0;
            enumfacing = EnumFacing.WEST;
        }
        if (!this.world.isBlockFullCube(blockpos.east()) && 1.0 - d0 < d3) {
            d3 = 1.0 - d0;
            enumfacing = EnumFacing.EAST;
        }
        if (!this.world.isBlockFullCube(blockpos.north()) && d2 < d3) {
            d3 = d2;
            enumfacing = EnumFacing.NORTH;
        }
        if (!this.world.isBlockFullCube(blockpos.south()) && 1.0 - d2 < d3) {
            d3 = 1.0 - d2;
            enumfacing = EnumFacing.SOUTH;
        }
        if (!this.world.isBlockFullCube(blockpos.up()) && 1.0 - d1 < d3) {
            d3 = 1.0 - d1;
            enumfacing = EnumFacing.UP;
        }
        float f = this.rand.nextFloat() * 0.2f + 0.1f;
        float f1 = enumfacing.getAxisDirection().getOffset();
        if (enumfacing.getAxis() == EnumFacing.Axis.X) {
            this.motionX = f1 * f;
            this.motionY *= 0.75;
            this.motionZ *= 0.75;
        } else if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            this.motionX *= 0.75;
            this.motionY = f1 * f;
            this.motionZ *= 0.75;
        } else if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
            this.motionX *= 0.75;
            this.motionY *= 0.75;
            this.motionZ = f1 * f;
        }
        return true;
    }

    public void setInWeb() {
        this.isInWeb = true;
        this.fallDistance = 0.0f;
    }

    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        }
        String s = EntityList.getEntityString(this);
        if (s == null) {
            s = "generic";
        }
        return I18n.translateToLocal("entity." + s + ".name");
    }

    @Nullable
    public Entity[] getParts() {
        return null;
    }

    public boolean isEntityEqual(Entity entityIn) {
        return this == entityIn;
    }

    public float getRotationYawHead() {
        return 0.0f;
    }

    public void setRotationYawHead(float rotation) {
    }

    public void setRenderYawOffset(float offset) {
    }

    public boolean canBeAttackedWithItem() {
        return true;
    }

    public boolean hitByEntity(Entity entityIn) {
        return false;
    }

    public String toString() {
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getName(), this.entityId, this.world == null ? "~NULL~" : this.world.getWorldInfo().getWorldName(), this.posX, this.posY, this.posZ);
    }

    public boolean isEntityInvulnerable(DamageSource source) {
        return this.invulnerable && source != DamageSource.outOfWorld && !source.isCreativePlayer();
    }

    public boolean func_190530_aW() {
        return this.invulnerable;
    }

    public void setEntityInvulnerable(boolean isInvulnerable) {
        this.invulnerable = isInvulnerable;
    }

    public void copyLocationAndAnglesFrom(Entity entityIn) {
        this.setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
    }

    private void copyDataFromOld(Entity entityIn) {
        NBTTagCompound nbttagcompound = entityIn.writeToNBT(new NBTTagCompound());
        nbttagcompound.removeTag("Dimension");
        this.readFromNBT(nbttagcompound);
        this.timeUntilPortal = entityIn.timeUntilPortal;
        this.lastPortalPos = entityIn.lastPortalPos;
        this.lastPortalVec = entityIn.lastPortalVec;
        this.teleportDirection = entityIn.teleportDirection;
    }

    @Nullable
    public Entity changeDimension(int dimensionIn) {
        if (!this.world.isRemote && !this.isDead) {
            BlockPos blockpos;
            this.world.theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = this.getServer();
            int i = this.dimension;
            WorldServer worldserver = minecraftserver.worldServerForDimension(i);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionIn);
            this.dimension = dimensionIn;
            if (i == 1 && dimensionIn == 1) {
                worldserver1 = minecraftserver.worldServerForDimension(0);
                this.dimension = 0;
            }
            this.world.removeEntity(this);
            this.isDead = false;
            this.world.theProfiler.startSection("reposition");
            if (dimensionIn == 1) {
                blockpos = worldserver1.getSpawnCoordinate();
            } else {
                double d0 = this.posX;
                double d1 = this.posZ;
                double d2 = 8.0;
                if (dimensionIn == -1) {
                    d0 = MathHelper.clamp(d0 / 8.0, worldserver1.getWorldBorder().minX() + 16.0, worldserver1.getWorldBorder().maxX() - 16.0);
                    d1 = MathHelper.clamp(d1 / 8.0, worldserver1.getWorldBorder().minZ() + 16.0, worldserver1.getWorldBorder().maxZ() - 16.0);
                } else if (dimensionIn == 0) {
                    d0 = MathHelper.clamp(d0 * 8.0, worldserver1.getWorldBorder().minX() + 16.0, worldserver1.getWorldBorder().maxX() - 16.0);
                    d1 = MathHelper.clamp(d1 * 8.0, worldserver1.getWorldBorder().minZ() + 16.0, worldserver1.getWorldBorder().maxZ() - 16.0);
                }
                d0 = MathHelper.clamp((int)d0, -29999872, 29999872);
                d1 = MathHelper.clamp((int)d1, -29999872, 29999872);
                float f = this.rotationYaw;
                this.setLocationAndAngles(d0, this.posY, d1, 90.0f, 0.0f);
                Teleporter teleporter = worldserver1.getDefaultTeleporter();
                teleporter.placeInExistingPortal(this, f);
                blockpos = new BlockPos(this);
            }
            worldserver.updateEntityWithOptionalForce(this, false);
            this.world.theProfiler.endStartSection("reloading");
            Entity entity = EntityList.func_191304_a(this.getClass(), worldserver1);
            if (entity != null) {
                entity.copyDataFromOld(this);
                if (i == 1 && dimensionIn == 1) {
                    BlockPos blockpos1 = worldserver1.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
                    entity.moveToBlockPosAndAngles(blockpos1, entity.rotationYaw, entity.rotationPitch);
                } else {
                    entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
                }
                boolean flag = entity.forceSpawn;
                entity.forceSpawn = true;
                worldserver1.spawnEntityInWorld(entity);
                entity.forceSpawn = flag;
                worldserver1.updateEntityWithOptionalForce(entity, false);
            }
            this.isDead = true;
            this.world.theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            this.world.theProfiler.endSection();
            return entity;
        }
        return null;
    }

    public boolean isNonBoss() {
        return true;
    }

    public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
        return blockStateIn.getBlock().getExplosionResistance(this);
    }

    public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
        return true;
    }

    public int getMaxFallHeight() {
        return 3;
    }

    public Vec3d getLastPortalVec() {
        return this.lastPortalVec;
    }

    public EnumFacing getTeleportDirection() {
        return this.teleportDirection;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public void addEntityCrashInfo(CrashReportCategory category) {
        category.setDetail("Entity Type", new ICrashReportDetail<String>(){

            @Override
            public String call() throws Exception {
                return EntityList.func_191301_a(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
            }
        });
        category.addCrashSection("Entity ID", this.entityId);
        category.setDetail("Entity Name", new ICrashReportDetail<String>(){

            @Override
            public String call() throws Exception {
                return Entity.this.getName();
            }
        });
        category.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
        category.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ)));
        category.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
        category.setDetail("Entity's Passengers", new ICrashReportDetail<String>(){

            @Override
            public String call() throws Exception {
                return Entity.this.getPassengers().toString();
            }
        });
        category.setDetail("Entity's Vehicle", new ICrashReportDetail<String>(){

            @Override
            public String call() throws Exception {
                return Entity.this.getRidingEntity().toString();
            }
        });
    }

    public boolean canRenderOnFire() {
        return this.isBurning();
    }

    public void setUniqueId(UUID uniqueIdIn) {
        this.entityUniqueID = uniqueIdIn;
        this.cachedUniqueIdString = this.entityUniqueID.toString();
    }

    public UUID getUniqueID() {
        return this.entityUniqueID;
    }

    public String getCachedUniqueIdString() {
        return this.cachedUniqueIdString;
    }

    public boolean isPushedByWater() {
        return true;
    }

    public static double getRenderDistanceWeight() {
        return renderDistanceWeight;
    }

    public static void setRenderDistanceWeight(double renderDistWeight) {
        renderDistanceWeight = renderDistWeight;
    }

    @Override
    public ITextComponent getDisplayName() {
        TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
        textcomponentstring.getStyle().setHoverEvent(this.getHoverEvent());
        textcomponentstring.getStyle().setInsertion(this.getCachedUniqueIdString());
        return textcomponentstring;
    }

    public void setCustomNameTag(String name) {
        this.dataManager.set(CUSTOM_NAME, name);
    }

    public String getCustomNameTag() {
        return this.dataManager.get(CUSTOM_NAME);
    }

    public boolean hasCustomName() {
        return !this.dataManager.get(CUSTOM_NAME).isEmpty();
    }

    public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
        this.dataManager.set(CUSTOM_NAME_VISIBLE, alwaysRenderNameTag);
    }

    public boolean getAlwaysRenderNameTag() {
        return this.dataManager.get(CUSTOM_NAME_VISIBLE);
    }

    public void setPositionAndUpdate(double x, double y, double z) {
        this.isPositionDirty = true;
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.world.updateEntityWithOptionalForce(this, false);
    }

    public boolean getAlwaysRenderNameTagForRender() {
        return this.getAlwaysRenderNameTag();
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
    }

    public EnumFacing getHorizontalFacing() {
        return EnumFacing.getHorizontal(MathHelper.floor((double)(this.rotationYaw * 4.0f / 360.0f) + 0.5) & 3);
    }

    public EnumFacing getAdjustedHorizontalFacing() {
        return this.getHorizontalFacing();
    }

    protected HoverEvent getHoverEvent() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        ResourceLocation resourcelocation = EntityList.func_191301_a(this);
        nbttagcompound.setString("id", this.getCachedUniqueIdString());
        if (resourcelocation != null) {
            nbttagcompound.setString("type", resourcelocation.toString());
        }
        nbttagcompound.setString("name", this.getName());
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new TextComponentString(nbttagcompound.toString()));
    }

    public boolean isSpectatedByPlayer(EntityPlayerMP player) {
        return true;
    }

    public AxisAlignedBB resizeAxisByNewVersion(AxisAlignedBB prevAxis) {
        EntityLivingBase base;
        AxisAlignedBB axis = null;
        Entity entity = this;
        if (entity instanceof EntityLivingBase && (base = (EntityLivingBase)entity) != null && base.hasNewVersionMoves) {
            double height;
            double prevHeight = height = prevAxis.maxY - prevAxis.minY;
            if (base.isLay) {
                height = 0.6;
            } else if (base.isNewSneak || base.isSneaking()) {
                height = 1.5;
            }
            if (height != prevHeight) {
                axis = new AxisAlignedBB(prevAxis.minX, prevAxis.minY, prevAxis.minZ, prevAxis.maxX, prevAxis.minY + height, prevAxis.maxZ);
            }
        }
        return axis == null ? prevAxis : axis;
    }

    public AxisAlignedBB getEntityBoundingBox() {
        if (this != null && this == FreeCam.fakePlayer) {
            return new AxisAlignedBB(FreeCam.fakePlayer.posX, FreeCam.fakePlayer.posY + (double)(FreeCam.fakePlayer.height / 2.0f) - (double)0.1f, FreeCam.fakePlayer.posZ, FreeCam.fakePlayer.posX, FreeCam.fakePlayer.posY + (double)(FreeCam.fakePlayer.height / 2.0f) + (double)0.1f, FreeCam.fakePlayer.posZ);
        }
        if (this != null && this != Minecraft.player && this instanceof EntityLivingBase && EntityBox.hitboxModState() && EntityBox.entityIsCurrentToExtend(this)) {
            return EntityBox.getExtendedHitbox(EntityBox.hitboxModPredictVec(this, EntityBox.hitboxModPredictSize()), this, EntityBox.hitboxModSizeBox(), this.resizeAxisByNewVersion(this.boundingBox));
        }
        return this.resizeAxisByNewVersion(this.boundingBox);
    }

    public AxisAlignedBB getRenderBoundingBox() {
        return this.getEntityBoundingBox();
    }

    public void setEntityBoundingBox(AxisAlignedBB bb) {
        this.boundingBox = bb;
    }

    public float getEyeHeight() {
        return this.height * 0.85f;
    }

    public boolean isOutsideBorder() {
        return this.isOutsideBorder;
    }

    public void setOutsideBorder(boolean outsideBorder) {
        this.isOutsideBorder = outsideBorder;
    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        return false;
    }

    @Override
    public void addChatMessage(ITextComponent component) {
    }

    @Override
    public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
        return true;
    }

    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }

    @Override
    public Vec3d getPositionVector() {
        return new Vec3d(this.posX, this.posY, this.posZ);
    }

    @Override
    public World getEntityWorld() {
        return this.world;
    }

    @Override
    public Entity getCommandSenderEntity() {
        return this;
    }

    @Override
    public boolean sendCommandFeedback() {
        return false;
    }

    @Override
    public void setCommandStat(CommandResultStats.Type type2, int amount) {
        if (this.world != null && !this.world.isRemote) {
            this.cmdResultStats.setCommandStatForSender(this.world.getMinecraftServer(), this, type2, amount);
        }
    }

    @Override
    @Nullable
    public MinecraftServer getServer() {
        return this.world.getMinecraftServer();
    }

    public CommandResultStats getCommandStats() {
        return this.cmdResultStats;
    }

    public void setCommandStats(Entity entityIn) {
        this.cmdResultStats.addAllStats(entityIn.getCommandStats());
    }

    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand stack) {
        return EnumActionResult.PASS;
    }

    public boolean isImmuneToExplosions() {
        return false;
    }

    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entityIn, entityLivingBaseIn);
        }
        EnchantmentHelper.applyArthropodEnchantments(entityLivingBaseIn, entityIn);
    }

    public void addTrackingPlayer(EntityPlayerMP player) {
    }

    public void removeTrackingPlayer(EntityPlayerMP player) {
    }

    public float getRotatedYaw(Rotation transformRotation) {
        float f = MathHelper.wrapDegrees(this.rotationYaw);
        switch (transformRotation) {
            case CLOCKWISE_180: {
                return f + 180.0f;
            }
            case COUNTERCLOCKWISE_90: {
                return f + 270.0f;
            }
            case CLOCKWISE_90: {
                return f + 90.0f;
            }
        }
        return f;
    }

    public float getMirroredYaw(Mirror transformMirror) {
        float f = MathHelper.wrapDegrees(this.rotationYaw);
        switch (transformMirror) {
            case LEFT_RIGHT: {
                return -f;
            }
            case FRONT_BACK: {
                return 180.0f - f;
            }
        }
        return f;
    }

    public boolean ignoreItemEntityData() {
        return false;
    }

    public boolean setPositionNonDirty() {
        boolean flag = this.isPositionDirty;
        this.isPositionDirty = false;
        return flag;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return null;
    }

    public List<Entity> getPassengers() {
        return this.riddenByEntities.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.riddenByEntities);
    }

    public boolean isPassenger(Entity entityIn) {
        for (Entity entity : this.getPassengers()) {
            if (!entity.equals(entityIn)) continue;
            return true;
        }
        return false;
    }

    public Collection<Entity> getRecursivePassengers() {
        HashSet<Entity> set = Sets.newHashSet();
        this.getRecursivePassengersByType(Entity.class, set);
        return set;
    }

    public <T extends Entity> Collection<T> getRecursivePassengersByType(Class<T> entityClass) {
        HashSet set = Sets.newHashSet();
        this.getRecursivePassengersByType(entityClass, set);
        return set;
    }

    private <T extends Entity> void getRecursivePassengersByType(Class<T> entityClass, Set<T> theSet) {
        for (Entity entity : this.getPassengers()) {
            if (entityClass.isAssignableFrom(entity.getClass())) {
                theSet.add(entity);
            }
            entity.getRecursivePassengersByType(entityClass, theSet);
        }
    }

    public Entity getLowestRidingEntity() {
        Entity entity = this;
        while (entity.isRiding()) {
            entity = entity.getRidingEntity();
        }
        return entity;
    }

    public boolean isRidingSameEntity(Entity entityIn) {
        return this.getLowestRidingEntity() == entityIn.getLowestRidingEntity();
    }

    public boolean isRidingOrBeingRiddenBy(Entity entityIn) {
        for (Entity entity : this.getPassengers()) {
            if (entity.equals(entityIn)) {
                return true;
            }
            if (!entity.isRidingOrBeingRiddenBy(entityIn)) continue;
            return true;
        }
        return false;
    }

    public boolean canPassengerSteer() {
        Entity entity = this.getControllingPassenger();
        if (entity instanceof EntityPlayer) {
            return ((EntityPlayer)entity).isUser();
        }
        return !this.world.isRemote;
    }

    @Nullable
    public Entity getRidingEntity() {
        return this.ridingEntity;
    }

    public EnumPushReaction getPushReaction() {
        return EnumPushReaction.NORMAL;
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    protected int func_190531_bD() {
        return 1;
    }

    static {
        FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
        AIR = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);
        CUSTOM_NAME = EntityDataManager.createKey(Entity.class, DataSerializers.STRING);
        CUSTOM_NAME_VISIBLE = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
        SILENT = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
        NO_GRAVITY = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
    }
}

