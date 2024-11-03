package com.viaversion.viarewind.api.minecraft;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.List;

public abstract class EntityModel<T extends Protocol<?, ?, ?, ?>> {
   protected final UserConnection user;
   protected final T protocol;

   public EntityModel(UserConnection user, T protocol) {
      this.user = user;
      this.protocol = protocol;
   }

   public abstract int getEntityId();

   public abstract void updateReplacementPosition(double var1, double var3, double var5);

   public abstract void handleOriginalMovementPacket(double var1, double var3, double var5);

   public abstract void setYawPitch(float var1, float var2);

   public abstract void setHeadYaw(float var1);

   public abstract void sendSpawnPacket();

   public abstract void deleteEntity();

   public abstract void updateMetadata(List<Metadata> var1);

   public UserConnection getUser() {
      return this.user;
   }

   public T getProtocol() {
      return this.protocol;
   }
}
