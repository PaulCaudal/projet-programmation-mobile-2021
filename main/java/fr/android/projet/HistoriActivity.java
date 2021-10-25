package fr.android.projet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class HistoriActivity extends AppCompatActivity {

    LinearLayout l;
    boolean fr;
    GridLayout g;
    TextView t1, t2;
    ImageView im;
    ArrayList<Match> matchs;
    Match matchencours;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori);
        AccessLocal accessLocal= new AccessLocal(getApplicationContext());
        fr=getIntent().getBooleanExtra("len",true);
        System.out.println(fr);
        Thread t =new Thread() {
            @Override
            public void run() {
                matchs=accessLocal.verif();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        l= findViewById(R.id.linescroll);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        if(!fr){
            TextView th = findViewById(R.id.textViewPlayervs);
            th.setText("History");
        }

        for(int i=0;i<matchs.size();i++){
            g=new GridLayout(this);
            g.setRowCount(2);
            g.setColumnCount(2);
            GridLayout.LayoutParams pgt1 = new GridLayout.LayoutParams(GridLayout.spec(0), GridLayout.spec(0));
            GridLayout.LayoutParams pgt2 = new GridLayout.LayoutParams(GridLayout.spec(1), GridLayout.spec(0));
            GridLayout.LayoutParams pgim = new GridLayout.LayoutParams(GridLayout.spec(0,2), GridLayout.spec(1));
            t1 = new TextView(this);
            t2 = new TextView(this);
            im = new ImageView(this);

            int c = Color.parseColor("#D3D1CF");
            g.setBackgroundColor(c);

            t1.setText(matchs.get(i).getJ1()+" vs "+matchs.get(i).getJ2());
            String newdate;
            if(fr)
                 newdate= refodate(matchs.get(i).getDate());
            else
                newdate= refodateen(matchs.get(i).getDate());
            t2.setText(newdate);
            t1.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);
            String uri ="@android:drawable/ic_media_play";
            int imgres= getResources().getIdentifier(uri,null,getPackageName());
            Drawable d = getResources().getDrawable(imgres);
            im.setImageDrawable(d);
            int finalI = i;
            int finalI1 = i;
            g.setOnClickListener(v-> {
                        try {
                            myClickHandler(v, finalI1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            t1.setTextAppearance(this, android.R.style.TextAppearance_Large);
            t2.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            g.setId(matchs.get(i).getId());
            pgim.setGravity(Gravity.RIGHT);
            pgim.width=160;
            pgim.height=160;
            g.addView(t1,pgt1);
            g.addView(t2,pgt2);
            g.addView(im,pgim);
            p.setMargins(0,25,0,0);
            l.addView(g,p);
        }
    }

    public void myClickHandler(View v,int i) throws IOException{
        matchencours=matchs.get(i);
        setContentView(R.layout.historique);
        inithisto(v);
        AccessLocal accessLocal= new AccessLocal(getApplicationContext());
        if(accessLocal.veriflocal(matchencours.getId())) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    ArrayList<Set> sets = accessLocal.recupset(matchencours.getId());
                    ArrayList<Uri> uris=accessLocal.recupuri(matchencours.getId());
                    matchencours.setImages_uri(uris);
                    matchencours.setSets(sets);
                    inithistomatch(v);
                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            inithistomatch(v);
        }
        if(fr==false)
            inithistolenen(v);
    }

    public void inithistolenen(View v){
        TextView sg=findViewById(R.id.textViewText1);
        TextView pga=findViewById(R.id.textViewText4);
        TextView ss=findViewById(R.id.textViewText7);
        TextView ps=findViewById(R.id.textViewTextse1);
        TextView ds=findViewById(R.id.textViewTextse4);
        TextView df=findViewById(R.id.textViewTextse10);
        TextView se=findViewById(R.id.textViewText10);
        TextView pg=findViewById(R.id.textViewTexteh1);
        TextView fp=findViewById(R.id.textViewTexteh4);
        TextView fd=findViewById(R.id.textViewTexteh7);

        sg.setText("General statistics");
        pga.setText("Winning point");
        ss.setText("Service statistic");
        ps.setText("First service");
        ds.setText("Second service");
        df.setText("Double fault");
        se.setText("Exchange statistique");
        pg.setText("Point won");
        fp.setText("Fault caused");
        fd.setText("Direct fault");
        v.refreshDrawableState();
    }

    public void myClickHandlerb(View v) throws IOException{
        switch (v.getId()){
            case R.id.backButtonhisto:
                Intent i = new Intent(HistoriActivity.this,MainActivity.class);
                i.putExtra("len",fr);
                HistoriActivity.this.startActivity(i);
                break;

            case R.id.backButtonhisto2:
                Intent i2 = new Intent(HistoriActivity.this,HistoriActivity.class);
                i2.putExtra("len",fr);
                HistoriActivity.this.startActivity(i2);
            break;

            case R.id.backButtonhisto3:
                setContentView(R.layout.historique);
                inithisto(v);
                inithistomatch(v);
                if(fr==false)
                    inithistolenen(v);
                break;
         }
    }

    public void myClickHandler2(View v) throws IOException{
        int g = Color.parseColor("#9A9696");
        int w = Color.parseColor("#FFFFFF");
        Button bm = findViewById(R.id.Button1);
        Button bs1 = findViewById(R.id.Button2);
        Button bs2 = findViewById(R.id.Button3);
        switch (v.getId()){
            case R.id.Button1:
                bm.setBackgroundColor(g);
                bs1.setBackgroundColor(w);
                bs2.setBackgroundColor(w);
                inithistomatch(v);
                break;
            case R.id.Button2:
                bs1.setBackgroundColor(g);
                bm.setBackgroundColor(w);
                bs2.setBackgroundColor(w);
                initset(v,0);
                break;
            case R.id.Button3:
                bs2.setBackgroundColor(g);
                bs1.setBackgroundColor(w);
                bm.setBackgroundColor(w);
                initset(v,1);
                break;

        }
    }

    public void inithisto(View view){
        setContentView(R.layout.historique);
        TextView th = findViewById(R.id.textViewPlayers);
        th.setText(matchencours.getJ1()+" vs "+matchencours.getJ2());
        TextView t1j1 = findViewById(R.id.textJ1);
        TextView t1j2 = findViewById(R.id.textJ2);
        t1j1.setText(matchencours.getJ1());
        t1j2.setText(matchencours.getJ2());
        TextView t2j1 = findViewById(R.id.textViewText2);
        TextView t2j2 = findViewById(R.id.textViewText3);
        t2j1.setText(matchencours.getJ1());
        t2j2.setText(matchencours.getJ2());
        TextView t3j1 = findViewById(R.id.textViewText8);
        TextView t3j2 = findViewById(R.id.textViewText9);
        t3j1.setText(matchencours.getJ1());
        t3j2.setText(matchencours.getJ2());
        TextView t4j1 = findViewById(R.id.textViewText11);
        TextView t4j2 = findViewById(R.id.textViewText12);
        t4j1.setText(matchencours.getJ1());
        t4j2.setText(matchencours.getJ2());
    }

    public void inithistomatch(View v){
        if(matchencours.getW()==1) {
            TextView t =findViewById(R.id.textserveurJ1);
            t.setText("•");
        }
        if(matchencours.getW()==1) {
            TextView t =findViewById(R.id.textserveurJ2);
            t.setText(" ");
        }
        int s= matchencours.sets.size();
        if(s>=2){
            TextView t1j1 = findViewById(R.id.textSet1J1);
            TextView t1j2 = findViewById(R.id.textSet2J1);
            t1j1.setText(""+matchencours.sets.get(0).getScoreJ1());
            t1j2.setText(""+matchencours.sets.get(0).getScoreJ2());
            TextView t2j1 = findViewById(R.id.textSet2J1);
            TextView t2j2 = findViewById(R.id.textSet2J2);
            t2j1.setText(""+matchencours.sets.get(1).getScoreJ1());
            t2j2.setText(""+matchencours.sets.get(1).getScoreJ2());
        }
        if(s==3){
            TextView t3j1 = findViewById(R.id.textSet2J1);
            TextView t3j2 = findViewById(R.id.textSet2J2);
            t3j1.setText(""+matchencours.sets.get(2).getScoreJ1());
            t3j2.setText(""+matchencours.sets.get(2).getScoreJ2());
        }

        int pgaj1=0,pgaj2=0;
        float psj1=0,psj2=0, dsj1=0, dsj2=0;
        int PremierserviceJ1=0,DeuxiemeserviceJ1=0, aceJ1=0, DoublefauteJ1=0, PointgagnantJ1=0,FautePJ1=0,FauteDJ1=0;
        int PremierserviceJ2=0, DeuxiemeserviceJ2=0,aceJ2=0,DoublefauteJ2=0,PointgagnantJ2=0,FautePJ2=0,FauteDJ2=0;
        for(int i =0; i< matchencours.sets.size();i++){
            Set set =matchencours.sets.get(i);
            for(int j=0;j<set.jeux.size();j++)
            {
                Jeu jeu= set.jeux.get(j);
                PremierserviceJ1+=jeu.getPremierserviceJ1();
                DeuxiemeserviceJ1+=jeu.getDeuxiemeserviceJ1();
                aceJ1+=jeu.getAceJ1();
                DoublefauteJ1+=jeu.getDoublefauteJ1();
                PointgagnantJ1+= jeu.getPointgagnantJ1();
                FautePJ1+=jeu.getFautePJ1();
                FauteDJ1+=jeu.getFauteDJ1();
                PremierserviceJ2+=jeu.getPremierserviceJ2();
                DeuxiemeserviceJ2+=jeu.getDeuxiemeserviceJ2();
                aceJ2+=jeu.getAceJ2();
                DoublefauteJ2+=jeu.getDoublefauteJ2();
                PointgagnantJ2+=jeu.getPointgagnantJ2();
                FautePJ2+=jeu.getFautePJ2();
                FauteDJ2+=jeu.getFauteDJ2();
            }
        }
        pgaj1=aceJ1+PointgagnantJ1+DoublefauteJ2+FautePJ2+FauteDJ2;
        pgaj2=aceJ2+PointgagnantJ2+DoublefauteJ1+FautePJ1+FauteDJ1;
        if((PremierserviceJ1+DeuxiemeserviceJ1+DoublefauteJ1+aceJ1)!=0) {
             psj1 = (float) ( (1.0*(PremierserviceJ1 + aceJ1))/ (1.0*(PremierserviceJ1 + DeuxiemeserviceJ1 + DoublefauteJ1 + aceJ1))*100);
        }
        if((PremierserviceJ2+DeuxiemeserviceJ2+DoublefauteJ2+aceJ2)!=0) {
            psj2 = (float)(( 1.0*(PremierserviceJ2 + aceJ2))/(1.0*(PremierserviceJ2 + DeuxiemeserviceJ2 + DoublefauteJ2 + aceJ2))*100 ) ;
        }
        if((DeuxiemeserviceJ1+DoublefauteJ1)!=0) {
            dsj1 = (float)(1.0*DeuxiemeserviceJ1 / (1.0*(DeuxiemeserviceJ1 + DoublefauteJ1))*100);
        }
        if((DeuxiemeserviceJ2+DoublefauteJ2)!=0) {
            dsj2 = (float)(1.0*DeuxiemeserviceJ2 / (1.0*(DeuxiemeserviceJ2 + DoublefauteJ2))*100);
        }
        //Point Gagnant
        TextView dpgaj1 = findViewById(R.id.textViewText5);
        TextView dpgaj2 = findViewById(R.id.textViewText6);
        dpgaj1.setText(""+pgaj1);
        dpgaj2.setText(""+pgaj2);

        //Premier service
        TextView dpsj1 = findViewById(R.id.textViewTextse2);
        TextView dpsj2 = findViewById(R.id.textViewTextse3);
        dpsj1.setText(psj1+"%");
        dpsj2.setText(psj2+"%");

        //Deuxieme serviec
        TextView ddsj1 = findViewById(R.id.textViewTextse5);
        TextView ddsj2 = findViewById(R.id.textViewTextse6);
        ddsj1.setText(dsj1+"%");
        ddsj2.setText(dsj2+"%");

        //Ace
        TextView dacej1 = findViewById(R.id.textViewTextse8);
        TextView dacej2 = findViewById(R.id.textViewTextse9);
        dacej1.setText(""+aceJ1);
        dacej2.setText(""+aceJ2);

        //Double faute
        TextView ddfj1 = findViewById(R.id.textViewTextse11);
        TextView ddfj2 = findViewById(R.id.textViewTextse12);
        ddfj1.setText(""+DoublefauteJ1);
        ddfj2.setText(""+DoublefauteJ2);

        //Point Gagnant
        TextView dpgj1 = findViewById(R.id.textViewTexteh2);
        TextView dpgj2 = findViewById(R.id.textViewTexteh3);
        dpgj1.setText(""+PointgagnantJ1);
        dpgj2.setText(""+PointgagnantJ2);

        //Faute P
        TextView dfpj1 = findViewById(R.id.textViewTexteh5);
        TextView dfpj2 = findViewById(R.id.textViewTexteh6);
        dfpj1.setText(""+FautePJ1);
        dfpj2.setText(""+FautePJ2);

        //Faute D
        TextView dfdj1 = findViewById(R.id.textViewTexteh8);
        TextView dfdj2 = findViewById(R.id.textViewTexteh9);
        dfdj1.setText(""+FauteDJ1);
        dfdj2.setText(""+FauteDJ2);

    }

    public void initset(View v, int idset){
        int pgaj1=0,pgaj2=0;
        float psj1=0,psj2=0, dsj1=0, dsj2=0;
        int PremierserviceJ1=0,DeuxiemeserviceJ1=0, aceJ1=0, DoublefauteJ1=0, PointgagnantJ1=0,FautePJ1=0,FauteDJ1=0;
        int PremierserviceJ2=0, DeuxiemeserviceJ2=0,aceJ2=0,DoublefauteJ2=0,PointgagnantJ2=0,FautePJ2=0,FauteDJ2=0;
        Set set =matchencours.sets.get(idset);
        for(int j=0;j<set.jeux.size();j++)
        {
            Jeu jeu= set.jeux.get(j);
            PremierserviceJ1+=jeu.getPremierserviceJ1();
            DeuxiemeserviceJ1+=jeu.getDeuxiemeserviceJ1();
            aceJ1+=jeu.getAceJ1();
            DoublefauteJ1+=jeu.getDoublefauteJ1();
            PointgagnantJ1+= jeu.getPointgagnantJ1();
            FautePJ1+=jeu.getFautePJ1();
            FauteDJ1+=jeu.getFauteDJ1();
            PremierserviceJ2+=jeu.getPremierserviceJ2();
            DeuxiemeserviceJ2+=jeu.getDeuxiemeserviceJ2();
            aceJ2+=jeu.getAceJ2();
            DoublefauteJ2+=jeu.getDoublefauteJ2();
            PointgagnantJ2+=jeu.getPointgagnantJ2();
            FautePJ2+=jeu.getFautePJ2();
            FauteDJ2+=jeu.getFauteDJ2();
        }
        pgaj1=aceJ1+PointgagnantJ1+DoublefauteJ2+FautePJ2+FauteDJ2;
        pgaj2=aceJ2+PointgagnantJ2+DoublefauteJ1+FautePJ1+FauteDJ1;
        if((PremierserviceJ1+DeuxiemeserviceJ1+DoublefauteJ1+aceJ1)!=0) {
            psj1 = (float) ( (1.0*(PremierserviceJ1 + aceJ1))/ (1.0*(PremierserviceJ1 + DeuxiemeserviceJ1 + DoublefauteJ1 + aceJ1))*100);
        }
        if((PremierserviceJ2+DeuxiemeserviceJ2+DoublefauteJ2+aceJ2)!=0) {
            psj2 = (float)(( 1.0*(PremierserviceJ2 + aceJ2))/(1.0*(PremierserviceJ2 + DeuxiemeserviceJ2 + DoublefauteJ2 + aceJ2))*100 ) ;
        }
        if((DeuxiemeserviceJ1+DoublefauteJ1)!=0) {
            dsj1 = (float)(1.0*DeuxiemeserviceJ1 / (1.0*(DeuxiemeserviceJ1 + DoublefauteJ1))*100);
        }
        if((DeuxiemeserviceJ2+DoublefauteJ2)!=0) {
            dsj2 = (float)(1.0*DeuxiemeserviceJ2 / (1.0*(DeuxiemeserviceJ2 + DoublefauteJ2))*100);
        }
        //Point Gagnant
        TextView dpgaj1 = findViewById(R.id.textViewText5);
        TextView dpgaj2 = findViewById(R.id.textViewText6);
        dpgaj1.setText(""+pgaj1);
        dpgaj2.setText(""+pgaj2);

        //Premier service
        TextView dpsj1 = findViewById(R.id.textViewTextse2);
        TextView dpsj2 = findViewById(R.id.textViewTextse3);
        dpsj1.setText(psj1+"%");
        dpsj2.setText(psj2+"%");

        //Deuxieme serviec
        TextView ddsj1 = findViewById(R.id.textViewTextse5);
        TextView ddsj2 = findViewById(R.id.textViewTextse6);
        ddsj1.setText(dsj1+"%");
        ddsj2.setText(dsj2+"%");

        //Ace
        TextView dacej1 = findViewById(R.id.textViewTextse8);
        TextView dacej2 = findViewById(R.id.textViewTextse9);
        dacej1.setText(""+aceJ1);
        dacej2.setText(""+aceJ2);

        //Double faute
        TextView ddfj1 = findViewById(R.id.textViewTextse11);
        TextView ddfj2 = findViewById(R.id.textViewTextse12);
        ddfj1.setText(""+DoublefauteJ1);
        ddfj2.setText(""+DoublefauteJ2);

        //Point Gagnant
        TextView dpgj1 = findViewById(R.id.textViewTexteh2);
        TextView dpgj2 = findViewById(R.id.textViewTexteh3);
        dpgj1.setText(""+PointgagnantJ1);
        dpgj2.setText(""+PointgagnantJ2);

        //Faute P
        TextView dfpj1 = findViewById(R.id.textViewTexteh5);
        TextView dfpj2 = findViewById(R.id.textViewTexteh6);
        dfpj1.setText(""+FautePJ1);
        dfpj2.setText(""+FautePJ2);

        //Faute D
        TextView dfdj1 = findViewById(R.id.textViewTexteh8);
        TextView dfdj2 = findViewById(R.id.textViewTexteh9);
        dfdj1.setText(""+FauteDJ1);
        dfdj2.setText(""+FauteDJ2);
    }

    public void myClickHandlerphoto(View v) throws IOException{

        setContentView(R.layout.photo);
        LinearLayout ly= findViewById(R.id.Layoutpic);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        ArrayList<Uri> uris = matchencours.images_uri;
        int size;
        if(uris.size()<3)
            size=uris.size();
        else
            size=uris.size()/3+1;
        System.out.println(size);
        int posx=0,posy=0;
        g=new GridLayout(this);

        g.setRowCount(size);
        g.setColumnCount(3);
        for(int i=0;i<uris.size();i++){
            GridLayout.LayoutParams pg = new GridLayout.LayoutParams(GridLayout.spec(posx), GridLayout.spec(posy));
            ImageView img = new ImageView(this);

            img.setImageURI(uris.get(i));
            pg.setGravity(Gravity.CENTER);
            pg.width=360;
            pg.height=360;
            pg.setMargins(10,25,0,0);
            g.addView(img,pg);
            posy+=1;
            if(posy==3)
            {
                posx+=1;posy=0;
            }
        }
        p.setMargins(0,15,0,0);
        ly.addView(g,p);
    }


        public String refodate(String olddate){
        String newdate="";
        String[] datesplite=olddate.split("\\s");
        //Jours
        if(datesplite[0].equals("Mon"))
            newdate+="Lundi "+datesplite[2];
        if(datesplite[0].equals("Tue"))
            newdate+="Mardi "+datesplite[2];
        if(datesplite[0].equals("Wed"))
            newdate+="Mercredi "+datesplite[2];
        if(datesplite[0].equals("Thu"))
            newdate+="Jeudi "+datesplite[2];
        if(datesplite[0].equals("Fri"))
            newdate+="Vendredi "+datesplite[2];
        if(datesplite[0].equals("Sat"))
            newdate+="Samedi "+datesplite[2];
        if(datesplite[0].equals("Sun"))
            newdate+="Dimanche "+datesplite[2];


        if(datesplite[1].equals("Mar"))
            newdate+=" Mars "+datesplite[5];
        String[] hsplite=datesplite[3].split(":");
        newdate+=" "+hsplite[0]+":"+hsplite[1];
        return newdate;
    }

    public String refodateen(String olddate){
        String newdate="";
        String[] datesplite=olddate.split("\\s");
        //Jours
        if(datesplite[0].equals("Mon"))
            newdate+="Monday "+datesplite[2];
        if(datesplite[0].equals("Tue"))
            newdate+="Tuesday "+datesplite[2];
        if(datesplite[0].equals("Wed"))
            newdate+="Wednesday "+datesplite[2];
        if(datesplite[0].equals("Thu"))
            newdate+="Thursday "+datesplite[2];
        if(datesplite[0].equals("Fri"))
            newdate+="Friday "+datesplite[2];
        if(datesplite[0].equals("Sat"))
            newdate+="Saturday "+datesplite[2];
        if(datesplite[0].equals("Sun"))
            newdate+="Sunday "+datesplite[2];


        if(datesplite[1].equals("Mar"))
            newdate+=" March "+datesplite[5];
        String[] hsplite=datesplite[3].split(":");
        newdate+=" "+hsplite[0]+":"+hsplite[1];
        return newdate;
    }
}