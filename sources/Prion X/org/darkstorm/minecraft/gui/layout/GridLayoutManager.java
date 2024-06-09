package org.darkstorm.minecraft.gui.layout;

import java.awt.Rectangle;

public class GridLayoutManager implements LayoutManager {
  private int columns;
  private int rows;
  
  public GridLayoutManager(int columns, int rows) { this.columns = columns;
    this.rows = rows;
  }
  

  public void reposition(Rectangle area, Rectangle[] componentAreas, Constraint[][] constraints)
  {
    if (componentAreas.length == 0) return;
    int componentsPerColumn;
    int componentsPerRow;
    int componentsPerColumn; if (columns == 0) {
      if (rows == 0) {
        double square = Math.sqrt(componentAreas.length);
        int componentsPerColumn = (int)square;
        int componentsPerRow = (int)square;
        if (square - (int)square > 0.0D)
          componentsPerColumn++;
      } else {
        int componentsPerRow = componentAreas.length / rows;
        if (componentAreas.length % rows > 0)
          componentsPerRow++;
        componentsPerColumn = rows;
      } } else { int componentsPerRow;
      if (rows == 0) {
        int componentsPerColumn = componentAreas.length / columns;
        if (componentAreas.length % columns > 0)
          componentsPerColumn++;
        componentsPerRow = columns;
      } else {
        componentsPerRow = columns;
        componentsPerColumn = rows;
      } }
    double elementWidth = width / componentsPerRow;
    double elementHeight = height / 
      componentsPerColumn;
    for (int row = 0; row < componentsPerColumn; row++) {
      for (int element = 0; element < componentsPerRow; element++) {
        int index = row * componentsPerRow + element;
        if (index >= componentAreas.length)
          break;
        Rectangle componentArea = componentAreas[index];
        Constraint[] componentConstraints = constraints[index];
        HorizontalGridConstraint horizontalAlign = HorizontalGridConstraint.LEFT;
        VerticalGridConstraint verticalAlign = VerticalGridConstraint.CENTER;
        for (Constraint constraint : componentConstraints) {
          if ((constraint instanceof HorizontalGridConstraint)) {
            horizontalAlign = (HorizontalGridConstraint)constraint;
          } else if ((constraint instanceof VerticalGridConstraint))
            verticalAlign = (VerticalGridConstraint)constraint;
        }
        switch (horizontalAlign) {
        case RIGHT: 
          width = ((int)elementWidth);
        case FILL: 
          x = ((int)(x + element * elementWidth));
          break;
        case LEFT: 
          x = 
            ((int)(x + (element + 1) * elementWidth - width));
          break;
        case CENTER: 
          x = 
            ((int)(x + element * elementWidth + elementWidth / 2.0D - width / 2));
        }
        
        switch (verticalAlign) {
        case TOP: 
          height = ((int)elementHeight);
        case CENTER: 
          y = ((int)(y + row * elementHeight));
          break;
        case FILL: 
          y = 
            ((int)(y + (row + 1) * elementHeight - height));
          break;
        case BOTTOM: 
          y = 
            ((int)(y + row * elementHeight + elementHeight / 2.0D - height / 2));
        }
        
      }
    }
  }
  

  public java.awt.Dimension getOptimalPositionedSize(Rectangle[] componentAreas, Constraint[][] constraints)
  {
    if (componentAreas.length == 0)
      return new java.awt.Dimension(0, 0);
    int componentsPerColumn;
    int componentsPerRow; int componentsPerColumn; if (columns == 0) {
      if (rows == 0) {
        double square = Math.sqrt(componentAreas.length);
        int componentsPerColumn = (int)square;
        int componentsPerRow = (int)square;
        if (square - (int)square > 0.0D)
          componentsPerColumn++;
      } else {
        int componentsPerRow = componentAreas.length / rows;
        if (componentAreas.length % rows > 0)
          componentsPerRow++;
        componentsPerColumn = rows;
      } } else { int componentsPerRow;
      if (rows == 0) {
        int componentsPerColumn = componentAreas.length / columns;
        if (componentAreas.length % columns > 0)
          componentsPerColumn++;
        componentsPerRow = columns;
      } else {
        componentsPerRow = columns;
        componentsPerColumn = rows;
      } }
    int maxElementWidth = 0;int maxElementHeight = 0;
    for (Rectangle component : componentAreas) {
      maxElementWidth = Math.max(maxElementWidth, width);
      maxElementHeight = Math.max(maxElementHeight, height);
    }
    return new java.awt.Dimension(maxElementWidth * componentsPerRow, 
      maxElementHeight * componentsPerColumn);
  }
  
  public int getColumns() {
    return columns;
  }
  
  public int getRows() {
    return rows;
  }
  
  public void setColumns(int columns) {
    this.columns = columns;
  }
  
  public void setRows(int rows) {
    this.rows = rows;
  }
  
  public static enum HorizontalGridConstraint implements Constraint {
    CENTER, 
    LEFT, 
    RIGHT, 
    FILL;
  }
  
  public static enum VerticalGridConstraint implements Constraint {
    CENTER, 
    TOP, 
    BOTTOM, 
    FILL;
  }
}
