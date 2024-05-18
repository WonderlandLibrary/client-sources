package HORIZON-6-0-SKIDPROTECTION;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import java.net.DatagramSocket;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLanServerPing extends Thread
{
    private static final AtomicInteger HorizonCode_Horizon_È;
    private static final Logger Â;
    private final String Ý;
    private final DatagramSocket Ø­áŒŠá;
    private boolean Âµá€;
    private final String Ó;
    private static final String à = "CL_00001137";
    
    static {
        HorizonCode_Horizon_È = new AtomicInteger(0);
        Â = LogManager.getLogger();
    }
    
    public ThreadLanServerPing(final String p_i1321_1_, final String p_i1321_2_) throws IOException {
        super("LanServerPinger #" + ThreadLanServerPing.HorizonCode_Horizon_È.incrementAndGet());
        this.Âµá€ = true;
        this.Ý = p_i1321_1_;
        this.Ó = p_i1321_2_;
        this.setDaemon(true);
        this.Ø­áŒŠá = new DatagramSocket();
    }
    
    @Override
    public void run() {
        final String var1 = HorizonCode_Horizon_È(this.Ý, this.Ó);
        final byte[] var2 = var1.getBytes();
        while (!this.isInterrupted() && this.Âµá€) {
            try {
                final InetAddress var3 = InetAddress.getByName("224.0.2.60");
                final DatagramPacket var4 = new DatagramPacket(var2, var2.length, var3, 4445);
                this.Ø­áŒŠá.send(var4);
            }
            catch (IOException var5) {
                ThreadLanServerPing.Â.warn("LanServerPinger: " + var5.getMessage());
                break;
            }
            try {
                Thread.sleep(1500L);
            }
            catch (InterruptedException ex) {}
        }
    }
    
    @Override
    public void interrupt() {
        super.interrupt();
        this.Âµá€ = false;
    }
    
    public static String HorizonCode_Horizon_È(final String p_77525_0_, final String p_77525_1_) {
        return "[MOTD]" + p_77525_0_ + "[/MOTD][AD]" + p_77525_1_ + "[/AD]";
    }
    
    public static String HorizonCode_Horizon_È(final String p_77524_0_) {
        final int var1 = p_77524_0_.indexOf("[MOTD]");
        if (var1 < 0) {
            return "missing no";
        }
        final int var2 = p_77524_0_.indexOf("[/MOTD]", var1 + "[MOTD]".length());
        return (var2 < var1) ? "missing no" : p_77524_0_.substring(var1 + "[MOTD]".length(), var2);
    }
    
    public static String Â(final String p_77523_0_) {
        final int var1 = p_77523_0_.indexOf("[/MOTD]");
        if (var1 < 0) {
            return null;
        }
        final int var2 = p_77523_0_.indexOf("[/MOTD]", var1 + "[/MOTD]".length());
        if (var2 >= 0) {
            return null;
        }
        final int var3 = p_77523_0_.indexOf("[AD]", var1 + "[/MOTD]".length());
        if (var3 < 0) {
            return null;
        }
        final int var4 = p_77523_0_.indexOf("[/AD]", var3 + "[AD]".length());
        return (var4 < var3) ? null : p_77523_0_.substring(var3 + "[AD]".length(), var4);
    }
}
