package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class Enchant extends net.SliceClient.commandbase.Command
{
  public Enchant()
  {
    super("enchant", "enchant");
  }
  
  public void execute(String[] args)
  {
    net.SliceClient.Utils.Wrapper.getMinecraft(); if (Minecraft.playerController.isInCreativeMode())
    {
      net.SliceClient.Utils.Wrapper.getMinecraft();ItemStack item = Minecraft.thePlayer.getCurrentEquippedItem();
      if (item != null)
      {
        for (Enchantment e : Enchantment.enchantmentsList) {
          if (e != null) {
            item.addEnchantment(e, 127);
          }
        }
        Util.addChatMessage(Slice.prefix + "Enchanted your §4" + item.getDisplayName());
        return;
      }
      Util.addChatMessage(Slice.prefix + "No item in hand found");
      return;
    }
    Util.addChatMessage(Slice.prefix + "Player must be in creative mode");
  }
}
