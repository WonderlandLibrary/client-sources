package net.minecraft.nbt;

import com.mojang.authlib.*;
import net.minecraft.util.*;
import com.mojang.authlib.properties.*;
import java.util.*;

public final class NBTUtil
{
    private static final String[] I;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x49 ^ 0x47])["".length()] = I("\u0014,$-", "ZMIHV");
        NBTUtil.I[" ".length()] = I("6\u0015\u0017,", "xtzIE");
        NBTUtil.I["  ".length()] = I("\u001a4", "SPJhk");
        NBTUtil.I["   ".length()] = I("&-", "oIWfH");
        NBTUtil.I[0x60 ^ 0x64] = I("\u0014\b*\n\u00116\u000e,\u001f\u0007", "DzEzt");
        NBTUtil.I[0x38 ^ 0x3D] = I("%$-\t6\u0007\"+\u001c ", "uVByS");
        NBTUtil.I[0x55 ^ 0x53] = I("\u0014\u000e\u0003\r\u0012", "Booxw");
        NBTUtil.I[0xB0 ^ 0xB7] = I("\u0005\u001d-\u001c\"\"\u00018\u0017", "VtJrC");
        NBTUtil.I[0x5C ^ 0x54] = I("6%\f=\b\u00119\u00196", "eLkSi");
        NBTUtil.I[0x57 ^ 0x5E] = I("'\u0005\u000e\u0016", "idcss");
        NBTUtil.I[0x6 ^ 0xC] = I("%\f", "lheEJ");
        NBTUtil.I[0x7 ^ 0xC] = I(".\u001b(:6", "xzDOS");
        NBTUtil.I[0x6E ^ 0x62] = I("\u0016\u0001\u001d/\u00141\u001d\b$", "EhzAu");
        NBTUtil.I[0x22 ^ 0x2F] = I("#\u00046\n<\u0001\u00020\u001f*", "svYzY");
    }
    
    public static NBTTagCompound writeGameProfile(final NBTTagCompound nbtTagCompound, final GameProfile gameProfile) {
        if (!StringUtils.isNullOrEmpty(gameProfile.getName())) {
            nbtTagCompound.setString(NBTUtil.I[0x1D ^ 0x14], gameProfile.getName());
        }
        if (gameProfile.getId() != null) {
            nbtTagCompound.setString(NBTUtil.I[0x6D ^ 0x67], gameProfile.getId().toString());
        }
        if (!gameProfile.getProperties().isEmpty()) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            final Iterator<String> iterator = gameProfile.getProperties().keySet().iterator();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                final NBTTagList list = new NBTTagList();
                final Iterator iterator2 = gameProfile.getProperties().get((Object)s).iterator();
                "".length();
                if (3 < -1) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final Property property = iterator2.next();
                    final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
                    nbtTagCompound3.setString(NBTUtil.I[0x19 ^ 0x12], property.getValue());
                    if (property.hasSignature()) {
                        nbtTagCompound3.setString(NBTUtil.I[0x5D ^ 0x51], property.getSignature());
                    }
                    list.appendTag(nbtTagCompound3);
                }
                nbtTagCompound2.setTag(s, list);
            }
            nbtTagCompound.setTag(NBTUtil.I[0x76 ^ 0x7B], nbtTagCompound2);
        }
        return nbtTagCompound;
    }
    
    public static GameProfile readGameProfileFromNBT(final NBTTagCompound nbtTagCompound) {
        String string = null;
        String string2 = null;
        if (nbtTagCompound.hasKey(NBTUtil.I["".length()], 0xAC ^ 0xA4)) {
            string = nbtTagCompound.getString(NBTUtil.I[" ".length()]);
        }
        if (nbtTagCompound.hasKey(NBTUtil.I["  ".length()], 0xBF ^ 0xB7)) {
            string2 = nbtTagCompound.getString(NBTUtil.I["   ".length()]);
        }
        if (StringUtils.isNullOrEmpty(string) && StringUtils.isNullOrEmpty(string2)) {
            return null;
        }
        UUID fromString;
        try {
            fromString = UUID.fromString(string2);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        catch (Throwable t) {
            fromString = null;
        }
        final GameProfile gameProfile = new GameProfile(fromString, string);
        if (nbtTagCompound.hasKey(NBTUtil.I[0x9C ^ 0x98], 0x9 ^ 0x3)) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(NBTUtil.I[0xBD ^ 0xB8]);
            final Iterator<String> iterator = compoundTag.getKeySet().iterator();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                final NBTTagList tagList = compoundTag.getTagList(s, 0x71 ^ 0x7B);
                int i = "".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (i < tagList.tagCount()) {
                    final NBTTagCompound compoundTag2 = tagList.getCompoundTagAt(i);
                    final String string3 = compoundTag2.getString(NBTUtil.I[0xA4 ^ 0xA2]);
                    if (compoundTag2.hasKey(NBTUtil.I[0x35 ^ 0x32], 0x88 ^ 0x80)) {
                        gameProfile.getProperties().put((Object)s, (Object)new Property(s, string3, compoundTag2.getString(NBTUtil.I[0x74 ^ 0x7C])));
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                    else {
                        gameProfile.getProperties().put((Object)s, (Object)new Property(s, string3));
                    }
                    ++i;
                }
            }
        }
        return gameProfile;
    }
    
    public static boolean func_181123_a(final NBTBase nbtBase, final NBTBase nbtBase2, final boolean b) {
        if (nbtBase == nbtBase2) {
            return " ".length() != 0;
        }
        if (nbtBase == null) {
            return " ".length() != 0;
        }
        if (nbtBase2 == null) {
            return "".length() != 0;
        }
        if (!nbtBase.getClass().equals(nbtBase2.getClass())) {
            return "".length() != 0;
        }
        if (nbtBase instanceof NBTTagCompound) {
            final NBTTagCompound nbtTagCompound = (NBTTagCompound)nbtBase;
            final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtBase2;
            final Iterator<String> iterator = nbtTagCompound.getKeySet().iterator();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                if (!func_181123_a(nbtTagCompound.getTag(s), nbtTagCompound2.getTag(s), b)) {
                    return "".length() != 0;
                }
            }
            return " ".length() != 0;
        }
        else {
            if (!(nbtBase instanceof NBTTagList) || !b) {
                return nbtBase.equals(nbtBase2);
            }
            final NBTTagList list = (NBTTagList)nbtBase;
            final NBTTagList list2 = (NBTTagList)nbtBase2;
            if (list.tagCount() == 0) {
                if (list2.tagCount() == 0) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            else {
                int i = "".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
                while (i < list.tagCount()) {
                    final NBTBase value = list.get(i);
                    int n = "".length();
                    int j = "".length();
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                    while (j < list2.tagCount()) {
                        if (func_181123_a(value, list2.get(j), b)) {
                            n = " ".length();
                            "".length();
                            if (4 <= 3) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++j;
                        }
                    }
                    if (n == 0) {
                        return "".length() != 0;
                    }
                    ++i;
                }
                return " ".length() != 0;
            }
        }
    }
}
