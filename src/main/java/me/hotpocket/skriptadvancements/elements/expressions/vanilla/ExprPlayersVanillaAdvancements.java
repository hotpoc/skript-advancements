package me.hotpocket.skriptadvancements.elements.expressions.vanilla;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.hotpocket.skriptadvancements.utils.VanillaUtils;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Name("Vanilla Advancements of Player")
@Description("Allows you to get and change the vanilla advancements of a player.")
@Examples("broadcast player's advancements")
@Since("1.4")

public class ExprPlayersVanillaAdvancements extends SimpleExpression<Advancement> {

    static {
        Skript.registerExpression(ExprPlayersVanillaAdvancements.class, Advancement.class, ExpressionType.SIMPLE,
                "[all [[of] the]] [vanilla] advancements of %players%",
                "[all [[of] the]] %players%'[s] [vanilla] advancements");
    }

    private Expression<Player> players;

    @Override
    protected @Nullable Advancement[] get(Event e) {
        for (Player player : players.getAll(e))
            return VanillaUtils.getPlayerAdvancements(player).toArray(new Advancement[VanillaUtils.getPlayerAdvancements(player).size()]);
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
        return "the vanilla advancements of " + players.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        players = (Expression<Player>) exprs[0];
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET, ADD, REMOVE, RESET, DELETE -> CollectionUtils.array(Advancement[].class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        List<Advancement> advancements = List.of((Advancement[]) delta);
        for (Player player : players.getAll(e)) {
            switch (mode) {
                case SET:
                    for (Advancement advancement : VanillaUtils.getPlayerAdvancements(player)) {
                        VanillaUtils.revokeAdvancement(player, advancement);
                    }
                    for (Advancement advancement : advancements) {
                        VanillaUtils.grantAdvancement(player, advancement);
                    }
                    break;
                case ADD:
                    for (Advancement advancement : advancements) {
                        if (!player.getAdvancementProgress(advancement).isDone()) {
                            VanillaUtils.grantAdvancement(player, advancement);
                        }
                    }
                    break;
                case REMOVE:
                    for (Advancement advancement : advancements) {
                        if (player.getAdvancementProgress(advancement).isDone()) {
                            VanillaUtils.revokeAdvancement(player, advancement);
                        }
                    }
                    break;
                case RESET, DELETE:
                    for (Advancement advancement : VanillaUtils.getPlayerAdvancements(player)) {
                        VanillaUtils.revokeAdvancement(player, advancement);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
