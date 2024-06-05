package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class RenderRabbit extends RenderLiving {
   private static final ResourceLocation field_177130_l = new ResourceLocation("textures/entity/rabbit/salt.png");
   private static final ResourceLocation field_177128_n = new ResourceLocation("textures/entity/rabbit/toast.png");
   private static final ResourceLocation field_177126_e = new ResourceLocation("textures/entity/rabbit/white.png");
   private static final ResourceLocation field_177127_a = new ResourceLocation("textures/entity/rabbit/brown.png");
   private static final ResourceLocation field_177131_m = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
   private static final ResourceLocation field_177129_o = new ResourceLocation("textures/entity/rabbit/caerbannog.png");
   private static final ResourceLocation field_177132_j = new ResourceLocation("textures/entity/rabbit/black.png");
   private static final String __OBFID = "CL_00002432";
   private static final ResourceLocation field_177133_k = new ResourceLocation("textures/entity/rabbit/gold.png");

   public RenderRabbit(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation func_177125_a(EntityRabbit var1) {
      String var2 = EnumChatFormatting.getTextWithoutFormattingCodes(var1.getName());
      if (var2 != null && var2.equals("Toast")) {
         return field_177128_n;
      } else {
         switch(var1.func_175531_cl()) {
         case 0:
         default:
            return field_177127_a;
         case 1:
            return field_177126_e;
         case 2:
            return field_177132_j;
         case 3:
            return field_177131_m;
         case 4:
            return field_177133_k;
         case 5:
            return field_177130_l;
         case 99:
            return field_177129_o;
         }
      }
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_177125_a((EntityRabbit)var1);
   }
}
