package me.hexxed.mercury.commands;

import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class Enchant extends me.hexxed.mercury.commandbase.Command
{
  public Enchant()
  {
    super("enchant", "enchant");
  }
  
  public void execute(String[] args)
  {
    if (getMinecraftplayerController.isInCreativeMode())
    {
      ItemStack item = getMinecraftthePlayer.getCurrentEquippedItem();
      if (item != null)
      {
        for (Enchantment e : Enchantment.enchantmentsList) {
          if (e != null) {
            item.addEnchantment(e, 127);
          }
        }
        Util.sendInfo("Enchanted your " + item.getDisplayName());
        return;
      }
      Util.sendInfo("No item in hand found");
      return;
    }
    Util.sendInfo("Player must be in creative mode");
  }
}
