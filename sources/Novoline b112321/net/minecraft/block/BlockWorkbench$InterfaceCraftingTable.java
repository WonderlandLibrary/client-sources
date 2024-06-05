package net.minecraft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockWorkbench$InterfaceCraftingTable implements IInteractionObject {
   private final World world;
   private final BlockPos position;

   public BlockWorkbench$InterfaceCraftingTable(World var1, BlockPos var2) {
      this.world = var1;
      this.position = var2;
   }

   public String getName() {
      return null;
   }

   public boolean hasCustomName() {
      return false;
   }

   public IChatComponent getDisplayName() {
      return new ChatComponentTranslation(Blocks.crafting_table.getUnlocalizedName() + ".name", new Object[0]);
   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerWorkbench(var1, this.world, this.position);
   }

   public String getGuiID() {
      return "minecraft:crafting_table";
   }
}
