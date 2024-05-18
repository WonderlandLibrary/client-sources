// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class Values
{
    public boolean players;
    public boolean mobs;
    public boolean animals;
    public boolean weapon;
    public boolean teams;
    public boolean invisible;
    public boolean inInventory;
    
    public Values() {
        this.players = true;
        this.mobs = true;
        this.animals = false;
        this.weapon = true;
        this.teams = true;
        this.invisible = false;
        this.inInventory = false;
        this.load();
        this.save();
    }
    
    public boolean isTargetValid(final Entity target) {
        if (target.isInvisible() && !this.invisible) {
            return false;
        }
        if (!this.inInventory) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen != null) {
                return false;
            }
        }
        if (target instanceof EntityMob) {
            if (!this.mobs) {
                return false;
            }
        }
        else if (target instanceof EntityAnimal) {
            if (!this.animals) {
                return false;
            }
        }
        else {
            if (!(target instanceof EntityPlayer)) {
                return false;
            }
            if (this.teams) {
                final Minecraft mc = Minecraft.getMinecraft();
                final EntityPlayer player = (EntityPlayer)target;
                if (mc.thePlayer.getTeam() != null) {
                    final ScorePlayerTeam playerteam = (ScorePlayerTeam)mc.thePlayer.getTeam();
                    if (playerteam.getMembershipCollection().contains(player.getGameProfile().getName())) {
                        return false;
                    }
                }
            }
            if (!this.players) {
                return false;
            }
        }
        return true;
    }
    
    public boolean holdWeapon() {
        if (!this.weapon) {
            return true;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        return mc.thePlayer.getCurrentEquippedItem() != null && (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword || mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemTool);
    }
    
    private File getTargetFile() {
        final File dir = new File("phantom");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File file = new File(dir, "target.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception ex) {}
        }
        return file;
    }
    
    public void load() {
        try {
            final FileReader fr = new FileReader(this.getTargetFile());
            final BufferedReader br = new BufferedReader(fr);
            String Line;
            while ((Line = br.readLine()) != null) {
                final String Line2 = null;
                final String[] args = Line2.split(":");
                final String name = args[0];
                final boolean value = Boolean.parseBoolean(args[1]);
                if (name.equalsIgnoreCase("weapon")) {
                    this.weapon = value;
                }
                else if (name.equalsIgnoreCase("invisible")) {
                    this.invisible = value;
                }
                else if (name.equalsIgnoreCase("mobs")) {
                    this.mobs = value;
                }
                else if (name.equalsIgnoreCase("animals")) {
                    this.animals = value;
                }
                else if (name.equalsIgnoreCase("players")) {
                    this.players = value;
                }
                else if (name.equalsIgnoreCase("teams")) {
                    this.teams = value;
                }
                else {
                    if (!name.equalsIgnoreCase("inInventory")) {
                        continue;
                    }
                    this.inInventory = value;
                }
            }
            br.close();
            fr.close();
        }
        catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    
    public void save() {
        try {
            final FileWriter fw = new FileWriter(this.getTargetFile(), false);
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write("weapon:" + this.weapon);
            bw.newLine();
            bw.write("invisible:" + this.invisible);
            bw.newLine();
            bw.write("players:" + this.players);
            bw.newLine();
            bw.write("mobs:" + this.mobs);
            bw.newLine();
            bw.write("animals:" + this.animals);
            bw.newLine();
            bw.write("teams:" + this.teams);
            bw.newLine();
            bw.write("inInventory:" + this.inInventory);
            bw.close();
            fw.close();
        }
        catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}
