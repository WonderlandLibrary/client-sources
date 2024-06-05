package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.events.EventManager;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.utils.Timer;
import cc.novoline.utils.notifications.NotificationType;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntPredicate;
import net.Co;
import net.a_E;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandException;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;

public class FindBountyCommand extends NovoCommand {
   private int j;
   private int n;
   private boolean k;
   private Timer l = new Timer();
   private Minecraft m = Minecraft.getInstance();

   public FindBountyCommand(@NotNull Novoline var1) {
      super(var1, "FindBounty", "Finds the bounty", (Iterable)Arrays.asList(new String[]{"fb", "findbounty"}));
   }

   public void process(String[] var1) throws CommandException, IOException {
      int[] var2 = a_E.b();
      if(var1.length == 0) {
         this.sendUsage("Find Bounty", ".fb <amountMin>-<amountMax>");
      } else if(var1.length != 1) {
         this.sendUsage("Find Bounty", ".fb <amountMin>-<amountMax>");
      } else if(var1[0].equalsIgnoreCase("stop")) {
         EventManager.unregister(this);
      } else {
         String[] var3 = var1[0].split("-");
         this.j = Integer.parseInt(var3[0]);
         this.n = Integer.parseInt(var3[1]);
         if(this.j > this.n) {
            this.sendUsage("Find Bounty", "The minimum amount is greater than the maximum amount!");
         } else {
            this.notify("Trying to find the bounty.");
            this.l.reset();
            if(!this.a()) {
               this.notify("Swapping servers...");
               EventManager.register(this);
            }

         }
      }
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int[] var2 = a_E.b();
      if(var1.getState() == EventState.PRE && this.l.delay(1000.0D)) {
         this.l.reset();
         this.m.getNetHandler().sendPacketNoEvent(new C01PacketChatMessage("/play pit"));
         this.a();
      }

   }

   private boolean a() {
      a_E.b();
      Iterator var2 = this.m.ingameGUI.a().c().iterator();
      if(var2.hasNext()) {
         NetworkPlayerInfo var3 = (NetworkPlayerInfo)var2.next();
         if(var3 != null) {
            String var4 = ScorePlayerTeam.formatPlayerName(var3.getPlayerTeam(), var3.getGameProfile().getName());
            String[] var5 = var4.split(" ");
            String var6 = var5[var5.length - 1];
            if(var6.endsWith("g") && var6.contains("§")) {
               String var7 = var6.replace("g", "");
               long var8 = var7.chars().filter(FindBountyCommand::lambda$findBounty$0).count();
               var7 = var7.substring((int)(var8 * 2L));
               int var10 = Integer.parseInt(var7);
               if(var10 >= this.j && var10 <= this.n) {
                  if(!this.a(var3) || this.b(var3)) {
                     this.notifyClient("Found a bountied player!", "Found " + EnumChatFormatting.GOLD + var10 + "g" + EnumChatFormatting.RESET + " on " + var3.getGameProfile().getName() + "!", 5000, NotificationType.INFO);
                     this.novoline.getPlayerManager().setType(var3.getGameProfile().getName(), PlayerManager$EnumPlayerType.TARGET);
                     this.notify("Added " + var3.getGameProfile().getName() + " to targets!");
                     int var11 = 0;
                     if(var11 < 20) {
                        this.novoline.getTaskManager().queue(new Co(this, 100 * var11));
                        ++var11;
                     }

                     EventManager.unregister(this);
                  }

                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean a(NetworkPlayerInfo var1) {
      a_E.b();
      Iterator var3 = this.m.world.getLoadedEntityList().iterator();
      if(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if(var4.getUniqueID() == var1.getGameProfile().getId()) {
            return true;
         }
      }

      return false;
   }

   private boolean b(NetworkPlayerInfo var1) {
      a_E.b();
      Iterator var3 = this.m.world.getLoadedEntityList().iterator();
      if(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if(var4.getUniqueID() == var1.getGameProfile().getId()) {
            if(Math.abs(this.m.player.posY - var4.posY) > 5.0D) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   private static boolean lambda$findBounty$0(int var0) {
      int[] var1 = a_E.b();
      return var0 == 167;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
