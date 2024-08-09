package mathax.client.systems.enemies;

import mathax.client.utils.misc.ISerializable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.Objects;

public class Enemy implements ISerializable<Enemy> {
    public String name;


    public Enemy(String name) {
        this.name = name;
    }

    public Enemy(PlayerEntity player) {
        this(player.getEntityName());
    }

    public Enemy(NbtCompound tag) {
        fromTag(tag);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("name", name);
        return tag;
    }

    @Override
    public Enemy fromTag(NbtCompound tag) {
        name = tag.getString("name");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy Enemy = (Enemy) o;
        return Objects.equals(name, Enemy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
