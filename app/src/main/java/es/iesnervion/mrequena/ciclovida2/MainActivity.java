package es.iesnervion.mrequena.ciclovida2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    /*
    Tareas necesarias para para arrancar la actividad, solo
    deben ejecutarse una vez en todo el ciclo de vida:
        definir la interfaz e instanciar propiedades
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
/*
 Tras onCreate, el sistema llama a onStart (actividad comienza a ser
 visible para el usuario) y a onResume (llamado cuando el usuario puede
 empezar a interactuar con la actividad)
 sirven para retornar desde estados de Pausa y Parada
*/
    @Override
    protected void onResume() {
        super.onResume();
    }

    //liberacion de recursos en onPause()
    @Override
    protected void onPause() {
        super.onPause();
    }
    //liberacion de recursos en onStop()
    @Override
    protected void onStop() {
        super.onStop();
    }
}
