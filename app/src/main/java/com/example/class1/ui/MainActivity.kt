package com.example.class1.ui

//import androidx.lifecycle.ViewModelProvider

//import androidx.support.v7.widget.LinearLayoutManager
//import androidx.support.v7.widget.RecyclerView


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.class1.GetData
import com.example.class1.R
import com.example.class1.model.Crypto
import com.example.class1.util.MyAdapter
import com.example.class1.viewmodel.CountViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
//import com.google.android.gms.ads.rewarded.RewardedAd;


class MainActivity : AppCompatActivity(), MyAdapter.Listener {
    // Declare our viewmodel for the count object which will be updated whenever counter changes
    private lateinit var countViewModel: CountViewModel
    //private lateinit var ad: RewardedAd

    private var counter: Long = 0 // Declare out counter var that will store number of user clicks, default value is 0
    private fun getUsername() = intent.extras?.get("username").toString().toLowerCase(Locale.US)
    // retrieves the username that was stored in LoginActivity

    private var myAdapter: MyAdapter? = null
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myRetroCryptoArrayList: ArrayList<Crypto>? = null

    private val baseURL = "https://api.nomics.com/v1/"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myCompositeDisposable = CompositeDisposable()
        //initRecyclerView()
        // initializing the recycler view crashes the app
        loadData()

        // Updates UI with the users last stored counter value
        countViewModel = ViewModelProviders.of( this).get(CountViewModel::class.java)
        // retrieves the value from PREFS file through CountRepository
        countViewModel.getUserCount(getUsername()).observe(this, androidx.lifecycle.Observer { updateCounter(it) })

        // Main button action that will increase the counter value by 1
        myButton.setOnClickListener{
            countViewModel.setUserCount(getUsername(), counter + 1)
        }
    }

    // used to update the text field showing the users current click count
    private fun updateCounter(count: Long) {
        counter = count
        textCounter.text = counter.toString()
    }

    //Initialise the RecyclerView//
    private fun initRecyclerView() {
        //Use a layout manager to position your items to look like a standard ListView//
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        cryptoCurrencyList.layoutManager = layoutManager
    }
    //Implement loadData//
    private fun loadData() {
        //Define the Retrofit request//
        val requestInterface = Retrofit.Builder()
            //Set the API’s base URL//
            .baseUrl(baseURL)
            //Specify the converter factory to use for serialization and deserialization//
            .addConverterFactory(GsonConverterFactory.create())
            //Add a call adapter factory to support RxJava return types//
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //Build the Retrofit instance//
            .build().create(GetData::class.java)

        //Add all RxJava disposables to a CompositeDisposable//
        myCompositeDisposable?.add(requestInterface.getData()
            //Send the Observable’s notifications to the main UI thread//
            .observeOn(AndroidSchedulers.mainThread())
            //Subscribe to the Observer away from the main UI thread//
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(cryptoList: List<Crypto>) {
        myRetroCryptoArrayList = ArrayList(cryptoList)
        myAdapter = MyAdapter(myRetroCryptoArrayList!!, this)
        //Set the adapter//
        cryptoCurrencyList.adapter = myAdapter
    }

    override fun onItemClick(crypto: Crypto) {
        //If the user clicks on an item, then display a Toast//
        Toast.makeText(this, "You clicked: ${crypto.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //Clear all your disposables//
        myCompositeDisposable?.clear()
    }
}