/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.sun.tools.javac.processing.JavacFiler
 *  com.sun.tools.javac.processing.JavacProcessingEnvironment
 *  com.sun.tools.javac.util.Context
 *  com.sun.tools.javac.util.Options
 *  lombok.permit.Permit
 */
package lombok.javac.apt;

import com.sun.tools.javac.processing.JavacFiler;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Options;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileManager;
import javax.tools.StandardLocation;
import lombok.permit.Permit;

@Deprecated
@SupportedAnnotationTypes(value={"*"})
public class Processor
extends AbstractProcessor {
    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        if (System.getProperty("lombok.disable") != null) {
            return;
        }
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.WARNING, "Wrong usage of 'lombok.javac.apt.Processor'. " + this.report(processingEnvironment));
    }

    private String report(ProcessingEnvironment processingEnvironment) {
        String string = this.collectData(processingEnvironment);
        try {
            return this.writeFile(string);
        } catch (Exception exception) {
            return "Report:\n\n" + string;
        }
    }

    private String writeFile(String string) throws IOException {
        File file = File.createTempFile("lombok-processor-report-", ".txt");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
        outputStreamWriter.write(string);
        outputStreamWriter.close();
        return "Report written to '" + file.getCanonicalPath() + "'\n";
    }

    private String collectData(ProcessingEnvironment processingEnvironment) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Problem report for usages of 'lombok.javac.apt.Processor'\n\n");
        this.listOptions(stringBuilder, processingEnvironment);
        this.findServices(stringBuilder, processingEnvironment.getFiler());
        this.addStacktrace(stringBuilder);
        this.listProperties(stringBuilder);
        return stringBuilder.toString();
    }

    private void listOptions(StringBuilder stringBuilder, ProcessingEnvironment processingEnvironment) {
        try {
            JavacProcessingEnvironment javacProcessingEnvironment = (JavacProcessingEnvironment)processingEnvironment;
            Options options = Options.instance((Context)javacProcessingEnvironment.getContext());
            Field field = Permit.getField(Options.class, (String)"values");
            Map map = (Map)field.get(options);
            if (map.isEmpty()) {
                stringBuilder.append("Options: empty\n\n");
                return;
            }
            stringBuilder.append("Compiler Options:\n");
            for (Map.Entry entry : map.entrySet()) {
                stringBuilder.append("- ");
                Processor.string(stringBuilder, (String)entry.getKey());
                stringBuilder.append(" = ");
                Processor.string(stringBuilder, (String)entry.getValue());
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
        } catch (Exception exception) {
            stringBuilder.append("No options available\n\n");
        }
    }

    private void findServices(StringBuilder stringBuilder, Filer filer) {
        try {
            Field field = Permit.getField(JavacFiler.class, (String)"fileManager");
            JavaFileManager javaFileManager = (JavaFileManager)field.get(filer);
            ClassLoader classLoader = javaFileManager.hasLocation(StandardLocation.ANNOTATION_PROCESSOR_PATH) ? javaFileManager.getClassLoader(StandardLocation.ANNOTATION_PROCESSOR_PATH) : javaFileManager.getClassLoader(StandardLocation.CLASS_PATH);
            Enumeration<URL> enumeration = classLoader.getResources("META-INF/services/javax.annotation.processing.Processor");
            if (!enumeration.hasMoreElements()) {
                stringBuilder.append("No processors discovered\n\n");
                return;
            }
            stringBuilder.append("Discovered processors:\n");
            while (enumeration.hasMoreElements()) {
                URL uRL = enumeration.nextElement();
                stringBuilder.append("- '").append(uRL).append("'");
                InputStream inputStream = (InputStream)uRL.getContent();
                if (inputStream == null) continue;
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    StringWriter stringWriter = new StringWriter();
                    char[] cArray = new char[8192];
                    int n = 0;
                    while ((n = inputStreamReader.read(cArray)) != -1) {
                        stringWriter.write(cArray, 0, n);
                    }
                    String string = stringWriter.toString();
                    if (string.contains("lombok.javac.apt.Processor")) {
                        stringBuilder.append(" <= problem\n");
                    } else {
                        stringBuilder.append(" (ok)\n");
                    }
                    stringBuilder.append("    ").append(string.replace("\n", "\n    ")).append("\n");
                } finally {
                    inputStream.close();
                }
            }
        } catch (Exception exception) {
            stringBuilder.append("Filer information unavailable\n");
        }
        stringBuilder.append("\n");
    }

    private void addStacktrace(StringBuilder stringBuilder) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        if (stackTraceElementArray != null) {
            stringBuilder.append("Called from\n");
            int n = 1;
            while (n < stackTraceElementArray.length) {
                StackTraceElement stackTraceElement = stackTraceElementArray[n];
                if (!stackTraceElement.getClassName().equals("lombok.javac.apt.Processor")) {
                    stringBuilder.append("- ").append(stackTraceElement).append("\n");
                }
                ++n;
            }
        } else {
            stringBuilder.append("No stacktrace available\n");
        }
        stringBuilder.append("\n");
    }

    private void listProperties(StringBuilder stringBuilder) {
        Properties properties = System.getProperties();
        ArrayList<String> arrayList = new ArrayList<String>(properties.stringPropertyNames());
        Collections.sort(arrayList);
        stringBuilder.append("Properties: \n");
        for (String string : arrayList) {
            if (string.startsWith("user.")) continue;
            stringBuilder.append("- ").append(string).append(" = ");
            Processor.string(stringBuilder, System.getProperty(string));
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");
    }

    private static void string(StringBuilder stringBuilder, String string) {
        if (string == null) {
            stringBuilder.append("null");
            return;
        }
        stringBuilder.append("\"");
        int n = 0;
        while (n < string.length()) {
            stringBuilder.append(Processor.escape(string.charAt(n)));
            ++n;
        }
        stringBuilder.append("\"");
    }

    private static String escape(char c) {
        switch (c) {
            case '\b': {
                return "\\b";
            }
            case '\f': {
                return "\\f";
            }
            case '\n': {
                return "\\n";
            }
            case '\r': {
                return "\\r";
            }
            case '\t': {
                return "\\t";
            }
            case '\'': {
                return "\\'";
            }
            case '\"': {
                return "\\\"";
            }
            case '\\': {
                return "\\\\";
            }
        }
        if (c < ' ') {
            return String.format("\\%03o", c);
        }
        return String.valueOf(c);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return true;
    }
}

