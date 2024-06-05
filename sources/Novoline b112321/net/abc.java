package net;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.dump.DumpTemplate;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import java.util.Map;
import net.aUV;
import net.abo;
import net.amo;
import net.lc;

public class abc extends ViaSubCommand {
   public String name() {
      return "dump";
   }

   public String description() {
      return "Dump information about your server, this is helpful if you report bugs.";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      abo.a();
      amo var4 = new amo(System.getProperty("java.version"), System.getProperty("os.name"), ProtocolManagerImpl.a, ProtocolManagerImpl.e(), Via.d().a(), Via.d().j(), Via.d().f(), lc.class.getPackage().getImplementationVersion(), Via.b().e());
      Map var5 = Via.d().getConfigurationProvider().getValues();
      DumpTemplate var6 = new DumpTemplate(var4, var5, Via.d().b(), Via.b().g().e());
      Via.d().b(new aUV(this, var1, var4, var6));
      if(PacketRemapper.b() == null) {
         abo.b(false);
      }

      return true;
   }

   private String b(String var1) {
      return String.format("https://dump.viaversion.com/%s", new Object[]{var1});
   }

   static String a(abc var0, String var1) {
      return var0.b(var1);
   }
}
