package de.florianmichael.vialoadingbase.platform.viaversion;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonObject;

public class VLBViaInjector implements ViaInjector {
   @Override
   public void inject() {
   }

   @Override
   public void uninject() {
   }

   @Override
   public String getDecoderName() {
      return "via-decoder";
   }

   @Override
   public String getEncoderName() {
      return "via-encoder";
   }

   @Override
   public IntSortedSet getServerProtocolVersions() {
      IntSortedSet versions = new IntLinkedOpenHashSet();

      for (ProtocolVersion value : ProtocolVersion.getProtocols()) {
         if (value.getVersion() >= ProtocolVersion.v1_7_1.getVersion()) {
            versions.add(value.getVersion());
         }
      }

      return versions;
   }

   @Override
   public int getServerProtocolVersion() {
      return this.getServerProtocolVersions().firstInt();
   }

   @Override
   public JsonObject getDump() {
      return new JsonObject();
   }
}
