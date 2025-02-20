package pro.akii.ks.zones.effects;

import org.bukkit.entity.Player;

/**
 * Interface for effects applied to players in zones.
 */
public interface ZoneEffect {
    void apply(Player player);
    void remove(Player player);
}