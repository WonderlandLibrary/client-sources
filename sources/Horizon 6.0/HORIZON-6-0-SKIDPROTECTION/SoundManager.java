package HORIZON-6-0-SKIDPROTECTION;

import paulscode.sound.Source;
import paulscode.sound.SoundSystem;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URL;
import java.util.Random;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.Iterator;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;
import com.google.common.collect.Lists;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.MarkerManager;
import java.util.List;
import com.google.common.collect.Multimap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

public class SoundManager
{
    private static final Marker HorizonCode_Horizon_È;
    private static final Logger Â;
    private final SoundHandler Ý;
    private final GameSettings Ø­áŒŠá;
    private HorizonCode_Horizon_È Âµá€;
    private boolean Ó;
    private int à;
    private final Map Ø;
    private final Map áŒŠÆ;
    private Map áˆºÑ¢Õ;
    private final Multimap ÂµÈ;
    private final List á;
    private final Map ˆÏ­;
    private final Map £á;
    private static final String Å = "CL_00001141";
    
    static {
        HorizonCode_Horizon_È = MarkerManager.getMarker("SOUNDS");
        Â = LogManager.getLogger();
    }
    
    public SoundManager(final SoundHandler p_i45119_1_, final GameSettings p_i45119_2_) {
        this.à = 0;
        this.Ø = (Map)HashBiMap.create();
        this.áŒŠÆ = (Map)((BiMap)this.Ø).inverse();
        this.áˆºÑ¢Õ = Maps.newHashMap();
        this.ÂµÈ = (Multimap)HashMultimap.create();
        this.á = Lists.newArrayList();
        this.ˆÏ­ = Maps.newHashMap();
        this.£á = Maps.newHashMap();
        this.Ý = p_i45119_1_;
        this.Ø­áŒŠá = p_i45119_2_;
        try {
            SoundSystemConfig.addLibrary((Class)LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("ogg", (Class)CodecJOrbis.class);
        }
        catch (SoundSystemException var4) {
            SoundManager.Â.error(SoundManager.HorizonCode_Horizon_È, "Error linking with the LibraryJavaSound plug-in", (Throwable)var4);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        this.Â();
        this.áŒŠÆ();
    }
    
    private synchronized void áŒŠÆ() {
        if (!this.Ó) {
            try {
                new Thread(new Runnable() {
                    private static final String Â = "CL_00001142";
                    
                    @Override
                    public void run() {
                        SoundSystemConfig.setLogger((SoundSystemLogger)new SoundSystemLogger() {
                            private static final String Â = "CL_00002378";
                            
                            public void message(final String p_message_1_, final int p_message_2_) {
                                if (!p_message_1_.isEmpty()) {
                                    SoundManager.Â.info(p_message_1_);
                                }
                            }
                            
                            public void importantMessage(final String p_importantMessage_1_, final int p_importantMessage_2_) {
                                if (!p_importantMessage_1_.isEmpty()) {
                                    SoundManager.Â.warn(p_importantMessage_1_);
                                }
                            }
                            
                            public void errorMessage(final String p_errorMessage_1_, final String p_errorMessage_2_, final int p_errorMessage_3_) {
                                if (!p_errorMessage_2_.isEmpty()) {
                                    SoundManager.Â.error("Error in class '" + p_errorMessage_1_ + "'");
                                    SoundManager.Â.error(p_errorMessage_2_);
                                }
                            }
                        });
                        SoundManager.HorizonCode_Horizon_È(SoundManager.this, new HorizonCode_Horizon_È(null));
                        SoundManager.HorizonCode_Horizon_È(SoundManager.this, true);
                        SoundManager.this.Âµá€.setMasterVolume(SoundManager.this.Ø­áŒŠá.HorizonCode_Horizon_È(SoundCategory.HorizonCode_Horizon_È));
                        SoundManager.Â.info(SoundManager.HorizonCode_Horizon_È, "Sound engine started");
                    }
                }, "Sound Library Loader").start();
            }
            catch (RuntimeException var2) {
                SoundManager.Â.error(SoundManager.HorizonCode_Horizon_È, "Error starting SoundSystem. Turning off sounds & music", (Throwable)var2);
                this.Ø­áŒŠá.HorizonCode_Horizon_È(SoundCategory.HorizonCode_Horizon_È, 0.0f);
                this.Ø­áŒŠá.Â();
            }
        }
    }
    
    private float HorizonCode_Horizon_È(final SoundCategory p_148595_1_) {
        return (p_148595_1_ != null && p_148595_1_ != SoundCategory.HorizonCode_Horizon_È) ? this.Ø­áŒŠá.HorizonCode_Horizon_È(p_148595_1_) : 1.0f;
    }
    
    public void HorizonCode_Horizon_È(final SoundCategory p_148601_1_, final float p_148601_2_) {
        if (this.Ó) {
            if (p_148601_1_ == SoundCategory.HorizonCode_Horizon_È) {
                this.Âµá€.setMasterVolume(p_148601_2_);
            }
            else {
                for (final String var4 : this.ÂµÈ.get((Object)p_148601_1_)) {
                    final ISound var5 = this.Ø.get(var4);
                    final float var6 = this.HorizonCode_Horizon_È(var5, this.áˆºÑ¢Õ.get(var5), p_148601_1_);
                    if (var6 <= 0.0f) {
                        this.Â(var5);
                    }
                    else {
                        this.Âµá€.setVolume(var4, var6);
                    }
                }
            }
        }
    }
    
    public void Â() {
        if (this.Ó) {
            this.Ý();
            this.Âµá€.cleanup();
            this.Ó = false;
        }
    }
    
    public void Ý() {
        if (this.Ó) {
            for (final String var2 : this.Ø.keySet()) {
                this.Âµá€.stop(var2);
            }
            this.Ø.clear();
            this.ˆÏ­.clear();
            this.á.clear();
            this.ÂµÈ.clear();
            this.áˆºÑ¢Õ.clear();
            this.£á.clear();
        }
    }
    
    public void Ø­áŒŠá() {
        ++this.à;
        for (final ITickableSound var2 : this.á) {
            var2.HorizonCode_Horizon_È();
            if (var2.ÂµÈ()) {
                this.Â(var2);
            }
            else {
                final String var3 = this.áŒŠÆ.get(var2);
                this.Âµá€.setVolume(var3, this.HorizonCode_Horizon_È(var2, this.áˆºÑ¢Õ.get(var2), this.Ý.HorizonCode_Horizon_È(var2.Â()).Âµá€()));
                this.Âµá€.setPitch(var3, this.HorizonCode_Horizon_È(var2, this.áˆºÑ¢Õ.get(var2)));
                this.Âµá€.setPosition(var3, var2.à(), var2.Ø(), var2.áŒŠÆ());
            }
        }
        final Iterator var1 = this.Ø.entrySet().iterator();
        while (var1.hasNext()) {
            final Map.Entry var4 = var1.next();
            final String var3 = var4.getKey();
            final ISound var5 = var4.getValue();
            if (!this.Âµá€.playing(var3)) {
                final int var6 = this.£á.get(var3);
                if (var6 > this.à) {
                    continue;
                }
                final int var7 = var5.Ø­áŒŠá();
                if (var5.Ý() && var7 > 0) {
                    this.ˆÏ­.put(var5, this.à + var7);
                }
                var1.remove();
                SoundManager.Â.debug(SoundManager.HorizonCode_Horizon_È, "Removed channel {} because it's not playing anymore", new Object[] { var3 });
                this.Âµá€.removeSource(var3);
                this.£á.remove(var3);
                this.áˆºÑ¢Õ.remove(var5);
                try {
                    this.ÂµÈ.remove((Object)this.Ý.HorizonCode_Horizon_È(var5.Â()).Âµá€(), (Object)var3);
                }
                catch (RuntimeException ex) {}
                if (!(var5 instanceof ITickableSound)) {
                    continue;
                }
                this.á.remove(var5);
            }
        }
        final Iterator var8 = this.ˆÏ­.entrySet().iterator();
        while (var8.hasNext()) {
            final Map.Entry var9 = var8.next();
            if (this.à >= var9.getValue()) {
                final ISound var5 = var9.getKey();
                if (var5 instanceof ITickableSound) {
                    ((ITickableSound)var5).HorizonCode_Horizon_È();
                }
                this.Ý(var5);
                var8.remove();
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final ISound p_148597_1_) {
        if (!this.Ó) {
            return false;
        }
        final String var2 = this.áŒŠÆ.get(p_148597_1_);
        return var2 != null && (this.Âµá€.playing(var2) || (this.£á.containsKey(var2) && this.£á.get(var2) <= this.à));
    }
    
    public void Â(final ISound p_148602_1_) {
        if (this.Ó) {
            final String var2 = this.áŒŠÆ.get(p_148602_1_);
            if (var2 != null) {
                this.Âµá€.stop(var2);
            }
        }
    }
    
    public void Ý(final ISound p_148611_1_) {
        if (this.Ó) {
            if (this.Âµá€.getMasterVolume() <= 0.0f) {
                SoundManager.Â.debug(SoundManager.HorizonCode_Horizon_È, "Skipped playing soundEvent: {}, master volume was zero", new Object[] { p_148611_1_.Â() });
            }
            else {
                final SoundEventAccessorComposite var2 = this.Ý.HorizonCode_Horizon_È(p_148611_1_.Â());
                if (var2 == null) {
                    SoundManager.Â.warn(SoundManager.HorizonCode_Horizon_È, "Unable to play unknown soundEvent: {}", new Object[] { p_148611_1_.Â() });
                }
                else {
                    final SoundPoolEntry var3 = var2.Ý();
                    if (var3 == SoundHandler.HorizonCode_Horizon_È) {
                        SoundManager.Â.warn(SoundManager.HorizonCode_Horizon_È, "Unable to play empty soundEvent: {}", new Object[] { var2.Ø­áŒŠá() });
                    }
                    else {
                        final float var4 = p_148611_1_.Âµá€();
                        float var5 = 16.0f;
                        if (var4 > 1.0f) {
                            var5 *= var4;
                        }
                        final SoundCategory var6 = var2.Âµá€();
                        final float var7 = this.HorizonCode_Horizon_È(p_148611_1_, var3, var6);
                        final double var8 = this.HorizonCode_Horizon_È(p_148611_1_, var3);
                        final ResourceLocation_1975012498 var9 = var3.HorizonCode_Horizon_È();
                        if (var7 == 0.0f) {
                            SoundManager.Â.debug(SoundManager.HorizonCode_Horizon_È, "Skipped playing sound {}, volume was zero.", new Object[] { var9 });
                        }
                        else {
                            final boolean var10 = p_148611_1_.Ý() && p_148611_1_.Ø­áŒŠá() == 0;
                            final String var11 = MathHelper.HorizonCode_Horizon_È((Random)ThreadLocalRandom.current()).toString();
                            if (var3.Ø­áŒŠá()) {
                                this.Âµá€.newStreamingSource(false, var11, HorizonCode_Horizon_È(var9), var9.toString(), var10, p_148611_1_.à(), p_148611_1_.Ø(), p_148611_1_.áŒŠÆ(), p_148611_1_.áˆºÑ¢Õ().HorizonCode_Horizon_È(), var5);
                            }
                            else {
                                this.Âµá€.newSource(false, var11, HorizonCode_Horizon_È(var9), var9.toString(), var10, p_148611_1_.à(), p_148611_1_.Ø(), p_148611_1_.áŒŠÆ(), p_148611_1_.áˆºÑ¢Õ().HorizonCode_Horizon_È(), var5);
                            }
                            SoundManager.Â.debug(SoundManager.HorizonCode_Horizon_È, "Playing sound {} for event {} as channel {}", new Object[] { var3.HorizonCode_Horizon_È(), var2.Ø­áŒŠá(), var11 });
                            this.Âµá€.setPitch(var11, (float)var8);
                            this.Âµá€.setVolume(var11, var7);
                            this.Âµá€.play(var11);
                            this.£á.put(var11, this.à + 20);
                            this.Ø.put(var11, p_148611_1_);
                            this.áˆºÑ¢Õ.put(p_148611_1_, var3);
                            if (var6 != SoundCategory.HorizonCode_Horizon_È) {
                                this.ÂµÈ.put((Object)var6, (Object)var11);
                            }
                            if (p_148611_1_ instanceof ITickableSound) {
                                this.á.add(p_148611_1_);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private float HorizonCode_Horizon_È(final ISound p_148606_1_, final SoundPoolEntry p_148606_2_) {
        return (float)MathHelper.HorizonCode_Horizon_È(p_148606_1_.Ó() * p_148606_2_.Â(), 0.5, 2.0);
    }
    
    private float HorizonCode_Horizon_È(final ISound p_148594_1_, final SoundPoolEntry p_148594_2_, final SoundCategory p_148594_3_) {
        return (float)MathHelper.HorizonCode_Horizon_È(p_148594_1_.Âµá€() * p_148594_2_.Ý(), 0.0, 1.0) * this.HorizonCode_Horizon_È(p_148594_3_);
    }
    
    public void Âµá€() {
        for (final String var2 : this.Ø.keySet()) {
            SoundManager.Â.debug(SoundManager.HorizonCode_Horizon_È, "Pausing channel {}", new Object[] { var2 });
            this.Âµá€.pause(var2);
        }
    }
    
    public void Ó() {
        for (final String var2 : this.Ø.keySet()) {
            SoundManager.Â.debug(SoundManager.HorizonCode_Horizon_È, "Resuming channel {}", new Object[] { var2 });
            this.Âµá€.play(var2);
        }
    }
    
    public void HorizonCode_Horizon_È(final ISound p_148599_1_, final int p_148599_2_) {
        this.ˆÏ­.put(p_148599_1_, this.à + p_148599_2_);
    }
    
    private static URL HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_148612_0_) {
        final String var1 = String.format("%s:%s:%s", "mcsounddomain", p_148612_0_.Ý(), p_148612_0_.Â());
        final URLStreamHandler var2 = new URLStreamHandler() {
            private static final String HorizonCode_Horizon_È = "CL_00001143";
            
            @Override
            protected URLConnection openConnection(final URL p_openConnection_1_) {
                return new URLConnection(p_openConnection_1_) {
                    private static final String Â = "CL_00001144";
                    
                    @Override
                    public void connect() {
                    }
                    
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return Minecraft.áŒŠà().Âµà().HorizonCode_Horizon_È(p_148612_0_).Â();
                    }
                };
            }
        };
        try {
            return new URL(null, var1, var2);
        }
        catch (MalformedURLException var3) {
            throw new Error("TODO: Sanely handle url exception! :D");
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_148615_1_, final float p_148615_2_) {
        if (this.Ó && p_148615_1_ != null) {
            final float var3 = p_148615_1_.Õ + (p_148615_1_.áƒ - p_148615_1_.Õ) * p_148615_2_;
            final float var4 = p_148615_1_.á€ + (p_148615_1_.É - p_148615_1_.á€) * p_148615_2_;
            final double var5 = p_148615_1_.áŒŠà + (p_148615_1_.ŒÏ - p_148615_1_.áŒŠà) * p_148615_2_;
            final double var6 = p_148615_1_.ŠÄ + (p_148615_1_.Çªà¢ - p_148615_1_.ŠÄ) * p_148615_2_ + p_148615_1_.Ðƒáƒ();
            final double var7 = p_148615_1_.Ñ¢á + (p_148615_1_.Ê - p_148615_1_.Ñ¢á) * p_148615_2_;
            final float var8 = MathHelper.Â((var4 + 90.0f) * 0.017453292f);
            final float var9 = MathHelper.HorizonCode_Horizon_È((var4 + 90.0f) * 0.017453292f);
            final float var10 = MathHelper.Â(-var3 * 0.017453292f);
            final float var11 = MathHelper.HorizonCode_Horizon_È(-var3 * 0.017453292f);
            final float var12 = MathHelper.Â((-var3 + 90.0f) * 0.017453292f);
            final float var13 = MathHelper.HorizonCode_Horizon_È((-var3 + 90.0f) * 0.017453292f);
            final float var14 = var8 * var10;
            final float var15 = var9 * var10;
            final float var16 = var8 * var12;
            final float var17 = var9 * var12;
            this.Âµá€.setListenerPosition((float)var5, (float)var6, (float)var7);
            this.Âµá€.setListenerOrientation(var14, var11, var15, var16, var13, var17);
        }
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final SoundManager soundManager, final HorizonCode_Horizon_È âµá€) {
        soundManager.Âµá€ = âµá€;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final SoundManager soundManager, final boolean ó) {
        soundManager.Ó = ó;
    }
    
    class HorizonCode_Horizon_È extends SoundSystem
    {
        private static final String Â = "CL_00001145";
        
        private HorizonCode_Horizon_È() {
        }
        
        public boolean playing(final String p_playing_1_) {
            final Object var2 = SoundSystemConfig.THREAD_SYNC;
            synchronized (SoundSystemConfig.THREAD_SYNC) {
                if (this.soundLibrary == null) {
                    // monitorexit(SoundSystemConfig.THREAD_SYNC)
                    return false;
                }
                final Source var3 = this.soundLibrary.getSources().get(p_playing_1_);
                // monitorexit(SoundSystemConfig.THREAD_SYNC)
                return var3 != null && (var3.playing() || var3.paused() || var3.preLoad);
            }
        }
        
        HorizonCode_Horizon_È(final SoundManager soundManager, final Object p_i45118_2_) {
            this(soundManager);
        }
    }
}
