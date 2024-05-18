package net.minecraft.entity.passive;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;

public class EntitySquid extends EntityWaterMob
{
    public float prevSquidRotation;
    private float randomMotionVecY;
    private float rotationVelocity;
    public float lastTentacleAngle;
    public float tentacleAngle;
    private float randomMotionVecX;
    private float randomMotionSpeed;
    public float squidYaw;
    public float prevSquidPitch;
    public float prevSquidYaw;
    public float squidRotation;
    private float field_70871_bB;
    public float squidPitch;
    private float randomMotionVecZ;
    
    static boolean access$0(final EntitySquid entitySquid) {
        return entitySquid.inWater;
    }
    
    @Override
    protected String getLivingSound() {
        return null;
    }
    
    @Override
    protected String getHurtSound() {
        return null;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt("   ".length() + n) + " ".length();
        int i = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < n2) {
            this.entityDropItem(new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLACK.getDyeDamage()), 0.0f);
            ++i;
        }
    }
    
    public EntitySquid(final World world) {
        super(world);
        this.setSize(0.95f, 0.95f);
        this.rand.setSeed(" ".length() + this.getEntityId());
        this.rotationVelocity = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
        this.tasks.addTask("".length(), new AIMoveRandom(this));
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;
        if (this.squidRotation > 6.283185307179586) {
            if (this.worldObj.isRemote) {
                this.squidRotation = 6.2831855f;
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else {
                this.squidRotation -= 6.283185307179586;
                if (this.rand.nextInt(0xAA ^ 0xA0) == 0) {
                    this.rotationVelocity = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
                }
                this.worldObj.setEntityState(this, (byte)(0x2A ^ 0x39));
            }
        }
        if (this.inWater) {
            if (this.squidRotation < 3.1415927f) {
                final float n = this.squidRotation / 3.1415927f;
                this.tentacleAngle = MathHelper.sin(n * n * 3.1415927f) * 3.1415927f * 0.25f;
                if (n > 0.75) {
                    this.randomMotionSpeed = 1.0f;
                    this.field_70871_bB = 1.0f;
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
                else {
                    this.field_70871_bB *= 0.8f;
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
            }
            else {
                this.tentacleAngle = 0.0f;
                this.randomMotionSpeed *= 0.9f;
                this.field_70871_bB *= 0.99f;
            }
            if (!this.worldObj.isRemote) {
                this.motionX = this.randomMotionVecX * this.randomMotionSpeed;
                this.motionY = this.randomMotionVecY * this.randomMotionSpeed;
                this.motionZ = this.randomMotionVecZ * this.randomMotionSpeed;
            }
            final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-(float)MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0f / 3.1415927f - this.renderYawOffset) * 0.1f;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw += (float)(3.141592653589793 * this.field_70871_bB * 1.5);
            this.squidPitch += (-(float)MathHelper.func_181159_b(sqrt_double, this.motionY) * 180.0f / 3.1415927f - this.squidPitch) * 0.1f;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927f * 0.25f;
            if (!this.worldObj.isRemote) {
                this.motionX = 0.0;
                this.motionY -= 0.08;
                this.motionY *= 0.9800000190734863;
                this.motionZ = 0.0;
            }
            this.squidPitch += (float)((-90.0f - this.squidPitch) * 0.02);
        }
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected String getDeathSound() {
        return null;
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (this.posY > 45.0 && this.posY < this.worldObj.func_181545_F() && super.getCanSpawnHere()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    public void func_175568_b(final float randomMotionVecX, final float randomMotionVecY, final float randomMotionVecZ) {
        this.randomMotionVecX = randomMotionVecX;
        this.randomMotionVecY = randomMotionVecY;
        this.randomMotionVecZ = randomMotionVecZ;
    }
    
    @Override
    protected Item getDropItem() {
        return null;
    }
    
    public boolean func_175567_n() {
        if (this.randomMotionVecX == 0.0f && this.randomMotionVecY == 0.0f && this.randomMotionVecZ == 0.0f) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0xC ^ 0x1F)) {
            this.squidRotation = 0.0f;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public boolean isInWater() {
        return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -0.6000000238418579, 0.0), Material.water, this);
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.5f;
    }
    
    static class AIMoveRandom extends EntityAIBase
    {
        private EntitySquid squid;
        
        public AIMoveRandom(final EntitySquid squid) {
            this.squid = squid;
        }
        
        @Override
        public boolean shouldExecute() {
            return " ".length() != 0;
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
                if (2 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void updateTask() {
            if (this.squid.getAge() > (0x2F ^ 0x4B)) {
                this.squid.func_175568_b(0.0f, 0.0f, 0.0f);
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else if (this.squid.getRNG().nextInt(0x73 ^ 0x41) == 0 || !EntitySquid.access$0(this.squid) || !this.squid.func_175567_n()) {
                final float n = this.squid.getRNG().nextFloat() * 3.1415927f * 2.0f;
                this.squid.func_175568_b(MathHelper.cos(n) * 0.2f, -0.1f + this.squid.getRNG().nextFloat() * 0.2f, MathHelper.sin(n) * 0.2f);
            }
        }
    }
}
