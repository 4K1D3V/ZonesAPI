package pro.akii.ks.zones;

import org.bukkit.Location;

import java.util.*;

/**
 * Spatial partitioning system to optimize zone detection.
 */
public class GridPartition {
    private final Map<String, Set<Zone>> grid = new HashMap<>();
    private final int cellSize = 16;

    /**
     * Adds a zone to the grid.
     *
     * @param zone The zone to add.
     */
    public void addZone(Zone zone) {
        Set<String> cells = getCellsForZone(zone);
        for (String cell : cells) {
            grid.computeIfAbsent(cell, k -> new HashSet<>()).add(zone);
        }
    }

    /**
     * Gets zones potentially containing a location.
     *
     * @param loc The location to check.
     * @return Set of zones in the grid cell.
     */
    public Set<Zone> getZonesForLocation(Location loc) {
        String cellKey = getCellKey(loc);
        return grid.getOrDefault(cellKey, Collections.emptySet());
    }

    private Set<String> getCellsForZone(Zone zone) {
        Set<String> cells = new HashSet<>();
        int minX = (int) Math.floor(zone.getMinX() / cellSize);
        int maxX = (int) Math.floor(zone.getMaxX() / cellSize);
        int minZ = (int) Math.floor(zone.getMinZ() / cellSize);
        int maxZ = (int) Math.floor(zone.getMaxZ() / cellSize);
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                cells.add(x + "," + z);
            }
        }
        return cells;
    }

    private String getCellKey(Location loc) {
        int x = (int) Math.floor(loc.getX() / cellSize);
        int z = (int) Math.floor(loc.getZ() / cellSize);
        return x + "," + z;
    }
}