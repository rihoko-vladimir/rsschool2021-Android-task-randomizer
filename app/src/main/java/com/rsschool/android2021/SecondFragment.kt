package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

class SecondFragment : Fragment() {

    private var fragmentCallback: SecondFragmentCallback? = null
    private var backButton: Button? = null
    private var result: TextView? = null
    var randomResult = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0
        val randomValue = generate(min, max)
        randomResult = randomValue
        result?.text = randomValue.toString()

        backButton?.setOnClickListener {
            // TODO: implement back
            pressBack(randomValue)
        }
    }

    private fun pressBack(randomValue: Int) {
        fragmentCallback?.onSecondFragmentBackPressed(randomValue)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            fragmentCallback = context
        } else {
            throw RuntimeException("Fragment must be attached to the main activity")
        }
    }

    private fun generate(min: Int, max: Int): Int = Random().nextInt(max - min + 1) + min

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCallback = null
        backButton = null
        result = null
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            // TODO: implement adding arguments
            return fragment.apply { arguments = args }
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}