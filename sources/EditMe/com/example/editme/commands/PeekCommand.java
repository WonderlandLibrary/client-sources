package com.example.editme.commands;

import com.example.editme.util.client.Wrapper;
import com.example.editme.util.command.syntax.SyntaxChunk;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityShulkerBox;

public class PeekCommand extends Command {
   public static TileEntityShulkerBox sb;

   public void call(String[] var1) {
      ItemStack var2 = Wrapper.getPlayer().field_71071_by.func_70448_g();
      if (var2.func_77973_b() instanceof ItemShulkerBox) {
         TileEntityShulkerBox var3 = new TileEntityShulkerBox();
         var3.field_145854_h = ((ItemShulkerBox)var2.func_77973_b()).func_179223_d();
         var3.func_145834_a(Wrapper.getWorld());
         var3.func_145839_a(var2.func_77978_p().func_74775_l("BlockEntityTag"));
         sb = var3;
      } else {
         Command.sendChatMessage("You aren't carrying a shulker box.");
      }

   }

   public PeekCommand() {
      super("peek", SyntaxChunk.EMPTY);
   }
}
