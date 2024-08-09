package ez.h.features.another;

import java.util.*;
import ez.h.features.*;
import com.google.common.collect.*;

public class XRay extends Feature
{
    public static final HashSet<Integer> blockIDs;
    public List<Integer> KEY_IDS;
    
    @Override
    public void onEnable() {
        XRay.blockIDs.clear();
        try {
            XRay.blockIDs.addAll((Collection<?>)this.KEY_IDS);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        XRay.mc.g.a();
    }
    
    public boolean containsID(final int n) {
        return XRay.blockIDs.contains(n);
    }
    
    @Override
    public void onDisable() {
        XRay.mc.g.a();
        super.onDisable();
    }
    
    static {
        blockIDs = new HashSet<Integer>();
    }
    
    public XRay() {
        super("XRay", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0432\u0438\u0434\u0435\u0442\u044c \u0447\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b.", Category.ANOTHER);
        final Integer[] array = new Integer[0x84 ^ 0xA7];
        array[0] = aow.a(aox.ag);
        array[1] = aow.a(aox.o);
        array[2] = (0xC9 ^ 0xC3);
        array[3] = (0x72 ^ 0x79);
        array[4] = 8;
        array[5] = 9;
        array[6] = (0x99 ^ 0x97);
        array[7] = (0x7A ^ 0x75);
        array[8] = (0xD1 ^ 0xC1);
        array[9] = (0xD7 ^ 0xC2);
        array[0x22 ^ 0x28] = (0xE9 ^ 0xC0);
        array[0x25 ^ 0x2E] = (0xA8 ^ 0x82);
        array[0x1D ^ 0x11] = (0x81 ^ 0xAF);
        array[0x9 ^ 0x4] = (0x51 ^ 0x61);
        array[0x2E ^ 0x20] = (0x4C ^ 0x78);
        array[0x16 ^ 0x19] = (0x44 ^ 0x7C);
        array[0x82 ^ 0x92] = (0x72 ^ 0x4B);
        array[0x3A ^ 0x2B] = (0x3D ^ 0x0);
        array[0xBF ^ 0xAD] = (0x6A ^ 0x54);
        array[0x42 ^ 0x51] = (0x4B ^ 0x2);
        array[0x53 ^ 0x47] = (0x53 ^ 0x7);
        array[0x53 ^ 0x46] = (0x60 ^ 0x2A);
        array[0xA9 ^ 0xBF] = (0xDF ^ 0x86);
        array[0x9C ^ 0x8B] = (0xA1 ^ 0xC6);
        array[0xA6 ^ 0xBE] = (0xE2 ^ 0x96);
        array[0x76 ^ 0x6F] = (0x76 ^ 0x3);
        array[0x33 ^ 0x29] = (0x36 ^ 0x40);
        array[0xB6 ^ 0xAD] = (0xCE ^ 0xB6);
        array[0x1A ^ 0x6] = 67 + 99 - 145 + 108;
        array[0x14 ^ 0x9] = 93 + 126 - 190 + 104;
        array[0x2C ^ 0x32] = 49 + 25 + 12 + 59;
        array[0x33 ^ 0x2C] = 68 + 104 - 90 + 55;
        array[0x74 ^ 0x54] = 151 + 124 - 254 + 131;
        array[0x7D ^ 0x5C] = 81 + 121 - 84 + 35;
        array[0x79 ^ 0x5B] = 149 + 118 - 185 + 72;
        this.KEY_IDS = (List<Integer>)Lists.newArrayList((Object[])array);
    }
}
