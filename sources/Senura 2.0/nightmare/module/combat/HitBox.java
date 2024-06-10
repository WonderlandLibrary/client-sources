/*    */ package nightmare.module.combat;
/*    */ 
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class HitBox
/*    */   extends Module {
/*    */   public HitBox() {
/* 14 */     super("HitBox", 0, Category.COMBAT);
/*    */     
/* 16 */     Nightmare.instance.settingsManager.rSetting(new Setting("Size", this, 0.1D, 0.1D, 1.0D, false));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 21 */     float size = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Size").getValDouble();
/*    */     
/* 23 */     setDisplayName(getName() + " " + EnumChatFormatting.GRAY + size);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\HitBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */