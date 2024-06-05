package net.minecraft.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public enum Item$ToolMaterial {
   WOOD(0, 59, 2.0F, 0.0F, 15),
   STONE(1, 131, 4.0F, 1.0F, 5),
   IRON(2, 250, 6.0F, 2.0F, 14),
   EMERALD(3, 1561, 8.0F, 3.0F, 10),
   GOLD(0, 32, 12.0F, 0.0F, 22);

   private final int harvestLevel;
   private final int maxUses;
   private final float efficiencyOnProperMaterial;
   private final float damageVsEntity;
   private final int enchantability;
   private static final Item$ToolMaterial[] $VALUES = new Item$ToolMaterial[]{WOOD, STONE, IRON, EMERALD, GOLD};

   private Item$ToolMaterial(int var3, int var4, float var5, float var6, int var7) {
      this.harvestLevel = var3;
      this.maxUses = var4;
      this.efficiencyOnProperMaterial = var5;
      this.damageVsEntity = var6;
      this.enchantability = var7;
   }

   public int getMaxUses() {
      return this.maxUses;
   }

   public float getEfficiencyOnProperMaterial() {
      return this.efficiencyOnProperMaterial;
   }

   public float getDamageVsEntity() {
      return this.damageVsEntity;
   }

   public int getHarvestLevel() {
      return this.harvestLevel;
   }

   public int getEnchantability() {
      return this.enchantability;
   }

   public Item getRepairItem() {
      return this == WOOD?Item.getItemFromBlock(Blocks.planks):(this == STONE?Item.getItemFromBlock(Blocks.cobblestone):(this == GOLD?Items.gold_ingot:(this == IRON?Items.iron_ingot:(this == EMERALD?Items.diamond:null))));
   }
}
