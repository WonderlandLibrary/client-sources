package dev.excellent.impl.util.player;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.client.component.impl.LastConnectionComponent;
import dev.excellent.impl.util.math.Mathf;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ClientBossInfo;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.UUID;

@UtilityClass
public class PlayerUtil implements IAccess {

    public int findItemSlot(Item item) {
        return findItemSlot(item, true);
    }

    public int findItemSlot(Item item, boolean armor) {
        if (armor) {
            for (ItemStack stack : mc.player.getArmorInventoryList()) {
                if (stack.getItem() == item) {
                    return -2;
                }
            }
        }
        int slot = -1;
        for (int i = 0; i < 36; i++) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == item) {
                slot = i;
                break;
            }
        }
        if (slot < 9 && slot != -1) {
            slot = slot + 36;
        }
        return slot;
    }

    public float[] getHealthFromScoreboard(LivingEntity target) {
        var ref = new Object() {
            float hp = target.getHealth();
            float maxHp = target.getMaxHealth();
        };
        if (isFuntime() && mc.world.getScoreboard().getObjectiveInDisplaySlot(2) != null) {
            mc.world.getScoreboard().getObjectivesForEntity(target.getScoreboardName()).entrySet().stream().filter(x -> x.getValue().getObjective().getDisplayName().getString().contains("Здоровья")).findAny().ifPresent(x -> {
                ref.hp = x.getValue().getScorePoints();
                ref.maxHp = 20;
            });
        }
        return new float[]{ref.hp, ref.maxHp};
    }

    public boolean isPvp() {
        boolean isPvp = false;
        for (Map.Entry<UUID, ClientBossInfo> bossInfo : mc.ingameGUI.getBossOverlay().getMapBossInfos().entrySet()) {
            if (bossInfo.getValue().getName().getString().toLowerCase().contains("pvp") || bossInfo.getValue().getName().getString().toLowerCase().contains("пвп")) {
                isPvp = true;
            }
        }
        return isPvp;
    }

    public void moveItem(int from, int to, boolean air) {
        moveItem(0, from, to, air);
    }

    public void moveItem(int windowId, int from, int to, boolean air) {
        if (from == to) return;
        pickupItem(windowId, from, 0);
        pickupItem(windowId, to, 0);
        if (air)
            pickupItem(windowId, from, 0);
    }

    public void pickupItem(int slot, int button) {
        pickupItem(0, slot, button);
    }

    public void pickupItem(int windowId, int slot, int button) {
        mc.playerController.windowClick(windowId, slot, button, ClickType.PICKUP, mc.player);
    }

    public void dropItem(int slot) {
        mc.playerController.windowClick(0, slot, 0, ClickType.THROW, mc.player);
    }

    public int getFireworkSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof FireworkRocketItem) {
                return i;
            }
        }
        return -1;
    }

    public double getBps(Entity entity, int decimal) {
        double x = entity.getPosX() - entity.prevPosX;
        double z = entity.getPosZ() - entity.prevPosZ;
        double speed = Math.sqrt(x * x + z * z) * 20.0D;
        return Mathf.round(speed, decimal);
    }

    public boolean isBlockUnder(final double height) {
        for (int offset = 0; offset < height; offset++) {
            if (blockRelativeToPlayer(0, -offset, 0).getDefaultState().isCollisionShapeLargerThanFullBlock()) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasBlocks(double y) {
        return !mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().expand(0, y, 0)).toList().isEmpty();
    }

    public static int getAxe(boolean hotBar) {
        int startSlot = hotBar ? 0 : 9;
        int endSlot = hotBar ? 9 : 36;

        for (int i = startSlot; i < endSlot; i++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof AxeItem) {
                return i;
            }
        }

        return -1;
    }

    public Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.world.getBlockState(new BlockPos(mc.player.getPositionVec()).add(offsetX, offsetY, offsetZ)).getBlock();
    }

    public Block blockAheadOfPlayer(final double offsetXZ, final double offsetY) {
        return blockRelativeToPlayer(-Math.sin(MoveUtil.direction()) * offsetXZ, offsetY, Math.cos(MoveUtil.direction()) * offsetXZ);
    }

    public double getEntityArmor(PlayerEntity target) {
        double totalArmor = 0.0;

        for (ItemStack armorStack : target.inventory.armorInventory) {
            if (armorStack != null && armorStack.getItem() instanceof ArmorItem) {
                totalArmor += getProtectionLvl(armorStack);
            }
        }
        return totalArmor;
    }

    public double getEntityHealth(Entity ent) {
        if (ent instanceof PlayerEntity player) {
            double armorValue = getEntityArmor(player) / 20.0;
            return (player.getHealth() + player.getAbsorptionAmount()) * armorValue;
        } else if (ent instanceof LivingEntity livingEntity) {
            return livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
        }
        return 0.0;
    }


    public double getProtectionLvl(ItemStack stack) {
        ArmorItem armor = (ArmorItem) stack.getItem();
        double damageReduce = armor.getDamageReduceAmount();
        if (stack.isEnchanted()) {
            damageReduce += (double) EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack) * 0.25;
        }
        return damageReduce;
    }

    public int getPearls() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof EnderPearlItem) {
                return i;
            }
        }
        return -1;
    }

    public boolean isFuntime() {
        return isConnectedToServer("funtime");
    }

    public boolean isConnectedToServer(String ip) {
        return LastConnectionComponent.ip != null && LastConnectionComponent.ip.toLowerCase().contains(ip);
    }


    public int getAnarchy() {
        int serverAnarchy = -1;
        if (mc.world.getScoreboard().getObjective("TAB-Scoreboard") != null) {
            try {
                serverAnarchy = Integer.parseInt(mc.world.getScoreboard().getObjective("TAB-Scoreboard").getDisplayName().getString().split("⚡ Анархия-")[1]);
            } catch (Exception ignored) {
            }
        }
        return serverAnarchy;
    }

    public int getPing() {
        return mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getUniqueID()) == null ? -1 : mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
    }

    public float squaredDistance2d(double x, double z) {
        if (mc.player == null)
            return 0.0f;
        double d = mc.player.getPosX() - x;
        double f = mc.player.getPosZ() - z;
        return (float) (d * d + f * f);
    }

    public boolean chestContainerOpened() {
        return mc.currentScreen instanceof ChestScreen;
    }

    public long delay() {
        return (long) (Math.random() * (20));
    }

    public void rsleep() throws InterruptedException {
        Thread.sleep(delay());
    }
}
