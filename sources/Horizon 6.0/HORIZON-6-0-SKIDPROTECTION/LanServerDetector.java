package HORIZON-6-0-SKIDPROTECTION;

import java.net.SocketTimeoutException;
import java.net.DatagramPacket;
import java.io.IOException;
import java.net.MulticastSocket;
import java.util.Iterator;
import java.net.InetAddress;
import java.util.Collections;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class LanServerDetector
{
    private static final AtomicInteger HorizonCode_Horizon_È;
    private static final Logger Â;
    private static final String Ý = "CL_00001133";
    
    static {
        HorizonCode_Horizon_È = new AtomicInteger(0);
        Â = LogManager.getLogger();
    }
    
    public static class HorizonCode_Horizon_È
    {
        private String HorizonCode_Horizon_È;
        private String Â;
        private long Ý;
        private static final String Ø­áŒŠá = "CL_00001134";
        
        public HorizonCode_Horizon_È(final String p_i1319_1_, final String p_i1319_2_) {
            this.HorizonCode_Horizon_È = p_i1319_1_;
            this.Â = p_i1319_2_;
            this.Ý = Minecraft.áƒ();
        }
        
        public String HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public String Â() {
            return this.Â;
        }
        
        public void Ý() {
            this.Ý = Minecraft.áƒ();
        }
    }
    
    public static class Â
    {
        private List Â;
        boolean HorizonCode_Horizon_È;
        private static final String Ý = "CL_00001136";
        
        public Â() {
            this.Â = Lists.newArrayList();
        }
        
        public synchronized boolean HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public synchronized void Â() {
            this.HorizonCode_Horizon_È = false;
        }
        
        public synchronized List Ý() {
            return Collections.unmodifiableList((List<?>)this.Â);
        }
        
        public synchronized void HorizonCode_Horizon_È(final String p_77551_1_, final InetAddress p_77551_2_) {
            final String var3 = ThreadLanServerPing.HorizonCode_Horizon_È(p_77551_1_);
            String var4 = ThreadLanServerPing.Â(p_77551_1_);
            if (var4 != null) {
                var4 = String.valueOf(p_77551_2_.getHostAddress()) + ":" + var4;
                boolean var5 = false;
                for (final HorizonCode_Horizon_È var7 : this.Â) {
                    if (var7.Â().equals(var4)) {
                        var7.Ý();
                        var5 = true;
                        break;
                    }
                }
                if (!var5) {
                    this.Â.add(new HorizonCode_Horizon_È(var3, var4));
                    this.HorizonCode_Horizon_È = true;
                }
            }
        }
    }
    
    public static class Ý extends Thread
    {
        private final Â HorizonCode_Horizon_È;
        private final InetAddress Â;
        private final MulticastSocket Ý;
        private static final String Ø­áŒŠá = "CL_00001135";
        
        public Ý(final Â p_i1320_1_) throws IOException {
            super("LanServerDetector #" + LanServerDetector.HorizonCode_Horizon_È.incrementAndGet());
            this.HorizonCode_Horizon_È = p_i1320_1_;
            this.setDaemon(true);
            this.Ý = new MulticastSocket(4445);
            this.Â = InetAddress.getByName("224.0.2.60");
            this.Ý.setSoTimeout(5000);
            this.Ý.joinGroup(this.Â);
        }
        
        @Override
        public void run() {
            final byte[] var2 = new byte[1024];
            while (!this.isInterrupted()) {
                final DatagramPacket var3 = new DatagramPacket(var2, var2.length);
                try {
                    this.Ý.receive(var3);
                }
                catch (SocketTimeoutException var6) {
                    continue;
                }
                catch (IOException var4) {
                    LanServerDetector.Â.error("Couldn't ping server", (Throwable)var4);
                    break;
                }
                final String var5 = new String(var3.getData(), var3.getOffset(), var3.getLength());
                LanServerDetector.Â.debug(var3.getAddress() + ": " + var5);
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5, var3.getAddress());
            }
            try {
                this.Ý.leaveGroup(this.Â);
            }
            catch (IOException ex) {}
            this.Ý.close();
        }
    }
}
