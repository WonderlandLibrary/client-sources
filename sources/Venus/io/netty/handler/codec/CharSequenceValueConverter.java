/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.handler.codec.DateFormatter;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.AsciiString;
import io.netty.util.internal.PlatformDependent;
import java.text.ParseException;
import java.util.Date;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CharSequenceValueConverter
implements ValueConverter<CharSequence> {
    public static final CharSequenceValueConverter INSTANCE = new CharSequenceValueConverter();
    private static final AsciiString TRUE_ASCII = new AsciiString("true");

    @Override
    public CharSequence convertObject(Object object) {
        if (object instanceof CharSequence) {
            return (CharSequence)object;
        }
        return object.toString();
    }

    @Override
    public CharSequence convertInt(int n) {
        return String.valueOf(n);
    }

    @Override
    public CharSequence convertLong(long l) {
        return String.valueOf(l);
    }

    @Override
    public CharSequence convertDouble(double d) {
        return String.valueOf(d);
    }

    @Override
    public CharSequence convertChar(char c) {
        return String.valueOf(c);
    }

    @Override
    public CharSequence convertBoolean(boolean bl) {
        return String.valueOf(bl);
    }

    @Override
    public CharSequence convertFloat(float f) {
        return String.valueOf(f);
    }

    @Override
    public boolean convertToBoolean(CharSequence charSequence) {
        return AsciiString.contentEqualsIgnoreCase(charSequence, TRUE_ASCII);
    }

    @Override
    public CharSequence convertByte(byte by) {
        return String.valueOf(by);
    }

    @Override
    public byte convertToByte(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).byteAt(0);
        }
        return Byte.parseByte(charSequence.toString());
    }

    @Override
    public char convertToChar(CharSequence charSequence) {
        return charSequence.charAt(0);
    }

    @Override
    public CharSequence convertShort(short s) {
        return String.valueOf(s);
    }

    @Override
    public short convertToShort(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).parseShort();
        }
        return Short.parseShort(charSequence.toString());
    }

    @Override
    public int convertToInt(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).parseInt();
        }
        return Integer.parseInt(charSequence.toString());
    }

    @Override
    public long convertToLong(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).parseLong();
        }
        return Long.parseLong(charSequence.toString());
    }

    @Override
    public CharSequence convertTimeMillis(long l) {
        return DateFormatter.format(new Date(l));
    }

    @Override
    public long convertToTimeMillis(CharSequence charSequence) {
        Date date = DateFormatter.parseHttpDate(charSequence);
        if (date == null) {
            PlatformDependent.throwException(new ParseException("header can't be parsed into a Date: " + charSequence, 0));
            return 0L;
        }
        return date.getTime();
    }

    @Override
    public float convertToFloat(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).parseFloat();
        }
        return Float.parseFloat(charSequence.toString());
    }

    @Override
    public double convertToDouble(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).parseDouble();
        }
        return Double.parseDouble(charSequence.toString());
    }

    @Override
    public double convertToDouble(Object object) {
        return this.convertToDouble((CharSequence)object);
    }

    @Override
    public Object convertDouble(double d) {
        return this.convertDouble(d);
    }

    @Override
    public float convertToFloat(Object object) {
        return this.convertToFloat((CharSequence)object);
    }

    @Override
    public Object convertFloat(float f) {
        return this.convertFloat(f);
    }

    @Override
    public long convertToTimeMillis(Object object) {
        return this.convertToTimeMillis((CharSequence)object);
    }

    @Override
    public Object convertTimeMillis(long l) {
        return this.convertTimeMillis(l);
    }

    @Override
    public long convertToLong(Object object) {
        return this.convertToLong((CharSequence)object);
    }

    @Override
    public Object convertLong(long l) {
        return this.convertLong(l);
    }

    @Override
    public int convertToInt(Object object) {
        return this.convertToInt((CharSequence)object);
    }

    @Override
    public Object convertInt(int n) {
        return this.convertInt(n);
    }

    @Override
    public short convertToShort(Object object) {
        return this.convertToShort((CharSequence)object);
    }

    @Override
    public Object convertShort(short s) {
        return this.convertShort(s);
    }

    @Override
    public char convertToChar(Object object) {
        return this.convertToChar((CharSequence)object);
    }

    @Override
    public Object convertChar(char c) {
        return this.convertChar(c);
    }

    @Override
    public byte convertToByte(Object object) {
        return this.convertToByte((CharSequence)object);
    }

    @Override
    public Object convertByte(byte by) {
        return this.convertByte(by);
    }

    @Override
    public boolean convertToBoolean(Object object) {
        return this.convertToBoolean((CharSequence)object);
    }

    @Override
    public Object convertBoolean(boolean bl) {
        return this.convertBoolean(bl);
    }

    @Override
    public Object convertObject(Object object) {
        return this.convertObject(object);
    }
}

