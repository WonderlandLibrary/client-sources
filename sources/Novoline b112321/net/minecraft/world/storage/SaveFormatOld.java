package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld implements ISaveFormat {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final File savesDirectory;

   public SaveFormatOld(File var1) {
      if(!var1.exists()) {
         var1.mkdirs();
      }

      this.savesDirectory = var1;
   }

   public String getName() {
      return "Old Format";
   }

   public List getSaveList() throws AnvilConverterException {
      ArrayList var1 = Lists.newArrayList();

      for(int var2 = 0; var2 < 5; ++var2) {
         String var3 = "World" + (var2 + 1);
         WorldInfo var4 = this.getWorldInfo(var3);
         var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), var4.getGameType(), false, var4.isHardcoreModeEnabled(), var4.areCommandsAllowed()));
      }

      return var1;
   }

   public void flushCache() {
   }

   public WorldInfo getWorldInfo(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      if(!var2.exists()) {
         return null;
      } else {
         File var3 = new File(var2, "level.dat");
         if(var3.exists()) {
            try {
               NBTTagCompound var9 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
               NBTTagCompound var10 = var9.getCompoundTag("Data");
               return new WorldInfo(var10);
            } catch (Exception var7) {
               LOGGER.error("Exception reading " + var3, var7);
            }
         }

         var3 = new File(var2, "level.dat_old");
         if(var3.exists()) {
            try {
               NBTTagCompound var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
               NBTTagCompound var5 = var4.getCompoundTag("Data");
               return new WorldInfo(var5);
            } catch (Exception var6) {
               LOGGER.error("Exception reading " + var3, var6);
            }
         }

         return null;
      }
   }

   public void renameWorld(String var1, String var2) {
      File var3 = new File(this.savesDirectory, var1);
      if(var3.exists()) {
         File var4 = new File(var3, "level.dat");
         if(var4.exists()) {
            try {
               NBTTagCompound var5 = CompressedStreamTools.readCompressed(new FileInputStream(var4));
               NBTTagCompound var6 = var5.getCompoundTag("Data");
               var6.setString("LevelName", var2);
               CompressedStreamTools.writeCompressed(var5, new FileOutputStream(var4));
            } catch (Exception var7) {
               var7.printStackTrace();
            }
         }
      }

   }

   public boolean func_154335_d(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      if(var2.exists()) {
         return false;
      } else {
         File var10000 = var2;

         try {
            var10000.mkdir();
            var2.delete();
            return true;
         } catch (Throwable var4) {
            LOGGER.warn("Couldn\'t make new level", var4);
            return false;
         }
      }
   }

   public boolean deleteWorldDirectory(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      if(!var2.exists()) {
         return true;
      } else {
         LOGGER.info("Deleting level " + var1);

         for(int var3 = 1; var3 <= 5; ++var3) {
            LOGGER.info("Attempt " + var3 + "...");
            if(deleteFiles(var2.listFiles())) {
               break;
            }

            LOGGER.warn("Unsuccessful in deleting contents.");
            if(var3 < 5) {
               long var10000 = 500L;

               try {
                  Thread.sleep(var10000);
               } catch (InterruptedException var5) {
                  ;
               }
            }
         }

         return var2.delete();
      }
   }

   protected static boolean deleteFiles(File[] var0) {
      for(File var4 : var0) {
         LOGGER.debug("Deleting " + var4);
         if(var4.isDirectory() && !deleteFiles(var4.listFiles())) {
            LOGGER.warn("Couldn\'t delete directory " + var4);
            return false;
         }

         if(!var4.delete()) {
            LOGGER.warn("Couldn\'t delete file " + var4);
            return false;
         }
      }

      return true;
   }

   public ISaveHandler getSaveLoader(String var1, boolean var2) {
      return new SaveHandler(this.savesDirectory, var1, var2);
   }

   public boolean func_154334_a(String var1) {
      return false;
   }

   public boolean isOldMapFormat(String var1) {
      return false;
   }

   public boolean convertMapFormat(String var1, IProgressUpdate var2) {
      return false;
   }

   public boolean canLoadWorld(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      return var2.isDirectory();
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
