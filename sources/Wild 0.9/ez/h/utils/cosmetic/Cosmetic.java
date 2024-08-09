package ez.h.utils.cosmetic;

import ez.h.utils.cosmetic.impl.*;

public class Cosmetic
{
    public static nf getWing(final String s) {
        return new nf("wild/" + s.toLowerCase() + ".png");
    }
    
    public static void renderAccessory(final String[] array, final aed aed, final float n) {
        for (final String s : array) {
            switch (s) {
                case "Dragon_wing": {
                    DragonWing.render(aed, n);
                    break;
                }
            }
        }
    }
    
    public static nf getCape(final String s) {
        return new nf("wild/" + s.toLowerCase() + ".png");
    }
}
