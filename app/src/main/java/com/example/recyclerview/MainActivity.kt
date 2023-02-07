package com.example.recyclerview

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    data class Person(val name: String, val surname: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content: String = assets.open("test.json").bufferedReader().use { it.readText() }
        val jsonArr = JSONArray(content)
        val people: MutableList<Person> = ArrayList(jsonArr.length())
        for (i in 0 until jsonArr.length()) {
            val obj = jsonArr.getJSONObject(i)
            val name = obj.getJSONObject("name")

            people.add(Person(name.getString("firstname"), name.getString("lastname")))
        }
        Log.i(TAG, people.toString())

        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        //recyclerView.layoutManager = GridLayoutManager(this, 5)

        val decor = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(decor)
        recyclerView.addItemDecoration(VerticalDecor(160))
        val adapter = MyRecyclerViewAdapter(people)
        recyclerView.adapter = adapter


        findViewById<Button>(R.id.button).setOnClickListener {
            people.removeAt(0)
            adapter.notifyItemRemoved(0)
            for (i in 0..6) {
                if (i % 2 == 0 || i % 3 == 0) {
                    adapter.notifyItemChanged(i)
                }
            }
//            adapter.notifyItemRangeChanged()
//            adapter.notifyDataSetChanged()
        }

//        val listView: ListView = findViewById(R.id.RecyclerView)
//        listView.adapter = ListViewAdapter(people)

    }

    class VerticalDecor(val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = space;
        }
    }

    class MyRecyclerViewAdapter(val people: List<Person>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var counter: TextView
            var firstname: TextView
            var lastname: TextView

            init {
                counter = itemView.findViewById(R.id.ItemNum)
                firstname = itemView.findViewById(R.id.ItemFirstname)
                lastname = itemView.findViewById(R.id.ItemLastname)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.counter.text = ""
            if (position % 3 == 0)
                holder.counter.text = position.toString()
            holder.firstname.text = people[position].name
            holder.lastname.text = people[position].surname
        }

        override fun getItemCount(): Int {
            return people.size
        }

    }

}