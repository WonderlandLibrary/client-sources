package winter.module.modules;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockNote;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.Module.Category;

public class NoteBot
  extends Module
{
  private Binding binding = new Binding();
  public static int note;
  public static List<Integer> notes = new ArrayList();
  public static GroovyShell groovyShell;
  boolean extraLook;
  boolean repeat;
  
  public NoteBot()
  {
    super("Note Bot", Module.Category.Other, -13970566);
    setBind(49);
    notes = new ArrayList();
    note = 0;
    this.repeat = false;
    this.extraLook = true;
    this.binding.setProperty("play", new PlayNoteFunction());
    this.binding.setProperty("rest", new RestFunction());
    this.binding.setProperty("chord", new ChordFunction());
    groovyShell = new GroovyShell(this.binding);
  }
  
  public void onEnable()
  {
    note = 0;
  }
  
  @EventListener
  public void onUpdate(UpdateEvent event)
  {
    if (event.isPre())
    {
      if (notes.isEmpty()) {
        return;
      }
      try
      {
        int noteToPlay = ((Integer)notes.get(note)).intValue();
        if (noteToPlay == -1) {
          return;
        }
        int startX = (int)Math.floor(this.mc.thePlayer.posX) - 2;
        int startY = (int)Math.floor(this.mc.thePlayer.posY) - 1;
        int startZ = (int)Math.floor(this.mc.thePlayer.posZ) - 2;
        int x = startX + noteToPlay % 5;
        int z = startZ + noteToPlay / 5;
        float[] values = BlockHelper.getFacingRotations(x, startY, z, BlockHelper.getFacing(new BlockPos(x, startY, z)));
        event.yaw = values[0];
        event.pitch = values[1];
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
    }
    else if (!event.isPre())
    {
      int loops = 0;
      if (notes.isEmpty()) {
        return;
      }
      for (;;)
      {
        if (note >= notes.size()) {
          note = 0;
        }
        try
        {
          int noteToPlay2 = ((Integer)notes.get(note)).intValue();
          note += 1;
          if (noteToPlay2 == -1) {
            return;
          }
          int startX2 = (int)Math.floor(this.mc.thePlayer.posX) - 2;
          int startY2 = (int)Math.floor(this.mc.thePlayer.posY) - 1;
          int startZ2 = (int)Math.floor(this.mc.thePlayer.posZ) - 2;
          int x2 = startX2 + noteToPlay2 % 5;
          int z2 = startZ2 + noteToPlay2 / 5;
          BlockPos pos = new BlockPos(x2, startY2, z2);
          if ((this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockNote))
          {
            if ((this.repeat) && (loops != 0))
            {
              float[] values2 = BlockHelper.getFacingRotations(pos.getX(), pos.getY(), pos.getZ(), BlockHelper.getFacing(pos));
              this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(values2[0], values2[1], this.mc.thePlayer.onGround));
            }
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, BlockHelper.getFacing(pos)));
            this.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos, BlockHelper.getFacing(pos)));
          }
          loops++;
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException1) {}
      }
    }
  }
  
  private class ChordFunction
  {
    private ChordFunction() {}
    
    private void play(int i)
    {
      NoteBot.notes.add(Integer.valueOf(i));
    }
    
    public void call(int i)
    {
      switch (i)
      {
      case 1: 
        play(1);
        play(5);
        play(8);
        break;
      case 2: 
        play(2);
        play(6);
        play(9);
        break;
      case 3: 
        play(3);
        play(6);
        play(10);
        break;
      case 4: 
        play(4);
        play(6);
        play(10);
        break;
      case 5: 
        play(5);
        play(7);
        play(12);
        break;
      case 6: 
        play(6);
        play(10);
        play(13);
        break;
      case 7: 
        play(7);
        play(10);
        play(14);
        break;
      case 8: 
        play(8);
        play(12);
        play(15);
        break;
      case 9: 
        play(9);
        play(13);
        play(16);
        break;
      case 10: 
        play(10);
        play(13);
        play(17);
        break;
      case 11: 
        play(11);
        play(15);
        play(18);
        break;
      case 12: 
        play(12);
        play(16);
        play(19);
        break;
      case 13: 
        play(13);
        play(17);
        play(20);
      }
    }
  }
  
  private class PlayNoteFunction
  {
    private PlayNoteFunction() {}
    
    public void call(int i)
    {
      NoteBot.notes.add(Integer.valueOf(i));
    }
  }
  
  private class RestFunction
  {
    private RestFunction() {}
    
    public void call(int integer)
    {
      for (int i = 0; i < integer; i++) {
        NoteBot.notes.add(Integer.valueOf(-1));
      }
    }
  }
}
