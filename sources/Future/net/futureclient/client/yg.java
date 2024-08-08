package net.futureclient.client;

import java.util.Iterator;
import net.futureclient.client.utils.Value;
import java.util.StringJoiner;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.client.Minecraft;
import net.futureclient.client.events.Event;

public class yg extends n<yf>
{
    public final XI k;
    
    public yg(final XI k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((yf)event);
    }
    
    @Override
    public void M(final yf yf) {
        final String m;
        if ((m = yf.M()).startsWith(XI.M(this.k))) {
            yf.M(true);
            if (m.startsWith(XI.M(this.k) + "say ") && !yf.M().substring(5).isEmpty()) {
                Minecraft.getMinecraft().getConnection().sendPacket((Packet)new CPacketChatMessage(yf.M().substring(5)));
                return;
            }
            final String s = m;
            final String trim = s.trim().substring(XI.M(this.k).length()).trim();
            final boolean contains;
            final String s2 = (contains = s.trim().contains(AE.M("e"))) ? trim.split(" ")[0] : trim.trim();
            final String[] array = contains ? trim.substring(s2.length()).trim().split(AE.M("e")) : new String[0];
            final String s3 = m;
            final String[] split = s3.split(" ");
            if (s3.length() < 1) {
                net.futureclient.client.s.M().M(AE.M("\u000b\te\u0005*\u000b(\u0007+\u0002e\u0011$\u0015e\u0003+\u0012 \u0014 \u0002k"));
                return;
            }
            final String s4 = m.contains(" ") ? split[0] : m;
            final Iterator<XB> iterator = (Iterator<XB>)this.k.M().iterator();
            while (iterator.hasNext()) {
                final XB xb;
                final String[] i;
                final int length = (i = (xb = iterator.next()).M()).length;
                int j = 0;
                int n = 0;
                while (j < length) {
                    final String s5 = i[n];
                    if (s4.replace(this.k.M(), "").equalsIgnoreCase(s5.replaceAll(AE.M("e"), ""))) {
                        try {
                            final String k;
                            if (!(k = xb.M(array)).equals("")) {
                                net.futureclient.client.s.M().M(k);
                            }
                        }
                        catch (Exception ex2) {
                            net.futureclient.client.s.M().M(String.format("%s%s %s", XI.M(this.k), s5.toLowerCase(), xb.M()));
                        }
                    }
                    j = ++n;
                }
            }
            final String[] split2 = m.split(AE.M("e"));
            final Iterator<Ga> iterator2 = (Iterator<Ga>)pg.M().M().M().iterator();
            while (iterator2.hasNext()) {
                final Ga ga;
                final String[] l;
                final int length2 = (l = (ga = iterator2.next()).M()).length;
                int n2 = 0;
                int n3 = 0;
                while (n2 < length2) {
                    final String s6 = l[n3];
                    try {
                        if (split2[0].equalsIgnoreCase(new StringBuilder().insert(0, this.k.M()).append(s6.replace(" ", "")).toString())) {
                            if (split2.length > 1) {
                                final String[] array2 = split2;
                                final String s7 = array2[1];
                                if (array2[1].equalsIgnoreCase(AE.M("\n,\u00151"))) {
                                    if (ga.e().size() > 0) {
                                        final StringJoiner stringJoiner = new StringJoiner(", ");
                                        final Iterator<Value<Object>> iterator4;
                                        Iterator<Value<Object>> iterator3 = iterator4 = ga.e().iterator();
                                        while (iterator3.hasNext()) {
                                            final Value<Object> value = iterator4.next();
                                            final StringJoiner stringJoiner2 = stringJoiner;
                                            final String m2 = AE.M("`\u0015e@$N`\u0015l@r");
                                            final Object[] array3 = new Object[2];
                                            final String[] m3 = value.M();
                                            final int n4 = 0;
                                            array3[n4] = m3[n4];
                                            array3[1] = value.M();
                                            stringJoiner2.add(String.format(m2, array3));
                                            iterator3 = iterator4;
                                        }
                                        net.futureclient.client.s.M().M(String.format("Properties (%s) %s.", ga.e().size(), stringJoiner.toString()));
                                    }
                                    else {
                                        final s m4 = net.futureclient.client.s.M();
                                        final String m5 = AE.M("c\u0003`\u0015cQe\u000e$\u0015e\b*F5\u0014*\u0016 \u00141\u000f \u0015k");
                                        final Object[] array4 = { null };
                                        final String[] m6 = ga.M();
                                        final int n5 = 0;
                                        array4[n5] = m6[n5];
                                        m4.M(String.format(m5, array4));
                                    }
                                }
                                else {
                                    final Value m7;
                                    if ((m7 = ga.M(s7)) != null) {
                                        if (m7.M() instanceof Number) {
                                            if (m7.M() instanceof Double) {
                                                m7.M(Double.parseDouble(split2[2]));
                                            }
                                            if (m7.M() instanceof Integer) {
                                                m7.M(Integer.parseInt(split2[2]));
                                            }
                                            if (m7.M() instanceof Float) {
                                                m7.M(Float.parseFloat(split2[2]));
                                            }
                                            if (m7.M() instanceof Long) {
                                                m7.M(Long.parseLong(split2[2]));
                                            }
                                            final s m8 = net.futureclient.client.s.M();
                                            final String s8 = "%s has been set to %s for %s.";
                                            final Object[] array5 = new Object[3];
                                            final String[] m9 = m7.M();
                                            final int n6 = 0;
                                            array5[n6] = m9[n6];
                                            array5[1] = m7.M();
                                            array5[2] = ga.M()[0];
                                            m8.M(String.format(s8, array5));
                                        }
                                        else if (m7.M() instanceof Enum) {
                                            if (!split2[2].equalsIgnoreCase(AE.M("\n,\u00151"))) {
                                                ((R<Object>)m7).M(split2[2]);
                                                final s m10 = net.futureclient.client.s.M();
                                                final String s9 = "%s has been set to %s for %s.";
                                                final Object[] array6 = new Object[3];
                                                final String[] m11 = m7.M();
                                                final int n7 = 0;
                                                array6[n7] = m11[n7];
                                                array6[1] = m7.M();
                                                array6[2] = ga.M()[0];
                                                m10.M(String.format(s9, array6));
                                            }
                                            else {
                                                final StringJoiner stringJoiner3 = new StringJoiner(AE.M("Je"));
                                                final Enum[] array7;
                                                final int length3 = (array7 = (Enum[])m7.M().getClass().getEnumConstants()).length;
                                                int n8 = 0;
                                                int n9 = 0;
                                                while (n8 < length3) {
                                                    final StringJoiner stringJoiner4 = stringJoiner3;
                                                    final String s10 = "%s%s&7";
                                                    final Object[] array8 = { array7[n9].name().equalsIgnoreCase(m7.M().toString()) ? AE.M("@$") : "&c", null };
                                                    final int n10 = 1;
                                                    final String name = array7[n9].name();
                                                    ++n9;
                                                    array8[n10] = name;
                                                    stringJoiner4.add(String.format(s10, array8));
                                                    n8 = n9;
                                                }
                                                final s m12 = net.futureclient.client.s.M();
                                                final String m13 = AE.M("C6\u0015eN`\u0015lF`\u0015k");
                                                final Object[] array9 = new Object[3];
                                                final String[] m14 = m7.M();
                                                final int n11 = 0;
                                                array9[n11] = m14[n11];
                                                array9[1] = array7.length;
                                                array9[2] = stringJoiner3.toString();
                                                m12.M(String.format(m13, array9));
                                            }
                                        }
                                        else {
                                            final boolean b = m7.M() instanceof String;
                                            final Value<Object> value2 = (Value<Object>)m7;
                                            if (b) {
                                                value2.M(split2[2]);
                                                final s m15 = net.futureclient.client.s.M();
                                                final String s11 = "%s has been set to \"%s\" for %s.";
                                                final Object[] array10 = new Object[3];
                                                final String[] m16 = m7.M();
                                                final int n12 = 0;
                                                array10[n12] = m16[n12];
                                                array10[1] = m7.M();
                                                array10[2] = ga.M()[0];
                                                m15.M(String.format(s11, array10));
                                            }
                                            else if (value2.M() instanceof Boolean) {
                                                final Value<Object> value3 = (Value<Object>)m7;
                                                value3.M(!value3.M());
                                                final s m17 = net.futureclient.client.s.M();
                                                final String m18 = AE.M("`\u0015e\u0012*\u0001\"\n \u0002eC6@rF#\t7F`\u0015k");
                                                final Object[] array11 = new Object[3];
                                                final String[] m19 = m7.M();
                                                final int n13 = 0;
                                                array11[n13] = m19[n13];
                                                array11[1] = (m7.M() ? "&aon" : AE.M("c\u0005*\u0000#"));
                                                array11[2] = ga.M()[0];
                                                m17.M(String.format(m18, array11));
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                final s m20 = net.futureclient.client.s.M();
                                final String s12 = "%s &e[list|valuename] [list|get]";
                                final Object[] array12 = { null };
                                final String[] array13 = split2;
                                final int n14 = 0;
                                array12[n14] = array13[n14];
                                m20.M(String.format(s12, array12));
                            }
                        }
                    }
                    catch (Exception ex) {
                        net.futureclient.client.s.M().M(String.format(AE.M("/+\u0010$\n,\u0002e\u00077\u00010\u000b \b1F`\u0015k"), ex.getMessage().replaceFirst("F", AE.M("#"))));
                    }
                    n2 = ++n3;
                }
            }
            net.futureclient.client.s.M().M("Invalid command.");
        }
    }
}
