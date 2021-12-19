package com.example.truthordaresaudi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.truthordaresaudi.data.model.UserSuggestions


class RulesFragment : Fragment() {

    private val myVM = ViewModel()
    private lateinit var value : EditText
    private lateinit var btn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rules, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        value = view.findViewById(R.id.suggestion)
        btn = view.findViewById(R.id.btnn)

        btn.setOnClickListener {
            val suggestions = UserSuggestions()
            suggestions.suggestion = value.text.toString()

            myVM.userRequests(suggestions)
        }





    }
}