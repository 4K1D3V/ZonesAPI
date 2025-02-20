package pro.akii.ks.zones;

import pro.akii.ks.zones.effects.ZoneEffect;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a cubic zone with effects applied to players inside it.
 */
public class Zone {
    private final World world;
    private final double minX, minY, minZ, maxX, maxY, maxZ;
    private final List<ZoneEffect> effects = new ArrayList<>();

    public Zone(World world, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.world = world;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    /**
     * Checks if a location is within this zone.
     *
     * @param loc Location to check.
     * @return True if inside.
     */
    public boolean contains(Location loc) {
        if (!loc.getWorld().equals(world)) return false;
        double x = loc.getX(), y = loc.getY(), z = loc.getZ();
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    /**
     * Adds an effect to the zone.
     *
     * @param effect The effect to add.
     */
    public void addEffect(ZoneEffect effect) {
        effects.add(effect);
    }

    public List<ZoneEffect> getEffects() {
        return new ArrayList<>(effects);
    }

    // Getter methods for grid partitioning
    public double getMinX() { return minX; }
    public double getMinZ() { return minZ; }
    public double getMaxX() { return maxX; }
    public double getMaxZ() { return maxZ; }
}