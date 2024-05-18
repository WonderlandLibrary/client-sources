/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  org.apache.commons.io.filefilter.DirectoryFileFilter
 *  org.apache.commons.io.filefilter.IOFileFilter
 */
package net.minecraft.client.resources;

import com.google.common.collect.Sets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.resources.AbstractResourcePack;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FolderResourcePack
extends AbstractResourcePack {
    private static final String __OBFID = "CL_00001076";

    public FolderResourcePack(File p_i1291_1_) {
        super(p_i1291_1_);
    }

    @Override
    protected InputStream getInputStreamByName(String p_110591_1_) throws IOException {
        return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, p_110591_1_)));
    }

    @Override
    protected boolean hasResourceName(String p_110593_1_) {
        return new File(this.resourcePackFile, p_110593_1_).isFile();
    }

    @Override
    public Set getResourceDomains() {
        HashSet var1 = Sets.newHashSet();
        File var2 = new File(this.resourcePackFile, "assets/");
        if (var2.isDirectory()) {
            File[] var3 = var2.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
            int var4 = var3.length;
            int var5 = 0;
            while (var5 < var4) {
                File var6 = var3[var5];
                String var7 = FolderResourcePack.getRelativeName(var2, var6);
                if (!var7.equals(var7.toLowerCase())) {
                    this.logNameNotLowercase(var7);
                } else {
                    var1.add(var7.substring(0, var7.length() - 1));
                }
                ++var5;
            }
        }
        return var1;
    }
}

