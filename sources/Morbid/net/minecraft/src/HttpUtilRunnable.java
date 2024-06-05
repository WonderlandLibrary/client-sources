package net.minecraft.src;

import java.net.*;
import java.io.*;
import java.util.*;

final class HttpUtilRunnable implements Runnable
{
    final IProgressUpdate feedbackHook;
    final String sourceURL;
    final Map field_76177_c;
    final File destinationFile;
    final IDownloadSuccess downloadSuccess;
    final int maxFileSize;
    
    HttpUtilRunnable(final IProgressUpdate par1IProgressUpdate, final String par2Str, final Map par3Map, final File par4File, final IDownloadSuccess par5IDownloadSuccess, final int par6) {
        this.feedbackHook = par1IProgressUpdate;
        this.sourceURL = par2Str;
        this.field_76177_c = par3Map;
        this.destinationFile = par4File;
        this.downloadSuccess = par5IDownloadSuccess;
        this.maxFileSize = par6;
    }
    
    @Override
    public void run() {
        URLConnection var1 = null;
        InputStream var2 = null;
        DataOutputStream var3 = null;
        if (this.feedbackHook != null) {
            this.feedbackHook.resetProgressAndMessage("Downloading Texture Pack");
            this.feedbackHook.resetProgresAndWorkingMessage("Making Request...");
        }
        try {
            final byte[] var4 = new byte[4096];
            final URL var5 = new URL(this.sourceURL);
            var1 = var5.openConnection();
            float var6 = 0.0f;
            float var7 = this.field_76177_c.entrySet().size();
            for (final Map.Entry var9 : this.field_76177_c.entrySet()) {
                var1.setRequestProperty(var9.getKey(), var9.getValue());
                if (this.feedbackHook != null) {
                    this.feedbackHook.setLoadingProgress((int)(++var6 / var7 * 100.0f));
                }
            }
            var2 = var1.getInputStream();
            var7 = var1.getContentLength();
            final int var10 = var1.getContentLength();
            if (this.feedbackHook != null) {
                this.feedbackHook.resetProgresAndWorkingMessage(String.format("Downloading file (%.2f MB)...", var7 / 1000.0f / 1000.0f));
            }
            if (this.destinationFile.exists()) {
                final long var11 = this.destinationFile.length();
                if (var11 == var10) {
                    this.downloadSuccess.onSuccess(this.destinationFile);
                    if (this.feedbackHook != null) {
                        this.feedbackHook.onNoMoreProgress();
                    }
                    return;
                }
                System.out.println("Deleting " + this.destinationFile + " as it does not match what we currently have (" + var10 + " vs our " + var11 + ").");
                this.destinationFile.delete();
            }
            var3 = new DataOutputStream(new FileOutputStream(this.destinationFile));
            if (this.maxFileSize > 0 && var7 > this.maxFileSize) {
                if (this.feedbackHook != null) {
                    this.feedbackHook.onNoMoreProgress();
                }
                throw new IOException("Filesize is bigger than maximum allowed (file is " + var6 + ", limit is " + this.maxFileSize + ")");
            }
            final boolean var12 = false;
            int var13;
            while ((var13 = var2.read(var4)) >= 0) {
                var6 += var13;
                if (this.feedbackHook != null) {
                    this.feedbackHook.setLoadingProgress((int)(var6 / var7 * 100.0f));
                }
                if (this.maxFileSize > 0 && var6 > this.maxFileSize) {
                    if (this.feedbackHook != null) {
                        this.feedbackHook.onNoMoreProgress();
                    }
                    throw new IOException("Filesize was bigger than maximum allowed (got >= " + var6 + ", limit was " + this.maxFileSize + ")");
                }
                var3.write(var4, 0, var13);
            }
            this.downloadSuccess.onSuccess(this.destinationFile);
            if (this.feedbackHook != null) {
                this.feedbackHook.onNoMoreProgress();
                return;
            }
        }
        catch (Throwable var14) {
            var14.printStackTrace();
        }
        finally {
            try {
                if (var2 != null) {
                    var2.close();
                }
            }
            catch (IOException ex) {}
            try {
                if (var3 != null) {
                    var3.close();
                }
            }
            catch (IOException ex2) {}
        }
        try {
            if (var2 != null) {
                var2.close();
            }
        }
        catch (IOException ex3) {}
        try {
            if (var3 != null) {
                var3.close();
            }
        }
        catch (IOException ex4) {}
    }
}
