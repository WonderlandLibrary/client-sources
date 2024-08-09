package dev.luvbeeq.baritone.command.defaults;

import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.command.ICommand;

import java.util.*;

public final class DefaultCommands {

    private DefaultCommands() {
    }

    public static List<ICommand> createAll(IBaritone baritone) {
        Objects.requireNonNull(baritone);
        List<ICommand> commands = new ArrayList<>(Arrays.asList(
                new HelpCommand(baritone),
                new SetCommand(baritone),
                new CommandAlias(baritone, Arrays.asList("modified", "mod", "baritone", "modifiedsettings"), "List modified settings", "set modified"),
                new CommandAlias(baritone, "reset", "Reset all settings or just one", "set reset"),
                new GoalCommand(baritone),
                new GotoCommand(baritone),
                new PathCommand(baritone),
                new ProcCommand(baritone),
                new ETACommand(baritone),
                new VersionCommand(baritone),
                new RepackCommand(baritone),
                new BuildCommand(baritone),
                new SchematicaCommand(baritone),
                new LitematicaCommand(baritone),
                new ComeCommand(baritone),
                new AxisCommand(baritone),
                new ForceCancelCommand(baritone),
                new GcCommand(baritone),
                new InvertCommand(baritone),
                new TunnelCommand(baritone),
                new RenderCommand(baritone),
                new FarmCommand(baritone),
                new FollowCommand(baritone),
                new ExploreFilterCommand(baritone),
                new ReloadAllCommand(baritone),
                new SaveAllCommand(baritone),
                new ExploreCommand(baritone),
                new BlacklistCommand(baritone),
                new FindCommand(baritone),
                new MineCommand(baritone),
                new ClickCommand(baritone),
                new SurfaceCommand(baritone),
                new ThisWayCommand(baritone),
                new WaypointsCommand(baritone),
                new CommandAlias(baritone, "sethome", "Sets your home waypoint", "waypoints save home"),
                new CommandAlias(baritone, "home", "Path to your home waypoint", "waypoints goto home"),
                new SelCommand(baritone)
        ));
        ExecutionControlCommands prc = new ExecutionControlCommands(baritone);
        commands.add(prc.pauseCommand);
        commands.add(prc.resumeCommand);
        commands.add(prc.pausedCommand);
        commands.add(prc.cancelCommand);
        return Collections.unmodifiableList(commands);
    }
}
