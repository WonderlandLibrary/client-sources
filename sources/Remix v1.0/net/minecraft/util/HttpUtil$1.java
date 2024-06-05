package net.minecraft.util;

import org.apache.commons.io.*;
import java.io.*;
import java.net.*;
import java.util.*;

static final class HttpUtil$1 implements Runnable {
    final /* synthetic */ IProgressUpdate val$p_180192_4_;
    final /* synthetic */ String val$p_180192_1_;
    final /* synthetic */ Proxy val$p_180192_5_;
    final /* synthetic */ Map val$p_180192_2_;
    final /* synthetic */ File val$p_180192_0_;
    final /* synthetic */ int val$p_180192_3_;
    
    @Override
    public void run() {
        InputStream var2 = null;
        DataOutputStream var3 = null;
        while (true) {
            if (this.val$p_180192_4_ != null) {
                this.val$p_180192_4_.resetProgressAndMessage("Downloading Resource Pack");
                this.val$p_180192_4_.displayLoadingString("Making Request...");
                try {
                    final byte[] var4 = new byte[4096];
                    final URL var5 = new URL(this.val$p_180192_1_);
                    final URLConnection var6 = var5.openConnection(this.val$p_180192_5_);
                    float var7 = 0.0f;
                    float var8 = (float)this.val$p_180192_2_.entrySet().size();
                    for (final Map.Entry var10 : this.val$p_180192_2_.entrySet()) {
                        var6.setRequestProperty(var10.getKey(), var10.getValue());
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setLoadingProgress((int)(++var7 / var8 * 100.0f));
                        }
                    }
                    var2 = var6.getInputStream();
                    var8 = (float)var6.getContentLength();
                    final int var11 = var6.getContentLength();
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.displayLoadingString(String.format("Downloading file (%.2f MB)...", var8 / 1000.0f / 1000.0f));
                    }
                    if (this.val$p_180192_0_.exists()) {
                        final long var12 = this.val$p_180192_0_.length();
                        if (var12 == var11) {
                            if (this.val$p_180192_4_ != null) {
                                this.val$p_180192_4_.setDoneWorking();
                            }
                            return;
                        }
                        HttpUtil.access$000().warn("Deleting " + this.val$p_180192_0_ + " as it does not match what we currently have (" + var11 + " vs our " + var12 + ").");
                        FileUtils.deleteQuietly(this.val$p_180192_0_);
                    }
                    else if (this.val$p_180192_0_.getParentFile() != null) {
                        this.val$p_180192_0_.getParentFile().mkdirs();
                    }
                    var3 = new DataOutputStream(new FileOutputStream(this.val$p_180192_0_));
                    if (this.val$p_180192_3_ > 0 && var8 > this.val$p_180192_3_) {
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setDoneWorking();
                        }
                        throw new IOException("Filesize is bigger than maximum allowed (file is " + var7 + ", limit is " + this.val$p_180192_3_ + ")");
                    }
                    final boolean var13 = false;
                    int var14;
                    while ((var14 = var2.read(var4)) >= 0) {
                        var7 += var14;
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setLoadingProgress((int)(var7 / var8 * 100.0f));
                        }
                        if (this.val$p_180192_3_ > 0 && var7 > this.val$p_180192_3_) {
                            if (this.val$p_180192_4_ != null) {
                                this.val$p_180192_4_.setDoneWorking();
                            }
                            throw new IOException("Filesize was bigger than maximum allowed (got >= " + var7 + ", limit was " + this.val$p_180192_3_ + ")");
                        }
                        var3.write(var4, 0, var14);
                    }
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setDoneWorking();
                    }
                }
                catch (Throwable var15) {
                    var15.printStackTrace();
                }
                finally {
                    IOUtils.closeQuietly(var2);
                    IOUtils.closeQuietly((OutputStream)var3);
                }
                return;
            }
            continue;
        }
    }
}