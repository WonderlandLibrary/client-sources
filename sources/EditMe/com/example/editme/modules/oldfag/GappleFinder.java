package com.example.editme.modules.oldfag;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

@Module.Info(
   name = "GappleFinder [BETA]",
   category = Module.Category.OLDFAG
)
public class GappleFinder extends Module {
   public Setting dungeonChance = this.register(SettingsManager.integerBuilder("Dungeon Chance").withValue((int)8).withVisibility(GappleFinder::lambda$new$0).build());
   private long seed;
   private Setting seedSetting = this.register(SettingsManager.stringBuilder("Seed").withValue("-1985258164").build());
   private List usedChunks;
   private Setting searchDirection;
   private Random rand;
   private boolean searching;
   private BlockPos chestPos;
   private int status;
   private List usedChests;

   private BlockPos getGappleChestPos() {
      if (!mc.field_71441_e.field_73010_i.isEmpty()) {
         Iterator var1 = mc.field_71441_e.field_147482_g.iterator();

         while(true) {
            TileEntityLockableLoot var3;
            do {
               TileEntity var2;
               do {
                  if (!var1.hasNext()) {
                     return null;
                  }

                  var2 = (TileEntity)var1.next();
               } while(!(var2 instanceof TileEntityLockableLoot));

               var3 = (TileEntityLockableLoot)var2;
            } while(var3.func_184276_b() == null);

            var3.func_184281_d(mc.field_71439_g);

            for(int var4 = 0; var4 < var3.func_70302_i_(); ++var4) {
               ItemStack var5 = var3.func_70301_a(var4);
               if (var5.func_77973_b() == Items.field_151153_ao && var5.func_77952_i() == 1) {
                  int var6 = var3.func_174877_v().func_177958_n() << 4;
                  int var7 = var3.func_174877_v().func_177952_p() << 4;
                  if (!this.usedChests.contains(new ChunkPos(var6, var7))) {
                     this.usedChests.add(new ChunkPos(var6, var7));
                     return var3.func_174877_v();
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   public GappleFinder() {
      this.searchDirection = this.register(SettingsManager.e("Search Direction", GappleFinder.Direction.POSX));
      this.seed = -1985258164L;
      this.status = 0;
      this.searching = false;
      this.usedChunks = new ArrayList();
      this.usedChests = new ArrayList();
   }

   private void lambda$onUpdate$1() {
      this.searching = true;
      this.sendNotification("Looking for dungeon");
      this.searching = false;
      this.status = 1;
   }

   public void onEnable() {
      this.rand = new Random(this.seed);
   }

   private static boolean lambda$new$0(Integer var0) {
      return false;
   }

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         this.seed = Long.parseLong((String)this.seedSetting.getValue());
         if (this.status == 0) {
            if (this.searching) {
               return;
            }

            this.sendNotification("Starting searcher thread");
            (new Thread(this::lambda$onUpdate$1)).start();
         } else if (this.status == 1) {
            this.sendNotification("Going to dungeon");
            this.status = 2;
         } else if (this.status == 2) {
            BlockPos var1 = this.getGappleChestPos();
            if (var1 != null) {
               this.sendNotification("Going to mine chest");
               this.chestPos = var1;
               this.status = 3;
            }
         } else if (this.status == 3 && mc.field_71441_e.func_175623_d(this.chestPos)) {
            this.sendNotification("Mined chest, restarting");
            this.status = 0;
         }

      }
   }

   private static enum Direction {
      NEGX,
      NEGZ,
      POSZ;

      private static final GappleFinder.Direction[] $VALUES = new GappleFinder.Direction[]{POSX, NEGX, POSZ, NEGZ};
      POSX;
   }
}
