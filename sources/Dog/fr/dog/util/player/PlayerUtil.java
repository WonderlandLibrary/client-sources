package fr.dog.util.player;

import com.google.common.base.Predicates;
import fr.dog.util.InstanceAccess;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import java.awt.*;
import java.util.List;


@UtilityClass
public class PlayerUtil implements InstanceAccess {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isPlayerInsideBlock() {
        Block daBlock = mc.theWorld.getBlockState(mc.thePlayer.getPosition()).getBlock();
        return daBlock instanceof BlockAir;
    }

    public static int getTool(Block block) {
        float n = 1.0f;
        int n2 = -1;
        for (int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
            final ItemStack getStackInSlot = mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null) {
                final float a = getEfficiency(getStackInSlot, block);
                if (a > n) {
                    n = a;
                    n2 = i;
                }
            }
        }
        return n2;
    }

    public static float getEfficiency(final ItemStack itemStack, final Block block) {
        float getStrVsBlock = itemStack.getStrVsBlock(block);
        if (getStrVsBlock > 1.0f) {
            final int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            if (getEnchantmentLevel > 0) {
                getStrVsBlock += getEnchantmentLevel * getEnchantmentLevel + 1;
            }
        }
        return getStrVsBlock;
    }

    public boolean isEntityTeamSameAsPlayer(EntityLivingBase target) {
        if (target.getTeam() != null && Minecraft.getMinecraft().thePlayer.getTeam() != null) {
            boolean ret0 = target.getDisplayName().getFormattedText().charAt(1) == Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText().charAt(1);
            boolean ret1 = target.getTeam() == Minecraft.getMinecraft().thePlayer.getTeam();

            return ret0 || ret1;

        }
        return false;
    }

    public Vec3 getClosestPointToEntity(Entity e) {
        double closestX = MathHelper.clamp_double(mc.thePlayer.posX, e.getEntityBoundingBox().minX, e.getEntityBoundingBox().maxX);
        double closestY = MathHelper.clamp_double(mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), e.getEntityBoundingBox().minY, e.getEntityBoundingBox().maxY);
        double closestZ = MathHelper.clamp_double(mc.thePlayer.posZ, e.getEntityBoundingBox().minZ, e.getEntityBoundingBox().maxZ);

        return new Vec3(closestX, closestY, closestZ);
    }

    public float getBiblicallyAccurateDistanceToEntity(Entity e) {
        return (float) getClosestPointToEntity(e).distanceTo(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ));
    }

    public Entity getMouseEntity(float partialTicks, float distance) {
        return getMouseEntity(partialTicks, distance, mc.thePlayer.getLook(partialTicks));
    }

    public Entity getMouseEntity(float partialTicks, float distance, float yaw, float pitch) {
        return getMouseEntity(partialTicks, distance, mc.thePlayer.getVectorForRotation(pitch, yaw));
    }

    public Entity getMouseEntity(float partialTicks, float distance, Vec3 vec31) {

        Entity pointedEntity = null;
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        if (entity != null && mc.theWorld != null) {
            Vec3 vec3 = entity.getPositionEyes(partialTicks);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * (double) distance, vec31.yCoord * (double) distance, vec31.zCoord * (double) distance);
            float f = 1.0F;
            List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * (double) distance, vec31.yCoord * (double) distance, vec31.zCoord * (double) distance).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = distance;
            for (Entity entity1 : list) {
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 >= 0.0D) {
                        pointedEntity = entity1;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0D) {
                        if (entity1 == entity.ridingEntity) {
                            if (d2 == 0.0D) {
                                pointedEntity = entity1;
                            }
                        } else {
                            pointedEntity = entity1;
                            d2 = d3;
                        }
                    }
                    if (d3 > distance) {
                        pointedEntity = null;
                    }
                }
            }
        }
        return pointedEntity;
    }
    public static Color getTeamColor(EntityLivingBase target) {
        char targetted = (target.getDisplayName().getFormattedText().charAt(1));
        for (EnumChatFormatting enumChatFormatting : EnumChatFormatting.values()) {
            if (String.valueOf(enumChatFormatting.formattingCode).equals(String.valueOf(targetted))) {
                return enumChatFormatting.color;
            }
        }
        return Color.WHITE;
    }



}
