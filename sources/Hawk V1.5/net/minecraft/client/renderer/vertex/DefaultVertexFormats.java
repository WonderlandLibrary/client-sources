package net.minecraft.client.renderer.vertex;

import optifine.Config;
import optifine.Reflector;
import shadersmod.client.SVertexFormat;

public class DefaultVertexFormats {
   public static VertexFormat field_176599_b = new VertexFormat();
   private static final String __OBFID = "CL_00002403";
   public static VertexFormat field_176600_a = new VertexFormat();
   private static final VertexFormat BLOCK_VANILLA;
   private static final VertexFormat ITEM_VANILLA;

   static {
      BLOCK_VANILLA = field_176600_a;
      ITEM_VANILLA = field_176599_b;
      field_176600_a.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
      field_176600_a.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
      field_176600_a.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
      field_176600_a.func_177349_a(new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2));
      field_176599_b.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
      field_176599_b.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
      field_176599_b.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
      field_176599_b.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
      field_176599_b.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.PADDING, 1));
   }

   public static void updateVertexFormats() {
      if (Config.isShaders()) {
         field_176600_a = SVertexFormat.makeDefVertexFormatBlock();
         field_176599_b = SVertexFormat.makeDefVertexFormatItem();
      } else {
         field_176600_a = BLOCK_VANILLA;
         field_176599_b = ITEM_VANILLA;
      }

      if (Reflector.Attributes_DEFAULT_BAKED_FORMAT.exists()) {
         VertexFormat var0 = field_176599_b;
         VertexFormat var1 = (VertexFormat)Reflector.getFieldValue(Reflector.Attributes_DEFAULT_BAKED_FORMAT);
         var1.clear();

         for(int var2 = 0; var2 < var0.func_177345_h(); ++var2) {
            var1.func_177349_a(var0.func_177348_c(var2));
         }
      }

   }
}
