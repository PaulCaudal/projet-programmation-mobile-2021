import java.io.DataOutputStream;
import java.io.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Serveur implements Runnable{

    private Socket sock;

    public Serveur(Socket s) {
        sock = s;
    }

    @Override
    public void run() {

        try {

            final BufferedReader in;
            final PrintWriter out;
            DataInputStream dis = new DataInputStream(sock.getInputStream());
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
            out = new PrintWriter(sock.getOutputStream());
            in = new BufferedReader (new InputStreamReader(sock.getInputStream()));
            int ordre =Integer.parseInt(in.readLine());
            if(ordre==1) {
                int id = Integer.parseInt(in.readLine());
                String j1 = in.readLine();
                String j2 = in.readLine();
                String Date = in.readLine();
                String loc =in.readLine();
                int w = Integer.parseInt(in.readLine());
                Match match = new Match(id,Date,j1,j2,w,loc);
                int nbset=Integer.parseInt(in.readLine());
                ArrayList<Set>sets=new ArrayList<>();
                for(int i=0;i<nbset;i++){
                    int ids=Integer.parseInt(in.readLine());
                    int scorej1=Integer.parseInt(in.readLine());
                    int scorej2=Integer.parseInt(in.readLine());
                    int nbjeu=Integer.parseInt(in.readLine());
                    ArrayList<Jeu> jeux = new ArrayList<>();
                    for(int j=0; j<nbjeu;j++){
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
                int nburi=Integer.parseInt(in.readLine());
                String uri;
                ArrayList<String> uris=new ArrayList<>();
                for(int k=0;k<nburi;k++){
                    uri=in.readLine();
                    uris.add(uri);
                }
                match.setSets(sets);
                match.setUris(uris);
                model.savematch(match);
            }
            if(ordre==2){
                ArrayList<Match> matches = model.getallmatch();
                out.println(""+matches.size());
                for(int k=0;k<matches.size();k++) {
                    Match match=matches.get(k);
                    out.println(""+match.getId());
                    out.println(match.getJ1());
                    out.println(match.getJ2());
                    out.println(match.getDate());
                    out.println(match.getLoc());
                    out.println("" + match.getW());
                    ArrayList<Set> sets = match.getSets();
                    out.println("" + sets.size());
                    System.out.println(sets.size());
                    for (int i = 0; i < sets.size(); i++) {
                        out.println("" + sets.get(i).getId());
                        out.println("" + sets.get(i).getScoreJ1());
                        out.println("" + sets.get(i).getScoreJ2());
                        ArrayList<Jeu> jeux = sets.get(i).getJeux();
                        System.out.println(jeux.size());
                        out.println("" + jeux.size());
                        for (int j = 0; j < jeux.size(); j++) {
                            out.println("" + jeux.get(j).getId());
                            out.println("" + jeux.get(j).getPremierserviceJ1());
                            out.println("" + jeux.get(j).getDeuxiemeserviceJ1());
                            out.println("" + jeux.get(j).getAceJ1());
                            out.println("" + jeux.get(j).getDoublefauteJ1());
                            out.println("" + jeux.get(j).getPointgagnantJ1());
                            out.println("" + jeux.get(j).getFautePJ1());
                            out.println("" + jeux.get(j).getFauteDJ1());

                            out.println("" + jeux.get(j).getPremierserviceJ2());
                            out.println("" + jeux.get(j).getDeuxiemeserviceJ2());
                            out.println("" + jeux.get(j).getAceJ2());
                            out.println("" + jeux.get(j).getDoublefauteJ2());
                            out.println("" + jeux.get(j).getPointgagnantJ2());
                            out.println("" + jeux.get(j).getFautePJ2());
                            out.println("" + jeux.get(j).getFauteDJ2());
                        }
                    }
                    ArrayList<String> uris=match.getUris();
                    out.println(""+uris.size());
                    for(int h=0;h<uris.size();h++){
                        out.println(uris.get(h));
                    }
                }

                out.flush();
            }
            out.close();
            in.close();
            sock.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
