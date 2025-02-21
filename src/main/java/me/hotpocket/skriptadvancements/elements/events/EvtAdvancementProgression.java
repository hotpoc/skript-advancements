package me.hotpocket.skriptadvancements.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.events.advancement.AdvancementProgressionUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class EvtAdvancementProgression extends SimpleEvent {

    static {
        Skript.registerEvent("Advancement Progression Update", EvtAdvancementProgression.class, AdvancementProgressionUpdateEvent.class, "advancement progress[ion] [update]")
                .since("1.4")
                .description("Called when an advancement's progression is changed for a player.")
                .examples("on advancement progression:");
        EventValues.registerEventValue(AdvancementProgressionUpdateEvent.class, Player.class, new Getter<Player, AdvancementProgressionUpdateEvent>() {
            @Override
            @Nullable
            public Player get(AdvancementProgressionUpdateEvent e) {
                return Bukkit.getPlayer(e.getTeamProgression().getAMember());
            }

        }, 0);
        EventValues.registerEventValue(AdvancementProgressionUpdateEvent.class, Advancement.class, new Getter<Advancement, AdvancementProgressionUpdateEvent>() {
            @Override
            @Nullable
            public Advancement get(AdvancementProgressionUpdateEvent e) {
                return e.getAdvancement();
            }

        }, 0);
        EventValues.registerEventValue(AdvancementProgressionUpdateEvent.class, Number.class, new Getter<Number, AdvancementProgressionUpdateEvent>() {
            @Override
            public Number get(AdvancementProgressionUpdateEvent e) {
                return e.getOldProgression();
            }

        }, 0);
        EventValues.registerEventValue(AdvancementProgressionUpdateEvent.class, Number.class, new Getter<Number, AdvancementProgressionUpdateEvent>() {
            @Override
            public Number get(AdvancementProgressionUpdateEvent e) {
                return e.getNewProgression();
            }

        }, 1);
    }
}
