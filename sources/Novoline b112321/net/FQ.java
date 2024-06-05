package net;

import net.FJ;
import net.Uj;
import net.aJ1;
import net.a_E;
import net.gZ;
import net.minecraft.command.CommandException;

public class FQ extends FJ {
   public FQ(gZ var1) {
      super(var1, "chat", "Sends a message to the public chat", "c");
   }

   public void b(String[] var1) throws CommandException {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.a("Command help", ".chat (.c)", new Uj[]{aJ1.a("(message)", this.b)});
         this.a();
      } else {
         String var3 = String.join(" ", var1);
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
