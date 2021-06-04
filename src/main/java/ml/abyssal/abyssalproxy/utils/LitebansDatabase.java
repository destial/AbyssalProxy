package ml.abyssal.abyssalproxy.utils;

import litebans.api.Database;
import ml.abyssal.abyssalproxy.events.Punishment;
import ml.abyssal.abyssalproxy.types.PunishmentType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class LitebansDatabase {
    public static String getPlayerName(String uuid) {
        String query = "SELECT name FROM {history} WHERE uuid=? ORDER BY date DESC LIMIT 1";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            st.setString(1, uuid);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getTotalBans() {
        String query = "SELECT COUNT(*) FROM {bans}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getTotalWarns() {
        String query = "SELECT COUNT(*) FROM {warnings}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getTotalKicks() {
        String query = "SELECT COUNT(*) FROM {kicks}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getTotalMutes() {
        String query = "SELECT COUNT(*) FROM {mutes}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getUUID(String name) {
        String query = "SELECT * FROM {history}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    if (name.equalsIgnoreCase(rs.getString("name"))) return rs.getString("uuid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Punishment> getHistory(String uuid) {
        List<Punishment> history = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
        String query = "SELECT * FROM {bans}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("uuid").equalsIgnoreCase(uuid)) {
                        if (rs.getBoolean("ipban")) {
                            Punishment p = new Punishment(PunishmentType.IPBAN, getPlayerName(uuid), rs.getString("banned_by_name"), rs.getLong("time"), rs.getLong("until"), rs.getString("reason"), rs.getBoolean("active"));
                            history.add(p);
                        } else {
                            Punishment p = new Punishment(PunishmentType.BAN, getPlayerName(uuid), rs.getString("banned_by_name"), rs.getLong("time"), rs.getLong("until"), rs.getString("reason"), rs.getBoolean("active"));
                            history.add(p);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "SELECT * FROM {mutes}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("uuid").equalsIgnoreCase(uuid)) {
                        Punishment p = new Punishment(PunishmentType.MUTE, getPlayerName(uuid), rs.getString("banned_by_name"), rs.getLong("time"), rs.getLong("until"), rs.getString("reason"), rs.getBoolean("active"));
                        history.add(p);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "SELECT * FROM {warnings}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("uuid").equalsIgnoreCase(uuid)) {
                        Punishment p = new Punishment(PunishmentType.WARN, getPlayerName(uuid), rs.getString("banned_by_name"), rs.getLong("time"), rs.getLong("until"), rs.getString("reason"), rs.getBoolean("active"));
                        history.add(p);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "SELECT * FROM {kicks}";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("uuid").equalsIgnoreCase(uuid)) {
                        Punishment p = new Punishment(PunishmentType.KICK, getPlayerName(uuid), rs.getString("banned_by_name"), rs.getLong("time"), rs.getLong("until"), rs.getString("reason"), rs.getBoolean("active"));
                        history.add(p);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        history.sort(Comparator.comparingLong(Punishment::getMilliseconds).reversed());
        return history;
    }
}
