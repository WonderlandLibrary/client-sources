// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.person;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public final class PlayerManager
{
    private List<String> friends;
    private List<String> enemies;
    
    public PlayerManager() {
        this.friends = new ArrayList<String>();
        this.enemies = new ArrayList<String>();
    }
    
    public void addFriend(final String name) {
        for (final String friend : this.friends) {
            if (friend.equalsIgnoreCase(name)) {
                return;
            }
        }
        this.friends.add(name);
    }
    
    public void addEnemy(final String name) {
        for (final String enemy : this.enemies) {
            if (enemy.equalsIgnoreCase(name)) {
                return;
            }
        }
        this.enemies.add(name);
    }
    
    public void removeFriend(final String name) {
        for (int i = 0; i < this.friends.size(); ++i) {
            if (this.friends.get(i).equalsIgnoreCase(name)) {
                this.friends.remove(this.friends.get(i));
                --i;
            }
        }
    }
    
    public void removeEnemy(final String name) {
        for (int i = 0; i < this.enemies.size(); ++i) {
            if (this.enemies.get(i).equalsIgnoreCase(name)) {
                this.enemies.remove(this.enemies.get(i));
                --i;
            }
        }
    }
    
    public void clearAll() {
        this.clearFriends();
        this.clearEnemies();
    }
    
    public void clearEnemies() {
        this.enemies.clear();
    }
    
    public void clearFriends() {
        this.friends.clear();
    }
    
    public boolean isFriend(final String name) {
        for (final String friend : this.friends) {
            if (friend.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isEnemy(final String name) {
        for (final String enemy : this.enemies) {
            if (enemy.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    public List<String> getFriends() {
        return this.friends;
    }
    
    public void setFriends(final List<String> friends) {
        this.friends = friends;
    }
    
    public List<String> getEnemies() {
        return this.enemies;
    }
    
    public void setEnemies(final List<String> enemies) {
        this.enemies = enemies;
    }
}
