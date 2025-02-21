package me.hotpocket.skriptadvancements.elements.effects.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Display Custom Advancement's Toast")
@Description({"Displays the toast of a custom advancement."})
@Examples("display toast of the advancement \"adventure/root\" to player")
@Since("1.4")

public class EffCustomAdvancementToast extends Effect {

    static {
        Skript.registerEffect(EffCustomAdvancementToast.class,
                "(show|display) [the] toast (of|from) %customadvancements% to %players%",
                "(show|display) %customadvancements%'[s] toast to %players%");
    }

    private Expression<Advancement> advancements;
    private Expression<Player> players;

    @Override
    protected void execute(Event e) {
        for (Advancement advancement : advancements.getAll(e))
            for (Player player : players.getAll(e))
                advancement.displayToastToPlayer(player);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "display the toast of " + advancements.toString(e, debug) + " to " + players.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        players = (Expression<Player>) exprs[1];
        return true;
    }
}
