package mtahfajar.miftah_1202154182_modul4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class CariGambar extends AppCompatActivity {

    //Deklarasi semua komponen yang akan digunakan
    private EditText edCari;
    private Button btnCari;
    private ImageView ivCari;
    private ProgressDialog progressDialog;
    private String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);

        //Inisialisasi semua komponen yang akan digunakan
        edCari = (EditText) findViewById(R.id.ed_cari_gambar);
        btnCari = (Button) findViewById(R.id.btn_cari);
        ivCari = (ImageView) findViewById(R.id.iv_cari);
    }

    public void cariGambar(View view) {
        //Mengubah EditText ke dalam bentuk String
        text = edCari.getText().toString();
        if (text.isEmpty()) {
            //Jika EditText kosong akan memunculkan Toast
            Toast.makeText(this, "Masukkan URL gambar terlebih dahulu", Toast.LENGTH_LONG).show();
        } else {
            //Jika EditText berisi String maka akan di eksekusi
            new DownloadGambar().execute(text);
        }
    }

    private class DownloadGambar extends AsyncTask<String, Void, Bitmap> {

        //Method ini digunakan untuk melakukan eksekusi progress dialog sebelum method onPostExecute dijalankan
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CariGambar.this);
            progressDialog.setTitle("Cari Gambar");
            progressDialog.setMessage("Loading Gambar");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


        //Method ini digunakan untuk melakukan aktivitas dibackground menggunakan AsyncTask
        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }


        //Method ini digunakan untuk melakukan eksekusi setImageBitmap setelah method doInBackground dijalankan
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivCari.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    }
}
