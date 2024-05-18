package wwe;

import java.io.*;
import java.net.*;

public class cIhE extends Thread
{
    private rfyb uycj;
    private String qrDo;
    private ServerSocket zhJf;
    
    cIhE(final rfyb uycj, final String qrDo) {
        super();
        this.zhJf = null;
        this.uycj = uycj;
        this.qrDo = qrDo;
        try {
            (this.zhJf = new ServerSocket(113)).setSoTimeout(60000);
        }
        catch (Exception ex) {
            this.uycj.jgkl("*** Could not start the ident server on port 113.");
            return;
        }
        this.uycj.jgkl("*** Ident server running on port 113 for the next 60 seconds...");
        this.setName(this.getClass() + "-Thread");
        this.start();
    }
    
    @Override
    public void run() {
        try {
            final Socket accept = this.zhJf.accept();
            accept.setSoTimeout(60000);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
            final String line = bufferedReader.readLine();
            if (line != null) {
                this.uycj.jgkl("*** Ident request received: " + line);
                final String string = line + " : USERID : UNIX : " + this.qrDo;
                bufferedWriter.write(string + "\r\n");
                bufferedWriter.flush();
                this.uycj.jgkl("*** Ident reply sent: " + string);
                bufferedWriter.close();
            }
            if (cIhE.\u2002\u2006\u200d\u2005) {
                throw null;
            }
        }
        catch (Exception ex) {}
        try {
            this.zhJf.close();
            if (cIhE.\u2002\u2006\u200d\u2005) {
                throw null;
            }
        }
        catch (Exception ex2) {}
        this.uycj.jgkl("*** The Ident server has been shut down.");
    }
}
