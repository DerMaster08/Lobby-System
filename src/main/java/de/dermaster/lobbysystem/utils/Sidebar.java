package de.dermaster.lobbysystem.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.server.ScoreboardServer;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Sidebar {

    private ImmutableList<String> sidebarLines;
    private String displayName = "null";
    private Player player;

    private PacketPlayOutScoreboardObjective removePacket = null;
    private PacketPlayOutScoreboardObjective createPacket = null;
    private PacketPlayOutScoreboardDisplayObjective displayPacket = null;
    private List<Packet<?>> packets = new ArrayList<Packet<?>>();

    public Sidebar(String displayName, ImmutableList<String> sidebarLines) {
        this.displayName = displayName;
        this.sidebarLines = sidebarLines;
    }

    public Sidebar(String displayName, ImmutableList<String> sidebarLines, Player player) {
        this(displayName,sidebarLines);
        this.player = player;
    }

    public ImmutableList<String> getSidebarLines() {
        return sidebarLines;
    }

    public Sidebar setSidebarLines(ImmutableList<String> sidebarLines) {
        this.sidebarLines = sidebarLines;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Sidebar setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public Sidebar setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public Sidebar build() {

        Scoreboard scoreboard = new Scoreboard();
        ScoreboardObjective scoreboardObjective = new ScoreboardObjective(scoreboard, displayName,
                IScoreboardCriteria.a, IChatBaseComponent.ChatSerializer.b(displayName),
                IScoreboardCriteria.EnumScoreboardHealthDisplay.a);

        removePacket = new PacketPlayOutScoreboardObjective(scoreboardObjective, 1);
        createPacket = new PacketPlayOutScoreboardObjective(scoreboardObjective, 0);
        displayPacket = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);

        scoreboardObjective.setDisplayName(IChatBaseComponent.ChatSerializer.b(displayName));

        transListToPackets();
        return this;
    }

    private void transListToPackets() {
        packets.clear();
        if(!sidebarLines.isEmpty()) {
            int i = sidebarLines.size()-1;
            for (String line : sidebarLines) {
                packets.add(new PacketPlayOutScoreboardScore(ScoreboardServer.Action.a,displayName,line, i));
                i--;
            }
        }
    }

    private void sendPacket(Packet<?> packet) {
        ((CraftPlayer) player).getHandle().b.sendPacket(packet);
    }

    private Sidebar update() {
        return this;
    }

    public Sidebar send() {
        if (player == null) return this;
        remove();
        if (createPacket != null)sendPacket(createPacket);
        if (displayPacket != null)sendPacket(displayPacket);
        if (!packets.isEmpty()) {
            for (Packet<?> packet : packets) {
                sendPacket(packet);
            }
        }
        return this;
    }

    public Sidebar send(Player player) {
        setPlayer(player);
        remove();
        if (createPacket != null)sendPacket(createPacket);
        if (displayPacket != null)sendPacket(displayPacket);
        if (!packets.isEmpty()) {
            for (Packet<?> packet : packets) {
                sendPacket(packet);
            }
        }
        return this;
    }

    public void remove() {
        if (removePacket != null)sendPacket(removePacket);
    }


}
