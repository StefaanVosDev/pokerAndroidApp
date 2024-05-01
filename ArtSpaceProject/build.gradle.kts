//import com.sun.org.apache.bcel.internal.Repository
import org.jetbrains.kotlin.ir.backend.js.lower.collectNativeImplementations

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

}