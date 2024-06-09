package lunadevs.luna.friend;

public class Friend
{
	  public String name;
	  private String username;
	  public String alias;
	  
	  public Friend(String name, String alias)
	  {
	    this.name = name;
	    this.alias = alias;
	  }
	  
	  public void setAlias(String alias)
	  {
	    this.alias = alias;
	  }
	  
	  public String getAlias()
	  {
	    return this.alias;
	  }
	  

	  public String getUsername()
	  {
	    return this.username;
	  }
}
