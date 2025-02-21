package me.hotpocket.skriptadvancements.elements.effects;

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

@Name("Disable Vanilla Advancement")
@Description({"Disables all of the vanilla advancements. This can be undone by restarting the server by turning it off and back on, just so no one gets confused.",
"WARNING: This quite literally disables ALL vanilla advancements, including recipes! It will spam your console with warnings as well."})
@Examples("disable all vanilla advancements")
@Since("1.4")

public class EffDisableVanillaAdvancements extends Effect {

    static {
        Skript.registerEffect(EffDisableVanillaAdvancements.class, "disable [all] [vanilla] advancements");
    }

    @Override
    protected void execute(Event e) {
        CustomUtils.getAPI().disableVanillaAdvancements();
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "disable all advancements";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
