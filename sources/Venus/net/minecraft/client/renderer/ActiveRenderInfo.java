/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import mpp.venusfr.events.CameraEvent;
import mpp.venusfr.venusfr;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockReader;
import net.optifine.reflect.Reflector;

public class ActiveRenderInfo {
    private boolean valid;
    private IBlockReader world;
    private Entity renderViewEntity;
    private Vector3d pos = Vector3d.ZERO;
    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();
    private final Vector3f look = new Vector3f(0.0f, 0.0f, 1.0f);
    private final Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
    private final Vector3f left = new Vector3f(1.0f, 0.0f, 0.0f);
    private float pitch;
    private float yaw;
    private final Quaternion rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    private boolean thirdPerson;
    private boolean thirdPersonReverse;
    private float height;
    private float previousHeight;
    private boolean firsttime;
    private CameraEvent event = new CameraEvent(1.0f);

    public void update(IBlockReader iBlockReader, Entity entity2, boolean bl, boolean bl2, float f) {
        this.valid = true;
        this.world = iBlockReader;
        this.renderViewEntity = entity2;
        this.thirdPerson = bl;
        this.thirdPersonReverse = bl2;
        this.setDirection(entity2.getYaw(f), entity2.getPitch(f));
        this.setPosition(MathHelper.lerp((double)f, entity2.prevPosX, entity2.getPosX()), MathHelper.lerp((double)f, entity2.prevPosY, entity2.getPosY()) + (double)MathHelper.lerp(f, this.previousHeight, this.height), MathHelper.lerp((double)f, entity2.prevPosZ, entity2.getPosZ()));
        this.event.partialTicks = f;
        venusfr.getInstance().getEventBus().post(this.event);
        if (bl) {
            if (bl2) {
                this.setDirection(this.yaw + 180.0f, -this.pitch);
            }
            this.movePosition(-this.calcCameraDistance(4.0), 0.0, 0.0);
        } else if (entity2 instanceof LivingEntity && ((LivingEntity)entity2).isSleeping()) {
            Direction direction = ((LivingEntity)entity2).getBedDirection();
            this.setDirection(direction != null ? direction.getHorizontalAngle() - 180.0f : 0.0f, 0.0f);
            this.movePosition(0.0, 0.3, 0.0);
        }
    }

    public void interpolateHeight() {
        if (this.renderViewEntity != null) {
            this.previousHeight = this.height;
            this.height += (this.renderViewEntity.getEyeHeight() - this.height) * 0.5f;
        }
    }

    private double calcCameraDistance(double d) {
        return d;
    }

    public void movePosition(double d, double d2, double d3) {
        double d4 = (double)this.look.getX() * d + (double)this.up.getX() * d2 + (double)this.left.getX() * d3;
        double d5 = (double)this.look.getY() * d + (double)this.up.getY() * d2 + (double)this.left.getY() * d3;
        double d6 = (double)this.look.getZ() * d + (double)this.up.getZ() * d2 + (double)this.left.getZ() * d3;
        this.setPosition(new Vector3d(this.pos.x + d4, this.pos.y + d5, this.pos.z + d6));
    }

    public void moveForward(double d) {
        double d2 = (double)this.look.getX() * d;
        double d3 = (double)this.look.getZ() * d;
        this.setPosition(new Vector3d(this.pos.x + d2, this.pos.y, this.pos.z + d3));
    }

    public void setDirection(float f, float f2) {
        this.pitch = f2;
        this.yaw = f;
        this.rotation.set(0.0f, 0.0f, 0.0f, 1.0f);
        this.rotation.multiply(Vector3f.YP.rotationDegrees(-f));
        this.rotation.multiply(Vector3f.XP.rotationDegrees(f2));
        this.look.set(0.0f, 0.0f, 1.0f);
        this.look.transform(this.rotation);
        this.up.set(0.0f, 1.0f, 0.0f);
        this.up.transform(this.rotation);
        this.left.set(1.0f, 0.0f, 0.0f);
        this.left.transform(this.rotation);
    }

    public void setPosition(double d, double d2, double d3) {
        this.setPosition(new Vector3d(d, d2, d3));
    }

    public void setPosition(Vector3d vector3d) {
        this.pos = vector3d;
        this.blockPos.setPos(vector3d.x, vector3d.y, vector3d.z);
    }

    public Vector3d getProjectedView() {
        return this.pos;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public Quaternion getRotation() {
        return this.rotation;
    }

    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }

    public boolean isValid() {
        return this.valid;
    }

    public boolean isThirdPerson() {
        return this.thirdPerson;
    }

    public FluidState getFluidState() {
        if (!this.valid) {
            return Fluids.EMPTY.getDefaultState();
        }
        FluidState fluidState = this.world.getFluidState(this.blockPos);
        return !fluidState.isEmpty() && this.pos.y >= (double)((float)this.blockPos.getY() + fluidState.getActualHeight(this.world, this.blockPos)) ? Fluids.EMPTY.getDefaultState() : fluidState;
    }

    public BlockState getBlockState() {
        return !this.valid ? Blocks.AIR.getDefaultState() : this.world.getBlockState(this.blockPos);
    }

    public void setAnglesInternal(float f, float f2) {
        this.yaw = f;
        this.pitch = f2;
    }

    public BlockState getBlockAtCamera() {
        if (!this.valid) {
            return Blocks.AIR.getDefaultState();
        }
        BlockState blockState = this.world.getBlockState(this.blockPos);
        if (Reflector.IForgeBlockState_getStateAtViewpoint.exists()) {
            blockState = (BlockState)Reflector.call(blockState, Reflector.IForgeBlockState_getStateAtViewpoint, this.world, this.blockPos, this.pos);
        }
        return blockState;
    }

    public final Vector3f getViewVector() {
        return this.look;
    }

    public final Vector3f getUpVector() {
        return this.up;
    }

    public void clear() {
        this.world = null;
        this.renderViewEntity = null;
        this.valid = false;
    }
}

