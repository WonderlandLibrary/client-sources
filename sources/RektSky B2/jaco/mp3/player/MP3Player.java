package jaco.mp3.player;

import javax.swing.*;
import jaco.mp3.player.plaf.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class MP3Player extends JPanel
{
    public static final String UI_CLASS_ID;
    private final Random a;
    private List b;
    private List c;
    private int d;
    private boolean e;
    private boolean f;
    private volatile boolean g;
    private volatile boolean h;
    private volatile boolean i;
    
    static {
        UI_CLASS_ID = String.valueOf(MP3Player.class.getName()) + "UI";
        if (UIManager.getDefaults().get(MP3Player.UI_CLASS_ID) == null) {
            setDefaultUI(MP3PlayerUI.class);
        }
    }
    
    @Override
    public String getUIClassID() {
        return MP3Player.UI_CLASS_ID;
    }
    
    public static void setDefaultUI(final Class clazz) {
        UIManager.getDefaults().put(MP3Player.UI_CLASS_ID, clazz.getName());
    }
    
    public MP3Player() {
        this.a = new Random();
        this.c = new Vector();
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = false;
    }
    
    public MP3Player(final URL... array) {
        this.a = new Random();
        this.c = new Vector();
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = false;
        for (int length = array.length, i = 0; i < length; ++i) {
            this.addToPlayList(array[i]);
        }
    }
    
    public MP3Player(final File... array) {
        this.a = new Random();
        this.c = new Vector();
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = false;
        for (int length = array.length, i = 0; i < length; ++i) {
            this.addToPlayList(array[i]);
        }
    }
    
    public synchronized void play() {
        if (this.b != null) {
            final Iterator<c> iterator = this.b.iterator();
            while (iterator.hasNext()) {
                iterator.next().a();
            }
        }
        if (this.g) {
            this.g = false;
            return;
        }
        this.a();
        this.h = false;
        final b b;
        (b = new b(this)).setDaemon(true);
        b.start();
    }
    
    public synchronized void pause() {
        if (this.b != null) {
            final Iterator<c> iterator = this.b.iterator();
            while (iterator.hasNext()) {
                iterator.next().b();
            }
        }
        this.g = true;
    }
    
    public synchronized void stop() {
        if (this.b != null) {
            final Iterator iterator = this.b.iterator();
            while (iterator.hasNext()) {
                iterator.next();
            }
        }
        this.a();
    }
    
    private void a() {
        this.g = false;
        this.i = true;
        while (this.i && !this.h) {
            try {
                Thread.sleep(10L);
            }
            catch (Exception ex) {}
        }
        this.i = false;
        this.h = true;
    }
    
    public synchronized void skipForward() {
        if (this.e) {
            this.d = this.a.nextInt(this.c.size());
            this.play();
            return;
        }
        if (this.d >= this.c.size() - 1) {
            if (this.f) {
                this.d = 0;
                this.play();
            }
        }
        else {
            ++this.d;
            this.play();
        }
    }
    
    public synchronized void skipBackward() {
        if (this.e) {
            this.d = this.a.nextInt(this.c.size());
            this.play();
            return;
        }
        if (this.d <= 0) {
            if (this.f) {
                this.d = this.c.size() - 1;
                this.play();
            }
        }
        else {
            --this.d;
            this.play();
        }
    }
    
    public boolean isPaused() {
        return this.g;
    }
    
    public boolean isStopped() {
        return this.h;
    }
    
    public synchronized void addMP3PlayerListener(final c c) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        this.b.add(c);
    }
    
    public synchronized void removeMP3PlayerListener(final c c) {
        this.b.remove(c);
    }
    
    public synchronized void removeAllMP3PlayerListeners() {
        this.b.clear();
    }
    
    public void addToPlayList(final URL url) {
        this.c.add(url);
    }
    
    public void addToPlayList(final File file) {
        try {
            this.c.add(file.toURI().toURL());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public List getPlayList() {
        return this.c;
    }
    
    public boolean isShuffle() {
        return this.e;
    }
    
    public void setShuffle(final boolean e) {
        this.e = e;
    }
    
    public boolean isRepeat() {
        return this.f;
    }
    
    public void setRepeat(final boolean f) {
        this.f = f;
    }
}
