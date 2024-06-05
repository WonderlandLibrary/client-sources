package net;

import cc.novoline.commands.impl.FindBountyCommand;
import cc.novoline.commands.impl.FontCommand;
import cc.novoline.commands.impl.IGNCommand;
import net.F7;
import net.F9;
import net.FA;
import net.FC;
import net.FE;
import net.FH;
import net.FM;
import net.FQ;
import net.FV;
import net.FW;
import net.FZ;
import net.Fc;
import net.Fi;
import net.Fl;
import net.Fo;
import net.gZ;
import net.minecraft.command.CommandHandler;
import org.jetbrains.annotations.NotNull;

public class oc extends CommandHandler {
   public oc(@NotNull gZ var1) {
      super(".");
      this.a(var1);
   }

   public void a(gZ var1) {
      this.a(new FH(var1));
      this.a(new FA(var1));
      this.a(new Fc(var1));
      this.a(new F9(var1));
      this.a(new FV(var1));
      this.a(new Fi(var1));
      this.a(new FE(var1));
      this.a(new Fl(var1));
      this.a(new FM(var1));
      this.a(new F7(var1));
      this.a(new FQ(var1));
      this.a(new FC(var1));
      this.a(new Fo(var1));
      this.a(new FW(var1));
      this.a(new FZ(var1));
      this.a(new FindBountyCommand(var1));
      this.a(new IGNCommand(var1));
      this.a(new FontCommand(var1));
   }
}
