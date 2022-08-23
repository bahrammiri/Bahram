package com.bahram.weather7
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.bahram.weather7.model.Final

class ViewPagerActivity : FragmentActivity() {

    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)


        val myFinal: ArrayList<Final> = intent.getParcelableExtra("Intent to ViewPagerActivity")!!

//        val sportsList: List<Final> = Sports.sportsList(applicationContext)

        adapter = NumberAdapter(this, myFinal as ArrayList<Final>)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = adapter
    }
}
