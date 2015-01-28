package org.aaron1011.whowas.impl.bukkit;

import org.aaron1011.whowas.impl.bukkit.command.WhoWasCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("whoWas").setExecutor(new WhoWasCommand());
    }
}
