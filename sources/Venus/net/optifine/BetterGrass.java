/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.block.MyceliumBlock;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.optifine.Config;
import net.optifine.model.BlockModelUtils;
import net.optifine.util.PropertiesOrdered;

public class BetterGrass {
    private static boolean betterGrass = true;
    private static boolean betterGrassPath = true;
    private static boolean betterMycelium = true;
    private static boolean betterPodzol = true;
    private static boolean betterGrassSnow = true;
    private static boolean betterMyceliumSnow = true;
    private static boolean betterPodzolSnow = true;
    private static boolean grassMultilayer = false;
    private static TextureAtlasSprite spriteGrass = null;
    private static TextureAtlasSprite spriteGrassSide = null;
    private static TextureAtlasSprite spriteGrassPath = null;
    private static TextureAtlasSprite spriteGrassPathSide = null;
    private static TextureAtlasSprite spriteMycelium = null;
    private static TextureAtlasSprite spritePodzol = null;
    private static TextureAtlasSprite spriteSnow = null;
    private static boolean spritesLoaded = false;
    private static IBakedModel modelCubeGrass = null;
    private static IBakedModel modelGrassPath = null;
    private static IBakedModel modelCubeGrassPath = null;
    private static IBakedModel modelCubeMycelium = null;
    private static IBakedModel modelCubePodzol = null;
    private static IBakedModel modelCubeSnow = null;
    private static boolean modelsLoaded = false;
    private static final String TEXTURE_GRASS_DEFAULT = "block/grass_block_top";
    private static final String TEXTURE_GRASS_SIDE_DEFAULT = "block/grass_block_side";
    private static final String TEXTURE_GRASS_PATH_DEFAULT = "block/grass_path_top";
    private static final String TEXTURE_GRASS_PATH_SIDE_DEFAULT = "block/grass_path_side";
    private static final String TEXTURE_MYCELIUM_DEFAULT = "block/mycelium_top";
    private static final String TEXTURE_PODZOL_DEFAULT = "block/podzol_top";
    private static final String TEXTURE_SNOW_DEFAULT = "block/snow";
    private static final Random RANDOM = new Random(0L);

    public static void updateIcons(AtlasTexture atlasTexture) {
        spritesLoaded = false;
        modelsLoaded = false;
        BetterGrass.loadProperties(atlasTexture);
    }

    public static void update() {
        if (spritesLoaded) {
            modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
            if (grassMultilayer) {
                IBakedModel iBakedModel = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
                modelCubeGrass = BlockModelUtils.joinModelsCube(iBakedModel, modelCubeGrass);
            }
            modelGrassPath = BlockModelUtils.makeModel("grass_path", spriteGrassPathSide, spriteGrassPath);
            modelCubeGrassPath = BlockModelUtils.makeModelCube(spriteGrassPath, -1);
            modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
            modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
            modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
            modelsLoaded = true;
        }
    }

    private static void loadProperties(AtlasTexture atlasTexture) {
        betterGrass = true;
        betterGrassPath = true;
        betterMycelium = true;
        betterPodzol = true;
        betterGrassSnow = true;
        betterMyceliumSnow = true;
        betterPodzolSnow = true;
        spriteGrass = atlasTexture.registerSprite(new ResourceLocation(TEXTURE_GRASS_DEFAULT));
        spriteGrassSide = atlasTexture.registerSprite(new ResourceLocation(TEXTURE_GRASS_SIDE_DEFAULT));
        spriteGrassPath = atlasTexture.registerSprite(new ResourceLocation(TEXTURE_GRASS_PATH_DEFAULT));
        spriteGrassPathSide = atlasTexture.registerSprite(new ResourceLocation(TEXTURE_GRASS_PATH_SIDE_DEFAULT));
        spriteMycelium = atlasTexture.registerSprite(new ResourceLocation(TEXTURE_MYCELIUM_DEFAULT));
        spritePodzol = atlasTexture.registerSprite(new ResourceLocation(TEXTURE_PODZOL_DEFAULT));
        spriteSnow = atlasTexture.registerSprite(new ResourceLocation(TEXTURE_SNOW_DEFAULT));
        spritesLoaded = true;
        String string = "optifine/bettergrass.properties";
        try {
            ResourceLocation resourceLocation = new ResourceLocation(string);
            if (!Config.hasResource(resourceLocation)) {
                return;
            }
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return;
            }
            boolean bl = Config.isFromDefaultResourcePack(resourceLocation);
            if (bl) {
                Config.dbg("BetterGrass: Parsing default configuration " + string);
            } else {
                Config.dbg("BetterGrass: Parsing configuration " + string);
            }
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            betterGrass = BetterGrass.getBoolean(propertiesOrdered, "grass", true);
            betterGrassPath = BetterGrass.getBoolean(propertiesOrdered, "grass_path", true);
            betterMycelium = BetterGrass.getBoolean(propertiesOrdered, "mycelium", true);
            betterPodzol = BetterGrass.getBoolean(propertiesOrdered, "podzol", true);
            betterGrassSnow = BetterGrass.getBoolean(propertiesOrdered, "grass.snow", true);
            betterMyceliumSnow = BetterGrass.getBoolean(propertiesOrdered, "mycelium.snow", true);
            betterPodzolSnow = BetterGrass.getBoolean(propertiesOrdered, "podzol.snow", true);
            grassMultilayer = BetterGrass.getBoolean(propertiesOrdered, "grass.multilayer", false);
            spriteGrass = BetterGrass.registerSprite(propertiesOrdered, "texture.grass", TEXTURE_GRASS_DEFAULT, atlasTexture);
            spriteGrassSide = BetterGrass.registerSprite(propertiesOrdered, "texture.grass_side", TEXTURE_GRASS_SIDE_DEFAULT, atlasTexture);
            spriteGrassPath = BetterGrass.registerSprite(propertiesOrdered, "texture.grass_path", TEXTURE_GRASS_PATH_DEFAULT, atlasTexture);
            spriteGrassPathSide = BetterGrass.registerSprite(propertiesOrdered, "texture.grass_path_side", TEXTURE_GRASS_PATH_SIDE_DEFAULT, atlasTexture);
            spriteMycelium = BetterGrass.registerSprite(propertiesOrdered, "texture.mycelium", TEXTURE_MYCELIUM_DEFAULT, atlasTexture);
            spritePodzol = BetterGrass.registerSprite(propertiesOrdered, "texture.podzol", TEXTURE_PODZOL_DEFAULT, atlasTexture);
            spriteSnow = BetterGrass.registerSprite(propertiesOrdered, "texture.snow", TEXTURE_SNOW_DEFAULT, atlasTexture);
        } catch (IOException iOException) {
            Config.warn("Error reading: " + string + ", " + iOException.getClass().getName() + ": " + iOException.getMessage());
        }
    }

    public static void refreshIcons(AtlasTexture atlasTexture) {
        spriteGrass = BetterGrass.getSprite(atlasTexture, spriteGrass.getName());
        spriteGrassSide = BetterGrass.getSprite(atlasTexture, spriteGrassSide.getName());
        spriteGrassPath = BetterGrass.getSprite(atlasTexture, spriteGrassPath.getName());
        spriteGrassPathSide = BetterGrass.getSprite(atlasTexture, spriteGrassPathSide.getName());
        spriteMycelium = BetterGrass.getSprite(atlasTexture, spriteMycelium.getName());
        spritePodzol = BetterGrass.getSprite(atlasTexture, spritePodzol.getName());
        spriteSnow = BetterGrass.getSprite(atlasTexture, spriteSnow.getName());
    }

    private static TextureAtlasSprite getSprite(AtlasTexture atlasTexture, ResourceLocation resourceLocation) {
        TextureAtlasSprite textureAtlasSprite = atlasTexture.getSprite(resourceLocation);
        if (textureAtlasSprite == null || textureAtlasSprite instanceof MissingTextureSprite) {
            Config.warn("Missing BetterGrass sprite: " + resourceLocation);
        }
        return textureAtlasSprite;
    }

    private static TextureAtlasSprite registerSprite(Properties properties, String string, String string2, AtlasTexture atlasTexture) {
        ResourceLocation resourceLocation;
        String string3 = properties.getProperty(string);
        if (string3 == null) {
            string3 = string2;
        }
        if (!Config.hasResource(resourceLocation = new ResourceLocation("textures/" + string3 + ".png"))) {
            Config.warn("BetterGrass texture not found: " + resourceLocation);
            string3 = string2;
        }
        ResourceLocation resourceLocation2 = new ResourceLocation(string3);
        return atlasTexture.registerSprite(resourceLocation2);
    }

    public static List getFaceQuads(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, Direction direction, List list) {
        if (direction != Direction.UP && direction != Direction.DOWN) {
            if (!modelsLoaded) {
                return list;
            }
            Block block = blockState.getBlock();
            if (block instanceof MyceliumBlock) {
                return BetterGrass.getFaceQuadsMycelium(iBlockReader, blockState, blockPos, direction, list);
            }
            if (block instanceof GrassPathBlock) {
                return BetterGrass.getFaceQuadsGrassPath(iBlockReader, blockState, blockPos, direction, list);
            }
            if (block == Blocks.PODZOL) {
                return BetterGrass.getFaceQuadsPodzol(iBlockReader, blockState, blockPos, direction, list);
            }
            if (block == Blocks.DIRT) {
                return BetterGrass.getFaceQuadsDirt(iBlockReader, blockState, blockPos, direction, list);
            }
            return block instanceof GrassBlock ? BetterGrass.getFaceQuadsGrass(iBlockReader, blockState, blockPos, direction, list) : list;
        }
        return list;
    }

    private static List getFaceQuadsMycelium(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, Direction direction, List list) {
        boolean bl;
        Block block = iBlockReader.getBlockState(blockPos.up()).getBlock();
        boolean bl2 = bl = block == Blocks.SNOW_BLOCK || block == Blocks.SNOW;
        if (Config.isBetterGrassFancy()) {
            if (bl) {
                if (betterMyceliumSnow && BetterGrass.getBlockAt(blockPos, direction, iBlockReader) == Blocks.SNOW) {
                    return modelCubeSnow.getQuads(blockState, direction, RANDOM);
                }
            } else if (betterMycelium && BetterGrass.getBlockAt(blockPos.down(), direction, iBlockReader) == Blocks.MYCELIUM) {
                return modelCubeMycelium.getQuads(blockState, direction, RANDOM);
            }
        } else if (bl) {
            if (betterMyceliumSnow) {
                return modelCubeSnow.getQuads(blockState, direction, RANDOM);
            }
        } else if (betterMycelium) {
            return modelCubeMycelium.getQuads(blockState, direction, RANDOM);
        }
        return list;
    }

    private static List getFaceQuadsGrassPath(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, Direction direction, List list) {
        if (!betterGrassPath) {
            return list;
        }
        if (Config.isBetterGrassFancy()) {
            return BetterGrass.getBlockAt(blockPos.down(), direction, iBlockReader) == Blocks.GRASS_PATH ? modelGrassPath.getQuads(blockState, direction, RANDOM) : list;
        }
        return modelGrassPath.getQuads(blockState, direction, RANDOM);
    }

    private static List getFaceQuadsPodzol(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, Direction direction, List list) {
        boolean bl;
        Block block = BetterGrass.getBlockAt(blockPos, Direction.UP, iBlockReader);
        boolean bl2 = bl = block == Blocks.SNOW_BLOCK || block == Blocks.SNOW;
        if (Config.isBetterGrassFancy()) {
            BlockPos blockPos2;
            BlockState blockState2;
            if (bl) {
                if (betterPodzolSnow && BetterGrass.getBlockAt(blockPos, direction, iBlockReader) == Blocks.SNOW) {
                    return modelCubeSnow.getQuads(blockState, direction, RANDOM);
                }
            } else if (betterPodzol && (blockState2 = iBlockReader.getBlockState(blockPos2 = blockPos.down().offset(direction))).getBlock() == Blocks.PODZOL) {
                return modelCubePodzol.getQuads(blockState, direction, RANDOM);
            }
        } else if (bl) {
            if (betterPodzolSnow) {
                return modelCubeSnow.getQuads(blockState, direction, RANDOM);
            }
        } else if (betterPodzol) {
            return modelCubePodzol.getQuads(blockState, direction, RANDOM);
        }
        return list;
    }

    private static List getFaceQuadsDirt(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, Direction direction, List list) {
        Block block = BetterGrass.getBlockAt(blockPos, Direction.UP, iBlockReader);
        return block == Blocks.GRASS_PATH && betterGrassPath && BetterGrass.getBlockAt(blockPos, direction, iBlockReader) == Blocks.GRASS_PATH ? modelCubeGrassPath.getQuads(blockState, direction, RANDOM) : list;
    }

    private static List getFaceQuadsGrass(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, Direction direction, List list) {
        boolean bl;
        Block block = iBlockReader.getBlockState(blockPos.up()).getBlock();
        boolean bl2 = bl = block == Blocks.SNOW_BLOCK || block == Blocks.SNOW;
        if (Config.isBetterGrassFancy()) {
            if (bl) {
                if (betterGrassSnow && BetterGrass.getBlockAt(blockPos, direction, iBlockReader) == Blocks.SNOW) {
                    return modelCubeSnow.getQuads(blockState, direction, RANDOM);
                }
            } else if (betterGrass && BetterGrass.getBlockAt(blockPos.down(), direction, iBlockReader) == Blocks.GRASS_BLOCK) {
                return modelCubeGrass.getQuads(blockState, direction, RANDOM);
            }
        } else if (bl) {
            if (betterGrassSnow) {
                return modelCubeSnow.getQuads(blockState, direction, RANDOM);
            }
        } else if (betterGrass) {
            return modelCubeGrass.getQuads(blockState, direction, RANDOM);
        }
        return list;
    }

    private static Block getBlockAt(BlockPos blockPos, Direction direction, IBlockReader iBlockReader) {
        BlockPos blockPos2 = blockPos.offset(direction);
        return iBlockReader.getBlockState(blockPos2).getBlock();
    }

    private static boolean getBoolean(Properties properties, String string, boolean bl) {
        String string2 = properties.getProperty(string);
        return string2 == null ? bl : Boolean.parseBoolean(string2);
    }
}

