package com.example.truthordaresaudi.ui

import android.media.MediaPlayer
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
import com.example.truthordaresaudi.SharedViewModel
import com.example.truthordaresaudi.R
import com.example.truthordaresaudi.data.model.GameData
import kotlin.random.Random


class PlayFragment : Fragment() {

    private lateinit var sharedVM: SharedViewModel
    lateinit var ibTruth: ImageButton
    lateinit var ibDare: ImageButton
    lateinit var ibPenalty: ImageButton
    lateinit var ibSpin: ImageButton
    lateinit var backBtn: ImageView
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
    private val random = Random(0)
    private var lastDir = 0
    private var spinning = false
    private var currentLanguage = ""
    private lateinit var gameTimer: CountDownTimer
    private lateinit var mediaPlayer: MediaPlayer
    private var isCounting: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedVM = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
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
        timerAnim = view.findViewById(R.id.timerAnim)
        backBtn = view.findViewById(R.id.backPlay)


        timerAnim.visibility = View.INVISIBLE
        tvTimer.visibility = View.INVISIBLE


        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_playFragment_to_homeFragment)
        }
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)

        Log.e("PlayFragment", "Check data waiting..")
        retrieveGameData(view)

        sharedVM.readLanguage.observe(viewLifecycleOwner, {
            currentLanguage = if (it == "ar") {
                "ar"
            } else {
                "en"
            }
        })
    }

    private fun playAudio() {
        mediaPlayer = MediaPlayer.create(context, R.raw.timer_sound)
        mediaPlayer.start()
    }

    private fun createTimer(milliseconds: Long) {
        if (isCounting) {
            gameTimer.cancel()
        }
        timerAnim.visibility = View.VISIBLE
        tvTimer.visibility = View.VISIBLE
        gameTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millis: Long) {
                val minutes = (millis / 1000) / 60
                val seconds = (millis / 1000) % 60
                val time = String.format("%02d:%02d", minutes, seconds)
                if(time == "00:10" || time == "٠٠:١٠"){
                    playAudio()
                }
                (resources.getString(R.string.left) + " $time").also { tvTimer.text = it }
            }

            override fun onFinish() {
                mediaPlayer.stop()
                tvTimer.text = resources.getString(R.string.finished)
                timerAnim.visibility = View.INVISIBLE
            }
        }
        gameTimer.start()
        isCounting = true
    }


    private fun retrieveGameData(view: View) {
        if (sharedVM.checkInternetConnection(view.context)) {
            sharedVM.dataList(view.context).observe(viewLifecycleOwner, { it ->
                val truthList: List<GameData> = it.filter { it.type == "truth" }
                val dareList: List<GameData> = it.filter { it.type == "dare" }
                val punishList: List<GameData> = it.filter { it.type == "punishment" }
                var count = 0

                fun truthClicked() {
                    createTimer(30000)
                    ibTruth.startAnimation(scaleUp)
                    if (count == truthList.size - 1) {
                        count = 0
                    }
                    if (currentLanguage == "en") {
                        playValue.text = truthList[count++].english
                    } else {
                        playValue.text = truthList[count++].arabic
                    }
                }

                fun dareClicked() {
                    createTimer(150000)
                    ibDare.startAnimation(scaleUp)
                    if (count == dareList.size - 1) {
                        count = 0
                    }
                    if (currentLanguage == "en") {
                        playValue.text = dareList[count++].english
                    } else {
                        playValue.text = dareList[count++].arabic
                    }
                }

                fun punishClicked() {
                    createTimer(180000)
                    ibPenalty.startAnimation(scaleUp)
                    if (count == punishList.size - 1) {
                        count = 0
                    }
                    if (currentLanguage == "en") {
                        playValue.text = punishList[count++].english
                    } else {
                        playValue.text = punishList[count++].arabic
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
                    spinBottle()
                }
                ibSpin.setOnClickListener {
                    spinBottle()
                }
            })
        } else {
            Toast.makeText(view.context, "No Internet connection, play offline ", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_playFragment_to_bottleFragment)
        }
    }

    private fun spinBottle() {
        ibSpin.startAnimation(scaleUp)
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

    override fun onDestroy() {
        super.onDestroy()
        if (this::gameTimer.isInitialized) {
            gameTimer.cancel()
        }
    }
}