package xyz.mathax.mathaxclient.gui.tabs.builtin;

import baritone.api.BaritoneAPI;
import baritone.api.utils.SettingsUtil;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.tabs.Tab;
import xyz.mathax.mathaxclient.gui.tabs.TabScreen;
import xyz.mathax.mathaxclient.gui.tabs.WindowTabScreen;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.utils.misc.BaritoneSettingValue;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaritoneTab extends Tab {
    private static Settings settings;

    private static Map<String, BaritoneSettingValue> settingValues;

    public BaritoneTab() {
        super("Baritone");
    }

    private static Settings getSettings() {
        if (settings != null) {
            return settings;
        }

        settings = new Settings();

        SettingGroup boolSettings = settings.createGroup("Checkboxes");
        SettingGroup doubleSettings = settings.createGroup("Numbers");
        SettingGroup itnSettings = settings.createGroup("Whole Numbers");
        SettingGroup stringSettings = settings.createGroup("Strings");
        SettingGroup colorSettings = settings.createGroup("Colors");
        SettingGroup blockListSettings = settings.createGroup("Block Lists");
        SettingGroup itemListSettings = settings.createGroup("Item Lists");

        try {
            Class<? extends baritone.api.Settings> klass = BaritoneAPI.getSettings().getClass();
            for (Field field : klass.getDeclaredFields()) {
                Object object = field.get(BaritoneAPI.getSettings());
                if (!(object instanceof baritone.api.Settings.Setting setting)) {
                    continue;
                }

                Object value = setting.value;
                if (value instanceof Boolean) {
                    boolSettings.add(new BoolSetting.Builder()
                        .name(getName(setting.getName()))
                        .description(getDescription(setting.getName()))
                        .defaultValue((boolean) setting.defaultValue)
                        .onChanged(aBoolean -> setting.value = aBoolean)
                        .onModuleEnabled(booleanSetting -> booleanSetting.set((Boolean) setting.value))
                        .build()
                    );
                } else if (value instanceof Double) {
                    doubleSettings.add(new DoubleSetting.Builder()
                        .name(getName(setting.getName()))
                        .description(getDescription(setting.getName()))
                        .defaultValue((double) setting.defaultValue)
                        .onChanged(aDouble -> setting.value = aDouble)
                        .onModuleEnabled(doubleSetting -> doubleSetting.set((Double) setting.value))
                        .build()
                    );
                } else if (value instanceof Float) {
                    doubleSettings.add(new DoubleSetting.Builder()
                        .name(getName(setting.getName()))
                        .description(getDescription(setting.getName()))
                        .defaultValue(((Float) setting.defaultValue).doubleValue())
                        .onChanged(aDouble -> setting.value = aDouble.floatValue())
                        .onModuleEnabled(doubleSetting -> doubleSetting.set(((Float) setting.value).doubleValue()))
                        .build()
                    );
                } else if (value instanceof Integer) {
                    itnSettings.add(new IntSetting.Builder()
                        .name(getName(setting.getName()))
                        .description(getDescription(setting.getName()))
                        .defaultValue((int) setting.defaultValue)
                        .onChanged(integer -> setting.value = integer)
                        .onModuleEnabled(integerSetting -> integerSetting.set((Integer) setting.value))
                        .build()
                    );
                } else if (value instanceof Long) {
                    itnSettings.add(new IntSetting.Builder()
                        .name(getName(setting.getName()))
                        .description(getDescription(setting.getName()))
                        .defaultValue(((Long) setting.defaultValue).intValue())
                        .onChanged(integer -> setting.value = integer.longValue())
                        .onModuleEnabled(integerSetting -> integerSetting.set(((Long) setting.value).intValue()))
                        .build()
                    );
                } else if (value instanceof String) {
                    stringSettings.add(new StringSetting.Builder()
                            .name(getName(setting.getName()))
                            .description(getDescription(setting.getName()))
                            .defaultValue((String) setting.defaultValue)
                            .onChanged(string -> setting.value = string)
                            .onModuleEnabled(stringSetting -> stringSetting.set((String) setting.value))
                            .build()
                    );
                } else if (value instanceof Color) {
                    Color color = (Color) setting.value;
                    colorSettings.add(new ColorSetting.Builder()
                        .name(getName(setting.getName()))
                        .description(getDescription(setting.getName()))
                        .defaultValue(new SettingColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()))
                        .onChanged(colorValue -> setting.value = new Color(colorValue.r, colorValue.g, colorValue.b, colorValue.a))
                        .onModuleEnabled(colorSetting -> colorSetting.set(new SettingColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())))
                        .build()
                    );
                } else if (value instanceof List) {
                    Type listType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    Type type = ((ParameterizedType) listType).getActualTypeArguments()[0];
                    if (type == Block.class) {
                        blockListSettings.add(new BlockListSetting.Builder()
                                .name(getName(setting.getName()))
                                .description(getDescription(setting.getName()))
                                .defaultValue((List<Block>) setting.defaultValue)
                                .onChanged(blockList -> setting.value = blockList)
                                .onModuleEnabled(blockListSetting -> blockListSetting.set((List<Block>) setting.value))
                                .build()
                        );
                    } else if (type == Item.class) {
                        itemListSettings.add(new ItemListSetting.Builder()
                                .name(getName(setting.getName()))
                                .description(getDescription(setting.getName()))
                                .defaultValue((List<Item>) setting.defaultValue)
                                .onChanged(itemList -> setting.value = itemList)
                                .onModuleEnabled(itemListSetting -> itemListSetting.set((List<Item>) setting.value))
                                .build()
                        );
                    }
                }
            }
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return settings;
    }

    @Override
    public TabScreen createScreen(Theme theme) {
        return new BaritoneScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof BaritoneScreen;
    }

    private static void addSettingValue(String settingName, String name, String description) {
        settingValues.put(settingName, new BaritoneSettingValue(name, description));
    }

    private static String getName(String settingName) {
        if (settingValues == null) {
            loadSettingValues();
        }

        return settingValues.get(settingName).name();
    }
    
    private static String getDescription(String settingName) {
        if (settingValues == null) {
            loadSettingValues();
        }

        return settingValues.get(settingName).description();
    }

    private static class BaritoneScreen extends WindowTabScreen {
        public BaritoneScreen(Theme theme, Tab tab) {
            super(theme, tab);

            getSettings().onEnabled();
        }

        @Override
        public void initWidgets() {
            WTextBox filter = add(theme.textBox("")).minWidth(400).expandX().widget();
            filter.setFocused(true);
            filter.action = () -> {
                clear();

                add(filter);
                add(theme.settings(getSettings(), filter.get().trim())).expandX();
            };

            add(theme.settings(getSettings(), filter.get().trim())).expandX();
        }

        @Override
        protected void onClosed() {
            SettingsUtil.save(BaritoneAPI.getSettings());
        }
    }

    private static void loadSettingValues() {
        settingValues = new HashMap<>();

        addSettingValue("acceptableThrowawayItems", "Acceptable throwaway items", "Blocks that Baritone is allowed to place (as throwaway, for sneak bridging, pillaring, etc.)");
        addSettingValue("allowBreak", "Allow break", "Allow Baritone to break blocks.");
        addSettingValue("allowBreakAnyway", "Allow break anyway", "Blocks that baritone will be allowed to break even with allowBreak set to false.");
        addSettingValue("allowDiagonalAscend", "Allow diagonal ascend", "Allow diagonal ascending.");
        addSettingValue("allowDiagonalDescend", "Allow diagonal descend", "Allow descending diagonally.");
        addSettingValue("allowDownward", "Allow downward", "Allow mining the block directly beneath its feet.");
        addSettingValue("allowInventory", "Allow inventory", "Allow Baritone to move items in your inventory to your hotbar.");
        addSettingValue("allowJumpAt256", "Allow jump at 256", "If true, parkour is allowed to make jumps when standing on blocks at the maximum height, so player feet is y=256.");
        addSettingValue("allowOnlyExposedOres", "Allow only exposed ores", "This will only allow baritone to mine exposed ores, can be used to stop ore obfuscators on servers that use them.");
        addSettingValue("allowOnlyExposedOresDistance", "Allow only exposed ores distance", "When allowOnlyExposedOres is enabled this is the distance around to search.");
        addSettingValue("allowOvershootDiagonalDescend","Allow overshoot diagonal descend", "Is it okay to sprint through a descend followed by a diagonal? The player overshoots the landing, but not enough to fall off.");
        addSettingValue("allowParkour", "Allow parkour", "You know what it is.");
        addSettingValue("allowParkourAscend", "Allow parkour ascend", "This should be monetized it's so good.");
        addSettingValue("allowParkourPlace", "Allow parkour place", "Actually pretty reliable.");
        addSettingValue("allowPlace", "Allow place", "Allow Baritone to place blocks.");
        addSettingValue("allowSprint", "Allow sprint", "Allow Baritone to sprint.");
        addSettingValue("allowVines", "Allow vines", "Enables some more advanced vine features.");
        addSettingValue("allowWalkOnBottomSlab", "Allow walk on bottom slab", "Slab behavior is complicated, disable this for higher path reliability.");
        addSettingValue("allowWaterBucketFall", "Allow water bucket fall", "Allow Baritone to fall arbitrary distances and place a water bucket beneath it.");
        addSettingValue("antiCheatCompatibility", "Anti-Cheat compatibility", "Will cause some minor behavioral differences to ensure that Baritone works on anti-cheats.");
        addSettingValue("assumeExternalAutoTool", "Assume external auto tool", "Disable baritone's auto-tool at runtime, but still assume that another mod will provide auto tool functionality.");
        addSettingValue("assumeSafeWalk", "Assume safe walk", "Assume safe walk functionality; don't sneak on a backplace traverse.");
        addSettingValue("assumeStep", "Assume step", "Assume step functionality; don't jump on an ascend.");
        addSettingValue("assumeWalkOnLava", "Assume walk on lava", "Allow Baritone to assume it can walk on still lava just like any other block.");
        addSettingValue("assumeWalkOnWater", "Assume walk on water", "Allow Baritone to assume it can walk on still water just like any other block.");
        addSettingValue("autoTool", "Auto tool", "Automatically select the best available tool.");
        addSettingValue("avoidance", "avoidance", "Toggle the following 4 settings.");
        addSettingValue("avoidBreakingMultiplier", "Avoid breaking multiplier", "This multiplies the break speed, if set above 1 it's \"encourage breaking\" instead.");
        addSettingValue("avoidUpdatingFallingBlocks", "Avoid updating falling blocks", "If this setting is true, Baritone will never break a block that is adjacent to an unsupported falling block.");
        addSettingValue("axisHeight", "Axis height", "The \"axis\" command (aka Goal Axis) will go to a axis, or diagonal axis, at this Y level.");
        addSettingValue("backfill", "Backfill", "Fill in blocks behind you.");
        addSettingValue("backtrackCostFavoringCoefficient", "Backtrack cost favoring coefficient", "Set to 1.0 to effectively disable this feature.");
        addSettingValue("blacklistClosestOnFailure", "Blacklist closest on failure", "When GetToBlockProcess or MineProcess fails to calculate a path, instead of just giving up, mark the closest instance of that block as \"unreachable\" and go towards the next closest.");
        addSettingValue("blockBreakAdditionalPenalty", "Block break additional penalty", "This is just a tiebreaker to make it less likely to break blocks if it can avoid it.");
        addSettingValue("blockPlacementPenalty", "Block placement penalty", "It doesn't actually take twenty ticks to place a block, this cost is so high because we want to generally conserve blocks which might be limited.");
        addSettingValue("blockReachDistance", "Block reach distance", "Block reach distance.");
        addSettingValue("blocksToAvoid", "Blocks to avoid", "Blocks that Baritone will attempt to avoid (used in avoidance).");
        addSettingValue("blocksToAvoidBreaking", "Blocks to avoid breaking", "blocks that baritone shouldn't break, but can if it needs to.");
        addSettingValue("blocksToDisallowBreaking", "Blocks to disallow breaking", "Blocks that Baritone is not allowed to break.");
        addSettingValue("breakCorrectBlockPenaltyMultiplier", "Break correct block penalty multiplier", "Multiply the cost of breaking a block that's correct in the builder's schematic by this coefficient.");
        addSettingValue("breakFromAbove", "Break from above", "Allow standing above a block while mining it, in Builder Process.");
        addSettingValue("builderTickScanRadius", "Builder tick scan radius", "Distance to scan every tick for updates.");
        addSettingValue("buildIgnoreBlocks", "Build ignore blocks", "A list of blocks to be treated as if they're air.");
        addSettingValue("buildIgnoreDirection", "Build ignore direction", "If this is true, the builder will ignore directionality of certain blocks like glazed terracotta.");
        addSettingValue("buildIgnoreExisting", "Build ignore existing", "If this is true, the builder will treat all non-air blocks as correct.");
        addSettingValue("buildInLayers", "Build in layers", "Don't consider the next layer in builder until the current one is done.");
        addSettingValue("buildOnlySelection", "Build only selection", "Only build the selected part of schematics.");
        addSettingValue("buildRepeat", "Build repeat", "How far to move before repeating the build.");
        addSettingValue("buildRepeatCount", "Build repeat count", "How many times to buildrepeat.");
        addSettingValue("buildRepeatSneaky", "Build repeat sneaky", "Don't notify schematics that they are moved.");
        addSettingValue("buildSkipBlocks", "Build skip blocks", "A list of blocks to be treated as correct.");
        addSettingValue("buildSubstitutes", "Build substitutes", "A mapping of blocks to blocks to be built instead.");
        addSettingValue("buildValidSubstitutes", "Build valid substitutes", "A mapping of blocks to blocks treated as correct in their position.");
        addSettingValue("cachedChunksExpirySeconds", "Cached chunks expiry seconds", "Cached chunks (regardless of if they're in RAM or saved to disk) expire and are deleted after this number of seconds. -1 to disable.");
        addSettingValue("cachedChunksOpacity", "Cached chunks opacity", "0.0f = not visible, fully transparent (instead of setting this to 0, turn off renderCachedChunks) 1.0f = fully opaque.");
        addSettingValue("cancelOnGoalInvalidation", "Cancel on goal invalidation", "Cancel the current path if the goal has changed, and the path originally ended in the goal but doesn't anymore.");
        addSettingValue("censorCoordinates", "Censor coordinates", "Censor coordinates in goals and block positions.");
        addSettingValue("censorRanCommands", "Censor ran commands", "Censor arguments to ran commands, to hide, for example, coordinates to #goal.");
        addSettingValue("chatControl", "Chat control", "Allow chat based control of Baritone.");
        addSettingValue("chatControlAnyway", "Chat control anyway", "Some clients like Impact try to force chatControl to off, so here's a second setting to do it anyway.");
        addSettingValue("chatDebug", "Chat debug", "Print all the debug messages to chat.");
        addSettingValue("chunkCaching", "Chunk caching", "The big one.");
        addSettingValue("colorBestPathSoFar", "Color best path so far", "The color of the best path so far.");
        addSettingValue("colorBlocksToBreak", "color blocks to break", "The color of the blocks to break.");
        addSettingValue("colorBlocksToPlace", "color blocks to place", "The color of the blocks to place.");
        addSettingValue("colorBlocksToWalkInto", "color blocks to walk into", "The color of the blocks to walk into.");
        addSettingValue("colorCurrentPath", "Color current path", "The color of the current path.");
        addSettingValue("colorGoalBox", "Color goal box", "The color of the goal box.");
        addSettingValue("colorInvertedGoalBox", "Color inverted goal box", "The color of the goal box when it's inverted.");
        addSettingValue("colorMostRecentConsidered", "Color most recent considered", "The color of the path to the most recent considered node.");
        addSettingValue("colorNextPath", "Color next path", "The color of the next path.");
        addSettingValue("colorSelection", "Color selection", "The color of all selections.");
        addSettingValue("colorSelectionPos1", "Color selection pos 1", "The color of the selection pos 1.");
        addSettingValue("colorSelectionPos2", "Color selection pos 2", "The color of the selection pos 2.");
        addSettingValue("considerPotionEffects", "Consider potion effects", "For example, if you have Mining Fatigue or Haste, adjust the costs of breaking blocks accordingly.");
        addSettingValue("costHeuristic", "Cost heuristic", "This is the big A* setting.");
        addSettingValue("costVerificationLookahead", "Cost verification lookahead", "Stop 5 movements before anything that made the path COST_INF.");
        addSettingValue("cutoffAtLoadBoundary", "Cutoff at load boundary", "After calculating a path (potentially through cached chunks), artificially cut it off to just the part that is entirely within currently loaded chunks.");
        addSettingValue("desktopNotifications", "Desktop notifications", "Desktop notifications.");
        addSettingValue("disableCompletionCheck", "Disable completion check", "Turn this on if your exploration filter is enormous, you don't want it to check if it's done, and you are just fine with it just hanging on completion.");
        addSettingValue("disconnectOnArrival", "Disconnect on arrival", "Disconnect from the server upon arriving at your goal.");
        addSettingValue("distanceTrim", "Distance trim", "Trim incorrect positions too far away, helps performance but hurts reliability in very large schematics.");
        addSettingValue("doBedWaypoints", "Do bed waypoints", "Allows baritone to save bed waypoints when interacting with beds.");
        addSettingValue("doDeathWaypoints", "Do death waypoints", "Allows baritone to save death waypoints.");
        addSettingValue("echoCommands", "Echo commands", "Echo commands to chat when they are run.");
        addSettingValue("enterPortal", "Enter portal", "When running a goto towards a nether portal block, walk all the way into the portal instead of stopping one block before.");
        addSettingValue("exploreChunkSetMinimumSize", "Explore chunk set minimum size", "Take the 10 closest chunks, even if they aren't strictly tied for distance metric from origin.");
        addSettingValue("exploreForBlocks", "Explore for blocks", "When GetToBlock or non-legit Mine doesn't know any locations for the desired block, explore randomly instead of giving up.");
        addSettingValue("exploreMaintainY", "Explore maintain Y", "Attempt to maintain Y coordinate while exploring.");
        addSettingValue("extendCacheOnThreshold", "Extend cache on threshold", "When the cache scan gives less blocks than the maximum threshold (but still above zero), scan the main world too.");
        addSettingValue("fadePath", "Fade path", "Start fading out the path at 20 movements ahead, and stop rendering it entirely 30 movements ahead.");
        addSettingValue("failureTimeoutMS", "Failure timeout ms", "Pathing can never take longer than this, even if that means failing to find any path at all.");
        addSettingValue("followOffsetDirection", "Follow offset direction", "The actual GoalNear is set in this direction from the entity you're following.");
        addSettingValue("followOffsetDistance", "Follow offset distance", "The actual GoalNear is set this distance away from the entity you're following.");
        addSettingValue("followRadius", "Follow radius", "The radius (for the GoalNear) of how close to your target position you actually have to be.");
        addSettingValue("forceInternalMining", "Force internal mining", "When mining block of a certain type, try to mine two at once instead of one.");
        addSettingValue("freeLook", "Free look", "Move without having to force the client-sided rotations.");
        addSettingValue("goalBreakFromAbove", "Goal break from above", "As well as breaking from above, set a goal to up and to the side of all blocks to break.");
        addSettingValue("goalRenderLineWidthPixels", "Goal render line width pixels", "Line width of the goal when rendered, in pixels.");
        addSettingValue("incorrectSize", "Incorrect size", "The set of incorrect blocks can never grow beyond this size.");
        addSettingValue("internalMiningAirException", "Internal mining air exception", "Modification to the previous setting, only has effect if forceInternalMining is true If true, only apply the previous setting if the block adjacent to the goal isn't air.");
        addSettingValue("itemSaver", "Item saver", "Stop using tools just before they are going to break.");
        addSettingValue("itemSaverThreshold", "Item saver threshold", "Durability to leave on the tool when using item saver.");
        addSettingValue("jumpPenalty", "Jump penalty", "Additional penalty for hitting the space bar (ascend, pillar, or parkour) because it uses hunger.");
        addSettingValue("layerHeight", "Layer height", "How high should the individual layers be?");
        addSettingValue("layerOrder", "Layer order", "false = build from bottom to top.");
        addSettingValue("legitMine", "Legit mine", "Disallow MineBehavior from using X-Ray to see where the ores are.");
        addSettingValue("legitMineIncludeDiagonals", "Legit mine include diagonals", "Magically see ores that are separated diagonally from existing ores.");
        addSettingValue("legitMineYLevel", "Legit mine Y level", "What Y level to go to for legit strip mining.");
        addSettingValue("logAsToast", "Log as toast", "Shows popup message in the upper right corner, similarly to when you make an advancement.");
        addSettingValue("mapArtMode", "Map art mode", "Build in map art mode, which makes baritone only care about the top block in each column.");
        addSettingValue("maxCachedWorldScanCount", "Max cached world scan count", "After finding this many instances of the target block in the cache, it will stop expanding outward the chunk search.");
        addSettingValue("maxCostIncrease", "Max cost increase", "If a movement's cost increases by more than this amount between calculation and execution (due to changes in the environment / world), cancel and recalculate.");
        addSettingValue("maxFallHeightBucket", "Max fall height bucket", "How far are you allowed to fall onto solid ground (with a water bucket)? It's not that reliable, so I've set it below what would kill an unarmored player (23).");
        addSettingValue("maxFallHeightNoWater", "Max fall height no water", "How far are you allowed to fall onto solid ground (without a water bucket)? 3 won't deal any damage.");
        addSettingValue("maxPathHistoryLength", "Max path history length", "If we are more than 300 movements into the current path, discard the oldest segments, as they are no longer useful.");
        addSettingValue("mineDropLoiterDurationMSThanksLouca", "Mine drop loiter duration ms", "While mining, wait this number of milliseconds after mining an ore to see if it will drop an item instead of immediately going onto the next one.");
        addSettingValue("mineGoalUpdateInterval", "Mine goal update interval", "Rescan for the goal once every 5 ticks.");
        addSettingValue("mineScanDroppedItems", "Mine scan dropped items", "While mining, should it also consider dropped items of the correct type as a pathing destination (as well as ore blocks)?");
        addSettingValue("minimumImprovementRepropagation", "Minimum improvement repropagation", "Don't repropagate cost improvements below 0.01 ticks.");
        addSettingValue("minYLevelWhileMining", "Min Y level while mining", "Sets the minimum y level whilst mining - set to 0 to turn off. if world has negative y values, subtract the min world height to get the value to put here.");
        addSettingValue("mobAvoidanceCoefficient", "Mob avoidance coefficient", "Set to 1.0 to effectively disable this feature.");
        addSettingValue("mobAvoidanceRadius", "Mob avoidance radius", "Distance to avoid mobs.");
        addSettingValue("mobSpawnerAvoidanceCoefficient", "Mob spawner avoidance coefficient", "Set to 1.0 to effectively disable this feature.");
        addSettingValue("mobSpawnerAvoidanceRadius", "Mob spawner avoidance radius", "Distance to avoid mob spawners.");
        addSettingValue("movementTimeoutTicks", "Movement timeout ticks", "If a movement takes this many ticks more than its initial cost estimate, cancel it.");
        addSettingValue("notificationOnBuildFinished", "Notification on build finished", "Desktop notification on build finished.");
        addSettingValue("notificationOnExploreFinished", "Notification on explore finished", "Desktop notification on explore finished.");
        addSettingValue("notificationOnFarmFail", "Notification on farm fail", "Desktop notification on farm fail.");
        addSettingValue("notificationOnMineFail", "Notification on mine fail", "Desktop notification on mine fail.");
        addSettingValue("notificationOnPathComplete", "Notification on path complete", "Desktop notification on path complete.");
        addSettingValue("notifier", "Notifier", "The function that is called when Baritone will send a desktop notification.");
        addSettingValue("okIfAir", "Ok if air", "A list of blocks to become air.");
        addSettingValue("okIfWater", "Ok if water", "Override builder's behavior to not attempt to correct blocks that are currently water.");
        addSettingValue("overshootTraverse", "Overshoot traverse", "If we overshoot a traverse and end up one block beyond the destination, mark it as successful anyway.");
        addSettingValue("pathCutoffFactor", "Path cutoff factor", "Static cutoff factor.");
        addSettingValue("pathCutoffMinimumLength", "Path cutoff minimum length", "Only apply static cutoff for paths of at least this length (in terms of number of movements).");
        addSettingValue("pathHistoryCutoffAmount", "Path history cutoff amount", "If the current path is too long, cut off this many movements from the beginning.");
        addSettingValue("pathingMapDefaultSize", "Pathing map default size", "Default size of the Long2ObjectOpenHashMap used in pathing.");
        addSettingValue("pathingMapLoadFactor", "Pathing map load factor", "Load factor coefficient for the Long2ObjectOpenHashMap used in pathing.");
        addSettingValue("pathingMaxChunkBorderFetch", "Pathing max chunk border fetch", "The maximum number of times it will fetch outside loaded or cached chunks before assuming that pathing has reached the end of the known area, and should therefore stop.");
        addSettingValue("pathRenderLineWidthPixels", "Path render line width pixels", "Line width of the path when rendered, in pixels.");
        addSettingValue("pathThroughCachedOnly", "Path through cached only", "Exclusively use cached chunks for pathing.");
        addSettingValue("pauseMiningForFallingBlocks", "Pause mining for falling blocks", "When breaking blocks for a movement, wait until all falling blocks have settled before continuing.");
        addSettingValue("planAheadFailureTimeoutMS", "Plan ahead failure timeout ms", "Planning ahead while executing a segment can never take longer than this, even if that means failing to find any path at all.");
        addSettingValue("planAheadPrimaryTimeoutMS", "Plan ahead primary timeout ms", "Planning ahead while executing a segment ends after this amount of time, but only if a path has been found.");
        addSettingValue("planningTickLookahead", "Planning tick lookahead", "Start planning the next path once the remaining movements tick estimates sum up to less than this value.");
        addSettingValue("preferSilkTouch", "Prefer silk touch", "Always prefer silk touch tools over regular tools.");
        addSettingValue("prefix", "Prefix", "The command prefix for chat control.");
        addSettingValue("prefixControl", "Prefix control", "Whether or not to allow you to run Baritone commands with the prefix.");
        addSettingValue("primaryTimeoutMS", "Primary timeout ms", "Pathing ends after this amount of time, but only if a path has been found.");
        addSettingValue("pruneRegionsFromRAM", "Prune regions from RAM", "On save, delete from RAM any cached regions that are more than 1024 blocks away from the player.");
        addSettingValue("randomLooking", "Random looking", "How many degrees to randomize the pitch and yaw every tick.");
        addSettingValue("randomLooking113", "Random looking 1.13", "How many degrees to randomize the yaw every tick. Set to 0 to disable.");
        addSettingValue("renderCachedChunks", "Render cached chunks", "Render cached chunks as semitransparent.");
        addSettingValue("renderGoal", "Render goal", "Render the goal.");
        addSettingValue("renderGoalAnimated", "Render goal animated", "Render the goal as a sick animated thingy instead of just a box (also controls animation of goal X Z if R ender goal X Z beacon is enabled).");
        addSettingValue("renderGoalIgnoreDepth", "Render goal ignore depth", "Ignore depth when rendering the goal.");
        addSettingValue("renderGoalXZBeacon", "Render goal X Z beacon", "Render X/Z type goals with the vanilla beacon beam effect.");
        addSettingValue("renderPath", "Render path", "Render the path.");
        addSettingValue("renderPathAsLine", "Render path as line", "Render the path as a line instead of a frickin thingy.");
        addSettingValue("renderPathIgnoreDepth", "Render path ignore depth", "Ignore depth when rendering the path.");
        addSettingValue("renderSelection", "Render selection", "Render selections.");
        addSettingValue("renderSelectionBoxes", "Render selection boxes", "Render selection boxes.");
        addSettingValue("renderSelectionBoxesIgnoreDepth", "Render selection boxes ignore depth", "Ignore depth when rendering the selection boxes (to break, to place, to walk into).");
        addSettingValue("renderSelectionCorners", "Render selection corners", "Render selection corners.");
        addSettingValue("renderSelectionIgnoreDepth", "Render selection ignore depth", "Ignore depth when rendering selections.");
        addSettingValue("repackOnAnyBlockChange", "Repack on any block change", "Whenever a block changes, repack the whole chunk that it's in.");
        addSettingValue("replantCrops", "Replant crops", "Replant normal Crops while farming and leave cactus and sugarcane to regrow.");
        addSettingValue("replantNetherWart", "Replant nether wart", "Replant nether wart while farming.");
        addSettingValue("rightClickContainerOnArrival", "Right click container on arrival", "When running a goto towards a container block (chest, ender chest, furnace, etc), right click and open it once you arrive.");
        addSettingValue("rightClickSpeed", "Right click speed", "How many ticks between right clicks are allowed.");
        addSettingValue("schematicFallbackExtension", "Schematic fallback extension", "The fallback used by the build command when no extension is specified.");
        addSettingValue("schematicOrientationX", "Schematic orientation X", "When this setting is true, build a schematic with the highest X coordinate being the origin, instead of the lowest.");
        addSettingValue("schematicOrientationY", "Schematic orientation Y", "When this setting is true, build a schematic with the highest Y coordinate being the origin, instead of the lowest.");
        addSettingValue("schematicOrientationZ", "Schematic orientation Z", "When this setting is true, build a schematic with the highest Z coordinate being the origin, instead of the lowest.");
        addSettingValue("selectionLineWidth", "Selection line width", "Line width of the goal when rendered, in pixels.");
        addSettingValue("selectionOpacity", "Selection opacity", "The opacity of the selection.");
        addSettingValue("shortBaritonePrefix", "Short Baritone prefix", "Use a short Baritone prefix [B] instead of [Baritone] when logging to chat.");
        addSettingValue("simplifyUnloadedYCoord", "Simplify unloaded Y coordinate", "If your goal is a goal block in an unloaded chunk, assume it's far enough away that the Y coordinate doesn't matter yet, and replace it with a GoalXZ to the same place before calculating a path.");
        addSettingValue("skipFailedLayers", "Skip failed layers", "If a layer is unable to be constructed, just skip it.");
        addSettingValue("slowPath", "Slow path", "For debugging, consider nodes much much slower.");
        addSettingValue("slowPathTimeDelayMS", "Slow path time delay ms", "Milliseconds between each node.");
        addSettingValue("slowPathTimeoutMS", "Slow path timeout ms", "The alternative timeout number when slowPath is on.");
        addSettingValue("splicePath", "Splice path", "When a new segment is calculated that doesn't overlap with the current one, but simply begins where the current segment ends, splice it on and make a longer combined path.");
        addSettingValue("sprintAscends", "Sprint ascends", "Sprint and jump a block early on ascends wherever possible.");
        addSettingValue("sprintInWater", "Sprint in water", "Continue sprinting while in water.");
        addSettingValue("startAtLayer", "Start at layer", "Start building the schematic at a specific layer.");
        addSettingValue("toaster", "Toaster", "The function that is called when Baritone will show a toast.");
        addSettingValue("toastTimer", "Toast timer", "The time of how long the message in the pop-up will display.");
        addSettingValue("useSwordToMine", "Use sword to mine", "Use sword to mine.");
        addSettingValue("verboseCommandExceptions", "Verbose command exceptions", "Print out ALL command exceptions as a stack trace to stdout, even simple syntax errors.");
        addSettingValue("walkOnWaterOnePenalty", "Walk on water one penalty", "Walking on water uses up hunger really quick, so penalize it.");
        addSettingValue("walkWhileBreaking", "Walk while breaking", "Don't stop walking forward when you need to break blocks in your way.");
        addSettingValue("worldExploringChunkOffset", "World exploring chunk offset", "While exploring the world, offset the closest unloaded chunk by this much in both axes.");
        addSettingValue("yLevelBoxSize", "Y level box size", "The size of the box that is rendered when the current goal is a Y level.");
    }
}
