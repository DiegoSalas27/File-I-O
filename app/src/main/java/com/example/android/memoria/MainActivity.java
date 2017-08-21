package com.example.android.memoria;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.example.android.memoria.R.id.text;

public class MainActivity extends AppCompatActivity {

    TextView tvResultado;
    EditText etNombre, etCorreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResultado = (TextView) findViewById(R.id.tvResultado);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
    }
    public void generarPreferencia(View view){

        //MODE_APPEND concatena los datos
        SharedPreferences mySharedPref = getSharedPreferences("archivo_pref", Context.MODE_PRIVATE); //sobreescribe lo guardado anteriormente
        SharedPreferences.Editor editor = mySharedPref.edit(); //inicializamos editor con la preferencia asignada

        editor.putString("nombre", etNombre.getText().toString()); //guarda elementos en formato xml
        editor.putString("correo", etCorreo.getText().toString());

        editor.commit();

        Toast.makeText(this, "Se ha creado la preferencia compartida!", Toast.LENGTH_SHORT).show();

        etNombre.setText("");
        etCorreo.setText("");
    }
    public void leerPreferencia(View view){

        SharedPreferences mySharedPref = getSharedPreferences("archivo_pref", Context.MODE_PRIVATE);
        String nombre = mySharedPref.getString("nombre", ""); // si no encuentra una variable "nombre", devuelve vacio
        String correo = mySharedPref.getString("correo", "no existe esa variable");

        tvResultado.setText("\nNombre: " + nombre + "\nCorreo: " + correo);
    }
    public void generarArchivo(View view){
        //las excepciones son importantes para entradas y salidas de datos
        try{
            String nombre = etNombre.getText().toString();
            FileOutputStream outputStream = null; //declaramos el objeto
            outputStream = openFileOutput(getString(R.string.nombre_archivo), Context.MODE_APPEND); //Le damos nombre al archivo y el modo en que es creado
            outputStream.write(nombre.getBytes()); //escribir el contenido del archivo
            outputStream.close();

            Toast.makeText(MainActivity.this, R.string.mensaje, Toast.LENGTH_SHORT).show();
            etNombre.setText("");

        }catch(Exception e){

            e.printStackTrace();
            Toast.makeText(MainActivity.this, R.string.error_escritura, Toast.LENGTH_SHORT).show();

        }

    }
    public void leerArchivo(View view){
        tvResultado.setText(read(getString(R.string.nombre_archivo)));
    }

    public String read(String file){

        String text = "";

        try{
            FileInputStream fileInputStream = openFileInput(file);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            text = new String(buffer);
        }catch(Exception e){

            e.printStackTrace();
            Toast.makeText(MainActivity.this, R.string.error_lectura, Toast.LENGTH_SHORT).show();
        }
        return text;
    }
}
