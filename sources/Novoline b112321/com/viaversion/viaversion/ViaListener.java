package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.UUID;
import net.bgR;
import org.jetbrains.annotations.Nullable;

public abstract class ViaListener {
   private final Class requiredPipeline;
   private boolean registered;

   public ViaListener(Class var1) {
      this.requiredPipeline = var1;
   }

   @Nullable
   protected bgR a(UUID var1) {
      return Via.b().a(var1);
   }

   protected boolean b(UUID var1) {
      PacketWrapperImpl.f();
      bgR var3 = this.a(var1);
      return this.requiredPipeline == null || var3.c().b().b(this.requiredPipeline);
   }

   public abstract void register();

   protected Class getRequiredPipeline() {
      return this.requiredPipeline;
   }

   protected boolean isRegistered() {
      return this.registered;
   }

   protected void setRegistered(boolean var1) {
      this.registered = var1;
   }
}
