package im.expensive.command.impl.feature;

import im.expensive.command.*;
import im.expensive.command.impl.CommandException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VClipCommand implements Command, CommandWithAdvice {
    final Prefix prefix;
    final Logger logger;
    final Minecraft mc;

    @Override
    public void execute(Parameters parameters) {
        final BlockPos playerPos = mc.player.getPosition();
        float yOffset;

        String direction = parameters.asString(0)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Необходимо указать направление или расстояние."));
        switch (direction) {
            case "up" -> yOffset = findOffset(playerPos, true);
            case "down" -> yOffset = findOffset(playerPos, false);
            default -> yOffset = parseOffset(direction);
        }
        if (yOffset != 0) {
            int elytraSlot = getElytraSlot();
            boolean hasElytra = elytraSlot != -1 && elytraSlot != -2;

            if (hasElytra) {
                switchElytra(elytraSlot, true);
            }

            teleport(yOffset, hasElytra);

            if (hasElytra) {
                switchElytra(elytraSlot, false);
            }
        } else {
            logger.log(TextFormatting.RED + "Не удалось выполнить телепортацию.");
        }
    }

    @Override
    public String name() {
        return "vclip";
    }

    @Override
    public String description() {
        return "Телепортирует вверх/вниз по вертикали";
    }


    @Override
    public List<String> adviceMessage() {
        String commandPrefix = prefix.get();
        return List.of(
                commandPrefix + "vclip up - Телепортация вверх до ближайшего свободного пространства",
                commandPrefix + "vclip down - Телепортация вниз до ближайшего свободного пространства",
                commandPrefix + "vclip <distance> - Телепортация на указанное расстояние",
                "Пример: " + TextFormatting.RED + commandPrefix + "vclip 10"
        );
    }

    private float findOffset(BlockPos playerPos, boolean toUp) {
        int startY = toUp ? 3 : -1;
        int endY = toUp ? 255 : -255;
        int step = toUp ? 1 : -1;

        for (int i = startY; i != endY; i += step) {
            BlockPos targetPos = playerPos.add(0, i, 0);
            if (mc.world.getBlockState(targetPos) == Blocks.AIR.getDefaultState()) {
                return i + (toUp ? 1 : -1);
            }
            if (mc.world.getBlockState(targetPos) == Blocks.BEDROCK.getDefaultState() && !toUp) {
                logger.log(TextFormatting.RED + "Тут нельзя телепортироваться под землю.");
                return 0;
            }
        }
        return 0;
    }

    private void teleport(float yOffset, boolean elytra) {
        if (elytra) {
            for (int i = 0; i < 2; i++) {
                mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(),
                        mc.player.getPosY(), mc.player.getPosZ(), false));
            }
            mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(),
                    mc.player.getPosY() + yOffset, mc.player.getPosZ(), false));
            mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
            mc.player.setPosition(mc.player.getPosX(), mc.player.getPosY() + yOffset, mc.player.getPosZ());
            String blockUnit = (yOffset > 1) ? "блока" : "блок";
            logger.log(TextFormatting.GRAY + "Попытка телепортироваться с элитрой...");
            logger.log(String.format("Вы были успешно телепортированы на %.1f %s по вертикали", yOffset, blockUnit));
            return;
        }
        int packetsCount = calculatePacketsCount(yOffset);
        for (int i = 0; i < packetsCount; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket(mc.player.isOnGround()));
        }
        mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(),
                mc.player.getPosY() + yOffset, mc.player.getPosZ(), false));
        mc.player.setPosition(mc.player.getPosX(), mc.player.getPosY() + yOffset, mc.player.getPosZ());
        String blockUnit = (yOffset > 1) ? "блока" : "блок";
        logger.log(String.format("Вы были успешно телепортированы на %.1f %s по вертикали", yOffset, blockUnit));
    }

    private float parseOffset(String distance) {
        if (NumberUtils.isNumber(distance)) {
            return Float.parseFloat(distance);
        }
        logger.log(TextFormatting.RED + distance + TextFormatting.GRAY + " не является числом!");
        return 0;
    }

    private int calculatePacketsCount(float yOffset) {
        return Math.max((int) (yOffset / 1000), 3);
    }

    private void switchElytra(int elytraSlot, boolean equip) {
        final int chestplateSlot = 6;
        if (equip) {
            mc.playerController.windowClick(0, elytraSlot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, chestplateSlot, 0, ClickType.PICKUP, mc.player);
        } else {
            mc.playerController.windowClick(0, chestplateSlot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, elytraSlot, 0, ClickType.PICKUP, mc.player);
        }
    }

    private int getElytraSlot() {
        for (ItemStack stack : mc.player.getArmorInventoryList()) {
            if (stack.getItem() == Items.ELYTRA) {
                return -2;
            }
        }
        int slot = -1;
        for (int i = 0; i < 36; i++) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == Items.ELYTRA) {
                slot = i;
                break;
            }
        }
        if (slot < 9 && slot != -1) {
            slot = slot + 36;
        }
        return slot;
    }
}
