package org.toolboxinst.buscadornotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.toolboxinst.buscadornotas.adapter.MovieBaseAdapter;
import org.toolboxinst.buscadornotas.model.Movie;
import org.toolboxinst.buscadornotas.repository.HelperRepository;
import org.toolboxinst.buscadornotas.service.Async;
import org.toolboxinst.buscadornotas.service.ConnectorImpl;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class MoviesActivity extends AppCompatActivity {


    private ConnectorImpl connector;
    private ImageButton imgButton;
    private EditText editText;

    private ListView listView;

    private HelperRepository helperRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        listView = (ListView) findViewById(R.id.list_view_movies);
        imgButton = (ImageButton)findViewById(R.id.imgBuscar);
        editText = (EditText)findViewById(R.id.input);
        helperRepository = new HelperRepository(this);
        List<Movie> movieList = helperRepository.recoveryMyMoviesInsert();


        if(movieList!=null && movieList.size() >= 1){
            System.err.println(movieList.size());
            final MovieBaseAdapter baseAdapter = new MovieBaseAdapter(movieList,this,this);
            listView.setAdapter(baseAdapter);
            listView.setTextFilterEnabled(true);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    System.err.println(s);
                    baseAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent at = new Intent(MoviesActivity.this,DetailAct.class);
                at.putExtra("id",id);
                startActivity(at);
            }
        });

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String input_tittle = String.valueOf(editText.getText());
                    Async async = new Async(MoviesActivity.this);
                    String[] param = {ConnectorImpl.MY_KEY,input_tittle};
                    async.execute(param);
            }
        });
    }


}
