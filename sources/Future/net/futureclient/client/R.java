package net.futureclient.client;

import net.futureclient.client.utils.Value;

public class R<T extends Enum> extends Value<T>
{
    public R(final T t, final String... array) {
        super(t, array);
    }
    
    public void e() {
        final Enum[] array;
        final int length = (array = (Enum[])this.M().getClass().getEnumConstants()).length;
        int i = 0;
        int n = 0;
        while (i < length) {
            if (array[n].name().equalsIgnoreCase(this.M())) {
                if (--n < 0) {
                    n = array.length - 1;
                }
                this.e(array[n].toString());
            }
            i = ++n;
        }
    }
    
    public void e(final String s) {
        final Enum[] array;
        final int length = (array = (Enum[])this.M().getClass().getEnumConstants()).length;
        int i = 0;
        int n = 0;
        while (i < length) {
            if (array[n].name().equalsIgnoreCase(s)) {
                this.D = (T)array[n];
            }
            i = ++n;
        }
    }
    
    public String M() {
        return Character.toString(this.D.name().charAt(0)) + this.D.name().toLowerCase().replaceFirst(Character.toString(this.D.name().charAt(0)).toLowerCase(), "");
    }
    
    public void M(final String s) {
        final Enum[] array;
        final int length = (array = (Enum[])this.M().getClass().getEnumConstants()).length;
        int i = 0;
        int n = 0;
        while (i < length) {
            if (array[n].name().equalsIgnoreCase(s)) {
                this.D = (T)array[n];
            }
            i = ++n;
        }
    }
    
    public void M() {
        final Enum[] array;
        final int length = (array = (Enum[])this.M().getClass().getEnumConstants()).length;
        int i = 0;
        int n = 0;
        while (i < length) {
            if (array[n].name().equalsIgnoreCase(this.M())) {
                if (++n > array.length - 1) {
                    n = 0;
                }
                this.e(array[n].toString());
            }
            i = ++n;
        }
    }
}
