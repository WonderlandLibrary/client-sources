/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.DecoratingHttp2FrameWriter;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2LifecycleManager;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.internal.ObjectUtil;

public class DecoratingHttp2ConnectionEncoder
extends DecoratingHttp2FrameWriter
implements Http2ConnectionEncoder {
    private final Http2ConnectionEncoder delegate;

    public DecoratingHttp2ConnectionEncoder(Http2ConnectionEncoder http2ConnectionEncoder) {
        super(http2ConnectionEncoder);
        this.delegate = ObjectUtil.checkNotNull(http2ConnectionEncoder, "delegate");
    }

    @Override
    public void lifecycleManager(Http2LifecycleManager http2LifecycleManager) {
        this.delegate.lifecycleManager(http2LifecycleManager);
    }

    @Override
    public Http2Connection connection() {
        return this.delegate.connection();
    }

    @Override
    public Http2RemoteFlowController flowController() {
        return this.delegate.flowController();
    }

    @Override
    public Http2FrameWriter frameWriter() {
        return this.delegate.frameWriter();
    }

    @Override
    public Http2Settings pollSentSettings() {
        return this.delegate.pollSentSettings();
    }

    @Override
    public void remoteSettings(Http2Settings http2Settings) throws Http2Exception {
        this.delegate.remoteSettings(http2Settings);
    }
}

