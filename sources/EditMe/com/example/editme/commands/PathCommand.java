package com.example.editme.commands;

import com.example.editme.modules.render.Pathfind;
import com.example.editme.util.command.syntax.ChunkBuilder;
import net.minecraft.pathfinding.PathPoint;

public class PathCommand extends Command {
   int x = Integer.MIN_VALUE;
   int z = Integer.MIN_VALUE;
   int y = Integer.MIN_VALUE;

   public PathCommand() {
      super("path", (new ChunkBuilder()).append("x").append("y").append("z").build());
   }

   public void call(String[] var1) {
      PathPoint var2;
      if (var1[0] != null && var1[0].equalsIgnoreCase("retry")) {
         if (this.x != Integer.MIN_VALUE) {
            var2 = new PathPoint(this.x, this.y, this.z);
            Pathfind.createPath(var2);
            if (!Pathfind.points.isEmpty()) {
               Command.sendChatMessage("Path created!");
            }

         } else {
            Command.sendChatMessage("No location to retry pathfinding to.");
         }
      } else if (var1.length <= 3) {
         Command.sendChatMessage("&cMissing arguments: x, y, z");
      } else {
         try {
            this.x = Integer.parseInt(var1[0]);
            this.y = Integer.parseInt(var1[1]);
            this.z = Integer.parseInt(var1[2]);
            var2 = new PathPoint(this.x, this.y, this.z);
            Pathfind.createPath(var2);
            if (!Pathfind.points.isEmpty()) {
               Command.sendChatMessage("Path created!");
            }

         } catch (NumberFormatException var3) {
            Command.sendChatMessage("Error: input must be numerical");
         }
      }
   }
}
