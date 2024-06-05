package net;

import cc.novoline.utils.java.Checks;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import viaversion.viaversion.api.data.UserConnection;

public class a29 extends NetworkManager {
   private UserConnection q;
   private final Lock s;
   private final Lock r;

   private a29(@NotNull EnumPacketDirection var1, @NotNull Lock var2, @NotNull Lock var3) {
      super(var1);
      this.s = var2;
      this.r = var3;
   }

   @Contract("_ -> new")
   @NotNull
   public static a29 a(@NotNull EnumPacketDirection var0) {
      Checks.notNull(var0, "Direction");
      ReentrantReadWriteLock var1 = new ReentrantReadWriteLock();
      return new a29(var0, var1.readLock(), var1.writeLock());
   }

   @Contract(
      mutates = "this"
   )
   public void a(@NotNull UserConnection var1) {
      Checks.notNull(var1, "User connection");
      Lock var2 = this.s;
      var2.lock();
      a29 var10000 = this;

      try {
         UserConnection var3 = var10000.q;
      } finally {
         var2.unlock();
      }

      throw new IllegalStateException("User connection cannot be set twice");
   }

   @Contract(
      pure = true
   )
   @NotNull
   public UserConnection a() {
      Lock var1 = this.s;
      var1.lock();
      a29 var10000 = this;

      UserConnection var2;
      try {
         var2 = var10000.q;
      } finally {
         var1.unlock();
      }

      return var2;
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
