package baritone;

import baritone.api.event.events.*;
import baritone.api.*;
import baritone.api.pathing.calc.*;
import baritone.api.process.*;
import java.io.*;
import baritone.api.cache.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.*;
import baritone.api.utils.*;
import baritone.api.pathing.goals.*;

public class co extends b implements cr
{
    public co(final a a) {
        super(a);
    }
    
    @Override
    public void onSendChatMessage(final ChatEvent chatEvent) {
        final String message = chatEvent.getMessage();
        if (a.a().prefixControl.value && message.startsWith("#")) {
            if (!this.a(message.substring(1))) {
                this.b("Invalid command");
            }
            chatEvent.cancel();
            return;
        }
        if (!a.a().chatControl.value && !a.a().removePrefix.value) {
            return;
        }
        if (this.a(message)) {
            chatEvent.cancel();
        }
    }
    
    private boolean a(String trim) {
        trim = trim.toLowerCase(Locale.US).trim();
        final j a = this.a.a;
        final ca a2 = this.a.a;
        for (final Settings$Setting<Boolean> settings$Setting : baritone.a.a().getAllValuesByType(Boolean.class)) {
            if (trim.equalsIgnoreCase(settings$Setting.getName())) {
                final Settings$Setting<Boolean> settings$Setting2 = settings$Setting;
                settings$Setting2.value ^= true;
                this.b("Toggled " + settings$Setting.getName() + " to " + settings$Setting.value);
                SettingsUtil.save(baritone.a.a());
                return true;
            }
        }
        if (trim.equals("baritone") || trim.equals("modifiedsettings") || trim.startsWith("settings m") || trim.equals("modified")) {
            this.b("All settings that have been modified from their default values:");
            final Iterator<Settings$Setting> iterator2 = SettingsUtil.modifiedSettings(baritone.a.a()).iterator();
            while (iterator2.hasNext()) {
                this.b(iterator2.next().toString());
            }
            return true;
        }
        if (trim.startsWith("settings")) {
            final String substring = trim.substring(8);
            try {
                final int int1;
                final int n = (int1 = Integer.parseInt(substring.trim())) * 10;
                final int min = Math.min(baritone.a.a().allSettings.size(), (int1 + 1) * 10);
                this.b("Settings " + n + " to " + (min - 1) + ":");
                for (int i = n; i < min; ++i) {
                    this.b(baritone.a.a().allSettings.get(i).toString());
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                this.b("All settings:");
                final Iterator<Settings$Setting<?>> iterator3 = baritone.a.a().allSettings.iterator();
                while (iterator3.hasNext()) {
                    this.b(iterator3.next().toString());
                }
                this.b("To get one page of ten settings at a time, do settings <num>");
            }
            return true;
        }
        if (trim.equals("") || trim.equals("help") || trim.equals("?")) {
            String[] split;
            for (int length = (split = "baritone - Output settings into chat\nsettings - Same as baritone\ngoal - Create a goal (one number is '<Y>', two is '<X> <Z>', three is '<X> <Y> <Z>, 'clear' to clear)\npath - Go towards goal\nrepack - (debug) Repacks chunk cache\nrescan - (debug) Same as repack\naxis - Paths towards the closest axis or diagonal axis, at y=120\ncancel - Cancels current path\nforcecancel - sudo cancel (only use if very glitched, try toggling 'pause' first)\ngc - Calls System.gc();\ninvert - Runs away from the goal instead of towards it\nfollow - Follows a player 'follow username'\nreloadall - (debug) Reloads chunk cache\nsaveall - (debug) Saves chunk cache\nfind - (debug) outputs how many blocks of a certain type are within the cache\nmine - Paths to and mines specified blocks 'mine x_ore y_ore ...'\nthisway - Creates a goal X blocks where you're facing\nlist - Lists waypoints under a category\nget - Same as list\nshow - Same as list\nsave - Saves a waypoint (works but don't try to make sense of it)\ngoto - Paths towards specified block or waypoint\nspawn - Paths towards world spawn or your most recent bed right-click\nsethome - Sets \"home\"\nhome - Paths towards \"home\" \ncosts - (debug) all movement costs from current location\ndamn - Daniel\nGo to https://github.com/cabaletta/baritone/blob/master/USAGE.md for more information".split("\n")).length, j = 0; j < length; ++j) {
                trim = split[j];
                this.b(trim);
            }
            return true;
        }
        if (trim.contains(" ")) {
            final String substring2 = trim.substring(0, trim.indexOf(32));
            final String s = trim;
            final String substring3 = s.substring(s.indexOf(32) + 1);
            final Settings$Setting<?> settings$Setting3;
            if ((settings$Setting3 = baritone.a.a().byLowerName.get(substring2)) != null) {
                if (substring3.equals("reset")) {
                    this.b("Resetting setting " + substring2 + " to default value.");
                    settings$Setting3.reset();
                }
                else {
                    try {
                        SettingsUtil.parseAndApply(baritone.a.a(), substring2, substring3);
                    }
                    catch (Exception ex2) {
                        this.b("Unable to parse setting");
                        return true;
                    }
                }
                SettingsUtil.save(baritone.a.a());
                this.b(settings$Setting3.toString());
                return true;
            }
        }
        if (baritone.a.a().byLowerName.containsKey(trim)) {
            this.b(baritone.a.a().byLowerName.get(trim).toString());
            return true;
        }
        if (trim.startsWith("goal")) {
            final String trim2;
            Goal a3;
            if ((trim2 = trim.substring(4).trim()).equals("clear") || trim2.equals("none")) {
                a3 = null;
            }
            else {
                String[] split2;
                if ((split2 = trim2.split(" "))[0].equals("")) {
                    split2 = new String[0];
                }
                if ((a3 = this.a(split2)) == null) {
                    return true;
                }
            }
            a2.setGoal(a3);
            this.b("Goal: ".concat(String.valueOf(a3)));
            return true;
        }
        if (trim.equals("path")) {
            if (a.getGoal() == null) {
                this.b("No goal.");
            }
            else if (a.getGoal().isInGoal(this.a.playerFeet())) {
                this.b("Already in goal");
            }
            else if (a.isPathing()) {
                this.b("Currently executing a path. Please cancel it first.");
            }
            else {
                a2.setGoalAndPath(a.getGoal());
            }
            return true;
        }
        if (trim.equals("fullpath")) {
            if (a.getGoal() == null) {
                this.b("No goal.");
            }
            else {
                this.b("Started segmented calculator");
                dk.a(a.a(), a.getGoal(), new ad(this.a, true), (Consumer<IPath>)this::a, this::c);
            }
            return true;
        }
        if (trim.equals("proc")) {
            final Optional<IBaritoneProcess> mostRecentInControl;
            if (!(mostRecentInControl = this.a.a.mostRecentInControl()).isPresent()) {
                this.b("No process is in control");
                return true;
            }
            final IBaritoneProcess baritoneProcess = mostRecentInControl.get();
            this.b("Class: " + baritoneProcess.getClass());
            this.b("Priority: " + baritoneProcess.priority());
            this.b("Temporary: " + baritoneProcess.isTemporary());
            this.b("Display name: " + baritoneProcess.displayName());
            this.b("Command: " + this.a.a.mostRecentCommand());
            return true;
        }
        else {
            if (trim.equals("version")) {
                final String implementationVersion;
                if ((implementationVersion = co.class.getPackage().getImplementationVersion()) == null) {
                    this.b("No version detected. Either dev environment or broken install.");
                }
                else {
                    this.b("You are using Baritone v".concat(String.valueOf(implementationVersion)));
                }
                return true;
            }
            if (trim.equals("repack") || trim.equals("rescan")) {
                final brx brx = (brx)this.a.world().B();
                final int n2 = this.a.playerFeet().p() >> 4;
                final int n3 = this.a.playerFeet().r() >> 4;
                int n4 = 0;
                for (int k = n2 - 40; k <= n2 + 40; ++k) {
                    for (int l = n3 - 40; l <= n3 + 40; ++l) {
                        final axw a4;
                        if ((a4 = brx.a(k, l)) != null) {
                            ++n4;
                            this.a.a.a.getCachedWorld().queueForPacking(a4);
                        }
                    }
                }
                this.b("Queued " + n4 + " chunks for repacking");
                return true;
            }
            if (trim.startsWith("build")) {
                String s2;
                et playerFeet;
                try {
                    final String[] split3 = trim.substring(5).trim().split(" ");
                    s2 = split3[0] + ".schematic";
                    playerFeet = new et(a(split3[1], this.a.playerFeet().a), a(split3[2], this.a.playerFeet().b), a(split3[3], this.a.playerFeet().c));
                }
                catch (Exception ex3) {
                    s2 = trim.substring(5).trim() + ".schematic";
                    playerFeet = this.a.playerFeet();
                }
                this.b("Loading '" + s2 + "' to build from origin " + playerFeet);
                final bs a5 = this.a.a;
                final String s3 = s2;
                final fq fq = (fq)playerFeet;
                final String s4 = s3;
                final bs bs = a5;
                final File file = new File(new File(bib.z().w, "schematics"), s4);
                System.out.println(file + " " + file.exists());
                this.b(bs.build(s4, file, fq) ? "Loaded" : "Unable to load");
                return true;
            }
            if (trim.equals("come")) {
                a2.setGoalAndPath(new GoalBlock(new et(co.a.aa())));
                this.b("Coming");
                return true;
            }
            if (trim.equals("axis") || trim.equals("highway")) {
                a2.setGoalAndPath(new GoalAxis());
                return true;
            }
            if (trim.equals("cancel") || trim.equals("stop")) {
                a.cancelEverything();
                this.b("ok canceled");
                return true;
            }
            if (trim.equals("forcecancel")) {
                a.cancelEverything();
                a.b();
                this.b("ok force canceled");
                return true;
            }
            if (trim.equals("gc")) {
                System.gc();
                this.b("Called System.gc();");
                return true;
            }
            if (trim.equals("invert")) {
                final Goal goal;
                et et;
                if ((goal = a.getGoal()) instanceof GoalXZ) {
                    et = new et(((GoalXZ)goal).getX(), 0, ((GoalXZ)goal).getZ());
                }
                else if (goal instanceof GoalBlock) {
                    et = ((GoalBlock)goal).getGoalPos();
                }
                else {
                    this.b("Goal must be GoalXZ or GoalBlock to invert");
                    this.b("Inverting goal of player feet");
                    et = this.a.playerFeet();
                }
                a2.setGoalAndPath(new cp(this, new et[] { et }));
                return true;
            }
            if (trim.startsWith("cleararea")) {
                final String substring4;
                et et2;
                et playerFeet2;
                if ((substring4 = trim.substring(9)).isEmpty()) {
                    final Goal goal2;
                    if ((goal2 = this.a.a.getGoal()) == null || !(goal2 instanceof GoalBlock)) {
                        this.b("Need to specify goal of opposite corner");
                        return true;
                    }
                    et2 = ((GoalBlock)goal2).getGoalPos();
                    playerFeet2 = this.a.playerFeet();
                }
                else {
                    try {
                        final String[] split4 = substring4.split(" ");
                        et2 = this.a.playerFeet();
                        playerFeet2 = new et(Integer.parseInt(split4[0]), Integer.parseInt(split4[1]), Integer.parseInt(split4[2]));
                    }
                    catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException ex4) {
                        this.b("unable to parse");
                        return true;
                    }
                }
                this.a.a.a(et2, playerFeet2);
                return true;
            }
            if (trim.equals("reset")) {
                final Iterator<Settings$Setting<?>> iterator4 = baritone.a.a().allSettings.iterator();
                while (iterator4.hasNext()) {
                    iterator4.next().reset();
                }
                SettingsUtil.save(baritone.a.a());
                this.b("Baritone settings reset");
                return true;
            }
            if (trim.equals("render")) {
                final BetterBlockPos playerFeet3 = this.a.playerFeet();
                bib.z().g.a(playerFeet3.a - 500, playerFeet3.b - 500, playerFeet3.c - 500, playerFeet3.a + 500, playerFeet3.b + 500, playerFeet3.c + 500);
                this.b("okay");
                return true;
            }
            if (trim.equals("echest")) {
                final Optional<List<aip>> a6;
                if ((a6 = this.a.a.a()).isPresent()) {
                    this.b("echest contents:");
                    this.a(a6.get());
                }
                else {
                    this.b("echest contents unknown");
                }
                return true;
            }
            if (trim.equals("chests")) {
                System.out.println(this.a.a);
                System.out.println(this.a.a.a);
                System.out.println(this.a.a.a.getContainerMemory());
                System.out.println(this.a.a.a.getContainerMemory().getRememberedInventories());
                System.out.println(this.a.a.a.getContainerMemory().getRememberedInventories().entrySet());
                System.out.println(this.a.a.a.getContainerMemory().getRememberedInventories().entrySet());
                for (final Map.Entry<et, IRememberedInventory> entry : this.a.a.a.getContainerMemory().getRememberedInventories().entrySet()) {
                    this.b(new StringBuilder().append(entry.getKey()).toString());
                    this.a(entry.getValue().getContents());
                }
                return true;
            }
            if (trim.startsWith("followplayers")) {
                final ce a7 = this.a.a;
                final Class<aed> clazz = aed.class;
                aed.class.getClass();
                a7.follow((Predicate<vg>)clazz::isInstance);
                this.b("Following any players");
                return true;
            }
            if (trim.startsWith("follow")) {
                final String trim3 = trim.substring(6).trim();
                Object o = Optional.empty();
                if (trim3.length() == 0) {
                    o = this.a.getSelectedEntity();
                }
                else {
                    final Iterator<aed> iterator6 = (Iterator<aed>)this.a.world().i.iterator();
                    while (iterator6.hasNext()) {
                        final aed aed;
                        final String lowerCase;
                        if (!(lowerCase = (aed = iterator6.next()).h_().trim().toLowerCase()).equals(this.a.player().h_().trim().toLowerCase()) && (lowerCase.contains(trim3) || trim3.contains(lowerCase))) {
                            o = Optional.of(aed);
                        }
                    }
                }
                if (!((Optional)o).isPresent()) {
                    this.b("Not found");
                    return true;
                }
                this.a.a.follow((Predicate<vg>)co::a);
                this.b("Following " + ((Optional<vg>)o).get());
                return true;
            }
            else {
                if (trim.equals("reloadall")) {
                    this.a.a.a.getCachedWorld().reloadAllFromDisk();
                    this.b("ok");
                    return true;
                }
                if (trim.equals("saveall")) {
                    this.a.a.a.getCachedWorld().save();
                    this.b("ok");
                    return true;
                }
                if (trim.startsWith("explore")) {
                    final String trim4 = trim.substring(7).trim();
                    int n5;
                    int n6;
                    try {
                        n5 = Integer.parseInt(trim4.split(" ")[0]);
                        n6 = Integer.parseInt(trim4.split(" ")[1]);
                    }
                    catch (Exception ex5) {
                        n5 = this.a.playerFeet().a;
                        n6 = this.a.playerFeet().c;
                    }
                    this.a.a.a = new et(n5, 0, n6);
                    this.b("Exploring from " + n5 + "," + n6);
                    return true;
                }
                if (trim.startsWith("find")) {
                    final String trim5 = trim.substring(4).trim();
                    final ArrayList<et> locations = this.a.a.a.getCachedWorld().getLocationsOf(trim5, 1, this.a.playerFeet().p(), this.a.playerFeet().r(), 4);
                    this.b("Have " + locations.size() + " locations");
                    final Iterator<et> iterator7 = locations.iterator();
                    while (iterator7.hasNext()) {
                        final aow u;
                        if (!p.a(u = cn.a(this.a, (et)iterator7.next()).u()).equalsIgnoreCase(trim5)) {
                            System.out.println("Was looking for " + trim5 + " but actually found " + u + " " + p.a(u));
                        }
                    }
                    return true;
                }
                if (trim.startsWith("mine")) {
                    final String[] split5 = trim.substring(4).trim().split(" ");
                    try {
                        final int int2 = Integer.parseInt(split5[1]);
                        this.a.a.mine(int2, p.a(split5[0]));
                        this.b("Will mine " + int2 + " " + split5[0]);
                        return true;
                    }
                    catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException ex6) {
                        String[] array;
                        for (int length2 = (array = split5).length, n7 = 0; n7 < length2; ++n7) {
                            final String s5;
                            if (p.b(s5 = array[n7]) == null) {
                                this.b(s5 + " isn't a valid block name");
                                return true;
                            }
                        }
                        this.a.a.mineByName(0, split5);
                        this.b("Started mining blocks of type " + Arrays.toString(split5));
                        return true;
                    }
                }
                if (trim.equals("click")) {
                    new Thread(co::a).start();
                    this.b("aight dude");
                    return true;
                }
                if (!trim.startsWith("thisway")) {
                    if (!trim.startsWith("forward")) {
                        if (trim.startsWith("list") || trim.startsWith("get ") || trim.startsWith("show")) {
                            String s6;
                            if ((s6 = trim.substring(4).trim()).endsWith("s")) {
                                s6 = s6.substring(0, s6.length() - 1);
                            }
                            final IWaypoint$Tag fromString;
                            if ((fromString = IWaypoint$Tag.fromString(s6)) == null) {
                                this.b("Not a valid tag. Tags are: " + Arrays.asList(IWaypoint$Tag.values()).toString().toLowerCase());
                                return true;
                            }
                            final ArrayList list;
                            (list = new ArrayList<Object>(this.a.a.a.getWaypoints().getByTag(fromString))).sort(Comparator.comparingLong(IWaypoint::getCreationTimestamp));
                            this.b("Waypoints under tag " + fromString + ":");
                            final Iterator<IWaypoint> iterator8 = (Iterator<IWaypoint>)list.iterator();
                            while (iterator8.hasNext()) {
                                this.b(iterator8.next().toString());
                            }
                            return true;
                        }
                        else {
                            if (trim.startsWith("save")) {
                                String trim6 = trim.substring(4).trim();
                                et playerFeet4 = this.a.playerFeet();
                                if (trim6.contains(" ")) {
                                    this.b("Name contains a space, assuming it's in the format 'save waypointName X Y Z'");
                                    final String[] split6;
                                    if ((split6 = trim6.split(" ")).length != 4) {
                                        this.b("Unable to parse, expected four things");
                                        return true;
                                    }
                                    try {
                                        playerFeet4 = new et(Integer.parseInt(split6[1]), Integer.parseInt(split6[2]), Integer.parseInt(split6[3]));
                                    }
                                    catch (NumberFormatException ex7) {
                                        this.b("Unable to parse coordinate integers");
                                        return true;
                                    }
                                    trim6 = split6[0];
                                }
                                this.a.a.a.getWaypoints().addWaypoint(new t(trim6, IWaypoint$Tag.USER, playerFeet4));
                                this.b("Saved user defined position " + playerFeet4 + " under name '" + trim6 + "'. Say 'goto " + trim6 + "' to set goal, say 'list user' to list custom waypoints.");
                                return true;
                            }
                            if (trim.startsWith("goto")) {
                                String s7;
                                if ((s7 = trim.substring(4).trim()).endsWith("s") && IWaypoint$Tag.fromString(s7.substring(0, s7.length() - 1)) != null) {
                                    s7 = s7.substring(0, s7.length() - 1);
                                }
                                final IWaypoint$Tag fromString2;
                                IWaypoint mostRecentByTag;
                                if ((fromString2 = IWaypoint$Tag.fromString(s7)) == null) {
                                    final aow b;
                                    if ((b = p.b(trim = s7)) != null) {
                                        this.a.a.getToBlock(b);
                                        return true;
                                    }
                                    if ((mostRecentByTag = this.a.a.a.getWaypoints().getAllWaypoints().stream().filter(co::a).max(Comparator.comparingLong(IWaypoint::getCreationTimestamp)).orElse(null)) == null) {
                                        final Goal a8;
                                        if ((a8 = this.a(s7.split(" "))) != null) {
                                            this.b("Going to ".concat(String.valueOf(a8)));
                                            a2.setGoalAndPath(a8);
                                        }
                                        return true;
                                    }
                                }
                                else if ((mostRecentByTag = this.a.a.a.getWaypoints().getMostRecentByTag(fromString2)) == null) {
                                    this.b("None saved for tag ".concat(String.valueOf(fromString2)));
                                    return true;
                                }
                                a2.setGoalAndPath((mostRecentByTag.getTag() == IWaypoint$Tag.BED) ? new GoalGetToBlock(mostRecentByTag.getLocation()) : new GoalBlock(mostRecentByTag.getLocation()));
                                return true;
                            }
                            if (trim.equals("spawn") || trim.equals("bed")) {
                                final IWaypoint mostRecentByTag2;
                                if ((mostRecentByTag2 = this.a.a.a.getWaypoints().getMostRecentByTag(IWaypoint$Tag.BED)) == null) {
                                    final et de = this.a.player().de();
                                    final GoalXZ goalAndPath = new GoalXZ(de.p(), de.r());
                                    this.b("spawn not saved, defaulting to world spawn. set goal to ".concat(String.valueOf(goalAndPath)));
                                    a2.setGoalAndPath(goalAndPath);
                                }
                                else {
                                    final GoalGetToBlock goalAndPath2 = new GoalGetToBlock(mostRecentByTag2.getLocation());
                                    a2.setGoalAndPath(goalAndPath2);
                                    this.b("Set goal to most recent bed ".concat(String.valueOf(goalAndPath2)));
                                }
                                return true;
                            }
                            if (trim.equals("sethome")) {
                                this.a.a.a.getWaypoints().addWaypoint(new t("", IWaypoint$Tag.HOME, this.a.playerFeet()));
                                this.b("Saved. Say home to set goal.");
                                return true;
                            }
                            if (trim.equals("home")) {
                                final IWaypoint mostRecentByTag3;
                                if ((mostRecentByTag3 = this.a.a.a.getWaypoints().getMostRecentByTag(IWaypoint$Tag.HOME)) == null) {
                                    this.b("home not saved");
                                }
                                else {
                                    final GoalBlock goalAndPath3 = new GoalBlock(mostRecentByTag3.getLocation());
                                    a2.setGoalAndPath(goalAndPath3);
                                    this.b("Going to saved home ".concat(String.valueOf(goalAndPath3)));
                                }
                                return true;
                            }
                            if (trim.equals("costs")) {
                                final List<ae> list2 = Stream.of(aj.values()).map((Function<? super aj, ?>)this::a).collect((Collector<? super Object, ?, List<ae>>)Collectors.toCollection((Supplier<R>)ArrayList::new));
                                while (list2.contains(null)) {
                                    list2.remove(null);
                                }
                                list2.sort(Comparator.comparingDouble((ToDoubleFunction<? super ae>)this::a));
                                final Iterator<ae> iterator9 = list2.iterator();
                                while (iterator9.hasNext()) {
                                    final ae ae;
                                    final String[] split7 = (ae = iterator9.next()).getClass().toString().split("\\.");
                                    final double cost;
                                    String value = String.valueOf(cost = ae.getCost());
                                    if (cost >= 1000000.0) {
                                        value = "IMPOSSIBLE";
                                    }
                                    final StringBuilder sb = new StringBuilder();
                                    final String[] array2 = split7;
                                    this.b(sb.append(array2[array2.length - 1]).append(" ").append(ae.getDest().p()).append(",").append(ae.getDest().q()).append(",").append(ae.getDest().r()).append(" ").append(value).toString());
                                }
                                return true;
                            }
                            if (trim.equals("damn")) {
                                this.b("daniel");
                            }
                            return false;
                        }
                    }
                }
                try {
                    final GoalXZ fromDirection = GoalXZ.fromDirection(this.a.playerFeetAsVec(), this.a.player().v, Double.parseDouble(trim.substring(7).trim()));
                    a2.setGoal(fromDirection);
                    this.b("Goal: ".concat(String.valueOf(fromDirection)));
                }
                catch (NumberFormatException ex8) {
                    this.b("Error unable to parse '" + trim.substring(7).trim() + "' to a double.");
                }
                return true;
            }
        }
    }
    
    private static int a(final String s, final int n) {
        if (s.equals("~")) {
            return n;
        }
        if (s.startsWith("~")) {
            return Integer.parseInt(s.substring(1)) + n;
        }
        return Integer.parseInt(s);
    }
    
    private void a(final List<aip> list) {
        final Iterator<aip> iterator = list.iterator();
        while (iterator.hasNext()) {
            final aip aip;
            if (!(aip = iterator.next()).b()) {
                this.b(aip.E() + "x " + aip.r() + "@" + aip.i());
            }
        }
    }
    
    private Goal a(final String[] array) {
        Goal goal = null;
        try {
            switch (array.length) {
                case 0: {
                    goal = new GoalBlock(this.a.playerFeet());
                    break;
                }
                case 1: {
                    goal = new GoalYLevel(Integer.parseInt(array[0]));
                    break;
                }
                case 2: {
                    goal = new GoalXZ(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
                    break;
                }
                case 3: {
                    goal = new GoalBlock(new et(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2])));
                    break;
                }
                default: {
                    this.b("unable to understand lol");
                    return null;
                }
            }
        }
        catch (NumberFormatException ex) {
            this.b("unable to parse integer ".concat(String.valueOf(ex)));
            return null;
        }
        return goal;
    }
    
    private double a(final ae ae) {
        return ae.a(new ad(this.a));
    }
    
    private ae a(final aj aj) {
        return aj.a(new ad(this.a), this.a.playerFeet());
    }
    
    private static boolean a(final String s, final IWaypoint waypoint) {
        return waypoint.getName().equalsIgnoreCase(s);
    }
    
    private static void a() {
        try {
            Thread.sleep(100L);
            co.a.a(co::b);
        }
        catch (Exception ex) {}
    }
    
    private static void b() {
        co.a.a((blk)new cq());
    }
    
    private static boolean a(final vg vg, final vg vg2) {
        return vg.equals((Object)vg2);
    }
    
    private void c() {
        this.b("Path calculation failed, no path");
    }
    
    private void a(final j j, final IPath path) {
        this.b("Found a path");
        this.b("Ends at " + path.getDest());
        this.b("Length " + path.length());
        this.b("Estimated time " + path.ticksRemainingFrom(0));
        synchronized (j.a) {
            j.a = new bq(j, path);
        }
    }
}
