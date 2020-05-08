package com.seank.kotlinflowplayground.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.seank.kotlinflowplayground.R
import com.seank.kotlinflowplayground.domain.Card


class CardViewPagerAdapter(private val cardList: List<Card>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(container.context).inflate(R.layout.item_card, container, false)
        val cardImage: ImageView = view.findViewById(R.id.card_image)
        val cardName: TextView = view.findViewById(R.id.card_name)
        Glide.with(cardImage).load(cardList[position].imgUrl).into(cardImage)
        cardName.text = cardList[position].name
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view == o
    }

    override fun getCount(): Int {
        return cardList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as View)
    }
}