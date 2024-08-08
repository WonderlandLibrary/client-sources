package net.futureclient.client.utils;

public class NumberValue extends Value<Number>
{
    private boolean d;
    public Number a;
    public Number D;
    public Number k;
    
    public NumberValue(final Number n, final Number k, final Number d, final String... array) {
        final double n2 = 1.273197475E-314;
        final boolean d2 = false;
        super(n, array);
        this.d = d2;
        this.k = k;
        this.D = d;
        this.a = n2;
    }
    
    public NumberValue(final Number n, final Number k, final Number d, final Number a, final String... array) {
        final boolean d2 = true;
        super(n, array);
        this.d = d2;
        this.k = k;
        this.D = d;
        this.a = a;
    }
    
    public Number B() {
        return super.M();
    }
    
    public Number b() {
        return this.k;
    }
    
    public Number e() {
        return this.a;
    }
    
    @Override
    public void M(final Object o) {
        this.M((Number)o);
    }
    
    @Override
    public Number M() {
        return this.D;
    }
    
    @Override
    public Object M() {
        return this.B();
    }
    
    @Override
    public void M(Number n) {
        NumberValue numberValue = null;
        Label_0330: {
            if (this.d) {
                if (n instanceof Integer) {
                    if (n.intValue() > this.D.intValue()) {
                        numberValue = this;
                        n = this.D;
                        break Label_0330;
                    }
                    if (n.intValue() < this.k.intValue()) {
                        numberValue = this;
                        n = this.k;
                        break Label_0330;
                    }
                }
                else if (n instanceof Float) {
                    if (n.floatValue() > this.D.floatValue()) {
                        numberValue = this;
                        n = this.D;
                        break Label_0330;
                    }
                    if (n.floatValue() < this.k.floatValue()) {
                        numberValue = this;
                        n = this.k;
                        break Label_0330;
                    }
                }
                else if (n instanceof Double) {
                    if (n.doubleValue() > this.D.doubleValue()) {
                        numberValue = this;
                        n = this.D;
                        break Label_0330;
                    }
                    if (n.doubleValue() < this.k.doubleValue()) {
                        numberValue = this;
                        n = this.k;
                        break Label_0330;
                    }
                }
                else if (n instanceof Long) {
                    if (n.longValue() > this.D.longValue()) {
                        numberValue = this;
                        n = this.D;
                        break Label_0330;
                    }
                    if (n.longValue() < this.k.longValue()) {
                        numberValue = this;
                        n = this.k;
                        break Label_0330;
                    }
                }
                else if (n instanceof Short) {
                    if (n.shortValue() > this.D.shortValue()) {
                        numberValue = this;
                        n = this.D;
                        break Label_0330;
                    }
                    if (n.shortValue() < this.k.shortValue()) {
                        numberValue = this;
                        n = this.k;
                        break Label_0330;
                    }
                }
                else if (n instanceof Byte) {
                    if (n.byteValue() > this.D.byteValue()) {
                        numberValue = this;
                        n = this.D;
                        break Label_0330;
                    }
                    if (n.byteValue() < this.k.byteValue()) {
                        n = this.k;
                    }
                }
            }
            numberValue = this;
        }
        numberValue.M(n);
    }
}
