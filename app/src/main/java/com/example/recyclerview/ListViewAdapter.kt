package com.example.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast

class ListViewAdapter(val list: List<MainActivity.Person>) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(idx: Int): MainActivity.Person {
        return list[idx]
    }

    override fun getItemId(idx: Int): Long {
        return idx.toLong()
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        // 1.
        var view = convertView

        // В таком виде нет толку от ViewHolder.
//        if (view == null) {
        viewHolder = ViewHolder()
        view = LayoutInflater.from(parent?.context).inflate(
            R.layout.item, parent, false
        )
        viewHolder.parent = view
        viewHolder.counter = view.findViewById(R.id.ItemNum)
        viewHolder.firstname = view.findViewById(R.id.ItemFirstname)
        viewHolder.lastname = view.findViewById(R.id.ItemLastname)
        // 2.
        view.tag = viewHolder
//        } else {
//            viewHolder = view.tag as ViewHolder
//        }

        if (idx % 3 == 0)
            viewHolder.counter.text = idx.toString()
        viewHolder.firstname.text = getItem(idx).name
        viewHolder.lastname.text = getItem(idx).surname
        return viewHolder.parent
    }

    class ViewHolder {
        lateinit var parent: View
        lateinit var counter: TextView
        lateinit var firstname: TextView
        lateinit var lastname: TextView
    }

}