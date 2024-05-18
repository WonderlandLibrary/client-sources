package optfine;

import java.lang.reflect.*;
import net.minecraft.world.chunk.*;
import java.util.*;

public class ChunkUtils
{
    private static boolean fieldHasEntitiesMissing;
    private static final String[] I;
    private static Field fieldHasEntities;
    
    public static boolean hasEntities(final Chunk chunk) {
        if (ChunkUtils.fieldHasEntities == null) {
            if (ChunkUtils.fieldHasEntitiesMissing) {
                return " ".length() != 0;
            }
            ChunkUtils.fieldHasEntities = fildFieldHasEntities(chunk);
            if (ChunkUtils.fieldHasEntities == null) {
                ChunkUtils.fieldHasEntitiesMissing = (" ".length() != 0);
                return " ".length() != 0;
            }
        }
        try {
            return ChunkUtils.fieldHasEntities.getBoolean(chunk);
        }
        catch (Exception ex) {
            Config.warn(ChunkUtils.I["".length()]);
            Config.warn(String.valueOf(ex.getClass().getName()) + ChunkUtils.I[" ".length()] + ex.getMessage());
            ChunkUtils.fieldHasEntitiesMissing = (" ".length() != 0);
            return " ".length() != 0;
        }
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static Field fildFieldHasEntities(final Chunk chunk) {
        try {
            final ArrayList<Field> list = new ArrayList<Field>();
            final ArrayList<Boolean> list2 = new ArrayList<Boolean>();
            final Field[] declaredFields = Chunk.class.getDeclaredFields();
            int i = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (i < declaredFields.length) {
                final Field field = declaredFields[i];
                if (field.getType() == Boolean.TYPE) {
                    field.setAccessible(" ".length() != 0);
                    list.add(field);
                    list2.add((Boolean)field.get(chunk));
                }
                ++i;
            }
            chunk.setHasEntities("".length() != 0);
            final ArrayList<Boolean> list3 = new ArrayList<Boolean>();
            final Iterator<Object> iterator = list.iterator();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                list3.add((Boolean)iterator.next().get(chunk));
            }
            chunk.setHasEntities(" ".length() != 0);
            final ArrayList<Boolean> list4 = new ArrayList<Boolean>();
            final Iterator<Object> iterator2 = list.iterator();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (iterator2.hasNext()) {
                list4.add((Boolean)iterator2.next().get(chunk));
            }
            final ArrayList<Field> list5 = new ArrayList<Field>();
            int j = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (j < list.size()) {
                final Field field2 = list.get(j);
                final Boolean b = list3.get(j);
                final Boolean b2 = list4.get(j);
                if (!b && b2) {
                    list5.add(field2);
                    field2.set(chunk, list2.get(j));
                }
                ++j;
            }
            if (list5.size() == " ".length()) {
                return (Field)list5.get("".length());
            }
        }
        catch (Exception ex) {
            Config.warn(String.valueOf(ex.getClass().getName()) + ChunkUtils.I["  ".length()] + ex.getMessage());
        }
        Config.warn(ChunkUtils.I["   ".length()]);
        return null;
    }
    
    private static void I() {
        (I = new String[0xE ^ 0xA])["".length()] = I("\u0004\n\u0016\u001f:a\u001b\u0005\u001c$(\u0016\u0003P\u000b)\r\n\u001bf)\u0019\u00175&5\u0011\u0010\u0019-2", "AxdpH");
        ChunkUtils.I[" ".length()] = I("r", "RnxjA");
        ChunkUtils.I["  ".length()] = I("s", "SBsYp");
        ChunkUtils.I["   ".length()] = I("\u0002>\"\f\u0011g*9\r\u0007.\"7C /9>\bM/-#&\r3%$\n\u00064", "GLPcc");
    }
    
    static {
        I();
        ChunkUtils.fieldHasEntities = null;
        ChunkUtils.fieldHasEntitiesMissing = ("".length() != 0);
    }
}
