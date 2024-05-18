package pw.latematt.xiv.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.StatCollector;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.utils.ChatLogger;

/**
 * @author Matthew
 */
public class Enchant implements CommandHandler {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            if (mc.thePlayer.getHeldItem() == null) {
                ChatLogger.print("You must be holding an item to enchant.");
            } else {
                if (!mc.thePlayer.capabilities.isCreativeMode) {
                    ChatLogger.print("You must be in creative mode to enchant.");
                } else {
                    try {
                        if (arguments[1].equalsIgnoreCase("clear")) {
                            mc.thePlayer.getHeldItem().setTagCompound(new NBTTagCompound());
                            ChatLogger.print("Cleared your current item's enchants.");
                        } else {
                            try {
                                int level = arguments[2].equalsIgnoreCase("*") ? 127 : Integer.parseInt(arguments[2]);

                                if (level > 127)
                                    level = 127;
                                else if (level < -127)
                                    level = -127;

                                if (arguments[1].equalsIgnoreCase("*")) {
                                    for (Enchantment enchant : Enchantment.enchantmentsList)
                                        mc.thePlayer.getHeldItem().addEnchantment(enchant, level);
                                    mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(mc.thePlayer.inventory.currentItem, mc.thePlayer.getHeldItem()));
                                    ChatLogger.print("Enchanted your current item with every enchantment.");
                                } else {
                                    Enchantment enchant = null;

                                    for (Enchantment enc : Enchantment.enchantmentsList) {
                                        String name = StatCollector.translateToLocal(enc.getName()).replaceAll(" ", "");

                                        if (name.equalsIgnoreCase(arguments[1])) {
                                            enchant = enc;
                                        }
                                    }

                                    if (enchant != null) {
                                        mc.thePlayer.getHeldItem().addEnchantment(enchant, level);
                                        mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(mc.thePlayer.inventory.currentItem, mc.thePlayer.getHeldItem()));
                                        ChatLogger.print(String.format("Enchanted your current item with %s.", StatCollector.translateToLocal(enchant.getName())));
                                    }
                                }
                            } catch (NumberFormatException e) {
                                ChatLogger.print(String.format("\"%s\" is not a number.", arguments[2]));
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        ChatLogger.print("Invalid arguments, valid: enchant <enchantment> <level>");
                    }
                }
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: enchant <enchantment> <level>");
        }
    }
}
