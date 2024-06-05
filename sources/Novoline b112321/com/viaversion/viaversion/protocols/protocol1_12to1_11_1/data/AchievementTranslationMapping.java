package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.HashSet;
import java.util.Set;

public class AchievementTranslationMapping {
   private static final Object2ObjectOpenHashMap a = new Object2ObjectOpenHashMap(150, 1.0F);
   private static final Set SPECIAL_ACHIEVEMENTS = new HashSet(10);
   private static boolean c;

   private static void a(String var0, String var1) {
      a.put(var0, var1);
   }

   private static void addSpecial(String var0, String var1) {
      a(var0, var1);
      SPECIAL_ACHIEVEMENTS.add(var0);
   }

   public static String a(String var0) {
      return (String)a.get(var0);
   }

   public static boolean isSpecial(String var0) {
      return SPECIAL_ACHIEVEMENTS.contains(var0);
   }

   static {
      b(true);
      a("chat.type.achievement", "%s has just earned the achievement %s");
      a("chat.type.achievement.taken", "%s has lost the achievement %s");
      a("stats.tooltip.type.achievement", "Achievement");
      a("stats.tooltip.type.statistic", "Statistic");
      a("stat.generalButton", "General");
      a("stat.blocksButton", "Blocks");
      a("stat.itemsButton", "Items");
      a("stat.mobsButton", "Mobs");
      a("stat.used", "Times Used");
      a("stat.mined", "Times Mined");
      a("stat.depleted", "Times Depleted");
      a("stat.crafted", "Times Crafted");
      a("stat.entityKills", "You killed %s %s");
      a("stat.entityKilledBy", "%s killed you %s time(s)");
      a("stat.entityKills.none", "You have never killed %s");
      a("stat.entityKilledBy.none", "You have never been killed by %s");
      a("stat.startGame", "Times Played");
      a("stat.createWorld", "Worlds Created");
      a("stat.loadWorld", "Saves Loaded");
      a("stat.joinMultiplayer", "Multiplayer Joins");
      a("stat.leaveGame", "Games Quit");
      a("stat.playOneMinute", "Minutes Played");
      a("stat.timeSinceDeath", "Since Last Death");
      a("stat.sneakTime", "Sneak Time");
      a("stat.walkOneCm", "Distance Walked");
      a("stat.crouchOneCm", "Distance Crouched");
      a("stat.sprintOneCm", "Distance Sprinted");
      a("stat.fallOneCm", "Distance Fallen");
      a("stat.swimOneCm", "Distance Swum");
      a("stat.flyOneCm", "Distance Flown");
      a("stat.climbOneCm", "Distance Climbed");
      a("stat.diveOneCm", "Distance Dove");
      a("stat.minecartOneCm", "Distance by Minecart");
      a("stat.boatOneCm", "Distance by Boat");
      a("stat.pigOneCm", "Distance by Pig");
      a("stat.horseOneCm", "Distance by Horse");
      a("stat.aviateOneCm", "Distance by Elytra");
      a("stat.jump", "Jumps");
      a("stat.drop", "Items Dropped");
      a("stat.dropped", "Dropped");
      a("stat.pickup", "Picked Up");
      a("stat.damageDealt", "Damage Dealt");
      a("stat.damageTaken", "Damage Taken");
      a("stat.deaths", "Number of Deaths");
      a("stat.mobKills", "Mob Kills");
      a("stat.animalsBred", "Animals Bred");
      a("stat.playerKills", "Player Kills");
      a("stat.fishCaught", "Fish Caught");
      a("stat.treasureFished", "Treasure Fished");
      a("stat.junkFished", "Junk Fished");
      a("stat.talkedToVillager", "Talked to Villagers");
      a("stat.tradedWithVillager", "Traded with Villagers");
      a("stat.cakeSlicesEaten", "Cake Slices Eaten");
      a("stat.cauldronFilled", "Cauldrons Filled");
      a("stat.cauldronUsed", "Water Taken from Cauldron");
      a("stat.armorCleaned", "Armor Pieces Cleaned");
      a("stat.bannerCleaned", "Banners Cleaned");
      a("stat.brewingstandInteraction", "Interactions with Brewing Stand");
      a("stat.beaconInteraction", "Interactions with Beacon");
      a("stat.dropperInspected", "Droppers Searched");
      a("stat.hopperInspected", "Hoppers Searched");
      a("stat.dispenserInspected", "Dispensers Searched");
      a("stat.noteblockPlayed", "Note Blocks Played");
      a("stat.noteblockTuned", "Note Blocks Tuned");
      a("stat.flowerPotted", "Plants Potted");
      a("stat.trappedChestTriggered", "Trapped Chests Triggered");
      a("stat.enderchestOpened", "Ender Chests Opened");
      a("stat.itemEnchanted", "Items Enchanted");
      a("stat.recordPlayed", "Records Played");
      a("stat.furnaceInteraction", "Interactions with Furnace");
      a("stat.workbenchInteraction", "Interactions with Crafting Table");
      a("stat.chestOpened", "Chests Opened");
      a("stat.shulkerBoxOpened", "Shulker Boxes Opened");
      a("stat.sleepInBed", "Times Slept in a Bed");
      a("stat.mineBlock", "%1$s Mined");
      a("stat.craftItem", "%1$s Crafted");
      a("stat.useItem", "%1$s Used");
      a("stat.breakItem", "%1$s Depleted");
      a("achievement.get", "Achievement get!");
      a("achievement.taken", "Taken!");
      a("achievement.unknown", "???");
      a("achievement.requires", "Requires \'%1$s\'");
      a("achievement.openInventory", "Taking Inventory");
      a("achievement.openInventory.desc", "Press \'E\' to open your inventory");
      a("achievement.mineWood", "Getting Wood");
      a("achievement.mineWood.desc", "Attack a tree until a block of wood pops out");
      a("achievement.buildWorkBench", "Benchmarking");
      a("achievement.buildWorkBench.desc", "Craft a workbench with four blocks of planks");
      a("achievement.buildPickaxe", "Time to Mine!");
      a("achievement.buildPickaxe.desc", "Use planks and sticks to make a pickaxe");
      a("achievement.buildFurnace", "Hot Topic");
      a("achievement.buildFurnace.desc", "Construct a furnace out of eight cobblestone blocks");
      a("achievement.acquireIron", "Acquire Hardware");
      a("achievement.acquireIron.desc", "Smelt an iron ingot");
      a("achievement.buildHoe", "Time to Farm!");
      a("achievement.buildHoe.desc", "Use planks and sticks to make a hoe");
      a("achievement.makeBread", "Bake Bread");
      a("achievement.makeBread.desc", "Turn wheat into bread");
      a("achievement.bakeCake", "The Lie");
      a("achievement.bakeCake.desc", "Wheat, sugar, milk and eggs!");
      a("achievement.buildBetterPickaxe", "Getting an Upgrade");
      a("achievement.buildBetterPickaxe.desc", "Construct a better pickaxe");
      addSpecial("achievement.overpowered", "Overpowered");
      a("achievement.overpowered.desc", "Eat a Notch apple");
      a("achievement.cookFish", "Delicious Fish");
      a("achievement.cookFish.desc", "Catch and cook fish!");
      addSpecial("achievement.onARail", "On A Rail");
      a("achievement.onARail.desc", "Travel by minecart at least 1 km from where you started");
      a("achievement.buildSword", "Time to Strike!");
      a("achievement.buildSword.desc", "Use planks and sticks to make a sword");
      a("achievement.killEnemy", "Monster Hunter");
      a("achievement.killEnemy.desc", "Attack and destroy a monster");
      a("achievement.killCow", "Cow Tipper");
      a("achievement.killCow.desc", "Harvest some leather");
      a("achievement.breedCow", "Repopulation");
      a("achievement.breedCow.desc", "Breed two cows with wheat");
      addSpecial("achievement.flyPig", "When Pigs Fly");
      a("achievement.flyPig.desc", "Fly a pig off a cliff");
      addSpecial("achievement.snipeSkeleton", "Sniper Duel");
      a("achievement.snipeSkeleton.desc", "Kill a skeleton with an arrow from more than 50 meters");
      a("achievement.diamonds", "DIAMONDS!");
      a("achievement.diamonds.desc", "Acquire diamonds with your iron tools");
      a("achievement.diamondsToYou", "Diamonds to you!");
      a("achievement.diamondsToYou.desc", "Throw diamonds at another player");
      a("achievement.portal", "We Need to Go Deeper");
      a("achievement.portal.desc", "Build a portal to the Nether");
      addSpecial("achievement.ghast", "Return to Sender");
      a("achievement.ghast.desc", "Destroy a Ghast with a fireball");
      a("achievement.blazeRod", "Into Fire");
      a("achievement.blazeRod.desc", "Relieve a Blaze of its rod");
      a("achievement.potion", "Local Brewery");
      a("achievement.potion.desc", "Brew a potion");
      addSpecial("achievement.theEnd", "The End?");
      a("achievement.theEnd.desc", "Locate the End");
      addSpecial("achievement.theEnd2", "The End.");
      a("achievement.theEnd2.desc", "Defeat the Ender Dragon");
      a("achievement.spawnWither", "The Beginning?");
      a("achievement.spawnWither.desc", "Spawn the Wither");
      a("achievement.killWither", "The Beginning.");
      a("achievement.killWither.desc", "Kill the Wither");
      addSpecial("achievement.fullBeacon", "Beaconator");
      a("achievement.fullBeacon.desc", "Create a full beacon");
      addSpecial("achievement.exploreAllBiomes", "Adventuring Time");
      a("achievement.exploreAllBiomes.desc", "Discover all biomes");
      a("achievement.enchantments", "Enchanter");
      a("achievement.enchantments.desc", "Use a book, obsidian and diamonds to construct an enchantment table");
      addSpecial("achievement.overkill", "Overkill");
      a("achievement.overkill.desc", "Deal nine hearts of damage in a single hit");
      a("achievement.bookcase", "Librarian");
      a("achievement.bookcase.desc", "Build some bookshelves to improve your enchantment table");
   }

   public static void b(boolean var0) {
      c = var0;
   }

   public static boolean b() {
      return c;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }
}
