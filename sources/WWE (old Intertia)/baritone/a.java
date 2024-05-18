package baritone;

import java.util.*;
import baritone.api.event.listener.*;
import baritone.api.*;
import baritone.api.utils.*;
import baritone.api.pathing.calc.*;
import baritone.api.cache.*;
import baritone.api.process.*;
import baritone.api.behavior.*;
import java.util.concurrent.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;

public final class a implements IBaritone
{
    private static ThreadPoolExecutor a;
    private static File a;
    private boolean a;
    public y a;
    public List<b> a;
    public j a;
    public d a;
    public f a;
    public c a;
    public ct a;
    public ce a;
    public ch a;
    public cf a;
    public ca a;
    public bs a;
    public cd a;
    public cw a;
    private IPlayerContext a;
    public w a;
    public cn a;
    
    a() {
        super();
        this.a = new y(this);
    }
    
    @Override
    public final synchronized void init() {
        if (this.a) {
            return;
        }
        this.a = dm.a;
        this.a = new ArrayList<b>();
        this.a = new j(this);
        this.a = new d(this);
        this.a = new f(this);
        this.a = new c(this);
        this.a = new ct(this);
        new co(this);
        this.a = new cw(this);
        this.a = new ce(this);
        this.a = new ch(this);
        this.a = new ca(this);
        this.a = new cf(this);
        this.a = new bs(this);
        this.a = new cd(this);
        this.a = new w();
        if (cj.a) {
            this.a.registerEventListener(cj.a);
        }
        this.a = true;
    }
    
    @Override
    public final IPlayerContext getPlayerContext() {
        return this.a;
    }
    
    @Override
    public final IEventBus getGameEventHandler() {
        return this.a;
    }
    
    public static Settings a() {
        return BaritoneAPI.getSettings();
    }
    
    public static File a() {
        return baritone.a.a;
    }
    
    public static Executor a() {
        return baritone.a.a;
    }
    
    @Override
    public final IInputOverrideHandler getInputOverrideHandler() {
        return this.a;
    }
    
    @Override
    public final IPathingControlManager getPathingControlManager() {
        return this.a;
    }
    
    @Override
    public final IWorldProvider getWorldProvider() {
        return this.a;
    }
    
    @Override
    public final IGetToBlockProcess getGetToBlockProcess() {
        return this.a;
    }
    
    @Override
    public final ICustomGoalProcess getCustomGoalProcess() {
        return this.a;
    }
    
    @Override
    public final IBuilderProcess getBuilderProcess() {
        return this.a;
    }
    
    @Override
    public final IMineProcess getMineProcess() {
        return this.a;
    }
    
    @Override
    public final IFollowProcess getFollowProcess() {
        return this.a;
    }
    
    @Override
    public final ILookBehavior getLookBehavior() {
        return this.a;
    }
    
    @Override
    public final IPathingBehavior getPathingBehavior() {
        return this.a;
    }
    
    static {
        baritone.a.a = new ThreadPoolExecutor(4, 0, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        if (!Files.exists((baritone.a.a = new File(bib.z().w, "baritone")).toPath(), new LinkOption[0])) {
            try {
                Files.createDirectories(baritone.a.a.toPath(), (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException ex) {}
        }
    }
}
