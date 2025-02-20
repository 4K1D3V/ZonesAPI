package pro.akii.ks.zones.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Applies a Bukkit potion effect to players within a zone.
 */
public class PotionZoneEffect implements ZoneEffect {
    private final PotionEffectType type;
    private final int amplifier;

    public PotionZoneEffect(PotionEffectType type, int amplifier) {
        this.type = type;
        this.amplifier = amplifier;
    }

    @Override
    public void apply(Player player) {
        player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, amplifier - 1, false, false));
    }

    @Override
    public void remove(Player player) {
        player.removePotionEffect(type);
    }
}