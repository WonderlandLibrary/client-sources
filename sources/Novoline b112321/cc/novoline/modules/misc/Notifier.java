package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.SpawnCheckEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.misc.WindowedFullscreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent$Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent$Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public final class Notifier extends AbstractModule {
   @Property("additional_y")
   private IntProperty add_y = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(10)).minimum(Integer.valueOf(10))).maximum(Integer.valueOf(90));

   public Notifier(ModuleManager var1) {
      super(var1, "Notifier", EnumModuleType.MISC, "alert you if player in your chunks");
      Manager.put(new Setting("NF_ADD_Y", "Addition Y", SettingType.SLIDER, this, this.add_y, 5.0D));
   }

   public boolean isServerBot(Entity var1) {
      return var1.getDisplayName().getFormattedText().contains("§8[NPC]");
   }

   @EventTarget
   public void onCheck(SpawnCheckEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getEntity() instanceof EntityPlayer && var1.getEntity().getEntityID() != this.mc.player.getEntityID() && !this.isServerBot(var1.getEntity())) {
         int var3 = (int)var1.getEntity().posX;
         int var4 = (int)var1.getEntity().posY;
         int var5 = (int)var1.getEntity().posZ;
         String var6 = this.mc.isSingleplayer()?"/tp ":".tp ";
         String var7 = var1.getEntity().getName().toLowerCase();
         ChatComponentText var8 = new ChatComponentText("§dNotifier§r§7 » ");
         ChatComponentText var9 = new ChatComponentText("§3Name: §r");
         ChatComponentText var10 = new ChatComponentText(var7);
         var10 = (ChatComponentText)var10.setChatStyle(var10.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent$Action.SHOW_TEXT, new ChatComponentText(var6 + var7))));
         var10 = (ChatComponentText)var10.setChatStyle(var10.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent$Action.RUN_COMMAND, var6 + var7)));
         ChatComponentText var11 = new ChatComponentText(" §3Coords: §r");
         ChatComponentText var12 = new ChatComponentText("§bX:§r " + var3 + " §bY:§r " + var4 + " §bZ:§r " + var5);
         String var13 = var6 + var3 + " " + var4 + " " + var5;
         var12 = (ChatComponentText)var12.setChatStyle(var12.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent$Action.SHOW_TEXT, new ChatComponentText(var13))));
         var12 = (ChatComponentText)var12.setChatStyle(var12.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent$Action.RUN_COMMAND, var13)));
         var8.appendSibling(var9).appendSibling(var10).appendSibling(var11).appendSibling(var12);
         this.mc.ingameGUI.n().a((IChatComponent)var8);
      }

   }
}
