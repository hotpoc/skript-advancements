package me.hotpocket.skriptadvancements.elements.conditions.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Is Custom Advancement Hidden")
@Description({"Checks if a custom advancement is not shown to a player."})
@Examples("if the custom advancement \"tabName/advancementName\" is hidden for player")
@Since("1.4")

public class CondCustomIsHidden extends Condition {

    static {
        Skript.registerCondition(CondCustomIsHidden.class,
                "%customadvancements% (is hidden|(isn't|is not) visible) (to|for) %players%",
                "%customadvancements% (is visible|(isn't|is not) hidden) (to|for) %players%");
    }

    private Expression<Advancement> advancements;
    private Expression<Player> players;

    @Override
    public boolean check(Event e) {
        return advancements.check(e, advancement -> {
            for (Player player : players.getAll(e))
                return advancement.isVisible(player);
            return false;
        }, isNegated());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return advancements.toString(e, debug) + " is/is not visible/hidden for " + players.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        players = (Expression<Player>) exprs[1];
        setNegated(matchedPattern == 1);
        return true;
    }
}
