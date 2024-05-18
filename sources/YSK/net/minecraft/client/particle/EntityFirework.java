package net.minecraft.client.particle;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;

public class EntityFirework
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static class OverlayFX extends EntityFX
    {
        @Override
        public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            final float n7 = 7.1f * MathHelper.sin((this.particleAge + n - 1.0f) * 0.25f * 3.1415927f);
            this.particleAlpha = 0.6f - (this.particleAge + n - 1.0f) * 0.25f * 0.5f;
            final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - OverlayFX.interpPosX);
            final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - OverlayFX.interpPosY);
            final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - OverlayFX.interpPosZ);
            final int brightnessForRender = this.getBrightnessForRender(n);
            final int n11 = brightnessForRender >> (0xA8 ^ 0xB8) & 63288 + 62266 - 79590 + 19571;
            final int n12 = brightnessForRender & 570 + 30027 + 19916 + 15022;
            worldRenderer.pos(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7).tex(0.5, 0.375).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
            worldRenderer.pos(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7).tex(0.5, 0.125).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
            worldRenderer.pos(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7).tex(0.25, 0.125).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
            worldRenderer.pos(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7).tex(0.25, 0.375).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n11, n12).endVertex();
        }
        
        protected OverlayFX(final World world, final double n, final double n2, final double n3) {
            super(world, n, n2, n3);
            this.particleMaxAge = (0x41 ^ 0x45);
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
    }
    
    public static class SparkFX extends EntityFX
    {
        private boolean hasFadeColour;
        private int baseTextureIndex;
        private float fadeColourBlue;
        private final EffectRenderer field_92047_az;
        private boolean twinkle;
        private float fadeColourRed;
        private float fadeColourGreen;
        private boolean trail;
        
        @Override
        public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            if (!this.twinkle || this.particleAge < this.particleMaxAge / "   ".length() || (this.particleAge + this.particleMaxAge) / "   ".length() % "  ".length() == 0) {
                super.renderParticle(worldRenderer, entity, n, n2, n3, n4, n5, n6);
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
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean canBePushed() {
            return "".length() != 0;
        }
        
        public void setFadeColour(final int n) {
            this.fadeColourRed = ((n & 10853561 + 1123975 - 414586 + 5148730) >> (0x74 ^ 0x64)) / 255.0f;
            this.fadeColourGreen = ((n & 55925 + 17686 - 53566 + 45235) >> (0x90 ^ 0x98)) / 255.0f;
            this.fadeColourBlue = ((n & 76 + 183 - 179 + 175) >> "".length()) / 255.0f;
            this.hasFadeColour = (" ".length() != 0);
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
            if (this.particleAge > this.particleMaxAge / "  ".length()) {
                this.setAlphaF(1.0f - (this.particleAge - this.particleMaxAge / "  ".length()) / this.particleMaxAge);
                if (this.hasFadeColour) {
                    this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2f;
                    this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2f;
                    this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2f;
                }
            }
            this.setParticleTextureIndex(this.baseTextureIndex + ((0x52 ^ 0x55) - this.particleAge * (0x97 ^ 0x9F) / this.particleMaxAge));
            this.motionY -= 0.004;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9100000262260437;
            this.motionY *= 0.9100000262260437;
            this.motionZ *= 0.9100000262260437;
            if (this.onGround) {
                this.motionX *= 0.699999988079071;
                this.motionZ *= 0.699999988079071;
            }
            if (this.trail && this.particleAge < this.particleMaxAge / "  ".length() && (this.particleAge + this.particleMaxAge) % "  ".length() == 0) {
                final SparkFX sparkFX = new SparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, this.field_92047_az);
                sparkFX.setAlphaF(0.99f);
                sparkFX.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
                sparkFX.particleAge = sparkFX.particleMaxAge / "  ".length();
                if (this.hasFadeColour) {
                    sparkFX.hasFadeColour = (" ".length() != 0);
                    sparkFX.fadeColourRed = this.fadeColourRed;
                    sparkFX.fadeColourGreen = this.fadeColourGreen;
                    sparkFX.fadeColourBlue = this.fadeColourBlue;
                }
                sparkFX.twinkle = this.twinkle;
                this.field_92047_az.addEffect(sparkFX);
            }
        }
        
        public void setColour(final int n) {
            final float n2 = ((n & 8555993 + 3897733 + 2746117 + 1511837) >> (0x2F ^ 0x3F)) / 255.0f;
            final float n3 = ((n & 56379 + 26645 - 32132 + 14388) >> (0x55 ^ 0x5D)) / 255.0f;
            final float n4 = ((n & 115 + 210 - 211 + 141) >> "".length()) / 255.0f;
            final float n5 = 1.0f;
            this.setRBGColorF(n2 * n5, n3 * n5, n4 * n5);
        }
        
        @Override
        public float getBrightness(final float n) {
            return 1.0f;
        }
        
        public void setTrail(final boolean trail) {
            this.trail = trail;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox() {
            return null;
        }
        
        @Override
        public int getBrightnessForRender(final float n) {
            return 2774410 + 6790534 - 9473949 + 15637885;
        }
        
        public SparkFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final EffectRenderer field_92047_az) {
            super(world, n, n2, n3);
            this.baseTextureIndex = 136 + 100 - 113 + 37;
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
            this.field_92047_az = field_92047_az;
            this.particleScale *= 0.75f;
            this.particleMaxAge = (0x79 ^ 0x49) + this.rand.nextInt(0xA0 ^ 0xAC);
            this.noClip = ("".length() != 0);
        }
        
        public void setTwinkle(final boolean twinkle) {
            this.twinkle = twinkle;
        }
    }
    
    public static class StarterFX extends EntityFX
    {
        private final EffectRenderer theEffectRenderer;
        private static final String[] I;
        boolean twinkle;
        private int fireworkAge;
        private NBTTagList fireworkExplosions;
        
        private void createBurst(final int[] array, final int[] array2, final boolean b, final boolean b2) {
            final double n = this.rand.nextGaussian() * 0.05;
            final double n2 = this.rand.nextGaussian() * 0.05;
            int i = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (i < (0x7A ^ 0x3C)) {
                this.createParticle(this.posX, this.posY, this.posZ, this.motionX * 0.5 + this.rand.nextGaussian() * 0.15 + n, this.motionY * 0.5 + this.rand.nextDouble() * 0.5, this.motionZ * 0.5 + this.rand.nextGaussian() * 0.15 + n2, array, array2, b, b2);
                ++i;
            }
        }
        
        @Override
        public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        }
        
        private static void I() {
            (I = new String[0x3A ^ 0x2A])["".length()] = I("&6\u0018\u001d(\u0010'\u0007\u001f4", "cNhqG");
            StarterFX.I[" ".length()] = I("\u0015\u001c\u0005&\u001a6\u0002", "SplEq");
            StarterFX.I["  ".length()] = I("\u0018\t1\u0004", "LpAaZ");
            StarterFX.I["   ".length()] = I("\r\u001f\u00104\u0004\u0004\u0004\t\"]", "kvbQs");
            StarterFX.I[0x64 ^ 0x60] = I("/\u000e=\u0004/\u0001\u0003.\u0010>", "CoOcJ");
            StarterFX.I[0x4A ^ 0x4F] = I("1*\u0013\u001d\u0001", "SFrnu");
            StarterFX.I[0x71 ^ 0x77] = I("/\u000b,'", "pmMUU");
            StarterFX.I[0x99 ^ 0x9E] = I("", "yOHvc");
            StarterFX.I[0x8A ^ 0x82] = I("&\u0012\t*", "rkyOA");
            StarterFX.I[0x75 ^ 0x7C] = I("#$\u001b\u001c(", "wVzuD");
            StarterFX.I[0x3C ^ 0x36] = I("\u0005\u001e9/(&\u0000", "CrPLC");
            StarterFX.I[0x1 ^ 0xA] = I("+* \u0015\u0003\u001b", "hELzq");
            StarterFX.I[0x23 ^ 0x2F] = I("/7-*$\u0006:&=\u0014", "iVIOg");
            StarterFX.I[0x63 ^ 0x6E] = I("\u000f30\u0004=\u0006()\u0012d", "iZBaJ");
            StarterFX.I[0x58 ^ 0x56] = I("<\u0013\u0007\u001e\u001c$\u00011\u0016\u0016:", "Hdnpw");
            StarterFX.I[0x75 ^ 0x7A] = I("\u0017\u0002+?\n\u000f\u0010", "cuBQa");
        }
        
        @Override
        public void onUpdate() {
            if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
                final boolean func_92037_i = this.func_92037_i();
                int n = "".length();
                if (this.fireworkExplosions.tagCount() >= "   ".length()) {
                    n = " ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    int i = "".length();
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                    while (i < this.fireworkExplosions.tagCount()) {
                        if (this.fireworkExplosions.getCompoundTagAt(i).getByte(StarterFX.I["  ".length()]) == " ".length()) {
                            n = " ".length();
                            "".length();
                            if (4 == 0) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                final StringBuilder sb = new StringBuilder(StarterFX.I["   ".length()]);
                String s;
                if (n != 0) {
                    s = StarterFX.I[0x90 ^ 0x94];
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
                else {
                    s = StarterFX.I[0x8F ^ 0x8A];
                }
                final StringBuilder append = sb.append(s);
                String s2;
                if (func_92037_i) {
                    s2 = StarterFX.I[0xAC ^ 0xAA];
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else {
                    s2 = StarterFX.I[0x6D ^ 0x6A];
                }
                this.worldObj.playSound(this.posX, this.posY, this.posZ, append.append(s2).toString(), 20.0f, 0.95f + this.rand.nextFloat() * 0.1f, " ".length() != 0);
            }
            if (this.fireworkAge % "  ".length() == 0 && this.fireworkExplosions != null && this.fireworkAge / "  ".length() < this.fireworkExplosions.tagCount()) {
                final NBTTagCompound compoundTag = this.fireworkExplosions.getCompoundTagAt(this.fireworkAge / "  ".length());
                final byte byte1 = compoundTag.getByte(StarterFX.I[0x68 ^ 0x60]);
                final boolean boolean1 = compoundTag.getBoolean(StarterFX.I[0x1F ^ 0x16]);
                final boolean boolean2 = compoundTag.getBoolean(StarterFX.I[0x20 ^ 0x2A]);
                int[] intArray = compoundTag.getIntArray(StarterFX.I[0x83 ^ 0x88]);
                final int[] intArray2 = compoundTag.getIntArray(StarterFX.I[0x7C ^ 0x70]);
                if (intArray.length == 0) {
                    final int[] array = new int[" ".length()];
                    array["".length()] = ItemDye.dyeColors["".length()];
                    intArray = array;
                }
                if (byte1 == " ".length()) {
                    this.createBall(0.5, 0x53 ^ 0x57, intArray, intArray2, boolean1, boolean2);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (byte1 == "  ".length()) {
                    final double n2 = 0.5;
                    final double[][] array2 = new double[0x29 ^ 0x2F][];
                    final int length = "".length();
                    final double[] array3 = new double["  ".length()];
                    array3["".length()] = 0.0;
                    array3[" ".length()] = 1.0;
                    array2[length] = array3;
                    final int length2 = " ".length();
                    final double[] array4 = new double["  ".length()];
                    array4["".length()] = 0.3455;
                    array4[" ".length()] = 0.309;
                    array2[length2] = array4;
                    final int length3 = "  ".length();
                    final double[] array5 = new double["  ".length()];
                    array5["".length()] = 0.9511;
                    array5[" ".length()] = 0.309;
                    array2[length3] = array5;
                    final int length4 = "   ".length();
                    final double[] array6 = new double["  ".length()];
                    array6["".length()] = 0.3795918367346939;
                    array6[" ".length()] = -0.12653061224489795;
                    array2[length4] = array6;
                    final int n3 = 0x72 ^ 0x76;
                    final double[] array7 = new double["  ".length()];
                    array7["".length()] = 0.6122448979591837;
                    array7[" ".length()] = -0.8040816326530612;
                    array2[n3] = array7;
                    final int n4 = 0x9C ^ 0x99;
                    final double[] array8 = new double["  ".length()];
                    array8["".length()] = 0.0;
                    array8[" ".length()] = -0.35918367346938773;
                    array2[n4] = array8;
                    this.createShaped(n2, array2, intArray, intArray2, boolean1, boolean2, "".length() != 0);
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
                else if (byte1 == "   ".length()) {
                    final double n5 = 0.5;
                    final double[][] array9 = new double[0x2A ^ 0x26][];
                    final int length5 = "".length();
                    final double[] array10 = new double["  ".length()];
                    array10["".length()] = 0.0;
                    array10[" ".length()] = 0.2;
                    array9[length5] = array10;
                    final int length6 = " ".length();
                    final double[] array11 = new double["  ".length()];
                    array11["".length()] = 0.2;
                    array11[" ".length()] = 0.2;
                    array9[length6] = array11;
                    final int length7 = "  ".length();
                    final double[] array12 = new double["  ".length()];
                    array12["".length()] = 0.2;
                    array12[" ".length()] = 0.6;
                    array9[length7] = array12;
                    final int length8 = "   ".length();
                    final double[] array13 = new double["  ".length()];
                    array13["".length()] = 0.6;
                    array13[" ".length()] = 0.6;
                    array9[length8] = array13;
                    final int n6 = 0x4 ^ 0x0;
                    final double[] array14 = new double["  ".length()];
                    array14["".length()] = 0.6;
                    array14[" ".length()] = 0.2;
                    array9[n6] = array14;
                    final int n7 = 0x65 ^ 0x60;
                    final double[] array15 = new double["  ".length()];
                    array15["".length()] = 0.2;
                    array15[" ".length()] = 0.2;
                    array9[n7] = array15;
                    final int n8 = 0x82 ^ 0x84;
                    final double[] array16 = new double["  ".length()];
                    array16["".length()] = 0.2;
                    array16[" ".length()] = 0.0;
                    array9[n8] = array16;
                    final int n9 = 0x87 ^ 0x80;
                    final double[] array17 = new double["  ".length()];
                    array17["".length()] = 0.4;
                    array17[" ".length()] = 0.0;
                    array9[n9] = array17;
                    final int n10 = 0x65 ^ 0x6D;
                    final double[] array18 = new double["  ".length()];
                    array18["".length()] = 0.4;
                    array18[" ".length()] = -0.6;
                    array9[n10] = array18;
                    final int n11 = 0x8E ^ 0x87;
                    final double[] array19 = new double["  ".length()];
                    array19["".length()] = 0.2;
                    array19[" ".length()] = -0.6;
                    array9[n11] = array19;
                    final int n12 = 0xC9 ^ 0xC3;
                    final double[] array20 = new double["  ".length()];
                    array20["".length()] = 0.2;
                    array20[" ".length()] = -0.4;
                    array9[n12] = array20;
                    final int n13 = 0x83 ^ 0x88;
                    final double[] array21 = new double["  ".length()];
                    array21["".length()] = 0.0;
                    array21[" ".length()] = -0.4;
                    array9[n13] = array21;
                    this.createShaped(n5, array9, intArray, intArray2, boolean1, boolean2, " ".length() != 0);
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else if (byte1 == (0x23 ^ 0x27)) {
                    this.createBurst(intArray, intArray2, boolean1, boolean2);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    this.createBall(0.25, "  ".length(), intArray, intArray2, boolean1, boolean2);
                }
                final int n14 = intArray["".length()];
                final float n15 = ((n14 & 8311481 + 14375834 - 7601044 + 1625409) >> (0x42 ^ 0x52)) / 255.0f;
                final float n16 = ((n14 & 8912 + 59930 - 56832 + 53270) >> (0x80 ^ 0x88)) / 255.0f;
                final float n17 = ((n14 & 223 + 104 - 124 + 52) >> "".length()) / 255.0f;
                final OverlayFX overlayFX = new OverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
                overlayFX.setRBGColorF(n15, n16, n17);
                this.theEffectRenderer.addEffect(overlayFX);
            }
            this.fireworkAge += " ".length();
            if (this.fireworkAge > this.particleMaxAge) {
                if (this.twinkle) {
                    final boolean func_92037_i2 = this.func_92037_i();
                    final StringBuilder sb2 = new StringBuilder(StarterFX.I[0xAA ^ 0xA7]);
                    String s3;
                    if (func_92037_i2) {
                        s3 = StarterFX.I[0xB7 ^ 0xB9];
                        "".length();
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        s3 = StarterFX.I[0x22 ^ 0x2D];
                    }
                    this.worldObj.playSound(this.posX, this.posY, this.posZ, sb2.append(s3).toString(), 20.0f, 0.9f + this.rand.nextFloat() * 0.15f, " ".length() != 0);
                }
                this.setDead();
            }
        }
        
        static {
            I();
        }
        
        public StarterFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final EffectRenderer theEffectRenderer, final NBTTagCompound nbtTagCompound) {
            super(world, n, n2, n3, 0.0, 0.0, 0.0);
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
            this.theEffectRenderer = theEffectRenderer;
            this.particleMaxAge = (0x4A ^ 0x42);
            if (nbtTagCompound != null) {
                this.fireworkExplosions = nbtTagCompound.getTagList(StarterFX.I["".length()], 0x50 ^ 0x5A);
                if (this.fireworkExplosions.tagCount() == 0) {
                    this.fireworkExplosions = null;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    this.particleMaxAge = this.fireworkExplosions.tagCount() * "  ".length() - " ".length();
                    int i = "".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    while (i < this.fireworkExplosions.tagCount()) {
                        if (this.fireworkExplosions.getCompoundTagAt(i).getBoolean(StarterFX.I[" ".length()])) {
                            this.twinkle = (" ".length() != 0);
                            this.particleMaxAge += (0x2E ^ 0x21);
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
            }
        }
        
        private void createShaped(final double n, final double[][] array, final int[] array2, final int[] array3, final boolean b, final boolean b2, final boolean b3) {
            final double n2 = array["".length()]["".length()];
            final double n3 = array["".length()][" ".length()];
            this.createParticle(this.posX, this.posY, this.posZ, n2 * n, n3 * n, 0.0, array2, array3, b, b2);
            final float n4 = this.rand.nextFloat() * 3.1415927f;
            double n5;
            if (b3) {
                n5 = 0.034;
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                n5 = 0.34;
            }
            final double n6 = n5;
            int i = "".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
            while (i < "   ".length()) {
                final double n7 = n4 + i * 3.1415927f * n6;
                double n8 = n2;
                double n9 = n3;
                int j = " ".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                while (j < array.length) {
                    final double n10 = array[j]["".length()];
                    final double n11 = array[j][" ".length()];
                    double n12 = 0.25;
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                    while (n12 <= 1.0) {
                        final double n13 = (n8 + (n10 - n8) * n12) * n;
                        final double n14 = (n9 + (n11 - n9) * n12) * n;
                        final double n15 = n13 * Math.sin(n7);
                        final double n16 = n13 * Math.cos(n7);
                        double n17 = -1.0;
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                        while (n17 <= 1.0) {
                            this.createParticle(this.posX, this.posY, this.posZ, n16 * n17, n14, n15 * n17, array2, array3, b, b2);
                            n17 += 2.0;
                        }
                        n12 += 0.25;
                    }
                    n8 = n10;
                    n9 = n11;
                    ++j;
                }
                ++i;
            }
        }
        
        private void createBall(final double n, final int n2, final int[] array, final int[] array2, final boolean b, final boolean b2) {
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            int i = -n2;
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i <= n2) {
                int j = -n2;
                "".length();
                if (2 == 1) {
                    throw null;
                }
                while (j <= n2) {
                    int k = -n2;
                    "".length();
                    if (false) {
                        throw null;
                    }
                    while (k <= n2) {
                        final double n3 = j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        final double n4 = i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        final double n5 = k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        final double n6 = MathHelper.sqrt_double(n3 * n3 + n4 * n4 + n5 * n5) / n + this.rand.nextGaussian() * 0.05;
                        this.createParticle(posX, posY, posZ, n3 / n6, n4 / n6, n5 / n6, array, array2, b, b2);
                        if (i != -n2 && i != n2 && j != -n2 && j != n2) {
                            k += n2 * "  ".length() - " ".length();
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        
        @Override
        public int getFXLayer() {
            return "".length();
        }
        
        private void createParticle(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int[] array, final int[] array2, final boolean trail, final boolean twinkle) {
            final SparkFX sparkFX = new SparkFX(this.worldObj, n, n2, n3, n4, n5, n6, this.theEffectRenderer);
            sparkFX.setAlphaF(0.99f);
            sparkFX.setTrail(trail);
            sparkFX.setTwinkle(twinkle);
            sparkFX.setColour(array[this.rand.nextInt(array.length)]);
            if (array2 != null && array2.length > 0) {
                sparkFX.setFadeColour(array2[this.rand.nextInt(array2.length)]);
            }
            this.theEffectRenderer.addEffect(sparkFX);
        }
        
        private boolean func_92037_i() {
            final Minecraft minecraft = Minecraft.getMinecraft();
            if (minecraft != null && minecraft.getRenderViewEntity() != null && minecraft.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) < 256.0) {
                return "".length() != 0;
            }
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
                if (2 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class Factory implements IParticleFactory
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
                if (1 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final SparkFX sparkFX = new SparkFX(world, n2, n3, n4, n5, n6, n7, Minecraft.getMinecraft().effectRenderer);
            sparkFX.setAlphaF(0.99f);
            return sparkFX;
        }
    }
}
