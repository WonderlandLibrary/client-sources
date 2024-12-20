// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

import java.io.InterruptedIOException;
import java.util.StringTokenizer;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.net.Socket;

public class InputThread extends Thread
{
    private PircBot _bot;
    private Socket _socket;
    private BufferedReader _breader;
    private BufferedWriter _bwriter;
    private boolean _isConnected;
    private boolean _disposed;
    public static final int MAX_LINE_LENGTH = 512;
    
    InputThread(final PircBot bot, final Socket socket, final BufferedReader breader, final BufferedWriter bwriter) {
        this._bot = null;
        this._socket = null;
        this._breader = null;
        this._bwriter = null;
        this._isConnected = true;
        this._disposed = false;
        this._bot = bot;
        this._socket = socket;
        this._breader = breader;
        this._bwriter = bwriter;
        this.setName(this.getClass() + "-Thread");
    }
    
    void sendRawLine(final String s) {
        OutputThread.sendRawLine(this._bot, this._bwriter, s);
    }
    
    boolean isConnected() {
        return this._isConnected;
    }
    
    public void run() {
        try {
            int i = 1;
            while (i != 0) {
                try {
                    String line;
                    while ((line = this._breader.readLine()) != null) {
                        try {
                            this._bot.handleLine(line);
                        }
                        catch (Throwable t) {
                            final StringWriter stringWriter = new StringWriter();
                            final PrintWriter printWriter = new PrintWriter(stringWriter);
                            t.printStackTrace(printWriter);
                            printWriter.flush();
                            final StringTokenizer stringTokenizer = new StringTokenizer(stringWriter.toString(), "\r\n");
                            synchronized (this._bot) {
                                this._bot.log("### Your implementation of PircBot is faulty and you have");
                                this._bot.log("### allowed an uncaught Exception or Error to propagate in your");
                                this._bot.log("### code. It may be possible for PircBot to continue operating");
                                this._bot.log("### normally. Here is the stack trace that was produced: -");
                                this._bot.log("### ");
                                while (stringTokenizer.hasMoreTokens()) {
                                    this._bot.log("### " + stringTokenizer.nextToken());
                                }
                            }
                        }
                    }
                    if (line != null) {
                        continue;
                    }
                    i = 0;
                }
                catch (InterruptedIOException ex) {
                    this.sendRawLine("PING " + System.currentTimeMillis() / 1000L);
                }
            }
        }
        catch (Exception ex2) {}
        try {
            this._socket.close();
        }
        catch (Exception ex3) {}
        if (!this._disposed) {
            this._bot.log("*** Disconnected.");
            this._isConnected = false;
            this._bot.onDisconnect();
        }
    }
    
    public void dispose() {
        try {
            this._disposed = true;
            this._socket.close();
        }
        catch (Exception ex) {}
    }
}
