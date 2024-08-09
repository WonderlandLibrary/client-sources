/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootParameter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class LootParameters {
    public static final LootParameter<Entity> THIS_ENTITY = LootParameters.register("this_entity");
    public static final LootParameter<PlayerEntity> LAST_DAMAGE_PLAYER = LootParameters.register("last_damage_player");
    public static final LootParameter<DamageSource> DAMAGE_SOURCE = LootParameters.register("damage_source");
    public static final LootParameter<Entity> KILLER_ENTITY = LootParameters.register("killer_entity");
    public static final LootParameter<Entity> DIRECT_KILLER_ENTITY = LootParameters.register("direct_killer_entity");
    public static final LootParameter<Vector3d> field_237457_g_ = LootParameters.register("origin");
    public static final LootParameter<BlockState> BLOCK_STATE = LootParameters.register("block_state");
    public static final LootParameter<TileEntity> BLOCK_ENTITY = LootParameters.register("block_entity");
    public static final LootParameter<ItemStack> TOOL = LootParameters.register("tool");
    public static final LootParameter<Float> EXPLOSION_RADIUS = LootParameters.register("explosion_radius");

    private static <T> LootParameter<T> register(String string) {
        return new LootParameter(new ResourceLocation(string));
    }
}

