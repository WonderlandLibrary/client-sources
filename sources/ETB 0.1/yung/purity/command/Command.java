package yung.purity.command;

import yung.purity.utils.Helper;





public abstract class Command
{
  private String name;
  private String[] alias;
  private String syntax;
  private String help;
  
  public Command(String name, String[] alias, String syntax, String help)
  {
    this.name = name.toLowerCase();
    this.syntax = syntax.toLowerCase();
    this.help = help;
    this.alias = alias;
  }
  

  public abstract String execute(String[] paramArrayOfString);
  
  public String getName()
  {
    return name;
  }
  
  public String[] getAlias() {
    return alias;
  }
  
  public String getSyntax() {
    return syntax;
  }
  
  public String getHelp() {
    return help;
  }
  
  public void syntaxError(String msg)
  {
    Helper.sendMessage(String.format("bad syntax", new Object[] { msg }));
  }
  
  public void syntaxError(byte errorType)
  {
    switch (errorType) {
    case 0: 
      syntaxError("bad argument");
      break;
    
    case 1: 
      syntaxError("argument gay");
    }
  }
}
