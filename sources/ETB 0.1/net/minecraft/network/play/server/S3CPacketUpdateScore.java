package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class S3CPacketUpdateScore implements Packet
{
  private String name = "";
  private String objective = "";
  private int value;
  private Action action;
  private static final String __OBFID = "CL_00001335";
  
  public S3CPacketUpdateScore() {}
  
  public S3CPacketUpdateScore(Score scoreIn)
  {
    name = scoreIn.getPlayerName();
    objective = scoreIn.getObjective().getName();
    value = scoreIn.getScorePoints();
    action = Action.CHANGE;
  }
  
  public S3CPacketUpdateScore(String nameIn)
  {
    name = nameIn;
    objective = "";
    value = 0;
    action = Action.REMOVE;
  }
  
  public S3CPacketUpdateScore(String nameIn, ScoreObjective objectiveIn)
  {
    name = nameIn;
    objective = objectiveIn.getName();
    value = 0;
    action = Action.REMOVE;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    name = data.readStringFromBuffer(40);
    action = ((Action)data.readEnumValue(Action.class));
    objective = data.readStringFromBuffer(16);
    
    if (action != Action.REMOVE)
    {
      value = data.readVarIntFromBuffer();
    }
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeString(name);
    data.writeEnumValue(action);
    data.writeString(objective);
    
    if (action != Action.REMOVE)
    {
      data.writeVarIntToBuffer(value);
    }
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleUpdateScore(this);
  }
  
  public String func_149324_c()
  {
    return name;
  }
  
  public String func_149321_d()
  {
    return objective;
  }
  
  public int func_149323_e()
  {
    return value;
  }
  
  public Action func_180751_d()
  {
    return action;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
  
  public static enum Action
  {
    CHANGE("CHANGE", 0), 
    REMOVE("REMOVE", 1);
    
    private static final Action[] $VALUES = { CHANGE, REMOVE };
    private static final String __OBFID = "CL_00002288";
    
    private Action(String p_i45957_1_, int p_i45957_2_) {}
  }
}
