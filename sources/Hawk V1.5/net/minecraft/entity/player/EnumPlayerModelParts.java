package net.minecraft.entity.player;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public enum EnumPlayerModelParts {
   private final String field_179338_j;
   LEFT_SLEEVE("LEFT_SLEEVE", 2, 2, "left_sleeve"),
   CAPE("CAPE", 0, 0, "cape"),
   JACKET("JACKET", 1, 1, "jacket");

   private static final String __OBFID = "CL_00002187";
   private final int field_179341_i;
   RIGHT_PANTS_LEG("RIGHT_PANTS_LEG", 5, 5, "right_pants_leg");

   private static final EnumPlayerModelParts[] $VALUES = new EnumPlayerModelParts[]{CAPE, JACKET, LEFT_SLEEVE, RIGHT_SLEEVE, LEFT_PANTS_LEG, RIGHT_PANTS_LEG, HAT};
   private final IChatComponent field_179339_k;
   private final int field_179340_h;
   HAT("HAT", 6, 6, "hat");

   private static final EnumPlayerModelParts[] ENUM$VALUES = new EnumPlayerModelParts[]{CAPE, JACKET, LEFT_SLEEVE, RIGHT_SLEEVE, LEFT_PANTS_LEG, RIGHT_PANTS_LEG, HAT};
   RIGHT_SLEEVE("RIGHT_SLEEVE", 3, 3, "right_sleeve"),
   LEFT_PANTS_LEG("LEFT_PANTS_LEG", 4, 4, "left_pants_leg");

   public String func_179329_c() {
      return this.field_179338_j;
   }

   public int func_179328_b() {
      return this.field_179340_h;
   }

   public int func_179327_a() {
      return this.field_179341_i;
   }

   private EnumPlayerModelParts(String var3, int var4, int var5, String var6) {
      this.field_179340_h = var5;
      this.field_179341_i = 1 << var5;
      this.field_179338_j = var6;
      this.field_179339_k = new ChatComponentTranslation(String.valueOf((new StringBuilder("options.modelPart.")).append(var6)), new Object[0]);
   }

   public IChatComponent func_179326_d() {
      return this.field_179339_k;
   }
}
