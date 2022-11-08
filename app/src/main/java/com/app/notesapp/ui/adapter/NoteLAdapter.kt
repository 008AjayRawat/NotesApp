package com.app.notesapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.notesapp.R
import com.app.notesapp.databinding.NoteItemsBinding
import com.app.notesapp.persistence.Note
import com.app.notesapp.util.DateUtils
import com.bumptech.glide.Glide


class NoteAdapter(noteList: List<Note>, private val interaction: Interaction? = null) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val notes = mutableListOf<Note>()

    init {
        notes.addAll(noteList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<NoteItemsBinding>(LayoutInflater.from(parent.context), R.layout.note_items, parent, false)
        return ViewHolder(binding, interaction)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = notes[position])
    }


    fun swap(notes: List<Note>) {
        val diffCallback = DiffCallback(this.notes, notes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.notes.clear()
        this.notes.addAll(notes)
        diffResult.dispatchUpdatesTo(this)
    }


    class ViewHolder(private val itemViewBinding: NoteItemsBinding, private val interaction: Interaction?) : RecyclerView.ViewHolder(itemViewBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Note) {
            item.updatedDate?.let { date ->
                if (item.createdDate.equals(item.updatedDate).not()) {
                    itemViewBinding.txtEditedTime.visibility = View.VISIBLE
                    itemViewBinding.txtEditedTime.text = "Edited on : ${DateUtils.convertMillsToDateFormat(date.toLong())}"
                } else {
                    itemViewBinding.txtEditedTime.visibility = View.GONE
                }
            } ?: kotlin.run {
                itemViewBinding.txtEditedTime.visibility = View.GONE
            }


            itemViewBinding.txtTitle.text = item.title
            itemViewBinding.txtDescription.text = item.description

            takeIf { item.tag.isNullOrEmpty().not() }?.let {
                itemViewBinding.iv.visibility = View.VISIBLE
                Glide.with(itemViewBinding.iv.context)
                    .load(item.tag)
                    .into(itemViewBinding.iv)
            } ?: kotlin.run {
                itemViewBinding.iv.visibility = View.GONE
            }

            itemViewBinding.txtTag.text = item.tag

            //Handle item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Note)
    }
}