package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.KK;
import net.aqQ;

public class PaintingProvider implements aqQ {
   private final Map paintings = new HashMap();

   public PaintingProvider() {
      this.add("kebab");
      this.add("aztec");
      this.add("alban");
      this.add("aztec2");
      KK.b();
      this.add("bomb");
      this.add("plant");
      this.add("wasteland");
      this.add("pool");
      this.add("courbet");
      this.add("sea");
      this.add("sunset");
      this.add("creebet");
      this.add("wanderer");
      this.add("graham");
      this.add("match");
      this.add("bust");
      this.add("stage");
      this.add("void");
      this.add("skullandroses");
      this.add("wither");
      this.add("fighters");
      this.add("pointer");
      this.add("pigscene");
      this.add("burningskull");
      this.add("skeleton");
      this.add("donkeykong");
   }

   private void add(String var1) {
      this.paintings.put("minecraft:" + var1, Integer.valueOf(this.paintings.size()));
   }

   public Optional getIntByIdentifier(String var1) {
      String[] var2 = KK.b();
      if(!var1.startsWith("minecraft:")) {
         var1 = "minecraft:" + var1.toLowerCase(Locale.ROOT);
      }

      return Optional.ofNullable(this.paintings.get(var1));
   }
}
