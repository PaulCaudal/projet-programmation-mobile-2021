package fr.android.projet;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

public class Match {
    private Joueur J1= new Joueur();
    private Joueur J2= new Joueur();
     ArrayList<Set> sets=new ArrayList<>();
     ArrayList<Uri> images_uri=new ArrayList<>();
    private Set setencours;
    private int serveur;
    private int scoreJ1=0;
    private int scoreJ2=0;
    private int w;
    private int id;
    private String j1,j2;
    private String Date;
    private String loc;
    public Match(int serveur){
        int i=sets.size();
        setencours=new Set(i);
        this.serveur=serveur;
    }
    public Match(int id, String Date, String j1,String j2,int w,String loc){
        this.id=id;
        this.j1=j1;
        this.j2=j2;
        this.Date=Date;
        this.w=w;
        this.loc=loc;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    public int getId() {
        return id;
    }

    public int getW() { return w; }

    public String getJ1() {
        return j1;
    }

    public String getJ2() {
        return j2;
    }

    public String getDate() {
        return Date;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public int getScoreJeuJ1(){return setencours.getScoreJeuJ1();}
    public int getScoreJeuJ2(){return setencours.getScoreJeuJ2();}
    public int getScoreSetJ1(){return setencours.getScoreJ1();}
    public int getScoreSetJ2(){return setencours.getScoreJ2();}
    public int getNumSet(){return sets.size()+1;}
    public int getNumJeu(){return setencours.getNumJeu();}

    public void finset (){
        int s1=setencours.getScoreJ1();
        int s2=setencours.getScoreJ2();

        if(s1>=6 && (s1-s2)>=2){
            scoreJ1+=1;
            sets.add(setencours);
            int i = sets.size();
            setencours=new Set(i);
        }
        if(s2>=6 && (s2-s1)>=2){
            scoreJ2+=1;
            sets.add(setencours);
            int i = sets.size();
            setencours=new Set(i);
        }

    }
    public int getScoreJ1(){return scoreJ1;}
    public int getScoreJ2(){return scoreJ2;}

    public void adduri(Uri uri){
        images_uri.add(uri);
    }

    public ArrayList<Uri> getImages_uri() {
        return images_uri;
    }

    public Uri getlastUri(){
        return images_uri.get(images_uri.size()-1);
    }

    public void setImages_uri(ArrayList<Uri> images_uri) {
        this.images_uri = images_uri;
    }

    //Joueur 1
    public void setPremierserviceJ1(){
        setencours.setPremierserviceJ1();
    }

    public void setDeuxiemeserviceJ1(){
        setencours.setDeuxiemeserviceJ1();
    }
    public void setAceJ1(){
        setencours.setAceJ1();
    }
    public void setDoublefauteJ1(){
        setencours.setDoublefauteJ1();
    }
    public void setPointgagnantJ1(){
        setencours.setPointgagnantJ1();
    }
    public void setFautePJ1(){
        setencours.setFautePJ1();
    }
    public void setFauteDJ1(){
        setencours.setFauteDJ1();
    }

    //Joueur 2
    public void setPremierserviceJ2(){
        setencours.setPremierserviceJ2();
    }
    public void setDeuxiemeserviceJ2(){
        setencours.setDeuxiemeserviceJ2();
    }
    public void setAceJ2(){
        setencours.setAceJ2();
    }
    public void setDoublefauteJ2(){
        setencours.setDoublefauteJ2();
    }
    public void setPointgagnantJ2(){
        setencours.setPointgagnantJ2();
    }
    public void setFautePJ2(){
        setencours.setFautePJ2();
    }
    public void setFauteDJ2(){
        setencours.setFauteDJ2();
    }
}
