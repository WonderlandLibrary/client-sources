package net.futureclient.client;

import java.net.Socket;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class V extends Thread
{
    private nH a;
    private String D;
    private ServerSocket k;
    
    public V(final nH a, final String d) {
        final ServerSocket k = null;
        super();
        this.k = k;
        this.a = a;
        this.D = d;
        try {
            (this.k = new ServerSocket(113)).setSoTimeout(60000);
        }
        catch (Exception ex) {
            this.a.A("*** Could not start the ident server on port 113.");
            return;
        }
        this.a.A("*** Ident server running on port 113 for the next 60 seconds...");
        this.setName(this.getClass() + "-Thread");
        this.start();
    }
    
    @Override
    public void run() {
        try {
            final Socket accept;
            (accept = this.k.accept()).setSoTimeout(60000);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
            final String line;
            if ((line = bufferedReader.readLine()) != null) {
                this.a.A(new StringBuilder().insert(0, "*** Ident request received: ").append(line).toString());
                final String string = new StringBuilder().insert(0, line).append(" : USERID : UNIX : ").append(this.D).toString();
                final BufferedWriter bufferedWriter2 = bufferedWriter;
                bufferedWriter2.write(string + "\r\n");
                bufferedWriter2.flush();
                this.a.A(new StringBuilder().insert(0, "*** Ident reply sent: ").append(string).toString());
                bufferedWriter.close();
            }
        }
        catch (Exception ex) {}
        V v;
        try {
            this.k.close();
            v = this;
        }
        catch (Exception ex2) {
            v = this;
        }
        v.a.A("*** The Ident server has been shut down.");
    }
    
    public static String M(String s) {
        final char c = '(';
        final char c2 = 'f';
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
