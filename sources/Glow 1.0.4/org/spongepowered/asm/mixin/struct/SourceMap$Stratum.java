package org.spongepowered.asm.mixin.struct;

import java.util.*;

static class Stratum
{
    private static final String STRATUM_MARK = "*S";
    private static final String FILE_MARK = "*F";
    private static final String LINES_MARK = "*L";
    public final String name;
    private final Map<String, File> files;
    
    public Stratum(final String name) {
        super();
        this.files = new LinkedHashMap<String, File>();
        this.name = name;
    }
    
    public File addFile(final int n, final int n2, final String s, final String s2) {
        File file = this.files.get(s2);
        if (file == null) {
            file = new File(this.files.size() + 1, n, n2, s, s2);
            this.files.put(s2, file);
        }
        return file;
    }
    
    void appendTo(final StringBuilder sb) {
        sb.append("*S").append(" ").append(this.name).append("\n");
        sb.append("*F").append("\n");
        final Iterator<File> iterator = this.files.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().appendFile(sb);
        }
        sb.append("*L").append("\n");
        final Iterator<File> iterator2 = this.files.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().appendLines(sb);
        }
    }
}
