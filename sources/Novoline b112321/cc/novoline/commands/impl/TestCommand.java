package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.utils.java.Checks;
import io.netty.buffer.Unpooled;
import java.util.Locale;
import java.util.function.Consumer;
import net.a29;
import net.a_E;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.CommandException;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import viaversion.viaversion.api.data.UserConnection;

public class TestCommand extends NovoCommand {
   public TestCommand(@NotNull Novoline var1) {
      super(var1, "test");
   }

   public void process(String[] var1) throws CommandException {
      a_E.b();
      Minecraft var3 = this.novoline.getMinecraft();
      if(var3.player != null) {
         NetHandlerPlayClient var4 = var3.player.connection;
         NetworkManager var5 = var4.getNetworkManager();
         if(var5 instanceof a29) {
            a29 var6 = (a29)var5;
            UserConnection var7 = var6.a();
            if(var1.length < 1) {
               this.send("No args");
               return;
            }

            String var8 = var1[0].toLowerCase(Locale.ROOT);
            byte var11 = -1;
            switch(var8.hashCode()) {
            case 97:
               if(!var8.equals("a")) {
                  break;
               }

               var11 = 0;
            case 98:
               if(var8.equals("b")) {
                  var11 = 1;
               }
            }

            switch(var11) {
            case 0:
               PacketBuffer var9 = constructPacket(10, 39, TestCommand::lambda$process$0);
            case 1:
               PacketBuffer var12 = constructPacket(10, 42, TestCommand::lambda$process$1);
            default:
               this.send("Unexpected value: " + var8);
               return;
            }
         }

         this.send("No via");
      }

   }

   public static void sendPacketWithoutConversion(@NotNull PacketBuffer var0) {
      Checks.notNull(var0, "Packet buffer");
      a_E.b();
      Minecraft var2 = Minecraft.getInstance();
      if(var2.player == null) {
         throw new IllegalStateException("mc.player is null");
      } else {
         NetHandlerPlayClient var3 = var2.player.connection;
         NetworkManager var4 = var3.getNetworkManager();
         if(!(var4 instanceof a29)) {
            throw new IllegalStateException("No via");
         } else {
            a29 var5 = (a29)var4;
            UserConnection var6 = var5.a();
            var6.sendRawPacketToServerClientSide(var0, false);
            if(acE.b() == null) {
               a_E.b(new int[3]);
            }

         }
      }
   }

   @Contract("_, _, _ -> new")
   @NotNull
   public static PacketBuffer constructPacket(int var0, int var1, @NotNull Consumer var2) {
      PacketBuffer var3 = new PacketBuffer(Unpooled.buffer(var0));
      var2.accept(var3);
      return var3;
   }

   private static void lambda$process$1(PacketBuffer var0) {
      var0.writeEnumValue(a_E.OFF_HAND);
   }

   private static void lambda$process$0(PacketBuffer var0) {
      var0.writeEnumValue(a_E.OFF_HAND);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
