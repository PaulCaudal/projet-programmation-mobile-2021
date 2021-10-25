import java.util.ArrayList;


public class Match {
    ArrayList<Set> sets=new ArrayList<>();
    ArrayList<String>uris=new ArrayList<>();
    private Set setencours;
    private int scoreJ1=0;
    private int scoreJ2=0;
    private int w;
    private int id;
    private String j1;
    private String j2;
    private String Date;
    private String loc;
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
    public void setUris(ArrayList<String> uris) {
        this.uris = uris;
    }

    public ArrayList<String> getUris() {
        return uris;
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


}
