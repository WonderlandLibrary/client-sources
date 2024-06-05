package net;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.JL;
import net.NG;
import net.WS;
import net.aEA;
import net.aLy;
import net.a_t;
import net.azS;
import net.bgR;
import net.hZ;
import net.minecraft.network.NetworkManager$4;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class lc {
   private final ViaPlatform j;
   private final JL i = new JL();
   private final hZ c;
   private final aLy a;
   private final WS f;
   private final Set b = new HashSet();
   private List d = new ArrayList();
   private NG e;
   private boolean h;
   private static boolean g;

   public lc(ViaPlatform var1, hZ var2, aLy var3, WS var4) {
      this.j = var1;
      this.c = var2;
      this.a = var3;
      this.f = var4;
   }

   public static NetworkManager$4 j() {
      return new NetworkManager$4();
   }

   public void m() {
      boolean var1 = n();
      if(System.getProperty("ViaVersion") != null) {
         this.j.onReload();
      }

      if(this.j.getConf().i()) {
         a_t.a();
      }

      ProtocolManagerImpl.c();

      try {
         this.c.d();
      } catch (Exception var4) {
         this.j.getLogger().severe("ViaVersion failed to inject:");
         var4.printStackTrace();
         return;
      }

      System.setProperty("ViaVersion", this.j.f());
      Iterator var2 = this.d.iterator();
      if(var2.hasNext()) {
         Runnable var3 = (Runnable)var2.next();
         var3.run();
      }

      this.d = null;
      this.j.a(this::b);
   }

   public void b() {
      boolean var1 = n();

      try {
         ProtocolManagerImpl.a = ProtocolVersion.b(this.c.f()).d();
      } catch (Exception var3) {
         this.j.getLogger().severe("ViaVersion failed to get the server protocol!");
         var3.printStackTrace();
      }

      if(ProtocolManagerImpl.a != -1) {
         this.j.getLogger().info("ViaVersion detected server version: " + ProtocolVersion.b(ProtocolManagerImpl.a));
         if(!ProtocolManagerImpl.b() && !this.j.e()) {
            this.j.getLogger().warning("ViaVersion does not have any compatible versions for this server version!");
            this.j.getLogger().warning("Please remember that ViaVersion only adds support for versions newer than the server version.");
            this.j.getLogger().warning("If you need support for older versions you may need to use one or more ViaVersion addons too.");
            this.j.getLogger().warning("In that case please read the ViaVersion resource page carefully or use https://jo0001.github.io/ViaSetup");
            this.j.getLogger().warning("and if you\'re still unsure, feel free to join our Discord-Server for further assistance.");
         }

         if(ProtocolManagerImpl.a == ProtocolVersion.v1_8.d() && !this.j.e()) {
            this.j.getLogger().warning("This version of Minecraft is over half a decade old and support for it will be fully dropped eventually. Please upgrade to a newer version to avoid encountering bugs and stability issues that have long been fixed.");
         }
      }

      ProtocolManagerImpl.onServerLoaded();
      this.f.b();
      this.e = Via.d().b(this::lambda$onServerLoaded$0, Long.valueOf(10L));
      if(ProtocolManagerImpl.a < ProtocolVersion.v1_9.d() && Via.c().B()) {
         Via.d().b(new azS(), Long.valueOf(1L));
      }

      if(ProtocolManagerImpl.a < ProtocolVersion.v1_13.d() && Via.c().C() > 0) {
         Via.d().b(new aEA(), Long.valueOf(1L));
      }

      ProtocolManagerImpl.refreshVersions();
      if(PacketRemapper.b() == null) {
         b(false);
      }

   }

   public void o() {
      this.j.getLogger().info("ViaVersion is disabling, if this is a reload and you experience issues consider rebooting.");

      try {
         this.c.b();
      } catch (Exception var2) {
         this.j.getLogger().severe("ViaVersion failed to uninject:");
         var2.printStackTrace();
      }

      this.f.a();
   }

   public Set d() {
      return this.j.i().b();
   }

   /** @deprecated */
   @Deprecated
   public Map h() {
      return this.k();
   }

   public Map k() {
      return this.j.i().a();
   }

   public UUID b(bgR var1) {
      return this.j.i().d(var1);
   }

   public boolean b(UUID var1) {
      return this.j.i().b(var1);
   }

   public void a(bgR var1) {
      this.j.i().b(var1);
   }

   public ViaPlatform p() {
      return this.j;
   }

   public JL f() {
      return this.i;
   }

   public boolean c() {
      return this.h;
   }

   public void a(boolean var1) {
      this.h = var1;
   }

   public hZ g() {
      return this.c;
   }

   public aLy a() {
      return this.a;
   }

   public WS l() {
      return this.f;
   }

   public Set e() {
      return this.b;
   }

   @Nullable
   public bgR a(UUID var1) {
      return this.j.i().a(var1);
   }

   public void a(Runnable var1) {
      this.d.add(var1);
   }

   private void lambda$onServerLoaded$0() {
      boolean var1 = i();
      if(ProtocolManagerImpl.a()) {
         this.j.a(this.e);
         this.e = null;
      }

   }

   public static void b(boolean var0) {
      g = var0;
   }

   public static boolean i() {
      return g;
   }

   public static boolean n() {
      boolean var0 = i();
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b(false);
   }
}
