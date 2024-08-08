package javassist.bytecode.stackmap;

public interface TypeTag {
   String TOP_TYPE = "*top*";
   TypeData.BasicType TOP = new TypeData.BasicType("*top*", 0, ' ');
   TypeData.BasicType INTEGER = new TypeData.BasicType("int", 1, 'I');
   TypeData.BasicType FLOAT = new TypeData.BasicType("float", 2, 'F');
   TypeData.BasicType DOUBLE = new TypeData.BasicType("double", 3, 'D');
   TypeData.BasicType LONG = new TypeData.BasicType("long", 4, 'J');
}
