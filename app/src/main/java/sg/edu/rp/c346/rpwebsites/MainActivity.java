package sg.edu.rp.c346.rpwebsites;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnCat;
    Spinner spnSub;
    Button btnGo;
    WebView wv;
    ArrayList<String> alSub;
    ArrayAdapter aaSub;
    String[] cat;
    String[] rp;
    String[] soi;
    String[][] sites;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    TextView tv1;
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnCat = findViewById(R.id.spnCat);
        spnSub = findViewById(R.id.spnSub);
        btnGo = findViewById(R.id.btnGo);
        wv = findViewById(R.id.wv);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDisplayZoomControls(true);

        pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        edit = pref.edit();




        cat = getResources().getStringArray(R.array.cat);
        rp = getResources().getStringArray(R.array.rp);
        soi = getResources().getStringArray(R.array.soi);


        alSub = new ArrayList<>();


        spnCat.setSelection(pref.getInt("cat",0));
        if(spnCat.getSelectedItemPosition() == 0){
            alSub.addAll(Arrays.asList(rp));
        }
        else{
            alSub.addAll(Arrays.asList(soi));
        }
        aaSub = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,alSub);
        spnSub.setAdapter(aaSub);
        spnSub.setSelection(pref.getInt("sub",0));


        sites = new String[][]{
                {
                    "https://www.rp.edu.sg/", "https://www.rp.edu.sg/student-life"
                },
                {
                    "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47", "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"
                }
        };


        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alSub.clear();
                edit.putInt("cat",position);
                edit.commit();
                switch(position){
                    case 0:
                        alSub.addAll(Arrays.asList(rp));
                        break;
                    case 1:
                        alSub.addAll(Arrays.asList(soi));
                        spnSub.setSelection(pref.getInt("sub",0));
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnSub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit.putInt("sub",position);
                edit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnCat.setVisibility(View.GONE);
                spnSub.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                wv.loadUrl(sites[spnCat.getSelectedItemPosition()][spnSub.getSelectedItemPosition()]);
            }
        });

    }
}
