package cl.ucn.disc.dsm.avejar.earthquakemaps.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.avejar.earthquakemaps.models.EarthQuakeContent;
import cl.ucn.disc.dsm.avejar.earthquakemaps.adapter.JSDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public final class UsgsController {

    /**
     * USGS API Service
     */
    public interface UsgsService {

        @GET("query?format=geojson&endtime&limit=150&orderby=magnitude")
        Call<List<EarthQuakeContent>> getData();
    }

    /**
     * JSON Deserializer
     */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(List.class, new JSDeserializer<List<EarthQuakeContent>>())
            .create();

    /**
     * Http interceptor
     */
    private static final HttpLoggingInterceptor interceptor;

    static {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    /**
     * OkHttpClient instance connect
     */
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();

    /**
     * Retrofit instance
     */
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    /**
     * Begin the service by the retrofit instance
     */
    private static final UsgsService service = retrofit.create(UsgsService.class);

    /**
     * Call the service
     *
     * @return
     * @throws IOException
     */
    public static List<EarthQuakeContent> getQuake() throws IOException {

        Call<List<EarthQuakeContent>> call = service.getData();
        return call.execute().body();
    }
}
