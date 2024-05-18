package net.minecraft.util;

import java.nio.*;
import java.io.*;
import net.minecraft.client.shader.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.event.*;
import java.util.*;
import org.apache.logging.log4j.*;
import java.text.*;

public class ScreenShotHelper
{
    private static final DateFormat dateFormat;
    private static final String[] I;
    private static final Logger logger;
    private static IntBuffer pixelBuffer;
    private static int[] pixelValues;
    
    public static IChatComponent saveScreenshot(final File file, final String s, int framebufferTextureWidth, int framebufferTextureHeight, final Framebuffer framebuffer) {
        try {
            final File file2 = new File(file, ScreenShotHelper.I[" ".length()]);
            file2.mkdir();
            if (OpenGlHelper.isFramebufferEnabled()) {
                framebufferTextureWidth = framebuffer.framebufferTextureWidth;
                framebufferTextureHeight = framebuffer.framebufferTextureHeight;
            }
            final int n = framebufferTextureWidth * framebufferTextureHeight;
            if (ScreenShotHelper.pixelBuffer == null || ScreenShotHelper.pixelBuffer.capacity() < n) {
                ScreenShotHelper.pixelBuffer = BufferUtils.createIntBuffer(n);
                ScreenShotHelper.pixelValues = new int[n];
            }
            GL11.glPixelStorei(1495 + 2588 - 1386 + 636, " ".length());
            GL11.glPixelStorei(606 + 2324 - 2328 + 2715, " ".length());
            ScreenShotHelper.pixelBuffer.clear();
            if (OpenGlHelper.isFramebufferEnabled()) {
                GlStateManager.bindTexture(framebuffer.framebufferTexture);
                GL11.glGetTexImage(373 + 2843 + 180 + 157, "".length(), 1472 + 24739 - 5014 + 11796, 26227 + 14972 - 25362 + 17802, ScreenShotHelper.pixelBuffer);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                GL11.glReadPixels("".length(), "".length(), framebufferTextureWidth, framebufferTextureHeight, 32952 + 11333 - 30002 + 18710, 4268 + 15781 + 11435 + 2155, ScreenShotHelper.pixelBuffer);
            }
            ScreenShotHelper.pixelBuffer.get(ScreenShotHelper.pixelValues);
            TextureUtil.processPixelValues(ScreenShotHelper.pixelValues, framebufferTextureWidth, framebufferTextureHeight);
            BufferedImage bufferedImage;
            if (OpenGlHelper.isFramebufferEnabled()) {
                bufferedImage = new BufferedImage(framebuffer.framebufferWidth, framebuffer.framebufferHeight, " ".length());
                int i;
                final int n2 = i = framebuffer.framebufferTextureHeight - framebuffer.framebufferHeight;
                "".length();
                if (1 == 4) {
                    throw null;
                }
                while (i < framebuffer.framebufferTextureHeight) {
                    int j = "".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    while (j < framebuffer.framebufferWidth) {
                        bufferedImage.setRGB(j, i - n2, ScreenShotHelper.pixelValues[i * framebuffer.framebufferTextureWidth + j]);
                        ++j;
                    }
                    ++i;
                }
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else {
                bufferedImage = new BufferedImage(framebufferTextureWidth, framebufferTextureHeight, " ".length());
                bufferedImage.setRGB("".length(), "".length(), framebufferTextureWidth, framebufferTextureHeight, ScreenShotHelper.pixelValues, "".length(), framebufferTextureWidth);
            }
            File timestampedPNGFileForDirectory;
            if (s == null) {
                timestampedPNGFileForDirectory = getTimestampedPNGFileForDirectory(file2);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                timestampedPNGFileForDirectory = new File(file2, s);
            }
            ImageIO.write(bufferedImage, ScreenShotHelper.I["  ".length()], timestampedPNGFileForDirectory);
            final ChatComponentText chatComponentText = new ChatComponentText(timestampedPNGFileForDirectory.getName());
            chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, timestampedPNGFileForDirectory.getAbsolutePath()));
            chatComponentText.getChatStyle().setUnderlined(" ".length() != 0);
            final String s2 = ScreenShotHelper.I["   ".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = chatComponentText;
            return new ChatComponentTranslation(s2, array);
        }
        catch (Exception ex) {
            ScreenShotHelper.logger.warn(ScreenShotHelper.I[0x60 ^ 0x64], (Throwable)ex);
            final String s3 = ScreenShotHelper.I[0x41 ^ 0x44];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = ex.getMessage();
            return new ChatComponentTranslation(s3, array2);
        }
    }
    
    public static IChatComponent saveScreenshot(final File file, final int n, final int n2, final Framebuffer framebuffer) {
        return saveScreenshot(file, null, n, n2, framebuffer);
    }
    
    private static void I() {
        (I = new String[0x57 ^ 0x5E])["".length()] = I("8\u0013\r(z\f'Y53\u001e\"<\u007f:,D\u0007\"", "AjtQW");
        ScreenShotHelper.I[" ".length()] = I("\u000b':&&\u00167 ,7\u000b", "xDHCC");
        ScreenShotHelper.I["  ".length()] = I("4\u001d\f", "DskPp");
        ScreenShotHelper.I["   ".length()] = I("\u001f\u0012\u001f\u0017\u0004\u0002\u0002\u0005\u001d\u0015B\u0002\u0018\u0011\u0002\t\u0002\u001e", "lqmra");
        ScreenShotHelper.I[0x98 ^ 0x9C] = I("\u000f\u000e\f\u000f2\"F\rC%-\u0017\u001cC%/\u0013\u001c\u00068?\t\u0016\u0017", "LaycV");
        ScreenShotHelper.I[0x18 ^ 0x1D] = I("\u0003!=,,\u001e1'&=^$. %\u00050*", "pBOII");
        ScreenShotHelper.I[0xA3 ^ 0xA5] = I("", "VbzhA");
        ScreenShotHelper.I[0x6B ^ 0x6C] = I("\u0006", "YiMYc");
        ScreenShotHelper.I[0x5 ^ 0xD] = I("b3\u001d\n", "LCsmk");
    }
    
    private static File getTimestampedPNGFileForDirectory(final File file) {
        final String string = ScreenShotHelper.dateFormat.format(new Date()).toString();
        int length = " ".length();
        do {
            final StringBuilder sb = new StringBuilder(String.valueOf(string));
            String string2;
            if (length == " ".length()) {
                string2 = ScreenShotHelper.I[0x40 ^ 0x46];
                "".length();
                if (!true) {
                    throw null;
                }
            }
            else {
                string2 = ScreenShotHelper.I[0x26 ^ 0x21] + length;
            }
            final File file2 = new File(file, sb.append(string2).append(ScreenShotHelper.I[0x7B ^ 0x73]).toString());
            if (!file2.exists()) {
                return file2;
            }
            ++length;
            "".length();
        } while (4 >= 2);
        throw null;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        dateFormat = new SimpleDateFormat(ScreenShotHelper.I["".length()]);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
