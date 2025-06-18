package me.hotpocket.skriptadvancements.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.StringUtils;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import me.hotpocket.skriptadvancements.customevent.AdvancementCompleteEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EvtCustomAdvancement extends SkriptEvent {

    static {
        Skript.registerEvent("Custom Advancement Complete", EvtCustomAdvancement.class, AdvancementCompleteEvent.class, "custom advancement [(done|complete[d]|grant[ed])] [[of] %-strings%]")
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

    private String @Nullable [] advancements = null;
    private Literal<String> advancementsLit;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        if (args[0] != null) {
            //noinspection unchecked
            advancementsLit = ((Literal<String>) args[0]);
            advancements = advancementsLit.getAll();
        }
        return true;
    }

    @Override
    public boolean check(Event event) {
        if (advancements == null)
            return true;

        String advancement;
        if (event instanceof AdvancementCompleteEvent advancementCompleteEvent) {
            advancement = advancementCompleteEvent.getAdvancement().getKey().getNamespace() + "/" + advancementCompleteEvent.getAdvancement().getKey().getKey();
        } else {
			advancement = "";
		}

		return Arrays.stream(advancements).anyMatch(candidateAdvancement ->
                StringUtils.startsWithIgnoreCase(advancement, candidateAdvancement) // matches the command label
                        && (candidateAdvancement.contains(" ") // if candidate contains arguments, then any command that starts with the candidate is a match
                        || advancement.length() == candidateAdvancement.length() // exact match
                        || Character.isWhitespace(advancement.charAt(candidateAdvancement.length()) // matches label with space after
                )));
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";
    }

}
