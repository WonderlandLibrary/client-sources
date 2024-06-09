package org.newdawn.slick.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

















public class MouseOverArea
  extends AbstractComponent
{
  private static final int NORMAL = 1;
  private static final int MOUSE_DOWN = 2;
  private static final int MOUSE_OVER = 3;
  private Image normalImage;
  private Image mouseOverImage;
  private Image mouseDownImage;
  private Color normalColor = Color.white;
  

  private Color mouseOverColor = Color.white;
  

  private Color mouseDownColor = Color.white;
  

  private Sound mouseOverSound;
  

  private Sound mouseDownSound;
  

  private Shape area;
  

  private Image currentImage;
  

  private Color currentColor;
  

  private boolean over;
  

  private boolean mouseDown;
  

  private int state = 1;
  







  private boolean mouseUp;
  







  public MouseOverArea(GUIContext container, Image image, int x, int y, ComponentListener listener)
  {
    this(container, image, x, y, image.getWidth(), image.getHeight());
    addListener(listener);
  }
  











  public MouseOverArea(GUIContext container, Image image, int x, int y)
  {
    this(container, image, x, y, image.getWidth(), image.getHeight());
  }
  


















  public MouseOverArea(GUIContext container, Image image, int x, int y, int width, int height, ComponentListener listener)
  {
    this(container, image, x, y, width, height);
    addListener(listener);
  }
  
















  public MouseOverArea(GUIContext container, Image image, int x, int y, int width, int height)
  {
    this(container, image, new Rectangle(x, y, width, height));
  }
  









  public MouseOverArea(GUIContext container, Image image, Shape shape)
  {
    super(container);
    
    area = shape;
    normalImage = image;
    currentImage = image;
    mouseOverImage = image;
    mouseDownImage = image;
    
    currentColor = normalColor;
    
    state = 1;
    Input input = container.getInput();
    over = area.contains(input.getMouseX(), input.getMouseY());
    mouseDown = input.isMouseButtonDown(0);
    updateImage();
  }
  





  public void setLocation(float x, float y)
  {
    if (area != null) {
      area.setX(x);
      area.setY(y);
    }
  }
  




  public void setX(float x)
  {
    area.setX(x);
  }
  




  public void setY(float y)
  {
    area.setY(y);
  }
  




  public int getX()
  {
    return (int)area.getX();
  }
  




  public int getY()
  {
    return (int)area.getY();
  }
  





  public void setNormalColor(Color color)
  {
    normalColor = color;
  }
  





  public void setMouseOverColor(Color color)
  {
    mouseOverColor = color;
  }
  





  public void setMouseDownColor(Color color)
  {
    mouseDownColor = color;
  }
  





  public void setNormalImage(Image image)
  {
    normalImage = image;
  }
  





  public void setMouseOverImage(Image image)
  {
    mouseOverImage = image;
  }
  





  public void setMouseDownImage(Image image)
  {
    mouseDownImage = image;
  }
  



  public void render(GUIContext container, Graphics g)
  {
    if (currentImage != null)
    {
      int xp = (int)(area.getX() + (getWidth() - currentImage.getWidth()) / 2);
      int yp = (int)(area.getY() + (getHeight() - currentImage.getHeight()) / 2);
      
      currentImage.draw(xp, yp, currentColor);
    } else {
      g.setColor(currentColor);
      g.fill(area);
    }
    updateImage();
  }
  


  private void updateImage()
  {
    if (!over) {
      currentImage = normalImage;
      currentColor = normalColor;
      state = 1;
      mouseUp = false;
    } else {
      if (mouseDown) {
        if ((state != 2) && (mouseUp)) {
          if (mouseDownSound != null) {
            mouseDownSound.play();
          }
          currentImage = mouseDownImage;
          currentColor = mouseDownColor;
          state = 2;
          
          notifyListeners();
          mouseUp = false;
        }
        
        return;
      }
      mouseUp = true;
      if (state != 3) {
        if (mouseOverSound != null) {
          mouseOverSound.play();
        }
        currentImage = mouseOverImage;
        currentColor = mouseOverColor;
        state = 3;
      }
    }
    

    mouseDown = false;
    state = 1;
  }
  





  public void setMouseOverSound(Sound sound)
  {
    mouseOverSound = sound;
  }
  





  public void setMouseDownSound(Sound sound)
  {
    mouseDownSound = sound;
  }
  


  public void mouseMoved(int oldx, int oldy, int newx, int newy)
  {
    over = area.contains(newx, newy);
  }
  


  public void mouseDragged(int oldx, int oldy, int newx, int newy)
  {
    mouseMoved(oldx, oldy, newx, newy);
  }
  


  public void mousePressed(int button, int mx, int my)
  {
    over = area.contains(mx, my);
    if (button == 0) {
      mouseDown = true;
    }
  }
  


  public void mouseReleased(int button, int mx, int my)
  {
    over = area.contains(mx, my);
    if (button == 0) {
      mouseDown = false;
    }
  }
  


  public int getHeight()
  {
    return (int)(area.getMaxY() - area.getY());
  }
  


  public int getWidth()
  {
    return (int)(area.getMaxX() - area.getX());
  }
  




  public boolean isMouseOver()
  {
    return over;
  }
  





  public void setLocation(int x, int y)
  {
    setLocation(x, y);
  }
}
