package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;
import java.io.BufferedInputStream;

public class ShaderLoader
{
    private final HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private final String Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001043";
    
    private ShaderLoader(final HorizonCode_Horizon_È type, final int shaderId, final String filename) {
        this.Ø­áŒŠá = 0;
        this.HorizonCode_Horizon_È = type;
        this.Ý = shaderId;
        this.Â = filename;
    }
    
    public void HorizonCode_Horizon_È(final ShaderManager manager) {
        ++this.Ø­áŒŠá;
        OpenGlHelper.Â(manager.à(), this.Ý);
    }
    
    public void Â(final ShaderManager manager) {
        --this.Ø­áŒŠá;
        if (this.Ø­áŒŠá <= 0) {
            OpenGlHelper.HorizonCode_Horizon_È(this.Ý);
            this.HorizonCode_Horizon_È.Ø­áŒŠá().remove(this.Â);
        }
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public static ShaderLoader HorizonCode_Horizon_È(final IResourceManager resourceManager, final HorizonCode_Horizon_È type, final String filename) throws IOException {
        ShaderLoader var3 = type.Ø­áŒŠá().get(filename);
        if (var3 == null) {
            final ResourceLocation_1975012498 var4 = new ResourceLocation_1975012498("shaders/program/" + filename + type.Â());
            final BufferedInputStream var5 = new BufferedInputStream(resourceManager.HorizonCode_Horizon_È(var4).Â());
            final byte[] var6 = HorizonCode_Horizon_È(var5);
            final ByteBuffer var7 = BufferUtils.createByteBuffer(var6.length);
            var7.put(var6);
            var7.position(0);
            final int var8 = OpenGlHelper.Â(type.Ý());
            OpenGlHelper.HorizonCode_Horizon_È(var8, var7);
            OpenGlHelper.Ý(var8);
            if (OpenGlHelper.Ý(var8, OpenGlHelper.ˆÏ­) == 0) {
                final String var9 = StringUtils.trim(OpenGlHelper.Ø­áŒŠá(var8, 32768));
                final JsonException var10 = new JsonException("Couldn't compile " + type.HorizonCode_Horizon_È() + " program: " + var9);
                var10.Â(var4.Â());
                throw var10;
            }
            var3 = new ShaderLoader(type, var8, filename);
            type.Ø­áŒŠá().put(filename, var3);
        }
        return var3;
    }
    
    protected static byte[] HorizonCode_Horizon_È(final BufferedInputStream p_177064_0_) throws IOException {
        byte[] var1;
        try {
            var1 = IOUtils.toByteArray((InputStream)p_177064_0_);
        }
        finally {
            p_177064_0_.close();
        }
        p_177064_0_.close();
        return var1;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("VERTEX", 0, "VERTEX", 0, "vertex", ".vsh", OpenGlHelper.£á), 
        Â("FRAGMENT", 1, "FRAGMENT", 1, "fragment", ".fsh", OpenGlHelper.Å);
        
        private final String Ý;
        private final String Ø­áŒŠá;
        private final int Âµá€;
        private final Map Ó;
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00001044";
        
        static {
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45090_1_, final int p_i45090_2_, final String p_i45090_3_, final String p_i45090_4_, final int p_i45090_5_) {
            this.Ó = Maps.newHashMap();
            this.Ý = p_i45090_3_;
            this.Ø­áŒŠá = p_i45090_4_;
            this.Âµá€ = p_i45090_5_;
        }
        
        public String HorizonCode_Horizon_È() {
            return this.Ý;
        }
        
        protected String Â() {
            return this.Ø­áŒŠá;
        }
        
        protected int Ý() {
            return this.Âµá€;
        }
        
        protected Map Ø­áŒŠá() {
            return this.Ó;
        }
    }
}
