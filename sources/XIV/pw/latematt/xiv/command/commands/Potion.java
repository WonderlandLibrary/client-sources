package pw.latematt.xiv.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.utils.ChatLogger;

/**
 * @author Rederpz
 */
public class Potion implements CommandHandler {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            if (mc.thePlayer.getHeldItem() == null || mc.thePlayer.getHeldItem().getItem() != Items.potionitem) {
                ChatLogger.print("You must be holding a potion to enhance potions.");
            } else {
                if (!mc.thePlayer.capabilities.isCreativeMode) {
                    ChatLogger.print("You must be in creative mode to enhance potions.");
                } else {
                    try {
                        if (arguments[1].equalsIgnoreCase("clear")) {
                            mc.thePlayer.getHeldItem().setTagCompound(new NBTTagCompound());
                            mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(mc.thePlayer.inventory.currentItem, mc.thePlayer.getHeldItem()));
                        } else {
                            int level = Integer.parseInt(arguments[2]);
                            int duration = Integer.parseInt(arguments[3]);

                            if (mc.thePlayer.getHeldItem().getTagCompound() == null) {
                                mc.thePlayer.getHeldItem().setTagCompound(new NBTTagCompound());
                            }

                            if (!mc.thePlayer.getHeldItem().getTagCompound().hasKey("CustomPotionEffects", 9)) {
                                mc.thePlayer.getHeldItem().getTagCompound().setTag("CustomPotionEffects", new NBTTagList());
                            }

                            if (arguments[1].equalsIgnoreCase("*")) {
                                for (net.minecraft.potion.Potion potion : net.minecraft.potion.Potion.potionTypes) {
                                    if (potion != null) {
                                        NBTTagList list = mc.thePlayer.getHeldItem().getTagCompound().getTagList("CustomPotionEffects", 10);
                                        NBTTagCompound tag = new NBTTagCompound();
                                        tag.setByte("Id", (byte) potion.getId());
                                        tag.setByte("Amplifier", (byte) level);
                                        tag.setInteger("Duration", duration);
                                        list.appendTag(tag);
                                    }
                                }

                                mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(mc.thePlayer.inventory.currentItem, mc.thePlayer.getHeldItem()));
                                ChatLogger.print("Enchanted your current item with every enchantment.");
                            } else {
                                net.minecraft.potion.Potion potion = null;

                                for (net.minecraft.potion.Potion pot : net.minecraft.potion.Potion.potionTypes) {
                                    if (pot != null) {
                                        String name = I18n.format(pot.getName(), new Object[0]).replaceAll(" ", "");

                                        if (name.equalsIgnoreCase(arguments[1])) {
                                            potion = pot;
                                        }
                                    }
                                }

                                if (potion != null) {
                                    NBTTagList list = mc.thePlayer.getHeldItem().getTagCompound().getTagList("CustomPotionEffects", 10);
                                    NBTTagCompound tag = new NBTTagCompound();
                                    tag.setByte("Id", (byte) potion.getId());
                                    tag.setByte("Amplifier", (byte) level);
                                    tag.setInteger("Duration", duration);
                                    list.appendTag(tag);

                                    mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(mc.thePlayer.inventory.currentItem, mc.thePlayer.getHeldItem()));
                                    ChatLogger.print(String.format("Enhanced your current potion with %s.", I18n.format(potion.getName())));
                                }
                            }
                        }
                    } catch (Exception e) {
                        ChatLogger.print("Invalid arguments, valid: potion <effect> <level> <duration>");
                    }
                }
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: potion <effect> <level> <duration>");
        }
    }
}
