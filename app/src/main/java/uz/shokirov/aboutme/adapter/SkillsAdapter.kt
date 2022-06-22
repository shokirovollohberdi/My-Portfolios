package uz.shokirov.aboutme.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.shokirov.aboutme.model.Skills
import uz.shokirov.aboutme.databinding.ItemSkillBinding

class SkillsAdapter(var list: List<Skills>) :
    RecyclerView.Adapter<SkillsAdapter.Vh>() {
    inner class Vh(var itemRv: ItemSkillBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun onBind(skills: Skills, position: Int) {
            itemRv.tvSkill.text = skills.skill
            itemRv.precent.let {
                it.setSecondaryProgress(skills.percent!!)
            }
            itemRv.tvPercent.text = "${skills.percent}%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}


