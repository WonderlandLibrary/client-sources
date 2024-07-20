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

    public static boolean exists(String key) {
        return MARKERS.containsKey(key);
    }

    public static Marker getMarker(String name) {
        Marker result = (Marker)MARKERS.get(name);
        if (result == null) {
            MARKERS.putIfAbsent(name, new Log4jMarker(name));
            result = (Marker)MARKERS.get(name);
        }
        return result;
    }

    @Deprecated
    public static Marker getMarker(String name, String parent) {
        Marker parentMarker = (Marker)MARKERS.get(parent);
        if (parentMarker == null) {
            throw new IllegalArgumentException("Parent Marker " + parent + " has not been defined");
        }
        return MarkerManager.getMarker(name, parentMarker);
    }

    @Deprecated
    public static Marker getMarker(String name, Marker parent) {
        return MarkerManager.getMarker(name).addParents(parent);
    }

    private static void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
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

        public Log4jMarker(String name) {
            MarkerManager.requireNonNull(name, "Marker name cannot be null.");
            this.name = name;
            this.parents = null;
        }

        @Override
        public synchronized Marker addParents(Marker ... parentMarkers) {
            MarkerManager.requireNonNull(parentMarkers, "A parent marker must be specified");
            Marker[] localParents = this.parents;
            int count = 0;
            int size = parentMarkers.length;
            if (localParents != null) {
                for (Marker parent : parentMarkers) {
                    if (Log4jMarker.contains(parent, localParents) || parent.isInstanceOf(this)) continue;
                    ++count;
                }
                if (count == 0) {
                    return this;
                }
                size = localParents.length + count;
            }
            Marker[] markers = new Marker[size];
            if (localParents != null) {
                System.arraycopy(localParents, 0, markers, 0, localParents.length);
            }
            int index = localParents == null ? 0 : localParents.length;
            for (Marker parent : parentMarkers) {
                if (localParents != null && (Log4jMarker.contains(parent, localParents) || parent.isInstanceOf(this))) continue;
                markers[index++] = parent;
            }
            this.parents = markers;
            return this;
        }

        @Override
        public synchronized boolean remove(Marker parent) {
            MarkerManager.requireNonNull(parent, "A parent marker must be specified");
            Marker[] localParents = this.parents;
            if (localParents == null) {
                return false;
            }
            int localParentsLength = localParents.length;
            if (localParentsLength == 1) {
                if (localParents[0].equals(parent)) {
                    this.parents = null;
                    return true;
                }
                return false;
            }
            int index = 0;
            Marker[] markers = new Marker[localParentsLength - 1];
            for (int i = 0; i < localParentsLength; ++i) {
                Marker marker = localParents[i];
                if (marker.equals(parent)) continue;
                if (index == localParentsLength - 1) {
                    return false;
                }
                markers[index++] = marker;
            }
            this.parents = markers;
            return true;
        }

        @Override
        public Marker setParents(Marker ... markers) {
            if (markers == null || markers.length == 0) {
                this.parents = null;
            } else {
                Marker[] array = new Marker[markers.length];
                System.arraycopy(markers, 0, array, 0, markers.length);
                this.parents = array;
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
            MarkerManager.requireNonNull(marker, "A marker parameter is required");
            if (this == marker) {
                return true;
            }
            Marker[] localParents = this.parents;
            if (localParents != null) {
                int localParentsLength = localParents.length;
                if (localParentsLength == 1) {
                    return Log4jMarker.checkParent(localParents[0], marker);
                }
                if (localParentsLength == 2) {
                    return Log4jMarker.checkParent(localParents[0], marker) || Log4jMarker.checkParent(localParents[1], marker);
                }
                for (int i = 0; i < localParentsLength; ++i) {
                    Marker localParent = localParents[i];
                    if (!Log4jMarker.checkParent(localParent, marker)) continue;
                    return true;
                }
            }
            return false;
        }

        @Override
        @PerformanceSensitive(value={"allocation", "unrolled"})
        public boolean isInstanceOf(String markerName) {
            MarkerManager.requireNonNull(markerName, "A marker name is required");
            if (markerName.equals(this.getName())) {
                return true;
            }
            Marker marker = (Marker)MARKERS.get(markerName);
            if (marker == null) {
                return false;
            }
            Marker[] localParents = this.parents;
            if (localParents != null) {
                int localParentsLength = localParents.length;
                if (localParentsLength == 1) {
                    return Log4jMarker.checkParent(localParents[0], marker);
                }
                if (localParentsLength == 2) {
                    return Log4jMarker.checkParent(localParents[0], marker) || Log4jMarker.checkParent(localParents[1], marker);
                }
                for (int i = 0; i < localParentsLength; ++i) {
                    Marker localParent = localParents[i];
                    if (!Log4jMarker.checkParent(localParent, marker)) continue;
                    return true;
                }
            }
            return false;
        }

        @PerformanceSensitive(value={"allocation", "unrolled"})
        private static boolean checkParent(Marker parent, Marker marker) {
            Marker[] localParents;
            if (parent == marker) {
                return true;
            }
            Marker[] markerArray = localParents = parent instanceof Log4jMarker ? ((Log4jMarker)parent).parents : parent.getParents();
            if (localParents != null) {
                int localParentsLength = localParents.length;
                if (localParentsLength == 1) {
                    return Log4jMarker.checkParent(localParents[0], marker);
                }
                if (localParentsLength == 2) {
                    return Log4jMarker.checkParent(localParents[0], marker) || Log4jMarker.checkParent(localParents[1], marker);
                }
                for (int i = 0; i < localParentsLength; ++i) {
                    Marker localParent = localParents[i];
                    if (!Log4jMarker.checkParent(localParent, marker)) continue;
                    return true;
                }
            }
            return false;
        }

        @PerformanceSensitive(value={"allocation"})
        private static boolean contains(Marker parent, Marker ... localParents) {
            for (Marker marker : localParents) {
                if (marker != parent) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof Marker)) {
                return false;
            }
            Marker marker = (Marker)o;
            return this.name.equals(marker.getName());
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            this.formatTo(sb);
            return sb.toString();
        }

        @Override
        public void formatTo(StringBuilder sb) {
            sb.append(this.name);
            Marker[] localParents = this.parents;
            if (localParents != null) {
                Log4jMarker.addParentInfo(sb, localParents);
            }
        }

        @PerformanceSensitive(value={"allocation"})
        private static void addParentInfo(StringBuilder sb, Marker ... parents) {
            sb.append("[ ");
            boolean first = true;
            for (Marker marker : parents) {
                Marker[] p;
                if (!first) {
                    sb.append(", ");
                }
                first = false;
                sb.append(marker.getName());
                Marker[] markerArray = p = marker instanceof Log4jMarker ? ((Log4jMarker)marker).parents : marker.getParents();
                if (p == null) continue;
                Log4jMarker.addParentInfo(sb, p);
            }
            sb.append(" ]");
        }
    }
}

