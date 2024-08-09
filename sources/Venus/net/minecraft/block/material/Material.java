/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.material;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public final class Material {
    public static final Material AIR = new Builder(MaterialColor.AIR).doesNotBlockMovement().notOpaque().notSolid().replaceable().build();
    public static final Material STRUCTURE_VOID = new Builder(MaterialColor.AIR).doesNotBlockMovement().notOpaque().notSolid().replaceable().build();
    public static final Material PORTAL = new Builder(MaterialColor.AIR).doesNotBlockMovement().notOpaque().notSolid().pushBlocks().build();
    public static final Material CARPET = new Builder(MaterialColor.WOOL).doesNotBlockMovement().notOpaque().notSolid().flammable().build();
    public static final Material PLANTS = new Builder(MaterialColor.FOLIAGE).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().build();
    public static final Material OCEAN_PLANT = new Builder(MaterialColor.WATER).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().build();
    public static final Material TALL_PLANTS = new Builder(MaterialColor.FOLIAGE).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().flammable().build();
    public static final Material NETHER_PLANTS = new Builder(MaterialColor.FOLIAGE).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().build();
    public static final Material SEA_GRASS = new Builder(MaterialColor.WATER).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().build();
    public static final Material WATER = new Builder(MaterialColor.WATER).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().liquid().build();
    public static final Material BUBBLE_COLUMN = new Builder(MaterialColor.WATER).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().liquid().build();
    public static final Material LAVA = new Builder(MaterialColor.TNT).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().liquid().build();
    public static final Material SNOW = new Builder(MaterialColor.SNOW).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().build();
    public static final Material FIRE = new Builder(MaterialColor.AIR).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().build();
    public static final Material MISCELLANEOUS = new Builder(MaterialColor.AIR).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().build();
    public static final Material WEB = new Builder(MaterialColor.WOOL).doesNotBlockMovement().notOpaque().pushDestroys().build();
    public static final Material REDSTONE_LIGHT = new Builder(MaterialColor.AIR).build();
    public static final Material CLAY = new Builder(MaterialColor.CLAY).build();
    public static final Material EARTH = new Builder(MaterialColor.DIRT).build();
    public static final Material ORGANIC = new Builder(MaterialColor.GRASS).build();
    public static final Material PACKED_ICE = new Builder(MaterialColor.ICE).build();
    public static final Material SAND = new Builder(MaterialColor.SAND).build();
    public static final Material SPONGE = new Builder(MaterialColor.YELLOW).build();
    public static final Material SHULKER = new Builder(MaterialColor.PURPLE).build();
    public static final Material WOOD = new Builder(MaterialColor.WOOD).flammable().build();
    public static final Material NETHER_WOOD = new Builder(MaterialColor.WOOD).build();
    public static final Material BAMBOO_SAPLING = new Builder(MaterialColor.WOOD).flammable().pushDestroys().doesNotBlockMovement().build();
    public static final Material BAMBOO = new Builder(MaterialColor.WOOD).flammable().pushDestroys().build();
    public static final Material WOOL = new Builder(MaterialColor.WOOL).flammable().build();
    public static final Material TNT = new Builder(MaterialColor.TNT).flammable().notOpaque().build();
    public static final Material LEAVES = new Builder(MaterialColor.FOLIAGE).flammable().notOpaque().pushDestroys().build();
    public static final Material GLASS = new Builder(MaterialColor.AIR).notOpaque().build();
    public static final Material ICE = new Builder(MaterialColor.ICE).notOpaque().build();
    public static final Material CACTUS = new Builder(MaterialColor.FOLIAGE).notOpaque().pushDestroys().build();
    public static final Material ROCK = new Builder(MaterialColor.STONE).build();
    public static final Material IRON = new Builder(MaterialColor.IRON).build();
    public static final Material SNOW_BLOCK = new Builder(MaterialColor.SNOW).build();
    public static final Material ANVIL = new Builder(MaterialColor.IRON).pushBlocks().build();
    public static final Material BARRIER = new Builder(MaterialColor.AIR).pushBlocks().build();
    public static final Material PISTON = new Builder(MaterialColor.STONE).pushBlocks().build();
    public static final Material CORAL = new Builder(MaterialColor.FOLIAGE).pushDestroys().build();
    public static final Material GOURD = new Builder(MaterialColor.FOLIAGE).pushDestroys().build();
    public static final Material DRAGON_EGG = new Builder(MaterialColor.FOLIAGE).pushDestroys().build();
    public static final Material CAKE = new Builder(MaterialColor.AIR).pushDestroys().build();
    private final MaterialColor color;
    private final PushReaction pushReaction;
    private final boolean blocksMovement;
    private final boolean flammable;
    private final boolean isLiquid;
    private final boolean isOpaque;
    private final boolean replaceable;
    private final boolean isSolid;

    public Material(MaterialColor materialColor, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, PushReaction pushReaction) {
        this.color = materialColor;
        this.isLiquid = bl;
        this.isSolid = bl2;
        this.blocksMovement = bl3;
        this.isOpaque = bl4;
        this.flammable = bl5;
        this.replaceable = bl6;
        this.pushReaction = pushReaction;
    }

    public boolean isLiquid() {
        return this.isLiquid;
    }

    public boolean isSolid() {
        return this.isSolid;
    }

    public boolean blocksMovement() {
        return this.blocksMovement;
    }

    public boolean isFlammable() {
        return this.flammable;
    }

    public boolean isReplaceable() {
        return this.replaceable;
    }

    public boolean isOpaque() {
        return this.isOpaque;
    }

    public PushReaction getPushReaction() {
        return this.pushReaction;
    }

    public MaterialColor getColor() {
        return this.color;
    }

    public static class Builder {
        private PushReaction pushReaction = PushReaction.NORMAL;
        private boolean blocksMovement = true;
        private boolean canBurn;
        private boolean isLiquid;
        private boolean isReplaceable;
        private boolean isSolid = true;
        private final MaterialColor color;
        private boolean isOpaque = true;

        public Builder(MaterialColor materialColor) {
            this.color = materialColor;
        }

        public Builder liquid() {
            this.isLiquid = true;
            return this;
        }

        public Builder notSolid() {
            this.isSolid = false;
            return this;
        }

        public Builder doesNotBlockMovement() {
            this.blocksMovement = false;
            return this;
        }

        private Builder notOpaque() {
            this.isOpaque = false;
            return this;
        }

        protected Builder flammable() {
            this.canBurn = true;
            return this;
        }

        public Builder replaceable() {
            this.isReplaceable = true;
            return this;
        }

        protected Builder pushDestroys() {
            this.pushReaction = PushReaction.DESTROY;
            return this;
        }

        protected Builder pushBlocks() {
            this.pushReaction = PushReaction.BLOCK;
            return this;
        }

        public Material build() {
            return new Material(this.color, this.isLiquid, this.isSolid, this.blocksMovement, this.isOpaque, this.canBurn, this.isReplaceable, this.pushReaction);
        }
    }
}

