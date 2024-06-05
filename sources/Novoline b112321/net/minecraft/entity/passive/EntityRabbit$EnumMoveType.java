package net.minecraft.entity.passive;

enum EntityRabbit$EnumMoveType {
   NONE(0.0F, 0.0F, 30, 1),
   HOP(0.8F, 0.2F, 20, 10),
   STEP(1.0F, 0.45F, 14, 14),
   SPRINT(1.75F, 0.4F, 1, 8),
   ATTACK(2.0F, 0.7F, 7, 8);

   private final float speed;
   private final float field_180077_g;
   private final int duration;
   private final int field_180085_i;
   private static final EntityRabbit$EnumMoveType[] $VALUES = new EntityRabbit$EnumMoveType[]{NONE, HOP, STEP, SPRINT, ATTACK};

   private EntityRabbit$EnumMoveType(float var3, float var4, int var5, int var6) {
      this.speed = var3;
      this.field_180077_g = var4;
      this.duration = var5;
      this.field_180085_i = var6;
   }

   public float getSpeed() {
      return this.speed;
   }

   public float func_180074_b() {
      return this.field_180077_g;
   }

   public int getDuration() {
      return this.duration;
   }

   public int func_180073_d() {
      return this.field_180085_i;
   }
}
