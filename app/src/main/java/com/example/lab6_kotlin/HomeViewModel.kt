package com.example.lab6_kotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6_kotlin.models.InputModel
import java.lang.Math.pow
import kotlin.math.sqrt

class HomeViewModel : ViewModel() {
    private val inputDataGrindingMachine = MutableLiveData(InputModel(
        eta = 0.92,
        cos = 0.9,
        Un = 0.38,
        n = 4,
        Pn = 20.0,
        Kv = 0.15,
        tg = 1.33
    ))
    val inputGrindingMachine = inputDataGrindingMachine

    private val inputDataGroutingMachine = MutableLiveData(InputModel(
        eta = 0.92,
        cos = 0.9,
        Un = 0.38,
        n = 2,
        Pn = 14.0,
        Kv = 0.12,
        tg = 1.0
    ))
    val inputGroutingMachine = inputDataGroutingMachine

    private val inputDataCircularSaw = MutableLiveData(InputModel(
        eta = 0.92,
        cos = 0.9,
        Un = 0.38,
        n = 1,
        Pn = 36.0,
        Kv = 0.3,
        tg = 1.52
    ))
    val inputCircularSaw = inputDataCircularSaw

    private val inputDataPress = MutableLiveData(InputModel(
        eta = 0.92,
        cos = 0.9,
        Un = 0.38,
        n = 1,
        Pn = 20.0,
        Kv = 0.5,
        tg = 0.75
    ))
    val inputPress = inputDataPress

    private val inputDataPolishingMachine = MutableLiveData(InputModel(
        eta = 0.92,
        cos = 0.9,
        Un = 0.38,
        n = 1,
        Pn = 40.0,
        Kv = 0.2,
        tg = 1.0
    ))
    val inputPolishingMachine = inputDataPolishingMachine

    private val inputDataShaper = MutableLiveData(InputModel(
        eta = 0.92,
        cos = 0.9,
        Un = 0.38,
        n = 2,
        Pn = 32.0,
        Kv = 0.2,
        tg = 1.0
    ))
    val inputShaper = inputDataShaper

    private val inputDataFan = MutableLiveData(InputModel(
        eta = 0.92,
        cos = 0.9,
        Un = 0.38,
        n = 1,
        Pn = 20.0,
        Kv = 0.65,
        tg = 0.75
    ))
    val inputFan = inputDataFan


    fun calculate(): Map<String, String> {
        val nPnGrinding = countNPn(inputGrindingMachine.value!!.n, inputGrindingMachine.value!!.Pn)
        val nPnPow2Grinding = countNPnPow2(inputGrindingMachine.value!!.n, inputGrindingMachine.value!!.Pn)
        val IpGrinding = calculateGrindingMachine(nPnGrinding)
        val nPKvGrinding = nPnGrinding * inputGrindingMachine.value!!.Kv

        val nPnGrouting = countNPn(inputGroutingMachine.value!!.n, inputGroutingMachine.value!!.Pn)
        val nPnPow2Grouting = countNPnPow2(inputGroutingMachine.value!!.n, inputGroutingMachine.value!!.Pn)
        val IpGrouting = calculateGroutingMachine(nPnGrouting)
        val nPKvGrouting = nPnGrouting * inputGroutingMachine.value!!.Kv

        val nPnCircleSaw = countNPn(inputCircularSaw.value!!.n, inputCircularSaw.value!!.Pn)
        val nPnPow2CircleSaw = countNPnPow2(inputCircularSaw.value!!.n, inputCircularSaw.value!!.Pn)
        val IpCircularSaw = calculateCircularSaw(nPnCircleSaw)
        val nPKvSaw = nPnCircleSaw * inputCircularSaw.value!!.Kv

        val nPnPress = countNPn(inputPress.value!!.n, inputPress.value!!.Pn)
        val nPnPow2Press = countNPnPow2(inputPress.value!!.n, inputPress.value!!.Pn)
        val IpPress = calculatePress(nPnPress)
        val nPKvPress = nPnPress * inputPress.value!!.Kv

        val nPnPolishing = countNPn(inputPolishingMachine.value!!.n, inputPolishingMachine.value!!.Pn)
        val npnPow2Polishing = countNPnPow2(inputPolishingMachine.value!!.n, inputPolishingMachine.value!!.Pn)
        val IpPolishing = calculatePolishingMachine(nPnPolishing)
        val nPKnPolishing = nPnPolishing * inputPolishingMachine.value!!.Kv

        val nPnShaper = countNPn(inputShaper.value!!.n, inputShaper.value!!.Pn)
        val nPnPow2Shaper = countNPnPow2(inputShaper.value!!.n, inputShaper.value!!.Pn)
        val IpShaper = calculateShaper(nPnShaper)
        val nPKnShaper = nPnShaper * inputShaper.value!!.Kv

        val nPnFan = countNPn(inputFan.value!!.n, inputFan.value!!.Pn)
        val nPnPow2Fan = countNPnPow2(inputFan.value!!.n, inputFan.value!!.Pn)
        val IpFan = calculateFan(nPnFan)
        val nPKnFan = nPnFan * inputFan.value!!.Kv

        val KvPh = nPKvGrinding + nPKvGrouting + nPKvSaw + nPKvPress + nPKnPolishing + nPKnShaper + nPKnFan
        val KvPhTan = nPKvGrinding * inputGrindingMachine.value!!.tg +
                nPKvGrouting * inputGroutingMachine.value!!.tg +
                nPKvSaw * inputDataCircularSaw.value!!.tg +
                nPKvPress * inputDataPress.value!!.tg +
                nPKnPolishing * inputPolishingMachine.value!!.tg +
                nPKnShaper * inputShaper.value!!.tg +
                nPKnFan * inputFan.value!!.tg

        val sum = nPnGrinding + nPnGrouting + nPnCircleSaw + nPnPress + nPnPolishing + nPnShaper + nPnFan
        val Kv = KvPh / sum

        val ne = pow(sum, 2.0) / (nPnPow2Grinding + npnPow2Polishing + nPnPow2Fan + nPnPow2Shaper +
                nPnPow2CircleSaw + nPnPow2Grouting)

        val Kp = 0.25
        val Pp = Kp * KvPh
        val Qp = 1 * KvPhTan
        val Sp = sqrt(pow(Pp, 2.0) + pow(Qp, 2.0))
        val Ip = Pp / inputFan.value!!.Un

        val summa = nPnGrinding + nPnGrouting + nPnShaper + nPnFan + nPnPress + nPnCircleSaw + nPnPolishing
        val summa2 = nPKvGrinding + nPKvGrouting + nPKvSaw + nPKvPress + nPKnPolishing + nPKnShaper + nPKnFan
        val KvAll = summa2 / summa

        val neAll = pow(summa, 2.0) / (nPnPow2Grinding + nPnPow2Press + nPnPow2Grouting + nPnPow2Fan +
                nPnPow2CircleSaw + nPnPow2Shaper)

        val Kpn = 0.7
        val Ppn = Kp * Kv * summa2
        val Qpn = Kpn * KvPhTan
        val Spn = sqrt((pow(Ppn, 2.0) + pow(Qpn, 2.0)))
        val Ipn = Ppn / inputFan.value!!.Un

        return mapOf(
            "KvPh" to KvPh.toString(),
            "Kv" to Kv.toString(),
            "ne" to ne.toString(),
            "Pp" to Pp.toString(),
            "Qp" to Qp.toString(),
            "Sp" to Sp.toString(),
            "Ip" to Ip.toString(),
            "KvAll" to KvAll.toString(),
            "neAll" to neAll.toString(),
            "Ppn" to Ppn.toString(),
            "Qpn" to Qpn.toString(),
            "Spn" to Spn.toString(),
            "Ipn" to Ipn.toString()
        )
    }



    private fun calculateGrindingMachine(nPn: Double): Double {
        return countEstimatedCurrent(
            nPn,
            inputGrindingMachine.value!!.Un,
            inputGrindingMachine.value!!.cos,
            inputGrindingMachine.value!!.eta
        )
    }

    private fun calculateGroutingMachine(nPn: Double): Double {
        return countEstimatedCurrent(
            nPn,
            inputGroutingMachine.value!!.Un,
            inputGroutingMachine.value!!.cos,
            inputGroutingMachine.value!!.eta
        )
    }

    private fun calculateCircularSaw(nPn: Double): Double {
        return countEstimatedCurrent(
            nPn,
            inputCircularSaw.value!!.Un,
            inputCircularSaw.value!!.cos,
            inputCircularSaw.value!!.eta
        )
    }

    private fun calculatePress(nPn: Double): Double {
        return countEstimatedCurrent(
            nPn,
            inputPress.value!!.Un,
            inputPress.value!!.cos,
            inputPress.value!!.eta
        )
    }

    private fun calculatePolishingMachine(nPn: Double): Double {
        return countEstimatedCurrent(
            nPn,
            inputPolishingMachine.value!!.Un,
            inputPolishingMachine.value!!.cos,
            inputPolishingMachine.value!!.eta
        )
    }

    private fun calculateShaper(nPn: Double): Double {
        return countEstimatedCurrent(
            nPn,
            inputShaper.value!!.Un,
            inputShaper.value!!.cos,
            inputShaper.value!!.eta
        )
    }

    private fun calculateFan(nPn: Double): Double {
        return countEstimatedCurrent(
            nPn,
            inputFan.value!!.Un,
            inputFan.value!!.cos,
            inputFan.value!!.eta
        )
    }


    private fun countNPn(n: Int, Pn: Double): Double {
        return n * Pn
    }

    private fun countNPnPow2(n: Int, Pn: Double): Double {
        return n * Pn * Pn
    }

    private fun countEstimatedCurrent(nPn: Double, Un: Double, cos: Double, eta: Double): Double {
        return nPn / (sqrt(3.0) * Un * cos * eta)
    }

}