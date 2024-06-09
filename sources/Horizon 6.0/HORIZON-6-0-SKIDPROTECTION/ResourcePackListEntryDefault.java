package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonParseException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackListEntryDefault extends ResourcePackListEntry
{
    private static final Logger Ý;
    private final IResourcePack Ø­áŒŠá;
    private final ResourceLocation_1975012498 Âµá€;
    private static final String Ó = "CL_00000822";
    
    static {
        Ý = LogManager.getLogger();
    }
    
    public ResourcePackListEntryDefault(final GuiScreenResourcePacks p_i45052_1_) {
        super(p_i45052_1_);
        this.Ø­áŒŠá = this.HorizonCode_Horizon_È.Ç().HorizonCode_Horizon_È;
        DynamicTexture var2;
        try {
            var2 = new DynamicTexture(this.Ø­áŒŠá.HorizonCode_Horizon_È());
        }
        catch (IOException var3) {
            var2 = TextureUtil.HorizonCode_Horizon_È;
        }
        this.Âµá€ = this.HorizonCode_Horizon_È.¥à().HorizonCode_Horizon_È("texturepackicon", var2);
    }
    
    @Override
    protected String HorizonCode_Horizon_È() {
        try {
            final PackMetadataSection var1 = (PackMetadataSection)this.Ø­áŒŠá.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ç().Â, "pack");
            if (var1 != null) {
                return var1.HorizonCode_Horizon_È().áŒŠÆ();
            }
        }
        catch (JsonParseException var2) {
            ResourcePackListEntryDefault.Ý.error("Couldn't load metadata info", (Throwable)var2);
        }
        catch (IOException var3) {
            ResourcePackListEntryDefault.Ý.error("Couldn't load metadata info", (Throwable)var3);
        }
        return EnumChatFormatting.ˆÏ­ + "Missing " + "pack.mcmeta" + " :(";
    }
    
    @Override
    protected boolean Âµá€() {
        return false;
    }
    
    @Override
    protected boolean Ó() {
        return false;
    }
    
    @Override
    protected boolean à() {
        return false;
    }
    
    @Override
    protected boolean Ø() {
        return false;
    }
    
    @Override
    protected String Â() {
        return "Default";
    }
    
    @Override
    protected void Ý() {
        this.HorizonCode_Horizon_È.¥à().HorizonCode_Horizon_È(this.Âµá€);
    }
    
    @Override
    protected boolean Ø­áŒŠá() {
        return false;
    }
}
