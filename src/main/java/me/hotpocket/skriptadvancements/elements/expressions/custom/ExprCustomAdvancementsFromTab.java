package me.hotpocket.skriptadvancements.elements.expressions.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprCustomAdvancementsFromTab extends SimpleExpression<Advancement> {

    static {
        Skript.registerExpression(ExprCustomAdvancementsFromTab.class, Advancement.class, ExpressionType.SIMPLE, "[all] [the] [custom] advancements from [the] tab %string%");
    }

    private Expression<String> tab;

    @Override
    protected @Nullable Advancement[] get(Event e) {
        if (CustomUtils.getAPI().getAdvancementTab(tab.getSingle(e)) != null && CustomUtils.getAPI().getAdvancementTab(tab.getSingle(e)).isInitialised())
            return CustomUtils.getAPI().getAdvancementTab(tab.getSingle(e)).getAdvancements().toArray(new Advancement[CustomUtils.getAPI().getAdvancementTab(tab.getSingle(e)).getAdvancements().size()]);
        return null;
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
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        tab = (Expression<String>) exprs[0];
        return true;
    }
}
