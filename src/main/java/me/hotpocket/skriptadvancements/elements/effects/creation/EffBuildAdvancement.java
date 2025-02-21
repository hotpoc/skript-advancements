package me.hotpocket.skriptadvancements.elements.effects.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancement;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancementTab;
import me.hotpocket.skriptadvancements.utils.creation.SkriptAdvancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

@Name("Creation - Build Advancement")
@Description({"Builds the last created advancement.",
"This can only be used inside of the advancement section."})
@Examples("build advancement")
@Since("1.4")

public class EffBuildAdvancement extends Effect {

    static {
        Skript.registerEffect(EffBuildAdvancement.class, "build [[the] last (created|made)] [custom] advancement");
    }

    @Override
    protected void execute(Event e) {
        SkriptAdvancement advancement = getParser().getCurrentSection(SecAdvancement.class).getAdvancement();
        SecAdvancementTab tab = getParser().getCurrentSection(SecAdvancementTab.class);
        if (advancement != null && tab != null) {
            if (advancement.root) {
                try {
                    tab.rootAdvancement = (RootAdvancement) advancement.build(tab.getTabName());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    tab.addAdvancement((BaseAdvancement) advancement.build(tab.getTabName()));
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "build the custom advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }
}
