package com.dzakyadlh.storytell.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.dzakyadlh.storytell.R

internal class StackRemoteViewsFactory(private val mContext: Context):RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {}

    override fun onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_img2))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_img3))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_img4))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_img5))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.example_img6))
    }

    override fun onDestroy() {}

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
        val extras = bundleOf(
            StorytellStackWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}