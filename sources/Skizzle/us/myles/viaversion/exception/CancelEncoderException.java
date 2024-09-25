/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.handler.codec.EncoderException
 */
package us.myles.ViaVersion.exception;

import io.netty.handler.codec.EncoderException;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.exception.CancelCodecException;

public class CancelEncoderException
extends EncoderException
implements CancelCodecException {
    public static final CancelEncoderException CACHED = new CancelEncoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these"){

        public Throwable fillInStackTrace() {
            return this;
        }
    };

    public CancelEncoderException() {
    }

    public CancelEncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelEncoderException(String message) {
        super(message);
    }

    public CancelEncoderException(Throwable cause) {
        super(cause);
    }

    public static CancelEncoderException generate(Throwable cause) {
        return Via.getManager().isDebug() ? new CancelEncoderException(cause) : CACHED;
    }
}

