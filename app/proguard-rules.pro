# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

-dontwarn okhttp3.**

-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.creator_model.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.facebook_model.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_model.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.language_model.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.my_download.**{*;}
-keep class vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.web_model.**{*;}

-keepattributes Signature

# This is also needed for R8 in compat mode since multiple
# optimizations will remove the generic signature such as class
# merging and argument removal. See:
# https://r8.googlesource.com/r8/+/refs/heads/main/compatibility-faq.md#troubleshooting-gson-gson
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# Optional. For using GSON @Expose annotation
-keepattributes AnnotationDefault,RuntimeVisibleAnnotations
-keepclassmembers class * {
   @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes AnnotationDefault,RuntimeVisibleAnnotations
-keepattributes *Annotation*

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type
