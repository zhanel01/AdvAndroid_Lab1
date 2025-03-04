package com.example.lab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.databinding.ItemCalendarEventBinding

data class CalendarEvent(
    val title: String,
    val startTime: String,
    val endTime: String
)

class CalendarEventsAdapter(private val events: List<CalendarEvent>) :
    RecyclerView.Adapter<CalendarEventsAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding: ItemCalendarEventBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCalendarEventBinding.inflate(inflater, parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.binding.ceStart.text = event.startTime
        holder.binding.ceEnd.text = event.endTime
        holder.binding.ceTitle.text = event.title
    }

    override fun getItemCount(): Int = events.size
}
