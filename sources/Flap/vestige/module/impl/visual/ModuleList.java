package vestige.module.impl.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PostMotionEvent;
import vestige.font.VestigeFontRenderer;
import vestige.module.AlignType;
import vestige.module.Category;
import vestige.module.EventListenType;
import vestige.module.HUDModule;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.EnumModeSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.animation.AnimationHolder;
import vestige.util.animation.AnimationType;
import vestige.util.animation.AnimationUtil;
import vestige.util.render.DrawUtil;
import vestige.util.render.FontUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.ModuleListUtil;

public class ModuleList extends HUDModule {
   private boolean initialised;
   private final ArrayList<AnimationHolder<Module>> modules = new ArrayList();
   private final ModeSetting mode = new ModeSetting("Mode", "New", new String[]{"Simple", "New"});
   private final ModeSetting background = new ModeSetting("Show Background", "True", new String[]{"True", "False"});
   private final ModeSetting font = FontUtil.getFontSetting(() -> {
      return this.mode.is("Custom");
   });
   private final EnumModeSetting<AnimationType> animType;
   private final IntegerSetting animDuration;
   private final DoubleSetting verticalSpacing;
   private final DoubleSetting extraWidth;
   private final BooleanSetting box;
   private final IntegerSetting boxAlpha;
   private final BooleanSetting leftOutline;
   private final BooleanSetting rightOutline;
   private final BooleanSetting topOutline;
   private final BooleanSetting bottomOutline;
   private final BooleanSetting lowcase;
   private final BooleanSetting shadow;
   private final BooleanSetting importantonly;
   private final EnumModeSetting<AlignType> alignMode;
   private VestigeFontRenderer productSans;
   private ClientTheme theme;

   public ModuleList() {
      super("Module List", Category.VISUAL, 5.0D, 5.0D, 100, 200, AlignType.RIGHT);
      this.animType = AnimationUtil.getAnimationType(AnimationType.SLIDE);
      this.animDuration = AnimationUtil.getAnimationDuration(() -> {
         return this.mode.is("Custom");
      }, 250);
      this.verticalSpacing = new DoubleSetting("Vertical spacing", () -> {
         return this.mode.is("Custom");
      }, 10.5D, 8.0D, 20.0D, 0.5D);
      this.extraWidth = new DoubleSetting("Extra width", () -> {
         return this.mode.is("Custom");
      }, 0.5D, 0.0D, 6.0D, 0.5D);
      this.box = new BooleanSetting("Box", () -> {
         return this.mode.is("Custom");
      }, false);
      this.boxAlpha = new IntegerSetting("Box alpha", () -> {
         return this.mode.is("Custom") && this.box.isEnabled();
      }, 100, 5, 255, 5);
      this.leftOutline = new BooleanSetting("Left outline", () -> {
         return this.mode.is("Custom");
      }, false);
      this.rightOutline = new BooleanSetting("Right outline", () -> {
         return this.mode.is("Custom");
      }, false);
      this.topOutline = new BooleanSetting("Top outline", () -> {
         return this.mode.is("Custom");
      }, false);
      this.bottomOutline = new BooleanSetting("Bottom outline", () -> {
         return this.mode.is("Custom");
      }, false);
      this.lowcase = new BooleanSetting("To lowcase", false);
      this.shadow = new BooleanSetting("Shadow", true);
      this.importantonly = new BooleanSetting("Important Only", true);
      this.alignMode = new EnumModeSetting("Align type", AlignType.RIGHT, AlignType.values());
      this.addSettings(new AbstractSetting[]{this.mode, this.background, this.font, this.animType, this.animDuration, this.verticalSpacing, this.box, this.extraWidth, this.boxAlpha, this.leftOutline, this.rightOutline, this.topOutline, this.bottomOutline, this.alignMode, this.lowcase, this.shadow, this.importantonly});
      this.listenType = EventListenType.MANUAL;
      this.startListening();
      this.setEnabledSilently(true);
   }

   public void onClientStarted() {
      Flap.instance.getModuleManager().modules.forEach((m) -> {
         this.modules.add(new AnimationHolder(m));
      });
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
   }

   protected void renderModule(boolean inChat) {
      if (!this.initialised) {
         this.sort();
         this.initialised = true;
      }

      this.alignType = (AlignType)this.alignMode.getMode();
      if (!mc.gameSettings.showDebugInfo) {
         String var2 = this.mode.getMode();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1818419758:
            if (var2.equals("Simple")) {
               var3 = 0;
            }
            break;
         case 78208:
            if (var2.equals("New")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
            this.renderSimple();
            break;
         case 1:
            this.renderNew();
         }

      }
   }

   @Listener
   public void onPostMotion(PostMotionEvent event) {
      this.sort();
   }

   private void sort() {
      Collections.reverse(this.modules);
      this.modules.sort((m1, m2) -> {
         return (int)Math.round(this.getStringWidth(((Module)m1.get()).getName()) * 8.0D - (double)Math.round(this.getStringWidth(((Module)m2.get()).getName()) * 8.0D));
      });
      Collections.reverse(this.modules);
   }

   private void renderSimple() {
      ScaledResolution sr = new ScaledResolution(mc);
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      float offsetY = 12.0F;
      float width = 0.0F;
      this.modules.sort((holder1, holder2) -> {
         Module m1 = (Module)holder1.get();
         Module m2 = (Module)holder2.get();
         double length1 = this.getStringWidth(m1.getName()) + this.getStringWidth(m1.getInfo());
         double length2 = this.getStringWidth(m2.getName()) + this.getStringWidth(m2.getInfo());
         return Double.compare(length2, length1);
      });
      Iterator var6 = this.modules.iterator();

      while(true) {
         AnimationHolder holder;
         Module m;
         String name;
         String cat;
         float mult;
         float startX;
         float endX;
         float endY;
         do {
            do {
               if (!var6.hasNext()) {
                  this.width = (int)width + 1;
                  this.height = (int)((double)y - this.posY.getValue()) + 1;
                  return;
               }

               holder = (AnimationHolder)var6.next();
               m = (Module)holder.get();
               holder.setAnimType((AnimationType)this.animType.getMode());
               holder.setAnimDuration(200L);
               holder.updateState(m.isEnabled());
            } while(holder.isAnimDone() && !holder.isRendered());

            name = m.getName();
            cat = m.getInfo();
            double catLength = this.getStringWidth(cat);
            double nameLength = this.getStringWidth(name) + catLength;
            mult = holder.getYMult();
            startX = this.alignMode.getMode() == AlignType.LEFT ? x - 5.0F : (float)((double)sr.getScaledWidth() - nameLength - (double)x - 5.0D);
            endX = this.alignMode.getMode() == AlignType.LEFT ? (float)((double)x + nameLength) : (float)sr.getScaledWidth() - x;
            endY = y + offsetY;
            if (Math.abs(endX - startX) > width) {
               width = Math.abs(endX - startX);
            }
         } while(this.importantonly.isEnabled() && ModuleListUtil.getModulesInutiol().contains(name));

         float finalY = y;
         float finalEndY = endY;
         float finalStartX = startX;
         float finalEndX = endX;
         String finalName = name;
         String finalCat = cat;
         Module finalM = m;
         holder.render(() -> {
            Gui.drawRect(0.0D, (double) finalY, 0.0D, (double) finalEndY, (new Color(0, 0, 0, 0)).getRGB());
            if (!finalM.getInfo().isEmpty()) {
               if (this.background.getMode().equals("True")) {
                  Gui.drawRect((double)(finalStartX - 1.0F), (double) finalY, (double) finalEndX, (double) finalEndY, (new Color(0, 0, 0, 45)).getRGB());
                  Gui.drawRect((double) finalEndX, (double) finalY, (double)(finalEndX + 1.0F), (double) finalEndY, this.getColor((int)(finalY * -35.0F)));
               }

               if (this.shadow.isEnabled()) {
                  RenderUtils2.drawBloomShadow(finalStartX, finalY, finalEndX, finalEndY, 10, 6, (new Color(0, 0, 0, 50)).getRGB(), false);
               }

               if (this.lowcase.isEnabled()) {
                  this.drawStringWithShadow(" " + finalName.toLowerCase(), finalStartX - 2.0F, finalY + 2.0F, this.getColor((int)(finalY * -17.0F)));
                  this.drawStringWithShadow(" " + finalCat.toLowerCase(), (float)((double) finalStartX + this.getStringWidth(finalName.toLowerCase())), finalY + 2.0F, Color.lightGray.getRGB());
               } else {
                  this.drawStringWithShadow(" " + finalName, finalStartX - 2.0F, finalY + 2.0F, this.getColor((int)(finalY * -17.0F)));
                  this.drawStringWithShadow(" " + finalCat, (float)((double) finalStartX + this.getStringWidth(finalName)), finalY + 2.0F, Color.lightGray.getRGB());
               }
            } else {
               if (this.background.getMode().equals("True")) {
                  Gui.drawRect((double)(finalStartX + 0.5F), (double) finalY, (double) finalEndX, (double) finalEndY, (new Color(0, 0, 0, 45)).getRGB());
                  Gui.drawRect((double) finalEndX, (double) finalY, (double)(finalEndX + 1.0F), (double) finalEndY, this.getColor((int)(finalY * -35.0F)));
               }

               if (this.shadow.isEnabled()) {
                  RenderUtils2.drawBloomShadow(finalStartX, finalY, finalEndX, finalEndY, 10, 6, (new Color(0, 0, 0, 50)).getRGB(), false);
               }

               if (this.lowcase.isEnabled()) {
                  this.drawStringWithShadow(finalName.toLowerCase(), finalStartX + 2.5F, finalY + 2.0F, this.getColor((int)(finalY * -35.0F)));
               } else {
                  this.drawStringWithShadow(finalName, finalStartX + 2.5F, finalY + 2.0F, this.getColor((int)(finalY * -35.0F)));
               }
            }

         }, startX, y, endX, y + offsetY * mult);
         y += offsetY * Math.min(mult * 4.0F, 1.0F);
      }
   }

   private void renderNew() {
      ScaledResolution sr = new ScaledResolution(mc);
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      float offsetY = 12.05F;
      float width = 0.0F;
      this.modules.sort((a, b) -> {
         Module m1 = (Module)a.get();
         Module m2 = (Module)b.get();
         double length1 = this.getStringWidth(m1.getName()) + this.getStringWidth(m1.getInfo());
         double length2 = this.getStringWidth(m2.getName()) + this.getStringWidth(m2.getInfo());
         return Double.compare(length2, length1);
      });
      Iterator var6 = this.modules.iterator();

      while(true) {
         AnimationHolder holder;
         Module m;
         String name;
         String cat;
         float mult;
         float startX;
         float endX;
         float endY;
         do {
            do {
               if (!var6.hasNext()) {
                  this.width = (int)width + 1;
                  this.height = (int)((double)y - this.posY.getValue()) + 1;
                  return;
               }

               holder = (AnimationHolder)var6.next();
               m = (Module)holder.get();
               holder.setAnimType((AnimationType)this.animType.getMode());
               holder.setAnimDuration(200L);
               holder.updateState(m.isEnabled());
            } while(holder.isAnimDone() && !holder.isRendered());

            name = m.getName();
            cat = m.getInfo();
            double catLength = this.getStringWidth(cat);
            double nameLength = this.getStringWidth(name) + catLength;
            mult = holder.getYMult();
            startX = this.alignMode.getMode() == AlignType.LEFT ? x - 5.0F : (float)((double)sr.getScaledWidth() - nameLength - (double)x - 5.0D);
            endX = this.alignMode.getMode() == AlignType.LEFT ? (float)((double)x + nameLength) : (float)sr.getScaledWidth() - x;
            endY = y + offsetY;
            if (Math.abs(endX - startX) > width) {
               width = Math.abs(endX - startX);
            }
         } while(this.importantonly.isEnabled() && ModuleListUtil.getModulesInutiol().contains(name));

         float finalEndY = endY;
         float finalY = y;
         Module finalM = m;
         float finalStartX = startX;
         float finalEndX = endX;
         String finalName = name;
         String finalCat = cat;
         holder.render(() -> {
            Gui.drawRect(0.0D, (double) finalY, 0.0D, (double) finalEndY, (new Color(0, 0, 0, 0)).getRGB());
            if (!finalM.getInfo().isEmpty()) {
               if (this.background.getMode().equals("True")) {
                  Gui.drawRect((double) finalStartX - 0.7D, (double) finalY, (double) finalEndX, (double) finalEndY, (new Color(0, 0, 0, 50)).getRGB());
                  if (this.shadow.isEnabled()) {
                     RenderUtils2.drawBloomShadow(finalStartX, finalY, finalEndX, finalEndY, 10, 6, (new Color(0, 0, 0, 50)).getRGB(), false);
                  }

                  Gui.drawRect((double) finalEndX, (double) finalY, (double)(finalEndX + 1.0F), (double) finalEndY, this.getColor((int)(finalY * -35.0F)));
               } else if (this.shadow.isEnabled()) {
                  RenderUtils2.drawBloomShadow(finalStartX, finalY, finalEndX, finalEndY, 10, 6, (new Color(0, 0, 0, 50)).getRGB(), false);
               }

               if (this.lowcase.isEnabled()) {
                  this.drawStringWithShadow(" " + finalName.toLowerCase(), (float)((double) finalStartX - 1.7D), finalY + 1.5F, this.getColor((int)(finalY * -35.0F)));
                  this.drawStringWithShadow(" " + finalCat.toLowerCase(), (float)((double) finalStartX + this.getStringWidth(finalName.toLowerCase())), finalY + 1.5F, Color.lightGray.getRGB());
               } else {
                  this.drawStringWithShadow(" " + finalName, (float)((double) finalStartX - 1.7D), finalY + 1.5F, this.getColor((int)(finalY * -35.0F)));
                  this.drawStringWithShadow(" " + finalCat, (float)((double) finalStartX + this.getStringWidth(finalName)), finalY + 1.5F, Color.lightGray.getRGB());
               }
            } else {
               if (this.background.getMode().equals("True")) {
                  Gui.drawRect((double)(finalStartX + 1.0F), (double) finalY, (double) finalEndX, (double) finalEndY, (new Color(0, 0, 0, 50)).getRGB());
                  Gui.drawRect((double) finalEndX, (double) finalY, (double)(finalEndX + 1.0F), (double) finalEndY, this.getColor((int)(finalY * -35.0F)));
                  if (this.shadow.isEnabled()) {
                     RenderUtils2.drawBloomShadow(finalStartX, finalY, finalEndX, finalEndY, 10, 6, (new Color(0, 0, 0, 50)).getRGB(), false);
                  }
               } else if (this.shadow.isEnabled()) {
                  RenderUtils2.drawBloomShadow(finalStartX, finalY, finalEndX, finalEndY, 10, 6, (new Color(0, 0, 0, 50)).getRGB(), false);
               }

               if (this.lowcase.isEnabled()) {
                  this.drawStringWithShadow(finalName.toLowerCase(), finalStartX + 2.5F, finalY + 1.5F, this.getColor((int)(finalY * -35.0F)));
               } else {
                  this.drawStringWithShadow(finalName, finalStartX + 2.5F, finalY + 1.5F, this.getColor((int)(finalY * -35.0F)));
               }
            }

         }, startX, y, endX, y + offsetY * mult);
         y += offsetY * Math.min(mult * 4.0F, 1.0F);
      }
   }

   private void renderOutline() {
      ScaledResolution sr = new ScaledResolution(mc);
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      float offsetY = 11.0F;
      float lastStartX = 0.0F;
      float lastEndX = 0.0F;
      boolean firstModule = true;
      float width = 0.0F;
      this.modules.sort((a, b) -> {
         Module m1 = (Module)a.get();
         Module m2 = (Module)b.get();
         double length1 = this.getStringWidth(m1.getName());
         double length2 = this.getStringWidth(m2.getName());
         return Double.compare(length2, length1);
      });
      Iterator var9 = this.modules.iterator();

      while(true) {
         AnimationHolder holder;
         String name;
         do {
            if (!var9.hasNext()) {
               DrawUtil.drawGradientVerticalRect((double)lastStartX - 1.5D, (double)y, (double)lastEndX + 1.5D, (double)(y + 2.0F), 1342177280, 0);
               this.width = (int)width + 1;
               this.height = (int)((double)y - this.posY.getValue()) + 1;
               return;
            }

            holder = (AnimationHolder)var9.next();
            Module m = (Module)holder.get();
            name = m.getName();
            holder.setAnimType(AnimationType.POP2);
            holder.setAnimDuration(200L);
            holder.updateState(m.isEnabled());
         } while(holder.isAnimDone() && !holder.isRendered());

         float startX = this.alignMode.getMode() == AlignType.LEFT ? x : (float)((double)sr.getScaledWidth() - this.getStringWidth(name) - (double)x);
         float endX = this.alignMode.getMode() == AlignType.LEFT ? (float)((double)x + this.getStringWidth(name)) : (float)sr.getScaledWidth() - x;
         float endY = y + offsetY;
         if (Math.abs(endX - startX) > width) {
            width = Math.abs(endX - startX);
         }

         if (firstModule) {
            DrawUtil.drawGradientVerticalRect((double)startX - 1.5D, (double)(y - 2.0F), (double)endX + 1.5D, (double)y, 0, 1342177280);
         } else {
            double diff = (double)startX - 3.5D - ((double)lastStartX - 1.5D);
            if (diff > 1.0D) {
               DrawUtil.drawGradientVerticalRect((double)lastStartX - 1.5D, (double)y, (double)(startX - 3.0F), (double)(y + 2.0F), 1342177280, 0);
            }
         }

         DrawUtil.drawGradientSideRect((double)endX + 1.5D, (double)y, (double)endX + 3.5D, (double)(y + offsetY * holder.getYMult()), 1342177280, 0);
         String finalName = name;
         float finalY = y;
         holder.render(() -> {
            DrawUtil.drawGradientSideRect((double)startX - 3.5D, (double) finalY, (double)startX - 1.5D, (double)endY, 0, 1342177280);
            this.drawStringWithShadow(finalName, startX, finalY + 2.0F, this.getColor((int)(finalY * -17.0F)));
         }, startX - 3.5F, y, endX, endY);
         y += offsetY * holder.getYMult();
         lastStartX = startX;
         lastEndX = endX;
         firstModule = false;
      }
   }

   private void renderCustom() {
      ScaledResolution sr = new ScaledResolution(mc);
      float x = (float)this.posX.getValue();
      float y = (float)this.posY.getValue();
      float offsetY = (float)this.verticalSpacing.getValue();
      double extraWidth = this.extraWidth.getValue();
      float width = 0.0F;
      float space = offsetY - (float)this.getFontHeight();
      boolean firstModule = true;
      float lastStartX = 0.0F;
      float lastEndX = 0.0F;
      Iterator var12 = this.modules.iterator();

      while(true) {
         AnimationHolder holder;
         String name;
         float startX;
         float endX;
         float mult;
         do {
            Module m;
            if (!var12.hasNext()) {
               if (this.bottomOutline.isEnabled() && !firstModule) {
                  double left = this.leftOutline.isEnabled() ? (double)lastStartX - extraWidth - 2.0D : (double)lastStartX - extraWidth;
                  double right = this.rightOutline.isEnabled() ? (double)lastEndX + extraWidth + 2.0D : (double)lastEndX + extraWidth;
                  Gui.drawRect(left, (double)y, right, (double)(y + 2.0F), this.getColor((int)(y * -17.0F)));
               }

               y = (float)this.posY.getValue();
               var12 = this.modules.iterator();

               while(true) {
                  do {
                     if (!var12.hasNext()) {
                        this.width = (int)width + 1;
                        this.height = (int)((double)y - this.posY.getValue()) + 1;
                        return;
                     }

                     holder = (AnimationHolder)var12.next();
                     m = (Module)holder.get();
                     name = m.getName();
                  } while(holder.isAnimDone() && !holder.isRendered());

                  startX = this.alignMode.getMode() == AlignType.LEFT ? x : (float)((double)sr.getScaledWidth() - this.getStringWidth(name) - (double)x);
                  endX = this.alignMode.getMode() == AlignType.LEFT ? (float)((double)x + this.getStringWidth(name)) : (float)sr.getScaledWidth() - x;
                  mult = holder.getYMult();
                  float renderY = y + (float)Math.round(space + 1.0F) / 2.0F;
                  String finalName = name;
                  float finalStartX = startX;
                  float finalY = y;
                  holder.render(() -> {
                     this.drawStringWithShadow(finalName, finalStartX, renderY, this.getColor((int)(finalY * -17.0F)));
                  }, startX, y, endX, mult);
                  y += offsetY * mult;
               }
            }

            holder = (AnimationHolder)var12.next();
            m = (Module)holder.get();
            name = m.getName();
            holder.setAnimType((AnimationType)this.animType.getMode());
            holder.setAnimDuration((long)this.animDuration.getValue());
            holder.updateState(m.isEnabled());
         } while(holder.isAnimDone() && !holder.isRendered());

         startX = this.alignMode.getMode() == AlignType.LEFT ? x : (float)((double)sr.getScaledWidth() - this.getStringWidth(name) - (double)x);
         endX = this.alignMode.getMode() == AlignType.LEFT ? (float)((double)x + this.getStringWidth(name)) : (float)sr.getScaledWidth() - x;
         if (Math.abs(endX - startX) > width) {
            width = Math.abs(endX - startX);
         }

         mult = holder.getYMult();
         if (this.leftOutline.isEnabled()) {
            Gui.drawRect((double)(startX - 2.0F) - extraWidth, (double)y, (double)startX - extraWidth, (double)(y + offsetY * mult), this.getColor((int)(y * -17.0F)));
         }

         if (this.rightOutline.isEnabled()) {
            Gui.drawRect((double)endX + extraWidth, (double)y, (double)endX + extraWidth + 2.0D, (double)(y + offsetY * mult), this.getColor((int)(y * -17.0F)));
         }

         double left;
         double right;
         if (firstModule) {
            if (this.topOutline.isEnabled()) {
               left = this.leftOutline.isEnabled() ? (double)startX - extraWidth - 2.0D : (double)startX - extraWidth;
               right = this.rightOutline.isEnabled() ? (double)endX + extraWidth + 2.0D : (double)endX + extraWidth;
               Gui.drawRect(left, (double)(y - 2.0F), right, (double)y, this.getColor((int)(y * -17.0F)));
            }
         } else if (this.bottomOutline.isEnabled()) {
            left = this.leftOutline.isEnabled() ? (double)lastStartX - extraWidth - 2.0D : (double)lastStartX - extraWidth;
            right = this.leftOutline.isEnabled() ? (double)startX - extraWidth - 2.0D : (double)startX - extraWidth;
            Gui.drawRect(left, (double)y, right, (double)(y + 2.0F), this.getColor((int)(y * -17.0F)));
         }

         if (this.box.isEnabled()) {
            Gui.drawRect((double)startX - extraWidth, (double)y, (double)endX + extraWidth, (double)(y + offsetY * mult), (new Color(0, 0, 0, this.boxAlpha.getValue())).getRGB());
         }

         y += offsetY * mult;
         firstModule = false;
         lastStartX = startX;
         lastEndX = endX;
      }
   }

   public void drawString(String text, float x, float y, int color) {
      String var5 = this.mode.getMode();
      byte var6 = -1;
      switch(var5.hashCode()) {
      case -1818419758:
         if (var5.equals("Simple")) {
            var6 = 0;
         }
         break;
      case 78208:
         if (var5.equals("New")) {
            var6 = 1;
         }
         break;
      case 558407714:
         if (var5.equals("Outline")) {
            var6 = 2;
         }
         break;
      case 2029746065:
         if (var5.equals("Custom")) {
            var6 = 3;
         }
      }

      switch(var6) {
      case 0:
         mc.fontRendererObj.drawString(text, x, y, color);
         return;
      case 1:
      case 2:
         this.productSans.drawString(text, x, y, color);
         return;
      case 3:
         FontUtil.drawString(this.font.getMode(), text, x, y, color);
      default:
         mc.fontRendererObj.drawString(text, x, y, color);
      }
   }

   public void drawStringWithShadow(String text, float x, float y, int color) {
      String var5 = this.mode.getMode();
      byte var6 = -1;
      switch(var5.hashCode()) {
      case -1818419758:
         if (var5.equals("Simple")) {
            var6 = 0;
         }
         break;
      case 78208:
         if (var5.equals("New")) {
            var6 = 1;
         }
         break;
      case 558407714:
         if (var5.equals("Outline")) {
            var6 = 2;
         }
         break;
      case 2029746065:
         if (var5.equals("Custom")) {
            var6 = 3;
         }
      }

      switch(var6) {
      case 0:
         mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
         break;
      case 1:
      case 2:
         this.productSans.drawStringWithShadow(text, x, y, color);
         break;
      case 3:
         FontUtil.drawStringWithShadow(this.font.getMode(), text, x, y, color);
      }

   }

   public double getStringWidth(String s) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1818419758:
         if (var2.equals("Simple")) {
            var3 = 0;
         }
         break;
      case 78208:
         if (var2.equals("New")) {
            var3 = 1;
         }
         break;
      case 558407714:
         if (var2.equals("Outline")) {
            var3 = 2;
         }
         break;
      case 2029746065:
         if (var2.equals("Custom")) {
            var3 = 3;
         }
      }

      switch(var3) {
      case 0:
         return (double)mc.fontRendererObj.getStringWidth(s);
      case 1:
      case 2:
         return this.productSans.getStringWidth(s);
      case 3:
         return FontUtil.getStringWidth(this.font.getMode(), s);
      default:
         return (double)mc.fontRendererObj.getStringWidth(s);
      }
   }

   public int getFontHeight() {
      String var1 = this.mode.getMode();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1818419758:
         if (var1.equals("Simple")) {
            var2 = 0;
         }
         break;
      case 78208:
         if (var1.equals("New")) {
            var2 = 1;
         }
         break;
      case 558407714:
         if (var1.equals("Outline")) {
            var2 = 2;
         }
         break;
      case 2029746065:
         if (var1.equals("Custom")) {
            var2 = 3;
         }
      }

      switch(var2) {
      case 0:
         return mc.fontRendererObj.FONT_HEIGHT;
      case 1:
      case 2:
         return this.productSans.getHeight();
      case 3:
         return FontUtil.getFontHeight(this.font.getMode());
      default:
         return mc.fontRendererObj.FONT_HEIGHT;
      }
   }

   public int getColor(int offset) {
      return this.theme.getColor(offset);
   }
}
