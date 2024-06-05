package net.minecraft.world.storage;

import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
   List getSaveList() throws AnvilConverterException;

   ISaveHandler getSaveLoader(String var1, boolean var2);

   void flushCache();

   boolean convertMapFormat(String var1, IProgressUpdate var2);

   WorldInfo getWorldInfo(String var1);

   boolean func_154335_d(String var1);

   boolean func_154334_a(String var1);

   boolean deleteWorldDirectory(String var1);

   void renameWorld(String var1, String var2);

   boolean canLoadWorld(String var1);

   boolean isOldMapFormat(String var1);

   String func_154333_a();
}
