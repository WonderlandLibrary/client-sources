/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import joptsimple.util.PathProperties;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PathConverter
implements ValueConverter<Path> {
    private final PathProperties[] pathProperties;

    public PathConverter(PathProperties ... pathPropertiesArray) {
        this.pathProperties = pathPropertiesArray;
    }

    @Override
    public Path convert(String string) {
        Path path = Paths.get(string, new String[0]);
        if (this.pathProperties != null) {
            for (PathProperties pathProperties : this.pathProperties) {
                if (pathProperties.accept(path)) continue;
                throw new ValueConversionException(this.message(pathProperties.getMessageKey(), path.toString()));
            }
        }
        return path;
    }

    @Override
    public Class<Path> valueType() {
        return Path.class;
    }

    @Override
    public String valuePattern() {
        return null;
    }

    private String message(String string, String string2) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("joptsimple.ExceptionMessages");
        Object[] objectArray = new Object[]{string2, this.valuePattern()};
        String string3 = resourceBundle.getString(PathConverter.class.getName() + "." + string + ".message");
        return new MessageFormat(string3).format(objectArray);
    }

    @Override
    public Object convert(String string) {
        return this.convert(string);
    }
}

