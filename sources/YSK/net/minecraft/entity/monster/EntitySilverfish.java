package net.minecraft.entity.monster;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import java.util.*;

public class EntitySilverfish extends EntityMob
{
    private static final String[] I;
    private AISummonSilverfish summonSilverfish;
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        float blockPathWeight;
        if (this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.stone) {
            blockPathWeight = 10.0f;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            blockPathWeight = super.getBlockPathWeight(blockPos);
        }
        return blockPathWeight;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (!super.getCanSpawnHere()) {
            return "".length() != 0;
        }
        if (this.worldObj.getClosestPlayerToEntity(this, 5.0) == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (damageSource instanceof EntityDamageSource || damageSource == DamageSource.magic) {
            this.summonSilverfish.func_179462_f();
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    @Override
    protected String getHurtSound() {
        return EntitySilverfish.I[" ".length()];
    }
    
    @Override
    protected Item getDropItem() {
        return null;
    }
    
    @Override
    public double getYOffset() {
        return 0.2;
    }
    
    public EntitySilverfish(final World world) {
        super(world);
        this.setSize(0.4f, 0.3f);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("   ".length(), this.summonSilverfish = new AISummonSilverfish(this));
        this.tasks.addTask(0x8D ^ 0x89, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, (boolean)("".length() != 0)));
        this.tasks.addTask(0x3E ^ 0x3B, new AIHideInStone(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)(" ".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return " ".length() != 0;
    }
    
    static {
        I();
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected String getLivingSound() {
        return EntitySilverfish.I["".length()];
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
    }
    
    @Override
    public float getEyeHeight() {
        return 0.1f;
    }
    
    @Override
    protected String getDeathSound() {
        return EntitySilverfish.I["  ".length()];
    }
    
    private static void I() {
        (I = new String[0x28 ^ 0x2C])["".length()] = I("?+\ti&;(\u001d\"'4-\u0018/{!%\u0012", "RDkGU");
        EntitySilverfish.I[" ".length()] = I(";\u0004-Y\u0012?\u00079\u0012\u00130\u0002<\u001fO>\u0002;", "VkOwa");
        EntitySilverfish.I["  ".length()] = I(",:!y\u0006(952\u0007'<0?[*</;", "AUCWu");
        EntitySilverfish.I["   ".length()] = I("8\u0002\u0012y\u000b<\u0001\u00062\n3\u0004\u0003?V&\u0019\u0015'", "UmpWx");
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntitySilverfish.I["   ".length()], 0.15f, 1.0f);
    }
    
    static class AIHideInStone extends EntityAIWander
    {
        private boolean field_179484_c;
        private EnumFacing facing;
        private final EntitySilverfish field_179485_a;
        
        @Override
        public boolean continueExecuting() {
            int n;
            if (this.field_179484_c) {
                n = "".length();
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                n = (super.continueExecuting() ? 1 : 0);
            }
            return n != 0;
        }
        
        public AIHideInStone(final EntitySilverfish field_179485_a) {
            super(field_179485_a, 1.0, 0x20 ^ 0x2A);
            this.field_179485_a = field_179485_a;
            this.setMutexBits(" ".length());
        }
        
        @Override
        public void startExecuting() {
            if (!this.field_179484_c) {
                super.startExecuting();
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else {
                final World worldObj = this.field_179485_a.worldObj;
                final BlockPos offset = new BlockPos(this.field_179485_a.posX, this.field_179485_a.posY + 0.5, this.field_179485_a.posZ).offset(this.facing);
                final IBlockState blockState = worldObj.getBlockState(offset);
                if (BlockSilverfish.canContainSilverfish(blockState)) {
                    worldObj.setBlockState(offset, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.forModelBlock(blockState)), "   ".length());
                    this.field_179485_a.spawnExplosionParticle();
                    this.field_179485_a.setDead();
                }
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.field_179485_a.getAttackTarget() != null) {
                return "".length() != 0;
            }
            if (!this.field_179485_a.getNavigator().noPath()) {
                return "".length() != 0;
            }
            final Random rng = this.field_179485_a.getRNG();
            if (rng.nextInt(0x97 ^ 0x9D) == 0) {
                this.facing = EnumFacing.random(rng);
                if (BlockSilverfish.canContainSilverfish(this.field_179485_a.worldObj.getBlockState(new BlockPos(this.field_179485_a.posX, this.field_179485_a.posY + 0.5, this.field_179485_a.posZ).offset(this.facing)))) {
                    this.field_179484_c = (" ".length() != 0);
                    return " ".length() != 0;
                }
            }
            this.field_179484_c = ("".length() != 0);
            return super.shouldExecute();
        }
    }
    
    static class AISummonSilverfish extends EntityAIBase
    {
        private EntitySilverfish silverfish;
        private int field_179463_b;
        private static final String[] I;
        
        @Override
        public void updateTask() {
            this.field_179463_b -= " ".length();
            if (this.field_179463_b <= 0) {
                final World worldObj = this.silverfish.worldObj;
                final Random rng = this.silverfish.getRNG();
                final BlockPos blockPos = new BlockPos(this.silverfish);
                int length = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (length <= (0x53 ^ 0x56) && length >= -(0x39 ^ 0x3C)) {
                    int length2 = "".length();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                    while (length2 <= (0xB5 ^ 0xBF) && length2 >= -(0xCC ^ 0xC6)) {
                        int length3 = "".length();
                        "".length();
                        if (1 < 1) {
                            throw null;
                        }
                        while (length3 <= (0x63 ^ 0x69) && length3 >= -(0xD ^ 0x7)) {
                            final BlockPos add = blockPos.add(length2, length, length3);
                            final IBlockState blockState = worldObj.getBlockState(add);
                            if (blockState.getBlock() == Blocks.monster_egg) {
                                if (worldObj.getGameRules().getBoolean(AISummonSilverfish.I["".length()])) {
                                    worldObj.destroyBlock(add, " ".length() != 0);
                                    "".length();
                                    if (4 != 4) {
                                        throw null;
                                    }
                                }
                                else {
                                    worldObj.setBlockState(add, blockState.getValue(BlockSilverfish.VARIANT).getModelBlock(), "   ".length());
                                }
                                if (rng.nextBoolean()) {
                                    return;
                                }
                            }
                            int n;
                            if (length3 <= 0) {
                                n = " ".length() - length3;
                                "".length();
                                if (0 >= 2) {
                                    throw null;
                                }
                            }
                            else {
                                n = "".length() - length3;
                            }
                            length3 = n;
                        }
                        int n2;
                        if (length2 <= 0) {
                            n2 = " ".length() - length2;
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else {
                            n2 = "".length() - length2;
                        }
                        length2 = n2;
                    }
                    int n3;
                    if (length <= 0) {
                        n3 = " ".length() - length;
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                    else {
                        n3 = "".length() - length;
                    }
                    length = n3;
                }
            }
        }
        
        public AISummonSilverfish(final EntitySilverfish silverfish) {
            this.silverfish = silverfish;
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.field_179463_b > 0) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        static {
            I();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u001e'4\u000b!\u001a-0%=\u0014", "sHVLS");
        }
        
        public void func_179462_f() {
            if (this.field_179463_b == 0) {
                this.field_179463_b = (0xBE ^ 0xAA);
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
                if (1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
