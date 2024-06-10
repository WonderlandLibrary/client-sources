/*  1:   */ package org.darkstorm.minecraft.gui.theme;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.darkstorm.minecraft.gui.component.Component;
/*  6:   */ 
/*  7:   */ public abstract class AbstractTheme
/*  8:   */   implements Theme
/*  9:   */ {
/* 10:   */   protected final Map<Class<? extends Component>, ComponentUI> uis;
/* 11:   */   
/* 12:   */   public AbstractTheme()
/* 13:   */   {
/* 14:11 */     this.uis = new HashMap();
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void installUI(AbstractComponentUI<?> ui)
/* 18:   */   {
/* 19:15 */     this.uis.put(ui.handledComponentClass, ui);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ComponentUI getUIForComponent(Component component)
/* 23:   */   {
/* 24:19 */     if ((component == null) || (!(component instanceof Component))) {
/* 25:20 */       throw new IllegalArgumentException();
/* 26:   */     }
/* 27:21 */     return getComponentUIForClass(component.getClass());
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ComponentUI getComponentUIForClass(Class<? extends Component> componentClass)
/* 31:   */   {
/* 32:27 */     for (Class<?> componentInterface : componentClass.getInterfaces())
/* 33:   */     {
/* 34:28 */       ComponentUI ui = (ComponentUI)this.uis.get(componentInterface);
/* 35:29 */       if (ui != null) {
/* 36:30 */         return ui;
/* 37:   */       }
/* 38:   */     }
/* 39:32 */     if (componentClass.getSuperclass().equals(Component.class)) {
/* 40:33 */       return (ComponentUI)this.uis.get(componentClass);
/* 41:   */     }
/* 42:35 */     if (!Component.class.isAssignableFrom(componentClass.getSuperclass())) {
/* 43:36 */       return null;
/* 44:   */     }
/* 45:37 */     return getComponentUIForClass(componentClass
/* 46:38 */       .getSuperclass());
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.theme.AbstractTheme
 * JD-Core Version:    0.7.0.1
 */