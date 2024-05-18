package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.io.IOUtils;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.util.Map;

public class SimpleResource implements IResource
{
    private final Map HorizonCode_Horizon_È;
    private final String Â;
    private final ResourceLocation_1975012498 Ý;
    private final InputStream Ø­áŒŠá;
    private final InputStream Âµá€;
    private final IMetadataSerializer Ó;
    private boolean à;
    private JsonObject Ø;
    private static final String áŒŠÆ = "CL_00001093";
    
    public SimpleResource(final String p_i46090_1_, final ResourceLocation_1975012498 p_i46090_2_, final InputStream p_i46090_3_, final InputStream p_i46090_4_, final IMetadataSerializer p_i46090_5_) {
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        this.Â = p_i46090_1_;
        this.Ý = p_i46090_2_;
        this.Ø­áŒŠá = p_i46090_3_;
        this.Âµá€ = p_i46090_4_;
        this.Ó = p_i46090_5_;
    }
    
    @Override
    public ResourceLocation_1975012498 HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public InputStream Â() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public boolean Ý() {
        return this.Âµá€ != null;
    }
    
    @Override
    public IMetadataSection HorizonCode_Horizon_È(final String p_110526_1_) {
        if (!this.Ý()) {
            return null;
        }
        if (this.Ø == null && !this.à) {
            this.à = true;
            BufferedReader var2 = null;
            try {
                var2 = new BufferedReader(new InputStreamReader(this.Âµá€));
                this.Ø = new JsonParser().parse((Reader)var2).getAsJsonObject();
            }
            finally {
                IOUtils.closeQuietly((Reader)var2);
            }
            IOUtils.closeQuietly((Reader)var2);
        }
        IMetadataSection var3 = this.HorizonCode_Horizon_È.get(p_110526_1_);
        if (var3 == null) {
            var3 = this.Ó.HorizonCode_Horizon_È(p_110526_1_, this.Ø);
        }
        return var3;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return this.Â;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof SimpleResource)) {
            return false;
        }
        final SimpleResource var2 = (SimpleResource)p_equals_1_;
        if (this.Ý != null) {
            if (!this.Ý.equals(var2.Ý)) {
                return false;
            }
        }
        else if (var2.Ý != null) {
            return false;
        }
        if (this.Â != null) {
            if (!this.Â.equals(var2.Â)) {
                return false;
            }
        }
        else if (var2.Â != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int var1 = (this.Â != null) ? this.Â.hashCode() : 0;
        var1 = 31 * var1 + ((this.Ý != null) ? this.Ý.hashCode() : 0);
        return var1;
    }
}
