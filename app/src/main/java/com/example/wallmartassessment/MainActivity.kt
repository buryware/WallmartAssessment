package com.example.wallmartassessment
//
// written by Steve Stansbury   10/27/2022
//
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var viewItems: MutableList<Any?>? = ArrayList()

    private var mAdapter: RecyclerAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.my_recycler_view) as RecyclerView

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView!!.setHasFixedSize(true)

        // use a linear layout manager
        layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.setLayoutManager(layoutManager)

        // specify an adapter
        mAdapter = RecyclerAdapter(this, viewItems)
        mRecyclerView!!.setAdapter(mAdapter)

        addItemsFromJSON()
    }

    private fun addItemsFromJSON() {
        try {
            val jsonDataString = readJSONDataFromFile()
            val jsonArray = JSONArray(jsonDataString)
            for (i in 0 until jsonArray.length()) {
                val itemObj = jsonArray.getJSONObject(i)
                val name = itemObj.getString("name")
                val region = itemObj.getString("region")
                val code = itemObj.getString("code")
                val capital = itemObj.getString("capital")
                val JSONdatainfo = JSONdata(name, region, code, capital)
                viewItems?.add(JSONdatainfo)
            }
        } catch (e: JSONException) {
            Log.d( "addItemsFromJSON: ", e.toString())
        } catch (e: IOException) {
            Log.d( "addItemsFromJSON: ", e.toString())
        }
    }

    @Throws(IOException::class)
    private fun readJSONDataFromFile(): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String? = null
            inputStream = resources.openRawResource(R.raw.wallmart)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

