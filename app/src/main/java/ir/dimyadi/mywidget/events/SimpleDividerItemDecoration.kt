package ir.dimyadi.mywidget.events

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import ir.dimyadi.mywidget.R

/**
 * @author MEHDIMYADI
 **/

class SimpleDividerItemDecoration(context: Context?) : ItemDecoration() {

    private val mDivider: Drawable? =
        ContextCompat.getDrawable(context!!, R.drawable.recycler_view_divider)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.adapter!!.itemCount
        for (i in 0 until childCount) {
            if (i == childCount - 1) {
                continue
            }
            val child = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
            return
        }
    }
}