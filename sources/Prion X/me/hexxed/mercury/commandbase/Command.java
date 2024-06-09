package me.hexxed.mercury.commandbase;

public class Command
{
  private String name;
  private String usage;
  
  public Command(String name, String usage) {
    this.name = name;
    this.usage = usage;
  }
  
  public String getName() {
    return name;
  }
  
  public String getUsage() {
    return "§4." + usage;
  }
  
  public void execute(String[] args) {}
}
