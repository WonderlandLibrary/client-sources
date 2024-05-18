package org.newdawn.slick.command;





public class BasicCommand
  implements Command
{
  private String name;
  




  public BasicCommand(String name)
  {
    this.name = name;
  }
  




  public String getName()
  {
    return name;
  }
  


  public int hashCode()
  {
    return name.hashCode();
  }
  


  public boolean equals(Object other)
  {
    if ((other instanceof BasicCommand)) {
      return name.equals(name);
    }
    
    return false;
  }
  


  public String toString()
  {
    return "[Command=" + name + "]";
  }
}
