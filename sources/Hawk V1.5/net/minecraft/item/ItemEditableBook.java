package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class ItemEditableBook extends Item {
   private static final String __OBFID = "CL_00000077";

   public static int func_179230_h(ItemStack var0) {
      return var0.getTagCompound().getInteger("generation");
   }

   public static boolean validBookTagContents(NBTTagCompound var0) {
      if (!ItemWritableBook.validBookPageTagContents(var0)) {
         return false;
      } else if (!var0.hasKey("title", 8)) {
         return false;
      } else {
         String var1 = var0.getString("title");
         return var1 != null && var1.length() <= 32 ? var0.hasKey("author", 8) : false;
      }
   }

   private void func_179229_a(ItemStack var1, EntityPlayer var2) {
      if (var1 != null && var1.getTagCompound() != null) {
         NBTTagCompound var3 = var1.getTagCompound();
         if (!var3.getBoolean("resolved")) {
            var3.setBoolean("resolved", true);
            if (validBookTagContents(var3)) {
               NBTTagList var4 = var3.getTagList("pages", 8);

               for(int var5 = 0; var5 < var4.tagCount(); ++var5) {
                  String var6 = var4.getStringTagAt(var5);

                  Object var7;
                  try {
                     IChatComponent var8 = IChatComponent.Serializer.jsonToComponent(var6);
                     var7 = ChatComponentProcessor.func_179985_a(var2, var8, var2);
                  } catch (Exception var9) {
                     var7 = new ChatComponentText(var6);
                  }

                  var4.set(var5, new NBTTagString(IChatComponent.Serializer.componentToJson((IChatComponent)var7)));
               }

               var3.setTag("pages", var4);
               if (var2 instanceof EntityPlayerMP && var2.getCurrentEquippedItem() == var1) {
                  Slot var10 = var2.openContainer.getSlotFromInventory(var2.inventory, var2.inventory.currentItem);
                  ((EntityPlayerMP)var2).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(0, var10.slotNumber, var1));
               }
            }
         }
      }

   }

   public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4) {
      if (var1.hasTagCompound()) {
         NBTTagCompound var5 = var1.getTagCompound();
         String var6 = var5.getString("author");
         if (!StringUtils.isNullOrEmpty(var6)) {
            var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append(StatCollector.translateToLocalFormatted("book.byAuthor", var6))));
         }

         var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append(StatCollector.translateToLocal(String.valueOf((new StringBuilder("book.generation.")).append(var5.getInteger("generation")))))));
      }

   }

   public String getItemStackDisplayName(ItemStack var1) {
      if (var1.hasTagCompound()) {
         NBTTagCompound var2 = var1.getTagCompound();
         String var3 = var2.getString("title");
         if (!StringUtils.isNullOrEmpty(var3)) {
            return var3;
         }
      }

      return super.getItemStackDisplayName(var1);
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      if (!var2.isRemote) {
         this.func_179229_a(var1, var3);
      }

      var3.displayGUIBook(var1);
      var3.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return var1;
   }

   public ItemEditableBook() {
      this.setMaxStackSize(1);
   }

   public boolean hasEffect(ItemStack var1) {
      return true;
   }
}
