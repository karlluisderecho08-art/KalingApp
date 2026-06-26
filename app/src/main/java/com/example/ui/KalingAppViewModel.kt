package com.example.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppScreen {
    SPLASH,
    ONBOARDING,
    WELCOME,
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    HOME_DASHBOARD,
    AI_CHAT,
    KNOWLEDGE_HUB,
    ARTICLES_LIST,
    ARTICLE_DETAIL,
    COMMUNITY_NARRATIVES,
    SHARE_STORY,
    MILK_BANK_PATHWAY,
    DONOR_PATHWAY,
    RECIPIENT_PATHWAY,
    FACILITY_LIST,
    FACILITY_DETAIL,
    REFERRAL_INSTRUCTIONS,
    NOTIFICATIONS,
    USER_PROFILE,
    SETTINGS_PAGE,
    ADMIN_DASHBOARD,
    EDIT_PROFILE,
    SAVED_ARTICLES,
    NOTIFICATION_DETAILS,
    DONOR_BOOKING,
    DONOR_STATUS,
    RECIPIENT_STATUS,
    BOOKING_DETAIL
}

class KalingAppViewModel : ViewModel() {

    // --- Navigation ---
    private val _currentScreen = MutableStateFlow(AppScreen.SPLASH)
    val currentScreen = _currentScreen.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    // Navigation History for backstack support
    private val backStack = java.util.Stack<AppScreen>()

    fun navigateWithBack(screen: AppScreen) {
        backStack.push(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack() {
        if (!backStack.isEmpty()) {
            _currentScreen.value = backStack.pop()
        } else {
            _currentScreen.value = AppScreen.HOME_DASHBOARD
        }
    }

    // --- User Session State ---
    var isLoggedIn by mutableStateOf(false)
    var currentUserProfile by mutableStateOf(UserProfile())
    
    // Auth Input Fields
    var loginEmail by mutableStateOf("")
    var loginPassword by mutableStateOf("")
    var registerEmail by mutableStateOf("")
    var registerPassword by mutableStateOf("")
    var registerName by mutableStateOf("")
    var registerBabyName by mutableStateOf("")
    var recoveryEmail by mutableStateOf("")

    // Admin Credentials Switch
    var isAdminRole by mutableStateOf(false)

    // --- Onboarding Slide Index ---
    var onboardingIndex by mutableStateOf(0)

    // --- Selected Item States ---
    var selectedArticle by mutableStateOf<Article?>(null)
    var selectedFacility by mutableStateOf<Facility?>(null)
    var selectedNotification by mutableStateOf<NotificationItem?>(null)

    // --- Profile Editing Fields ---
    var editMomName by mutableStateOf("")
    var editBabyName by mutableStateOf("")
    var editBabyAgeWeeks by mutableStateOf("6")
    var editPediatricClinic by mutableStateOf("")

    fun startProfileEditing() {
        editMomName = currentUserProfile.momName
        editBabyName = currentUserProfile.babyName
        editBabyAgeWeeks = currentUserProfile.babyAgeWeeks.toString()
        editPediatricClinic = currentUserProfile.pediatricClinic
    }

    fun saveProfileEditing() {
        val ageWeeks = editBabyAgeWeeks.toIntOrNull() ?: 6
        currentUserProfile = currentUserProfile.copy(
            momName = editMomName,
            babyName = editBabyName,
            babyAgeWeeks = ageWeeks,
            pediatricClinic = editPediatricClinic
        )
    }

    // --- Search & Filters ---
    var articleQuery by mutableStateOf("")
    var selectedCategory by mutableStateOf("All Content")

    // --- Community Share Story Form ---
    var newStoryContent by mutableStateOf("")
    var newStoryTag by mutableStateOf("First-Time Mom Journey")
    var newStoryAgeGroup by mutableStateOf("Newborn (0-2 months)")

    // --- Search & Filters ---
    var recipientNeonateName by mutableStateOf("")
    var recipientClinicInfo by mutableStateOf("")
    var recipientSelectedReason by mutableStateOf("Prematurity (Low Birth Weight)")
    var recipientPrescriptionProof by mutableStateOf(false)
    var recipientFormSubmitted by mutableStateOf(false)

    // --- dynamic lists ---
    private val _aiMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val aiMessages = _aiMessages.asStateFlow()

    var isAiTyping by mutableStateOf(false)
    var chatInputText by mutableStateOf("")

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles = _articles.asStateFlow()

    private val _savedArticleIds = MutableStateFlow<Set<String>>(emptySet())
    val savedArticleIds = _savedArticleIds.asStateFlow()

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories = _stories.asStateFlow()

    private val _facilities = MutableStateFlow<List<Facility>>(emptyList())
    val facilities = _facilities.asStateFlow()

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications = _notifications.asStateFlow()

    private val _bookings = MutableStateFlow<List<BookingRequest>>(emptyList())
    val bookings = _bookings.asStateFlow()

    var selectedBooking by mutableStateOf<BookingRequest?>(null)

    private val _articleComments = MutableStateFlow<List<ArticleComment>>(emptyList())
    val articleComments = _articleComments.asStateFlow()

    var newCommentText by mutableStateOf("")

    var donorBookingFacility by mutableStateOf("")
    var donorBookingDate by mutableStateOf("")
    var donorBookingTime by mutableStateOf("")
    var donorBookingSubmitted by mutableStateOf(false)

    init {
        loadStaticData()
        // Welcome message from Kali
        _aiMessages.value = listOf(
            ChatMessage(
                id = "welcome_chat",
                text = "Hello, Rachel! How is baby James doing today? Kali is here to support your breastfeeding journey with trusted, evidence-based guidance. 🌸 Feel free to ask me anything about latching techniques, breastmilk storage rules, maternal wellness, or milk bank donations.",
                isUser = false,
                timestamp = "08:18 AM"
            )
        )
    }

    /**
     * Toggles bookmarking on an article.
     */
    fun toggleSaveArticle(articleId: String) {
        val currentSet = _savedArticleIds.value
        if (currentSet.contains(articleId)) {
            _savedArticleIds.value = currentSet - articleId
            addNotification(
                title = "Article Removed",
                description = "Saved article has been removed from your list.",
                category = "Articles"
            )
        } else {
            _savedArticleIds.value = currentSet + articleId
            addNotification(
                title = "Article Bookmarked 🌸",
                description = "Knowledge card saved. You can access this offline under your Profile.",
                category = "Articles"
            )
        }
    }

    /**
     * Custom Story Submission Flow with moderation simulation
     */
    fun submitStory() {
        if (newStoryContent.isBlank()) return

        val newStory = Story(
            id = "story_${System.currentTimeMillis()}",
            author = currentUserProfile.momName,
            babyAgeGroup = newStoryAgeGroup,
            tag = newStoryTag,
            content = newStoryContent,
            date = "Today",
            isApproved = false // Set approved flag to false first to moderate it!
        )

        // Prepend story to state
        _stories.value = listOf(newStory) + _stories.value
        newStoryContent = ""

        addNotification(
            title = "Experience Shared for Peer Quality Review 🌸",
            description = "Your beautiful story has been submitted. To ensure medically safe advice, a clinical Moderator will review it shortly.",
            category = "Moderator Alert"
        )
    }

    /**
     * Submit AI Chat queries. Triggering real Gemini assistant or local fallback cleanly.
     */
    fun sendChatMessage() {
        val query = chatInputText.trim()
        if (query.isEmpty()) return

        // 1. Add user message
        val userMsgId = "msg_${System.currentTimeMillis()}"
        val userMsg = ChatMessage(id = userMsgId, text = query, isUser = true, timestamp = "Just now")
        _aiMessages.value = _aiMessages.value + userMsg
        chatInputText = ""
        isAiTyping = true

        viewModelScope.launch {
            // Emulate slight typing latency for highly realistic UX
            delay(1200)
            val aiResponse = GeminiApiClient.getBreastfeedingResolution(query)
            val aiMsgId = "msg_${System.currentTimeMillis()}_ai"
            val aiMsg = ChatMessage(id = aiMsgId, text = aiResponse, isUser = false, timestamp = "Just now")
            _aiMessages.value = _aiMessages.value + aiMsg
            isAiTyping = false
            
            addNotification(
                title = "Kali AI Advice Received 🩺",
                description = "Review verified steps on: \"${query.take(24)}...\"",
                category = "Reminders"
            )
        }
    }

    fun submitRecipientForm() {
        if (recipientNeonateName.isBlank() || recipientClinicInfo.isBlank()) return
        recipientFormSubmitted = true
        addNotification(
            title = "Recipient Referral Submitted 🩺",
            description = "Your request for pasteurized donor human milk is under review. Hospital coordination started.",
            category = "Referrals"
        )
    }

    // --- Admin panel moderation actions ---
    fun approveStory(storyId: String) {
        _stories.value = _stories.value.map {
            if (it.id == storyId) it.copy(isApproved = true, status = "Approved Story ✓") else it
        }
        addNotification(
            title = "Story Publicly Approved",
            description = "Moderator approved a peer story for the breastfeeding community feed.",
            category = "Moderator Alert"
        )
    }

    fun rejectStory(storyId: String) {
        _stories.value = _stories.value.map {
            if (it.id == storyId) it.copy(isApproved = false, status = "Moderated / Private") else it
        }
    }

    fun addNotification(title: String, description: String, category: String) {
        val newNotif = NotificationItem(
            id = "notif_${System.currentTimeMillis()}",
            title = title,
            description = description,
            category = category,
            time = "Just now"
        )
        _notifications.value = listOf(newNotif) + _notifications.value
    }

    fun readAllNotifications() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    fun readNotification(id: String) {
        _notifications.value = _notifications.value.map {
            if (it.id == id) it.copy(isRead = true) else it
        }
    }

    fun submitDonorBooking() {
        if (donorBookingFacility.isBlank()) return
        val booking = BookingRequest(
            id = "book_${System.currentTimeMillis()}",
            type = "Donor",
            facilityName = donorBookingFacility,
            status = "Pending",
            currentStep = 1,
            totalSteps = 5,
            date = if (donorBookingDate.isNotBlank()) donorBookingDate else "To be scheduled"
        )
        _bookings.value = listOf(booking) + _bookings.value
        donorBookingSubmitted = true
        addNotification(
            title = "Donor Booking Submitted",
            description = "Your milk donation booking at ${booking.facilityName} is now pending facility confirmation.",
            category = "Referrals"
        )
    }

    fun submitArticleComment(articleId: String) {
        if (newCommentText.isBlank()) return
        val comment = ArticleComment(
            id = "comment_${System.currentTimeMillis()}",
            articleId = articleId,
            author = currentUserProfile.momName,
            content = newCommentText,
            date = "Just now",
            status = "Pending"
        )
        _articleComments.value = _articleComments.value + comment
        newCommentText = ""
        addNotification(
            title = "Comment Submitted for Review",
            description = "Your comment is pending moderator approval before becoming visible.",
            category = "Moderator Alert"
        )
    }

    private fun loadStaticData() {
        // Primary Medical/Postpartum Breastfeeding Articles
        _articles.value = listOf(
            Article(
                id = "art_1",
                title = "The Golden Hour: Securing the Perfect Newborn Latch",
                category = "Latching Techniques",
                readTime = "5 min read",
                teaser = "Discover the skin-to-skin practices that stimulate rapid colostrum release and anchor a deep painless lactation lock.",
                content = "Within the first hour following your beautiful birth, your little bundle is highly alert. This is known as 'The Golden Hour'. Placing your wet, warm baby bare-chested directly against your skin sends massive neurological triggers that release adrenaline, prolactin, and oxytocin to stimulate lactation. \n\n" +
                        "To perform the perfect biological latch, lie comfortably reclined. Place baby tummy-down on your chest. Let their head seek your breast naturally. Their wide mouth should engulf a large, asymmetrical mouthful of the under-areola, rather than just the nipple. If you feel a sharp pinching sensation, slide your clean finger into baby's cheek to break the vacuum, gently release details, and attempt the chin-first roll technique again. Consistency brings painless perfection over hours!",
                author = "Dr. Amanda Rose, IBCLC (Lactation Consultant)"
            ),
            Article(
                id = "art_2",
                title = "Liquid Gold Storage: Demystifying Times & Temperatures",
                category = "Milk Storage & Safety",
                readTime = "3 min read",
                teaser = "A simple, stress-free clinical checklist to freeze, thaw, and transport breastmilk safely without damaging vital antibodies.",
                content = "Expressed breastmilk is rich in live leucocytes, antibodies, and vital nutrients that need strict temperature compliance to keep baby safe from high environmental pathogens. Here is our official clinical summary:\n\n" +
                        "1. **Counter Room Temp (up to 25°C)**: Safe for 4 hours. Keep away from windows.\n" +
                        "2. **Standard Refrigeration (4°C)**: Best up to 4 days. Always store in the cold back shelf, never on the door pockets where temperatures swing constantly.\n" +
                        "3. **Freezer Chest (-18°C)**: Superbly nutritional for 6 months. \n\n" +
                        "When preparing a feed, thaw milk overnight in the refrigerator. Swirl the bottle gently to re-emulsify protective milk fat that naturally separates; NEVER shake aggressively. Warm by placing the bottle inside a bowl of water. Microwaving creates scalding-hot pockets and immediately deactivates anti-infective qualities.",
                author = "Professor Clara Lin, Neonatal Pathologist"
            ),
            Article(
                id = "art_3",
                title = "Maternal Diet: Supercharge Your Breastmilk with Nutrients",
                category = "Maternal Nutrition",
                readTime = "6 min read",
                teaser = "What you eat matters. Elevate iron, healthy fatty lipids, and vital hydration elements to nurture baby easily.",
                content = "Mamas, feed yourself to protect your milk! You burn roughly 500 extra calories a day when breastfeeding. This postpartum phase demands nutrient-dense selections:\n\n" +
                        "• **Healthy Fats**: Avocado, raw walnuts, and salmon load your milk with DHA, ensuring superb cognitive and visual brain development.\n" +
                        "• **Galactagogues**: Oats, fenugreek infusions, and brewer's yeast traditionally coax a higher prolactin output.\n" +
                        "• **Calcium & Minerals**: Leafy kale, safe calcium-fortified tofu, and sesame protect your mother-bone density while supplying the baby.\n" +
                        "• **Hydration Secret**: Drink a clean glass of lukewarm water every time you sit down to nurse or express milk. Fluid restriction suppresses your personal physical stamina, not your flow, but can lead to acute fatigue.",
                author = "Elena Torres, Maternal Nutrition Specialist"
            ),
            Article(
                id = "art_4",
                title = "Coping with Engorgement: Relieving Hard, Painful Breasts",
                category = "Newborn Health",
                readTime = "4 min read",
                teaser = "Learn clinical techniques of cold compression, reverse pressure softening, and lymphatic massage.",
                content = "Around days 3-5 postpartum, massive blood inflow produces firm, tight breasts. This biological surge is tissue swelling plus milk build-up, called engorgement. \n\n" +
                        "To comfortably latch a baby onto a hard, balloon-like areola, use **Reverse Pressure Softening**: press your clean index, middle, and ring fingertips flat around the base of the nipple for 60 seconds. This pushes excess lymphatic fluid back into the chest wall, creating a soft, flexible ring that is perfect for nursing. Use cool ice gel packs after breastfeeding to quickly constrict blood vessels and reduce localized swelling. Warm showers should only be used right before nursing to encourage milk flow, as excessive warmth can worsen vascular congestion.",
                author = "Dr. Amanda Rose, IBCLC"
            )
        )

        // Real Mom Peer Stories (Moderator cleared and pending)
        _stories.value = listOf(
            Story(
                id = "story_1",
                author = "Marielle Gonzales, Mom of Twins",
                babyAgeGroup = "Infant (3-6 months)",
                tag = "Working & Breastfeeding",
                content = "To my fellow working mamas: returning to the office while maintaining my milk flow felt like scaling a mountain. Today, I set up my KalingApp alerts to cue a 15-minute pump at 10 AM and 2 PM. My employer honored the lactation room, and looking at photos of my twins kept my let-down reflex strong! Don't let shame stop you—we got this!",
                date = "2 days ago",
                likes = 24
            ),
            Story(
                id = "story_2",
                author = "Samantha Cruz",
                babyAgeGroup = "Newborn (0-2 months)",
                tag = "First-Time Mom Journey",
                content = "During our first week, baby Luka dropped 9% weight. I cried during every feeding. I felt like a failure. Using KalingApp's perfect golden hour latch tutorials changed everything. My pediatrician confirmed today he gained 11 ounces, back to his birth curve! Soreness is fading, replaced by the sweetest quiet bonds.",
                date = "3 days ago",
                likes = 18
            ),
            Story(
                id = "story_3",
                author = "Nikka Santos, Pumping Pioneer",
                babyAgeGroup = "Infant (3-6 months)",
                tag = "Working & Breastfeeding",
                content = "Here is my story! I had a massive oversupply in my freezer but didn't know how to donate. I submitted my donor requirements screening inside KalingApp and just had my first collection pick-up. Knowing my excess milk helper packs will rescue a premature preemie in the St. Luke NICU brings an indescribable peace.",
                date = "4 days ago",
                likes = 32
            )
        )

        // Accredited Milk Bank Facilities list
        _facilities.value = listOf(
            Facility(
                id = "fac_1",
                name = "St. Luke's Medical Center - Human Milk Depot",
                type = "Accredited Human Milk Bank",
                contact = "+63 (2) 8789-8000 ext. NICU",
                address = "Level 2, Mother & Child Pavilion, Quezon City, Manila",
                distance = "1.2 km away",
                description = "Our main metropolitan accredited hospital depot specializing in pasteurizing excess human milk for critical low-birth-weight neonates in maternal wards."
            ),
            Facility(
                id = "fac_2",
                name = "Philippine General Hospital (PGH) Lactation Bank",
                type = "Government Accredited Donor Facility",
                contact = "+63 (02) 8554-8400 Local 3505",
                address = "Taft Avenue, Ermita, Manila, Metro Manila",
                distance = "5.8 km away",
                description = "State-run medical center accepting raw human milk drops in bulk. Provides free sterile bags and ice-pack collection transport coolers."
            ),
            Facility(
                id = "fac_3",
                name = "St. Martin de Porres Maternity Depot",
                type = "Community Medical Center Depot",
                contact = "+63 (2) 8711-4191",
                address = "156 A. Bonifacio Ave, Manila",
                distance = "8.3 km away",
                description = "A friendly community-based collection center accepting vetted donor drops. Offers direct peer counseling and home collection pickups."
            )
        )

        _bookings.value = listOf(
            BookingRequest(
                id = "book_1",
                type = "Donor",
                facilityName = "St. Luke's Medical Center",
                status = "Screening",
                currentStep = 3,
                totalSteps = 5,
                date = "June 30, 2026",
                notes = "Blood screening scheduled"
            ),
            BookingRequest(
                id = "book_2",
                type = "Recipient",
                facilityName = "Philippine General Hospital",
                status = "Pending",
                currentStep = 2,
                totalSteps = 4,
                date = "July 2, 2026",
                notes = "Awaiting prescription verification"
            )
        )

        _articleComments.value = listOf(
            ArticleComment(
                id = "comment_1",
                articleId = "art_1",
                author = "Marielle Gonzales",
                content = "This golden hour technique changed everything for us! My baby latched perfectly after following these steps.",
                date = "2 days ago",
                status = "Approved"
            ),
            ArticleComment(
                id = "comment_2",
                articleId = "art_1",
                author = "Samantha Cruz",
                content = "I had trouble with the chin-first technique at first but after a few tries it really worked. Thank you for this guide!",
                date = "1 day ago",
                status = "Approved"
            ),
            ArticleComment(
                id = "comment_3",
                articleId = "art_2",
                author = "Nikka Santos",
                content = "Very helpful storage guidelines. I was doing the freezer bags wrong before reading this.",
                date = "3 days ago",
                status = "Approved"
            ),
            ArticleComment(
                id = "comment_4",
                articleId = "art_1",
                author = "Ana Reyes",
                content = "Can I apply this technique for twins? Would love more twin-specific advice.",
                date = "5 hours ago",
                status = "Pending"
            )
        )

        // Pre-populated notification inbox
        _notifications.value = listOf(
            NotificationItem(
                id = "notif_1",
                title = "Welcome to KalingApp! 🌸",
                description = "Let's build your breastfeeding confidence. Check out the AI Assistant.",
                category = "Reminders",
                time = "3 hours ago",
                isRead = false
            ),
            NotificationItem(
                id = "notif_2",
                title = "New Medical Article Published",
                description = "\"Coping with Engorgement\" has been reviewed by IBCLC consultants.",
                category = "Articles",
                time = "5 hours ago",
                isRead = true
            )
        )
    }
}
