package us.myles.ViaVersion.api.protocol;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.version.VersionRange;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.ayx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProtocolVersion {
   private static final Int2ObjectMap l = new Int2ObjectOpenHashMap();
   private static final List versionList = new ArrayList();
   public static final ProtocolVersion v1_4_6 = register(51, "1.4.6/7", new VersionRange("1.4", 6, 7));
   public static final ProtocolVersion v1_5_1 = register(60, "1.5.1");
   public static final ProtocolVersion v1_5_2 = register(61, "1.5.2");
   public static final ProtocolVersion v_1_6_1 = register(73, "1.6.1");
   public static final ProtocolVersion v_1_6_2 = register(74, "1.6.2");
   public static final ProtocolVersion v_1_6_3 = register(77, "1.6.3");
   public static final ProtocolVersion v_1_6_4 = register(78, "1.6.4");
   public static final ProtocolVersion v1_7_1 = register(4, "1.7-1.7.5", new VersionRange("1.7", 0, 5));
   public static final ProtocolVersion v1_7_6 = register(5, "1.7.6-1.7.10", new VersionRange("1.7", 6, 10));
   public static final ProtocolVersion v1_8 = register(47, "1.8.x");
   public static final ProtocolVersion v1_9 = register(107, "1.9");
   public static final ProtocolVersion v1_9_1 = register(108, "1.9.1");
   public static final ProtocolVersion v1_9_2 = register(109, "1.9.2");
   public static final ProtocolVersion v1_9_3 = register(110, "1.9.3/4", new VersionRange("1.9", 3, 4));
   public static final ProtocolVersion v1_10 = register(210, "1.10.x");
   public static final ProtocolVersion v1_11 = register(315, "1.11");
   public static final ProtocolVersion v1_11_1 = register(316, "1.11.1/2", new VersionRange("1.11", 1, 2));
   public static final ProtocolVersion v1_12 = register(335, "1.12");
   public static final ProtocolVersion v1_12_1 = register(338, "1.12.1");
   public static final ProtocolVersion v1_12_2 = register(340, "1.12.2");
   public static final ProtocolVersion v1_13 = register(393, "1.13");
   public static final ProtocolVersion v1_13_1 = register(401, "1.13.1");
   public static final ProtocolVersion v1_13_2 = register(404, "1.13.2");
   public static final ProtocolVersion v1_14 = register(477, "1.14");
   public static final ProtocolVersion v1_14_1 = register(480, "1.14.1");
   public static final ProtocolVersion v1_14_2 = register(485, "1.14.2");
   public static final ProtocolVersion v1_14_3 = register(490, "1.14.3");
   public static final ProtocolVersion v1_14_4 = register(498, "1.14.4");
   public static final ProtocolVersion v1_15 = register(573, "1.15");
   public static final ProtocolVersion v1_15_1 = register(575, "1.15.1");
   public static final ProtocolVersion v1_15_2 = register(578, "1.15.2");
   public static final ProtocolVersion v1_16 = register(735, "1.16");
   public static final ProtocolVersion v1_16_1 = register(736, "1.16.1");
   public static final ProtocolVersion v1_16_2 = register(751, "1.16.2");
   public static final ProtocolVersion v1_16_3 = register(753, "1.16.3");
   public static final ProtocolVersion u = register(754, "1.16.4");
   public static final ProtocolVersion unknown = register(-1, "UNKNOWN");
   private final int version;
   private final int snapshotVersion;
   private final String name;
   private final boolean versionWildcard;
   private final Set includedVersions;

   public static ProtocolVersion register(int var0, String var1) {
      return register(var0, -1, var1);
   }

   public static ProtocolVersion register(int var0, int var1, String var2) {
      return register(var0, var1, var2, (VersionRange)null);
   }

   public static ProtocolVersion register(int var0, String var1, @Nullable VersionRange var2) {
      return register(var0, -1, var1, var2);
   }

   public static ProtocolVersion register(int var0, int var1, String var2, @Nullable VersionRange var3) {
      ProtocolVersion var4 = new ProtocolVersion(var0, var1, var2, var3);
      versionList.add(var4);
      l.put(var4.d(), var4);
      if(var4.isSnapshot()) {
         l.put(var4.getFullSnapshotVersion(), var4);
      }

      return var4;
   }

   public static boolean a(int var0) {
      return l.containsKey(var0);
   }

   @NotNull
   public static ProtocolVersion b(int var0) {
      ayx.h();
      ProtocolVersion var2 = (ProtocolVersion)l.get(var0);
      return var2 != null?var2:new ProtocolVersion(var0, "Unknown (" + var0 + ")");
   }

   public static int getIndex(ProtocolVersion var0) {
      return versionList.indexOf(var0);
   }

   public static List i() {
      return Collections.unmodifiableList(new ArrayList(l.values()));
   }

   @Nullable
   public static ProtocolVersion getClosest(String var0) {
      ayx.h();
      ObjectIterator var2 = l.values().iterator();
      if(var2.hasNext()) {
         ProtocolVersion var3 = (ProtocolVersion)var2.next();
         String var4 = var3.getName();
         if(var4.equals(var0)) {
            return var3;
         }

         if(var3.isVersionWildcard()) {
            String var5 = var4.substring(0, var4.length() - 2);
            if(var5.equals(var0) || var0.startsWith(var4.substring(0, var4.length() - 1))) {
               return var3;
            }
         }

         if(var3.isRange() && var3.getIncludedVersions().contains(var0)) {
            return var3;
         }
      }

      return null;
   }

   public ProtocolVersion(int var1, String var2) {
      this(var1, -1, var2, (VersionRange)null);
   }

   public ProtocolVersion(int var1, int var2, String var3, @Nullable VersionRange var4) {
      this.version = var1;
      this.snapshotVersion = var2;
      this.name = var3;
      this.versionWildcard = var3.endsWith(".x");
      if(this.versionWildcard) {
         ;
      }

      Preconditions.checkArgument(true, "A version cannot be a wildcard and a range at the same time!");
      this.includedVersions = new HashSet();

      for(int var5 = var4.getRangeFrom(); var5 <= var4.getRangeTo(); ++var5) {
         this.includedVersions.add(var4.getBaseVersion());
         this.includedVersions.add(var4.getBaseVersion() + "." + var5);
      }

   }

   public int d() {
      return this.version;
   }

   public int getSnapshotVersion() {
      Preconditions.checkArgument(this.isSnapshot());
      return this.snapshotVersion;
   }

   public int getFullSnapshotVersion() {
      Preconditions.checkArgument(this.isSnapshot());
      return 1073741824 | this.snapshotVersion;
   }

   public int c() {
      PacketRemapper[] var1 = ayx.h();
      return this.snapshotVersion == -1?this.version:1073741824 | this.snapshotVersion;
   }

   public boolean isRange() {
      PacketRemapper[] var1 = ayx.h();
      return this.includedVersions.size() != 1;
   }

   public Set getIncludedVersions() {
      return Collections.unmodifiableSet(this.includedVersions);
   }

   public boolean isVersionWildcard() {
      return this.versionWildcard;
   }

   public String getName() {
      return this.name;
   }

   public boolean isSnapshot() {
      return this.snapshotVersion != -1;
   }

   /** @deprecated */
   @Deprecated
   public int b() {
      return this.version;
   }

   public boolean equals(Object var1) {
      PacketRemapper[] var2 = ayx.h();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         ProtocolVersion var3 = (ProtocolVersion)var1;
         return this.version == var3.version;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.version;
   }

   public String toString() {
      return String.format("%s(%d)", new Object[]{this.name, Integer.valueOf(this.version)});
   }
}
