package pro.akii.ks.zones.effects;

import pro.akii.ks.core.ZonesAPICore;
import pro.akii.ks.nms.NmsAdapter;
import org.bukkit.entity.Player;

/**
 * NMS-based effect for custom walk speed and particle visuals.
 */
public class NmsZoneEffect implements ZoneEffect {
    private final float speed;
    private final String particleType;
    private final double radius;

    public NmsZoneEffect(float speed, String particleType, double radius) {
        this.speed = speed;
        this.particleType = particleType;
        this.radius = radius;
    }

    @Override
    public void apply(Player player) {
        NmsAdapter adapter = ZonesAPICore.getInstance().getNmsAdapter();
        if (adapter != null) {
            adapter.setPlayerSpeed(player, speed);
        }
    }

    @Override
    public void remove(Player player) {
        NmsAdapter adapter = ZonesAPICore.getInstance().getNmsAdapter();
        if (adapter != null) {
            adapter.resetPlayerSpeed(player);
        }
    }

    /**
     * Spawns particle visuals around the player.
     *
     * @param player The player to show particles to.
     */
    public void spawnParticles(Player player) {
        NmsAdapter adapter = ZonesAPICore.getInstance().getNmsAdapter();
        if (adapter != null) {
            adapter.sendParticle(
                player, particleType,
                player.getX(), player.getY() + 1.5, player.getZ(),
                (float) radius, (float) radius, (float) radius,
                0.01f, 10
            );
        }
    }
}