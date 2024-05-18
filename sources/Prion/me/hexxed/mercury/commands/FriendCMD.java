package me.hexxed.mercury.commands;

import me.hexxed.mercury.util.Friends;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class FriendCMD extends me.hexxed.mercury.commandbase.Command
{
  public FriendCMD()
  {
    super("friend", ".friend <add/remove/list/radd/rremove/ra/alias> [name] [alias]");
  }
  
  public void execute(String[] args) {
    String s;
    if (args.length == 1) { String str1;
      switch ((str1 = args[0]).hashCode()) {case 3631:  if (str1.equals("ra")) break; break; case 3322014:  if (!str1.equals("list"))
        {
          break label273;
          for (String s : Friends.getFriends().keySet()) {
            Friends.removeFriends(s);
          }
          Util.sendInfo("Removed all friends. So sad :(");
          break label280;
        }
        else {
          boolean b = true;
          StringBuilder sb = new StringBuilder();
          sb.append("List of Friends:");
          for (java.util.Iterator localIterator2 = Friends.getFriends().keySet().iterator(); localIterator2.hasNext();) { s = (String)localIterator2.next();
            if (!b) {
              sb.append(" §7,§b" + s + " §6(" + Friends.getAlias(s) + ")");
            } else {
              sb.append(" §b" + s + " §6(" + Friends.getAlias(s) + ")");
              b = false;
            }
          }
          Util.sendInfo(sb.toString()); }
        break;
      }
      label273:
      Util.addChatMessage(getUsage());
      
      label280:
      
      return; }
    Integer radius;
    if (args.length == 2) {
      radius = Integer.valueOf(0);
      Integer amount = Integer.valueOf(0);
      switch ((s = args[0]).hashCode()) {case -934610812:  if (s.equals("remove")) {} break; case 96417:  if (s.equals("add")) break; break; case 3492591:  if (s.equals("radd")) {} break; case 1456561014:  if (!s.equals("rremove"))
        {
          break label824;
          if (Friends.addFriend(args[1], args[1])) {
            Util.sendInfo("Added §b" + args[1] + " §7to your friendlist");
            break label831; }
          Util.sendInfo("Friend is already on your friendlist");
          

          break label831;
          
          if (Friends.removeFriends(args[1])) {
            Util.sendInfo("Removed §b" + args[1] + " §7from your friendlist");
            break label831; }
          Util.sendInfo("Friend is not on your friendlist");
          

          break label831;
          
          try
          {
            radius = Integer.valueOf(Integer.parseInt(args[1]));
          } catch (Exception e) {
            Util.sendInfo("Argument isn't a number!");
            return;
          }
          for (EntityPlayer e : getMinecrafttheWorld.playerEntities) {
            if ((getMinecraftthePlayer.getDistanceToEntity(e) < radius.intValue()) && (!Friends.isFriend(e.getName())) && (e != getMinecraftthePlayer)) {
              Friends.addFriend(e.getName(), e.getName());
              amount = Integer.valueOf(amount.intValue() + 1);
            }
          }
          Util.sendInfo("Added " + String.valueOf(amount) + " friends");
          amount = Integer.valueOf(0);
          break label831;
        }
        else {
          try {
            radius = Integer.valueOf(Integer.parseInt(args[1]));
          } catch (Exception e) {
            Util.sendInfo("Argument isn't a number!");
            return;
          }
          for (EntityPlayer e : getMinecrafttheWorld.playerEntities) {
            if ((getMinecraftthePlayer.getDistanceToEntity(e) < radius.intValue()) && (Friends.isFriend(e.getName())) && (e != getMinecraftthePlayer)) {
              Friends.removeFriends(e.getName());
              amount = Integer.valueOf(amount.intValue() + 1);
            }
          }
          Util.sendInfo("Removed " + String.valueOf(amount) + " friends");
          amount = Integer.valueOf(0); }
        break;
      }
      label824:
      Util.addChatMessage(getUsage());
      
      label831:
      
      return;
    }
    if (args.length != 3) {
      Util.addChatMessage(getUsage());
      return;
    }
    switch ((radius = args[0]).hashCode()) {case -934610812:  if (radius.equals("remove")) {} break; case 96417:  if (radius.equals("add")) break; break; case 92902992:  if (!radius.equals("alias"))
      {
        break label1085;
        if (Friends.addFriend(args[1], args[2])) {
          Util.sendInfo("Added §b" + args[1] + " §7with alias §6" + args[2] + " §7to your friendlist");
          return; }
        Util.sendInfo("Friend is already on your friendlist");
        
        return;
        

        if (Friends.removeFriends(args[1])) {
          Util.sendInfo("Removed §b" + args[1] + " §7from your friendlist");
          return; }
        Util.sendInfo("Friend is not on your friendlist");
        
        return;

      }
      else if (Friends.setAlias(args[1], args[2])) {
        Util.sendInfo("Alias of §b" + args[1] + "§7 set to§6 " + args[2]);
      } else {
        Util.sendInfo("Friend is not on your friendlist");
      }
      break;
    }
    label1085:
    Util.addChatMessage(getUsage());
  }
}
