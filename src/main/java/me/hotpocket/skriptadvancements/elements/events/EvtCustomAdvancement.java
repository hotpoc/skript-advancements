package me.hotpocket.skriptadvancements.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import me.hotpocket.skriptadvancements.customevent.AdvancementCompleteEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class EvtCustomAdvancement extends SimpleEvent {

    static {
        Skript.registerEvent("Custom Advancement Complete", EvtCustomAdvancement.class, AdvancementCompleteEvent.class, "custom advancement [(done|complete[d]|grant[ed])]")
                .description("Called when a custom advancement is completed by a player.")
                .examples("on custom advancement:");
        EventValues.registerEventValue(AdvancementCompleteEvent.class, Advancement.class, new Getter<Advancement, AdvancementCompleteEvent>() {
            @Override
            @Nullable
            public Advancement get(AdvancementCompleteEvent e) {
                return e.getAdvancement();
            }

        }, 0);
        EventValues.registerEventValue(AdvancementCompleteEvent.class, Player.class, new Getter<Player, AdvancementCompleteEvent>() {
            @Override
            @Nullable
            public Player get(AdvancementCompleteEvent e) {
                return e.getPlayer();
            }

        }, 0);
    }
}
