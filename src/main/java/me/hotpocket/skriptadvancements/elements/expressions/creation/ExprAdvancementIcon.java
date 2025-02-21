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
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Name("Creation - Advancement Icon")
@Description("Sets the icon of a custom advancement to any item.")
@Examples("")
@Since("1.4")

public class ExprAdvancementIcon extends SimpleExpression<ItemType> {

    static {
        Skript.registerExpression(ExprAdvancementIcon.class, ItemType.class, ExpressionType.SIMPLE,
                "[the] icon [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] icon");
    }

    @Override
    protected @Nullable ItemType[] get(Event e) {
        return new ItemType[]{new ItemType(getParser().getCurrentSection(SecAdvancement.class).getAdvancement().displayBuilder.getIcon())};
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
        return "the icon of the advancement";
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
        ItemType itemType = (ItemType) delta[0];
        ItemStack itemStack = new ItemStack(itemType.getMaterial());
        itemStack.setItemMeta(itemType.getItemMeta());
        getParser().getCurrentSection(SecAdvancement.class).getAdvancement().displayBuilder.setIcon(itemStack);
    }
}
