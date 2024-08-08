package net.futureclient.client.modules.miscellaneous;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.futureclient.client.s;
import java.io.IOException;
import net.futureclient.client.Oi;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.futureclient.client.dH;
import net.futureclient.client.YH;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.songstealer.Listener2;
import net.futureclient.client.modules.miscellaneous.songstealer.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import java.util.ArrayList;
import net.futureclient.client.pg;
import net.futureclient.client.Category;
import java.io.File;
import net.futureclient.client.G;
import java.util.List;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class SongStealer extends Ea
{
    private NumberValue range;
    private List<G> d;
    private int a;
    private boolean D;
    private File k;
    
    public SongStealer() {
        super("SongStealer", new String[] { "SongStealer", "SongSteal" }, true, -8069141, Category.MISCELLANEOUS);
        this.k = new File(pg.M().M(), "songs");
        this.range = new NumberValue(20.0f, 0.0f, 50.0f, 1, new String[] { "Range", "Ragne", "Rang", "r" });
        final int n = 1;
        this.d = new ArrayList<G>();
        final Value[] array = new Value[n];
        array[0] = this.range;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public void B() {
        this.D = false;
        this.d.clear();
        super.B();
    }
    
    public static Minecraft getMinecraft() {
        return SongStealer.D;
    }
    
    public static Minecraft getMinecraft1() {
        return SongStealer.D;
    }
    
    public void b() {
        final String s = "\u0002l\u001et\u0014v\\=\u00026\u001fw\u0005}\u0013w\u0005";
        this.d.add((G)new YH(10));
        final String format = String.format(dH.M(s), new SimpleDateFormat("HH-mm-ss").format(new Date()));
        try {
            Oi.M(new File(this.k, format), (G[])this.d.<G>toArray(new G[this.d.size()]));
            super.b();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        net.futureclient.client.s.M().M(String.format("Saved song %s\"%s\"%s.", ChatFormatting.WHITE, format, ChatFormatting.GRAY), false);
        super.b();
    }
    
    public static Minecraft getMinecraft2() {
        return SongStealer.D;
    }
    
    public static int e(final SongStealer songStealer) {
        return songStealer.a;
    }
    
    public static Minecraft getMinecraft3() {
        return SongStealer.D;
    }
    
    public static List M(final SongStealer songStealer) {
        return songStealer.d;
    }
    
    public static NumberValue M(final SongStealer songStealer) {
        return songStealer.range;
    }
    
    public static boolean M(final SongStealer songStealer, final boolean d) {
        return songStealer.D = d;
    }
    
    public static int M(final SongStealer songStealer) {
        return songStealer.a++;
    }
    
    public static int M(final SongStealer songStealer, final int a) {
        return songStealer.a = a;
    }
    
    public static boolean M(final SongStealer songStealer) {
        return songStealer.D;
    }
}
