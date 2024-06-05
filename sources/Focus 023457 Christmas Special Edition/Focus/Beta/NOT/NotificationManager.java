package Focus.Beta.NOT;

import java.awt.Color;
import java.util.ArrayList;

import Focus.Beta.IMPL.font.CFont;
import Focus.Beta.IMPL.font.CFontRenderer;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.UTILS.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class NotificationManager {
  private static final ArrayList<Notification> notis = new ArrayList<>();
  
  private static final ArrayList<Notification> removeQueue = new ArrayList<>();
  
  public static void render() {
    CFontRenderer tFontRenderer = FontLoaders.arial18;
    ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
    float height = 30.0F;
    float margin = 5.0F;
    float inMargin = 5.0F;
    float y = height + margin;
    Notification[] notifs = notis.<Notification>toArray(new Notification[0]);
    for (Notification n : notifs) {
      float t = Math.max(tFontRenderer.getStringWidth(n.title), tFontRenderer.getStringWidth(n.desc));
      float width = 40.0F + inMargin + t;
      if (System.currentTimeMillis() - n.timeStarted > n.time) {
        n.baixarMinecraftFree2021(-(width + margin), 0.0F, 0.15F);
        if (n.yOffset <= 0.0F)
          removeQueue.add(n); 
      } else {
        n.yOffset = height + margin;
        n.baxiarX(width + margin, 0.15F);
      } 
      GlStateManager.pushMatrix();
      RenderUtil.blur(res.getScaledWidth() - n.xOffset, res.getScaledHeight() - y, res.getScaledWidth() - n.xOffset + width, res.getScaledHeight() - y + height);
      GlStateManager.translate(res.getScaledWidth() - n.xOffset, res.getScaledHeight() - y, 0.0F);
      RenderUtil.rect(0.0F, 0.0F, width, height, new Color(10, 10, 30, 120));
      float b = ((float)n.time - n.getTimePassed()) / (float)n.time % 1.0F;
      RenderUtil.rect(0.0F, 0.0F, Math.max(width * (1.0F - b), 0.1F), 1.0F, n.type.getColor());
      tFontRenderer.drawString(n.title, inMargin * 2.0F, height / 2.0F - tFontRenderer.getHeight() / 2.0F, n.type.getColor().getRGB());
      GlStateManager.popMatrix();
      y += n.yOffset;
    } 
    notis.removeAll(removeQueue);
    removeQueue.clear();
  }
  
  public static void addNoti(String title, String desc, NotificationType notiType, long time) {
    notis.add(new Notification(title, desc, notiType, time));
  }
  
  public static void publish(String content, NotificationType type, long ms) {
    notis.add(new Notification(content, "", type, ms));
  }
  
  public static void publish(Object content, NotificationType type, long ms) {
    notis.add(new Notification(String.valueOf(content), "", type, ms));
  }
  
  public static class Notification {
    public String title;
    
    public String desc;
    
    public long time;
    
    public NotificationType type;
    
    public long timeStarted;
    
    public float xOffset;
    
    public float yOffset;
    
    public Notification(String title, String desc, NotificationType notiType, long time) {
      this.title = title;
      this.desc = desc;
      this.time = time;
      this.type = notiType;
      this.timeStarted = System.currentTimeMillis();
      this.xOffset = 0.0F;
      this.yOffset = 0.0F;
    }
    
    public float getTimePassed() {
      return (float)(System.currentTimeMillis() - this.timeStarted);
    }
    
    public void baxiarX(float x, float s) {
      this.xOffset = RenderUtil.animate(x, this.xOffset, s);
    }
    
    public void baxiarY(float y, float s) {
      this.yOffset = RenderUtil.animate(y, this.yOffset, s);
    }
    
    public void baixarMinecraftFree2021(float x, float y, float s) {
      baxiarX(x, s);
      baxiarY(y, s);
    }
  }
}
