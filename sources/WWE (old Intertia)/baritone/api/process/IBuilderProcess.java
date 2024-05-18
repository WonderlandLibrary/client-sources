package baritone.api.process;

import baritone.api.utils.*;
import java.io.*;

public interface IBuilderProcess extends IBaritoneProcess
{
    void build(final String p0, final ISchematic p1, final fq p2);
    
    boolean build(final String p0, final File p1, final fq p2);
}
