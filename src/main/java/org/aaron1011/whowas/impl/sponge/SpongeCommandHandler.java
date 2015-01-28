package org.aaron1011.whowas.impl.sponge;

import com.google.common.base.Optional;
import org.aaron1011.whowas.core.PlayerNameHistory;
import org.aaron1011.whowas.core.PlayerNameHistoryFetcher;
import org.aaron1011.whowas.core.PlayerUUIDFetcher;
import org.aaron1011.whowas.core.TimestampedName;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.message.Message;
import org.spongepowered.api.text.message.MessageBuilder;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpongeCommandHandler implements CommandCallable {

    private final Game game;

    public SpongeCommandHandler(Game game) {
        this.game = game;
    }

    @Override
    public boolean call(CommandSource source, String arguments, List<String> parents) {
        String[] args = arguments.split(" ", -1);
        System.out.println("Args: " + args.toString());

        if (arguments.equals("")) {
            MessageBuilder builder = Messages.builder(getUsage());
            builder = builder.color(TextColors.RED);
            source.sendMessage(builder.build());
        } else {
            Optional<Player> player = this.game.getServer().get().getPlayer(args[0]);
            UUID uuid;
            if (!player.isPresent()) {
                try {
                    Optional<UUID> uuidOpt = PlayerUUIDFetcher.getUUID(args[0]);
                    if (uuidOpt.isPresent()) {
                        uuid = uuidOpt.get();
                    } else {
                        source.sendMessage(String.format("Unable to fetch uuid for username %s. Do they exist?", args[0]));
                        return true;
                    }
                } catch (Exception e) {
                    source.sendMessage(String.format("Error occurred when fetch UUID for %s: ", args[0]) + e.getMessage());
                    return true;
                }
            } else {
                uuid = player.get().getUniqueId();
            }

            PlayerNameHistory history;
            try {
                Optional<PlayerNameHistory> historyOpt = PlayerNameHistoryFetcher.getPlayerNameHistory(uuid);
                if (historyOpt.isPresent()) {
                    history = historyOpt.get();
                } else {
                    source.sendMessage(String.format("Error fetcher player name history for UUID %s. Does it exist?", uuid));
                    return true;
                }
            } catch (Exception e) {
                source.sendMessage("Error fetching player name history: " + e.getMessage());
                return true;
            }

            source.sendMessage(Messages.builder("Name history:").color(TextColors.BLUE).build());
            for (TimestampedName name: history.getNames()) {
                MessageBuilder builder = Messages.builder(name.getName() + ": ");
                if (name.getChangedToAt().isPresent()) {
                    builder.append(Messages.builder("Changed to at " + name.getChangedToAt().get().toString()).color(TextColors.GREEN).build());
                } else {
                    builder.append(Messages.builder("In use").color(TextColors.GOLD).build());
                }

                Message message = builder.build();
                source.sendMessage(message);
            }
        }
        return true;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        return true;
    }

    @Override
    public Optional<String> getShortDescription() {
        return Optional.of("Displays the name history for a player");
    }

    @Override
    public Optional<String> getHelp() {
        return Optional.of("Test help");
    }

    @Override
    public String getUsage() {
        return "Usage: /whoWas <player>";
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return new ArrayList<String>();
    }
}
