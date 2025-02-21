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
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Advancement")
@Description("Allows you to get a vanilla advancement or an advancement created by a plugin or datapack.")
@Examples("add the advancement \"adventure/root\" to advancements of player")
@Since("1.4")

public class ExprAdvancement extends SimpleExpression<Advancement> {

    static {
        Skript.registerExpression(ExprAdvancement.class, Advancement.class, ExpressionType.SIMPLE,
                "[the] advancement %string%");
    }

    private Expression<String> advancementName;

    @Override
    protected @Nullable Advancement[] get(Event e) {
        if (advancementName.getSingle(e).contains(":")) {
            if (Bukkit.getAdvancement(new NamespacedKey(advancementName.getSingle(e).split(":")[0].toLowerCase(), advancementName.getSingle(e).split(":")[1].toLowerCase())) != null)
                return new Advancement[]{Bukkit.getAdvancement(new NamespacedKey(advancementName.getSingle(e).split(":")[0].toLowerCase(), advancementName.getSingle(e).split(":")[1].toLowerCase()))};
        } else {
            if (Bukkit.getAdvancement(NamespacedKey.minecraft(advancementName.getSingle(e).toLowerCase())) != null)
                return new Advancement[]{Bukkit.getAdvancement(NamespacedKey.minecraft(advancementName.getSingle(e).toLowerCase()))};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Advancement> getReturnType() {
        return Advancement.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the advancement " + advancementName.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancementName = (Expression<String>) exprs[0];
        return true;
    }
}
