package HORIZON-6-0-SKIDPROTECTION;

import java.net.SocketAddress;
import com.google.gson.JsonObject;
import java.io.File;

public class BanList extends UserList
{
    private static final String Ý = "CL_00001396";
    
    public BanList(final File bansFile) {
        super(bansFile);
    }
    
    @Override
    protected UserListEntry HorizonCode_Horizon_È(final JsonObject entryData) {
        return new IPBanEntry(entryData);
    }
    
    public boolean HorizonCode_Horizon_È(final SocketAddress address) {
        final String var2 = this.Ý(address);
        return this.Ø­áŒŠá(var2);
    }
    
    public IPBanEntry Â(final SocketAddress address) {
        final String var2 = this.Ý(address);
        return (IPBanEntry)this.HorizonCode_Horizon_È(var2);
    }
    
    private String Ý(final SocketAddress address) {
        String var2 = address.toString();
        if (var2.contains("/")) {
            var2 = var2.substring(var2.indexOf(47) + 1);
        }
        if (var2.contains(":")) {
            var2 = var2.substring(0, var2.indexOf(58));
        }
        return var2;
    }
}
