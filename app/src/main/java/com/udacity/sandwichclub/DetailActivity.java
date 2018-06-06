package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mMainName, mAlsoKnownAs, mIngredients, mPlaceOfOrigin, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mIngredientImage = (ImageView) findViewById(R.id.image_iv);
        mMainName = (TextView) findViewById(R.id.main_name_tv);
        mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        mDescription = (TextView) findViewById(R.id.description_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        if (!sandwich.getImage().isEmpty()) {

            Picasso.with(this).load(sandwich.getImage()).into(mIngredientImage);

        }

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String main_name = sandwich.getMainName();
        String place_of_origin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();

        List<String> also_known_as = sandwich.getAlsoKnownAs();
        List<String> ingredients = sandwich.getIngredients();

        if (main_name != null && !main_name.isEmpty()) {
            mMainName.setText(main_name);
        } else {
            mAlsoKnownAs.setText("N/A");
        }

        if (place_of_origin!=null && !place_of_origin.isEmpty()) {
            mPlaceOfOrigin.setText(place_of_origin);
        } else {
            mAlsoKnownAs.setText("N/A");
        }

        if (description!=null && !description.isEmpty()) {
            mDescription.setText(description);
        } else {
            mAlsoKnownAs.setText("N/A");
        }

        if (also_known_as!=null && !also_known_as.isEmpty()) {
            for (int i=0; i<also_known_as.size(); i++) {
                mAlsoKnownAs.append(also_known_as.get(i) + ", ");
            }
        } else {
            mAlsoKnownAs.setText("N/A");
        }

        if (ingredients!=null && !ingredients.isEmpty()) {
            for(int i=0; i<ingredients.size(); i++) {
                mIngredients.append(ingredients.get(i) + ", ");
            }
        } else {
            mIngredients.setText("N/A");
        }

    }
}
