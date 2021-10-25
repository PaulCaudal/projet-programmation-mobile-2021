package fr.android.projet;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class acceuil extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);

    }
    protected void Afficher(){


    }

    public void myClickHandler2(View view) throws IOException {
        switch (view.getId()){
            case R.id.imageButton3:
                EditText t = findViewById(R.id.editTextTextPostalAddress);
                LocationManager l=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                System.out.println(l+"                   bbbbbbbbbbbbbbbbbbbbbbbb");
                break;
            case R.id.button2:
                break;
        }

    }
}
