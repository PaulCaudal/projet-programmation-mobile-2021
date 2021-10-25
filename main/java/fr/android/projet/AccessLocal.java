package fr.android.projet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

public class AccessLocal {
    private MySQLiteOpenHelper accessDB;
    private SQLiteDatabase db;

    public AccessLocal(Context c){
        accessDB=new MySQLiteOpenHelper(c, "dbMatch2.sqlite",null,1);
    }

    public void ajout (Match match, String j1, String j2, int win, int los,String loc){
        db= accessDB.getReadableDatabase();
        int id;
        String r="select * from Matchs";
        Cursor c=db.rawQuery(r,null);
        int count = c.getCount();
        if(count==5)
        {
            Match m =getmatch();
            sendtoserveur(m);
        }
        r="select * from Matchs ";
        c=db.rawQuery(r,null);
        c.moveToFirst();
        int max=0,temp=0;
        while(!c.isAfterLast()){
            temp=c.getInt(0);
            if(max<temp)
                max=temp;
            c.moveToNext();
        }
         id=max+1;
        db = accessDB.getWritableDatabase();
        Date d = new Date();
        r = "insert into Matchs (ID, Date, Loc, Winner, Loser, Joueur1, Joueur2) values ";
        r += "(" + id + ",\"" + d + "\",\""+loc+"\"," + win + "," + los + ",\"" + j1 + "\",\"" + j2 + "\")";
        db.execSQL(r);
        ArrayList<Set> sets = match.getSets();
        for (int i = 0; i < sets.size(); i++) {
            r = "insert into Sets (IDMatch, NumSet, Score1, Score2) values ";
            r += "(" + id + "," + i + "," + sets.get(i).getScoreJ1() + "," + sets.get(i).getScoreJ2() + ")";
            db.execSQL(r);
            ArrayList<Jeu> jeux = sets.get(i).getJeux();
            for (int j = 0; j < jeux.size(); j++) {
                r = "insert into Jeux (IDMatch, NumSet, NumJeu, PremierserviceJ1,DeuxiemeserviceJ1,aceJ1,DoublefauteJ1,PointgagnantJ1,FautePJ1,FauteDJ1, PremierserviceJ2,DeuxiemeserviceJ2,aceJ2,DoublefauteJ2,PointgagnantJ2,FautePJ2,FauteDJ2) values ";
                r += "(" + id + "," + i + "," + j + "," + jeux.get(j).getPremierserviceJ1() + "," + jeux.get(j).getDeuxiemeserviceJ1() + ", " + jeux.get(j).getAceJ1() + ", " + jeux.get(j).getDoublefauteJ1() + "," + jeux.get(j).getPointgagnantJ1() + "," + jeux.get(j).getFautePJ1() + "," + jeux.get(j).getFauteDJ1();
                r += "," + jeux.get(j).getPremierserviceJ2() + "," + jeux.get(j).getDeuxiemeserviceJ2() + ", " + jeux.get(j).getAceJ2() + ", " + jeux.get(j).getDoublefauteJ2() + "," + jeux.get(j).getPointgagnantJ2() + "," + jeux.get(j).getFautePJ2() + "," + jeux.get(j).getFauteDJ2() + ")";
                db.execSQL(r);
            }
        }
        ArrayList<Uri> uri=match.getImages_uri();
        for(int k=0;k<uri.size();k++){
            r="insert into Uris(IDMatch,uri) values ("+id+",\""+uri.get(k).toString()+"\")";
            db.execSQL(r);
        }
        db= accessDB.getReadableDatabase();
        r="select * from Matchs";
        c=db.rawQuery(r,null);
        count = c.getCount();
        System.out.println(count);
    }
    public Match getmatch(){
        Match m;
        db= accessDB.getReadableDatabase();
        String r="select * from Matchs ";
        Cursor c=db.rawQuery(r,null);
        c.moveToFirst();
        int min=150,temp=0;
        while(!c.isAfterLast()){
            temp=c.getInt(0);
            if(min>temp)
                min=temp;
            c.moveToNext();
        }
         r="select * from Matchs where ID="+min;
         c=db.rawQuery(r,null);
        c.moveToLast();
        int id=c.getInt(0);
        String Date = c.getString(1);
        String loc = c.getString(2);
        int w = c.getInt(3);
        int los = c.getInt(4);
        String j1 = c.getString(5);
        String j2 = c.getString(6);
        m = new Match(id,Date,j1,j2,w,loc);
        ArrayList<Set>set = recupset(id);
        m.setSets(set);
        ArrayList<Uri> uris =recupuri(id);
        m.setImages_uri(uris);
        r="delete from Matchs where ID="+id;
        db.execSQL(r);
        r="delete from Sets where IDMatch="+id;
        db.execSQL(r);
        r="delete from Jeux where IDMatch="+id;
        db.execSQL(r);
        r="delete from Uris where IDMatch="+id;
        db.execSQL(r);
        return m;
    }

    public boolean veriflocal(int id){
        db=accessDB.getReadableDatabase();
        ArrayList<Match> match =new ArrayList<>();
        String r= "select * from Matchs where ID="+id;
        Cursor c=db.rawQuery(r,null);
        if(c.getCount()!=0)
            return true;
        else
            return false;
    }

    public ArrayList<Match> verif(){
        db=accessDB.getReadableDatabase();
        ArrayList<Match> match =new ArrayList<>();
        String r= "select * from Matchs";
        Cursor c=db.rawQuery(r,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int id=c.getInt(0);
            String Date = c.getString(1);
            String loc = c.getString(2);
            int w = c.getInt(3);
            int los = c.getInt(4);
            String j1 = c.getString(5);
            String j2 = c.getString(6);
            System.out.println("ID: "+id+"\nDate: "+Date+"\nWinner: "+w+"\nLoser: "+los+"\nJoueur 1: "+j1+"\nJoueur 2: "+j2);
            match.add(new Match(id,Date,j1,j2,w,loc));
            c.moveToNext();
        }
        c.close();
        match=getfromserveur(match);
        return match;
    }

    public ArrayList<Set> recupset(int id){
        db=accessDB.getReadableDatabase();
        ArrayList<Set> sets=new ArrayList<>();
        String r= "select * from Sets where IDMatch="+id;
        Cursor c=db.rawQuery(r,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int ids=c.getInt(1);
            int j1 = c.getInt(2);
            int j2 = c.getInt(3);
            ArrayList<Jeu> jeux=recupjeu(id,ids);
            System.out.println("ID: "+ids+"\nscore j1: "+j1+"\nscore j2: "+j2);
            sets.add(new Set(ids,jeux,j1,j2));
            c.moveToNext();
        }
        return sets;
    }

    public ArrayList<Jeu> recupjeu(int idm,int ids){
        db=accessDB.getReadableDatabase();
        ArrayList<Jeu> jeux=new ArrayList<>();
        String r= "select * from Jeux where NumSet="+ids+" and IDMatch="+idm;
        Cursor c=db.rawQuery(r,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            int idj=c.getInt(2);
            int PremierserviceJ1=c.getInt(3);
            int DeuxiemeserviceJ1=c.getInt(4);
            int aceJ1=c.getInt(5);
            int DoublefauteJ1=c.getInt(6);
            int PointgagnantJ1=c.getInt(7);
            int FautePJ1=c.getInt(8);
            int FauteDJ1=c.getInt(9);
            int PremierserviceJ2=c.getInt(10);
            int DeuxiemeserviceJ2=c.getInt(11);
            int aceJ2=c.getInt(12);
            int DoublefauteJ2=c.getInt(13);
            int PointgagnantJ2=c.getInt(14);
            int FautePJ2=c.getInt(15);
            int FauteDJ2=c.getInt(16);

            jeux.add(new Jeu(idj,PremierserviceJ1,DeuxiemeserviceJ1,aceJ1,DoublefauteJ1,PointgagnantJ1,FautePJ1,FauteDJ1,PremierserviceJ2,DeuxiemeserviceJ2,aceJ2,DoublefauteJ2,PointgagnantJ2,FautePJ2,FauteDJ2));
            c.moveToNext();
        }
        return jeux;
    }

    public ArrayList<Uri> recupuri(int id){
        ArrayList<Uri> uris = new ArrayList<>();
        String r= "select * from Uris where IDMatch="+id;
        Cursor c=db.rawQuery(r,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            uris.add(Uri.parse(c.getString(1)));
            c.moveToNext();
        }
        return uris;
    }

    public ArrayList<Match> getfromserveur(ArrayList<Match> matches){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Socket comm = null;
                final BufferedReader in;
                final PrintWriter out;
                try {
                    comm = new Socket("10.0.2.2", 9876);
                    out = new PrintWriter(comm.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(comm.getInputStream()));
                    //DataInputStream dis = new DataInputStream(comm.getInputStream());
                    //DataOutputStream dos = new DataOutputStream(comm.getOutputStream());
                    String res = "";
                    //Ordre pour le serveur: receive
                    out.println(""+2);
                    out.flush();
                    //Data du Match
                    int nbmatch = Integer.parseInt(in.readLine());
                    for (int i = 0; i < nbmatch; i++) {
                        int id = Integer.parseInt(in.readLine());
                        String j1 = in.readLine();
                        String j2 = in.readLine();
                        String Date = in.readLine();
                        String loc=in.readLine();
                        int w = Integer.parseInt(in.readLine());
                        Match match = new Match(id,Date,j1,j2,w,loc);
                        int nbset=Integer.parseInt(in.readLine());
                        ArrayList<Set>sets=new ArrayList<>();

                        for (int j = 0; j < nbset; j++) {

                            int ids=Integer.parseInt(in.readLine());
                            int scorej1=Integer.parseInt(in.readLine());
                            int scorej2=Integer.parseInt(in.readLine());
                            int nbjeu=Integer.parseInt(in.readLine());
                            ArrayList<Jeu> jeux = new ArrayList<>();

                            for(int k = 0; k < nbjeu; k++){
                                int idj=Integer.parseInt(in.readLine());
                                int PremierserviceJ1=Integer.parseInt(in.readLine());
                                int DeuxiemeserviceJ1=Integer.parseInt(in.readLine());
                                int aceJ1=Integer.parseInt(in.readLine());
                                int DoublefauteJ1=Integer.parseInt(in.readLine());
                                int PointgagnantJ1=Integer.parseInt(in.readLine());
                                int FautePJ1=Integer.parseInt(in.readLine());
                                int FauteDJ1=Integer.parseInt(in.readLine());
                                int PremierserviceJ2=Integer.parseInt(in.readLine());
                                int DeuxiemeserviceJ2=Integer.parseInt(in.readLine());
                                int aceJ2=Integer.parseInt(in.readLine());
                                int DoublefauteJ2=Integer.parseInt(in.readLine());
                                int PointgagnantJ2=Integer.parseInt(in.readLine());
                                int FautePJ2=Integer.parseInt(in.readLine());
                                int FauteDJ2=Integer.parseInt(in.readLine());
                                jeux.add(new Jeu(idj,PremierserviceJ1,DeuxiemeserviceJ1,aceJ1,DoublefauteJ1,PointgagnantJ1,FautePJ1,FauteDJ1,PremierserviceJ2,DeuxiemeserviceJ2,aceJ2,DoublefauteJ2,PointgagnantJ2,FautePJ2,FauteDJ2));
                            }
                            sets.add(new Set(ids,jeux,scorej1,scorej2));
                        }
                        int nburis=Integer.parseInt(in.readLine());
                        ArrayList<Uri> uris=new ArrayList<>();
                        for(int h=0;h<nburis;h++){
                            uris.add(Uri.parse(in.readLine()));
                        }
                        match.setSets(sets);
                        matches.add(match);
                    }

                    out.flush();
                    res=in.readLine();
                    out.close();
                    in.close();
                    System.out.println(res);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    System.out.println("Erreur d'host");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Autre");
                }

            }
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return matches;
    }

    public void sendtoserveur(Match match){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Socket comm = null;
                final BufferedReader in;
                final PrintWriter out;
                try {
                    comm = new Socket("10.0.2.2", 9876);
                    out = new PrintWriter(comm.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(comm.getInputStream()));
                    //DataInputStream dis = new DataInputStream(comm.getInputStream());
                    //DataOutputStream dos = new DataOutputStream(comm.getOutputStream());
                    String res = "";
                    //Ordre pour le serveur: receive
                    out.println(""+1);
                    //Data du Match
                    out.println(""+match.getId());
                    out.println(match.getJ1());
                    out.println(match.getJ2());
                    out.println(match.getDate());
                    out.println(match.getLoc());
                    out.println(""+match.getW());
                    ArrayList<Set> sets = match.getSets();
                    out.println(""+sets.size());
                    for (int i = 0; i < sets.size(); i++) {
                        out.println(""+sets.get(i).getId());
                        out.println(""+sets.get(i).getScoreJ1());
                        out.println(""+sets.get(i).getScoreJ2());
                        ArrayList<Jeu> jeux = sets.get(i).getJeux();
                        out.println(""+jeux.size());
                        for (int j = 0; j < jeux.size(); j++) {
                            out.println(""+jeux.get(j).getId());
                            out.println(""+jeux.get(j).getPremierserviceJ1());
                            out.println(""+jeux.get(j).getDeuxiemeserviceJ1());
                            out.println(""+jeux.get(j).getAceJ1());
                            out.println(""+jeux.get(j).getDoublefauteJ1());
                            out.println(""+jeux.get(j).getPointgagnantJ1());
                            out.println(""+jeux.get(j).getFautePJ1());
                            out.println(""+jeux.get(j).getFauteDJ1());

                            out.println(""+jeux.get(j).getPremierserviceJ2());
                            out.println(""+jeux.get(j).getDeuxiemeserviceJ2());
                            out.println(""+jeux.get(j).getAceJ2());
                            out.println(""+jeux.get(j).getDoublefauteJ2());
                            out.println(""+jeux.get(j).getPointgagnantJ2());
                            out.println(""+jeux.get(j).getFautePJ2());
                            out.println(""+jeux.get(j).getFauteDJ2());
                        }
                    }
                    out.println(""+match.images_uri.size());
                    for(int k=0;k<match.images_uri.size();k++){
                        out.println(match.images_uri.get(k).toString());
                    }

                    out.flush();
                    res=in.readLine();
                    out.close();
                    in.close();
                    System.out.println(res);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    System.out.println("Erreur d'host");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Autre");
                }

            }
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
