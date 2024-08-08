package net.futureclient.client;

import net.futureclient.client.modules.miscellaneous.Translate;

public class iB extends XB
{
    public final Translate k;
    
    public iB(final Translate k, final String[] array) {
        this.k = k;
        super(array);
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length != 1) {
            return null;
        }
        final String s;
        if (!(s = array[0]).startsWith("trnsl") || s.length() < 27) {
            return "Invalid Yandex Translate API key. Generate one at https://translate.yandex.com/developers/keys";
        }
        jG.e(s);
        return String.format("Yandex Translate API key has been set to &e%s&7.", s);
    }
    
    @Override
    public String M() {
        return "&e[key]";
    }
}
