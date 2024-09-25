/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.tools.web;

import java.io.IOException;
import java.net.Socket;
import us.myles.viaversion.libs.javassist.tools.web.Webserver;

class ServiceThread
extends Thread {
    Webserver web;
    Socket sock;

    public ServiceThread(Webserver w, Socket s) {
        this.web = w;
        this.sock = s;
    }

    @Override
    public void run() {
        try {
            this.web.process(this.sock);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

