package com.gmail.necnionch.myplugin.shadowmotd.bukkit;

import com.google.common.collect.Lists;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ShadowMotdPlugin extends JavaPlugin implements Listener {

    private final ShadowMotdConfig config = new ShadowMotdConfig(this);

    @Override
    public void onEnable() {
        if (!config.load()) {
            getLogger().warning("Failed to load config");
            setEnabled(false);
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    @NotNull
    public ShadowMotdConfig getPluginConfig() {
        return config;
    }


    public String buildShadowMotdText() {
        Function<String, String> replacer = s -> PlaceholderAPI.setPlaceholders(null, s);

        return config.getReplaceValues().stream()
                .map(val -> val.replace(replacer))
                .collect(Collectors.joining(config.getValuesJoinString()));
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent event) {
        List<String> lines = Lists.newArrayList(event.getMotd().split("\n"));

        // fill fore lines
        while (lines.size() < 2) {
            lines.add("");
        }

        // append shadow motd
        lines.add(buildShadowMotdText());

        event.setMotd(String.join("\n", lines));
    }

}
