/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieIdentityComparator;

@Contract(threading=ThreadingBehavior.SAFE)
public class BasicCookieStore
implements CookieStore,
Serializable {
    private static final long serialVersionUID = -7581093305228232025L;
    private final TreeSet<Cookie> cookies = new TreeSet<Cookie>(new CookieIdentityComparator());
    private transient ReadWriteLock lock = new ReentrantReadWriteLock();

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.lock = new ReentrantReadWriteLock();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addCookie(Cookie cookie) {
        if (cookie != null) {
            this.lock.writeLock().lock();
            try {
                this.cookies.remove(cookie);
                if (!cookie.isExpired(new Date())) {
                    this.cookies.add(cookie);
                }
            } finally {
                this.lock.writeLock().unlock();
            }
        }
    }

    public void addCookies(Cookie[] cookieArray) {
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                this.addCookie(cookie);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Cookie> getCookies() {
        this.lock.readLock().lock();
        try {
            ArrayList<Cookie> arrayList = new ArrayList<Cookie>(this.cookies);
            return arrayList;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean clearExpired(Date date) {
        if (date == null) {
            return true;
        }
        this.lock.writeLock().lock();
        try {
            boolean bl = false;
            Iterator<Cookie> iterator2 = this.cookies.iterator();
            while (iterator2.hasNext()) {
                if (!iterator2.next().isExpired(date)) continue;
                iterator2.remove();
                bl = true;
            }
            boolean bl2 = bl;
            return bl2;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        this.lock.writeLock().lock();
        try {
            this.cookies.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        this.lock.readLock().lock();
        try {
            String string = this.cookies.toString();
            return string;
        } finally {
            this.lock.readLock().unlock();
        }
    }
}

