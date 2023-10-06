package com.example.hahalolofake.ui.equalizer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hahalolofake.R
import com.example.hahalolofake.data.models.Effect
import com.example.hahalolofake.databinding.ItemEffectBinding

class EqualizerAdapter(
    private var data: List<Effect>,
    private val context: Context,
    private var selectedPosition: Int,
    private var listener: EqualizerListener
) : RecyclerView.Adapter<EqualizerAdapter.EqualizerViewHolder>() {

    companion object {
        val dummyData = listOf(
            Effect(name = "Normal", image = R.drawable.normal),
            Effect(name = "Classical", image = R.drawable.classical),
            Effect(name = "Dance", image = R.drawable.dance),
            Effect(name = "Flat", image = R.drawable. flat),
            Effect(name = "Folk", image = R.drawable.folk),
            Effect(name = "Heavy Metal", image = R.drawable.heavy),
            Effect(name = "Hip Hop", image = R.drawable.hiphop),
            Effect(name = "Jazz", image = R.drawable.jazz),
            Effect(name = "Pop", image = R.drawable.pop),
            Effect(name = "Rock", image = R.drawable.rock)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqualizerViewHolder {
        return EqualizerViewHolder.from(parent, context)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: EqualizerViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position == selectedPosition)
        holder.binding.root.setOnClickListener {
            data[selectedPosition].isSelected = false
            selectedPosition = position
            data[selectedPosition].isSelected = true
            notifyDataSetChanged()
            listener.onClickEffect(position, item)
            savePosition(position)
        }
    }

    class EqualizerViewHolder(val binding: ItemEffectBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Effect, isSelected: Boolean) {
            binding.apply {
                itemEffect.text = item.name ?: ""
                if (isSelected) {
                    itemEffect.setBackgroundResource(R.drawable.bg_equalizer_select)
                } else {
                    itemEffect.setBackgroundResource(R.drawable.bg_effect)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup, context: Context): EqualizerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEffectBinding.inflate(layoutInflater, parent, false)
                return EqualizerViewHolder(binding, context)
            }
        }
    }

    private fun savePosition(position: Int) {
        val pref = context.getSharedPreferences(
            "myPref", AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putInt("positionEffect", position)
        editor.commit()
    }
}

interface EqualizerListener {
    fun onClickEffect(position: Int, item: Effect)
}