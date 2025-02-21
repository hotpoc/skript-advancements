package me.hotpocket.skriptadvancements.elements.effects.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Dispose Tab")
@Description({"Disables an advancement tab. This can be useful when a tab is registered after you remove an advancement tab creation section from a script."})
@Examples("dispose tab \"myTab\"")
@Since("1.4.1")

public class EffDisposeTab extends Effect {

    static {
        Skript.registerEffect(EffDisposeTab.class, "(dispose|disable|unregister) [the] [advancement] tab [(titled|named)] %string%");
    }

    private Expression<String> tab;

    @Override
    protected void execute(Event e) {
        if (CustomUtils.getAPI().getAdvancementTab(tab.getSingle(e)) != null)
            CustomUtils.getAPI().unregisterAdvancementTab(tab.getSingle(e).toLowerCase().replaceAll(" ", "_"));
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "disable the advancement tab " + tab.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        tab = (Expression<String>) exprs[0];
        return true;
    }
}
