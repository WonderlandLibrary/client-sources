package baritone;

import org.apache.commons.lang3.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.function.*;
import baritone.api.cache.*;
import java.util.*;

public final class w implements IWorldProvider, cr
{
    private static final Map<Path, v> a;
    public v a;
    
    public w() {
        super();
    }
    
    public final void a(final int n) {
        final chd f;
        File a;
        File file2;
        if ((f = w.a.F()) != null) {
            File file;
            if ((file = ((db)((dd)f.a(n).r()).getChunkLoader()).getChunkSaveLocation()).toPath().relativize(w.a.w.toPath()).getNameCount() != 2) {
                file = file.getParentFile();
            }
            file2 = (a = new File(file, "baritone"));
        }
        else {
            String s = w.a.C().b;
            if (SystemUtils.IS_OS_WINDOWS) {
                s = s.replace(":", "_");
            }
            file2 = new File(baritone.a.a(), s);
            a = baritone.a.a();
        }
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(new File(a, "readme.txt"));
            Throwable t = null;
            try {
                fileOutputStream.write("https://github.com/cabaletta/baritone\n".getBytes());
                fileOutputStream.close();
            }
            catch (Throwable t3) {
                final Throwable t2 = t3;
                t = t3;
                throw t2;
            }
            finally {
                if (t != null) {
                    try {
                        fileOutputStream.close();
                    }
                    catch (Throwable t4) {
                        t.addSuppressed(t4);
                    }
                }
                else {
                    fileOutputStream.close();
                }
            }
        }
        catch (IOException ex) {}
        final Path path;
        if (!Files.exists(path = new File(file2, "DIM".concat(String.valueOf(n))).toPath(), new LinkOption[0])) {
            try {
                Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException ex2) {}
        }
        System.out.println("Baritone world data dir: ".concat(String.valueOf(path)));
        synchronized (w.a) {
            this.a = w.a.computeIfAbsent(path, w::a);
        }
    }
    
    @Override
    public final IWorldData getCurrentWorld() {
        return this.a;
    }
    
    private static v a(final int n, final Path path) {
        return new v(path, n);
    }
    
    static {
        a = new HashMap<Path, v>();
    }
}
