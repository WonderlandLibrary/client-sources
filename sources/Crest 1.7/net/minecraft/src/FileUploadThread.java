// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.src;

import java.util.Map;

public class FileUploadThread extends Thread
{
    private String urlString;
    private Map headers;
    private byte[] content;
    private IFileUploadListener listener;
    
    public FileUploadThread(final String urlString, final Map headers, final byte[] content, final IFileUploadListener listener) {
        this.urlString = urlString;
        this.headers = headers;
        this.content = content;
        this.listener = listener;
    }
    
    @Override
    public void run() {
        try {
            HttpUtils.post(this.urlString, this.headers, this.content);
            this.listener.fileUploadFinished(this.urlString, this.content, null);
        }
        catch (Exception var2) {
            this.listener.fileUploadFinished(this.urlString, this.content, var2);
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
