package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.List;

public interface EntityRewriter<T extends Protocol> extends Rewriter<T> {
   EntityType typeFromId(int var1);

   default EntityType objectTypeFromId(int type) {
      return this.typeFromId(type);
   }

   int newEntityId(int var1);

   void handleMetadata(int var1, List<Metadata> var2, UserConnection var3);

   default <E extends EntityTracker> E tracker(UserConnection connection) {
      return connection.getEntityTracker((Class<? extends Protocol>)this.protocol().getClass());
   }
}
