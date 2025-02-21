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

import java.util.Arrays;
import java.util.List;

@Name("Creation - Advancement Description")
@Description("Sets the description of a custom advancement to a string.")
@Examples("set description of advancement to \"My new advancement!\"")
@Since("1.4")

public class ExprAdvancementDescription extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprAdvancementDescription.class, String.class, ExpressionType.SIMPLE,
                "[the] description [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] description");
    }

    @Override
    protected @Nullable String[] get(Event e) {
        List<String> description = getParser().getCurrentSection(SecAdvancement.class).getAdvancement().displayBuilder.getDescription();
        return description.toArray(new String[description.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the description of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET-> CollectionUtils.array(String[].class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        getParser().getCurrentSection(SecAdvancement.class).getAdvancement().displayBuilder.setDescription(List.of(Arrays.copyOf(delta, delta.length, String[].class)));
    }
}
