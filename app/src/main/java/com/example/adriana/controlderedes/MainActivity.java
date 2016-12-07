package com.example.adriana.controlderedes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class MainActivity extends AppCompatActivity {
    private String IP = "mysql.hostinger.es";
    private String baseDatos = "/u446646577_red";
    private EditText a, b;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void iniciar(View view) {
        a = (EditText) findViewById(R.id.et1);
        b = (EditText) findViewById(R.id.pw1);
        String usuario = a.getText().toString();
        String password = b.getText().toString();
        new ConexionDB().execute(IP, baseDatos, "datos", usuario);
        // Toast.makeText(getApplicationContext(), "Informacion incorrecta", Toast.LENGTH_SHORT).show()
    }

    public class ConexionDB extends AsyncTask<String, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(String... strings) {

            try {
                Connection conn;
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://mysql.hostinger.es/u446646577_red", "u446646577_root", "amistad17.");
                Statement estado = conn.createStatement();
                Toast.makeText(getApplicationContext(), "Conexion establecida", Toast.LENGTH_SHORT).show();
                String peticion = "select * from " + strings[2] + " where usuario_red='" + strings[3] + "'";
                ResultSet result = estado.executeQuery(peticion);
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResultSet result) {

            try {
                if (result != null) {
                    if (!result.next()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "No existen resultados", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        String usuario = a.getText().toString();
                        String pass = b.getText().toString();
                        String user = result.getString("usuario_red");
                        String pw = result.getString("password");
                        if (usuario.compareTo(user) == 0 && pass.compareTo(pw) == 0) {
                            Intent i = new Intent(MainActivity.this, SegundaActivity.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No existen datos", Toast.LENGTH_LONG);
                    toast.show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}


