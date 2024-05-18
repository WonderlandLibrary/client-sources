package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class EntityFX extends Entity
{
    private static final String[] I;
    protected int particleMaxAge;
    protected float particleGravity;
    protected TextureAtlasSprite particleIcon;
    protected float particleTextureJitterX;
    protected float particleGreen;
    public static double interpPosY;
    protected int particleTextureIndexY;
    protected float particleTextureJitterY;
    protected int particleAge;
    protected float particleScale;
    protected int particleTextureIndexX;
    protected float particleAlpha;
    protected float particleRed;
    protected float particleBlue;
    public static double interpPosZ;
    public static double interpPosX;
    
    public void nextTextureIndexX() {
        this.particleTextureIndexX += " ".length();
    }
    
    public void setRBGColorF(final float particleRed, final float particleGreen, final float particleBlue) {
        this.particleRed = particleRed;
        this.particleGreen = particleGreen;
        this.particleBlue = particleBlue;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return "".length() != 0;
    }
    
    public void setParticleTextureIndex(final int n) {
        if (this.getFXLayer() != 0) {
            throw new RuntimeException(EntityFX.I[" ".length()]);
        }
        this.particleTextureIndexX = n % (0x50 ^ 0x40);
        this.particleTextureIndexY = n / (0x51 ^ 0x41);
    }
    
    public float getRedColorF() {
        return this.particleRed;
    }
    
    public int getFXLayer() {
        return "".length();
    }
    
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        float minU = this.particleTextureIndexX / 16.0f;
        float maxU = minU + 0.0624375f;
        float minV = this.particleTextureIndexY / 16.0f;
        float maxV = minV + 0.0624375f;
        final float n7 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            minU = this.particleIcon.getMinU();
            maxU = this.particleIcon.getMaxU();
            minV = this.particleIcon.getMinV();
            maxV = this.particleIcon.getMaxV();
        }
        final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityFX.interpPosX);
        final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityFX.interpPosY);
        final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityFX.interpPosZ);
        final int brightnessForRender = this.getBrightnessForRender(n);
        final int n11 = brightnessForRender >> (0x15 ^ 0x5) & 65042 + 62868 - 114942 + 52567;
        final int n12 = brightnessForRender & 56304 + 55185 - 48115 + 2161;
        worldRenderer.pos(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7).tex(maxU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7).tex(maxU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7).tex(minU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7).tex(minU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityFX multipleParticleScaleBy(final float n) {
        this.setSize(0.2f * n, 0.2f * n);
        this.particleScale *= n;
        return this;
    }
    
    @Override
    protected void entityInit() {
    }
    
    public float getBlueColorF() {
        return this.particleBlue;
    }
    
    public EntityFX multiplyVelocity(final float n) {
        this.motionX *= n;
        this.motionY = (this.motionY - 0.10000000149011612) * n + 0.10000000149011612;
        this.motionZ *= n;
        return this;
    }
    
    protected EntityFX(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.particleAlpha = 1.0f;
        this.setSize(0.2f, 0.2f);
        this.setPosition(n, n2, n3);
        this.prevPosX = n;
        this.lastTickPosX = n;
        this.prevPosY = n2;
        this.lastTickPosY = n2;
        this.prevPosZ = n3;
        this.lastTickPosZ = n3;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
        this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.particleMaxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.particleAge = "".length();
    }
    
    public float getGreenColorF() {
        return this.particleGreen;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public float getAlpha() {
        return this.particleAlpha;
    }
    
    public void setParticleIcon(final TextureAtlasSprite particleIcon) {
        if (this.getFXLayer() != " ".length()) {
            throw new RuntimeException(EntityFX.I["".length()]);
        }
        this.particleIcon = particleIcon;
        "".length();
        if (-1 >= 4) {
            throw null;
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getClass().getSimpleName()) + EntityFX.I["  ".length()] + this.posX + EntityFX.I["   ".length()] + this.posY + EntityFX.I[0x39 ^ 0x3D] + this.posZ + EntityFX.I[0x77 ^ 0x72] + this.particleRed + EntityFX.I[0xAD ^ 0xAB] + this.particleGreen + EntityFX.I[0x22 ^ 0x25] + this.particleBlue + EntityFX.I[0x56 ^ 0x5E] + this.particleAlpha + EntityFX.I[0x8E ^ 0x87] + this.particleAge;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void setAlphaF(final float particleAlpha) {
        if (this.particleAlpha == 1.0f && particleAlpha < 1.0f) {
            Minecraft.getMinecraft().effectRenderer.moveToAlphaLayer(this);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (this.particleAlpha < 1.0f && particleAlpha == 1.0f) {
            Minecraft.getMinecraft().effectRenderer.moveToNoAlphaLayer(this);
        }
        this.particleAlpha = particleAlpha;
    }
    
    public EntityFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this(world, n, n2, n3);
        this.motionX = n4 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.motionY = n5 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.motionZ = n6 + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        final float n7 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / sqrt_double * n7 * 0.4000000059604645;
        this.motionY = this.motionY / sqrt_double * n7 * 0.4000000059604645 + 0.10000000149011612;
        this.motionZ = this.motionZ / sqrt_double * n7 * 0.4000000059604645;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final int particleAge = this.particleAge;
        this.particleAge = particleAge + " ".length();
        if (particleAge >= this.particleMaxAge) {
            this.setDead();
        }
        this.motionY -= 0.04 * this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    private static void I() {
        (I = new String[0x97 ^ 0x9D])["".length()] = I("\u0010\u0000\u001c1 0\nJ3-5\u0002J$#y>\u000b\"80\r\u00065b*\u000b\u001e\u0004)!BJ%?<N\t?#+\n\u0003>--\u000bJ=)-\u0006\u00054?", "YnjPL");
        EntityFX.I[" ".length()] = I("\u0002\u0014\u0017\u0014\u001d\"\u001eA\u0016\u0010'\u0016A\u0001\u001ek*\u0000\u0007\u0005\"\u0019\r\u0010_8\u001f\u00158\u00188\u00195\u0010\t", "Kzauq");
        EntityFX.I["  ".length()] = I("Mk7\u001c1Ac", "aKgsB");
        EntityFX.I["   ".length()] = I("J", "fYSLI");
        EntityFX.I[0x3B ^ 0x3F] = I("x", "TQVLl");
        EntityFX.I[0xBC ^ 0xB9] = I("\\TH\u0010,79Hj", "uxhBk");
        EntityFX.I[0xBD ^ 0xBB] = I("k", "GevFw");
        EntityFX.I[0x57 ^ 0x50] = I("\\", "pxjDm");
        EntityFX.I[0xB6 ^ 0xBE] = I("y", "UCNtG");
        EntityFX.I[0xB0 ^ 0xB9] = I("[Mq\"\u0017\u0017A", "raQcp");
    }
    
    static {
        I();
    }
}
