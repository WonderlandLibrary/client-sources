package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class FileDownloadThread extends Thread {
	private String urlString = null;
	private IFileDownloadListener listener = null;

	public FileDownloadThread(String p_i26_1_, IFileDownloadListener p_i26_2_) {
		this.urlString = p_i26_1_;
		this.listener = p_i26_2_;
	}

	@Override
	public void run() {
		try {
			byte[] abyte = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
			this.listener.fileDownloadFinished(this.urlString, abyte, (Throwable) null);
		} catch (Exception exception) {
			this.listener.fileDownloadFinished(this.urlString, (byte[]) null, exception);
		}
	}

	public String getUrlString() {
		return this.urlString;
	}

	public IFileDownloadListener getListener() {
		return this.listener;
	}
}
