package me.hotpocket.skriptadvancements.elements.conditions.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Does Custom Advancement Show Toast")
@Description({"Checks if a custom advancement shows toast"})
@Examples("if the custom advancement \"tabName/advancementName\" shows toast")
@Since("1.4")

public class CondCustomShowsToast extends Condition {

    static {
        Skript.registerCondition(CondCustomAnnouncesToChat.class,
                "%customadvancements% [do[es]] show[s] toast",
                "%customadvancements% (doesn't|does not|don't|do not) show toast");
    }

    private Expression<Advancement> advancements;

    @Override
    public boolean check(Event e) {
       for (Advancement advancement : advancements.getAll(e))
           return isNegated() == advancement.getDisplay().doesShowToast();
       return false;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return advancements.toString(e, debug) + " do/do not show toast";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}
