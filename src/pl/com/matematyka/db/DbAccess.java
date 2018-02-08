package pl.com.matematyka.db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DbAccess {

    private static Connection con = null;
    private final DbConfig conf = new DbConfig();

    public DbAccess() {
        conf.czytajConfig();
    }

    public final Connection getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connString = "jdbc:mysql://" + conf.getHost() + "/" + conf.getDbName();
            con = (Connection) DriverManager.getConnection(connString, conf.getDbUser(), conf.getDbPass());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return con;
    }

    public int dodaj_uaktualnij_uzytkownika(String fname, String lname, String userName, String pass, InputStream pic) {
        int ret = 0;
        try {
            if (con == null) {
                con = getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("call dodaj_uzytkownika(?, ?, ?, ?, ?)");
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, userName);
            ps.setString(4, pass);
            ps.setBlob(5, pic);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret = rs.getInt(1);
            }

        } catch (Exception e) {
            ret = -1;
        }
        return ret;
    }

    public User wezUzytkownika(String userName, String password) {
        User u = new User();
        u.setUserName("");
        u.setUserPassword("");
        try {
            if (con == null) {
                con = getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("call wez_uzytkownika(?, ?)");
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setUserName(userName);
                u.setUserPassword(password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public List<Dzial> wezDzialy() {
        List<Dzial> ld = new ArrayList<Dzial>();
        try {
            if (con == null) {
                con = getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("call wez_dzialy()");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dzial d = new Dzial();
                d.setId_dzialu(rs.getInt("id"));
                d.setNazwa_dzialu(rs.getString("nazwa"));
                ld.add(d);
            }
        } catch (Exception e) {
        }
        return ld;
    }

    public List<Zadanie> wezZadania(int ile, int dzial_id) {
        List<Zadanie> lz = new ArrayList<Zadanie>();
        try {
            if (con == null) {
                con = getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("call wez_zadania(?, ?)");
            ps.setInt(1, ile);
            ps.setInt(2, dzial_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Zadanie z = new Zadanie();
                z.setId_zadania(rs.getInt("id"));
                z.setTresc(rs.getString("tresc"));
                z.setOdp1(rs.getString("odp1"));
                z.setOdp2(rs.getString("odp2"));
                z.setOdp3(rs.getString("odp3"));
                z.setOdp4(rs.getString("odp4"));
                z.setPoprawna(rs.getInt("poprawna"));
                lz.add(z);
            }

        } catch (Exception e) {
        }

        return lz;
    }

    public void zapisz_statystyke(String userName, int id_zadania, boolean popr, String dStart, String dStop) {
        try {
            if (con == null) {
                con = getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("call zapisz_statystyke(?, ?, ?, ?, ?)");
            ps.setString(1, userName);
            ps.setInt(2, id_zadania);
            ps.setBoolean(3, popr);
            ps.setString(4, dStart);
            ps.setString(5, dStop);
            ps.executeQuery();
        } catch (Exception e) {
        }
    }

    public List<Stats> wez_statystyke(String userName) {
        List<Stats> ls = new ArrayList<Stats>();
        try {
            if (con == null) {
                con = getConnection();
            }
            PreparedStatement ps = (PreparedStatement) con.prepareStatement("call wez_statystyke(?)");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Stats s = new Stats();
                s.setTresc(rs.getString("tresc"));
                s.setPopr(rs.getInt("popr"));
                s.setCzas_start(rs.getString("czas_rozpoczecia"));
                s.setCzas_stop(rs.getString("czas_zakonczenia"));  
                ls.add(s);
            }
        } catch (Exception e) {
        }
        return ls;
    }

}
