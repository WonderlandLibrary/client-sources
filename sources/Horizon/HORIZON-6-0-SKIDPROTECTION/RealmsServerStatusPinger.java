package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Collections;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class RealmsServerStatusPinger
{
    private static final Logger HorizonCode_Horizon_È;
    private final List Â;
    private static final String Ý = "CL_00001854";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public RealmsServerStatusPinger() {
        this.Â = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    public void HorizonCode_Horizon_È(final String p_pingServer_1_, final RealmsServerPing p_pingServer_2_) throws UnknownHostException {
        if (p_pingServer_1_ != null && !p_pingServer_1_.startsWith("0.0.0.0") && !p_pingServer_1_.isEmpty()) {
            final RealmsServerAddress var3 = RealmsServerAddress.HorizonCode_Horizon_È(p_pingServer_1_);
            final NetworkManager var4 = NetworkManager.HorizonCode_Horizon_È(InetAddress.getByName(var3.HorizonCode_Horizon_È()), var3.Â());
            this.Â.add(var4);
            var4.HorizonCode_Horizon_È(new INetHandlerStatusClient() {
                private boolean Â = false;
                private static final String Ý = "CL_00001807";
                
                @Override
                public void HorizonCode_Horizon_È(final S00PacketServerInfo packetIn) {
                    final ServerStatusResponse var2 = packetIn.HorizonCode_Horizon_È();
                    if (var2.Â() != null) {
                        p_pingServer_2_.HorizonCode_Horizon_È = String.valueOf(var2.Â().Â());
                    }
                    var4.HorizonCode_Horizon_È(new C01PacketPing(Realms.Âµá€()));
                    this.Â = true;
                }
                
                @Override
                public void HorizonCode_Horizon_È(final S01PacketPong packetIn) {
                    var4.HorizonCode_Horizon_È(new ChatComponentText("Finished"));
                }
                
                @Override
                public void HorizonCode_Horizon_È(final IChatComponent reason) {
                    if (!this.Â) {
                        RealmsServerStatusPinger.HorizonCode_Horizon_È.error("Can't ping " + p_pingServer_1_ + ": " + reason.Ø());
                    }
                }
            });
            try {
                var4.HorizonCode_Horizon_È(new C00Handshake(RealmsSharedConstants.HorizonCode_Horizon_È, var3.HorizonCode_Horizon_È(), var3.Â(), EnumConnectionState.Ý));
                var4.HorizonCode_Horizon_È(new C00PacketServerQuery());
            }
            catch (Throwable var5) {
                RealmsServerStatusPinger.HorizonCode_Horizon_È.error((Object)var5);
            }
        }
    }
    
    public void HorizonCode_Horizon_È() {
        final List var1 = this.Â;
        synchronized (this.Â) {
            final Iterator var2 = this.Â.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.Âµá€()) {
                    var3.HorizonCode_Horizon_È();
                }
                else {
                    var2.remove();
                    var3.áˆºÑ¢Õ();
                }
            }
        }
        // monitorexit(this.\u00c2)
    }
    
    public void Â() {
        final List var1 = this.Â;
        synchronized (this.Â) {
            final Iterator var2 = this.Â.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.Âµá€()) {
                    var2.remove();
                    var3.HorizonCode_Horizon_È(new ChatComponentText("Cancelled"));
                }
            }
        }
        // monitorexit(this.\u00c2)
    }
}
