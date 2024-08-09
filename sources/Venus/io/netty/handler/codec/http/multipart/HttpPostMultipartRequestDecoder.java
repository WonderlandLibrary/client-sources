/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.CaseIgnoringComparator;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HttpPostMultipartRequestDecoder
implements InterfaceHttpPostRequestDecoder {
    private final HttpDataFactory factory;
    private final HttpRequest request;
    private Charset charset;
    private boolean isLastChunk;
    private final List<InterfaceHttpData> bodyListHttpData = new ArrayList<InterfaceHttpData>();
    private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap<CharSequence, List<InterfaceHttpData>>(CaseIgnoringComparator.INSTANCE);
    private ByteBuf undecodedChunk;
    private int bodyListHttpDataRank;
    private String multipartDataBoundary;
    private String multipartMixedBoundary;
    private HttpPostRequestDecoder.MultiPartStatus currentStatus = HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED;
    private Map<CharSequence, Attribute> currentFieldAttributes;
    private FileUpload currentFileUpload;
    private Attribute currentAttribute;
    private boolean destroyed;
    private int discardThreshold = 0xA00000;
    private static final String FILENAME_ENCODED = HttpHeaderValues.FILENAME.toString() + '*';

    public HttpPostMultipartRequestDecoder(HttpRequest httpRequest) {
        this(new DefaultHttpDataFactory(16384L), httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest) {
        this(httpDataFactory, httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, Charset charset) {
        this.request = ObjectUtil.checkNotNull(httpRequest, "request");
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
        this.factory = ObjectUtil.checkNotNull(httpDataFactory, "factory");
        this.setMultipart(this.request.headers().get(HttpHeaderNames.CONTENT_TYPE));
        if (httpRequest instanceof HttpContent) {
            this.offer((HttpContent)((Object)httpRequest));
        } else {
            this.undecodedChunk = Unpooled.buffer();
            this.parseBody();
        }
    }

    private void setMultipart(String string) {
        String[] stringArray = HttpPostRequestDecoder.getMultipartDataBoundary(string);
        if (stringArray != null) {
            this.multipartDataBoundary = stringArray[0];
            if (stringArray.length > 1 && stringArray[5] != null) {
                this.charset = Charset.forName(stringArray[5]);
            }
        } else {
            this.multipartDataBoundary = null;
        }
        this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
    }

    private void checkDestroyed() {
        if (this.destroyed) {
            throw new IllegalStateException(HttpPostMultipartRequestDecoder.class.getSimpleName() + " was destroyed already");
        }
    }

    @Override
    public boolean isMultipart() {
        this.checkDestroyed();
        return false;
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
    public HttpPostMultipartRequestDecoder offer(HttpContent httpContent) {
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
        if (this.currentFileUpload != null) {
            return this.currentFileUpload;
        }
        return this.currentAttribute;
    }

    private void parseBody() {
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE || this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
            if (this.isLastChunk) {
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
            }
            return;
        }
        this.parseBodyMultipart();
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

    private void parseBodyMultipart() {
        if (this.undecodedChunk == null || this.undecodedChunk.readableBytes() == 0) {
            return;
        }
        InterfaceHttpData interfaceHttpData = this.decodeMultipart(this.currentStatus);
        while (interfaceHttpData != null) {
            this.addHttpData(interfaceHttpData);
            if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE || this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) break;
            interfaceHttpData = this.decodeMultipart(this.currentStatus);
        }
    }

    private InterfaceHttpData decodeMultipart(HttpPostRequestDecoder.MultiPartStatus multiPartStatus) {
        switch (1.$SwitchMap$io$netty$handler$codec$http$multipart$HttpPostRequestDecoder$MultiPartStatus[multiPartStatus.ordinal()]) {
            case 1: {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
            }
            case 2: {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
            }
            case 3: {
                return this.findMultipartDelimiter(this.multipartDataBoundary, HttpPostRequestDecoder.MultiPartStatus.DISPOSITION, HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE);
            }
            case 4: {
                return this.findMultipartDisposition();
            }
            case 5: {
                Attribute attribute;
                Charset charset = null;
                Attribute attribute2 = this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
                if (attribute2 != null) {
                    try {
                        charset = Charset.forName(attribute2.getValue());
                    } catch (IOException iOException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
                    } catch (UnsupportedCharsetException unsupportedCharsetException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(unsupportedCharsetException);
                    }
                }
                Attribute attribute3 = this.currentFieldAttributes.get(HttpHeaderValues.NAME);
                if (this.currentAttribute == null) {
                    long l;
                    attribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
                    try {
                        l = attribute != null ? Long.parseLong(attribute.getValue()) : 0L;
                    } catch (IOException iOException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
                    } catch (NumberFormatException numberFormatException) {
                        l = 0L;
                    }
                    try {
                        this.currentAttribute = l > 0L ? this.factory.createAttribute(this.request, HttpPostMultipartRequestDecoder.cleanString(attribute3.getValue()), l) : this.factory.createAttribute(this.request, HttpPostMultipartRequestDecoder.cleanString(attribute3.getValue()));
                    } catch (NullPointerException nullPointerException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(nullPointerException);
                    } catch (IllegalArgumentException illegalArgumentException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
                    } catch (IOException iOException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
                    }
                    if (charset != null) {
                        this.currentAttribute.setCharset(charset);
                    }
                }
                if (!HttpPostMultipartRequestDecoder.loadDataMultipart(this.undecodedChunk, this.multipartDataBoundary, this.currentAttribute)) {
                    return null;
                }
                attribute = this.currentAttribute;
                this.currentAttribute = null;
                this.currentFieldAttributes = null;
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
                return attribute;
            }
            case 6: {
                return this.getFileUpload(this.multipartDataBoundary);
            }
            case 7: {
                return this.findMultipartDelimiter(this.multipartMixedBoundary, HttpPostRequestDecoder.MultiPartStatus.MIXEDDISPOSITION, HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
            }
            case 8: {
                return this.findMultipartDisposition();
            }
            case 9: {
                return this.getFileUpload(this.multipartMixedBoundary);
            }
            case 10: {
                return null;
            }
            case 11: {
                return null;
            }
        }
        throw new HttpPostRequestDecoder.ErrorDataDecoderException("Shouldn't reach here.");
    }

    private static void skipControlCharacters(ByteBuf byteBuf) {
        if (!byteBuf.hasArray()) {
            try {
                HttpPostMultipartRequestDecoder.skipControlCharactersStandard(byteBuf);
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(indexOutOfBoundsException);
            }
            return;
        }
        HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(byteBuf);
        while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            char c;
            if (Character.isISOControl(c = (char)(seekAheadOptimize.bytes[seekAheadOptimize.pos++] & 0xFF)) || Character.isWhitespace(c)) continue;
            seekAheadOptimize.setReadPosition(1);
            return;
        }
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException("Access out of bounds");
    }

    private static void skipControlCharactersStandard(ByteBuf byteBuf) {
        char c;
        while (Character.isISOControl(c = (char)byteBuf.readUnsignedByte()) || Character.isWhitespace(c)) {
        }
        byteBuf.readerIndex(byteBuf.readerIndex() - 1);
    }

    private InterfaceHttpData findMultipartDelimiter(String string, HttpPostRequestDecoder.MultiPartStatus multiPartStatus, HttpPostRequestDecoder.MultiPartStatus multiPartStatus2) {
        String string2;
        int n = this.undecodedChunk.readerIndex();
        try {
            HttpPostMultipartRequestDecoder.skipControlCharacters(this.undecodedChunk);
        } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException notEnoughDataDecoderException) {
            this.undecodedChunk.readerIndex(n);
            return null;
        }
        this.skipOneLine();
        try {
            string2 = HttpPostMultipartRequestDecoder.readDelimiter(this.undecodedChunk, string);
        } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException notEnoughDataDecoderException) {
            this.undecodedChunk.readerIndex(n);
            return null;
        }
        if (string2.equals(string)) {
            this.currentStatus = multiPartStatus;
            return this.decodeMultipart(multiPartStatus);
        }
        if (string2.equals(string + "--")) {
            this.currentStatus = multiPartStatus2;
            if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER) {
                this.currentFieldAttributes = null;
                return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
            }
            return null;
        }
        this.undecodedChunk.readerIndex(n);
        throw new HttpPostRequestDecoder.ErrorDataDecoderException("No Multipart delimiter found");
    }

    private InterfaceHttpData findMultipartDisposition() {
        Object object;
        int n = this.undecodedChunk.readerIndex();
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
            this.currentFieldAttributes = new TreeMap<CharSequence, Attribute>(CaseIgnoringComparator.INSTANCE);
        }
        while (!this.skipOneLine()) {
            Attribute attribute;
            Object object2;
            try {
                HttpPostMultipartRequestDecoder.skipControlCharacters(this.undecodedChunk);
                object = HttpPostMultipartRequestDecoder.readLine(this.undecodedChunk, this.charset);
            } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException notEnoughDataDecoderException) {
                this.undecodedChunk.readerIndex(n);
                return null;
            }
            String[] stringArray = HttpPostMultipartRequestDecoder.splitMultipartHeader((String)object);
            if (HttpHeaderNames.CONTENT_DISPOSITION.contentEqualsIgnoreCase(stringArray[0])) {
                boolean bl;
                if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
                    bl = HttpHeaderValues.FORM_DATA.contentEqualsIgnoreCase(stringArray[5]);
                } else {
                    boolean bl2 = bl = HttpHeaderValues.ATTACHMENT.contentEqualsIgnoreCase(stringArray[5]) || HttpHeaderValues.FILE.contentEqualsIgnoreCase(stringArray[5]);
                }
                if (!bl) continue;
                for (int i = 2; i < stringArray.length; ++i) {
                    object2 = stringArray[i].split("=", 2);
                    try {
                        attribute = this.getContentDispositionAttribute((String[])object2);
                    } catch (NullPointerException nullPointerException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(nullPointerException);
                    } catch (IllegalArgumentException illegalArgumentException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
                    }
                    this.currentFieldAttributes.put(attribute.getName(), attribute);
                }
                continue;
            }
            if (HttpHeaderNames.CONTENT_TRANSFER_ENCODING.contentEqualsIgnoreCase(stringArray[0])) {
                Attribute attribute2;
                try {
                    attribute2 = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_TRANSFER_ENCODING.toString(), HttpPostMultipartRequestDecoder.cleanString(stringArray[5]));
                } catch (NullPointerException nullPointerException) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(nullPointerException);
                } catch (IllegalArgumentException illegalArgumentException) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
                }
                this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_TRANSFER_ENCODING, attribute2);
                continue;
            }
            if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(stringArray[0])) {
                Attribute attribute3;
                try {
                    attribute3 = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_LENGTH.toString(), HttpPostMultipartRequestDecoder.cleanString(stringArray[5]));
                } catch (NullPointerException nullPointerException) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(nullPointerException);
                } catch (IllegalArgumentException illegalArgumentException) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
                }
                this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_LENGTH, attribute3);
                continue;
            }
            if (HttpHeaderNames.CONTENT_TYPE.contentEqualsIgnoreCase(stringArray[0])) {
                if (HttpHeaderValues.MULTIPART_MIXED.contentEqualsIgnoreCase(stringArray[5])) {
                    if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
                        String string = StringUtil.substringAfter(stringArray[5], '=');
                        this.multipartMixedBoundary = "--" + string;
                        this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER;
                        return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER);
                    }
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
                }
                for (int i = 1; i < stringArray.length; ++i) {
                    String string = HttpHeaderValues.CHARSET.toString();
                    if (stringArray[i].regionMatches(true, 0, string, 0, string.length())) {
                        object2 = StringUtil.substringAfter(stringArray[i], '=');
                        try {
                            attribute = this.factory.createAttribute(this.request, string, HttpPostMultipartRequestDecoder.cleanString((String)object2));
                        } catch (NullPointerException nullPointerException) {
                            throw new HttpPostRequestDecoder.ErrorDataDecoderException(nullPointerException);
                        } catch (IllegalArgumentException illegalArgumentException) {
                            throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
                        }
                        this.currentFieldAttributes.put(HttpHeaderValues.CHARSET, attribute);
                        continue;
                    }
                    try {
                        object2 = this.factory.createAttribute(this.request, HttpPostMultipartRequestDecoder.cleanString(stringArray[0]), stringArray[i]);
                    } catch (NullPointerException nullPointerException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(nullPointerException);
                    } catch (IllegalArgumentException illegalArgumentException) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
                    }
                    this.currentFieldAttributes.put(object2.getName(), (Attribute)object2);
                }
                continue;
            }
            throw new HttpPostRequestDecoder.ErrorDataDecoderException("Unknown Params: " + (String)object);
        }
        object = this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
            if (object != null) {
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD;
                return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD);
            }
            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FIELD;
            return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.FIELD);
        }
        if (object != null) {
            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD;
            return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD);
        }
        throw new HttpPostRequestDecoder.ErrorDataDecoderException("Filename not found");
    }

    private Attribute getContentDispositionAttribute(String ... stringArray) {
        String string = HttpPostMultipartRequestDecoder.cleanString(stringArray[0]);
        String string2 = stringArray[5];
        if (HttpHeaderValues.FILENAME.contentEquals(string)) {
            int n = string2.length() - 1;
            if (n > 0 && string2.charAt(0) == '\"' && string2.charAt(n) == '\"') {
                string2 = string2.substring(1, n);
            }
        } else if (FILENAME_ENCODED.equals(string)) {
            try {
                string = HttpHeaderValues.FILENAME.toString();
                String[] stringArray2 = string2.split("'", 3);
                string2 = QueryStringDecoder.decodeComponent(stringArray2[0], Charset.forName(stringArray2[5]));
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(arrayIndexOutOfBoundsException);
            } catch (UnsupportedCharsetException unsupportedCharsetException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(unsupportedCharsetException);
            }
        } else {
            string2 = HttpPostMultipartRequestDecoder.cleanString(string2);
        }
        return this.factory.createAttribute(this.request, string, string2);
    }

    protected InterfaceHttpData getFileUpload(String string) {
        HttpData httpData;
        Object object;
        Attribute attribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        Charset charset = this.charset;
        HttpPostBodyUtil.TransferEncodingMechanism transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
        if (attribute != null) {
            try {
                object = attribute.getValue().toLowerCase();
            } catch (IOException iOException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
            }
            if (((String)object).equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
                charset = CharsetUtil.US_ASCII;
            } else if (((String)object).equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
                charset = CharsetUtil.ISO_8859_1;
                transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
            } else if (((String)object).equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
            } else {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("TransferEncoding Unknown: " + (String)object);
            }
        }
        if ((object = this.currentFieldAttributes.get(HttpHeaderValues.CHARSET)) != null) {
            try {
                charset = Charset.forName(object.getValue());
            } catch (IOException iOException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
            } catch (UnsupportedCharsetException unsupportedCharsetException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(unsupportedCharsetException);
            }
        }
        if (this.currentFileUpload == null) {
            long l;
            httpData = this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
            Attribute attribute2 = this.currentFieldAttributes.get(HttpHeaderValues.NAME);
            Attribute attribute3 = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TYPE);
            Attribute attribute4 = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
            try {
                l = attribute4 != null ? Long.parseLong(attribute4.getValue()) : 0L;
            } catch (IOException iOException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
            } catch (NumberFormatException numberFormatException) {
                l = 0L;
            }
            try {
                String string2 = attribute3 != null ? attribute3.getValue() : "application/octet-stream";
                this.currentFileUpload = this.factory.createFileUpload(this.request, HttpPostMultipartRequestDecoder.cleanString(attribute2.getValue()), HttpPostMultipartRequestDecoder.cleanString(httpData.getValue()), string2, transferEncodingMechanism.value(), charset, l);
            } catch (NullPointerException nullPointerException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(nullPointerException);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(illegalArgumentException);
            } catch (IOException iOException) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
            }
        }
        if (!HttpPostMultipartRequestDecoder.loadDataMultipart(this.undecodedChunk, string, this.currentFileUpload)) {
            return null;
        }
        if (this.currentFileUpload.isCompleted()) {
            if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD) {
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
                this.currentFieldAttributes = null;
            } else {
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER;
                this.cleanMixedAttributes();
            }
            httpData = this.currentFileUpload;
            this.currentFileUpload = null;
            return httpData;
        }
        return null;
    }

    @Override
    public void destroy() {
        this.checkDestroyed();
        this.cleanFiles();
        this.destroyed = true;
        if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
            this.undecodedChunk.release();
            this.undecodedChunk = null;
        }
        for (int i = this.bodyListHttpDataRank; i < this.bodyListHttpData.size(); ++i) {
            this.bodyListHttpData.get(i).release();
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

    private void cleanMixedAttributes() {
        this.currentFieldAttributes.remove(HttpHeaderValues.CHARSET);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_LENGTH);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TYPE);
        this.currentFieldAttributes.remove(HttpHeaderValues.FILENAME);
    }

    private static String readLineStandard(ByteBuf byteBuf, Charset charset) {
        int n = byteBuf.readerIndex();
        try {
            ByteBuf byteBuf2 = Unpooled.buffer(64);
            while (byteBuf.isReadable()) {
                byte by = byteBuf.readByte();
                if (by == 13) {
                    by = byteBuf.getByte(byteBuf.readerIndex());
                    if (by == 10) {
                        byteBuf.readByte();
                        return byteBuf2.toString(charset);
                    }
                    byteBuf2.writeByte(13);
                    continue;
                }
                if (by == 10) {
                    return byteBuf2.toString(charset);
                }
                byteBuf2.writeByte(by);
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            byteBuf.readerIndex(n);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(indexOutOfBoundsException);
        }
        byteBuf.readerIndex(n);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private static String readLine(ByteBuf byteBuf, Charset charset) {
        if (!byteBuf.hasArray()) {
            return HttpPostMultipartRequestDecoder.readLineStandard(byteBuf, charset);
        }
        HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(byteBuf);
        int n = byteBuf.readerIndex();
        try {
            ByteBuf byteBuf2 = Unpooled.buffer(64);
            while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                byte by;
                if ((by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == 13) {
                    if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                        if ((by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == 10) {
                            seekAheadOptimize.setReadPosition(0);
                            return byteBuf2.toString(charset);
                        }
                        --seekAheadOptimize.pos;
                        byteBuf2.writeByte(13);
                        continue;
                    }
                    byteBuf2.writeByte(by);
                    continue;
                }
                if (by == 10) {
                    seekAheadOptimize.setReadPosition(0);
                    return byteBuf2.toString(charset);
                }
                byteBuf2.writeByte(by);
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            byteBuf.readerIndex(n);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(indexOutOfBoundsException);
        }
        byteBuf.readerIndex(n);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private static String readDelimiterStandard(ByteBuf byteBuf, String string) {
        int n = byteBuf.readerIndex();
        try {
            byte by;
            StringBuilder stringBuilder = new StringBuilder(64);
            int n2 = string.length();
            for (int i = 0; byteBuf.isReadable() && i < n2; ++i) {
                by = byteBuf.readByte();
                if (by == string.charAt(i)) {
                    stringBuilder.append((char)by);
                    continue;
                }
                byteBuf.readerIndex(n);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            }
            if (byteBuf.isReadable()) {
                by = byteBuf.readByte();
                if (by == 13) {
                    by = byteBuf.readByte();
                    if (by == 10) {
                        return stringBuilder.toString();
                    }
                    byteBuf.readerIndex(n);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                }
                if (by == 10) {
                    return stringBuilder.toString();
                }
                if (by == 45) {
                    stringBuilder.append('-');
                    by = byteBuf.readByte();
                    if (by == 45) {
                        stringBuilder.append('-');
                        if (byteBuf.isReadable()) {
                            by = byteBuf.readByte();
                            if (by == 13) {
                                by = byteBuf.readByte();
                                if (by == 10) {
                                    return stringBuilder.toString();
                                }
                                byteBuf.readerIndex(n);
                                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                            }
                            if (by == 10) {
                                return stringBuilder.toString();
                            }
                            byteBuf.readerIndex(byteBuf.readerIndex() - 1);
                            return stringBuilder.toString();
                        }
                        return stringBuilder.toString();
                    }
                }
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            byteBuf.readerIndex(n);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(indexOutOfBoundsException);
        }
        byteBuf.readerIndex(n);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private static String readDelimiter(ByteBuf byteBuf, String string) {
        if (!byteBuf.hasArray()) {
            return HttpPostMultipartRequestDecoder.readDelimiterStandard(byteBuf, string);
        }
        HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(byteBuf);
        int n = byteBuf.readerIndex();
        int n2 = string.length();
        try {
            byte by;
            StringBuilder stringBuilder = new StringBuilder(64);
            for (int i = 0; seekAheadOptimize.pos < seekAheadOptimize.limit && i < n2; ++i) {
                if ((by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == string.charAt(i)) {
                    stringBuilder.append((char)by);
                    continue;
                }
                byteBuf.readerIndex(n);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            }
            if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                if ((by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == 13) {
                    if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                        if ((by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == 10) {
                            seekAheadOptimize.setReadPosition(0);
                            return stringBuilder.toString();
                        }
                        byteBuf.readerIndex(n);
                        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                    }
                    byteBuf.readerIndex(n);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                }
                if (by == 10) {
                    seekAheadOptimize.setReadPosition(0);
                    return stringBuilder.toString();
                }
                if (by == 45) {
                    stringBuilder.append('-');
                    if (seekAheadOptimize.pos < seekAheadOptimize.limit && (by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == 45) {
                        stringBuilder.append('-');
                        if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                            if ((by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == 13) {
                                if (seekAheadOptimize.pos < seekAheadOptimize.limit) {
                                    if ((by = seekAheadOptimize.bytes[seekAheadOptimize.pos++]) == 10) {
                                        seekAheadOptimize.setReadPosition(0);
                                        return stringBuilder.toString();
                                    }
                                    byteBuf.readerIndex(n);
                                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                                }
                                byteBuf.readerIndex(n);
                                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                            }
                            if (by == 10) {
                                seekAheadOptimize.setReadPosition(0);
                                return stringBuilder.toString();
                            }
                            seekAheadOptimize.setReadPosition(1);
                            return stringBuilder.toString();
                        }
                        seekAheadOptimize.setReadPosition(0);
                        return stringBuilder.toString();
                    }
                }
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            byteBuf.readerIndex(n);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(indexOutOfBoundsException);
        }
        byteBuf.readerIndex(n);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private static boolean loadDataMultipartStandard(ByteBuf byteBuf, String string, HttpData httpData) {
        int n = byteBuf.readerIndex();
        int n2 = string.length();
        int n3 = 0;
        int n4 = n;
        int n5 = 10;
        boolean bl = false;
        while (byteBuf.isReadable()) {
            byte by = byteBuf.readByte();
            if (n5 == 10 && by == string.codePointAt(n3)) {
                if (n2 != ++n3) continue;
                bl = true;
                break;
            }
            n4 = byteBuf.readerIndex();
            if (by == 10) {
                n3 = 0;
                n4 -= n5 == 13 ? 2 : 1;
            }
            n5 = by;
        }
        if (n5 == 13) {
            --n4;
        }
        ByteBuf byteBuf2 = byteBuf.copy(n, n4 - n);
        try {
            httpData.addContent(byteBuf2, bl);
        } catch (IOException iOException) {
            throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
        }
        byteBuf.readerIndex(n4);
        return bl;
    }

    private static boolean loadDataMultipart(ByteBuf byteBuf, String string, HttpData httpData) {
        int n;
        if (!byteBuf.hasArray()) {
            return HttpPostMultipartRequestDecoder.loadDataMultipartStandard(byteBuf, string, httpData);
        }
        HttpPostBodyUtil.SeekAheadOptimize seekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(byteBuf);
        int n2 = byteBuf.readerIndex();
        int n3 = string.length();
        int n4 = 0;
        int n5 = seekAheadOptimize.pos;
        int n6 = 10;
        boolean bl = false;
        while (seekAheadOptimize.pos < seekAheadOptimize.limit) {
            n = seekAheadOptimize.bytes[seekAheadOptimize.pos++];
            if (n6 == 10 && n == string.codePointAt(n4)) {
                if (n3 != ++n4) continue;
                bl = true;
                break;
            }
            n5 = seekAheadOptimize.pos;
            if (n == 10) {
                n4 = 0;
                n5 -= n6 == 13 ? 2 : 1;
            }
            n6 = n;
        }
        if (n6 == 13) {
            --n5;
        }
        n = seekAheadOptimize.getReadPosition(n5);
        ByteBuf byteBuf2 = byteBuf.copy(n2, n - n2);
        try {
            httpData.addContent(byteBuf2, bl);
        } catch (IOException iOException) {
            throw new HttpPostRequestDecoder.ErrorDataDecoderException(iOException);
        }
        byteBuf.readerIndex(n);
        return bl;
    }

    private static String cleanString(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        block4: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\t': 
                case ',': 
                case ':': 
                case ';': 
                case '=': {
                    stringBuilder.append(' ');
                    continue block4;
                }
                case '\"': {
                    continue block4;
                }
                default: {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString().trim();
    }

    private boolean skipOneLine() {
        if (!this.undecodedChunk.isReadable()) {
            return true;
        }
        byte by = this.undecodedChunk.readByte();
        if (by == 13) {
            if (!this.undecodedChunk.isReadable()) {
                this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
                return true;
            }
            by = this.undecodedChunk.readByte();
            if (by == 10) {
                return false;
            }
            this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
            return true;
        }
        if (by == 10) {
            return false;
        }
        this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
        return true;
    }

    private static String[] splitMultipartHeader(String string) {
        int n;
        int n2;
        char c;
        int n3;
        ArrayList<String> arrayList = new ArrayList<String>(1);
        for (n3 = n2 = HttpPostBodyUtil.findNonWhitespace(string, 0); n3 < string.length() && (c = string.charAt(n3)) != ':' && !Character.isWhitespace(c); ++n3) {
        }
        for (n = n3; n < string.length(); ++n) {
            if (string.charAt(n) != ':') continue;
            ++n;
            break;
        }
        int n4 = HttpPostBodyUtil.findNonWhitespace(string, n);
        int n5 = HttpPostBodyUtil.findEndOfString(string);
        arrayList.add(string.substring(n2, n3));
        String string2 = n4 >= n5 ? "" : string.substring(n4, n5);
        String[] stringArray = string2.indexOf(59) >= 0 ? HttpPostMultipartRequestDecoder.splitMultipartHeaderValues(string2) : string2.split(",");
        for (String string3 : stringArray) {
            arrayList.add(string3.trim());
        }
        String[] stringArray2 = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); ++i) {
            stringArray2[i] = (String)arrayList.get(i);
        }
        return stringArray2;
    }

    private static String[] splitMultipartHeaderValues(String string) {
        ArrayList<String> arrayList = InternalThreadLocalMap.get().arrayList(1);
        boolean bl = false;
        boolean bl2 = false;
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (bl) {
                if (bl2) {
                    bl2 = false;
                    continue;
                }
                if (c == '\\') {
                    bl2 = true;
                    continue;
                }
                if (c != '\"') continue;
                bl = false;
                continue;
            }
            if (c == '\"') {
                bl = true;
                continue;
            }
            if (c != ';') continue;
            arrayList.add(string.substring(n, i));
            n = i + 1;
        }
        arrayList.add(string.substring(n));
        return arrayList.toArray(new String[arrayList.size()]);
    }

    @Override
    public InterfaceHttpPostRequestDecoder offer(HttpContent httpContent) {
        return this.offer(httpContent);
    }
}

