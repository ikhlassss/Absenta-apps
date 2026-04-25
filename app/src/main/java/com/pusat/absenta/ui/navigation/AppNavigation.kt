package com.pusat.absenta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pusat.absenta.ui.auth.LoginScreen
import com.pusat.absenta.ui.face.FaceRegistrationScreen
import com.pusat.absenta.ui.main.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        
        // Halaman Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Setelah login, simulasi cek apakah user sudah mendaftar wajah.
                    // Saat ini kita asumsikan "belum daftar wajah", jadi arahkan ke face_registration
                    navController.navigate("face_registration") {
                        // Hapus login dari backstack agar user tidak bisa back ke login
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Halaman Pendaftaran Wajah (Onboarding)
        composable("face_registration") {
            FaceRegistrationScreen(
                onRegistrationSuccess = {
                    // Setelah sukses pindai wajah, masuk ke Main (Home)
                    navController.navigate("main") {
                        // Hapus face_registration dari backstack agar tidak bisa di-back
                        popUpTo("face_registration") { inclusive = true }
                    }
                }
            )
        }

        // Halaman Utama (Home dengan Bottom Navigation)
        composable("main") {
            MainScreen()
        }
    }
}
