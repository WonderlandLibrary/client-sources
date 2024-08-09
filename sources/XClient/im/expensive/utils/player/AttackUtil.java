package im.expensive.utils.player;

import im.expensive.Expensive;
import im.expensive.command.friends.FriendStorage;
import im.expensive.utils.client.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttackUtil implements IMinecraft {
    public static boolean isPlayerFalling(boolean onlyCrit, boolean onlySpace, boolean sync) {

        boolean cancelReason = mc.player.isInWater() && mc.player.areEyesInFluid(FluidTags.WATER)
                || mc.player.isInLava()
                || mc.player.isOnLadder() || mc.player.isPassenger()
                || mc.player.abilities.isFlying;
        boolean onSpace = !mc.gameSettings.keyBindJump.isKeyDown() && mc.player.isOnGround() && onlySpace;

        float attackStrength = mc.player.getCooledAttackStrength(sync ? Expensive.getInstance().getTpsCalc().getAdjustTicks() : 1.0f);

        if (attackStrength < 0.92) return false;

        if (!cancelReason && onlyCrit) {
            return onSpace || !mc.player.isOnGround() && mc.player.fallDistance > 0;
        }

        return true;
    }

    public final List<EntityType> entityTypes = new ArrayList<>();

    // ����� ��� ����������� ���� ��������
    public EntityType ofType(Entity entity, EntityType... types) {
        List<EntityType> typeList = Arrays.asList(types);

        if (entity instanceof PlayerEntity) {
            if (entityIsMe(entity, typeList)) {
                return EntityType.SELF;
            } else if (entity != mc.player) {
                if (entityIsPlayer(entity, typeList)) {
                    return EntityType.PLAYERS;
                } else if (entityIsFriend(entity, typeList)) {
                    return EntityType.FRIENDS;
                }
                if (entityIsNakedPlayer(entity, typeList)) {
                    return EntityType.NAKED;
                }
            }
        } else if (entityIsMob(entity, typeList)) {
            return EntityType.MOBS;
        } else if (entityIsAnimal(entity, typeList)) {
            return EntityType.ANIMALS;
        }
        return null;
    }

    private static boolean entityIsMe(Entity entity, List<EntityType> typeList) {
        return entity == mc.player && typeList.contains(EntityType.SELF);
    }

    private static boolean entityIsPlayer(Entity entity, List<EntityType> typeList) {
        return typeList.contains(EntityType.PLAYERS) && !FriendStorage.isFriend(entity.getName().getString());
    }

    private static boolean entityIsFriend(Entity entity, List<EntityType> typeList) {
        return typeList.contains(EntityType.FRIENDS) && FriendStorage.isFriend(entity.getName().getString());
    }

    private static boolean entityIsMob(Entity entity, List<EntityType> typeList) {
        return entity instanceof MonsterEntity && typeList.contains(EntityType.MOBS);
    }

    private static boolean entityIsNakedPlayer(Entity entity, List<EntityType> typeList) {
        return entity instanceof PlayerEntity && ((LivingEntity) entity).getTotalArmorValue() == 0;
    }

    private boolean entityIsAnimal(Entity entity, List<EntityType> typeList) {
        return (entity instanceof AnimalEntity || entity instanceof GolemEntity || entity instanceof VillagerEntity)
                && typeList.contains(EntityType.ANIMALS);
    }

    public AttackUtil apply(EntityType type) {
        this.entityTypes.add(type);
        return this;
    }

    public EntityType[] build() {
        return this.entityTypes.toArray(new EntityType[0]);
    }

    public enum EntityType {
        PLAYERS, MOBS, ANIMALS, NAKED, FRIENDS, NPC, SELF
    }
}
