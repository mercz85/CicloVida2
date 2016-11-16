package es.iesnervion.mrequena.ciclovida2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView txtCiclo;
    EditText txtEdit;
    Button btnPause;
    Button btnResume;
    ImageView foto;
    Button galeria;

    // https://amatellanes.wordpress.com/2013/09/09/android-spinner-dinamico-en-android-provincias-y-localidades-de-espana/
    Spinner spProvincias;
    Spinner spLocalidades;
    Button btnComprobar;

    private static final int TAKE_PICTURE = 1; //NO LO VOY A USAR
    private static final int RESULT_LOAD_IMAGE = 0;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /*
    Tareas necesarias para para arrancar la actividad, solo
    deben ejecutarse una vez en todo el ciclo de vida:
        definir la interfaz e instanciar propiedades
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        TextView txtCiclo = (TextView) findViewById(R.id.txtCiclo);
        EditText txtEdit = (EditText) findViewById(R.id.txtEdit);
        Button btnPause = (Button) findViewById(R.id.btnPause);
        Button btnResume = (Button) findViewById(R.id.btnResume);
        ImageView foto = (ImageView) findViewById(R.id.img);
        Button galeria = (Button) findViewById(R.id.galeria);

        this.spProvincias = (Spinner) findViewById(R.id.sp_provincia);
        this.spLocalidades = (Spinner) findViewById(R.id.sp_localidad);
        this.btnComprobar = (Button) findViewById(R.id.btnLugar);

        loadSpinnerProvincias();

    }

    /*
     Tras onCreate, el sistema llama a onStart (actividad comienza a ser
     visible para el usuario) y a onResume (llamado cuando el usuario puede
     empezar a interactuar con la actividad)
     sirven para retornar desde estados de Pausa y Parada
     inicializar los componentes liberados en onPause
    */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
    parar acciones en marcha
    guardar info que el usuario esperiaria guardar (input de texto)
    liberacion de recursos
    No operaciones costosas (guardar en BD)
    */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
    liberacion de recursos en onStop() no liberados en onPause()
    operaciones de cierre y guardado
    */
    @Override
    protected void onStop() {
        super.onStop();
    }


    //METODOS ON CLICK

    public void onClickResume(View view) {
    }

    public void onClickPause(View view) {
    }

    public void onClickGaleria(View view) {
/*        Intent intent = new Intent();
        intent.setType("image*//**//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, ""), TAKE_PICTURE);*/

//        Intent i = new Intent(
//                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(i, TAKE_PICTURE);

        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    //ESTE CODIGO FUNCIONA
        /*
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "content://media/internal/images/media"));
        startActivity(intent);
        */
    //ESTE CODIGO FUNCIONA
    /*
             Intent galleryIntent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivity(galleryIntent);
      */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PICTURE) {
//
//        }
/*        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == TAKE_PICTURE) {
                Uri selectedImageUri = data.getData();
                System.out.println("Image URI: "+selectedImageUri.toString());
                foto.setImageURI(selectedImageUri);
            }
        }*/

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  RESULT_LOAD_IMAGE  && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            cursor.close();

            ImageView foto = (ImageView) findViewById(R.id.img);
            foto.setImageBitmap(BitmapFactory.decodeFile(picturePath));



            // String picturePath contains the path of selected Image
        }
        //https://developer.android.com/guide/topics/media/camera.html

        //viralpatel.net/blogs/pick-image-from-galary-android-app/
    }

    /**
     * Populate the Spinner.
     */
    private void loadSpinnerProvincias() {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.provincias, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spProvincias.setAdapter(adapter);

        // This activity implements the AdapterView.OnItemSelectedListener
        this.spProvincias.setOnItemSelectedListener(this);
        this.spLocalidades.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            switch (parent.getId()) {
                case R.id.sp_provincia:

                    // Retrieves an array
                    TypedArray arrayLocalidades = getResources().obtainTypedArray(
                            R.array.array_provincia_a_localidades);
                    CharSequence[] localidades = arrayLocalidades.getTextArray(position);
                    arrayLocalidades.recycle();

                    // Create an ArrayAdapter using the string array and a default
                    // spinner layout
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                            this, android.R.layout.simple_spinner_item,
                            android.R.id.text1, localidades);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Apply the adapter to the spinner
                    this.spLocalidades.setAdapter(adapter);

                    break;

                case R.id.sp_localidad:

                    break;
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Callback method to be invoked when the selection disappears from this
        // view. The selection can disappear for instance when touch is
        // activated or when the adapter becomes empty.
    }


    public void showLocalidadSelected(View view) {
        Context context = getApplicationContext();
        CharSequence text = " LOCALIDAD: "+spLocalidades.getSelectedItem().toString()
                + "\n PROVINCIA: "+spProvincias.getSelectedItem().toString(); //"Hello toast!";

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
