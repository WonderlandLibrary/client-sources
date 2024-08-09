/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.logging;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;

@ChannelHandler.Sharable
public class LoggingHandler
extends ChannelDuplexHandler {
    private static final LogLevel DEFAULT_LEVEL = LogLevel.DEBUG;
    protected final InternalLogger logger;
    protected final InternalLogLevel internalLevel;
    private final LogLevel level;

    public LoggingHandler() {
        this(DEFAULT_LEVEL);
    }

    public LoggingHandler(LogLevel logLevel) {
        if (logLevel == null) {
            throw new NullPointerException("level");
        }
        this.logger = InternalLoggerFactory.getInstance(this.getClass());
        this.level = logLevel;
        this.internalLevel = logLevel.toInternalLevel();
    }

    public LoggingHandler(Class<?> clazz) {
        this(clazz, DEFAULT_LEVEL);
    }

    public LoggingHandler(Class<?> clazz, LogLevel logLevel) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (logLevel == null) {
            throw new NullPointerException("level");
        }
        this.logger = InternalLoggerFactory.getInstance(clazz);
        this.level = logLevel;
        this.internalLevel = logLevel.toInternalLevel();
    }

    public LoggingHandler(String string) {
        this(string, DEFAULT_LEVEL);
    }

    public LoggingHandler(String string, LogLevel logLevel) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        if (logLevel == null) {
            throw new NullPointerException("level");
        }
        this.logger = InternalLoggerFactory.getInstance(string);
        this.level = logLevel;
        this.internalLevel = logLevel.toInternalLevel();
    }

    public LogLevel level() {
        return this.level;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "REGISTERED"));
        }
        channelHandlerContext.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "UNREGISTERED"));
        }
        channelHandlerContext.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "ACTIVE"));
        }
        channelHandlerContext.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "INACTIVE"));
        }
        channelHandlerContext.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "EXCEPTION", throwable), throwable);
        }
        channelHandlerContext.fireExceptionCaught(throwable);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "USER_EVENT", object));
        }
        channelHandlerContext.fireUserEventTriggered(object);
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "BIND", socketAddress));
        }
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "CONNECT", socketAddress, socketAddress2));
        }
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "DISCONNECT"));
        }
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "CLOSE"));
        }
        channelHandlerContext.close(channelPromise);
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "DEREGISTER"));
        }
        channelHandlerContext.deregister(channelPromise);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "READ COMPLETE"));
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "READ", object));
        }
        channelHandlerContext.fireChannelRead(object);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "WRITE", object));
        }
        channelHandlerContext.write(object, channelPromise);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "WRITABILITY CHANGED"));
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(channelHandlerContext, "FLUSH"));
        }
        channelHandlerContext.flush();
    }

    protected String format(ChannelHandlerContext channelHandlerContext, String string) {
        String string2 = channelHandlerContext.channel().toString();
        return new StringBuilder(string2.length() + 1 + string.length()).append(string2).append(' ').append(string).toString();
    }

    protected String format(ChannelHandlerContext channelHandlerContext, String string, Object object) {
        if (object instanceof ByteBuf) {
            return LoggingHandler.formatByteBuf(channelHandlerContext, string, (ByteBuf)object);
        }
        if (object instanceof ByteBufHolder) {
            return LoggingHandler.formatByteBufHolder(channelHandlerContext, string, (ByteBufHolder)object);
        }
        return LoggingHandler.formatSimple(channelHandlerContext, string, object);
    }

    protected String format(ChannelHandlerContext channelHandlerContext, String string, Object object, Object object2) {
        if (object2 == null) {
            return LoggingHandler.formatSimple(channelHandlerContext, string, object);
        }
        String string2 = channelHandlerContext.channel().toString();
        String string3 = String.valueOf(object);
        String string4 = object2.toString();
        StringBuilder stringBuilder = new StringBuilder(string2.length() + 1 + string.length() + 2 + string3.length() + 2 + string4.length());
        stringBuilder.append(string2).append(' ').append(string).append(": ").append(string3).append(", ").append(string4);
        return stringBuilder.toString();
    }

    private static String formatByteBuf(ChannelHandlerContext channelHandlerContext, String string, ByteBuf byteBuf) {
        String string2 = channelHandlerContext.channel().toString();
        int n = byteBuf.readableBytes();
        if (n == 0) {
            StringBuilder stringBuilder = new StringBuilder(string2.length() + 1 + string.length() + 4);
            stringBuilder.append(string2).append(' ').append(string).append(": 0B");
            return stringBuilder.toString();
        }
        int n2 = n / 16 + (n % 15 == 0 ? 0 : 1) + 4;
        StringBuilder stringBuilder = new StringBuilder(string2.length() + 1 + string.length() + 2 + 10 + 1 + 2 + n2 * 80);
        stringBuilder.append(string2).append(' ').append(string).append(": ").append(n).append('B').append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(stringBuilder, byteBuf);
        return stringBuilder.toString();
    }

    private static String formatByteBufHolder(ChannelHandlerContext channelHandlerContext, String string, ByteBufHolder byteBufHolder) {
        String string2 = channelHandlerContext.channel().toString();
        String string3 = byteBufHolder.toString();
        ByteBuf byteBuf = byteBufHolder.content();
        int n = byteBuf.readableBytes();
        if (n == 0) {
            StringBuilder stringBuilder = new StringBuilder(string2.length() + 1 + string.length() + 2 + string3.length() + 4);
            stringBuilder.append(string2).append(' ').append(string).append(", ").append(string3).append(", 0B");
            return stringBuilder.toString();
        }
        int n2 = n / 16 + (n % 15 == 0 ? 0 : 1) + 4;
        StringBuilder stringBuilder = new StringBuilder(string2.length() + 1 + string.length() + 2 + string3.length() + 2 + 10 + 1 + 2 + n2 * 80);
        stringBuilder.append(string2).append(' ').append(string).append(": ").append(string3).append(", ").append(n).append('B').append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(stringBuilder, byteBuf);
        return stringBuilder.toString();
    }

    private static String formatSimple(ChannelHandlerContext channelHandlerContext, String string, Object object) {
        String string2 = channelHandlerContext.channel().toString();
        String string3 = String.valueOf(object);
        StringBuilder stringBuilder = new StringBuilder(string2.length() + 1 + string.length() + 2 + string3.length());
        return stringBuilder.append(string2).append(' ').append(string).append(": ").append(string3).toString();
    }
}

