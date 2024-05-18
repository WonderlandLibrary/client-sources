package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class RandomPositionGenerator
{
    private static Vec3 staticVector;
    
    private static Vec3 findRandomTargetBlock(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        final Random rng = entityCreature.getRNG();
        int n3 = "".length();
        int length = "".length();
        int length2 = "".length();
        int length3 = "".length();
        float n4 = -99999.0f;
        int length4;
        if (entityCreature.hasHome()) {
            final double n5 = entityCreature.getHomePosition().distanceSq(MathHelper.floor_double(entityCreature.posX), MathHelper.floor_double(entityCreature.posY), MathHelper.floor_double(entityCreature.posZ)) + 4.0;
            final double n6 = entityCreature.getMaximumHomeDistance() + n;
            int n7;
            if (n5 < n6 * n6) {
                n7 = " ".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                n7 = "".length();
            }
            length4 = n7;
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            length4 = "".length();
        }
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < (0x9 ^ 0x3)) {
            int n8 = rng.nextInt("  ".length() * n + " ".length()) - n;
            final int n9 = rng.nextInt("  ".length() * n2 + " ".length()) - n2;
            int n10 = rng.nextInt("  ".length() * n + " ".length()) - n;
            if (vec3 == null || n8 * vec3.xCoord + n10 * vec3.zCoord >= 0.0) {
                if (entityCreature.hasHome() && n > " ".length()) {
                    final BlockPos homePosition = entityCreature.getHomePosition();
                    if (entityCreature.posX > homePosition.getX()) {
                        n8 -= rng.nextInt(n / "  ".length());
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        n8 += rng.nextInt(n / "  ".length());
                    }
                    if (entityCreature.posZ > homePosition.getZ()) {
                        n10 -= rng.nextInt(n / "  ".length());
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        n10 += rng.nextInt(n / "  ".length());
                    }
                }
                final int n11 = n8 + MathHelper.floor_double(entityCreature.posX);
                final int n12 = n9 + MathHelper.floor_double(entityCreature.posY);
                final int n13 = n10 + MathHelper.floor_double(entityCreature.posZ);
                final BlockPos blockPos = new BlockPos(n11, n12, n13);
                if (length4 == 0 || entityCreature.isWithinHomeDistanceFromPosition(blockPos)) {
                    final float blockPathWeight = entityCreature.getBlockPathWeight(blockPos);
                    if (blockPathWeight > n4) {
                        n4 = blockPathWeight;
                        length = n11;
                        length2 = n12;
                        length3 = n13;
                        n3 = " ".length();
                    }
                }
            }
            ++i;
        }
        if (n3 != 0) {
            return new Vec3(length, length2, length3);
        }
        return null;
    }
    
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static Vec3 findRandomTargetBlockAwayFrom(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        RandomPositionGenerator.staticVector = new Vec3(entityCreature.posX, entityCreature.posY, entityCreature.posZ).subtract(vec3);
        return findRandomTargetBlock(entityCreature, n, n2, RandomPositionGenerator.staticVector);
    }
    
    public static Vec3 findRandomTargetBlockTowards(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        RandomPositionGenerator.staticVector = vec3.subtract(entityCreature.posX, entityCreature.posY, entityCreature.posZ);
        return findRandomTargetBlock(entityCreature, n, n2, RandomPositionGenerator.staticVector);
    }
    
    static {
        RandomPositionGenerator.staticVector = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static Vec3 findRandomTarget(final EntityCreature entityCreature, final int n, final int n2) {
        return findRandomTargetBlock(entityCreature, n, n2, null);
    }
}
