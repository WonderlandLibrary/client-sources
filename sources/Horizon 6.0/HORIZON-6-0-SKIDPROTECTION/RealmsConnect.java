package HORIZON-6-0-SKIDPROTECTION;

import java.net.UnknownHostException;
import java.net.InetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect
{
    private static final Logger HorizonCode_Horizon_È;
    private final RealmsScreen Â;
    private volatile boolean Ý;
    private NetworkManager Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001844";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public RealmsConnect(final RealmsScreen p_i1079_1_) {
        this.Ý = false;
        this.Â = p_i1079_1_;
    }
    
    public void HorizonCode_Horizon_È(final String p_connect_1_, final int p_connect_2_) {
        new Thread("Realms-connect-task") {
            private static final String Â = "CL_00001808";
            
            @Override
            public void run() {
                InetAddress var1 = null;
                try {
                    var1 = InetAddress.getByName(p_connect_1_);
                    if (RealmsConnect.this.Ý) {
                        return;
                    }
                    RealmsConnect.HorizonCode_Horizon_È(RealmsConnect.this, NetworkManager.HorizonCode_Horizon_È(var1, p_connect_2_));
                    if (RealmsConnect.this.Ý) {
                        return;
                    }
                    RealmsConnect.this.Ø­áŒŠá.HorizonCode_Horizon_È(new NetHandlerLoginClient(RealmsConnect.this.Ø­áŒŠá, Minecraft.áŒŠà(), RealmsConnect.this.Â.HorizonCode_Horizon_È()));
                    if (RealmsConnect.this.Ý) {
                        return;
                    }
                    RealmsConnect.this.Ø­áŒŠá.HorizonCode_Horizon_È(new C00Handshake(47, p_connect_1_, p_connect_2_, EnumConnectionState.Ø­áŒŠá));
                    if (RealmsConnect.this.Ý) {
                        return;
                    }
                    RealmsConnect.this.Ø­áŒŠá.HorizonCode_Horizon_È(new C00PacketLoginStart(Minecraft.áŒŠà().Õ().Âµá€()));
                }
                catch (UnknownHostException var2) {
                    if (RealmsConnect.this.Ý) {
                        return;
                    }
                    RealmsConnect.HorizonCode_Horizon_È.error("Couldn't connect to world", (Throwable)var2);
                    Realms.HorizonCode_Horizon_È(new DisconnectedRealmsScreen(RealmsConnect.this.Â, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + p_connect_1_ + "'" })));
                }
                catch (Exception var3) {
                    if (RealmsConnect.this.Ý) {
                        return;
                    }
                    RealmsConnect.HorizonCode_Horizon_È.error("Couldn't connect to world", (Throwable)var3);
                    String var4 = var3.toString();
                    if (var1 != null) {
                        final String var5 = String.valueOf(var1.toString()) + ":" + p_connect_2_;
                        var4 = var4.replaceAll(var5, "");
                    }
                    Realms.HorizonCode_Horizon_È(new DisconnectedRealmsScreen(RealmsConnect.this.Â, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var4 })));
                }
            }
        }.start();
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ý = true;
    }
    
    public void Â() {
        if (this.Ø­áŒŠá != null) {
            if (this.Ø­áŒŠá.Âµá€()) {
                this.Ø­áŒŠá.HorizonCode_Horizon_È();
            }
            else {
                this.Ø­áŒŠá.áˆºÑ¢Õ();
            }
        }
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final RealmsConnect realmsConnect, final NetworkManager ø­áŒŠá) {
        realmsConnect.Ø­áŒŠá = ø­áŒŠá;
    }
}
