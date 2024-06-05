package optifine;

import java.util.*;
import org.lwjgl.opengl.*;

static final class Config$1 implements Comparator {
    @Override
    public int compare(final Object o1, final Object o2) {
        final DisplayMode dm1 = (DisplayMode)o1;
        final DisplayMode dm2 = (DisplayMode)o2;
        return (dm1.getWidth() != dm2.getWidth()) ? (dm2.getWidth() - dm1.getWidth()) : ((dm1.getHeight() != dm2.getHeight()) ? (dm2.getHeight() - dm1.getHeight()) : 0);
    }
}