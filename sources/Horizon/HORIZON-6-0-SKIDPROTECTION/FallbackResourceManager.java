package HORIZON-6-0-SKIDPROTECTION;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements IResourceManager
{
    private static final Logger Â;
    protected final List HorizonCode_Horizon_È;
    private final IMetadataSerializer Ý;
    private static final String Ø­áŒŠá = "CL_00001074";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public FallbackResourceManager(final IMetadataSerializer p_i1289_1_) {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.Ý = p_i1289_1_;
    }
    
    public void HorizonCode_Horizon_È(final IResourcePack p_110538_1_) {
        this.HorizonCode_Horizon_È.add(p_110538_1_);
    }
    
    @Override
    public Set HorizonCode_Horizon_È() {
        return null;
    }
    
    @Override
    public IResource HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_110536_1_) throws IOException {
        IResourcePack var2 = null;
        final ResourceLocation_1975012498 var3 = Ý(p_110536_1_);
        for (int var4 = this.HorizonCode_Horizon_È.size() - 1; var4 >= 0; --var4) {
            final IResourcePack var5 = this.HorizonCode_Horizon_È.get(var4);
            if (var2 == null && var5.Â(var3)) {
                var2 = var5;
            }
            if (var5.Â(p_110536_1_)) {
                InputStream var6 = null;
                if (var2 != null) {
                    var6 = this.HorizonCode_Horizon_È(var3, var2);
                }
                return new SimpleResource(var5.Â(), p_110536_1_, this.HorizonCode_Horizon_È(p_110536_1_, var5), var6, this.Ý);
            }
        }
        throw new FileNotFoundException(p_110536_1_.toString());
    }
    
    protected InputStream HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_177245_1_, final IResourcePack p_177245_2_) throws IOException {
        final InputStream var3 = p_177245_2_.HorizonCode_Horizon_È(p_177245_1_);
        return FallbackResourceManager.Â.isDebugEnabled() ? new HorizonCode_Horizon_È(var3, p_177245_1_, p_177245_2_.Â()) : var3;
    }
    
    @Override
    public List Â(final ResourceLocation_1975012498 p_135056_1_) throws IOException {
        final ArrayList var2 = Lists.newArrayList();
        final ResourceLocation_1975012498 var3 = Ý(p_135056_1_);
        for (final IResourcePack var5 : this.HorizonCode_Horizon_È) {
            if (var5.Â(p_135056_1_)) {
                final InputStream var6 = var5.Â(var3) ? this.HorizonCode_Horizon_È(var3, var5) : null;
                var2.add(new SimpleResource(var5.Â(), p_135056_1_, this.HorizonCode_Horizon_È(p_135056_1_, var5), var6, this.Ý));
            }
        }
        if (var2.isEmpty()) {
            throw new FileNotFoundException(p_135056_1_.toString());
        }
        return var2;
    }
    
    static ResourceLocation_1975012498 Ý(final ResourceLocation_1975012498 p_110537_0_) {
        return new ResourceLocation_1975012498(p_110537_0_.Ý(), String.valueOf(p_110537_0_.Â()) + ".mcmeta");
    }
    
    static class HorizonCode_Horizon_È extends InputStream
    {
        private final InputStream HorizonCode_Horizon_È;
        private final String Â;
        private boolean Ý;
        private static final String Ø­áŒŠá = "CL_00002395";
        
        public HorizonCode_Horizon_È(final InputStream p_i46093_1_, final ResourceLocation_1975012498 p_i46093_2_, final String p_i46093_3_) {
            this.Ý = false;
            this.HorizonCode_Horizon_È = p_i46093_1_;
            final ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            new Exception().printStackTrace(new PrintStream(var4));
            this.Â = "Leaked resource: '" + p_i46093_2_ + "' loaded from pack: '" + p_i46093_3_ + "'\n" + var4.toString();
        }
        
        @Override
        public void close() throws IOException {
            this.HorizonCode_Horizon_È.close();
            this.Ý = true;
        }
        
        @Override
        protected void finalize() throws Throwable {
            if (!this.Ý) {
                FallbackResourceManager.Â.warn(this.Â);
            }
            super.finalize();
        }
        
        @Override
        public int read() throws IOException {
            return this.HorizonCode_Horizon_È.read();
        }
    }
}
