package org.spongepowered.asm.lib;

class Handler {
   Label start;
   Label end;
   Label handler;
   String desc;
   int type;
   Handler next;

   static Handler remove(Handler var0, Label var1, Label var2) {
      if (var0 == null) {
         return null;
      } else {
         var0.next = remove(var0.next, var1, var2);
         int var3 = var0.start.position;
         int var4 = var0.end.position;
         int var5 = var1.position;
         int var6 = var2 == null ? Integer.MAX_VALUE : var2.position;
         if (var5 < var4 && var6 > var3) {
            if (var5 <= var3) {
               if (var6 >= var4) {
                  var0 = var0.next;
               } else {
                  var0.start = var2;
               }
            } else if (var6 >= var4) {
               var0.end = var1;
            } else {
               Handler var7 = new Handler();
               var7.start = var2;
               var7.end = var0.end;
               var7.handler = var0.handler;
               var7.desc = var0.desc;
               var7.type = var0.type;
               var7.next = var0.next;
               var0.end = var1;
               var0.next = var7;
            }
         }

         return var0;
      }
   }
}
