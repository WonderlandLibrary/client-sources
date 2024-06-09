package me.hexxed.mercury.gui;

import java.util.ArrayList;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.macros.MacroManager;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.modules.tabgui.Tabenter;
import me.hexxed.mercury.overlay.OverlayManager;
import me.hexxed.mercury.util.TimeHelper;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import org.darkstorm.minecraft.gui.MercuryGuiManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiIngameHook extends GuiIngame
{
  private boolean[] keyStates;
  private Minecraft mc = Minecraft.getMinecraft();
  private FontRenderer fr = mc.fontRendererObj;
  
  public GuiIngameHook(Minecraft mcIn) {
    super(mcIn);
    keyStates = new boolean['Ā'];
  }
  
  public boolean checkKey(int i) {
    if (mc.currentScreen != null) {
      return false;
    }
    
    if (Keyboard.isKeyDown(i) != keyStates[i]) {
      return keyStates[i] = keyStates[i] != 0 ? 0 : 1;
    }
    return false;
  }
  

  java.util.List<String> names = new ArrayList();
  public static boolean menu = false;
  
  private int ping = 0;
  
  public void func_175180_a(float p_175180_1_)
  {
    if (getMinecraftingameGUI.getChatGUI().getChatOpen()) Tabenter.chat.setLastMS();
    super.func_175180_a(p_175180_1_);
    if (mc.theWorld == null) {
      return;
    }
    ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int width = sr.getScaledWidth();
    int height = sr.getScaledHeight();
    getValueswidth = width;
    getValuesheight = height;
    








































    if ((checkKey(57)) && (ModuleManager.getModByName("HighJump").isEnabled()) && (ModuleManager.isAntiCheatOn())) {
      Util.damagePlayer(1);
    }
    


    if (checkKey(56)) {
      if (mc.currentScreen == null) {
        mc.displayGuiScreen(Mercury.getInstance().getGui());
        menu = true;
      } else {
        menu = false;
      }
    }
    
    if (getMinecraftcurrentScreen != Mercury.getInstance().getGui()) {
      Mercury.getGuiManager().renderPinned();
    }
    
    Mercury.getGuiManager().update();
    


    int key = -1;
    Mercury.getModuleManager(); for (Module m : ModuleManager.moduleList)
    {
      if (((checkKey(m.getKeybind())) || (m.getKeybind() == key)) && (m.getKeybind() != 0)) {
        if (m.getCategory() == ModuleCategory.NONE) {
          m.setStateSilent(!m.isEnabled());
        } else {
          m.toggle();
        }
        key = m.getKeybind();
      }
    }
    
    MacroManager.getManager().checkMacroKeys();
    
    try
    {
      ping = mc.thePlayer.sendQueue.func_175102_a(mc.thePlayer.getUniqueID()).getResponseTime().intValue();
    } catch (NullPointerException e) {
      ping = 0;
    }
    getValuesping = ping;
    if (!mc.gameSettings.showDebugInfo) {
      OverlayManager.getManager().draw();
    }
  }
  


























  private void drawArmorStatus(ScaledResolution scaledRes)
  {
    if (mc.playerController.isNotCreative()) {
      int x = 15;
      GL11.glPushMatrix();
      RenderHelper.enableGUIStandardItemLighting();
      for (int index = 3; index >= 0; index--) {
        ItemStack stack = mc.thePlayer.inventory.armorInventory[index];
        if (stack != null) {
          mc.getRenderItem().func_180450_b(stack, scaledRes.getScaledWidth() / 2 + x, scaledRes.getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(Material.water) ? 65 : 55));
          mc.getRenderItem().func_175030_a(mc.fontRendererObj, stack, scaledRes.getScaledWidth() / 2 + x, scaledRes.getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(Material.water) ? 65 : 55));
          x += 18;
        }
      }
      RenderHelper.disableStandardItemLighting();
      GL11.glPopMatrix();
    }
  }
}
