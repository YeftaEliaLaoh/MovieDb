package com.example.moviedb.utils

import com.example.moviedb.Database.Favourite

class MockTestUtil {
    companion object {
        fun mockFavourite() = Favourite(1, "123", "/tes/testpath")
        fun mockFavouriteList(): List<Favourite> {
            val favourite = ArrayList<Favourite>()
            favourite.add(mockFavourite())
            favourite.add(mockFavourite())
            favourite.add(mockFavourite())
            return favourite
        }
    }
}
