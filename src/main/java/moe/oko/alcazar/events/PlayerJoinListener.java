package moe.oko.alcazar.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static moe.oko.alcazar.database.ASQL.dbReconnection;

public final class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO: Cache player join statistic

    }
}
