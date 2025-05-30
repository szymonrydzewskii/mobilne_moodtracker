package com.example.moodtracker_zadanie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.moodtracker_zadanie.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoodEntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoodEntryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //spinner
        val spinner: Spinner = view.findViewById(R.id.wydarzenia_spinner)

        val options = arrayOf("Szkoła", "Dom", "Znajomi", "Zdrowie", "inne")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        //button
        val saveButton = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            val opisDnia = view.findViewById<EditText>(R.id.cosiewydarzylo_edittext).text.toString()
            val nastroj = when (view.findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId){
                R.id.wesoly_radiobutton -> MoodType.Wesoły
                R.id.przecietny_radiobutton -> MoodType.Przeciętny
                R.id.smutny_radiobutton -> MoodType.Smutny
                else -> MoodType.Wesoły
            }
            val wydarzenia = view.findViewById<Spinner>(R.id.wydarzenia_spinner).selectedItem.toString()
            val spalemDobrze = view.findViewById<CheckBox>(R.id.spalemdobrze_checkbox).isChecked
            val aktywnyFizycznie = view.findViewById<CheckBox>(R.id.bylemaktywny_checkbox).isChecked
            val spotkalemSie = view.findViewById<CheckBox>(R.id.spotkalemsie_checkbox).isChecked
            val rating = view.findViewById<RatingBar>(R.id.rating_bar).rating
            val czyWazne = view.findViewById<Switch>(R.id.wazny_dzien_switch).isChecked
            val wpis = MoodEntry(
                opisDnia = opisDnia,
                nastroj = nastroj,
                wydarzenia = wydarzenia,
                spalemDobrze = spalemDobrze,
                aktywnyFizycznie = aktywnyFizycznie,
                spotkalemSie = spotkalemSie,
                rating = rating,
                czyWazne = czyWazne
            )


            if(opisDnia.isNotBlank()){
                FakeMoodRepository.addMood(wpis)
                Toast.makeText(requireContext(),"Zapisano wpis", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_moodEntryFragment_to_moodHistoryFragment)
            } else{
                Toast.makeText(requireContext(), "Opisz swój dzień", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_entry, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoodEntryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoodEntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}