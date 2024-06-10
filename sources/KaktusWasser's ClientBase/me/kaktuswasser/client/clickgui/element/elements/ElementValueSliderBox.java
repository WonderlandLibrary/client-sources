// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.clickgui.element.elements;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.clickgui.element.Element;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.utilities.NahrFont.FontType;
import me.kaktuswasser.client.values.Value;
import net.minecraft.client.Minecraft;

public class ElementValueSliderBox extends Element {
   protected Value value;
   protected String text;
   protected boolean drag;
   protected boolean set = true;
   protected float textWidth;
   protected float maxWidth;

   public ElementValueSliderBox(Value value) {
      this.value = value;
      Iterator var3 = Client.getModuleManager().getModules().iterator();

      while(var3.hasNext()) {
         Module module = (Module)var3.next();
         if(value.getName().startsWith(module.getName().toLowerCase() + "_")) {
            this.text = value.getName().substring(module.getName().length() + 1);
         }
      }

   }

   public boolean isHovering(int mouseX, int mouseY) {
      return mouseX >= this.x + 1 && mouseX <= this.x + this.width - 1 && mouseY >= this.y + 5 && mouseY <= this.y + this.height - 1;
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.value.getValue() instanceof Boolean) {
         Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0F, 0.75F);
         this.value.setValue(Boolean.valueOf(!((Boolean)this.value.getValue()).booleanValue()));
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      if(state == 0) {
         this.drag = false;
      }

   }

   public void drawScreen(int mouseX, int mouseY, float button) {
      this.setTextWidth(RenderHelper.getNahrFont().getStringWidth(this.text));
      if(this.value.getValue() instanceof Boolean) {
         RenderHelper.drawRect((float)(this.x + 1), (float)(this.y + 5), (float)(this.x + this.width - 1), (float)(this.y + this.height - 1), ((Boolean)this.value.getValue()).booleanValue()? 0x9971f442: 0x1071f442);
         RenderHelper.drawRect((float)this.x, (float)(this.y + 4), (float)(this.x + 1), (float)(this.y + this.height), -872415232);
         RenderHelper.drawRect((float)this.x, (float)(this.y + 4), (float)(this.x + this.width), (float)(this.y + 5), -872415232);
         RenderHelper.drawRect((float)(this.x + this.width - 1), (float)(this.y + 4), (float)(this.x + this.width), (float)(this.y + this.height), -872415232);
         RenderHelper.drawRect((float)this.x, (float)(this.y + this.height - 1), (float)(this.x + this.width), (float)(this.y + this.height), -872415232);
         if(this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect((float)(this.x + 1), (float)(this.y + 5), (float)(this.x + this.width - 1), (float)(this.y + this.height - 1), -2130706433);
         }

         RenderHelper.getNahrFont().drawString(this.text, (float)(this.x + 18), (float)(this.y + 1), FontType.SHADOW_THIN, -1, -16777216);
      }

   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public Value getValue() {
      return this.value;
   }

   public void setValue(Value value) {
      this.value = value;
   }

   public boolean isDrag() {
      return this.drag;
   }

   public void setDrag(boolean drag) {
      this.drag = drag;
   }

   public float getTextWidth() {
      return this.textWidth;
   }

   public void setTextWidth(float textWidth) {
      this.textWidth = textWidth;
   }

   public float getMaxWidth() {
      return this.maxWidth;
   }

   public void setMaxWidth(float maxWidth) {
      this.maxWidth = maxWidth;
   }
}
