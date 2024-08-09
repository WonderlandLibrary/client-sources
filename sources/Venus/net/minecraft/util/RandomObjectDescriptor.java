/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.Random;
import java.util.UUID;
import net.minecraft.util.Util;

public class RandomObjectDescriptor {
    private static final String[] FIRST_PART = new String[]{"Slim", "Far", "River", "Silly", "Fat", "Thin", "Fish", "Bat", "Dark", "Oak", "Sly", "Bush", "Zen", "Bark", "Cry", "Slack", "Soup", "Grim", "Hook", "Dirt", "Mud", "Sad", "Hard", "Crook", "Sneak", "Stink", "Weird", "Fire", "Soot", "Soft", "Rough", "Cling", "Scar"};
    private static final String[] SECOND_PART = new String[]{"Fox", "Tail", "Jaw", "Whisper", "Twig", "Root", "Finder", "Nose", "Brow", "Blade", "Fry", "Seek", "Wart", "Tooth", "Foot", "Leaf", "Stone", "Fall", "Face", "Tongue", "Voice", "Lip", "Mouth", "Snail", "Toe", "Ear", "Hair", "Beard", "Shirt", "Fist"};

    public static String getRandomObjectDescriptor(UUID uUID) {
        Random random2 = RandomObjectDescriptor.getRandomFromUUID(uUID);
        return RandomObjectDescriptor.getRandomString(random2, FIRST_PART) + RandomObjectDescriptor.getRandomString(random2, SECOND_PART);
    }

    private static String getRandomString(Random random2, String[] stringArray) {
        return Util.getRandomObject(stringArray, random2);
    }

    private static Random getRandomFromUUID(UUID uUID) {
        return new Random(uUID.hashCode() >> 2);
    }
}

