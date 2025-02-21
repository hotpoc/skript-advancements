package me.hotpocket.skriptadvancements.elements.converters;

import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementKey;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.skriptlang.skript.lang.converter.Converter;
import org.skriptlang.skript.lang.converter.Converters;

import javax.annotation.Nullable;

public class CustomAdvancementConverter {

    static {
        Converters.registerConverter(String.class, Advancement.class, new Converter<String, Advancement>() {
            @Nullable
            @Override
            public Advancement convert(String advancementName) {
                if (advancementName.contains("/")) {
                    String[] name = advancementName.toLowerCase().split("/");
                    if (UltimateAdvancementAPI.getInstance(SkriptAdvancements.getInstance()).getAdvancementTab(name[0]) != null) {
                        if (CustomUtils.getAPI().getAdvancement(new AdvancementKey(name[0], name[1])) != null)
                            return CustomUtils.getAPI().getAdvancement(new AdvancementKey(name[0], name[1]));
                    }
                }
                return null;
            }
        });
    }
}