// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

import java.io.BufferedWriter;

public class OutputThread extends Thread
{
    private PircBot _bot;
    private Queue _outQueue;
    
    OutputThread(final PircBot bot, final Queue outQueue) {
        this._bot = null;
        this._outQueue = null;
        this._bot = bot;
        this._outQueue = outQueue;
        this.setName(this.getClass() + "-Thread");
    }
    
    static void sendRawLine(final PircBot pircBot, final BufferedWriter bufferedWriter, String substring) {
        if (substring.length() > pircBot.getMaxLineLength() - 2) {
            substring = substring.substring(0, pircBot.getMaxLineLength() - 2);
        }
        synchronized (bufferedWriter) {
            try {
                bufferedWriter.write(substring + "\r\n");
                bufferedWriter.flush();
                pircBot.log(">>>" + substring);
            }
            catch (Exception ex) {}
        }
    }
    
    public void run() {
        try {
            int i = 1;
            while (i != 0) {
                Thread.sleep(this._bot.getMessageDelay());
                final String s = (String)this._outQueue.next();
                if (s != null) {
                    this._bot.sendRawLine(s);
                }
                else {
                    i = 0;
                }
            }
        }
        catch (InterruptedException ex) {}
    }
}
