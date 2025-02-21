package me.hotpocket.skriptadvancements.elements.expressions.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Creation - Advancement Max Progression")
@Description("Sets the max progression of a custom advancement to an integer.")
@Examples("set the max progression of advancement to 3")
@Since("1.4")

public class ExprAdvancementMaxProgression extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(ExprAdvancementMaxProgression.class, Integer.class, ExpressionType.SIMPLE,
                "[the] max[imum] progress[ion] [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] max[imum] progress[ion]");
    }

    @Override
    protected @Nullable Integer[] get(Event e) {
        return new Integer[]{getParser().getCurrentSection(SecAdvancement.class).getAdvancement().maxProgression};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the max progression of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET-> CollectionUtils.array(Number.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        getParser().getCurrentSection(SecAdvancement.class).getAdvancement().maxProgression = ((Number) delta[0]).intValue();
    }
}
