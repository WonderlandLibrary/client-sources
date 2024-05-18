package net.minecraft.client.renderer;

import java.util.concurrent.atomic.*;
import java.awt.image.*;
import java.net.*;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import javax.imageio.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class ThreadDownloadImageData extends SimpleTexture
{
    private static final String[] I;
    private Thread imageThread;
    private static final AtomicInteger threadDownloadCounter;
    public Boolean imageFound;
    private final File cacheFile;
    private static final Logger logger;
    private boolean textureUploaded;
    private final String imageUrl;
    private static final String __OBFID;
    private BufferedImage bufferedImage;
    private final IImageBuffer imageBuffer;
    
    protected void loadTextureFromServer() {
        (this.imageThread = new Thread(this, ThreadDownloadImageData.I["   ".length()] + ThreadDownloadImageData.threadDownloadCounter.incrementAndGet()) {
            private static final String[] I;
            final ThreadDownloadImageData this$0;
            private static final String __OBFID;
            
            private static void I() {
                (I = new String["   ".length()])["".length()] = I("1\u0017\u0010?\t\u001a\u0019\u00038\u000b\u0012X\u000f%\u0011\u0005X\u00134\u001d\u0001\r\u00154E\u0013\n\b<E\u000e\u0005G%\nU\u0003\u001a", "uxgQe");
                ThreadDownloadImageData$1.I[" ".length()] = I("\"\n \u0000\r\u000fB!L\r\u000e\u0012;\u0000\u0006\u0000\u0001u\u0004\u001d\u0015\u0015u\u0018\f\u0019\u0011 \u001e\f", "aeUli");
                ThreadDownloadImageData$1.I["  ".length()] = I("\u0007\u0001-D\u007ft}CDzt", "DMrtO");
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
                __OBFID = ThreadDownloadImageData$1.I["  ".length()];
            }
            
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                final Logger access$0 = ThreadDownloadImageData.access$0();
                final String s = ThreadDownloadImageData$1.I["".length()];
                final Object[] array = new Object["  ".length()];
                array["".length()] = ThreadDownloadImageData.access$1(this.this$0);
                array[" ".length()] = ThreadDownloadImageData.access$2(this.this$0);
                access$0.debug(s, array);
                try {
                    httpURLConnection = (HttpURLConnection)new URL(ThreadDownloadImageData.access$1(this.this$0)).openConnection(Minecraft.getMinecraft().getProxy());
                    httpURLConnection.setDoInput(" ".length() != 0);
                    httpURLConnection.setDoOutput("".length() != 0);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() / (0x43 ^ 0x27) != "  ".length()) {
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        final ThreadDownloadImageData this$0 = this.this$0;
                        int n;
                        if (ThreadDownloadImageData.access$3(this.this$0) != null) {
                            n = " ".length();
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                        }
                        else {
                            n = "".length();
                        }
                        this$0.imageFound = (n != 0);
                        return;
                    }
                    BufferedImage bufferedImage;
                    if (ThreadDownloadImageData.access$2(this.this$0) != null) {
                        FileUtils.copyInputStreamToFile(httpURLConnection.getInputStream(), ThreadDownloadImageData.access$2(this.this$0));
                        bufferedImage = ImageIO.read(ThreadDownloadImageData.access$2(this.this$0));
                        "".length();
                        if (3 < 0) {
                            throw null;
                        }
                    }
                    else {
                        bufferedImage = TextureUtil.readBufferedImage(httpURLConnection.getInputStream());
                    }
                    if (ThreadDownloadImageData.access$4(this.this$0) != null) {
                        bufferedImage = ThreadDownloadImageData.access$4(this.this$0).parseUserSkin(bufferedImage);
                    }
                    this.this$0.setBufferedImage(bufferedImage);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                catch (Exception ex) {
                    ThreadDownloadImageData.access$0().error(ThreadDownloadImageData$1.I[" ".length()], (Throwable)ex);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    final ThreadDownloadImageData this$2 = this.this$0;
                    int n2;
                    if (ThreadDownloadImageData.access$3(this.this$0) != null) {
                        n2 = " ".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        n2 = "".length();
                    }
                    this$2.imageFound = (n2 != 0);
                    return;
                }
                finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    final ThreadDownloadImageData this$3 = this.this$0;
                    int n3;
                    if (ThreadDownloadImageData.access$3(this.this$0) != null) {
                        n3 = " ".length();
                        "".length();
                        if (4 == 3) {
                            throw null;
                        }
                    }
                    else {
                        n3 = "".length();
                    }
                    this$3.imageFound = (n3 != 0);
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                final ThreadDownloadImageData this$4 = this.this$0;
                int n4;
                if (ThreadDownloadImageData.access$3(this.this$0) != null) {
                    n4 = " ".length();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else {
                    n4 = "".length();
                }
                this$4.imageFound = (n4 != 0);
            }
        }).setDaemon(" ".length() != 0);
        this.imageThread.start();
    }
    
    static File access$2(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.cacheFile;
    }
    
    private static void I() {
        (I = new String[0x89 ^ 0x8D])["".length()] = I("$=\u0014b\\WAzbX^", "gqKRl");
        ThreadDownloadImageData.I[" ".length()] = I("$\u001c.>\u0006\u0006\u0014o2\u001b\u001c\u0003o.\n\u0010\u0007:(\nH\u0015=5\u0002H\u001f 9\u000e\u0004S,;\f\u0000\u0016or\u0014\u0015Z", "hsOZo");
        ThreadDownloadImageData.I["  ".length()] = I("4\u0004\u001e?\t\u0019L\u001fs\u0001\u0018\n\u000fs\u001e\u001c\u0002\u0005s", "wkkSm");
        ThreadDownloadImageData.I["   ".length()] = I("\u001f\u001d\u001d\u000189\u001dE1\"<\u0016\t\u001a,/\u001d\u0017Un", "KxeuM");
    }
    
    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        if (this.imageBuffer != null) {
            this.imageBuffer.skinAvailable();
        }
        int n;
        if (this.bufferedImage != null) {
            n = " ".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        this.imageFound = (n != 0);
    }
    
    @Override
    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        if (this.bufferedImage == null && this.textureLocation != null) {
            super.loadTexture(resourceManager);
        }
        if (this.imageThread == null) {
            if (this.cacheFile != null && this.cacheFile.isFile()) {
                final Logger logger = ThreadDownloadImageData.logger;
                final String s = ThreadDownloadImageData.I[" ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = this.cacheFile;
                logger.debug(s, array);
                try {
                    this.bufferedImage = ImageIO.read(this.cacheFile);
                    if (this.imageBuffer != null) {
                        this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
                    }
                    int n;
                    if (this.bufferedImage != null) {
                        n = " ".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    this.imageFound = (n != 0);
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    return;
                }
                catch (IOException ex) {
                    ThreadDownloadImageData.logger.error(ThreadDownloadImageData.I["  ".length()] + this.cacheFile, (Throwable)ex);
                    this.loadTextureFromServer();
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                    return;
                }
            }
            this.loadTextureFromServer();
        }
    }
    
    static Logger access$0() {
        return ThreadDownloadImageData.logger;
    }
    
    static String access$1(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageUrl;
    }
    
    static IImageBuffer access$4(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageBuffer;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ThreadDownloadImageData(final File cacheFile, final String imageUrl, final ResourceLocation resourceLocation, final IImageBuffer imageBuffer) {
        super(resourceLocation);
        this.imageFound = null;
        this.cacheFile = cacheFile;
        this.imageUrl = imageUrl;
        this.imageBuffer = imageBuffer;
    }
    
    static {
        I();
        __OBFID = ThreadDownloadImageData.I["".length()];
        logger = LogManager.getLogger();
        threadDownloadCounter = new AtomicInteger("".length());
    }
    
    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            if (this.textureLocation != null) {
                this.deleteGlTexture();
            }
            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
            this.textureUploaded = (" ".length() != 0);
        }
    }
    
    static BufferedImage access$3(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.bufferedImage;
    }
}
