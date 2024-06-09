package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.transformer.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import java.text.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.util.*;
import java.util.*;
import org.apache.commons.io.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class ExtensionCheckInterfaces implements IExtension
{
    private static final String AUDIT_DIR = "audit";
    private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
    private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
    private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
    private static final Logger logger;
    private final File csv;
    private final File report;
    private final Multimap<ClassInfo, ClassInfo.Method> interfaceMethods;
    private boolean strict;
    
    public ExtensionCheckInterfaces() {
        super();
        this.interfaceMethods = (Multimap<ClassInfo, ClassInfo.Method>)HashMultimap.create();
        final File file = new File(Constants.DEBUG_OUTPUT_DIR, "audit");
        file.mkdirs();
        this.csv = new File(file, "mixin_implementation_report.csv");
        this.report = new File(file, "mixin_implementation_report.txt");
        try {
            Files.write((CharSequence)"Class,Method,Signature,Interface\n", this.csv, Charsets.ISO_8859_1);
        }
        catch (IOException ex) {}
        try {
            Files.write((CharSequence)("Mixin Implementation Report generated on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n"), this.report, Charsets.ISO_8859_1);
        }
        catch (IOException ex2) {}
    }
    
    @Override
    public boolean checkActive(final MixinEnvironment mixinEnvironment) {
        this.strict = mixinEnvironment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT);
        return mixinEnvironment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS);
    }
    
    @Override
    public void preApply(final ITargetClassContext targetClassContext) {
        final ClassInfo classInfo = targetClassContext.getClassInfo();
        final Iterator<ClassInfo.Method> iterator = classInfo.getInterfaceMethods(false).iterator();
        while (iterator.hasNext()) {
            this.interfaceMethods.put((Object)classInfo, (Object)iterator.next());
        }
    }
    
    @Override
    public void postApply(final ITargetClassContext targetClassContext) {
        final ClassInfo classInfo = targetClassContext.getClassInfo();
        if (classInfo.isAbstract() && !this.strict) {
            ExtensionCheckInterfaces.logger.info("{} is skipping abstract target {}", new Object[] { this.getClass().getSimpleName(), targetClassContext });
            return;
        }
        final String replace = classInfo.getName().replace('/', '.');
        int n = 0;
        final PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.add("Class: %s", replace).hr();
        prettyPrinter.add("%-32s %-47s  %s", "Return Type", "Missing Method", "From Interface").hr();
        final Set<ClassInfo.Method> interfaceMethods = classInfo.getInterfaceMethods(true);
        final HashSet set = new HashSet(classInfo.getSuperClass().getInterfaceMethods(true));
        set.addAll(this.interfaceMethods.removeAll((Object)classInfo));
        for (final ClassInfo.Method method : interfaceMethods) {
            final ClassInfo.Method methodInHierarchy = classInfo.findMethodInHierarchy(method.getName(), method.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
            if (methodInHierarchy != null && !methodInHierarchy.isAbstract()) {
                continue;
            }
            if (set.contains(method)) {
                continue;
            }
            if (n > 0) {
                prettyPrinter.add();
            }
            final SignaturePrinter setModifiers = new SignaturePrinter(method.getName(), method.getDesc()).setModifiers("");
            final String replace2 = method.getOwner().getName().replace('/', '.');
            ++n;
            prettyPrinter.add("%-32s%s", setModifiers.getReturnType(), setModifiers);
            prettyPrinter.add("%-80s  %s", "", replace2);
            this.appendToCSVReport(replace, method, replace2);
        }
        if (n > 0) {
            prettyPrinter.hr().add("%82s%s: %d", "", "Total unimplemented", n);
            prettyPrinter.print(System.err);
            this.appendToTextReport(prettyPrinter);
        }
    }
    
    @Override
    public void export(final MixinEnvironment mixinEnvironment, final String s, final boolean b, final byte[] array) {
    }
    
    private void appendToCSVReport(final String s, final ClassInfo.Method method, final String s2) {
        try {
            Files.append((CharSequence)String.format("%s,%s,%s,%s\n", s, method.getName(), method.getDesc(), s2), this.csv, Charsets.ISO_8859_1);
        }
        catch (IOException ex) {}
    }
    
    private void appendToTextReport(final PrettyPrinter prettyPrinter) {
        OutputStream output = null;
        try {
            output = new FileOutputStream(this.report, true);
            final PrintStream printStream = new PrintStream(output);
            printStream.print("\n");
            prettyPrinter.print(printStream);
        }
        catch (Exception ex) {}
        finally {
            IOUtils.closeQuietly(output);
        }
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
