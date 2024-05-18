package net.SliceClient.Utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;



public class PlayerSkin
{
  private static final double SKIN_HEIGHT = 32.0D;
  private Map<SkinComponent, ResourceLocation> skinLocations;
  private boolean skinLoaded;
  
  public PlayerSkin()
  {
    skinLocations = new HashMap();
  }
  
  public void setPlayerSkin(BufferedImage skin, String resourceLocation)
  {
    SkinComponent[] arrayOfSkinComponent;
    int j = (arrayOfSkinComponent = SkinComponent.values()).length;
    for (int i = 0; i < j; i++)
    {
      SkinComponent skinComponent = arrayOfSkinComponent[i];
      skinLocations.put(skinComponent, skinComponent.crop(skin, resourceLocation));
    }
    skinLoaded = true;
  }
  
  public void renderComponent(SkinComponent skinComponent, int posX, int posY, int width, int height)
  {
    if (skinLoaded)
    {
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      ResourceLocation skinLocation = (ResourceLocation)skinLocations.get(skinComponent);
      ImageRenderer.GENERIC_RENDERER.renderImage(skinLocation, posX, posY, width, height);
    }
    else if (skinComponent != SkinComponent.HAT)
    {
      Gui.drawRect(posX, posY, posX + width, posY + height, 5263440);
    }
  }
  
  private void renderComponent(SkinComponent skinComponent, int posX, int posY)
  {
    renderComponent(skinComponent, posX, posY, skinComponent.getWidth(), skinComponent.getHeight());
  }
  
  public void renderSkin(double posX, double posY, double height)
  {
    double scale = height / 32.0D;
    GL11.glPushMatrix();
    GL11.glTranslated(posX, posY, 0.0D);
    GL11.glScaled(scale, scale, 1.0D);
    renderComponent(SkinComponent.HEAD, 4, 0);
    renderComponent(SkinComponent.HAT, 4, 0);
    renderComponent(SkinComponent.LEFT_ARM, 0, 8);
    renderComponent(SkinComponent.RIGHT_ARM, 12, 8);
    renderComponent(SkinComponent.BODY, 4, 8);
    renderComponent(SkinComponent.LEFT_LEG, 8, 20);
    renderComponent(SkinComponent.RIGHT_LEG, 4, 20);
    GL11.glPopMatrix();
  }
  
  public boolean isSkinLoaded()
  {
    return skinLoaded;
  }
}
