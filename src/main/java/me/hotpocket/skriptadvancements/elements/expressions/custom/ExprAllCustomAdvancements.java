package me.hotpocket.skriptadvancements.elements.expressions.custom;

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
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("All Custom Advancements")
@Description("Allows you to get all of the custom advancements.")
@Examples("broadcast all custom advancements")
@Since("1.4")

public class ExprAllCustomAdvancements extends SimpleExpression<Advancement> {

    static {
        Skript.registerExpression(ExprAllCustomAdvancements.class, Advancement.class, ExpressionType.SIMPLE, "all [[of] the] custom advancements");
    }

    @Override
    protected @Nullable Advancement[] get(Event e) {
        return CustomUtils.getAdvancements().toArray(new Advancement[CustomUtils.getAdvancements().size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Advancement> getReturnType() {
        return Advancement.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "all custom advancements";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
