package org.spongepowered.asm.lib.util;

import java.util.*;
import org.spongepowered.asm.lib.*;

public interface ASMifiable
{
    void asmify(final StringBuffer p0, final String p1, final Map<Label, String> p2);
}
