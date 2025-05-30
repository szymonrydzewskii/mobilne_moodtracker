package com.example.moodtracker_zadanie

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import java.text.SimpleDateFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoodDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoodDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val args: MoodDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mood = args.moodEntry

        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        view.findViewById<TextView>(R.id.date_text).text = "${formatter.format(mood.date)}"
        view.findViewById<TextView>(R.id.mood_text).text = "${mood.nastroj}"
        view.findViewById<TextView>(R.id.description_text).text = "${mood.opisDnia}"
        view.findViewById<TextView>(R.id.category_text).text = "${mood.wydarzenia}"
        val physicalText = view.findViewById<TextView>(R.id.physical_text)
        if (mood.aktywnyFizycznie) {
            physicalText.text = "Byłem aktywny fizycznie"
        } else {
            physicalText.visibility = View.GONE
        }
        val sleepText = view.findViewById<TextView>(R.id.sleep_text)
        if (mood.spalemDobrze) {
            sleepText.text = "Spałem dobrze"
        } else {
            sleepText.visibility = View.GONE
        }
        val socialText = view.findViewById<TextView>(R.id.social_text)
        if (mood.spotkalemSie) {
            socialText.text = "Spotkałem się ze znajomymi"
        } else {
            socialText.visibility = View.GONE
        }
        view.findViewById<TextView>(R.id.rating_text).text = "${mood.rating}/5"
        view.findViewById<TextView>(R.id.important_text).text = "${if (mood.czyWazne) "Tak" else "Nie"}"

        val backgroundColor = when (mood.nastroj) {
            MoodType.Wesoły -> Color.parseColor("#C8E6C9")
            MoodType.Przeciętny -> Color.parseColor("#FFF9C4")
            MoodType.Smutny -> Color.parseColor("#BBDEFB")
        }
        view.setBackgroundColor(backgroundColor)

        val iconView = view.findViewById<ImageView>(R.id.ikonaNastroju)
        val iconRes = when (mood.nastroj){
            MoodType.Wesoły -> R.drawable.happy
            MoodType.Przeciętny -> R.drawable.mid
            MoodType.Smutny -> R.drawable.sad
        }
        iconView.setImageResource(iconRes)

        val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar_display)
        ratingBar.rating = mood.rating

        view.findViewById<Button>(R.id.delete_button).setOnClickListener {
            FakeMoodRepository.removeMood(mood.id)
            Toast.makeText(requireContext(), "Wpis usunięty", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_moodDetailsFragment_to_moodHistoryFragment)
        }

        view.findViewById<Button>(R.id.share_button).setOnClickListener {
            Toast.makeText(requireContext(), "Udostępnianie wpisu", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoodDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoodDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}