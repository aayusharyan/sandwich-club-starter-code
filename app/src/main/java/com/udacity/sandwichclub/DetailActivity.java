package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        //Display Name Alias
        TextView nameAliasTextView = (TextView) findViewById(R.id.also_known_tv);
        List<String> nameAlias = sandwich.getAlsoKnownAs();
        if(nameAlias.size() > 0) {
            nameAliasTextView.setVisibility(View.VISIBLE);
            findViewById(R.id.also_known_label).setVisibility(View.VISIBLE);
        }
        for(String singleNameAlias : nameAlias) {
            nameAliasTextView.append("• " + singleNameAlias + "\n");
        }

        //Display Ingredients
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();
        if(ingredients.size() > 0) {
            ingredientsTextView.setVisibility(View.VISIBLE);
            findViewById(R.id.ingredients_label).setVisibility(View.VISIBLE);
        }
        for(String single_ingredient : ingredients) {
            ingredientsTextView.append("• " + single_ingredient + "\n");
        }

        //Display Description
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        String desc = sandwich.getDescription();
        if(desc.trim().length() > 0) {
            descriptionTextView.setVisibility(View.VISIBLE);
            findViewById(R.id.description_label).setVisibility(View.VISIBLE);
            descriptionTextView.setText(desc);
            descriptionTextView.append("\n");
        }

        //Display Place of Origin
        TextView placeOfOriginTextView = (TextView) findViewById(R.id.place_of_origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if(placeOfOrigin.trim().length() > 0) {
            placeOfOriginTextView.setVisibility(View.VISIBLE);
            findViewById(R.id.place_of_origin_label).setVisibility(View.VISIBLE);
            placeOfOriginTextView.setText(placeOfOrigin);
            placeOfOriginTextView.append("\n");
        }
    }
}
