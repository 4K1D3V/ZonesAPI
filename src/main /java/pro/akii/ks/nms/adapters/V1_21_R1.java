package pro.akii.ks.nms.adapters;

import pro.akii.ks.nms.NmsAdapter;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * NMS adapter for Minecraft 1.21.
 * Placeholder: Update with actual 1.21 NMS imports when available.
 */
public class V1_21_R1 implements NmsAdapter {
    @Override
    public void setPlayerSpeed(Player player, float speed) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        serverPlayer.abilities.walkSpeed = speed;
        serverPlayer.onUpdateAbilities();
    }

    @Override
    public void resetPlayerSpeed(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        serverPlayer.abilities.walkSpeed = 0.1f; // Default speed
        serverPlayer.onUpdateAbilities();
    }

    @Override
    public void sendParticle(Player player, String type, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        ParticleType<?> particle;
        switch (type.toLowerCase()) {
            case "flame":
                particle = ParticleTypes.FLAME;
                break;
            case "smoke":
                particle = ParticleTypes.SMOKE;
                break;
            case "spark":
                particle = ParticleTypes.ELECTRIC_SPARK;
                break;
            default:
                return;
        }
        ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(
            particle, false, x, y, z, offsetX, offsetY, offsetZ, speed, count
        );
        serverPlayer.connection.send(packet);
    }
}