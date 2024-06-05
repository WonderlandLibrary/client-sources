package net.minecraft.server.integrated;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class IntegratedPlayerList extends ServerConfigurationManager {
   private NBTTagCompound hostPlayerData;
   private static final String __OBFID = "CL_00001128";

   public IntegratedPlayerList(IntegratedServer var1) {
      super(var1);
      this.setViewDistance(10);
   }

   protected void writePlayerData(EntityPlayerMP var1) {
      if (var1.getName().equals(this.func_180603_b().getServerOwner())) {
         this.hostPlayerData = new NBTTagCompound();
         var1.writeToNBT(this.hostPlayerData);
      }

      super.writePlayerData(var1);
   }

   public MinecraftServer getServerInstance() {
      return this.func_180603_b();
   }

   public IntegratedServer func_180603_b() {
      return (IntegratedServer)super.getServerInstance();
   }

   public NBTTagCompound getHostPlayerData() {
      return this.hostPlayerData;
   }

   public String allowUserToConnect(SocketAddress var1, GameProfile var2) {
      return var2.getName().equalsIgnoreCase(this.func_180603_b().getServerOwner()) && this.getPlayerByUsername(var2.getName()) != null ? "That name is already taken." : super.allowUserToConnect(var1, var2);
   }
}
