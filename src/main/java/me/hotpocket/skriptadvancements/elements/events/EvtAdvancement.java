package me.hotpocket.skriptadvancements.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.Nullable;

public class EvtAdvancement extends SimpleEvent {

    static {
        Skript.registerEvent("Advancement Complete", EvtAdvancement.class, PlayerAdvancementDoneEvent.class, "advancement [(done|complete[d]|grant[ed])]")
                .since("1.4")
                .description("Called when an advancement is completed by a player.")
                .examples("on advancement:");
        EventValues.registerEventValue(PlayerAdvancementDoneEvent.class, Advancement.class, new Getter<Advancement, PlayerAdvancementDoneEvent>() {
            @Override
            @Nullable
            public Advancement get(PlayerAdvancementDoneEvent e) {
                return e.getAdvancement();
            }

        }, 0);
        EventValues.registerEventValue(PlayerAdvancementDoneEvent.class, Player.class, new Getter<Player, PlayerAdvancementDoneEvent>() {
            @Override
            @Nullable
            public Player get(PlayerAdvancementDoneEvent e) {
                return e.getPlayer();
            }

        }, 0);
        EventValues.registerEventValue(PlayerAdvancementDoneEvent.class, String.class, new Getter<String, PlayerAdvancementDoneEvent>() {
            @Override
            @Nullable
            public String get(PlayerAdvancementDoneEvent e) {
                return Bukkit.getUnsafe().legacyComponentSerializer().serialize(e.message());
            }

        }, 0);
    }
}
