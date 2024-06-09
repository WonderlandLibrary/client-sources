package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import org.apache.commons.io.IOUtils;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.List;
import java.util.Iterator;
import java.io.IOException;
import java.util.Map;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import java.lang.reflect.ParameterizedType;
import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;

public class SoundHandler implements IUpdatePlayerListBox, IResourceManagerReloadListener
{
    private static final Logger Â;
    private static final Gson Ý;
    private static final ParameterizedType Ø­áŒŠá;
    public static final SoundPoolEntry HorizonCode_Horizon_È;
    private final SoundRegistry Âµá€;
    private final SoundManager Ó;
    private final IResourceManager à;
    private static final String Ø = "CL_00001147";
    
    static {
        Â = LogManager.getLogger();
        Ý = new GsonBuilder().registerTypeAdapter((Type)SoundList.class, (Object)new SoundListSerializer()).create();
        Ø­áŒŠá = new ParameterizedType() {
            private static final String HorizonCode_Horizon_È = "CL_00001148";
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { String.class, SoundList.class };
            }
            
            @Override
            public Type getRawType() {
                return Map.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        HorizonCode_Horizon_È = new SoundPoolEntry(new ResourceLocation_1975012498("meta:missing_sound"), 0.0, 0.0, false);
    }
    
    public SoundHandler(final IResourceManager manager, final GameSettings p_i45122_2_) {
        this.Âµá€ = new SoundRegistry();
        this.à = manager;
        this.Ó = new SoundManager(this, p_i45122_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110549_1_) {
        this.Ó.HorizonCode_Horizon_È();
        this.Âµá€.Â();
        for (final String var3 : p_110549_1_.HorizonCode_Horizon_È()) {
            try {
                final List var4 = p_110549_1_.Â(new ResourceLocation_1975012498(var3, "sounds.json"));
                for (final IResource var6 : var4) {
                    try {
                        final Map var7 = this.HorizonCode_Horizon_È(var6.Â());
                        for (final Map.Entry var9 : var7.entrySet()) {
                            this.HorizonCode_Horizon_È(new ResourceLocation_1975012498(var3, var9.getKey()), var9.getValue());
                        }
                    }
                    catch (RuntimeException var10) {
                        SoundHandler.Â.warn("Invalid sounds.json", (Throwable)var10);
                    }
                }
            }
            catch (IOException ex) {}
        }
    }
    
    protected Map HorizonCode_Horizon_È(final InputStream p_175085_1_) {
        Map var2;
        try {
            var2 = (Map)SoundHandler.Ý.fromJson((Reader)new InputStreamReader(p_175085_1_), (Type)SoundHandler.Ø­áŒŠá);
        }
        finally {
            IOUtils.closeQuietly(p_175085_1_);
        }
        IOUtils.closeQuietly(p_175085_1_);
        return var2;
    }
    
    private void HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_147693_1_, final SoundList p_147693_2_) {
        final boolean var4 = !this.Âµá€.Ý(p_147693_1_);
        SoundEventAccessorComposite var5;
        if (!var4 && !p_147693_2_.Â()) {
            var5 = (SoundEventAccessorComposite)this.Âµá€.HorizonCode_Horizon_È(p_147693_1_);
        }
        else {
            if (!var4) {
                SoundHandler.Â.debug("Replaced sound event location {}", new Object[] { p_147693_1_ });
            }
            var5 = new SoundEventAccessorComposite(p_147693_1_, 1.0, 1.0, p_147693_2_.Ý());
            this.Âµá€.HorizonCode_Horizon_È(var5);
        }
        for (final SoundList.HorizonCode_Horizon_È var7 : p_147693_2_.HorizonCode_Horizon_È()) {
            final String var8 = var7.HorizonCode_Horizon_È();
            final ResourceLocation_1975012498 var9 = new ResourceLocation_1975012498(var8);
            final String var10 = var8.contains(":") ? var9.Ý() : p_147693_1_.Ý();
            Object var14 = null;
            switch (SoundHandler.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var7.Âµá€().ordinal()]) {
                case 1: {
                    final ResourceLocation_1975012498 var11 = new ResourceLocation_1975012498(var10, "sounds/" + var9.Â() + ".ogg");
                    InputStream var12 = null;
                    try {
                        var12 = this.à.HorizonCode_Horizon_È(var11).Â();
                    }
                    catch (FileNotFoundException var15) {
                        SoundHandler.Â.warn("File {} does not exist, cannot add it to event {}", new Object[] { var11, p_147693_1_ });
                    }
                    catch (IOException var13) {
                        SoundHandler.Â.warn("Could not load sound file " + var11 + ", cannot add it to event " + p_147693_1_, (Throwable)var13);
                    }
                    finally {
                        IOUtils.closeQuietly(var12);
                    }
                    IOUtils.closeQuietly(var12);
                    var14 = new SoundEventAccessor(new SoundPoolEntry(var11, var7.Ý(), var7.Â(), var7.Ó()), var7.Ø­áŒŠá());
                    break;
                }
                case 2: {
                    var14 = new ISoundEventAccessor(var10, var7) {
                        final ResourceLocation_1975012498 HorizonCode_Horizon_È = new ResourceLocation_1975012498(p_i1292_1_, horizonCode_Horizon_È.HorizonCode_Horizon_È());
                        private static final String Ý = "CL_00001149";
                        
                        @Override
                        public int HorizonCode_Horizon_È() {
                            final SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.this.Âµá€.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                            return (var1 == null) ? 0 : var1.HorizonCode_Horizon_È();
                        }
                        
                        public SoundPoolEntry Ý() {
                            final SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.this.Âµá€.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                            return (var1 == null) ? SoundHandler.HorizonCode_Horizon_È : var1.Ý();
                        }
                        
                        @Override
                        public Object Â() {
                            return this.Ý();
                        }
                    };
                    break;
                }
                default: {
                    throw new IllegalStateException("IN YOU FACE");
                }
            }
            var5.HorizonCode_Horizon_È((ISoundEventAccessor)var14);
        }
    }
    
    public SoundEventAccessorComposite HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_147680_1_) {
        return (SoundEventAccessorComposite)this.Âµá€.HorizonCode_Horizon_È(p_147680_1_);
    }
    
    public void HorizonCode_Horizon_È(final ISound p_147682_1_) {
        this.Ó.Ý(p_147682_1_);
    }
    
    public void HorizonCode_Horizon_È(final ISound p_147681_1_, final int p_147681_2_) {
        this.Ó.HorizonCode_Horizon_È(p_147681_1_, p_147681_2_);
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_147691_1_, final float p_147691_2_) {
        this.Ó.HorizonCode_Horizon_È(p_147691_1_, p_147691_2_);
    }
    
    public void Â() {
        this.Ó.Âµá€();
    }
    
    public void Ý() {
        this.Ó.Ý();
    }
    
    public void Ø­áŒŠá() {
        this.Ó.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ó.Ø­áŒŠá();
    }
    
    public void Âµá€() {
        this.Ó.Ó();
    }
    
    public void HorizonCode_Horizon_È(final SoundCategory p_147684_1_, final float volume) {
        if (p_147684_1_ == SoundCategory.HorizonCode_Horizon_È && volume <= 0.0f) {
            this.Ý();
        }
        this.Ó.HorizonCode_Horizon_È(p_147684_1_, volume);
    }
    
    public void Â(final ISound p_147683_1_) {
        this.Ó.Â(p_147683_1_);
    }
    
    public SoundEventAccessorComposite HorizonCode_Horizon_È(final SoundCategory... p_147686_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (final ResourceLocation_1975012498 var4 : this.Âµá€.Ý()) {
            final SoundEventAccessorComposite var5 = (SoundEventAccessorComposite)this.Âµá€.HorizonCode_Horizon_È(var4);
            if (ArrayUtils.contains((Object[])p_147686_1_, (Object)var5.Âµá€())) {
                var2.add(var5);
            }
        }
        if (var2.isEmpty()) {
            return null;
        }
        return var2.get(new Random().nextInt(var2.size()));
    }
    
    public boolean Ý(final ISound p_147692_1_) {
        return this.Ó.HorizonCode_Horizon_È(p_147692_1_);
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001150";
        
        static {
            HorizonCode_Horizon_È = new int[SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È.values().length];
            try {
                SoundHandler.HorizonCode_Horizon_È.HorizonCode_Horizon_È[SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SoundHandler.HorizonCode_Horizon_È.HorizonCode_Horizon_È[SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
