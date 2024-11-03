package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

public class PlayerAbilitiesProvider implements Provider {
   public float getFlyingSpeed(UserConnection connection) {
      return 0.05F;
   }

   public float getWalkingSpeed(UserConnection connection) {
      return 0.1F;
   }
}
