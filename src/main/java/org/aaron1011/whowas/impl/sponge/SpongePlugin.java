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
        System.out.println("Plugin started!");
        System.out.println("Config dir: " + configDir.getAbsolutePath());
        List<String> aliases = new ArrayList<String>();
        aliases.add("whoWas");
        aliases.add("ww");

        try {
            event.getGame().getCommandDispatcher().register(this, new SpongeCommandHandler(event.getGame()), aliases);
        } catch (Throwable t) {
            System.out.println("Oops!");
            t.printStackTrace();
        }

        // The Forge impl doesn't have any of this implemented yet...

        /**
        System.out.println("Getting service!");
        ConfigService service = event.getGame().getServiceManager().provideUnchecked(ConfigService.class);
        System.out.println("File: " + service.getPluginConfig(this));
        Logger logger = event.getGame().getPluginManager().getLogger(event.getGame().getPluginManager().fromInstance(this).get());
        logger.debug("Hello from logger!");
         **/
    }
}
