package directionhud;

public enum PositionMode
{
    CUSTOM,
    TOPCENTER,
    TOPLEFT,
    TOPRIGHT,
    MIDDLELEFT,
    MIDDLECENTER,
    MIDDLERIGHT,
    BOTTOMLEFT,
    BOTTOMCENTER,
    BOTTOMRIGHT;

    public static PositionMode getByName(String name)
    {
        for (PositionMode positionmode : (PositionMode[])PositionMode.class.getEnumConstants())
        {
            if (positionmode.name().equals(name))
            {
                return positionmode;
            }
        }

        return CUSTOM;
    }

    public static PositionMode getNext(String nameOfCurrent)
    {
        return getNext(getByName(nameOfCurrent));
    }

    public static PositionMode getNext(PositionMode before)
    {
        PositionMode positionmode = null;
        boolean flag = false;

        for (PositionMode positionmode1 : (PositionMode[])PositionMode.class.getEnumConstants())
        {
            if (positionmode1.equals(before))
            {
                flag = true;
            }
            else if (flag)
            {
                positionmode = positionmode1;
                break;
            }
        }

        return positionmode == null && flag ? CUSTOM : positionmode;
    }
}
