package me.hotpocket.skriptadvancements.elements.expressions.display.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Display - Custom Advancement Description")
@Description("Allows you to get the displayed description of a custom advancement")
@Examples("broadcast displayed description of the custom advancement \"tabName/advancementName\"")
@Since("1.4")

public class ExprCustomDisplayedDescription extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprCustomDisplayedDescription.class, String.class, ExpressionType.SIMPLE,
                "[the] displayed description[s] of %customadvancements%");
    }

    private Expression<Advancement> advancements;

    @Override
    protected @Nullable String[] get(Event e) {
        for (Advancement advancement : advancements.getAll(e))
            return new String[]{advancement.getDisplay().getCompactDescription()};
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the displayed title of " + advancements.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        return true;
    }
}
