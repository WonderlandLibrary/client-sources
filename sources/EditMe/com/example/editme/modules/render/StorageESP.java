package com.example.editme.modules.render;

import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.color.ColourUtils;
import com.example.editme.util.render.EditmeTessellator;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "StorageESP",
   category = Module.Category.RENDER
)
public class StorageESP extends Module {
   private int getEntityColor(Entity var1) {
      if (var1 instanceof EntityMinecartChest) {
         return ColourUtils.Colors.YELLOW;
      } else {
         return var1 instanceof EntityItemFrame ? ColourUtils.Colors.GRAY : -1;
      }
   }

   private int getTileEntityColor(TileEntity var1) {
      if (!(var1 instanceof TileEntityChest) && !(var1 instanceof TileEntityDispenser) && !(var1 instanceof TileEntityShulkerBox)) {
         if (var1 instanceof TileEntityEnderChest) {
            return ColourUtils.Colors.PURPLE;
         } else {
            return !(var1 instanceof TileEntityFurnace) && !(var1 instanceof TileEntityHopper) ? -1 : ColourUtils.Colors.GRAY;
         }
      } else {
         return ColourUtils.Colors.YELLOW;
      }
   }

   private int changeAlpha(int var1, int var2) {
      var1 &= 16777215;
      return var2 << 24 | var1;
   }

   public void onWorldRender(RenderEvent var1) {
      ArrayList var2 = new ArrayList();
      GlStateManager.func_179094_E();
      Iterator var3 = mc.field_71441_e.field_147482_g.iterator();

      BlockPos var5;
      int var6;
      while(var3.hasNext()) {
         TileEntity var4 = (TileEntity)var3.next();
         var5 = var4.func_174877_v();
         var6 = this.getTileEntityColor(var4);
         int var7 = 63;
         if (var4 instanceof TileEntityChest) {
            TileEntityChest var8 = (TileEntityChest)var4;
            if (var8.field_145992_i != null) {
               var7 = ~(var7 & 4);
            }

            if (var8.field_145990_j != null) {
               var7 = ~(var7 & 32);
            }

            if (var8.field_145988_l != null) {
               var7 = ~(var7 & 8);
            }

            if (var8.field_145991_k != null) {
               var7 = ~(var7 & 16);
            }
         }

         if (var6 != -1) {
            var2.add(new StorageESP.Fst(this, var5, var6, var7));
         }
      }

      var3 = mc.field_71441_e.field_72996_f.iterator();

      while(var3.hasNext()) {
         Entity var9 = (Entity)var3.next();
         var5 = var9.func_180425_c();
         var6 = this.getEntityColor(var9);
         if (var6 != -1) {
            var2.add(new StorageESP.Fst(this, var9 instanceof EntityItemFrame ? var5.func_177982_a(0, -1, 0) : var5, var6, 63));
         }
      }

      EditmeTessellator.prepare(7);
      var3 = var2.iterator();

      while(var3.hasNext()) {
         StorageESP.Fst var10 = (StorageESP.Fst)var3.next();
         EditmeTessellator.drawBox((BlockPos)var10.getFirst(), this.changeAlpha((Integer)var10.getSecond(), 100), (Integer)var10.getThird());
      }

      EditmeTessellator.release();
      GlStateManager.func_179121_F();
      GlStateManager.func_179098_w();
   }

   private class Fst {
      final StorageESP this$0;
      private final Object second;
      private final Object third;
      private final Object first;

      Fst(StorageESP var1, Object var2, Object var3, Object var4) {
         this.this$0 = var1;
         this.first = var2;
         this.second = var3;
         this.third = var4;
      }

      Object getFirst() {
         return this.first;
      }

      Object getThird() {
         return this.third;
      }

      Object getSecond() {
         return this.second;
      }
   }
}
