package org.grace.pokedex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.grace.pokedex.adapters.RowTypesAdapter;
import org.grace.pokedex.data.AppDatabase;
import org.grace.pokedex.data.Pokemon;
import org.grace.pokedex.data.PokemonDetails;
import org.grace.pokedex.adapters.AsyncTaskHandler;
import org.grace.pokedex.network.PokemonDetailsAsyncTask;

import java.util.Arrays;

public class PokemonDetailsActivity extends AppCompatActivity implements AsyncTaskHandler {

    ImageView image, favorite;
    TextView name, types, height, experience, id, typeText;
    RecyclerView rvDetailsTypes;
    AppDatabase database;
    String url;
    String pokemonName;
    Pokemon favoritePokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        image = findViewById(R.id.details_image);
        favorite = findViewById(R.id.details_favorite);
        name = findViewById(R.id.details_name);
        types = findViewById(R.id.detatils_type);
        height = findViewById(R.id.detatils_height);
        experience = findViewById(R.id.detatils_experience);
        id = findViewById(R.id.details_id);
        typeText = findViewById(R.id.detatils_typessText);
        rvDetailsTypes = findViewById(R.id.rv_details_types);
        url = getIntent().getStringExtra("URL");

        PokemonDetailsAsyncTask pokemonDetailsAsyncTask = new PokemonDetailsAsyncTask();
        pokemonDetailsAsyncTask.handler = this;
        pokemonDetailsAsyncTask.execute(url);
        database = AppDatabase.getDatabase(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inicio:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTaskEnd(Object result) {
        PokemonDetails details = (PokemonDetails) result;
        pokemonName = details.getName().toUpperCase();
        Glide.with(this).load(details.getImage()).into(image);
        name.setText(details.getName());
        height.setText("Altura: " + details.getHeight() + "0 cm");
        experience.setText("Experiencia: " + details.getBaseExperience() + " xp");
        id.setText("ID: #" + details.getId());
        String typesString = "";

        for (int i = 0; i < details.getTypes().length; i++) {
            typesString += details.getTypes()[i] + " ";
        }

        rvDetailsTypes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDetailsTypes.setAdapter(new RowTypesAdapter(this, Arrays.asList(details.getTypes())));
        types.setText("Tipo/s " + System.lineSeparator() + typesString);
        favoritePokemon = database.pokemonDao().findByName(details.getName());
    }

    public void onClickType(View view) {
        Intent intent = new Intent(this, PokemonTypeActivity.class);
        startActivity(intent);
    }
}
