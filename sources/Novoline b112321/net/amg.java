package net;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.t4;
import viaversion.viaversion.api.entities.ObjectType;

public enum amg implements ObjectType {
   BOAT(1, t4.BOAT),
   ITEM(2, t4.DROPPED_ITEM),
   AREA_EFFECT_CLOUD(3, t4.AREA_EFFECT_CLOUD),
   MINECART(10, t4.MINECART_RIDEABLE),
   TNT_PRIMED(50, t4.PRIMED_TNT),
   ENDER_CRYSTAL(51, t4.ENDER_CRYSTAL),
   TIPPED_ARROW(60, t4.TIPPED_ARROW),
   SNOWBALL(61, t4.SNOWBALL),
   EGG(62, t4.EGG),
   FIREBALL(63, t4.FIREBALL),
   SMALL_FIREBALL(64, t4.SMALL_FIREBALL),
   ENDER_PEARL(65, t4.ENDER_PEARL),
   WITHER_SKULL(66, t4.WITHER_SKULL),
   SHULKER_BULLET(67, t4.SHULKER_BULLET),
   FALLING_BLOCK(70, t4.FALLING_BLOCK),
   ITEM_FRAME(71, t4.ITEM_FRAME),
   ENDER_SIGNAL(72, t4.ENDER_SIGNAL),
   POTION(73, t4.SPLASH_POTION),
   THROWN_EXP_BOTTLE(75, t4.THROWN_EXP_BOTTLE),
   FIREWORK(76, t4.FIREWORK),
   LEASH(77, t4.LEASH_HITCH),
   ARMOR_STAND(78, t4.ARMOR_STAND),
   FISHIHNG_HOOK(90, t4.FISHING_HOOK),
   SPECTRAL_ARROW(91, t4.SPECTRAL_ARROW),
   DRAGON_FIREBALL(93, t4.DRAGON_FIREBALL);

   private static final Map d = new HashMap();
   private final int c;
   private final t4 a;
   private static final amg[] b = new amg[]{BOAT, ITEM, AREA_EFFECT_CLOUD, MINECART, TNT_PRIMED, ENDER_CRYSTAL, TIPPED_ARROW, SNOWBALL, EGG, FIREBALL, SMALL_FIREBALL, ENDER_PEARL, WITHER_SKULL, SHULKER_BULLET, FALLING_BLOCK, ITEM_FRAME, ENDER_SIGNAL, POTION, THROWN_EXP_BOTTLE, FIREWORK, LEASH, ARMOR_STAND, FISHIHNG_HOOK, SPECTRAL_ARROW, DRAGON_FIREBALL};

   private amg(int var3, t4 var4) {
      this.c = var3;
      this.a = var4;
   }

   public int getId() {
      return this.c;
   }

   public t4 a() {
      return this.a;
   }

   public static Optional b(int var0) {
      return var0 == -1?Optional.empty():Optional.ofNullable(d.get(Integer.valueOf(var0)));
   }

   public static Optional a(int var0) {
      t4.d();
      Optional var2 = b(var0);
      return !var2.isPresent()?Optional.empty():Optional.of(((amg)var2.get()).a);
   }

   static {
      for(amg var11 : values()) {
         d.put(Integer.valueOf(var11.c), var11);
      }

   }
}
