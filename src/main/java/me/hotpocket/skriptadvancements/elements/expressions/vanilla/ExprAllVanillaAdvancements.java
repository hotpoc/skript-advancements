package me.hotpocket.skriptadvancements.elements.expressions.vanilla;

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
import me.hotpocket.skriptadvancements.utils.VanillaUtils;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("All Vanilla Advancements")
@Description("Allows you to get all of the vanilla advancements.")
@Examples("broadcast all advancements")
@Since("1.4")

public class ExprAllVanillaAdvancements extends SimpleExpression<Advancement> {

    static {
        Skript.registerExpression(ExprAllVanillaAdvancements.class, Advancement.class, ExpressionType.SIMPLE, "all [[of] the] [vanilla] advancements");
    }

    @Override
    protected @Nullable Advancement[] get(Event e) {
        return VanillaUtils.getAdvancements().toArray(new Advancement[VanillaUtils.getAdvancements().size()]);
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
        return "all vanilla advancements";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
