package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.HashMap;
import java.util.Map;
import net.amd;
import net.ayx;
import net.km;

public class kw extends km {
   private final Map c = new HashMap();

   public kw(ayx var1) {
      super(var1);
      this.c.put("block.minecraft.flowing_water", "Flowing Water");
      amd.a();
      this.c.put("block.minecraft.flowing_lava", "Flowing Lava");
      this.c.put("block.minecraft.bed", "Bed");
      this.c.put("block.minecraft.bed.not_valid", "Your home bed was missing or obstructed");
      this.c.put("block.minecraft.bed.set_spawn", "Respawn point set");
      this.c.put("block.minecraft.two_turtle_eggs", "Two Turtle Eggs");
      this.c.put("block.minecraft.three_turtle_eggs", "Three Turtle Eggs");
      this.c.put("block.minecraft.four_turtle_eggs", "Four Turtle Eggs");
      this.c.put("block.minecraft.banner", "Banner");
      this.c.put("block.minecraft.wall_banner", "Wall Banner");
      this.c.put("item.minecraft.zombie_pigman_spawn_egg", "Zombie Pigman Spawn Egg");
      this.c.put("item.minecraft.skeleton_skull", "Skeleton Skull");
      this.c.put("item.minecraft.wither_skeleton_skull", "Wither Skeleton Skull");
      this.c.put("item.minecraft.zombie_head", "Zombie Head");
      this.c.put("item.minecraft.creeper_head", "Creeper Head");
      this.c.put("item.minecraft.dragon_head", "Dragon Head");
      this.c.put("entity.minecraft.zombie_pigman", "Zombie Pigman");
      this.c.put("death.fell.accident.water", "%1$s fell out of the water");
      this.c.put("death.attack.netherBed.message", "%1$s was killed by %2$s");
      this.c.put("death.attack.netherBed.link", "Intentional Game Design");
      this.c.put("advancements.husbandry.break_diamond_hoe.title", "Serious Dedication");
      this.c.put("advancements.husbandry.break_diamond_hoe.description", "Completely use up a diamond hoe, and then reevaluate your life choices");
      this.c.put("biome.minecraft.nether", "Nether");
   }

   public void a(JsonElement var1) {
      amd.a();
      super.a((JsonElement)var1);
      if(var1 != null && var1.isJsonObject()) {
         JsonObject var3 = var1.getAsJsonObject();
         JsonObject var4 = var3.getAsJsonObject("score");
         if(var4 != null && !var3.has("text")) {
            JsonPrimitive var5 = var4.getAsJsonPrimitive("value");
            if(var5 != null) {
               var3.remove("score");
               var3.add("text", var5);
            }

         }
      }
   }

   protected void a(JsonObject var1, String var2) {
      String var3 = (String)this.c.get(var2);
      var1.addProperty("translate", var3);
   }
}
