/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import net.minecraft.client.Minecraft;
import net.optifine.http.HttpPipeline;
import net.optifine.http.IFileDownloadListener;

public class FileDownloadThread
extends Thread {
    private String urlString = null;
    private IFileDownloadListener listener = null;

    public FileDownloadThread(String string, IFileDownloadListener iFileDownloadListener) {
        this.urlString = string;
        this.listener = iFileDownloadListener;
    }

    @Override
    public void run() {
        try {
            byte[] byArray = HttpPipeline.get(this.urlString, Minecraft.getInstance().getProxy());
            this.listener.fileDownloadFinished(this.urlString, byArray, null);
        } catch (Exception exception) {
            this.listener.fileDownloadFinished(this.urlString, null, exception);
        }
    }

    public String getUrlString() {
        return this.urlString;
    }

    public IFileDownloadListener getListener() {
        return this.listener;
    }
}

