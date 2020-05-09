package ir.dimyadi.mywidget.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import ir.dimyadi.mywidget.R
import ir.dimyadi.mywidget.events.EventsAdapter.MyViewHolder
import java.util.*

/**
 * @author MEHDIMYADI
 **/

internal class EventsAdapter(
    private val allEvents: ArrayList<String>
) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_textview, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.event.text = allEvents[position]
        //holder.itemView.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Toast.makeText(context, allEvents.get(position), Toast.LENGTH_SHORT).show();
        //    }
        //});
    }

    override fun getItemCount(): Int {
        return allEvents.size
    }

    internal class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var event: TextView = itemView.findViewById(R.id.events)

        init {
            TextViewCompat.setAutoSizeTextTypeWithDefaults(
                event,
                TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
            )
        }
    }

}