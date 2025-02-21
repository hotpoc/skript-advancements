package me.hotpocket.skriptadvancements.elements.converters;

import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.skriptlang.skript.lang.converter.Converter;
import org.skriptlang.skript.lang.converter.Converters;

import javax.annotation.Nullable;

public class AdvancementConverter {

    static {
        Converters.registerConverter(String.class, Advancement.class, new Converter<String, Advancement>() {
            @Nullable
            @Override
            public Advancement convert(String s) {
                NamespacedKey namespacedKey = CustomUtils.getNamespacedKey(s);
                return Bukkit.getAdvancement(namespacedKey);
            }
        });
    }
}