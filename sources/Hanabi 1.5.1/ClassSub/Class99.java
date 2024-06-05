package ClassSub;

import java.security.*;

final class Class99 implements PrivilegedAction
{
    
    
    @Override
    public Object run() {
        final String property = System.getProperty("org.newdawn.slick.forceVerboseLog");
        if (property != null && property.equalsIgnoreCase("true")) {
            Class301.setForcedVerboseOn();
        }
        return null;
    }
}
