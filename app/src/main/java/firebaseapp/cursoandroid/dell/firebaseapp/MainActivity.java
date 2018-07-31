package firebaseapp.cursoandroid.dell.firebaseapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnPhoto;
    private String file;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPhoto = (Button) findViewById(R.id.btnChangePhoto);
        imgPhoto = (ImageView) findViewById(R.id.imagem);

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ContextCompat.checkSelfPermission( MainActivity.this, android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( MainActivity.this, new String[] {
                            android.Manifest.permission.CAMERA  },1 );
                } else {
                    Intent takePictureIntente = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntente.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntente, 1);
                    }
                }
            }
        });

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {
                    android.Manifest.permission.CAMERA  },1 );
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            file = converterImgString(imageBitmap, 100);

            imgPhoto.setImageBitmap(imageBitmap);
        }
    }

    private String converterImgString(Bitmap bitmap, int quality) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality, array);
        byte[] imagenByte = array.toByteArray();
        String imagemString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagemString;
    }
}
