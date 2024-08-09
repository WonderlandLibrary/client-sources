/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.IClearable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.EmptyBlockReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;

public class Template {
    private final List<Palette> blocks = Lists.newArrayList();
    private final List<EntityInfo> entities = Lists.newArrayList();
    private BlockPos size = BlockPos.ZERO;
    private String author = "?";

    public BlockPos getSize() {
        return this.size;
    }

    public void setAuthor(String string) {
        this.author = string;
    }

    public String getAuthor() {
        return this.author;
    }

    public void takeBlocksFromWorld(World world, BlockPos blockPos, BlockPos blockPos2, boolean bl, @Nullable Block block) {
        if (blockPos2.getX() >= 1 && blockPos2.getY() >= 1 && blockPos2.getZ() >= 1) {
            BlockPos blockPos3 = blockPos.add(blockPos2).add(-1, -1, -1);
            ArrayList<BlockInfo> arrayList = Lists.newArrayList();
            ArrayList<BlockInfo> arrayList2 = Lists.newArrayList();
            ArrayList<BlockInfo> arrayList3 = Lists.newArrayList();
            BlockPos blockPos4 = new BlockPos(Math.min(blockPos.getX(), blockPos3.getX()), Math.min(blockPos.getY(), blockPos3.getY()), Math.min(blockPos.getZ(), blockPos3.getZ()));
            BlockPos blockPos5 = new BlockPos(Math.max(blockPos.getX(), blockPos3.getX()), Math.max(blockPos.getY(), blockPos3.getY()), Math.max(blockPos.getZ(), blockPos3.getZ()));
            this.size = blockPos2;
            for (BlockPos blockPos6 : BlockPos.getAllInBoxMutable(blockPos4, blockPos5)) {
                BlockInfo blockInfo;
                BlockPos blockPos7 = blockPos6.subtract(blockPos4);
                BlockState blockState = world.getBlockState(blockPos6);
                if (block != null && block == blockState.getBlock()) continue;
                TileEntity tileEntity = world.getTileEntity(blockPos6);
                if (tileEntity != null) {
                    CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
                    compoundNBT.remove("x");
                    compoundNBT.remove("y");
                    compoundNBT.remove("z");
                    blockInfo = new BlockInfo(blockPos7, blockState, compoundNBT.copy());
                } else {
                    blockInfo = new BlockInfo(blockPos7, blockState, null);
                }
                Template.func_237149_a_(blockInfo, arrayList, arrayList2, arrayList3);
            }
            List<BlockInfo> list = Template.func_237151_a_(arrayList, arrayList2, arrayList3);
            this.blocks.clear();
            this.blocks.add(new Palette(list));
            if (bl) {
                this.takeEntitiesFromWorld(world, blockPos4, blockPos5.add(1, 1, 1));
            } else {
                this.entities.clear();
            }
        }
    }

    private static void func_237149_a_(BlockInfo blockInfo, List<BlockInfo> list, List<BlockInfo> list2, List<BlockInfo> list3) {
        if (blockInfo.nbt != null) {
            list2.add(blockInfo);
        } else if (!blockInfo.state.getBlock().isVariableOpacity() && blockInfo.state.hasOpaqueCollisionShape(EmptyBlockReader.INSTANCE, BlockPos.ZERO)) {
            list.add(blockInfo);
        } else {
            list3.add(blockInfo);
        }
    }

    private static List<BlockInfo> func_237151_a_(List<BlockInfo> list, List<BlockInfo> list2, List<BlockInfo> list3) {
        Comparator<BlockInfo> comparator = Comparator.comparingInt(Template::lambda$func_237151_a_$0).thenComparingInt(Template::lambda$func_237151_a_$1).thenComparingInt(Template::lambda$func_237151_a_$2);
        list.sort(comparator);
        list3.sort(comparator);
        list2.sort(comparator);
        ArrayList<BlockInfo> arrayList = Lists.newArrayList();
        arrayList.addAll(list);
        arrayList.addAll(list3);
        arrayList.addAll(list2);
        return arrayList;
    }

    private void takeEntitiesFromWorld(World world, BlockPos blockPos, BlockPos blockPos2) {
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos, blockPos2), Template::lambda$takeEntitiesFromWorld$3);
        this.entities.clear();
        for (Entity entity2 : list) {
            Vector3d vector3d = new Vector3d(entity2.getPosX() - (double)blockPos.getX(), entity2.getPosY() - (double)blockPos.getY(), entity2.getPosZ() - (double)blockPos.getZ());
            CompoundNBT compoundNBT = new CompoundNBT();
            entity2.writeUnlessPassenger(compoundNBT);
            BlockPos blockPos3 = entity2 instanceof PaintingEntity ? ((PaintingEntity)entity2).getHangingPosition().subtract(blockPos) : new BlockPos(vector3d);
            this.entities.add(new EntityInfo(vector3d, blockPos3, compoundNBT.copy()));
        }
    }

    public List<BlockInfo> func_215381_a(BlockPos blockPos, PlacementSettings placementSettings, Block block) {
        return this.func_215386_a(blockPos, placementSettings, block, false);
    }

    public List<BlockInfo> func_215386_a(BlockPos blockPos, PlacementSettings placementSettings, Block block, boolean bl) {
        ArrayList<BlockInfo> arrayList = Lists.newArrayList();
        MutableBoundingBox mutableBoundingBox = placementSettings.getBoundingBox();
        if (this.blocks.isEmpty()) {
            return Collections.emptyList();
        }
        for (BlockInfo blockInfo : placementSettings.func_237132_a_(this.blocks, blockPos).func_237158_a_(block)) {
            BlockPos blockPos2;
            BlockPos blockPos3 = blockPos2 = bl ? Template.transformedBlockPos(placementSettings, blockInfo.pos).add(blockPos) : blockInfo.pos;
            if (mutableBoundingBox != null && !mutableBoundingBox.isVecInside(blockPos2)) continue;
            arrayList.add(new BlockInfo(blockPos2, blockInfo.state.rotate(placementSettings.getRotation()), blockInfo.nbt));
        }
        return arrayList;
    }

    public BlockPos calculateConnectedPos(PlacementSettings placementSettings, BlockPos blockPos, PlacementSettings placementSettings2, BlockPos blockPos2) {
        BlockPos blockPos3 = Template.transformedBlockPos(placementSettings, blockPos);
        BlockPos blockPos4 = Template.transformedBlockPos(placementSettings2, blockPos2);
        return blockPos3.subtract(blockPos4);
    }

    public static BlockPos transformedBlockPos(PlacementSettings placementSettings, BlockPos blockPos) {
        return Template.getTransformedPos(blockPos, placementSettings.getMirror(), placementSettings.getRotation(), placementSettings.getCenterOffset());
    }

    public void func_237144_a_(IServerWorld iServerWorld, BlockPos blockPos, PlacementSettings placementSettings, Random random2) {
        placementSettings.setBoundingBoxFromChunk();
        this.func_237152_b_(iServerWorld, blockPos, placementSettings, random2);
    }

    public void func_237152_b_(IServerWorld iServerWorld, BlockPos blockPos, PlacementSettings placementSettings, Random random2) {
        this.func_237146_a_(iServerWorld, blockPos, blockPos, placementSettings, random2, 1);
    }

    public boolean func_237146_a_(IServerWorld iServerWorld, BlockPos blockPos, BlockPos blockPos2, PlacementSettings placementSettings, Random random2, int n) {
        if (this.blocks.isEmpty()) {
            return true;
        }
        List<BlockInfo> list = placementSettings.func_237132_a_(this.blocks, blockPos).func_237157_a_();
        if (!(list.isEmpty() && (placementSettings.getIgnoreEntities() || this.entities.isEmpty()) || this.size.getX() < 1 || this.size.getY() < 1 || this.size.getZ() < 1)) {
            Object object;
            Object blockState2;
            Object object2;
            Object object3;
            MutableBoundingBox mutableBoundingBox = placementSettings.getBoundingBox();
            ArrayList<Object> arrayList = Lists.newArrayListWithCapacity(placementSettings.func_204763_l() ? list.size() : 0);
            ArrayList<Pair<Object, CompoundNBT>> arrayList2 = Lists.newArrayListWithCapacity(list.size());
            int n2 = Integer.MAX_VALUE;
            int n3 = Integer.MAX_VALUE;
            int n4 = Integer.MAX_VALUE;
            int n5 = Integer.MIN_VALUE;
            int n6 = Integer.MIN_VALUE;
            int n7 = Integer.MIN_VALUE;
            for (BlockInfo directionArray2 : Template.func_237145_a_(iServerWorld, blockPos, blockPos2, placementSettings, list)) {
                object3 = directionArray2.pos;
                if (mutableBoundingBox != null && !mutableBoundingBox.isVecInside((Vector3i)object3)) continue;
                FluidState n8 = placementSettings.func_204763_l() ? iServerWorld.getFluidState((BlockPos)object3) : null;
                object2 = directionArray2.state.mirror(placementSettings.getMirror()).rotate(placementSettings.getRotation());
                if (directionArray2.nbt != null) {
                    blockState2 = iServerWorld.getTileEntity((BlockPos)object3);
                    IClearable.clearObj(blockState2);
                    iServerWorld.setBlockState((BlockPos)object3, Blocks.BARRIER.getDefaultState(), 20);
                }
                if (!iServerWorld.setBlockState((BlockPos)object3, (BlockState)object2, n)) continue;
                n2 = Math.min(n2, ((Vector3i)object3).getX());
                n3 = Math.min(n3, ((Vector3i)object3).getY());
                n4 = Math.min(n4, ((Vector3i)object3).getZ());
                n5 = Math.max(n5, ((Vector3i)object3).getX());
                n6 = Math.max(n6, ((Vector3i)object3).getY());
                n7 = Math.max(n7, ((Vector3i)object3).getZ());
                arrayList2.add(Pair.of(object3, directionArray2.nbt));
                if (directionArray2.nbt != null && (blockState2 = iServerWorld.getTileEntity((BlockPos)object3)) != null) {
                    directionArray2.nbt.putInt("x", ((Vector3i)object3).getX());
                    directionArray2.nbt.putInt("y", ((Vector3i)object3).getY());
                    directionArray2.nbt.putInt("z", ((Vector3i)object3).getZ());
                    if (blockState2 instanceof LockableLootTileEntity) {
                        directionArray2.nbt.putLong("LootTableSeed", random2.nextLong());
                    }
                    ((TileEntity)blockState2).read(directionArray2.state, directionArray2.nbt);
                    ((TileEntity)blockState2).mirror(placementSettings.getMirror());
                    ((TileEntity)blockState2).rotate(placementSettings.getRotation());
                }
                if (n8 == null || !(((AbstractBlock.AbstractBlockState)object2).getBlock() instanceof ILiquidContainer)) continue;
                ((ILiquidContainer)((Object)((AbstractBlock.AbstractBlockState)object2).getBlock())).receiveFluid(iServerWorld, (BlockPos)object3, (BlockState)object2, n8);
                if (n8.isSource()) continue;
                arrayList.add(object3);
            }
            boolean bl = true;
            Direction[] directionArray = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            while (bl && !arrayList.isEmpty()) {
                bl = false;
                object3 = arrayList.iterator();
                while (object3.hasNext()) {
                    BlockState blockState;
                    Block block;
                    BlockPos blockPos3 = (BlockPos)object3.next();
                    object2 = blockPos3;
                    blockState2 = iServerWorld.getFluidState(blockPos3);
                    for (int blockState3 = 0; blockState3 < directionArray.length && !((FluidState)blockState2).isSource(); ++blockState3) {
                        BlockPos blockPos4 = ((BlockPos)object2).offset(directionArray[blockState3]);
                        object = iServerWorld.getFluidState(blockPos4);
                        if (!(((FluidState)object).getActualHeight(iServerWorld, blockPos4) > ((FluidState)blockState2).getActualHeight(iServerWorld, (BlockPos)object2)) && (!((FluidState)object).isSource() || ((FluidState)blockState2).isSource())) continue;
                        blockState2 = object;
                        object2 = blockPos4;
                    }
                    if (!((FluidState)blockState2).isSource() || !((block = (blockState = iServerWorld.getBlockState(blockPos3)).getBlock()) instanceof ILiquidContainer)) continue;
                    ((ILiquidContainer)((Object)block)).receiveFluid(iServerWorld, blockPos3, blockState, (FluidState)blockState2);
                    bl = true;
                    object3.remove();
                }
            }
            if (n2 <= n5) {
                if (!placementSettings.func_215218_i()) {
                    object3 = new BitSetVoxelShapePart(n5 - n2 + 1, n6 - n3 + 1, n7 - n4 + 1);
                    int n8 = n2;
                    int n9 = n3;
                    int tileEntity = n4;
                    for (Pair pair : arrayList2) {
                        object = (BlockPos)pair.getFirst();
                        ((VoxelShapePart)object3).setFilled(((Vector3i)object).getX() - n8, ((Vector3i)object).getY() - n9, ((Vector3i)object).getZ() - tileEntity, true, false);
                    }
                    Template.func_222857_a(iServerWorld, n, (VoxelShapePart)object3, n8, n9, tileEntity);
                }
                for (Pair pair : arrayList2) {
                    TileEntity tileEntity;
                    BlockPos blockPos5 = (BlockPos)pair.getFirst();
                    if (!placementSettings.func_215218_i()) {
                        BlockState blockState;
                        BlockState blockState4 = iServerWorld.getBlockState(blockPos5);
                        if (blockState4 != (blockState = Block.getValidBlockForPosition(blockState4, iServerWorld, blockPos5))) {
                            iServerWorld.setBlockState(blockPos5, blockState, n & 0xFFFFFFFE | 0x10);
                        }
                        iServerWorld.func_230547_a_(blockPos5, blockState.getBlock());
                    }
                    if (pair.getSecond() == null || (tileEntity = iServerWorld.getTileEntity(blockPos5)) == null) continue;
                    tileEntity.markDirty();
                }
            }
            if (!placementSettings.getIgnoreEntities()) {
                this.func_237143_a_(iServerWorld, blockPos, placementSettings.getMirror(), placementSettings.getRotation(), placementSettings.getCenterOffset(), mutableBoundingBox, placementSettings.func_237134_m_());
            }
            return false;
        }
        return true;
    }

    public static void func_222857_a(IWorld iWorld, int n, VoxelShapePart voxelShapePart, int n2, int n3, int n4) {
        voxelShapePart.forEachFace((arg_0, arg_1, arg_2, arg_3) -> Template.lambda$func_222857_a$4(n2, n3, n4, iWorld, n, arg_0, arg_1, arg_2, arg_3));
    }

    public static List<BlockInfo> func_237145_a_(IWorld iWorld, BlockPos blockPos, BlockPos blockPos2, PlacementSettings placementSettings, List<BlockInfo> list) {
        ArrayList<BlockInfo> arrayList = Lists.newArrayList();
        for (BlockInfo blockInfo : list) {
            BlockPos blockPos3 = Template.transformedBlockPos(placementSettings, blockInfo.pos).add(blockPos);
            BlockInfo blockInfo2 = new BlockInfo(blockPos3, blockInfo.state, blockInfo.nbt != null ? blockInfo.nbt.copy() : null);
            Iterator<StructureProcessor> iterator2 = placementSettings.getProcessors().iterator();
            while (blockInfo2 != null && iterator2.hasNext()) {
                blockInfo2 = iterator2.next().func_230386_a_(iWorld, blockPos, blockPos2, blockInfo, blockInfo2, placementSettings);
            }
            if (blockInfo2 == null) continue;
            arrayList.add(blockInfo2);
        }
        return arrayList;
    }

    private void func_237143_a_(IServerWorld iServerWorld, BlockPos blockPos, Mirror mirror, Rotation rotation, BlockPos blockPos2, @Nullable MutableBoundingBox mutableBoundingBox, boolean bl) {
        for (EntityInfo entityInfo : this.entities) {
            BlockPos blockPos3 = Template.getTransformedPos(entityInfo.blockPos, mirror, rotation, blockPos2).add(blockPos);
            if (mutableBoundingBox != null && !mutableBoundingBox.isVecInside(blockPos3)) continue;
            CompoundNBT compoundNBT = entityInfo.nbt.copy();
            Vector3d vector3d = Template.getTransformedPos(entityInfo.pos, mirror, rotation, blockPos2);
            Vector3d vector3d2 = vector3d.add(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            ListNBT listNBT = new ListNBT();
            listNBT.add(DoubleNBT.valueOf(vector3d2.x));
            listNBT.add(DoubleNBT.valueOf(vector3d2.y));
            listNBT.add(DoubleNBT.valueOf(vector3d2.z));
            compoundNBT.put("Pos", listNBT);
            compoundNBT.remove("UUID");
            Template.loadEntity(iServerWorld, compoundNBT).ifPresent(arg_0 -> Template.lambda$func_237143_a_$5(mirror, rotation, vector3d2, bl, iServerWorld, compoundNBT, arg_0));
        }
    }

    private static Optional<Entity> loadEntity(IServerWorld iServerWorld, CompoundNBT compoundNBT) {
        try {
            return EntityType.loadEntityUnchecked(compoundNBT, iServerWorld.getWorld());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public BlockPos transformedSize(Rotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90: 
            case CLOCKWISE_90: {
                return new BlockPos(this.size.getZ(), this.size.getY(), this.size.getX());
            }
        }
        return this.size;
    }

    public static BlockPos getTransformedPos(BlockPos blockPos, Mirror mirror, Rotation rotation, BlockPos blockPos2) {
        int n = blockPos.getX();
        int n2 = blockPos.getY();
        int n3 = blockPos.getZ();
        boolean bl = true;
        switch (mirror) {
            case LEFT_RIGHT: {
                n3 = -n3;
                break;
            }
            case FRONT_BACK: {
                n = -n;
                break;
            }
            default: {
                bl = false;
            }
        }
        int n4 = blockPos2.getX();
        int n5 = blockPos2.getZ();
        switch (rotation) {
            case COUNTERCLOCKWISE_90: {
                return new BlockPos(n4 - n5 + n3, n2, n4 + n5 - n);
            }
            case CLOCKWISE_90: {
                return new BlockPos(n4 + n5 - n3, n2, n5 - n4 + n);
            }
            case CLOCKWISE_180: {
                return new BlockPos(n4 + n4 - n, n2, n5 + n5 - n3);
            }
        }
        return bl ? new BlockPos(n, n2, n3) : blockPos;
    }

    public static Vector3d getTransformedPos(Vector3d vector3d, Mirror mirror, Rotation rotation, BlockPos blockPos) {
        double d = vector3d.x;
        double d2 = vector3d.y;
        double d3 = vector3d.z;
        boolean bl = true;
        switch (mirror) {
            case LEFT_RIGHT: {
                d3 = 1.0 - d3;
                break;
            }
            case FRONT_BACK: {
                d = 1.0 - d;
                break;
            }
            default: {
                bl = false;
            }
        }
        int n = blockPos.getX();
        int n2 = blockPos.getZ();
        switch (rotation) {
            case COUNTERCLOCKWISE_90: {
                return new Vector3d((double)(n - n2) + d3, d2, (double)(n + n2 + 1) - d);
            }
            case CLOCKWISE_90: {
                return new Vector3d((double)(n + n2 + 1) - d3, d2, (double)(n2 - n) + d);
            }
            case CLOCKWISE_180: {
                return new Vector3d((double)(n + n + 1) - d, d2, (double)(n2 + n2 + 1) - d3);
            }
        }
        return bl ? new Vector3d(d, d2, d3) : vector3d;
    }

    public BlockPos getZeroPositionWithTransform(BlockPos blockPos, Mirror mirror, Rotation rotation) {
        return Template.getZeroPositionWithTransform(blockPos, mirror, rotation, this.getSize().getX(), this.getSize().getZ());
    }

    public static BlockPos getZeroPositionWithTransform(BlockPos blockPos, Mirror mirror, Rotation rotation, int n, int n2) {
        int n3 = mirror == Mirror.FRONT_BACK ? --n : 0;
        int n4 = mirror == Mirror.LEFT_RIGHT ? --n2 : 0;
        BlockPos blockPos2 = blockPos;
        switch (rotation) {
            case COUNTERCLOCKWISE_90: {
                blockPos2 = blockPos.add(n4, 0, n - n3);
                break;
            }
            case CLOCKWISE_90: {
                blockPos2 = blockPos.add(n2 - n4, 0, n3);
                break;
            }
            case CLOCKWISE_180: {
                blockPos2 = blockPos.add(n - n3, 0, n2 - n4);
                break;
            }
            case NONE: {
                blockPos2 = blockPos.add(n3, 0, n4);
            }
        }
        return blockPos2;
    }

    public MutableBoundingBox getMutableBoundingBox(PlacementSettings placementSettings, BlockPos blockPos) {
        return this.func_237150_a_(blockPos, placementSettings.getRotation(), placementSettings.getCenterOffset(), placementSettings.getMirror());
    }

    public MutableBoundingBox func_237150_a_(BlockPos blockPos, Rotation rotation, BlockPos blockPos2, Mirror mirror) {
        BlockPos blockPos3 = this.transformedSize(rotation);
        int n = blockPos2.getX();
        int n2 = blockPos2.getZ();
        int n3 = blockPos3.getX() - 1;
        int n4 = blockPos3.getY() - 1;
        int n5 = blockPos3.getZ() - 1;
        MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(0, 0, 0, 0, 0, 0);
        switch (rotation) {
            case COUNTERCLOCKWISE_90: {
                mutableBoundingBox = new MutableBoundingBox(n - n2, 0, n + n2 - n5, n - n2 + n3, n4, n + n2);
                break;
            }
            case CLOCKWISE_90: {
                mutableBoundingBox = new MutableBoundingBox(n + n2 - n3, 0, n2 - n, n + n2, n4, n2 - n + n5);
                break;
            }
            case CLOCKWISE_180: {
                mutableBoundingBox = new MutableBoundingBox(n + n - n3, 0, n2 + n2 - n5, n + n, n4, n2 + n2);
                break;
            }
            case NONE: {
                mutableBoundingBox = new MutableBoundingBox(0, 0, 0, n3, n4, n5);
            }
        }
        switch (mirror) {
            case LEFT_RIGHT: {
                this.func_215385_a(rotation, n5, n3, mutableBoundingBox, Direction.NORTH, Direction.SOUTH);
                break;
            }
            case FRONT_BACK: {
                this.func_215385_a(rotation, n3, n5, mutableBoundingBox, Direction.WEST, Direction.EAST);
            }
        }
        mutableBoundingBox.offset(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return mutableBoundingBox;
    }

    private void func_215385_a(Rotation rotation, int n, int n2, MutableBoundingBox mutableBoundingBox, Direction direction, Direction direction2) {
        BlockPos blockPos = BlockPos.ZERO;
        blockPos = rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90 ? (rotation == Rotation.CLOCKWISE_180 ? blockPos.offset(direction2, n) : blockPos.offset(direction, n)) : blockPos.offset(rotation.rotate(direction), n2);
        mutableBoundingBox.offset(blockPos.getX(), 0, blockPos.getZ());
    }

    /*
     * WARNING - void declaration
     */
    public CompoundNBT writeToNBT(CompoundNBT compoundNBT) {
        Object object;
        AbstractList abstractList;
        if (this.blocks.isEmpty()) {
            compoundNBT.put("blocks", new ListNBT());
            compoundNBT.put("palette", new ListNBT());
        } else {
            Object object2;
            Object object32;
            Iterator iterator2;
            void object4;
            abstractList = Lists.newArrayList();
            BasicPalette basicPalette = new BasicPalette();
            abstractList.add(basicPalette);
            boolean i = true;
            while (object4 < this.blocks.size()) {
                abstractList.add(new BasicPalette());
                ++object4;
            }
            ListNBT listNBT = new ListNBT();
            object = this.blocks.get(0).func_237157_a_();
            for (int j = 0; j < object.size(); ++j) {
                iterator2 = (BlockInfo)object.get(j);
                object32 = new CompoundNBT();
                ((CompoundNBT)object32).put("pos", this.writeInts(((BlockInfo)((Object)iterator2)).pos.getX(), ((BlockInfo)((Object)iterator2)).pos.getY(), ((BlockInfo)((Object)iterator2)).pos.getZ()));
                int n = basicPalette.idFor(((BlockInfo)((Object)iterator2)).state);
                ((CompoundNBT)object32).putInt("state", n);
                if (((BlockInfo)((Object)iterator2)).nbt != null) {
                    ((CompoundNBT)object32).put("nbt", ((BlockInfo)((Object)iterator2)).nbt);
                }
                listNBT.add(object32);
                for (int k = 1; k < this.blocks.size(); ++k) {
                    object2 = (BasicPalette)abstractList.get(k);
                    ((BasicPalette)object2).addMapping(this.blocks.get((int)k).func_237157_a_().get((int)j).state, n);
                }
            }
            compoundNBT.put("blocks", listNBT);
            if (abstractList.size() == 1) {
                var6_11 = new ListNBT();
                iterator2 = basicPalette.iterator();
                while (iterator2.hasNext()) {
                    object32 = (BlockState)iterator2.next();
                    var6_11.add(NBTUtil.writeBlockState((BlockState)object32));
                }
                compoundNBT.put("palette", var6_11);
            } else {
                var6_11 = new ListNBT();
                for (Object object32 : abstractList) {
                    ListNBT listNBT2 = new ListNBT();
                    Iterator<BlockState> iterator3 = ((BasicPalette)object32).iterator();
                    while (iterator3.hasNext()) {
                        object2 = iterator3.next();
                        listNBT2.add(NBTUtil.writeBlockState((BlockState)object2));
                    }
                    var6_11.add(listNBT2);
                }
                compoundNBT.put("palettes", var6_11);
            }
        }
        abstractList = new ListNBT();
        for (EntityInfo entityInfo : this.entities) {
            object = new CompoundNBT();
            ((CompoundNBT)object).put("pos", this.writeDoubles(entityInfo.pos.x, entityInfo.pos.y, entityInfo.pos.z));
            ((CompoundNBT)object).put("blockPos", this.writeInts(entityInfo.blockPos.getX(), entityInfo.blockPos.getY(), entityInfo.blockPos.getZ()));
            if (entityInfo.nbt != null) {
                ((CompoundNBT)object).put("nbt", entityInfo.nbt);
            }
            abstractList.add(object);
        }
        compoundNBT.put("entities", (INBT)((Object)abstractList));
        compoundNBT.put("size", this.writeInts(this.size.getX(), this.size.getY(), this.size.getZ()));
        compoundNBT.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
        return compoundNBT;
    }

    public void read(CompoundNBT compoundNBT) {
        int n;
        ListNBT listNBT;
        this.blocks.clear();
        this.entities.clear();
        ListNBT listNBT2 = compoundNBT.getList("size", 3);
        this.size = new BlockPos(listNBT2.getInt(0), listNBT2.getInt(1), listNBT2.getInt(2));
        ListNBT listNBT3 = compoundNBT.getList("blocks", 10);
        if (compoundNBT.contains("palettes", 0)) {
            listNBT = compoundNBT.getList("palettes", 9);
            for (n = 0; n < listNBT.size(); ++n) {
                this.readPalletesAndBlocks(listNBT.getList(n), listNBT3);
            }
        } else {
            this.readPalletesAndBlocks(compoundNBT.getList("palette", 10), listNBT3);
        }
        listNBT = compoundNBT.getList("entities", 10);
        for (n = 0; n < listNBT.size(); ++n) {
            CompoundNBT compoundNBT2 = listNBT.getCompound(n);
            ListNBT listNBT4 = compoundNBT2.getList("pos", 6);
            Vector3d vector3d = new Vector3d(listNBT4.getDouble(0), listNBT4.getDouble(1), listNBT4.getDouble(2));
            ListNBT listNBT5 = compoundNBT2.getList("blockPos", 3);
            BlockPos blockPos = new BlockPos(listNBT5.getInt(0), listNBT5.getInt(1), listNBT5.getInt(2));
            if (!compoundNBT2.contains("nbt")) continue;
            CompoundNBT compoundNBT3 = compoundNBT2.getCompound("nbt");
            this.entities.add(new EntityInfo(vector3d, blockPos, compoundNBT3));
        }
    }

    private void readPalletesAndBlocks(ListNBT listNBT, ListNBT listNBT2) {
        BasicPalette basicPalette = new BasicPalette();
        for (int i = 0; i < listNBT.size(); ++i) {
            basicPalette.addMapping(NBTUtil.readBlockState(listNBT.getCompound(i)), i);
        }
        ArrayList<BlockInfo> arrayList = Lists.newArrayList();
        ArrayList<BlockInfo> arrayList2 = Lists.newArrayList();
        ArrayList<BlockInfo> arrayList3 = Lists.newArrayList();
        for (int i = 0; i < listNBT2.size(); ++i) {
            CompoundNBT compoundNBT = listNBT2.getCompound(i);
            ListNBT listNBT3 = compoundNBT.getList("pos", 3);
            BlockPos blockPos = new BlockPos(listNBT3.getInt(0), listNBT3.getInt(1), listNBT3.getInt(2));
            BlockState blockState = basicPalette.stateFor(compoundNBT.getInt("state"));
            CompoundNBT compoundNBT2 = compoundNBT.contains("nbt") ? compoundNBT.getCompound("nbt") : null;
            BlockInfo blockInfo = new BlockInfo(blockPos, blockState, compoundNBT2);
            Template.func_237149_a_(blockInfo, arrayList, arrayList2, arrayList3);
        }
        List<BlockInfo> list = Template.func_237151_a_(arrayList, arrayList2, arrayList3);
        this.blocks.add(new Palette(list));
    }

    private ListNBT writeInts(int ... nArray) {
        ListNBT listNBT = new ListNBT();
        for (int n : nArray) {
            listNBT.add(IntNBT.valueOf(n));
        }
        return listNBT;
    }

    private ListNBT writeDoubles(double ... dArray) {
        ListNBT listNBT = new ListNBT();
        for (double d : dArray) {
            listNBT.add(DoubleNBT.valueOf(d));
        }
        return listNBT;
    }

    private static void lambda$func_237143_a_$5(Mirror mirror, Rotation rotation, Vector3d vector3d, boolean bl, IServerWorld iServerWorld, CompoundNBT compoundNBT, Entity entity2) {
        float f = entity2.getMirroredYaw(mirror);
        entity2.setLocationAndAngles(vector3d.x, vector3d.y, vector3d.z, f += entity2.rotationYaw - entity2.getRotatedYaw(rotation), entity2.rotationPitch);
        if (bl && entity2 instanceof MobEntity) {
            ((MobEntity)entity2).onInitialSpawn(iServerWorld, iServerWorld.getDifficultyForLocation(new BlockPos(vector3d)), SpawnReason.STRUCTURE, null, compoundNBT);
        }
        iServerWorld.func_242417_l(entity2);
    }

    private static void lambda$func_222857_a$4(int n, int n2, int n3, IWorld iWorld, int n4, Direction direction, int n5, int n6, int n7) {
        BlockState blockState;
        BlockState blockState2;
        BlockState blockState3;
        BlockPos blockPos = new BlockPos(n + n5, n2 + n6, n3 + n7);
        BlockPos blockPos2 = blockPos.offset(direction);
        BlockState blockState4 = iWorld.getBlockState(blockPos);
        if (blockState4 != (blockState3 = blockState4.updatePostPlacement(direction, blockState2 = iWorld.getBlockState(blockPos2), iWorld, blockPos, blockPos2))) {
            iWorld.setBlockState(blockPos, blockState3, n4 & 0xFFFFFFFE);
        }
        if (blockState2 != (blockState = blockState2.updatePostPlacement(direction.getOpposite(), blockState3, iWorld, blockPos2, blockPos))) {
            iWorld.setBlockState(blockPos2, blockState, n4 & 0xFFFFFFFE);
        }
    }

    private static boolean lambda$takeEntitiesFromWorld$3(Entity entity2) {
        return !(entity2 instanceof PlayerEntity);
    }

    private static int lambda$func_237151_a_$2(BlockInfo blockInfo) {
        return blockInfo.pos.getZ();
    }

    private static int lambda$func_237151_a_$1(BlockInfo blockInfo) {
        return blockInfo.pos.getX();
    }

    private static int lambda$func_237151_a_$0(BlockInfo blockInfo) {
        return blockInfo.pos.getY();
    }

    public static class BlockInfo {
        public final BlockPos pos;
        public final BlockState state;
        public final CompoundNBT nbt;

        public BlockInfo(BlockPos blockPos, BlockState blockState, @Nullable CompoundNBT compoundNBT) {
            this.pos = blockPos;
            this.state = blockState;
            this.nbt = compoundNBT;
        }

        public String toString() {
            return String.format("<StructureBlockInfo | %s | %s | %s>", this.pos, this.state, this.nbt);
        }
    }

    public static final class Palette {
        private final List<BlockInfo> field_237155_a_;
        private final Map<Block, List<BlockInfo>> field_237156_b_ = Maps.newHashMap();

        private Palette(List<BlockInfo> list) {
            this.field_237155_a_ = list;
        }

        public List<BlockInfo> func_237157_a_() {
            return this.field_237155_a_;
        }

        public List<BlockInfo> func_237158_a_(Block block) {
            return this.field_237156_b_.computeIfAbsent(block, this::lambda$func_237158_a_$1);
        }

        private List lambda$func_237158_a_$1(Block block) {
            return this.field_237155_a_.stream().filter(arg_0 -> Palette.lambda$func_237158_a_$0(block, arg_0)).collect(Collectors.toList());
        }

        private static boolean lambda$func_237158_a_$0(Block block, BlockInfo blockInfo) {
            return blockInfo.state.isIn(block);
        }
    }

    public static class EntityInfo {
        public final Vector3d pos;
        public final BlockPos blockPos;
        public final CompoundNBT nbt;

        public EntityInfo(Vector3d vector3d, BlockPos blockPos, CompoundNBT compoundNBT) {
            this.pos = vector3d;
            this.blockPos = blockPos;
            this.nbt = compoundNBT;
        }
    }

    static class BasicPalette
    implements Iterable<BlockState> {
        public static final BlockState DEFAULT_BLOCK_STATE = Blocks.AIR.getDefaultState();
        private final ObjectIntIdentityMap<BlockState> ids = new ObjectIntIdentityMap(16);
        private int lastId;

        private BasicPalette() {
        }

        public int idFor(BlockState blockState) {
            int n = this.ids.getId(blockState);
            if (n == -1) {
                n = this.lastId++;
                this.ids.put(blockState, n);
            }
            return n;
        }

        @Nullable
        public BlockState stateFor(int n) {
            BlockState blockState = this.ids.getByValue(n);
            return blockState == null ? DEFAULT_BLOCK_STATE : blockState;
        }

        @Override
        public Iterator<BlockState> iterator() {
            return this.ids.iterator();
        }

        public void addMapping(BlockState blockState, int n) {
            this.ids.put(blockState, n);
        }
    }
}

