package dev.excellent.client.command.impl;

import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.player.PlayerUtil;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.text.TextFormatting;

public final class ClipCommand extends Command {

    public ClipCommand() {
        super("", "clip", "vclip", "hclip");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length <= 1 || args[1].isEmpty()) {
            error();
            return;
        }

        switch (args[0].toLowerCase()) {
            case "vclip" -> {
                final double amount = Double.parseDouble(args[1]);

                mc.player.setPosition(mc.player.getPositionVec().x, mc.player.getPositionVec().y + amount, mc.player.getPositionVec().z);
                ChatUtil.addText("Clipped you " + (amount > 0 ? "up" : "down") + " " + Math.abs(amount) + " blocks.");
            }
            case "hclip" -> {
                final double amount = Double.parseDouble(args[1]);

                final double yaw = Math.toRadians(mc.player.rotationYaw);
                final double x = Math.sin(yaw) * amount;
                final double z = Math.cos(yaw) * amount;

                mc.player.setPosition(mc.player.getPositionVec().x - x, mc.player.getPositionVec().y, mc.player.getPositionVec().z + z);
                ChatUtil.addText("Clipped you " + (amount > 0 ? "forward" : "back") + " " + Math.abs(amount) + " blocks.");
            }
            case "clip" -> {
                if (args.length == 2 || args[2].isEmpty()) {
                    error();
                    return;
                }

                switch (args[1]) {
                    case "upward", "upwards", "up" -> {
                        final double amount = Double.parseDouble(args[2]);

                        mc.player.setPosition(mc.player.getPositionVec().x, mc.player.getPositionVec().y + amount, mc.player.getPositionVec().z);
                        ChatUtil.addText("Clipped you up " + amount + " blocks.");
                    }
                    case "downward", "downwards", "down" -> {
                        final double amount = Double.parseDouble(args[2]);

                        mc.player.setPosition(mc.player.getPositionVec().x, mc.player.getPositionVec().y - amount, mc.player.getPositionVec().z);
                        ChatUtil.addText("Clipped you down " + amount + " blocks.");
                    }
                    case "forwards", "forward" -> {
                        final double amount = Double.parseDouble(args[2]);

                        final double yaw = Math.toRadians(mc.player.rotationYaw);
                        final double x = Math.sin(yaw) * amount;
                        final double z = Math.cos(yaw) * amount;

                        mc.player.setPosition(mc.player.getPositionVec().x - x, mc.player.getPositionVec().y, mc.player.getPositionVec().z + z);
                        ChatUtil.addText("Clipped you forward " + amount + " blocks.");
                    }
                    case "backwards", "backward", "back" -> {
                        final double amount = Double.parseDouble(args[2]);

                        final double yaw = Math.toRadians(mc.player.rotationYaw);
                        final double x = Math.sin(yaw) * amount;
                        final double z = Math.cos(yaw) * amount;

                        mc.player.setPosition(mc.player.getPositionVec().x + x, mc.player.getPositionVec().y, mc.player.getPositionVec().z - z);
                        ChatUtil.addText("Clipped you back " + amount + " blocks.");
                    }
                    case "left" -> {
                        final double amount = Double.parseDouble(args[2]);

                        final double yaw = Math.toRadians(mc.player.rotationYaw - 90);
                        final double x = Math.sin(yaw) * amount;
                        final double z = Math.cos(yaw) * amount;

                        mc.player.setPosition(mc.player.getPositionVec().x - x, mc.player.getPositionVec().y, mc.player.getPositionVec().z + z);
                        ChatUtil.addText("Clipped you left " + amount + " blocks.");
                    }
                    case "right" -> {
                        final double amount = Double.parseDouble(args[2]);

                        final double yaw = Math.toRadians(mc.player.rotationYaw + 90);
                        final double x = Math.sin(yaw) * amount;
                        final double z = Math.cos(yaw) * amount;

                        mc.player.setPosition(mc.player.getPositionVec().x - x, mc.player.getPositionVec().y, mc.player.getPositionVec().z + z);
                        ChatUtil.addText("Clipped you right " + amount + " blocks.");
                    }
                    case "elytra" -> {
                        final double amount = Double.parseDouble(args[2]);

                        int elytra = PlayerUtil.findItemSlot(Items.ELYTRA);
                        if (elytra == -1) {
                            ChatUtil.addText(TextFormatting.RED + "Вам нужны элитры в инвентаре");

                        }
                        if (elytra != -2) {
                            if (mc.playerController != null) {
                                mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
                                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            }
                        }
                        mc.player.connection
                                .sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), false));
                        mc.player.connection
                                .sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), false));
                        mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                        mc.player.connection
                                .sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() + amount, mc.player.getPosZ(), false));
                        mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                        if (elytra != -2) {
                            if (mc.playerController != null) {
                                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                                mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
                            }
                        }
                        mc.player.setPosition(mc.player.getPosX(), mc.player.getPosY() + amount, mc.player.getPosZ());
                        if (mc.player.inventory.armorInventory.get(2).getItem() == Items.ELYTRA) {
                            int i = 0;
                            for (ItemStack item : mc.player.inventory.mainInventory) {
                                if (item.getItem() instanceof AirItem) {
                                    if (mc.playerController != null) {
                                        mc.playerController.windowClick(0, 6, 1, ClickType.QUICK_MOVE, mc.player);
                                        mc.playerController.windowClick(0, i, 1, ClickType.QUICK_MOVE, mc.player);
                                    }
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                    default -> error();
                }
            }
            default -> error();
        }
    }
}
