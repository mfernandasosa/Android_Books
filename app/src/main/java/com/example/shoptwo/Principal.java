package com.example.shoptwo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shoptwo.entity.Libro;
import com.example.shoptwo.adapters.LibroAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Principal extends AppCompatActivity {
    private FirebaseFirestore db;
    private CollectionReference librosRef;
    private ListView lista;
    private Button btnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),Maps.class);
                startActivity(intent2);
            }
        });

        db = FirebaseFirestore.getInstance();
        librosRef = db.collection(DataInfo.LIB_REF);
        lista = findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Libro libro = (Libro) lista.getItemAtPosition(position);
                Intent intent = new Intent(Principal.this,
                        EditLibroActivity.class);
                intent.putExtra("idLibro", libro.getId());
                llamados.launch(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarDatos();
    }

    public void agregar(View view) {
        Intent intent = new Intent(Principal.this,
                AddLibroActivity.class);
        llamados.launch(intent);

    }


    ActivityResultLauncher<Intent> llamados = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_OK) {
                        cargarDatos();
                    }
                }
            }
    );

    private void cargarDatos() {
        librosRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Libro> libros = new ArrayList<>();
                            for (QueryDocumentSnapshot documento : task.getResult()) {
                                Libro libro = documento.toObject(Libro.class);
                                libro.setId(documento.getId());
                                libros.add(libro);
                            }
                            mostrarDatos(libros);
                        } else {
                            Toast.makeText(Principal.this,
                                    "Error en la carga de datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void mostrarDatos(ArrayList<Libro> libros) {
        LibroAdapter adapter = new LibroAdapter(Principal.this, libros);
        lista.setAdapter(adapter);
    }


}
