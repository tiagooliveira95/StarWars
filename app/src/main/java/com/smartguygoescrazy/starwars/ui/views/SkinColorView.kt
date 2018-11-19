package com.smartguygoescrazy.starwars.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class SkinColorView : View {

    var skinColor : List<String> = arrayListOf("metal", "gold", "grey")

    private var mPaint : ArrayList<Paint> = arrayListOf()


    private var mWidth : Int = 0
    private var mHeight : Int = 0

    private var mHorizontalCenterOffset :Float = 0f
    private var mVerticalCenterOffset : Float = 0f

    var v1 : Float = 0f
    private var v2 : Float = 0f

    private val mPadding : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4f,resources.displayMetrics)
    private var cRadius : Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12f,resources.displayMetrics)



    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        for(stringColor in skinColor){
            val color = getColorFromString(stringColor)
            val p = Paint()
            p.color = color
            p.isAntiAlias = true
            mPaint.add(p)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(mHorizontalCenterOffset ,mVerticalCenterOffset,cRadius - mPadding, mPaint[1])

        if(skinColor.size >= 2){
            canvas?.drawCircle(v1 ,mVerticalCenterOffset,cRadius - mPadding, mPaint[0])
            if(skinColor.size == 3){
                canvas?.drawCircle(mHorizontalCenterOffset + v2 ,mVerticalCenterOffset,cRadius - mPadding, mPaint[2])
            }

        }


        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(calculateWidth(), calculateHeight())

        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)

        calculateOffsets(mWidth,mHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        calculateOffsets(mWidth,mHeight)
        invalidate()
    }

    private fun calculateOffsets(w : Int, h : Int){
        v1 = w / 6f
        v2 = w / 3f

        mHorizontalCenterOffset = w / 2f
        mVerticalCenterOffset = h / 2f
    }

    private fun calculateWidth() : Int{
        return MeasureSpec.makeMeasureSpec((
                (mPadding + cRadius*2) * skinColor.size + mPadding).toInt(),
            MeasureSpec.EXACTLY)
    }

    private fun calculateHeight() : Int{
        return MeasureSpec.makeMeasureSpec((cRadius * 2 + mPadding * 2).toInt(),
            MeasureSpec.EXACTLY)
    }

    private fun getColorFromString(color: String) : Int{
        return when(color){
            "fair" -> Color.parseColor("#FFE0BD")
            "gold" -> Color.parseColor("#DAA520")
            "white" -> Color.WHITE
            "blue" -> Color.parseColor("#1E90FF")
            "light" -> Color.parseColor("#F6C2B4")
            "red" -> Color.parseColor("#B22222")
            "green" -> Color.parseColor("#32CD32")
            "green-tan" -> Color.parseColor("#228B22")
            "brown" -> Color.parseColor("#614126")
            "pale" -> Color.parseColor("#DB7093")
            "metal" -> Color.parseColor("#43464B")
            "dark" -> Color.parseColor("#272525")
            "brown mottle" -> Color.parseColor("#624a2e")
            "grey" -> Color.parseColor("#D3D3D3")
            "mottled orange" -> Color.parseColor("#ffb347 ")
            "yellow" -> Color.parseColor("#FFFF00")
            "tan" -> Color.parseColor("#D2B48C")
            "silver" -> Color.parseColor("#c0c0c0")
            else -> Color.WHITE
        }
    }

    // combinações unicas de cor
    // { fair },                           Luke Skywalker
    // { gold },                           C-3PO
    // { white, blue },                    C-3PO
    // { white },                          R2-D2
    // { light },                          Darth Vader
    // { white, red },                     R5-D4
    // { unknown },                        Chewbacca
    // { green },                          Greedo
    // { green-tan, brown },               Jabba Desilijic Tiure
    // { pale },                           Palpatine
    // { metal },                          IG-88
    // { dark },                           Lando Calrissian
    // { brown mottle },                   Ackbar
    // { brown },                          Wicket Systri Warrick
    // { grey },                           Nien Nunb
    // { mottled green },                  Nute Gunray
    // { orange },                         Jar Jar Binks
    // { blue, grey },                     Watto
    // { grey, red },                      Sebulba
    // { red },                            Darth Maul
    // { blue },                           Ayla Secura
    // { grey, green, yellow },            Ben Quadinaros
    // { yellow },                         Luminara Unduli
    // { tan },                            Bail Prestor Organa
    // { fair, green, yellow },            Zam Wesell
    // { grey, blue },                     Ratts Tyerell
    // { silver, red },                    R4-P17
    // { green, grey },                    Wat Tambor
    // { red, blue, white },               Shaak Ti
    // { brown, white },                   Grievous
    // { none }                            BB8
}