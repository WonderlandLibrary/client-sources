/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.env;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

public class EnvScalarConstructor
extends Constructor {
    public static final Tag ENV_TAG = new Tag("!ENV");
    public static final Pattern ENV_FORMAT = Pattern.compile("^\\$\\{\\s*((?<name>\\w+)((?<separator>:?(-|\\?))(?<value>\\S+)?)?)\\s*\\}$");

    public EnvScalarConstructor() {
        super(new LoaderOptions());
        this.yamlConstructors.put(ENV_TAG, new ConstructEnv(this, null));
    }

    public EnvScalarConstructor(TypeDescription typeDescription, Collection<TypeDescription> collection, LoaderOptions loaderOptions) {
        super(typeDescription, collection, loaderOptions);
        this.yamlConstructors.put(ENV_TAG, new ConstructEnv(this, null));
    }

    public String apply(String string, String string2, String string3, String string4) {
        if (string4 != null && !string4.isEmpty()) {
            return string4;
        }
        if (string2 != null) {
            if (string2.equals("?") && string4 == null) {
                throw new MissingEnvironmentVariableException("Missing mandatory variable " + string + ": " + string3);
            }
            if (string2.equals(":?")) {
                if (string4 == null) {
                    throw new MissingEnvironmentVariableException("Missing mandatory variable " + string + ": " + string3);
                }
                if (string4.isEmpty()) {
                    throw new MissingEnvironmentVariableException("Empty mandatory variable " + string + ": " + string3);
                }
            }
            if (string2.startsWith(":") ? string4 == null || string4.isEmpty() : string4 == null) {
                return string3;
            }
        }
        return "";
    }

    public String getEnv(String string) {
        return System.getenv(string);
    }

    static String access$100(EnvScalarConstructor envScalarConstructor, ScalarNode scalarNode) {
        return envScalarConstructor.constructScalar(scalarNode);
    }

    private class ConstructEnv
    extends AbstractConstruct {
        final EnvScalarConstructor this$0;

        private ConstructEnv(EnvScalarConstructor envScalarConstructor) {
            this.this$0 = envScalarConstructor;
        }

        @Override
        public Object construct(Node node) {
            String string = EnvScalarConstructor.access$100(this.this$0, (ScalarNode)node);
            Matcher matcher = ENV_FORMAT.matcher(string);
            matcher.matches();
            String string2 = matcher.group("name");
            String string3 = matcher.group("value");
            String string4 = matcher.group("separator");
            return this.this$0.apply(string2, string4, string3 != null ? string3 : "", this.this$0.getEnv(string2));
        }

        ConstructEnv(EnvScalarConstructor envScalarConstructor, 1 var2_2) {
            this(envScalarConstructor);
        }
    }
}

