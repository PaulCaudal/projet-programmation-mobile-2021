package fr.android.projet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private String provider;
    private int i=0;
    private Match match;
    private int serveur=0;
    private String nomjoueur1;
    private String nomjoueur2;
    private MainActivity a=this;
    private AccessLocal accessLocal;
    private boolean fr=true;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fr=getIntent().getBooleanExtra("len",true);
        if(fr) {
            RadioButton s = findViewById(R.id.radiolang2);
            s.setChecked(true);

        }
        else{
            RadioButton s = findViewById(R.id.radiolang);
            s.setChecked(true);
            initlangmainen(this.getCurrentFocus());
        }
        locationRequest= new LocationRequest();
        locationRequest.setInterval(1000*30);
        locationRequest.setFastestInterval(1000*5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void myClickHandler(View view) throws IOException {
        switch (view.getId()){
            case R.id.button1:
                setContentView(R.layout.acceuil);
                if(!fr)
                    initlangacceuilen(view);

                break;
            case R.id.button2:

                Intent intent = new Intent(MainActivity.this, HistoriActivity.class);
                intent.putExtra("len",fr);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.imageButton3:
                /*EditText t = findViewById(R.id.editTextTextPostalAddress);
                Criteria criteres = new Criteria();
                LocationManager l=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                criteres.setAccuracy(Criteria.ACCURACY_FINE);
                String fournisseur = l.getBestProvider(criteres, true);
                System.out.println(fournisseur+"                   bbbbbbbbbbbbbbbbbbbbbbbb");*/
                updateGps();

                break;
            case R.id.backButton:
                setContentView(R.layout.activity_main);
                RadioButton en =findViewById(R.id.radiolang);
                RadioButton fra =findViewById(R.id.radiolang2);
                if(!fr) {
                    fr = false;
                    fra.setChecked(false);
                    en.setChecked(true);
                    initlangmainen(view);
                }
                if(fr) {
                    fr = true;
                    fra.setChecked(true);
                    en.setChecked(false);
                    initlangmainfr(view);
                }
                break;
            case R.id.buttondem:
                initmatch(view);
                setContentView(R.layout.match);
                if(!fr) {
                    initlenmatchen(view);
                }
                Chooseserveur Cs=new Chooseserveur(a,view);
                Dialog dialog;
                dialog = Cs.onCreateDialog(new Bundle());
                dialog.show();
                inittext(view);
                match=new Match(serveur);
                break;
            case R.id.radiolang:
                RadioButton a =findViewById(R.id.radiolang);
                RadioButton f =findViewById(R.id.radiolang2);
                if(fr) {
                    fr = false;
                    f.setChecked(false);
                    a.setChecked(true);
                    initlangmainen(view);
                }
                break;
            case R.id.radiolang2:
                RadioButton a2 =findViewById(R.id.radiolang);
                RadioButton f2=findViewById(R.id.radiolang2);
                if(!fr) {
                    fr = true;
                    f2.setChecked(true);
                    a2.setChecked(false);
                    initlangmainfr(view);
                }
                break;
        }
    }

    public void updateGps(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateUIValues(location);
                }
            });

        }
        else{

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99);
            }
        }
    }

    private void updateUIValues(Location location) {
        System.out.println(location.getLatitude());
        System.out.println(location.getLongitude());

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        TextView t =findViewById(R.id.editTextTextPostalAddress);
        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            t.setText(addresses.get(0).getAddressLine(0));
            match.setLoc(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
            t.setText("indisponible");
            match.setLoc("indisponible");
        }
    }


    public void initlangmainen(View v){
        Button nm=findViewById(R.id.button1);
        Button h=findViewById(R.id.button2);
        nm.setText("New Match");
        h.setText("History");
    }
    public void initlangmainfr(View v){
        Button nm=findViewById(R.id.button1);
        Button h=findViewById(R.id.button2);
        nm.setText("Nouveau Match");
        h.setText("Historique");
    }

    public void initlangacceuilen(View v){
        TextView tv=findViewById(R.id.textViewPlayervs);
        Button b = findViewById(R.id.buttondem);
        EditText j1 = findViewById(R.id.editTextTextPersonName2);
        EditText j2 = findViewById(R.id.editTextTextPersonName);

        tv.setText("New Match");
        b.setText("Start");
        j1.setText("Player 1");
        j2.setText("Player 2");
    }

    public void initlenmatchen(View v){
        TextView ts = findViewById(R.id.textView4);
        Button OK1 = findViewById(R.id.button1OK);
        Button OK2 = findViewById(R.id.button2OK);
        Button DF = findViewById(R.id.buttonDF);

        TextView te = findViewById(R.id.textView3);
        Button PG1 = findViewById(R.id.buttonPG1);
        Button PG2 = findViewById(R.id.buttonPG2);
        Button FP1 = findViewById(R.id.buttonFP1);
        Button FP2 = findViewById(R.id.buttonFP2);
        Button FD1 = findViewById(R.id.buttonFD1);
        Button FD2 = findViewById(R.id.buttonFD2);

        ts.setText("Server");
        OK1.setText("1st OK");
        OK2.setText("2nd OK");
        DF.setText("Double fault");
        te.setText("Exchange");
        PG1.setText("Point Won");
        PG2.setText("Point Won");
        FP1.setText("Fault caused");
        FP2.setText("Fault caused");
        FD1.setText("Direct fault");
        FD2.setText("Direct fault");
    }

    public void initmatch(View view) {
        EditText nomj1= findViewById(R.id.editTextTextPersonName2);
        EditText nomj2= findViewById(R.id.editTextTextPersonName);
        nomjoueur1=nomj1.getText().toString();
        nomjoueur2=nomj2.getText().toString();
    }
    public void inittext(View view){
        TextView joueur1=findViewById(R.id.textJ1);
        TextView joueur2=findViewById(R.id.textJ2);
        TextView InitialJ1=findViewById(R.id.textInit1);
        TextView Initialj2=findViewById(R.id.textInit2);
        joueur1.setText(nomjoueur1);
        joueur2.setText(nomjoueur2);
        String charJ1=nomjoueur1.substring(0,1);
        String charJ2=nomjoueur2.substring(0,1);
        InitialJ1.setText(charJ1);
        Initialj2.setText(charJ2);
    }

    public void setserveur(View view){
        TextView j1=findViewById(R.id.textserveurJ1);
        TextView j2=findViewById(R.id.textserveurJ2);
        TextView sert=findViewById(R.id.textView4);
        if(serveur==1){
            j1.setText("•");
            j2.setText(" ");
            if(fr)
                sert.setText("serveur: "+nomjoueur1);
            else
                sert.setText("server: "+nomjoueur1);
        }
        if(serveur==2){
            j2.setText("•");
            j1.setText(" ");
            if(fr)
                sert.setText("serveur: "+nomjoueur2);
            else
                sert.setText("server: "+nomjoueur2);
        }
    }
    public void myClickHandlermatch(View view) throws IOException{
        int numJeu=match.getNumJeu();
        switch (view.getId()){
            case R.id.button1OK:
                if(serveur==1)
                    match.setPremierserviceJ1();
                if(serveur==2)
                    match.setPremierserviceJ2();
                break;
            case R.id.button1Ace:
                if(serveur==1)
                    match.setAceJ1();
                if(serveur==2)
                    match.setAceJ2();
                break;
            case R.id.button2OK:
                if(serveur==1)
                    match.setDeuxiemeserviceJ1();
                if(serveur==2)
                    match.setDeuxiemeserviceJ2();
                break;
            case R.id.button2Ace:
                if(serveur==1)
                    match.setAceJ1();
                if(serveur==2)
                    match.setAceJ2();
                break;
            case R.id.buttonDF:
                if(serveur==1)
                    match.setDoublefauteJ1();
                if(serveur==2)
                    match.setDoublefauteJ2();
                break;
            case R.id.buttonPG1:
                match.setPointgagnantJ1();
                break;
            case R.id.buttonPG2:
                match.setPointgagnantJ2();
                break;
            case R.id.buttonFP1:
                match.setFautePJ1();
                break;
            case R.id.buttonFP2:
                match.setFautePJ2();
                break;
            case R.id.buttonFD1:
                match.setFauteDJ1();
                break;
            case R.id.buttonFD2:
                match.setFauteDJ2();
                break;
        }
        int scoreJeuJ1 = match.getScoreJeuJ1();
        int scoreJeuJ2 = match.getScoreJeuJ2();
        int scoreSetJ1 = match.getScoreSetJ1();
        int scoreSetJ2 = match.getScoreSetJ2();
        int numSet = match.getNumSet();
        TextView jeuJ1=findViewById(R.id.textJeuJ1);
        TextView jeuJ2=findViewById(R.id.textJeuJ2);
        TextView setJ1 = null,setJ2 = null;
        match.finset();
        if(numSet==1){
            setJ1=findViewById(R.id.textSet1J1);
            setJ2=findViewById(R.id.textSet1J2);
        }
        if(numSet==2){
            setJ1=findViewById(R.id.textSet2J1);
            setJ2=findViewById(R.id.textSet2J2);
        }
        if(numSet==3) {
            setJ1 = findViewById(R.id.textSet3J1);
            setJ2 = findViewById(R.id.textSet3J2);
        }
        if(scoreJeuJ1<=40)
            jeuJ1.setText("" + scoreJeuJ1);
        if(scoreJeuJ1==50)
            jeuJ1.setText("AV");
        if(scoreJeuJ2<=40)
            jeuJ2.setText("" + scoreJeuJ2);
        if(scoreJeuJ2==50)
            jeuJ2.setText("AV");
        setJ1.setText(""+scoreSetJ1);
        setJ2.setText(""+scoreSetJ2);
        int numJeu2=match.getNumJeu();
        if(numJeu!=numJeu2) {
            boolean b = false;
            if (serveur == 1 && !b) {
                serveur = 2;
                b = true;
            }
            if (serveur == 2 && !b) {
                serveur = 1;
                b = true;
            }
            setserveur(view);
        }
        int scoreMatchJ1=match.getScoreJ1();
        int scoreMatchJ2=match.getScoreJ2();
        String loc = match.getLoc();
        if(scoreMatchJ1==2){
            accessLocal = new AccessLocal(getApplicationContext());
            Thread t =new Thread() {
                @Override
                public void run() {
                    accessLocal.ajout(match,nomjoueur1,nomjoueur2,1,2,loc);
                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setContentView(R.layout.activity_main);
            J1WIN Cs=new J1WIN(a);
            Dialog dialog;
            dialog = Cs.onCreateDialog(new Bundle());
            dialog.show();
        }
        if(scoreMatchJ2==2){
            accessLocal = new AccessLocal(getApplicationContext());
            accessLocal.ajout(match,nomjoueur1,nomjoueur2,2,1,loc);
            setContentView(R.layout.activity_main);
            J2WIN Cs=new J2WIN(a);
            Dialog dialog;
            dialog = Cs.onCreateDialog(new Bundle());
            dialog.show();
        }

    }
    private class Chooseserveur extends Dialog {
        private MainActivity a;
        private View v;
        public Chooseserveur(Activity a,View view){
            super(a,R.style.Theme_AppCompat_Dialog);
            this.a= (MainActivity) a;
            v=view;
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(a);
            if(fr) {
                builder.setMessage("Qui sert en premier")
                        .setPositiveButton(nomjoueur2, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                serveur = 2;
                                setserveur(v);
                            }
                        })
                        .setNegativeButton(nomjoueur1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                serveur = 1;
                                setserveur(v);
                            }
                        });
            }
            else
            {
                builder.setMessage("Who will serve first")
                        .setPositiveButton(nomjoueur2, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                serveur = 2;
                                setserveur(v);
                            }
                        })
                        .setNegativeButton(nomjoueur1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                serveur = 1;
                                setserveur(v);
                            }
                        });
            }
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private class J1WIN extends Dialog {
        private MainActivity a;
        public J1WIN(Activity a){
            super(a,R.style.Theme_AppCompat_Dialog);
            this.a= (MainActivity) a;
        }
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(a);
            if(fr) {
                builder.setMessage(nomjoueur1 + " à gagner")
                        .setPositiveButton("OK", null);
            }
            else
            {
                builder.setMessage(nomjoueur1 + " won")
                        .setPositiveButton("OK", null);
            }
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private class J2WIN extends Dialog {
        private MainActivity a;
        public J2WIN(Activity a){
            super(a,R.style.Theme_AppCompat_Dialog);
            this.a= (MainActivity) a;
        }
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(a);
            if(fr) {
                builder.setMessage(nomjoueur2 + " à gagner")
                        .setPositiveButton("OK", null);
            }
            else
            {
                builder.setMessage(nomjoueur2 + " won")
                        .setPositiveButton("OK", null);
            }
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    public void myClickHandlercam(View view) throws IOException {

       if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
           if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
               String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
               requestPermissions(permission, 1000);
           }
           else{
               openCamera();
           }

       }
       else {
           openCamera();
       }

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        match.adduri(getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values));
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT,match.getlastUri());
        startActivityForResult(i,1001);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1000:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openCamera();
                else
                    Toast.makeText(this,"Permission denied...",Toast.LENGTH_SHORT).show();
                break;
            case 99:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    updateGps();
                else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


}

