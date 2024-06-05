package net.minecraft.client.main;

import net.minecraft.client.main.GameConfiguration$DisplayInformation;
import net.minecraft.client.main.GameConfiguration$FolderInformation;
import net.minecraft.client.main.GameConfiguration$GameInformation;
import net.minecraft.client.main.GameConfiguration$ServerInformation;
import net.minecraft.client.main.GameConfiguration$UserInformation;

public class GameConfiguration {
   public final GameConfiguration$UserInformation userInfo;
   public final GameConfiguration$DisplayInformation displayInfo;
   public final GameConfiguration$FolderInformation folderInfo;
   public final GameConfiguration$GameInformation gameInfo;
   public final GameConfiguration$ServerInformation serverInfo;

   public GameConfiguration(GameConfiguration$UserInformation var1, GameConfiguration$DisplayInformation var2, GameConfiguration$FolderInformation var3, GameConfiguration$GameInformation var4, GameConfiguration$ServerInformation var5) {
      this.userInfo = var1;
      this.displayInfo = var2;
      this.folderInfo = var3;
      this.gameInfo = var4;
      this.serverInfo = var5;
   }
}
