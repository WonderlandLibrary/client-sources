package net.minecraft.entity.monster;

import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.ai.*;

public class EntitySpider extends EntityMob
{
    private static final String[] I;
    
    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }
    
    static {
        I();
    }
    
    @Override
    public void setInWeb() {
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.5f;
    }
    
    @Override
    protected PathNavigate getNewNavigator(final World world) {
        return new PathNavigateClimber(this, world);
    }
    
    @Override
    protected String getHurtSound() {
        return EntitySpider.I[" ".length()];
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntitySpider.I["   ".length()], 0.15f, 1.0f);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x29 ^ 0x39, new Byte((byte)"".length()));
    }
    
    public EntitySpider(final World world) {
        super(world);
        this.setSize(1.4f, 0.9f);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("   ".length(), new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(0x2E ^ 0x2A, new AISpiderAttack(this, EntityPlayer.class));
        this.tasks.addTask(0x6C ^ 0x68, new AISpiderAttack(this, EntityIronGolem.class));
        this.tasks.addTask(0x9D ^ 0x98, new EntityAIWander(this, 0.8));
        this.tasks.addTask(0xC5 ^ 0xC3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x9F ^ 0x99, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new AISpiderTarget<Object>(this, EntityPlayer.class));
        this.targetTasks.addTask("   ".length(), new AISpiderTarget<Object>(this, EntityIronGolem.class));
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected String getLivingSound() {
        return EntitySpider.I["".length()];
    }
    
    private static void I() {
        (I = new String[0x7D ^ 0x79])["".length()] = I("\n\u0019\u0000Z9\u0017\u001f\u0006\u00118I\u0005\u0003\r", "gvbtJ");
        EntitySpider.I[" ".length()] = I("\u001b\t!]\u0004\u0006\u000f'\u0016\u0005X\u0015\"\n", "vfCsw");
        EntitySpider.I["  ".length()] = I("\u001a*3^\u001f\u0007,5\u0015\u001eY!4\u0011\u0018\u001f", "wEQpl");
        EntitySpider.I["   ".length()] = I("?56\\\u000b\"30\u0017\n|) \u0017\b", "RZTrx");
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        super.dropFewItems(b, n);
        if (b && (this.rand.nextInt("   ".length()) == 0 || this.rand.nextInt(" ".length() + n) > 0)) {
            this.dropItem(Items.spider_eye, " ".length());
        }
    }
    
    public boolean isBesideClimbableBlock() {
        if ((this.dataWatcher.getWatchableObjectByte(0x99 ^ 0x89) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, (IEntityLivingData)onInitialSpawn);
        if (this.worldObj.rand.nextInt(0xD8 ^ 0xBC) == 0) {
            final EntitySkeleton entitySkeleton = new EntitySkeleton(this.worldObj);
            entitySkeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entitySkeleton.onInitialSpawn(difficultyInstance, null);
            this.worldObj.spawnEntityInWorld(entitySkeleton);
            entitySkeleton.mountEntity(this);
        }
        if (onInitialSpawn == null) {
            onInitialSpawn = new GroupData();
            if (this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1f * difficultyInstance.getClampedAdditionalDifficulty()) {
                ((GroupData)onInitialSpawn).func_111104_a(this.worldObj.rand);
            }
        }
        if (onInitialSpawn instanceof GroupData) {
            final int potionEffectId = ((GroupData)onInitialSpawn).potionEffectId;
            if (potionEffectId > 0 && Potion.potionTypes[potionEffectId] != null) {
                this.addPotionEffect(new PotionEffect(potionEffectId, 1921394854 + 2092344984 - 2054649424 + 188393233));
            }
        }
        return (IEntityLivingData)onInitialSpawn;
    }
    
    public void setBesideClimbableBlock(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x4D ^ 0x5D);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | " ".length());
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            b2 = (byte)(watchableObjectByte & -"  ".length());
        }
        this.dataWatcher.updateObject(0x80 ^ 0x90, b2);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    public float getEyeHeight() {
        return 0.65f;
    }
    
    @Override
    public boolean isPotionApplicable(final PotionEffect potionEffect) {
        int n;
        if (potionEffect.getPotionID() == Potion.poison.id) {
            n = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            n = (super.isPotionApplicable(potionEffect) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.string;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    protected String getDeathSound() {
        return EntitySpider.I["  ".length()];
    }
    
    static class AISpiderAttack extends EntityAIAttackOnCollide
    {
        @Override
        protected double func_179512_a(final EntityLivingBase entityLivingBase) {
            return 4.0f + entityLivingBase.width;
        }
        
        public AISpiderAttack(final EntitySpider entitySpider, final Class<? extends Entity> clazz) {
            super(entitySpider, clazz, 1.0, " ".length() != 0);
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean continueExecuting() {
            if (this.attacker.getBrightness(1.0f) >= 0.5f && this.attacker.getRNG().nextInt(0x6C ^ 0x8) == 0) {
                this.attacker.setAttackTarget(null);
                return "".length() != 0;
            }
            return super.continueExecuting();
        }
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int potionEffectId;
        
        public void func_111104_a(final Random random) {
            final int nextInt = random.nextInt(0xC2 ^ 0xC7);
            if (nextInt <= " ".length()) {
                this.potionEffectId = Potion.moveSpeed.id;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (nextInt <= "  ".length()) {
                this.potionEffectId = Potion.damageBoost.id;
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (nextInt <= "   ".length()) {
                this.potionEffectId = Potion.regeneration.id;
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else if (nextInt <= (0xA3 ^ 0xA7)) {
                this.potionEffectId = Potion.invisibility.id;
            }
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
    }
    
    static class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget
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
                if (1 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public AISpiderTarget(final EntitySpider entitySpider, final Class<T> clazz) {
            super(entitySpider, clazz, " ".length() != 0);
        }
        
        @Override
        public boolean shouldExecute() {
            int n;
            if (this.taskOwner.getBrightness(1.0f) >= 0.5f) {
                n = "".length();
                "".length();
                if (!true) {
                    throw null;
                }
            }
            else {
                n = (super.shouldExecute() ? 1 : 0);
            }
            return n != 0;
        }
    }
}
