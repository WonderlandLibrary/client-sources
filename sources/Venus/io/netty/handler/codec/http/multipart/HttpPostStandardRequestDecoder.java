/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.CaseIgnoringComparator;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HttpPostStandardRequestDecoder
implements InterfaceHttpPostRequestDecoder {
    private final HttpDataFactory factory;
    private final HttpRequest request;
    private final Charset charset;
    private boolean isLastChunk;
    private final List<InterfaceHttpData> bodyListHttpData = new ArrayList<InterfaceHttpData>();
    private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap<CharSequence, List<InterfaceHttpData>>(CaseIgnoringComparator.INSTANCE);
    private ByteBuf undecodedChunk;
    private int bodyListHttpDataRank;
    private HttpPostRequestDecoder.MultiPartStatus currentStatus = HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED;
    private Attribute currentAttribute;
    private boolean destroyed;
    private int discardThreshold = 0xA00000;

    public HttpPostStandardRequestDecoder(HttpRequest httpRequest) {
        this(new DefaultHttpDataFactory(16384L), httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostStandardRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest) {
        this(httpDataFactory, httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostStandardRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, Charset charset) {
        this.request = ObjectUtil.checkNotNull(httpRequest, "request");
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
        this.factory = ObjectUtil.checkNotNull(httpDataFactory, "factory");
        if (httpRequest instanceof HttpContent) {
            this.offer((HttpContent)((Object)httpRequest));
        } else {
            this.undecodedChunk = Unpooled.buffer();
            this.parseBody();
        }
    }

    private void checkDestroyed() {
        if (this.destroyed) {
            throw new IllegalStateException(HttpPostStandardRequestDecoder.class.getSimpleName() + " was destroyed already");
        }
    }

    @Override
    public boolean isMultipart() {
        this.checkDestroyed();
        return true;
    }

    @Override
    public void setDiscardThreshold(int n) {
        this.discardThreshold = ObjectUtil.checkPositiveOrZero(n, "discardThreshold");
    }

    @Override
    public int getDiscardThreshold() {
        return this.discardThreshold;
    }

    @Override
    public List<InterfaceHttpData> getBodyHttpDatas() {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
        }
        return this.bodyListHttpData;
    }

    @Override
    public List<InterfaceHttpData> getBodyHttpDatas(String string) {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
        }
        return this.bodyMapHttpData.get(string);
    }

    @Override
    public InterfaceHttpData getBodyHttpData(String string) {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
        }
        List<InterfaceHttpData> list = this.bodyMapHttpData.get(string);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public HttpPostStandardRequestDecoder offer(HttpContent httpContent) {
        this.checkDestroyed();
        ByteBuf byteBuf = httpContent.content();
        if (this.undecodedChunk == null) {
            this.undecodedChunk = byteBuf.copy();
        } else {
            this.undecodedChunk.writeBytes(byteBuf);
        }
        if (httpContent instanceof LastHttpContent) {
            this.isLastChunk = true;
        }
        this.parseBody();
        if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
            this.undecodedChunk.discardReadBytes();
        }
        return this;
    }

    @Override
    public boolean hasNext() {
        this.checkDestroyed();
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE && this.bodyListHttpDataRank >= this.bodyListHttpData.size()) {
            throw new HttpPostRequestDecoder.EndOfDataDecoderException();
        }
        return !this.bodyListHttpData.isEmpty() && this.bodyListHttpDataRank < this.bodyListHttpData.size();
    }

    @Override
    public InterfaceHttpData next() {
        this.checkDestroyed();
        if (this.hasNext()) {
            return this.bodyListHttpData.get(this.bodyListHttpDataRank++);
        }
        return null;
    }

    @Override
    public InterfaceHttpData currentPartialHttpData() {
        return this.currentAttribute;
    }

    private void parseBody() {
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE || this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
            if (this.isLastChunk) {
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
            }
            return;
        }
        this.parseBodyAttributes();
    }

    protected void addHttpData(InterfaceHttpData interfaceHttpData) {
        if (interfaceHttpData == null) {
            return;
        }
        List<InterfaceHttpData> list = this.bodyMapHttpData.get(interfaceHttpData.getName());
        if (list == null) {
            list = new ArrayList<InterfaceHttpData>(1);
            this.bodyMapHttpData.put(interfaceHttpData.getName(), list);
        }
        list.add(interfaceHttpData);
        this.bodyListHttpData.add(interfaceHttpData);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseBodyAttributesStandard() {
        int n;
        int n2 = n = this.undecodedChunk.readerIndex();
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED) {
            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
        }
        boolean bl = true;
        try {
            int n3;
            block7: while (this.undecodedChunk.isReadable() && bl) {
                char c = (char)this.undecodedChunk.readUnsignedByte();
                ++n2;
                switch (1.$SwitchMap$io$netty$handler$codec$http$multipart$HttpPostRequestDecoder$MultiPartStatus[this.currentStatus.ordinal()]) {
                    case 1: {
                        String string;
                        if (c == '=') {
                            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FIELD;
                            int n4 = n2 - 1;
                            string = HttpPostStandardRequestDecoder.decodeAttribute(this.undecodedChunk.toString(n, n4 - n, this.charset), this.charset);
                            this.currentAttribute = this.factory.createAttribute(this.request, string);
                            n = n2;
                            continue block7;
                        }
                        if (c != '&') continue block7;
                        this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                        n3 = n2 - 1;
                        string = HttpPostStandardRequestDecoder.decodeAttribute(this.undecodedChunk.toString(n, n3 - n, this.charset), this.charset);
                        this.currentAttribute = this.factory.createAttribute(this.request, string);
                        this.currentAttribute.setValue("");
                        this.addHttpData(this.currentAttribute);
                        this.currentAttribute = null;
                        n = n2;
                        bl = true;
                        continue block7;
                    }
                    case 2: {
                        if (c == '&') {
                            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                            n3 = n2 - 1;
                            this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                            n = n2;
                            bl = true;
                            continue block7;
                        }
                        if (c == '\r') {
                            if (this.undecodedChunk.isReadable()) {
                                c = (char)this.undecodedChunk.readUnsignedByte();
                                if (c != '\n') throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad end of line");
                                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                                n3 = ++n2 - 2;
                                this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                                n = n2;
                                bl = false;
                                continue block7;
                            }
                            --n2;
                            continue block7;
                        }
                        if (c != '\n') continue block7;
                        this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                        n3 = n2 - 1;
                        this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                        n = n2;
                        bl = false;
                        continue block7;
                    }
                }
                bl = false;
            }
            if (this.isLastChunk && this.currentAttribute != null) {
                n3 = n2;
                if (n3 > n) {
                    this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                } else if (!this.currentAttribute.isCompleted()) {
                    this.setFinalBuffer(Unpooled.EMPTY_BUFFER);
                }
                n = n2;
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
            } else if (bl && this.currentAttribute != null && this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.FIELD) {
                this.currentAttribute.addContent(this.undecodedChunk.copy(n, n2 - n), false);
                n = n2;
            }
            this.undecodedChunk.readerIndex(n);
            return;
        } catch (HttpPostRequestDecoder.ErrorDataDecoderException errorDataDecoderException) {
            this.undecodedChunk.readerIndex(n);
            throw errorDataDecoderException;
        } catch (IOException iOException) {
            this.undecodedChunk.readerIndex(n);
            throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseBodyAttributes() {
        int n;
        if (!this.undecodedChunk.hasArray()) {
            this.parseBodyAttributesStandard();
            return;
        }
        HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        int n2 = n = this.undecodedChunk.readerIndex();
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED) {
            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
        }
        boolean bl = true;
        try {
            int n3;
            block8: while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                char c = (char)(seekAheadOptimize.bytes[seekAheadOptimize.pos++] & 0xFF);
                ++n2;
                switch (1.$SwitchMap$io$netty$handler$codec$http$multipart$HttpPostRequestDecoder$MultiPartStatus[this.currentStatus.ordinal()]) {
                    case 1: {
                        String string;
                        if (c == '=') {
                            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FIELD;
                            int n4 = n2 - 1;
                            string = HttpPostStandardRequestDecoder.decodeAttribute(this.undecodedChunk.toString(n, n4 - n, this.charset), this.charset);
                            this.currentAttribute = this.factory.createAttribute(this.request, string);
                            n = n2;
                            continue block8;
                        }
                        if (c != '&') continue block8;
                        this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                        n3 = n2 - 1;
                        string = HttpPostStandardRequestDecoder.decodeAttribute(this.undecodedChunk.toString(n, n3 - n, this.charset), this.charset);
                        this.currentAttribute = this.factory.createAttribute(this.request, string);
                        this.currentAttribute.setValue("");
                        this.addHttpData(this.currentAttribute);
                        this.currentAttribute = null;
                        n = n2;
                        bl = true;
                        continue block8;
                    }
                    case 2: {
                        if (c == '&') {
                            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                            n3 = n2 - 1;
                            this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                            n = n2;
                            bl = true;
                            continue block8;
                        }
                        if (c == '\r') {
                            if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                                c = (char)(seekAheadOptimize.bytes[seekAheadOptimize.pos++] & 0xFF);
                                ++n2;
                                if (c != '\n') {
                                    seekAheadOptimize.setReadPosition(0);
                                    throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad end of line");
                                }
                                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                                n3 = n2 - 2;
                                seekAheadOptimize.setReadPosition(0);
                                this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                                n = n2;
                                bl = false;
                                break block8;
                            }
                            if (seekAheadOptimize.limit <= 0) continue block8;
                            --n2;
                            continue block8;
                        }
                        if (c != '\n') continue block8;
                        this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                        n3 = n2 - 1;
                        seekAheadOptimize.setReadPosition(0);
                        this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                        n = n2;
                        bl = false;
                        break block8;
                    }
                }
                seekAheadOptimize.setReadPosition(0);
                bl = false;
                break;
            }
            if (this.isLastChunk && this.currentAttribute != null) {
                n3 = n2;
                if (n3 > n) {
                    this.setFinalBuffer(this.undecodedChunk.copy(n, n3 - n));
                } else if (!this.currentAttribute.isCompleted()) {
                    this.setFinalBuffer(Unpooled.EMPTY_BUFFER);
                }
                n = n2;
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
            } else if (bl && this.currentAttribute != null && this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.FIELD) {
                this.currentAttribute.addContent(this.undecodedChunk.copy(n, n2 - n), false);
                n = n2;
            }
            this.undecodedChunk.readerIndex(n);
            return;
        } catch (HttpPostRequestDecoder.ErrorDataDecoderException errorDataDecoderException) {
            this.undecodedChunk.readerIndex(n);
            throw errorDataDecoderException;
        } catch (IOException iOException) {
            this.undecodedChunk.readerIndex(n);
            throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
        } catch (IllegalArgumentException illegalArgumentException) {
            this.undecodedChunk.readerIndex(n);
            throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
        }
    }

    private void setFinalBuffer(ByteBuf byteBuf) throws IOException {
        this.currentAttribute.addContent(byteBuf, true);
        String string = HttpPostStandardRequestDecoder.decodeAttribute(this.currentAttribute.getByteBuf().toString(this.charset), this.charset);
        this.currentAttribute.setValue(string);
        this.addHttpData(this.currentAttribute);
        this.currentAttribute = null;
    }

    private static String decodeAttribute(String string, Charset charset) {
        try {
            return QueryStringDecoder.decodeComponent(string, charset);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad string: '" + string + '\'', illegalArgumentException);
        }
    }

    @Override
    public void destroy() {
        this.cleanFiles();
        this.destroyed = true;
        if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
            this.undecodedChunk.release();
            this.undecodedChunk = null;
        }
    }

    @Override
    public void cleanFiles() {
        this.checkDestroyed();
        this.factory.cleanRequestHttpData(this.request);
    }

    @Override
    public void removeHttpDataFromClean(InterfaceHttpData interfaceHttpData) {
        this.checkDestroyed();
        this.factory.removeHttpDataFromClean(this.request, interfaceHttpData);
    }

    @Override
    public InterfaceHttpPostRequestDecoder offer(HttpContent httpContent) {
        return this.offer(httpContent);
    }
}

