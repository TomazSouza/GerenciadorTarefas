package tom.dev.com.taskmanagement.utility

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerTouchListener(context: Context?, recyclerView: RecyclerView?, clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

    private var mClickListener: ClickListener? = clickListener
    private var mGestureDetector: GestureDetector? = null

    init {
        this.mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent?) {

                val child = e?.x?.let { recyclerView?.findChildViewUnder(it, e.y) }
                if (child != null && clickListener != null) {
                    recyclerView?.getChildAdapterPosition(child)?.let { clickListener.onLongClick(child, it) }
                }

            }

        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && mClickListener != null && mGestureDetector?.onTouchEvent(e) != null) {
            mClickListener?.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {  }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) { }

}