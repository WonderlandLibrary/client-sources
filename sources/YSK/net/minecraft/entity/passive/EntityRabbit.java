package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.ai.*;

public class EntityRabbit extends EntityAnimal
{
    private int field_175540_bm;
    private static final String[] I;
    private boolean field_175537_bp;
    private AIAvoidEntity<EntityWolf> aiAvoidWolves;
    private int carrotTicks;
    private EntityPlayer field_175543_bt;
    private boolean field_175536_bo;
    private int field_175535_bn;
    private EnumMoveType moveType;
    private int currentMoveTypeDuration;
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        int rabbitType = this.rand.nextInt(0x31 ^ 0x37);
        int n = "".length();
        if (onInitialSpawn instanceof RabbitTypeData) {
            rabbitType = ((RabbitTypeData)onInitialSpawn).typeData;
            n = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            onInitialSpawn = new RabbitTypeData(rabbitType);
        }
        this.setRabbitType(rabbitType);
        if (n != 0) {
            this.setGrowingAge(-(9671 + 21532 - 26689 + 19486));
        }
        return onInitialSpawn;
    }
    
    static {
        I();
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == " ".length()) {
            this.createRunningParticles();
            this.field_175535_bn = (0x5 ^ 0xF);
            this.field_175540_bm = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    private static void I() {
        (I = new String[0x2D ^ 0x27])["".length()] = I("\u001d6\t\u0014\u0002;\u0003\u0012\u0006\u000e", "OWkvk");
        EntityRabbit.I[" ".length()] = I("/\f(6\u0001\u0003\u0011(<66\n981", "bcZSB");
        EntityRabbit.I["  ".length()] = I(":\u000f\u0010\u000b\u0018\u001c:\u000b\u0019\u0014", "hnriq");
        EntityRabbit.I["   ".length()] = I("\u0015\u0007\u0005\u0015\u00069\u001a\u0005\u001f1\f\u0001\u0014\u001b6", "XhwpE");
        EntityRabbit.I[0xB2 ^ 0xB6] = I("\"\"2h\u0006./2/\u0000a%?6", "OMPFt");
        EntityRabbit.I[0x6E ^ 0x6B] = I("\u000b7 G\u0001\u0007: \u0000\u0007H1&\u0005\u0016", "fXBis");
        EntityRabbit.I[0x68 ^ 0x6E] = I(")*\nJ5%'\n\r3j-\u001d\u00163", "DEhdG");
        EntityRabbit.I[0xB9 ^ 0xBE] = I("\"\u001a\u0015X\u001f.\u0017\u0015\u001f\u0019a\u0011\u0012\u0017\u0019'", "Ouwvm");
        EntityRabbit.I[0x60 ^ 0x68] = I("=,5v1$76;;", "PCWXP");
        EntityRabbit.I[0x97 ^ 0x9E] = I(".(\u0005:\u00002h::\u0018'#\u0003\u0011\u0001%(\b}\u001a*+\u0014", "KFqSt");
    }
    
    public int getRabbitType() {
        return this.dataWatcher.getWatchableObjectByte(0x88 ^ 0x9A);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x48 ^ 0x5A, (byte)"".length());
    }
    
    private void func_175517_cu() {
        this.updateMoveTypeDuration();
        this.func_175520_cs();
    }
    
    protected void createEatingParticles() {
        final World worldObj = this.worldObj;
        final EnumParticleTypes block_DUST = EnumParticleTypes.BLOCK_DUST;
        final double n = this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width;
        final double n2 = this.posY + 0.5 + this.rand.nextFloat() * this.height;
        final double n3 = this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width;
        final double n4 = 0.0;
        final double n5 = 0.0;
        final double n6 = 0.0;
        final int[] array = new int[" ".length()];
        array["".length()] = Block.getStateId(Blocks.carrots.getStateFromMeta(0x9F ^ 0x98));
        worldObj.spawnParticle(block_DUST, n, n2, n3, n4, n5, n6, array);
        this.carrotTicks = (0x3E ^ 0x5A);
    }
    
    private void func_175520_cs() {
        ((RabbitJumpHelper)this.jumpHelper).func_180066_a("".length() != 0);
    }
    
    @Override
    public void spawnRunningParticles() {
    }
    
    public boolean func_175523_cj() {
        return this.field_175536_bo;
    }
    
    @Override
    protected String getDeathSound() {
        return EntityRabbit.I[0x9E ^ 0x99];
    }
    
    static boolean access$1(final EntityRabbit entityRabbit) {
        return entityRabbit.isCarrotEaten();
    }
    
    public void updateAITasks() {
        if (this.moveHelper.getSpeed() > 0.8) {
            this.setMoveType(EnumMoveType.SPRINT);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else if (this.moveType != EnumMoveType.ATTACK) {
            this.setMoveType(EnumMoveType.HOP);
        }
        if (this.currentMoveTypeDuration > 0) {
            this.currentMoveTypeDuration -= " ".length();
        }
        if (this.carrotTicks > 0) {
            this.carrotTicks -= this.rand.nextInt("   ".length());
            if (this.carrotTicks < 0) {
                this.carrotTicks = "".length();
            }
        }
        if (this.onGround) {
            if (!this.field_175537_bp) {
                this.setJumping("".length() != 0, EnumMoveType.NONE);
                this.func_175517_cu();
            }
            if (this.getRabbitType() == (0xC1 ^ 0xA2) && this.currentMoveTypeDuration == 0) {
                final EntityLivingBase attackTarget = this.getAttackTarget();
                if (attackTarget != null && this.getDistanceSqToEntity(attackTarget) < 16.0) {
                    this.calculateRotationYaw(attackTarget.posX, attackTarget.posZ);
                    this.moveHelper.setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, this.moveHelper.getSpeed());
                    this.doMovementAction(EnumMoveType.ATTACK);
                    this.field_175537_bp = (" ".length() != 0);
                }
            }
            final RabbitJumpHelper rabbitJumpHelper = (RabbitJumpHelper)this.jumpHelper;
            if (!rabbitJumpHelper.getIsJumping()) {
                if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
                    final PathEntity path = this.navigator.getPath();
                    Vec3 position = new Vec3(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        position = path.getPosition(this);
                    }
                    this.calculateRotationYaw(position.xCoord, position.zCoord);
                    this.doMovementAction(this.moveType);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
            }
            else if (!rabbitJumpHelper.func_180065_d()) {
                this.func_175518_cr();
            }
        }
        this.field_175537_bp = this.onGround;
    }
    
    public void setMovementSpeed(final double speed) {
        this.getNavigator().setSpeed(speed);
        this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), speed);
    }
    
    private void updateMoveTypeDuration() {
        this.currentMoveTypeDuration = this.getMoveTypeDuration();
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
    
    public void setRabbitType(final int n) {
        if (n == (0xDE ^ 0xBD)) {
            this.tasks.removeTask(this.aiAvoidWolves);
            this.tasks.addTask(0x90 ^ 0x94, new AIEvilAttack(this));
            this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
            this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
            this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityWolf.class, (boolean)(" ".length() != 0)));
            if (!this.hasCustomName()) {
                this.setCustomNameTag(StatCollector.translateToLocal(EntityRabbit.I[0x3 ^ 0xA]));
            }
        }
        this.dataWatcher.updateObject(0x91 ^ 0x83, (byte)n);
    }
    
    public void setJumping(final boolean b, final EnumMoveType enumMoveType) {
        super.setJumping(b);
        if (!b) {
            if (this.moveType == EnumMoveType.ATTACK) {
                this.moveType = EnumMoveType.HOP;
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
        }
        else {
            this.setMovementSpeed(1.5 * enumMoveType.getSpeed());
            this.playSound(this.getJumpingSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
        }
        this.field_175536_bo = b;
    }
    
    public void doMovementAction(final EnumMoveType enumMoveType) {
        this.setJumping(" ".length() != 0, enumMoveType);
        this.field_175535_bn = enumMoveType.func_180073_d();
        this.field_175540_bm = "".length();
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (this.getRabbitType() == (0x73 ^ 0x10)) {
            this.playSound(EntityRabbit.I[0x2 ^ 0xA], 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0f);
        }
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }
    
    private void calculateRotationYaw(final double n, final double n2) {
        this.rotationYaw = (float)(MathHelper.func_181159_b(n2 - this.posZ, n - this.posX) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    protected int getMoveTypeDuration() {
        return this.moveType.getDuration();
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemStack) {
        if (itemStack != null && this.isRabbitBreedingItem(itemStack.getItem())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void func_175518_cr() {
        ((RabbitJumpHelper)this.jumpHelper).func_180066_a(" ".length() != 0);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.field_175540_bm != this.field_175535_bn) {
            if (this.field_175540_bm == 0 && !this.worldObj.isRemote) {
                this.worldObj.setEntityState(this, (byte)" ".length());
            }
            this.field_175540_bm += " ".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else if (this.field_175535_bn != 0) {
            this.field_175540_bm = "".length();
            this.field_175535_bn = "".length();
        }
    }
    
    public float func_175521_o(final float n) {
        float n2;
        if (this.field_175535_bn == 0) {
            n2 = 0.0f;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n2 = (this.field_175540_bm + n) / this.field_175535_bn;
        }
        return n2;
    }
    
    @Override
    protected void addRandomDrop() {
        this.entityDropItem(new ItemStack(Items.rabbit_foot, " ".length()), 0.0f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityRabbit.I["".length()], this.getRabbitType());
        nbtTagCompound.setInteger(EntityRabbit.I[" ".length()], this.carrotTicks);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt("  ".length()) + this.rand.nextInt(" ".length() + n);
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < n2) {
            this.dropItem(Items.rabbit_hide, " ".length());
            ++i;
        }
        final int nextInt = this.rand.nextInt("  ".length());
        int j = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (j < nextInt) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_rabbit, " ".length());
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                this.dropItem(Items.rabbit, " ".length());
            }
            ++j;
        }
    }
    
    @Override
    protected float getJumpUpwardsMotion() {
        float func_180074_b;
        if (this.moveHelper.isUpdating() && this.moveHelper.getY() > this.posY + 0.5) {
            func_180074_b = 0.5f;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            func_180074_b = this.moveType.func_180074_b();
        }
        return func_180074_b;
    }
    
    private boolean isCarrotEaten() {
        if (this.carrotTicks == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected String getLivingSound() {
        return EntityRabbit.I[0x74 ^ 0x71];
    }
    
    private boolean isRabbitBreedingItem(final Item item) {
        if (item != Items.carrot && item != Items.golden_carrot && item != Item.getItemFromBlock(Blocks.yellow_flower)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setRabbitType(nbtTagCompound.getInteger(EntityRabbit.I["  ".length()]));
        this.carrotTicks = nbtTagCompound.getInteger(EntityRabbit.I["   ".length()]);
    }
    
    @Override
    public int getTotalArmorValue() {
        int totalArmorValue;
        if (this.getRabbitType() == (0x3F ^ 0x5C)) {
            totalArmorValue = (0xA6 ^ 0xAE);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            totalArmorValue = super.getTotalArmorValue();
        }
        return totalArmorValue;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityRabbit.I[0xA9 ^ 0xAF];
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    public void setMoveType(final EnumMoveType moveType) {
        this.moveType = moveType;
    }
    
    public EntityRabbit(final World world) {
        super(world);
        this.field_175540_bm = "".length();
        this.field_175535_bn = "".length();
        this.field_175536_bo = ("".length() != 0);
        this.field_175537_bp = ("".length() != 0);
        this.currentMoveTypeDuration = "".length();
        this.moveType = EnumMoveType.HOP;
        this.carrotTicks = "".length();
        this.field_175543_bt = null;
        this.setSize(0.6f, 0.7f);
        this.jumpHelper = new RabbitJumpHelper(this);
        this.moveHelper = new RabbitMoveHelper(this);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.navigator.setHeightRequirement(2.5f);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask(" ".length(), new AIPanic(this, 1.33));
        this.tasks.addTask("  ".length(), new EntityAITempt(this, 1.0, Items.carrot, (boolean)("".length() != 0)));
        this.tasks.addTask("  ".length(), new EntityAITempt(this, 1.0, Items.golden_carrot, (boolean)("".length() != 0)));
        this.tasks.addTask("  ".length(), new EntityAITempt(this, 1.0, Item.getItemFromBlock(Blocks.yellow_flower), (boolean)("".length() != 0)));
        this.tasks.addTask("   ".length(), new EntityAIMate(this, 0.8));
        this.tasks.addTask(0x5D ^ 0x58, new AIRaidFarm(this));
        this.tasks.addTask(0x19 ^ 0x1C, new EntityAIWander(this, 0.6));
        this.tasks.addTask(0x90 ^ 0x9B, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.aiAvoidWolves = new AIAvoidEntity<EntityWolf>(this, EntityWolf.class, 16.0f, 1.33, 1.33);
        this.tasks.addTask(0xB3 ^ 0xB7, this.aiAvoidWolves);
        this.setMovementSpeed(0.0);
    }
    
    @Override
    public EntityRabbit createChild(final EntityAgeable entityAgeable) {
        final EntityRabbit entityRabbit = new EntityRabbit(this.worldObj);
        if (entityAgeable instanceof EntityRabbit) {
            final EntityRabbit entityRabbit2 = entityRabbit;
            int rabbitType;
            if (this.rand.nextBoolean()) {
                rabbitType = this.getRabbitType();
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else {
                rabbitType = ((EntityRabbit)entityAgeable).getRabbitType();
            }
            entityRabbit2.setRabbitType(rabbitType);
        }
        return entityRabbit;
    }
    
    protected String getJumpingSound() {
        return EntityRabbit.I[0x33 ^ 0x37];
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        int n2;
        if (this.isEntityInvulnerable(damageSource)) {
            n2 = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            n2 = (super.attackEntityFrom(damageSource, n) ? 1 : 0);
        }
        return n2 != 0;
    }
    
    static class AIRaidFarm extends EntityAIMoveToBlock
    {
        private boolean field_179498_d;
        private static final String[] I;
        private boolean field_179499_e;
        private final EntityRabbit field_179500_c;
        
        @Override
        public void updateTask() {
            super.updateTask();
            this.field_179500_c.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + " ".length(), this.destinationBlock.getZ() + 0.5, 10.0f, this.field_179500_c.getVerticalFaceSpeed());
            if (this.getIsAboveDestination()) {
                final World worldObj = this.field_179500_c.worldObj;
                final BlockPos up = this.destinationBlock.up();
                final IBlockState blockState = worldObj.getBlockState(up);
                final Block block = blockState.getBlock();
                if (this.field_179499_e && block instanceof BlockCarrot && blockState.getValue((IProperty<Integer>)BlockCarrot.AGE) == (0x99 ^ 0x9E)) {
                    worldObj.setBlockState(up, Blocks.air.getDefaultState(), "  ".length());
                    worldObj.destroyBlock(up, " ".length() != 0);
                    this.field_179500_c.createEatingParticles();
                }
                this.field_179499_e = ("".length() != 0);
                this.runDelay = (0xB8 ^ 0xB2);
            }
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                if (!this.field_179500_c.worldObj.getGameRules().getBoolean(AIRaidFarm.I["".length()])) {
                    return "".length() != 0;
                }
                this.field_179499_e = ("".length() != 0);
                this.field_179498_d = EntityRabbit.access$1(this.field_179500_c);
            }
            return super.shouldExecute();
        }
        
        static {
            I();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I(":\"\u0017?\u0006>(\u0013\u0011\u001a0", "WMuxt");
        }
        
        @Override
        public void resetTask() {
            super.resetTask();
        }
        
        @Override
        public void startExecuting() {
            super.startExecuting();
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
                if (-1 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean continueExecuting() {
            if (this.field_179499_e && super.continueExecuting()) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public AIRaidFarm(final EntityRabbit field_179500_c) {
            super(field_179500_c, 0.699999988079071, 0xD ^ 0x1D);
            this.field_179499_e = ("".length() != 0);
            this.field_179500_c = field_179500_c;
        }
        
        @Override
        protected boolean shouldMoveTo(final World world, BlockPos up) {
            if (world.getBlockState(up).getBlock() == Blocks.farmland) {
                up = up.up();
                final IBlockState blockState = world.getBlockState(up);
                if (blockState.getBlock() instanceof BlockCarrot && blockState.getValue((IProperty<Integer>)BlockCarrot.AGE) == (0x47 ^ 0x40) && this.field_179498_d && !this.field_179499_e) {
                    this.field_179499_e = (" ".length() != 0);
                    return " ".length() != 0;
                }
            }
            return "".length() != 0;
        }
    }
    
    static class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T>
    {
        private EntityRabbit entityInstance;
        
        public AIAvoidEntity(final EntityRabbit entityInstance, final Class<T> clazz, final float n, final double n2, final double n3) {
            super(entityInstance, clazz, n, n2, n3);
            this.entityInstance = entityInstance;
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
                if (2 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void updateTask() {
            super.updateTask();
        }
    }
    
    public class RabbitJumpHelper extends EntityJumpHelper
    {
        private EntityRabbit theEntity;
        private boolean field_180068_d;
        final EntityRabbit this$0;
        
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
                if (-1 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public RabbitJumpHelper(final EntityRabbit this$0, final EntityRabbit theEntity) {
            this.this$0 = this$0;
            super(theEntity);
            this.field_180068_d = ("".length() != 0);
            this.theEntity = theEntity;
        }
        
        public boolean getIsJumping() {
            return this.isJumping;
        }
        
        public boolean func_180065_d() {
            return this.field_180068_d;
        }
        
        public void func_180066_a(final boolean field_180068_d) {
            this.field_180068_d = field_180068_d;
        }
        
        @Override
        public void doJump() {
            if (this.isJumping) {
                this.theEntity.doMovementAction(EnumMoveType.STEP);
                this.isJumping = ("".length() != 0);
            }
        }
    }
    
    enum EnumMoveType
    {
        private static final EnumMoveType[] ENUM$VALUES;
        private final float speed;
        private final int field_180085_i;
        
        STEP(EnumMoveType.I["  ".length()], "  ".length(), 1.0f, 0.45f, 0x61 ^ 0x6F, 0xA2 ^ 0xAC), 
        HOP(EnumMoveType.I[" ".length()], " ".length(), 0.8f, 0.2f, 0x38 ^ 0x2C, 0xAD ^ 0xA7);
        
        private final float field_180077_g;
        private final int duration;
        
        ATTACK(EnumMoveType.I[0xC3 ^ 0xC7], 0x77 ^ 0x73, 2.0f, 0.7f, 0xB7 ^ 0xB0, 0x41 ^ 0x49), 
        SPRINT(EnumMoveType.I["   ".length()], "   ".length(), 1.75f, 0.4f, " ".length(), 0x31 ^ 0x39);
        
        private static final String[] I;
        
        NONE(EnumMoveType.I["".length()], "".length(), 0.0f, 0.0f, 0xA6 ^ 0xB8, " ".length());
        
        public int func_180073_d() {
            return this.field_180085_i;
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
                if (0 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public float func_180074_b() {
            return this.field_180077_g;
        }
        
        public float getSpeed() {
            return this.speed;
        }
        
        public int getDuration() {
            return this.duration;
        }
        
        private static void I() {
            (I = new String[0x52 ^ 0x57])["".length()] = I("!\u0000!\"", "oOogO");
            EnumMoveType.I[" ".length()] = I(";;3", "stcQS");
            EnumMoveType.I["  ".length()] = I("#\u00020;", "pVukj");
            EnumMoveType.I["   ".length()] = I("'\u0017*>% ", "tGxwk");
            EnumMoveType.I[0x58 ^ 0x5C] = I("(\u0011=)\b\"", "iEihK");
        }
        
        private EnumMoveType(final String s, final int n, final float speed, final float field_180077_g, final int duration, final int field_180085_i) {
            this.speed = speed;
            this.field_180077_g = field_180077_g;
            this.duration = duration;
            this.field_180085_i = field_180085_i;
        }
        
        static {
            I();
            final EnumMoveType[] enum$VALUES = new EnumMoveType[0x67 ^ 0x62];
            enum$VALUES["".length()] = EnumMoveType.NONE;
            enum$VALUES[" ".length()] = EnumMoveType.HOP;
            enum$VALUES["  ".length()] = EnumMoveType.STEP;
            enum$VALUES["   ".length()] = EnumMoveType.SPRINT;
            enum$VALUES[0x43 ^ 0x47] = EnumMoveType.ATTACK;
            ENUM$VALUES = enum$VALUES;
        }
    }
    
    public static class RabbitTypeData implements IEntityLivingData
    {
        public int typeData;
        
        public RabbitTypeData(final int typeData) {
            this.typeData = typeData;
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
                if (1 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class AIEvilAttack extends EntityAIAttackOnCollide
    {
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
                if (3 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public AIEvilAttack(final EntityRabbit entityRabbit) {
            super(entityRabbit, EntityLivingBase.class, 1.4, " ".length() != 0);
        }
        
        @Override
        protected double func_179512_a(final EntityLivingBase entityLivingBase) {
            return 4.0f + entityLivingBase.width;
        }
    }
    
    static class RabbitMoveHelper extends EntityMoveHelper
    {
        private EntityRabbit theEntity;
        
        public RabbitMoveHelper(final EntityRabbit theEntity) {
            super(theEntity);
            this.theEntity = theEntity;
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
                if (-1 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.theEntity.onGround && !this.theEntity.func_175523_cj()) {
                this.theEntity.setMovementSpeed(0.0);
            }
            super.onUpdateMoveHelper();
        }
    }
    
    static class AIPanic extends EntityAIPanic
    {
        private EntityRabbit theEntity;
        
        @Override
        public void updateTask() {
            super.updateTask();
            this.theEntity.setMovementSpeed(this.speed);
        }
        
        public AIPanic(final EntityRabbit theEntity, final double n) {
            super(theEntity, n);
            this.theEntity = theEntity;
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
    }
}
