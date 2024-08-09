package dev.luvbeeq.baritone.api.process;

import java.nio.file.Path;

public interface IExploreProcess extends IBaritoneProcess {

    void explore(int centerX, int centerZ);

    void applyJsonFilter(Path path, boolean invert) throws Exception;
}
