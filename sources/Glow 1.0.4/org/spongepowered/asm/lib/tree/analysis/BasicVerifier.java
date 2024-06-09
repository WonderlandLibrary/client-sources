package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class BasicVerifier extends BasicInterpreter
{
    public BasicVerifier() {
        super(327680);
    }
    
    protected BasicVerifier(final int n) {
        super(n);
    }
    
    @Override
    public BasicValue copyOperation(final AbstractInsnNode abstractInsnNode, final BasicValue basicValue) throws AnalyzerException {
        BasicValue basicValue2 = null;
        switch (abstractInsnNode.getOpcode()) {
            case 21:
            case 54: {
                basicValue2 = BasicValue.INT_VALUE;
                break;
            }
            case 23:
            case 56: {
                basicValue2 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 22:
            case 55: {
                basicValue2 = BasicValue.LONG_VALUE;
                break;
            }
            case 24:
            case 57: {
                basicValue2 = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 25: {
                if (!basicValue.isReference()) {
                    throw new AnalyzerException(abstractInsnNode, null, "an object reference", basicValue);
                }
                return basicValue;
            }
            case 58: {
                if (!basicValue.isReference() && !BasicValue.RETURNADDRESS_VALUE.equals(basicValue)) {
                    throw new AnalyzerException(abstractInsnNode, null, "an object reference or a return address", basicValue);
                }
                return basicValue;
            }
            default: {
                return basicValue;
            }
        }
        if (!basicValue2.equals(basicValue)) {
            throw new AnalyzerException(abstractInsnNode, null, basicValue2, basicValue);
        }
        return basicValue;
    }
    
    @Override
    public BasicValue unaryOperation(final AbstractInsnNode abstractInsnNode, final BasicValue basicValue) throws AnalyzerException {
        BasicValue basicValue2 = null;
        switch (abstractInsnNode.getOpcode()) {
            case 116:
            case 132:
            case 133:
            case 134:
            case 135:
            case 145:
            case 146:
            case 147:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 170:
            case 171:
            case 172:
            case 188:
            case 189: {
                basicValue2 = BasicValue.INT_VALUE;
                break;
            }
            case 118:
            case 139:
            case 140:
            case 141:
            case 174: {
                basicValue2 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 117:
            case 136:
            case 137:
            case 138:
            case 173: {
                basicValue2 = BasicValue.LONG_VALUE;
                break;
            }
            case 119:
            case 142:
            case 143:
            case 144:
            case 175: {
                basicValue2 = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 180: {
                basicValue2 = this.newValue(Type.getObjectType(((FieldInsnNode)abstractInsnNode).owner));
                break;
            }
            case 192: {
                if (!basicValue.isReference()) {
                    throw new AnalyzerException(abstractInsnNode, null, "an object reference", basicValue);
                }
                return super.unaryOperation(abstractInsnNode, basicValue);
            }
            case 190: {
                if (!this.isArrayValue(basicValue)) {
                    throw new AnalyzerException(abstractInsnNode, null, "an array reference", basicValue);
                }
                return super.unaryOperation(abstractInsnNode, basicValue);
            }
            case 176:
            case 191:
            case 193:
            case 194:
            case 195:
            case 198:
            case 199: {
                if (!basicValue.isReference()) {
                    throw new AnalyzerException(abstractInsnNode, null, "an object reference", basicValue);
                }
                return super.unaryOperation(abstractInsnNode, basicValue);
            }
            case 179: {
                basicValue2 = this.newValue(Type.getType(((FieldInsnNode)abstractInsnNode).desc));
                break;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
        if (!this.isSubTypeOf(basicValue, basicValue2)) {
            throw new AnalyzerException(abstractInsnNode, null, basicValue2, basicValue);
        }
        return super.unaryOperation(abstractInsnNode, basicValue);
    }
    
    @Override
    public BasicValue binaryOperation(final AbstractInsnNode abstractInsnNode, final BasicValue basicValue, final BasicValue basicValue2) throws AnalyzerException {
        BasicValue basicValue3 = null;
        BasicValue basicValue4 = null;
        switch (abstractInsnNode.getOpcode()) {
            case 46: {
                basicValue3 = this.newValue(Type.getType("[I"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 51: {
                if (this.isSubTypeOf(basicValue, this.newValue(Type.getType("[Z")))) {
                    basicValue3 = this.newValue(Type.getType("[Z"));
                }
                else {
                    basicValue3 = this.newValue(Type.getType("[B"));
                }
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 52: {
                basicValue3 = this.newValue(Type.getType("[C"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 53: {
                basicValue3 = this.newValue(Type.getType("[S"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 47: {
                basicValue3 = this.newValue(Type.getType("[J"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 48: {
                basicValue3 = this.newValue(Type.getType("[F"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 49: {
                basicValue3 = this.newValue(Type.getType("[D"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 50: {
                basicValue3 = this.newValue(Type.getType("[Ljava/lang/Object;"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 96:
            case 100:
            case 104:
            case 108:
            case 112:
            case 120:
            case 122:
            case 124:
            case 126:
            case 128:
            case 130:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164: {
                basicValue3 = BasicValue.INT_VALUE;
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 98:
            case 102:
            case 106:
            case 110:
            case 114:
            case 149:
            case 150: {
                basicValue3 = BasicValue.FLOAT_VALUE;
                basicValue4 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 97:
            case 101:
            case 105:
            case 109:
            case 113:
            case 127:
            case 129:
            case 131:
            case 148: {
                basicValue3 = BasicValue.LONG_VALUE;
                basicValue4 = BasicValue.LONG_VALUE;
                break;
            }
            case 121:
            case 123:
            case 125: {
                basicValue3 = BasicValue.LONG_VALUE;
                basicValue4 = BasicValue.INT_VALUE;
                break;
            }
            case 99:
            case 103:
            case 107:
            case 111:
            case 115:
            case 151:
            case 152: {
                basicValue3 = BasicValue.DOUBLE_VALUE;
                basicValue4 = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 165:
            case 166: {
                basicValue3 = BasicValue.REFERENCE_VALUE;
                basicValue4 = BasicValue.REFERENCE_VALUE;
                break;
            }
            case 181: {
                final FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;
                basicValue3 = this.newValue(Type.getObjectType(fieldInsnNode.owner));
                basicValue4 = this.newValue(Type.getType(fieldInsnNode.desc));
                break;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
        if (!this.isSubTypeOf(basicValue, basicValue3)) {
            throw new AnalyzerException(abstractInsnNode, "First argument", basicValue3, basicValue);
        }
        if (!this.isSubTypeOf(basicValue2, basicValue4)) {
            throw new AnalyzerException(abstractInsnNode, "Second argument", basicValue4, basicValue2);
        }
        if (abstractInsnNode.getOpcode() == 50) {
            return this.getElementValue(basicValue);
        }
        return super.binaryOperation(abstractInsnNode, basicValue, basicValue2);
    }
    
    @Override
    public BasicValue ternaryOperation(final AbstractInsnNode abstractInsnNode, final BasicValue basicValue, final BasicValue basicValue2, final BasicValue basicValue3) throws AnalyzerException {
        BasicValue basicValue4 = null;
        BasicValue basicValue5 = null;
        switch (abstractInsnNode.getOpcode()) {
            case 79: {
                basicValue4 = this.newValue(Type.getType("[I"));
                basicValue5 = BasicValue.INT_VALUE;
                break;
            }
            case 84: {
                if (this.isSubTypeOf(basicValue, this.newValue(Type.getType("[Z")))) {
                    basicValue4 = this.newValue(Type.getType("[Z"));
                }
                else {
                    basicValue4 = this.newValue(Type.getType("[B"));
                }
                basicValue5 = BasicValue.INT_VALUE;
                break;
            }
            case 85: {
                basicValue4 = this.newValue(Type.getType("[C"));
                basicValue5 = BasicValue.INT_VALUE;
                break;
            }
            case 86: {
                basicValue4 = this.newValue(Type.getType("[S"));
                basicValue5 = BasicValue.INT_VALUE;
                break;
            }
            case 80: {
                basicValue4 = this.newValue(Type.getType("[J"));
                basicValue5 = BasicValue.LONG_VALUE;
                break;
            }
            case 81: {
                basicValue4 = this.newValue(Type.getType("[F"));
                basicValue5 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 82: {
                basicValue4 = this.newValue(Type.getType("[D"));
                basicValue5 = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 83: {
                basicValue4 = basicValue;
                basicValue5 = BasicValue.REFERENCE_VALUE;
                break;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
        if (!this.isSubTypeOf(basicValue, basicValue4)) {
            throw new AnalyzerException(abstractInsnNode, "First argument", "a " + basicValue4 + " array reference", basicValue);
        }
        if (!BasicValue.INT_VALUE.equals(basicValue2)) {
            throw new AnalyzerException(abstractInsnNode, "Second argument", BasicValue.INT_VALUE, basicValue2);
        }
        if (!this.isSubTypeOf(basicValue3, basicValue5)) {
            throw new AnalyzerException(abstractInsnNode, "Third argument", basicValue5, basicValue3);
        }
        return null;
    }
    
    @Override
    public BasicValue naryOperation(final AbstractInsnNode abstractInsnNode, final List<? extends BasicValue> list) throws AnalyzerException {
        final int opcode = abstractInsnNode.getOpcode();
        if (opcode == 197) {
            for (int i = 0; i < list.size(); ++i) {
                if (!BasicValue.INT_VALUE.equals(list.get(i))) {
                    throw new AnalyzerException(abstractInsnNode, null, BasicValue.INT_VALUE, (Value)list.get(i));
                }
            }
        }
        else {
            int j = 0;
            int n = 0;
            if (opcode != 184 && opcode != 186) {
                final Type objectType = Type.getObjectType(((MethodInsnNode)abstractInsnNode).owner);
                if (!this.isSubTypeOf((BasicValue)list.get(j++), this.newValue(objectType))) {
                    throw new AnalyzerException(abstractInsnNode, "Method owner", this.newValue(objectType), (Value)list.get(0));
                }
            }
            final Type[] argumentTypes = Type.getArgumentTypes((opcode == 186) ? ((InvokeDynamicInsnNode)abstractInsnNode).desc : ((MethodInsnNode)abstractInsnNode).desc);
            while (j < list.size()) {
                final BasicValue value = this.newValue(argumentTypes[n++]);
                final BasicValue basicValue = (BasicValue)list.get(j++);
                if (!this.isSubTypeOf(basicValue, value)) {
                    throw new AnalyzerException(abstractInsnNode, "Argument " + n, value, basicValue);
                }
            }
        }
        return super.naryOperation(abstractInsnNode, list);
    }
    
    @Override
    public void returnOperation(final AbstractInsnNode abstractInsnNode, final BasicValue basicValue, final BasicValue basicValue2) throws AnalyzerException {
        if (!this.isSubTypeOf(basicValue, basicValue2)) {
            throw new AnalyzerException(abstractInsnNode, "Incompatible return type", basicValue2, basicValue);
        }
    }
    
    protected boolean isArrayValue(final BasicValue basicValue) {
        return basicValue.isReference();
    }
    
    protected BasicValue getElementValue(final BasicValue basicValue) throws AnalyzerException {
        return BasicValue.REFERENCE_VALUE;
    }
    
    protected boolean isSubTypeOf(final BasicValue basicValue, final BasicValue basicValue2) {
        return basicValue.equals(basicValue2);
    }
    
    @Override
    public void returnOperation(final AbstractInsnNode abstractInsnNode, final Value value, final Value value2) throws AnalyzerException {
        this.returnOperation(abstractInsnNode, (BasicValue)value, (BasicValue)value2);
    }
    
    @Override
    public Value naryOperation(final AbstractInsnNode abstractInsnNode, final List list) throws AnalyzerException {
        return this.naryOperation(abstractInsnNode, (List<? extends BasicValue>)list);
    }
    
    @Override
    public Value ternaryOperation(final AbstractInsnNode abstractInsnNode, final Value value, final Value value2, final Value value3) throws AnalyzerException {
        return this.ternaryOperation(abstractInsnNode, (BasicValue)value, (BasicValue)value2, (BasicValue)value3);
    }
    
    @Override
    public Value binaryOperation(final AbstractInsnNode abstractInsnNode, final Value value, final Value value2) throws AnalyzerException {
        return this.binaryOperation(abstractInsnNode, (BasicValue)value, (BasicValue)value2);
    }
    
    @Override
    public Value unaryOperation(final AbstractInsnNode abstractInsnNode, final Value value) throws AnalyzerException {
        return this.unaryOperation(abstractInsnNode, (BasicValue)value);
    }
    
    @Override
    public Value copyOperation(final AbstractInsnNode abstractInsnNode, final Value value) throws AnalyzerException {
        return this.copyOperation(abstractInsnNode, (BasicValue)value);
    }
}
