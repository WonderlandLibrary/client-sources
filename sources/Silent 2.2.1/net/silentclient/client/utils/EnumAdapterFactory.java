package net.silentclient.client.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EnumAdapterFactory implements TypeAdapterFactory
{
    @SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
    {
        Class<T> oclass = (Class<T>) typeToken.getRawType();

        if (!oclass.isEnum())
        {
            return null;
        }
        else
        {
            final Map<String, T> map = new HashMap<String, T>();

            for (T t : oclass.getEnumConstants())
            {
                map.put(this.toUppercase(t), t);
            }

            return new TypeAdapter<T>()
            {
                public void write(JsonWriter writer, Object object) throws IOException
                {
                    if (object == null)
                    {
                        writer.nullValue();
                    }
                    else
                    {
                        writer.value(EnumAdapterFactory.this.toUppercase(object));
                    }
                }
                public T read(JsonReader reader) throws IOException
                {
                    if (reader.peek() == JsonToken.NULL)
                    {
                        reader.nextNull();
                        return (T)null;
                    }
                    else
                    {
                        return (T)map.get(reader.nextString());
                    }
                }
            };
        }
    }

    private String toUppercase(Object objec)
    {
        return objec instanceof Enum ? ((Enum<?>)objec).name().toUpperCase(Locale.US) : objec.toString().toUpperCase(Locale.US);
    }
}
