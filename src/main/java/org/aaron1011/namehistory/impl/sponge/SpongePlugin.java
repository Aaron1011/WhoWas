package org.aaron1011.namehistory.impl.sponge;

import com.google.common.base.Optional;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.util.event.Subscribe;

@Plugin(id = "namehistory", name = "Name History", version = "1.0.0")
public class SpongePlugin {

    @Subscribe
    public void onServerStart(ServerStartedEvent event) {
        registerCommands(event);
        event.getGame().getEventManager().register(this, new SpongeEventHandler());
    }

    private void registerCommands(ServerStartedEvent event) {
        Optional<CommandService> service = event.getGame().getServiceManager().provide(CommandService.class);
        if (service.isPresent()) {
            CommandService commandService = service.get();
        }
    }
}