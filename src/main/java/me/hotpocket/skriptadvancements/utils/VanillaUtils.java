package me.hotpocket.skriptadvancements.utils;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class VanillaUtils {

    public static void grantAdvancement(Player player, Advancement advancement) {
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        for (String criteria : progress.getRemainingCriteria())
            progress.awardCriteria(criteria);
    }

    public static void revokeAdvancement(Player player, Advancement advancement) {
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        for (String criteria : progress.getAwardedCriteria())
            progress.revokeCriteria(criteria);
    }

    public static List<Advancement> getAdvancements() {
        List<Advancement> advancementList = new ArrayList<>();
        Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
        while (iterator.hasNext()) {
            advancementList.add(iterator.next());
        }
        return advancementList;
    }

    public static List<Advancement> getPlayerAdvancements(Player player) {
        List<Advancement> advancementList = new ArrayList<>();
        for (Advancement advancement : getAdvancements()) {
            if (player.getAdvancementProgress(advancement).isDone()) {
                advancementList.add(advancement);
            }
        }
        return advancementList;
    }

    public static int getProgression(Player player, Advancement advancement) {
        return player.getAdvancementProgress(advancement).getAdvancement().getCriteria().size();
    }

    public static void setProgression(Player player, Advancement advancement, int progression) {
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        revokeAdvancement(player, advancement);
        progression = min(max(progression, 0), progress.getRemainingCriteria().size());
        List<String> criterion = new ArrayList<>(progress.getRemainingCriteria());
        for (int i = 0; i < progression; i++) {
            progress.awardCriteria(criterion.get(i));
        }
    }

    public static void addProgression(Player player, Advancement advancement, int progression) {
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        progression = min(max(progression, 0), progress.getRemainingCriteria().size());
        if (!progress.isDone()) {
            List<String> criterion = new ArrayList<>(progress.getRemainingCriteria());
            for (int i = 0; i < progression; i++) {
                progress.awardCriteria(criterion.get(i));
            }
        }
    }

    public static void removeProgression(Player player, Advancement advancement, int progression) {
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        progression = min(max(progression, 0), progress.getAwardedCriteria().size());
        List<String> criterion = new ArrayList<>(progress.getAwardedCriteria());
        for (int i = 0; i < progression; i++) {
            progress.revokeCriteria(criterion.get(i));
        }
    }
}
