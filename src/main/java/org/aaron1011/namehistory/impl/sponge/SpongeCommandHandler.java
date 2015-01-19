package org.aaron1011.namehistory.impl.sponge;

import com.google.common.base.Optional;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import java.util.ArrayList;
import java.util.List;

public class SpongeCommandHandler implements CommandCallable {
    @Override
    public boolean call(CommandSource source, String arguments, List<String> parents) throws CommandException {
        source.sendMessage("Hello, world!");
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
        return "nameHistory <player>";
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return new ArrayList<String>();
    }
}
