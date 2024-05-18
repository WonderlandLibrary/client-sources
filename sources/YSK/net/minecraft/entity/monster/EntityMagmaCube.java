package net.minecraft.entity.monster;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class EntityMagmaCube extends EntitySlime
{
    private static final String[] I;
    
    @Override
    protected String getJumpSound() {
        String s;
        if (this.getSlimeSize() > " ".length()) {
            s = EntityMagmaCube.I["".length()];
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            s = EntityMagmaCube.I[" ".length()];
        }
        return s;
    }
    
    @Override
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.FLAME;
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected int getAttackStrength() {
        return super.getAttackStrength() + "  ".length();
    }
    
    @Override
    protected boolean makesSoundOnLand() {
        return " ".length() != 0;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 3545809 + 7999591 + 2599908 + 1583572;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.magma_cream;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
    
    @Override
    protected boolean canDamagePlayer() {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("#\u0001\u0013h\u001c/\t\u001c'\u0012;\f\u0014h\u0013'\t", "NnqFq");
        EntityMagmaCube.I[" ".length()] = I("+\u001f5F\t'\u0017:\t\u00073\u00122F\u0017+\u0011;\u0004", "FpWhd");
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.42f + this.getSlimeSize() * 0.1f;
        this.isAirBorne = (" ".length() != 0);
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    public boolean isNotColliding() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isBurning() {
        return "".length() != 0;
    }
    
    @Override
    protected void alterSquishAmount() {
        this.squishAmount *= 0.9f;
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public EntityMagmaCube(final World world) {
        super(world);
        this.isImmuneToFire = (" ".length() != 0);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null && this.getSlimeSize() > " ".length()) {
            int n2 = this.rand.nextInt(0x2C ^ 0x28) - "  ".length();
            if (n > 0) {
                n2 += this.rand.nextInt(n + " ".length());
            }
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < n2) {
                this.dropItem(dropItem, " ".length());
                ++i;
            }
        }
    }
    
    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * (0xB8 ^ 0xBC);
    }
    
    @Override
    protected void handleJumpLava() {
        this.motionY = 0.22f + this.getSlimeSize() * 0.05f;
        this.isAirBorne = (" ".length() != 0);
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.getSlimeSize() * "   ".length();
    }
    
    @Override
    protected EntitySlime createInstance() {
        return new EntityMagmaCube(this.worldObj);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
