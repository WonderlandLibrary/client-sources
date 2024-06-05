package ClassSub;

import java.applet.*;

public abstract class Class12
{
    
    
    public abstract Object call(final String p0, final Object... p1) throws Class19;
    
    public abstract Object eval(final String p0) throws Class19;
    
    public abstract Object getMember(final String p0) throws Class19;
    
    public abstract void setMember(final String p0, final Object p1) throws Class19;
    
    public abstract void removeMember(final String p0) throws Class19;
    
    public abstract Object getSlot(final int p0) throws Class19;
    
    public abstract void setSlot(final int p0, final Object p1) throws Class19;
    
    public static Class12 getWindow(final Applet applet) throws Class19 {
        throw new Class19("Unexpected error: This method should not be used unless loaded from plugin.jar");
    }
}
