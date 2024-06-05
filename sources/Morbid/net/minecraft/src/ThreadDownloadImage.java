package net.minecraft.src;

import java.net.*;
import javax.imageio.*;

class ThreadDownloadImage extends Thread
{
    final String location;
    final IImageBuffer buffer;
    final ThreadDownloadImageData imageData;
    
    ThreadDownloadImage(final ThreadDownloadImageData par1, final String par2Str, final IImageBuffer par3IImageBuffer) {
        this.imageData = par1;
        this.location = par2Str;
        this.buffer = par3IImageBuffer;
    }
    
    @Override
    public void run() {
        HttpURLConnection var1 = null;
        try {
            final URL var2 = new URL(this.location);
            var1 = (HttpURLConnection)var2.openConnection();
            var1.setDoInput(true);
            var1.setDoOutput(false);
            var1.connect();
            if (var1.getResponseCode() / 100 == 4) {
                return;
            }
            if (this.buffer == null) {
                this.imageData.image = ImageIO.read(var1.getInputStream());
            }
            else {
                this.imageData.image = this.buffer.parseUserSkin(ImageIO.read(var1.getInputStream()));
            }
        }
        catch (Exception var3) {
            var3.printStackTrace();
            return;
        }
        finally {
            var1.disconnect();
        }
        var1.disconnect();
    }
}
