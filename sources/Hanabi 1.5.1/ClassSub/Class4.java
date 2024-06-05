package ClassSub;

import java.security.*;

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
