package net.minecraft.init;

import java.io.PrintStream;
import net.au2;
import net.auY;
import net.oO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap$1;
import net.minecraft.init.Bootstrap$10;
import net.minecraft.init.Bootstrap$11;
import net.minecraft.init.Bootstrap$12;
import net.minecraft.init.Bootstrap$13;
import net.minecraft.init.Bootstrap$16;
import net.minecraft.init.Bootstrap$2;
import net.minecraft.init.Bootstrap$3;
import net.minecraft.init.Bootstrap$4;
import net.minecraft.init.Bootstrap$6;
import net.minecraft.init.Bootstrap$7;
import net.minecraft.init.Bootstrap$8;
import net.minecraft.init.Bootstrap$9;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.LoggingPrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Bootstrap {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final PrintStream SYSOUT = System.out;
   private static boolean alreadyRegistered = false;

   public static boolean isRegistered() {
      return alreadyRegistered;
   }

   static void registerDispenserBehaviors() {
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new Bootstrap$1());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new Bootstrap$2());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new Bootstrap$3());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new Bootstrap$4());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new oO());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new Bootstrap$6());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new Bootstrap$7());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new Bootstrap$8());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new Bootstrap$9());
      Bootstrap$10 var0 = new Bootstrap$10();
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, var0);
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, var0);
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new Bootstrap$11());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new Bootstrap$12());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new Bootstrap$13());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new auY());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Items.skull, new au2());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.pumpkin), new Bootstrap$16());
   }

   public static void register() {
      if(!alreadyRegistered) {
         alreadyRegistered = true;
         if(LOGGER.isDebugEnabled()) {
            redirectOutputToLog();
         }

         Block.registerBlocks();
         BlockFire.init();
         Item.registerItems();
         StatList.init();
         registerDispenserBehaviors();
      }

   }

   private static void redirectOutputToLog() {
      System.setErr(new LoggingPrintStream("STDERR", System.err));
      System.setOut(new LoggingPrintStream("STDOUT", SYSOUT));
   }

   public static void printToSYSOUT(String var0) {
      SYSOUT.println(var0);
   }
}
