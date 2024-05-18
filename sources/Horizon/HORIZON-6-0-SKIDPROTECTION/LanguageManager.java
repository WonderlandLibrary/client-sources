package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Sets;
import java.util.SortedSet;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.io.IOException;
import java.util.List;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements IResourceManagerReloadListener
{
    private static final Logger Â;
    private final IMetadataSerializer Ý;
    private String Ø­áŒŠá;
    protected static final Locale HorizonCode_Horizon_È;
    private Map Âµá€;
    private static final String Ó = "CL_00001096";
    
    static {
        Â = LogManager.getLogger();
        HorizonCode_Horizon_È = new Locale();
    }
    
    public LanguageManager(final IMetadataSerializer p_i1304_1_, final String p_i1304_2_) {
        this.Âµá€ = Maps.newHashMap();
        this.Ý = p_i1304_1_;
        this.Ø­áŒŠá = p_i1304_2_;
        I18n.HorizonCode_Horizon_È(LanguageManager.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final List p_135043_1_) {
        this.Âµá€.clear();
        for (final IResourcePack var3 : p_135043_1_) {
            try {
                final LanguageMetadataSection var4 = (LanguageMetadataSection)var3.HorizonCode_Horizon_È(this.Ý, "language");
                if (var4 == null) {
                    continue;
                }
                for (final Language var6 : var4.HorizonCode_Horizon_È()) {
                    if (!this.Âµá€.containsKey(var6.HorizonCode_Horizon_È())) {
                        this.Âµá€.put(var6.HorizonCode_Horizon_È(), var6);
                    }
                }
            }
            catch (RuntimeException var7) {
                LanguageManager.Â.warn("Unable to parse metadata section of resourcepack: " + var3.Â(), (Throwable)var7);
            }
            catch (IOException var8) {
                LanguageManager.Â.warn("Unable to parse metadata section of resourcepack: " + var3.Â(), (Throwable)var8);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110549_1_) {
        final ArrayList var2 = Lists.newArrayList((Object[])new String[] { "en_US" });
        if (!"en_US".equals(this.Ø­áŒŠá)) {
            var2.add(this.Ø­áŒŠá);
        }
        LanguageManager.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_110549_1_, var2);
        StringTranslate.HorizonCode_Horizon_È(LanguageManager.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return LanguageManager.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    public boolean Â() {
        return this.Ý() != null && this.Ý().Â();
    }
    
    public void HorizonCode_Horizon_È(final Language p_135045_1_) {
        this.Ø­áŒŠá = p_135045_1_.HorizonCode_Horizon_È();
    }
    
    public Language Ý() {
        return this.Âµá€.containsKey(this.Ø­áŒŠá) ? this.Âµá€.get(this.Ø­áŒŠá) : this.Âµá€.get("en_US");
    }
    
    public SortedSet Ø­áŒŠá() {
        return Sets.newTreeSet((Iterable)this.Âµá€.values());
    }
}
