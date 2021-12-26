package com.example.truthordare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random


class BottleFragment : Fragment() {

    lateinit var ivBottle: ImageView
    lateinit var ibSpin: ImageButton
    lateinit var tvSpin: TextView
    private val random = Random(0)
    private var lastDir = 0
    private var spinning = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivBottle = view.findViewById(R.id.ivBottle)
        ibSpin = view.findViewById(R.id.spinBtn)
        tvSpin = view.findViewById(R.id.tvSpin)

        ibSpin.setOnClickListener {
            spinBottle()
        }
        tvSpin.setOnClickListener {
            spinBottle()
        }
    }

    private fun spinBottle() {
        if (!spinning) {
            val newDir = random.nextInt(2000)
            val pivotX: Float = ivBottle.width / 2f
            val pivotY: Float = ivBottle.height / 2f
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
            ivBottle.startAnimation(rotate)
        }
    }
}