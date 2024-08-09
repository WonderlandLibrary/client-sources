//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.events;

import com.mojang.blaze3d.matrix.MatrixStack;

public class EventDisplay {
    private MatrixStack matrixStack;
    private float partialTicks;
    private Type type;

    public EventDisplay(MatrixStack matrixStack, float partialTicks) {
        this.matrixStack = matrixStack;
        this.partialTicks = partialTicks;
    }

    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public Type getType() {
        return this.type;
    }

    public void setMatrixStack(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EventDisplay)) {
            return false;
        } else {
            EventDisplay other = (EventDisplay)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (Float.compare(this.getPartialTicks(), other.getPartialTicks()) != 0) {
                return false;
            } else {
                Object this$matrixStack = this.getMatrixStack();
                Object other$matrixStack = other.getMatrixStack();
                if (this$matrixStack == null) {
                    if (other$matrixStack != null) {
                        return false;
                    }
                } else if (!this$matrixStack.equals(other$matrixStack)) {
                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventDisplay;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getPartialTicks());
        Object $matrixStack = this.getMatrixStack();
        result = result * 59 + ($matrixStack == null ? 43 : $matrixStack.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        MatrixStack var10000 = this.getMatrixStack();
        return "EventDisplay(matrixStack=" + var10000 + ", partialTicks=" + this.getPartialTicks() + ", type=" + this.getType() + ")";
    }

    public static enum Type {
        PRE,
        POST,
        HIGH;

        private Type() {
        }
    }
}
