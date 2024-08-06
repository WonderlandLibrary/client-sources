package club.strifeclient.util.world;

import club.strifeclient.Client;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.MultiSelectSetting;
import club.strifeclient.util.misc.MinecraftUtil;
import club.strifeclient.util.player.PlayerUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class WorldUtil extends MinecraftUtil {

    private static final Predicate<Entity> entityLivingBaseValidator = e -> e instanceof EntityLivingBase;

    public enum Target implements SerializableEnum {
        PLAYERS("Players"), MOBS("Mobs"), ANIMALS("Animals"), INVISIBLES("Invisibles"),
        DEAD("Dead"), TEAMS("Teams"), FRIENDS("Friends");

        final String name;

        Target(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static <T extends MultiSelectSetting<Target>> boolean isValid(EntityLivingBase entityLivingBase, T targetsProperty) {
        if (entityLivingBase == null) return false;
        if (entityLivingBase.isInvisible()) return targetsProperty.isSelected(Target.INVISIBLES);
        if (!entityLivingBase.isEntityAlive()) return targetsProperty.isSelected(Target.DEAD);
        if (entityLivingBase instanceof EntityOtherPlayerMP) {
            final EntityPlayer player = (EntityPlayer) entityLivingBase;
            if (Client.INSTANCE.getTargetManager().isFriend(player.getName()))
                return targetsProperty.isSelected(Target.FRIENDS);
            if (PlayerUtil.isTeammate(player)) return targetsProperty.isSelected(Target.TEAMS);
//            return WorldUtil.checkPing(player);
            return targetsProperty.isSelected(Target.PLAYERS);
        }
        if (entityLivingBase instanceof EntityMob)
            return targetsProperty.isSelected(Target.MOBS);
        if (entityLivingBase instanceof EntityAnimal)
            return targetsProperty.isSelected(Target.ANIMALS);
        return false;
    }

    public static List<EntityLivingBase> getLivingEntities(Predicate<Entity> predicate) {
        List<EntityLivingBase> entities = new ArrayList<>();
        for(Entity entity : mc.theWorld.getLoadedEntityList()) {
            if(entityLivingBaseValidator.and(predicate).test(entity))
                entities.add((EntityLivingBase)entity);
        }
        return entities;
    }

    public static EnumFacing getInvertedFace(EnumFacing face) {
        switch (face) {
            case EAST: return EnumFacing.WEST;
            case WEST: return EnumFacing.EAST;
            case NORTH: return EnumFacing.SOUTH;
            case SOUTH: return EnumFacing.NORTH;
            case UP: return EnumFacing.DOWN;
            case DOWN: return EnumFacing.UP;
            default: return face;
        }
    }

}
