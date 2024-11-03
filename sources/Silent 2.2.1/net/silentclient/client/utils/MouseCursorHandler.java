package net.silentclient.client.utils;

import net.silentclient.client.utils.cursors.SystemCursors;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MouseCursorHandler {
    private Map<CursorType, Cursor> cursors;
    private CursorType currentCursor;
    private boolean customCursorDisabled;

    public MouseCursorHandler() {
        this.cursors = new HashMap<CursorType, Cursor>();
        this.currentCursor = CursorType.NORMAL;
        this.customCursorDisabled = false;
        final int minCursorSize = Cursor.getMinCursorSize();
        final int maxCursorSize = Cursor.getMaxCursorSize();
        LogManager.getLogger().info("Min Cursor Size: " + minCursorSize + " max: " + maxCursorSize);
        if (maxCursorSize < 24) {
            this.customCursorDisabled = true;
            return;
        }
        this.loadCursorType(CursorType.MOVE);
        this.loadCursorType(CursorType.NWSE_RESIZE);
        this.loadCursorType(CursorType.NESW_RESIZE);
        this.loadCursorType(CursorType.EDIT_TEXT);
        this.loadCursorType(CursorType.POINTER);
    }

    public void loadCursorType(final CursorType cursorType) {
        try {
            this.cursors.put(cursorType, this.loadCursor(ImageIO.read(this.getClass().getResourceAsStream(String.format("/assets/minecraft/silentclient/mouse/%s/%s.png", OSUtil.isMac() ? "mac" : "win", cursorType.name().toLowerCase(Locale.US))))));
        }
        catch (final Exception ex) {
            LogManager.getLogger().catching((Throwable)ex);
        }
    }

    public Cursor loadCursor(final BufferedImage bufferedImage) throws LWJGLException {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int min = Math.min(Math.max(width, Cursor.getMinCursorSize()), Cursor.getMaxCursorSize());
        final int[] src = new int[min * min];
        int n = 0;
        for (int i = 0; i < min; ++i) {
            for (int j = 0; j < min; ++j) {
                int rgb = 0;
                try {
                    rgb = bufferedImage.getRGB(i, j);
                }
                catch (final Exception ex) {}
                src[n++] = rgb;
            }
        }
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(min * min);
        intBuffer.put(src);
        intBuffer.rewind();
        LogManager.getLogger().info("Loading cursor: w: " + width + " h: " + height);
        return new Cursor(min, min, width / 2, height / 2, 1, intBuffer, (IntBuffer)null);
    }

    public boolean enableCursor(final CursorType cursorType) {
        if(SystemCursors.isSupported()) {
            SystemCursors.setCursor(cursorType.getCursor());
            this.currentCursor = cursorType;
            return true;
        } else {
            if (this.customCursorDisabled) {
                return false;
            }
            if (this.currentCursor.equals(cursorType)) {
                return true;
            }
            if (cursorType.equals(CursorType.NORMAL)) {
                this.disableCursor();
                return true;
            }
            try {
                final Cursor nativeCursor = this.cursors.get(cursorType);
                if (nativeCursor != null) {
                    Mouse.setNativeCursor(nativeCursor);
                    this.currentCursor = cursorType;
                    return true;
                }
            }
            catch (final Exception ex) {
                LogManager.getLogger().catching((Throwable)ex);
            }
            return false;
        }
    }

    public void disableCursor() {
        if(SystemCursors.isSupported()) {
            this.currentCursor = CursorType.NORMAL;
            SystemCursors.setCursor(SystemCursors.ARROW);
        } else {
            if (this.customCursorDisabled) {
                return;
            }
            if (this.currentCursor != CursorType.NORMAL) {
                try {
                    Mouse.setNativeCursor((Cursor)null);
                    this.currentCursor = CursorType.NORMAL;
                }
                catch (final Exception ex) {
                    LogManager.getLogger().catching((Throwable)ex);
                }
            }
        }
    }

    public CursorType getCurrentCursor() {
        return CursorType.NORMAL;
    }

    public enum CursorType
    {
        NORMAL(SystemCursors.ARROW),
        NESW_RESIZE(SystemCursors.RESIZE_NESW),
        NWSE_RESIZE(SystemCursors.RESIZE_NWSE),
        MOVE(SystemCursors.CROSSHAIR),
        EDIT_TEXT(SystemCursors.IBEAM),
        POINTER(SystemCursors.POINTING_HAND);

        private final byte cursor;

        CursorType(byte cursor) {
            this.cursor = cursor;
        }

        public byte getCursor() {
            return cursor;
        }
    }
}
