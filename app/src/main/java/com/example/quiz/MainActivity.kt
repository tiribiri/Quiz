package com.example.quiz

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class Question(
    val imageResId: Int,
    val options: List<String>,
    val correctAnswer: String,
    val descriptionResId: Int
)

class MainActivity : AppCompatActivity() {

    private val questions = listOf(
        Question(R.drawable.q1, listOf("Chat", "Chien", "Lapin"), "Chat", R.string.desc_q1),
        Question(R.drawable.q2, listOf("Paris", "Rome", "Madrid"), "Paris", R.string.desc_q2),
        Question(R.drawable.q3, listOf("Rouge", "Vert", "Bleu"), "Bleu", R.string.desc_q3),
        Question(R.drawable.q4, listOf("Voiture", "Moto", "Bus"), "Moto", R.string.desc_q4),
        Question(R.drawable.q5, listOf("Pomme", "Banane", "Orange"), "Banane", R.string.desc_q5)
    )

    private var currentIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageViewQuestion)
        val button1 = findViewById<Button>(R.id.buttonOption1)
        val button2 = findViewById<Button>(R.id.buttonOption2)
        val button3 = findViewById<Button>(R.id.buttonOption3)
        val progressText = findViewById<TextView>(R.id.textViewProgress)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val scoreText = findViewById<TextView>(R.id.textViewScore)

        fun loadQuestion() {
            val q = questions[currentIndex]
            imageView.setImageResource(q.imageResId)
            imageView.contentDescription = getString(q.descriptionResId)
            button1.text = q.options[0]
            button2.text = q.options[1]
            button3.text = q.options[2]
            progressText.text = getString(R.string.progress_format, currentIndex + 1, questions.size)
            progressBar.progress = currentIndex + 1
        }

        fun checkAnswer(answer: String) {
            val q = questions[currentIndex]
            if (answer == q.correctAnswer) score++
            currentIndex++
            if (currentIndex < questions.size) {
                loadQuestion()
            } else {
                scoreText.text = getString(R.string.final_score_format, score, questions.size)

                // Affiche un AlertDialog avec Replay/Quitter
                val dialog = android.app.AlertDialog.Builder(this)
                    .setTitle("Quiz terminé")
                    .setMessage("Score final : $score / ${questions.size}")
                    .setPositiveButton("Rejouer") { _, _ ->
                        // Reset et relancer
                        currentIndex = 0
                        score = 0
                        button1.isEnabled = true
                        button2.isEnabled = true
                        button3.isEnabled = true
                        loadQuestion()
                        scoreText.text = getString(R.string.score_text)
                    }
                    .setNegativeButton("Quitter") { _, _ ->
                        finish() // ferme l'activité
                    }
                    .create()
                dialog.show()

                // Désactive les boutons pendant l'affichage du score
                button1.isEnabled = false
                button2.isEnabled = false
                button3.isEnabled = false
            }
        }

        button1.setOnClickListener { checkAnswer(button1.text.toString()) }
        button2.setOnClickListener { checkAnswer(button2.text.toString()) }
        button3.setOnClickListener { checkAnswer(button3.text.toString()) }

        loadQuestion()
    }
}
