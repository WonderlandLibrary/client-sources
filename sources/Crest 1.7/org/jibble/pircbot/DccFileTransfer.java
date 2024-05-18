// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

import java.net.InetAddress;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.net.Socket;

public class DccFileTransfer
{
    public static final int BUFFER_SIZE = 1024;
    private PircBot _bot;
    private DccManager _manager;
    private String _nick;
    private String _login;
    private String _hostname;
    private String _type;
    private long _address;
    private int _port;
    private long _size;
    private boolean _received;
    private Socket _socket;
    private long _progress;
    private File _file;
    private int _timeout;
    private boolean _incoming;
    private long _packetDelay;
    private long _startTime;
    
    DccFileTransfer(final PircBot bot, final DccManager manager, final String nick, final String login, final String hostname, final String type, final String s, final long address, final int port, final long size) {
        this._login = null;
        this._hostname = null;
        this._socket = null;
        this._progress = 0L;
        this._file = null;
        this._timeout = 0;
        this._packetDelay = 0L;
        this._startTime = 0L;
        this._bot = bot;
        this._manager = manager;
        this._nick = nick;
        this._login = login;
        this._hostname = hostname;
        this._type = type;
        this._file = new File(s);
        this._address = address;
        this._port = port;
        this._size = size;
        this._received = false;
        this._incoming = true;
    }
    
    DccFileTransfer(final PircBot bot, final DccManager manager, final File file, final String nick, final int timeout) {
        this._login = null;
        this._hostname = null;
        this._socket = null;
        this._progress = 0L;
        this._file = null;
        this._timeout = 0;
        this._packetDelay = 0L;
        this._startTime = 0L;
        this._bot = bot;
        this._manager = manager;
        this._nick = nick;
        this._file = file;
        this._size = file.length();
        this._timeout = timeout;
        this._received = true;
        this._incoming = false;
    }
    
    public synchronized void receive(final File file, final boolean b) {
        if (!this._received) {
            this._received = true;
            this._file = file;
            if (this._type.equals("SEND") && b) {
                this._progress = file.length();
                if (this._progress == 0L) {
                    this.doReceive(file, false);
                }
                else {
                    this._bot.sendCTCPCommand(this._nick, "DCC RESUME file.ext " + this._port + " " + this._progress);
                    this._manager.addAwaitingResume(this);
                }
            }
            else {
                this._progress = file.length();
                this.doReceive(file, b);
            }
        }
    }
    
    void doReceive(final File file, final boolean b) {
        new Thread() {
            public void run() {
                BufferedOutputStream bufferedOutputStream = null;
                Exception ex = null;
                try {
                    final int[] longToIp = DccFileTransfer.this._bot.longToIp(DccFileTransfer.this._address);
                    DccFileTransfer.this._socket = new Socket(longToIp[0] + "." + longToIp[1] + "." + longToIp[2] + "." + longToIp[3], DccFileTransfer.this._port);
                    DccFileTransfer.this._socket.setSoTimeout(30000);
                    DccFileTransfer.this._startTime = System.currentTimeMillis();
                    DccFileTransfer.this._manager.removeAwaitingResume(DccFileTransfer.this);
                    final BufferedInputStream bufferedInputStream = new BufferedInputStream(DccFileTransfer.this._socket.getInputStream());
                    final BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(DccFileTransfer.this._socket.getOutputStream());
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file.getCanonicalPath(), b));
                    final byte[] array = new byte[1024];
                    final byte[] array2 = new byte[4];
                    int read;
                    while ((read = bufferedInputStream.read(array, 0, array.length)) != -1) {
                        bufferedOutputStream.write(array, 0, read);
                        DccFileTransfer.this._progress += read;
                        array2[0] = (byte)(DccFileTransfer.this._progress >> 24 & 0xFFL);
                        array2[1] = (byte)(DccFileTransfer.this._progress >> 16 & 0xFFL);
                        array2[2] = (byte)(DccFileTransfer.this._progress >> 8 & 0xFFL);
                        array2[3] = (byte)(DccFileTransfer.this._progress >> 0 & 0xFFL);
                        bufferedOutputStream2.write(array2);
                        bufferedOutputStream2.flush();
                        DccFileTransfer.this.delay();
                    }
                    bufferedOutputStream.flush();
                }
                catch (Exception ex2) {
                    ex = ex2;
                }
                finally {
                    try {
                        bufferedOutputStream.close();
                        DccFileTransfer.this._socket.close();
                    }
                    catch (Exception ex3) {}
                }
                DccFileTransfer.this._bot.onFileTransferFinished(DccFileTransfer.this, ex);
            }
        }.start();
    }
    
    void doSend(final boolean b) {
        new Thread() {
            public void run() {
                BufferedInputStream bufferedInputStream = null;
                Exception ex = null;
                try {
                    ServerSocket serverSocket = null;
                    final int[] dccPorts = DccFileTransfer.this._bot.getDccPorts();
                    if (dccPorts == null) {
                        serverSocket = new ServerSocket(0);
                    }
                    else {
                        int i = 0;
                        while (i < dccPorts.length) {
                            try {
                                serverSocket = new ServerSocket(dccPorts[i]);
                            }
                            catch (Exception ex3) {
                                ++i;
                                continue;
                            }
                            break;
                        }
                        if (serverSocket == null) {
                            throw new IOException("All ports returned by getDccPorts() are in use.");
                        }
                    }
                    serverSocket.setSoTimeout(DccFileTransfer.this._timeout);
                    DccFileTransfer.this._port = serverSocket.getLocalPort();
                    InetAddress inetAddress = DccFileTransfer.this._bot.getDccInetAddress();
                    if (inetAddress == null) {
                        inetAddress = DccFileTransfer.this._bot.getInetAddress();
                    }
                    final long ipToLong = DccFileTransfer.this._bot.ipToLong(inetAddress.getAddress());
                    final String replace = DccFileTransfer.this._file.getName().replace(' ', '_').replace('\t', '_');
                    if (b) {
                        DccFileTransfer.this._manager.addAwaitingResume(DccFileTransfer.this);
                    }
                    DccFileTransfer.this._bot.sendCTCPCommand(DccFileTransfer.this._nick, "DCC SEND " + replace + " " + ipToLong + " " + DccFileTransfer.this._port + " " + DccFileTransfer.this._file.length());
                    DccFileTransfer.this._socket = serverSocket.accept();
                    DccFileTransfer.this._socket.setSoTimeout(30000);
                    DccFileTransfer.this._startTime = System.currentTimeMillis();
                    if (b) {
                        DccFileTransfer.this._manager.removeAwaitingResume(DccFileTransfer.this);
                    }
                    serverSocket.close();
                    final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(DccFileTransfer.this._socket.getOutputStream());
                    final BufferedInputStream bufferedInputStream2 = new BufferedInputStream(DccFileTransfer.this._socket.getInputStream());
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(DccFileTransfer.this._file));
                    if (DccFileTransfer.this._progress > 0L) {
                        for (long n = 0L; n < DccFileTransfer.this._progress; n += bufferedInputStream.skip(DccFileTransfer.this._progress - n)) {}
                    }
                    final byte[] array = new byte[1024];
                    final byte[] array2 = new byte[4];
                    int read;
                    while ((read = bufferedInputStream.read(array, 0, array.length)) != -1) {
                        bufferedOutputStream.write(array, 0, read);
                        bufferedOutputStream.flush();
                        bufferedInputStream2.read(array2, 0, array2.length);
                        DccFileTransfer.this._progress += read;
                        DccFileTransfer.this.delay();
                    }
                }
                catch (Exception ex2) {
                    ex = ex2;
                }
                finally {
                    try {
                        bufferedInputStream.close();
                        DccFileTransfer.this._socket.close();
                    }
                    catch (Exception ex4) {}
                }
                DccFileTransfer.this._bot.onFileTransferFinished(DccFileTransfer.this, ex);
            }
        }.start();
    }
    
    void setProgress(final long progress) {
        this._progress = progress;
    }
    
    private void delay() {
        if (this._packetDelay > 0L) {
            try {
                Thread.sleep(this._packetDelay);
            }
            catch (InterruptedException ex) {}
        }
    }
    
    public String getNick() {
        return this._nick;
    }
    
    public String getLogin() {
        return this._login;
    }
    
    public String getHostname() {
        return this._hostname;
    }
    
    public File getFile() {
        return this._file;
    }
    
    public int getPort() {
        return this._port;
    }
    
    public boolean isIncoming() {
        return this._incoming;
    }
    
    public boolean isOutgoing() {
        return !this.isIncoming();
    }
    
    public void setPacketDelay(final long packetDelay) {
        this._packetDelay = packetDelay;
    }
    
    public long getPacketDelay() {
        return this._packetDelay;
    }
    
    public long getSize() {
        return this._size;
    }
    
    public long getProgress() {
        return this._progress;
    }
    
    public double getProgressPercentage() {
        return 100.0 * (this.getProgress() / this.getSize());
    }
    
    public void close() {
        try {
            this._socket.close();
        }
        catch (Exception ex) {}
    }
    
    public long getTransferRate() {
        final long n = (System.currentTimeMillis() - this._startTime) / 1000L;
        if (n <= 0L) {
            return 0L;
        }
        return this.getProgress() / n;
    }
    
    public long getNumericalAddress() {
        return this._address;
    }
}
