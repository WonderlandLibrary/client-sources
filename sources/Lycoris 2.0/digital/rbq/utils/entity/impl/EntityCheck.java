/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.entity.impl;

import java.util.function.Supplier;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import digital.rbq.friend.FriendManager;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.utils.entity.ICheck;

public final class EntityCheck
implements ICheck {
    private final BoolOption players;
    private final BoolOption animals;
    private final BoolOption monsters;
    private final BoolOption invisibles;
    private final Supplier<Boolean> friend;

    public EntityCheck(BoolOption players, BoolOption animals, BoolOption monsters, BoolOption invisibles, Supplier<Boolean> friend) {
        this.players = players;
        this.animals = animals;
        this.monsters = monsters;
        this.invisibles = invisibles;
        this.friend = friend;
    }

    @Override
    public boolean validate(Entity entity) {
        if (entity instanceof EntityPlayerSP) {
            return false;
        }
        if (!this.invisibles.getValue().booleanValue() && entity.isInvisible()) {
            return false;
        }
        if (this.animals.getValue().booleanValue() && entity instanceof EntityAnimal) {
            return true;
        }
        if (this.players.getValue().booleanValue() && entity instanceof EntityPlayer) {
            return !FriendManager.isFriend(entity.getName()) || this.friend.get() == false;
        }
        return this.monsters.getValue() != false && (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityDragon || entity instanceof EntityGolem);
    }
}

