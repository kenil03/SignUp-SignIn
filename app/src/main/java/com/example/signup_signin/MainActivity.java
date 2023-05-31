package com.example.signup_signin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    EditText id,name,age,stream;
    Button browse,add;
    ImageView img;

    Uri uri;

    ActivityResultLauncher<String> gallaryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        stream = findViewById(R.id.stream);
        browse = findViewById(R.id.browse);

        gallaryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            img.setImageURI(result);
            uri = result;
        });
        browse.setOnClickListener(v -> gallaryLauncher.launch("image/*"));
        add = findViewById(R.id.addStudent);
        add.setOnClickListener(v -> {


            String Id = id.getText().toString().trim();
            String Name = name.getText().toString().trim();
            String Age = age.getText().toString().trim();
            String Stream = stream.getText().toString().trim();

            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("File Uploader");
            dialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference upload = storage.getReference("Image" + new Random().nextInt(50));
            upload.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> {
                        upload.getDownloadUrl().addOnSuccessListener(uri -> {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference root = database.getReference("/Students/RollNumbers ");
                            DataHolder dataHolder = new DataHolder(Name,Age,Stream,uri.toString());
                            root.child(Id).setValue(dataHolder);
                        });

                        id.setText("");
                        name.setText("");
                        age.setText("");
                        stream.setText("");
                        img.setImageResource(R.drawable.image123456);
                        dialog.dismiss();
                        Toast.makeText(this, "Sucessfully Uploaded to Firebase Database", Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(snapshot -> {
                        float pr = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploading "+ (int)pr + "%");
                    });
        });
    }
}