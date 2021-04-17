package com.example.project_1

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabsIntent
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var nadapter: ListAdapter
    lateinit var toggle: ActionBarDrawerToggle
     var  isMemberVisible:Boolean= false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //recyclerview
        fetchDataByHealth("in") //calling this by default
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        nadapter= ListAdapter(this)
        recyclerView.adapter=nadapter
        //#For Night Mode
        val sharePref: SharedPreferences =getSharedPreferences("SharedPref",0)
        val isNightModeOn:Boolean=sharePref.getBoolean("NightMode",false)
        val sharedPredEdit: SharedPreferences.Editor=sharePref.edit()
        if(isNightModeOn)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        fun lightMode()//light mode
        {
           // if(isNightModeOn)
            {

            }
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPredEdit.putBoolean("NightMode", false)
            sharedPredEdit.apply()
//            else
//            {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                sharedPredEdit.putBoolean("NightMode", true)
//                sharedPredEdit.apply()
//            }
        }

        fun nightMode()//Night Mode
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPredEdit.putBoolean("NightMode", true)
            sharedPredEdit.apply()
        }
        //Notification Drawer
        val draw=findViewById<DrawerLayout>(R.id.drawer_layout)
        toggle= ActionBarDrawerToggle(this,draw,R.string.open,R.string.close)
        draw.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val nav=findViewById<NavigationView>(R.id.nav_view)
        nav.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.darkMode->nightMode()
                R.id.lightMode->lightMode()
                //call by headlines
                R.id.india->fetchDataByHeadlines("in")
                R.id.france->fetchDataByHeadlines("fr")
                R.id.australia->fetchDataByHeadlines("au")
                R.id.russia->fetchDataByHeadlines("ru")
                R.id.uk->fetchDataByHeadlines("gb")
                R.id.usa->fetchDataByHeadlines("us")
                //call by health
                R.id.india_h->fetchDataByHealth("in")
                R.id.france_h->fetchDataByHealth("fr")
                R.id.australia_h->fetchDataByHealth("au")
                R.id.russia_h->fetchDataByHealth("ru")
                R.id.uk_h->fetchDataByHealth("gb")
                R.id.usa_h->fetchDataByHealth("us")
                //call by science
                R.id.india_s->fetchDataByScience("in")
                R.id.france_s->fetchDataByScience("fr")
                R.id.australia_s->fetchDataByScience("au")
                R.id.russia_s->fetchDataByScience("ru")
                R.id.uk_s->fetchDataByScience("gb")
                R.id.usa_s->fetchDataByScience("us")
                //call by technology
                R.id.india_t->fetchDataByScience("in")
                R.id.france_t->fetchDataByScience("fr")
                R.id.australia_t->fetchDataByScience("au")
                R.id.russia_t->fetchDataByScience("ru")
                R.id.uk_t->fetchDataByScience("gb")
                R.id.usa_t->fetchDataByScience("us")
                //call by channels
                R.id.bbc->fetchDataAccordingToChannel("bbc-news")
                R.id.cnn->fetchDataAccordingToChannel("cnn")
                R.id.fox->fetchDataAccordingToChannel("fox-news")
                R.id.google->fetchDataAccordingToChannel("google-news")
            }
            true
        }
        //Main
    }
    //Fetch data as per headlines
    private fun fetchDataByHeadlines(country:String)
    {
        val requestQueue= Volley.newRequestQueue(this)
       val url= "https://saurav.tech/NewsAPI/top-headlines/category/general/${country}.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val newsJsonArray = response.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    nadapter.updateNews(newsArray)
                },
                { error ->
                    val intent= Intent(this,VolleyErrorActivity::class.java)
                    startActivity(intent)
                }
        )
      //requestQueue.add(jsonObjectRequest) another way to call make request
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    private fun fetchDataByHealth(country:String)
    {
        val requestQueue= Volley.newRequestQueue(this)
        val url= "https://saurav.tech/NewsAPI/top-headlines/category/health/${country}.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val newsJsonArray = response.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    nadapter.updateNews(newsArray)
                },
                { error ->
                    val intent= Intent(this,VolleyErrorActivity::class.java)
                    startActivity(intent)
                }
        )
        //requestQueue.add(jsonObjectRequest) another way to call make request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    private fun fetchDataByScience(country:String)
    {
        val requestQueue= Volley.newRequestQueue(this)
        val url= "https://saurav.tech/NewsAPI/top-headlines/category/science/${country}.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val newsJsonArray = response.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    nadapter.updateNews(newsArray)
                },
                { error ->
                    val intent= Intent(this,VolleyErrorActivity::class.java)
                    startActivity(intent)
                }
        )
        //requestQueue.add(jsonObjectRequest) another way to call make request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    private fun fetchDataByTech(country:String)
    {
        val requestQueue= Volley.newRequestQueue(this)
        val url= "https://saurav.tech/NewsAPI/top-headlines/category/technology/${country}.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val newsJsonArray = response.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    nadapter.updateNews(newsArray)
                },
                { error ->
                    val intent= Intent(this,VolleyErrorActivity::class.java)
                    startActivity(intent)
                }
        )
        //requestQueue.add(jsonObjectRequest) another way to call make request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun fetchDataAccordingToChannel(channel_name:String)
    {
        val requestQueue= Volley.newRequestQueue(this)
        val url= "https://saurav.tech/NewsAPI/everything/${channel_name}.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val newsJsonArray = response.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    nadapter.updateNews(newsArray)
                },
                { error ->
                    val intent= Intent(this,VolleyErrorActivity::class.java)
                    startActivity(intent)
                }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    //
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return  true
        }
        return super.onContextItemSelected(item)

    }
    override fun onItemClicked(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}