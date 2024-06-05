package ClassSub;

import java.io.*;
import java.util.*;

public class Class22 implements Class160
{
    public static PrintStream out;
    
    
    @Override
    public void error(final String s, final Throwable t) {
        this.error(s);
        this.error(t);
    }
    
    @Override
    public void error(final Throwable t) {
        Class22.out.println(new Date() + " ERROR:" + t.getMessage());
        t.printStackTrace(Class22.out);
    }
    
    @Override
    public void error(final String s) {
        Class22.out.println(new Date() + " ERROR:" + s);
    }
    
    @Override
    public void warn(final String s) {
        Class22.out.println(new Date() + " WARN:" + s);
    }
    
    @Override
    public void info(final String s) {
        Class22.out.println(new Date() + " INFO:" + s);
    }
    
    @Override
    public void debug(final String s) {
        Class22.out.println(new Date() + " DEBUG:" + s);
    }
    
    @Override
    public void warn(final String s, final Throwable t) {
        this.warn(s);
        t.printStackTrace(Class22.out);
    }
    
    static {
        Class22.out = System.out;
    }
}
