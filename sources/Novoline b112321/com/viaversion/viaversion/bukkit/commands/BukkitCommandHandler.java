package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.bgR;
import net.bgY;
import net.iU;

public class BukkitCommandHandler extends iU {
   public boolean c(bgR var1) {
      PacketRemapper[] var2 = bgY.b();
      return !(var1 instanceof bgY);
   }
}
