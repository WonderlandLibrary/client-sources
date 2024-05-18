package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.io.IOUtils;
import java.io.Closeable;
import java.awt.image.BufferedImage;
import java.util.Map;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.Future;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import com.google.common.io.Files;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.Futures;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.Iterator;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.io.File;
import java.io.FileFilter;
import org.apache.logging.log4j.Logger;

public class ResourcePackRepository
{
    private static final Logger Ý;
    private static final FileFilter Ø­áŒŠá;
    private final File Âµá€;
    public final IResourcePack HorizonCode_Horizon_È;
    private final File Ó;
    public final IMetadataSerializer Â;
    private IResourcePack à;
    private final ReentrantLock Ø;
    private ListenableFuture áŒŠÆ;
    private List áˆºÑ¢Õ;
    private List ÂµÈ;
    private static final String á = "CL_00001087";
    
    static {
        Ý = LogManager.getLogger();
        Ø­áŒŠá = new FileFilter() {
            private static final String HorizonCode_Horizon_È = "CL_00001088";
            
            @Override
            public boolean accept(final File p_accept_1_) {
                final boolean var2 = p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip");
                final boolean var3 = p_accept_1_.isDirectory() && new File(p_accept_1_, "pack.mcmeta").isFile();
                return var2 || var3;
            }
        };
    }
    
    public ResourcePackRepository(final File p_i45101_1_, final File p_i45101_2_, final IResourcePack p_i45101_3_, final IMetadataSerializer p_i45101_4_, final GameSettings p_i45101_5_) {
        this.Ø = new ReentrantLock();
        this.áˆºÑ¢Õ = Lists.newArrayList();
        this.ÂµÈ = Lists.newArrayList();
        this.Âµá€ = p_i45101_1_;
        this.Ó = p_i45101_2_;
        this.HorizonCode_Horizon_È = p_i45101_3_;
        this.Â = p_i45101_4_;
        this.à();
        this.HorizonCode_Horizon_È();
        for (final String var7 : p_i45101_5_.ÇŽá) {
            for (final HorizonCode_Horizon_È var9 : this.áˆºÑ¢Õ) {
                if (var9.Ø­áŒŠá().equals(var7)) {
                    this.ÂµÈ.add(var9);
                    break;
                }
            }
        }
    }
    
    private void à() {
        if (!this.Âµá€.isDirectory() && (!this.Âµá€.delete() || !this.Âµá€.mkdirs())) {
            ResourcePackRepository.Ý.debug("Unable to create resourcepack folder: " + this.Âµá€);
        }
    }
    
    private List Ø() {
        return this.Âµá€.isDirectory() ? Arrays.asList(this.Âµá€.listFiles(ResourcePackRepository.Ø­áŒŠá)) : Collections.emptyList();
    }
    
    public void HorizonCode_Horizon_È() {
        final ArrayList var1 = Lists.newArrayList();
        for (final File var3 : this.Ø()) {
            final HorizonCode_Horizon_È var4 = new HorizonCode_Horizon_È(var3, null);
            if (!this.áˆºÑ¢Õ.contains(var4)) {
                try {
                    var4.HorizonCode_Horizon_È();
                    var1.add(var4);
                }
                catch (Exception var7) {
                    var1.remove(var4);
                }
            }
            else {
                final int var5 = this.áˆºÑ¢Õ.indexOf(var4);
                if (var5 <= -1 || var5 >= this.áˆºÑ¢Õ.size()) {
                    continue;
                }
                var1.add(this.áˆºÑ¢Õ.get(var5));
            }
        }
        this.áˆºÑ¢Õ.removeAll(var1);
        for (final HorizonCode_Horizon_È var6 : this.áˆºÑ¢Õ) {
            var6.Â();
        }
        this.áˆºÑ¢Õ = var1;
    }
    
    public List Â() {
        return (List)ImmutableList.copyOf((Collection)this.áˆºÑ¢Õ);
    }
    
    public List Ý() {
        return (List)ImmutableList.copyOf((Collection)this.ÂµÈ);
    }
    
    public void HorizonCode_Horizon_È(final List p_148527_1_) {
        this.ÂµÈ.clear();
        this.ÂµÈ.addAll(p_148527_1_);
    }
    
    public File Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public ListenableFuture HorizonCode_Horizon_È(final String p_180601_1_, final String p_180601_2_) {
        String var3;
        if (p_180601_2_.matches("^[a-f0-9]{40}$")) {
            var3 = p_180601_2_;
        }
        else {
            var3 = p_180601_1_.substring(p_180601_1_.lastIndexOf("/") + 1);
            if (var3.contains("?")) {
                var3 = var3.substring(0, var3.indexOf("?"));
            }
            if (!var3.endsWith(".zip")) {
                return Futures.immediateFailedFuture((Throwable)new IllegalArgumentException("Invalid filename; must end in .zip"));
            }
            var3 = "legacy_" + var3.replaceAll("\\W", "");
        }
        final File var4 = new File(this.Ó, var3);
        this.Ø.lock();
        ListenableFuture var12;
        try {
            this.Ó();
            if (var4.exists() && p_180601_2_.length() == 40) {
                try {
                    final String var5 = Hashing.sha1().hashBytes(Files.toByteArray(var4)).toString();
                    if (var5.equals(p_180601_2_)) {
                        final ListenableFuture var6 = this.HorizonCode_Horizon_È(var4);
                        return var6;
                    }
                    ResourcePackRepository.Ý.warn("File " + var4 + " had wrong hash (expected " + p_180601_2_ + ", found " + var5 + "). Deleting it.");
                    FileUtils.deleteQuietly(var4);
                }
                catch (IOException var7) {
                    ResourcePackRepository.Ý.warn("File " + var4 + " couldn't be hashed. Deleting it.", (Throwable)var7);
                    FileUtils.deleteQuietly(var4);
                }
            }
            final GuiScreenWorking var8 = new GuiScreenWorking();
            final Map var9 = Minecraft.£Ï();
            final Minecraft var10 = Minecraft.áŒŠà();
            Futures.getUnchecked((Future)var10.HorizonCode_Horizon_È(new Runnable() {
                private static final String Â = "CL_00001089";
                
                @Override
                public void run() {
                    var10.HorizonCode_Horizon_È(var8);
                }
            }));
            final SettableFuture var11 = SettableFuture.create();
            Futures.addCallback(this.áŒŠÆ = HttpUtil.HorizonCode_Horizon_È(var4, p_180601_1_, var9, 52428800, var8, var10.ŠÂµà()), (FutureCallback)new FutureCallback() {
                private static final String Â = "CL_00002394";
                
                public void onSuccess(final Object p_onSuccess_1_) {
                    ResourcePackRepository.this.HorizonCode_Horizon_È(var4);
                    var11.set((Object)null);
                }
                
                public void onFailure(final Throwable p_onFailure_1_) {
                    var11.setException(p_onFailure_1_);
                }
            });
            var12 = this.áŒŠÆ;
        }
        finally {
            this.Ø.unlock();
        }
        this.Ø.unlock();
        return var12;
    }
    
    public ListenableFuture HorizonCode_Horizon_È(final File p_177319_1_) {
        this.à = new FileResourcePack(p_177319_1_);
        return Minecraft.áŒŠà().ŠÄ();
    }
    
    public IResourcePack Âµá€() {
        return this.à;
    }
    
    public void Ó() {
        this.Ø.lock();
        try {
            if (this.áŒŠÆ != null) {
                this.áŒŠÆ.cancel(true);
            }
            this.áŒŠÆ = null;
            this.à = null;
        }
        finally {
            this.Ø.unlock();
        }
        this.Ø.unlock();
    }
    
    public class HorizonCode_Horizon_È
    {
        private final File Â;
        private IResourcePack Ý;
        private PackMetadataSection Ø­áŒŠá;
        private BufferedImage Âµá€;
        private ResourceLocation_1975012498 Ó;
        private static final String à = "CL_00001090";
        
        private HorizonCode_Horizon_È(final File p_i1295_2_) {
            this.Â = p_i1295_2_;
        }
        
        public void HorizonCode_Horizon_È() throws IOException {
            this.Ý = (this.Â.isDirectory() ? new FolderResourcePack(this.Â) : new FileResourcePack(this.Â));
            this.Ø­áŒŠá = (PackMetadataSection)this.Ý.HorizonCode_Horizon_È(ResourcePackRepository.this.Â, "pack");
            try {
                this.Âµá€ = this.Ý.HorizonCode_Horizon_È();
            }
            catch (IOException ex) {}
            if (this.Âµá€ == null) {
                this.Âµá€ = ResourcePackRepository.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
            }
            this.Â();
        }
        
        public void HorizonCode_Horizon_È(final TextureManager p_110518_1_) {
            if (this.Ó == null) {
                this.Ó = p_110518_1_.HorizonCode_Horizon_È("texturepackicon", new DynamicTexture(this.Âµá€));
            }
            p_110518_1_.HorizonCode_Horizon_È(this.Ó);
        }
        
        public void Â() {
            if (this.Ý instanceof Closeable) {
                IOUtils.closeQuietly((Closeable)this.Ý);
            }
        }
        
        public IResourcePack Ý() {
            return this.Ý;
        }
        
        public String Ø­áŒŠá() {
            return this.Ý.Â();
        }
        
        public String Âµá€() {
            return (this.Ø­áŒŠá == null) ? (EnumChatFormatting.ˆÏ­ + "Invalid pack.mcmeta (or missing 'pack' section)") : this.Ø­áŒŠá.HorizonCode_Horizon_È().áŒŠÆ();
        }
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            return this == p_equals_1_ || (p_equals_1_ instanceof HorizonCode_Horizon_È && this.toString().equals(p_equals_1_.toString()));
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        @Override
        public String toString() {
            return String.format("%s:%s:%d", this.Â.getName(), this.Â.isDirectory() ? "folder" : "zip", this.Â.lastModified());
        }
        
        HorizonCode_Horizon_È(final ResourcePackRepository resourcePackRepository, final File p_i1296_2_, final Object p_i1296_3_) {
            this(resourcePackRepository, p_i1296_2_);
        }
    }
}
