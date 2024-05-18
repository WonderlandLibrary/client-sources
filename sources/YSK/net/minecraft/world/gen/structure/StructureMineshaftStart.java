package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;

public class StructureMineshaftStart extends StructureStart
{
    public StructureMineshaftStart(final World world, final Random random, final int n, final int n2) {
        super(n, n2);
        final StructureMineshaftPieces.Room room = new StructureMineshaftPieces.Room("".length(), random, (n << (0xBB ^ 0xBF)) + "  ".length(), (n2 << (0xAB ^ 0xAF)) + "  ".length());
        this.components.add(room);
        room.buildComponent(room, this.components, random);
        this.updateBoundingBox();
        this.markAvailableHeight(world, random, 0xB4 ^ 0xBE);
    }
    
    public StructureMineshaftStart() {
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
