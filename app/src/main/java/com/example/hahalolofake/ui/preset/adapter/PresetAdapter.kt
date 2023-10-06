package com.example.hahalolofake.ui.preset.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hahalolofake.R
import com.example.hahalolofake.data.models.Effect
import com.example.hahalolofake.data.models.EffectSound
import com.example.hahalolofake.databinding.ItemEffectSoundBinding

class PresetAdapter(
    private var data: MutableList<EffectSound>,
    private val context: Context,
    private var selectedPosition: Int,
    private var listener: PresetListener
) : RecyclerView.Adapter<PresetAdapter.PresetViewHolder>() {

    companion object {
        val dummyData = listOf(
            EffectSound(name = "Normal", image = R.drawable.normal),
            EffectSound(name = "Classical", image = R.drawable.classical),
            EffectSound(name = "Dance", image = R.drawable.dance),
            EffectSound(name = "Flat", image = R.drawable. flat),
            EffectSound(name = "Folk", image = R.drawable.folk),
            EffectSound(name = "Heavy Metal", image = R.drawable.heavy),
            EffectSound(name = "Hip Hop", image = R.drawable.hiphop),
            EffectSound(name = "Jazz", image = R.drawable.jazz),
            EffectSound(name = "Pop", image = R.drawable.pop),
            EffectSound(name = "Rock", image = R.drawable.rock)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresetViewHolder {
        return PresetViewHolder.from(parent, context)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PresetViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position == selectedPosition)
        holder.binding.root.setOnClickListener {
            data[selectedPosition].isSelected = false
            selectedPosition = position
            data[selectedPosition].isSelected = true
            notifyDataSetChanged()
            listener.onClickSoundEffect(position, item)
            savePosition(position)
        }
        holder.binding.menuBtn.setOnClickListener {
            listener.onDeleteEffect(position, item)
        }
    }

    class PresetViewHolder(val binding: ItemEffectSoundBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EffectSound, isSelected: Boolean) {
            binding.apply {
                soundTxt.text = item.name ?: ""
                Glide.with(context)
                    .load(item.image)
                    .into(binding.soundImg)

                if (isSelected) {
                    soundImg.setBackgroundResource(R.drawable.bg_item_preset_v2)
                } else {
                    soundImg.setBackgroundResource(R.drawable.bg_item_preset_unselect)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup, context: Context): PresetViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEffectSoundBinding.inflate(layoutInflater, parent, false)
                return PresetViewHolder(binding, context)
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

interface PresetListener {
    fun onClickSoundEffect(position: Int, item: EffectSound)
    fun onDeleteEffect(position: Int, item: EffectSound)
}