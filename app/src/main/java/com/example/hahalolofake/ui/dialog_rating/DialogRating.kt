package com.example.hahalolofake.ui.dialog_rating

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hahalolofake.R
import com.example.hahalolofake.utils.SharePrefs
import com.willy.ratingbar.ScaleRatingBar

@SuppressLint("MissingInflatedId")
class DialogRating(private val context: Context) : Dialog(
    context, R.style.CustomAlertDialog
) {
    private var onPress: OnPress? = null
    private val tvTitle: TextView
    private val tvContent: TextView
    private val ratingBar: ScaleRatingBar
    private val imgIcon: ImageView
    private val imageView: ImageView
    private val editFeedback: EditText
    private val sharedPreference: SharedPreferences? = null
    private val editor: SharedPreferences.Editor? = null
    private val KEY_CHECK_OPEN_APP = "KEY CHECK OPEN APP"
    private val btnRate: Button
    private val Send: Button? = null
    private val Cancel: Button? = null
    private val btnLater: Button
    private lateinit var sharePrefs: SharePrefs

    init {
        setContentView(R.layout.fragment_dialog_rating)
        val attributes = window!!.attributes
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = attributes
        window!!.setSoftInputMode(16)
        tvTitle = findViewById<View>(R.id.tvTitle) as TextView
        tvContent = findViewById<View>(R.id.tvContent) as TextView
        ratingBar = findViewById<View>(R.id.ratingBar) as ScaleRatingBar
        imgIcon = findViewById<View>(R.id.imgIcon) as ImageView
        imageView = findViewById<View>(R.id.imageView) as ImageView
        editFeedback = findViewById<View>(R.id.editFeedback) as EditText
        btnRate = findViewById<View>(R.id.btnRate) as Button
        btnLater = findViewById<View>(R.id.btnLater) as Button
        sharePrefs = SharePrefs(context)

        onclick()
        changeRating()

        setCancelable(false)

        editFeedback.visibility = View.GONE
        btnRate.text = getContext().getString(R.string.rate)
        tvTitle.text = getContext().getString(R.string.title_rate_4_5)
        tvContent.text = getContext().getString(R.string.content_rate_4_5)
        imgIcon.setImageResource(R.drawable.ic_rate_love)
        ratingBar.rating = 5f
        btnLater.text=context.getString(R.string.exit)
    }

    interface OnPress {
        fun sendThank()
        fun rating()
        fun cancel()
        fun later()
    }

    fun init(context: Context?, onPress: OnPress?) {
        this.onPress = onPress
    }

    private fun changeRating() {
        ratingBar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            when (rating.toString()) {
                "1.0" -> {
                    editFeedback.visibility = View.GONE
                    btnRate.text = getContext().getString(R.string.rate)
                    tvTitle.text = getContext().getString(R.string.do_you_like_the_app)
                    tvContent.text = getContext().getString(R.string.let_us_know_your_experience)
                    imgIcon.setImageResource(R.drawable.ic_rate_1)
                }

                "2.0" -> {
                    editFeedback.visibility = View.GONE
                    btnRate.text = getContext().getString(R.string.rate)
                    tvTitle.text = getContext().getString(R.string.oh_no)
                    tvContent.text = getContext().getString(R.string.please_give_us_some_feedback)
                    imgIcon.setImageResource(R.drawable.ic_rate_2)
                }

                "3.0" -> {
                    editFeedback.visibility = View.GONE
                    btnRate.text = getContext().getString(R.string.rate)
                    tvTitle.text = getContext().getString(R.string.poor)
                    tvContent.text = getContext().getString(R.string.please_give_us_some_feedback)
                    imgIcon.setImageResource(R.drawable.ic_rate_3)
                }

                "4.0" -> {
                    editFeedback.visibility = View.GONE
                    btnRate.text = getContext().getString(R.string.rate)
                    tvTitle.text = getContext().getString(R.string.good)
                    tvContent.text = getContext().getString(R.string.please_give_us_some_feedback)
                    imgIcon.setImageResource(R.drawable.ic_rate_4)
                }

                "5.0" -> {
                    editFeedback.visibility = View.GONE
                    btnRate.text = getContext().getString(R.string.rate)
                    tvTitle.text = getContext().getString(R.string.great)
                    tvContent.text = getContext().getString(R.string.thanks_for_your_feedback)
                    imgIcon.setImageResource(R.drawable.ic_rate_5)
                }

                else -> {
                    btnRate.text = getContext().getString(R.string.rate)
                    editFeedback.visibility = View.GONE
                    tvTitle.text = getContext().getString(R.string.we_love_you_too)
                    tvContent.text = getContext().getString(R.string.thanks_for_your_feedback)
                    imgIcon.setImageResource(R.drawable.ic_rate_love)
                }
            }
        }
    }


    val newName: String get() = editFeedback.text.toString()

    private fun onclick() {
        btnRate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                savePrefData()
                sharePrefs.numRate = ratingBar.rating.toInt()
                if (ratingBar.rating == 0f) {
                    Toast.makeText(
                        context,
                        "Please feedback",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (ratingBar.rating <= 4.0) {
                    imageView.visibility = View.GONE
                    imgIcon.visibility = View.VISIBLE
                    onPress!!.sendThank()
                } else {
                    //Edit
                    //imageView.setVisibility(View.VISIBLE);
                    imageView.visibility = View.GONE
                    imgIcon.visibility = View.VISIBLE
                    onPress!!.rating()

                }
            }
        })
        btnLater.setOnClickListener { onPress!!.later() }
    }

    private fun savePrefData() {
        val pref = context.getSharedPreferences("myPref", AppCompatActivity.MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putBoolean("isRate", true)
        editor?.commit()
    }
}