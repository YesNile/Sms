package com.example.sms.presenter.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sms.data.Message
import com.example.sms.R
import com.example.sms.databinding.DialogItemBinding

class DialogAdapter() : RecyclerView.Adapter<DialogAdapter.ChatViewHolder>() {

    private val list = mutableListOf<Message>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = DialogItemBinding.inflate(layoutInflater, parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ChatViewHolder(private val binding: DialogItemBinding, ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Message) = with(binding) {
            val context = binding.root.context
            smsAvatar.background = getRandomBackground(context)
            smsAvatar.text = entry.address.first().toString()
            smsSender.text = entry.address
            messageAgenda.text = entry.message
        }

        private fun getRandomBackground(context: Context): Drawable? {
            val background = ContextCompat.getDrawable(context, R.drawable.sms_chat_entry_avatar)
            val color = context.resources.getIntArray(R.array.rainbow).random()
            background?.let { DrawableCompat.setTint(it, color) }
            return background
        }
    }

    fun submitList(list: List<Message>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }
}