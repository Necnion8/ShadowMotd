package com.gmail.necnionch.myplugin.shadowmotd.bukkit;

import com.gmail.necnionch.myplugin.shadowmotd.bukkit.value.ReplaceValue;
import com.gmail.necnionch.myplugin.shadowmotd.common.BukkitConfigDriver;
import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ShadowMotdConfig extends BukkitConfigDriver {

    private final List<ReplaceValue> replaceValues = Lists.newArrayList();

    public ShadowMotdConfig(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onLoaded(FileConfiguration config) {
        replaceValues.clear();
        for (Map<?, ?> e : config.getMapList("values")) {
            try {
                //noinspection unchecked
                replaceValues.add(ReplaceValue.deserialize((Map<Object, Object>) e));
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }

        return true;
    }

    public List<ReplaceValue> getReplaceValues() {
        return Collections.unmodifiableList(replaceValues);
    }

    public String getValuesJoinString() {
        return config.getString("values-join", "\0");
    }

}
