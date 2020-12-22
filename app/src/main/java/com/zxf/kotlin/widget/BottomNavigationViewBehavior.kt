package com.zxf.kotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class BottomNavigationViewBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        child.translationY = abs(dependency.y-80)
        return true
    }
}