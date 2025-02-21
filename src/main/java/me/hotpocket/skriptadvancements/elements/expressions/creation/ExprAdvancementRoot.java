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

@Name("Creation - Advancement Root")
@Description("Sets the root of a custom advancement to true or false.")
@Examples("set root of advancement to true")
@Since("1.4")

public class ExprAdvancementRoot extends SimpleExpression<Boolean> {

    static {
        Skript.registerExpression(ExprAdvancementRoot.class, Boolean.class, ExpressionType.SIMPLE,
                "[the] root [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] root");
    }

    @Override
    protected @Nullable Boolean[] get(Event e) {
        return new Boolean[]{getParser().getCurrentSection(SecAdvancement.class).getAdvancement().root};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the root of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET -> CollectionUtils.array(Boolean.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        getParser().getCurrentSection(SecAdvancement.class).getAdvancement().root = (Boolean) delta[0];
    }
}
