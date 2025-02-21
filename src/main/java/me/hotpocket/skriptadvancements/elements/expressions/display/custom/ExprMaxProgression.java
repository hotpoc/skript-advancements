package me.hotpocket.skriptadvancements.elements.expressions.display.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprMaxProgression extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(ExprMaxProgression.class, Integer.class, ExpressionType.SIMPLE, "[the] max[imum] progress[ion] (of|for) %customadvancements%");
    }

    private Expression<Advancement> advancements;

    @Override
    protected @Nullable Integer[] get(Event e) {
        for (Advancement advancement : advancements.getAll(e))
            return new Integer[]{advancement.getMaxProgression()};
        return new Integer[]{0};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the max progression of " + advancements.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        return true;
    }
}
