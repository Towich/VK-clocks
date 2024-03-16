package com.towich.vk_test_2.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.towich.vk_test_2.R
import com.towich.vk_test_2.ui.view.ClockView
import java.util.Calendar


class FullScreenClockFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen_clock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clockView = view.findViewById<ClockView>(R.id.clockView)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_forward)

        fab.setOnClickListener {
            view.findNavController().navigate(R.id.action_fullScreenClockFragment_to_colorfulClocksFragment)
        }

        val timer = object: CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                val currentTimeArray = getCurrentHours()
                clockView.hours = currentTimeArray[0]
                clockView.minutes = currentTimeArray[1]
                clockView.seconds = currentTimeArray[2]
            }

            override fun onFinish() {
                start()
            }

        }
        timer.start()
    }

    private fun getCurrentHours(): IntArray {
        val resultArray = IntArray(3)
        val calendar = Calendar.getInstance()

        resultArray[0] = calendar.get(Calendar.HOUR_OF_DAY)
        resultArray[1] = calendar.get(Calendar.MINUTE)
        resultArray[2] = calendar.get(Calendar.SECOND)

        return resultArray
    }
}