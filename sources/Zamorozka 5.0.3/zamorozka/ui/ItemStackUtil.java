package zamorozka.ui;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.List;




public class ItemStackUtil
{
  public static final ItemStack empty = new ItemStack(Blocks.AIR);
  
  public static void addEmpty(List<ItemStack> stacks, int num) {
    for (int i = 0; i < num; i++) {
      stacks.add(empty);
    }
  }
  
  public static void fillEmpty(List<ItemStack> stacks) { addEmpty(stacks, 9 - stacks.size() % 9); }


  
  public static void addEmpty(List<ItemStack> stacks) { stacks.add(empty); }


  
  public static ItemStack stringtostack(String Sargs) {
    try {
      Item item = new Item();
      Sargs = Sargs.replace('&', '');
      
      String[] args = null;
      int i = 1;
      int j = 0;
      args = Sargs.split(" ");
      
      ResourceLocation resourcelocation = new ResourceLocation(args[0]);
      item = (Item)Item.REGISTRY.getObject(resourcelocation);
      
      if (args.length >= 2 && args[1].matches("\\d+")) {
        i = Integer.parseInt(args[1]);
      }
      if (args.length >= 3 && args[2].matches("\\d+")) {
        j = Integer.parseInt(args[2]);
      }

      
      ItemStack itemstack = new ItemStack(item, i, j);
      
      if (args.length >= 4) {
        
        String NBT = "";
        for (int nbtcount = 3; nbtcount < args.length; nbtcount++) {
          NBT = String.valueOf(String.valueOf(NBT)) + " " + args[nbtcount];
        }

        
        itemstack.setTagCompound(JsonToNBT.getTagFromJson(NBT));
      } 
      return itemstack;
    } catch (Exception ex) {
      ex.printStackTrace();
      
      return new ItemStack(Blocks.BARRIER);
    } 
  }
  public static void removeSuspiciousTags(ItemStack item, boolean force, boolean display, boolean hideFlags) {
    NBTTagCompound tag = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound();
    if (force || !tag.hasKey("Exploit"))
      tag.setByte("Exploit", (byte)((display ? 1 : 0) + (hideFlags ? 2 : 0))); 
    item.setTagCompound(tag);
  }
  
  public static void removeSuspiciousTags(List<ItemStack> itemList, boolean display, boolean hideFlags) {
    for (ItemStack item : itemList) {
      removeSuspiciousTags(item, false, display, hideFlags);
    }
  }
  
  public static void removeSuspiciousTags(List<ItemStack> itemList) { removeSuspiciousTags(itemList, true, true); }

  
  public static void modify(ItemStack stack) {
    if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("Exploit")) {
      byte state = stack.getTagCompound().getByte("Exploit");
      stack.getTagCompound().removeTag("Exploit");
      
      if (state % 2 == 1 && stack.getTagCompound().hasKey("display", 10))
        stack.getTagCompound().removeTag("display"); 
      if (state % 4 == 1)
        stack.getTagCompound().setByte("HideFlags", (byte)63); 
    } 
  }
}
