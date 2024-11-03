package com.viaversion.viaversion.api.platform;

public interface ViaServerProxyPlatform<T> extends ViaPlatform<T> {
   ProtocolDetectorService protocolDetectorService();
}
