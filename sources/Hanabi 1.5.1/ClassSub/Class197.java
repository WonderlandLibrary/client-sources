package ClassSub;

public class Class197
{
    public static final int IMMEDIATE_RENDERER = 1;
    public static final int VERTEX_ARRAY_RENDERER = 2;
    public static final int DEFAULT_LINE_STRIP_RENDERER = 3;
    public static final int QUAD_BASED_LINE_STRIP_RENDERER = 4;
    private static Class311 renderer;
    private static Class127 lineStripRenderer;
    
    
    public static void setRenderer(final int n) {
        switch (n) {
            case 1: {
                setRenderer(new Class54());
            }
            case 2: {
                setRenderer(new Class149());
            }
            default: {
                throw new RuntimeException("Unknown renderer type: " + n);
            }
        }
    }
    
    public static void setLineStripRenderer(final int n) {
        switch (n) {
            case 3: {
                setLineStripRenderer(new Class16());
            }
            case 4: {
                setLineStripRenderer(new Class169());
            }
            default: {
                throw new RuntimeException("Unknown line strip renderer type: " + n);
            }
        }
    }
    
    public static void setLineStripRenderer(final Class127 lineStripRenderer) {
        Class197.lineStripRenderer = lineStripRenderer;
    }
    
    public static void setRenderer(final Class311 renderer) {
        Class197.renderer = renderer;
    }
    
    public static Class311 get() {
        return Class197.renderer;
    }
    
    public static Class127 getLineStripRenderer() {
        return Class197.lineStripRenderer;
    }
    
    static {
        Class197.renderer = new Class54();
        Class197.lineStripRenderer = new Class16();
    }
}
