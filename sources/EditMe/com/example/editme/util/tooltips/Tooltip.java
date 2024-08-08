package com.example.editme.util.tooltips;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;

public class Tooltip implements Comparable {
   public double distanceToPlayer;
   private boolean countDown;
   private int height;
   private List text;
   private TextFormatting textFormatting;
   private int tickCount;
   private int width;
   private boolean forceFade;
   private static final Minecraft mc = Minecraft.func_71410_x();
   private ScaledResolution sr;
   public int alpha;
   private EntityPlayer player;
   public double scale;
   private EntityItem entity;
   public int colorOutlineShade;
   private int fadeCount;
   public int colorOutline;
   public int colorBackground;

   public boolean isDead() {
      return this.tickCount <= 0;
   }

   private boolean modsAreLoaded() {
      return Loader.isModLoaded("waila") | Loader.isModLoaded("nei") | Loader.isModLoaded("hwyla");
   }

   public int getHeight() {
      return this.height;
   }

   public void forceFade() {
      if (!this.forceFade) {
         this.tickCount = 10;
         this.fadeCount = 10;
         this.forceFade = true;
      }
   }

   public EntityItem getEntity() {
      return this.entity;
   }

   public double getFade() {
      return this.tickCount > this.fadeCount ? 1.0D : Math.abs(Math.pow(-1.0D, 2.0D) * ((double)this.tickCount / (double)this.fadeCount));
   }

   public int size() {
      return this.text.size();
   }

   public TextFormatting formattingColor() {
      return this.textFormatting;
   }

   public int getWidth() {
      return this.width;
   }

   private void generateTooltip(EntityPlayer var1) {
      boolean var2 = mc.field_71474_y.field_82882_x;
      this.text = this.entity.func_92059_d().func_82840_a(var1, var2 ? TooltipFlags.ADVANCED : TooltipFlags.NORMAL);
      if (this.entity.func_92059_d().func_190916_E() > 1) {
         this.text.set(0, String.valueOf((new StringBuilder()).append(this.entity.func_92059_d().func_190916_E()).append(" x ").append((String)this.text.get(0))));
      }

   }

   public void tick() {
      this.sr = new ScaledResolution(mc);
      if (this.entity == null || this.entity.field_70128_L) {
         this.tickCount = 0;
      }

      if (this.countDown) {
         --this.tickCount;
      } else {
         this.tickCount += 2;
      }

      if (this.tickCount < 0) {
         this.tickCount = 0;
      }

      if (this.tickCount > 40 + this.fadeCount) {
         this.tickCount = 40 + this.fadeCount;
      }

      this.generateTooltip(this.player);
      this.calculateSize();
      this.distanceToPlayer = (double)this.entity.func_70032_d(this.player);
      this.scale = this.distanceToPlayer / (double)((6 - this.sr.func_78325_e()) * 160);
      if (this.scale < 0.01D) {
         this.scale = 0.01D;
      }

      this.scale *= 2.0D;
      if (this.getFade() > 0.75D) {
         this.alpha = -1090519040;
      } else {
         this.alpha = ((int)(this.getFade() * 255.0D) & 255) << 24;
      }

      this.colorBackground = 1048592 | this.alpha;
      this.colorOutline = (TooltipsUtil.getRarityColor(this) | this.alpha) & -2039584;
      this.colorOutlineShade = (this.colorOutline & 16711422) >> 1 | this.alpha;
      this.countDown = true;
   }

   public int compareTo(Tooltip var1) {
      return (int)(var1.distanceToPlayer * 10000.0D - this.distanceToPlayer * 10000.0D);
   }

   public Tooltip(EntityPlayer var1, EntityItem var2) {
      this.sr = new ScaledResolution(mc);
      this.text = new ArrayList();
      this.countDown = true;
      this.player = var1;
      this.entity = var2;
      this.textFormatting = var2.func_92059_d().func_77953_t().field_77937_e;
      this.generateTooltip(var1);
      this.calculateSize();
      this.fadeCount = 10;
      this.tickCount = 40 + this.fadeCount;
   }

   public int getTickCount() {
      return this.tickCount;
   }

   public boolean reset() {
      if (this.forceFade) {
         return false;
      } else {
         this.countDown = false;
         return true;
      }
   }

   public List getText() {
      return this.text;
   }

   public int compareTo(Object var1) {
      return this.compareTo((Tooltip)var1);
   }

   private void calculateSize() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.text.size(); ++var2) {
         int var3 = mc.field_71466_p.func_78256_a((String)this.text.get(var2));
         if (var3 > var1) {
            var1 = var3;
         }
      }

      this.width = var1;
      this.height = 8;
      if (this.size() > 1) {
         this.height += 2 + (this.size() - 1) * 10;
      }

   }
}
