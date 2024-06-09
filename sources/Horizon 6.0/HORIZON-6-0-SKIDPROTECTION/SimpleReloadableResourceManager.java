package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Iterables;
import com.google.common.base.Function;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Iterator;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Set;
import java.util.List;
import java.util.Map;
import com.google.common.base.Joiner;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager
{
    private static final Logger HorizonCode_Horizon_È;
    private static final Joiner Â;
    private final Map Ý;
    private final List Ø­áŒŠá;
    private final Set Âµá€;
    private final IMetadataSerializer Ó;
    private static final String à = "CL_00001091";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = Joiner.on(", ");
    }
    
    public SimpleReloadableResourceManager(final IMetadataSerializer p_i1299_1_) {
        this.Ý = Maps.newHashMap();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Âµá€ = Sets.newLinkedHashSet();
        this.Ó = p_i1299_1_;
    }
    
    public void HorizonCode_Horizon_È(final IResourcePack p_110545_1_) {
        for (final String var3 : p_110545_1_.Ý()) {
            this.Âµá€.add(var3);
            FallbackResourceManager var4 = this.Ý.get(var3);
            if (var4 == null) {
                var4 = new FallbackResourceManager(this.Ó);
                this.Ý.put(var3, var4);
            }
            var4.HorizonCode_Horizon_È(p_110545_1_);
        }
    }
    
    @Override
    public Set HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    @Override
    public IResource HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_110536_1_) throws IOException {
        final IResourceManager var2 = this.Ý.get(p_110536_1_.Ý());
        if (var2 != null) {
            return var2.HorizonCode_Horizon_È(p_110536_1_);
        }
        throw new FileNotFoundException(p_110536_1_.toString());
    }
    
    @Override
    public List Â(final ResourceLocation_1975012498 p_135056_1_) throws IOException {
        final IResourceManager var2 = this.Ý.get(p_135056_1_.Ý());
        if (var2 != null) {
            return var2.Â(p_135056_1_);
        }
        throw new FileNotFoundException(p_135056_1_.toString());
    }
    
    private void Â() {
        this.Ý.clear();
        this.Âµá€.clear();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final List p_110541_1_) {
        this.Â();
        SimpleReloadableResourceManager.HorizonCode_Horizon_È.info("Reloading ResourceManager: " + SimpleReloadableResourceManager.Â.join(Iterables.transform((Iterable)p_110541_1_, (Function)new Function() {
            private static final String Â = "CL_00001092";
            
            public String HorizonCode_Horizon_È(final IResourcePack p_apply_1_) {
                return p_apply_1_.Â();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((IResourcePack)p_apply_1_);
            }
        })));
        for (final IResourcePack var3 : p_110541_1_) {
            this.HorizonCode_Horizon_È(var3);
        }
        this.Ý();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManagerReloadListener p_110542_1_) {
        this.Ø­áŒŠá.add(p_110542_1_);
        p_110542_1_.HorizonCode_Horizon_È(this);
    }
    
    private void Ý() {
        for (final IResourceManagerReloadListener var2 : this.Ø­áŒŠá) {
            var2.HorizonCode_Horizon_È(this);
        }
    }
}
