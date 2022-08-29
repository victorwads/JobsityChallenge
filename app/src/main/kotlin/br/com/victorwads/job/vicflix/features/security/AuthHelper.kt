package br.com.victorwads.job.vicflix.features.security

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import br.com.victorwads.job.vicflix.R

class AuthHelper(val activity: FragmentActivity) {

    private val biometricManager = BiometricManager.from(activity)

    fun isAvailable(): Boolean =
        biometricManager.canAuthenticate(TYPES) == BiometricManager.BIOMETRIC_SUCCESS

    fun isEnabled(): Boolean = PreferenceManager.getDefaultSharedPreferences(activity)
        .getBoolean(CONFIG_KEY, false)

    fun handleAuth(onError: () -> Unit, onSuccess: () -> Unit): Boolean {
        if (isAvailable() && isEnabled()) {
            auth(onError, onSuccess)
            return true
        }
        return false
    }

    fun auth(onError: () -> Unit, onSuccess: () -> Unit) {

        BiometricPrompt.PromptInfo.Builder()
            .setTitle(activity.getString(R.string.security_request_title))
            .setSubtitle(activity.getString(R.string.security_request_subtitle))
            .setConfirmationRequired(false)
            .setAllowedAuthenticators(TYPES)
            .build().let {
                BiometricPrompt(
                    activity,
                    ContextCompat.getMainExecutor(activity),
                    Handler(onError, onSuccess)
                ).authenticate(it)
            }
    }

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
        private const val TYPES = Authenticators.DEVICE_CREDENTIAL or
                Authenticators.BIOMETRIC_WEAK or
                Authenticators.BIOMETRIC_STRONG
    }
}
