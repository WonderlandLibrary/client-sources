package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import java.util.ArrayList;
import java.util.List;
import net.abi;
import net.bgR;
import net.cI;

public class GlassConnectionHandler extends AbstractFenceConnectionHandler {
   static List init() {
      ArrayList var1 = new ArrayList(18);
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:white_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:orange_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:magenta_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:light_blue_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:yellow_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:lime_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:pink_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:gray_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:light_gray_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:cyan_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:purple_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:blue_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:brown_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:green_stained_glass_pane"));
      abi.b();
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:red_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:black_stained_glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:glass_pane"));
      var1.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:iron_bars"));
      return var1;
   }

   public GlassConnectionHandler(String var1) {
      super(var1);
   }

   protected byte a(bgR var1, Position var2, int var3) {
      abi.b();
      byte var5 = super.a(var1, var2, var3);
      if(var5 != 0) {
         return var5;
      } else {
         cI var6 = var1.c();
         return var6.h() <= 47 && var6.h() != -1?15:var5;
      }
   }
}
