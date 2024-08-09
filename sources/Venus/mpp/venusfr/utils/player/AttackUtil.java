/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.venusfr;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;

public class AttackUtil
implements IMinecraft {
    public final List<EntityType> entityTypes = new ArrayList<EntityType>();

    public static boolean isPlayerFalling(boolean bl, boolean bl2, boolean bl3) {
        boolean bl4 = AttackUtil.mc.player.isInWater() && AttackUtil.mc.player.areEyesInFluid(FluidTags.WATER) || AttackUtil.mc.player.isInLava() || AttackUtil.mc.player.isOnLadder() || AttackUtil.mc.player.isPassenger() || AttackUtil.mc.player.abilities.isFlying;
        boolean bl5 = !AttackUtil.mc.gameSettings.keyBindJump.isKeyDown() && AttackUtil.mc.player.isOnGround() && bl2;
        float f = AttackUtil.mc.player.getCooledAttackStrength(bl3 ? venusfr.getInstance().getTpsCalc().getAdjustTicks() : 1.0f);
        if ((double)f < 0.92) {
            return true;
        }
        if (!bl4 && bl) {
            return bl5 || !AttackUtil.mc.player.isOnGround() && AttackUtil.mc.player.fallDistance > 0.0f;
        }
        return false;
    }

    public EntityType ofType(Entity entity2, EntityType ... entityTypeArray) {
        List<EntityType> list = Arrays.asList(entityTypeArray);
        if (entity2 instanceof PlayerEntity) {
            if (AttackUtil.entityIsMe(entity2, list)) {
                return EntityType.SELF;
            }
            if (entity2 != AttackUtil.mc.player) {
                if (AttackUtil.entityIsPlayer(entity2, list)) {
                    return EntityType.PLAYERS;
                }
                if (AttackUtil.entityIsFriend(entity2, list)) {
                    return EntityType.FRIENDS;
                }
                if (AttackUtil.entityIsNakedPlayer(entity2, list)) {
                    return EntityType.NAKED;
                }
            }
        } else {
            if (AttackUtil.entityIsMob(entity2, list)) {
                return EntityType.MOBS;
            }
            if (this.entityIsAnimal(entity2, list)) {
                return EntityType.ANIMALS;
            }
        }
        return null;
    }

    private static boolean entityIsMe(Entity entity2, List<EntityType> list) {
        return entity2 == AttackUtil.mc.player && list.contains((Object)EntityType.SELF);
    }

    private static boolean entityIsPlayer(Entity entity2, List<EntityType> list) {
        return list.contains((Object)EntityType.PLAYERS) && !FriendStorage.isFriend(entity2.getName().getString());
    }

    private static boolean entityIsFriend(Entity entity2, List<EntityType> list) {
        return list.contains((Object)EntityType.FRIENDS) && FriendStorage.isFriend(entity2.getName().getString());
    }

    private static boolean entityIsMob(Entity entity2, List<EntityType> list) {
        return entity2 instanceof MonsterEntity && list.contains((Object)EntityType.MOBS);
    }

    private static boolean entityIsNakedPlayer(Entity entity2, List<EntityType> list) {
        return entity2 instanceof PlayerEntity && ((LivingEntity)entity2).getTotalArmorValue() == 0;
    }

    private boolean entityIsAnimal(Entity entity2, List<EntityType> list) {
        return (entity2 instanceof AnimalEntity || entity2 instanceof GolemEntity || entity2 instanceof VillagerEntity) && list.contains((Object)EntityType.ANIMALS);
    }

    public AttackUtil apply(EntityType entityType) {
        this.entityTypes.add(entityType);
        return this;
    }

    public EntityType[] build() {
        return this.entityTypes.toArray(new EntityType[0]);
    }

    public static enum EntityType {
        PLAYERS,
        MOBS,
        ANIMALS,
        NAKED,
        FRIENDS,
        NPC,
        SELF;

    }
}

