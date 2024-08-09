/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.RasterRangesUtils;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.peer.ComponentPeer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

public class WindowUtils {
    private static final String TRANSPARENT_OLD_BG = "transparent-old-bg";
    private static final String TRANSPARENT_OLD_OPAQUE = "transparent-old-opaque";
    private static final String TRANSPARENT_ALPHA = "transparent-alpha";
    public static final Shape MASK_NONE = null;

    private static NativeWindowUtils getInstance() {
        return Holder.INSTANCE;
    }

    public static void setWindowMask(Window window, Shape shape) {
        WindowUtils.getInstance().setWindowMask((Component)window, shape);
    }

    public static void setComponentMask(Component component, Shape shape) {
        WindowUtils.getInstance().setWindowMask(component, shape);
    }

    public static void setWindowMask(Window window, Icon icon) {
        WindowUtils.getInstance().setWindowMask((Component)window, icon);
    }

    public static boolean isWindowAlphaSupported() {
        return WindowUtils.getInstance().isWindowAlphaSupported();
    }

    public static GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
        return WindowUtils.getInstance().getAlphaCompatibleGraphicsConfiguration();
    }

    public static void setWindowAlpha(Window window, float f) {
        WindowUtils.getInstance().setWindowAlpha(window, Math.max(0.0f, Math.min(f, 1.0f)));
    }

    public static void setWindowTransparent(Window window, boolean bl) {
        WindowUtils.getInstance().setWindowTransparent(window, bl);
    }

    static class 1 {
    }

    private static class X11WindowUtils
    extends NativeWindowUtils {
        private boolean didCheck;
        private long[] alphaVisualIDs = new long[0];
        private static final long OPAQUE = 0xFFFFFFFFL;
        private static final String OPACITY = "_NET_WM_WINDOW_OPACITY";

        private X11WindowUtils() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static X11.Pixmap createBitmap(X11.Display display, X11.Window window, Raster raster) {
            X11 x11 = X11.INSTANCE;
            Rectangle rectangle = raster.getBounds();
            int n = rectangle.x + rectangle.width;
            int n2 = rectangle.y + rectangle.height;
            X11.Pixmap pixmap = x11.XCreatePixmap(display, window, n, n2, 1);
            X11.GC gC = x11.XCreateGC(display, pixmap, new NativeLong(0L), null);
            if (gC == null) {
                return null;
            }
            x11.XSetForeground(display, gC, new NativeLong(0L));
            x11.XFillRectangle(display, pixmap, gC, 0, 0, n, n2);
            ArrayList arrayList = new ArrayList();
            try {
                int n3;
                RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput(arrayList){
                    final List val$rlist;
                    {
                        this.val$rlist = list;
                    }

                    public boolean outputRange(int n, int n2, int n3, int n4) {
                        this.val$rlist.add(new Rectangle(n, n2, n3, n4));
                        return false;
                    }
                });
                X11.XRectangle[] xRectangleArray = (X11.XRectangle[])new X11.XRectangle().toArray(arrayList.size());
                for (n3 = 0; n3 < xRectangleArray.length; ++n3) {
                    Rectangle rectangle2 = (Rectangle)arrayList.get(n3);
                    xRectangleArray[n3].x = (short)rectangle2.x;
                    xRectangleArray[n3].y = (short)rectangle2.y;
                    xRectangleArray[n3].width = (short)rectangle2.width;
                    xRectangleArray[n3].height = (short)rectangle2.height;
                    Pointer pointer = xRectangleArray[n3].getPointer();
                    pointer.setShort(0L, (short)rectangle2.x);
                    pointer.setShort(2L, (short)rectangle2.y);
                    pointer.setShort(4L, (short)rectangle2.width);
                    pointer.setShort(6L, (short)rectangle2.height);
                    xRectangleArray[n3].setAutoSynch(true);
                }
                n3 = 1;
                x11.XSetForeground(display, gC, new NativeLong(1L));
                x11.XFillRectangles(display, pixmap, gC, xRectangleArray, xRectangleArray.length);
            } finally {
                x11.XFreeGC(display, gC);
            }
            return pixmap;
        }

        public boolean isWindowAlphaSupported() {
            return this.getAlphaVisualIDs().length > 0;
        }

        private static long getVisualID(GraphicsConfiguration graphicsConfiguration) {
            try {
                Object object = graphicsConfiguration.getClass().getMethod("getVisual", null).invoke(graphicsConfiguration, null);
                return ((Number)object).longValue();
            } catch (Exception exception) {
                exception.printStackTrace();
                return -1L;
            }
        }

        public GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
            if (this.isWindowAlphaSupported()) {
                GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice[] graphicsDeviceArray = graphicsEnvironment.getScreenDevices();
                for (int i = 0; i < graphicsDeviceArray.length; ++i) {
                    GraphicsConfiguration[] graphicsConfigurationArray = graphicsDeviceArray[i].getConfigurations();
                    for (int j = 0; j < graphicsConfigurationArray.length; ++j) {
                        long l = X11WindowUtils.getVisualID(graphicsConfigurationArray[j]);
                        long[] lArray = this.getAlphaVisualIDs();
                        for (int k = 0; k < lArray.length; ++k) {
                            if (l != lArray[k]) continue;
                            return graphicsConfigurationArray[j];
                        }
                    }
                }
            }
            return super.getAlphaCompatibleGraphicsConfiguration();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private synchronized long[] getAlphaVisualIDs() {
            if (this.didCheck) {
                return this.alphaVisualIDs;
            }
            this.didCheck = true;
            X11 x11 = X11.INSTANCE;
            X11.Display display = x11.XOpenDisplay(null);
            if (display == null) {
                return this.alphaVisualIDs;
            }
            Structure structure = null;
            try {
                int n = x11.XDefaultScreen(display);
                X11.XVisualInfo xVisualInfo = new X11.XVisualInfo();
                xVisualInfo.screen = n;
                xVisualInfo.depth = 32;
                xVisualInfo.c_class = 4;
                NativeLong nativeLong = new NativeLong(14L);
                IntByReference intByReference = new IntByReference();
                structure = x11.XGetVisualInfo(display, nativeLong, xVisualInfo, intByReference);
                if (structure != null) {
                    int n2;
                    ArrayList<X11.VisualID> arrayList = new ArrayList<X11.VisualID>();
                    X11.XVisualInfo[] xVisualInfoArray = (X11.XVisualInfo[])structure.toArray(intByReference.getValue());
                    for (n2 = 0; n2 < xVisualInfoArray.length; ++n2) {
                        X11.Xrender.XRenderPictFormat xRenderPictFormat = X11.Xrender.INSTANCE.XRenderFindVisualFormat(display, xVisualInfoArray[n2].visual);
                        if (xRenderPictFormat.type != 1 || xRenderPictFormat.direct.alphaMask == 0) continue;
                        arrayList.add(xVisualInfoArray[n2].visualid);
                    }
                    this.alphaVisualIDs = new long[arrayList.size()];
                    for (n2 = 0; n2 < this.alphaVisualIDs.length; ++n2) {
                        this.alphaVisualIDs[n2] = ((Number)arrayList.get(n2)).longValue();
                    }
                    long[] lArray = this.alphaVisualIDs;
                    return lArray;
                }
            } finally {
                if (structure != null) {
                    x11.XFree(structure.getPointer());
                }
                x11.XCloseDisplay(display);
            }
            return this.alphaVisualIDs;
        }

        private static X11.Window getContentWindow(Window window, X11.Display display, X11.Window window2, Point point) {
            if (window instanceof Frame && !((Frame)window).isUndecorated() || window instanceof Dialog && !((Dialog)window).isUndecorated()) {
                int[] nArray;
                X11 x11 = X11.INSTANCE;
                X11.WindowByReference windowByReference = new X11.WindowByReference();
                X11.WindowByReference windowByReference2 = new X11.WindowByReference();
                PointerByReference pointerByReference = new PointerByReference();
                IntByReference intByReference = new IntByReference();
                x11.XQueryTree(display, window2, windowByReference, windowByReference2, pointerByReference, intByReference);
                Pointer pointer = pointerByReference.getValue();
                int[] nArray2 = nArray = pointer.getIntArray(0L, intByReference.getValue());
                int n = nArray2.length;
                int n2 = 0;
                if (n2 < n) {
                    int n3 = nArray2[n2];
                    X11.Window window3 = new X11.Window((long)n3);
                    X11.XWindowAttributes xWindowAttributes = new X11.XWindowAttributes();
                    x11.XGetWindowAttributes(display, window3, xWindowAttributes);
                    point.x = -xWindowAttributes.x;
                    point.y = -xWindowAttributes.y;
                    window2 = window3;
                }
                if (pointer != null) {
                    x11.XFree(pointer);
                }
            }
            return window2;
        }

        private static X11.Window getDrawable(Component component) {
            int n = (int)Native.getComponentID(component);
            if (n == 0) {
                return null;
            }
            return new X11.Window((long)n);
        }

        public void setWindowAlpha(Window window, float f) {
            if (!this.isWindowAlphaSupported()) {
                throw new UnsupportedOperationException("This X11 display does not provide a 32-bit visual");
            }
            Runnable runnable = new Runnable(this, window, f){
                final Window val$w;
                final float val$alpha;
                final X11WindowUtils this$0;
                {
                    this.this$0 = x11WindowUtils;
                    this.val$w = window;
                    this.val$alpha = f;
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void run() {
                    X11 x11 = X11.INSTANCE;
                    X11.Display display = x11.XOpenDisplay(null);
                    if (display == null) {
                        return;
                    }
                    try {
                        X11.Window window = X11WindowUtils.access$800(this.val$w);
                        if (this.val$alpha == 1.0f) {
                            x11.XDeleteProperty(display, window, x11.XInternAtom(display, X11WindowUtils.OPACITY, false));
                        } else {
                            int n = (int)((long)(this.val$alpha * 4.2949673E9f) & 0xFFFFFFFFFFFFFFFFL);
                            IntByReference intByReference = new IntByReference(n);
                            x11.XChangeProperty(display, window, x11.XInternAtom(display, X11WindowUtils.OPACITY, false), X11.XA_CARDINAL, 32, 0, intByReference.getPointer(), 1);
                        }
                    } finally {
                        x11.XCloseDisplay(display);
                    }
                }
            };
            this.whenDisplayable(window, runnable);
        }

        public void setWindowTransparent(Window window, boolean bl) {
            boolean bl2;
            if (!(window instanceof RootPaneContainer)) {
                throw new IllegalArgumentException("Window must be a RootPaneContainer");
            }
            if (!this.isWindowAlphaSupported()) {
                throw new UnsupportedOperationException("This X11 display does not provide a 32-bit visual");
            }
            if (!window.getGraphicsConfiguration().equals(this.getAlphaCompatibleGraphicsConfiguration())) {
                throw new IllegalArgumentException("Window GraphicsConfiguration '" + window.getGraphicsConfiguration() + "' does not support transparency");
            }
            boolean bl3 = bl2 = window.getBackground() != null && window.getBackground().getAlpha() == 0;
            if (bl == bl2) {
                return;
            }
            this.whenDisplayable(window, new Runnable(this, window, bl){
                final Window val$w;
                final boolean val$transparent;
                final X11WindowUtils this$0;
                {
                    this.this$0 = x11WindowUtils;
                    this.val$w = window;
                    this.val$transparent = bl;
                }

                public void run() {
                    JRootPane jRootPane = ((RootPaneContainer)((Object)this.val$w)).getRootPane();
                    JLayeredPane jLayeredPane = jRootPane.getLayeredPane();
                    Container container = jRootPane.getContentPane();
                    if (container instanceof X11TransparentContentPane) {
                        ((X11TransparentContentPane)container).setTransparent(this.val$transparent);
                    } else if (this.val$transparent) {
                        X11TransparentContentPane x11TransparentContentPane = new X11TransparentContentPane(this.this$0, container);
                        jRootPane.setContentPane(x11TransparentContentPane);
                        jLayeredPane.add((Component)new RepaintTrigger(x11TransparentContentPane), JLayeredPane.DRAG_LAYER);
                    }
                    this.this$0.setLayersTransparent(this.val$w, this.val$transparent);
                    this.this$0.setForceHeavyweightPopups(this.val$w, this.val$transparent);
                    this.this$0.setDoubleBuffered(this.val$w, !this.val$transparent);
                }
            });
        }

        private void setWindowShape(Window window, PixmapSource pixmapSource) {
            Runnable runnable = new Runnable(this, window, pixmapSource){
                final Window val$w;
                final PixmapSource val$src;
                final X11WindowUtils this$0;
                {
                    this.this$0 = x11WindowUtils;
                    this.val$w = window;
                    this.val$src = pixmapSource;
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void run() {
                    X11.Pixmap pixmap;
                    X11.Display display;
                    X11 x11;
                    block4: {
                        x11 = X11.INSTANCE;
                        display = x11.XOpenDisplay(null);
                        if (display == null) {
                            return;
                        }
                        pixmap = null;
                        try {
                            X11.Window window = X11WindowUtils.access$800(this.val$w);
                            pixmap = this.val$src.getPixmap(display, window);
                            X11.Xext xext = X11.Xext.INSTANCE;
                            xext.XShapeCombineMask(display, window, 0, 0, 0, pixmap == null ? X11.Pixmap.None : pixmap, 0);
                            if (pixmap == null) break block4;
                            x11.XFreePixmap(display, pixmap);
                        } catch (Throwable throwable) {
                            if (pixmap != null) {
                                x11.XFreePixmap(display, pixmap);
                            }
                            x11.XCloseDisplay(display);
                            throw throwable;
                        }
                    }
                    x11.XCloseDisplay(display);
                    this.this$0.setForceHeavyweightPopups(this.this$0.getWindow(this.val$w), pixmap != null);
                }
            };
            this.whenDisplayable(window, runnable);
        }

        protected void setMask(Component component, Raster raster) {
            this.setWindowShape(this.getWindow(component), new PixmapSource(this, raster){
                final Raster val$raster;
                final X11WindowUtils this$0;
                {
                    this.this$0 = x11WindowUtils;
                    this.val$raster = raster;
                }

                public X11.Pixmap getPixmap(X11.Display display, X11.Window window) {
                    return this.val$raster != null ? X11WindowUtils.access$1000(display, window, this.val$raster) : null;
                }
            });
        }

        X11WindowUtils(1 var1_1) {
            this();
        }

        static X11.Window access$800(Component component) {
            return X11WindowUtils.getDrawable(component);
        }

        static X11.Window access$900(Window window, X11.Display display, X11.Window window2, Point point) {
            return X11WindowUtils.getContentWindow(window, display, window2, point);
        }

        static X11.Pixmap access$1000(X11.Display display, X11.Window window, Raster raster) {
            return X11WindowUtils.createBitmap(display, window, raster);
        }

        private static interface PixmapSource {
            public X11.Pixmap getPixmap(X11.Display var1, X11.Window var2);
        }

        private class X11TransparentContentPane
        extends NativeWindowUtils.TransparentContentPane {
            private static final long serialVersionUID = 1L;
            private Memory buffer;
            private int[] pixels;
            private final int[] pixel;
            final X11WindowUtils this$0;

            public X11TransparentContentPane(X11WindowUtils x11WindowUtils, Container container) {
                this.this$0 = x11WindowUtils;
                super(x11WindowUtils, container);
                this.pixel = new int[4];
            }

            protected void paintDirect(BufferedImage bufferedImage, Rectangle rectangle) {
                Window window = SwingUtilities.getWindowAncestor(this);
                X11 x11 = X11.INSTANCE;
                X11.Display display = x11.XOpenDisplay(null);
                X11.Window window2 = X11WindowUtils.access$800(window);
                Point point = new Point();
                window2 = X11WindowUtils.access$900(window, display, window2, point);
                X11.GC gC = x11.XCreateGC(display, window2, new NativeLong(0L), null);
                Raster raster = bufferedImage.getData();
                int n = rectangle.width;
                int n2 = rectangle.height;
                if (this.buffer == null || this.buffer.size() != (long)(n * n2 * 4)) {
                    this.buffer = new Memory(n * n2 * 4);
                    this.pixels = new int[n * n2];
                }
                for (int i = 0; i < n2; ++i) {
                    for (int j = 0; j < n; ++j) {
                        raster.getPixel(j, i, this.pixel);
                        int n3 = this.pixel[3] & 0xFF;
                        int n4 = this.pixel[2] & 0xFF;
                        int n5 = this.pixel[1] & 0xFF;
                        int n6 = this.pixel[0] & 0xFF;
                        this.pixels[i * n + j] = n3 << 24 | n6 << 16 | n5 << 8 | n4;
                    }
                }
                X11.XWindowAttributes xWindowAttributes = new X11.XWindowAttributes();
                x11.XGetWindowAttributes(display, window2, xWindowAttributes);
                X11.XImage xImage = x11.XCreateImage(display, xWindowAttributes.visual, 32, 2, 0, this.buffer, n, n2, 32, n * 4);
                this.buffer.write(0L, this.pixels, 0, this.pixels.length);
                point.x += rectangle.x;
                point.y += rectangle.y;
                x11.XPutImage(display, window2, gC, xImage, 0, 0, point.x, point.y, n, n2);
                x11.XFree(xImage.getPointer());
                x11.XFreeGC(display, gC);
                x11.XCloseDisplay(display);
            }
        }
    }

    private static class MacWindowUtils
    extends NativeWindowUtils {
        private static final String WDRAG = "apple.awt.draggableWindowBackground";

        private MacWindowUtils() {
        }

        public boolean isWindowAlphaSupported() {
            return false;
        }

        private OSXMaskingContentPane installMaskingPane(Window window) {
            OSXMaskingContentPane oSXMaskingContentPane;
            if (window instanceof RootPaneContainer) {
                RootPaneContainer rootPaneContainer = (RootPaneContainer)((Object)window);
                Container container = rootPaneContainer.getContentPane();
                if (container instanceof OSXMaskingContentPane) {
                    oSXMaskingContentPane = (OSXMaskingContentPane)container;
                } else {
                    oSXMaskingContentPane = new OSXMaskingContentPane(container);
                    rootPaneContainer.setContentPane(oSXMaskingContentPane);
                }
            } else {
                Component component;
                Component component2 = component = window.getComponentCount() > 0 ? window.getComponent(0) : null;
                if (component instanceof OSXMaskingContentPane) {
                    oSXMaskingContentPane = (OSXMaskingContentPane)component;
                } else {
                    oSXMaskingContentPane = new OSXMaskingContentPane(component);
                    window.add(oSXMaskingContentPane);
                }
            }
            return oSXMaskingContentPane;
        }

        public void setWindowTransparent(Window window, boolean bl) {
            boolean bl2;
            boolean bl3 = bl2 = window.getBackground() != null && window.getBackground().getAlpha() == 0;
            if (bl != bl2) {
                this.setBackgroundTransparent(window, bl, "setWindowTransparent");
            }
        }

        private void fixWindowDragging(Window window, String string) {
            JRootPane jRootPane;
            Boolean bl;
            if (window instanceof RootPaneContainer && (bl = (Boolean)(jRootPane = ((RootPaneContainer)((Object)window)).getRootPane()).getClientProperty(WDRAG)) == null) {
                jRootPane.putClientProperty(WDRAG, Boolean.FALSE);
                if (window.isDisplayable()) {
                    System.err.println(string + "(): To avoid content dragging, " + string + "() must be called before the window is realized, or " + WDRAG + " must be set to Boolean.FALSE before the window is realized.  If you really want content dragging, set " + WDRAG + " on the window's root pane to Boolean.TRUE before calling " + string + "() to hide this message.");
                }
            }
        }

        public void setWindowAlpha(Window window, float f) {
            if (window instanceof RootPaneContainer) {
                JRootPane jRootPane = ((RootPaneContainer)((Object)window)).getRootPane();
                jRootPane.putClientProperty("Window.alpha", new Float(f));
                this.fixWindowDragging(window, "setWindowAlpha");
            }
            this.whenDisplayable(window, new Runnable(this, window, f){
                final Window val$w;
                final float val$alpha;
                final MacWindowUtils this$0;
                {
                    this.this$0 = macWindowUtils;
                    this.val$w = window;
                    this.val$alpha = f;
                }

                public void run() {
                    ComponentPeer componentPeer = this.val$w.getPeer();
                    try {
                        componentPeer.getClass().getMethod("setAlpha", Float.TYPE).invoke(componentPeer, new Float(this.val$alpha));
                    } catch (Exception exception) {
                        // empty catch block
                    }
                }
            });
        }

        protected void setWindowMask(Component component, Raster raster) {
            if (raster != null) {
                this.setWindowMask(component, this.toShape(raster));
            } else {
                this.setWindowMask(component, new Rectangle(0, 0, component.getWidth(), component.getHeight()));
            }
        }

        public void setWindowMask(Component component, Shape shape) {
            if (component instanceof Window) {
                Window window = (Window)component;
                OSXMaskingContentPane oSXMaskingContentPane = this.installMaskingPane(window);
                oSXMaskingContentPane.setMask(shape);
                this.setBackgroundTransparent(window, shape != MASK_NONE, "setWindowMask");
            }
        }

        private void setBackgroundTransparent(Window window, boolean bl, String string) {
            JRootPane jRootPane;
            JRootPane jRootPane2 = jRootPane = window instanceof RootPaneContainer ? ((RootPaneContainer)((Object)window)).getRootPane() : null;
            if (bl) {
                if (jRootPane != null) {
                    jRootPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_BG, window.getBackground());
                }
                window.setBackground(new Color(0, 0, 0, 0));
            } else if (jRootPane != null) {
                Color color = (Color)jRootPane.getClientProperty(WindowUtils.TRANSPARENT_OLD_BG);
                if (color != null) {
                    color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                }
                window.setBackground(color);
                jRootPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_BG, null);
            } else {
                window.setBackground(null);
            }
            this.fixWindowDragging(window, string);
        }

        MacWindowUtils(1 var1_1) {
            this();
        }

        private static class OSXMaskingContentPane
        extends JPanel {
            private static final long serialVersionUID = 1L;
            private Shape shape;

            public OSXMaskingContentPane(Component component) {
                super(new BorderLayout());
                if (component != null) {
                    this.add(component, "Center");
                }
            }

            public void setMask(Shape shape) {
                this.shape = shape;
                this.repaint();
            }

            public void paint(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D)graphics.create();
                graphics2D.setComposite(AlphaComposite.Clear);
                graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
                graphics2D.dispose();
                if (this.shape != null) {
                    graphics2D = (Graphics2D)graphics.create();
                    graphics2D.setClip(this.shape);
                    super.paint(graphics2D);
                    graphics2D.dispose();
                } else {
                    super.paint(graphics);
                }
            }
        }
    }

    private static class W32WindowUtils
    extends NativeWindowUtils {
        private W32WindowUtils() {
        }

        private WinDef.HWND getHWnd(Component component) {
            WinDef.HWND hWND = new WinDef.HWND();
            hWND.setPointer(Native.getComponentPointer(component));
            return hWND;
        }

        public boolean isWindowAlphaSupported() {
            return Boolean.getBoolean("sun.java2d.noddraw");
        }

        private boolean usingUpdateLayeredWindow(Window window) {
            if (window instanceof RootPaneContainer) {
                JRootPane jRootPane = ((RootPaneContainer)((Object)window)).getRootPane();
                return jRootPane.getClientProperty(WindowUtils.TRANSPARENT_OLD_BG) != null;
            }
            return true;
        }

        private void storeAlpha(Window window, byte by) {
            if (window instanceof RootPaneContainer) {
                JRootPane jRootPane = ((RootPaneContainer)((Object)window)).getRootPane();
                Byte by2 = by == -1 ? null : new Byte(by);
                jRootPane.putClientProperty(WindowUtils.TRANSPARENT_ALPHA, by2);
            }
        }

        private byte getAlpha(Window window) {
            JRootPane jRootPane;
            Byte by;
            if (window instanceof RootPaneContainer && (by = (Byte)(jRootPane = ((RootPaneContainer)((Object)window)).getRootPane()).getClientProperty(WindowUtils.TRANSPARENT_ALPHA)) != null) {
                return by;
            }
            return 1;
        }

        public void setWindowAlpha(Window window, float f) {
            if (!this.isWindowAlphaSupported()) {
                throw new UnsupportedOperationException("Set sun.java2d.noddraw=true to enable transparent windows");
            }
            this.whenDisplayable(window, new Runnable(this, window, f){
                final Window val$w;
                final float val$alpha;
                final W32WindowUtils this$0;
                {
                    this.this$0 = w32WindowUtils;
                    this.val$w = window;
                    this.val$alpha = f;
                }

                public void run() {
                    WinDef.HWND hWND = W32WindowUtils.access$400(this.this$0, this.val$w);
                    User32 user32 = User32.INSTANCE;
                    int n = user32.GetWindowLong(hWND, -20);
                    byte by = (byte)((int)(255.0f * this.val$alpha) & 0xFF);
                    if (W32WindowUtils.access$500(this.this$0, this.val$w)) {
                        WinUser.BLENDFUNCTION bLENDFUNCTION = new WinUser.BLENDFUNCTION();
                        bLENDFUNCTION.SourceConstantAlpha = by;
                        bLENDFUNCTION.AlphaFormat = 1;
                        user32.UpdateLayeredWindow(hWND, null, null, null, null, null, 0, bLENDFUNCTION, 2);
                    } else if (this.val$alpha == 1.0f) {
                        user32.SetWindowLong(hWND, -20, n &= 0xFFF7FFFF);
                    } else {
                        user32.SetWindowLong(hWND, -20, n |= 0x80000);
                        user32.SetLayeredWindowAttributes(hWND, 0, by, 2);
                    }
                    this.this$0.setForceHeavyweightPopups(this.val$w, this.val$alpha != 1.0f);
                    W32WindowUtils.access$600(this.this$0, this.val$w, by);
                }
            });
        }

        public void setWindowTransparent(Window window, boolean bl) {
            boolean bl2;
            if (!(window instanceof RootPaneContainer)) {
                throw new IllegalArgumentException("Window must be a RootPaneContainer");
            }
            if (!this.isWindowAlphaSupported()) {
                throw new UnsupportedOperationException("Set sun.java2d.noddraw=true to enable transparent windows");
            }
            boolean bl3 = bl2 = window.getBackground() != null && window.getBackground().getAlpha() == 0;
            if (bl == bl2) {
                return;
            }
            this.whenDisplayable(window, new Runnable(this, window, bl){
                final Window val$w;
                final boolean val$transparent;
                final W32WindowUtils this$0;
                {
                    this.this$0 = w32WindowUtils;
                    this.val$w = window;
                    this.val$transparent = bl;
                }

                public void run() {
                    User32 user32 = User32.INSTANCE;
                    WinDef.HWND hWND = W32WindowUtils.access$400(this.this$0, this.val$w);
                    int n = user32.GetWindowLong(hWND, -20);
                    JRootPane jRootPane = ((RootPaneContainer)((Object)this.val$w)).getRootPane();
                    JLayeredPane jLayeredPane = jRootPane.getLayeredPane();
                    Container container = jRootPane.getContentPane();
                    if (container instanceof W32TransparentContentPane) {
                        ((W32TransparentContentPane)container).setTransparent(this.val$transparent);
                    } else if (this.val$transparent) {
                        W32TransparentContentPane w32TransparentContentPane = new W32TransparentContentPane(this.this$0, container);
                        jRootPane.setContentPane(w32TransparentContentPane);
                        jLayeredPane.add((Component)new RepaintTrigger(w32TransparentContentPane), JLayeredPane.DRAG_LAYER);
                    }
                    if (this.val$transparent && !W32WindowUtils.access$500(this.this$0, this.val$w)) {
                        user32.SetWindowLong(hWND, -20, n |= 0x80000);
                    } else if (!this.val$transparent && W32WindowUtils.access$500(this.this$0, this.val$w)) {
                        user32.SetWindowLong(hWND, -20, n &= 0xFFF7FFFF);
                    }
                    this.this$0.setLayersTransparent(this.val$w, this.val$transparent);
                    this.this$0.setForceHeavyweightPopups(this.val$w, this.val$transparent);
                    this.this$0.setDoubleBuffered(this.val$w, !this.val$transparent);
                }
            });
        }

        public void setWindowMask(Component component, Shape shape) {
            if (shape instanceof Area && ((Area)shape).isPolygonal()) {
                this.setMask(component, (Area)shape);
            } else {
                super.setWindowMask(component, shape);
            }
        }

        private void setWindowRegion(Component component, WinDef.HRGN hRGN) {
            this.whenDisplayable(component, new Runnable(this, component, hRGN){
                final Component val$w;
                final WinDef.HRGN val$hrgn;
                final W32WindowUtils this$0;
                {
                    this.this$0 = w32WindowUtils;
                    this.val$w = component;
                    this.val$hrgn = hRGN;
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void run() {
                    GDI32 gDI32 = GDI32.INSTANCE;
                    User32 user32 = User32.INSTANCE;
                    WinDef.HWND hWND = W32WindowUtils.access$400(this.this$0, this.val$w);
                    try {
                        user32.SetWindowRgn(hWND, this.val$hrgn, true);
                        this.this$0.setForceHeavyweightPopups(this.this$0.getWindow(this.val$w), this.val$hrgn != null);
                    } finally {
                        gDI32.DeleteObject(this.val$hrgn);
                    }
                }
            });
        }

        private void setMask(Component component, Area area) {
            GDI32 gDI32 = GDI32.INSTANCE;
            PathIterator pathIterator = area.getPathIterator(null);
            int n = pathIterator.getWindingRule() == 1 ? 2 : 1;
            float[] fArray = new float[6];
            ArrayList<WinUser.POINT> arrayList = new ArrayList<WinUser.POINT>();
            int n2 = 0;
            ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
            while (!pathIterator.isDone()) {
                int n3 = pathIterator.currentSegment(fArray);
                if (n3 == 0) {
                    n2 = 1;
                    arrayList.add(new WinUser.POINT((int)fArray[0], (int)fArray[1]));
                } else if (n3 == 1) {
                    ++n2;
                    arrayList.add(new WinUser.POINT((int)fArray[0], (int)fArray[1]));
                } else if (n3 == 4) {
                    arrayList2.add(new Integer(n2));
                } else {
                    throw new RuntimeException("Area is not polygonal: " + area);
                }
                pathIterator.next();
            }
            WinUser.POINT[] pOINTArray = (WinUser.POINT[])new WinUser.POINT().toArray(arrayList.size());
            WinUser.POINT[] pOINTArray2 = arrayList.toArray(new WinUser.POINT[arrayList.size()]);
            for (int i = 0; i < pOINTArray.length; ++i) {
                pOINTArray[i].x = pOINTArray2[i].x;
                pOINTArray[i].y = pOINTArray2[i].y;
            }
            int[] nArray = new int[arrayList2.size()];
            for (int i = 0; i < nArray.length; ++i) {
                nArray[i] = (Integer)arrayList2.get(i);
            }
            WinDef.HRGN hRGN = gDI32.CreatePolyPolygonRgn(pOINTArray, nArray, nArray.length, n);
            this.setWindowRegion(component, hRGN);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected void setMask(Component component, Raster raster) {
            WinDef.HRGN hRGN;
            GDI32 gDI32 = GDI32.INSTANCE;
            WinDef.HRGN hRGN2 = hRGN = raster != null ? gDI32.CreateRectRgn(0, 0, 0, 0) : null;
            if (hRGN != null) {
                WinDef.HRGN hRGN3 = gDI32.CreateRectRgn(0, 0, 0, 0);
                try {
                    RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput(this, hRGN3, hRGN){
                        final WinDef.HRGN val$tempRgn;
                        final WinDef.HRGN val$region;
                        final W32WindowUtils this$0;
                        {
                            this.this$0 = w32WindowUtils;
                            this.val$tempRgn = hRGN;
                            this.val$region = hRGN2;
                        }

                        public boolean outputRange(int n, int n2, int n3, int n4) {
                            GDI32 gDI32 = GDI32.INSTANCE;
                            gDI32.SetRectRgn(this.val$tempRgn, n, n2, n + n3, n2 + n4);
                            return gDI32.CombineRgn(this.val$region, this.val$region, this.val$tempRgn, 2) != 0;
                        }
                    });
                } finally {
                    gDI32.DeleteObject(hRGN3);
                }
            }
            this.setWindowRegion(component, hRGN);
        }

        W32WindowUtils(1 var1_1) {
            this();
        }

        static WinDef.HWND access$400(W32WindowUtils w32WindowUtils, Component component) {
            return w32WindowUtils.getHWnd(component);
        }

        static boolean access$500(W32WindowUtils w32WindowUtils, Window window) {
            return w32WindowUtils.usingUpdateLayeredWindow(window);
        }

        static void access$600(W32WindowUtils w32WindowUtils, Window window, byte by) {
            w32WindowUtils.storeAlpha(window, by);
        }

        static byte access$700(W32WindowUtils w32WindowUtils, Window window) {
            return w32WindowUtils.getAlpha(window);
        }

        private class W32TransparentContentPane
        extends NativeWindowUtils.TransparentContentPane {
            private static final long serialVersionUID = 1L;
            private WinDef.HDC memDC;
            private WinDef.HBITMAP hBitmap;
            private Pointer pbits;
            private Dimension bitmapSize;
            final W32WindowUtils this$0;

            public W32TransparentContentPane(W32WindowUtils w32WindowUtils, Container container) {
                this.this$0 = w32WindowUtils;
                super(w32WindowUtils, container);
            }

            private void disposeBackingStore() {
                GDI32 gDI32 = GDI32.INSTANCE;
                if (this.hBitmap != null) {
                    gDI32.DeleteObject(this.hBitmap);
                    this.hBitmap = null;
                }
                if (this.memDC != null) {
                    gDI32.DeleteDC(this.memDC);
                    this.memDC = null;
                }
            }

            public void removeNotify() {
                super.removeNotify();
                this.disposeBackingStore();
            }

            public void setTransparent(boolean bl) {
                super.setTransparent(bl);
                if (!bl) {
                    this.disposeBackingStore();
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            protected void paintDirect(BufferedImage bufferedImage, Rectangle rectangle) {
                Window window = SwingUtilities.getWindowAncestor(this);
                GDI32 gDI32 = GDI32.INSTANCE;
                User32 user32 = User32.INSTANCE;
                int n = rectangle.x;
                int n2 = rectangle.y;
                Point point = SwingUtilities.convertPoint(this, n, n2, window);
                int n3 = rectangle.width;
                int n4 = rectangle.height;
                int n5 = window.getWidth();
                int n6 = window.getHeight();
                WinDef.HDC hDC = user32.GetDC(null);
                WinNT.HANDLE hANDLE = null;
                try {
                    Object object;
                    Object object2;
                    if (this.memDC == null) {
                        this.memDC = gDI32.CreateCompatibleDC(hDC);
                    }
                    if (this.hBitmap == null || !window.getSize().equals(this.bitmapSize)) {
                        if (this.hBitmap != null) {
                            gDI32.DeleteObject(this.hBitmap);
                            this.hBitmap = null;
                        }
                        object2 = new WinGDI.BITMAPINFO();
                        ((WinGDI.BITMAPINFO)object2).bmiHeader.biWidth = n5;
                        ((WinGDI.BITMAPINFO)object2).bmiHeader.biHeight = n6;
                        ((WinGDI.BITMAPINFO)object2).bmiHeader.biPlanes = 1;
                        ((WinGDI.BITMAPINFO)object2).bmiHeader.biBitCount = (short)32;
                        ((WinGDI.BITMAPINFO)object2).bmiHeader.biCompression = 0;
                        ((WinGDI.BITMAPINFO)object2).bmiHeader.biSizeImage = n5 * n6 * 4;
                        object = new PointerByReference();
                        this.hBitmap = gDI32.CreateDIBSection(this.memDC, (WinGDI.BITMAPINFO)object2, 0, (PointerByReference)object, null, 0);
                        this.pbits = ((PointerByReference)object).getValue();
                        this.bitmapSize = new Dimension(n5, n6);
                    }
                    hANDLE = gDI32.SelectObject(this.memDC, this.hBitmap);
                    object2 = bufferedImage.getData();
                    object = new int[4];
                    int[] nArray = new int[n3];
                    for (int i = 0; i < n4; ++i) {
                        int n7;
                        for (n7 = 0; n7 < n3; ++n7) {
                            ((Raster)object2).getPixel(n7, i, (int[])object);
                            int n8 = (object[3] & 0xFF) << 24;
                            int n9 = object[2] & 0xFF;
                            int n10 = (object[1] & 0xFF) << 8;
                            int n11 = (object[0] & 0xFF) << 16;
                            nArray[n7] = n8 | n9 | n10 | n11;
                        }
                        n7 = n6 - (point.y + i) - 1;
                        this.pbits.write((long)((n7 * n5 + point.x) * 4), nArray, 0, nArray.length);
                    }
                    WinUser.SIZE sIZE = new WinUser.SIZE();
                    sIZE.cx = window.getWidth();
                    sIZE.cy = window.getHeight();
                    WinUser.POINT pOINT = new WinUser.POINT();
                    pOINT.x = window.getX();
                    pOINT.y = window.getY();
                    WinUser.POINT pOINT2 = new WinUser.POINT();
                    WinUser.BLENDFUNCTION bLENDFUNCTION = new WinUser.BLENDFUNCTION();
                    WinDef.HWND hWND = W32WindowUtils.access$400(this.this$0, window);
                    ByteByReference byteByReference = new ByteByReference();
                    IntByReference intByReference = new IntByReference();
                    byte by = W32WindowUtils.access$700(this.this$0, window);
                    try {
                        if (user32.GetLayeredWindowAttributes(hWND, null, byteByReference, intByReference) && (intByReference.getValue() & 2) != 0) {
                            by = byteByReference.getValue();
                        }
                    } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                        // empty catch block
                    }
                    bLENDFUNCTION.SourceConstantAlpha = by;
                    bLENDFUNCTION.AlphaFormat = 1;
                    user32.UpdateLayeredWindow(hWND, hDC, pOINT, sIZE, this.memDC, pOINT2, 0, bLENDFUNCTION, 2);
                    user32.ReleaseDC(null, hDC);
                } catch (Throwable throwable) {
                    user32.ReleaseDC(null, hDC);
                    if (this.memDC != null && hANDLE != null) {
                        gDI32.SelectObject(this.memDC, hANDLE);
                    }
                    throw throwable;
                }
                if (this.memDC != null && hANDLE != null) {
                    gDI32.SelectObject(this.memDC, hANDLE);
                }
            }
        }
    }

    private static class Holder {
        public static boolean requiresVisible;
        public static final NativeWindowUtils INSTANCE;

        private Holder() {
        }

        static {
            if (Platform.isWindows()) {
                INSTANCE = new W32WindowUtils(null);
            } else if (Platform.isMac()) {
                INSTANCE = new MacWindowUtils(null);
            } else if (Platform.isX11()) {
                INSTANCE = new X11WindowUtils(null);
                requiresVisible = System.getProperty("java.version").matches("^1\\.4\\..*");
            } else {
                String string = System.getProperty("os.name");
                throw new UnsupportedOperationException("No support for " + string);
            }
        }
    }

    public static abstract class NativeWindowUtils {
        protected Window getWindow(Component component) {
            return component instanceof Window ? (Window)component : SwingUtilities.getWindowAncestor(component);
        }

        protected void whenDisplayable(Component component, Runnable runnable) {
            if (component.isDisplayable() && (!Holder.requiresVisible || component.isVisible())) {
                runnable.run();
            } else if (Holder.requiresVisible) {
                this.getWindow(component).addWindowListener(new WindowAdapter(this, runnable){
                    final Runnable val$action;
                    final NativeWindowUtils this$0;
                    {
                        this.this$0 = nativeWindowUtils;
                        this.val$action = runnable;
                    }

                    public void windowOpened(WindowEvent windowEvent) {
                        windowEvent.getWindow().removeWindowListener(this);
                        this.val$action.run();
                    }

                    public void windowClosed(WindowEvent windowEvent) {
                        windowEvent.getWindow().removeWindowListener(this);
                    }
                });
            } else {
                component.addHierarchyListener(new HierarchyListener(this, runnable){
                    final Runnable val$action;
                    final NativeWindowUtils this$0;
                    {
                        this.this$0 = nativeWindowUtils;
                        this.val$action = runnable;
                    }

                    public void hierarchyChanged(HierarchyEvent hierarchyEvent) {
                        if ((hierarchyEvent.getChangeFlags() & 2L) != 0L && hierarchyEvent.getComponent().isDisplayable()) {
                            hierarchyEvent.getComponent().removeHierarchyListener(this);
                            this.val$action.run();
                        }
                    }
                });
            }
        }

        protected Raster toRaster(Shape shape) {
            WritableRaster writableRaster = null;
            if (shape != MASK_NONE) {
                Rectangle rectangle = shape.getBounds();
                if (rectangle.width > 0 && rectangle.height > 0) {
                    BufferedImage bufferedImage = new BufferedImage(rectangle.x + rectangle.width, rectangle.y + rectangle.height, 12);
                    Graphics2D graphics2D = bufferedImage.createGraphics();
                    graphics2D.setColor(Color.black);
                    graphics2D.fillRect(0, 0, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
                    graphics2D.setColor(Color.white);
                    graphics2D.fill(shape);
                    writableRaster = bufferedImage.getRaster();
                }
            }
            return writableRaster;
        }

        protected Raster toRaster(Component component, Icon icon) {
            WritableRaster writableRaster = null;
            if (icon != null) {
                Rectangle rectangle = new Rectangle(0, 0, icon.getIconWidth(), icon.getIconHeight());
                BufferedImage bufferedImage = new BufferedImage(rectangle.width, rectangle.height, 2);
                Graphics2D graphics2D = bufferedImage.createGraphics();
                graphics2D.setComposite(AlphaComposite.Clear);
                graphics2D.fillRect(0, 0, rectangle.width, rectangle.height);
                graphics2D.setComposite(AlphaComposite.SrcOver);
                icon.paintIcon(component, graphics2D, 0, 0);
                writableRaster = bufferedImage.getAlphaRaster();
            }
            return writableRaster;
        }

        protected Shape toShape(Raster raster) {
            Area area = new Area(new Rectangle(0, 0, 0, 0));
            RasterRangesUtils.outputOccupiedRanges(raster, new RasterRangesUtils.RangesOutput(this, area){
                final Area val$area;
                final NativeWindowUtils this$0;
                {
                    this.this$0 = nativeWindowUtils;
                    this.val$area = area;
                }

                public boolean outputRange(int n, int n2, int n3, int n4) {
                    this.val$area.add(new Area(new Rectangle(n, n2, n3, n4)));
                    return false;
                }
            });
            return area;
        }

        public void setWindowAlpha(Window window, float f) {
        }

        public boolean isWindowAlphaSupported() {
            return true;
        }

        public GraphicsConfiguration getAlphaCompatibleGraphicsConfiguration() {
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
            return graphicsDevice.getDefaultConfiguration();
        }

        public void setWindowTransparent(Window window, boolean bl) {
        }

        protected void setDoubleBuffered(Component component, boolean bl) {
            if (component instanceof JComponent) {
                ((JComponent)component).setDoubleBuffered(bl);
            }
            if (component instanceof JRootPane && bl) {
                ((JRootPane)component).setDoubleBuffered(false);
            } else if (component instanceof Container) {
                Component[] componentArray = ((Container)component).getComponents();
                for (int i = 0; i < componentArray.length; ++i) {
                    this.setDoubleBuffered(componentArray[i], bl);
                }
            }
        }

        protected void setLayersTransparent(Window window, boolean bl) {
            Color color;
            Color color2 = color = bl ? new Color(0, 0, 0, 0) : null;
            if (window instanceof RootPaneContainer) {
                JComponent jComponent;
                RootPaneContainer rootPaneContainer = (RootPaneContainer)((Object)window);
                JRootPane jRootPane = rootPaneContainer.getRootPane();
                JLayeredPane jLayeredPane = jRootPane.getLayeredPane();
                Container container = jRootPane.getContentPane();
                JComponent jComponent2 = jComponent = container instanceof JComponent ? (JComponent)container : null;
                if (bl) {
                    jLayeredPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE, jLayeredPane.isOpaque());
                    jLayeredPane.setOpaque(true);
                    jRootPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE, jRootPane.isOpaque());
                    jRootPane.setOpaque(true);
                    if (jComponent != null) {
                        jComponent.putClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE, jComponent.isOpaque());
                        jComponent.setOpaque(true);
                    }
                    jRootPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_BG, jRootPane.getParent().getBackground());
                } else {
                    jLayeredPane.setOpaque(Boolean.TRUE.equals(jLayeredPane.getClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE)));
                    jLayeredPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE, null);
                    jRootPane.setOpaque(Boolean.TRUE.equals(jRootPane.getClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE)));
                    jRootPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE, null);
                    if (jComponent != null) {
                        jComponent.setOpaque(Boolean.TRUE.equals(jComponent.getClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE)));
                        jComponent.putClientProperty(WindowUtils.TRANSPARENT_OLD_OPAQUE, null);
                    }
                    color = (Color)jRootPane.getClientProperty(WindowUtils.TRANSPARENT_OLD_BG);
                    jRootPane.putClientProperty(WindowUtils.TRANSPARENT_OLD_BG, null);
                }
            }
            window.setBackground(color);
        }

        protected void setMask(Component component, Raster raster) {
            throw new UnsupportedOperationException("Window masking is not available");
        }

        protected void setWindowMask(Component component, Raster raster) {
            if (component.isLightweight()) {
                throw new IllegalArgumentException("Component must be heavyweight: " + component);
            }
            this.setMask(component, raster);
        }

        public void setWindowMask(Component component, Shape shape) {
            this.setWindowMask(component, this.toRaster(shape));
        }

        public void setWindowMask(Component component, Icon icon) {
            this.setWindowMask(component, this.toRaster(component, icon));
        }

        protected void setForceHeavyweightPopups(Window window, boolean bl) {
            if (!(window instanceof HeavyweightForcer)) {
                Window[] windowArray = window.getOwnedWindows();
                for (int i = 0; i < windowArray.length; ++i) {
                    if (!(windowArray[i] instanceof HeavyweightForcer)) continue;
                    if (bl) {
                        return;
                    }
                    windowArray[i].dispose();
                }
                Boolean bl2 = Boolean.valueOf(System.getProperty("jna.force_hw_popups", "true"));
                if (bl && bl2.booleanValue()) {
                    new HeavyweightForcer(window);
                }
            }
        }

        protected abstract class TransparentContentPane
        extends JPanel
        implements AWTEventListener {
            private static final long serialVersionUID = 1L;
            private boolean transparent;
            final NativeWindowUtils this$0;

            public TransparentContentPane(NativeWindowUtils nativeWindowUtils, Container container) {
                this.this$0 = nativeWindowUtils;
                super(new BorderLayout());
                this.add((Component)container, "Center");
                this.setTransparent(false);
                if (container instanceof JPanel) {
                    ((JComponent)container).setOpaque(true);
                }
            }

            public void addNotify() {
                super.addNotify();
                Toolkit.getDefaultToolkit().addAWTEventListener(this, 2L);
            }

            public void removeNotify() {
                Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                super.removeNotify();
            }

            public void setTransparent(boolean bl) {
                this.transparent = bl;
                this.setOpaque(!bl);
                this.setDoubleBuffered(!bl);
                this.repaint();
            }

            public void eventDispatched(AWTEvent aWTEvent) {
                if (aWTEvent.getID() == 300 && SwingUtilities.isDescendingFrom(((ContainerEvent)aWTEvent).getChild(), this)) {
                    Component component = ((ContainerEvent)aWTEvent).getChild();
                    this.this$0.setDoubleBuffered(component, true);
                }
            }

            public void paint(Graphics graphics) {
                if (this.transparent) {
                    Rectangle rectangle = graphics.getClipBounds();
                    int n = rectangle.width;
                    int n2 = rectangle.height;
                    if (this.getWidth() > 0 && this.getHeight() > 0) {
                        BufferedImage bufferedImage = new BufferedImage(n, n2, 3);
                        Graphics2D graphics2D = bufferedImage.createGraphics();
                        graphics2D.setComposite(AlphaComposite.Clear);
                        graphics2D.fillRect(0, 0, n, n2);
                        graphics2D.dispose();
                        graphics2D = bufferedImage.createGraphics();
                        graphics2D.translate(-rectangle.x, -rectangle.y);
                        super.paint(graphics2D);
                        graphics2D.dispose();
                        this.paintDirect(bufferedImage, rectangle);
                    }
                } else {
                    super.paint(graphics);
                }
            }

            protected abstract void paintDirect(BufferedImage var1, Rectangle var2);
        }
    }

    protected static class RepaintTrigger
    extends JComponent {
        private static final long serialVersionUID = 1L;
        private final Listener listener = this.createListener();
        private final JComponent content;
        private Rectangle dirty;

        public RepaintTrigger(JComponent jComponent) {
            this.content = jComponent;
        }

        public void addNotify() {
            super.addNotify();
            Window window = SwingUtilities.getWindowAncestor(this);
            this.setSize(this.getParent().getSize());
            window.addComponentListener(this.listener);
            window.addWindowListener(this.listener);
            Toolkit.getDefaultToolkit().addAWTEventListener(this.listener, 48L);
        }

        public void removeNotify() {
            Toolkit.getDefaultToolkit().removeAWTEventListener(this.listener);
            Window window = SwingUtilities.getWindowAncestor(this);
            window.removeComponentListener(this.listener);
            window.removeWindowListener(this.listener);
            super.removeNotify();
        }

        protected void paintComponent(Graphics graphics) {
            Rectangle rectangle = graphics.getClipBounds();
            if (this.dirty == null || !this.dirty.contains(rectangle)) {
                this.dirty = this.dirty == null ? rectangle : this.dirty.union(rectangle);
                this.content.repaint(this.dirty);
            } else {
                this.dirty = null;
            }
        }

        protected Listener createListener() {
            return new Listener(this);
        }

        static JComponent access$000(RepaintTrigger repaintTrigger) {
            return repaintTrigger.content;
        }

        protected class Listener
        extends WindowAdapter
        implements ComponentListener,
        HierarchyListener,
        AWTEventListener {
            final RepaintTrigger this$0;

            protected Listener(RepaintTrigger repaintTrigger) {
                this.this$0 = repaintTrigger;
            }

            public void windowOpened(WindowEvent windowEvent) {
                this.this$0.repaint();
            }

            public void componentHidden(ComponentEvent componentEvent) {
            }

            public void componentMoved(ComponentEvent componentEvent) {
            }

            public void componentResized(ComponentEvent componentEvent) {
                this.this$0.setSize(this.this$0.getParent().getSize());
                this.this$0.repaint();
            }

            public void componentShown(ComponentEvent componentEvent) {
                this.this$0.repaint();
            }

            public void hierarchyChanged(HierarchyEvent hierarchyEvent) {
                this.this$0.repaint();
            }

            public void eventDispatched(AWTEvent aWTEvent) {
                Component component;
                if (aWTEvent instanceof MouseEvent && (component = ((MouseEvent)aWTEvent).getComponent()) != null && SwingUtilities.isDescendingFrom(component, RepaintTrigger.access$000(this.this$0))) {
                    MouseEvent mouseEvent = SwingUtilities.convertMouseEvent(component, (MouseEvent)aWTEvent, RepaintTrigger.access$000(this.this$0));
                    Component component2 = SwingUtilities.getDeepestComponentAt(RepaintTrigger.access$000(this.this$0), mouseEvent.getX(), mouseEvent.getY());
                    if (component2 != null) {
                        this.this$0.setCursor(component2.getCursor());
                    }
                }
            }
        }
    }

    private static class HeavyweightForcer
    extends Window {
        private static final long serialVersionUID = 1L;
        private final boolean packed;

        public HeavyweightForcer(Window window) {
            super(window);
            this.pack();
            this.packed = true;
        }

        public boolean isVisible() {
            return this.packed;
        }

        public Rectangle getBounds() {
            return this.getOwner().getBounds();
        }
    }
}

