package dev.luvbeeq.baritone.api.pathing.path;

import dev.luvbeeq.baritone.api.pathing.calc.IPath;

/**
 * @author Brady
 * @since 10/8/2018
 */
public interface IPathExecutor {

    IPath getPath();

    int getPosition();
}
