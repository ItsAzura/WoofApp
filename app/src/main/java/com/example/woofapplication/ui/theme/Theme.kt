package com.example.woofapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//khi người dùng chuyển darkmode thì có những màu này
private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

//khi người dùng chuyển lightmode thì có những màu này
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

@Composable
fun WoofApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),//hỏi xem có phải darkmode hay ko?
    dynamicColor: Boolean = false ,//hỏi xem app có dùng màu động hay không? gắn giá trị mắc định là false
    content: @Composable () -> Unit //đại diện cho giao diện người dùng
) {
    val colorScheme = when {
        //TH1
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            //Nếu dynamicColor được bật và thiết bị chạy từ Android 12 trở lên
            val context = LocalContext.current
            //Lấy Context từ môi trường cục bộ.
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            //Nếu không, nếu thì darkTheme đc bật và sử dụng bảng màu tối
            // Nếu ko thoả mãn đk nào thì sử dụng bảng màu sáng
        }
        //TH2
        darkTheme -> DarkColorScheme
        //Nếu darktheme được bật thì gán màu từ darkColorScheme vào colorScheme
        else -> LightColorScheme
        //Nếu không thì gán lightColorScheme vào colorScheme
    }
    val view = LocalView.current
    //cho phép bạn truy cập đến view mà compose đang hiện thị
    if (!view.isInEditMode) {
        //kiểm tra view đang không ở chế độ chỉnh sửa
        SideEffect {
            //thực hiện các tác vụ liên quan đến thanh trạng thái
            val window = (view.context as Activity).window
            //lấy tham chiếu đến cửa sổ của activity đang chứa compose
            window.statusBarColor = colorScheme.primary.toArgb()
            //đặt thanh trang thái thành màu primary trả về màu ARGB
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            //Điều chỉnh giao diện của thanh trạng thái dựa vào darktheme nếu darkTheme = true
        }
    }
    MaterialTheme(
        //Áp dụng cá cài đặt vào giao diện người dùng
        colorScheme = colorScheme,
        //xác định bảng màu
        typography = Typography,
        //Xác định kiểu chữ
        shapes = Shapes,
        //Xác đinh hình dáng ui
        content = content
        //Xác định giao diện phần nội dung
    )
}
