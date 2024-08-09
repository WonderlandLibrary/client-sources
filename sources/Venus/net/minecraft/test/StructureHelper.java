/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerTickList;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.io.IOUtils;

public class StructureHelper {
    public static String field_229590_a_ = "gameteststructures";

    public static Rotation func_240562_a_(int n) {
        switch (n) {
            case 0: {
                return Rotation.NONE;
            }
            case 1: {
                return Rotation.CLOCKWISE_90;
            }
            case 2: {
                return Rotation.CLOCKWISE_180;
            }
            case 3: {
                return Rotation.COUNTERCLOCKWISE_90;
            }
        }
        throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + n);
    }

    public static AxisAlignedBB func_229594_a_(StructureBlockTileEntity structureBlockTileEntity) {
        BlockPos blockPos = structureBlockTileEntity.getPos();
        BlockPos blockPos2 = blockPos.add(structureBlockTileEntity.getStructureSize().add(-1, -1, -1));
        BlockPos blockPos3 = Template.getTransformedPos(blockPos2, Mirror.NONE, structureBlockTileEntity.getRotation(), blockPos);
        return new AxisAlignedBB(blockPos, blockPos3);
    }

    public static MutableBoundingBox func_240568_b_(StructureBlockTileEntity structureBlockTileEntity) {
        BlockPos blockPos = structureBlockTileEntity.getPos();
        BlockPos blockPos2 = blockPos.add(structureBlockTileEntity.getStructureSize().add(-1, -1, -1));
        BlockPos blockPos3 = Template.getTransformedPos(blockPos2, Mirror.NONE, structureBlockTileEntity.getRotation(), blockPos);
        return new MutableBoundingBox(blockPos, blockPos3);
    }

    public static void func_240564_a_(BlockPos blockPos, BlockPos blockPos2, Rotation rotation, ServerWorld serverWorld) {
        BlockPos blockPos3 = Template.getTransformedPos(blockPos.add(blockPos2), Mirror.NONE, rotation, blockPos);
        serverWorld.setBlockState(blockPos3, Blocks.COMMAND_BLOCK.getDefaultState());
        CommandBlockTileEntity commandBlockTileEntity = (CommandBlockTileEntity)serverWorld.getTileEntity(blockPos3);
        commandBlockTileEntity.getCommandBlockLogic().setCommand("test runthis");
        BlockPos blockPos4 = Template.getTransformedPos(blockPos3.add(0, 0, -1), Mirror.NONE, rotation, blockPos3);
        serverWorld.setBlockState(blockPos4, Blocks.STONE_BUTTON.getDefaultState().rotate(rotation));
    }

    public static void func_229603_a_(String string, BlockPos blockPos, BlockPos blockPos2, Rotation rotation, ServerWorld serverWorld) {
        MutableBoundingBox mutableBoundingBox = StructureHelper.func_229598_a_(blockPos, blockPos2, rotation);
        StructureHelper.func_229595_a_(mutableBoundingBox, blockPos.getY(), serverWorld);
        serverWorld.setBlockState(blockPos, Blocks.STRUCTURE_BLOCK.getDefaultState());
        StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)serverWorld.getTileEntity(blockPos);
        structureBlockTileEntity.setIgnoresEntities(true);
        structureBlockTileEntity.setName(new ResourceLocation(string));
        structureBlockTileEntity.setSize(blockPos2);
        structureBlockTileEntity.setMode(StructureMode.SAVE);
        structureBlockTileEntity.setShowBoundingBox(false);
    }

    public static StructureBlockTileEntity func_240565_a_(String string, BlockPos blockPos, Rotation rotation, int n, ServerWorld serverWorld, boolean bl) {
        BlockPos blockPos2;
        BlockPos blockPos3 = StructureHelper.func_229605_a_(string, serverWorld).getSize();
        MutableBoundingBox mutableBoundingBox = StructureHelper.func_229598_a_(blockPos, blockPos3, rotation);
        if (rotation == Rotation.NONE) {
            blockPos2 = blockPos;
        } else if (rotation == Rotation.CLOCKWISE_90) {
            blockPos2 = blockPos.add(blockPos3.getZ() - 1, 0, 0);
        } else if (rotation == Rotation.CLOCKWISE_180) {
            blockPos2 = blockPos.add(blockPos3.getX() - 1, 0, blockPos3.getZ() - 1);
        } else {
            if (rotation != Rotation.COUNTERCLOCKWISE_90) {
                throw new IllegalArgumentException("Invalid rotation: " + rotation);
            }
            blockPos2 = blockPos.add(0, 0, blockPos3.getX() - 1);
        }
        StructureHelper.func_229608_b_(blockPos, serverWorld);
        StructureHelper.func_229595_a_(mutableBoundingBox, blockPos.getY(), serverWorld);
        StructureBlockTileEntity structureBlockTileEntity = StructureHelper.func_240566_a_(string, blockPos2, rotation, serverWorld, bl);
        ((ServerTickList)serverWorld.getPendingBlockTicks()).getPending(mutableBoundingBox, true, true);
        serverWorld.clearBlockEvents(mutableBoundingBox);
        return structureBlockTileEntity;
    }

    private static void func_229608_b_(BlockPos blockPos, ServerWorld serverWorld) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        for (int i = -1; i < 4; ++i) {
            for (int j = -1; j < 4; ++j) {
                int n = chunkPos.x + i;
                int n2 = chunkPos.z + j;
                serverWorld.forceChunk(n, n2, false);
            }
        }
    }

    public static void func_229595_a_(MutableBoundingBox mutableBoundingBox, int n, ServerWorld serverWorld) {
        MutableBoundingBox mutableBoundingBox2 = new MutableBoundingBox(mutableBoundingBox.minX - 2, mutableBoundingBox.minY - 3, mutableBoundingBox.minZ - 3, mutableBoundingBox.maxX + 3, mutableBoundingBox.maxY + 20, mutableBoundingBox.maxZ + 3);
        BlockPos.getAllInBox(mutableBoundingBox2).forEach(arg_0 -> StructureHelper.lambda$func_229595_a_$0(n, serverWorld, arg_0));
        ((ServerTickList)serverWorld.getPendingBlockTicks()).getPending(mutableBoundingBox2, true, true);
        serverWorld.clearBlockEvents(mutableBoundingBox2);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(mutableBoundingBox2.minX, mutableBoundingBox2.minY, mutableBoundingBox2.minZ, mutableBoundingBox2.maxX, mutableBoundingBox2.maxY, mutableBoundingBox2.maxZ);
        List<Entity> list = serverWorld.getEntitiesWithinAABB(Entity.class, axisAlignedBB, StructureHelper::lambda$func_229595_a_$1);
        list.forEach(Entity::remove);
    }

    public static MutableBoundingBox func_229598_a_(BlockPos blockPos, BlockPos blockPos2, Rotation rotation) {
        BlockPos blockPos3 = blockPos.add(blockPos2).add(-1, -1, -1);
        BlockPos blockPos4 = Template.getTransformedPos(blockPos3, Mirror.NONE, rotation, blockPos);
        MutableBoundingBox mutableBoundingBox = MutableBoundingBox.createProper(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos4.getX(), blockPos4.getY(), blockPos4.getZ());
        int n = Math.min(mutableBoundingBox.minX, mutableBoundingBox.maxX);
        int n2 = Math.min(mutableBoundingBox.minZ, mutableBoundingBox.maxZ);
        BlockPos blockPos5 = new BlockPos(blockPos.getX() - n, 0, blockPos.getZ() - n2);
        mutableBoundingBox.func_236989_a_(blockPos5);
        return mutableBoundingBox;
    }

    public static Optional<BlockPos> func_229596_a_(BlockPos blockPos, int n, ServerWorld serverWorld) {
        return StructureHelper.func_229609_c_(blockPos, n, serverWorld).stream().filter(arg_0 -> StructureHelper.lambda$func_229596_a_$2(blockPos, serverWorld, arg_0)).findFirst();
    }

    @Nullable
    public static BlockPos func_229607_b_(BlockPos blockPos, int n, ServerWorld serverWorld) {
        Comparator<BlockPos> comparator = Comparator.comparingInt(arg_0 -> StructureHelper.lambda$func_229607_b_$3(blockPos, arg_0));
        Collection<BlockPos> collection = StructureHelper.func_229609_c_(blockPos, n, serverWorld);
        Optional<BlockPos> optional = collection.stream().min(comparator);
        return optional.orElse(null);
    }

    public static Collection<BlockPos> func_229609_c_(BlockPos blockPos, int n, ServerWorld serverWorld) {
        ArrayList<BlockPos> arrayList = Lists.newArrayList();
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos);
        axisAlignedBB = axisAlignedBB.grow(n);
        for (int i = (int)axisAlignedBB.minX; i <= (int)axisAlignedBB.maxX; ++i) {
            for (int j = (int)axisAlignedBB.minY; j <= (int)axisAlignedBB.maxY; ++j) {
                for (int k = (int)axisAlignedBB.minZ; k <= (int)axisAlignedBB.maxZ; ++k) {
                    BlockPos blockPos2 = new BlockPos(i, j, k);
                    BlockState blockState = serverWorld.getBlockState(blockPos2);
                    if (!blockState.isIn(Blocks.STRUCTURE_BLOCK)) continue;
                    arrayList.add(blockPos2);
                }
            }
        }
        return arrayList;
    }

    private static Template func_229605_a_(String string, ServerWorld serverWorld) {
        TemplateManager templateManager = serverWorld.getStructureTemplateManager();
        Template template = templateManager.getTemplate(new ResourceLocation(string));
        if (template != null) {
            return template;
        }
        String string2 = string + ".snbt";
        Path path = Paths.get(field_229590_a_, string2);
        CompoundNBT compoundNBT = StructureHelper.func_229606_a_(path);
        if (compoundNBT == null) {
            throw new RuntimeException("Could not find structure file " + path + ", and the structure is not available in the world structures either.");
        }
        return templateManager.func_227458_a_(compoundNBT);
    }

    private static StructureBlockTileEntity func_240566_a_(String string, BlockPos blockPos, Rotation rotation, ServerWorld serverWorld, boolean bl) {
        serverWorld.setBlockState(blockPos, Blocks.STRUCTURE_BLOCK.getDefaultState());
        StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)serverWorld.getTileEntity(blockPos);
        structureBlockTileEntity.setMode(StructureMode.LOAD);
        structureBlockTileEntity.setRotation(rotation);
        structureBlockTileEntity.setIgnoresEntities(true);
        structureBlockTileEntity.setName(new ResourceLocation(string));
        structureBlockTileEntity.func_242688_a(serverWorld, bl);
        if (structureBlockTileEntity.getStructureSize() != BlockPos.ZERO) {
            return structureBlockTileEntity;
        }
        Template template = StructureHelper.func_229605_a_(string, serverWorld);
        structureBlockTileEntity.func_242689_a(serverWorld, bl, template);
        if (structureBlockTileEntity.getStructureSize() == BlockPos.ZERO) {
            throw new RuntimeException("Failed to load structure " + string);
        }
        return structureBlockTileEntity;
    }

    @Nullable
    private static CompoundNBT func_229606_a_(Path path) {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String string = IOUtils.toString(bufferedReader);
            return JsonToNBT.getTagFromJson(string);
        } catch (IOException iOException) {
            return null;
        } catch (CommandSyntaxException commandSyntaxException) {
            throw new RuntimeException("Error while trying to load structure " + path, commandSyntaxException);
        }
    }

    private static void func_229591_a_(int n, BlockPos blockPos, ServerWorld serverWorld) {
        Object object;
        BlockState blockState = null;
        FlatGenerationSettings flatGenerationSettings = FlatGenerationSettings.func_242869_a(serverWorld.func_241828_r().getRegistry(Registry.BIOME_KEY));
        if (flatGenerationSettings instanceof FlatGenerationSettings) {
            object = flatGenerationSettings.getStates();
            if (blockPos.getY() < n && blockPos.getY() <= ((BlockState[])object).length) {
                blockState = object[blockPos.getY() - 1];
            }
        } else if (blockPos.getY() == n - 1) {
            blockState = serverWorld.getBiome(blockPos).getGenerationSettings().getSurfaceBuilderConfig().getTop();
        } else if (blockPos.getY() < n - 1) {
            blockState = serverWorld.getBiome(blockPos).getGenerationSettings().getSurfaceBuilderConfig().getUnder();
        }
        if (blockState == null) {
            blockState = Blocks.AIR.getDefaultState();
        }
        object = new BlockStateInput(blockState, Collections.emptySet(), null);
        ((BlockStateInput)object).place(serverWorld, blockPos, 1);
        serverWorld.func_230547_a_(blockPos, blockState.getBlock());
    }

    private static boolean func_229599_a_(BlockPos blockPos, BlockPos blockPos2, ServerWorld serverWorld) {
        StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)serverWorld.getTileEntity(blockPos);
        AxisAlignedBB axisAlignedBB = StructureHelper.func_229594_a_(structureBlockTileEntity).grow(1.0);
        return axisAlignedBB.contains(Vector3d.copyCentered(blockPos2));
    }

    private static int lambda$func_229607_b_$3(BlockPos blockPos, BlockPos blockPos2) {
        return blockPos2.manhattanDistance(blockPos);
    }

    private static boolean lambda$func_229596_a_$2(BlockPos blockPos, ServerWorld serverWorld, BlockPos blockPos2) {
        return StructureHelper.func_229599_a_(blockPos2, blockPos, serverWorld);
    }

    private static boolean lambda$func_229595_a_$1(Entity entity2) {
        return !(entity2 instanceof PlayerEntity);
    }

    private static void lambda$func_229595_a_$0(int n, ServerWorld serverWorld, BlockPos blockPos) {
        StructureHelper.func_229591_a_(n, blockPos, serverWorld);
    }
}

