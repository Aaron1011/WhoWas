package org.aaron1011.whowas.impl.bukkit.command;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.aaron1011.whowas.core.namehistory.PlayerNameHistory;
import org.aaron1011.whowas.core.namehistory.PlayerNameHistoryFetcher;
import org.aaron1011.whowas.core.uuid.PlayerUUIDFetcher;
import org.aaron1011.whowas.core.namehistory.TimestampedName;
import org.aaron1011.whowas.impl.bukkit.BukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class WhoWasCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public WhoWasCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        Player player;
        if (this.plugin.getConfig().getBoolean("exactLookup", true)) {
            player = sender.getServer().getPlayerExact(args[0]);
        } else {
            player = sender.getServer().getPlayer(args[0]);
        }

        UUID uuid;
        if (player == null) {
            try {
                Optional<UUID> uuidOpt = PlayerUUIDFetcher.getUUID(args[0]);
                if (uuidOpt.isPresent()) {
                    uuid = uuidOpt.get();
                } else {
                    sender.sendMessage(String.format("Unable to fetch uuid for username %s. Do they exist?", args[0]));
                    return true;
                }
            } catch (Exception e) {
                sender.sendMessage(String.format("Error occurred when fetching UUID for %s: ", args[0]) + e.getMessage());
                return true;
            }
        } else {
            uuid = player.getUniqueId();
        }

        PlayerNameHistory history;
        try {
            Optional<PlayerNameHistory> historyOpt = PlayerNameHistoryFetcher.getPlayerNameHistory(uuid);
            if (historyOpt.isPresent()) {
                history = historyOpt.get();
            } else {
                sender.sendMessage(String.format("Error fetcher player name history for UUID %s. Does it exist?", uuid));
                return true;
            }
        } catch (Exception e) {
            sender.sendMessage("Error fetching player name history: " + e.getMessage());
            return true;
        }

        sender.sendMessage(ChatColor.BLUE + "Name history:");
        if (history.getNames().size() == 1) {
            TimestampedName name = history.getNames().get(0);
            sender.sendMessage(name.getName() + ": " + ChatColor.GOLD + "In use");
        } else {
            for (TimestampedName name : Lists.reverse(history.getNames())) {
                StringBuilder builder = new StringBuilder(name.getName() + ": ");

                if (name.getChangedToAt().isPresent()) {
                    builder.append(ChatColor.GREEN + "Changed to at " + name.getChangedToAt().get().toString());
                } else {
                    builder.append(ChatColor.GOLD + "First name");
                }

                sender.sendMessage(builder.toString());
            }
        }
        return true;
    }
}
