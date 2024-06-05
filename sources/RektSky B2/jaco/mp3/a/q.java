package jaco.mp3.a;

import java.io.*;

public class q extends Exception
{
    private Throwable a;
    
    public q() {
    }
    
    public q(final String s, final Throwable a) {
        super(s);
        this.a = a;
    }
    
    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }
    
    @Override
    public void printStackTrace(final PrintStream printStream) {
        if (this.a == null) {
            super.printStackTrace(printStream);
            return;
        }
        this.a.printStackTrace();
    }
}
