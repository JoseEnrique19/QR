package net.desarrollolibre.qr.qr;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Main2Activity extends AppCompatActivity {
    String rec_token = "";
    String sel = "";
    RadioGroup grbtn_nombres;
    RadioButton rb_ata,rb_diego,rb_fer,rb_kike,rb_uri;
    TextView txt_codigo;
    Button btn_enviar;
    int hora, min, seg;

    Calendar calendario = new GregorianCalendar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Variables de elementos
        txt_codigo = (TextView)findViewById(R.id.txt_codigo);
        grbtn_nombres = (RadioGroup) findViewById(R.id.grbtn_nombres);
        btn_enviar = (Button)findViewById(R.id.btn_enviar);
        rb_ata = (RadioButton)findViewById(R.id.rb_ata);
        rb_diego = (RadioButton)findViewById(R.id.rb_diego);
        rb_fer = (RadioButton)findViewById(R.id.rb_fer);
        rb_kike = (RadioButton)findViewById(R.id.rb_kike);
        rb_uri = (RadioButton)findViewById(R.id.rb_uri);
        //Estado inicial de los elementos
        txt_codigo.setVisibility(View.INVISIBLE);
        grbtn_nombres.setVisibility(View.INVISIBLE);
        btn_enviar.setVisibility(View.INVISIBLE);
        //Recibimos QR de la pantalla anterior
        rec_token = getIntent().getStringExtra("cod_token");
        verif();
        //Boton
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro();
            }
        });
    }

    public void checa(){//Verifica cual esta seleccionado y obtiene el texto
        int value;
        value = grbtn_nombres.getCheckedRadioButtonId();
        if (value == 2131165263){
            sel = "Atahualtpa";
        }else if (value == 2131165264){
            sel = "Diego David";
        }else if (value == 2131165265){
            sel = "Luis Fernando";
        }else if (value == 2131165266){
            sel = "Jose Enrique";
        }else if (value == 2131165267){
            sel = "Uriel";
        }else if (value == (-1)){
            sel = "nada";
        }
    }

    public void verif(){//Verifica que codigo sea el bueno
        if (rec_token.equals("Ximb4l")){
            txt_codigo.setText("Codigo QR correcto");
            txt_codigo.setVisibility(View.VISIBLE);
            grbtn_nombres.setVisibility(View.VISIBLE);
            btn_enviar.setVisibility(View.VISIBLE);
        }else {
            txt_codigo.setText("Codigo QR erroneo\nRegresa para checar otro");
            txt_codigo.setVisibility(View.VISIBLE);
        }
    }

    public  void registro(){//realiza el registro en la BD
        checa();
        if (sel=="nada"){
            Toast.makeText(this,"Selecciona tu nombre",Toast.LENGTH_LONG).show();
        }else {
            String type = "llegada";
            hora = (calendario.get(Calendar.HOUR_OF_DAY))-6;
            min = calendario.get(Calendar.MINUTE);
            seg = calendario.get(Calendar.SECOND);
            String hour = hora+":"+min+":"+seg;

            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, sel,hour);
        }
    }
}
