public class Jeu {
    private int id;
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

    public int getId() {
        return id;
    }

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

}
