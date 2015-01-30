package org.aaron1011.whowas.impl.sponge;

import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.event.Subscribe;

import java.util.ArrayList;
import java.util.List;

@Plugin(id = "whowas", name = "Who Was", version = "1.0.2")
public class SpongePlugin {

    @Subscribe
    public void onServerStart(ServerStartedEvent event) {
        registerCommands(event);
    }

    private void registerCommands(ServerStartedEvent event) {
        List<String> aliases = new ArrayList<String>();
        aliases.add("whoWas");
        aliases.add("ww");
        event.getGame().getCommandDispatcher().register(this, new SpongeCommandHandler(event.getGame()), aliases);
    }
}