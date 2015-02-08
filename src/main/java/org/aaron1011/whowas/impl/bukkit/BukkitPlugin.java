package org.aaron1011.whowas.impl.bukkit;

import net.amoebaman.amoebautils.plugin.Updater;
import org.aaron1011.whowas.impl.bukkit.command.WhoWasCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        getCommand("whoWas").setExecutor(new WhoWasCommand(this));
        // Waiting for plugin id
        runUpdater();
    }

    private void runUpdater() {
        if (getConfig().contains("update.checkForUpdates") && getConfig().getBoolean("update.checkForUpdates")) {
            getLogger().info("Checking for updates");
            Updater.UpdateType type = getConfig().getBoolean("update.autoInstallUpdates") ? Updater.UpdateType.DEFAULT : Updater.UpdateType.NO_DOWNLOAD;
            Updater updater = new Updater(this, 88955, getFile(), type, true);
            switch (updater.getResult()) {
                case FAIL_BADID:
                case FAIL_NOVERSION:
                    getLogger().severe("Failed to check for updates due to a bad plugin name/id. Contact the developer: " + updater.getResult().name());
                    break;
                case FAIL_DBO:
                    getLogger().severe("An error occured while checking Bukkit Dev for updates");
                    break;
                case FAIL_DOWNLOAD:
                    getLogger().severe("An error occurred downloading the update.");
                    break;
                case UPDATE_AVAILABLE:
                    getLogger().warning("An update for WhoWas is available to download on Bukkit Dev");
                    break;
                case NO_UPDATE:
                    getLogger().info("WhoWas is up to date!");
                    break;
                case SUCCESS:
                    getLogger().info("WhoWas has been successfully updated. Restart the server to use the new version");
                    break;
                default:
            }
        }
    }
}
