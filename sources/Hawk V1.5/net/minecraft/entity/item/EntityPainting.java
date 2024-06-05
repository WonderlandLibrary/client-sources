package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityPainting extends EntityHanging {
   private static final String __OBFID = "CL_00001556";
   public EntityPainting.EnumArt art;

   public EntityPainting(World var1) {
      super(var1);
   }

   public void setLocationAndAngles(double var1, double var3, double var5, float var7, float var8) {
      BlockPos var9 = new BlockPos(var1 - this.posX, var3 - this.posY, var5 - this.posZ);
      BlockPos var10 = this.field_174861_a.add(var9);
      this.setPosition((double)var10.getX(), (double)var10.getY(), (double)var10.getZ());
   }

   public EntityPainting(World var1, BlockPos var2, EnumFacing var3, String var4) {
      this(var1, var2, var3);
      EntityPainting.EnumArt[] var5 = EntityPainting.EnumArt.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         EntityPainting.EnumArt var8 = var5[var7];
         if (var8.title.equals(var4)) {
            this.art = var8;
            break;
         }
      }

      this.func_174859_a(var3);
   }

   public int getHeightPixels() {
      return this.art.sizeY;
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      String var2 = var1.getString("Motive");
      EntityPainting.EnumArt[] var3 = EntityPainting.EnumArt.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EntityPainting.EnumArt var6 = var3[var5];
         if (var6.title.equals(var2)) {
            this.art = var6;
         }
      }

      if (this.art == null) {
         this.art = EntityPainting.EnumArt.KEBAB;
      }

      super.readEntityFromNBT(var1);
   }

   public void func_180426_a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      BlockPos var11 = new BlockPos(var1 - this.posX, var3 - this.posY, var5 - this.posZ);
      BlockPos var12 = this.field_174861_a.add(var11);
      this.setPosition((double)var12.getX(), (double)var12.getY(), (double)var12.getZ());
   }

   public int getWidthPixels() {
      return this.art.sizeX;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      var1.setString("Motive", this.art.title);
      super.writeEntityToNBT(var1);
   }

   public EntityPainting(World var1, BlockPos var2, EnumFacing var3) {
      super(var1, var2);
      ArrayList var4 = Lists.newArrayList();
      EntityPainting.EnumArt[] var5 = EntityPainting.EnumArt.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         EntityPainting.EnumArt var8 = var5[var7];
         this.art = var8;
         this.func_174859_a(var3);
         if (this.onValidSurface()) {
            var4.add(var8);
         }
      }

      if (!var4.isEmpty()) {
         this.art = (EntityPainting.EnumArt)var4.get(this.rand.nextInt(var4.size()));
      }

      this.func_174859_a(var3);
   }

   public void onBroken(Entity var1) {
      if (this.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
         if (var1 instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)var1;
            if (var2.capabilities.isCreativeMode) {
               return;
            }
         }

         this.entityDropItem(new ItemStack(Items.painting), 0.0F);
      }

   }

   public static enum EnumArt {
      SEA("SEA", 9, "Sea", 32, 16, 64, 32),
      BOMB("BOMB", 4, "Bomb", 16, 16, 64, 0),
      PLANT("PLANT", 5, "Plant", 16, 16, 80, 0);

      private static final String __OBFID = "CL_00001557";
      AZTEC_2("AZTEC_2", 3, "Aztec2", 16, 16, 48, 0);

      public final int offsetY;
      ALBAN("ALBAN", 2, "Alban", 16, 16, 32, 0);

      public final int offsetX;
      BURNING_SKULL("BURNING_SKULL", 23, "BurningSkull", 64, 64, 128, 192),
      MATCH("MATCH", 14, "Match", 32, 32, 0, 128),
      WASTELAND("WASTELAND", 6, "Wasteland", 16, 16, 96, 0);

      private static final EntityPainting.EnumArt[] $VALUES = new EntityPainting.EnumArt[]{KEBAB, AZTEC, ALBAN, AZTEC_2, BOMB, PLANT, WASTELAND, POOL, COURBET, SEA, SUNSET, CREEBET, WANDERER, GRAHAM, MATCH, BUST, STAGE, VOID, SKULL_AND_ROSES, WITHER, FIGHTERS, POINTER, PIGSCENE, BURNING_SKULL, SKELETON, DONKEY_KONG};
      DONKEY_KONG("DONKEY_KONG", 25, "DonkeyKong", 64, 48, 192, 112),
      WANDERER("WANDERER", 12, "Wanderer", 16, 32, 0, 64),
      BUST("BUST", 15, "Bust", 32, 32, 32, 128);

      public final int sizeY;
      POOL("POOL", 7, "Pool", 32, 16, 0, 32);

      public final int sizeX;
      POINTER("POINTER", 21, "Pointer", 64, 64, 0, 192),
      WITHER("WITHER", 19, "Wither", 32, 32, 160, 128),
      VOID("VOID", 17, "Void", 32, 32, 96, 128),
      SUNSET("SUNSET", 10, "Sunset", 32, 16, 96, 32);

      public final String title;
      GRAHAM("GRAHAM", 13, "Graham", 16, 32, 16, 64),
      FIGHTERS("FIGHTERS", 20, "Fighters", 64, 32, 0, 96),
      COURBET("COURBET", 8, "Courbet", 32, 16, 32, 32),
      AZTEC("AZTEC", 1, "Aztec", 16, 16, 16, 0),
      STAGE("STAGE", 16, "Stage", 32, 32, 64, 128),
      KEBAB("KEBAB", 0, "Kebab", 16, 16, 0, 0),
      SKELETON("SKELETON", 24, "Skeleton", 64, 48, 192, 64),
      PIGSCENE("PIGSCENE", 22, "Pigscene", 64, 64, 64, 192),
      CREEBET("CREEBET", 11, "Creebet", 32, 16, 128, 32);

      private static final EntityPainting.EnumArt[] ENUM$VALUES = new EntityPainting.EnumArt[]{KEBAB, AZTEC, ALBAN, AZTEC_2, BOMB, PLANT, WASTELAND, POOL, COURBET, SEA, SUNSET, CREEBET, WANDERER, GRAHAM, MATCH, BUST, STAGE, VOID, SKULL_AND_ROSES, WITHER, FIGHTERS, POINTER, PIGSCENE, BURNING_SKULL, SKELETON, DONKEY_KONG};
      SKULL_AND_ROSES("SKULL_AND_ROSES", 18, "SkullAndRoses", 32, 32, 128, 128);

      public static final int field_180001_A = "SkullAndRoses".length();

      private EnumArt(String var3, int var4, String var5, int var6, int var7, int var8, int var9) {
         this.title = var5;
         this.sizeX = var6;
         this.sizeY = var7;
         this.offsetX = var8;
         this.offsetY = var9;
      }
   }
}
