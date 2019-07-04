package org.toolboxinst.buscadornotas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.toolboxinst.buscadornotas.model.Movie;
import org.toolboxinst.buscadornotas.repository.HelperRepository;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private ImageView imageFullScreenView;
    private Button close;
    private Movie m;

    @Override
    public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
        HelperRepository helperRepository = new HelperRepository(this);
    setContentView(R.layout.layout_fullscreen);
    Intent at = getIntent();
    Long id = at.getLongExtra("id", 0);

    if(id != 0){
         m = helperRepository.getMovieById(id);
    }

    imageFullScreenView = (ImageView) findViewById(R.id.imgFullScreen);
    close = (Button) findViewById(R.id.btnclose);

        /**
         *
   close.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    });
         */
   try{
       imageFullScreenView.setImageBitmap(getPhoto(m.getImg_move_byte()));
   }catch (NullPointerException e){
       imageFullScreenView.setImageResource(R.drawable.img);
   }

}

    public Bitmap getPhoto(byte[] photo_bytes){
        try {
            return BitmapFactory.decodeByteArray(photo_bytes, 0, photo_bytes.length);
        }catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
