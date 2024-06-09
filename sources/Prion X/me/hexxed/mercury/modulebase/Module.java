package me.hexxed.mercury.modulebase;

import me.hexxed.mercury.Mercury;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;


public class Module
{
  protected Minecraft mc = Mercury.getMinecraft();
  private String modulename;
  private int keybind;
  private boolean nocheatsafe;
  private boolean isEnabled;
  private boolean outboundpacketcancelled;
  private boolean inboundpacketcancelled;
  public static String outboundmessage;
  private ModuleCategory category;
  public static String inboundmessage;
  public boolean blockbreakcancelled;
  public boolean visible;
  public boolean inboundmessagecancelled;
  private String displayname;
  
  public Module(String modulename, int keybind, boolean nocheatsafe, ModuleCategory category) {
    this.modulename = modulename;
    displayname = modulename;
    this.keybind = keybind;
    this.nocheatsafe = true;
    this.category = category;
    visible = true;
  }
  
  public Module(String modulename, int keybind, boolean nocheatsafe, ModuleCategory category, boolean visible) {
    this(modulename, keybind, nocheatsafe, category);
    this.visible = visible;
  }
  
  public void onTick() {}
  
  public void onPreUpdate() {}
  
  public void onPostUpdate() {}
  
  public void onPreMotionUpdate() {}
  
  public void onPreEntityMotionUpdate() {}
  
  public void onPostMotionUpdate() {}
  
  public void onEnable() {}
  
  public void onDisable() {}
  
  public void onToggle() {}
  
  public void onRender() {}
  
  public void onAttackEntity(EntityPlayer var1EntityPlayer, Entity var2Entity) {}
  
  public void onClickBlock(BlockPos pos, EnumFacing facing) {}
  
  public void onPacketSend(Packet packet) {}
  
  public void onPacketRecieving(Packet packet) {}
  
  public void onChatSend() {}
  
  public void onChatRecieving(String message) {}
  
  public void onDamage() {}
  
  public void onStep() {}
  
  public void onBoundingBox(Block block, BlockPos pos) {}
  
  public String getModuleName() {
    return modulename;
  }
  
  public String getModuleDisplayName() {
    return displayname;
  }
  
  public void setModuleDisplayName(String name) {
    displayname = name;
  }
  
  public int getKeybind() {
    return keybind;
  }
  
  public boolean isEnabled() {
    return isEnabled;
  }
  
  public void setKeybind(int keybind) {
    this.keybind = keybind;
  }
  
  public final void setState(boolean flag) {
    isEnabled = flag;
    if (isEnabled())
    {
      mc.thePlayer.playSound("random.wood_click", 1.0F, 1.0F);
      onEnable();
    }
    else {
      mc.thePlayer.playSound("random.wood_click", 1.0F, 1.0F);
      onDisable();
    }
  }
  
  public final void setStateSilent(boolean flag) {
    isEnabled = flag;
    if (isEnabled()) {
      onEnable();
    } else {
      onDisable();
    }
  }
  
  public final void toggle() {
    setState(!isEnabled);
    onToggle();
  }
  
  public boolean isNoCheatSafe() {
    return nocheatsafe;
  }
  
  public boolean isOutboundPacketCancelled() {
    return outboundpacketcancelled;
  }
  
  public void setOutboundPacketCancelled(boolean state) {
    outboundpacketcancelled = state;
  }
  
  public boolean isInboundPacketCancelled() {
    return inboundpacketcancelled;
  }
  
  public void setInboundPacketCancelled(boolean state) {
    inboundpacketcancelled = state;
  }
  
  public void setBlockBreakCancelled(boolean state) {
    blockbreakcancelled = state;
  }
  
  public ModuleCategory getCategory() {
    return category;
  }
  
  public boolean isCategory(ModuleCategory category) {
    return this.category == category;
  }
  
  public String getOutboundMessage() {
    return outboundmessage;
  }
  
  public String getInboundMessage() {
    return inboundmessage;
  }
  
  public void setInboundMessageCancelled(boolean state) {
    inboundmessagecancelled = state;
  }
  
  public boolean isInboundMessageCancelled() {
    return inboundmessagecancelled;
  }
}
