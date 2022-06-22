package uz.shokirov.aboutme.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.shokirov.aboutme.databinding.ItemPortfoliosBinding
import uz.shokirov.aboutme.model.Skills
import uz.shokirov.aboutme.databinding.ItemSkillBinding
import uz.shokirov.aboutme.model.Portfolios

class PortfolioAdapter(var list: List<Portfolios>, var onClick: OnClick) :
    RecyclerView.Adapter<PortfolioAdapter.Vh>() {
    inner class Vh(var itemRv: ItemPortfoliosBinding) : RecyclerView.ViewHolder(itemRv.root) {
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun onBind(portfolios: Portfolios, position: Int) {
            itemRv.itemImage.load(portfolios.imageUrl)
            itemRv.tvName.text = portfolios.name
            itemRv.root.setOnClickListener {
                onClick.click(list[position], position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPortfoliosBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}

interface OnClick {
    fun click(portfolios: Portfolios, position: Int)
}


