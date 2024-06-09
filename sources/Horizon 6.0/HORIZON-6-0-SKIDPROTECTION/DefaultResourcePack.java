package HORIZON-6-0-SKIDPROTECTION;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;

public class DefaultResourcePack implements IResourcePack
{
    public static final Set HorizonCode_Horizon_È;
    private final Map Â;
    private static final String Ý = "CL_00001073";
    
    static {
        HorizonCode_Horizon_È = (Set)ImmutableSet.of((Object)"minecraft", (Object)"realms");
    }
    
    public DefaultResourcePack(final Map p_i46346_1_) {
        this.Â = p_i46346_1_;
    }
    
    @Override
    public InputStream HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_110590_1_) throws IOException {
        final InputStream var2 = this.Ø­áŒŠá(p_110590_1_);
        if (var2 != null) {
            return var2;
        }
        final InputStream var3 = this.Ý(p_110590_1_);
        if (var3 != null) {
            return var3;
        }
        throw new FileNotFoundException(p_110590_1_.Â());
    }
    
    public InputStream Ý(final ResourceLocation_1975012498 p_152780_1_) throws IOException {
        final File var2 = this.Â.get(p_152780_1_.toString());
        return (var2 != null && var2.isFile()) ? new FileInputStream(var2) : null;
    }
    
    private InputStream Ø­áŒŠá(final ResourceLocation_1975012498 p_110605_1_) {
        return DefaultResourcePack.class.getResourceAsStream("/assets/" + p_110605_1_.Ý() + "/" + p_110605_1_.Â());
    }
    
    @Override
    public boolean Â(final ResourceLocation_1975012498 p_110589_1_) {
        return this.Ø­áŒŠá(p_110589_1_) != null || this.Â.containsKey(p_110589_1_.toString());
    }
    
    @Override
    public Set Ý() {
        return DefaultResourcePack.HorizonCode_Horizon_È;
    }
    
    @Override
    public IMetadataSection HorizonCode_Horizon_È(final IMetadataSerializer p_135058_1_, final String p_135058_2_) throws IOException {
        try {
            final FileInputStream var3 = new FileInputStream(this.Â.get("pack.mcmeta"));
            return AbstractResourcePack.HorizonCode_Horizon_È(p_135058_1_, var3, p_135058_2_);
        }
        catch (RuntimeException var4) {
            return null;
        }
        catch (FileNotFoundException var5) {
            return null;
        }
    }
    
    @Override
    public BufferedImage HorizonCode_Horizon_È() throws IOException {
        return TextureUtil.HorizonCode_Horizon_È(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation_1975012498("pack.png").Â()));
    }
    
    @Override
    public String Â() {
        return "Default";
    }
}
