package fr.android.projet;

public class Joueur {
    private int Premierservice=0;
    private int Deuxiemeservice=0;
    private int ace=0;
    private int Doublefaute=0;
    private int Pointgagnant=0;
    private int FauteP=0;
    private int FauteD=0;

    public void setPremierservice(){
        Premierservice+=1;
    }
    public void setDeuxiemeservice(){
        Deuxiemeservice+=1;
    }
    public void setAce(){
        ace+=1;
    }
    public void setDoublefaute(){
        Doublefaute+=1;
    }
    public void setPointgagnant(){
        Pointgagnant+=1;
    }
    public void setFauteP(){
        FauteP+=1;
    }
    public void setFauteD(){
        FauteD+=1;
    }
}
