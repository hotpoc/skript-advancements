package me.hotpocket.skriptadvancements.elements.expressions.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
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
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Creation - Advancement Background")
@Description("Sets the background of a custom advancement to any solid block.")
@Examples("set the background of advancement to diamond block")
@Since("1.4")

public class ExprAdvancementBackground extends SimpleExpression<ItemType> {

    static {
        Skript.registerExpression(ExprAdvancementBackground.class, ItemType.class, ExpressionType.SIMPLE,
                "[the] background [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] background");
    }

    @Override
    protected @Nullable ItemType[] get(Event e) {
        Skript.warning("The background expression always returns <none>. Use the background texture expression.");
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the background of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET -> CollectionUtils.array(ItemType.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        getParser().getCurrentSection(SecAdvancement.class).getAdvancement().backgroundTexture = CustomUtils.getTexture(((ItemType) delta[0]).getMaterial());
    }
}