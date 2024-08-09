/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import java.util.Map;
import net.optifine.http.HttpUtils;
import net.optifine.http.IFileUploadListener;

public class FileUploadThread
extends Thread {
    private String urlString;
    private Map headers;
    private byte[] content;
    private IFileUploadListener listener;

    public FileUploadThread(String string, Map map, byte[] byArray, IFileUploadListener iFileUploadListener) {
        this.urlString = string;
        this.headers = map;
        this.content = byArray;
        this.listener = iFileUploadListener;
    }

    @Override
    public void run() {
        try {
            HttpUtils.post(this.urlString, this.headers, this.content);
            this.listener.fileUploadFinished(this.urlString, this.content, null);
        } catch (Exception exception) {
            this.listener.fileUploadFinished(this.urlString, this.content, exception);
        }
    }

    public String getUrlString() {
        return this.urlString;
    }

    public byte[] getContent() {
        return this.content;
    }

    public IFileUploadListener getListener() {
        return this.listener;
    }
}

