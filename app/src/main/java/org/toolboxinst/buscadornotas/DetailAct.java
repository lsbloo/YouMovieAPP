package org.toolboxinst.buscadornotas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.toolboxinst.buscadornotas.model.Movie;
import org.toolboxinst.buscadornotas.repository.HelperRepository;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAct extends AppCompatActivity {

    private TextView nome_detail;
    private CircleImageView circleImageView;
    private TextView genero_detail;
    private TextView sinopse_detail;
    private TextView atores_detail;
    private Long id_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_detail);
        nome_detail = (TextView) findViewById(R.id.nome_detail);
        circleImageView = findViewById(R.id.photo_detail);
        genero_detail = findViewById(R.id.genero);
        sinopse_detail = findViewById(R.id.sinopse);
        atores_detail = findViewById(R.id.atores);


        HelperRepository helperRepository = new HelperRepository(this);
        Intent at = getIntent();
        Long id = at.getLongExtra("id",0);
        if( id != 0){
            Movie m = helperRepository.getMovieById(id);
            nome_detail.setText("Nome: " + m.getName_movie());
            genero_detail.setText("Genero: " + m.getGenre());
            sinopse_detail.setText("Sinopse: " + m.getSinopse_movie());
            atores_detail.setText("Atores: "  + m.getActors());
            id_pass=id;
            try{
                circleImageView.setImageBitmap(getPhoto(m.getImg_move_byte()));
            }catch (NullPointerException e){
                circleImageView.setImageResource(R.drawable.img);
            }
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent at = new Intent(DetailAct.this,FullscreenActivity.class);
                at.putExtra("id" , id_pass);
                startActivity(at);
            }
        });
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
