package com.example.truthordaresaudi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truthordaresaudi.R
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.delay


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compose_splash.setContent {
            MaterialTheme {
                ComposeSplashScreen(findNavController())
            }
        }
    }
}

@Composable
fun ComposeSplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }
    val sizeState by animateSizeAsState(
        targetValue = if (startAnimation) Size(250f, 250f) else Size(50f, 50f),
        animationSpec = tween(durationMillis = 2000)
    )

    val rotateState by animateFloatAsState(
        targetValue = if (startAnimation) 360f else 0f, animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        navController.navigate(R.id.action_splashFragment_to_homeFragment)
    }
    SplashCompose(sizeState, rotateState)
}

@Composable
fun SplashCompose(sizeState: Size, rotateState: Float) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            modifier = Modifier
                .rotate(rotateState)
                .size(
                    sizeState.width.dp,
                    sizeState.height.dp
                ), painter = painterResource(id = R.drawable.logotordm), contentDescription = "logo"
        )

    }
}


