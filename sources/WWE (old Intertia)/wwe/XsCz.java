package wwe;

public class XsCz
{
    public static final String YiAs = "\u000f";
    public static final String Mbra = "\u0002";
    public static final String DZQx = "\u001f";
    public static final String WJmX = "\u0016";
    public static final String Yniz = "\u000300";
    public static final String yzRf = "\u000301";
    public static final String gSxO = "\u000302";
    public static final String MRKy = "\u000303";
    public static final String tStO = "\u000304";
    public static final String EKUk = "\u000305";
    public static final String GCSO = "\u000306";
    public static final String UNfV = "\u000307";
    public static final String jruW = "\u000308";
    public static final String YUpS = "\u000309";
    public static final String HAPi = "\u000310";
    public static final String Ssuj = "\u000311";
    public static final String fZxg = "\u000312";
    public static final String FLXa = "\u000313";
    public static final String acOu = "\u000314";
    public static final String DBpn = "\u000315";
    
    private XsCz() {
        super();
    }
    
    public static String CToC(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < length) {
            final char char1 = s.charAt(i);
            if (char1 == '\u0003') {
                if (++i < length && Character.isDigit(s.charAt(i))) {
                    if (++i < length && Character.isDigit(s.charAt(i))) {
                        ++i;
                    }
                    if (i < length && s.charAt(i) == ',') {
                        if (++i < length) {
                            if (Character.isDigit(s.charAt(i))) {
                                if (++i < length && Character.isDigit(s.charAt(i))) {
                                    ++i;
                                    if (XsCz.\u200b) {
                                        throw null;
                                    }
                                }
                            }
                            else {
                                --i;
                                if (XsCz.\u200b) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            --i;
                            if (XsCz.\u200b) {
                                throw null;
                            }
                        }
                    }
                }
            }
            else if (char1 == '\u000f') {
                ++i;
                if (XsCz.\u200b) {
                    throw null;
                }
            }
            else {
                sb.append(char1);
                ++i;
            }
            if (XsCz.\u200b) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static String UASB(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < length) {
            final char char1 = s.charAt(i);
            if (char1 != '\u000f' && char1 != '\u0002' && char1 != '\u001f') {
                if (char1 == '\u0016') {
                    if (XsCz.\u200b) {
                        throw null;
                    }
                }
                else {
                    sb.append(char1);
                }
            }
            ++i;
            if (XsCz.\u200b) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static String qcNE(final String s) {
        return UASB(CToC(s));
    }
}
