package net.minecraft.src;

import java.util.concurrent.atomic.*;
import javax.crypto.*;
import java.security.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class TcpConnection implements INetworkManager
{
    public static AtomicInteger field_74471_a;
    public static AtomicInteger field_74469_b;
    private final Object sendQueueLock;
    private final ILogAgent field_98215_i;
    private Socket networkSocket;
    private final SocketAddress remoteSocketAddress;
    private volatile DataInputStream socketInputStream;
    private volatile DataOutputStream socketOutputStream;
    private volatile boolean isRunning;
    private volatile boolean isTerminating;
    private List readPackets;
    private List dataPackets;
    private List chunkDataPackets;
    private NetHandler theNetHandler;
    private boolean isServerTerminating;
    private Thread writeThread;
    private Thread readThread;
    private String terminationReason;
    private Object[] field_74480_w;
    private int field_74490_x;
    private int sendQueueByteLength;
    public static int[] field_74470_c;
    public static int[] field_74467_d;
    public int field_74468_e;
    boolean isInputBeingDecrypted;
    boolean isOutputEncrypted;
    private SecretKey sharedKeyForEncryption;
    private PrivateKey field_74463_A;
    private int chunkDataPacketsDelay;
    
    static {
        TcpConnection.field_74471_a = new AtomicInteger();
        TcpConnection.field_74469_b = new AtomicInteger();
        TcpConnection.field_74470_c = new int[256];
        TcpConnection.field_74467_d = new int[256];
    }
    
    public TcpConnection(final ILogAgent par1ILogAgent, final Socket par2Socket, final String par3Str, final NetHandler par4NetHandler) throws IOException {
        this(par1ILogAgent, par2Socket, par3Str, par4NetHandler, null);
    }
    
    public TcpConnection(final ILogAgent par1ILogAgent, final Socket par2Socket, final String par3Str, final NetHandler par4NetHandler, final PrivateKey par5PrivateKey) throws IOException {
        this.sendQueueLock = new Object();
        this.isRunning = true;
        this.isTerminating = false;
        this.readPackets = Collections.synchronizedList(new ArrayList<Object>());
        this.dataPackets = Collections.synchronizedList(new ArrayList<Object>());
        this.chunkDataPackets = Collections.synchronizedList(new ArrayList<Object>());
        this.isServerTerminating = false;
        this.terminationReason = "";
        this.field_74490_x = 0;
        this.sendQueueByteLength = 0;
        this.field_74468_e = 0;
        this.isInputBeingDecrypted = false;
        this.isOutputEncrypted = false;
        this.sharedKeyForEncryption = null;
        this.field_74463_A = null;
        this.chunkDataPacketsDelay = 50;
        this.field_74463_A = par5PrivateKey;
        this.networkSocket = par2Socket;
        this.field_98215_i = par1ILogAgent;
        this.remoteSocketAddress = par2Socket.getRemoteSocketAddress();
        this.theNetHandler = par4NetHandler;
        try {
            par2Socket.setSoTimeout(30000);
            par2Socket.setTrafficClass(24);
        }
        catch (SocketException var7) {
            System.err.println(var7.getMessage());
        }
        this.socketInputStream = new DataInputStream(par2Socket.getInputStream());
        this.socketOutputStream = new DataOutputStream(new BufferedOutputStream(par2Socket.getOutputStream(), 5120));
        this.readThread = new TcpReaderThread(this, String.valueOf(par3Str) + " read thread");
        this.writeThread = new TcpWriterThread(this, String.valueOf(par3Str) + " write thread");
        this.readThread.start();
        this.writeThread.start();
    }
    
    @Override
    public void closeConnections() {
        this.wakeThreads();
        this.writeThread = null;
        this.readThread = null;
    }
    
    @Override
    public void setNetHandler(final NetHandler par1NetHandler) {
        this.theNetHandler = par1NetHandler;
    }
    
    @Override
    public void addToSendQueue(final Packet par1Packet) {
        if (!this.isServerTerminating) {
            final Object var2 = this.sendQueueLock;
            final Object var3 = this.sendQueueLock;
            synchronized (this.sendQueueLock) {
                this.sendQueueByteLength += par1Packet.getPacketSize() + 1;
                this.dataPackets.add(par1Packet);
            }
            // monitorexit(this.sendQueueLock)
        }
    }
    
    private boolean sendPacket() {
        boolean var1 = false;
        try {
            if (this.field_74468_e == 0 || (!this.dataPackets.isEmpty() && System.currentTimeMillis() - this.dataPackets.get(0).creationTimeMillis >= this.field_74468_e)) {
                final Packet var2 = this.func_74460_a(false);
                if (var2 != null) {
                    Packet.writePacket(var2, this.socketOutputStream);
                    if (var2 instanceof Packet252SharedKey && !this.isOutputEncrypted) {
                        if (!this.theNetHandler.isServerHandler()) {
                            this.sharedKeyForEncryption = ((Packet252SharedKey)var2).getSharedKey();
                        }
                        this.encryptOuputStream();
                    }
                    final int[] var3 = TcpConnection.field_74467_d;
                    final int var4 = var2.getPacketId();
                    final int[] array = var3;
                    final int n = var4;
                    array[n] += var2.getPacketSize() + 1;
                    var1 = true;
                }
            }
            if (this.chunkDataPacketsDelay-- <= 0 && (this.field_74468_e == 0 || (!this.chunkDataPackets.isEmpty() && System.currentTimeMillis() - this.chunkDataPackets.get(0).creationTimeMillis >= this.field_74468_e))) {
                final Packet var2 = this.func_74460_a(true);
                if (var2 != null) {
                    Packet.writePacket(var2, this.socketOutputStream);
                    final int[] var3 = TcpConnection.field_74467_d;
                    final int var4 = var2.getPacketId();
                    final int[] array2 = var3;
                    final int n2 = var4;
                    array2[n2] += var2.getPacketSize() + 1;
                    this.chunkDataPacketsDelay = 0;
                    var1 = true;
                }
            }
            return var1;
        }
        catch (Exception var5) {
            if (!this.isTerminating) {
                this.onNetworkError(var5);
            }
            return false;
        }
    }
    
    private Packet func_74460_a(final boolean par1) {
        Packet var2 = null;
        final List var3 = par1 ? this.chunkDataPackets : this.dataPackets;
        final Object var4 = this.sendQueueLock;
        final Object var5 = this.sendQueueLock;
        synchronized (this.sendQueueLock) {
            while (!var3.isEmpty() && var2 == null) {
                var2 = var3.remove(0);
                this.sendQueueByteLength -= var2.getPacketSize() + 1;
                if (this.func_74454_a(var2, par1)) {
                    var2 = null;
                }
            }
            // monitorexit(this.sendQueueLock)
            return var2;
        }
    }
    
    private boolean func_74454_a(final Packet par1Packet, final boolean par2) {
        if (!par1Packet.isRealPacket()) {
            return false;
        }
        final List var3 = par2 ? this.chunkDataPackets : this.dataPackets;
        for (final Packet var5 : var3) {
            if (var5.getPacketId() == par1Packet.getPacketId()) {
                return par1Packet.containsSameEntityIDAs(var5);
            }
        }
        return false;
    }
    
    @Override
    public void wakeThreads() {
        if (this.readThread != null) {
            this.readThread.interrupt();
        }
        if (this.writeThread != null) {
            this.writeThread.interrupt();
        }
    }
    
    private boolean readPacket() {
        boolean var1 = false;
        try {
            final Packet var2 = Packet.readPacket(this.field_98215_i, this.socketInputStream, this.theNetHandler.isServerHandler(), this.networkSocket);
            if (var2 != null) {
                if (var2 instanceof Packet252SharedKey && !this.isInputBeingDecrypted) {
                    if (this.theNetHandler.isServerHandler()) {
                        this.sharedKeyForEncryption = ((Packet252SharedKey)var2).getSharedKey(this.field_74463_A);
                    }
                    this.decryptInputStream();
                }
                final int[] var3 = TcpConnection.field_74470_c;
                final int var4 = var2.getPacketId();
                final int[] array = var3;
                final int n = var4;
                array[n] += var2.getPacketSize() + 1;
                if (!this.isServerTerminating) {
                    if (var2.canProcessAsync() && this.theNetHandler.canProcessPacketsAsync()) {
                        this.field_74490_x = 0;
                        var2.processPacket(this.theNetHandler);
                    }
                    else {
                        this.readPackets.add(var2);
                    }
                }
                var1 = true;
            }
            else {
                this.networkShutdown("disconnect.endOfStream", new Object[0]);
            }
            return var1;
        }
        catch (Exception var5) {
            if (!this.isTerminating) {
                this.onNetworkError(var5);
            }
            return false;
        }
    }
    
    private void onNetworkError(final Exception par1Exception) {
        par1Exception.printStackTrace();
        this.networkShutdown("disconnect.genericReason", "Internal exception: " + par1Exception.toString());
    }
    
    @Override
    public void networkShutdown(final String par1Str, final Object... par2ArrayOfObj) {
        if (this.isRunning) {
            this.isTerminating = true;
            this.terminationReason = par1Str;
            this.field_74480_w = par2ArrayOfObj;
            this.isRunning = false;
            new TcpMasterThread(this).start();
            try {
                this.socketInputStream.close();
            }
            catch (Throwable t) {}
            try {
                this.socketOutputStream.close();
            }
            catch (Throwable t2) {}
            try {
                this.networkSocket.close();
            }
            catch (Throwable t3) {}
            this.socketInputStream = null;
            this.socketOutputStream = null;
            this.networkSocket = null;
        }
    }
    
    @Override
    public void processReadPackets() {
        if (this.sendQueueByteLength > 2097152) {
            this.networkShutdown("disconnect.overflow", new Object[0]);
        }
        if (this.readPackets.isEmpty()) {
            if (this.field_74490_x++ == 1200) {
                this.networkShutdown("disconnect.timeout", new Object[0]);
            }
        }
        else {
            this.field_74490_x = 0;
        }
        int var1 = 1000;
        while (!this.readPackets.isEmpty() && var1-- >= 0) {
            final Packet var2 = this.readPackets.remove(0);
            var2.processPacket(this.theNetHandler);
        }
        this.wakeThreads();
        if (this.isTerminating && this.readPackets.isEmpty()) {
            this.theNetHandler.handleErrorMessage(this.terminationReason, this.field_74480_w);
        }
    }
    
    @Override
    public SocketAddress getSocketAddress() {
        return this.remoteSocketAddress;
    }
    
    @Override
    public void serverShutdown() {
        if (!this.isServerTerminating) {
            this.wakeThreads();
            this.isServerTerminating = true;
            this.readThread.interrupt();
            new TcpMonitorThread(this).start();
        }
    }
    
    private void decryptInputStream() throws IOException {
        this.isInputBeingDecrypted = true;
        final InputStream var1 = this.networkSocket.getInputStream();
        this.socketInputStream = new DataInputStream(CryptManager.decryptInputStream(this.sharedKeyForEncryption, var1));
    }
    
    private void encryptOuputStream() throws IOException {
        this.socketOutputStream.flush();
        this.isOutputEncrypted = true;
        final BufferedOutputStream var1 = new BufferedOutputStream(CryptManager.encryptOuputStream(this.sharedKeyForEncryption, this.networkSocket.getOutputStream()), 5120);
        this.socketOutputStream = new DataOutputStream(var1);
    }
    
    @Override
    public int packetSize() {
        return this.chunkDataPackets.size();
    }
    
    public Socket getSocket() {
        return this.networkSocket;
    }
    
    static boolean isRunning(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.isRunning;
    }
    
    static boolean isServerTerminating(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.isServerTerminating;
    }
    
    static boolean readNetworkPacket(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.readPacket();
    }
    
    static boolean sendNetworkPacket(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.sendPacket();
    }
    
    static DataOutputStream getOutputStream(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.socketOutputStream;
    }
    
    static boolean isTerminating(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.isTerminating;
    }
    
    static void sendError(final TcpConnection par0TcpConnection, final Exception par1Exception) {
        par0TcpConnection.onNetworkError(par1Exception);
    }
    
    static Thread getReadThread(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.readThread;
    }
    
    static Thread getWriteThread(final TcpConnection par0TcpConnection) {
        return par0TcpConnection.writeThread;
    }
}
