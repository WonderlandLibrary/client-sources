package net.futureclient.client;

public class mg extends XB
{
    public mg() {
        super(new String[] { "Prefix", "Pref" });
    }
    
    @Override
    public String M() {
        return "&e[char]";
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length != 1) {
            return null;
        }
        final String s;
        if ((s = array[0]).equalsIgnoreCase(pg.M().M().M())) {
            return "That is already your prefix.";
        }
        pg.M().M().M(s);
        return String.format("Command prefix set to %s.", s);
    }
}
