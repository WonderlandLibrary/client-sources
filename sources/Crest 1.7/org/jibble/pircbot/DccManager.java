// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

import java.util.StringTokenizer;
import java.util.Vector;

public class DccManager
{
    private PircBot _bot;
    private Vector _awaitingResume;
    
    DccManager(final PircBot bot) {
        this._awaitingResume = new Vector();
        this._bot = bot;
    }
    
    boolean processRequest(final String s, final String s2, final String s3, final String s4) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s4);
        stringTokenizer.nextToken();
        final String nextToken = stringTokenizer.nextToken();
        final String nextToken2 = stringTokenizer.nextToken();
        if (nextToken.equals("SEND")) {
            final long long1 = Long.parseLong(stringTokenizer.nextToken());
            final int int1 = Integer.parseInt(stringTokenizer.nextToken());
            long long2 = -1L;
            try {
                long2 = Long.parseLong(stringTokenizer.nextToken());
            }
            catch (Exception ex) {}
            this._bot.onIncomingFileTransfer(new DccFileTransfer(this._bot, this, s, s2, s3, nextToken, nextToken2, long1, int1, long2));
        }
        else if (nextToken.equals("RESUME")) {
            final int int2 = Integer.parseInt(stringTokenizer.nextToken());
            final long long3 = Long.parseLong(stringTokenizer.nextToken());
            DccFileTransfer dccFileTransfer = null;
            synchronized (this._awaitingResume) {
                for (int i = 0; i < this._awaitingResume.size(); ++i) {
                    dccFileTransfer = (DccFileTransfer)this._awaitingResume.elementAt(i);
                    if (dccFileTransfer.getNick().equals(s) && dccFileTransfer.getPort() == int2) {
                        this._awaitingResume.removeElementAt(i);
                        break;
                    }
                }
            }
            if (dccFileTransfer != null) {
                dccFileTransfer.setProgress(long3);
                this._bot.sendCTCPCommand(s, "DCC ACCEPT file.ext " + int2 + " " + long3);
            }
        }
        else if (nextToken.equals("ACCEPT")) {
            final int int3 = Integer.parseInt(stringTokenizer.nextToken());
            Long.parseLong(stringTokenizer.nextToken());
            DccFileTransfer dccFileTransfer2 = null;
            synchronized (this._awaitingResume) {
                for (int j = 0; j < this._awaitingResume.size(); ++j) {
                    dccFileTransfer2 = (DccFileTransfer)this._awaitingResume.elementAt(j);
                    if (dccFileTransfer2.getNick().equals(s) && dccFileTransfer2.getPort() == int3) {
                        this._awaitingResume.removeElementAt(j);
                        break;
                    }
                }
            }
            if (dccFileTransfer2 != null) {
                dccFileTransfer2.doReceive(dccFileTransfer2.getFile(), true);
            }
        }
        else {
            if (!nextToken.equals("CHAT")) {
                return false;
            }
            new Thread() {
                private final /* synthetic */ DccChat val$chat = new DccChat(DccManager.this._bot, s, s2, s3, Long.parseLong(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));
                
                public void run() {
                    DccManager.this._bot.onIncomingChatRequest(this.val$chat);
                }
            }.start();
        }
        return true;
    }
    
    void addAwaitingResume(final DccFileTransfer dccFileTransfer) {
        synchronized (this._awaitingResume) {
            this._awaitingResume.addElement(dccFileTransfer);
        }
    }
    
    void removeAwaitingResume(final DccFileTransfer dccFileTransfer) {
        this._awaitingResume.removeElement(dccFileTransfer);
    }
}
