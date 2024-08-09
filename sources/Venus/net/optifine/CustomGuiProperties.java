/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.DropperTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.optifine.Config;
import net.optifine.CustomGuis;
import net.optifine.config.BiomeId;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchProfession;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;

public class CustomGuiProperties {
    private String fileName = null;
    private String basePath = null;
    private EnumContainer container = null;
    private Map<ResourceLocation, ResourceLocation> textureLocations = null;
    private NbtTagValue nbtName = null;
    private BiomeId[] biomes = null;
    private RangeListInt heights = null;
    private Boolean large = null;
    private Boolean trapped = null;
    private Boolean christmas = null;
    private Boolean ender = null;
    private RangeListInt levels = null;
    private MatchProfession[] professions = null;
    private EnumVariant[] variants = null;
    private DyeColor[] colors = null;
    private static final EnumVariant[] VARIANTS_HORSE = new EnumVariant[]{EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA};
    private static final EnumVariant[] VARIANTS_DISPENSER = new EnumVariant[]{EnumVariant.DISPENSER, EnumVariant.DROPPER};
    private static final EnumVariant[] VARIANTS_INVALID = new EnumVariant[0];
    private static final DyeColor[] COLORS_INVALID = new DyeColor[0];
    private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
    private static final ResourceLocation BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
    private static final ResourceLocation BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
    private static final ResourceLocation HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
    private static final ResourceLocation DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
    private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
    private static final ResourceLocation FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
    private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
    private static final ResourceLocation INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
    private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
    private static final ResourceLocation VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");

    public CustomGuiProperties(Properties properties, String string) {
        ConnectedParser connectedParser = new ConnectedParser("CustomGuis");
        this.fileName = connectedParser.parseName(string);
        this.basePath = connectedParser.parseBasePath(string);
        this.container = (EnumContainer)connectedParser.parseEnum(properties.getProperty("container"), EnumContainer.values(), "container");
        this.textureLocations = CustomGuiProperties.parseTextureLocations(properties, "texture", this.container, "textures/gui/", this.basePath);
        this.nbtName = connectedParser.parseNbtTagValue("name", properties.getProperty("name"));
        this.biomes = connectedParser.parseBiomes(properties.getProperty("biomes"));
        this.heights = connectedParser.parseRangeListInt(properties.getProperty("heights"));
        this.large = connectedParser.parseBooleanObject(properties.getProperty("large"));
        this.trapped = connectedParser.parseBooleanObject(properties.getProperty("trapped"));
        this.christmas = connectedParser.parseBooleanObject(properties.getProperty("christmas"));
        this.ender = connectedParser.parseBooleanObject(properties.getProperty("ender"));
        this.levels = connectedParser.parseRangeListInt(properties.getProperty("levels"));
        this.professions = connectedParser.parseProfessions(properties.getProperty("professions"));
        Enum[] enumArray = CustomGuiProperties.getContainerVariants(this.container);
        this.variants = (EnumVariant[])connectedParser.parseEnums(properties.getProperty("variants"), enumArray, "variants", VARIANTS_INVALID);
        this.colors = CustomGuiProperties.parseEnumDyeColors(properties.getProperty("colors"));
    }

    private static EnumVariant[] getContainerVariants(EnumContainer enumContainer) {
        if (enumContainer == EnumContainer.HORSE) {
            return VARIANTS_HORSE;
        }
        return enumContainer == EnumContainer.DISPENSER ? VARIANTS_DISPENSER : new EnumVariant[]{};
    }

    private static DyeColor[] parseEnumDyeColors(String string) {
        if (string == null) {
            return null;
        }
        string = string.toLowerCase();
        String[] stringArray = Config.tokenize(string, " ");
        DyeColor[] dyeColorArray = new DyeColor[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            DyeColor dyeColor = CustomGuiProperties.parseEnumDyeColor(string2);
            if (dyeColor == null) {
                CustomGuiProperties.warn("Invalid color: " + string2);
                return COLORS_INVALID;
            }
            dyeColorArray[i] = dyeColor;
        }
        return dyeColorArray;
    }

    private static DyeColor parseEnumDyeColor(String string) {
        if (string == null) {
            return null;
        }
        DyeColor[] dyeColorArray = DyeColor.values();
        for (int i = 0; i < dyeColorArray.length; ++i) {
            DyeColor dyeColor = dyeColorArray[i];
            if (dyeColor.getString().equals(string)) {
                return dyeColor;
            }
            if (!dyeColor.getTranslationKey().equals(string)) continue;
            return dyeColor;
        }
        return null;
    }

    private static ResourceLocation parseTextureLocation(String string, String string2) {
        if (string == null) {
            return null;
        }
        Object object = TextureUtils.fixResourcePath(string = string.trim(), string2);
        if (!((String)object).endsWith(".png")) {
            object = (String)object + ".png";
        }
        return new ResourceLocation(string2 + "/" + (String)object);
    }

    private static Map<ResourceLocation, ResourceLocation> parseTextureLocations(Properties properties, String string, EnumContainer enumContainer, String string2, String string3) {
        Object object;
        HashMap<ResourceLocation, ResourceLocation> hashMap = new HashMap<ResourceLocation, ResourceLocation>();
        String string4 = properties.getProperty(string);
        if (string4 != null) {
            object = CustomGuiProperties.getGuiTextureLocation(enumContainer);
            ResourceLocation resourceLocation = CustomGuiProperties.parseTextureLocation(string4, string3);
            if (object != null && resourceLocation != null) {
                hashMap.put((ResourceLocation)object, resourceLocation);
            }
        }
        object = string + ".";
        for (String string5 : properties.keySet()) {
            if (!string5.startsWith((String)object)) continue;
            String string6 = string5.substring(((String)object).length());
            string6 = string6.replace('\\', '/');
            string6 = StrUtils.removePrefixSuffix(string6, "/", ".png");
            String string7 = string2 + string6 + ".png";
            String string8 = properties.getProperty(string5);
            ResourceLocation resourceLocation = new ResourceLocation(string7);
            ResourceLocation resourceLocation2 = CustomGuiProperties.parseTextureLocation(string8, string3);
            hashMap.put(resourceLocation, resourceLocation2);
        }
        return hashMap;
    }

    private static ResourceLocation getGuiTextureLocation(EnumContainer enumContainer) {
        if (enumContainer == null) {
            return null;
        }
        switch (1.$SwitchMap$net$optifine$CustomGuiProperties$EnumContainer[enumContainer.ordinal()]) {
            case 1: {
                return ANVIL_GUI_TEXTURE;
            }
            case 2: {
                return BEACON_GUI_TEXTURE;
            }
            case 3: {
                return BREWING_STAND_GUI_TEXTURE;
            }
            case 4: {
                return CHEST_GUI_TEXTURE;
            }
            case 5: {
                return CRAFTING_TABLE_GUI_TEXTURE;
            }
            case 6: {
                return null;
            }
            case 7: {
                return DISPENSER_GUI_TEXTURE;
            }
            case 8: {
                return ENCHANTMENT_TABLE_GUI_TEXTURE;
            }
            case 9: {
                return FURNACE_GUI_TEXTURE;
            }
            case 10: {
                return HOPPER_GUI_TEXTURE;
            }
            case 11: {
                return HORSE_GUI_TEXTURE;
            }
            case 12: {
                return INVENTORY_GUI_TEXTURE;
            }
            case 13: {
                return SHULKER_BOX_GUI_TEXTURE;
            }
            case 14: {
                return VILLAGER_GUI_TEXTURE;
            }
        }
        return null;
    }

    public boolean isValid(String string) {
        if (this.fileName != null && this.fileName.length() > 0) {
            if (this.basePath == null) {
                CustomGuiProperties.warn("No base path found: " + string);
                return true;
            }
            if (this.container == null) {
                CustomGuiProperties.warn("No container found: " + string);
                return true;
            }
            if (this.textureLocations.isEmpty()) {
                CustomGuiProperties.warn("No texture found: " + string);
                return true;
            }
            if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
                CustomGuiProperties.warn("Invalid professions or careers: " + string);
                return true;
            }
            if (this.variants == VARIANTS_INVALID) {
                CustomGuiProperties.warn("Invalid variants: " + string);
                return true;
            }
            if (this.colors == COLORS_INVALID) {
                CustomGuiProperties.warn("Invalid colors: " + string);
                return true;
            }
            return false;
        }
        CustomGuiProperties.warn("No name found: " + string);
        return true;
    }

    private static void warn(String string) {
        Config.warn("[CustomGuis] " + string);
    }

    private boolean matchesGeneral(EnumContainer enumContainer, BlockPos blockPos, IWorldReader iWorldReader) {
        Biome biome;
        if (this.container != enumContainer) {
            return true;
        }
        if (this.biomes != null && !Matches.biome(biome = iWorldReader.getBiome(blockPos), this.biomes)) {
            return true;
        }
        return this.heights == null || this.heights.isInRange(blockPos.getY());
    }

    public boolean matchesPos(EnumContainer enumContainer, BlockPos blockPos, IWorldReader iWorldReader, Screen screen) {
        String string;
        if (!this.matchesGeneral(enumContainer, blockPos, iWorldReader)) {
            return true;
        }
        if (this.nbtName != null && !this.nbtName.matchesValue(string = CustomGuiProperties.getName(screen))) {
            return true;
        }
        switch (1.$SwitchMap$net$optifine$CustomGuiProperties$EnumContainer[enumContainer.ordinal()]) {
            case 2: {
                return this.matchesBeacon(blockPos, iWorldReader);
            }
            case 4: {
                return this.matchesChest(blockPos, iWorldReader);
            }
            case 7: {
                return this.matchesDispenser(blockPos, iWorldReader);
            }
            case 13: {
                return this.matchesShulker(blockPos, iWorldReader);
            }
        }
        return false;
    }

    public static String getName(Screen screen) {
        ITextComponent iTextComponent = screen.getTitle();
        return iTextComponent == null ? null : iTextComponent.getUnformattedComponentText();
    }

    private boolean matchesBeacon(BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader) {
        int n;
        TileEntity tileEntity = iBlockDisplayReader.getTileEntity(blockPos);
        if (!(tileEntity instanceof BeaconTileEntity)) {
            return true;
        }
        BeaconTileEntity beaconTileEntity = (BeaconTileEntity)tileEntity;
        return this.levels != null && !this.levels.isInRange(n = beaconTileEntity.getLevels());
    }

    private boolean matchesChest(BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader) {
        TileEntity tileEntity = iBlockDisplayReader.getTileEntity(blockPos);
        if (tileEntity instanceof ChestTileEntity) {
            ChestTileEntity chestTileEntity = (ChestTileEntity)tileEntity;
            return this.matchesChest(chestTileEntity, blockPos, iBlockDisplayReader);
        }
        if (tileEntity instanceof EnderChestTileEntity) {
            EnderChestTileEntity enderChestTileEntity = (EnderChestTileEntity)tileEntity;
            return this.matchesEnderChest(enderChestTileEntity, blockPos, iBlockDisplayReader);
        }
        return true;
    }

    private boolean matchesChest(ChestTileEntity chestTileEntity, BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader) {
        BlockState blockState = iBlockDisplayReader.getBlockState(blockPos);
        ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? blockState.get(ChestBlock.TYPE) : ChestType.SINGLE;
        boolean bl = chestType != ChestType.SINGLE;
        boolean bl2 = chestTileEntity instanceof TrappedChestTileEntity;
        boolean bl3 = CustomGuis.isChristmas;
        boolean bl4 = false;
        return this.matchesChest(bl, bl2, bl3, bl4);
    }

    private boolean matchesEnderChest(EnderChestTileEntity enderChestTileEntity, BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader) {
        return this.matchesChest(false, false, false, false);
    }

    private boolean matchesChest(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        if (this.large != null && this.large != bl) {
            return true;
        }
        if (this.trapped != null && this.trapped != bl2) {
            return true;
        }
        if (this.christmas != null && this.christmas != bl3) {
            return true;
        }
        return this.ender == null || this.ender == bl4;
    }

    private boolean matchesDispenser(BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader) {
        EnumVariant enumVariant;
        TileEntity tileEntity = iBlockDisplayReader.getTileEntity(blockPos);
        if (!(tileEntity instanceof DispenserTileEntity)) {
            return true;
        }
        DispenserTileEntity dispenserTileEntity = (DispenserTileEntity)tileEntity;
        return this.variants != null && !Config.equalsOne((Object)(enumVariant = this.getDispenserVariant(dispenserTileEntity)), (Object[])this.variants);
    }

    private EnumVariant getDispenserVariant(DispenserTileEntity dispenserTileEntity) {
        return dispenserTileEntity instanceof DropperTileEntity ? EnumVariant.DROPPER : EnumVariant.DISPENSER;
    }

    private boolean matchesShulker(BlockPos blockPos, IBlockDisplayReader iBlockDisplayReader) {
        DyeColor dyeColor;
        TileEntity tileEntity = iBlockDisplayReader.getTileEntity(blockPos);
        if (!(tileEntity instanceof ShulkerBoxTileEntity)) {
            return true;
        }
        ShulkerBoxTileEntity shulkerBoxTileEntity = (ShulkerBoxTileEntity)tileEntity;
        return this.colors != null && !Config.equalsOne(dyeColor = shulkerBoxTileEntity.getColor(), this.colors);
    }

    public boolean matchesEntity(EnumContainer enumContainer, Entity entity2, IWorldReader iWorldReader) {
        String string;
        if (!this.matchesGeneral(enumContainer, entity2.getPosition(), iWorldReader)) {
            return true;
        }
        if (this.nbtName != null && !this.nbtName.matchesValue(string = entity2.getScoreboardName())) {
            return true;
        }
        switch (1.$SwitchMap$net$optifine$CustomGuiProperties$EnumContainer[enumContainer.ordinal()]) {
            case 11: {
                return this.matchesHorse(entity2, iWorldReader);
            }
            case 14: {
                return this.matchesVillager(entity2, iWorldReader);
            }
        }
        return false;
    }

    private boolean matchesVillager(Entity entity2, IBlockDisplayReader iBlockDisplayReader) {
        int n;
        VillagerData villagerData;
        VillagerProfession villagerProfession;
        if (!(entity2 instanceof VillagerEntity)) {
            return true;
        }
        VillagerEntity villagerEntity = (VillagerEntity)entity2;
        return this.professions != null && !MatchProfession.matchesOne(villagerProfession = (villagerData = villagerEntity.getVillagerData()).getProfession(), n = villagerData.getLevel(), this.professions);
    }

    private boolean matchesHorse(Entity entity2, IBlockDisplayReader iBlockDisplayReader) {
        DyeColor dyeColor;
        Object object;
        if (!(entity2 instanceof AbstractHorseEntity)) {
            return true;
        }
        AbstractHorseEntity abstractHorseEntity = (AbstractHorseEntity)entity2;
        if (this.variants != null && !Config.equalsOne(object = this.getHorseVariant(abstractHorseEntity), (Object[])this.variants)) {
            return true;
        }
        return this.colors != null && abstractHorseEntity instanceof LlamaEntity && !Config.equalsOne(dyeColor = ((LlamaEntity)(object = (LlamaEntity)abstractHorseEntity)).getColor(), this.colors);
    }

    private EnumVariant getHorseVariant(AbstractHorseEntity abstractHorseEntity) {
        if (abstractHorseEntity instanceof HorseEntity) {
            return EnumVariant.HORSE;
        }
        if (abstractHorseEntity instanceof DonkeyEntity) {
            return EnumVariant.DONKEY;
        }
        if (abstractHorseEntity instanceof MuleEntity) {
            return EnumVariant.MULE;
        }
        return abstractHorseEntity instanceof LlamaEntity ? EnumVariant.LLAMA : null;
    }

    public EnumContainer getContainer() {
        return this.container;
    }

    public ResourceLocation getTextureLocation(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = this.textureLocations.get(resourceLocation);
        return resourceLocation2 == null ? resourceLocation : resourceLocation2;
    }

    public String toString() {
        return "name: " + this.fileName + ", container: " + this.container + ", textures: " + this.textureLocations;
    }

    public static enum EnumContainer {
        ANVIL,
        BEACON,
        BREWING_STAND,
        CHEST,
        CRAFTING,
        DISPENSER,
        ENCHANTMENT,
        FURNACE,
        HOPPER,
        HORSE,
        VILLAGER,
        SHULKER_BOX,
        CREATIVE,
        INVENTORY;

    }

    private static enum EnumVariant {
        HORSE,
        DONKEY,
        MULE,
        LLAMA,
        DISPENSER,
        DROPPER;

    }
}

