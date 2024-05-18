package baritone;

import baritone.api.cache.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class u implements IWaypointCollection
{
    private final Path a;
    private final Map<IWaypoint$Tag, Set<IWaypoint>> a;
    
    u(final Path a) {
        super();
        this.a = a;
        if (!Files.exists(a, new LinkOption[0])) {
            try {
                Files.createDirectories(a, (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException ex) {}
        }
        System.out.println("Would save waypoints to ".concat(String.valueOf(a)));
        this.a = new HashMap<IWaypoint$Tag, Set<IWaypoint>>();
        this.a();
    }
    
    private void a() {
        IWaypoint$Tag[] values;
        for (int length = (values = IWaypoint$Tag.values()).length, i = 0; i < length; ++i) {
            this.a(values[i]);
        }
    }
    
    private synchronized void a(final IWaypoint$Tag waypoint$Tag) {
        this.a.put(waypoint$Tag, new HashSet<IWaypoint>());
        final Path resolve;
        if (!Files.exists(resolve = this.a.resolve(waypoint$Tag.name().toLowerCase() + ".mp4"), new LinkOption[0])) {
            return;
        }
        try {
            final FileInputStream fileInputStream = new FileInputStream(resolve.toFile());
            Throwable t = null;
            try {
                final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                Throwable t2 = null;
                try {
                    final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
                    Throwable t3 = null;
                    try {
                        final long long1;
                        if ((long1 = dataInputStream.readLong()) != 121977993584L) {
                            throw new IOException("Bad magic value ".concat(String.valueOf(long1)));
                        }
                        long long2 = dataInputStream.readLong();
                        while (long2-- > 0L) {
                            this.a.get(waypoint$Tag).add(new t(dataInputStream.readUTF(), waypoint$Tag, new et(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt()), dataInputStream.readLong()));
                        }
                        dataInputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataInputStream.close();
                            }
                            catch (Throwable t6) {
                                t3.addSuppressed(t6);
                            }
                        }
                        else {
                            dataInputStream.close();
                        }
                    }
                    bufferedInputStream.close();
                }
                catch (Throwable t8) {
                    final Throwable t7 = t8;
                    t2 = t8;
                    throw t7;
                }
                finally {
                    if (t2 != null) {
                        try {
                            bufferedInputStream.close();
                        }
                        catch (Throwable t9) {
                            t2.addSuppressed(t9);
                        }
                    }
                    else {
                        bufferedInputStream.close();
                    }
                }
                fileInputStream.close();
            }
            catch (Throwable t11) {
                final Throwable t10 = t11;
                t = t11;
                throw t10;
            }
            finally {
                if (t != null) {
                    try {
                        fileInputStream.close();
                    }
                    catch (Throwable t12) {
                        t.addSuppressed(t12);
                    }
                }
                else {
                    fileInputStream.close();
                }
            }
        }
        catch (IOException ex) {}
    }
    
    private synchronized void b(final IWaypoint$Tag waypoint$Tag) {
        final Path resolve = this.a.resolve(waypoint$Tag.name().toLowerCase() + ".mp4");
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(resolve.toFile());
            Throwable t = null;
            try {
                final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                Throwable t2 = null;
                try {
                    final DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
                    Throwable t3 = null;
                    try {
                        dataOutputStream.writeLong(121977993584L);
                        dataOutputStream.writeLong(this.a.get(waypoint$Tag).size());
                        for (final IWaypoint waypoint : this.a.get(waypoint$Tag)) {
                            dataOutputStream.writeUTF(waypoint.getName());
                            dataOutputStream.writeLong(waypoint.getCreationTimestamp());
                            dataOutputStream.writeInt(waypoint.getLocation().p());
                            dataOutputStream.writeInt(waypoint.getLocation().q());
                            dataOutputStream.writeInt(waypoint.getLocation().r());
                        }
                        dataOutputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataOutputStream.close();
                            }
                            catch (Throwable t6) {
                                t3.addSuppressed(t6);
                            }
                        }
                        else {
                            dataOutputStream.close();
                        }
                    }
                    bufferedOutputStream.close();
                }
                catch (Throwable t8) {
                    final Throwable t7 = t8;
                    t2 = t8;
                    throw t7;
                }
                finally {
                    if (t2 != null) {
                        try {
                            bufferedOutputStream.close();
                        }
                        catch (Throwable t9) {
                            t2.addSuppressed(t9);
                        }
                    }
                    else {
                        bufferedOutputStream.close();
                    }
                }
                fileOutputStream.close();
            }
            catch (Throwable t11) {
                final Throwable t10 = t11;
                t = t11;
                throw t10;
            }
            finally {
                if (t != null) {
                    try {
                        fileOutputStream.close();
                    }
                    catch (Throwable t12) {
                        t.addSuppressed(t12);
                    }
                }
                else {
                    fileOutputStream.close();
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public final void addWaypoint(final IWaypoint waypoint) {
        if (this.a.get(waypoint.getTag()).add(waypoint)) {
            this.b(waypoint.getTag());
        }
    }
    
    @Override
    public final void removeWaypoint(final IWaypoint waypoint) {
        if (this.a.get(waypoint.getTag()).remove(waypoint)) {
            this.b(waypoint.getTag());
        }
    }
    
    @Override
    public final IWaypoint getMostRecentByTag(final IWaypoint$Tag waypoint$Tag) {
        return this.a.get(waypoint$Tag).stream().min(Comparator.comparingLong((ToLongFunction<? super IWaypoint>)u::a)).orElse(null);
    }
    
    @Override
    public final Set<IWaypoint> getByTag(final IWaypoint$Tag waypoint$Tag) {
        return Collections.unmodifiableSet((Set<? extends IWaypoint>)this.a.get(waypoint$Tag));
    }
    
    @Override
    public final Set<IWaypoint> getAllWaypoints() {
        return this.a.values().stream().flatMap((Function<? super Set<IWaypoint>, ? extends Stream<?>>)Collection::stream).collect((Collector<? super Object, ?, Set<IWaypoint>>)Collectors.toSet());
    }
    
    private static long a(final IWaypoint waypoint) {
        return -waypoint.getCreationTimestamp();
    }
}
