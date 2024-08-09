/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.handler.codec.http.multipart.MemoryFileUpload;
import io.netty.handler.codec.http.multipart.MixedAttribute;
import io.netty.handler.codec.http.multipart.MixedFileUpload;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultHttpDataFactory
implements HttpDataFactory {
    public static final long MINSIZE = 16384L;
    public static final long MAXSIZE = -1L;
    private final boolean useDisk;
    private final boolean checkSize;
    private long minSize;
    private long maxSize = -1L;
    private Charset charset = HttpConstants.DEFAULT_CHARSET;
    private final Map<HttpRequest, List<HttpData>> requestFileDeleteMap = Collections.synchronizedMap(new IdentityHashMap());

    public DefaultHttpDataFactory() {
        this.useDisk = false;
        this.checkSize = true;
        this.minSize = 16384L;
    }

    public DefaultHttpDataFactory(Charset charset) {
        this();
        this.charset = charset;
    }

    public DefaultHttpDataFactory(boolean bl) {
        this.useDisk = bl;
        this.checkSize = false;
    }

    public DefaultHttpDataFactory(boolean bl, Charset charset) {
        this(bl);
        this.charset = charset;
    }

    public DefaultHttpDataFactory(long l) {
        this.useDisk = false;
        this.checkSize = true;
        this.minSize = l;
    }

    public DefaultHttpDataFactory(long l, Charset charset) {
        this(l);
        this.charset = charset;
    }

    @Override
    public void setMaxLimit(long l) {
        this.maxSize = l;
    }

    private List<HttpData> getList(HttpRequest httpRequest) {
        List<HttpData> list = this.requestFileDeleteMap.get(httpRequest);
        if (list == null) {
            list = new ArrayList<HttpData>();
            this.requestFileDeleteMap.put(httpRequest, list);
        }
        return list;
    }

    @Override
    public Attribute createAttribute(HttpRequest httpRequest, String string) {
        if (this.useDisk) {
            DiskAttribute diskAttribute = new DiskAttribute(string, this.charset);
            diskAttribute.setMaxSize(this.maxSize);
            List<HttpData> list = this.getList(httpRequest);
            list.add(diskAttribute);
            return diskAttribute;
        }
        if (this.checkSize) {
            MixedAttribute mixedAttribute = new MixedAttribute(string, this.minSize, this.charset);
            mixedAttribute.setMaxSize(this.maxSize);
            List<HttpData> list = this.getList(httpRequest);
            list.add(mixedAttribute);
            return mixedAttribute;
        }
        MemoryAttribute memoryAttribute = new MemoryAttribute(string);
        memoryAttribute.setMaxSize(this.maxSize);
        return memoryAttribute;
    }

    @Override
    public Attribute createAttribute(HttpRequest httpRequest, String string, long l) {
        if (this.useDisk) {
            DiskAttribute diskAttribute = new DiskAttribute(string, l, this.charset);
            diskAttribute.setMaxSize(this.maxSize);
            List<HttpData> list = this.getList(httpRequest);
            list.add(diskAttribute);
            return diskAttribute;
        }
        if (this.checkSize) {
            MixedAttribute mixedAttribute = new MixedAttribute(string, l, this.minSize, this.charset);
            mixedAttribute.setMaxSize(this.maxSize);
            List<HttpData> list = this.getList(httpRequest);
            list.add(mixedAttribute);
            return mixedAttribute;
        }
        MemoryAttribute memoryAttribute = new MemoryAttribute(string, l);
        memoryAttribute.setMaxSize(this.maxSize);
        return memoryAttribute;
    }

    private static void checkHttpDataSize(HttpData httpData) {
        try {
            httpData.checkSize(httpData.length());
        } catch (IOException iOException) {
            throw new IllegalArgumentException("Attribute bigger than maxSize allowed");
        }
    }

    @Override
    public Attribute createAttribute(HttpRequest httpRequest, String string, String string2) {
        if (this.useDisk) {
            Attribute attribute;
            try {
                attribute = new DiskAttribute(string, string2, this.charset);
                attribute.setMaxSize(this.maxSize);
            } catch (IOException iOException) {
                attribute = new MixedAttribute(string, string2, this.minSize, this.charset);
                attribute.setMaxSize(this.maxSize);
            }
            DefaultHttpDataFactory.checkHttpDataSize(attribute);
            List<HttpData> list = this.getList(httpRequest);
            list.add(attribute);
            return attribute;
        }
        if (this.checkSize) {
            MixedAttribute mixedAttribute = new MixedAttribute(string, string2, this.minSize, this.charset);
            mixedAttribute.setMaxSize(this.maxSize);
            DefaultHttpDataFactory.checkHttpDataSize(mixedAttribute);
            List<HttpData> list = this.getList(httpRequest);
            list.add(mixedAttribute);
            return mixedAttribute;
        }
        try {
            MemoryAttribute memoryAttribute = new MemoryAttribute(string, string2, this.charset);
            memoryAttribute.setMaxSize(this.maxSize);
            DefaultHttpDataFactory.checkHttpDataSize(memoryAttribute);
            return memoryAttribute;
        } catch (IOException iOException) {
            throw new IllegalArgumentException(iOException);
        }
    }

    @Override
    public FileUpload createFileUpload(HttpRequest httpRequest, String string, String string2, String string3, String string4, Charset charset, long l) {
        if (this.useDisk) {
            DiskFileUpload diskFileUpload = new DiskFileUpload(string, string2, string3, string4, charset, l);
            diskFileUpload.setMaxSize(this.maxSize);
            DefaultHttpDataFactory.checkHttpDataSize(diskFileUpload);
            List<HttpData> list = this.getList(httpRequest);
            list.add(diskFileUpload);
            return diskFileUpload;
        }
        if (this.checkSize) {
            MixedFileUpload mixedFileUpload = new MixedFileUpload(string, string2, string3, string4, charset, l, this.minSize);
            mixedFileUpload.setMaxSize(this.maxSize);
            DefaultHttpDataFactory.checkHttpDataSize(mixedFileUpload);
            List<HttpData> list = this.getList(httpRequest);
            list.add(mixedFileUpload);
            return mixedFileUpload;
        }
        MemoryFileUpload memoryFileUpload = new MemoryFileUpload(string, string2, string3, string4, charset, l);
        memoryFileUpload.setMaxSize(this.maxSize);
        DefaultHttpDataFactory.checkHttpDataSize(memoryFileUpload);
        return memoryFileUpload;
    }

    @Override
    public void removeHttpDataFromClean(HttpRequest httpRequest, InterfaceHttpData interfaceHttpData) {
        if (!(interfaceHttpData instanceof HttpData)) {
            return;
        }
        List<HttpData> list = this.requestFileDeleteMap.get(httpRequest);
        if (list == null) {
            return;
        }
        Iterator<HttpData> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            HttpData httpData = iterator2.next();
            if (httpData != interfaceHttpData) continue;
            iterator2.remove();
            if (list.isEmpty()) {
                this.requestFileDeleteMap.remove(httpRequest);
            }
            return;
        }
    }

    @Override
    public void cleanRequestHttpData(HttpRequest httpRequest) {
        List<HttpData> list = this.requestFileDeleteMap.remove(httpRequest);
        if (list != null) {
            for (HttpData httpData : list) {
                httpData.release();
            }
        }
    }

    @Override
    public void cleanAllHttpData() {
        Iterator<Map.Entry<HttpRequest, List<HttpData>>> iterator2 = this.requestFileDeleteMap.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<HttpRequest, List<HttpData>> entry = iterator2.next();
            List<HttpData> list = entry.getValue();
            for (HttpData httpData : list) {
                httpData.release();
            }
            iterator2.remove();
        }
    }

    @Override
    public void cleanRequestHttpDatas(HttpRequest httpRequest) {
        this.cleanRequestHttpData(httpRequest);
    }

    @Override
    public void cleanAllHttpDatas() {
        this.cleanAllHttpData();
    }
}

