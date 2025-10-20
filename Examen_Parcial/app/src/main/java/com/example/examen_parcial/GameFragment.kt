package com.example.examen_parcial

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.random.Random

class GameFragment : Fragment() {

    private var score = 0
    private var targetColorIndex = 0
    private var timer: CountDownTimer? = null
    private var isGameActive = true
    private val colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN)
    private lateinit var colorButtons: List<View>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGame()
        startTimer()
    }

    private fun setupGame() {
        // Inicializar la lista de botones
        colorButtons = listOf(
            requireView().findViewById(R.id.btn_red),
            requireView().findViewById(R.id.btn_green),
            requireView().findViewById(R.id.btn_blue),
            requireView().findViewById(R.id.btn_yellow),
            requireView().findViewById(R.id.btn_purple),
            requireView().findViewById(R.id.btn_orange)
        )

        colorButtons.forEachIndexed { index, button ->
            button.setOnClickListener { checkAnswer(index) }
        }

        generateNewColor()
        updateScore()
    }

    private fun checkAnswer(selectedIndex: Int) {
        if (!isGameActive) return

        val colorTarget = requireView().findViewById<View>(R.id.color_target)
        val selectedButton = colorButtons[selectedIndex]

        if (selectedIndex == targetColorIndex) {
            // ANIMACIÓN DE ACIERTO
            score++
            updateScore()
            animateSuccess(colorTarget, selectedButton)
            generateNewColorWithAnimation()
        } else {
            // ANIMACIoN DE ERROR
            animateError(selectedButton)
        }
    }

    // ANIMACIoN PARA ACIERTO
    private fun animateSuccess(colorTarget: View, button: View) {
        // Animación de escala en el color 
        val scaleAnimation = ScaleAnimation(
            0.8f, 1.2f, 0.8f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 300
        scaleAnimation.repeatCount = 1
        scaleAnimation.repeatMode = Animation.REVERSE
        colorTarget.startAnimation(scaleAnimation)

        // Animación de pulsación en el boton correcto
        val pulseAnimation = AlphaAnimation(0.5f, 1.0f)
        pulseAnimation.duration = 200
        pulseAnimation.repeatCount = 2
        button.startAnimation(pulseAnimation)
    }

    // ANIMACIoN PARA ERROR
    private fun animateError(button: View) {
        // Animación de vibración en el botón incorrecto
        val shakeAnimation = TranslateAnimation(
            -10f, 10f, 0f, 0f
        )
        shakeAnimation.duration = 50
        shakeAnimation.repeatCount = 4
        shakeAnimation.repeatMode = Animation.REVERSE
        button.startAnimation(shakeAnimation)
    }

    // ANIMACIoN AL CAMBIAR COLOR
    private fun generateNewColorWithAnimation() {
        targetColorIndex = Random.nextInt(colors.size)
        val colorTarget = requireView().findViewById<View>(R.id.color_target)

        // Animación de al cambiar color
        val fadeAnimation = AlphaAnimation(0.3f, 1.0f)
        fadeAnimation.duration = 400
        colorTarget.startAnimation(fadeAnimation)

        colorTarget.setBackgroundColor(colors[targetColorIndex])
    }

    // sin animacion
    private fun generateNewColor() {
        targetColorIndex = Random.nextInt(colors.size)
        requireView().findViewById<View>(R.id.color_target).setBackgroundColor(colors[targetColorIndex])
    }

    private fun updateScore() {
        requireView().findViewById<android.widget.TextView>(R.id.tv_score).text = getString(R.string.score, score)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                requireView().findViewById<android.widget.TextView>(R.id.tv_time).text = getString(R.string.time, millisUntilFinished / 1000)
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
    }

    private fun endGame() {
        isGameActive = false
        timer?.cancel()
        val action = R.id.action_game_to_result
        val bundle = Bundle()
        bundle.putInt("score", score)
        findNavController().navigate(action, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }
}
