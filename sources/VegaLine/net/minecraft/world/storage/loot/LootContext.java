/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage.loot;

import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;

public class LootContext {
    private final float luck;
    private final WorldServer worldObj;
    private final LootTableManager lootTableManager;
    @Nullable
    private final Entity lootedEntity;
    @Nullable
    private final EntityPlayer player;
    @Nullable
    private final DamageSource damageSource;
    private final Set<LootTable> lootTables = Sets.newLinkedHashSet();

    public LootContext(float luckIn, WorldServer worldIn, LootTableManager lootTableManagerIn, @Nullable Entity lootedEntityIn, @Nullable EntityPlayer playerIn, @Nullable DamageSource damageSourceIn) {
        this.luck = luckIn;
        this.worldObj = worldIn;
        this.lootTableManager = lootTableManagerIn;
        this.lootedEntity = lootedEntityIn;
        this.player = playerIn;
        this.damageSource = damageSourceIn;
    }

    @Nullable
    public Entity getLootedEntity() {
        return this.lootedEntity;
    }

    @Nullable
    public Entity getKillerPlayer() {
        return this.player;
    }

    @Nullable
    public Entity getKiller() {
        return this.damageSource == null ? null : this.damageSource.getEntity();
    }

    public boolean addLootTable(LootTable lootTableIn) {
        return this.lootTables.add(lootTableIn);
    }

    public void removeLootTable(LootTable lootTableIn) {
        this.lootTables.remove(lootTableIn);
    }

    public LootTableManager getLootTableManager() {
        return this.lootTableManager;
    }

    public float getLuck() {
        return this.luck;
    }

    @Nullable
    public Entity getEntity(EntityTarget target) {
        switch (target) {
            case THIS: {
                return this.getLootedEntity();
            }
            case KILLER: {
                return this.getKiller();
            }
            case KILLER_PLAYER: {
                return this.getKillerPlayer();
            }
        }
        return null;
    }

    public static enum EntityTarget {
        THIS("this"),
        KILLER("killer"),
        KILLER_PLAYER("killer_player");

        private final String targetType;

        private EntityTarget(String type2) {
            this.targetType = type2;
        }

        public static EntityTarget fromString(String type2) {
            for (EntityTarget lootcontext$entitytarget : EntityTarget.values()) {
                if (!lootcontext$entitytarget.targetType.equals(type2)) continue;
                return lootcontext$entitytarget;
            }
            throw new IllegalArgumentException("Invalid entity target " + type2);
        }

        public static class Serializer
        extends TypeAdapter<EntityTarget> {
            @Override
            public void write(JsonWriter p_write_1_, EntityTarget p_write_2_) throws IOException {
                p_write_1_.value(p_write_2_.targetType);
            }

            @Override
            public EntityTarget read(JsonReader p_read_1_) throws IOException {
                return EntityTarget.fromString(p_read_1_.nextString());
            }
        }
    }

    public static class Builder {
        private final WorldServer worldObj;
        private float luck;
        private Entity lootedEntity;
        private EntityPlayer player;
        private DamageSource damageSource;

        public Builder(WorldServer worldIn) {
            this.worldObj = worldIn;
        }

        public Builder withLuck(float luckIn) {
            this.luck = luckIn;
            return this;
        }

        public Builder withLootedEntity(Entity entityIn) {
            this.lootedEntity = entityIn;
            return this;
        }

        public Builder withPlayer(EntityPlayer playerIn) {
            this.player = playerIn;
            return this;
        }

        public Builder withDamageSource(DamageSource dmgSource) {
            this.damageSource = dmgSource;
            return this;
        }

        public LootContext build() {
            return new LootContext(this.luck, this.worldObj, this.worldObj.getLootTableManager(), this.lootedEntity, this.player, this.damageSource);
        }
    }
}

