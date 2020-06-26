package com.example.moviedb.database

import com.example.moviedb.utils.MockTestUtil.Companion.mockFavourite
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class MovieDaoTest : LocalDatabase() {

  @Test
  fun insertAndReadTest() {
    db.FavDao().insertRow(mockFavourite())
    val loadFromDB = db.FavDao().getallfav()[0]
    assertThat(loadFromDB.id.toString(), `is`("1"))
    assertThat(loadFromDB.movie_id, `is`("123"))
    assertThat(loadFromDB.path, `is`("/tes/testpath"))
  }

}
