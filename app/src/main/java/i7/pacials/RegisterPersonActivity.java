package i7.pacials;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.hamonteroa.ocrtest_m2.OcrCaptureActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import i7.pacials.Clases.Persona;

public class RegisterPersonActivity extends AppCompatActivity {


    private static final int RC_OCR_CAPTURE = 9003;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef = ref.child("Persona");
    EditText ci;
    EditText nombComp;
    EditText fechaNac;
    EditText ocupacion;
    EditText direccion;
    EditText estadoCivil;
    ArrayList<Persona> xx = new ArrayList<>();
    Calendar calendar;
    Persona registrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String x=this.getIntent().getExtras().get("listPerson").toString();
        setContentView(R.layout.activity_register_person);
        ci = (EditText) findViewById(R.id.ciRegisterP);
        nombComp = (EditText) findViewById(R.id.nameRegisterP);
        fechaNac = (EditText) findViewById(R.id.birthRegisterP);
        ocupacion = (EditText) findViewById(R.id.ocupationRegisterP);
        direccion = (EditText) findViewById(R.id.direccionRegisterP);
        estadoCivil = (EditText) findViewById(R.id.statuscivilRegisterP);
        xx=new Gson().fromJson(x,xx.getClass());
        calendar = Calendar.getInstance();
        fechaNac.setKeyListener(null);
    }

    public void aPerson(View view) {
        String fechanac = fechaNac.getText().toString();
        String estadocivil = estadoCivil.getText().toString();
        String ocup = ocupacion.getText().toString();
        String direction = direccion.getText().toString();
        String nomb = nombComp.getText().toString();

        try {
            Persona p = new Persona(fechanac, estadocivil, direction, nomb, ocup);
            p.setCI(Integer.parseInt(ci.getText().toString()));
            if (xx.contains(p)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("La persona ya existe (El CI insertado ya existe)")
                        .setTitle("Atención!!")
                        .setCancelable(false)
                        .setNeutralButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
            mensajeRef.child("" + p.getCI()).setValue(p);
            registrada = p;
            mensajeRef.child("" + p.getCI()).child("ci").removeValue();
            this.finish();
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Ha ocurrido un error al ingresar tus Datos, Verifica e intenta nuevamente")
                    .setTitle("Atención!!")
                    .setCancelable(false)
                    .setNeutralButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public Persona getRegistrada() {
        return registrada;
    }

    public void cPersona(View view) {
        this.finish();
    }

    public void mostrarDialogo(View view) {
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showDate(year, monthOfYear, dayOfMonth);
                calendar.set(dayOfMonth, monthOfYear, year);
            }
        }, dia, mes, year);
        datePickerDialog.show();
    }

    private void showDate(int year, int monthOfYear, int dayOfMonth) {
        fechaNac.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
    }

    public void insertarOCR(View view) {

        Intent intent = new Intent(this, OcrCaptureActivity.class);
        intent.putExtra(OcrCaptureActivity.AutoFocus, true);
        intent.putExtra(OcrCaptureActivity.UseFlash, true);
        startActivityForResult(intent, RC_OCR_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    ArrayList<String> deito = data.getStringArrayListExtra("deito");
                    Toast.makeText(this, deito.toString(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < deito.size(); i++) {
                        String c = deito.get(i);
                        String[] cc = c.split("\n");
                        for (int j = 0; j < cc.length; j++) {
                            verificarString(cc[j]);
                        }
                    }
                    //.setText(text);
                } else {

                }
            } else {

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void verificarString(String s) {
        //probando nombre
        if (new String(s.toUpperCase()).equals(s) && s.length()>3 && !isNumber(s)) {
            nombComp.setText(s);
            return;
        }
        if (isNumber(s)) {
            ci.setText(s);
        }
        //probando fecha desde "el xx de ......... de xxxx"
        String[] fecha = s.split(" ");
        try {
            for (int i = 0; i < fecha.length; i++) {
                if (fecha[i].equalsIgnoreCase("el")) {
                    int dia = Integer.parseInt(fecha[i + 1]);
                    int mes = obtenerMes(fecha[i + 3]);
                    int year = Integer.parseInt(fecha[i + 5]);
                    showDate(year, mes, dia);
                    return;
                }
                //tomando desde Civil {Soltero},{Casado},etc.
                if (fecha[i].equalsIgnoreCase("civil")) {
                    estadoCivil.setText(fecha[i + 1]);
                    return;
                }
                //preguntandole por ocupa (transformando a lower todo)
                if (fecha[i].toLowerCase().contains("ocupa")) {
                    String gu = fecha[i + 1];
                    if (fecha[i + 1].length() < 4) {
                        gu = fecha[i + 2];
                    }
                    ocupacion.setText(gu);
                    return;
                }
                if (fecha[i].equalsIgnoreCase("domicilio")) {
                    String dom = "";
                    for (int j = i + 1; j < fecha.length; j++) {
                        dom.concat(" " + fecha[j]);
                        dom.trim();
                    }
                }
            }
        } catch (Exception e) {
            //por si ocurre errores
        }

    }

    private boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private int obtenerMes(String s) {
        switch (s) {
            case "Enero":
                return 1;
            case "Febrero":
                return 2;
            case "Marzo":
                return 3;
            case "Abril":
                return 4;
            case "Mayo":
                return 5;
            case "Junio":
                return 6;
            case "Julio":
                return 7;
            case "Agosto":
                return 8;
            case "Septiembre":
                return 9;
            case "Octubre":
                return 10;
            case "Noviembre":
                return 11;
            case "Diciembre":
                return 12;
        }
        return 0;
    }
}
