package net.minecraft.entity.monster;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityEnderman extends EntityMob
{
    private static final UUID attackingSpeedBoostModifierUUID;
    private static final String[] I;
    private static final AttributeModifier attackingSpeedBoostModifier;
    private boolean isAggressive;
    private static final Set<Block> carriableBlocks;
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null) {
            final int nextInt = this.rand.nextInt("  ".length() + n);
            int i = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i < nextInt) {
                this.dropItem(dropItem, " ".length());
                ++i;
            }
        }
    }
    
    protected boolean teleportRandomly() {
        return this.teleportTo(this.posX + (this.rand.nextDouble() - 0.5) * 64.0, this.posY + (this.rand.nextInt(0x38 ^ 0x78) - (0x3D ^ 0x1D)), this.posZ + (this.rand.nextDouble() - 0.5) * 64.0);
    }
    
    protected boolean teleportTo(final double posX, final double posY, final double posZ) {
        final double posX2 = this.posX;
        final double posY2 = this.posY;
        final double posZ2 = this.posZ;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        int n = "".length();
        BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);
        if (this.worldObj.isBlockLoaded(blockPos)) {
            int n2 = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (n2 == 0 && blockPos.getY() > 0) {
                final BlockPos down = blockPos.down();
                if (this.worldObj.getBlockState(down).getBlock().getMaterial().blocksMovement()) {
                    n2 = " ".length();
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    continue;
                }
                else {
                    --this.posY;
                    blockPos = down;
                }
            }
            if (n2 != 0) {
                super.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
                    n = " ".length();
                }
            }
        }
        if (n == 0) {
            this.setPosition(posX2, posY2, posZ2);
            return "".length() != 0;
        }
        final int n3 = 17 + 5 + 101 + 5;
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < n3) {
            final double n4 = i / (n3 - 1.0);
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX2 + (this.posX - posX2) * n4 + (this.rand.nextDouble() - 0.5) * this.width * 2.0, posY2 + (this.posY - posY2) * n4 + this.rand.nextDouble() * this.height, posZ2 + (this.posZ - posZ2) * n4 + (this.rand.nextDouble() - 0.5) * this.width * 2.0, (this.rand.nextFloat() - 0.5f) * 0.2f, (this.rand.nextFloat() - 0.5f) * 0.2f, (this.rand.nextFloat() - 0.5f) * 0.2f, new int["".length()]);
            ++i;
        }
        this.worldObj.playSoundEffect(posX2, posY2, posZ2, EntityEnderman.I[0xB0 ^ 0xB9], 1.0f, 1.0f);
        this.playSound(EntityEnderman.I[0x4F ^ 0x45], 1.0f, 1.0f);
        return " ".length() != 0;
    }
    
    static AttributeModifier access$0() {
        return EntityEnderman.attackingSpeedBoostModifier;
    }
    
    public void setScreaming(final boolean b) {
        final DataWatcher dataWatcher = this.dataWatcher;
        final int n = 0xA5 ^ 0xB7;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    static Set access$3() {
        return EntityEnderman.carriableBlocks;
    }
    
    protected boolean teleportToEntity(final Entity entity) {
        final Vec3 normalize = new Vec3(this.posX - entity.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - entity.posY + entity.getEyeHeight(), this.posZ - entity.posZ).normalize();
        final double n = 16.0;
        return this.teleportTo(this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - normalize.xCoord * n, this.posY + (this.rand.nextInt(0x21 ^ 0x31) - (0x31 ^ 0x39)) - normalize.yCoord * n, this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - normalize.zCoord * n);
    }
    
    @Override
    public float getEyeHeight() {
        return 2.55f;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityEnderman.I[0xCA ^ 0xC7];
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (damageSource.getEntity() == null || !(damageSource.getEntity() instanceof EntityEndermite)) {
            if (!this.worldObj.isRemote) {
                this.setScreaming(" ".length() != 0);
            }
            if (damageSource instanceof EntityDamageSource && damageSource.getEntity() instanceof EntityPlayer) {
                if (damageSource.getEntity() instanceof EntityPlayerMP && ((EntityPlayerMP)damageSource.getEntity()).theItemInWorldManager.isCreative()) {
                    this.setScreaming("".length() != 0);
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                else {
                    this.isAggressive = (" ".length() != 0);
                }
            }
            if (damageSource instanceof EntityDamageSourceIndirect) {
                this.isAggressive = ("".length() != 0);
                int i = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (i < (0x29 ^ 0x69)) {
                    if (this.teleportRandomly()) {
                        return " ".length() != 0;
                    }
                    ++i;
                }
                return "".length() != 0;
            }
        }
        final boolean attackEntity = super.attackEntityFrom(damageSource, n);
        if (damageSource.isUnblockable() && this.rand.nextInt(0x47 ^ 0x4D) != 0) {
            this.teleportRandomly();
        }
        return attackEntity;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected String getDeathSound() {
        return EntityEnderman.I[0xD ^ 0x3];
    }
    
    private static void I() {
        (I = new String[0x6D ^ 0x62])["".length()] = I("AKS\u0003s5?!k{F8&kwGLPkzDLUk{BHSws4KZw\u0002A", "qycFC");
        EntityEnderman.I[" ".length()] = I("\u0007<$(\"-!>.a585,%f*?&22", "FHPIA");
        EntityEnderman.I["  ".length()] = I(":3$\"\u000b<6", "YRVPb");
        EntityEnderman.I["   ".length()] = I("-\u0019\u0006\u001a\u0011+\u001c0\t\f/", "Nxthx");
        EntityEnderman.I[0x98 ^ 0x9C] = I(";-+\u0014\b=(", "XLYfa");
        EntityEnderman.I[0x47 ^ 0x42] = I("\n\u000b*<?\f\u000e", "ijXNV");
        EntityEnderman.I[0xAA ^ 0xAC] = I("7\u001b\u0018\u0017/1\u001e.\u000425", "TzjeF");
        EntityEnderman.I[0x5E ^ 0x59] = I("/\b#\u0010()\r", "LiQbA");
        EntityEnderman.I[0xA0 ^ 0xA8] = I("\t&86\u0003\u000f#\u000e%\u001e\u000b", "jGJDj");
        EntityEnderman.I[0x4 ^ 0xD] = I(",\u0007:I\u0017/\f=\u0015\u001f$\u0006v\u0017\u001d3\u001c9\u000b", "AhXgr");
        EntityEnderman.I[0x1A ^ 0x10] = I("\n#1I\u0000\t(6\u0015\b\u0002\"}\u0017\n\u001582\u000b", "gLSge");
        EntityEnderman.I[0x91 ^ 0x9A] = I("\b\u0017&\u007f&\u000b\u001c!#.\u0000\u0016j\" \u0017\u001d%<", "exDQC");
        EntityEnderman.I[0x2F ^ 0x23] = I("\u0019 4g*\u001a+3;\"\u0011!x +\u0018*", "tOVIO");
        EntityEnderman.I[0xB ^ 0x6] = I("\u0003<\u0003O\u0015\u00007\u0004\u0013\u001d\u000b=O\t\u0019\u001a", "nSaap");
        EntityEnderman.I[0xBE ^ 0xB0] = I("=?\fL\n>4\u000b\u0010\u00025>@\u0006\n1$\u0006", "PPnbo");
    }
    
    @Override
    protected String getLivingSound() {
        String s;
        if (this.isScreaming()) {
            s = EntityEnderman.I[0x4D ^ 0x46];
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            s = EntityEnderman.I[0x44 ^ 0x48];
        }
        return s;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.ender_pearl;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        IBlockState heldBlockState;
        if (nbtTagCompound.hasKey(EntityEnderman.I[0x1F ^ 0x1B], 0xA2 ^ 0xAA)) {
            heldBlockState = Block.getBlockFromName(nbtTagCompound.getString(EntityEnderman.I[0x58 ^ 0x5D])).getStateFromMeta(nbtTagCompound.getShort(EntityEnderman.I[0xB9 ^ 0xBF]) & 51008 + 51925 - 39670 + 2272);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            heldBlockState = Block.getBlockById(nbtTagCompound.getShort(EntityEnderman.I[0xA9 ^ 0xAE])).getStateFromMeta(nbtTagCompound.getShort(EntityEnderman.I[0x49 ^ 0x41]) & 41138 + 43665 - 64079 + 44811);
        }
        this.setHeldBlockState(heldBlockState);
    }
    
    public boolean isScreaming() {
        if (this.dataWatcher.getWatchableObjectByte(0x88 ^ 0x9A) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        attackingSpeedBoostModifierUUID = UUID.fromString(EntityEnderman.I["".length()]);
        attackingSpeedBoostModifier = new AttributeModifier(EntityEnderman.attackingSpeedBoostModifierUUID, EntityEnderman.I[" ".length()], 0.15000000596046448, "".length()).setSaved("".length() != 0);
        (carriableBlocks = Sets.newIdentityHashSet()).add(Blocks.grass);
        EntityEnderman.carriableBlocks.add(Blocks.dirt);
        EntityEnderman.carriableBlocks.add(Blocks.sand);
        EntityEnderman.carriableBlocks.add(Blocks.gravel);
        EntityEnderman.carriableBlocks.add(Blocks.yellow_flower);
        EntityEnderman.carriableBlocks.add(Blocks.red_flower);
        EntityEnderman.carriableBlocks.add(Blocks.brown_mushroom);
        EntityEnderman.carriableBlocks.add(Blocks.red_mushroom);
        EntityEnderman.carriableBlocks.add(Blocks.tnt);
        EntityEnderman.carriableBlocks.add(Blocks.cactus);
        EntityEnderman.carriableBlocks.add(Blocks.clay);
        EntityEnderman.carriableBlocks.add(Blocks.pumpkin);
        EntityEnderman.carriableBlocks.add(Blocks.melon_block);
        EntityEnderman.carriableBlocks.add(Blocks.mycelium);
    }
    
    public EntityEnderman(final World world) {
        super(world);
        this.setSize(0.6f, 2.9f);
        this.stepHeight = 1.0f;
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), new EntityAIAttackOnCollide(this, 1.0, (boolean)("".length() != 0)));
        this.tasks.addTask(0x10 ^ 0x17, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0xA1 ^ 0xA9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x22 ^ 0x2A, new EntityAILookIdle(this));
        this.tasks.addTask(0x48 ^ 0x42, new AIPlaceBlock(this));
        this.tasks.addTask(0x65 ^ 0x6E, new AITakeBlock(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new AIFindPlayer(this));
        this.targetTasks.addTask("   ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityEndermite.class, 0xAD ^ 0xA7, (boolean)(" ".length() != 0), (boolean)("".length() != 0), (com.google.common.base.Predicate<?>)new Predicate<EntityEndermite>(this) {
            final EntityEnderman this$0;
            
            public boolean apply(final EntityEndermite entityEndermite) {
                return entityEndermite.isSpawnedByPlayer();
            }
            
            public boolean apply(final Object o) {
                return this.apply((EntityEndermite)o);
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }));
    }
    
    private boolean shouldAttackPlayer(final EntityPlayer entityPlayer) {
        final ItemStack itemStack = entityPlayer.inventory.armorInventory["   ".length()];
        if (itemStack != null && itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            return "".length() != 0;
        }
        final Vec3 normalize = entityPlayer.getLook(1.0f).normalize();
        final Vec3 vec3 = new Vec3(this.posX - entityPlayer.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - (entityPlayer.posY + entityPlayer.getEyeHeight()), this.posZ - entityPlayer.posZ);
        int n;
        if (normalize.dotProduct(vec3.normalize()) > 1.0 - 0.025 / vec3.lengthVector()) {
            n = (entityPlayer.canEntityBeSeen(this) ? 1 : 0);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < "  ".length()) {
                this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, (this.rand.nextDouble() - 0.5) * 2.0, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0, new int["".length()]);
                ++i;
            }
        }
        this.isJumping = ("".length() != 0);
        super.onLivingUpdate();
    }
    
    public void setHeldBlockState(final IBlockState blockState) {
        this.dataWatcher.updateObject(0x85 ^ 0x95, (short)(Block.getStateId(blockState) & 55006 + 45346 - 37711 + 2894));
    }
    
    static void access$2(final EntityEnderman entityEnderman, final boolean isAggressive) {
        entityEnderman.isAggressive = isAggressive;
    }
    
    public IBlockState getHeldBlockState() {
        return Block.getStateById(this.dataWatcher.getWatchableObjectShort(0x91 ^ 0x81) & 13524 + 6364 + 26778 + 18869);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x79 ^ 0x69, new Short((short)"".length()));
        this.dataWatcher.addObject(0xB2 ^ 0xA3, new Byte((byte)"".length()));
        this.dataWatcher.addObject(0x45 ^ 0x57, new Byte((byte)"".length()));
    }
    
    static boolean access$1(final EntityEnderman entityEnderman, final EntityPlayer entityPlayer) {
        return entityEnderman.shouldAttackPlayer(entityPlayer);
    }
    
    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        if (this.isScreaming() && !this.isAggressive && this.rand.nextInt(0xA3 ^ 0xC7) == 0) {
            this.setScreaming("".length() != 0);
        }
        if (this.worldObj.isDaytime()) {
            final float brightness = this.getBrightness(1.0f);
            if (brightness > 0.5f && this.worldObj.canSeeSky(new BlockPos(this)) && this.rand.nextFloat() * 30.0f < (brightness - 0.4f) * 2.0f) {
                this.setAttackTarget(null);
                this.setScreaming("".length() != 0);
                this.isAggressive = ("".length() != 0);
                this.teleportRandomly();
            }
        }
        super.updateAITasks();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        final IBlockState heldBlockState = this.getHeldBlockState();
        nbtTagCompound.setShort(EntityEnderman.I["  ".length()], (short)Block.getIdFromBlock(heldBlockState.getBlock()));
        nbtTagCompound.setShort(EntityEnderman.I["   ".length()], (short)heldBlockState.getBlock().getMetaFromState(heldBlockState));
    }
    
    static class AITakeBlock extends EntityAIBase
    {
        private EntityEnderman enderman;
        private static final String[] I;
        
        @Override
        public boolean shouldExecute() {
            int n;
            if (!this.enderman.worldObj.getGameRules().getBoolean(AITakeBlock.I["".length()])) {
                n = "".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else if (this.enderman.getHeldBlockState().getBlock().getMaterial() != Material.air) {
                n = "".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else if (this.enderman.getRNG().nextInt(0x2E ^ 0x3A) == 0) {
                n = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("7\u0000\u0017-\u00003\n\u0013\u0003\u001c=", "Zoujr");
        }
        
        static {
            I();
        }
        
        @Override
        public void updateTask() {
            final Random rng = this.enderman.getRNG();
            final World worldObj = this.enderman.worldObj;
            final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.enderman.posX - 2.0 + rng.nextDouble() * 4.0), MathHelper.floor_double(this.enderman.posY + rng.nextDouble() * 3.0), MathHelper.floor_double(this.enderman.posZ - 2.0 + rng.nextDouble() * 4.0));
            final IBlockState blockState = worldObj.getBlockState(blockPos);
            if (EntityEnderman.access$3().contains(blockState.getBlock())) {
                this.enderman.setHeldBlockState(blockState);
                worldObj.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
        
        public AITakeBlock(final EntityEnderman enderman) {
            this.enderman = enderman;
        }
    }
    
    static class AIFindPlayer extends EntityAINearestAttackableTarget
    {
        private int field_179450_h;
        private EntityEnderman enderman;
        private EntityPlayer player;
        private int field_179451_i;
        private static final String[] I;
        
        @Override
        public boolean shouldExecute() {
            final double targetDistance = this.getTargetDistance();
            final List<Entity> entitiesWithinAABB = this.taskOwner.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0, targetDistance), (com.google.common.base.Predicate<? super Entity>)this.targetEntitySelector);
            Collections.sort((List<Object>)entitiesWithinAABB, (Comparator<? super Object>)this.theNearestAttackableTargetSorter);
            if (entitiesWithinAABB.isEmpty()) {
                return "".length() != 0;
            }
            this.player = (EntityPlayer)entitiesWithinAABB.get("".length());
            return " ".length() != 0;
        }
        
        static {
            I();
        }
        
        @Override
        public void resetTask() {
            this.player = null;
            this.enderman.setScreaming("".length() != 0);
            this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityEnderman.access$0());
            super.resetTask();
        }
        
        @Override
        public void startExecuting() {
            this.field_179450_h = (0x65 ^ 0x60);
            this.field_179451_i = "".length();
        }
        
        @Override
        public void updateTask() {
            if (this.player != null) {
                if ((this.field_179450_h -= " ".length()) <= 0) {
                    this.targetEntity = this.player;
                    this.player = null;
                    super.startExecuting();
                    this.enderman.playSound(AIFindPlayer.I["".length()], 1.0f, 1.0f);
                    this.enderman.setScreaming(" ".length() != 0);
                    this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(EntityEnderman.access$0());
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            else {
                if (this.targetEntity != null) {
                    if (this.targetEntity instanceof EntityPlayer && EntityEnderman.access$1(this.enderman, (EntityPlayer)this.targetEntity)) {
                        if (this.targetEntity.getDistanceSqToEntity(this.enderman) < 16.0) {
                            this.enderman.teleportRandomly();
                        }
                        this.field_179451_i = "".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else if (this.targetEntity.getDistanceSqToEntity(this.enderman) > 256.0) {
                        final int field_179451_i = this.field_179451_i;
                        this.field_179451_i = field_179451_i + " ".length();
                        if (field_179451_i >= (0x65 ^ 0x7B) && this.enderman.teleportToEntity(this.targetEntity)) {
                            this.field_179451_i = "".length();
                        }
                    }
                }
                super.updateTask();
            }
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("=$\u0003V'>/\u0004\n/5%O\u000b619\u0004", "PKaxB");
        }
        
        public AIFindPlayer(final EntityEnderman enderman) {
            super(enderman, EntityPlayer.class, " ".length() != 0);
            this.enderman = enderman;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean continueExecuting() {
            if (this.player == null) {
                return super.continueExecuting();
            }
            if (!EntityEnderman.access$1(this.enderman, this.player)) {
                return "".length() != 0;
            }
            EntityEnderman.access$2(this.enderman, " ".length() != 0);
            this.enderman.faceEntity(this.player, 10.0f, 10.0f);
            return " ".length() != 0;
        }
    }
    
    static class AIPlaceBlock extends EntityAIBase
    {
        private static final String[] I;
        private EntityEnderman enderman;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public AIPlaceBlock(final EntityEnderman enderman) {
            this.enderman = enderman;
        }
        
        @Override
        public boolean shouldExecute() {
            int n;
            if (!this.enderman.worldObj.getGameRules().getBoolean(AIPlaceBlock.I["".length()])) {
                n = "".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else if (this.enderman.getHeldBlockState().getBlock().getMaterial() == Material.air) {
                n = "".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else if (this.enderman.getRNG().nextInt(1723 + 1929 - 2459 + 807) == 0) {
                n = " ".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I(",*\u0010\b9( \u0014&%&", "AErOK");
        }
        
        private boolean func_179474_a(final World world, final BlockPos blockPos, final Block block, final Block block2, final Block block3) {
            int n;
            if (!block.canPlaceBlockAt(world, blockPos)) {
                n = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (block2.getMaterial() != Material.air) {
                n = "".length();
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else if (block3.getMaterial() == Material.air) {
                n = "".length();
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                n = (block3.isFullCube() ? 1 : 0);
            }
            return n != 0;
        }
        
        static {
            I();
        }
        
        @Override
        public void updateTask() {
            final Random rng = this.enderman.getRNG();
            final World worldObj = this.enderman.worldObj;
            final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.enderman.posX - 1.0 + rng.nextDouble() * 2.0), MathHelper.floor_double(this.enderman.posY + rng.nextDouble() * 2.0), MathHelper.floor_double(this.enderman.posZ - 1.0 + rng.nextDouble() * 2.0));
            if (this.func_179474_a(worldObj, blockPos, this.enderman.getHeldBlockState().getBlock(), worldObj.getBlockState(blockPos).getBlock(), worldObj.getBlockState(blockPos.down()).getBlock())) {
                worldObj.setBlockState(blockPos, this.enderman.getHeldBlockState(), "   ".length());
                this.enderman.setHeldBlockState(Blocks.air.getDefaultState());
            }
        }
    }
}
