package net.futureclient.client;

import java.io.InterruptedIOException;
import java.util.StringTokenizer;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class W extends Thread
{
    private boolean j;
    private nH K;
    private BufferedReader M;
    private BufferedWriter d;
    private boolean a;
    private Socket D;
    public static final int k = 512;
    
    public W(final nH k, final Socket d, final BufferedReader m, final BufferedWriter d2) {
        final boolean j = false;
        final boolean a = true;
        final BufferedWriter d3 = null;
        final BufferedReader i = null;
        final Socket d4 = null;
        final nH l = null;
        super();
        this.K = l;
        this.D = d4;
        this.M = i;
        this.d = d3;
        this.a = a;
        this.j = j;
        this.K = k;
        this.D = d;
        this.M = m;
        this.d = d2;
        this.setName(this.getClass() + "-Thread");
    }
    
    @Override
    public void run() {
        try {
            int i = 1;
            while (i != 0) {
                try {
                    String line;
                    while (true) {
                        W w = this;
                        while ((line = w.M.readLine()) != null) {
                            try {
                                this.K.c(line);
                                w = this;
                                continue;
                            }
                            catch (Throwable t2) {
                                final StringWriter stringWriter = new StringWriter();
                                final PrintWriter printWriter = new PrintWriter(stringWriter);
                                final Throwable t = t2;
                                final PrintWriter printWriter2 = printWriter;
                                t.printStackTrace(printWriter2);
                                printWriter2.flush();
                                final StringTokenizer stringTokenizer = new StringTokenizer(stringWriter.toString(), "\r\n");
                                synchronized (this.K) {
                                    this.K.A("### Your implementation of PircBot is faulty and you have");
                                    this.K.A("### allowed an uncaught Exception or Error to propagate in your");
                                    this.K.A("### code. It may be possible for PircBot to continue operating");
                                    this.K.A("### normally. Here is the stack trace that was produced: -");
                                    this.K.A("### ");
                                    StringTokenizer stringTokenizer2 = stringTokenizer;
                                    while (stringTokenizer2.hasMoreTokens()) {
                                        this.K.A(new StringBuilder().insert(0, "### ").append((stringTokenizer2 = stringTokenizer).nextToken()).toString());
                                    }
                                }
                            }
                            break;
                        }
                        break;
                    }
                    if (line != null) {
                        continue;
                    }
                    i = 0;
                }
                catch (InterruptedIOException ex) {
                    this.M("PING " + System.currentTimeMillis() / 1000L);
                }
            }
        }
        catch (Exception ex2) {}
        W w2;
        try {
            this.D.close();
            w2 = this;
        }
        catch (Exception ex3) {
            w2 = this;
        }
        if (!w2.j) {
            final boolean a = false;
            this.K.A("*** Disconnected.");
            this.a = a;
            this.K.c();
        }
    }
    
    public boolean M() {
        return this.a;
    }
    
    public void M(final String s) {
        t.M(this.K, this.d, s);
    }
    
    public void M() {
        try {
            this.j = true;
            this.D.close();
        }
        catch (Exception ex) {}
    }
    
    public static String M(String s) {
        final char c = ')';
        final char c2 = '$';
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n;
        int i = n = length - 1;
        final char[] array2 = array;
        final char c3 = c2;
        final char c4 = c;
        while (i >= 0) {
            final char[] array3 = array2;
            final String s2 = s;
            final int n2 = n;
            final char char1 = s2.charAt(n2);
            --n;
            array3[n2] = (char)(char1 ^ c4);
            if (n < 0) {
                break;
            }
            final char[] array4 = array2;
            final String s3 = s;
            final int n3 = n--;
            array4[n3] = (char)(s3.charAt(n3) ^ c3);
            i = n;
        }
        return new String(array2);
    }
}
