package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList$EntityEggInfo;

class GuiStats$StatsMobsList extends GuiSlot {
   private final List field_148222_l;
   final GuiStats this$0;

   public GuiStats$StatsMobsList(GuiStats var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 32, var1.height - 64, GuiStats.access$1600(var1).getHeight() * 4);
      this.this$0 = var1;
      this.field_148222_l = Lists.newArrayList();
      this.setShowSelectionBox(false);

      for(EntityList$EntityEggInfo var4 : EntityList.entityEggs.values()) {
         if(GuiStats.access$100(var1).readStat(var4.field_151512_d) > 0 || GuiStats.access$100(var1).readStat(var4.field_151513_e) > 0) {
            this.field_148222_l.add(var4);
         }
      }

   }

   protected int getSize() {
      return this.field_148222_l.size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
   }

   protected boolean isSelected(int var1) {
      return false;
   }

   protected int getContentHeight() {
      return this.getSize() * GuiStats.access$1700(this.this$0).getHeight() * 4;
   }

   protected void drawBackground() {
      this.this$0.drawDefaultBackground();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      EntityList$EntityEggInfo var7 = (EntityList$EntityEggInfo)this.field_148222_l.get(var1);
      String var8 = I18n.format("entity." + EntityList.getStringFromID(var7.spawnedID) + ".name", new Object[0]);
      int var9 = GuiStats.access$100(this.this$0).readStat(var7.field_151512_d);
      int var10 = GuiStats.access$100(this.this$0).readStat(var7.field_151513_e);
      String var11 = I18n.format("stat.entityKills", new Object[]{Integer.valueOf(var9), var8});
      String var12 = I18n.format("stat.entityKilledBy", new Object[]{var8, Integer.valueOf(var10)});
      var11 = I18n.format("stat.entityKills.none", new Object[]{var8});
      var12 = I18n.format("stat.entityKilledBy.none", new Object[]{var8});
      this.this$0.drawString(GuiStats.access$1800(this.this$0), var8, var2 + 2 - 10, var3 + 1, 16777215);
      this.this$0.drawString(GuiStats.access$1900(this.this$0), var11, var2 + 2, var3 + 1 + GuiStats.access$2000(this.this$0).getHeight(), 6316128);
      this.this$0.drawString(GuiStats.access$2100(this.this$0), var12, var2 + 2, var3 + 1 + GuiStats.access$2200(this.this$0).getHeight() * 2, 6316128);
   }
}
