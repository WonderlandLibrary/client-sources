package baritone;

import java.nio.file.*;
import baritone.api.cache.*;
import java.io.*;

public final class v implements IWorldData
{
    public final n a;
    private final u a;
    private final r a;
    public final Path a;
    private int a;
    
    v(final Path a, final int a2) {
        super();
        this.a = a;
        this.a = new n(a.resolve("cache"), a2);
        this.a = new u(a.resolve("waypoints"));
        this.a = new r(a.resolve("containers"));
        this.a = a2;
    }
    
    public final void a() {
        baritone.a.a().execute(this::c);
        baritone.a.a().execute(this::b);
    }
    
    @Override
    public final ICachedWorld getCachedWorld() {
        return this.a;
    }
    
    @Override
    public final IWaypointCollection getWaypoints() {
        return this.a;
    }
    
    @Override
    public final IContainerMemory getContainerMemory() {
        return this.a;
    }
    
    private void b() {
        System.out.println("Started saving saved containers in a new thread");
        try {
            this.a.a();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to save saved containers");
        }
    }
    
    private void c() {
        System.out.println("Started saving the world in a new thread");
        this.a.save();
    }
}
