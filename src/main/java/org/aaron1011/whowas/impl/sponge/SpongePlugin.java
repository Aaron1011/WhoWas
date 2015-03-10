package org.aaron1011.whowas.impl.sponge;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.entity.living.player.fishing.PlayerCastFishingLineEvent;
import org.spongepowered.api.event.entity.living.player.fishing.PlayerHookedEntityEvent;
import org.spongepowered.api.event.entity.living.player.fishing.PlayerRetractFishingLineEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.service.config.ConfigService;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.util.event.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Plugin(id = "whowas", name = "Who Was", version = "1.1.0")
public class SpongePlugin {

    @Inject
    @ConfigDir(sharedRoot = false)
    File configDir;

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
