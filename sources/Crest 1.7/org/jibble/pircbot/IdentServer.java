// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

import java.net.Socket;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class IdentServer extends Thread
{
    private PircBot _bot;
    private String _login;
    private ServerSocket _ss;
    
    IdentServer(final PircBot bot, final String login) {
        this._ss = null;
        this._bot = bot;
        this._login = login;
        try {
            (this._ss = new ServerSocket(113)).setSoTimeout(60000);
        }
        catch (Exception ex) {
            this._bot.log("*** Could not start the ident server on port 113.");
            return;
        }
        this._bot.log("*** Ident server running on port 113 for the next 60 seconds...");
        this.setName(this.getClass() + "-Thread");
        this.start();
    }
    
    public void run() {
        try {
            final Socket accept = this._ss.accept();
            accept.setSoTimeout(60000);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
            final String line = bufferedReader.readLine();
            if (line != null) {
                this._bot.log("*** Ident request received: " + line);
                final String string = line + " : USERID : UNIX : " + this._login;
                bufferedWriter.write(string + "\r\n");
                bufferedWriter.flush();
                this._bot.log("*** Ident reply sent: " + string);
                bufferedWriter.close();
            }
        }
        catch (Exception ex) {}
        try {
            this._ss.close();
        }
        catch (Exception ex2) {}
        this._bot.log("*** The Ident server has been shut down.");
    }
}
