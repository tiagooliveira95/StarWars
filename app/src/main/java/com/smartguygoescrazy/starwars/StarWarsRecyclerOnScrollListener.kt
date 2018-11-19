package com.smartguygoescrazy.starwars

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class StarWarsRecyclerOnScrollListener : RecyclerView.OnScrollListener() {


    private var mLoading : Boolean = false
    private var mPreviousTotal = 0
    var endReached : Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if(endReached) return

        val visibleItemsCount = recyclerView.childCount
        val itemsCount = recyclerView.layoutManager?.itemCount
        val firstVisibleCount = ((recyclerView.layoutManager) as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

        if(mLoading){
            if(itemsCount!! > mPreviousTotal){
                mLoading = false
                mPreviousTotal = itemsCount
            }
        }

        val visibleThresh = 6
        if(!mLoading && (itemsCount!! - visibleItemsCount) <= (firstVisibleCount + visibleThresh)){
            onLoadRequest()
            mLoading = true
        }
    }


    abstract fun onLoadRequest()
}