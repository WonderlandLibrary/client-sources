package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class FileDownloadThread extends Thread {
    private String urlString = null;
    private IFileDownloadListener listener = null;

    public FileDownloadThread(String p_i48_1_, IFileDownloadListener p_i48_2_) {
        this.urlString = p_i48_1_;
        this.listener = p_i48_2_;
    }

    public void run() {
        try {
            byte[] abyte = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
            this.listener.fileDownloadFinished(this.urlString, abyte, null);
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
