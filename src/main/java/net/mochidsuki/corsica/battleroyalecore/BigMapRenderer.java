package net.mochidsuki.corsica.battleroyalecore;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.*;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

import static org.bukkit.Bukkit.getMap;

public class BigMapRenderer extends MapRenderer {

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {

        int mi = 0;
        for(int x = 0;x <= 127;x++) {
            for (int z = 0; z <= 127; z++) {

                canvas.setPixelColor(x, z, v.colors[mi]);
                mi = mi + 1;
            }
        }
            //カーソルクリア
            for(int i = 0; i <= canvas.getCursors().size();i++){
                try {
                    canvas.getCursors().removeCursor(canvas.getCursors().getCursor(i));
                }catch (Exception e){}
            }
            try {
                //チームメイト表示
                Team playerteam = player.getScoreboard().getPlayerTeam(player);
                String[] tp = new String[Objects.requireNonNull(playerteam).getEntries().size()];
                playerteam.getEntries().toArray(tp);
                Player[] teamplayer = new Player[tp.length];
                MapCursorCollection cursor = new MapCursorCollection();
                for (int i = 0; i < tp.length; i++) {
                    teamplayer[i] = Bukkit.getPlayer(tp[i]);
                    if (!(teamplayer[i] == player)) {
                        int x = (Objects.requireNonNull(teamplayer[i]).getLocation().getBlockX() - player.getLocation().getBlockX()) / 8;
                        if (x > 64) {
                            x = 64;
                        }
                        int z = (teamplayer[i].getLocation().getBlockX() - player.getLocation().getBlockZ()) / 8;
                        if (z > 64) {
                            z = 64;
                        }
                        cursor.addCursor(new MapCursor((byte) x, (byte) z, (byte) ((teamplayer[i].getLocation().getYaw() - teamplayer[i].getLocation().getYaw() % 45) / 45), MapCursor.Type.BLUE_POINTER, true));
                        canvas.setCursors(cursor);
                        }
                    }
            }catch (Exception e){}

            //border予測線
            int[] distance = new int[4];
            distance[0] = (b.target[0] - v.mcx)/8;
            distance[1] = (b.target[1] - v.mcx)/8;
            distance[2] = (b.target[3] - v.mcz)/8;
            distance[3] = (b.target[2] - v.mcz)/8;
            for (; distance[1] <= distance[0]; distance[1]++) {
                if(distance[1] >= -64 && distance[1] <= 64 && distance[2] >= -64 && distance[2] <= 64 ) {
                    canvas.setPixelColor(distance[1]+64, distance[2]+64, Color.gray);
                }
                if(distance[1] >= -64 && distance[1] <= 64 && distance[3] >= -64 && distance[3] <= 64 ) {
                    canvas.setPixelColor(distance[1]+64, distance[3]+64, Color.gray);
                }
            }
            distance[1] = (b.target[1] - v.mcx)/8;
            for (; distance[2] <= distance[3]; distance[2]++) {
                if(distance[0] >= -64 && distance[0] <= 64 && distance[2] >= -64 && distance[2] <= 64 ) {
                    canvas.setPixelColor(distance[0]+64, distance[2]+64, Color.gray);
                }
                if(distance[1] >= -64 && distance[1] <= 64 && distance[2] >= -64 && distance[2] <= 64 ) {
                    canvas.setPixelColor(distance[1]+64, distance[2]+64, Color.gray);
                }
            }

            //border現在位置
            int[] distanceNow = new int[4];
            distanceNow[0] = (int)(v.now[0] - v.mcx)/8;
            distanceNow[1] = (int)(v.now[1] - v.mcx)/8;
            distanceNow[2] = (int)(v.now[3] - v.mcz)/8;
            distanceNow[3] = (int)(v.now[2] - v.mcz)/8;
            for (; distanceNow[1] <= distanceNow[0]; distanceNow[1]++) {
                if(distanceNow[1] >= -64 && distanceNow[1] <= 64 && distanceNow[2] >= -64 && distanceNow[2] <= 64 ) {
                    canvas.setPixelColor(distanceNow[1]+64, distanceNow[2]+64, Color.red);
                }
                if(distanceNow[1] >= -64 && distanceNow[1] <= 64 && distanceNow[3] >= -64 && distanceNow[3] <= 64 ) {
                    canvas.setPixelColor(distanceNow[1]+64, distanceNow[3]+64, Color.red);
                }
            }
            distanceNow[1] = (int)(v.now[1] - v.mcx)/8;
            for (; distanceNow[2] <= distanceNow[3]; distanceNow[2]++) {
                if(distanceNow[0] >= -64 && distanceNow[0] <= 64 && distanceNow[2] >= -64 && distanceNow[2] <= 64 ) {
                    canvas.setPixelColor(distanceNow[0]+64, distanceNow[2]+64, Color.red);
                }
                if(distanceNow[1] >= -64 && distanceNow[1] <= 64 && distanceNow[2] >= -64 && distanceNow[2] <= 64 ) {
                    canvas.setPixelColor(distanceNow[1]+64, distanceNow[2]+64, Color.red);
                }
            }
        }
    }
