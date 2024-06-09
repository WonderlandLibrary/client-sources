package org.newdawn.slick.util.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;







public class AStarPathFinder
  implements PathFinder, PathFindingContext
{
  private ArrayList closed = new ArrayList();
  
  private PriorityList open = new PriorityList(null);
  

  private TileBasedMap map;
  

  private int maxSearchDistance;
  

  private Node[][] nodes;
  

  private boolean allowDiagMovement;
  

  private AStarHeuristic heuristic;
  

  private Node current;
  

  private Mover mover;
  

  private int sourceX;
  
  private int sourceY;
  
  private int distance;
  

  public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement)
  {
    this(map, maxSearchDistance, allowDiagMovement, new ClosestHeuristic());
  }
  








  public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement, AStarHeuristic heuristic)
  {
    this.heuristic = heuristic;
    this.map = map;
    this.maxSearchDistance = maxSearchDistance;
    this.allowDiagMovement = allowDiagMovement;
    
    nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
    for (int x = 0; x < map.getWidthInTiles(); x++) {
      for (int y = 0; y < map.getHeightInTiles(); y++) {
        nodes[x][y] = new Node(x, y);
      }
    }
  }
  


  public Path findPath(Mover mover, int sx, int sy, int tx, int ty)
  {
    current = null;
    

    this.mover = mover;
    sourceX = tx;
    sourceY = ty;
    distance = 0;
    
    if (map.blocked(this, tx, ty)) {
      return null;
    }
    
    for (int x = 0; x < map.getWidthInTiles(); x++) {
      for (int y = 0; y < map.getHeightInTiles(); y++) {
        nodes[x][y].reset();
      }
    }
    


    nodes[sx][sy].cost = 0.0F;
    nodes[sx][sy].depth = 0;
    closed.clear();
    open.clear();
    addToOpen(nodes[sx][sy]);
    
    nodes[tx][ty].parent = null;
    

    int maxDepth = 0;
    int x; for (; (maxDepth < maxSearchDistance) && (open.size() != 0); 
        






















        x < 2)
    {
      int lx = sx;
      int ly = sy;
      if (current != null) {
        lx = current.x;
        ly = current.y;
      }
      
      current = getFirstInOpen();
      distance = current.depth;
      
      if ((current == nodes[tx][ty]) && 
        (isValidLocation(mover, lx, ly, tx, ty))) {
        break;
      }
      

      removeFromOpen(current);
      addToClosed(current);
      


      x = -1; continue;
      for (int y = -1; y < 2; y++)
      {
        if ((x != 0) || (y != 0))
        {




          if ((allowDiagMovement) || 
            (x == 0) || (y == 0))
          {




            int xp = x + current.x;
            int yp = y + current.y;
            
            if (isValidLocation(mover, current.x, current.y, xp, yp))
            {


              float nextStepCost = current.cost + getMovementCost(mover, current.x, current.y, xp, yp);
              Node neighbour = nodes[xp][yp];
              map.pathFinderVisited(xp, yp);
              




              if (nextStepCost < cost) {
                if (inOpenList(neighbour)) {
                  removeFromOpen(neighbour);
                }
                if (inClosedList(neighbour)) {
                  removeFromClosed(neighbour);
                }
              }
              



              if ((!inOpenList(neighbour)) && (!inClosedList(neighbour))) {
                cost = nextStepCost;
                heuristic = getHeuristicCost(mover, xp, yp, tx, ty);
                maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                addToOpen(neighbour);
              }
            }
          }
        }
      }
      x++;
    }
    





















































    if (nodes[tx][ty].parent == null) {
      return null;
    }
    



    Path path = new Path();
    Node target = nodes[tx][ty];
    while (target != nodes[sx][sy]) {
      path.prependStep(x, y);
      target = parent;
    }
    path.prependStep(sx, sy);
    

    return path;
  }
  




  public int getCurrentX()
  {
    if (current == null) {
      return -1;
    }
    
    return current.x;
  }
  




  public int getCurrentY()
  {
    if (current == null) {
      return -1;
    }
    
    return current.y;
  }
  





  protected Node getFirstInOpen()
  {
    return (Node)open.first();
  }
  




  protected void addToOpen(Node node)
  {
    node.setOpen(true);
    open.add(node);
  }
  





  protected boolean inOpenList(Node node)
  {
    return node.isOpen();
  }
  




  protected void removeFromOpen(Node node)
  {
    node.setOpen(false);
    open.remove(node);
  }
  




  protected void addToClosed(Node node)
  {
    node.setClosed(true);
    closed.add(node);
  }
  





  protected boolean inClosedList(Node node)
  {
    return node.isClosed();
  }
  




  protected void removeFromClosed(Node node)
  {
    node.setClosed(false);
    closed.remove(node);
  }
  









  protected boolean isValidLocation(Mover mover, int sx, int sy, int x, int y)
  {
    boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles());
    
    if ((!invalid) && ((sx != x) || (sy != y))) {
      this.mover = mover;
      sourceX = sx;
      sourceY = sy;
      invalid = map.blocked(this, x, y);
    }
    
    return !invalid;
  }
  









  public float getMovementCost(Mover mover, int sx, int sy, int tx, int ty)
  {
    this.mover = mover;
    sourceX = sx;
    sourceY = sy;
    
    return map.getCost(this, tx, ty);
  }
  










  public float getHeuristicCost(Mover mover, int x, int y, int tx, int ty)
  {
    return heuristic.getCost(map, mover, x, y, tx, ty);
  }
  





  private class PriorityList
  {
    private List list = new LinkedList();
    

    private PriorityList() {}
    

    public Object first()
    {
      return list.get(0);
    }
    


    public void clear()
    {
      list.clear();
    }
    





    public void add(Object o)
    {
      for (int i = 0; i < list.size(); i++) {
        if (((Comparable)list.get(i)).compareTo(o) > 0) {
          list.add(i, o);
          break;
        }
      }
      if (!list.contains(o)) {
        list.add(o);
      }
    }
    





    public void remove(Object o)
    {
      list.remove(o);
    }
    




    public int size()
    {
      return list.size();
    }
    





    public boolean contains(Object o)
    {
      return list.contains(o);
    }
    
    public String toString() {
      String temp = "{";
      for (int i = 0; i < size(); i++) {
        temp = temp + list.get(i).toString() + ",";
      }
      temp = temp + "}";
      
      return temp;
    }
  }
  


  private class Node
    implements Comparable
  {
    private int x;
    

    private int y;
    

    private float cost;
    

    private Node parent;
    

    private float heuristic;
    
    private int depth;
    
    private boolean open;
    
    private boolean closed;
    

    public Node(int x, int y)
    {
      this.x = x;
      this.y = y;
    }
    





    public int setParent(Node parent)
    {
      depth += 1;
      this.parent = parent;
      
      return depth;
    }
    


    public int compareTo(Object other)
    {
      Node o = (Node)other;
      
      float f = heuristic + cost;
      float of = heuristic + cost;
      
      if (f < of)
        return -1;
      if (f > of) {
        return 1;
      }
      return 0;
    }
    





    public void setOpen(boolean open)
    {
      this.open = open;
    }
    




    public boolean isOpen()
    {
      return open;
    }
    




    public void setClosed(boolean closed)
    {
      this.closed = closed;
    }
    




    public boolean isClosed()
    {
      return closed;
    }
    


    public void reset()
    {
      closed = false;
      open = false;
      cost = 0.0F;
      depth = 0;
    }
    


    public String toString()
    {
      return "[Node " + x + "," + y + "]";
    }
  }
  


  public Mover getMover()
  {
    return mover;
  }
  


  public int getSearchDistance()
  {
    return distance;
  }
  


  public int getSourceX()
  {
    return sourceX;
  }
  


  public int getSourceY()
  {
    return sourceY;
  }
}
