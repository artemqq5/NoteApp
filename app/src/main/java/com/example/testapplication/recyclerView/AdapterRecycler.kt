package com.example.testapplication.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.database.TableNote
import com.example.testapplication.databinding.ItemNoteBinding
import com.example.testapplication.helperDate.DateParsing
import kotlin.reflect.KProperty0


class AdapterRecycler(
    var dataList: List<TableNote>, private val transitionToEdit: (item: TableNote) -> Unit
) : RecyclerView.Adapter<AdapterRecycler.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: ItemNoteBinding = ItemNoteBinding.bind(view)


        fun bindView(item: TableNote, toEdit: (item: TableNote) -> Unit) {

            binding.titleNote.text = item.title
            binding.descriptionNote.text = item.description
            binding.dateNote.text = DateParsing.getFormattedDate(item.date!!)

            itemView.setOnClickListener {
                // go to edit fragment
                toEdit(item)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataList[position], transitionToEdit)


    }

    override fun getItemCount() = dataList.size


    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<TableNote>) {
        dataList = newList.reversed()
        notifyDataSetChanged()
    }

}