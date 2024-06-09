package com.client.glowclient;

import com.client.glowclient.commands.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;
import java.util.*;

public class Be extends Command
{
    private Module b;
    
    public Be(final Module b) {
        super(b.k());
        this.b = b;
    }
    
    @Override
    public String M(final String s, final String[] array) {
        switch (array.length) {
            case 2: {
                final Collection<AA> k = xc.k();
                while (false) {}
                final Iterator<AA> iterator = k.iterator();
                while (iterator.hasNext()) {
                    final Value value;
                    if (((value = (Value)iterator.next()) instanceof BooleanValue || value instanceof NumberValue || value instanceof StringValue || value instanceof nB) && value.M().equals(this.b.k()) && value.A().startsWith(array[1])) {
                        return new StringBuilder().insert(0, Command.B.e()).append(this.b.k()).append(" ").append(value.A()).toString();
                    }
                }
                return "";
            }
            case 3: {
                final Iterator<AA> iterator2 = xc.k().iterator();
                while (iterator2.hasNext()) {
                    final Value value2;
                    if ((value2 = (Value)iterator2.next()) instanceof nB) {
                        for (final String s2 : ((nB)value2).M()) {
                            if (value2.M().equals(array[0].replace(Command.B.e(), "")) && value2.A().equals(array[1]) && s2.startsWith(array[2])) {
                                return new StringBuilder().insert(0, Command.B.e()).append(this.b.k()).append(" ").append(value2.A()).append(" ").append(s2).toString();
                            }
                        }
                    }
                }
                return "";
            }
            default: {
                return new StringBuilder().insert(0, Command.B.e()).append(this.b.k()).toString();
            }
        }
    }
    
    @Override
    public void M(final String s, final String[] array) {
        if (array.length < 3) {
            qd.D("§cNot enough data given");
        }
        final Iterator<AA> iterator = xc.k().iterator();
        while (iterator.hasNext()) {
            final Value value;
            if ((value = (Value)iterator.next()) instanceof BooleanValue && value.M().equals(this.b.k()) && value.A().equalsIgnoreCase(array[1])) {
                value.M(Boolean.parseBoolean(array[2]));
                qd.D(new StringBuilder().insert(0, "§bSet setting: ").append(value.A()).append(" from module: ").append(this.b.k()).append(" to ").append(array[2]).toString());
            }
            if (value instanceof NumberValue && value.M().equals(this.b.k()) && value.A().equalsIgnoreCase(array[1]) && sd.D(array[2])) {
                value.M(Double.parseDouble(array[2]));
                qd.D(new StringBuilder().insert(0, "§bSet setting: ").append(value.A()).append(" from module: ").append(this.b.k()).append(" to ").append(array[2]).toString());
            }
            Label_0511: {
                if (value instanceof StringValue && value.M().equals(this.b.k()) && value.A().equalsIgnoreCase(array[1])) {
                    if (array.length > 2) {
                        String string = "";
                        final int length = array.length;
                        int n;
                        int i = n = 0;
                        while (i < length) {
                            final String s2;
                            if (!(s2 = array[n]).equals(array[0]) && !s2.equals(array[1])) {
                                string = new StringBuilder().insert(0, string).append(s2).append(" ").toString();
                            }
                            i = ++n;
                        }
                        value.M(string);
                        qd.D(new StringBuilder().insert(0, "§bSet setting: ").append(value.A()).append(" from module: ").append(this.b.k()).append(" to ").append(((StringValue)value).e()).toString());
                        final StringValue stringValue = (StringValue)value;
                        break Label_0511;
                    }
                    value.M(array[2]);
                    qd.D(new StringBuilder().insert(0, "§bSet setting: ").append(value.A()).append(" from module: ").append(this.b.k()).append(" to ").append(((StringValue)value).e()).toString());
                }
                final StringValue stringValue = (StringValue)value;
            }
            StringValue stringValue;
            if (stringValue instanceof nB && value.M().equals(this.b.k()) && value.A().equalsIgnoreCase(array[1])) {
                final Iterator<String> iterator2 = ((nB)value).M().iterator();
                while (iterator2.hasNext()) {
                    final String s3;
                    if ((s3 = iterator2.next()).equalsIgnoreCase(array[2])) {
                        value.M(s3);
                        qd.D(new StringBuilder().insert(0, "§bSet setting: ").append(value.A()).append(" from module: ").append(this.b.k()).append(" to ").append(s3).toString());
                    }
                }
            }
        }
    }
}
