package com.example.shoptwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoptwo.entity.Libro;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddLibroActivity extends AppCompatActivity {
    private EditText txtCodigo, txtTitulo, txtPaginas, txtImagen;
    private FirebaseFirestore db;
    private CollectionReference librosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_libro);
        db = FirebaseFirestore.getInstance();
        librosRef = db.collection(DataInfo.LIB_REF);
        setup();
    }

    private void setup() {
        txtCodigo = findViewById(R.id.txtDescripcion);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtPaginas = findViewById(R.id.txtPaginas);
        txtImagen = findViewById(R.id.txtImagen);
    }

    public void volver(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void guardar(View view) {
        String codigo = txtCodigo.getText().toString();
        String titulo = txtTitulo.getText().toString();
        String paginas = txtPaginas.getText().toString();
        String imagen = txtImagen.getText().toString();

        if (TextUtils.isEmpty(codigo)) {
            txtCodigo.setError("Campo Obligatorio");
            return;
        }

        if (titulo==null || titulo.length()==0) {
            txtTitulo.setError("Campo Requerido");
            return;
        }

        Libro libro = new Libro();
        libro.setCodigo(codigo);
        libro.setTitulo(titulo);
        libro.setPaginas(Integer.parseInt(paginas));
        libro.setImagen(imagen);

        librosRef.add(libro)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documento) {
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddLibroActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
