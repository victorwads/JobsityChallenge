package br.com.victorwads.job.vicflix.features.security

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager

class AuthHelper(val activity: FragmentActivity) {

    private val biometricManager = BiometricManager.from(activity)

    fun isAvailable(): Boolean =
        biometricManager.canAuthenticate(
            Authenticators.BIOMETRIC_WEAK or Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL
        ) == BiometricManager.BIOMETRIC_SUCCESS

    fun auth(onError: () -> Unit, onSuccess: () -> Unit) {

        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build().let {
                BiometricPrompt(
                    activity,
                    ContextCompat.getMainExecutor(activity),
                    Handler(onError, onSuccess)
                ).authenticate(it)
            }
    }

    fun isEnabled(): Boolean = PreferenceManager.getDefaultSharedPreferences(activity)
        .getBoolean(Companion.CONFIG_KEY, false)

    private class Handler(
        val onError: () -> Unit,
        val onSuccess: () -> Unit
    ) : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            onError()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onSuccess()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            onError()
        }
    }

    companion object {
        private const val CONFIG_KEY = "need_auth"
    }
}
