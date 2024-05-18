package net.SliceClient.Utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;

public class NetHandlerPlayClientHook
  extends NetHandlerPlayClient
{
  public static NetworkManager netManager;
  
  public NetHandlerPlayClientHook(Minecraft mc, GuiScreen gui, NetworkManager netManager, GameProfile profile)
  {
    super(mc, gui, netManager, profile);
    netManager = netManager;
  }
}
