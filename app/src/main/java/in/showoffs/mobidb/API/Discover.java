package in.showoffs.mobidb.API;

import android.text.TextUtils;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.showoffs.mobidb.utils.Constants;

/**
 * Created by nagraj on 22/7/16.
 */

public class Discover {

    private static final String PARAM_PRIMARY_RELEASE_YEAR = "primary_release_year";
    private static final String PARAM_VOTE_COUNT_GTE = "vote_count.gte";
    private static final String PARAM_VOTE_AVERAGE_GTE = "vote_average.gte";
    private static final String PARAM_WITH_GENRES = "with_genres";
    private static final String PARAM_WITH_KEYWORKDS = "with_keywordss";
    private static final String PARAM_RELEASE_DATE_GTE = "release_date.gte";
    private static final String PARAM_RELEASE_DATE_LTE = "release_date.lte";
    private static final String PARAM_CERTIFICATION_COUNTRY = "certification_country";
    private static final String PARAM_CERTIFICATION_LTE = "certification.lte";
    private static final String PARAM_WITH_COMPANIES = "with_companies";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final int YEAR_MIN = 1900;
    private static final int YEAR_MAX = 2100;
    /*
    * TmdbApi API Base URL
    */
    private static final String TMDB_API_BASE = "https://api.themoviedb.org/3/";
    private static final String APPEND_TO_RESPONSE = "append_to_response";
    private final Map<String, String> params = new HashMap<>();
    private String baseUrl;


    /**
     * Get the parameters
     * <p/>
     * This will be used to construct the URL in the API
     *
     * @return
     */
    public Map<String, String> getParams() {
        return params;
    }


    /**
     * Minimum value is 1 if included.
     *
     * @param page
     * @return
     */
    public Discover page(Integer page) {
        if (page != null && page > 0) {
            params.put(Constants.PARAM_PAGE, String.valueOf(page));
        }
        return this;
    }


    public Discover apiKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            params.put(Constants.PARAM_API_KEY, key);
        }
        return this;
    }

    public Discover withPath(String... path) {
        StringBuilder baseUrlBuilder = new StringBuilder(TMDB_API_BASE);

        for (int i = 0; i < path.length; i++) {
            baseUrlBuilder.append(path[i]);

            if (i < path.length - 1) {
                baseUrlBuilder.append("/");
            }
        }

        baseUrl = baseUrlBuilder.toString();
        return this;
    }

    /**
     * ISO 639-1 code
     *
     * @param language
     * @return
     */
    public Discover language(String language) {
        if (!TextUtils.isEmpty(language)) {
            params.put(Constants.PARAM_LANGUAGE, language);
        }
        return this;
    }


    /**
     * Available options are <br> vote_average.desc<br> vote_average.asc<br> release_date.desc<br> release_date.asc<br>
     * popularity.desc<br> popularity.asc
     *
     * @param sortBy
     * @return
     */
    public Discover sortBy(String sortBy) {
        if (!TextUtils.isEmpty(sortBy)) {
            params.put(PARAM_SORT_BY, sortBy);
        }
        return this;
    }


    /**
     * Toggle the inclusion of adult titles
     *
     * @param includeAdult
     * @return
     */
    public Discover includeAdult(boolean includeAdult) {
        params.put(Constants.PARAM_ADULT, String.valueOf(includeAdult));
        return this;
    }


    /**
     * Filter the results release dates to matches that include this value.
     *
     * @param year
     * @return
     */
    public Discover year(int year) {
        if (checkYear(year)) {
            params.put(Constants.PARAM_YEAR, String.valueOf(year));
        }
        return this;
    }

    /**
     * check the year is between the min and max
     *
     * @param year
     * @return
     */
    private boolean checkYear(int year) {
        return (year >= YEAR_MIN && year <= YEAR_MAX);
    }

    /**
     * Filter the results so that only the primary release date year has this value
     *
     * @param primaryReleaseYear
     * @return
     */
    public Discover primaryReleaseYear(int primaryReleaseYear) {
        if (checkYear(primaryReleaseYear)) {
            params.put(PARAM_PRIMARY_RELEASE_YEAR, String.valueOf(primaryReleaseYear));
        }
        return this;
    }

    /**
     * Only include movies that are equal to, or have a vote count higher than this value
     *
     * @param voteCountGte
     * @return
     */
    public Discover voteCountGte(int voteCountGte) {
        if (voteCountGte > 0) {
            params.put(PARAM_VOTE_COUNT_GTE, String.valueOf(voteCountGte));
        }
        return this;
    }

    /**
     * Only include movies that are equal to, or have a higher average rating than this value
     *
     * @param voteAverageGte
     * @return
     */
    public Discover voteAverageGte(float voteAverageGte) {
        if (voteAverageGte > 0) {
            params.put(PARAM_VOTE_AVERAGE_GTE, String.valueOf(voteAverageGte));
        }
        return this;
    }

    /**
     * Only include movies with the specified genres.
     * <p/>
     * Expected value is an integer (the id of a genre).
     * <p/>
     * Multiple values can be specified.
     * <p/>
     * Comma separated indicates an 'AND' query, while a pipe (|) separated value indicates an 'OR'
     *
     * @param withGenres
     * @return
     */
    @Deprecated
    public Discover withGenres(String withGenres) {
        if (!TextUtils.isEmpty(withGenres)) {
            params.put(PARAM_WITH_GENRES, withGenres);
        }
        return this;
    }

    /**
     * The minimum release to include.
     * <p/>
     * Expected format is YYYY-MM-DD.
     *
     * @param releaseDateGte
     * @return
     */
    public Discover releaseDateGte(String releaseDateGte) {
        if (!TextUtils.isEmpty(releaseDateGte)) {
            params.put(PARAM_RELEASE_DATE_GTE, releaseDateGte);
        }
        return this;
    }

    /**
     * The maximum release to include.
     * <p/>
     * Expected format is YYYY-MM-DD.
     *
     * @param releaseDateLte
     * @return
     */
    public Discover releaseDateLte(String releaseDateLte) {
        if (!TextUtils.isEmpty(releaseDateLte)) {
            params.put(PARAM_RELEASE_DATE_LTE, releaseDateLte);
        }
        return this;
    }

    /**
     * Only include movies with certifications for a specific country.
     * <p/>
     * When this value is specified, 'certificationLte' is required.
     * <p/>
     * A ISO 3166-1 is expected
     *
     * @param certificationCountry
     * @return
     */
    public Discover certificationCountry(String certificationCountry) {
        if (!TextUtils.isEmpty(certificationCountry)) {
            params.put(PARAM_CERTIFICATION_COUNTRY, certificationCountry);
        }
        return this;
    }

    /**
     * Only include movies with this certification and lower.
     * <p/>
     * Expected value is a valid certification for the specified 'certificationCountry'.
     *
     * @param certificationLte
     * @return
     */
    public Discover certificationLte(String certificationLte) {
        if (!TextUtils.isEmpty(certificationLte)) {
            params.put(PARAM_CERTIFICATION_LTE, certificationLte);
        }
        return this;
    }

    /**
     * Filter movies to include a specific company.
     * <p/>
     * Expected value is an integer (the id of a company).
     * <p/>
     * They can be comma separated to indicate an 'AND' query
     *
     * @param withCompanies
     * @return
     */
    public Discover withCompanies(String withCompanies) {
        if (!TextUtils.isEmpty(withCompanies)) {
            params.put(PARAM_WITH_COMPANIES, withCompanies);
        }
        return this;
    }

    public URL buildUrl() {
        StringBuilder urlBuilder = new StringBuilder(baseUrl == null
                ? TMDB_API_BASE + "discover/movie"
                : baseUrl);

        try {

            if (params.size() > 0) {
                List<String> keys = new ArrayList<String>(params.keySet());
                for (int i = 0; i < keys.size(); i++) {
                    urlBuilder.append(i == 0 ? "?" : "&");
                    String paramName = keys.get(i);

                    urlBuilder.append(paramName).append("=");
                    urlBuilder.append(URLEncoder.encode(params.get(paramName), "UTF-8"));
                }
            }

            return new URL(urlBuilder.toString());

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}