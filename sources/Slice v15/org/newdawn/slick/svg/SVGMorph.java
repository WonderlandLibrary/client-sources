package org.newdawn.slick.svg;

import java.util.ArrayList;
import org.newdawn.slick.geom.MorphShape;






public class SVGMorph
  extends Diagram
{
  private ArrayList figures = new ArrayList();
  




  public SVGMorph(Diagram diagram)
  {
    super(diagram.getWidth(), diagram.getHeight());
    
    for (int i = 0; i < diagram.getFigureCount(); i++) {
      Figure figure = diagram.getFigure(i);
      Figure copy = new Figure(figure.getType(), new MorphShape(figure.getShape()), figure.getData(), figure.getTransform());
      
      figures.add(copy);
    }
  }
  




  public void addStep(Diagram diagram)
  {
    if (diagram.getFigureCount() != figures.size()) {
      throw new RuntimeException("Mismatched diagrams, missing ids");
    }
    for (int i = 0; i < diagram.getFigureCount(); i++) {
      Figure figure = diagram.getFigure(i);
      String id = figure.getData().getMetaData();
      
      for (int j = 0; j < figures.size(); j++) {
        Figure existing = (Figure)figures.get(j);
        if (existing.getData().getMetaData().equals(id)) {
          MorphShape morph = (MorphShape)existing.getShape();
          morph.addShape(figure.getShape());
          break;
        }
      }
    }
  }
  






  public void setExternalDiagram(Diagram diagram)
  {
    for (int i = 0; i < figures.size(); i++) {
      Figure figure = (Figure)figures.get(i);
      
      for (int j = 0; j < diagram.getFigureCount(); j++) {
        Figure newBase = diagram.getFigure(j);
        if (newBase.getData().getMetaData().equals(figure.getData().getMetaData())) {
          MorphShape shape = (MorphShape)figure.getShape();
          shape.setExternalFrame(newBase.getShape());
          break;
        }
      }
    }
  }
  




  public void updateMorphTime(float delta)
  {
    for (int i = 0; i < figures.size(); i++) {
      Figure figure = (Figure)figures.get(i);
      MorphShape shape = (MorphShape)figure.getShape();
      shape.updateMorphTime(delta);
    }
  }
  





  public void setMorphTime(float time)
  {
    for (int i = 0; i < figures.size(); i++) {
      Figure figure = (Figure)figures.get(i);
      MorphShape shape = (MorphShape)figure.getShape();
      shape.setMorphTime(time);
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
}
