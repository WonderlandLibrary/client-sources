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
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.CaseIgnoringComparator;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    public HttpPostMultipartRequestDecoder(HttpRequest request) {
        this(new DefaultHttpDataFactory(16384L), request, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request) {
        this(factory, request, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
        if (factory == null) {
            throw new NullPointerException("factory");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.request = request;
        this.charset = charset;
        this.factory = factory;
        this.setMultipart(this.request.headers().get(HttpHeaderNames.CONTENT_TYPE));
        if (request instanceof HttpContent) {
            this.offer((HttpContent)((Object)request));
        } else {
            this.undecodedChunk = Unpooled.buffer();
            this.parseBody();
        }
    }

    private void setMultipart(String contentType) {
        String[] dataBoundary = HttpPostRequestDecoder.getMultipartDataBoundary(contentType);
        if (dataBoundary != null) {
            this.multipartDataBoundary = dataBoundary[0];
            if (dataBoundary.length > 1 && dataBoundary[1] != null) {
                this.charset = Charset.forName(dataBoundary[1]);
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
        return true;
    }

    @Override
    public void setDiscardThreshold(int discardThreshold) {
        if (discardThreshold < 0) {
            throw new IllegalArgumentException("discardThreshold must be >= 0");
        }
        this.discardThreshold = discardThreshold;
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
    public List<InterfaceHttpData> getBodyHttpDatas(String name) {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
        }
        return this.bodyMapHttpData.get(name);
    }

    @Override
    public InterfaceHttpData getBodyHttpData(String name) {
        this.checkDestroyed();
        if (!this.isLastChunk) {
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
        }
        List<InterfaceHttpData> list = this.bodyMapHttpData.get(name);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public HttpPostMultipartRequestDecoder offer(HttpContent content) {
        this.checkDestroyed();
        ByteBuf buf = content.content();
        if (this.undecodedChunk == null) {
            this.undecodedChunk = buf.copy();
        } else {
            this.undecodedChunk.writeBytes(buf);
        }
        if (content instanceof LastHttpContent) {
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

    protected void addHttpData(InterfaceHttpData data) {
        if (data == null) {
            return;
        }
        List<InterfaceHttpData> datas = this.bodyMapHttpData.get(data.getName());
        if (datas == null) {
            datas = new ArrayList<InterfaceHttpData>(1);
            this.bodyMapHttpData.put(data.getName(), datas);
        }
        datas.add(data);
        this.bodyListHttpData.add(data);
    }

    private void parseBodyMultipart() {
        if (this.undecodedChunk == null || this.undecodedChunk.readableBytes() == 0) {
            return;
        }
        InterfaceHttpData data = this.decodeMultipart(this.currentStatus);
        while (data != null) {
            this.addHttpData(data);
            if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE || this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) break;
            data = this.decodeMultipart(this.currentStatus);
        }
    }

    private InterfaceHttpData decodeMultipart(HttpPostRequestDecoder.MultiPartStatus state) {
        switch (state) {
            case NOTSTARTED: {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
            }
            case PREAMBLE: {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
            }
            case HEADERDELIMITER: {
                return this.findMultipartDelimiter(this.multipartDataBoundary, HttpPostRequestDecoder.MultiPartStatus.DISPOSITION, HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE);
            }
            case DISPOSITION: {
                return this.findMultipartDisposition();
            }
            case FIELD: {
                Charset localCharset = null;
                Attribute charsetAttribute = this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
                if (charsetAttribute != null) {
                    try {
                        localCharset = Charset.forName(charsetAttribute.getValue());
                    } catch (IOException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    } catch (UnsupportedCharsetException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    }
                }
                Attribute nameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.NAME);
                if (this.currentAttribute == null) {
                    long size;
                    Attribute lengthAttribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
                    try {
                        size = lengthAttribute != null ? Long.parseLong(lengthAttribute.getValue()) : 0L;
                    } catch (IOException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    } catch (NumberFormatException e) {
                        size = 0L;
                    }
                    try {
                        this.currentAttribute = size > 0L ? this.factory.createAttribute(this.request, HttpPostMultipartRequestDecoder.cleanString(nameAttribute.getValue()), size) : this.factory.createAttribute(this.request, HttpPostMultipartRequestDecoder.cleanString(nameAttribute.getValue()));
                    } catch (NullPointerException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    } catch (IllegalArgumentException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    } catch (IOException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    }
                    if (localCharset != null) {
                        this.currentAttribute.setCharset(localCharset);
                    }
                }
                try {
                    this.loadFieldMultipart(this.multipartDataBoundary);
                } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException ignored) {
                    return null;
                }
                Attribute finalAttribute = this.currentAttribute;
                this.currentAttribute = null;
                this.currentFieldAttributes = null;
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
                return finalAttribute;
            }
            case FILEUPLOAD: {
                return this.getFileUpload(this.multipartDataBoundary);
            }
            case MIXEDDELIMITER: {
                return this.findMultipartDelimiter(this.multipartMixedBoundary, HttpPostRequestDecoder.MultiPartStatus.MIXEDDISPOSITION, HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
            }
            case MIXEDDISPOSITION: {
                return this.findMultipartDisposition();
            }
            case MIXEDFILEUPLOAD: {
                return this.getFileUpload(this.multipartMixedBoundary);
            }
            case PREEPILOGUE: {
                return null;
            }
            case EPILOGUE: {
                return null;
            }
        }
        throw new HttpPostRequestDecoder.ErrorDataDecoderException("Shouldn't reach here.");
    }

    void skipControlCharacters() {
        HttpPostBodyUtil.SeekAheadOptimize sao;
        try {
            sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException ignored) {
            try {
                this.skipControlCharactersStandard();
            } catch (IndexOutOfBoundsException e1) {
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e1);
            }
            return;
        }
        while (sao.pos < sao.limit) {
            char c;
            if (Character.isISOControl(c = (char)(sao.bytes[sao.pos++] & 0xFF)) || Character.isWhitespace(c)) continue;
            sao.setReadPosition(1);
            return;
        }
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException("Access out of bounds");
    }

    void skipControlCharactersStandard() {
        char c;
        while (Character.isISOControl(c = (char)this.undecodedChunk.readUnsignedByte()) || Character.isWhitespace(c)) {
        }
        this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
    }

    private InterfaceHttpData findMultipartDelimiter(String delimiter, HttpPostRequestDecoder.MultiPartStatus dispositionStatus, HttpPostRequestDecoder.MultiPartStatus closeDelimiterStatus) {
        String newline;
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            this.skipControlCharacters();
        } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException ignored) {
            this.undecodedChunk.readerIndex(readerIndex);
            return null;
        }
        this.skipOneLine();
        try {
            newline = this.readDelimiter(delimiter);
        } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException ignored) {
            this.undecodedChunk.readerIndex(readerIndex);
            return null;
        }
        if (newline.equals(delimiter)) {
            this.currentStatus = dispositionStatus;
            return this.decodeMultipart(dispositionStatus);
        }
        if (newline.equals(delimiter + "--")) {
            this.currentStatus = closeDelimiterStatus;
            if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER) {
                this.currentFieldAttributes = null;
                return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
            }
            return null;
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new HttpPostRequestDecoder.ErrorDataDecoderException("No Multipart delimiter found");
    }

    private InterfaceHttpData findMultipartDisposition() {
        int readerIndex = this.undecodedChunk.readerIndex();
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
            this.currentFieldAttributes = new TreeMap<CharSequence, Attribute>(CaseIgnoringComparator.INSTANCE);
        }
        while (!this.skipOneLine()) {
            String newline;
            try {
                this.skipControlCharacters();
                newline = this.readLine();
            } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException ignored) {
                this.undecodedChunk.readerIndex(readerIndex);
                return null;
            }
            String[] contents = HttpPostMultipartRequestDecoder.splitMultipartHeader(newline);
            if (HttpHeaderNames.CONTENT_DISPOSITION.contentEqualsIgnoreCase(contents[0])) {
                boolean checkSecondArg;
                if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
                    checkSecondArg = HttpHeaderValues.FORM_DATA.contentEqualsIgnoreCase(contents[1]);
                } else {
                    boolean bl = checkSecondArg = HttpHeaderValues.ATTACHMENT.contentEqualsIgnoreCase(contents[1]) || HttpHeaderValues.FILE.contentEqualsIgnoreCase(contents[1]);
                }
                if (!checkSecondArg) continue;
                for (int i = 2; i < contents.length; ++i) {
                    Attribute attribute;
                    String[] values = contents[i].split("=", 2);
                    try {
                        String name = HttpPostMultipartRequestDecoder.cleanString(values[0]);
                        String value = values[1];
                        value = HttpHeaderValues.FILENAME.contentEquals(name) ? value.substring(1, value.length() - 1) : HttpPostMultipartRequestDecoder.cleanString(value);
                        attribute = this.factory.createAttribute(this.request, name, value);
                    } catch (NullPointerException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    } catch (IllegalArgumentException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    }
                    this.currentFieldAttributes.put(attribute.getName(), attribute);
                }
                continue;
            }
            if (HttpHeaderNames.CONTENT_TRANSFER_ENCODING.contentEqualsIgnoreCase(contents[0])) {
                Attribute attribute;
                try {
                    attribute = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_TRANSFER_ENCODING.toString(), HttpPostMultipartRequestDecoder.cleanString(contents[1]));
                } catch (NullPointerException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                } catch (IllegalArgumentException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                }
                this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_TRANSFER_ENCODING, attribute);
                continue;
            }
            if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(contents[0])) {
                Attribute attribute;
                try {
                    attribute = this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_LENGTH.toString(), HttpPostMultipartRequestDecoder.cleanString(contents[1]));
                } catch (NullPointerException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                } catch (IllegalArgumentException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                }
                this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_LENGTH, attribute);
                continue;
            }
            if (HttpHeaderNames.CONTENT_TYPE.contentEqualsIgnoreCase(contents[0])) {
                if (HttpHeaderValues.MULTIPART_MIXED.contentEqualsIgnoreCase(contents[1])) {
                    if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
                        String values = StringUtil.substringAfter(contents[2], '=');
                        this.multipartMixedBoundary = "--" + values;
                        this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER;
                        return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER);
                    }
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
                }
                for (int i = 1; i < contents.length; ++i) {
                    Attribute attribute;
                    if (contents[i].toLowerCase().startsWith(HttpHeaderValues.CHARSET.toString())) {
                        Attribute attribute2;
                        String values = StringUtil.substringAfter(contents[i], '=');
                        try {
                            attribute2 = this.factory.createAttribute(this.request, HttpHeaderValues.CHARSET.toString(), HttpPostMultipartRequestDecoder.cleanString(values));
                        } catch (NullPointerException e) {
                            throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                        } catch (IllegalArgumentException e) {
                            throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                        }
                        this.currentFieldAttributes.put(HttpHeaderValues.CHARSET, attribute2);
                        continue;
                    }
                    try {
                        attribute = this.factory.createAttribute(this.request, HttpPostMultipartRequestDecoder.cleanString(contents[0]), contents[i]);
                    } catch (NullPointerException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    } catch (IllegalArgumentException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    }
                    this.currentFieldAttributes.put(attribute.getName(), attribute);
                }
                continue;
            }
            throw new HttpPostRequestDecoder.ErrorDataDecoderException("Unknown Params: " + newline);
        }
        Attribute filenameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
        if (this.currentStatus == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
            if (filenameAttribute != null) {
                this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD;
                return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD);
            }
            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.FIELD;
            return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.FIELD);
        }
        if (filenameAttribute != null) {
            this.currentStatus = HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD;
            return this.decodeMultipart(HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD);
        }
        throw new HttpPostRequestDecoder.ErrorDataDecoderException("Filename not found");
    }

    protected InterfaceHttpData getFileUpload(String delimiter) {
        Attribute charsetAttribute;
        Attribute encoding = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        Charset localCharset = this.charset;
        HttpPostBodyUtil.TransferEncodingMechanism mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
        if (encoding != null) {
            String code;
            try {
                code = encoding.getValue().toLowerCase();
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
            if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
                localCharset = CharsetUtil.US_ASCII;
            } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
                localCharset = CharsetUtil.ISO_8859_1;
                mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
            } else if (code.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                mechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
            } else {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("TransferEncoding Unknown: " + code);
            }
        }
        if ((charsetAttribute = this.currentFieldAttributes.get(HttpHeaderValues.CHARSET)) != null) {
            try {
                localCharset = Charset.forName(charsetAttribute.getValue());
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            } catch (UnsupportedCharsetException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        }
        if (this.currentFileUpload == null) {
            long size;
            Attribute filenameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
            Attribute nameAttribute = this.currentFieldAttributes.get(HttpHeaderValues.NAME);
            Attribute contentTypeAttribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TYPE);
            Attribute lengthAttribute = this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
            try {
                size = lengthAttribute != null ? Long.parseLong(lengthAttribute.getValue()) : 0L;
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            } catch (NumberFormatException ignored) {
                size = 0L;
            }
            try {
                String contentType = contentTypeAttribute != null ? contentTypeAttribute.getValue() : "application/octet-stream";
                this.currentFileUpload = this.factory.createFileUpload(this.request, HttpPostMultipartRequestDecoder.cleanString(nameAttribute.getValue()), HttpPostMultipartRequestDecoder.cleanString(filenameAttribute.getValue()), contentType, mechanism.value(), localCharset, size);
            } catch (NullPointerException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            } catch (IllegalArgumentException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        }
        try {
            this.readFileUploadByteMultipart(delimiter);
        } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException e) {
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
            FileUpload fileUpload = this.currentFileUpload;
            this.currentFileUpload = null;
            return fileUpload;
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
    public void removeHttpDataFromClean(InterfaceHttpData data) {
        this.checkDestroyed();
        this.factory.removeHttpDataFromClean(this.request, data);
    }

    private void cleanMixedAttributes() {
        this.currentFieldAttributes.remove(HttpHeaderValues.CHARSET);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_LENGTH);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TYPE);
        this.currentFieldAttributes.remove(HttpHeaderValues.FILENAME);
    }

    private String readLineStandard() {
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            ByteBuf line = Unpooled.buffer(64);
            while (this.undecodedChunk.isReadable()) {
                byte nextByte = this.undecodedChunk.readByte();
                if (nextByte == 13) {
                    nextByte = this.undecodedChunk.getByte(this.undecodedChunk.readerIndex());
                    if (nextByte == 10) {
                        this.undecodedChunk.readByte();
                        return line.toString(this.charset);
                    }
                    line.writeByte(13);
                    continue;
                }
                if (nextByte == 10) {
                    return line.toString(this.charset);
                }
                line.writeByte(nextByte);
            }
        } catch (IndexOutOfBoundsException e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private String readLine() {
        HttpPostBodyUtil.SeekAheadOptimize sao;
        try {
            sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException ignored) {
            return this.readLineStandard();
        }
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            ByteBuf line = Unpooled.buffer(64);
            while (sao.pos < sao.limit) {
                byte nextByte;
                if ((nextByte = sao.bytes[sao.pos++]) == 13) {
                    if (sao.pos < sao.limit) {
                        if ((nextByte = sao.bytes[sao.pos++]) == 10) {
                            sao.setReadPosition(0);
                            return line.toString(this.charset);
                        }
                        --sao.pos;
                        line.writeByte(13);
                        continue;
                    }
                    line.writeByte(nextByte);
                    continue;
                }
                if (nextByte == 10) {
                    sao.setReadPosition(0);
                    return line.toString(this.charset);
                }
                line.writeByte(nextByte);
            }
        } catch (IndexOutOfBoundsException e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private String readDelimiterStandard(String delimiter) {
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            byte nextByte;
            StringBuilder sb = new StringBuilder(64);
            int len = delimiter.length();
            for (int delimiterPos = 0; this.undecodedChunk.isReadable() && delimiterPos < len; ++delimiterPos) {
                nextByte = this.undecodedChunk.readByte();
                if (nextByte == delimiter.charAt(delimiterPos)) {
                    sb.append((char)nextByte);
                    continue;
                }
                this.undecodedChunk.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            }
            if (this.undecodedChunk.isReadable()) {
                nextByte = this.undecodedChunk.readByte();
                if (nextByte == 13) {
                    nextByte = this.undecodedChunk.readByte();
                    if (nextByte == 10) {
                        return sb.toString();
                    }
                    this.undecodedChunk.readerIndex(readerIndex);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                }
                if (nextByte == 10) {
                    return sb.toString();
                }
                if (nextByte == 45) {
                    sb.append('-');
                    nextByte = this.undecodedChunk.readByte();
                    if (nextByte == 45) {
                        sb.append('-');
                        if (this.undecodedChunk.isReadable()) {
                            nextByte = this.undecodedChunk.readByte();
                            if (nextByte == 13) {
                                nextByte = this.undecodedChunk.readByte();
                                if (nextByte == 10) {
                                    return sb.toString();
                                }
                                this.undecodedChunk.readerIndex(readerIndex);
                                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                            }
                            if (nextByte == 10) {
                                return sb.toString();
                            }
                            this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
                            return sb.toString();
                        }
                        return sb.toString();
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private String readDelimiter(String delimiter) {
        HttpPostBodyUtil.SeekAheadOptimize sao;
        try {
            sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException ignored) {
            return this.readDelimiterStandard(delimiter);
        }
        int readerIndex = this.undecodedChunk.readerIndex();
        int len = delimiter.length();
        try {
            byte nextByte;
            StringBuilder sb = new StringBuilder(64);
            for (int delimiterPos = 0; sao.pos < sao.limit && delimiterPos < len; ++delimiterPos) {
                if ((nextByte = sao.bytes[sao.pos++]) == delimiter.charAt(delimiterPos)) {
                    sb.append((char)nextByte);
                    continue;
                }
                this.undecodedChunk.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            }
            if (sao.pos < sao.limit) {
                if ((nextByte = sao.bytes[sao.pos++]) == 13) {
                    if (sao.pos < sao.limit) {
                        if ((nextByte = sao.bytes[sao.pos++]) == 10) {
                            sao.setReadPosition(0);
                            return sb.toString();
                        }
                        this.undecodedChunk.readerIndex(readerIndex);
                        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                    }
                    this.undecodedChunk.readerIndex(readerIndex);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                }
                if (nextByte == 10) {
                    sao.setReadPosition(0);
                    return sb.toString();
                }
                if (nextByte == 45) {
                    sb.append('-');
                    if (sao.pos < sao.limit && (nextByte = sao.bytes[sao.pos++]) == 45) {
                        sb.append('-');
                        if (sao.pos < sao.limit) {
                            if ((nextByte = sao.bytes[sao.pos++]) == 13) {
                                if (sao.pos < sao.limit) {
                                    if ((nextByte = sao.bytes[sao.pos++]) == 10) {
                                        sao.setReadPosition(0);
                                        return sb.toString();
                                    }
                                    this.undecodedChunk.readerIndex(readerIndex);
                                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                                }
                                this.undecodedChunk.readerIndex(readerIndex);
                                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                            }
                            if (nextByte == 10) {
                                sao.setReadPosition(0);
                                return sb.toString();
                            }
                            sao.setReadPosition(1);
                            return sb.toString();
                        }
                        sao.setReadPosition(0);
                        return sb.toString();
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
        this.undecodedChunk.readerIndex(readerIndex);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    private void readFileUploadByteMultipartStandard(String delimiter) {
        int readerIndex = this.undecodedChunk.readerIndex();
        boolean newLine = true;
        int index = 0;
        int lastPosition = this.undecodedChunk.readerIndex();
        boolean found = false;
        while (this.undecodedChunk.isReadable()) {
            byte nextByte = this.undecodedChunk.readByte();
            if (newLine) {
                if (nextByte == delimiter.codePointAt(index)) {
                    if (delimiter.length() != ++index) continue;
                    found = true;
                    break;
                }
                newLine = false;
                index = 0;
                if (nextByte == 13) {
                    if (!this.undecodedChunk.isReadable()) continue;
                    nextByte = this.undecodedChunk.readByte();
                    if (nextByte == 10) {
                        newLine = true;
                        index = 0;
                        lastPosition = this.undecodedChunk.readerIndex() - 2;
                        continue;
                    }
                    lastPosition = this.undecodedChunk.readerIndex() - 1;
                    this.undecodedChunk.readerIndex(lastPosition);
                    continue;
                }
                if (nextByte == 10) {
                    newLine = true;
                    index = 0;
                    lastPosition = this.undecodedChunk.readerIndex() - 1;
                    continue;
                }
                lastPosition = this.undecodedChunk.readerIndex();
                continue;
            }
            if (nextByte == 13) {
                if (!this.undecodedChunk.isReadable()) continue;
                nextByte = this.undecodedChunk.readByte();
                if (nextByte == 10) {
                    newLine = true;
                    index = 0;
                    lastPosition = this.undecodedChunk.readerIndex() - 2;
                    continue;
                }
                lastPosition = this.undecodedChunk.readerIndex() - 1;
                this.undecodedChunk.readerIndex(lastPosition);
                continue;
            }
            if (nextByte == 10) {
                newLine = true;
                index = 0;
                lastPosition = this.undecodedChunk.readerIndex() - 1;
                continue;
            }
            lastPosition = this.undecodedChunk.readerIndex();
        }
        ByteBuf buffer = this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex);
        if (found) {
            try {
                this.currentFileUpload.addContent(buffer, true);
                this.undecodedChunk.readerIndex(lastPosition);
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        } else {
            try {
                this.currentFileUpload.addContent(buffer, false);
                this.undecodedChunk.readerIndex(lastPosition);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        }
    }

    private void readFileUploadByteMultipart(String delimiter) {
        HttpPostBodyUtil.SeekAheadOptimize sao;
        try {
            sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException ignored) {
            this.readFileUploadByteMultipartStandard(delimiter);
            return;
        }
        int readerIndex = this.undecodedChunk.readerIndex();
        boolean newLine = true;
        int index = 0;
        int lastrealpos = sao.pos;
        boolean found = false;
        while (sao.pos < sao.limit) {
            byte nextByte = sao.bytes[sao.pos++];
            if (newLine) {
                if (nextByte == delimiter.codePointAt(index)) {
                    if (delimiter.length() != ++index) continue;
                    found = true;
                    break;
                }
                newLine = false;
                index = 0;
                if (nextByte == 13) {
                    if (sao.pos >= sao.limit) continue;
                    if ((nextByte = sao.bytes[sao.pos++]) == 10) {
                        newLine = true;
                        index = 0;
                        lastrealpos = sao.pos - 2;
                        continue;
                    }
                    lastrealpos = --sao.pos;
                    continue;
                }
                if (nextByte == 10) {
                    newLine = true;
                    index = 0;
                    lastrealpos = sao.pos - 1;
                    continue;
                }
                lastrealpos = sao.pos;
                continue;
            }
            if (nextByte == 13) {
                if (sao.pos >= sao.limit) continue;
                if ((nextByte = sao.bytes[sao.pos++]) == 10) {
                    newLine = true;
                    index = 0;
                    lastrealpos = sao.pos - 2;
                    continue;
                }
                lastrealpos = --sao.pos;
                continue;
            }
            if (nextByte == 10) {
                newLine = true;
                index = 0;
                lastrealpos = sao.pos - 1;
                continue;
            }
            lastrealpos = sao.pos;
        }
        int lastPosition = sao.getReadPosition(lastrealpos);
        ByteBuf buffer = this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex);
        if (found) {
            try {
                this.currentFileUpload.addContent(buffer, true);
                this.undecodedChunk.readerIndex(lastPosition);
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        } else {
            try {
                this.currentFileUpload.addContent(buffer, false);
                this.undecodedChunk.readerIndex(lastPosition);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        }
    }

    private void loadFieldMultipartStandard(String delimiter) {
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            boolean newLine = true;
            int index = 0;
            int lastPosition = this.undecodedChunk.readerIndex();
            boolean found = false;
            while (this.undecodedChunk.isReadable()) {
                byte nextByte = this.undecodedChunk.readByte();
                if (newLine) {
                    if (nextByte == delimiter.codePointAt(index)) {
                        if (delimiter.length() != ++index) continue;
                        found = true;
                        break;
                    }
                    newLine = false;
                    index = 0;
                    if (nextByte == 13) {
                        if (this.undecodedChunk.isReadable()) {
                            nextByte = this.undecodedChunk.readByte();
                            if (nextByte == 10) {
                                newLine = true;
                                index = 0;
                                lastPosition = this.undecodedChunk.readerIndex() - 2;
                                continue;
                            }
                            lastPosition = this.undecodedChunk.readerIndex() - 1;
                            this.undecodedChunk.readerIndex(lastPosition);
                            continue;
                        }
                        lastPosition = this.undecodedChunk.readerIndex() - 1;
                        continue;
                    }
                    if (nextByte == 10) {
                        newLine = true;
                        index = 0;
                        lastPosition = this.undecodedChunk.readerIndex() - 1;
                        continue;
                    }
                    lastPosition = this.undecodedChunk.readerIndex();
                    continue;
                }
                if (nextByte == 13) {
                    if (this.undecodedChunk.isReadable()) {
                        nextByte = this.undecodedChunk.readByte();
                        if (nextByte == 10) {
                            newLine = true;
                            index = 0;
                            lastPosition = this.undecodedChunk.readerIndex() - 2;
                            continue;
                        }
                        lastPosition = this.undecodedChunk.readerIndex() - 1;
                        this.undecodedChunk.readerIndex(lastPosition);
                        continue;
                    }
                    lastPosition = this.undecodedChunk.readerIndex() - 1;
                    continue;
                }
                if (nextByte == 10) {
                    newLine = true;
                    index = 0;
                    lastPosition = this.undecodedChunk.readerIndex() - 1;
                    continue;
                }
                lastPosition = this.undecodedChunk.readerIndex();
            }
            if (found) {
                try {
                    this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), true);
                } catch (IOException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                }
            }
            try {
                this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), false);
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
            this.undecodedChunk.readerIndex(lastPosition);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            this.undecodedChunk.readerIndex(lastPosition);
        } catch (IndexOutOfBoundsException e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
    }

    private void loadFieldMultipart(String delimiter) {
        HttpPostBodyUtil.SeekAheadOptimize sao;
        try {
            sao = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
        } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException ignored) {
            this.loadFieldMultipartStandard(delimiter);
            return;
        }
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            boolean newLine = true;
            int index = 0;
            int lastrealpos = sao.pos;
            boolean found = false;
            while (sao.pos < sao.limit) {
                byte nextByte = sao.bytes[sao.pos++];
                if (newLine) {
                    if (nextByte == delimiter.codePointAt(index)) {
                        if (delimiter.length() != ++index) continue;
                        found = true;
                        break;
                    }
                    newLine = false;
                    index = 0;
                    if (nextByte == 13) {
                        if (sao.pos >= sao.limit) continue;
                        if ((nextByte = sao.bytes[sao.pos++]) == 10) {
                            newLine = true;
                            index = 0;
                            lastrealpos = sao.pos - 2;
                            continue;
                        }
                        lastrealpos = --sao.pos;
                        continue;
                    }
                    if (nextByte == 10) {
                        newLine = true;
                        index = 0;
                        lastrealpos = sao.pos - 1;
                        continue;
                    }
                    lastrealpos = sao.pos;
                    continue;
                }
                if (nextByte == 13) {
                    if (sao.pos >= sao.limit) continue;
                    if ((nextByte = sao.bytes[sao.pos++]) == 10) {
                        newLine = true;
                        index = 0;
                        lastrealpos = sao.pos - 2;
                        continue;
                    }
                    lastrealpos = --sao.pos;
                    continue;
                }
                if (nextByte == 10) {
                    newLine = true;
                    index = 0;
                    lastrealpos = sao.pos - 1;
                    continue;
                }
                lastrealpos = sao.pos;
            }
            int lastPosition = sao.getReadPosition(lastrealpos);
            if (found) {
                try {
                    this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), true);
                } catch (IOException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                }
            }
            try {
                this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), false);
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
            this.undecodedChunk.readerIndex(lastPosition);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            this.undecodedChunk.readerIndex(lastPosition);
        } catch (IndexOutOfBoundsException e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
    }

    private static String cleanString(String field) {
        StringBuilder sb = new StringBuilder(field.length());
        for (int i = 0; i < field.length(); ++i) {
            char nextChar = field.charAt(i);
            if (nextChar == ':') {
                sb.append(' ');
                continue;
            }
            if (nextChar == ',') {
                sb.append(' ');
                continue;
            }
            if (nextChar == '=') {
                sb.append(' ');
                continue;
            }
            if (nextChar == ';') {
                sb.append(' ');
                continue;
            }
            if (nextChar == '\t') {
                sb.append(' ');
                continue;
            }
            if (nextChar == '\"') continue;
            sb.append(nextChar);
        }
        return sb.toString().trim();
    }

    private boolean skipOneLine() {
        if (!this.undecodedChunk.isReadable()) {
            return false;
        }
        byte nextByte = this.undecodedChunk.readByte();
        if (nextByte == 13) {
            if (!this.undecodedChunk.isReadable()) {
                this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
                return false;
            }
            nextByte = this.undecodedChunk.readByte();
            if (nextByte == 10) {
                return true;
            }
            this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
            return false;
        }
        if (nextByte == 10) {
            return true;
        }
        this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
        return false;
    }

    private static String[] splitMultipartHeader(String sb) {
        int colonEnd;
        int nameStart;
        char ch;
        int nameEnd;
        ArrayList<String> headers = new ArrayList<String>(1);
        for (nameEnd = nameStart = HttpPostBodyUtil.findNonWhitespace(sb, 0); nameEnd < sb.length() && (ch = sb.charAt(nameEnd)) != ':' && !Character.isWhitespace(ch); ++nameEnd) {
        }
        for (colonEnd = nameEnd; colonEnd < sb.length(); ++colonEnd) {
            if (sb.charAt(colonEnd) != ':') continue;
            ++colonEnd;
            break;
        }
        int valueStart = HttpPostBodyUtil.findNonWhitespace(sb, colonEnd);
        int valueEnd = HttpPostBodyUtil.findEndOfString(sb);
        headers.add(sb.substring(nameStart, nameEnd));
        String svalue = sb.substring(valueStart, valueEnd);
        String[] values = svalue.indexOf(59) >= 0 ? HttpPostMultipartRequestDecoder.splitMultipartHeaderValues(svalue) : svalue.split(",");
        for (String value : values) {
            headers.add(value.trim());
        }
        String[] array = new String[headers.size()];
        for (int i = 0; i < headers.size(); ++i) {
            array[i] = (String)headers.get(i);
        }
        return array;
    }

    private static String[] splitMultipartHeaderValues(String svalue) {
        ArrayList<String> values = InternalThreadLocalMap.get().arrayList(1);
        boolean inQuote = false;
        boolean escapeNext = false;
        int start = 0;
        for (int i = 0; i < svalue.length(); ++i) {
            char c = svalue.charAt(i);
            if (inQuote) {
                if (escapeNext) {
                    escapeNext = false;
                    continue;
                }
                if (c == '\\') {
                    escapeNext = true;
                    continue;
                }
                if (c != '\"') continue;
                inQuote = false;
                continue;
            }
            if (c == '\"') {
                inQuote = true;
                continue;
            }
            if (c != ';') continue;
            values.add(svalue.substring(start, i));
            start = i + 1;
        }
        values.add(svalue.substring(start));
        return values.toArray(new String[values.size()]);
    }
}

