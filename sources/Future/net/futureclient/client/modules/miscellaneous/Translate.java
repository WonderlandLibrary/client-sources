package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.translate.Listener3;
import net.futureclient.client.modules.miscellaneous.translate.Listener2;
import net.futureclient.client.modules.miscellaneous.translate.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.iB;
import net.futureclient.client.pg;
import net.futureclient.client.cA;
import net.futureclient.client.jG;
import net.futureclient.client.utils.Value;
import net.futureclient.client.a;
import net.futureclient.client.mi;
import net.futureclient.client.Category;
import net.futureclient.client.fI;
import net.futureclient.client.dj;
import net.futureclient.client.R;
import net.futureclient.client.Ea;

public class Translate extends Ea
{
    private R<dj> lang;
    private fI D;
    private dj k;
    
    public Translate() {
        super("Translate", new String[] { "Translate", "Translator", "trans", "Lanugage", "Language", "Lang", "Linguo" }, true, -6692885, Category.MISCELLANEOUS);
        this.D = new fI(new mi());
        this.lang = new R<dj>(dj.Wa, new String[] { "Lang", "Lanugage", "Linguo", "Language", "L" });
        this.M(new Value[] { this.lang });
        jG.e("trnsl.1.1.20170518T111332Z.58a1bdbd6f594a69.53b43d8829fb0d32cc1b8fa36cef007dac7717c4");
        new cA(this, "yandexapikey.txt");
        pg.M().M().e((Object)new iB(this, new String[] { "TranslateKey", "TransKey", "YandexKey", "YandKey" }));
        this.M(new n[] { (n)new Listener1(this), (n)new Listener2(this), new Listener3(this) });
    }
    
    public static dj M(final Translate translate, final dj k) {
        return translate.k = k;
    }
    
    public static R M(final Translate translate) {
        return translate.lang;
    }
    
    public static fI M(final Translate translate) {
        return translate.D;
    }
    
    public static dj M(final Translate translate) {
        return translate.k;
    }
    
    public static Minecraft getMinecraft() {
        return Translate.D;
    }
}
