/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public final class MarkerManager {
    private static final ConcurrentMap<String, Marker> MARKERS = new ConcurrentHashMap<String, Marker>();

    private MarkerManager() {
    }

    public static void clear() {
        MARKERS.clear();
    }

    public static boolean exists(String string) {
        return MARKERS.containsKey(string);
    }

    public static Marker getMarker(String string) {
        Marker marker = (Marker)MARKERS.get(string);
        if (marker == null) {
            MARKERS.putIfAbsent(string, new Log4jMarker(string));
            marker = (Marker)MARKERS.get(string);
        }
        return marker;
    }

    @Deprecated
    public static Marker getMarker(String string, String string2) {
        Marker marker = (Marker)MARKERS.get(string2);
        if (marker == null) {
            throw new IllegalArgumentException("Parent Marker " + string2 + " has not been defined");
        }
        return MarkerManager.getMarker(string, marker);
    }

    @Deprecated
    public static Marker getMarker(String string, Marker marker) {
        return MarkerManager.getMarker(string).addParents(marker);
    }

    private static void requireNonNull(Object object, String string) {
        if (object == null) {
            throw new IllegalArgumentException(string);
        }
    }

    static void access$000(Object object, String string) {
        MarkerManager.requireNonNull(object, string);
    }

    static ConcurrentMap access$100() {
        return MARKERS;
    }

    public static class Log4jMarker
    implements Marker,
    StringBuilderFormattable {
        private static final long serialVersionUID = 100L;
        private final String name;
        private volatile Marker[] parents;

        private Log4jMarker() {
            this.name = null;
            this.parents = null;
        }

        public Log4jMarker(String string) {
            MarkerManager.access$000(string, "Marker name cannot be null.");
            this.name = string;
            this.parents = null;
        }

        @Override
        public synchronized Marker addParents(Marker ... markerArray) {
            MarkerManager.access$000(markerArray, "A parent marker must be specified");
            Marker[] markerArray2 = this.parents;
            int n = 0;
            int n2 = markerArray.length;
            if (markerArray2 != null) {
                for (Marker marker : markerArray) {
                    if (Log4jMarker.contains(marker, markerArray2) || marker.isInstanceOf(this)) continue;
                    ++n;
                }
                if (n == 0) {
                    return this;
                }
                n2 = markerArray2.length + n;
            }
            Marker[] markerArray3 = new Marker[n2];
            if (markerArray2 != null) {
                System.arraycopy(markerArray2, 0, markerArray3, 0, markerArray2.length);
            }
            int n3 = markerArray2 == null ? 0 : markerArray2.length;
            for (Marker marker : markerArray) {
                if (markerArray2 != null && (Log4jMarker.contains(marker, markerArray2) || marker.isInstanceOf(this))) continue;
                markerArray3[n3++] = marker;
            }
            this.parents = markerArray3;
            return this;
        }

        @Override
        public synchronized boolean remove(Marker marker) {
            MarkerManager.access$000(marker, "A parent marker must be specified");
            Marker[] markerArray = this.parents;
            if (markerArray == null) {
                return true;
            }
            int n = markerArray.length;
            if (n == 1) {
                if (markerArray[0].equals(marker)) {
                    this.parents = null;
                    return false;
                }
                return true;
            }
            int n2 = 0;
            Marker[] markerArray2 = new Marker[n - 1];
            for (int i = 0; i < n; ++i) {
                Marker marker2 = markerArray[i];
                if (marker2.equals(marker)) continue;
                if (n2 == n - 1) {
                    return true;
                }
                markerArray2[n2++] = marker2;
            }
            this.parents = markerArray2;
            return false;
        }

        @Override
        public Marker setParents(Marker ... markerArray) {
            if (markerArray == null || markerArray.length == 0) {
                this.parents = null;
            } else {
                Marker[] markerArray2 = new Marker[markerArray.length];
                System.arraycopy(markerArray, 0, markerArray2, 0, markerArray.length);
                this.parents = markerArray2;
            }
            return this;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Marker[] getParents() {
            if (this.parents == null) {
                return null;
            }
            return Arrays.copyOf(this.parents, this.parents.length);
        }

        @Override
        public boolean hasParents() {
            return this.parents != null;
        }

        @Override
        @PerformanceSensitive(value={"allocation", "unrolled"})
        public boolean isInstanceOf(Marker marker) {
            MarkerManager.access$000(marker, "A marker parameter is required");
            if (this == marker) {
                return false;
            }
            Marker[] markerArray = this.parents;
            if (markerArray != null) {
                int n = markerArray.length;
                if (n == 1) {
                    return Log4jMarker.checkParent(markerArray[0], marker);
                }
                if (n == 2) {
                    return Log4jMarker.checkParent(markerArray[0], marker) || Log4jMarker.checkParent(markerArray[5], marker);
                }
                for (int i = 0; i < n; ++i) {
                    Marker marker2 = markerArray[i];
                    if (!Log4jMarker.checkParent(marker2, marker)) continue;
                    return false;
                }
            }
            return true;
        }

        @Override
        @PerformanceSensitive(value={"allocation", "unrolled"})
        public boolean isInstanceOf(String string) {
            MarkerManager.access$000(string, "A marker name is required");
            if (string.equals(this.getName())) {
                return false;
            }
            Marker marker = (Marker)MarkerManager.access$100().get(string);
            if (marker == null) {
                return true;
            }
            Marker[] markerArray = this.parents;
            if (markerArray != null) {
                int n = markerArray.length;
                if (n == 1) {
                    return Log4jMarker.checkParent(markerArray[0], marker);
                }
                if (n == 2) {
                    return Log4jMarker.checkParent(markerArray[0], marker) || Log4jMarker.checkParent(markerArray[5], marker);
                }
                for (int i = 0; i < n; ++i) {
                    Marker marker2 = markerArray[i];
                    if (!Log4jMarker.checkParent(marker2, marker)) continue;
                    return false;
                }
            }
            return true;
        }

        @PerformanceSensitive(value={"allocation", "unrolled"})
        private static boolean checkParent(Marker marker, Marker marker2) {
            Marker[] markerArray;
            if (marker == marker2) {
                return false;
            }
            Marker[] markerArray2 = markerArray = marker instanceof Log4jMarker ? ((Log4jMarker)marker).parents : marker.getParents();
            if (markerArray != null) {
                int n = markerArray.length;
                if (n == 1) {
                    return Log4jMarker.checkParent(markerArray[0], marker2);
                }
                if (n == 2) {
                    return Log4jMarker.checkParent(markerArray[0], marker2) || Log4jMarker.checkParent(markerArray[5], marker2);
                }
                for (int i = 0; i < n; ++i) {
                    Marker marker3 = markerArray[i];
                    if (!Log4jMarker.checkParent(marker3, marker2)) continue;
                    return false;
                }
            }
            return true;
        }

        @PerformanceSensitive(value={"allocation"})
        private static boolean contains(Marker marker, Marker ... markerArray) {
            for (Marker marker2 : markerArray) {
                if (marker2 != marker) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || !(object instanceof Marker)) {
                return true;
            }
            Marker marker = (Marker)object;
            return this.name.equals(marker.getName());
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            this.formatTo(stringBuilder);
            return stringBuilder.toString();
        }

        @Override
        public void formatTo(StringBuilder stringBuilder) {
            stringBuilder.append(this.name);
            Marker[] markerArray = this.parents;
            if (markerArray != null) {
                Log4jMarker.addParentInfo(stringBuilder, markerArray);
            }
        }

        @PerformanceSensitive(value={"allocation"})
        private static void addParentInfo(StringBuilder stringBuilder, Marker ... markerArray) {
            stringBuilder.append("[ ");
            boolean bl = true;
            for (Marker marker : markerArray) {
                Marker[] markerArray2;
                if (!bl) {
                    stringBuilder.append(", ");
                }
                bl = false;
                stringBuilder.append(marker.getName());
                Marker[] markerArray3 = markerArray2 = marker instanceof Log4jMarker ? ((Log4jMarker)marker).parents : marker.getParents();
                if (markerArray2 == null) continue;
                Log4jMarker.addParentInfo(stringBuilder, markerArray2);
            }
            stringBuilder.append(" ]");
        }
    }
}

