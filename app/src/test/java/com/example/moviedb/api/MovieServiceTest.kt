
import com.example.moviedb.Interface.Popinterface
import com.example.moviedb.Util.Config.Companion.API_KEY
import com.example.moviedb.api.ApiAbstract
import java.io.IOException
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class PopinterfaceTest : ApiAbstract<Popinterface>() {

  private lateinit var service: Popinterface

  @Before
  fun initService() {
    this.service = createService(Popinterface::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchGetPopular() {
    enqueueResponse("/tmdb_get_popular.json")
    val response = service.getPopular(API_KEY,"1").execute()
    assertThat(response.body()?.page, `is`("1"))
    assertThat(response.body()?.total_pages, `is`("500"))
  }

  @Throws(IOException::class)
  @Test
  fun fetchGetToprated() {
    enqueueResponse("/tmdb_get_toprated.json")
    val response = service.getToprated(API_KEY,"1").execute()
    assertThat(response.body()?.page, `is`("1"))
    assertThat(response.body()?.total_pages, `is`("373"))
  }

  @Throws(IOException::class)
  @Test
  fun fetchGetUpcoming() {
    enqueueResponse("/tmdb_get_upcoming.json")
    val response = service.getUpcoming(API_KEY,"1").execute()
    assertThat(response.body()?.page, `is`("1"))
    assertThat(response.body()?.total_pages, `is`("7"))
  }

  @Throws(IOException::class)
  @Test
  fun fetchGetNowplaying() {
    enqueueResponse("/tmdb_get_now_playing.json")
    val response = service.getNowplaying(API_KEY,"1").execute()
    assertThat(response.body()?.page, `is`("1"))
    assertThat(response.body()?.total_pages, `is`("29"))
  }

  @Throws(IOException::class)
  @Test
  fun fetchGetSearchMovie() {
    enqueueResponse("/tmdb_get_search_movie.json")
    val response = service.getSearchMovie(API_KEY,"1").execute()
    assertThat(response.body()!!.results[0].id, `is`("330470"))
    assertThat(response.body()!!.results[0].original_title, `is`("Wa-shoku ~Beyond Sushi~"))
    assertThat(response.body()!!.results[0].poster_path, `is`("/1m5URgOde4Kzg3rZiyX9E2Vmxo2.jpg"))

  }


  @Throws(IOException::class)
  @Test
  fun fetchGetMovie() {
    enqueueResponse("/tmdb_get_movie.json")
    val response = service.getmovies(1,API_KEY).execute()
    assertThat(response.body()?.original_title, `is`("ArtemisFowl"))
    assertThat(response.body()?.poster_path, `is`("/tI8ocADh22GtQFV28vGHaBZVb0U.jpg"))
  }



}
