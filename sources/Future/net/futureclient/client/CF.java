package net.futureclient.client;

public class CF extends XB
{
    public CF() {
        super(new String[] { "Watermark", "setwatermark", "clientname", "setname", "setclientname" });
    }
    
    @Override
    public String M() {
        return "&e[name]";
    }
    
    @Override
    public String M(String[] array) {
        if (array.length >= 1) {
            final StringBuilder sb = new StringBuilder();
            final int length = (array = array).length;
            int i = 0;
            int n = 0;
            while (i < length) {
                sb.append(array[n]);
                final StringBuilder sb2 = sb;
                ++n;
                sb2.append(" ");
                i = n;
            }
            final String trim = sb.toString().trim();
            pg.M().M(trim);
            return String.format("Watermark has been set to %s.", trim);
        }
        return null;
    }
}
