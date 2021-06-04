package ml.abyssal.abyssalproxy.utils;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.JailEvent;
import ml.abyssal.abyssalproxy.events.Punishment;
import ml.abyssal.abyssalproxy.events.ReportEvent;
import ml.abyssal.abyssalproxy.types.PunishmentType;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class Parser {
    public static String punishment(Punishment punishment, String line) {
        String p = line;
        String type;
        long total = 0;
        switch (punishment.getType()) {
            case BAN:
                type = "Ban";
                total = LitebansDatabase.getTotalBans();
                break;
            case KICK:
                type = "Kick";
                total = LitebansDatabase.getTotalKicks();
                break;
            case MUTE:
                type = "Mute";
                total = LitebansDatabase.getTotalMutes();
                break;
            case WARN:
                type = "Warn";
                total = LitebansDatabase.getTotalWarns();
                break;
            case IPBAN:
                type = "IPBan";
                total = LitebansDatabase.getTotalBans();
                break;
            default:
                type = "Unknown";
                break;
        }
        p = p.replace("{type}", type);
        p = p.replace("{victim}", punishment.getVictim());
        p = p.replace("{executor}", punishment.getExecutor());
        p = p.replace("{duration}", punishment.getDuration());
        p = p.replace("{reason}", punishment.getReason());
        p = p.replace("{total}", String.valueOf(total));
        return Parser.color(p);
    }

    public static Color punishmentColor(PunishmentType type) {
        switch (type) {
            case IPBAN:
                return AbyssalProxy.getInstance().getConfigManager().getIpBanColor();
            case WARN:
                return AbyssalProxy.getInstance().getConfigManager().getWarnColor();
            case MUTE:
                return AbyssalProxy.getInstance().getConfigManager().getMuteColor();
            case KICK:
                return AbyssalProxy.getInstance().getConfigManager().getKickColor();
            case BAN:
                return AbyssalProxy.getInstance().getConfigManager().getBanColor();
            default:
                return new Color(128, 0, 128);
        }
    }

    public static String jail(JailEvent e, String line) {
        String p = line;
        p = p.replace("{type}", "Jail");
        p = p.replace("{victim}", e.getTarget());
        p = p.replace("{executor}", e.getSender());
        p = p.replace("{duration}", e.getTime());
        p = p.replace("{reason}", e.getReason());
        p = p.replace("{total}", "");
        return Parser.color(p);
    }

    public static String report(ReportEvent e, String line) {
        String p = line;
        p = p.replace("{player}", e.getReporter().getName());
        p = p.replace("{target}", e.getTarget().getName());
        p = p.replace("{reason}", e.getReason());
        p = p.replace("{server}", e.getServer().getName());
        return Parser.color(p);
    }

    public static int getDecimal(String hex){
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        int val = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    public static String toDate(long milliseconds) {
        if (milliseconds <= 0) return "Forever";
        String str = ""; long d = 0, h = 0, m = 0, s = 0;
        if (milliseconds >= 86400000L) {
            d = milliseconds / 86400000L;
            str = d + (d > 1 ? " days" : " day");
            milliseconds = milliseconds - (d * 86400000L);
        }
        if (milliseconds >= 3600000L) {
            h = milliseconds / 3600000;
            if (d != 0) {
                str += ", " + h + (h > 1 ? " hours" : " hour");
            } else {
                str += h + (h > 1 ? " hours" : " hour");
            }
            milliseconds = milliseconds - (h * 3600000L);
        }
        if (milliseconds > 60000L) {
            m = milliseconds / 60000L;
            if (h != 0 || d != 0) {
                str += ", " + m + (m > 1 ? " minutes" : " minute");
            } else {
                str += m + (m > 1 ? " minutes" : " minute");
            }
            milliseconds = milliseconds - (m * 60000L);
        }
        if (milliseconds > 1000L) {
            s = milliseconds / 1000L;
            if (h != 0 || d != 0 || m != 0) {
                str += ", " + s + (s > 1 ? " seconds" : " second");
            } else {
                str += s + (s > 1 ? " seconds" : " second");
            }
        }

        return str;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String clean(String message) {
        return ChatColor.stripColor(message);
    }
}
