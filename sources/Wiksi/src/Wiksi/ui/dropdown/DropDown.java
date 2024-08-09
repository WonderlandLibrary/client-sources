package src.Wiksi.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.functions.api.Category;
import src.Wiksi.utils.client.ClientUtil;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.client.Vec2i;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.Cursors;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.Scissor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public class DropDown extends Screen implements IMinecraft {
   public SearchField searchField;
   public ThemeEditor themeEditor;
   private final PanelStyle panelstul;
   private final List<Panel> panels = new ArrayList();
   private static Animation animation = new Animation();
   public static float scale = 1.0F;

   public DropDown(ITextComponent titleIn) {
      super(titleIn);
      Category[] var2 = Category.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Category category = var2[var4];
         if (category != Category.Theme) {
            this.panels.add(new Panel(category));
         }
      }

      this.panelstul = new PanelStyle(Category.Theme);
   }

   public boolean isPauseScreen() {
      return false;
   }

   protected void init() {
      int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
      int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
      float x = (float)windowWidth / 2.0F - (float)(this.panels.size() * 145) / 2.0F + 290.0F + 27.5F;
      float y = (float)windowHeight / 2.0F + 162.5F + 30.0F;
      this.themeEditor = new ThemeEditor(windowWidth - 80, windowHeight - 80, 60, 60);
      this.searchField = new SearchField((int)x, (int)y, 120, 16, "Поиск");
      animation = animation.animate(1.0D, 0.25D, Easings.EXPO_OUT);
      super.init();
   }

   public void closeScreen() {
      super.closeScreen();
      GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      Vec2i fixMouse = this.adjustMouseCoordinates((int)mouseX, (int)mouseY);
      Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
      mouseX = (double)fix.getX();
      mouseY = (double)fix.getY();
      Iterator var9 = this.panels.iterator();

      while(var9.hasNext()) {
         Panel panel = (Panel)var9.next();
         if (MathUtil.isHovered((float)mouseX, (float)mouseY, panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight())) {
            panel.setScroll((float)((double)panel.getScroll() + delta * 20.0D));
         }
      }

      if (this.themeEditor.toRender && MathUtil.isHovered((float)mouseX, (float)mouseY, this.panelstul.getX(), this.panelstul.getY(), this.panelstul.getWidth(), this.panelstul.getHeight())) {
         this.panelstul.setScroll((float)((double)this.panelstul.getScroll() + delta * 20.0D));
      }

      return super.mouseScrolled(mouseX, mouseY, delta);
   }

   public boolean charTyped(char codePoint, int modifiers) {
      if (this.searchField.charTyped(codePoint, modifiers)) {
         return true;
      } else {
         Iterator var3 = this.panels.iterator();

         while(var3.hasNext()) {
            Panel panel = (Panel)var3.next();
            panel.charTyped(codePoint, modifiers);
         }

         if (this.themeEditor.toRender) {
            this.panelstul.charTyped(codePoint, modifiers);
         }

         return super.charTyped(codePoint, modifiers);
      }
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      KawaseBlur.blur.updateBlur(2.0F, 3);
      KawaseBlur.blur.BLURRED.draw();
      mc.gameRenderer.setupOverlayRendering(2);
      animation.update();
      if (animation.getValue() < 0.1D) {
         this.closeScreen();
      }

      float off = 10.0F;
      float width = (float)this.panels.size() * 115.0F;
      this.updateScaleBasedOnScreenWidth();
      int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
      int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
      Vec2i fixMouse = this.adjustMouseCoordinates(mouseX, mouseY);
      Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
      mouseX = fix.getX();
      mouseY = fix.getY();
      GlStateManager.pushMatrix();
      GlStateManager.translatef((float)windowWidth / 2.0F, (float)windowHeight / 2.0F, 0.0F);
      GlStateManager.scaled(animation.getValue(), animation.getValue(), 1.0D);
      GlStateManager.scaled((double)scale, (double)scale, 1.0D);
      GlStateManager.translatef((float)(-windowWidth) / 2.0F, (float)(-windowHeight) / 2.0F, 0.0F);
      Iterator var11 = this.panels.iterator();

      while(var11.hasNext()) {
         Panel panel = (Panel)var11.next();
         panel.setY((float)windowHeight / 2.0F - 110.0F);
         panel.setX((float)windowWidth / 2.0F - width / 2.0F + (float)panel.getCategory().ordinal() * 115.0F + 5.0F);
         float animationValue = (float)animation.getValue() * scale;
         float halfAnimationValueRest = (1.0F - animationValue) / 2.0F;
         float testX = panel.getX() + panel.getWidth() * halfAnimationValueRest;
         float testY = panel.getY() + panel.getHeight() * halfAnimationValueRest;
         float testW = panel.getWidth() * animationValue;
         float testH = panel.getHeight() * animationValue;
         testX = testX * animationValue + ((float)windowWidth - testW) * halfAnimationValueRest;
         Scissor.push();
         Scissor.setFromComponentCoordinates((double)testX, (double)testY, (double)testW, (double)(testH - 0.5F));
         panel.render(matrixStack, (float)mouseX, (float)mouseY);
         Scissor.unset();
         Scissor.pop();
      }

      if (this.themeEditor.toRender) {
         animation.update();
         this.panelstul.setY((float)windowHeight / 2.0F - 110.0F + 90.0F);
         this.panelstul.setX((float)windowWidth / 2.0F - width / 2.0F + (float)this.panelstul.getCategory().ordinal() * 115.0F + 5.0F + 80.0F);
         this.panelstul.render(matrixStack, (float)mouseX, (float)mouseY);
      }

      this.themeEditor.render(matrixStack, mouseX, mouseY, partialTicks);
      this.searchField.render(matrixStack, mouseX, mouseY, partialTicks);
      GlStateManager.popMatrix();
      mc.gameRenderer.setupOverlayRendering();
   }

   private void updateScaleBasedOnScreenWidth() {
      float PANEL_WIDTH = 105.0F;
      float MARGIN = 10.0F;
      float MIN_SCALE = 1.0F;
      float totalPanelWidth = (float)this.panels.size() * 115.0F;
      float screenWidth = (float)mc.getMainWindow().getScaledWidth();
      if (totalPanelWidth >= screenWidth) {
         scale = screenWidth / totalPanelWidth;
         scale = MathHelper.clamp(scale, 1.0F, 1.0F);
      } else {
         scale = 1.0F;
      }

   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         Iterator var4 = this.panels.iterator();

         while(var4.hasNext()) {
            Panel panel = (Panel)var4.next();
            panel.keyPressed(keyCode, scanCode, modifiers);
         }

         if (this.themeEditor.toRender) {
            this.panelstul.keyPressed(keyCode, scanCode, modifiers);
         }

         if (keyCode == 256) {
            animation = animation.animate(0.0D, 0.25D, Easings.EXPO_OUT);
            return false;
         } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
         }
      }
   }

   private Vec2i adjustMouseCoordinates(int mouseX, int mouseY) {
      int windowWidth = mc.getMainWindow().getScaledWidth();
      int windowHeight = mc.getMainWindow().getScaledHeight();
      float adjustedMouseX = ((float)mouseX - (float)windowWidth / 2.0F) / scale + (float)windowWidth / 2.0F;
      float adjustedMouseY = ((float)mouseY - (float)windowHeight / 2.0F) / scale + (float)windowHeight / 2.0F;
      return new Vec2i((int)adjustedMouseX, (int)adjustedMouseY);
   }

   private double pathX(float mouseX, float scale) {
      if (scale == 1.0F) {
         return (double)mouseX;
      } else {
         int windowWidth = mc.getMainWindow().scaledWidth();
         int windowHeight = mc.getMainWindow().scaledHeight();
         mouseX /= scale;
         mouseX -= (float)windowWidth / 2.0F - (float)windowWidth / 2.0F * scale;
         return (double)mouseX;
      }
   }

   private double pathY(float mouseY, float scale) {
      if (scale == 1.0F) {
         return (double)mouseY;
      } else {
         int windowWidth = mc.getMainWindow().scaledWidth();
         int windowHeight = mc.getMainWindow().scaledHeight();
         mouseY /= scale;
         mouseY -= (float)windowHeight / 2.0F - (float)windowHeight / 2.0F * scale;
         return (double)mouseY;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else if (this.themeEditor.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else {
         Vec2i fixMouse = this.adjustMouseCoordinates((int)mouseX, (int)mouseY);
         Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
         mouseX = (double)fix.getX();
         mouseY = (double)fix.getY();
         Iterator var8 = this.panels.iterator();

         while(var8.hasNext()) {
            Panel panel = (Panel)var8.next();
            panel.mouseClick((float)mouseX, (float)mouseY, button);
         }

         this.panelstul.mouseClick((float)mouseX, (float)mouseY, button);
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      Vec2i fixMouse = this.adjustMouseCoordinates((int)mouseX, (int)mouseY);
      Vec2i fix = ClientUtil.getMouse(fixMouse.getX(), fixMouse.getY());
      mouseX = (double)fix.getX();
      mouseY = (double)fix.getY();
      Iterator var8 = this.panels.iterator();

      while(var8.hasNext()) {
         Panel panel = (Panel)var8.next();
         panel.mouseRelease((float)mouseX, (float)mouseY, button);
      }

      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean isSearching() {
      return !this.searchField.isEmpty();
   }

   public String getSearchText() {
      return this.searchField.getText();
   }

   public boolean searchCheck(String text) {
      return this.isSearching() && !text.replaceAll(" ", "").toLowerCase().contains(this.getSearchText().replaceAll(" ", "").toLowerCase());
   }

   public boolean isRendered() {
      return this.themeEditor.toRender;
   }

   public static Animation getAnimation() {
      return animation;
   }
}
