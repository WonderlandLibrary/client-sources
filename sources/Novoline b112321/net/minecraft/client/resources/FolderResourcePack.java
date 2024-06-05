package net.minecraft.client.resources;

import com.google.common.collect.Sets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.resources.AbstractResourcePack;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class FolderResourcePack extends AbstractResourcePack {
   public FolderResourcePack(File var1) {
      super(var1);
   }

   protected InputStream getInputStreamByName(String var1) throws IOException {
      return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, var1)));
   }

   protected boolean hasResourceName(String var1) {
      return (new File(this.resourcePackFile, var1)).isFile();
   }

   public Set getResourceDomains() {
      HashSet var1 = Sets.newHashSet();
      File var2 = new File(this.resourcePackFile, "assets/");
      if(var2.isDirectory()) {
         for(File var6 : var2.listFiles(DirectoryFileFilter.DIRECTORY)) {
            String var7 = getRelativeName(var2, var6);
            if(!var7.equals(var7.toLowerCase())) {
               this.logNameNotLowercase(var7);
            } else {
               var1.add(var7.substring(0, var7.length() - 1));
            }
         }
      }

      return var1;
   }
}
