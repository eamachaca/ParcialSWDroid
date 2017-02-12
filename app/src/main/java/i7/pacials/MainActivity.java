package i7.pacials;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import i7.pacials.Clases.Conexion;
import i7.pacials.Clases.Image;
import i7.pacials.Clases.Persona;
import i7.pacials.Clases.Personal;
import i7.pacials.Clases.Solicitud;
import i7.pacials.Controlador.ListaImagenesAdapter;
import i7.pacials.Controlador.ListaPersonaAdaptador;

import static i7.pacials.R.layout.cliente;


public class MainActivity extends AppCompatActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final int REQUEST_CODE_SCAN = 47;

    private static final String SAVED_SCANNED_HHOTO = "scanned_photo";

    public static final int REQUEST_CAMERA = 1;
    private Image image=new Image();

    // ===========================================================
    // Fields
    // ===========================================================

    //private final ViewHolder viewHolder = new ViewHolder();

    private String scannedPhoto="";
    private Button btnEnviar;
    private Button btnScan;
    private int contador=0;
    public int cont=0;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getters & Setters
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================


    private RecyclerView recyclerView;
    Solicitud solicitud;
    private ListaImagenesAdapter listaImagenesAdapter;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef;
    private String codRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recycImage);
        listaImagenesAdapter= new ListaImagenesAdapter();
        recyclerView.setAdapter(listaImagenesAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        mensajeRef = ref.child("Request").child("numero").child("Images");
        btnEnviar=(Button)findViewById(R.id.enviarIMG);
        btnScan=(Button)findViewById(R.id.scanBtn);
        solicitud=(Solicitud) getIntent().getExtras().get("request");
    }


    public void scanear(View v) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MainActivity.REQUEST_CAMERA);
            } else {
                // permission has been granted, continue as usual
                onScanButtonClicked();
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    onScanButtonClicked();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            String imgPath = data.getStringExtra(ScanActivity.RESULT_IMAGE_PATH);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            contador++;
            image=new Image();
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath,options);
            image.setCodigoRequisito(codRequest);
            image.setImage(bitmap);
            image.setURL(imgPath);
            image.setCodigoRequisito(solicitud.getKey());
            Dialog dialog=crearDialogoBusqueda();
            dialog.show();
        }
    }

    private boolean saveBitmapToFile(File file, Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private Dialog crearDialogoBusqueda(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText textoBusqueda = new EditText(this);
        builder.setTitle("Inserte el nombre de la Imagen");   // Título
        builder.setView(textoBusqueda);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                image.setName( textoBusqueda.getText().toString());
                listaImagenesAdapter.adicionarImagen(image);
            }
        });
        return builder.create();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_SCANNED_HHOTO, scannedPhoto);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void onScanButtonClicked() {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanActivity.EXTRA_BRAND_IMG_RES, R.drawable.ic_crop_white_24dp);
        intent.putExtra(ScanActivity.EXTRA_TITLE, "Crop Document");
        intent.putExtra(ScanActivity.EXTRA_ACTION_BAR_COLOR, R.color.blue);
        intent.putExtra(ScanActivity.EXTRA_LANGUAGE, "es");
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
    private File createImageFile(String fileName) {
        File storageDir = this.getExternalFilesDir("images");
        if (storageDir == null) {
            throw new RuntimeException("Not able to get to External storage");
        }
        File image = new File(storageDir, fileName + ".jpg");

        return image;
    }

    private void removeFile(String absoluteLocation) {
        if (absoluteLocation == null) return;

        File f = new File(absoluteLocation);
        if (f.exists()) {
            f.delete();
        }
    }

    public void siguiente(){
        if(cont>=listaImagenesAdapter.getItemCount()) {
            return;
        }
        final Image triste=listaImagenesAdapter.getDataset().get(cont);
        btnScan.setClickable(false);
        btnEnviar.setClickable(false);
        RequestParams params = new RequestParams();
        try
        {
            //File myFile = new File(triste.getURL());

            File myFile = createImageFile("scanned_doc"+new Random().nextInt(200));
            saveBitmapToFile(myFile, triste.getImage());
            params.put("deito", myFile);
            params.put("nombre",triste.getName());
            params.put("solicitud",triste.getCodigoRequisito());
            AsyncHttpClient client=new AsyncHttpClient();
            String newURL=Conexion.URL+"storage/create";
            client.post(newURL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    listaImagenesAdapter.eliminarImagen(triste);
                    btnScan.setClickable(true);
                    btnEnviar.setClickable(true);
                    siguiente();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
                    listaImagenesAdapter.eliminarImagen(triste);
                    btnScan.setClickable(true);
                    btnEnviar.setClickable(true);
                    siguiente();

                }
            });

        } catch(FileNotFoundException e) {} catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void enviar(View view) {
        cont=0;
        siguiente();
    }


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
