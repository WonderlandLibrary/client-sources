package wwe;

public enum wwe/Foie
{
    public static final wwe/Foie iNhF;
    public static final wwe/Foie laFO;
    public static final wwe/Foie Dnge;
    public static final wwe/Foie UwxW;
    public static final wwe/Foie sSpe;
    public static final wwe/Foie yGLD;
    public static final wwe/Foie OHcM;
    public static final wwe/Foie xwDq;
    public static final wwe/Foie LtQS;
    public static final wwe/Foie DFPB;
    public static final wwe/Foie ADwi;
    public static final wwe/Foie MHTz;
    public String XGUi;
    public String[] xlWb;
    public String[] Otco;
    private static final wwe/Foie[] pvoo;
    
    public static wwe/Foie[] DsUE() {
        return wwe/Foie.pvoo.clone();
    }
    
    public static wwe/Foie bgAb(final String s) {
        return Enum.valueOf(wwe/Foie.class, s);
    }
    
    private wwe/Foie(final String xgUi, final String[] array) {
        this.XGUi = xgUi;
        this.xlWb = array;
        this.Otco = array;
    }
    
    static {
        wwe/Foie.iNhF = new wwe/Foie("HELP", 0, "Help", new String[] { "-tps, -ping, -myping, -ping playername, -worstping, -bestping", "Hey, !object1 the commands are: -tps, -ping, -myping, -ping playername, -worstping, -bestping" });
        wwe/Foie.laFO = new wwe/Foie("HIYOURNAME", 1, "Welcoming you", new String[] { "Hello !object1 hows you day today?", "Hi !object1 hows you day today?", "Hello !object1 hows you day today so far?", "Hi !object1 hows you day today so far?", "Hello !object1 hope your having a good day?", "Hi !object1 hope your having a good day?", "Hello !object1 hope your having a great day?", "Hi !object1 hope your having a great day?" });
        wwe/Foie.Dnge = new wwe/Foie("BYEYOURNAME", 2, "Saying good bye", new String[] { "Bye !object1", "Bye bye !object1", "Bye !object1 have a great rest of the day", "Bye bye !object1 have a great rest of the day" });
        wwe/Foie.UwxW = new wwe/Foie("TPS", 3, "Tps", new String[] { "Tps is currently !object1" });
        wwe/Foie.sSpe = new wwe/Foie("PING", 4, "Ping", new String[] { "Pong" });
        wwe/Foie.yGLD = new wwe/Foie("MYPING", 5, "Myping", new String[] { "Your ping is !object1" });
        wwe/Foie.OHcM = new wwe/Foie("PINGPLAYERNAME", 6, "Ping playername", new String[] { "!object1's ping is !object2" });
        wwe/Foie.xwDq = new wwe/Foie("WORSTPING", 7, "Worstping", new String[] { "!object1 has the worst ping, !object2" });
        wwe/Foie.LtQS = new wwe/Foie("BESTPING", 8, "Bestping", new String[] { "!object1 has the best ping, !object2" });
        wwe/Foie.DFPB = new wwe/Foie("RANDOMPING", 9, "Randomping", new String[] { "!object1 ping is, !object2" });
        wwe/Foie.ADwi = new wwe/Foie("YOURNAME", 10, "Yourname", new String[] { "Yeah?", "Yeah? !object1" });
        wwe/Foie.MHTz = new wwe/Foie("INCORRECTCOMMAND", 11, "Incorrectcommand", new String[] { "Incorrect command, try -help", "Close, !object1, but the correct command is: -help" });
        wwe/Foie.pvoo = new wwe/Foie[] { wwe/Foie.iNhF, wwe/Foie.laFO, wwe/Foie.Dnge, wwe/Foie.UwxW, wwe/Foie.sSpe, wwe/Foie.yGLD, wwe/Foie.OHcM, wwe/Foie.xwDq, wwe/Foie.LtQS, wwe/Foie.DFPB, wwe/Foie.ADwi, wwe/Foie.MHTz };
    }
}
