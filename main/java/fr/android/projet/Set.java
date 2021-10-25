package fr.android.projet;

import java.util.ArrayList;

public class Set {
    private int id;
    ArrayList<Jeu> jeux=new ArrayList<>();
    private int scorej1;
    private int scorej2;
    private Jeu jeuencours;

    public Set(int id){
        this.id=id;
        int i=jeux.size();
        jeuencours = new Jeu(i);
        scorej1=0;
        scorej2=0;
    }

    public Set(int id,ArrayList<Jeu> jeux, int scorej1, int scorej2){
        this.id=id;
        this.jeux=jeux;
        this.scorej1=scorej1;
        this.scorej2=scorej2;
    }

    public ArrayList<Jeu> getJeux() {
        return jeux;
    }

    public int getId() { return id;}
    public int getScoreJ1(){return scorej1;}
    public int getScoreJ2(){return scorej2;}
    public int getScoreJeuJ1(){return jeuencours.getScorej1();}
    public int getScoreJeuJ2(){return jeuencours.getScorej2();}
    public int getNumJeu(){return jeux.size();}

    public void findejeu(){
        int s1=jeuencours.getScorej1();
        int s2=jeuencours.getScorej2();
        boolean b=false;
        if(s1==70){
            scorej1+=1;b=true;
        }
        if(s2==70){
            scorej2+=1;b=true;
        }
        if(b){
            jeux.add(jeuencours);
            int i=jeux.size();
            jeuencours=new Jeu(i);
        }
    }


    //Joueur 1
    public void setPremierserviceJ1(){
        jeuencours.setPremierserviceJ1();
    }
    public void setDeuxiemeserviceJ1(){
        jeuencours.setDeuxiemeserviceJ1();
    }
    public void setAceJ1(){
        jeuencours.setAceJ1();
        findejeu();
    }
    public void setDoublefauteJ1(){
        jeuencours.setDoublefauteJ1();
        findejeu();
    }
    public void setPointgagnantJ1(){
        jeuencours.setPointgagnantJ1();
        findejeu();
    }
    public void setFautePJ1(){
        jeuencours.setFautePJ1();
        findejeu();
    }
    public void setFauteDJ1(){
        jeuencours.setFauteDJ1();
        findejeu();
    }

    //Joueur 2
    public void setPremierserviceJ2(){
        jeuencours.setPremierserviceJ2();
        findejeu();
    }
    public void setDeuxiemeserviceJ2(){
        jeuencours.setDeuxiemeserviceJ2();
        findejeu();
    }
    public void setAceJ2(){
        jeuencours.setAceJ2();
        findejeu();
    }
    public void setDoublefauteJ2(){
        jeuencours.setDoublefauteJ2();
        findejeu();
    }
    public void setPointgagnantJ2(){
        jeuencours.setPointgagnantJ2();
        findejeu();
    }
    public void setFautePJ2(){
        jeuencours.setFautePJ2();
        findejeu();
    }
    public void setFauteDJ2(){
        jeuencours.setFauteDJ2();
        findejeu();
    }
}
