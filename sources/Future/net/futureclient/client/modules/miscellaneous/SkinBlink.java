package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.futureclient.client.modules.miscellaneous.skinblink.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import java.util.Set;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class SkinBlink extends Ea
{
    private Value<Boolean> random;
    private Timer a;
    private NumberValue delay;
    private Set k;
    
    public SkinBlink() {
        super("SkinBlink", new String[] { "SkinBlink" }, true, -15605781, Category.MISCELLANEOUS);
        this.delay = new NumberValue(0.1f, 0.0f, 20.0f, 1.273197475E-314, new String[] { "Delay", "Sped", "Speed", "Del", "D" });
        this.random = new Value<Boolean>(false, new String[] { "Random", "Randomization", "Rand" });
        final int n = 2;
        this.a = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.random;
        array[1] = this.delay;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    public void B() {
        super.B();
        this.k = SkinBlink.D.gameSettings.getModelParts();
    }
    
    public void b() {
        super.b();
        final EnumPlayerModelParts[] values;
        final int length = (values = EnumPlayerModelParts.values()).length;
        int i = 0;
        int n = 0;
        while (i < length) {
            final EnumPlayerModelParts enumPlayerModelParts = values[n];
            final GameSettings gameSettings = SkinBlink.D.gameSettings;
            final Set k = this.k;
            final EnumPlayerModelParts enumPlayerModelParts2 = enumPlayerModelParts;
            ++n;
            gameSettings.setModelPartEnabled(enumPlayerModelParts2, k.contains(enumPlayerModelParts2));
            i = n;
        }
    }
    
    public static Minecraft getMinecraft() {
        return SkinBlink.D;
    }
    
    public static Minecraft getMinecraft1() {
        return SkinBlink.D;
    }
    
    public static NumberValue M(final SkinBlink skinBlink) {
        return skinBlink.delay;
    }
    
    public static Timer M(final SkinBlink skinBlink) {
        return skinBlink.a;
    }
    
    public static Value M(final SkinBlink skinBlink) {
        return skinBlink.random;
    }
}
