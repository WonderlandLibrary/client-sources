package HORIZON-6-0-SKIDPROTECTION;

import java.awt.image.BufferedImage;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack
{
    private static final Logger Â;
    public final File HorizonCode_Horizon_È;
    private static final String Ý = "CL_00001072";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public AbstractResourcePack(final File p_i1287_1_) {
        this.HorizonCode_Horizon_È = p_i1287_1_;
    }
    
    private static String Ý(final ResourceLocation_1975012498 p_110592_0_) {
        return String.format("%s/%s/%s", "assets", p_110592_0_.Ý(), p_110592_0_.Â());
    }
    
    protected static String HorizonCode_Horizon_È(final File p_110595_0_, final File p_110595_1_) {
        return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
    }
    
    @Override
    public InputStream HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_110590_1_) throws IOException {
        return this.HorizonCode_Horizon_È(Ý(p_110590_1_));
    }
    
    @Override
    public boolean Â(final ResourceLocation_1975012498 p_110589_1_) {
        return this.Â(Ý(p_110589_1_));
    }
    
    protected abstract InputStream HorizonCode_Horizon_È(final String p0) throws IOException;
    
    protected abstract boolean Â(final String p0);
    
    protected void Ý(final String p_110594_1_) {
        AbstractResourcePack.Â.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", new Object[] { p_110594_1_, this.HorizonCode_Horizon_È });
    }
    
    @Override
    public IMetadataSection HorizonCode_Horizon_È(final IMetadataSerializer p_135058_1_, final String p_135058_2_) throws IOException {
        return HorizonCode_Horizon_È(p_135058_1_, this.HorizonCode_Horizon_È("pack.mcmeta"), p_135058_2_);
    }
    
    static IMetadataSection HorizonCode_Horizon_È(final IMetadataSerializer p_110596_0_, final InputStream p_110596_1_, final String p_110596_2_) {
        JsonObject var3 = null;
        BufferedReader var4 = null;
        try {
            var4 = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
            var3 = new JsonParser().parse((Reader)var4).getAsJsonObject();
        }
        catch (RuntimeException var5) {
            throw new JsonParseException((Throwable)var5);
        }
        finally {
            IOUtils.closeQuietly((Reader)var4);
        }
        IOUtils.closeQuietly((Reader)var4);
        return p_110596_0_.HorizonCode_Horizon_È(p_110596_2_, var3);
    }
    
    @Override
    public BufferedImage HorizonCode_Horizon_È() throws IOException {
        return TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È("pack.png"));
    }
    
    @Override
    public String Â() {
        return this.HorizonCode_Horizon_È.getName();
    }
}
