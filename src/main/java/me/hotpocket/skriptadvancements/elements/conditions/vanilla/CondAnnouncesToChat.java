package me.hotpocket.skriptadvancements.elements.conditions.vanilla;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Does Advancement Announce to Chat")
@Description({"Checks if an advancement announces to the chat."})
@Examples("if the advancement \"tabName/advancementName\" announces to the chat")
@Since("1.4")

public class CondAnnouncesToChat extends Condition {

    static {
        if (Skript.classExists("io.papermc.paper.advancement.AdvancementDisplay"))
            Skript.registerCondition(CondAnnouncesToChat.class,
                    "%advancements% [do[es]] announce to [the] chat",
                    "%advancements% (doesn't|does not|don't|do not) announce to [the] chat");
    }

    private Expression<Advancement> advancements;

    @Override
    public boolean check(Event e) {
        return advancements.check(e, advancement -> {
            if (advancement.getDisplay() != null)
                return advancement.getDisplay().doesAnnounceToChat();
            return false;
        }, isNegated());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return advancements.toString(e, debug) + " do/do not announce to chat";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}
