package zamorozka.ui;

public class Render2DEvent
{
private int width;
private int height;

public Render2DEvent(int width, int height) {
  this.width = width;
  this.height = height;
}


public int getWidth() { return this.width; }



public void setWidth(int width) { this.width = width; }



public int getHeight() { return this.height; }



public void setHeight(int height) { this.height = height; }
}
