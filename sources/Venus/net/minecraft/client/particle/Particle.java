/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.MobAppearanceParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.optifine.BlockPosM;

public abstract class Particle {
    private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    protected final ClientWorld world;
    protected double prevPosX;
    protected double prevPosY;
    protected double prevPosZ;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double motionX;
    protected double motionY;
    protected double motionZ;
    private AxisAlignedBB boundingBox = EMPTY_AABB;
    protected boolean onGround;
    protected boolean canCollide = true;
    private boolean collidedY;
    protected boolean isExpired;
    protected float width = 0.6f;
    protected float height = 1.8f;
    protected final Random rand = new Random();
    protected int age;
    protected int maxAge;
    protected float particleGravity;
    protected float particleRed = 1.0f;
    protected float particleGreen = 1.0f;
    protected float particleBlue = 1.0f;
    protected float particleAlpha = 1.0f;
    protected float particleAngle;
    protected float prevParticleAngle;
    private BlockPosM blockPosM = new BlockPosM();

    protected Particle(ClientWorld clientWorld, double d, double d2, double d3) {
        this.world = clientWorld;
        this.setSize(0.2f, 0.2f);
        this.setPosition(d, d2, d3);
        this.prevPosX = d;
        this.prevPosY = d2;
        this.prevPosZ = d3;
        this.maxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
    }

    public Particle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        this(clientWorld, d, d2, d3);
        this.motionX = d4 + (Math.random() * 2.0 - 1.0) * (double)0.4f;
        this.motionY = d5 + (Math.random() * 2.0 - 1.0) * (double)0.4f;
        this.motionZ = d6 + (Math.random() * 2.0 - 1.0) * (double)0.4f;
        float f = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / (double)f2 * (double)f * (double)0.4f;
        this.motionY = this.motionY / (double)f2 * (double)f * (double)0.4f + (double)0.1f;
        this.motionZ = this.motionZ / (double)f2 * (double)f * (double)0.4f;
    }

    public Particle multiplyVelocity(float f) {
        this.motionX *= (double)f;
        this.motionY = (this.motionY - (double)0.1f) * (double)f + (double)0.1f;
        this.motionZ *= (double)f;
        return this;
    }

    public Particle multiplyParticleScaleBy(float f) {
        this.setSize(0.2f * f, 0.2f * f);
        return this;
    }

    public void setColor(float f, float f2, float f3) {
        this.particleRed = f;
        this.particleGreen = f2;
        this.particleBlue = f3;
    }

    protected void setAlphaF(float f) {
        this.particleAlpha = f;
    }

    public void setMaxAge(int n) {
        this.maxAge = n;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.motionY -= 0.04 * (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.98f;
            this.motionY *= (double)0.98f;
            this.motionZ *= (double)0.98f;
            if (this.onGround) {
                this.motionX *= (double)0.7f;
                this.motionZ *= (double)0.7f;
            }
        }
    }

    public abstract void renderParticle(IVertexBuilder var1, ActiveRenderInfo var2, float var3);

    public abstract IParticleRenderType getRenderType();

    public String toString() {
        return this.getClass().getSimpleName() + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.age;
    }

    public void setExpired() {
        this.isExpired = true;
    }

    protected void setSize(float f, float f2) {
        if (f != this.width || f2 != this.height) {
            this.width = f;
            this.height = f2;
            AxisAlignedBB axisAlignedBB = this.getBoundingBox();
            double d = (axisAlignedBB.minX + axisAlignedBB.maxX - (double)f) / 2.0;
            double d2 = (axisAlignedBB.minZ + axisAlignedBB.maxZ - (double)f) / 2.0;
            this.setBoundingBox(new AxisAlignedBB(d, axisAlignedBB.minY, d2, d + (double)this.width, axisAlignedBB.minY + (double)this.height, d2 + (double)this.width));
        }
    }

    public void setPosition(double d, double d2, double d3) {
        this.posX = d;
        this.posY = d2;
        this.posZ = d3;
        float f = this.width / 2.0f;
        float f2 = this.height;
        this.setBoundingBox(new AxisAlignedBB(d - (double)f, d2, d3 - (double)f, d + (double)f, d2 + (double)f2, d3 + (double)f));
    }

    public void move(double d, double d2, double d3) {
        if (!this.collidedY) {
            double d4 = d;
            double d5 = d2;
            double d6 = d3;
            if (this.canCollide && (d != 0.0 || d2 != 0.0 || d3 != 0.0) && this.hasNearBlocks(d, d2, d3)) {
                Vector3d vector3d = Entity.collideBoundingBoxHeuristically(null, new Vector3d(d, d2, d3), this.getBoundingBox(), this.world, ISelectionContext.dummy(), new ReuseableStream<VoxelShape>(Stream.empty()), false, false);
                d = vector3d.x;
                d2 = vector3d.y;
                d3 = vector3d.z;
            }
            if (d != 0.0 || d2 != 0.0 || d3 != 0.0) {
                this.setBoundingBox(this.getBoundingBox().offset(d, d2, d3));
                this.resetPositionToBB();
            }
            if (Math.abs(d5) >= (double)1.0E-5f && Math.abs(d2) < (double)1.0E-5f) {
                this.collidedY = true;
            }
            boolean bl = this.onGround = d5 != d2 && d5 < 0.0;
            if (d4 != d) {
                this.motionX = 0.0;
            }
            if (d6 != d3) {
                this.motionZ = 0.0;
            }
        }
    }

    protected void resetPositionToBB() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        this.posX = (axisAlignedBB.minX + axisAlignedBB.maxX) / 2.0;
        this.posY = axisAlignedBB.minY;
        this.posZ = (axisAlignedBB.minZ + axisAlignedBB.maxZ) / 2.0;
    }

    protected int getBrightnessForRender(float f) {
        BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);
        return this.world.isBlockLoaded(blockPos) ? WorldRenderer.getCombinedLight(this.world, blockPos) : 0;
    }

    public boolean isAlive() {
        return !this.isExpired;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB axisAlignedBB) {
        this.boundingBox = axisAlignedBB;
    }

    private boolean hasNearBlocks(double d, double d2, double d3) {
        if (!(this.width > 1.0f) && !(this.height > 1.0f)) {
            double d4;
            double d5;
            int n = MathHelper.floor(this.posX);
            int n2 = MathHelper.floor(this.posY);
            int n3 = MathHelper.floor(this.posZ);
            this.blockPosM.setXyz(n, n2, n3);
            BlockState blockState = this.world.getBlockState(this.blockPosM);
            if (!blockState.isAir()) {
                return false;
            }
            double d6 = d > 0.0 ? this.boundingBox.maxX : (d5 = d < 0.0 ? this.boundingBox.minX : this.posX);
            double d7 = d2 > 0.0 ? this.boundingBox.maxY : (d4 = d2 < 0.0 ? this.boundingBox.minY : this.posY);
            double d8 = d3 > 0.0 ? this.boundingBox.maxZ : (d3 < 0.0 ? this.boundingBox.minZ : this.posZ);
            int n4 = MathHelper.floor(d5 + d);
            int n5 = MathHelper.floor(d4 + d2);
            int n6 = MathHelper.floor(d8 + d3);
            if (n4 != n || n5 != n2 || n6 != n3) {
                this.blockPosM.setXyz(n4, n5, n6);
                BlockState blockState2 = this.world.getBlockState(this.blockPosM);
                if (!blockState2.isAir()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean shouldCull() {
        return !(this instanceof MobAppearanceParticle);
    }
}

