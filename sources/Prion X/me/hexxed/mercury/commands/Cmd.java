package me.hexxed.mercury.commands;

import me.hexxed.mercury.commandbase.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class Cmd extends Command
{
  public Cmd()
  {
    super("opblock", "opblock");
  }
  
  public void execute(String[] args)
  {
    if (args.length != 0) {
      ItemStack payload = new ItemStack(net.minecraft.init.Blocks.command_block);
      stackTagCompound = new NBTTagCompound();
      NBTTagCompound comp = new NBTTagCompound();
      StringBuilder sb = new StringBuilder();
      for (String s : args) {
        sb.append(s + " ");
      }
      sb.trimToSize();
      comp.setTag("Command", new NBTTagString(me.hexxed.mercury.util.ChatColor.translateAlternateColorCodes('&', sb.toString())));
      stackTagCompound.setTag("BlockEntityTag", comp);
      getMinecraftthePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, payload));
      return;
    }
    ItemStack payload = new ItemStack(net.minecraft.init.Blocks.command_block);
    stackTagCompound = new NBTTagCompound();
    NBTTagCompound comp = new NBTTagCompound();
    comp.setTag("Command", new NBTTagString("execute @a ~ ~ ~ op " + Minecraft.getMinecraft().getSession().getUsername()));
    stackTagCompound.setTag("BlockEntityTag", comp);
    getMinecraftthePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, payload));
  }
}
