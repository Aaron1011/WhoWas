package org.aaron1011.namehistory.impl.sponge;

import com.google.common.base.Optional;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.util.event.Subscribe;

import java.util.ArrayList;
import java.util.List;

@Plugin(id = "namehistory", name = "Name History", version = "1.0.0")
public class SpongePlugin {

    @Subscribe
    public void onServerStart(ServerStartedEvent event) {
        registerCommands(event);
    }

    private void registerCommands(ServerStartedEvent event) {
        List<String> aliases = new ArrayList<String>();
        aliases.add("nameHistory");
        aliases.add("nm");
        event.getGame().getCommandDispatcher().register(this, new SpongeCommandHandler(), aliases); 
    }
}