package com.cashloan.myapplication.downloader_video.facebook.other

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.cashloan.myapplication.downloader_video.ObjectJson
import com.cashloan.myapplication.downloader_video.R
import com.cashloan.myapplication.downloader_video.facebook.other.OtherFuntions
import okio.IOException
import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

internal class DownloadServiceLinkNew(val context: Context) {
    private var mediafunctions: OtherFuntions = OtherFuntions(context)

    fun links(str: String) {
        Thread {
            try {
                jsonObject(str)
            } catch (e2: JSONException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        context, context.getString(R.string.somethingWentWrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                e2.printStackTrace()
            }
        }.start()
    }

    @Throws(JSONException::class)
    fun jsonObject(str: String) {
        var i: Int
        val replaceAll: String
        var replaceAll2: String? = null
        val jSONObject = JSONObject()
        val sourceCodeWeb = mediafunctions.codeWeb(
            str.replace("https://m.", "https://www.").replace("https://mbasic.", "https://www.")
        )
        var jSONObject2 = JSONObject()
        try {
            val indexOf = sourceCodeWeb.indexOf("browser_native_sd_url") + 21 + 3
            val indexOf2 = sourceCodeWeb.indexOf("\"", indexOf + 5)
            replaceAll2 = sourceCodeWeb.substring(indexOf, indexOf2).replace("\\\\/".toRegex(), "/")
                .replace("\\\\u0025".toRegex(), "%")
            Log.i("sd_link", sourceCodeWeb.substring(indexOf, indexOf + 500))
        } catch (e2: Exception) {
            e2.printStackTrace()
            jSONObject2 = JSONObject()
        }
        if (link(replaceAll2)) {
            val fileSize = fileSize(replaceAll2)
            jSONObject2.put("link", replaceAll2)
            jSONObject2.put("size", fileSize)
            i = 1
            jSONObject.put("sd", jSONObject2)
            val jSONObject3 = JSONObject()
            val indexOf3 = sourceCodeWeb.indexOf("browser_native_hd_url") + 21 + 3
            replaceAll = sourceCodeWeb.substring(indexOf3, sourceCodeWeb.indexOf("\"", indexOf3))
                .replace("\\\\/".toRegex(), "/").replace("\\\\u0025".toRegex(), "%")
            if (link(replaceAll)) {
                jSONObject3.put("link", replaceAll)
                jSONObject3.put("size", fileSize(replaceAll))
                i++
            }
            jSONObject.put("hd", jSONObject3)
            EventBus.getDefault().post(ObjectJson(jSONObject))
            return
        }
        jSONObject.put("sd", jSONObject2)
        val indexOf32 = sourceCodeWeb.indexOf("browser_native_hd_url") + 21 + 3
        replaceAll = sourceCodeWeb.substring(indexOf32, sourceCodeWeb.indexOf("\"", indexOf32))
            .replace("\\\\/", "/").replace("\\\\u0025", "%")
        link(replaceAll)
    }

    fun fileSize(str: String?): String {
        return try {
            val httpURLConnection = URL(str).openConnection() as HttpURLConnection
            httpURLConnection.connect()
            mediafunctions.byteCountBin(httpURLConnection.contentLength.toLong())
        } catch (e2: IOException) {
            e2.printStackTrace()
            "Unknown"
        }
    }

    fun link(str: String?): Boolean {
        return str!!.contains("https") && !str.contains(" ")
    }
}