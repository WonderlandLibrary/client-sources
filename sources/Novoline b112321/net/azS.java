package net;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import net.Cw;
import net.aRY;
import net.bgR;
import net.cI;
import net.cY;

public class azS implements Runnable {
   public void run() {
      Cw.a();

      for(bgR var3 : Via.b().d()) {
         cI var4 = var3.c();
         if(var4 != null && var4.b().b(aRY.class)) {
            cY var5 = (cY)var3.b(cY.class);
            if(var5 != null) {
               long var6 = var5.a();
               if(var6 <= System.currentTimeMillis() && var3.p().isOpen()) {
                  ((MovementTransmitterProvider)Via.b().f().b(MovementTransmitterProvider.class)).a(var3);
               }
               break;
            }
         }
      }

   }
}
