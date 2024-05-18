package net.SliceClient.commands;

import net.SliceClient.Slice;
import net.SliceClient.Utils.Util;
import net.SliceClient.commandbase.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class OneHitSword extends Command
{
  public OneHitSword()
  {
    super("1hitsword", ".1hitsword");
  }
  

  public void execute(String[] args)
  {
    Minecraft.getMinecraft(); if (thePlayerinventory.getStackInSlot(36) != null)
    {
      Util.addChatMessage(Slice.prefix + "§4[Error] Please take off your shoes.");
      return; }
    Minecraft.getMinecraft(); if (!thePlayercapabilities.isCreativeMode)
    {
      Util.addChatMessage(Slice.prefix + "§4[Error] Creative mode only.");
      return;
    }
    
    ItemStack stack = new ItemStack(net.minecraft.init.Items.diamond_sword);
    NBTTagCompound compound = stack.getTagCompound();
    if (compound == null) {
      compound = new NBTTagCompound();
      stack.setTagCompound(compound);
      compound = stack.getTagCompound();
    }
    
    stack.setStackDisplayName("§6§lSuper §9 - §4§l1§fHit§4§lSword");
    NBTTagList modifiers = new NBTTagList();
    NBTTagCompound speedy = new NBTTagCompound();
    speedy.setTag("AttributeName", new NBTTagString("generic.attackDamage"));
    speedy.setTag("Name", new NBTTagString("generic.attackDamage"));
    speedy.setTag("Amount", new NBTTagInt(Integer.MAX_VALUE));
    speedy.setTag("Operation", new NBTTagInt(0));
    speedy.setTag("UUIDLeast", new NBTTagInt(894654));
    speedy.setTag("UUIDMost", new NBTTagInt(2872));
    modifiers.appendTag(speedy);
    compound.setTag("AttributeModifiers", modifiers);
    stack.setTagCompound(compound);
    Minecraft.getMinecraft();Minecraft.thePlayer.getInventory()[0] = stack;
    Util.addChatMessage(Slice.prefix + "The CrashSword is now in your shoes!");
  }
}
