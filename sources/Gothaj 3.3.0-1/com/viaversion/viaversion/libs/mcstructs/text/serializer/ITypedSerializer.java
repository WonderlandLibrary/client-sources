package com.viaversion.viaversion.libs.mcstructs.text.serializer;

public interface ITypedSerializer<T, O> {
   T serialize(O var1);

   O deserialize(T var1);
}
