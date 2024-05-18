package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.io.FileUtils;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture
{
    private static final Logger à;
    private static final AtomicInteger Ø;
    private final File áŒŠÆ;
    private final String áˆºÑ¢Õ;
    private final IImageBuffer ÂµÈ;
    private BufferedImage á;
    private Thread ˆÏ­;
    private boolean £á;
    private static final String Å = "CL_00001049";
    
    static {
        à = LogManager.getLogger();
        Ø = new AtomicInteger(0);
    }
    
    public ThreadDownloadImageData(final File p_i1049_1_, final String p_i1049_2_, final ResourceLocation_1975012498 p_i1049_3_, final IImageBuffer p_i1049_4_) {
        super(p_i1049_3_);
        this.áŒŠÆ = p_i1049_1_;
        this.áˆºÑ¢Õ = p_i1049_2_;
        this.ÂµÈ = p_i1049_4_;
    }
    
    private void Ó() {
        if (!this.£á && this.á != null) {
            if (this.Ó != null) {
                this.Âµá€();
            }
            TextureUtil.HorizonCode_Horizon_È(super.HorizonCode_Horizon_È(), this.á);
            this.£á = true;
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        this.Ó();
        return super.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È(final BufferedImage p_147641_1_) {
        this.á = p_147641_1_;
        if (this.ÂµÈ != null) {
            this.ÂµÈ.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110551_1_) throws IOException {
        if (this.á == null && this.Ó != null) {
            super.HorizonCode_Horizon_È(p_110551_1_);
        }
        if (this.ˆÏ­ == null) {
            if (this.áŒŠÆ != null && this.áŒŠÆ.isFile()) {
                ThreadDownloadImageData.à.debug("Loading http texture from local cache ({})", new Object[] { this.áŒŠÆ });
                try {
                    this.á = ImageIO.read(this.áŒŠÆ);
                    if (this.ÂµÈ != null) {
                        this.HorizonCode_Horizon_È(this.ÂµÈ.HorizonCode_Horizon_È(this.á));
                    }
                }
                catch (IOException var3) {
                    ThreadDownloadImageData.à.error("Couldn't load skin " + this.áŒŠÆ, (Throwable)var3);
                    this.Â();
                }
            }
            else {
                this.Â();
            }
        }
    }
    
    protected void Â() {
        (this.ˆÏ­ = new Thread("Texture Downloader #" + ThreadDownloadImageData.Ø.incrementAndGet()) {
            private static final String Â = "CL_00001050";
            
            @Override
            public void run() {
                HttpURLConnection var1 = null;
                ThreadDownloadImageData.à.debug("Downloading http texture from {} to {}", new Object[] { ThreadDownloadImageData.this.áˆºÑ¢Õ, ThreadDownloadImageData.this.áŒŠÆ });
                try {
                    var1 = (HttpURLConnection)new URL(ThreadDownloadImageData.this.áˆºÑ¢Õ).openConnection(Minecraft.áŒŠà().ŠÂµà());
                    var1.setDoInput(true);
                    var1.setDoOutput(false);
                    var1.connect();
                    if (var1.getResponseCode() / 100 != 2) {
                        return;
                    }
                    BufferedImage var2;
                    if (ThreadDownloadImageData.this.áŒŠÆ != null) {
                        FileUtils.copyInputStreamToFile(var1.getInputStream(), ThreadDownloadImageData.this.áŒŠÆ);
                        var2 = ImageIO.read(ThreadDownloadImageData.this.áŒŠÆ);
                    }
                    else {
                        var2 = TextureUtil.HorizonCode_Horizon_È(var1.getInputStream());
                    }
                    if (ThreadDownloadImageData.this.ÂµÈ != null) {
                        var2 = ThreadDownloadImageData.this.ÂµÈ.HorizonCode_Horizon_È(var2);
                    }
                    ThreadDownloadImageData.this.HorizonCode_Horizon_È(var2);
                }
                catch (Exception var3) {
                    ThreadDownloadImageData.à.error("Couldn't download http texture", (Throwable)var3);
                }
                finally {
                    if (var1 != null) {
                        try {
                            var1.disconnect();
                        }
                        catch (Exception ex) {}
                    }
                }
                if (var1 != null) {
                    try {
                        var1.disconnect();
                    }
                    catch (Exception ex2) {}
                }
            }
        }).setDaemon(true);
        this.ˆÏ­.start();
    }
}
