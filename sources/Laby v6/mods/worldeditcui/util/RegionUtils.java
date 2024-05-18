package mods.worldeditcui.util;

import net.minecraft.client.Minecraft;

public class RegionUtils
{
    private static float rotationYaw;
    private static float rotationPitch;

    public static Vector getDirection(String dirStr)
    {
        dirStr = dirStr.toLowerCase();
        PlayerDirection playerdirection = getPlayerDirection(dirStr);
        System.out.println(playerdirection.name());

        switch (playerdirection)
        {
            case WEST:
            case EAST:
            case SOUTH:
            case NORTH:
            case UP:
            case DOWN:
                return playerdirection.vector();

            default:
                return null;
        }
    }

    private static PlayerDirection getPlayerDirection(String dirStr)
    {
        PlayerDirection playerdirection = null;

        switch (dirStr.charAt(0))
        {
            case 'b':
                playerdirection = getCardinalDirection(180);

            case 'c':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'o':
            case 'p':
            case 'q':
            case 't':
            case 'v':
            default:
                break;

            case 'd':
                playerdirection = PlayerDirection.DOWN;
                break;

            case 'e':
                playerdirection = PlayerDirection.EAST;
                break;

            case 'f':
            case 'm':
                playerdirection = getCardinalDirection(0);
                break;

            case 'l':
                playerdirection = getCardinalDirection(-90);
                break;

            case 'n':
                if (dirStr.indexOf(119) > 0)
                {
                    return PlayerDirection.NORTH_WEST;
                }

                if (dirStr.indexOf(101) > 0)
                {
                    return PlayerDirection.NORTH_EAST;
                }

                playerdirection = PlayerDirection.NORTH;
                break;

            case 'r':
                playerdirection = getCardinalDirection(90);
                break;

            case 's':
                if (dirStr.indexOf(119) > 0)
                {
                    return PlayerDirection.SOUTH_WEST;
                }

                if (dirStr.indexOf(101) > 0)
                {
                    return PlayerDirection.SOUTH_EAST;
                }

                playerdirection = PlayerDirection.SOUTH;
                break;

            case 'u':
                playerdirection = PlayerDirection.UP;
                break;

            case 'w':
                playerdirection = PlayerDirection.WEST;
        }

        return playerdirection;
    }

    private static PlayerDirection getCardinalDirection(int yawOffset)
    {
        if ((double)getPitch() > 67.5D)
        {
            return PlayerDirection.DOWN;
        }
        else if ((double)getPitch() < -67.5D)
        {
            return PlayerDirection.UP;
        }
        else
        {
            double d0 = (double)((getYaw() + (float)yawOffset) % 360.0F);

            if (d0 < 0.0D)
            {
                d0 += 360.0D;
            }

            return getDirection(d0);
        }
    }

    private static float getPitch()
    {
        return rotationPitch;
    }

    private static float getYaw()
    {
        return rotationYaw;
    }

    public static void setLastYawAndPitch()
    {
        rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
    }

    private static PlayerDirection getDirection(double rot)
    {
        return 0.0D <= rot && rot < 22.5D ? PlayerDirection.SOUTH : (22.5D <= rot && rot < 67.5D ? PlayerDirection.SOUTH_WEST : (67.5D <= rot && rot < 112.5D ? PlayerDirection.WEST : (112.5D <= rot && rot < 157.5D ? PlayerDirection.NORTH_WEST : (157.5D <= rot && rot < 202.5D ? PlayerDirection.NORTH : (202.5D <= rot && rot < 247.5D ? PlayerDirection.NORTH_EAST : (247.5D <= rot && rot < 292.5D ? PlayerDirection.EAST : (292.5D <= rot && rot < 337.5D ? PlayerDirection.SOUTH_EAST : (337.5D <= rot && rot < 360.0D ? PlayerDirection.SOUTH : null))))))));
    }

    public static RegionUtils.VectorSet expand(Vector pos1, Vector pos2, Vector... changes)
    {
        for (Vector vector : changes)
        {
            if (vector.getX() > 0.0D)
            {
                if (Math.max(pos1.getX(), pos2.getX()) == pos1.getX())
                {
                    pos1 = pos1.add(new Vector(vector.getX(), 0.0D, 0.0D));
                }
                else
                {
                    pos2 = pos2.add(new Vector(vector.getX(), 0.0D, 0.0D));
                }
            }
            else if (Math.min(pos1.getX(), pos2.getX()) == pos1.getX())
            {
                pos1 = pos1.add(new Vector(vector.getX(), 0.0D, 0.0D));
            }
            else
            {
                pos2 = pos2.add(new Vector(vector.getX(), 0.0D, 0.0D));
            }

            if (vector.getY() > 0.0D)
            {
                if (Math.max(pos1.getY(), pos2.getY()) == pos1.getY())
                {
                    pos1 = pos1.add(new Vector(0.0D, vector.getY(), 0.0D));
                }
                else
                {
                    pos2 = pos2.add(new Vector(0.0D, vector.getY(), 0.0D));
                }
            }
            else if (Math.min(pos1.getY(), pos2.getY()) == pos1.getY())
            {
                pos1 = pos1.add(new Vector(0.0D, vector.getY(), 0.0D));
            }
            else
            {
                pos2 = pos2.add(new Vector(0.0D, vector.getY(), 0.0D));
            }

            if (vector.getZ() > 0.0D)
            {
                if (Math.max(pos1.getZ(), pos2.getZ()) == pos1.getZ())
                {
                    pos1 = pos1.add(new Vector(0.0D, 0.0D, vector.getZ()));
                }
                else
                {
                    pos2 = pos2.add(new Vector(0.0D, 0.0D, vector.getZ()));
                }
            }
            else if (Math.min(pos1.getZ(), pos2.getZ()) == pos1.getZ())
            {
                pos1 = pos1.add(new Vector(0.0D, 0.0D, vector.getZ()));
            }
            else
            {
                pos2 = pos2.add(new Vector(0.0D, 0.0D, vector.getZ()));
            }
        }

        return new RegionUtils.VectorSet(pos1, pos2);
    }

    public static RegionUtils.VectorSet shift(Vector pos1, Vector pos2, Vector... changes)
    {
        for (Vector vector : changes)
        {
            pos1 = pos1.add(vector);
            pos2 = pos2.add(vector);
        }

        return new RegionUtils.VectorSet(pos1, pos2);
    }

    public static RegionUtils.VectorSet contract(Vector pos1, Vector pos2, Vector... changes)
    {
        for (Vector vector : changes)
        {
            if (vector.getX() < 0.0D)
            {
                if (Math.max(pos1.getX(), pos2.getX()) == pos1.getX())
                {
                    pos1 = pos1.add(new Vector(vector.getX(), 0.0D, 0.0D));
                }
                else
                {
                    pos2 = pos2.add(new Vector(vector.getX(), 0.0D, 0.0D));
                }
            }
            else if (Math.min(pos1.getX(), pos2.getX()) == pos1.getX())
            {
                pos1 = pos1.add(new Vector(vector.getX(), 0.0D, 0.0D));
            }
            else
            {
                pos2 = pos2.add(new Vector(vector.getX(), 0.0D, 0.0D));
            }

            if (vector.getY() < 0.0D)
            {
                if (Math.max(pos1.getY(), pos2.getY()) == pos1.getY())
                {
                    pos1 = pos1.add(new Vector(0.0D, vector.getY(), 0.0D));
                }
                else
                {
                    pos2 = pos2.add(new Vector(0.0D, vector.getY(), 0.0D));
                }
            }
            else if (Math.min(pos1.getY(), pos2.getY()) == pos1.getY())
            {
                pos1 = pos1.add(new Vector(0.0D, vector.getY(), 0.0D));
            }
            else
            {
                pos2 = pos2.add(new Vector(0.0D, vector.getY(), 0.0D));
            }

            if (vector.getZ() < 0.0D)
            {
                if (Math.max(pos1.getZ(), pos2.getZ()) == pos1.getZ())
                {
                    pos1 = pos1.add(new Vector(0.0D, 0.0D, vector.getZ()));
                }
                else
                {
                    pos2 = pos2.add(new Vector(0.0D, 0.0D, vector.getZ()));
                }
            }
            else if (Math.min(pos1.getZ(), pos2.getZ()) == pos1.getZ())
            {
                pos1 = pos1.add(new Vector(0.0D, 0.0D, vector.getZ()));
            }
            else
            {
                pos2 = pos2.add(new Vector(0.0D, 0.0D, vector.getZ()));
            }
        }

        return new RegionUtils.VectorSet(pos1, pos2);
    }

    public static class VectorSet
    {
        private Vector vector1;
        private Vector vector2;

        public VectorSet(Vector vector1, Vector vector2)
        {
            this.vector1 = vector1;
            this.vector2 = vector2;
        }

        public Vector getVector1()
        {
            return this.vector1;
        }

        public Vector getVector2()
        {
            return this.vector2;
        }
    }
}
