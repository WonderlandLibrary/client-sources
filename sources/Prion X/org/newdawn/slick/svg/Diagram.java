package org.newdawn.slick.svg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.newdawn.slick.geom.Shape;




public class Diagram
{
  private ArrayList figures = new ArrayList();
  
  private HashMap patterns = new HashMap();
  
  private HashMap gradients = new HashMap();
  
  private HashMap figureMap = new HashMap();
  


  private float width;
  


  private float height;
  


  public Diagram(float width, float height)
  {
    this.width = width;
    this.height = height;
  }
  




  public float getWidth()
  {
    return width;
  }
  




  public float getHeight()
  {
    return height;
  }
  





  public void addPatternDef(String name, String href)
  {
    patterns.put(name, href);
  }
  





  public void addGradient(String name, Gradient gradient)
  {
    gradients.put(name, gradient);
  }
  





  public String getPatternDef(String name)
  {
    return (String)patterns.get(name);
  }
  





  public Gradient getGradient(String name)
  {
    return (Gradient)gradients.get(name);
  }
  




  public String[] getPatternDefNames()
  {
    return (String[])patterns.keySet().toArray(new String[0]);
  }
  





  public Figure getFigureByID(String id)
  {
    return (Figure)figureMap.get(id);
  }
  




  public void addFigure(Figure figure)
  {
    figures.add(figure);
    figureMap.put(figure.getData().getAttribute("id"), figure);
    
    String fillRef = figure.getData().getAsReference("fill");
    Gradient gradient = getGradient(fillRef);
    if ((gradient != null) && 
      (gradient.isRadial())) {
      for (int i = 0; i < InkscapeLoader.RADIAL_TRIANGULATION_LEVEL; i++) {
        figure.getShape().increaseTriangulation();
      }
    }
  }
  





  public int getFigureCount()
  {
    return figures.size();
  }
  





  public Figure getFigure(int index)
  {
    return (Figure)figures.get(index);
  }
  




  public void removeFigure(Figure figure)
  {
    figures.remove(figure);
    figureMap.remove(figure.getData().getAttribute("id"));
  }
}
