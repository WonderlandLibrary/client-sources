package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

@FunctionalInterface
public interface VersionProvider extends Provider {
   int getClosestServerProtocol(UserConnection var1) throws Exception;
}
