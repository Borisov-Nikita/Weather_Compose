package nik.borisov.weathercompose.presentation.model.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nik.borisov.weathercompose.R

enum class WeatherInterpretationCodes(
    val code: Int,
    @StringRes val dayDescription: Int,
    @StringRes val nightDescription: Int,
    @DrawableRes val dayIcon: Int,
    @DrawableRes val nightIcon: Int
) {
    CODE_0(
        code = 0,
        dayDescription = R.string.code_0_day,
        nightDescription = R.string.code_0_night,
        dayIcon = R.drawable.ic_weather_code_0_day,
        nightIcon = R.drawable.ic_weather_code_0_night
    ),
    CODE_1(
        code = 1,
        dayDescription = R.string.code_1_day,
        nightDescription = R.string.code_1_night,
        dayIcon = R.drawable.ic_weather_code_1_day,
        nightIcon = R.drawable.ic_weather_code_1_night
    ),
    CODE_2(
        code = 2,
        dayDescription = R.string.code_2,
        nightDescription = R.string.code_2,
        dayIcon = R.drawable.ic_weather_code_2_day,
        nightIcon = R.drawable.ic_weather_code_2_night
    ),
    CODE_3(
        code = 3,
        dayDescription = R.string.code_3,
        nightDescription = R.string.code_3,
        dayIcon = R.drawable.ic_weather_code_3,
        nightIcon = R.drawable.ic_weather_code_3
    ),
    CODE_45(
        code = 45,
        dayDescription = R.string.code_45,
        nightDescription = R.string.code_45,
        dayIcon = R.drawable.ic_weather_code_45_48,
        nightIcon = R.drawable.ic_weather_code_45_48
    ),
    CODE_48(
        code = 48,
        dayDescription = R.string.code_48,
        nightDescription = R.string.code_48,
        dayIcon = R.drawable.ic_weather_code_45_48,
        nightIcon = R.drawable.ic_weather_code_45_48
    ),
    CODE_51(
        code = 51,
        dayDescription = R.string.code_51,
        nightDescription = R.string.code_51,
        dayIcon = R.drawable.ic_weather_code_51_day,
        nightIcon = R.drawable.ic_weather_code_51_night
    ),
    CODE_53(
        code = 53,
        dayDescription = R.string.code_53,
        nightDescription = R.string.code_53,
        dayIcon = R.drawable.ic_weather_code_53_day,
        nightIcon = R.drawable.ic_weather_code_53_night
    ),
    CODE_55(
        code = 55,
        dayDescription = R.string.code_55,
        nightDescription = R.string.code_55,
        dayIcon = R.drawable.ic_weather_code_55_day,
        nightIcon = R.drawable.ic_weather_code_55_night
    ),
    CODE_56(
        code = 56,
        dayDescription = R.string.code_56,
        nightDescription = R.string.code_56,
        dayIcon = R.drawable.ic_weather_code_56_day,
        nightIcon = R.drawable.ic_weather_code_56_night
    ),
    CODE_57(
        code = 57,
        dayDescription = R.string.code_57,
        nightDescription = R.string.code_57,
        dayIcon = R.drawable.ic_weather_code_57_day,
        nightIcon = R.drawable.ic_weather_code_57_night
    ),
    CODE_61(
        code = 61,
        dayDescription = R.string.code_61,
        nightDescription = R.string.code_61,
        dayIcon = R.drawable.ic_weather_code_61,
        nightIcon = R.drawable.ic_weather_code_61
    ),
    CODE_63(
        code = 63,
        dayDescription = R.string.code_63,
        nightDescription = R.string.code_63,
        dayIcon = R.drawable.ic_weather_code_63,
        nightIcon = R.drawable.ic_weather_code_63
    ),
    CODE_65(
        code = 65,
        dayDescription = R.string.code_65,
        nightDescription = R.string.code_65,
        dayIcon = R.drawable.ic_weather_code_65,
        nightIcon = R.drawable.ic_weather_code_65
    ),
    CODE_66(
        code = 66,
        dayDescription = R.string.code_66,
        nightDescription = R.string.code_66,
        dayIcon = R.drawable.ic_weather_code_66,
        nightIcon = R.drawable.ic_weather_code_66
    ),
    CODE_67(
        code = 67,
        dayDescription = R.string.code_67,
        nightDescription = R.string.code_67,
        dayIcon = R.drawable.ic_weather_code_67,
        nightIcon = R.drawable.ic_weather_code_67
    ),
    CODE_71(
        code = 71,
        dayDescription = R.string.code_71,
        nightDescription = R.string.code_71,
        dayIcon = R.drawable.ic_weather_code_71,
        nightIcon = R.drawable.ic_weather_code_71
    ),
    CODE_73(
        code = 73,
        dayDescription = R.string.code_73,
        nightDescription = R.string.code_73,
        dayIcon = R.drawable.ic_weather_code_73,
        nightIcon = R.drawable.ic_weather_code_73
    ),
    CODE_75(
        code = 75,
        dayDescription = R.string.code_75,
        nightDescription = R.string.code_75,
        dayIcon = R.drawable.ic_weather_code_75,
        nightIcon = R.drawable.ic_weather_code_75
    ),
    CODE_77(
        code = 77,
        dayDescription = R.string.code_77,
        nightDescription = R.string.code_77,
        dayIcon = R.drawable.ic_weather_code_77,
        nightIcon = R.drawable.ic_weather_code_77
    ),
    CODE_80(
        code = 80,
        dayDescription = R.string.code_80,
        nightDescription = R.string.code_80,
        dayIcon = R.drawable.ic_weather_code_80,
        nightIcon = R.drawable.ic_weather_code_80
    ),
    CODE_81(
        code = 81,
        dayDescription = R.string.code_81,
        nightDescription = R.string.code_81,
        dayIcon = R.drawable.ic_weather_code_81,
        nightIcon = R.drawable.ic_weather_code_81
    ),
    CODE_82(
        code = 82,
        dayDescription = R.string.code_82,
        nightDescription = R.string.code_82,
        dayIcon = R.drawable.ic_weather_code_82,
        nightIcon = R.drawable.ic_weather_code_82
    ),
    CODE_85(
        code = 85,
        dayDescription = R.string.code_85,
        nightDescription = R.string.code_85,
        dayIcon = R.drawable.ic_weather_code_85,
        nightIcon = R.drawable.ic_weather_code_85
    ),
    CODE_86(
        code = 86,
        dayDescription = R.string.code_86,
        nightDescription = R.string.code_86,
        dayIcon = R.drawable.ic_weather_code_86,
        nightIcon = R.drawable.ic_weather_code_86
    ),
    CODE_95(
        code = 95,
        dayDescription = R.string.code_95,
        nightDescription = R.string.code_95,
        dayIcon = R.drawable.ic_weather_code_95,
        nightIcon = R.drawable.ic_weather_code_95
    ),
    CODE_96(
        code = 96,
        dayDescription = R.string.code_96,
        nightDescription = R.string.code_96,
        dayIcon = R.drawable.ic_weather_code_96_99,
        nightIcon = R.drawable.ic_weather_code_96_99
    ),
    CODE_99(
        code = 99,
        dayDescription = R.string.code_99,
        nightDescription = R.string.code_99,
        dayIcon = R.drawable.ic_weather_code_96_99,
        nightIcon = R.drawable.ic_weather_code_96_99
    );

    fun getDescriptionResId(isDay: Boolean): Int {
        return if (isDay) dayDescription else nightDescription
    }

    fun getIconResId(isDay: Boolean): Int {
        return if (isDay) dayIcon else nightIcon
    }
}