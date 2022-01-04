package com.example.truthordaresaudi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.truthordaresaudi.R


class RulesFragment : Fragment() {

    private lateinit var backBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rules, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn = view.findViewById(R.id.backRules)
        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_rulesFragment_to_homeFragment)
        }
//        value = view.findViewById(R.id.suggestion)
////        btn = view.findViewById(R.id.btnn)
//
//        btn.setOnClickListener {
//            val suggestions = UserSuggestions()
//            suggestions.suggestion = value.text.toString()
//
//            myVM.userRequests(suggestions, view.context, view)
//        }





    }
}