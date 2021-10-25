package fr.android.projet;

public class Jeu {
    private int id;
    private int serveur;
    private int scorej1;
    private int scorej2;
    private int PremierserviceJ1;
    private int DeuxiemeserviceJ1;
    private int aceJ1;
    private int DoublefauteJ1;
    private int PointgagnantJ1;
    private int FautePJ1;
    private int FauteDJ1;
    private int PremierserviceJ2;
    private int DeuxiemeserviceJ2;
    private int aceJ2;
    private int DoublefauteJ2;
    private int PointgagnantJ2;
    private int FautePJ2;
    private int FauteDJ2;

    public Jeu(int id){
        this.id=id;
        scorej1=0;
        scorej2=0;
        PremierserviceJ1=0;DeuxiemeserviceJ1=0;aceJ1=0; DoublefauteJ1=0;PointgagnantJ1=0;FautePJ1=0;FauteDJ1=0;
        PremierserviceJ2=0;DeuxiemeserviceJ2=0;aceJ2=0;DoublefauteJ2=0;PointgagnantJ2=0;FautePJ2=0;FauteDJ2=0;
    }
    public Jeu(int id, int PremierserviceJ1, int DeuxiemeserviceJ1, int aceJ1, int DoublefauteJ1, int PointgagnantJ1, int FautePJ1, int FauteDJ1, int  PremierserviceJ2, int DeuxiemeserviceJ2, int aceJ2, int DoublefauteJ2, int PointgagnantJ2, int FautePJ2, int FauteDJ2){
        this.id=id;
        this.PremierserviceJ1=PremierserviceJ1;
        this.DeuxiemeserviceJ1=DeuxiemeserviceJ1;
        this.aceJ1=aceJ1;
        this.DoublefauteJ1=DoublefauteJ1;
        this.PointgagnantJ1=PointgagnantJ1;
        this.FautePJ1=FautePJ1;
        this.FauteDJ1=FauteDJ1;
        this.PremierserviceJ2=PremierserviceJ2;
        this.DeuxiemeserviceJ2=DeuxiemeserviceJ2;
        this.aceJ2=aceJ2;
        this.DoublefauteJ2=DoublefauteJ2;
        this.PointgagnantJ2=PointgagnantJ2;
        this.FautePJ2=FautePJ2;
        this.FauteDJ2=FauteDJ2;
    }

    public int getId() { return id;}

    public int getPremierserviceJ1() {
        return PremierserviceJ1;
    }

    public int getDeuxiemeserviceJ1() {
        return DeuxiemeserviceJ1;
    }

    public int getAceJ1() {
        return aceJ1;
    }

    public int getDoublefauteJ1() {
        return DoublefauteJ1;
    }

    public int getPointgagnantJ1() {
        return PointgagnantJ1;
    }

    public int getFautePJ1() {
        return FautePJ1;
    }

    public int getFauteDJ1() {
        return FauteDJ1;
    }

    public int getPremierserviceJ2() {
        return PremierserviceJ2;
    }

    public int getDeuxiemeserviceJ2() {
        return DeuxiemeserviceJ2;
    }

    public int getAceJ2() {
        return aceJ2;
    }

    public int getDoublefauteJ2() {
        return DoublefauteJ2;
    }

    public int getPointgagnantJ2() {
        return PointgagnantJ2;
    }

    public int getFautePJ2() {
        return FautePJ2;
    }

    public int getFauteDJ2() {
        return FauteDJ2;
    }

    public int getScorej1(){return scorej1;}
    public int getScorej2(){return scorej2;}
    //score
    public int scorej1(){
        if(scorej1==0) {
            scorej1 = 15;return 0;
        }
        if(scorej1==15) {
            scorej1 = 30;return 0;
        }
        if(scorej1==30) {
            scorej1 = 40;return 0;
        }
        if(scorej1==40 && scorej2<=30)//fin de jeu
        {
            scorej1 = 70;return 0;
        }
        if(scorej1==50 && scorej2==40)//fin de jeu
        {
            scorej1 = 70;return 0;
        }
        if(scorej1==40 && scorej2==40)//avantage
        {
            scorej1 = 50;return 0;
        }
        if(scorej1==40 && scorej2==50)//egalité
        {
            scorej2 = 40;return 0;
        }
        return 0;
    }

    public int scorej2(){
        if(scorej2==0) {
            scorej2 = 15;return 0;
        }
        if(scorej2==15) {
            scorej2 = 30;return 0;
        }
        if(scorej2==30) {
            scorej2 = 40;return 0;
        }
        if(scorej2==40 && scorej1<=30)//fin de jeu
        {
            scorej2 = 70;return 0;
        }
        if(scorej2==50 && scorej1==40)//fin de jeu
        {
            scorej2 = 70;return 0;
        }
        if(scorej2==40 && scorej1==40)//avantage
        {
            scorej2 = 50;return 0;
        }
        if(scorej2==40 && scorej1==50)//egalité
        {
            scorej1 = 40;return 0;
        }
        return 0;
    }

    //Joueur 1
    public void setPremierserviceJ1(){
        PremierserviceJ1+=1;
    }
    public void setDeuxiemeserviceJ1(){
        DeuxiemeserviceJ1+=1;
    }
    public void setAceJ1(){
        aceJ1+=1;
        scorej1();
    }
    public void setDoublefauteJ1(){
        DoublefauteJ1+=1;
        scorej2();
    }
    public void setPointgagnantJ1(){
        PointgagnantJ1+=1;
        scorej1();
    }
    public void setFautePJ1(){
        FautePJ1+=1;
        scorej2();
    }
    public void setFauteDJ1(){
        FauteDJ1+=1;
        scorej2();
    }

    //Joueur 2
    public void setPremierserviceJ2(){
        PremierserviceJ2+=1;
    }
    public void setDeuxiemeserviceJ2(){
        DeuxiemeserviceJ2+=1;
    }
    public void setAceJ2(){
        aceJ2+=1;
        scorej2();
    }
    public void setDoublefauteJ2(){
        DoublefauteJ2+=1;
        scorej1();
    }
    public void setPointgagnantJ2(){
        PointgagnantJ2+=1;
        scorej2();
    }
    public void setFautePJ2(){
        FautePJ2+=1;
        scorej1();
    }
    public void setFauteDJ2(){
        FauteDJ2+=1;
        scorej1();
    }
}
