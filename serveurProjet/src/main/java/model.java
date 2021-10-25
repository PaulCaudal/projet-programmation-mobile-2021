import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class model {
    private static Connection cnx;
    private static Statement st;
    private static ResultSet rst,rst2,rst3;

    public static void savematch(Match m){
        cnx= Connexion.connecterDB();
        String query="INSERT INTO `Matchs`(`ID`, `Date`, `Loc`, `NomJ1`, `NomJ2`, `Winner`) VALUES ("+m.getId()+",'"+m.getDate()+"','"+m.getLoc()+"','"+m.getJ1()+"','"+m.getJ2()+"',"+m.getW()+")";

        try {
            st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ArrayList<Set> sets = m.getSets();
        for(int i =0; i<sets.size();i++){
            saveset(sets.get(i),m.getId());
        }
        ArrayList<String> uris = m.getUris();
        saveUris(uris,m.getId());
    }
    public static void saveset(Set s, int idm){
        cnx= Connexion.connecterDB();
        String query ="INSERT INTO `Sets`(`IDMatch`, `NumSet`, `Scorej1`, `Scorej2`) VALUES ("+idm+","+s.getId()+","+s.getScoreJ1()+","+s.getScoreJ2()+")";
        try {
            st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ArrayList<Jeu> jeux = s.getJeux();
        for(int i=0;i<jeux.size();i++){
            savejeu(jeux.get(i),idm,s.getId());
        }
    }
    public static void savejeu(Jeu j, int idm, int ids){
        cnx= Connexion.connecterDB();
        String query="INSERT INTO `Jeux`(`IDMatch`, `NumSet`, `NumJeu`, `PremierserviceJ1`, `DeuxiemeserviceJ1`, `aceJ1`, `DoublefauteJ1`, `PointgagnantJ1`, `FautePJ1`, `FauteDJ1`, `PremierserviceJ2`, `DeuxiemeserviceJ2`, `aceJ2`, `DoublefauteJ2`, `PointgagnantJ2`, `FautePJ2`, `FauteDJ2`)";
        query+=" VALUES ("+idm+","+ids+","+j.getId()+","+j.getPremierserviceJ1()+","+j.getDeuxiemeserviceJ1()+","+j.getAceJ1()+","+j.getDoublefauteJ1()+","+j.getPointgagnantJ1()+","+j.getFautePJ1()+","+j.getFauteDJ1()+","+j.getPremierserviceJ2()+","+j.getDeuxiemeserviceJ2()+","+j.getAceJ2()+","+j.getDoublefauteJ2()+","+j.getPointgagnantJ2()+","+j.getFautePJ2()+","+j.getFauteDJ2()+")";
        try {
            st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void saveUris(ArrayList<String> uris, int idm){
        cnx= Connexion.connecterDB();
        String query="";
        {
            for (int i = 0; i < uris.size(); i++)
                query = "INSERT INTO `Uris`(`IDMatch`, `uri`) VALUES (" + idm + ",'" + uris.get(i) + "')";
            try {
                st = cnx.createStatement();
                st.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    
    public static ArrayList<Match> getallmatch(){
        ArrayList<Match>matches = new ArrayList<>();
        cnx= Connexion.connecterDB();
        String query="SELECT * FROM Matchs";
        try{
            st=cnx.createStatement();
            rst3=st.executeQuery(query);
            rst3.first();
            boolean b=true;
            while(b){
                int id=rst3.getInt(1);
                String Date = rst3.getString(2);
                String loc = rst3.getString(3);
                int w = rst3.getInt(6);
                String j1 = rst3.getString(4);
                String j2 = rst3.getString(5);
                Match match = new Match(id,Date,j1,j2,w,loc);
                ArrayList<Set> sets=getallset(id);
                ArrayList<String> uris=getalluris(id);
                match.setSets(sets);
                match.setUris(uris);
                matches.add(match);
                b=rst3.next();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return matches;
    }
    public static ArrayList<Set> getallset(int idm){
        ArrayList<Set> sets = new ArrayList<>();
        cnx= Connexion.connecterDB();
        String query="SELECT * FROM Sets WHERE IDMatch="+idm;
        try{
            st=cnx.createStatement();
            rst2=st.executeQuery(query);
            rst2.first();
            boolean b=true;
            while(b){
                int ids=rst2.getInt(2);
                int j1 = rst2.getInt(3);
                int j2 = rst2.getInt(4);
                ArrayList<Jeu> jeux=getalljeu(idm,ids);
                sets.add(new Set(ids,jeux,j1,j2));
                b=rst2.next();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sets;
    }

    public static ArrayList<Jeu> getalljeu(int idm, int ids){
        ArrayList<Jeu> jeux=new ArrayList<>();
        cnx= Connexion.connecterDB();
        String query="SELECT * FROM Jeux WHERE IDMatch="+idm+" AND NumSet="+ids;
        try{
            st=cnx.createStatement();
            rst=st.executeQuery(query);
            rst.first();
            boolean b=true;
            while(b){
                int idj=rst.getInt(3);
                int PremierserviceJ1=rst.getInt(4);
                int DeuxiemeserviceJ1=rst.getInt(5);
                int aceJ1=rst.getInt(6);
                int DoublefauteJ1=rst.getInt(7);
                int PointgagnantJ1=rst.getInt(8);
                int FautePJ1=rst.getInt(9);
                int FauteDJ1=rst.getInt(10);
                int PremierserviceJ2=rst.getInt(11);
                int DeuxiemeserviceJ2=rst.getInt(12);
                int aceJ2=rst.getInt(13);
                int DoublefauteJ2=rst.getInt(14);
                int PointgagnantJ2=rst.getInt(15);
                int FautePJ2=rst.getInt(16);
                int FauteDJ2=rst.getInt(17);
                jeux.add(new Jeu(idj,PremierserviceJ1,DeuxiemeserviceJ1,aceJ1,DoublefauteJ1,PointgagnantJ1,FautePJ1,FauteDJ1,PremierserviceJ2,DeuxiemeserviceJ2,aceJ2,DoublefauteJ2,PointgagnantJ2,FautePJ2,FauteDJ2));
                b=rst.next();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return jeux;
    }

    public static ArrayList<String> getalluris(int idm){
        ArrayList<String> uris= new ArrayList<>();
        cnx= Connexion.connecterDB();
        String query="SELECT * FROM Uris WHERE IDMatch="+idm;
        try{
            st=cnx.createStatement();
            rst2=st.executeQuery(query);
            rst2.first();
            boolean b=true;
            while(b){
                String uri=rst2.getString(2);
                uris.add(uri);
                b=rst2.next();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return uris;
    }
}
