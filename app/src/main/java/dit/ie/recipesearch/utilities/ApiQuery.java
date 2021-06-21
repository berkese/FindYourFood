package dit.ie.recipesearch.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// Generates a URL based on the parameters based via the SearchActivity
public class ApiQuery {


    // API Key KEY
    final static String API_KEY = "22475de221b96ad1fa41e4f30047a648";

    // API KEY SEARCH
    final static String API_KEY_PARAM = "app_key";


    // Application ID KEY
    final static String APP_ID = "bdd692eb";

    // Application ID PARAM
    final static String APP_ID_PARAM = "app_id";

    // URL for base search for api
    final static String RECIPE_BASE_URL = "https://api.edamam.com/search";

    // Search query
    final static String PARAM_QUERY = "q";

    // Default limit of search results. Will be overwritten if user enters a custom parameter
    static String limitAmount = "0";

    // Limit Query
    final static String LIMIT_QUERY = "from";

    // Default offset of search results. Will be overwritten if user enters a custom parameter
    static String offsetAmount = "10";

    // Offset Query
    final static String OFFSET_QUERY = "to";


    // Minimum Calories. TEMP
    static String calories = "0-1000";

    // Calories Query
    final static String CALORIES_QUERY = "calories";

    // Generate URL for GET request and catch formatting errors
    public static URL buildUrl(String recipeSearchQuery) {
        Uri builtUri = Uri.parse(RECIPE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, recipeSearchQuery)
                .appendQueryParameter(APP_ID_PARAM, APP_ID)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LIMIT_QUERY, limitAmount)
                .appendQueryParameter(OFFSET_QUERY, offsetAmount)
                .appendQueryParameter(CALORIES_QUERY, calories)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    // Return result for GET request and catch connection error
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
