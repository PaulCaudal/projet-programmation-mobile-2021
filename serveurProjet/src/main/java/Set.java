import java.util.ArrayList;

public class Set {
    private int id;
    ArrayList<Jeu> jeux=new ArrayList<>();
    private int scorej1;
    private int scorej2;
    private Jeu jeuencours;

    public Set(int id,ArrayList<Jeu> jeux, int scorej1, int scorej2){
        this.id=id;
        this.jeux=jeux;
        this.scorej1=scorej1;
        this.scorej2=scorej2;
    }

    public ArrayList<Jeu> getJeux() {
        return jeux;
    }

    public int getId() {
        return id;
    }

    public int getScoreJ1(){return scorej1;}
    public int getScoreJ2(){return scorej2;}
    public int getNumJeu(){return jeux.size();}


}
