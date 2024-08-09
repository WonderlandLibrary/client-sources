/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.AbstractMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;

public class FilledMapItem
extends AbstractMapItem {
    public FilledMapItem(Item.Properties properties) {
        super(properties);
    }

    public static ItemStack setupNewMap(World world, int n, int n2, byte by, boolean bl, boolean bl2) {
        ItemStack itemStack = new ItemStack(Items.FILLED_MAP);
        FilledMapItem.createMapData(itemStack, world, n, n2, by, bl, bl2, world.getDimensionKey());
        return itemStack;
    }

    @Nullable
    public static MapData getData(ItemStack itemStack, World world) {
        return world.getMapData(FilledMapItem.getMapName(FilledMapItem.getMapId(itemStack)));
    }

    @Nullable
    public static MapData getMapData(ItemStack itemStack, World world) {
        MapData mapData = FilledMapItem.getData(itemStack, world);
        if (mapData == null && world instanceof ServerWorld) {
            mapData = FilledMapItem.createMapData(itemStack, world, world.getWorldInfo().getSpawnX(), world.getWorldInfo().getSpawnZ(), 3, false, false, world.getDimensionKey());
        }
        return mapData;
    }

    public static int getMapId(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        return compoundNBT != null && compoundNBT.contains("map", 0) ? compoundNBT.getInt("map") : 0;
    }

    private static MapData createMapData(ItemStack itemStack, World world, int n, int n2, int n3, boolean bl, boolean bl2, RegistryKey<World> registryKey) {
        int n4 = world.getNextMapId();
        MapData mapData = new MapData(FilledMapItem.getMapName(n4));
        mapData.initData(n, n2, n3, bl, bl2, registryKey);
        world.registerMapData(mapData);
        itemStack.getOrCreateTag().putInt("map", n4);
        return mapData;
    }

    public static String getMapName(int n) {
        return "map_" + n;
    }

    public void updateMapData(World world, Entity entity2, MapData mapData) {
        if (world.getDimensionKey() == mapData.dimension && entity2 instanceof PlayerEntity) {
            int n = 1 << mapData.scale;
            int n2 = mapData.xCenter;
            int n3 = mapData.zCenter;
            int n4 = MathHelper.floor(entity2.getPosX() - (double)n2) / n + 64;
            int n5 = MathHelper.floor(entity2.getPosZ() - (double)n3) / n + 64;
            int n6 = 128 / n;
            if (world.getDimensionType().getHasCeiling()) {
                n6 /= 2;
            }
            MapData.MapInfo mapInfo = mapData.getMapInfo((PlayerEntity)entity2);
            ++mapInfo.step;
            boolean bl = false;
            for (int i = n4 - n6 + 1; i < n4 + n6; ++i) {
                if ((i & 0xF) != (mapInfo.step & 0xF) && !bl) continue;
                bl = false;
                double d = 0.0;
                for (int j = n5 - n6 - 1; j < n5 + n6; ++j) {
                    byte by;
                    MaterialColor materialColor;
                    int n7;
                    int n8;
                    if (i < 0 || j < -1 || i >= 128 || j >= 128) continue;
                    int n9 = i - n4;
                    int n10 = j - n5;
                    boolean bl2 = n9 * n9 + n10 * n10 > (n6 - 2) * (n6 - 2);
                    int n11 = (n2 / n + i - 64) * n;
                    int n12 = (n3 / n + j - 64) * n;
                    LinkedHashMultiset<MaterialColor> linkedHashMultiset = LinkedHashMultiset.create();
                    Chunk chunk = world.getChunkAt(new BlockPos(n11, 0, n12));
                    if (chunk.isEmpty()) continue;
                    ChunkPos chunkPos = chunk.getPos();
                    int n13 = n11 & 0xF;
                    int n14 = n12 & 0xF;
                    int n15 = 0;
                    double d2 = 0.0;
                    if (world.getDimensionType().getHasCeiling()) {
                        int n16 = n11 + n12 * 231871;
                        if (((n16 = n16 * n16 * 31287121 + n16 * 11) >> 20 & 1) == 0) {
                            linkedHashMultiset.add(Blocks.DIRT.getDefaultState().getMaterialColor(world, BlockPos.ZERO), 10);
                        } else {
                            linkedHashMultiset.add(Blocks.STONE.getDefaultState().getMaterialColor(world, BlockPos.ZERO), 100);
                        }
                        d2 = 100.0;
                    } else {
                        BlockPos.Mutable mutable = new BlockPos.Mutable();
                        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
                        for (n8 = 0; n8 < n; ++n8) {
                            for (int k = 0; k < n; ++k) {
                                BlockState blockState;
                                n7 = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, n8 + n13, k + n14) + 1;
                                if (n7 <= 1) {
                                    blockState = Blocks.BEDROCK.getDefaultState();
                                } else {
                                    do {
                                        mutable.setPos(chunkPos.getXStart() + n8 + n13, --n7, chunkPos.getZStart() + k + n14);
                                    } while ((blockState = chunk.getBlockState(mutable)).getMaterialColor(world, mutable) == MaterialColor.AIR && n7 > 0);
                                    if (n7 > 0 && !blockState.getFluidState().isEmpty()) {
                                        BlockState blockState2;
                                        int n17 = n7 - 1;
                                        mutable2.setPos(mutable);
                                        do {
                                            mutable2.setY(n17--);
                                            blockState2 = chunk.getBlockState(mutable2);
                                            ++n15;
                                        } while (n17 > 0 && !blockState2.getFluidState().isEmpty());
                                        blockState = this.func_211698_a(world, blockState, mutable);
                                    }
                                }
                                mapData.removeStaleBanners(world, chunkPos.getXStart() + n8 + n13, chunkPos.getZStart() + k + n14);
                                d2 += (double)n7 / (double)(n * n);
                                linkedHashMultiset.add(blockState.getMaterialColor(world, mutable));
                            }
                        }
                    }
                    n15 /= n * n;
                    double d3 = (d2 - d) * 4.0 / (double)(n + 4) + ((double)(i + j & 1) - 0.5) * 0.4;
                    n8 = 1;
                    if (d3 > 0.6) {
                        n8 = 2;
                    }
                    if (d3 < -0.6) {
                        n8 = 0;
                    }
                    if ((materialColor = Iterables.getFirst(Multisets.copyHighestCountFirst(linkedHashMultiset), MaterialColor.AIR)) == MaterialColor.WATER) {
                        d3 = (double)n15 * 0.1 + (double)(i + j & 1) * 0.2;
                        n8 = 1;
                        if (d3 < 0.5) {
                            n8 = 2;
                        }
                        if (d3 > 0.9) {
                            n8 = 0;
                        }
                    }
                    d = d2;
                    if (j < 0 || n9 * n9 + n10 * n10 >= n6 * n6 || bl2 && (i + j & 1) == 0 || (n7 = mapData.colors[i + j * 128]) == (by = (byte)(materialColor.colorIndex * 4 + n8))) continue;
                    mapData.colors[i + j * 128] = by;
                    mapData.updateMapData(i, j);
                    bl = true;
                }
            }
        }
    }

    private BlockState func_211698_a(World world, BlockState blockState, BlockPos blockPos) {
        FluidState fluidState = blockState.getFluidState();
        return !fluidState.isEmpty() && !blockState.isSolidSide(world, blockPos, Direction.UP) ? fluidState.getBlockState() : blockState;
    }

    private static boolean func_195954_a(Biome[] biomeArray, int n, int n2, int n3) {
        return biomeArray[n2 * n + n3 * n * 128 * n].getDepth() >= 0.0f;
    }

    public static void func_226642_a_(ServerWorld serverWorld, ItemStack itemStack) {
        MapData mapData = FilledMapItem.getMapData(itemStack, serverWorld);
        if (mapData != null && serverWorld.getDimensionKey() == mapData.dimension) {
            int n;
            int n2;
            int n3 = 1 << mapData.scale;
            int n4 = mapData.xCenter;
            int n5 = mapData.zCenter;
            Biome[] biomeArray = new Biome[128 * n3 * 128 * n3];
            for (n2 = 0; n2 < 128 * n3; ++n2) {
                for (n = 0; n < 128 * n3; ++n) {
                    biomeArray[n2 * 128 * n3 + n] = serverWorld.getBiome(new BlockPos((n4 / n3 - 64) * n3 + n, 0, (n5 / n3 - 64) * n3 + n2));
                }
            }
            for (n2 = 0; n2 < 128; ++n2) {
                for (n = 0; n < 128; ++n) {
                    if (n2 <= 0 || n <= 0 || n2 >= 127 || n >= 127) continue;
                    Biome biome = biomeArray[n2 * n3 + n * n3 * 128 * n3];
                    int n6 = 8;
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2 - 1, n - 1)) {
                        --n6;
                    }
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2 - 1, n + 1)) {
                        --n6;
                    }
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2 - 1, n)) {
                        --n6;
                    }
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2 + 1, n - 1)) {
                        --n6;
                    }
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2 + 1, n + 1)) {
                        --n6;
                    }
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2 + 1, n)) {
                        --n6;
                    }
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2, n - 1)) {
                        --n6;
                    }
                    if (FilledMapItem.func_195954_a(biomeArray, n3, n2, n + 1)) {
                        --n6;
                    }
                    int n7 = 3;
                    MaterialColor materialColor = MaterialColor.AIR;
                    if (biome.getDepth() < 0.0f) {
                        materialColor = MaterialColor.ADOBE;
                        if (n6 > 7 && n % 2 == 0) {
                            n7 = (n2 + (int)(MathHelper.sin((float)n + 0.0f) * 7.0f)) / 8 % 5;
                            if (n7 == 3) {
                                n7 = 1;
                            } else if (n7 == 4) {
                                n7 = 0;
                            }
                        } else if (n6 > 7) {
                            materialColor = MaterialColor.AIR;
                        } else if (n6 > 5) {
                            n7 = 1;
                        } else if (n6 > 3) {
                            n7 = 0;
                        } else if (n6 > 1) {
                            n7 = 0;
                        }
                    } else if (n6 > 0) {
                        materialColor = MaterialColor.BROWN;
                        n7 = n6 > 3 ? 1 : 3;
                    }
                    if (materialColor == MaterialColor.AIR) continue;
                    mapData.colors[n2 + n * 128] = (byte)(materialColor.colorIndex * 4 + n7);
                    mapData.updateMapData(n2, n);
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity2, int n, boolean bl) {
        MapData mapData;
        if (!world.isRemote && (mapData = FilledMapItem.getMapData(itemStack, world)) != null) {
            if (entity2 instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity)entity2;
                mapData.updateVisiblePlayers(playerEntity, itemStack);
            }
            if (!mapData.locked && (bl || entity2 instanceof PlayerEntity && ((PlayerEntity)entity2).getHeldItemOffhand() == itemStack)) {
                this.updateMapData(world, entity2, mapData);
            }
        }
    }

    @Override
    @Nullable
    public IPacket<?> getUpdatePacket(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        return FilledMapItem.getMapData(itemStack, world).getMapPacket(itemStack, world, playerEntity);
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null && compoundNBT.contains("map_scale_direction", 0)) {
            FilledMapItem.scaleMap(itemStack, world, compoundNBT.getInt("map_scale_direction"));
            compoundNBT.remove("map_scale_direction");
        } else if (compoundNBT != null && compoundNBT.contains("map_to_lock", 0) && compoundNBT.getBoolean("map_to_lock")) {
            FilledMapItem.func_219992_b(world, itemStack);
            compoundNBT.remove("map_to_lock");
        }
    }

    protected static void scaleMap(ItemStack itemStack, World world, int n) {
        MapData mapData = FilledMapItem.getMapData(itemStack, world);
        if (mapData != null) {
            FilledMapItem.createMapData(itemStack, world, mapData.xCenter, mapData.zCenter, MathHelper.clamp(mapData.scale + n, 0, 4), mapData.trackingPosition, mapData.unlimitedTracking, mapData.dimension);
        }
    }

    public static void func_219992_b(World world, ItemStack itemStack) {
        MapData mapData = FilledMapItem.getMapData(itemStack, world);
        if (mapData != null) {
            MapData mapData2 = FilledMapItem.createMapData(itemStack, world, 0, 0, mapData.scale, mapData.trackingPosition, mapData.unlimitedTracking, mapData.dimension);
            mapData2.copyFrom(mapData);
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        MapData mapData;
        MapData mapData2 = mapData = world == null ? null : FilledMapItem.getMapData(itemStack, world);
        if (mapData != null && mapData.locked) {
            list.add(new TranslationTextComponent("filled_map.locked", FilledMapItem.getMapId(itemStack)).mergeStyle(TextFormatting.GRAY));
        }
        if (iTooltipFlag.isAdvanced()) {
            if (mapData != null) {
                list.add(new TranslationTextComponent("filled_map.id", FilledMapItem.getMapId(itemStack)).mergeStyle(TextFormatting.GRAY));
                list.add(new TranslationTextComponent("filled_map.scale", 1 << mapData.scale).mergeStyle(TextFormatting.GRAY));
                list.add(new TranslationTextComponent("filled_map.level", mapData.scale, 4).mergeStyle(TextFormatting.GRAY));
            } else {
                list.add(new TranslationTextComponent("filled_map.unknown").mergeStyle(TextFormatting.GRAY));
            }
        }
    }

    public static int getColor(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getChildTag("display");
        if (compoundNBT != null && compoundNBT.contains("MapColor", 0)) {
            int n = compoundNBT.getInt("MapColor");
            return 0xFF000000 | n & 0xFFFFFF;
        }
        return 1;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockState blockState = itemUseContext.getWorld().getBlockState(itemUseContext.getPos());
        if (blockState.isIn(BlockTags.BANNERS)) {
            if (!itemUseContext.getWorld().isRemote) {
                MapData mapData = FilledMapItem.getMapData(itemUseContext.getItem(), itemUseContext.getWorld());
                mapData.tryAddBanner(itemUseContext.getWorld(), itemUseContext.getPos());
            }
            return ActionResultType.func_233537_a_(itemUseContext.getWorld().isRemote);
        }
        return super.onItemUse(itemUseContext);
    }
}

