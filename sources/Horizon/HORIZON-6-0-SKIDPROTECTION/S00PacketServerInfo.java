package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import com.google.gson.TypeAdapterFactory;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class S00PacketServerInfo implements Packet
{
    private static final Gson HorizonCode_Horizon_È;
    private ServerStatusResponse Â;
    private static final String Ý = "CL_00001384";
    
    static {
        HorizonCode_Horizon_È = new GsonBuilder().registerTypeAdapter((Type)ServerStatusResponse.HorizonCode_Horizon_È.class, (Object)new ServerStatusResponse.HorizonCode_Horizon_È.HorizonCode_Horizon_È()).registerTypeAdapter((Type)ServerStatusResponse.Â.class, (Object)new ServerStatusResponse.Â.HorizonCode_Horizon_È()).registerTypeAdapter((Type)ServerStatusResponse.class, (Object)new ServerStatusResponse.Ý()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.HorizonCode_Horizon_È()).registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.HorizonCode_Horizon_È()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
    }
    
    public S00PacketServerInfo() {
    }
    
    public S00PacketServerInfo(final ServerStatusResponse responseIn) {
        this.Â = responseIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Â = (ServerStatusResponse)S00PacketServerInfo.HorizonCode_Horizon_È.fromJson(data.Ý(32767), (Class)ServerStatusResponse.class);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(S00PacketServerInfo.HorizonCode_Horizon_È.toJson((Object)this.Â));
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerStatusClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public ServerStatusResponse HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerStatusClient)handler);
    }
}
