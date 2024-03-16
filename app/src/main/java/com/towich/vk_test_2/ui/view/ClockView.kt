package com.towich.vk_test_2.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.towich.vk_test_2.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ClockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val maxHours = 12
    private val maxMinutes = 60
    private val maxSeconds = 60
    private val colorPrimaryTypedValue by lazy {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimary,
            typedValue,
            true
        )
        typedValue.data
    }
    private val colorSecondaryTypedValue by lazy {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            com.google.android.material.R.attr.colorSecondary,
            typedValue,
            true
        )
        typedValue.data
    }

    private val defaultClockElementsColor: Int = colorSecondaryTypedValue
    private val defaultSecondClockHandColor: Int = Color.RED

    private val clockBasePaint = Paint()
    private val clockFramePaint = Paint()
    private val numberPaint = Paint()
    private val lineAsNumberPaint = Paint()
    private val hourClockHandPaint = Paint()
    private val minuteClockHandPaint = Paint()
    private val secondClockHandPaint = Paint()
    private val middleCirclePaint = Paint()

    private var numbersTextSize: Float = 60f

    var hours: Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var minutes: Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var seconds: Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var clockElementsColor: Int? = null
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var secondClockHandColor: Int? = null
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var backgroundClockColor: Int? = null
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }
    var borderClockColor: Int? = null
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ClockView, 0, 0).apply {
            hours = getInteger(R.styleable.ClockView_hours, 0)
            minutes = getInteger(R.styleable.ClockView_minutes, 0)
            seconds = getInteger(R.styleable.ClockView_seconds, 0)
            clockElementsColor =
                getInteger(R.styleable.ClockView_clock_elements_color, defaultClockElementsColor)
            secondClockHandColor = getInteger(
                R.styleable.ClockView_second_clock_hand_color,
                defaultSecondClockHandColor
            )
            backgroundClockColor =
                getInteger(R.styleable.ClockView_background_clock_color, colorPrimaryTypedValue)
            borderClockColor =
                getInteger(R.styleable.ClockView_border_clock_color, colorSecondaryTypedValue)
        }

        numberPaint.apply {
            style = Paint.Style.FILL_AND_STROKE
            textAlign = Paint.Align.CENTER
            val typeFaceFont = resources.getFont(R.font.baloo)
            typeface = Typeface.create(typeFaceFont, Typeface.BOLD)
            color = clockElementsColor ?: defaultClockElementsColor
        }

        clockBasePaint.apply {
            style = Paint.Style.FILL
            color = backgroundClockColor ?: colorPrimaryTypedValue
        }

        clockFramePaint.apply {
            style = Paint.Style.STROKE
            color = borderClockColor ?: colorPrimaryTypedValue
        }

        lineAsNumberPaint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        hourClockHandPaint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = clockElementsColor ?: defaultClockElementsColor
        }

        minuteClockHandPaint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = clockElementsColor ?: defaultClockElementsColor
        }

        secondClockHandPaint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = secondClockHandColor ?: defaultSecondClockHandColor
        }

        middleCirclePaint.apply {
            style = Paint.Style.FILL_AND_STROKE
            color = clockElementsColor ?: defaultClockElementsColor
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val minOfHeightWidth = min(height, width)
        val radiusCircle = minOfHeightWidth / 2f - height / 20f
        val cx = (width / 2).toFloat()
        val cy = (height / 2).toFloat()

        numbersTextSize = radiusCircle / 4

        numberPaint.apply {
            textSize = numbersTextSize
        }
        clockFramePaint.apply {
            strokeWidth = radiusCircle / 20f
            setShadowLayer(
                minOfHeightWidth / 2f / 20,
                0.0f,
                0.0f,
                borderClockColor ?: colorSecondaryTypedValue
            )
        }
        hourClockHandPaint.apply {
            strokeWidth = radiusCircle / 15f
            setShadowLayer(
                minOfHeightWidth / 2f / 20,
                minOfHeightWidth / 120f,
                minOfHeightWidth / 120f,
                clockElementsColor ?: defaultClockElementsColor
            )
        }
        minuteClockHandPaint.apply {
            strokeWidth = radiusCircle / 25f
            setShadowLayer(
                minOfHeightWidth / 2f / 20,
                minOfHeightWidth / 80f,
                minOfHeightWidth / 80f,
                clockElementsColor ?: defaultClockElementsColor
            )
        }
        secondClockHandPaint.apply {
            strokeWidth = radiusCircle / 40f
            setShadowLayer(
                minOfHeightWidth / 2f / 20,
                minOfHeightWidth / 120f,
                minOfHeightWidth / 120f,
                secondClockHandColor ?: defaultSecondClockHandColor
            )
        }
        lineAsNumberPaint.apply {
            strokeWidth = radiusCircle / 40f
            color = clockElementsColor ?: defaultClockElementsColor
        }


        drawBaseClock(
            canvas = canvas,
            cx = cx,
            cy = cy,
            radiusCircle = radiusCircle,
            paint = clockBasePaint
        )

        drawFrameClock(
            canvas = canvas,
            cx = cx,
            cy = cy,
            radiusCircle = radiusCircle,
            paint = clockFramePaint
        )

        drawNumbers(
            canvas = canvas,
            cx = cx,
            cy = cy,
            radiusCircle = radiusCircle - (radiusCircle / 4.5f)
        )

        drawClockHand(
            canvas = canvas,
            cx = cx,
            cy = cy,
            radiusCircle = radiusCircle * 0.5f,
            currentUnits = hours + minutes / 60f,
            maxUnits = maxHours,
            paint = hourClockHandPaint
        )

        drawClockHand(
            canvas = canvas,
            cx = cx,
            cy = cy,
            radiusCircle = radiusCircle * 0.6f,
            currentUnits = minutes + seconds / 60f,
            maxUnits = maxMinutes,
            paint = minuteClockHandPaint
        )

        drawClockHand(
            canvas = canvas,
            cx = cx,
            cy = cy,
            radiusCircle = radiusCircle * 0.7f,
            currentUnits = seconds.toFloat(),
            maxUnits = maxSeconds,
            paint = secondClockHandPaint
        )

        drawMiddleCircle(
            canvas = canvas,
            cx = cx,
            cy = cy,
            radiusCircle = radiusCircle * 0.05f,
            paint = middleCirclePaint
        )
    }

    private fun drawBaseClock(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radiusCircle: Float,
        paint: Paint
    ) {
        canvas.drawCircle(cx, cy, radiusCircle, paint)
    }

    private fun drawFrameClock(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radiusCircle: Float,
        paint: Paint
    ) {
        canvas.drawCircle(cx, cy, radiusCircle, paint)
    }

    private fun drawClockHand(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radiusCircle: Float,
        currentUnits: Float,
        maxUnits: Int,
        paint: Paint
    ) {
        val offset = -90
        val angle = 360 / maxUnits.toDouble() * currentUnits + offset

        val xEnd = radiusCircle * cos(Math.toRadians(angle)).toFloat() + cx
        val yEnd = radiusCircle * sin(Math.toRadians(angle)).toFloat() + cy

        canvas.drawLine(cx, cy, xEnd, yEnd, paint)
    }

    private fun drawNumbers(canvas: Canvas, cx: Float, cy: Float, radiusCircle: Float) {
        val offset = -90

        repeat(12) {
            val angle = 360 / 12.0 * it + offset
            val xEnd = radiusCircle * cos(Math.toRadians(angle)).toFloat() + cx
            val yEnd = radiusCircle * sin(Math.toRadians(angle)).toFloat() + cy

            if (it == 0 || it == 3 || it == 6 || it == 9) {
                val number = if (it == 0) 12 else it
                canvas.drawText("$number", xEnd, yEnd + numbersTextSize / 3, numberPaint)
            } else {
                val xEnd2 =
                    (radiusCircle + radiusCircle / 10) * cos(Math.toRadians(angle)).toFloat() + cx
                val yEnd2 =
                    (radiusCircle + radiusCircle / 10) * sin(Math.toRadians(angle)).toFloat() + cy
                canvas.drawLine(xEnd, yEnd, xEnd2, yEnd2, lineAsNumberPaint)
            }

        }
    }

    private fun drawMiddleCircle(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radiusCircle: Float,
        paint: Paint
    ) {
        canvas.drawCircle(cx, cy, radiusCircle, paint)
    }
}