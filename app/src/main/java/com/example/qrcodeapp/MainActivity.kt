package com.deiaa.qrcodeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


var num=""
var str=""
var clc=1
class MainActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        val adBuilder=AdRequest.Builder().build()
        adView.loadAd(adBuilder)

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-2623524900216357/3223797668", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.message)
                mInterstitialAd = null
                //
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }
//        val adView = AdView(this)
//
//        adView.adSize = AdSize.BANNER
//
//        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"



        Sendnow.visibility=View.INVISIBLE
        linearLayout.visibility=View.INVISIBLE
        messageEnter.visibility=View.INVISIBLE
        userTelg.visibility=View.INVISIBLE
        CreateQR.visibility=View.INVISIBLE


        OnTouchBouttons()
    }

    fun sendNow(view: View) {
        if(num.equals("1")) {
            if (phoneEnter.text.toString().trim().isEmpty())
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
            else {
                val url =
                    str + ccp.selectedCountryCode.toString() + phoneEnter.text.toString() + "?text=" + messageEnter.text.toString()
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
        else if(num.equals("2")){
            if (userTelg.text.toString().trim().isEmpty())
                Toast.makeText(this, "Enter UserName", Toast.LENGTH_SHORT).show()
            else {
                val url =
                    str + userTelg.text.toString() + "?text=" + messageEnter.text.toString()
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
        //Toast.makeText(this,str.toString(), Toast.LENGTH_SHORT).show()
    }

    fun whatsUp(view:View){

        Sendnow.visibility=View.VISIBLE
        linearLayout.visibility=View.VISIBLE
        ccp.visibility=View.VISIBLE
        messageEnter.visibility=View.VISIBLE
        CreateQR.visibility=View.VISIBLE
        userTelg.visibility=View.GONE
        imageView2.visibility=View.GONE

        num="1"
        str="https://wa.me/"
    }

    fun telegram(view:View){


        Sendnow.visibility=View.VISIBLE
        userTelg.visibility=View.VISIBLE
        CreateQR.visibility=View.VISIBLE
        messageEnter.visibility=View.VISIBLE
        imageView2.visibility=View.GONE
        linearLayout.visibility=View.INVISIBLE

        num="2"
        str="https://t.me/"
    }

    fun OnTouchBouttons() {

        whatsUp.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        whatsUp.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_up
                            )
                        )

                    MotionEvent.ACTION_UP ->
                        whatsUp.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_down
                            )
                        )

                }

                return v?.onTouchEvent(event) ?: true
            }
        })

        CreateQR.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        CreateQR.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_up
                            )
                        )

                    MotionEvent.ACTION_UP ->
                        CreateQR.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_down
                            )
                        )

                }

                return v?.onTouchEvent(event) ?: true
            }
        })

        telegram.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        telegram.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_up
                            )
                        )

                    MotionEvent.ACTION_UP ->
                        telegram.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_down
                            )
                        )

                }

                return v?.onTouchEvent(event) ?: true
            }
        })

        Sendnow.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        Sendnow.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_up
                            )
                        )

                    MotionEvent.ACTION_UP ->
                        Sendnow.startAnimation(
                            AnimationUtils.loadAnimation(
                                this@MainActivity,
                                R.anim.scale_down
                            )
                        )

                }

                return v?.onTouchEvent(event) ?: true
            }
        })
    }

    fun CreateQR(view: View) {

        clc+=1

        if(clc%2==0) {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }

        else {

            if (num.equals("1")) {
                if (phoneEnter.text.toString().trim().isEmpty())
                    Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
                else {
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra(
                        "text",
                        str + ccp.selectedCountryCode.toString() + phoneEnter.text.toString() + "?text=" + messageEnter.text.toString()
                    )

                    intent.putExtra(
                        "num",
                        num
                    )
                    startActivity(intent)
                }
            } else if (num.equals("2")) {
                if (userTelg.text.toString().trim().isEmpty())
                    Toast.makeText(this, "Enter UserName", Toast.LENGTH_SHORT).show()
                else {
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra(
                        "text",
                        str + userTelg.text.toString() + "?text=" + messageEnter.text.toString()
                    )
                    intent.putExtra(
                        "num",
                        num
                    )
                    startActivity(intent)
                }
            }
        }
    }
}