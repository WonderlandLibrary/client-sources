package exhibition.gui.console.components;

import exhibition.Client;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class SourceConsolePanel extends SourceComponent {
   private List components = new ArrayList();
   private float x = 100.0F;
   private float y = 100.0F;
   private float dragX;
   private float dragY;
   private float lastDragX;
   private float lastDragY;
   private float width = 267.0F;
   private float height = 198.0F;
   private float lastWidth;
   private float lastHeight;
   private boolean dragging;
   private boolean expanding;

   private void prepareScissorBox(float x, float y, float x2, float y2) {
      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      int factor = scale.getScaleFactor();
      GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
   }

   public void mousePressed(float mouseX, float mouseY, int mouseID) {
      float xOffset = this.x + this.dragX;
      float yOffset = this.y + this.dragY;
      if (mouseX >= xOffset && mouseY >= yOffset && mouseX <= xOffset + this.width && mouseY <= yOffset + 12.0F && mouseID == 0) {
         this.dragging = true;
         this.lastDragX = mouseX - this.dragX;
         this.lastDragY = mouseY - this.dragY;
      }

      if (mouseX >= xOffset + this.width - 4.0F - 5.0F && mouseY >= yOffset + 5.0F && mouseX <= xOffset + this.width - 4.0F && mouseY <= yOffset + 10.0F && mouseID == 0) {
         this.dragging = false;
         Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
      }

      if (mouseX >= xOffset + this.width - 5.0F && mouseY >= yOffset + this.height - 5.0F && mouseX <= xOffset + this.width && mouseY <= yOffset + this.height && mouseID == 0) {
         this.expanding = true;
         this.lastWidth = mouseX - this.width;
         this.lastHeight = mouseY - this.height;
      }

      Iterator var6 = this.components.iterator();

      while(var6.hasNext()) {
         SourceComponent component = (SourceComponent)var6.next();
         component.mousePressed(mouseX, mouseY, mouseID);
      }

   }

   public void mouseReleased(float mouseX, float mouseY, int mouseID) {
      if (mouseID == 0 && this.dragging || this.expanding) {
         this.dragging = false;
         this.expanding = false;
      }

      Iterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         SourceComponent component = (SourceComponent)var4.next();
         component.mouseReleased(mouseX, mouseY, mouseID);
      }

   }

   public void drawScreen(float mouseX, float mouseY) {
      float xOffset = this.x + this.dragX;
      float yOffset = this.y + this.dragY;
      RenderingUtil.drawRoundedRect(xOffset, yOffset, xOffset + this.width, yOffset + this.height, Colors.getColor(162), Colors.getColor(162));
      RenderingUtil.rectangle((double)(xOffset + 4.0F), (double)(yOffset + 13.0F), (double)(xOffset + this.width - 4.0F), (double)(yOffset + this.height - 3.0F), Colors.getColor(62));
      if (!Client.getSourceConsoleGUI().sourceConsole.getStringList().isEmpty()) {
         GlStateManager.pushMatrix();
         this.prepareScissorBox(xOffset + 8.0F, yOffset + 20.0F, xOffset + this.width - 4.0F - 11.5F, yOffset + this.height - 19.5F);
         GL11.glEnable(3089);
         float yMEME = 0.0F;
         float maximum = (this.height - 20.0F) / 6.0F - 3.5F;
         if (Client.getSourceConsoleGUI().sourceConsole.getStringList().size() > (int)maximum) {
            yMEME = ((float)Client.getSourceConsoleGUI().sourceConsole.getStringList().size() - maximum) * -6.0F;
         }

         for(Iterator var7 = Client.getSourceConsoleGUI().sourceConsole.getStringList().iterator(); var7.hasNext(); yMEME += 6.0F) {
            String str = (String)var7.next();
            Client.verdana10.drawString(str, xOffset + 9.0F, yOffset + 23.0F + yMEME, -1);
         }

         GL11.glDisable(3089);
         GlStateManager.popMatrix();
      }

      RenderingUtil.rectangle((double)(xOffset + this.width - 8.0F), (double)(yOffset + 19.0F), (double)(xOffset + this.width - 8.0F) - 7.5D, (double)(yOffset + this.height - 20.0F) + 0.5D, Colors.getColor(33));
      RenderingUtil.rectangle((double)(xOffset + this.width - 9.0F), (double)(yOffset + 23.0F), (double)(xOffset + this.width - 8.0F - 7.0F), (double)(yOffset + this.height - 24.0F), Colors.getColor(60));
      RenderingUtil.rectangleBordered((double)(xOffset + this.width) - 8.5D, (double)(yOffset + 19.0F), (double)(xOffset + this.width - 8.0F - 7.0F), (double)yOffset + 26.5D, 0.5D, Colors.getColor(47), Colors.getColor(38));
      RenderingUtil.rectangleBordered((double)(xOffset + this.width) - 8.5D, (double)(yOffset + this.height - 20.0F - 7.0F), (double)(xOffset + this.width - 8.0F - 7.0F), (double)(yOffset + this.height - 20.0F), 0.5D, Colors.getColor(47), Colors.getColor(38));
      GlStateManager.pushMatrix();
      GlStateManager.translate((double)(xOffset + this.width - 8.0F - 5.0F), (double)yOffset + 22.5D, 0.0D);
      GlStateManager.rotate(270.0F, 0.0F, 0.0F, 90.0F);
      RenderingUtil.rectangle(-1.0D, -0.5D, -0.5D, 3.0D, Colors.getColor(111));
      RenderingUtil.rectangle(-0.5D, 0.0D, 0.0D, 2.5D, Colors.getColor(111));
      RenderingUtil.rectangle(0.0D, 0.5D, 0.5D, 2.0D, Colors.getColor(111));
      RenderingUtil.rectangle(0.5D, 1.0D, 1.0D, 1.5D, Colors.getColor(111));
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate((double)(xOffset + this.width - 8.0F) - 2.5D, (double)(yOffset + this.height) - 23.5D, 0.0D);
      GlStateManager.rotate(90.0F, 0.0F, 0.0F, 90.0F);
      RenderingUtil.rectangle(-1.0D, -0.5D, -0.5D, 3.0D, Colors.getColor(111));
      RenderingUtil.rectangle(-0.5D, 0.0D, 0.0D, 2.5D, Colors.getColor(111));
      RenderingUtil.rectangle(0.0D, 0.5D, 0.5D, 2.0D, Colors.getColor(111));
      RenderingUtil.rectangle(0.5D, 1.0D, 1.0D, 1.5D, Colors.getColor(111));
      GlStateManager.popMatrix();
      RenderingUtil.rectangle((double)(xOffset + 8.0F), (double)(yOffset + 19.0F), (double)(xOffset + this.width - 8.0F), (double)yOffset + 19.5D, Colors.getColor(0, 65));
      RenderingUtil.rectangle((double)(xOffset + 8.0F), (double)(yOffset + 19.0F), (double)xOffset + 8.5D, (double)(yOffset + this.height - 20.0F), Colors.getColor(0, 65));
      RenderingUtil.rectangle((double)(xOffset + 8.0F), (double)(yOffset + this.height - 20.0F), (double)(xOffset + this.width - 8.0F), (double)(yOffset + this.height - 20.0F) + 0.5D, Colors.getColor(255, 140));
      RenderingUtil.rectangle((double)(xOffset + this.width) - 8.5D, (double)(yOffset + 19.0F), (double)(xOffset + this.width - 8.0F), (double)(yOffset + this.height - 20.0F) + 0.5D, Colors.getColor(255, 140));
      RenderingUtil.rectangle((double)(xOffset + 8.0F), (double)(yOffset + this.height) - 15.5D, (double)(xOffset + this.width - 8.0F) - 40.5D, (double)(yOffset + this.height - 15.0F), Colors.getColor(0, 65));
      RenderingUtil.rectangle((double)(xOffset + 8.0F), (double)(yOffset + this.height) - 15.5D, (double)xOffset + 8.5D, (double)(yOffset + this.height - 7.0F), Colors.getColor(0, 65));
      RenderingUtil.rectangle((double)(xOffset + this.width - 8.0F - 41.0F), (double)(yOffset + this.height) - 15.5D, (double)(xOffset + this.width - 8.0F) - 40.5D, (double)(yOffset + this.height - 7.0F), Colors.getColor(255, 140));
      RenderingUtil.rectangle((double)(xOffset + 8.0F), (double)(yOffset + this.height) - 7.5D, (double)(xOffset + this.width - 8.0F) - 40.5D, (double)(yOffset + this.height - 7.0F), Colors.getColor(255, 140));
      RenderingUtil.rectangle((double)(xOffset + this.width - 8.0F - 40.5F + 3.0F), (double)(yOffset + this.height) - 15.5D, (double)(xOffset + this.width - 8.0F - 40.5F + 3.0F + 32.0F), (double)(yOffset + this.height - 16.0F + 9.0F), Colors.getColor(8));
      RenderingUtil.rectangle((double)(xOffset + this.width - 8.0F - 40.5F) + 3.5D, (double)(yOffset + this.height - 15.0F), (double)(xOffset + this.width - 8.0F - 40.5F + 3.0F + 32.0F) - 0.5D, (double)(yOffset + this.height - 16.0F + 9.0F) - 0.5D, Colors.getColor(181));
      RenderingUtil.rectangle((double)(xOffset + this.width - 8.0F - 40.5F + 4.0F), (double)(yOffset + this.height) - 14.5D, (double)(xOffset + this.width - 8.0F - 40.5F + 3.0F) + 31.5D, (double)(yOffset + this.height - 16.0F) + 8.5D, Colors.getColor(62));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F - 1.0F), (double)yOffset + 8.5D, (double)(xOffset + this.width - 4.0F), (double)yOffset + 9.5D, Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F) - 1.5D, (double)(yOffset + 8.0F), (double)(xOffset + this.width - 4.0F) - 0.5D, (double)(yOffset + 9.0F), Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F) - 1.5D, (double)(yOffset + 7.0F), (double)(xOffset + this.width - 4.0F) - 0.5D, (double)(yOffset + 6.0F), Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F - 1.0F), (double)yOffset + 5.5D, (double)(xOffset + this.width - 4.0F), (double)yOffset + 6.5D, Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F - 4.0F), (double)yOffset + 8.5D, (double)(xOffset + this.width - 4.0F - 3.0F), (double)yOffset + 9.5D, Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F) - 3.5D, (double)(yOffset + 8.0F), (double)(xOffset + this.width - 4.0F) - 2.5D, (double)(yOffset + 9.0F), Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F) - 3.5D, (double)(yOffset + 7.0F), (double)(xOffset + this.width - 4.0F) - 2.5D, (double)(yOffset + 6.0F), Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F - 4.0F), (double)yOffset + 5.5D, (double)(xOffset + this.width - 4.0F - 3.0F), (double)yOffset + 6.5D, Colors.getColor(201));
      RenderingUtil.rectangle((double)(xOffset + this.width - 4.0F - 3.0F), (double)yOffset + 6.5D, (double)(xOffset + this.width - 4.0F - 1.0F), (double)yOffset + 8.5D, Colors.getColor(201));
      Client.verdana10.drawString("Submit", xOffset + this.width - 8.0F - 40.5F + 6.0F, yOffset + this.height - 12.0F, -1);
      Client.verdana10.drawString("§lConsole", xOffset + 8.0F, yOffset + 7.0F, -1);
      if (this.dragging) {
         this.dragX = mouseX - this.lastDragX;
         this.dragY = mouseY - this.lastDragY;
      }

      if (this.expanding) {
         this.width = mouseX - this.lastWidth;
         this.height = mouseY - this.lastHeight;
         if (this.width < 70.0F) {
            this.width = 70.0F;
         }

         if (this.height < 55.0F) {
            this.height = 55.0F;
         }
      }

      Iterator var9 = this.components.iterator();

      while(var9.hasNext()) {
         SourceComponent component = (SourceComponent)var9.next();
         component.drawScreen(mouseX, mouseY);
      }

   }

   public void keyboardTyped(int keyTyped) {
      if (Client.getSourceConsoleGUI().timer.delay(100.0F) && keyTyped == 41) {
         this.dragging = false;
         Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
      }

   }
}
