/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.BellTileEntity;
import net.minecraft.tileentity.BlastFurnaceTileEntity;
import net.minecraft.tileentity.BrewingStandTileEntity;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.ComparatorTileEntity;
import net.minecraft.tileentity.ConduitTileEntity;
import net.minecraft.tileentity.DaylightDetectorTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.DropperTileEntity;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.SmokerTileEntity;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntityType<T extends TileEntity> {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final TileEntityType<FurnaceTileEntity> FURNACE = TileEntityType.register("furnace", Builder.create(FurnaceTileEntity::new, Blocks.FURNACE));
    public static final TileEntityType<ChestTileEntity> CHEST = TileEntityType.register("chest", Builder.create(ChestTileEntity::new, Blocks.CHEST));
    public static final TileEntityType<TrappedChestTileEntity> TRAPPED_CHEST = TileEntityType.register("trapped_chest", Builder.create(TrappedChestTileEntity::new, Blocks.TRAPPED_CHEST));
    public static final TileEntityType<EnderChestTileEntity> ENDER_CHEST = TileEntityType.register("ender_chest", Builder.create(EnderChestTileEntity::new, Blocks.ENDER_CHEST));
    public static final TileEntityType<JukeboxTileEntity> JUKEBOX = TileEntityType.register("jukebox", Builder.create(JukeboxTileEntity::new, Blocks.JUKEBOX));
    public static final TileEntityType<DispenserTileEntity> DISPENSER = TileEntityType.register("dispenser", Builder.create(DispenserTileEntity::new, Blocks.DISPENSER));
    public static final TileEntityType<DropperTileEntity> DROPPER = TileEntityType.register("dropper", Builder.create(DropperTileEntity::new, Blocks.DROPPER));
    public static final TileEntityType<SignTileEntity> SIGN = TileEntityType.register("sign", Builder.create(SignTileEntity::new, Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN));
    public static final TileEntityType<MobSpawnerTileEntity> MOB_SPAWNER = TileEntityType.register("mob_spawner", Builder.create(MobSpawnerTileEntity::new, Blocks.SPAWNER));
    public static final TileEntityType<PistonTileEntity> PISTON = TileEntityType.register("piston", Builder.create(PistonTileEntity::new, Blocks.MOVING_PISTON));
    public static final TileEntityType<BrewingStandTileEntity> BREWING_STAND = TileEntityType.register("brewing_stand", Builder.create(BrewingStandTileEntity::new, Blocks.BREWING_STAND));
    public static final TileEntityType<EnchantingTableTileEntity> ENCHANTING_TABLE = TileEntityType.register("enchanting_table", Builder.create(EnchantingTableTileEntity::new, Blocks.ENCHANTING_TABLE));
    public static final TileEntityType<EndPortalTileEntity> END_PORTAL = TileEntityType.register("end_portal", Builder.create(EndPortalTileEntity::new, Blocks.END_PORTAL));
    public static final TileEntityType<BeaconTileEntity> BEACON = TileEntityType.register("beacon", Builder.create(BeaconTileEntity::new, Blocks.BEACON));
    public static final TileEntityType<SkullTileEntity> SKULL = TileEntityType.register("skull", Builder.create(SkullTileEntity::new, Blocks.SKELETON_SKULL, Blocks.SKELETON_WALL_SKULL, Blocks.CREEPER_HEAD, Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_HEAD, Blocks.DRAGON_WALL_HEAD, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL, Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD));
    public static final TileEntityType<DaylightDetectorTileEntity> DAYLIGHT_DETECTOR = TileEntityType.register("daylight_detector", Builder.create(DaylightDetectorTileEntity::new, Blocks.DAYLIGHT_DETECTOR));
    public static final TileEntityType<HopperTileEntity> HOPPER = TileEntityType.register("hopper", Builder.create(HopperTileEntity::new, Blocks.HOPPER));
    public static final TileEntityType<ComparatorTileEntity> COMPARATOR = TileEntityType.register("comparator", Builder.create(ComparatorTileEntity::new, Blocks.COMPARATOR));
    public static final TileEntityType<BannerTileEntity> BANNER = TileEntityType.register("banner", Builder.create(BannerTileEntity::new, Blocks.WHITE_BANNER, Blocks.ORANGE_BANNER, Blocks.MAGENTA_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.YELLOW_BANNER, Blocks.LIME_BANNER, Blocks.PINK_BANNER, Blocks.GRAY_BANNER, Blocks.LIGHT_GRAY_BANNER, Blocks.CYAN_BANNER, Blocks.PURPLE_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.GREEN_BANNER, Blocks.RED_BANNER, Blocks.BLACK_BANNER, Blocks.WHITE_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER, Blocks.LIME_WALL_BANNER, Blocks.PINK_WALL_BANNER, Blocks.GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.CYAN_WALL_BANNER, Blocks.PURPLE_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.BLACK_WALL_BANNER));
    public static final TileEntityType<StructureBlockTileEntity> STRUCTURE_BLOCK = TileEntityType.register("structure_block", Builder.create(StructureBlockTileEntity::new, Blocks.STRUCTURE_BLOCK));
    public static final TileEntityType<EndGatewayTileEntity> END_GATEWAY = TileEntityType.register("end_gateway", Builder.create(EndGatewayTileEntity::new, Blocks.END_GATEWAY));
    public static final TileEntityType<CommandBlockTileEntity> COMMAND_BLOCK = TileEntityType.register("command_block", Builder.create(CommandBlockTileEntity::new, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.REPEATING_COMMAND_BLOCK));
    public static final TileEntityType<ShulkerBoxTileEntity> SHULKER_BOX = TileEntityType.register("shulker_box", Builder.create(ShulkerBoxTileEntity::new, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX));
    public static final TileEntityType<BedTileEntity> BED = TileEntityType.register("bed", Builder.create(BedTileEntity::new, Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED));
    public static final TileEntityType<ConduitTileEntity> CONDUIT = TileEntityType.register("conduit", Builder.create(ConduitTileEntity::new, Blocks.CONDUIT));
    public static final TileEntityType<BarrelTileEntity> BARREL = TileEntityType.register("barrel", Builder.create(BarrelTileEntity::new, Blocks.BARREL));
    public static final TileEntityType<SmokerTileEntity> SMOKER = TileEntityType.register("smoker", Builder.create(SmokerTileEntity::new, Blocks.SMOKER));
    public static final TileEntityType<BlastFurnaceTileEntity> BLAST_FURNACE = TileEntityType.register("blast_furnace", Builder.create(BlastFurnaceTileEntity::new, Blocks.BLAST_FURNACE));
    public static final TileEntityType<LecternTileEntity> LECTERN = TileEntityType.register("lectern", Builder.create(LecternTileEntity::new, Blocks.LECTERN));
    public static final TileEntityType<BellTileEntity> BELL = TileEntityType.register("bell", Builder.create(BellTileEntity::new, Blocks.BELL));
    public static final TileEntityType<JigsawTileEntity> JIGSAW = TileEntityType.register("jigsaw", Builder.create(JigsawTileEntity::new, Blocks.JIGSAW));
    public static final TileEntityType<CampfireTileEntity> CAMPFIRE = TileEntityType.register("campfire", Builder.create(CampfireTileEntity::new, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE));
    public static final TileEntityType<BeehiveTileEntity> BEEHIVE = TileEntityType.register("beehive", Builder.create(BeehiveTileEntity::new, Blocks.BEE_NEST, Blocks.BEEHIVE));
    private final Supplier<? extends T> factory;
    private final Set<Block> validBlocks;
    private final Type<?> datafixerType;

    @Nullable
    public static ResourceLocation getId(TileEntityType<?> tileEntityType) {
        return Registry.BLOCK_ENTITY_TYPE.getKey(tileEntityType);
    }

    private static <T extends TileEntity> TileEntityType<T> register(String string, Builder<T> builder) {
        if (builder.blocks.isEmpty()) {
            LOGGER.warn("Block entity type {} requires at least one valid block to be defined!", (Object)string);
        }
        Type<?> type = Util.attemptDataFix(TypeReferences.BLOCK_ENTITY, string);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, string, builder.build(type));
    }

    public TileEntityType(Supplier<? extends T> supplier, Set<Block> set, Type<?> type) {
        this.factory = supplier;
        this.validBlocks = set;
        this.datafixerType = type;
    }

    @Nullable
    public T create() {
        return (T)((TileEntity)this.factory.get());
    }

    public boolean isValidBlock(Block block) {
        return this.validBlocks.contains(block);
    }

    @Nullable
    public T getIfExists(IBlockReader iBlockReader, BlockPos blockPos) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        return (T)(tileEntity != null && tileEntity.getType() == this ? tileEntity : null);
    }

    public static final class Builder<T extends TileEntity> {
        private final Supplier<? extends T> factory;
        private final Set<Block> blocks;

        private Builder(Supplier<? extends T> supplier, Set<Block> set) {
            this.factory = supplier;
            this.blocks = set;
        }

        public static <T extends TileEntity> Builder<T> create(Supplier<? extends T> supplier, Block ... blockArray) {
            return new Builder<T>(supplier, ImmutableSet.copyOf(blockArray));
        }

        public TileEntityType<T> build(Type<?> type) {
            return new TileEntityType<T>(this.factory, this.blocks, type);
        }
    }
}

