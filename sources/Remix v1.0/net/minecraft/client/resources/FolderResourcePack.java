package net.minecraft.client.resources;

import com.google.common.collect.*;
import org.apache.commons.io.filefilter.*;
import java.io.*;
import java.util.*;

public class FolderResourcePack extends AbstractResourcePack
{
    public FolderResourcePack(final File p_i1291_1_) {
        super(p_i1291_1_);
    }
    
    @Override
    protected InputStream getInputStreamByName(final String p_110591_1_) throws IOException {
        return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, p_110591_1_)));
    }
    
    @Override
    protected boolean hasResourceName(final String p_110593_1_) {
        return new File(this.resourcePackFile, p_110593_1_).isFile();
    }
    
    @Override
    public Set getResourceDomains() {
        final HashSet var1 = Sets.newHashSet();
        final File var2 = new File(this.resourcePackFile, "assets/");
        if (var2.isDirectory()) {
            for (final File var6 : var2.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)) {
                final String var7 = AbstractResourcePack.getRelativeName(var2, var6);
                if (!var7.equals(var7.toLowerCase())) {
                    this.logNameNotLowercase(var7);
                }
                else {
                    var1.add(var7.substring(0, var7.length() - 1));
                }
            }
        }
        return var1;
    }
}
