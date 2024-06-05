package net;

import com.google.common.reflect.TypeToken;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.I6;
import net.NR;
import net.UW;
import net.X9;
import net.ZV;
import net.a6t;
import net.agP;
import net.akH;
import net.aqA;
import net.awp;
import net.azi;
import net.gZ;
import net.pE;
import net.t8;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class no {
   private final UW b;
   private final int d;
   private Path a;
   private static agP c;

   public no(@NotNull UW var1, int var2) {
      this.b = var1;
      this.d = var2;
   }

   public void a() {
      I6 var2 = this.b.e();
      aqA.b();
      Map var3 = this.f();
      Iterator var4 = var3.entrySet().iterator();
      if(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         String var6 = (String)var5.getKey();
         ProtocolPathEntry var7 = (ProtocolPathEntry)var5.getValue();
         ObjectIterator var8 = var2.entrySet().iterator();
         if(var8.hasNext()) {
            Entry var9 = (Entry)var8.next();
            if(((String)var9.getKey()).equalsIgnoreCase(var6)) {
               ((t8)var9.getValue()).b().a(var7);
            }
         }
      }

   }

   public boolean b() {
      aqA.b();
      boolean var2 = false;
      no var10000 = this;

      try {
         Path var3 = var10000.e();
         return false;
      } catch (Throwable var4) {
         gZ.g().t().a("Cannot save binds!", 5000, azi.ERROR);
         if(!var2) {
            gZ.v().warn("An unexpected error occurred while deleting config file!", var4);
         }

         return false;
      }
   }

   @NotNull
   private Map f() {
      aqA.b();
      Path var2 = this.d();
      if(Files.notExists(var2, new LinkOption[0])) {
         gZ.v().error("Binds file doesn\'t exist");
         return Collections.emptyMap();
      } else {
         agP var3 = this.c();
         awp var4 = ((a6t)((a6t)awp.b().a((agP)var3)).a((Path)var2)).a();
         awp var10000 = var4;
         agP var10001 = var3;

         akH var5;
         try {
            var5 = var10000.a((agP)var10001);
         } catch (IOException var9) {
            gZ.v().error("An I/O error occurred while reading binds", var9);
            return Collections.emptyMap();
         }

         Map var6 = Collections.emptyMap();

         try {
            var6 = (Map)var5.b((TypeToken)(new pE(this)));
            return var6 == null?Collections.emptyMap():var6;
         } catch (X9 var8) {
            gZ.v().error("An I/O error occurred while deserializing binds", var8);
            return var6;
         }
      }
   }

   @Nullable
   private Path e() {
      aqA.b();
      Path var2 = this.d();
      if(Files.notExists(var2, new LinkOption[0])) {
         try {
            Files.createFile(var2, new FileAttribute[0]);
         } catch (IOException var4) {
            gZ.v().warn("An I/O error occurred while creating binds file!", var4);
            return null;
         }
      }

      return var2;
   }

   @NotNull
   public Path d() {
      String var1 = aqA.b();
      return this.a == null?(this.a = this.b.d().i().resolve("binds.novo")):this.a;
   }

   @NotNull
   private agP c() {
      String var1 = aqA.b();
      if(c == null) {
         agP var2 = agP.e();
         var2.f().a((TypeToken)TypeToken.of(ProtocolPathEntry.class), (ZV)(new NR()));
         c = var2;
         return var2;
      } else {
         return c;
      }
   }

   private static ProtocolPathEntry lambda$save$0(Entry var0) {
      return (ProtocolPathEntry)((t8)var0.getValue()).b().o().a();
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
