/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.Location
 *  org.apache.tools.ant.Task
 *  org.apache.tools.ant.types.FileSet
 *  org.apache.tools.ant.types.Path
 *  org.apache.tools.ant.types.Reference
 *  org.apache.tools.ant.types.ResourceCollection
 */
package lombok.delombok.ant;

import java.io.File;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.types.ResourceCollection;

class Tasks {
    Tasks() {
    }

    public static class Delombok
    extends Task {
        private File fromDir;
        private File toDir;
        private Path classpath;
        private Path sourcepath;
        private Path modulepath;
        private boolean verbose;
        private String encoding;
        private Path path;
        private List<Format> formatOptions = new ArrayList<Format>();
        private static ClassLoader shadowLoader;

        public void setClasspath(Path path) {
            if (this.classpath == null) {
                this.classpath = path;
            } else {
                this.classpath.append(path);
            }
        }

        public Path createClasspath() {
            if (this.classpath == null) {
                this.classpath = new Path(this.getProject());
            }
            return this.classpath.createPath();
        }

        public void setClasspathRef(Reference reference) {
            this.createClasspath().setRefid(reference);
        }

        public void setSourcepath(Path path) {
            if (this.sourcepath == null) {
                this.sourcepath = path;
            } else {
                this.sourcepath.append(path);
            }
        }

        public Path createSourcepath() {
            if (this.sourcepath == null) {
                this.sourcepath = new Path(this.getProject());
            }
            return this.sourcepath.createPath();
        }

        public void setSourcepathRef(Reference reference) {
            this.createSourcepath().setRefid(reference);
        }

        public void setModulepath(Path path) {
            if (this.modulepath == null) {
                this.modulepath = path;
            } else {
                this.modulepath.append(path);
            }
        }

        public Path createModulepath() {
            if (this.modulepath == null) {
                this.modulepath = new Path(this.getProject());
            }
            return this.modulepath.createPath();
        }

        public void setModulepathRef(Reference reference) {
            this.createModulepath().setRefid(reference);
        }

        public void setFrom(File file) {
            this.fromDir = file;
        }

        public void setTo(File file) {
            this.toDir = file;
        }

        public void setVerbose(boolean bl) {
            this.verbose = bl;
        }

        public void setEncoding(String string) {
            this.encoding = string;
        }

        public void addFileset(FileSet fileSet) {
            if (this.path == null) {
                this.path = new Path(this.getProject());
            }
            this.path.add((ResourceCollection)fileSet);
        }

        public void addFormat(Format format2) {
            this.formatOptions.add(format2);
        }

        public static Class<?> shadowLoadClass(String string) {
            try {
                if (shadowLoader == null) {
                    try {
                        Class.forName("lombok.core.LombokNode");
                        shadowLoader = Delombok.class.getClassLoader();
                    } catch (ClassNotFoundException classNotFoundException) {
                        Class<?> clazz = Class.forName("lombok.launch.Main");
                        Method method = clazz.getDeclaredMethod("getShadowClassLoader", new Class[0]);
                        method.setAccessible(false);
                        shadowLoader = (ClassLoader)method.invoke(null, new Object[0]);
                    }
                }
                return Class.forName(string, true, shadowLoader);
            } catch (Throwable throwable) {
                Throwable throwable2;
                if (throwable instanceof InvocationTargetException) {
                    throwable2 = throwable.getCause();
                }
                if (throwable2 instanceof RuntimeException) {
                    throw (RuntimeException)throwable2;
                }
                if (throwable2 instanceof Error) {
                    throw (Error)throwable2;
                }
                throw new RuntimeException(throwable2);
            }
        }

        public void execute() throws BuildException {
            Location location = this.getLocation();
            try {
                AccessibleObject accessibleObject;
                Object obj = Delombok.shadowLoadClass("lombok.delombok.ant.DelombokTaskImpl").getConstructor(new Class[0]).newInstance(new Object[0]);
                Field[] fieldArray = ((Object)((Object)this)).getClass().getDeclaredFields();
                int n = fieldArray.length;
                int n2 = 0;
                while (n2 < n) {
                    accessibleObject = fieldArray[n2];
                    if (!((Field)accessibleObject).isSynthetic() && !Modifier.isStatic(((Field)accessibleObject).getModifiers())) {
                        Field field = obj.getClass().getDeclaredField(((Field)accessibleObject).getName());
                        field.setAccessible(false);
                        if (((Field)accessibleObject).getName().equals("formatOptions")) {
                            ArrayList<String> arrayList = new ArrayList<String>();
                            for (Format format2 : this.formatOptions) {
                                if (format2.getValue() == null) {
                                    throw new BuildException("'value' property required for <format>");
                                }
                                arrayList.add(format2.getValue());
                            }
                            field.set(obj, arrayList);
                        } else {
                            field.set(obj, ((Field)accessibleObject).get((Object)this));
                        }
                    }
                    ++n2;
                }
                accessibleObject = obj.getClass().getMethod("execute", Location.class);
                ((Method)accessibleObject).invoke(obj, location);
            } catch (Throwable throwable) {
                Throwable throwable2;
                if (throwable instanceof InvocationTargetException) {
                    throwable2 = throwable.getCause();
                }
                if (throwable2 instanceof RuntimeException) {
                    throw (RuntimeException)throwable2;
                }
                if (throwable2 instanceof Error) {
                    throw (Error)throwable2;
                }
                throw new RuntimeException(throwable2);
            }
        }
    }

    public static class Format {
        private String value;

        public int hashCode() {
            int n = 1;
            n = 31 * n + (this.value == null ? 0 : this.value.hashCode());
            return n;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null) {
                return true;
            }
            if (this.getClass() != object.getClass()) {
                return true;
            }
            Format format2 = (Format)object;
            return this.value == null ? format2.value != null : !this.value.equals(format2.value);
        }

        public String toString() {
            return "FormatOption [value=" + this.value + "]";
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String string) {
            this.value = string;
        }
    }
}

