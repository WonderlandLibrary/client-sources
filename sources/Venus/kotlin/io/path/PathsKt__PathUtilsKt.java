/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.io.path;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.internal.InlineOnly;
import kotlin.io.CloseableKt;
import kotlin.io.path.ExperimentalPathApi;
import kotlin.io.path.FileVisitorBuilder;
import kotlin.io.path.FileVisitorBuilderImpl;
import kotlin.io.path.PathRelativizer;
import kotlin.io.path.PathTreeWalk;
import kotlin.io.path.PathWalkOption;
import kotlin.io.path.PathsKt;
import kotlin.io.path.PathsKt__PathRecursiveFunctionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u00cc\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a*\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00012\u0012\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u001a\"\u00020\u0001H\u0087\b\u00a2\u0006\u0002\u0010\u001b\u001a?\u0010\u001c\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007\u00a2\u0006\u0002\u0010!\u001a6\u0010\u001c\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010\"\u001aK\u0010#\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007\u00a2\u0006\u0002\u0010%\u001aB\u0010#\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010&\u001a\u001c\u0010'\u001a\u00020(2\u0006\u0010\u0017\u001a\u00020\u00022\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*H\u0001\u001a4\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00020,2\u0017\u0010-\u001a\u0013\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u0002000.\u00a2\u0006\u0002\b1H\u0007\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\r\u00102\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u00103\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a.\u00104\u001a\u00020\u0002*\u00020\u00022\u0006\u00105\u001a\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u0002070\u001a\"\u000207H\u0087\b\u00a2\u0006\u0002\u00108\u001a\u001f\u00104\u001a\u00020\u0002*\u00020\u00022\u0006\u00105\u001a\u00020\u00022\b\b\u0002\u00109\u001a\u00020:H\u0087\b\u001a.\u0010;\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010<\u001a.\u0010=\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010<\u001a.\u0010>\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010<\u001a\u0015\u0010?\u001a\u00020\u0002*\u00020\u00022\u0006\u00105\u001a\u00020\u0002H\u0087\b\u001a-\u0010@\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007\u00a2\u0006\u0002\u0010<\u001a6\u0010A\u001a\u00020\u0002*\u00020\u00022\u0006\u00105\u001a\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010B\u001a\r\u0010C\u001a\u000200*\u00020\u0002H\u0087\b\u001a\r\u0010D\u001a\u00020:*\u00020\u0002H\u0087\b\u001a\u0015\u0010E\u001a\u00020\u0002*\u00020\u00022\u0006\u0010F\u001a\u00020\u0002H\u0087\n\u001a\u0015\u0010E\u001a\u00020\u0002*\u00020\u00022\u0006\u0010F\u001a\u00020\u0001H\u0087\n\u001a&\u0010G\u001a\u00020:*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010I\u001a2\u0010J\u001a\u0002HK\"\n\b\u0000\u0010K\u0018\u0001*\u00020L*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010M\u001a4\u0010N\u001a\u0004\u0018\u0001HK\"\n\b\u0000\u0010K\u0018\u0001*\u00020L*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010M\u001a\r\u0010O\u001a\u00020P*\u00020\u0002H\u0087\b\u001a\r\u0010Q\u001a\u00020R*\u00020\u0002H\u0087\b\u001a.\u0010S\u001a\u000200*\u00020\u00022\b\b\u0002\u0010T\u001a\u00020\u00012\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002000.H\u0087\b\u00f8\u0001\u0000\u001a0\u0010V\u001a\u0004\u0018\u00010W*\u00020\u00022\u0006\u0010X\u001a\u00020\u00012\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010Y\u001a&\u0010Z\u001a\u00020[*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010\\\u001a(\u0010]\u001a\u0004\u0018\u00010^*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010_\u001a,\u0010`\u001a\b\u0012\u0004\u0012\u00020b0a*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010c\u001a&\u0010d\u001a\u00020:*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010I\u001a\r\u0010e\u001a\u00020:*\u00020\u0002H\u0087\b\u001a\r\u0010f\u001a\u00020:*\u00020\u0002H\u0087\b\u001a\r\u0010g\u001a\u00020:*\u00020\u0002H\u0087\b\u001a&\u0010h\u001a\u00020:*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010I\u001a\u0015\u0010i\u001a\u00020:*\u00020\u00022\u0006\u0010F\u001a\u00020\u0002H\u0087\b\u001a\r\u0010j\u001a\u00020:*\u00020\u0002H\u0087\b\u001a\r\u0010k\u001a\u00020:*\u00020\u0002H\u0087\b\u001a\u001c\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00020m*\u00020\u00022\b\b\u0002\u0010T\u001a\u00020\u0001H\u0007\u001a.\u0010n\u001a\u00020\u0002*\u00020\u00022\u0006\u00105\u001a\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u0002070\u001a\"\u000207H\u0087\b\u00a2\u0006\u0002\u00108\u001a\u001f\u0010n\u001a\u00020\u0002*\u00020\u00022\u0006\u00105\u001a\u00020\u00022\b\b\u0002\u00109\u001a\u00020:H\u0087\b\u001a&\u0010o\u001a\u00020:*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010I\u001a2\u0010p\u001a\u0002Hq\"\n\b\u0000\u0010q\u0018\u0001*\u00020r*\u00020\u00022\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010s\u001a<\u0010p\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010W0t*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00012\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010u\u001a\r\u0010v\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0014\u0010w\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0016\u0010x\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0014\u0010y\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a8\u0010z\u001a\u00020\u0002*\u00020\u00022\u0006\u0010X\u001a\u00020\u00012\b\u0010{\u001a\u0004\u0018\u00010W2\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020H0\u001a\"\u00020HH\u0087\b\u00a2\u0006\u0002\u0010|\u001a\u0015\u0010}\u001a\u00020\u0002*\u00020\u00022\u0006\u0010{\u001a\u00020[H\u0087\b\u001a\u0015\u0010~\u001a\u00020\u0002*\u00020\u00022\u0006\u0010{\u001a\u00020^H\u0087\b\u001a\u001b\u0010\u007f\u001a\u00020\u0002*\u00020\u00022\f\u0010{\u001a\b\u0012\u0004\u0012\u00020b0aH\u0087\b\u001a\u000f\u0010\u0080\u0001\u001a\u00020\u0002*\u00030\u0081\u0001H\u0087\b\u001aF\u0010\u0082\u0001\u001a\u0003H\u0083\u0001\"\u0005\b\u0000\u0010\u0083\u0001*\u00020\u00022\b\b\u0002\u0010T\u001a\u00020\u00012\u001b\u0010\u0084\u0001\u001a\u0016\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00020\u0085\u0001\u0012\u0005\u0012\u0003H\u0083\u00010.H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0086\u0001\u001a3\u0010\u0087\u0001\u001a\u000200*\u00020\u00022\r\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020\u00020,2\n\b\u0002\u0010\u0089\u0001\u001a\u00030\u008a\u00012\t\b\u0002\u0010\u008b\u0001\u001a\u00020:H\u0007\u001aJ\u0010\u0087\u0001\u001a\u000200*\u00020\u00022\n\b\u0002\u0010\u0089\u0001\u001a\u00030\u008a\u00012\t\b\u0002\u0010\u008b\u0001\u001a\u00020:2\u0017\u0010-\u001a\u0013\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u0002000.\u00a2\u0006\u0002\b1H\u0007\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0003 \u0001\u001a0\u0010\u008c\u0001\u001a\t\u0012\u0004\u0012\u00020\u00020\u0085\u0001*\u00020\u00022\u0014\u00106\u001a\u000b\u0012\u0007\b\u0001\u0012\u00030\u008d\u00010\u001a\"\u00030\u008d\u0001H\u0007\u00a2\u0006\u0003\u0010\u008e\u0001\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001f\u0010\u0007\u001a\u00020\u0001*\u00020\u00028\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"\u001e\u0010\n\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006\"\u001e\u0010\r\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006\"\u001e\u0010\u0010\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0006\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00028\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u0004\u001a\u0004\b\u0015\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u008f\u0001"}, d2={"extension", "", "Ljava/nio/file/Path;", "getExtension$annotations", "(Ljava/nio/file/Path;)V", "getExtension", "(Ljava/nio/file/Path;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath$annotations", "getInvariantSeparatorsPath", "invariantSeparatorsPathString", "getInvariantSeparatorsPathString$annotations", "getInvariantSeparatorsPathString", "name", "getName$annotations", "getName", "nameWithoutExtension", "getNameWithoutExtension$annotations", "getNameWithoutExtension", "pathString", "getPathString$annotations", "getPathString", "Path", "path", "base", "subpaths", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", "createTempDirectory", "directory", "prefix", "attributes", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createTempFile", "suffix", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "fileAttributeViewNotAvailable", "", "attributeViewClass", "Ljava/lang/Class;", "fileVisitor", "Ljava/nio/file/FileVisitor;", "builderAction", "Lkotlin/Function1;", "Lkotlin/io/path/FileVisitorBuilder;", "", "Lkotlin/ExtensionFunctionType;", "absolute", "absolutePathString", "copyTo", "target", "options", "Ljava/nio/file/CopyOption;", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", "overwrite", "", "createDirectories", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createDirectory", "createFile", "createLinkPointingTo", "createParentDirectories", "createSymbolicLinkPointingTo", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "deleteExisting", "deleteIfExists", "div", "other", "exists", "Ljava/nio/file/LinkOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "fileAttributesView", "V", "Ljava/nio/file/attribute/FileAttributeView;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileAttributeView;", "fileAttributesViewOrNull", "fileSize", "", "fileStore", "Ljava/nio/file/FileStore;", "forEachDirectoryEntry", "glob", "action", "getAttribute", "", "attribute", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/lang/Object;", "getLastModifiedTime", "Ljava/nio/file/attribute/FileTime;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileTime;", "getOwner", "Ljava/nio/file/attribute/UserPrincipal;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/UserPrincipal;", "getPosixFilePermissions", "", "Ljava/nio/file/attribute/PosixFilePermission;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/util/Set;", "isDirectory", "isExecutable", "isHidden", "isReadable", "isRegularFile", "isSameFileAs", "isSymbolicLink", "isWritable", "listDirectoryEntries", "", "moveTo", "notExists", "readAttributes", "A", "Ljava/nio/file/attribute/BasicFileAttributes;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/BasicFileAttributes;", "", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/util/Map;", "readSymbolicLink", "relativeTo", "relativeToOrNull", "relativeToOrSelf", "setAttribute", "value", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/Object;[Ljava/nio/file/LinkOption;)Ljava/nio/file/Path;", "setLastModifiedTime", "setOwner", "setPosixFilePermissions", "toPath", "Ljava/net/URI;", "useDirectoryEntries", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/nio/file/Path;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "visitFileTree", "visitor", "maxDepth", "", "followLinks", "walk", "Lkotlin/io/path/PathWalkOption;", "(Ljava/nio/file/Path;[Lkotlin/io/path/PathWalkOption;)Lkotlin/sequences/Sequence;", "kotlin-stdlib-jdk7"}, xs="kotlin/io/path/PathsKt")
@SourceDebugExtension(value={"SMAP\nPathUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathUtils.kt\nkotlin/io/path/PathsKt__PathUtilsKt\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1174:1\n26#2:1175\n26#2:1179\n1#3:1176\n1855#4,2:1177\n*S KotlinDebug\n*F\n+ 1 PathUtils.kt\nkotlin/io/path/PathsKt__PathUtilsKt\n*L\n221#1:1175\n616#1:1179\n440#1:1177,2\n*E\n"})
class PathsKt__PathUtilsKt
extends PathsKt__PathRecursiveFunctionsKt {
    @NotNull
    public static final String getName(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Path path2 = path.getFileName();
        String string = path2 != null ? ((Object)path2).toString() : null;
        if (string == null) {
            string = "";
        }
        return string;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static void getName$annotations(Path path) {
    }

    @NotNull
    public static final String getNameWithoutExtension(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Object object = path.getFileName();
        if (object == null || (object = object.toString()) == null || (object = StringsKt.substringBeforeLast$default((String)object, ".", null, 2, null)) == null) {
            object = "";
        }
        return object;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static void getNameWithoutExtension$annotations(Path path) {
    }

    @NotNull
    public static final String getExtension(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Object object = path.getFileName();
        if (object == null || (object = object.toString()) == null || (object = StringsKt.substringAfterLast((String)object, '.', "")) == null) {
            object = "";
        }
        return object;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static void getExtension$annotations(Path path) {
    }

    private static final String getPathString(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return ((Object)path).toString();
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    public static void getPathString$annotations(Path path) {
    }

    @NotNull
    public static final String getInvariantSeparatorsPathString(@NotNull Path path) {
        String string;
        Intrinsics.checkNotNullParameter(path, "<this>");
        String string2 = path.getFileSystem().getSeparator();
        if (!Intrinsics.areEqual(string2, "/")) {
            String string3 = ((Object)path).toString();
            Intrinsics.checkNotNullExpressionValue(string2, "separator");
            string = StringsKt.replace$default(string3, string2, "/", false, 4, null);
        } else {
            string = ((Object)path).toString();
        }
        return string;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static void getInvariantSeparatorsPathString$annotations(Path path) {
    }

    private static final String getInvariantSeparatorsPath(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return PathsKt.getInvariantSeparatorsPathString(path);
    }

    @Deprecated(message="Use invariantSeparatorsPathString property instead.", replaceWith=@ReplaceWith(expression="invariantSeparatorsPathString", imports={}), level=DeprecationLevel.ERROR)
    @SinceKotlin(version="1.4")
    @ExperimentalPathApi
    @InlineOnly
    public static void getInvariantSeparatorsPath$annotations(Path path) {
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path absolute(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Path path2 = path.toAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(path2, "toAbsolutePath()");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final String absolutePathString(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return ((Object)path.toAbsolutePath()).toString();
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path relativeTo(@NotNull Path path, @NotNull Path path2) {
        Path path3;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "base");
        try {
            path3 = PathRelativizer.INSTANCE.tryRelativeTo(path, path2);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(illegalArgumentException.getMessage() + "\nthis path: " + path + "\nbase path: " + path2, illegalArgumentException);
        }
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path relativeToOrSelf(@NotNull Path path, @NotNull Path path2) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "base");
        Path path3 = PathsKt.relativeToOrNull(path, path2);
        if (path3 == null) {
            path3 = path;
        }
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @Nullable
    public static final Path relativeToOrNull(@NotNull Path path, @NotNull Path path2) {
        Path path3;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "base");
        try {
            path3 = PathRelativizer.INSTANCE.tryRelativeTo(path, path2);
        } catch (IllegalArgumentException illegalArgumentException) {
            path3 = null;
        }
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path copyTo(Path path, Path path2, boolean bl) throws IOException {
        CopyOption[] copyOptionArray;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        if (bl) {
            CopyOption[] copyOptionArray2 = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray = copyOptionArray2;
        } else {
            boolean bl2 = false;
            copyOptionArray = new CopyOption[]{};
        }
        CopyOption[] copyOptionArray3 = copyOptionArray;
        Path path3 = Files.copy(path, path2, Arrays.copyOf(copyOptionArray3, copyOptionArray3.length));
        Intrinsics.checkNotNullExpressionValue(path3, "copy(this, target, *options)");
        return path3;
    }

    static Path copyTo$default(Path path, Path path2, boolean bl, int n, Object copyOptionArray) throws IOException {
        CopyOption[] copyOptionArray2;
        if ((n & 2) != 0) {
            bl = false;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        if (bl) {
            copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray2 = copyOptionArray;
        } else {
            boolean bl2 = false;
            copyOptionArray2 = new CopyOption[]{};
        }
        CopyOption[] copyOptionArray3 = copyOptionArray2;
        Path path3 = Files.copy(path, path2, Arrays.copyOf(copyOptionArray3, copyOptionArray3.length));
        Intrinsics.checkNotNullExpressionValue(path3, "copy(this, target, *options)");
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path copyTo(Path path, Path path2, CopyOption ... copyOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        Intrinsics.checkNotNullParameter(copyOptionArray, "options");
        Path path3 = Files.copy(path, path2, Arrays.copyOf(copyOptionArray, copyOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(path3, "copy(this, target, *options)");
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean exists(Path path, LinkOption ... linkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        return Files.exists(path, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean notExists(Path path, LinkOption ... linkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        return Files.notExists(path, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isRegularFile(Path path, LinkOption ... linkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        return Files.isRegularFile(path, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isDirectory(Path path, LinkOption ... linkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        return Files.isDirectory(path, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isSymbolicLink(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return Files.isSymbolicLink(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isExecutable(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return Files.isExecutable(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isHidden(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return Files.isHidden(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isReadable(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return Files.isReadable(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isWritable(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return Files.isWritable(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isSameFileAs(Path path, Path path2) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "other");
        return Files.isSameFile(path, path2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final List<Path> listDirectoryEntries(@NotNull Path path, @NotNull String string) throws IOException {
        Iterable iterable;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "glob");
        Closeable closeable = Files.newDirectoryStream(path, string);
        Throwable throwable = null;
        try {
            iterable = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(iterable, "it");
            iterable = CollectionsKt.toList(iterable);
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
        return iterable;
    }

    public static List listDirectoryEntries$default(Path path, String string, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            string = "*";
        }
        return PathsKt.listDirectoryEntries(path, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final <T> T useDirectoryEntries(Path path, String string, Function1<? super Sequence<? extends Path>, ? extends T> function1) throws IOException {
        DirectoryStream directoryStream;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "glob");
        Intrinsics.checkNotNullParameter(function1, "block");
        Closeable closeable = Files.newDirectoryStream(path, string);
        Throwable throwable = null;
        try {
            directoryStream = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(directoryStream, "it");
            directoryStream = function1.invoke(CollectionsKt.asSequence(directoryStream));
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, throwable);
            InlineMarker.finallyEnd(1);
        }
        return (T)directoryStream;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Object useDirectoryEntries$default(Path path, String string, Function1 function1, int n, Object object) throws IOException {
        DirectoryStream<Object> directoryStream;
        if ((n & 1) != 0) {
            string = "*";
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "glob");
        Intrinsics.checkNotNullParameter(function1, "block");
        Closeable closeable = Files.newDirectoryStream(path, string);
        object = null;
        try {
            directoryStream = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(directoryStream, "it");
            directoryStream = function1.invoke(CollectionsKt.asSequence(directoryStream));
        } catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, (Throwable)object);
            InlineMarker.finallyEnd(1);
        }
        return directoryStream;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final void forEachDirectoryEntry(Path path, String string, Function1<? super Path, Unit> function1) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "glob");
        Intrinsics.checkNotNullParameter(function1, "action");
        Closeable closeable = Files.newDirectoryStream(path, string);
        Throwable throwable = null;
        try {
            Object object = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(object, "it");
            Iterable iterable = (Iterable)object;
            boolean bl2 = false;
            for (Object t : iterable) {
                function1.invoke((Path)t);
            }
            object = Unit.INSTANCE;
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, throwable);
            InlineMarker.finallyEnd(1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void forEachDirectoryEntry$default(Path path, String string, Function1 function1, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            string = "*";
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "glob");
        Intrinsics.checkNotNullParameter(function1, "action");
        Closeable closeable = Files.newDirectoryStream(path, string);
        object = null;
        try {
            Object object2 = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(object2, "it");
            Iterable iterable = (Iterable)object2;
            boolean bl2 = false;
            for (Object t : iterable) {
                function1.invoke(t);
            }
            object2 = Unit.INSTANCE;
        } catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        } finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, (Throwable)object);
            InlineMarker.finallyEnd(1);
        }
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final long fileSize(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return Files.size(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final void deleteExisting(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Files.delete(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean deleteIfExists(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        return Files.deleteIfExists(path);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createDirectory(Path path, FileAttribute<?> ... fileAttributeArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path2 = Files.createDirectory(path, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "createDirectory(this, *attributes)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createDirectories(Path path, FileAttribute<?> ... fileAttributeArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path2 = Files.createDirectories(path, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "createDirectories(this, *attributes)");
        return path2;
    }

    @SinceKotlin(version="1.9")
    @NotNull
    public static final Path createParentDirectories(@NotNull Path path, @NotNull FileAttribute<?> ... fileAttributeArray) throws IOException {
        Path path2;
        block4: {
            Intrinsics.checkNotNullParameter(path, "<this>");
            Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
            Path path3 = path2 = path;
            boolean bl = false;
            Path path4 = path3.getParent();
            if (path4 != null) {
                LinkOption[] linkOptionArray = new LinkOption[]{};
                if (!Files.isDirectory(path4, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                    try {
                        FileAttribute<?>[] fileAttributeArray2 = Arrays.copyOf(fileAttributeArray, fileAttributeArray.length);
                        Intrinsics.checkNotNullExpressionValue(Files.createDirectories(path4, Arrays.copyOf(fileAttributeArray2, fileAttributeArray2.length)), "createDirectories(this, *attributes)");
                    } catch (FileAlreadyExistsException fileAlreadyExistsException) {
                        LinkOption[] linkOptionArray2 = new LinkOption[]{};
                        if (Files.isDirectory(path4, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length))) break block4;
                        throw fileAlreadyExistsException;
                    }
                }
            }
        }
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path moveTo(Path path, Path path2, CopyOption ... copyOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        Intrinsics.checkNotNullParameter(copyOptionArray, "options");
        Path path3 = Files.move(path, path2, Arrays.copyOf(copyOptionArray, copyOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(path3, "move(this, target, *options)");
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path moveTo(Path path, Path path2, boolean bl) throws IOException {
        CopyOption[] copyOptionArray;
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        if (bl) {
            CopyOption[] copyOptionArray2 = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray = copyOptionArray2;
        } else {
            boolean bl2 = false;
            copyOptionArray = new CopyOption[]{};
        }
        CopyOption[] copyOptionArray3 = copyOptionArray;
        Path path3 = Files.move(path, path2, Arrays.copyOf(copyOptionArray3, copyOptionArray3.length));
        Intrinsics.checkNotNullExpressionValue(path3, "move(this, target, *options)");
        return path3;
    }

    static Path moveTo$default(Path path, Path path2, boolean bl, int n, Object copyOptionArray) throws IOException {
        CopyOption[] copyOptionArray2;
        if ((n & 2) != 0) {
            bl = false;
        }
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        if (bl) {
            copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray2 = copyOptionArray;
        } else {
            boolean bl2 = false;
            copyOptionArray2 = new CopyOption[]{};
        }
        CopyOption[] copyOptionArray3 = copyOptionArray2;
        Path path3 = Files.move(path, path2, Arrays.copyOf(copyOptionArray3, copyOptionArray3.length));
        Intrinsics.checkNotNullExpressionValue(path3, "move(this, target, *options)");
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final FileStore fileStore(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        FileStore fileStore = Files.getFileStore(path);
        Intrinsics.checkNotNullExpressionValue(fileStore, "getFileStore(this)");
        return fileStore;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Object getAttribute(Path path, String string, LinkOption ... linkOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "attribute");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        return Files.getAttribute(path, string, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setAttribute(Path path, String string, Object object, LinkOption ... linkOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "attribute");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        Path path2 = Files.setAttribute(path, string, object, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "setAttribute(this, attribute, value, *options)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final <V extends FileAttributeView> V fileAttributesViewOrNull(Path path, LinkOption ... linkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        Intrinsics.reifiedOperationMarker(4, "V");
        return (V)Files.getFileAttributeView(path, FileAttributeView.class, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final <V extends FileAttributeView> V fileAttributesView(Path path, LinkOption ... linkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        Intrinsics.reifiedOperationMarker(4, "V");
        FileAttributeView fileAttributeView = Files.getFileAttributeView(path, FileAttributeView.class, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
        if (fileAttributeView == null) {
            Intrinsics.reifiedOperationMarker(4, "V");
            PathsKt.fileAttributeViewNotAvailable(path, FileAttributeView.class);
            throw new KotlinNothingValueException();
        }
        return (V)fileAttributeView;
    }

    @PublishedApi
    @NotNull
    public static final Void fileAttributeViewNotAvailable(@NotNull Path path, @NotNull Class<?> clazz) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(clazz, "attributeViewClass");
        throw new UnsupportedOperationException("The desired attribute view type " + clazz + " is not available for the file " + path + '.');
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final <A extends BasicFileAttributes> A readAttributes(Path path, LinkOption ... linkOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        Intrinsics.reifiedOperationMarker(4, "A");
        BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(basicFileAttributes, "readAttributes(this, A::class.java, *options)");
        return (A)basicFileAttributes;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Map<String, Object> readAttributes(Path path, String string, LinkOption ... linkOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "attributes");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        Map<String, Object> map = Files.readAttributes(path, string, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(map, "readAttributes(this, attributes, *options)");
        return map;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final FileTime getLastModifiedTime(Path path, LinkOption ... linkOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        FileTime fileTime = Files.getLastModifiedTime(path, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(fileTime, "getLastModifiedTime(this, *options)");
        return fileTime;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setLastModifiedTime(Path path, FileTime fileTime) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(fileTime, "value");
        Path path2 = Files.setLastModifiedTime(path, fileTime);
        Intrinsics.checkNotNullExpressionValue(path2, "setLastModifiedTime(this, value)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final UserPrincipal getOwner(Path path, LinkOption ... linkOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        return Files.getOwner(path, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setOwner(Path path, UserPrincipal userPrincipal) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(userPrincipal, "value");
        Path path2 = Files.setOwner(path, userPrincipal);
        Intrinsics.checkNotNullExpressionValue(path2, "setOwner(this, value)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Set<PosixFilePermission> getPosixFilePermissions(Path path, LinkOption ... linkOptionArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(linkOptionArray, "options");
        Set<PosixFilePermission> set = Files.getPosixFilePermissions(path, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
        Intrinsics.checkNotNullExpressionValue(set, "getPosixFilePermissions(this, *options)");
        return set;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setPosixFilePermissions(Path path, Set<? extends PosixFilePermission> set) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(set, "value");
        Path path2 = Files.setPosixFilePermissions(path, set);
        Intrinsics.checkNotNullExpressionValue(path2, "setPosixFilePermissions(this, value)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createLinkPointingTo(Path path, Path path2) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        Path path3 = Files.createLink(path, path2);
        Intrinsics.checkNotNullExpressionValue(path3, "createLink(this, target)");
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createSymbolicLinkPointingTo(Path path, Path path2, FileAttribute<?> ... fileAttributeArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "target");
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path3 = Files.createSymbolicLink(path, path2, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path3, "createSymbolicLink(this, target, *attributes)");
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path readSymbolicLink(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Path path2 = Files.readSymbolicLink(path);
        Intrinsics.checkNotNullExpressionValue(path2, "readSymbolicLink(this)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createFile(Path path, FileAttribute<?> ... fileAttributeArray) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path2 = Files.createFile(path, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path2, "createFile(this, *attributes)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createTempFile(String string, String string2, FileAttribute<?> ... fileAttributeArray) throws IOException {
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path = Files.createTempFile(string, string2, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempFile(prefix, suffix, *attributes)");
        return path;
    }

    static Path createTempFile$default(String string, String string2, FileAttribute[] fileAttributeArray, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path = Files.createTempFile(string, string2, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempFile(prefix, suffix, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path createTempFile(@Nullable Path path, @Nullable String string, @Nullable String string2, @NotNull FileAttribute<?> ... fileAttributeArray) throws IOException {
        Path path2;
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        if (path != null) {
            Path path3 = Files.createTempFile(path, string, string2, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
            path2 = path3;
            Intrinsics.checkNotNullExpressionValue(path3, "createTempFile(directory\u2026fix, suffix, *attributes)");
        } else {
            Path path4 = Files.createTempFile(string, string2, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
            path2 = path4;
            Intrinsics.checkNotNullExpressionValue(path4, "createTempFile(prefix, suffix, *attributes)");
        }
        return path2;
    }

    public static Path createTempFile$default(Path path, String string, String string2, FileAttribute[] fileAttributeArray, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            string = null;
        }
        if ((n & 4) != 0) {
            string2 = null;
        }
        return PathsKt.createTempFile(path, string, string2, fileAttributeArray);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createTempDirectory(String string, FileAttribute<?> ... fileAttributeArray) throws IOException {
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path = Files.createTempDirectory(string, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempDirectory(prefix, *attributes)");
        return path;
    }

    static Path createTempDirectory$default(String string, FileAttribute[] fileAttributeArray, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            string = null;
        }
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        Path path = Files.createTempDirectory(string, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempDirectory(prefix, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path createTempDirectory(@Nullable Path path, @Nullable String string, @NotNull FileAttribute<?> ... fileAttributeArray) throws IOException {
        Path path2;
        Intrinsics.checkNotNullParameter(fileAttributeArray, "attributes");
        if (path != null) {
            Path path3 = Files.createTempDirectory(path, string, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
            path2 = path3;
            Intrinsics.checkNotNullExpressionValue(path3, "createTempDirectory(dire\u2026ory, prefix, *attributes)");
        } else {
            Path path4 = Files.createTempDirectory(string, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length));
            path2 = path4;
            Intrinsics.checkNotNullExpressionValue(path4, "createTempDirectory(prefix, *attributes)");
        }
        return path2;
    }

    public static Path createTempDirectory$default(Path path, String string, FileAttribute[] fileAttributeArray, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            string = null;
        }
        return PathsKt.createTempDirectory(path, string, fileAttributeArray);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path div(Path path, Path path2) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(path2, "other");
        Path path3 = path.resolve(path2);
        Intrinsics.checkNotNullExpressionValue(path3, "this.resolve(other)");
        return path3;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path div(Path path, String string) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(string, "other");
        Path path2 = path.resolve(string);
        Intrinsics.checkNotNullExpressionValue(path2, "this.resolve(other)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path Path(String string) {
        Intrinsics.checkNotNullParameter(string, "path");
        Path path = Paths.get(string, new String[0]);
        Intrinsics.checkNotNullExpressionValue(path, "get(path)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path Path(String string, String ... stringArray) {
        Intrinsics.checkNotNullParameter(string, "base");
        Intrinsics.checkNotNullParameter(stringArray, "subpaths");
        Path path = Paths.get(string, Arrays.copyOf(stringArray, stringArray.length));
        Intrinsics.checkNotNullExpressionValue(path, "get(base, *subpaths)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path toPath(URI uRI) {
        Intrinsics.checkNotNullParameter(uRI, "<this>");
        Path path = Paths.get(uRI);
        Intrinsics.checkNotNullExpressionValue(path, "get(this)");
        return path;
    }

    @ExperimentalPathApi
    @SinceKotlin(version="1.7")
    @NotNull
    public static final Sequence<Path> walk(@NotNull Path path, @NotNull PathWalkOption ... pathWalkOptionArray) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(pathWalkOptionArray, "options");
        return new PathTreeWalk(path, pathWalkOptionArray);
    }

    @ExperimentalPathApi
    @SinceKotlin(version="1.7")
    public static final void visitFileTree(@NotNull Path path, @NotNull FileVisitor<Path> fileVisitor, int n, boolean bl) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(fileVisitor, "visitor");
        Set<FileVisitOption> set = bl ? SetsKt.setOf(FileVisitOption.FOLLOW_LINKS) : SetsKt.emptySet();
        Files.walkFileTree(path, set, n, fileVisitor);
    }

    public static void visitFileTree$default(Path path, FileVisitor fileVisitor, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = Integer.MAX_VALUE;
        }
        if ((n2 & 4) != 0) {
            bl = false;
        }
        PathsKt.visitFileTree(path, fileVisitor, n, bl);
    }

    @ExperimentalPathApi
    @SinceKotlin(version="1.7")
    public static final void visitFileTree(@NotNull Path path, int n, boolean bl, @NotNull Function1<? super FileVisitorBuilder, Unit> function1) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(function1, "builderAction");
        PathsKt.visitFileTree(path, PathsKt.fileVisitor(function1), n, bl);
    }

    public static void visitFileTree$default(Path path, int n, boolean bl, Function1 function1, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = Integer.MAX_VALUE;
        }
        if ((n2 & 2) != 0) {
            bl = false;
        }
        PathsKt.visitFileTree(path, n, bl, function1);
    }

    @ExperimentalPathApi
    @SinceKotlin(version="1.7")
    @NotNull
    public static final FileVisitor<Path> fileVisitor(@NotNull Function1<? super FileVisitorBuilder, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "builderAction");
        FileVisitorBuilderImpl fileVisitorBuilderImpl = new FileVisitorBuilderImpl();
        function1.invoke(fileVisitorBuilderImpl);
        return fileVisitorBuilderImpl.build();
    }
}

