package ClassSub;

import java.security.*;

public class Class89
{
    private static boolean usePngLoader;
    private static boolean pngLoaderPropertyChecked;
    private static final String PNG_LOADER = "org.newdawn.slick.pngloader";
    
    
    private static void checkProperty() {
        final class Class4 implements PrivilegedAction
        {
            
            
            @Override
            public Object run() {
                if ("false".equalsIgnoreCase(System.getProperty("org.newdawn.slick.pngloader"))) {
                    Class89.access$002(false);
                }
                Class301.info("Use Java PNG Loader = " + Class89.access$000());
                return null;
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: athrow         
        //     4: getstatic       ClassSub/Class89.pngLoaderPropertyChecked:Z
        //     7: ifne            32
        //    10: ldc             1
        //    12: putstatic       ClassSub/Class89.pngLoaderPropertyChecked:Z
        //    15: new             LClassSub/Class4;
        //    18: dup            
        //    19: invokespecial   ClassSub/Class4.<init>:()V
        //    22: invokestatic    java/security/AccessController.doPrivileged:(Ljava/security/PrivilegedAction;)Ljava/lang/Object;
        //    25: pop            
        //    26: goto            32
        //    29: nop            
        //    30: athrow         
        //    31: astore_0       
        //    32: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  15     26     31     32     Ljava/lang/Throwable;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Class96 getImageDataFor(String lowerCase) {
        checkProperty();
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.endsWith(".tga")) {
            return new Class129();
        }
        if (lowerCase.endsWith(".png")) {
            final Class58 class58 = new Class58();
            if (Class89.usePngLoader) {
                class58.add(new Class321());
            }
            class58.add(new Class293());
            return class58;
        }
        return new Class293();
    }
    
    static boolean access$002(final boolean usePngLoader) {
        return Class89.usePngLoader = usePngLoader;
    }
    
    static boolean access$000() {
        return Class89.usePngLoader;
    }
    
    static {
        Class89.usePngLoader = true;
        Class89.pngLoaderPropertyChecked = false;
    }
}
