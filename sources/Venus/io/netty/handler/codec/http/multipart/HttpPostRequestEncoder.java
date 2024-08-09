/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InternalAttribute;
import io.netty.handler.stream.ChunkedInput;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HttpPostRequestEncoder
implements ChunkedInput<HttpContent> {
    private static final Map.Entry[] percentEncodings = new Map.Entry[]{new AbstractMap.SimpleImmutableEntry<Pattern, String>(Pattern.compile("\\*"), "%2A"), new AbstractMap.SimpleImmutableEntry<Pattern, String>(Pattern.compile("\\+"), "%20"), new AbstractMap.SimpleImmutableEntry<Pattern, String>(Pattern.compile("~"), "%7E")};
    private final HttpDataFactory factory;
    private final HttpRequest request;
    private final Charset charset;
    private boolean isChunked;
    private final List<InterfaceHttpData> bodyListDatas;
    final List<InterfaceHttpData> multipartHttpDatas;
    private final boolean isMultipart;
    String multipartDataBoundary;
    String multipartMixedBoundary;
    private boolean headerFinalized;
    private final EncoderMode encoderMode;
    private boolean isLastChunk;
    private boolean isLastChunkSent;
    private FileUpload currentFileUpload;
    private boolean duringMixedMode;
    private long globalBodySize;
    private long globalProgress;
    private ListIterator<InterfaceHttpData> iterator;
    private ByteBuf currentBuffer;
    private InterfaceHttpData currentData;
    private boolean isKey = true;

    public HttpPostRequestEncoder(HttpRequest httpRequest, boolean bl) throws ErrorDataEncoderException {
        this(new DefaultHttpDataFactory(16384L), httpRequest, bl, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }

    public HttpPostRequestEncoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, boolean bl) throws ErrorDataEncoderException {
        this(httpDataFactory, httpRequest, bl, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }

    public HttpPostRequestEncoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, boolean bl, Charset charset, EncoderMode encoderMode) throws ErrorDataEncoderException {
        this.request = ObjectUtil.checkNotNull(httpRequest, "request");
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
        this.factory = ObjectUtil.checkNotNull(httpDataFactory, "factory");
        if (HttpMethod.TRACE.equals(httpRequest.method())) {
            throw new ErrorDataEncoderException("Cannot create a Encoder if request is a TRACE");
        }
        this.bodyListDatas = new ArrayList<InterfaceHttpData>();
        this.isLastChunk = false;
        this.isLastChunkSent = false;
        this.isMultipart = bl;
        this.multipartHttpDatas = new ArrayList<InterfaceHttpData>();
        this.encoderMode = encoderMode;
        if (this.isMultipart) {
            this.initDataMultipart();
        }
    }

    public void cleanFiles() {
        this.factory.cleanRequestHttpData(this.request);
    }

    public boolean isMultipart() {
        return this.isMultipart;
    }

    private void initDataMultipart() {
        this.multipartDataBoundary = HttpPostRequestEncoder.getNewMultipartDelimiter();
    }

    private void initMixedMultipart() {
        this.multipartMixedBoundary = HttpPostRequestEncoder.getNewMultipartDelimiter();
    }

    private static String getNewMultipartDelimiter() {
        return Long.toHexString(PlatformDependent.threadLocalRandom().nextLong());
    }

    public List<InterfaceHttpData> getBodyListAttributes() {
        return this.bodyListDatas;
    }

    public void setBodyHttpDatas(List<InterfaceHttpData> list) throws ErrorDataEncoderException {
        if (list == null) {
            throw new NullPointerException("datas");
        }
        this.globalBodySize = 0L;
        this.bodyListDatas.clear();
        this.currentFileUpload = null;
        this.duringMixedMode = false;
        this.multipartHttpDatas.clear();
        for (InterfaceHttpData interfaceHttpData : list) {
            this.addBodyHttpData(interfaceHttpData);
        }
    }

    public void addBodyAttribute(String string, String string2) throws ErrorDataEncoderException {
        String string3 = string2 != null ? string2 : "";
        Attribute attribute = this.factory.createAttribute(this.request, ObjectUtil.checkNotNull(string, "name"), string3);
        this.addBodyHttpData(attribute);
    }

    public void addBodyFileUpload(String string, File file, String string2, boolean bl) throws ErrorDataEncoderException {
        this.addBodyFileUpload(string, file.getName(), file, string2, bl);
    }

    public void addBodyFileUpload(String string, String string2, File file, String string3, boolean bl) throws ErrorDataEncoderException {
        ObjectUtil.checkNotNull(string, "name");
        ObjectUtil.checkNotNull(file, "file");
        if (string2 == null) {
            string2 = "";
        }
        String string4 = string3;
        String string5 = null;
        if (string3 == null) {
            string4 = bl ? "text/plain" : "application/octet-stream";
        }
        if (!bl) {
            string5 = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();
        }
        FileUpload fileUpload = this.factory.createFileUpload(this.request, string, string2, string4, string5, null, file.length());
        try {
            fileUpload.setContent(file);
        } catch (IOException iOException) {
            throw new ErrorDataEncoderException(iOException);
        }
        this.addBodyHttpData(fileUpload);
    }

    public void addBodyFileUploads(String string, File[] fileArray, String[] stringArray, boolean[] blArray) throws ErrorDataEncoderException {
        if (fileArray.length != stringArray.length && fileArray.length != blArray.length) {
            throw new IllegalArgumentException("Different array length");
        }
        for (int i = 0; i < fileArray.length; ++i) {
            this.addBodyFileUpload(string, fileArray[i], stringArray[i], blArray[i]);
        }
    }

    public void addBodyHttpData(InterfaceHttpData interfaceHttpData) throws ErrorDataEncoderException {
        if (this.headerFinalized) {
            throw new ErrorDataEncoderException("Cannot add value once finalized");
        }
        this.bodyListDatas.add(ObjectUtil.checkNotNull(interfaceHttpData, "data"));
        if (!this.isMultipart) {
            if (interfaceHttpData instanceof Attribute) {
                Attribute attribute = (Attribute)interfaceHttpData;
                try {
                    String string = this.encodeAttribute(attribute.getName(), this.charset);
                    String string2 = this.encodeAttribute(attribute.getValue(), this.charset);
                    Attribute attribute2 = this.factory.createAttribute(this.request, string, string2);
                    this.multipartHttpDatas.add(attribute2);
                    this.globalBodySize += (long)(attribute2.getName().length() + 1) + attribute2.length() + 1L;
                } catch (IOException iOException) {
                    throw new ErrorDataEncoderException(iOException);
                }
            } else if (interfaceHttpData instanceof FileUpload) {
                FileUpload fileUpload = (FileUpload)interfaceHttpData;
                String string = this.encodeAttribute(fileUpload.getName(), this.charset);
                String string3 = this.encodeAttribute(fileUpload.getFilename(), this.charset);
                Attribute attribute = this.factory.createAttribute(this.request, string, string3);
                this.multipartHttpDatas.add(attribute);
                this.globalBodySize += (long)(attribute.getName().length() + 1) + attribute.length() + 1L;
            }
            return;
        }
        if (interfaceHttpData instanceof Attribute) {
            InternalAttribute internalAttribute;
            if (this.duringMixedMode) {
                internalAttribute = new InternalAttribute(this.charset);
                internalAttribute.addValue("\r\n--" + this.multipartMixedBoundary + "--");
                this.multipartHttpDatas.add(internalAttribute);
                this.multipartMixedBoundary = null;
                this.currentFileUpload = null;
                this.duringMixedMode = false;
            }
            internalAttribute = new InternalAttribute(this.charset);
            if (!this.multipartHttpDatas.isEmpty()) {
                internalAttribute.addValue("\r\n");
            }
            internalAttribute.addValue("--" + this.multipartDataBoundary + "\r\n");
            Attribute attribute = (Attribute)interfaceHttpData;
            internalAttribute.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + attribute.getName() + "\"\r\n");
            internalAttribute.addValue(HttpHeaderNames.CONTENT_LENGTH + ": " + attribute.length() + "\r\n");
            Charset charset = attribute.getCharset();
            if (charset != null) {
                internalAttribute.addValue(HttpHeaderNames.CONTENT_TYPE + ": " + "text/plain" + "; " + HttpHeaderValues.CHARSET + '=' + charset.name() + "\r\n");
            }
            internalAttribute.addValue("\r\n");
            this.multipartHttpDatas.add(internalAttribute);
            this.multipartHttpDatas.add(interfaceHttpData);
            this.globalBodySize += attribute.length() + (long)internalAttribute.size();
        } else if (interfaceHttpData instanceof FileUpload) {
            Object object;
            boolean bl;
            FileUpload fileUpload = (FileUpload)interfaceHttpData;
            InternalAttribute internalAttribute = new InternalAttribute(this.charset);
            if (!this.multipartHttpDatas.isEmpty()) {
                internalAttribute.addValue("\r\n");
            }
            if (this.duringMixedMode) {
                if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) {
                    bl = true;
                } else {
                    internalAttribute.addValue("--" + this.multipartMixedBoundary + "--");
                    this.multipartHttpDatas.add(internalAttribute);
                    this.multipartMixedBoundary = null;
                    internalAttribute = new InternalAttribute(this.charset);
                    internalAttribute.addValue("\r\n");
                    bl = false;
                    this.currentFileUpload = fileUpload;
                    this.duringMixedMode = false;
                }
            } else if (this.encoderMode != EncoderMode.HTML5 && this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) {
                this.initMixedMultipart();
                object = (InternalAttribute)this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2);
                this.globalBodySize -= (long)((InternalAttribute)object).size();
                StringBuilder stringBuilder = new StringBuilder(139 + this.multipartDataBoundary.length() + this.multipartMixedBoundary.length() * 2 + fileUpload.getFilename().length() + fileUpload.getName().length()).append("--").append(this.multipartDataBoundary).append("\r\n").append(HttpHeaderNames.CONTENT_DISPOSITION).append(": ").append(HttpHeaderValues.FORM_DATA).append("; ").append(HttpHeaderValues.NAME).append("=\"").append(fileUpload.getName()).append("\"\r\n").append(HttpHeaderNames.CONTENT_TYPE).append(": ").append(HttpHeaderValues.MULTIPART_MIXED).append("; ").append(HttpHeaderValues.BOUNDARY).append('=').append(this.multipartMixedBoundary).append("\r\n\r\n").append("--").append(this.multipartMixedBoundary).append("\r\n").append(HttpHeaderNames.CONTENT_DISPOSITION).append(": ").append(HttpHeaderValues.ATTACHMENT);
                if (!fileUpload.getFilename().isEmpty()) {
                    stringBuilder.append("; ").append(HttpHeaderValues.FILENAME).append("=\"").append(fileUpload.getFilename()).append('\"');
                }
                stringBuilder.append("\r\n");
                ((InternalAttribute)object).setValue(stringBuilder.toString(), 1);
                ((InternalAttribute)object).setValue("", 2);
                this.globalBodySize += (long)((InternalAttribute)object).size();
                bl = true;
                this.duringMixedMode = true;
            } else {
                bl = false;
                this.currentFileUpload = fileUpload;
                this.duringMixedMode = false;
            }
            if (bl) {
                internalAttribute.addValue("--" + this.multipartMixedBoundary + "\r\n");
                if (fileUpload.getFilename().isEmpty()) {
                    internalAttribute.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.ATTACHMENT + "\r\n");
                } else {
                    internalAttribute.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.ATTACHMENT + "; " + HttpHeaderValues.FILENAME + "=\"" + fileUpload.getFilename() + "\"\r\n");
                }
            } else {
                internalAttribute.addValue("--" + this.multipartDataBoundary + "\r\n");
                if (fileUpload.getFilename().isEmpty()) {
                    internalAttribute.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + fileUpload.getName() + "\"\r\n");
                } else {
                    internalAttribute.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + fileUpload.getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + fileUpload.getFilename() + "\"\r\n");
                }
            }
            internalAttribute.addValue(HttpHeaderNames.CONTENT_LENGTH + ": " + fileUpload.length() + "\r\n");
            internalAttribute.addValue(HttpHeaderNames.CONTENT_TYPE + ": " + fileUpload.getContentType());
            object = fileUpload.getContentTransferEncoding();
            if (object != null && ((String)object).equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                internalAttribute.addValue("\r\n" + HttpHeaderNames.CONTENT_TRANSFER_ENCODING + ": " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n");
            } else if (fileUpload.getCharset() != null) {
                internalAttribute.addValue("; " + HttpHeaderValues.CHARSET + '=' + fileUpload.getCharset().name() + "\r\n\r\n");
            } else {
                internalAttribute.addValue("\r\n\r\n");
            }
            this.multipartHttpDatas.add(internalAttribute);
            this.multipartHttpDatas.add(interfaceHttpData);
            this.globalBodySize += fileUpload.length() + (long)internalAttribute.size();
        }
    }

    public HttpRequest finalizeRequest() throws ErrorDataEncoderException {
        Object object;
        Object object2;
        if (!this.headerFinalized) {
            if (this.isMultipart) {
                object2 = new InternalAttribute(this.charset);
                if (this.duringMixedMode) {
                    ((InternalAttribute)object2).addValue("\r\n--" + this.multipartMixedBoundary + "--");
                }
                ((InternalAttribute)object2).addValue("\r\n--" + this.multipartDataBoundary + "--\r\n");
                this.multipartHttpDatas.add((InterfaceHttpData)object2);
                this.multipartMixedBoundary = null;
                this.currentFileUpload = null;
                this.duringMixedMode = false;
                this.globalBodySize += (long)((InternalAttribute)object2).size();
            }
        } else {
            throw new ErrorDataEncoderException("Header already encoded");
        }
        this.headerFinalized = true;
        object2 = this.request.headers();
        List<String> list = ((HttpHeaders)object2).getAll(HttpHeaderNames.CONTENT_TYPE);
        List<String> list2 = ((HttpHeaders)object2).getAll(HttpHeaderNames.TRANSFER_ENCODING);
        if (list != null) {
            ((HttpHeaders)object2).remove(HttpHeaderNames.CONTENT_TYPE);
            for (String string : list) {
                object = string.toLowerCase();
                if (((String)object).startsWith(HttpHeaderValues.MULTIPART_FORM_DATA.toString()) || ((String)object).startsWith(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())) continue;
                ((HttpHeaders)object2).add((CharSequence)HttpHeaderNames.CONTENT_TYPE, (Object)string);
            }
        }
        if (this.isMultipart) {
            String string = HttpHeaderValues.MULTIPART_FORM_DATA + "; " + HttpHeaderValues.BOUNDARY + '=' + this.multipartDataBoundary;
            ((HttpHeaders)object2).add((CharSequence)HttpHeaderNames.CONTENT_TYPE, (Object)string);
        } else {
            ((HttpHeaders)object2).add((CharSequence)HttpHeaderNames.CONTENT_TYPE, (Object)HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
        }
        long l = this.globalBodySize;
        if (!this.isMultipart) {
            --l;
        }
        this.iterator = this.multipartHttpDatas.listIterator();
        ((HttpHeaders)object2).set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)String.valueOf(l));
        if (l > 8096L || this.isMultipart) {
            this.isChunked = true;
            if (list2 != null) {
                ((HttpHeaders)object2).remove(HttpHeaderNames.TRANSFER_ENCODING);
                for (CharSequence charSequence : list2) {
                    if (HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase(charSequence)) continue;
                    ((HttpHeaders)object2).add((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)charSequence);
                }
            }
            HttpUtil.setTransferEncodingChunked(this.request, true);
            return new WrappedHttpRequest(this.request);
        }
        object = this.nextChunk();
        if (this.request instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest)this.request;
            ByteBuf byteBuf = object.content();
            if (fullHttpRequest.content() != byteBuf) {
                fullHttpRequest.content().clear().writeBytes(byteBuf);
                byteBuf.release();
            }
            return fullHttpRequest;
        }
        return new WrappedFullHttpRequest(this.request, (HttpContent)object, null);
    }

    public boolean isChunked() {
        return this.isChunked;
    }

    private String encodeAttribute(String string, Charset charset) throws ErrorDataEncoderException {
        if (string == null) {
            return "";
        }
        try {
            String string2 = URLEncoder.encode(string, charset.name());
            if (this.encoderMode == EncoderMode.RFC3986) {
                for (Map.Entry entry : percentEncodings) {
                    String string3 = (String)entry.getValue();
                    string2 = ((Pattern)entry.getKey()).matcher(string2).replaceAll(string3);
                }
            }
            return string2;
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new ErrorDataEncoderException(charset.name(), unsupportedEncodingException);
        }
    }

    private ByteBuf fillByteBuf() {
        int n = this.currentBuffer.readableBytes();
        if (n > 8096) {
            return this.currentBuffer.readRetainedSlice(8096);
        }
        ByteBuf byteBuf = this.currentBuffer;
        this.currentBuffer = null;
        return byteBuf;
    }

    private HttpContent encodeNextChunkMultipart(int n) throws ErrorDataEncoderException {
        ByteBuf byteBuf;
        if (this.currentData == null) {
            return null;
        }
        if (this.currentData instanceof InternalAttribute) {
            byteBuf = ((InternalAttribute)this.currentData).toByteBuf();
            this.currentData = null;
        } else {
            try {
                byteBuf = ((HttpData)this.currentData).getChunk(n);
            } catch (IOException iOException) {
                throw new ErrorDataEncoderException(iOException);
            }
            if (byteBuf.capacity() == 0) {
                this.currentData = null;
                return null;
            }
        }
        this.currentBuffer = this.currentBuffer == null ? byteBuf : Unpooled.wrappedBuffer(this.currentBuffer, byteBuf);
        if (this.currentBuffer.readableBytes() < 8096) {
            this.currentData = null;
            return null;
        }
        byteBuf = this.fillByteBuf();
        return new DefaultHttpContent(byteBuf);
    }

    private HttpContent encodeNextChunkUrlEncoded(int n) throws ErrorDataEncoderException {
        ByteBuf byteBuf;
        String string;
        if (this.currentData == null) {
            return null;
        }
        int n2 = n;
        if (this.isKey) {
            string = this.currentData.getName();
            byteBuf = Unpooled.wrappedBuffer(string.getBytes());
            this.isKey = false;
            this.currentBuffer = this.currentBuffer == null ? Unpooled.wrappedBuffer(byteBuf, Unpooled.wrappedBuffer("=".getBytes())) : Unpooled.wrappedBuffer(this.currentBuffer, byteBuf, Unpooled.wrappedBuffer("=".getBytes()));
            n2 -= byteBuf.readableBytes() + 1;
            if (this.currentBuffer.readableBytes() >= 8096) {
                byteBuf = this.fillByteBuf();
                return new DefaultHttpContent(byteBuf);
            }
        }
        try {
            byteBuf = ((HttpData)this.currentData).getChunk(n2);
        } catch (IOException iOException) {
            throw new ErrorDataEncoderException(iOException);
        }
        string = null;
        if (byteBuf.readableBytes() < n2) {
            this.isKey = true;
            String string2 = string = this.iterator.hasNext() ? Unpooled.wrappedBuffer("&".getBytes()) : null;
        }
        if (byteBuf.capacity() == 0) {
            this.currentData = null;
            if (this.currentBuffer == null) {
                this.currentBuffer = string;
            } else if (string != null) {
                this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[]{this.currentBuffer, string});
            }
            if (this.currentBuffer.readableBytes() >= 8096) {
                byteBuf = this.fillByteBuf();
                return new DefaultHttpContent(byteBuf);
            }
            return null;
        }
        this.currentBuffer = this.currentBuffer == null ? (string != null ? Unpooled.wrappedBuffer(new ByteBuf[]{byteBuf, string}) : byteBuf) : (string != null ? Unpooled.wrappedBuffer(new ByteBuf[]{this.currentBuffer, byteBuf, string}) : Unpooled.wrappedBuffer(this.currentBuffer, byteBuf));
        if (this.currentBuffer.readableBytes() < 8096) {
            this.currentData = null;
            this.isKey = true;
            return null;
        }
        byteBuf = this.fillByteBuf();
        return new DefaultHttpContent(byteBuf);
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    @Deprecated
    public HttpContent readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext.alloc());
    }

    @Override
    public HttpContent readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        if (this.isLastChunkSent) {
            return null;
        }
        HttpContent httpContent = this.nextChunk();
        this.globalProgress += (long)httpContent.content().readableBytes();
        return httpContent;
    }

    private HttpContent nextChunk() throws ErrorDataEncoderException {
        HttpContent httpContent;
        if (this.isLastChunk) {
            this.isLastChunkSent = true;
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        int n = this.calculateRemainingSize();
        if (n <= 0) {
            ByteBuf byteBuf = this.fillByteBuf();
            return new DefaultHttpContent(byteBuf);
        }
        if (this.currentData != null) {
            httpContent = this.isMultipart ? this.encodeNextChunkMultipart(n) : this.encodeNextChunkUrlEncoded(n);
            if (httpContent != null) {
                return httpContent;
            }
            n = this.calculateRemainingSize();
        }
        if (!this.iterator.hasNext()) {
            return this.lastChunk();
        }
        while (n > 0 && this.iterator.hasNext()) {
            this.currentData = this.iterator.next();
            httpContent = this.isMultipart ? this.encodeNextChunkMultipart(n) : this.encodeNextChunkUrlEncoded(n);
            if (httpContent == null) {
                n = this.calculateRemainingSize();
                continue;
            }
            return httpContent;
        }
        return this.lastChunk();
    }

    private int calculateRemainingSize() {
        int n = 8096;
        if (this.currentBuffer != null) {
            n -= this.currentBuffer.readableBytes();
        }
        return n;
    }

    private HttpContent lastChunk() {
        this.isLastChunk = true;
        if (this.currentBuffer == null) {
            this.isLastChunkSent = true;
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        ByteBuf byteBuf = this.currentBuffer;
        this.currentBuffer = null;
        return new DefaultHttpContent(byteBuf);
    }

    @Override
    public boolean isEndOfInput() throws Exception {
        return this.isLastChunkSent;
    }

    @Override
    public long length() {
        return this.isMultipart ? this.globalBodySize : this.globalBodySize - 1L;
    }

    @Override
    public long progress() {
        return this.globalProgress;
    }

    @Override
    public Object readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        return this.readChunk(byteBufAllocator);
    }

    @Override
    @Deprecated
    public Object readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class WrappedFullHttpRequest
    extends WrappedHttpRequest
    implements FullHttpRequest {
        private final HttpContent content;

        private WrappedFullHttpRequest(HttpRequest httpRequest, HttpContent httpContent) {
            super(httpRequest);
            this.content = httpContent;
        }

        @Override
        public FullHttpRequest setProtocolVersion(HttpVersion httpVersion) {
            super.setProtocolVersion(httpVersion);
            return this;
        }

        @Override
        public FullHttpRequest setMethod(HttpMethod httpMethod) {
            super.setMethod(httpMethod);
            return this;
        }

        @Override
        public FullHttpRequest setUri(String string) {
            super.setUri(string);
            return this;
        }

        @Override
        public FullHttpRequest copy() {
            return this.replace(this.content().copy());
        }

        @Override
        public FullHttpRequest duplicate() {
            return this.replace(this.content().duplicate());
        }

        @Override
        public FullHttpRequest retainedDuplicate() {
            return this.replace(this.content().retainedDuplicate());
        }

        @Override
        public FullHttpRequest replace(ByteBuf byteBuf) {
            DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.protocolVersion(), this.method(), this.uri(), byteBuf);
            defaultFullHttpRequest.headers().set(this.headers());
            defaultFullHttpRequest.trailingHeaders().set(this.trailingHeaders());
            return defaultFullHttpRequest;
        }

        @Override
        public FullHttpRequest retain(int n) {
            this.content.retain(n);
            return this;
        }

        @Override
        public FullHttpRequest retain() {
            this.content.retain();
            return this;
        }

        @Override
        public FullHttpRequest touch() {
            this.content.touch();
            return this;
        }

        @Override
        public FullHttpRequest touch(Object object) {
            this.content.touch(object);
            return this;
        }

        @Override
        public ByteBuf content() {
            return this.content.content();
        }

        @Override
        public HttpHeaders trailingHeaders() {
            if (this.content instanceof LastHttpContent) {
                return ((LastHttpContent)this.content).trailingHeaders();
            }
            return EmptyHttpHeaders.INSTANCE;
        }

        @Override
        public int refCnt() {
            return this.content.refCnt();
        }

        @Override
        public boolean release() {
            return this.content.release();
        }

        @Override
        public boolean release(int n) {
            return this.content.release(n);
        }

        @Override
        public HttpRequest setUri(String string) {
            return this.setUri(string);
        }

        @Override
        public HttpRequest setMethod(HttpMethod httpMethod) {
            return this.setMethod(httpMethod);
        }

        @Override
        public HttpRequest setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public FullHttpMessage touch(Object object) {
            return this.touch(object);
        }

        @Override
        public FullHttpMessage touch() {
            return this.touch();
        }

        @Override
        public FullHttpMessage retain() {
            return this.retain();
        }

        @Override
        public FullHttpMessage retain(int n) {
            return this.retain(n);
        }

        @Override
        public FullHttpMessage replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public FullHttpMessage retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public FullHttpMessage duplicate() {
            return this.duplicate();
        }

        @Override
        public FullHttpMessage copy() {
            return this.copy();
        }

        @Override
        public LastHttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public LastHttpContent touch() {
            return this.touch();
        }

        @Override
        public LastHttpContent retain() {
            return this.retain();
        }

        @Override
        public LastHttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public LastHttpContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public LastHttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public LastHttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public LastHttpContent copy() {
            return this.copy();
        }

        @Override
        public HttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public HttpContent touch() {
            return this.touch();
        }

        @Override
        public HttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public HttpContent retain() {
            return this.retain();
        }

        @Override
        public HttpContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public HttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public HttpContent copy() {
            return this.copy();
        }

        @Override
        public ByteBufHolder touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ByteBufHolder touch() {
            return this.touch();
        }

        @Override
        public ByteBufHolder retain(int n) {
            return this.retain(n);
        }

        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }

        @Override
        public ByteBufHolder replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public ByteBufHolder retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }

        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }

        @Override
        public ReferenceCounted touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ReferenceCounted touch() {
            return this.touch();
        }

        @Override
        public ReferenceCounted retain(int n) {
            return this.retain(n);
        }

        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }

        WrappedFullHttpRequest(HttpRequest httpRequest, HttpContent httpContent, 1 var3_3) {
            this(httpRequest, httpContent);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class WrappedHttpRequest
    implements HttpRequest {
        private final HttpRequest request;

        WrappedHttpRequest(HttpRequest httpRequest) {
            this.request = httpRequest;
        }

        @Override
        public HttpRequest setProtocolVersion(HttpVersion httpVersion) {
            this.request.setProtocolVersion(httpVersion);
            return this;
        }

        @Override
        public HttpRequest setMethod(HttpMethod httpMethod) {
            this.request.setMethod(httpMethod);
            return this;
        }

        @Override
        public HttpRequest setUri(String string) {
            this.request.setUri(string);
            return this;
        }

        @Override
        public HttpMethod getMethod() {
            return this.request.method();
        }

        @Override
        public HttpMethod method() {
            return this.request.method();
        }

        @Override
        public String getUri() {
            return this.request.uri();
        }

        @Override
        public String uri() {
            return this.request.uri();
        }

        @Override
        public HttpVersion getProtocolVersion() {
            return this.request.protocolVersion();
        }

        @Override
        public HttpVersion protocolVersion() {
            return this.request.protocolVersion();
        }

        @Override
        public HttpHeaders headers() {
            return this.request.headers();
        }

        @Override
        public DecoderResult decoderResult() {
            return this.request.decoderResult();
        }

        @Override
        @Deprecated
        public DecoderResult getDecoderResult() {
            return this.request.getDecoderResult();
        }

        @Override
        public void setDecoderResult(DecoderResult decoderResult) {
            this.request.setDecoderResult(decoderResult);
        }

        @Override
        public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }
    }

    public static class ErrorDataEncoderException
    extends Exception {
        private static final long serialVersionUID = 5020247425493164465L;

        public ErrorDataEncoderException() {
        }

        public ErrorDataEncoderException(String string) {
            super(string);
        }

        public ErrorDataEncoderException(Throwable throwable) {
            super(throwable);
        }

        public ErrorDataEncoderException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    public static enum EncoderMode {
        RFC1738,
        RFC3986,
        HTML5;

    }
}

