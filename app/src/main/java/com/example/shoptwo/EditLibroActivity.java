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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditLibroActivity extends AppCompatActivity {
    private EditText txtCodigo, txtTitulo, txtPaginas, txtImagen;
    private FirebaseFirestore db;
    private CollectionReference librosRef;
    private String idLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_libro);
        idLibro = getIntent().getStringExtra("idLibro");
        if (idLibro==null || idLibro.length()==0) {
            setResult(RESULT_CANCELED);
            finish();
        }
        db = FirebaseFirestore.getInstance();
        librosRef = db.collection(DataInfo.LIB_REF);
        setup();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarDatos();
    }

    private void cargarDatos() {
        librosRef.document(idLibro).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documento) {
                        if (documento.exists()) {
                            Libro libro = documento.toObject(Libro.class);
                            libro.setId(documento.getId());
                            mostrarDatos(libro);
                        } else {
                            Toast.makeText(EditLibroActivity.this,
                                    "El Libro no existe", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditLibroActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void mostrarDatos(Libro libro) {
        txtCodigo.setText(libro.getCodigo());
        txtTitulo.setText(libro.getTitulo());
        txtPaginas.setText(String.valueOf(libro.getPaginas()));
        txtImagen.setText(libro.getImagen());
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
        libro.setId(idLibro);
        libro.setCodigo(codigo);
        libro.setTitulo(titulo);
        libro.setPaginas(Integer.parseInt(paginas));
        libro.setImagen(imagen);

        librosRef.document(idLibro).set(libro)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditLibroActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void eliminar(View view) {
        librosRef.document(idLibro).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditLibroActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
