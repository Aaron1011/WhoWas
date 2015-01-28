package org.aaron1011.whowas.impl.bukkit;

import org.aaron1011.whowas.impl.bukkit.command.WhoWasCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        getCommand("whoWas").setExecutor(new WhoWasCommand());
    }
}
