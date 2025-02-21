package me.hotpocket.skriptadvancements.elements.expressions.vanilla;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Date Awarded")
@Description("Allows you to get the date awarded of a player's advancement.")
@Examples("broadcast date awarded of the advancement \"minecraft:adventure/root\" for player")
@Since("1.4")

public class ExprDateAwarded extends SimpleExpression<Date> {

    static {
        Skript.registerExpression(ExprDateAwarded.class, Date.class, ExpressionType.SIMPLE,
                "[the] date awarded of %advancements% for %players%",
                "%advancements%'[s] date awarded for %players%");
    }

    private Expression<Advancement> advancements;
    private Expression<Player> players;

    @Override
    protected @Nullable Date[] get(Event e) {
        for (Player player : players.getArray(e)) {
            for (Advancement advancement : advancements.getArray(e)) {
                if (player.getAdvancementProgress(advancement).isDone()) {
                    for (String criteria : Bukkit.getAdvancement(advancement.getKey()).getCriteria()) {
                        return new Date[]{new Date(player.getAdvancementProgress(advancement).getDateAwarded(criteria).getTime())};
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "date awarded of " + advancements.toString(e, debug) + " for " + players.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        players = (Expression<Player>) exprs[1];
        return true;
    }
}
