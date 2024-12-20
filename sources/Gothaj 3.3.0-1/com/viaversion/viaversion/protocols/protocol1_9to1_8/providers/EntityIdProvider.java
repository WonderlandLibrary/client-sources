package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;

public class EntityIdProvider implements Provider {
   public int getEntityId(UserConnection user) throws Exception {
      return user.<EntityTracker>getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
   }
}
