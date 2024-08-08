package net.futureclient.client;

public static final class QD extends XB {
    public QD() {
        super(new String[] { "Add", "A" });
    }
    
    @Override
    public String M() {
        return "&e[username] [alias]";
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length == 2) {
            final String s = array[0];
            final String s2 = array[1];
            if (pg.M().M().M(s)) {
                return "That user is already a friend.";
            }
            pg.M().M().e(new rD(s, s2));
            return String.format("Added friend with alias %s.", s2);
        }
        else {
            if (array.length != 1) {
                return null;
            }
            final String s3 = array[0];
            if (pg.M().M().M(s3)) {
                return "That user is already a friend.";
            }
            final ge m = pg.M().M();
            final String s4 = s3;
            m.e(new rD(s4, s4));
            return String.format("Added friend with alias %s.", s3);
        }
    }
}