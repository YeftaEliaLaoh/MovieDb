package com.example.moviedb.Controller

import android.view.View
import android.widget.LinearLayout

class MainActivityController {

    private var navigationIsShowing = false

    fun showOrHideNavigation(navigationLayout: LinearLayout) {
        if (navigationIsShowing) {
            navigationIsShowing = false
            navigationLayout.visibility = View.GONE
        } else {
            navigationIsShowing = true
            navigationLayout.visibility = View.VISIBLE
        }

    }

}
