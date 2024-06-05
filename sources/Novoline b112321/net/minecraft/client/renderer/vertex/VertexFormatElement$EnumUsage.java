package net.minecraft.client.renderer.vertex;

public enum VertexFormatElement$EnumUsage {
   POSITION("Position"),
   NORMAL("Normal"),
   COLOR("Vertex Color"),
   UV("UV"),
   MATRIX("Bone Matrix"),
   BLEND_WEIGHT("Blend Weight"),
   PADDING("Padding");

   private final String displayName;
   private static final VertexFormatElement$EnumUsage[] $VALUES = new VertexFormatElement$EnumUsage[]{POSITION, NORMAL, COLOR, UV, MATRIX, BLEND_WEIGHT, PADDING};

   private VertexFormatElement$EnumUsage(String var3) {
      this.displayName = var3;
   }

   public String getDisplayName() {
      return this.displayName;
   }
}
