package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.util.file.*;

public final class Freecam extends ModBase
{
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private float yawHead;
    private boolean wasFriend;
    
    public Freecam() {
        super("Freecam", "C", false, ".t freecam");
        this.setDescription("Let's you move outside your body");
    }
    
    @Override
    public void onEnable() {
        this.getWrapper();
        this.posX = MorbidWrapper.getPlayer().u;
        this.getWrapper();
        this.posY = MorbidWrapper.getPlayer().v;
        this.getWrapper();
        this.posZ = MorbidWrapper.getPlayer().w;
        this.getWrapper();
        this.yaw = MorbidWrapper.getPlayer().A;
        this.getWrapper();
        this.pitch = MorbidWrapper.getPlayer().B;
        this.getWrapper();
        this.yawHead = MorbidWrapper.getPlayer().aA;
        this.getWrapper();
        final aab world = MorbidWrapper.getWorld();
        this.getWrapper();
        final bfk bfk;
        final bfk self = bfk = new bfk(world, MorbidWrapper.getPlayer().bS);
        this.getWrapper();
        final double u = MorbidWrapper.getPlayer().u;
        this.getWrapper();
        final double par3 = MorbidWrapper.getPlayer().v - 1.5;
        this.getWrapper();
        bfk.b(u, par3, MorbidWrapper.getPlayer().w);
        self.A = this.yaw;
        self.B = this.pitch;
        self.aA = this.yawHead;
        this.getWrapper();
        MorbidWrapper.mcObj().e.a(-1, self);
        if (!Morbid.getFriends().isFriend(self)) {
            final FriendList friends = Morbid.getFriends();
            this.getWrapper();
            final String bs = MorbidWrapper.getPlayer().bS;
            this.getWrapper();
            friends.addFriend(bs, MorbidWrapper.getPlayer().bS);
            this.wasFriend = false;
        }
        else {
            this.wasFriend = true;
        }
    }
    
    @Override
    public void onDisable() {
        if (!this.wasFriend) {
            final FriendList friends = Morbid.getFriends();
            this.getWrapper();
            friends.delFriend(MorbidWrapper.getPlayer().bS);
        }
        this.getWrapper();
        MorbidWrapper.mcObj().e.b(-1);
        this.getWrapper();
        MorbidWrapper.getPlayer().Z = false;
        this.getWrapper();
        MorbidWrapper.getPlayer().b(this.posX, this.posY, this.posZ);
        this.getWrapper();
        MorbidWrapper.getPlayer().B = this.pitch;
        this.getWrapper();
        MorbidWrapper.getPlayer().A = this.yaw;
        this.getWrapper();
        MorbidWrapper.mcObj().f.a();
        this.wasFriend = false;
    }
    
    @Override
    public void preUpdate() {
        this.getWrapper();
        MorbidWrapper.getPlayer().Z = true;
        this.getWrapper();
        if (!MorbidWrapper.getPlayer().ce.b) {
            this.getWrapper();
            MorbidWrapper.getPlayer().y = 0.0;
            this.getWrapper();
            MorbidWrapper.getPlayer().z = 0.0;
            this.getWrapper();
            MorbidWrapper.getPlayer().x = 0.0;
            this.getWrapper();
            MorbidWrapper.getPlayer().F = false;
            this.getWrapper();
            MorbidWrapper.getPlayer().aP = 0.8f;
            this.getWrapper();
            if (MorbidWrapper.mcObj().z.M.e) {
                this.getWrapper();
                MorbidWrapper.getPlayer().y = 0.4;
            }
            this.getWrapper();
            if (MorbidWrapper.mcObj().z.Q.e) {
                this.getWrapper();
                MorbidWrapper.getPlayer().y = -0.4;
            }
        }
    }
}
