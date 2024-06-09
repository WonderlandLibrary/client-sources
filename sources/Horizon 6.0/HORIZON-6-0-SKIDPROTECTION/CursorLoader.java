package HORIZON-6-0-SKIDPROTECTION;

import java.nio.ByteOrder;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import java.nio.IntBuffer;
import org.lwjgl.input.Cursor;

public class CursorLoader
{
    private static CursorLoader HorizonCode_Horizon_È;
    
    static {
        CursorLoader.HorizonCode_Horizon_È = new CursorLoader();
    }
    
    public static CursorLoader HorizonCode_Horizon_È() {
        return CursorLoader.HorizonCode_Horizon_È;
    }
    
    public Cursor HorizonCode_Horizon_È(final String ref, final int x, final int y) throws IOException, LWJGLException {
        LoadableImageData imageData = null;
        imageData = ImageDataFactory.HorizonCode_Horizon_È(ref);
        imageData.HorizonCode_Horizon_È(false);
        final ByteBuffer buf = imageData.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), true, true, null);
        for (int i = 0; i < buf.limit(); i += 4) {
            final byte red = buf.get(i);
            final byte green = buf.get(i + 1);
            final byte blue = buf.get(i + 2);
            final byte alpha = buf.get(i + 3);
            buf.put(i + 2, red);
            buf.put(i + 1, green);
            buf.put(i, blue);
            buf.put(i + 3, alpha);
        }
        try {
            int yspot = imageData.Â() - y - 1;
            if (yspot < 0) {
                yspot = 0;
            }
            return new Cursor(imageData.Âµá€(), imageData.Ø­áŒŠá(), x, yspot, 1, buf.asIntBuffer(), (IntBuffer)null);
        }
        catch (Throwable e) {
            Log.Ý("Chances are you cursor is too small for this platform");
            throw new LWJGLException(e);
        }
    }
    
    public Cursor HorizonCode_Horizon_È(final ByteBuffer buf, final int x, final int y, final int width, final int height) throws IOException, LWJGLException {
        for (int i = 0; i < buf.limit(); i += 4) {
            final byte red = buf.get(i);
            final byte green = buf.get(i + 1);
            final byte blue = buf.get(i + 2);
            final byte alpha = buf.get(i + 3);
            buf.put(i + 2, red);
            buf.put(i + 1, green);
            buf.put(i, blue);
            buf.put(i + 3, alpha);
        }
        try {
            int yspot = height - y - 1;
            if (yspot < 0) {
                yspot = 0;
            }
            return new Cursor(width, height, x, yspot, 1, buf.asIntBuffer(), (IntBuffer)null);
        }
        catch (Throwable e) {
            Log.Ý("Chances are you cursor is too small for this platform");
            throw new LWJGLException(e);
        }
    }
    
    public Cursor HorizonCode_Horizon_È(final ImageData imageData, final int x, final int y) throws IOException, LWJGLException {
        final ByteBuffer buf = imageData.Ý();
        for (int i = 0; i < buf.limit(); i += 4) {
            final byte red = buf.get(i);
            final byte green = buf.get(i + 1);
            final byte blue = buf.get(i + 2);
            final byte alpha = buf.get(i + 3);
            buf.put(i + 2, red);
            buf.put(i + 1, green);
            buf.put(i, blue);
            buf.put(i + 3, alpha);
        }
        try {
            int yspot = imageData.Â() - y - 1;
            if (yspot < 0) {
                yspot = 0;
            }
            return new Cursor(imageData.Âµá€(), imageData.Ø­áŒŠá(), x, yspot, 1, buf.asIntBuffer(), (IntBuffer)null);
        }
        catch (Throwable e) {
            Log.Ý("Chances are you cursor is too small for this platform");
            throw new LWJGLException(e);
        }
    }
    
    public Cursor HorizonCode_Horizon_È(final String ref, final int x, final int y, final int width, final int height, final int[] cursorDelays) throws IOException, LWJGLException {
        final IntBuffer cursorDelaysBuffer = ByteBuffer.allocateDirect(cursorDelays.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        for (int i = 0; i < cursorDelays.length; ++i) {
            cursorDelaysBuffer.put(cursorDelays[i]);
        }
        cursorDelaysBuffer.flip();
        final LoadableImageData imageData = new TGAImageData();
        final ByteBuffer buf = imageData.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(ref), false, null);
        return new Cursor(width, height, x, y, cursorDelays.length, buf.asIntBuffer(), cursorDelaysBuffer);
    }
}
