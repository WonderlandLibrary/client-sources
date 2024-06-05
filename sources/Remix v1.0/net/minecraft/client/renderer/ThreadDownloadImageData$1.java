package net.minecraft.client.renderer;

import java.net.*;
import net.minecraft.client.*;
import optifine.*;
import org.apache.commons.io.*;
import javax.imageio.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;

class ThreadDownloadImageData$1 extends Thread {
    @Override
    public void run() {
        HttpURLConnection var1 = null;
        ThreadDownloadImageData.access$200().debug("Downloading http texture from {} to {}", new Object[] { ThreadDownloadImageData.access$000(ThreadDownloadImageData.this), ThreadDownloadImageData.access$100(ThreadDownloadImageData.this) });
        if (ThreadDownloadImageData.access$300(ThreadDownloadImageData.this)) {
            ThreadDownloadImageData.access$400(ThreadDownloadImageData.this);
        }
        else {
            try {
                var1 = (HttpURLConnection)new URL(ThreadDownloadImageData.access$000(ThreadDownloadImageData.this)).openConnection(Minecraft.getMinecraft().getProxy());
                var1.setDoInput(true);
                var1.setDoOutput(false);
                var1.connect();
                if (var1.getResponseCode() / 100 != 2) {
                    if (var1.getErrorStream() != null) {
                        Config.readAll(var1.getErrorStream());
                    }
                    return;
                }
                BufferedImage var2;
                if (ThreadDownloadImageData.access$100(ThreadDownloadImageData.this) != null) {
                    FileUtils.copyInputStreamToFile(var1.getInputStream(), ThreadDownloadImageData.access$100(ThreadDownloadImageData.this));
                    var2 = ImageIO.read(ThreadDownloadImageData.access$100(ThreadDownloadImageData.this));
                }
                else {
                    var2 = TextureUtil.func_177053_a(var1.getInputStream());
                }
                if (ThreadDownloadImageData.access$500(ThreadDownloadImageData.this) != null) {
                    var2 = ThreadDownloadImageData.access$500(ThreadDownloadImageData.this).parseUserSkin(var2);
                }
                ThreadDownloadImageData.this.setBufferedImage(var2);
            }
            catch (Exception var3) {
                ThreadDownloadImageData.access$200().error("Couldn't download http texture: " + var3.getClass().getName() + ": " + var3.getMessage());
            }
            finally {
                if (var1 != null) {
                    var1.disconnect();
                }
                ThreadDownloadImageData.this.imageFound = (ThreadDownloadImageData.access$600(ThreadDownloadImageData.this) != null);
            }
        }
    }
}