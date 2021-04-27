package ml.abyssal.abyssalproxy.utils;

import ml.abyssal.abyssalproxy.AbyssalProxy;
import ml.abyssal.abyssalproxy.events.JailEvent;
import ml.abyssal.abyssalproxy.events.Punishment;
import ml.abyssal.abyssalproxy.events.ReportEvent;
import ml.abyssal.abyssalproxy.types.PunishmentType;

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
        return p;
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
        return p;
    }

    public static String report(ReportEvent e, String line) {
        String p = line;
        p = p.replace("{player}", e.getReporter().getName());
        p = p.replace("{target}", e.getTarget().getName());
        p = p.replace("{reason}", e.getReason());
        p = p.replace("{server}", e.getServer().getName());
        return p;
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
}
