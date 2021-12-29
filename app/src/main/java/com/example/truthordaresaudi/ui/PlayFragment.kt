package com.example.truthordaresaudi.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.truthordaresaudi.MyViewModel
import com.example.truthordaresaudi.R
import com.example.truthordaresaudi.data.model.GameData
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class PlayFragment : Fragment() {  //, View.OnClickListener

    private lateinit var myVM: MyViewModel
    lateinit var ibTruth: ImageButton
    lateinit var ibDare: ImageButton
    lateinit var ibPenalty: ImageButton
    lateinit var ibSpin: ImageButton
    lateinit var tvTruth: TextView
    lateinit var tvDare: TextView
    lateinit var tvPenalty: TextView
    lateinit var tvSpin: TextView
    lateinit var playValue: TextView
    lateinit var bottle: ImageView
    lateinit var tvTimer: TextView
    lateinit var timerAnim: LottieAnimationView
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    var isCounting: Boolean = false
    private var selectedLanguage = "en"  //Locale.getDefault().displayLanguage or from data store
    private val random = Random(0)
    private var lastDir = 0
    private var spinning = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myVM = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        ibTruth = view.findViewById(R.id.truthBtn)
        ibDare = view.findViewById(R.id.dareBtn)
        ibPenalty = view.findViewById(R.id.penaltyBtn)
        tvTruth = view.findViewById(R.id.tvTruth)
        tvDare = view.findViewById(R.id.tvDare)
        tvPenalty = view.findViewById(R.id.tvPenalty)
        playValue = view.findViewById(R.id.tvGameValue)
        ibSpin = view.findViewById(R.id.spinBtn)
        tvSpin = view.findViewById(R.id.tvSpin)
        bottle = view.findViewById(R.id.ivBottle)
        tvTimer = view.findViewById(R.id.tvTimer)
        tvTimer.visibility = View.INVISIBLE
        timerAnim = view.findViewById(R.id.timerAnim)
        timerAnim.visibility = View.INVISIBLE

        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)

        Log.d("Check data", "waiting..")
        retrieveGameData(view)

        /*myVM.readLanguage.observe(viewLifecycleOwner,{
            if (it == "ar"){
                Log.e("ProfileFragmentTheme","it = arabic")
                selectedLanguage = "ar"
            }else{
                Log.e("ProfileFragmentTheme","it = english")
                selectedLanguage = "en"

            }
        })*/

    }


    private fun retrieveGameData(view: View) {
        val truthTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millis: Long) {
                tvTimer.text = "Left 00: " + millis / 1000
            }

            override fun onFinish() {
                Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                tvTimer.text = "Finished!"
                timerAnim.visibility = View.INVISIBLE
            }
        }

        val dareTimer = object : CountDownTimer(150000, 1000) {
            override fun onTick(millis: Long) {
                val hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millis)
                    ), TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                )
                tvTimer.text = "Left $hms"

            }

            override fun onFinish() {
                Toast.makeText(context, "Done!", Toast.LENGTH_LONG)
                    .show()
                tvTimer.text = "Finished!"
                timerAnim.visibility = View.INVISIBLE
            }
        }


        if (myVM.checkConnection(view.context)) {
            myVM.dataList(view.context).observe(viewLifecycleOwner, { it ->
                val truthList: List<GameData> = it.filter { it.type == "truth" }
                val dareList: List<GameData> = it.filter { it.type == "dare" }
                val punishList: List<GameData> = it.filter { it.type == "punishment" }
                var count = 0

                fun truthClicked() {
                    timerAnim.visibility = View.VISIBLE
                    tvTimer.visibility = View.VISIBLE
                    ibTruth.startAnimation(scaleUp)
                    if (count == truthList.size - 1) {
                        count = 0
                    }
                    if (selectedLanguage == "en") {
                        playValue.text = truthList[count++].english
                    } else {
                        playValue.text = truthList[count++].arabic
                    }

                    if (!isCounting) {
                        isCounting = true
                        truthTimer.start()
                    } else {
                        truthTimer.cancel()
                        truthTimer.start()
                        dareTimer.cancel()
                    }
                }

                fun dareClicked() {
                    timerAnim.visibility = View.VISIBLE
                    tvTimer.visibility = View.VISIBLE
                    ibDare.startAnimation(scaleUp)
                    if (count == dareList.size - 1) {
                        count = 0
                    }
                    if (selectedLanguage == "en") {
                        playValue.text = dareList[count++].english
                    } else {
                        playValue.text = dareList[count++].arabic
                    }
                    if (!isCounting) {
                        isCounting = true
                        dareTimer.start()
                    } else {
                        dareTimer.cancel()
                        truthTimer.cancel()
                        dareTimer.start()

                    }
                }

                fun punishClicked() {
                    timerAnim.visibility = View.VISIBLE
                    tvTimer.visibility = View.VISIBLE
                    ibPenalty.startAnimation(scaleUp)
                    if (count == punishList.size - 1) {
                        count = 0
                    }
                    if (selectedLanguage == "en") {
                        playValue.text = punishList[count++].english
                    } else {
                        playValue.text = punishList[count++].arabic
                    }
                    if (!isCounting) {
                        isCounting = true
                        dareTimer.start()
                    } else {
                        dareTimer.cancel()
                        truthTimer.cancel()
                        dareTimer.start()
                    }
                }


                ibTruth.setOnClickListener {
                    truthClicked()
                }
                tvTruth.setOnClickListener {
                    truthClicked()
                }
                ibDare.setOnClickListener {
                    dareClicked()
                }
                tvDare.setOnClickListener {
                    dareClicked()
                }
                ibPenalty.setOnClickListener {
                    punishClicked()
                }
                tvPenalty.setOnClickListener {
                    punishClicked()
                }
                tvSpin.setOnClickListener {
                    ibSpin.startAnimation(scaleUp)
                    spinBottle()
                }
                ibSpin.setOnClickListener {
                    ibSpin.startAnimation(scaleUp)
                    spinBottle()
                }
            })
        } else {
            Toast.makeText(view.context, "No Internet connection, play offline ", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_playFragment_to_bottleFragment)

        }
    }

    private fun spinBottle() {
        if (!spinning) {
            val newDir = random.nextInt(2000)
            val pivotX: Float = bottle.width / 2f
            val pivotY: Float = bottle.height / 2f
            val rotate: Animation =
                RotateAnimation(lastDir.toFloat(), newDir.toFloat(), pivotX, pivotY)
            rotate.duration = 2500
            rotate.fillAfter = true
            rotate.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    spinning = true
                }

                override fun onAnimationEnd(animation: Animation) {
                    spinning = false
                }

                override fun onAnimationRepeat(animation: Animation) {
                }
            })
            lastDir = newDir
            bottle.startAnimation(rotate)
        }
    }
    //  override fun onClick(v: View?) {
//        when (v?.id) {
////            R.id.truthBtn, R.id.tvTruth -> findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
////            R.id.dareBtn, R.id.tvDare -> findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
////            R.id.penaltyBtn, R.id.tvPenalty -> findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
//
//
//        }
//    }
}