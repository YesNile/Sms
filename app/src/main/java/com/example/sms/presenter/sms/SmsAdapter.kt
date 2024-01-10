package com.example.sms.presenter.sms

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sms.R
import com.example.sms.data.DataMessage
import com.example.sms.databinding.DataMessageItemBinding

class SmsAdapter(
    private val onItemClick: (DataMessage) -> Unit,
) : RecyclerView.Adapter<SmsAdapter.SmsEntryViewHolder>() {

    private val list = mutableListOf<DataMessage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsEntryViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataMessageItemBinding.inflate(layoutInflater, parent, false)
        return SmsEntryViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: SmsEntryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<DataMessage>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    inner class SmsEntryViewHolder(
        private val binding: DataMessageItemBinding,
        private val onItemClick: (DataMessage) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: DataMessage) = with(binding) {
            val context = binding.root.context

            smsAvatar.background = getRandomBackground(context)
            smsAvatar.text = entry.address.first().toString()

            smsSender.text = entry.address
            messageAgenda.text = entry.messages.first()

            root.setOnClickListener {
                onItemClick(entry)
            }
        }

        private fun getRandomBackground(context: Context): Drawable? {
            val background =
                ContextCompat.getDrawable(context, R.drawable.sms_chat_entry_avatar)
            val color = context.resources.getIntArray(R.array.rainbow).random()
            background?.let { DrawableCompat.setTint(it, color) }
            return background
        }
    }
}