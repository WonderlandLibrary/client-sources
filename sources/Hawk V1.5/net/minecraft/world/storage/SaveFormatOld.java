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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld implements ISaveFormat {
   private static final String __OBFID = "CL_00000586";
   protected final File savesDirectory;
   private static final Logger logger = LogManager.getLogger();

   public boolean func_154335_d(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      if (var2.exists()) {
         return false;
      } else {
         try {
            var2.mkdir();
            var2.delete();
            return true;
         } catch (Throwable var4) {
            logger.warn("Couldn't make new level", var4);
            return false;
         }
      }
   }

   public void renameWorld(String var1, String var2) {
      File var3 = new File(this.savesDirectory, var1);
      if (var3.exists()) {
         File var4 = new File(var3, "level.dat");
         if (var4.exists()) {
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

   public ISaveHandler getSaveLoader(String var1, boolean var2) {
      return new SaveHandler(this.savesDirectory, var1, var2);
   }

   public String func_154333_a() {
      return "Old Format";
   }

   public boolean convertMapFormat(String var1, IProgressUpdate var2) {
      return false;
   }

   public void flushCache() {
   }

   public boolean canLoadWorld(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      return var2.isDirectory();
   }

   public boolean func_154334_a(String var1) {
      return false;
   }

   public boolean deleteWorldDirectory(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      if (!var2.exists()) {
         return true;
      } else {
         logger.info(String.valueOf((new StringBuilder("Deleting level ")).append(var1)));

         for(int var3 = 1; var3 <= 5; ++var3) {
            logger.info(String.valueOf((new StringBuilder("Attempt ")).append(var3).append("...")));
            if (deleteFiles(var2.listFiles())) {
               break;
            }

            logger.warn("Unsuccessful in deleting contents.");
            if (var3 < 5) {
               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var5) {
               }
            }
         }

         return var2.delete();
      }
   }

   public boolean isOldMapFormat(String var1) {
      return false;
   }

   public List getSaveList() throws AnvilConverterException {
      ArrayList var1 = Lists.newArrayList();

      for(int var2 = 0; var2 < 5; ++var2) {
         String var3 = String.valueOf((new StringBuilder("World")).append(var2 + 1));
         WorldInfo var4 = this.getWorldInfo(var3);
         if (var4 != null) {
            var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), var4.getGameType(), false, var4.isHardcoreModeEnabled(), var4.areCommandsAllowed()));
         }
      }

      return var1;
   }

   protected static boolean deleteFiles(File[] var0) {
      for(int var1 = 0; var1 < var0.length; ++var1) {
         File var2 = var0[var1];
         logger.debug(String.valueOf((new StringBuilder("Deleting ")).append(var2)));
         if (var2.isDirectory() && !deleteFiles(var2.listFiles())) {
            logger.warn(String.valueOf((new StringBuilder("Couldn't delete directory ")).append(var2)));
            return false;
         }

         if (!var2.delete()) {
            logger.warn(String.valueOf((new StringBuilder("Couldn't delete file ")).append(var2)));
            return false;
         }
      }

      return true;
   }

   public SaveFormatOld(File var1) {
      if (!var1.exists()) {
         var1.mkdirs();
      }

      this.savesDirectory = var1;
   }

   public WorldInfo getWorldInfo(String var1) {
      File var2 = new File(this.savesDirectory, var1);
      if (!var2.exists()) {
         return null;
      } else {
         File var3 = new File(var2, "level.dat");
         NBTTagCompound var4;
         NBTTagCompound var5;
         if (var3.exists()) {
            try {
               var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
               var5 = var4.getCompoundTag("Data");
               return new WorldInfo(var5);
            } catch (Exception var8) {
               logger.error(String.valueOf((new StringBuilder("Exception reading ")).append(var3)), var8);
            }
         }

         var3 = new File(var2, "level.dat_old");
         if (var3.exists()) {
            try {
               var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
               var5 = var4.getCompoundTag("Data");
               return new WorldInfo(var5);
            } catch (Exception var7) {
               logger.error(String.valueOf((new StringBuilder("Exception reading ")).append(var3)), var7);
            }
         }

         return null;
      }
   }
}
