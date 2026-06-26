package com.example.data

data class UserProfile(
    val momName: String = "Rachel",
    val babyName: String = "James",
    val babyAgeWeeks: Int = 6,
    val breastfeedingStatus: String = "Active Lactation",
    val babyBirthDate: String = "April 10, 2026",
    val pediatricClinic: String = "St. Luke's Mother & Child Care",
    val trackingStreaks: Int = 14,
    val totalDrawnOz: Float = 48.5f
)

data class Article(
    val id: String,
    val title: String,
    val category: String, // "Latching Techniques", "Milk Storage & Safety", "Maternal Nutrition", "Newborn Health"
    val readTime: String, // "4 min read"
    val teaser: String,
    val content: String,
    val author: String,
    val rating: String = "4.9 ★",
    val evidenceLabel: String = "Verified Pediatrician Reviewed",
    val date: String = "May 2026"
)

data class Story(
    val id: String,
    val author: String,
    val babyAgeGroup: String, // "Newborn (0-2 months)", "Infant (3-6 months)"
    val tag: String, // "First-Time Mom Journey", "Working & Breastfeeding"
    val content: String,
    val date: String,
    val status: String = "Verified Mom Narrative",
    val likes: Int = 12,
    val isApproved: Boolean = true,
    val moderationNote: String = ""
)

data class Facility(
    val id: String,
    val name: String,
    val type: String, // "Accredited Human Milk Bank", "Hospital Depot"
    val contact: String,
    val address: String,
    val distance: String,
    val description: String,
    val operatingHours: String = "8:00 AM - 4:00 PM (Mon-Fri)",
    val donorRequirements: String = "Negative Hepatitis, HIV & Syphilis screen; active lactation; excess supply",
    val recipientRequirements: String = "Doctor prescription showing medical need; neonate or low birth weight status"
)

data class NotificationItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String, // "Reminders", "Articles", "Referrals", "Moderator Alert"
    val time: String,
    val isRead: Boolean = false
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)

data class BookingRequest(
    val id: String,
    val type: String,
    val facilityName: String,
    val status: String = "Pending",
    val currentStep: Int = 1,
    val totalSteps: Int = 5,
    val date: String,
    val notes: String = ""
)

data class ArticleComment(
    val id: String,
    val articleId: String,
    val author: String,
    val content: String,
    val date: String,
    val status: String = "Pending"
)
