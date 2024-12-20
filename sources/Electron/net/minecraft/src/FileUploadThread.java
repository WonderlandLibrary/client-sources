package net.minecraft.src;

import java.util.Map;

public class FileUploadThread extends Thread {
    private final String urlString;
    private final Map headers;
    private final byte[] content;
    private final IFileUploadListener listener;

    public FileUploadThread(String p_i49_1_, Map p_i49_2_, byte[] p_i49_3_, IFileUploadListener p_i49_4_) {
        this.urlString = p_i49_1_;
        this.headers = p_i49_2_;
        this.content = p_i49_3_;
        this.listener = p_i49_4_;
    }

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
