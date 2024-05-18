package HORIZON-6-0-SKIDPROTECTION;

import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.util.zip.InflaterInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import com.google.common.collect.Lists;
import java.util.List;
import java.io.RandomAccessFile;
import java.io.File;

public class RegionFile
{
    private static final byte[] HorizonCode_Horizon_È;
    private final File Â;
    private RandomAccessFile Ý;
    private final int[] Ø­áŒŠá;
    private final int[] Âµá€;
    private List Ó;
    private int à;
    private long Ø;
    private static final String áŒŠÆ = "CL_00000381";
    
    static {
        HorizonCode_Horizon_È = new byte[4096];
    }
    
    public RegionFile(final File p_i2001_1_) {
        this.Ø­áŒŠá = new int[1024];
        this.Âµá€ = new int[1024];
        this.Â = p_i2001_1_;
        this.à = 0;
        try {
            if (p_i2001_1_.exists()) {
                this.Ø = p_i2001_1_.lastModified();
            }
            this.Ý = new RandomAccessFile(p_i2001_1_, "rw");
            if (this.Ý.length() < 4096L) {
                for (int var2 = 0; var2 < 1024; ++var2) {
                    this.Ý.writeInt(0);
                }
                for (int var2 = 0; var2 < 1024; ++var2) {
                    this.Ý.writeInt(0);
                }
                this.à += 8192;
            }
            if ((this.Ý.length() & 0xFFFL) != 0x0L) {
                for (int var2 = 0; var2 < (this.Ý.length() & 0xFFFL); ++var2) {
                    this.Ý.write(0);
                }
            }
            int var2 = (int)this.Ý.length() / 4096;
            this.Ó = Lists.newArrayListWithCapacity(var2);
            for (int var3 = 0; var3 < var2; ++var3) {
                this.Ó.add(true);
            }
            this.Ó.set(0, false);
            this.Ó.set(1, false);
            this.Ý.seek(0L);
            for (int var3 = 0; var3 < 1024; ++var3) {
                final int var4 = this.Ý.readInt();
                this.Ø­áŒŠá[var3] = var4;
                if (var4 != 0 && (var4 >> 8) + (var4 & 0xFF) <= this.Ó.size()) {
                    for (int var5 = 0; var5 < (var4 & 0xFF); ++var5) {
                        this.Ó.set((var4 >> 8) + var5, false);
                    }
                }
            }
            for (int var3 = 0; var3 < 1024; ++var3) {
                final int var4 = this.Ý.readInt();
                this.Âµá€[var3] = var4;
            }
        }
        catch (IOException var6) {
            var6.printStackTrace();
        }
    }
    
    public synchronized DataInputStream HorizonCode_Horizon_È(final int p_76704_1_, final int p_76704_2_) {
        if (this.Ø­áŒŠá(p_76704_1_, p_76704_2_)) {
            return null;
        }
        try {
            final int var3 = this.Âµá€(p_76704_1_, p_76704_2_);
            if (var3 == 0) {
                return null;
            }
            final int var4 = var3 >> 8;
            final int var5 = var3 & 0xFF;
            if (var4 + var5 > this.Ó.size()) {
                return null;
            }
            this.Ý.seek(var4 * 4096);
            final int var6 = this.Ý.readInt();
            if (var6 > 4096 * var5) {
                return null;
            }
            if (var6 <= 0) {
                return null;
            }
            final byte var7 = this.Ý.readByte();
            if (var7 == 1) {
                final byte[] var8 = new byte[var6 - 1];
                this.Ý.read(var8);
                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var8))));
            }
            if (var7 == 2) {
                final byte[] var8 = new byte[var6 - 1];
                this.Ý.read(var8);
                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var8))));
            }
            return null;
        }
        catch (IOException var9) {
            return null;
        }
    }
    
    public DataOutputStream Â(final int p_76710_1_, final int p_76710_2_) {
        DataOutputStream dataOutputStream;
        if (this.Ø­áŒŠá(p_76710_1_, p_76710_2_)) {
            dataOutputStream = null;
        }
        else {
            final DeflaterOutputStream deflaterOutputStream;
            dataOutputStream = new DataOutputStream(deflaterOutputStream);
            deflaterOutputStream = new DeflaterOutputStream(new HorizonCode_Horizon_È(p_76710_1_, p_76710_2_));
        }
        return dataOutputStream;
    }
    
    protected synchronized void HorizonCode_Horizon_È(final int p_76706_1_, final int p_76706_2_, final byte[] p_76706_3_, final int p_76706_4_) {
        try {
            final int var5 = this.Âµá€(p_76706_1_, p_76706_2_);
            int var6 = var5 >> 8;
            final int var7 = var5 & 0xFF;
            final int var8 = (p_76706_4_ + 5) / 4096 + 1;
            if (var8 >= 256) {
                return;
            }
            if (var6 != 0 && var7 == var8) {
                this.HorizonCode_Horizon_È(var6, p_76706_3_, p_76706_4_);
            }
            else {
                for (int var9 = 0; var9 < var7; ++var9) {
                    this.Ó.set(var6 + var9, true);
                }
                int var9 = this.Ó.indexOf(true);
                int var10 = 0;
                if (var9 != -1) {
                    for (int var11 = var9; var11 < this.Ó.size(); ++var11) {
                        if (var10 != 0) {
                            if (this.Ó.get(var11)) {
                                ++var10;
                            }
                            else {
                                var10 = 0;
                            }
                        }
                        else if (this.Ó.get(var11)) {
                            var9 = var11;
                            var10 = 1;
                        }
                        if (var10 >= var8) {
                            break;
                        }
                    }
                }
                if (var10 >= var8) {
                    var6 = var9;
                    this.HorizonCode_Horizon_È(p_76706_1_, p_76706_2_, var9 << 8 | var8);
                    for (int var11 = 0; var11 < var8; ++var11) {
                        this.Ó.set(var6 + var11, false);
                    }
                    this.HorizonCode_Horizon_È(var6, p_76706_3_, p_76706_4_);
                }
                else {
                    this.Ý.seek(this.Ý.length());
                    var6 = this.Ó.size();
                    for (int var11 = 0; var11 < var8; ++var11) {
                        this.Ý.write(RegionFile.HorizonCode_Horizon_È);
                        this.Ó.add(false);
                    }
                    this.à += 4096 * var8;
                    this.HorizonCode_Horizon_È(var6, p_76706_3_, p_76706_4_);
                    this.HorizonCode_Horizon_È(p_76706_1_, p_76706_2_, var6 << 8 | var8);
                }
            }
            this.Â(p_76706_1_, p_76706_2_, (int)(MinecraftServer.Œà() / 1000L));
        }
        catch (IOException var12) {
            var12.printStackTrace();
        }
    }
    
    private void HorizonCode_Horizon_È(final int p_76712_1_, final byte[] p_76712_2_, final int p_76712_3_) throws IOException {
        this.Ý.seek(p_76712_1_ * 4096);
        this.Ý.writeInt(p_76712_3_ + 1);
        this.Ý.writeByte(2);
        this.Ý.write(p_76712_2_, 0, p_76712_3_);
    }
    
    private boolean Ø­áŒŠá(final int p_76705_1_, final int p_76705_2_) {
        return p_76705_1_ < 0 || p_76705_1_ >= 32 || p_76705_2_ < 0 || p_76705_2_ >= 32;
    }
    
    private int Âµá€(final int p_76707_1_, final int p_76707_2_) {
        return this.Ø­áŒŠá[p_76707_1_ + p_76707_2_ * 32];
    }
    
    public boolean Ý(final int p_76709_1_, final int p_76709_2_) {
        return this.Âµá€(p_76709_1_, p_76709_2_) != 0;
    }
    
    private void HorizonCode_Horizon_È(final int p_76711_1_, final int p_76711_2_, final int p_76711_3_) throws IOException {
        this.Ø­áŒŠá[p_76711_1_ + p_76711_2_ * 32] = p_76711_3_;
        this.Ý.seek((p_76711_1_ + p_76711_2_ * 32) * 4);
        this.Ý.writeInt(p_76711_3_);
    }
    
    private void Â(final int p_76713_1_, final int p_76713_2_, final int p_76713_3_) throws IOException {
        this.Âµá€[p_76713_1_ + p_76713_2_ * 32] = p_76713_3_;
        this.Ý.seek(4096 + (p_76713_1_ + p_76713_2_ * 32) * 4);
        this.Ý.writeInt(p_76713_3_);
    }
    
    public void HorizonCode_Horizon_È() throws IOException {
        if (this.Ý != null) {
            this.Ý.close();
        }
    }
    
    class HorizonCode_Horizon_È extends ByteArrayOutputStream
    {
        private int Â;
        private int Ý;
        private static final String Ø­áŒŠá = "CL_00000382";
        
        public HorizonCode_Horizon_È(final int p_i2000_2_, final int p_i2000_3_) {
            super(8096);
            this.Â = p_i2000_2_;
            this.Ý = p_i2000_3_;
        }
        
        @Override
        public void close() throws IOException {
            RegionFile.this.HorizonCode_Horizon_È(this.Â, this.Ý, this.buf, this.count);
        }
    }
}
