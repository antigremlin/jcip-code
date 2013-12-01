package net.jcip.vehicles;

import java.util.HashMap;
import java.util.Map;

public class MonitorVehicleTracker {

    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> data) {
        locations = deepCopy(data);
    }

    public synchronized MutablePoint getLocation(String name) {
        return new MutablePoint(safeGet(name));
    }

    public synchronized void setLocation(String name, MutablePoint point) {
        MutablePoint loc = safeGet(name);
        loc.x = point.x;
        loc.y = point.y;
    }

    private MutablePoint safeGet(String name) {
        MutablePoint loc = locations.get(name);
        if (loc == null) throw new IllegalArgumentException("No such ID: " + name);
        return loc;
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> src) {
        Map<String, MutablePoint> copy = new HashMap<String, MutablePoint>();
        for (String id : src.keySet()) {
            MutablePoint point = src.get(id);
            copy.put(id, new MutablePoint(point));
        }
        return copy;
    }

}
