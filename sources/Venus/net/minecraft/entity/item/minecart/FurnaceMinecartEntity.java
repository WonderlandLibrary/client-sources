/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FurnaceMinecartEntity
extends AbstractMinecartEntity {
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(FurnaceMinecartEntity.class, DataSerializers.BOOLEAN);
    private int fuel;
    public double pushX;
    public double pushZ;
    private static final Ingredient BURNABLE_FUELS = Ingredient.fromItems(Items.COAL, Items.CHARCOAL);

    public FurnaceMinecartEntity(EntityType<? extends FurnaceMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public FurnaceMinecartEntity(World world, double d, double d2, double d3) {
        super(EntityType.FURNACE_MINECART, world, d, d2, d3);
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.FURNACE;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(POWERED, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote()) {
            if (this.fuel > 0) {
                --this.fuel;
            }
            if (this.fuel <= 0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
            }
            this.setMinecartPowered(this.fuel > 0);
        }
        if (this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
            this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getPosX(), this.getPosY() + 0.8, this.getPosZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected double getMaximumSpeed() {
        return 0.2;
    }

    @Override
    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (!damageSource.isExplosion() && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.entityDropItem(Blocks.FURNACE);
        }
    }

    @Override
    protected void moveAlongTrack(BlockPos blockPos, BlockState blockState) {
        double d = 1.0E-4;
        double d2 = 0.001;
        super.moveAlongTrack(blockPos, blockState);
        Vector3d vector3d = this.getMotion();
        double d3 = FurnaceMinecartEntity.horizontalMag(vector3d);
        double d4 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (d4 > 1.0E-4 && d3 > 0.001) {
            double d5 = MathHelper.sqrt(d3);
            double d6 = MathHelper.sqrt(d4);
            this.pushX = vector3d.x / d5 * d6;
            this.pushZ = vector3d.z / d5 * d6;
        }
    }

    @Override
    protected void applyDrag() {
        double d = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (d > 1.0E-7) {
            d = MathHelper.sqrt(d);
            this.pushX /= d;
            this.pushZ /= d;
            this.setMotion(this.getMotion().mul(0.8, 0.0, 0.8).add(this.pushX, 0.0, this.pushZ));
        } else {
            this.setMotion(this.getMotion().mul(0.98, 0.0, 0.98));
        }
        super.applyDrag();
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (BURNABLE_FUELS.test(itemStack) && this.fuel + 3600 <= 32000) {
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            this.fuel += 3600;
        }
        if (this.fuel > 0) {
            this.pushX = this.getPosX() - playerEntity.getPosX();
            this.pushZ = this.getPosZ() - playerEntity.getPosZ();
        }
        return ActionResultType.func_233537_a_(this.world.isRemote);
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putDouble("PushX", this.pushX);
        compoundNBT.putDouble("PushZ", this.pushZ);
        compoundNBT.putShort("Fuel", (short)this.fuel);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.pushX = compoundNBT.getDouble("PushX");
        this.pushZ = compoundNBT.getDouble("PushZ");
        this.fuel = compoundNBT.getShort("Fuel");
    }

    protected boolean isMinecartPowered() {
        return this.dataManager.get(POWERED);
    }

    protected void setMinecartPowered(boolean bl) {
        this.dataManager.set(POWERED, bl);
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return (BlockState)((BlockState)Blocks.FURNACE.getDefaultState().with(FurnaceBlock.FACING, Direction.NORTH)).with(FurnaceBlock.LIT, this.isMinecartPowered());
    }
}

