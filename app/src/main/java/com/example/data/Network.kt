package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

object GeminiApiClient {
    private const val TAG = "GeminiApiClient"
    private const val MODEL = "gemini-3.5-flash"
    
    // Setup OkHttpClient with appropriate 60s timeouts as recommended in gemini-api SKILL
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val mediaTypeJson = "application/json; charset=utf-8".toMediaType()

    /**
     * Generates a conversational clinical response for breastfeeding mothers using the Gemini API.
     * Incorporates robust local fallback logic with clinical advice in case of network loss/empty keys.
     */
    suspend fun getBreastfeedingResolution(userPrompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.w(TAG, "Gemini API key is unconfigured. Utilizing clinical local fallback processor.")
            return@withContext getLocalClinicalResponse(userPrompt)
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL:generateContent?key=$apiKey"

        val systemInstruction = "You are Kali, an exceptionally warm, empathetic, and medically verified peer lactation consultant and pediatric healthcare companion. You speak directly to a nursing mother with reassuring, encouraging, and emotionally supportive language (using soft feminine minimalism style). Always emphasize medically safe evidence-based advice (meeting WHO and AAP guidelines). Provide clear bullet points that are highly readable on a small mobile screen. Keep replies below 3 paragraphs. Remind the mother to consult a pediatrician if symptoms (like fever, severe pain, or baby dehydration) persist, using a tiny supportive clinical disclaimer at the bottom."

        // Construct JSON manually to guarantee simplicity and zero dependency complications
        val requestJson = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", userPrompt)
                        })
                    })
                })
            })
            put("systemInstruction", JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().apply {
                        put("text", systemInstruction)
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.4)
                put("topP", 0.95)
            })
        }

        val body = requestJson.toString().toRequestBody(mediaTypeJson)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: "Unknown error"
                    Log.e(TAG, "Gemini call failed with code ${response.code}: $errorBody")
                    return@withContext getLocalClinicalResponse(userPrompt)
                }

                val responseBody = response.body?.string()
                if (responseBody.isNullOrEmpty()) {
                    return@withContext getLocalClinicalResponse(userPrompt)
                }

                val jsonResponse = JSONObject(responseBody)
                val candidates = jsonResponse.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    if (parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).getString("text")
                    }
                }
                
                return@withContext getLocalClinicalResponse(userPrompt)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during Gemini REST api request: ${e.message}", e)
            return@withContext getLocalClinicalResponse(userPrompt)
        }
    }

    /**
     * Highly responsive, medically sound local clinical responses for popular topics.
     * Ensures perfect operation when offline or if the user doesn't have an API key.
     */
    private fun getLocalClinicalResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("latch") || lower.contains("pain") || lower.contains("sore") -> {
                "Oh, raw and sore nipples can be so painful, mama, but please know you are doing an amazing job. Soreness is usually a sign that your sweet little one hasn't quite achieved a deep latch. Let's try to adjust tonight:\n\n" +
                        "• **The 'Tummy-to-Tummy' Hold**: Ensure your baby's chest and belly are flush against your body, not turned upward. Their chin should touch your breast first.\n" +
                        "• **The Chipple Tickle**: Gently brush your nipple from baby's nose down to their lip to trigger a wide 'gawking' yawn. Wait for that wide mouth before bringing them in.\n" +
                        "• **Air Drying & Recovery**: Expression of a few drops of fresh colostrum or milk over your nipple after nursing and letting it air dry can soothe sensitive skin. Lanolin cream also provides a lovely soothing barrier!\n\n" +
                        "*Disclaimers: Nursing should feel like a deep tugging but never sharp pain. If bleeding or fever occurs, an in-person lactation consult can provide a personalized clinical check.*"
            }
            lower.contains("supply") || lower.contains("increase") || lower.contains("low milk") -> {
                "Boosting your milk supply is a very common goal, mama, and your body is incredibly capable! Remember, milk production is a pure demand-and-supply system. Let's look at ways to send extra signals to your body:\n\n" +
                        "• **Amplify the Frequency**: Try to breastfeed or dry-pump every 2 to 3 hours during day cycles. Night nursing (prolactin spikes) is especially magnificent at prompting more supply!\n" +
                        "• **Double-Empty Comfort**: Offer both sides at each feeding. When baby slows down on the first breast, perform some gentle breast compressions, then switch to the other side.\n" +
                        "• **Maternal Hydration**: Ensure you are drinking enough fluids and eating warm, dense postpartum foods (oatmeal, ginger, and papaya are traditional favorites).\n\n" +
                        "*Disclaimers: Keep track of your baby's wet diapers (6+ daily is the gold standard). If baby appears lethargic, please consult your healthcare clinic immediately.*"
            }
            lower.contains("storage") || lower.contains("freeze") || lower.contains("keep") || lower.contains("temp") -> {
                "Keeping your liquid gold safe is so important for baby's health. Here are the clinical guidelines for expressing and storing human milk safely at home:\n\n" +
                        "• **Room Temperature**: Freshly expressed milk is safe on the counter (up to 25°C or 77°F) for **4 hours**.\n" +
                        "• **Refrigeration**: Fresh milk in the back of the fridge (never the door) is perfect for up to **4 days**.\n" +
                        "• **Deep Freezing**: Stored in a standard freezer bag, it remains highly nutritious for up to **6 months** (and acceptable up to 12 months).\n" +
                        "• **Defrosting**: Thaw in the fridge overnight or swirl under warm running water. **Never microwave** breastmilk, as it creates hazardous heat spots and destroys beneficial immunological proteins.\n\n" +
                        "*Guidelines conform to WHO & Academy of Breastfeeding Medicine safety standards.*"
            }
            lower.contains("bank") || lower.contains("donate") || lower.contains("donor") -> {
                "How beautiful of you to consider sharing your excess milk! Lactation donation is truly a life-saving miracle for fragile premature infants. Here is how the donation pathways function:\n\n" +
                        "• **Preparation**: Keep an extra-clean pumping regime and store milk in sterile collection bags labeled strictly with dates.\n" +
                        "• **Medical Screenings**: Accredited milk banks run a complimentary lifestyle assessment and quick blood screen (for HIV, Syphilis, and Hepatitis) to protect vulnerable babies.\n" +
                        "• **Minimum Donation**: Many hospital milk depots look for a minimum of 100-150 ounces as an initial pool, though some accommodate smaller newborn drops.\n\n" +
                        "Use our **Milk Bank Referral** screen to locate your nearest accredited partner hospital inside KalingApp!"
            }
            else -> {
                "Welcome to KalingApp Breastfeeding AI, mama. I am here to hold your hand and guide you through every developmental milestone with reassuring, evidence-based practices.\n\n" +
                        "What is on your heart today? You can ask me about:\n" +
                        "• Correcting a shallow diaper/latch correction\n" +
                        "• Relieving full, painful engorgement\n" +
                        "• Expressed breastmilk storage temperature timelines\n" +
                        "• Checking donor eligibility criteria\n\n" +
                        "Tell me what is going on, and we will walk through it together step-by-step!"
            }
        }
    }
}
