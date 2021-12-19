package com.example.truthordaresaudi

import android.os.Bundle
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
import com.example.truthordaresaudi.data.model.GameData
import kotlin.random.Random


class PlayFragment : Fragment() {  //, View.OnClickListener

    private var vm = ViewModel()
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
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation

    private val random = Random(0)
    private var lastDir = 0
    private var spinning = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)

        Log.d("Check data", "waiting..")
        retrieveGameData()
    }


    private fun retrieveGameData() {
        vm.dataList().observe(viewLifecycleOwner, {
            val truthList  : List<GameData> = it.filter { it.type == "truth" }
            val dareList  : List<GameData> = it.filter { it.type == "dare" }
            val punishList  : List<GameData> = it.filter { it.type == "punishment" }
            var count = 0

            ibTruth.setOnClickListener {
                ibTruth.startAnimation(scaleUp)
                if(count == truthList.size-1){
                    count = 0
                }
                playValue.text = truthList[count++] .english
            }
            tvTruth.setOnClickListener {
                ibTruth.startAnimation(scaleUp)
                if(count == truthList.size-1){ //&& lang == ar
                    count = 0
                }
                playValue.text = truthList[count++] .english
            }
            ibDare.setOnClickListener {
                ibDare.startAnimation(scaleUp)
                if(count == dareList.size-1){ //&& lang == ar
                    count = 0
                }
                playValue.text = dareList[count++] .english
            }
            tvDare.setOnClickListener {
                ibDare.startAnimation(scaleUp)
                if(count == dareList.size-1){ //&& lang == ar
                    count = 0
                }
                playValue.text = dareList[count++] .english
            }
            ibPenalty.setOnClickListener {
                ibPenalty.startAnimation(scaleUp)
                if(count == punishList.size-1){ //&& lang == ar
                    count = 0
                }
                playValue.text = punishList[count++] .english
            }
            tvPenalty.setOnClickListener {
                ibPenalty.startAnimation(scaleUp)
                if(count == punishList.size-1){ //&& lang == ar
                    count = 0
                }
                playValue.text = punishList[count++] .english
            }
            tvSpin.setOnClickListener {
                ibSpin.startAnimation(scaleUp)
                spinBottle()
            }
            ibSpin.setOnClickListener {
//                GlobalScope.launch (Dispatchers.Main){
//                    ibSpin.startAnimation(scaleUp)
//                    delay(500)
//                }
                ibSpin.startAnimation(scaleUp)
                spinBottle()
            }


        })
    }

    private fun spinBottle() {
        if (!spinning) {
            val newDir = random.nextInt(2000)
            val pivotX: Float = bottle.width / 2f
            val pivotY: Float = bottle.height / 2f
            val rotate: Animation = RotateAnimation(lastDir.toFloat(), newDir.toFloat(), pivotX, pivotY)
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