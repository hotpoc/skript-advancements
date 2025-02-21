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
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Display Advancement's Toast")
@Description({"Displays the toast of an advancement."})
@Examples("display toast of the advancement \"adventure/root\" to player")
@Since("1.4")

public class EffAdvancementToast extends Effect {

    static {
        if (Skript.classExists("net.kyori.adventure.text.Component") && Skript.classExists("io.papermc.paper.advancement.AdvancementDisplay"))
            Skript.registerEffect(EffAdvancementToast.class,
                    "(show|display) [the] toast (of|from) %advancements% to %players%",
                    "(show|display) %advancements%'[s] toast to %players%");
    }

    private Expression<Advancement> advancements;
    private Expression<Player> players;

    @Override
    protected void execute(Event e) {
        for (Advancement advancement : advancements.getAll(e))
            for (Player player : players.getAll(e))
                if (advancement.getDisplay() != null)
                    CustomUtils.getAPI().displayCustomToast(player, advancement.getDisplay().icon(), Bukkit.getUnsafe().legacyComponentSerializer().serialize(advancement.getDisplay().title()), AdvancementFrameType.valueOf(advancement.getDisplay().frame().name()));
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "display the toast of " + advancements.toString(e, debug) + " to " + players.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        advancements = (Expression<Advancement>) exprs[0];
        players = (Expression<Player>) exprs[1];
        return true;
    }
}
