package me.hotpocket.skriptadvancements.elements.expressions.display.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
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

@Name("Display - Custom Advancement Icon")
@Description("Allows you to get the displayed icon of a custom advancement")
@Examples("give player the displayed icon of the custom advancement \"tabName/advancementName\"")
@Since("1.4")

public class ExprCustomDisplayedIcon extends SimpleExpression<ItemType> {

    static {
        Skript.registerExpression(ExprCustomDisplayedIcon.class, ItemType.class, ExpressionType.SIMPLE,
                "[the] displayed icon[s] of %customadvancements%");
    }

    private Expression<Advancement> advancements;

    @Override
    protected @Nullable ItemType[] get(Event e) {
        for (Advancement advancement : advancements.getAll(e))
            return new ItemType[]{new ItemType(advancement.getDisplay().getIcon())};
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the displayed icon of " + advancements.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        return true;
    }
}
