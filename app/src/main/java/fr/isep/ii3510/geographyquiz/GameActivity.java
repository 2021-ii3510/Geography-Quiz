package fr.isep.ii3510.geographyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameActivity extends AppCompatActivity {
    private TextView countryTextView;
    private ListView capitalListView;

    private List<Country> countries;
    private Country quizCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 1. We first bind the widgets to Java objects
        countryTextView = findViewById(R.id.country_textView);
        capitalListView = findViewById(R.id.listView);

        // 2. We can also define here the listener that will be attached to the ListView
        // Note that we can define the listener just once (as there is no need to define it again and again)
        // However, doing so also leads to the following modification: `quizCountry` is not a class variable
        capitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView capitalCity = (TextView) view;
                // Note the use of `Toast` to write messages to the user
                if (capitalCity.getText().toString().equals(quizCountry.getCapitalCityName())) {
                    SharedPreferences prefs = getSharedPreferences("leaderboard", MODE_PRIVATE);
                    int currentScore = prefs.getInt(quizCountry.getCountryName(), 0);
                    currentScore++;

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(quizCountry.getCountryName(), currentScore);
                    editor.apply();

                    Log.i("Current answer", quizCountry.getCountryName() + ": " + currentScore);

                    Toast.makeText(GameActivity.this, "Your Geography ROX", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GameActivity.this, "Go back to school", Toast.LENGTH_SHORT).show();
                }
                // After an item is clicked, we redo the whole process of picking countries and updating the display
                List<Country> pickedCountries = pickRandomCountries();
                updateDisplay(pickedCountries);
            }
        });

        // 3. We then read the CSV file and store the content in `countries`
        readFile();

        // 4. We randomly pick 5 countries
        List<Country> pickedCountries = pickRandomCountries();
        // 5. We update the display
        updateDisplay(pickedCountries);
    }

    private void readFile() {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.countries_capitals));
        if (countries == null) countries = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String countryName = line.split(",")[0];
            String capitalCityName = line.split(",")[1];
            Country country = new Country(countryName, capitalCityName);
            countries.add(country);
        }
        scanner.close();
    }

    /**
     * This method:
     * 1. Initializes a list of countries;
     * 2. Randomly generate numbers that correspond to the index of the picked countries
     * 3. Before adding this country, We make sure that it is not already added to the list of picked countries
     * @return A list of `Country` that will be used to show the "quiz country" and the capital cities
     */
    private List<Country> pickRandomCountries() {
        List<Country> pickedCountries = new ArrayList<>();
        Random random = new Random();
        while (pickedCountries.size() != 5) {
            Country randomlyPickedCountry = countries.get(random.nextInt(countries.size()));
            if (!pickedCountries.contains(randomlyPickedCountry)) pickedCountries.add(randomlyPickedCountry);
        }
        return pickedCountries;
    }

    /**
     * We define the first `Country` to be the "quiz country".
     * Then, we need to populate the `ListView` by initializing an `ArrayAdapter`. To do that, we create a
     *     list of Strings `capitalCities` that will take all the capital cities and shuffle them.
     * And finally, we set the adapter of the `ListView` to actually show its contents.
     * @param pickedCountries The list of `Country` that are picked for the game
     */
    private void updateDisplay(List<Country> pickedCountries) {
        quizCountry = pickedCountries.get(0);
        countryTextView.setText(quizCountry.getCountryName());

        // I create the actual contents of the ListView
        List<String> capitalCities = new ArrayList<>();
        for (Country country : pickedCountries) {
            capitalCities.add(country.getCapitalCityName());
        }
        Collections.shuffle(capitalCities);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, capitalCities);
        capitalListView.setAdapter(adapter);
    }
}