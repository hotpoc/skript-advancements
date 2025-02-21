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
import me.hotpocket.skriptadvancements.utils.advancement.VisibilityType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Creation - Advancement Visibility")
@Description("Sets the visibility of a custom advancement to visible, hidden, or visible when the parent is granted.")
@Examples({
        "set visibility of advancement to visible",
        "set visibility of advancement to hidden",
        "set visibility of advancement to parent granted"
        })
@Since("1.6")

public class ExprAdvancementVisibility extends SimpleExpression<VisibilityType> {

    static {
        Skript.registerExpression(ExprAdvancementVisibility.class, VisibilityType.class, ExpressionType.SIMPLE,
                "[the] visibility [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] visibility");
    }

    @Override
    protected @Nullable VisibilityType[] get(Event e) {
        return new VisibilityType[]{getParser().getCurrentSection(SecAdvancement.class).getAdvancement().visibilityType};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends VisibilityType> getReturnType() {
        return VisibilityType.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the visibility of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET -> CollectionUtils.array(VisibilityType[].class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        getParser().getCurrentSection(SecAdvancement.class).getAdvancement().visibilityType = (VisibilityType) delta[0];
    }
}
