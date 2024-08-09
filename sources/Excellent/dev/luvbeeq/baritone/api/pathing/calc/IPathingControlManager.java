package dev.luvbeeq.baritone.api.pathing.calc;

import dev.luvbeeq.baritone.api.process.IBaritoneProcess;
import dev.luvbeeq.baritone.api.process.PathingCommand;

import java.util.Optional;

/**
 * @author leijurv
 */
public interface IPathingControlManager {

    /**
     * Registers a process with this pathing control manager. See {@link IBaritoneProcess} for more details.
     *
     * @param process The process
     * @see IBaritoneProcess
     */
    void registerProcess(IBaritoneProcess process);

    /**
     * @return The most recent {@link IBaritoneProcess} that had control
     */
    Optional<IBaritoneProcess> mostRecentInControl();

    /**
     * @return The most recent pathing command executed
     */
    Optional<PathingCommand> mostRecentCommand();
}
