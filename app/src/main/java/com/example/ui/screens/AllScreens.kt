package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.AppScreen
import com.example.ui.KalingAppViewModel
import com.example.ui.theme.*
import kotlinx.coroutines.delay

/**
 * Main switchboard for KalingApp UI/UX presentation.
 * Displays screens based on currentState or overlays.
 */
@Composable
fun MainAppContainer(viewModel: KalingAppViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PureWhite, WarmBeige),
                    startY = 0f,
                    endY = 1500f
                )
            )
    ) {
        // Safe drawing area wrapper
        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar(currentScreen)) {
                    KalingBottomNavigation(viewModel, currentScreen)
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        AppScreen.SPLASH -> SplashScreen(viewModel)
                        AppScreen.ONBOARDING -> OnboardingScreen(viewModel)
                        AppScreen.WELCOME -> WelcomeScreen(viewModel)
                        AppScreen.LOGIN -> LoginScreen(viewModel)
                        AppScreen.REGISTER -> RegisterScreen(viewModel)
                        AppScreen.FORGOT_PASSWORD -> ForgotPasswordScreen(viewModel)
                        AppScreen.HOME_DASHBOARD -> HomeDashboardScreen(viewModel)
                        AppScreen.AI_CHAT -> AiChatScreen(viewModel)
                        AppScreen.KNOWLEDGE_HUB -> KnowledgeHubScreen(viewModel)
                        AppScreen.ARTICLES_LIST -> KnowledgeHubScreen(viewModel)
                        AppScreen.ARTICLE_DETAIL -> ArticleDetailScreen(viewModel)
                        AppScreen.COMMUNITY_NARRATIVES -> CommunityNarrativesScreen(viewModel)
                        AppScreen.SHARE_STORY -> ShareStoryScreen(viewModel)
                        AppScreen.MILK_BANK_PATHWAY -> MilkBankPathwayScreen(viewModel)
                        AppScreen.DONOR_PATHWAY -> DonorPathwayScreen(viewModel)
                        AppScreen.RECIPIENT_PATHWAY -> RecipientPathwayScreen(viewModel)
                        AppScreen.FACILITY_LIST -> FacilityListScreen(viewModel)
                        AppScreen.FACILITY_DETAIL -> FacilityDetailScreen(viewModel)
                        AppScreen.REFERRAL_INSTRUCTIONS -> ReferralInstructionsScreen(viewModel)
                        AppScreen.NOTIFICATIONS -> NotificationsScreen(viewModel)
                        AppScreen.USER_PROFILE -> UserProfileScreen(viewModel)
                        AppScreen.SETTINGS_PAGE -> SettingsPageScreen(viewModel)
                        AppScreen.ADMIN_DASHBOARD -> DesktopAdminDashboard(viewModel)
                        AppScreen.EDIT_PROFILE -> EditProfileScreen(viewModel)
                        AppScreen.SAVED_ARTICLES -> SavedArticlesScreen(viewModel)
                        AppScreen.NOTIFICATION_DETAILS -> NotificationDetailsScreen(viewModel)
                        AppScreen.DONOR_BOOKING -> DonorBookingScreen(viewModel)
                        AppScreen.DONOR_STATUS -> DonorStatusScreen(viewModel)
                        AppScreen.RECIPIENT_STATUS -> RecipientStatusScreen(viewModel)
                        AppScreen.BOOKING_DETAIL -> BookingDetailScreen(viewModel)
                    }
                }
            }
        }
    }
}

/**
 * Decides whether bottom navigation is active.
 */
private fun shouldShowBottomBar(screen: AppScreen): Boolean {
    return when (screen) {
        AppScreen.SPLASH, AppScreen.ONBOARDING, AppScreen.WELCOME,
        AppScreen.LOGIN, AppScreen.REGISTER, AppScreen.FORGOT_PASSWORD,
        AppScreen.ADMIN_DASHBOARD -> false
        else -> true
    }
}

// Reusable drawing decorator modifier for soft pink wave background and elegant gold curved lines in corners
fun Modifier.kalingDecorativeBackground(
    waveColor: Color = BlushPink,
    lineColor: Color = GoldAccent
) = this.drawBehind {
    val w = size.width
    val h = size.height

    // 1. Top Right Corner: Elegant gold thin concentric curved arcs (luxury healthcare styling)
    val pathGold1 = androidx.compose.ui.graphics.Path().apply {
        moveTo(w * 0.65f, 0f)
        cubicTo(w * 0.78f, h * 0.06f, w * 0.90f, h * 0.05f, w, h * 0.12f)
    }
    drawPath(
        path = pathGold1,
        color = lineColor.copy(alpha = 0.35f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
    )

    val pathGold2 = androidx.compose.ui.graphics.Path().apply {
        moveTo(w * 0.52f, 0f)
        cubicTo(w * 0.72f, h * 0.10f, w * 0.85f, h * 0.08f, w, h * 0.18f)
    }
    drawPath(
        path = pathGold2,
        color = lineColor.copy(alpha = 0.20f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5.dp.toPx())
    )

    // 2. Bottom Portion: Soft layered biological pink waves (emotional maternal warmth)
    val wavePath1 = androidx.compose.ui.graphics.Path().apply {
        moveTo(0f, h * 0.83f)
        cubicTo(
            w * 0.25f, h * 0.79f,
            w * 0.65f, h * 0.93f,
            w, h * 0.85f
        )
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }
    drawPath(
        path = wavePath1,
        color = waveColor.copy(alpha = 0.35f),
        style = androidx.compose.ui.graphics.drawscope.Fill
    )

    val wavePath2 = androidx.compose.ui.graphics.Path().apply {
        moveTo(0f, h * 0.87f)
        cubicTo(
            w * 0.32f, h * 0.89f,
            w * 0.72f, h * 0.82f,
            w, h * 0.91f
        )
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }
    drawPath(
        path = wavePath2,
        color = waveColor.copy(alpha = 0.20f),
        style = androidx.compose.ui.graphics.drawscope.Fill
    )
}

// ==========================================
// 1. BRAND LOGO COMPOSABLE & SPLASH SCREEN
// ==========================================
@Composable
fun KalingAppLogo(
    modifier: Modifier = Modifier,
    logoColor: Color = SoftPink,
    accentColor: Color = GoldAccent,
    backgroundTint: Color = BlushPink
) {
    Image(
        painter = painterResource(id = com.example.R.drawable.kalingapp_logo),
        contentDescription = "KalingApp Logo",
        modifier = modifier.testTag("kaling_app_logo_image")
    )
}

@Composable
fun SplashScreen(viewModel: KalingAppViewModel) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 second duration
        viewModel.navigateTo(AppScreen.ONBOARDING)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LightBeige, BlushPink),
                    startY = 0f,
                    endY = 2000f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-30).dp)
        ) {
            // High fidelity Mother-and-Baby official splash logo
            // Optimized size (420.dp) for high visual impact without clipping
            Image(
                painter = painterResource(id = com.example.R.drawable.kalingapp_splash),
                contentDescription = "KalingApp Splash Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(420.dp)
                    .testTag("splash_logo_image")
            )
            
            Spacer(modifier = Modifier.height(2.dp)) // Ultra-compact spacing
            
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = SoftPink)) {
                        append("Kaling")
                    }
                    withStyle(style = SpanStyle(color = GoldAccent)) {
                        append("App")
                    }
                },
                fontSize = 62.sp,
                fontWeight = FontWeight.Medium, // Refined Medium weight for luxury look
                fontFamily = FontFamily.Serif,
                letterSpacing = 0.5.sp
            )
            
            Spacer(modifier = Modifier.height(2.dp)) // Tight gap per reference

            Text(
                text = "Nurturing Mothers, Sustaining Life",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                color = GoldAccent.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CircularProgressIndicator(
                color = SoftPink,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .size(24.dp)
                    .testTag("splash_progress")
            )
        }

        // Versioning footer
        Text(
            text = "v1.4",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText.copy(alpha = 0.3f)
        )
    }
}

// ==========================================
// 2. ONBOARDING SCREEN
// ==========================================
@Composable
fun OnboardingScreen(viewModel: KalingAppViewModel) {
    val slides = listOf(
        Triple(
            "AI Guidance",
            "Get instant medically sound guidance from Kali, our smart robot assistant, on latch positioning, colostrum flow, and safe temperature limits.",
            Icons.Filled.SmartToy
        ),
        Triple(
            "Verified Knowledge",
            "Access our accredited pediatric library. Read articles and safety guides fully vetted under WHO standards by IBCLC professionals.",
            Icons.Filled.MenuBook
        ),
        Triple(
            "Milk Bank Support",
            "Coordinate safely with accredited donor milk banks. Track step-by-step checklists to contribute excess supply or register as a recipient.",
            Icons.Filled.LocalHospital
        ),
        Triple(
            "Maternal Wellness",
            "Join moderated support circles of first-time mothers. Share your narrative post-partum, log lactation sessions, and monitor streaks.",
            Icons.Filled.Spa
        )
    )

    val currentSlide = slides[viewModel.onboardingIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top skip action
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                KalingAppLogo(modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = SoftPink)) {
                            append("Kaling")
                        }
                        withStyle(style = SpanStyle(color = GoldAccent)) {
                            append("App")
                        }
                    },
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp
                )
            }
            TextButton(
                onClick = { viewModel.navigateTo(AppScreen.WELCOME) },
                colors = ButtonDefaults.textButtonColors(contentColor = GoldAccent)
            ) {
                Text("Skip", fontWeight = FontWeight.Bold)
            }
        }

        // Center card container
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = BorderStroke(1.dp, BlushPink.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Giant icon highlight with gold accented rings
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(BlushPink, CircleShape)
                        .border(1.5.dp, GoldAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = currentSlide.third,
                        contentDescription = "Slide icon",
                        modifier = Modifier.size(52.dp),
                        tint = SoftPink
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = currentSlide.first,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftPink,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = currentSlide.second,
                    fontSize = 13.5.sp,
                    color = CharcoalText.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 21.sp
                )
            }
        }

        // Page indictors and Bottom controller
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                slides.forEachIndexed { i, _ ->
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(if (viewModel.onboardingIndex == i) 24.dp else 6.dp)
                            .background(
                                color = if (viewModel.onboardingIndex == i) SoftPink else SoftPink.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }

            Button(
                onClick = {
                    if (viewModel.onboardingIndex < slides.size - 1) {
                        viewModel.onboardingIndex++
                    } else {
                        viewModel.onboardingIndex = 0
                        viewModel.navigateTo(AppScreen.WELCOME)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("onboarding_next_button"),
                colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (viewModel.onboardingIndex == slides.size - 1) "Get Started ✓" else "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            }
        }
    }
}

// ==========================================
// 3. WELCOME SCREEN
// ==========================================
@Composable
fun WelcomeScreen(viewModel: KalingAppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Reduced top whitespace

        Image(
            painter = painterResource(id = com.example.R.drawable.kalingapp_splash),
            contentDescription = "KalingApp Welcome Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(340.dp) // Increased size per request
                .testTag("welcome_logo_image")
        )

        Spacer(modifier = Modifier.height(8.dp)) // Tightened luxury spacing

        Text(
            text = "Welcome to KalingApp",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Serif,
            color = SoftPink,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Supporting you through every single step of your breastfeeding, lactation safety, and milk donor pathways.",
            fontSize = 15.sp,
            color = CharcoalText.copy(alpha = 0.65f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            lineHeight = 22.sp
        )

        // Divider gold line
        HorizontalDivider(
            modifier = Modifier
                .width(60.dp)
                .padding(vertical = 24.dp),
            thickness = 2.dp,
            color = GoldAccent
        )

        Button(
            onClick = { viewModel.navigateTo(AppScreen.LOGIN) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("welcome_login_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Login into your Account", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PureWhite)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { viewModel.navigateTo(AppScreen.REGISTER) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("welcome_register_btn"),
            border = BorderStroke(1.5.dp, SoftPink),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = SoftPink),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Create New Account", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = {
                viewModel.isLoggedIn = true
                viewModel.navigateTo(AppScreen.HOME_DASHBOARD)
            },
            colors = ButtonDefaults.textButtonColors(contentColor = GoldAccent)
        ) {
            Text("Bypass / Quick-Access Demo Mode", fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// 4. LOGIN SCREEN
// ==========================================
@Composable
fun LoginScreen(viewModel: KalingAppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.WELCOME) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = SoftPink)) {
                            append("Kaling")
                        }
                        withStyle(style = SpanStyle(color = GoldAccent)) {
                            append("App")
                        }
                    },
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp
                )
            }
            KalingAppLogo(modifier = Modifier.size(36.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Welcome Back",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Access your lactation metrics and breastfeeding plans.",
            fontSize = 14.sp,
            color = CharcoalText.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 28.dp)
        )

        // Email field
        OutlinedTextField(
            value = viewModel.loginEmail,
            onValueChange = { viewModel.loginEmail = it },
            label = { Text("Email Address") },
            placeholder = { Text("email@kalingapp.org") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login_email_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SoftPink,
                unfocusedBorderColor = SoftPink.copy(alpha = 0.4f),
                focusedLabelColor = SoftPink
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password field
        OutlinedTextField(
            value = viewModel.loginPassword,
            onValueChange = { viewModel.loginPassword = it },
            label = { Text("Account Password") },
            placeholder = { Text("••••••••") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login_password_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SoftPink,
                unfocusedBorderColor = SoftPink.copy(alpha = 0.4f),
                focusedLabelColor = SoftPink
            ),
            singleLine = true
        )

        // Administrative Role Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.isAdminRole,
                    onCheckedChange = { viewModel.isAdminRole = it },
                    colors = CheckboxDefaults.colors(checkedColor = GoldAccent)
                )
                Text(
                    text = "Request Admin Dashboard Entry",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = CharcoalText.copy(alpha = 0.8f)
                )
            }
        }

        Button(
            onClick = {
                viewModel.isLoggedIn = true
                if (viewModel.isAdminRole) {
                    viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD)
                } else {
                    viewModel.navigateTo(AppScreen.HOME_DASHBOARD)
                }
                viewModel.addNotification(
                    title = "Secure Login Confirmed",
                    description = "Welcome back, Mom! Synchronization complete.",
                    category = "Reminders"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("login_submit_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Sign In Securely", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PureWhite)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { viewModel.navigateTo(AppScreen.FORGOT_PASSWORD) },
            colors = ButtonDefaults.textButtonColors(contentColor = GoldAccent)
        ) {
            Text("Recover Forgotten Password?", fontWeight = FontWeight.SemiBold)
        }
    }
}

// ==========================================
// 5. REGISTER SCREEN
// ==========================================
@Composable
fun RegisterScreen(viewModel: KalingAppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.WELCOME) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = SoftPink)) {
                            append("Kaling")
                        }
                        withStyle(style = SpanStyle(color = GoldAccent)) {
                            append("App")
                        }
                    },
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp
                )
            }
            KalingAppLogo(modifier = Modifier.size(36.dp))
        }

        Text(
            text = "Join KalingApp",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        )
        Text(
            text = "Synchronize medical timelines & donor compatibility.",
            fontSize = 13.sp,
            color = CharcoalText.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = viewModel.registerName,
            onValueChange = { viewModel.registerName = it },
            label = { Text("Mother's Full Name") },
            placeholder = { Text("Rachel Gonzales") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reg_name_input"),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.registerBabyName,
            onValueChange = { viewModel.registerBabyName = it },
            label = { Text("Baby's First Name (or 'Expecting')") },
            placeholder = { Text("James") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reg_baby_input"),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.registerEmail,
            onValueChange = { viewModel.registerEmail = it },
            label = { Text("Email Address") },
            placeholder = { Text("user@kalingapp.org") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reg_email_input"),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.registerPassword,
            onValueChange = { viewModel.registerPassword = it },
            label = { Text("Secure Password") },
            placeholder = { Text("••••••••") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reg_password_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (viewModel.registerName.isNotBlank()) {
                    viewModel.currentUserProfile = UserProfile(
                        momName = viewModel.registerName,
                        babyName = if (viewModel.registerBabyName.isNotBlank()) viewModel.registerBabyName else "James"
                    )
                }
                viewModel.isLoggedIn = true
                viewModel.navigateTo(AppScreen.HOME_DASHBOARD)
                viewModel.addNotification(
                    title = "Welcome, ${viewModel.currentUserProfile.momName}! 🌸",
                    description = "Lactation dashboard initialized for ${viewModel.currentUserProfile.babyName}.",
                    category = "Reminders"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("reg_submit_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Register securely ✓", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

// ==========================================
// 6. FORGOT PASSWORD
// ==========================================
@Composable
fun ForgotPasswordScreen(viewModel: KalingAppViewModel) {
    var checkSent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.LOGIN) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = SoftPink)) {
                            append("Kaling")
                        }
                        withStyle(style = SpanStyle(color = GoldAccent)) {
                            append("App")
                        }
                    },
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp
                )
            }
            KalingAppLogo(modifier = Modifier.size(36.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Reset Password",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your registered email below. We'll send an authentication recovery link to restore your clinical tracking.",
            fontSize = 14.sp,
            color = CharcoalText.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = viewModel.recoveryEmail,
            onValueChange = { viewModel.recoveryEmail = it },
            label = { Text("Registered Email Address") },
            placeholder = { Text("mama@email.com") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("recovery_email_input"),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (checkSent) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = GoldAccentLight),
                border = BorderStroke(1.dp, GoldAccent)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "Sent", tint = GoldAccent)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Recovery token transmission triggered! Check your inbox in 1-2 minutes.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = CharcoalText
                    )
                }
            }
        }

        Button(
            onClick = {
                if (viewModel.recoveryEmail.isNotBlank()) {
                    checkSent = true
                    viewModel.addNotification(
                        title = "Security Recovery Sent",
                        description = "Forgot Password action triggered for email ${viewModel.recoveryEmail}.",
                        category = "Moderator Alert"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("recovery_submit_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Verify Email Identity", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

// ==========================================
// 7. HOME DASHBOARD SCREEN
// ==========================================
@Composable
fun HomeDashboardScreen(viewModel: KalingAppViewModel) {
    val profile = viewModel.currentUserProfile
    val notifications by viewModel.notifications.collectAsState()
    val unreadCount = notifications.count { !it.isRead }

    // Interactive tracker local states for ultra-realism and real-time feel
    var waterGlassCount by remember { mutableStateOf(0) }
    var isTimerActive by remember { mutableStateOf(false) }
    var timerSeconds by remember { mutableStateOf(0) }
    var selectedFeedingSide by remember { mutableStateOf("Left") }
    var lactationLogMessage by remember { mutableStateOf("Ready for next feeding session") }

    // Automatic stopwatch incrementer using LaunchedEffect
    LaunchedEffect(isTimerActive) {
        if (isTimerActive) {
            while (true) {
                delay(1000)
                timerSeconds++
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Clinical Trust Standards top banner (AAP & WHO Protocol)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = GoldAccentLight.copy(alpha = 0.6f)),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.25f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Clinical Trust Certified",
                    tint = GoldAccent,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "WHO & AAP Clinical Standards Compliant • IBCLC Certified",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Top Header Greeting with User Details & Admin Portal Trigger
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good Morning, ${profile.momName}! 🌸",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = CharcoalText,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "How is baby ${profile.babyName} doing today?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = CharcoalGray,
                    modifier = Modifier.padding(top = 2.dp, bottom = 4.dp)
                )
                Text(
                    text = "Care Track • ${profile.babyAgeWeeks} Weeks Old",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CharcoalGray.copy(0.8f)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Notifications Support Feature Button
                IconButton(
                    onClick = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) },
                    modifier = Modifier.size(44.dp)
                ) {
                    BadgedBox(
                        badge = {
                            if (unreadCount > 0) {
                                Badge(
                                    containerColor = SoftPink,
                                    contentColor = PureWhite
                                ) {
                                    Text(unreadCount.toString(), fontSize = 10.sp)
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "View System Notifications",
                            tint = CharcoalGray,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Quick Toggle Admin entry
                IconButton(
                    onClick = { viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD) },
                    modifier = Modifier
                        .size(44.dp)
                        .background(GoldAccentLight, CircleShape)
                        .border(1.dp, GoldAccent.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AdminPanelSettings,
                        contentDescription = "Administrator Access Portal",
                        tint = GoldAccent,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        // High Fidelity Lactation Streaks and Milestones Hero Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.15f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(BlushPink.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "ACTIVE POSTPARTUM JOURNEY",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftPinkDark,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Goal Vetted",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Streaks graphic circle
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(BlushPink, CircleShape)
                            .border(2.dp, SoftPink, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${profile.trackingStreaks}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = SoftPinkDark
                            )
                            Text(
                                text = "days",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SoftPinkDark.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Your Hydration & Milk Output Streak",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = CharcoalText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Every ounce of milk protects Baby ${profile.babyName}. Keep the incredible momentum going, mama!",
                            fontSize = 12.sp,
                            color = CharcoalGray,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // ==========================================
        // CLINICAL CARE TRACKER WORKSPACE (NEW INTERACTIVE FEATURE)
        // ==========================================
        Text(
            text = "Interactive Care Trackers",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card Left: Breastfeeding Co-op Live Timer
            Card(
                modifier = Modifier
                    .weight(1.1f)
                    .height(180.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.15f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("PUMP/FEED TIMER", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(if (isTimerActive) Color.Green else Color.Gray, CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        // Human readable Minutes:Seconds format
                        val minutes = timerSeconds / 60
                        val seconds = timerSeconds % 60
                        val timeDisplay = String.format("%02d:%02d", minutes, seconds)
                        
                        Text(
                            text = timeDisplay,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Light,
                            color = CharcoalText,
                            letterSpacing = 1.sp
                        )
                        
                        // Active side chooser (Left or Right breast)
                        Row(
                            modifier = Modifier.padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf("Left", "Right").forEach { side ->
                                val sel = selectedFeedingSide == side
                                Box(
                                    modifier = Modifier
                                        .background(
                                            if (sel) SoftPink else BlushPink.copy(alpha = 0.4f),
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable { selectedFeedingSide = side }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(side, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (sel) PureWhite else SoftPinkDark)
                                }
                            }
                        }
                    }

                    // Compact controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { 
                                isTimerActive = !isTimerActive 
                                if (!isTimerActive && timerSeconds > 0) {
                                    lactationLogMessage = "Logged ${timerSeconds / 60}m on $selectedFeedingSide breast • Saved"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isTimerActive) Color(0xFFE53935) else SoftPink,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.testTag("feed_timer_toggle")
                        ) {
                            Icon(
                                imageVector = if (isTimerActive) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = PureWhite,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isTimerActive) "Pause" else "Start", fontSize = 11.sp, color = PureWhite, fontWeight = FontWeight.Bold)
                        }

                        if (timerSeconds > 0) {
                            TextButton(
                                onClick = {
                                    isTimerActive = false
                                    timerSeconds = 0
                                    lactationLogMessage = "Timer cleared"
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Reset", color = CharcoalGray, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            // Card Right: Breastfeeding Hydration Tracker Water Logger widget
            Card(
                modifier = Modifier
                    .weight(0.9f)
                    .height(180.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.15f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("HYDRATION", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                            Icon(
                                imageVector = Icons.Filled.Spa,
                                contentDescription = null,
                                tint = SoftPink.copy(alpha = 0.5f),
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "$waterGlassCount / 10",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CharcoalText
                        )
                        Text(
                            text = "Glasses Requisite",
                            fontSize = 11.sp,
                            color = CharcoalGray,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Hydration progress lines indicator
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .background(SoftGray, RoundedCornerShape(3.dp))
                        ) {
                            val ratio = (waterGlassCount.toFloat() / 10f).coerceIn(0f, 1f)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(ratio)
                                    .fillMaxHeight()
                                    .background(SoftPink, RoundedCornerShape(3.dp))
                            )
                        }
                    }

                    Button(
                        onClick = { 
                            if (waterGlassCount < 12) waterGlassCount++ 
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = PureWhite),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("log_water_btn"),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        Text("+1 Glass Water 💧", fontSize = 10.sp, color = PureWhite, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Mini status logger text banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .background(LightBeige, RoundedCornerShape(10.dp))
                .border(1.dp, SoftGray, RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(SoftPink, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Log: $lactationLogMessage",
                    fontSize = 11.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = CharcoalGray
                )
            }
        }


        // Primary Services Title Header
        Text(
            text = "KalingApp Interactive Services",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Core detailed service cards with premium aesthetic details
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            
            // Service 1: AI Latching & Nursing Assistant Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo(AppScreen.AI_CHAT) }
                    .testTag("service_ai_chat"),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(BlushPink, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.SmartToy, "AI icon", tint = SoftPink, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .background(BlushPink.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("CLINICAL SUPPORT AI", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = SoftPinkDark)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Kali Lactation AI", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 15.sp)
                        Text("Real-time support on latching & safe storage.", fontSize = 11.sp, color = CharcoalGray)
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Navigate Screen",
                        tint = SoftPink.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Service 2: Milk Bank Pathways High priority
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo(AppScreen.MILK_BANK_PATHWAY) }
                    .testTag("service_milk_bank"),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(GoldAccentLight, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.LocalHospital, "Lactation depot", tint = GoldAccent, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .background(GoldAccentLight.copy(alpha = 0.7f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("REFERRAL & SUPPORT CENTERS", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Lactation Support & Referral", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 15.sp)
                        Text("Find accredited milk banks and professional support clinics.", fontSize = 11.sp, color = CharcoalGray)
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Navigate Screen",
                        tint = GoldAccent.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Service 3: Verified Knowledge Hub
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo(AppScreen.KNOWLEDGE_HUB) }
                    .testTag("service_knowledge_hub"),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(BlushPink, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.MenuBook, "Book icon", tint = SoftPink, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .background(BlushPink.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("ACCREDITED LIBRARY", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = SoftPinkDark)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Verified Pediatric Library", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 15.sp)
                        Text("WHO evidence compliance reading list & safety tips.", fontSize = 11.sp, color = CharcoalGray)
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Navigate Screen",
                        tint = SoftPink.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Service 4: Community Narratives Vetted Stories
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo(AppScreen.COMMUNITY_NARRATIVES) }
                    .testTag("service_community"),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(GoldAccentLight, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Spa, "Peer community", tint = GoldAccent, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .background(GoldAccentLight.copy(alpha = 0.7f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("CO-OP STORY CIRCLES", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Story Circles & Co-op Narratives", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 15.sp)
                        Text("Read moderated first-time mom journey narratives.", fontSize = 11.sp, color = CharcoalGray)
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Navigate Screen",
                        tint = GoldAccent.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

// ==========================================
// 8. AI BREASTFEEDING CHAT SCREEN & 9. AI RESPONSE
// ==========================================
@Composable
fun AiChatScreen(viewModel: KalingAppViewModel) {
    val messages by viewModel.aiMessages.collectAsState()
    val isTyping = viewModel.isAiTyping

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
    ) {
        // App top header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(BlushPink, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.SmartToy, "AI Assistant", tint = SoftPink, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Kali Clinical AI", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 15.sp)
                            Text("Continuous medical assistance", fontSize = 11.sp, color = CharcoalText.copy(0.5f))
                        }
                    }

                    // Support Label
                    Box(
                        modifier = Modifier
                            .background(GoldAccentLight, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("Informational Support", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Medical Safety Disclaimer Box
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BlushPink.copy(0.6f)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "🩺 Clinical Disclaimer: Kali provides vetted support but is NOT a dynamic diagnostic substitute for in-person pediatric reviews.",
                        fontSize = 10.5.sp,
                        color = SoftPinkDark,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Messages list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message)
            }

            if (isTyping) {
                item {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(PureWhite, RoundedCornerShape(16.dp))
                                .border(1.dp, SoftPink.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Kali is evaluating guidelines",
                                    fontSize = 12.sp,
                                    color = SoftPinkDark.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                var activeDot by remember { mutableStateOf(0) }
                                LaunchedEffect(activeDot) {
                                    delay(250)
                                    activeDot = (activeDot + 1) % 3
                                }
                                for (i in 0 until 3) {
                                    val sizeScale = if (activeDot == i) 7.dp else 5.dp
                                    val colorScale = if (activeDot == i) SoftPink else SoftPink.copy(alpha = 0.4f)
                                    Box(
                                        modifier = Modifier
                                            .size(sizeScale)
                                            .background(colorScale, CircleShape)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom text insertion panel
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.chatInputText,
                    onValueChange = { viewModel.chatInputText = it },
                    placeholder = { Text("Ask about soreness, low supply, storage temperature...") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_text_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SoftPink,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { viewModel.sendChatMessage() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(SoftPink, RoundedCornerShape(12.dp))
                        .testTag("chat_send_button")
                ) {
                    Icon(Icons.Filled.Send, "Send button", tint = PureWhite)
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    } else {
        RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    }

    val containerColor = if (message.isUser) SoftPink else PureWhite
    val contentTextColor = if (message.isUser) PureWhite else CharcoalText
    val align = if (message.isUser) Alignment.End else Alignment.Start

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = align
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor),
            shape = bubbleShape,
            border = if (!message.isUser) BorderStroke(1.dp, SoftPink.copy(alpha = 0.12f)) else null,
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    color = contentTextColor,
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.timestamp,
                    fontSize = 9.sp,
                    color = if (message.isUser) PureWhite.copy(0.7f) else CharcoalText.copy(0.4f),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

// ==========================================
// 10. VERIFIED KNOWLEDGE HUB & 11. ARTICLES LIST
// ==========================================
@Composable
fun KnowledgeHubScreen(viewModel: KalingAppViewModel) {
    val articles by viewModel.articles.collectAsState()
    val search = viewModel.articleQuery
    val activeCategory = viewModel.selectedCategory

    val categories = listOf("All Content", "Latching Techniques", "Milk Storage & Safety", "Maternal Nutrition")

    // Filter list
    val filteredArticles = articles.filter { article ->
        val matchesSearch = article.title.lowercase().contains(search.lowercase()) ||
                article.teaser.lowercase().contains(search.lowercase())
        val matchesCategory = activeCategory == "All Content" || article.category == activeCategory
        matchesSearch && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Verified Pediatric Library", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Large search bar
        OutlinedTextField(
            value = viewModel.articleQuery,
            onValueChange = { viewModel.articleQuery = it },
            placeholder = { Text("Search medical guides...") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("article_search_input"),
            leadingIcon = { Icon(Icons.Filled.Search, "Search", tint = SoftPink) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SoftPink,
                focusedLabelColor = SoftPink
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category selections
        ScrollableRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                val isSelected = activeCategory == category
                if (isSelected) {
                    Button(
                        onClick = { viewModel.selectedCategory = category },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftPink,
                            contentColor = PureWhite
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(category, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                    }
                } else {
                    OutlinedButton(
                        onClick = { viewModel.selectedCategory = category },
                        border = BorderStroke(1.dp, SoftPink),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SoftPink
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(category, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "$activeCategory Checklist (${filteredArticles.size} Guides)",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredArticles.isEmpty()) {
            EmptyStatePlaceholder(
                title = "No Pediatric Summaries Found",
                description = "We couldn't locate any clinical material matching '$search' under '$activeCategory'. Try search queries like 'latch', 'storage', 'engorgement', or 'diet' for evidence-based maternal guidance.",
                icon = Icons.Default.Search,
                actionLabel = "Reset Search Filters",
                onAction = {
                    viewModel.articleQuery = ""
                    viewModel.selectedCategory = "All Content"
                },
                modifier = Modifier.padding(top = 24.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredArticles) { article ->
                    ArticleCard(article = article) {
                        viewModel.selectedArticle = article
                        viewModel.navigateWithBack(AppScreen.ARTICLE_DETAIL)
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("article_card_${article.id}"),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, BlushPink.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Tag
                Box(
                    modifier = Modifier
                        .background(BlushPink, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(article.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                }

                Text(article.readTime, fontSize = 11.sp, color = CharcoalText.copy(0.5f))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = article.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = article.teaser,
                fontSize = 12.5.sp,
                color = CharcoalText.copy(0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.VerifiedUser, "Vetted", tint = GoldAccent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = article.evidenceLabel,
                    fontSize = 11.sp,
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ==========================================
// 12. ARTICLE DETAILS SCREEN
// ==========================================
@Composable
fun ArticleDetailScreen(viewModel: KalingAppViewModel) {
    val article = viewModel.selectedArticle ?: return
    val savedIds by viewModel.savedArticleIds.collectAsState()
    val isBookmarked = savedIds.contains(article.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }

            IconButton(
                onClick = { viewModel.toggleSaveArticle(article.id) },
                modifier = Modifier.testTag("save_article_btn")
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save",
                    tint = SoftPink
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Category + rating
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(BlushPink, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(article.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SoftPink)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("⭐ ${article.rating}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.VerifiedUser, "Accredited", tint = GoldAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(article.evidenceLabel, fontSize = 12.sp, color = GoldAccent, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Written by: ${article.author}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText.copy(0.6f)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SoftGray)

        Text(
            text = article.content,
            fontSize = 14.5.sp,
            color = CharcoalText.copy(0.9f),
            lineHeight = 23.sp
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp), color = SoftGray)

        val allComments by viewModel.articleComments.collectAsState()
        val articleComments = allComments.filter { it.articleId == article.id }
        val approvedComments = articleComments.filter { it.status == "Approved" }
        val pendingComments = articleComments.filter { it.status == "Pending" && it.author == viewModel.currentUserProfile.momName }

        Text(
            text = "Community Discussion (${approvedComments.size})",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText
        )
        Text(
            text = "Comments are moderated for clinical safety",
            fontSize = 11.sp,
            color = CharcoalGray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = viewModel.newCommentText,
            onValueChange = { viewModel.newCommentText = it },
            placeholder = { Text("Share your experience or ask a question...") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("article_comment_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.submitArticleComment(article.id) },
            modifier = Modifier
                .align(Alignment.End)
                .testTag("submit_comment_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Post Comment", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
        }

        Spacer(modifier = Modifier.height(16.dp))

        pendingComments.forEach { comment ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                colors = CardDefaults.cardColors(containerColor = GoldAccentLight.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(comment.author, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CharcoalText)
                        Box(
                            modifier = Modifier
                                .background(GoldAccent.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("PENDING REVIEW", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(comment.content, fontSize = 12.5.sp, color = CharcoalText.copy(0.8f), lineHeight = 17.sp)
                    Text(comment.date, fontSize = 10.sp, color = CharcoalGray, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }

        approvedComments.forEach { comment ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, SoftGray)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(BlushPink, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(comment.author.take(1), fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(comment.author, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CharcoalText)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Verified, "Approved", tint = SoftPink, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Approved", fontSize = 9.sp, color = SoftPink, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(comment.content, fontSize = 12.5.sp, color = CharcoalText.copy(0.8f), lineHeight = 17.sp)
                    Text(comment.date, fontSize = 10.sp, color = CharcoalGray, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ==========================================
// 13. COMMUNITY NARRATIVES & 14. SHARE EXPERIENCE
// ==========================================
@Composable
fun CommunityNarrativesScreen(viewModel: KalingAppViewModel) {
    val stories by viewModel.stories.collectAsState()
    val approvedStories = stories.filter { it.isApproved }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                }
                Text("Peer Story Circle 🌸", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
            }

            // Create story CTA
            Button(
                onClick = { viewModel.navigateTo(AppScreen.SHARE_STORY) },
                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent, contentColor = PureWhite),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("add_story_cta")
            ) {
                Text("Share Journey", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PureWhite)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Moderation notice
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GoldAccentLight),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Gavel, "Moderation notice", tint = GoldAccent, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "All narratives are peer-moderated under IBCLC clinical observation guidelines to preserve maternal safety.",
                    fontSize = 11.sp,
                    color = CharcoalText.copy(0.8f),
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(approvedStories) { story ->
                StoryCard(story)
            }
        }
    }
}

@Composable
fun StoryCard(story: Story) {
    var heartCount by remember { mutableStateOf(story.likes) }
    var liked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("story_card_${story.id}"),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, BlushPink.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(BlushPink, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(story.author.take(1), fontWeight = FontWeight.Bold, color = SoftPink)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(story.author, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalText)
                        Text(story.babyAgeGroup, fontSize = 10.sp, color = CharcoalText.copy(0.5f))
                    }
                }

                Box(
                    modifier = Modifier
                        .background(GoldAccentLight, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(story.tag, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = story.content,
                fontSize = 13.sp,
                color = CharcoalText,
                lineHeight = 19.sp
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SoftGray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(story.date, fontSize = 10.sp, color = CharcoalText.copy(0.4f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        if (!liked) {
                            heartCount++
                            liked = true
                        } else {
                            heartCount--
                            liked = false
                        }
                    }) {
                        Icon(
                            imageVector = if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Heart",
                            tint = SoftPink,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        "$heartCount Hearts",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SoftPinkDark
                    )
                }
            }
        }
    }
}

@Composable
fun ShareStoryScreen(viewModel: KalingAppViewModel) {
    var isSubmittedState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateTo(AppScreen.COMMUNITY_NARRATIVES) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Add Community Story", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Share Your Lactation Journey",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink
        )
        Text(
            text = "Your insights could be the exact lifebuoy another first-time mother is searching for today.",
            fontSize = 13.sp,
            color = CharcoalText.copy(0.6f),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Text area
        OutlinedTextField(
            value = viewModel.newStoryContent,
            onValueChange = { viewModel.newStoryContent = it },
            placeholder = { Text("Describe breastfeeding challenges, matching a proper latch routine, return-to-work hacks, or milk donation emotions...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .testTag("new_story_input"),
            shape = RoundedCornerShape(12.dp),
            maxLines = 10
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Options: Story Category tags
        Text("Select Narrative Vibe Tag:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("First-Time Mom Journey", "Working & Breastfeeding", "Supply Triumph").forEach { tag ->
                val isSelected = viewModel.newStoryTag == tag
                if (isSelected) {
                    Button(
                        onClick = { viewModel.newStoryTag = tag },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldAccent,
                            contentColor = PureWhite
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                    }
                } else {
                    OutlinedButton(
                        onClick = { viewModel.newStoryTag = tag },
                        border = BorderStroke(1.dp, GoldAccent),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GoldAccent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isSubmittedState) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                colors = CardDefaults.cardColors(containerColor = GoldAccentLight),
                border = BorderStroke(1.dp, GoldAccent)
            ) {
                Text(
                    text = "✓ Narrative logged for medical review. It will become public in the Community Feed once approved in the Admin Panel.",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalText,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }

        Button(
            onClick = {
                if (viewModel.newStoryContent.isNotBlank()) {
                    viewModel.submitStory()
                    isSubmittedState = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_story_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Publish Story Safely", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

// ==========================================
// 15. MILK BANK REFERRAL PATHWAY MAIN SCREEN
// ==========================================
@Composable
fun MilkBankPathwayScreen(viewModel: KalingAppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Referral & Support Guidance", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Accredited Referral Support",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink
        )

        Text(
            text = "Pasteurized donor human milk is a medically vital prescription safeguard shielding premature and fragile neonates from life-threatening gastrointestinal illnesses.",
            fontSize = 13.5.sp,
            color = CharcoalText.copy(0.7f),
            modifier = Modifier.padding(vertical = 4.dp),
            lineHeight = 19.sp
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SoftGray)

        // Donor Advocacy Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.navigateWithBack(AppScreen.DONOR_PATHWAY) }
                .testTag("donor_path_select"),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = BorderStroke(1.5.dp, SoftPink),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(BlushPink, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Favorite, "Awareness Icon", tint = SoftPink, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Milk Donation Awareness", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
                    Text("Learn about the social responsibility of safe and responsible breastmilk donation.", fontSize = 11.5.sp, color = CharcoalGray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recipient Option Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.navigateWithBack(AppScreen.RECIPIENT_PATHWAY) }
                .testTag("recipient_path_select"),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = BorderStroke(1.5.dp, GoldAccent),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(GoldAccentLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.LocalHospital, "Hospital Icon", tint = GoldAccent, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Request Pasteurized Milk", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 16.sp)
                    Text("Submit prescription indicators for neonatal infants under intensive safety care.", fontSize = 11.5.sp, color = CharcoalGray)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Guide CTA
        Button(
            onClick = { viewModel.navigateWithBack(AppScreen.FACILITY_LIST) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("view_accredited_depots_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Browse Accredited Milk Banks", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

// ==========================================
// 16. DONOR PATHWAY - MULTI-STEP BOOKING & MONITORING
// ==========================================
@Composable
fun DonorPathwayScreen(viewModel: KalingAppViewModel) {
    val bookings by viewModel.bookings.collectAsState()
    val donorBookings = bookings.filter { it.type == "Donor" }

    val donorSteps = listOf(
        Triple("Book Appointment", "Schedule visit at an accredited milk bank facility", Icons.Filled.CalendarMonth),
        Triple("Medical Screening", "Complete blood screening (HIV, Hepatitis, Syphilis)", Icons.Filled.Biotech),
        Triple("Milk Analysis", "Laboratory pasteurization quality and safety testing", Icons.Filled.Science),
        Triple("Donation Results", "Receive clearance status and begin milk drop-offs", Icons.Filled.Verified)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Donor Pathway", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Milk Donation Journey",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink
        )
        Text(
            text = "Follow each step to safely donate your excess breastmilk to infants in need through accredited milk bank facilities.",
            fontSize = 13.sp,
            color = CharcoalText.copy(0.7f),
            lineHeight = 19.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GoldAccentLight.copy(alpha = 0.6f)),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.25f))
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Info, "Info", tint = GoldAccent, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "KalingApp provides referral guidance only. All screenings and donations are handled by accredited facilities.",
                    fontSize = 11.sp,
                    color = CharcoalText.copy(0.8f),
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Donation Process Steps", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
        Spacer(modifier = Modifier.height(12.dp))

        donorSteps.forEachIndexed { index, (title, desc, icon) ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (index == 0) SoftPink else BlushPink,
                                CircleShape
                            )
                            .border(1.5.dp, if (index == 0) SoftPink else SoftPink.copy(0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, title, tint = if (index == 0) PureWhite else SoftPink, modifier = Modifier.size(20.dp))
                    }
                    if (index < donorSteps.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(40.dp)
                                .background(SoftPink.copy(0.3f))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f).padding(bottom = 12.dp)) {
                    Text(
                        text = "Step ${index + 1}: $title",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (index == 0) SoftPink else CharcoalText
                    )
                    Text(desc, fontSize = 12.sp, color = CharcoalGray, lineHeight = 17.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { viewModel.navigateWithBack(AppScreen.DONOR_BOOKING) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("start_donor_booking_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Filled.CalendarMonth, "Book", tint = PureWhite, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start Donation Booking", fontWeight = FontWeight.Bold, color = PureWhite)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { viewModel.navigateWithBack(AppScreen.DONOR_STATUS) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            border = BorderStroke(1.5.dp, SoftPink),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Filled.TrackChanges, "Track", tint = SoftPink, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Track My Booking Status", fontWeight = FontWeight.Bold, color = SoftPink)
        }

        if (donorBookings.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Active Bookings", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
            Spacer(modifier = Modifier.height(8.dp))

            donorBookings.forEach { booking ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            viewModel.selectedBooking = booking
                            viewModel.navigateWithBack(AppScreen.BOOKING_DETAIL)
                        },
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, SoftPink.copy(0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(BlushPink, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${booking.currentStep}", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(booking.facilityName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalText)
                            Text("Step ${booking.currentStep}/${booking.totalSteps} - ${booking.status}", fontSize = 11.sp, color = CharcoalGray)
                        }
                        Icon(Icons.Filled.ArrowForward, "View", tint = SoftPink.copy(0.6f), modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { viewModel.navigateWithBack(AppScreen.FACILITY_LIST) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Browse Accredited Facilities", color = GoldAccent, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// 17. RECIPIENT PATHWAY - MULTI-STEP REQUEST & MONITORING
// ==========================================
@Composable
fun RecipientPathwayScreen(viewModel: KalingAppViewModel) {
    val bookings by viewModel.bookings.collectAsState()
    val recipientBookings = bookings.filter { it.type == "Recipient" }

    val recipientSteps = listOf(
        Triple("Submit Requirements", "Provide medical prescription and infant diagnostic documents", Icons.Filled.Description),
        Triple("Application Review", "Hospital and milk bank verify eligibility and medical need", Icons.Filled.FactCheck),
        Triple("Schedule Pickup", "Book pasteurized milk collection at approved facility", Icons.Filled.CalendarMonth),
        Triple("Receive Results", "Collect approved donor milk supply for your infant", Icons.Filled.Verified)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Recipient Pathway", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Request Pasteurized Donor Milk",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink
        )
        Text(
            text = "Submit your infant's medical prescription to request pasteurized donor human milk from accredited milk bank facilities.",
            fontSize = 13.sp,
            color = CharcoalText.copy(0.7f),
            lineHeight = 19.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        Text("Request Process Overview", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
        Spacer(modifier = Modifier.height(12.dp))

        recipientSteps.forEachIndexed { index, (title, desc, icon) ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (index == 0) GoldAccent else GoldAccentLight,
                                CircleShape
                            )
                            .border(1.5.dp, if (index == 0) GoldAccent else GoldAccent.copy(0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, title, tint = if (index == 0) PureWhite else GoldAccent, modifier = Modifier.size(20.dp))
                    }
                    if (index < recipientSteps.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(40.dp)
                                .background(GoldAccent.copy(0.3f))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f).padding(bottom = 12.dp)) {
                    Text(
                        text = "Step ${index + 1}: $title",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (index == 0) GoldAccent else CharcoalText
                    )
                    Text(desc, fontSize = 12.sp, color = CharcoalGray, lineHeight = 17.sp)
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SoftGray)

        Text(
            text = "Submit Milk Request",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.recipientNeonateName,
            onValueChange = { viewModel.recipientNeonateName = it },
            label = { Text("Infant Full Name") },
            placeholder = { Text("Baby Luka Gonzales") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("recipe_neonate_name"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.recipientClinicInfo,
            onValueChange = { viewModel.recipientClinicInfo = it },
            label = { Text("Hospital / NICU Ward") },
            placeholder = { Text("NICU Room B, Level 3 St. Luke's QC") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("recipe_clinic_info"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Diagnostic Reason:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Column {
            listOf("Prematurity (Low Birth Weight)", "Inborn Errors of Metabolism", "Severe Postpartum Maternal Illness").forEach { reason ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.recipientSelectedReason == reason,
                        onClick = { viewModel.recipientSelectedReason = reason },
                        colors = RadioButtonDefaults.colors(selectedColor = GoldAccent)
                    )
                    Text(reason, fontSize = 12.5.sp, color = CharcoalText)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(GoldAccentLight, RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Checkbox(
                checked = viewModel.recipientPrescriptionProof,
                onCheckedChange = { viewModel.recipientPrescriptionProof = it }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                "Medical prescription or neonatal priority reference attached (Required by WHO guidelines)",
                fontSize = 11.sp,
                color = CharcoalText,
                lineHeight = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (viewModel.recipientFormSubmitted) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = GoldAccentLight),
                border = BorderStroke(1.dp, GoldAccent)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.CheckCircle, "Success", tint = GoldAccent, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Request Submitted Successfully", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = GoldAccent)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Hospital NICU coordination has been notified. Track your request status below.",
                        fontSize = 12.sp,
                        color = CharcoalText
                    )
                }
            }
        }

        Button(
            onClick = { viewModel.submitRecipientForm() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_recipient_req"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Verify & Submit Prescription", fontWeight = FontWeight.Bold, color = PureWhite)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { viewModel.navigateWithBack(AppScreen.RECIPIENT_STATUS) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            border = BorderStroke(1.5.dp, GoldAccent),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Filled.TrackChanges, "Track", tint = GoldAccent, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Track Request Status", fontWeight = FontWeight.Bold, color = GoldAccent)
        }

        if (recipientBookings.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("My Requests", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
            Spacer(modifier = Modifier.height(8.dp))

            recipientBookings.forEach { booking ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            viewModel.selectedBooking = booking
                            viewModel.navigateWithBack(AppScreen.BOOKING_DETAIL)
                        },
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, GoldAccent.copy(0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(GoldAccentLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${booking.currentStep}", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(booking.facilityName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalText)
                            Text("Step ${booking.currentStep}/${booking.totalSteps} - ${booking.status}", fontSize = 11.sp, color = CharcoalGray)
                        }
                        Icon(Icons.Filled.ArrowForward, "View", tint = GoldAccent.copy(0.6f), modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

// ==========================================
// 18. FACILITY LISTINGS & 19. FACILITY DETAILS
// ==========================================
@Composable
fun FacilityListScreen(viewModel: KalingAppViewModel) {
    val facilities by viewModel.facilities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Hospital Milk Depots", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Accredited Human Milk Banks",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalText
        )
        Text(
            text = "Vetted donor collection partners under the Department of Health (DOH).",
            fontSize = 12.sp,
            color = CharcoalText.copy(0.5f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = BlushPink.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.LocationOn, "Location", tint = SoftPink, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Smart Allocation: Ranked by proximity and service availability",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftPinkDark
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(facilities.sortedBy { it.distance }) { facility ->
                val rankIndex = facilities.sortedBy { it.distance }.indexOf(facility)
                val servicesList = when {
                    facility.type.contains("Accredited") -> listOf("Donor Screening", "Pasteurization", "Milk Storage", "Counseling")
                    facility.type.contains("Government") -> listOf("Donor Screening", "Milk Storage", "Free Collection Kits")
                    else -> listOf("Milk Collection", "Peer Counseling", "Home Pickup")
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectedFacility = facility
                            viewModel.navigateWithBack(AppScreen.FACILITY_DETAIL)
                        }
                        .testTag("facility_card_${facility.id}"),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(
                        1.dp,
                        if (rankIndex == 0) SoftPink else BlushPink.copy(alpha = 0.5f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (rankIndex == 0) {
                                    Box(
                                        modifier = Modifier
                                            .background(SoftPink, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("BEST MATCH", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Box(
                                    modifier = Modifier
                                        .background(BlushPink, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(facility.type, fontSize = 9.5.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.LocationOn, "Distance", tint = GoldAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(facility.distance, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(facility.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
                        Text(facility.address, fontSize = 12.sp, color = CharcoalText.copy(0.6f), maxLines = 1)

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            servicesList.take(3).forEach { service ->
                                Box(
                                    modifier = Modifier
                                        .background(GoldAccentLight, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(service, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                                }
                            }
                            if (servicesList.size > 3) {
                                Box(
                                    modifier = Modifier
                                        .background(BlushPink, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("+${servicesList.size - 3}", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Phone, "Phone", tint = SoftPink, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(facility.contact, fontSize = 12.sp, color = CharcoalText.copy(0.8f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FacilityDetailScreen(viewModel: KalingAppViewModel) {
    val facility = viewModel.selectedFacility ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }

            Box(
                modifier = Modifier
                    .background(GoldAccentLight, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("DOH Certified ✓", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(facility.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
        Text(facility.type, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = SoftPink)

        Spacer(modifier = Modifier.height(16.dp))

        // Emulate static map grid box beautifully using dynamic canvas arches
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SoftGray)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // draw geometric maps mesh lines
                    drawLine(color = PureWhite, start = Offset(0f, 100f), end = Offset(size.width, 300f), strokeWidth = 3f)
                    drawLine(color = PureWhite, start = Offset(150f, 0f), end = Offset(150f, size.height), strokeWidth = 3f)
                    drawCircle(color = SoftPink, center = Offset(size.width/2, size.height/2), radius = 10.dp.toPx())
                }
                Box(
                    modifier = Modifier
                        .background(PureWhite, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("📍 ${facility.distance} Match", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("About Facility Depot:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Text(facility.description, fontSize = 13.sp, color = CharcoalGray, lineHeight = 18.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Schedule, "Hours", tint = GoldAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(facility.operatingHours, fontSize = 12.5.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Donors and Recipient Requirements
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BlushPink),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Donor Registration Checklist", fontWeight = FontWeight.Bold, color = SoftPinkDark, fontSize = 13.sp)
                Text(facility.donorRequirements, fontSize = 12.sp, color = CharcoalText, lineHeight = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GoldAccentLight),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Recipient Medical Policy", fontWeight = FontWeight.Bold, color = GoldAccent, fontSize = 13.sp)
                Text(facility.recipientRequirements, fontSize = 12.sp, color = CharcoalText, lineHeight = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.navigateTo(AppScreen.REFERRAL_INSTRUCTIONS) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("how_to_submit_referral_cta"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Read Referral Action Roadmap", fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

// ==========================================
// 20. REFERRAL INSTRUCTIONS
// ==========================================
@Composable
fun ReferralInstructionsScreen(viewModel: KalingAppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Referral Steps Guide", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Clinical Action Roadmap",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Multi-Step Layout
        StepItem(
            num = "1",
            title = "Validate Eligibility Checklists",
            desc = "Ensure you conform to strict milk bank chemistry standards by filling out our Donor Pathway screener."
        )
        StepItem(
            num = "2",
            title = "Obtain Hospital Doctor Prescriptions",
            desc = "If requesting safety donor milk, pediatric clinical prescriptions are mapped securely in our network database."
        )
        StepItem(
            num = "3",
            title = "Arrange Cold-Chain Transport Drops",
            desc = "Milk banks issue free clean glass bottles and safety coolers. Drop expressed bottles within 48 hours."
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("close_instructions_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(14.dp)
         ) {
            Text("Return to Dashboard", fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

@Composable
fun StepItem(num: String, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(BlushPink, CircleShape)
                .border(1.dp, SoftPink, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(num, fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = CharcoalText)
            Text(desc, fontSize = 12.5.sp, color = CharcoalGray, lineHeight = 17.sp)
        }
    }
}

// ==========================================
// 21. NOTIFICATIONS PAGE
// ==========================================
@Composable
fun NotificationsScreen(viewModel: KalingAppViewModel) {
    val notifications by viewModel.notifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                }
                Text("Notifications (${notifications.filter { !it.isRead }.size} Unread)", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
            }

            TextButton(onClick = { viewModel.readAllNotifications() }) {
                Text("Mark All Read", color = GoldAccent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (notifications.isEmpty()) {
            EmptyStatePlaceholder(
                title = "Your Notification Inbox is Clear",
                description = "Postpartum reminders, diagnostic results, and milk bank pathways will appear here once active.",
                icon = Icons.Default.NotificationsNone,
                actionLabel = "Back to Dashboard",
                onAction = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) },
                modifier = Modifier.padding(top = 32.dp)
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(notifications) { notif ->
                    val color = if (notif.isRead) PureWhite else BlushPink.copy(0.5f)
                    val border = if (notif.isRead) BorderStroke(1.dp, SoftGray) else BorderStroke(1.dp, SoftPink)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.readNotification(notif.id)
                                viewModel.selectedNotification = notif
                                viewModel.navigateWithBack(AppScreen.NOTIFICATION_DETAILS)
                            }
                            .testTag("notification_card_${notif.id}"),
                        colors = CardDefaults.cardColors(containerColor = color),
                        border = border,
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    notif.category.uppercase(),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (notif.category == "Articles") SoftPink else GoldAccent
                                )
                                Text(notif.time, fontSize = 9.sp, color = CharcoalText.copy(0.4f))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(notif.title, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = CharcoalText)
                            Text(notif.description, fontSize = 12.sp, color = CharcoalGray, lineHeight = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 22. USER PROFILE
// ==========================================
@Composable
fun UserProfileScreen(viewModel: KalingAppViewModel) {
    val profile = viewModel.currentUserProfile
    val articles by viewModel.articles.collectAsState()
    val savedIds by viewModel.savedArticleIds.collectAsState()

    val savedArticlesList = articles.filter { savedIds.contains(it.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .kalingDecorativeBackground()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                KalingAppLogo(modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("User Profile Portal", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
            }

            IconButton(onClick = { viewModel.navigateTo(AppScreen.SETTINGS_PAGE) }) {
                Icon(Icons.Filled.Settings, "Settings", tint = GoldAccent)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = BorderStroke(1.dp, SoftPink),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(BlushPink, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(profile.momName.take(1), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(profile.momName, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = CharcoalText)
                Text(profile.breastfeedingStatus, fontSize = 12.sp, color = SoftPinkDark, fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.startProfileEditing()
                        viewModel.navigateWithBack(AppScreen.EDIT_PROFILE)
                    },
                    modifier = Modifier.testTag("edit_profile_button"),
                    border = BorderStroke(1.dp, SoftPink),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Filled.Edit, "Edit Profile Icon", tint = SoftPink, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Edit Profile Clinical Data", color = SoftPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SoftGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Baby age", fontSize = 10.sp, color = CharcoalText.copy(0.5f))
                        Text("${profile.babyAgeWeeks} weeks", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Baby Name", fontSize = 10.sp, color = CharcoalText.copy(0.5f))
                        Text(profile.babyName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Donor Level", fontSize = 10.sp, color = CharcoalText.copy(0.5f))
                        Text("Level 1 🌸", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = GoldAccent)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Saved Reading guides header with full-screen link
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("My Bookmarked Guides (${savedArticlesList.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = CharcoalText)
            TextButton(
                onClick = { viewModel.navigateWithBack(AppScreen.SAVED_ARTICLES) },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("See All 📚", color = SoftPinkDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (savedArticlesList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No guides saved yet. Tap the bookmark icon on any medical article to keep it offline.",
                    fontSize = 12.sp,
                    color = CharcoalText.copy(0.5f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            savedArticlesList.forEach { article ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable {
                            viewModel.selectedArticle = article
                            viewModel.navigateWithBack(AppScreen.ARTICLE_DETAIL)
                        },
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, SoftGray)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(article.category.uppercase(), fontSize = 8.sp, fontWeight = FontWeight.Bold, color = SoftPink)
                            Text(article.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Icon(Icons.Filled.ArrowForward, "Read", tint = GoldAccent, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// ==========================================
// 23. SETTINGS PAGE
// ==========================================
@Composable
fun SettingsToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, SoftGray)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalText)
                Text(subtitle, fontSize = 11.sp, color = CharcoalGray, lineHeight = 14.sp)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = SoftPink, checkedTrackColor = BlushPink)
            )
        }
    }
}

@Composable
fun SettingsPageScreen(viewModel: KalingAppViewModel) {
    var notifAllow by remember { mutableStateOf(true) }
    var privacyAllow by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateTo(AppScreen.USER_PROFILE) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("App Settings", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Preferences", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = CharcoalText)

        Spacer(modifier = Modifier.height(12.dp))

        // Preference checks
        SettingsToggle(
            checked = notifAllow,
            onCheckedChange = { notifAllow = it },
            title = "Hydration & Pump Reminders",
            subtitle = "Push gentle alarms every 3 hours to balance physiological supply."
        )

        Spacer(modifier = Modifier.height(10.dp))

        SettingsToggle(
            checked = privacyAllow,
            onCheckedChange = { privacyAllow = it },
            title = "Biometric Lock Protection",
            subtitle = "Require Mother-Facial ID secure lock to access clinical details."
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Account Control", fontSize = 13.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        // Gold gold division line and logout
        OutlinedButton(
            onClick = {
                viewModel.isLoggedIn = false
                viewModel.navigateTo(AppScreen.WELCOME)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = SoftPink),
            border = BorderStroke(1.dp, SoftPink)
        ) {
            Text("Secure Clear Session / Log Out", fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// ADMIN DASHBOARD SCREEN
// ==========================================
@Composable
fun DesktopAdminDashboard(viewModel: KalingAppViewModel) {
    val stories by viewModel.stories.collectAsState()
    val pendingStories = stories.filter { !it.isApproved }

    var selectedTab by remember { mutableStateOf("Moderation") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
    ) {
        // Desktop Header Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    KalingAppLogo(modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("KalingApp Admin Portal", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = CharcoalText)
                        Text("Health Services Administrative Hub", fontSize = 11.sp, color = CharcoalText.copy(0.5f))
                    }
                }

                // Return to user app
                Button(
                    onClick = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) },
                    colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
                    modifier = Modifier.testTag("exit_admin_btn")
                ) {
                    Text("Return to Mobile Client", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                }
            }
        }

        // Sidebar Navigation on Left, content on right
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight()
                    .background(PureWhite)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("Moderation", "Statistics").forEach { tab ->
                    val isSel = selectedTab == tab
                    TextButton(
                        onClick = { selectedTab = tab },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (isSel) SoftPink else CharcoalText
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isSel) BlushPink else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(tab, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            // Main display panel
            if (selectedTab == "Moderation") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text("Narrative Content Moderation Queue", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
                    Text("Verify peer breastfeeding submissions conform to maternal health security.", fontSize = 12.sp, color = CharcoalText.copy(0.5f))

                    Spacer(modifier = Modifier.height(16.dp))

                    if (pendingStories.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Pending narrative queue completely clear. Perfect job, admin! 🛡️", fontSize = 13.sp, color = CharcoalText.copy(0.5f))
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(pendingStories) { story ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                                    border = BorderStroke(1.dp, GoldAccent)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text("Author: ${story.author}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                                Text("Baby Age Group: ${story.babyAgeGroup}", fontSize = 11.sp, color = CharcoalText.copy(0.5f))
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .background(GoldAccentLight, RoundedCornerShape(6.dp))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text("PENDING", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(10.dp))

                                        Text(story.content, fontSize = 12.5.sp, color = CharcoalText, lineHeight = 17.sp)

                                        Spacer(modifier = Modifier.height(14.dp))

                                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                            Button(
                                                onClick = { viewModel.approveStory(story.id) },
                                                colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("Approve Story ✓", fontSize = 11.sp, color = PureWhite)
                                            }

                                            OutlinedButton(
                                                onClick = { viewModel.rejectStory(story.id) },
                                                border = BorderStroke(1.dp, Color.Red),
                                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("Reject", fontSize = 11.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Statistics dashboard view
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Clinical System Metrics", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = CharcoalText)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Total Donated Milk",
                            score = "452 oz",
                            alert = "Metropolitan goal 80%",
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                        MetricCard(
                            title = "Registered Mothers",
                            score = "128 Moms",
                            alert = "21 active expected",
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "AI Consult Hours",
                            score = "14.5 Hrs",
                            alert = "78 successful solutions",
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                        MetricCard(
                            title = "Accredited Depots",
                            score = "3 Banks",
                            alert = "DOH Verified status",
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricCard(title: String, score: String, alert: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BlushPink.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalText.copy(0.5f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = score,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SoftPink,
                textAlign = TextAlign.Center,
                maxLines = 1,
                softWrap = false
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = alert,
                fontSize = 9.sp,
                color = GoldAccent,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ==========================================
// DONOR BOOKING SCREEN
// ==========================================
@Composable
fun DonorBookingScreen(viewModel: KalingAppViewModel) {
    val facilities by viewModel.facilities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Book Donation Appointment", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Schedule Your Visit",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPink
        )
        Text(
            text = "Select an accredited milk bank facility and your preferred schedule for the initial screening visit.",
            fontSize = 13.sp,
            color = CharcoalText.copy(0.7f),
            lineHeight = 19.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        Text("Select Facility", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
        Spacer(modifier = Modifier.height(8.dp))

        facilities.forEach { facility ->
            val isSelected = viewModel.donorBookingFacility == facility.name
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { viewModel.donorBookingFacility = facility.name },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) BlushPink else PureWhite
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    if (isSelected) 2.dp else 1.dp,
                    if (isSelected) SoftPink else SoftGray
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { viewModel.donorBookingFacility = facility.name },
                        colors = RadioButtonDefaults.colors(selectedColor = SoftPink)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(facility.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalText)
                        Text(facility.distance, fontSize = 11.sp, color = GoldAccent, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.donorBookingDate,
            onValueChange = { viewModel.donorBookingDate = it },
            label = { Text("Preferred Date") },
            placeholder = { Text("e.g., July 5, 2026") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink),
            leadingIcon = { Icon(Icons.Filled.CalendarMonth, "Date", tint = SoftPink) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.donorBookingTime,
            onValueChange = { viewModel.donorBookingTime = it },
            label = { Text("Preferred Time") },
            placeholder = { Text("e.g., 10:00 AM") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink),
            leadingIcon = { Icon(Icons.Filled.Schedule, "Time", tint = SoftPink) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (viewModel.donorBookingSubmitted) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = GoldAccentLight),
                border = BorderStroke(1.dp, GoldAccent)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.CheckCircle, "Booked", tint = GoldAccent, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Booking Confirmed", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = GoldAccent)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your appointment at ${viewModel.donorBookingFacility} has been submitted. You will receive a confirmation notification once the facility responds.",
                        fontSize = 12.sp,
                        color = CharcoalText,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        Button(
            onClick = { viewModel.submitDonorBooking() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("confirm_donor_booking_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(14.dp),
            enabled = viewModel.donorBookingFacility.isNotBlank()
        ) {
            Icon(Icons.Filled.CheckCircle, "Confirm", tint = PureWhite, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Confirm Booking", fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

// ==========================================
// DONOR STATUS TRACKING SCREEN
// ==========================================
@Composable
fun DonorStatusScreen(viewModel: KalingAppViewModel) {
    val bookings by viewModel.bookings.collectAsState()
    val donorBookings = bookings.filter { it.type == "Donor" }

    val stepLabels = listOf("Eligibility", "Booking", "Screening", "Analysis", "Results")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Donor Booking Status", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (donorBookings.isEmpty()) {
            EmptyStatePlaceholder(
                title = "No Active Donor Bookings",
                description = "Start your milk donation journey by booking an appointment at an accredited facility through the Donor Pathway screen.",
                icon = Icons.Default.CalendarMonth,
                actionLabel = "Start Booking",
                onAction = { viewModel.navigateTo(AppScreen.DONOR_PATHWAY) },
                modifier = Modifier.padding(top = 32.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(donorBookings) { booking ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectedBooking = booking
                                viewModel.navigateWithBack(AppScreen.BOOKING_DETAIL)
                            },
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.3f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(booking.facilityName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = CharcoalText)
                                Box(
                                    modifier = Modifier
                                        .background(
                                            when (booking.status) {
                                                "Approved" -> SoftPink
                                                "Rejected" -> Color(0xFFE53935)
                                                else -> GoldAccent
                                            },
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(booking.status.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                                }
                            }

                            Text(booking.date, fontSize = 11.sp, color = CharcoalGray, modifier = Modifier.padding(top = 2.dp))

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                stepLabels.forEachIndexed { index, label ->
                                    val stepNum = index + 1
                                    val isCompleted = stepNum < booking.currentStep
                                    val isCurrent = stepNum == booking.currentStep
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(
                                                    when {
                                                        isCompleted -> SoftPink
                                                        isCurrent -> GoldAccent
                                                        else -> SoftGray
                                                    },
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (isCompleted) {
                                                Icon(Icons.Filled.Check, "Done", tint = PureWhite, modifier = Modifier.size(14.dp))
                                            } else {
                                                Text("$stepNum", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isCurrent) PureWhite else CharcoalGray)
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            label,
                                            fontSize = 8.sp,
                                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isCurrent) GoldAccent else CharcoalGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                            if (booking.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.Info, "Note", tint = GoldAccent, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(booking.notes, fontSize = 11.sp, color = CharcoalGray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// RECIPIENT STATUS TRACKING SCREEN
// ==========================================
@Composable
fun RecipientStatusScreen(viewModel: KalingAppViewModel) {
    val bookings by viewModel.bookings.collectAsState()
    val recipientBookings = bookings.filter { it.type == "Recipient" }

    val stepLabels = listOf("Requirements", "Review", "Scheduling", "Results")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Recipient Request Status", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (recipientBookings.isEmpty()) {
            EmptyStatePlaceholder(
                title = "No Active Requests",
                description = "Submit a pasteurized milk request through the Recipient Pathway to see your application status here.",
                icon = Icons.Default.FactCheck,
                actionLabel = "Submit Request",
                onAction = { viewModel.navigateTo(AppScreen.RECIPIENT_PATHWAY) },
                modifier = Modifier.padding(top = 32.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(recipientBookings) { booking ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectedBooking = booking
                                viewModel.navigateWithBack(AppScreen.BOOKING_DETAIL)
                            },
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(booking.facilityName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = CharcoalText)
                                Box(
                                    modifier = Modifier
                                        .background(
                                            when (booking.status) {
                                                "Approved" -> SoftPink
                                                "Rejected" -> Color(0xFFE53935)
                                                else -> GoldAccent
                                            },
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(booking.status.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                                }
                            }

                            Text(booking.date, fontSize = 11.sp, color = CharcoalGray, modifier = Modifier.padding(top = 2.dp))

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                stepLabels.forEachIndexed { index, label ->
                                    val stepNum = index + 1
                                    val isCompleted = stepNum < booking.currentStep
                                    val isCurrent = stepNum == booking.currentStep
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(
                                                    when {
                                                        isCompleted -> GoldAccent
                                                        isCurrent -> SoftPink
                                                        else -> SoftGray
                                                    },
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (isCompleted) {
                                                Icon(Icons.Filled.Check, "Done", tint = PureWhite, modifier = Modifier.size(14.dp))
                                            } else {
                                                Text("$stepNum", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (isCurrent) PureWhite else CharcoalGray)
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            label,
                                            fontSize = 8.sp,
                                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isCurrent) SoftPink else CharcoalGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                            if (booking.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.Info, "Note", tint = GoldAccent, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(booking.notes, fontSize = 11.sp, color = CharcoalGray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// BOOKING DETAIL SCREEN
// ==========================================
@Composable
fun BookingDetailScreen(viewModel: KalingAppViewModel) {
    val booking = viewModel.selectedBooking ?: return

    val isDonor = booking.type == "Donor"
    val accentColor = if (isDonor) SoftPink else GoldAccent
    val accentLight = if (isDonor) BlushPink else GoldAccentLight
    val stepLabels = if (isDonor) {
        listOf("Eligibility Check", "Appointment Booking", "Medical Screening", "Milk Analysis", "Donation Results")
    } else {
        listOf("Submit Requirements", "Application Review", "Schedule Pickup", "Receive Results")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                }
                Text("Booking Details", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
            }
            Box(
                modifier = Modifier
                    .background(accentLight, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(booking.type.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = accentColor)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, accentColor.copy(0.2f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(booking.facilityName, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = CharcoalText)
                Text(booking.date, fontSize = 12.sp, color = CharcoalGray, modifier = Modifier.padding(top = 2.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Current Status", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
                    Box(
                        modifier = Modifier
                            .background(
                                when (booking.status) {
                                    "Approved" -> SoftPink
                                    "Rejected" -> Color(0xFFE53935)
                                    else -> GoldAccent
                                },
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(booking.status.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SoftGray)

                Text("Progress Timeline", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CharcoalText)
                Spacer(modifier = Modifier.height(12.dp))

                stepLabels.forEachIndexed { index, label ->
                    val stepNum = index + 1
                    val isCompleted = stepNum < booking.currentStep
                    val isCurrent = stepNum == booking.currentStep

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        when {
                                            isCompleted -> accentColor
                                            isCurrent -> accentColor.copy(alpha = 0.7f)
                                            else -> SoftGray
                                        },
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isCompleted) {
                                    Icon(Icons.Filled.Check, "Completed", tint = PureWhite, modifier = Modifier.size(18.dp))
                                } else {
                                    Text("$stepNum", fontWeight = FontWeight.Bold, color = if (isCurrent) PureWhite else CharcoalGray, fontSize = 13.sp)
                                }
                            }
                            if (index < stepLabels.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(32.dp)
                                        .background(if (isCompleted) accentColor.copy(0.5f) else SoftGray)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f).padding(bottom = 8.dp)) {
                            Text(
                                label,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp,
                                color = when {
                                    isCurrent -> accentColor
                                    isCompleted -> CharcoalText
                                    else -> CharcoalGray
                                }
                            )
                            if (isCurrent) {
                                Text("In Progress", fontSize = 11.sp, color = accentColor, fontWeight = FontWeight.Medium)
                            } else if (isCompleted) {
                                Text("Completed", fontSize = 11.sp, color = CharcoalGray)
                            } else {
                                Text("Upcoming", fontSize = 11.sp, color = CharcoalGray.copy(0.6f))
                            }
                        }
                    }
                }

                if (booking.notes.isNotBlank()) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SoftGray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.StickyNote2, "Notes", tint = accentColor, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(booking.notes, fontSize = 12.sp, color = CharcoalText, lineHeight = 17.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = accentLight.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Info, "Info", tint = accentColor, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "You will receive notifications as your booking progresses through each stage. Ensure your contact details are up to date.",
                    fontSize = 11.sp,
                    color = CharcoalText.copy(0.8f),
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Back to Overview", fontWeight = FontWeight.Bold, color = PureWhite)
        }
    }
}

// ==========================================
// CENTRALIZED BOTTOM NAVIGATION BAR
// ==========================================
@Composable
fun KalingBottomNavigation(viewModel: KalingAppViewModel, currentScreen: AppScreen) {
    NavigationBar(
        containerColor = PureWhite,
        tonalElevation = 6.dp,
        windowInsets = WindowInsets.navigationBars // Notch/Gesture pill guidelines complied
    ) {
        // Tab 1: Home Dashboard
        NavigationBarItem(
            selected = currentScreen == AppScreen.HOME_DASHBOARD,
            onClick = { viewModel.navigateTo(AppScreen.HOME_DASHBOARD) },
            label = { Text("Home", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.HOME_DASHBOARD) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftPink,
                selectedTextColor = SoftPink,
                indicatorColor = BlushPink
            )
        )

        // Tab 2: AI Assistant
        NavigationBarItem(
            selected = currentScreen == AppScreen.AI_CHAT,
            onClick = { viewModel.navigateTo(AppScreen.AI_CHAT) },
            label = { Text("Kali AI", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.AI_CHAT) Icons.Filled.SmartToy else Icons.Outlined.SmartToy,
                    contentDescription = "AI Assistant"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftPink,
                selectedTextColor = SoftPink,
                indicatorColor = BlushPink
            )
        )

        // Tab 3: Knowledge Library
        NavigationBarItem(
            selected = currentScreen == AppScreen.KNOWLEDGE_HUB || currentScreen == AppScreen.ARTICLES_LIST,
            onClick = { viewModel.navigateTo(AppScreen.KNOWLEDGE_HUB) },
            label = { Text("Knowledge", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.KNOWLEDGE_HUB || currentScreen == AppScreen.ARTICLES_LIST) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                    contentDescription = "Knowledge"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftPink,
                selectedTextColor = SoftPink,
                indicatorColor = BlushPink
            )
        )

        // Tab 4: Community Stories
        NavigationBarItem(
            selected = currentScreen == AppScreen.COMMUNITY_NARRATIVES,
            onClick = { viewModel.navigateTo(AppScreen.COMMUNITY_NARRATIVES) },
            label = { Text("Community", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            icon = {
                Icon(
                     imageVector = if (currentScreen == AppScreen.COMMUNITY_NARRATIVES) Icons.Filled.Forum else Icons.Outlined.Forum,
                     contentDescription = "Community"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftPink,
                selectedTextColor = SoftPink,
                indicatorColor = BlushPink
            )
        )

        // Tab 5: Profile & Care Track
        NavigationBarItem(
            selected = currentScreen == AppScreen.USER_PROFILE,
            onClick = { viewModel.navigateTo(AppScreen.USER_PROFILE) },
            label = { Text("Profile", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.USER_PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Profile"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftPink,
                selectedTextColor = SoftPink,
                indicatorColor = BlushPink
            )
        )
    }
}

/**
 * Clean support helper for scrollable rows.
 */
@Composable
fun ScrollableRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

// ==========================================
// 24. REUSABLE POLISHED EMPTY, ERROR & LOADING STATES
// ==========================================
@Composable
fun EmptyStatePlaceholder(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, BlushPink.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(BlushPink.copy(alpha = 0.6f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SoftPink,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = CharcoalText.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            if (actionLabel != null && onAction != null) {
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onAction,
                    colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("empty_state_action_btn")
                ) {
                    Text(actionLabel, color = PureWhite, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun ErrorStateBanner(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF2F2)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFF8B4B4))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error icon",
                    tint = Color(0xFFE02424),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = message,
                    fontSize = 13.sp,
                    color = Color(0xFF9B1C1C),
                    fontWeight = FontWeight.Medium
                )
            }
            TextButton(
                onClick = onRetry,
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFE02424))
            ) {
                Text("Retry", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LoadingPlaceholder(
    message: String = "Consulting Pediatric Clinical Database...",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PureWhite.copy(alpha = 0.92f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var alphaScale by remember { mutableStateOf(0.4f) }
            LaunchedEffect(Unit) {
                while(true) {
                    alphaScale = 1.0f
                    delay(800)
                    alphaScale = 0.4f
                    delay(800)
                }
            }
            
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .alpha(alphaScale),
                contentAlignment = Alignment.Center
            ) {
                KalingAppLogo(modifier = Modifier.size(90.dp))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            CircularProgressIndicator(
                color = SoftPink,
                strokeWidth = 3.dp,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = message,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = SoftPinkDark,
                textAlign = TextAlign.Center
            )
        }
    }
}


// ==========================================
// 25. EDIT PROFILE CLINICAL FORM
// ==========================================
@Composable
fun EditProfileScreen(viewModel: KalingAppViewModel) {
    var isSaving by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var ageInputError by remember { mutableStateOf(false) }

    // Mock switches to showcase loading/error screens on update
    var simulateLoadingScreen by remember { mutableStateOf(false) }
    var simulateErrorState by remember { mutableStateOf(false) }

    LaunchedEffect(isSaving) {
        if (isSaving) {
            delay(1500)
            isSaving = false
            if (simulateErrorState) {
                showError = true
            } else {
                viewModel.saveProfileEditing()
                viewModel.navigateBack()
            }
        }
    }

    if (isSaving && simulateLoadingScreen) {
        LoadingPlaceholder(message = "Saving clinical data profile...")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
                }
                Text("Edit Profile Clinical Data", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showError) {
                ErrorStateBanner(
                    message = "Postpartum Registry Offline. Failed to update hospital folder.",
                    onRetry = {
                        showError = false
                        isSaving = true
                    }
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Clinical Demographics", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SoftPinkDark)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = viewModel.editMomName,
                        onValueChange = { viewModel.editMomName = it },
                        label = { Text("Mother Name") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_mom_name"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = viewModel.editBabyName,
                        onValueChange = { viewModel.editBabyName = it },
                        label = { Text("Baby Name") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_baby_name"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = viewModel.editBabyAgeWeeks,
                        onValueChange = { 
                            viewModel.editBabyAgeWeeks = it
                            ageInputError = it.toIntOrNull() == null
                        },
                        label = { Text("Baby Age (Weeks)") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_baby_age"),
                        shape = RoundedCornerShape(12.dp),
                        isError = ageInputError,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink)
                    )
                    if (ageInputError) {
                        Text("Please enter a valid age in weeks (numeric)", color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = viewModel.editPediatricClinic,
                        onValueChange = { viewModel.editPediatricClinic = it },
                        label = { Text("Primary Pediatric Clinic") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_pediatric_clinic"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SoftPink)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // State/Simulation Toggles to demonstrate Loading & Error screens
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BlushPink.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("States Demonstrator Controls", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SoftPinkDark)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Simulate loading screen during save", fontSize = 11.sp, color = CharcoalText)
                        Switch(
                            checked = simulateLoadingScreen,
                            onCheckedChange = { simulateLoadingScreen = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = SoftPink)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Simulate error banner state during save", fontSize = 11.sp, color = CharcoalText)
                        Switch(
                            checked = simulateErrorState,
                            onCheckedChange = { simulateErrorState = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = SoftPink)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { 
                    if (!ageInputError) {
                        isSaving = true 
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("save_profile_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Confirm & Save Changes 🌸", color = PureWhite, fontWeight = FontWeight.Bold)
            }
        }
    }
}


// ==========================================
// 26. PREMIUM SAVED ARTICLES SCREEN
// ==========================================
@Composable
fun SavedArticlesScreen(viewModel: KalingAppViewModel) {
    val articles by viewModel.articles.collectAsState()
    val savedIds by viewModel.savedArticleIds.collectAsState()

    val savedArticlesList = articles.filter { savedIds.contains(it.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Saved Clinical Guidelines", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (savedArticlesList.isEmpty()) {
            EmptyStatePlaceholder(
                title = "Your Saved Repository is Empty",
                description = "Bookmark lactation instructions, milk banking pathways, and clinical checklists inside the Knowledge Hub for immediate visual reference offline, even during late night feedings.",
                icon = Icons.Default.BookmarkBorder,
                actionLabel = "Explore Medical Guides",
                onAction = { viewModel.navigateTo(AppScreen.KNOWLEDGE_HUB) },
                modifier = Modifier.padding(top = 32.dp)
            )
        } else {
            Text(
                text = "${savedArticlesList.size} Bookmarked medical guidelines saved manually",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = SoftPinkDark,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(savedArticlesList) { article ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectedArticle = article
                                viewModel.navigateWithBack(AppScreen.ARTICLE_DETAIL)
                            }
                            .testTag("saved_article_card_${article.id}"),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.15f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    article.category,
                                    fontSize = 10.sp,
                                    color = SoftPink,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(article.readTime, fontSize = 10.sp, color = CharcoalGray)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = article.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalText
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = article.teaser,
                                fontSize = 12.sp,
                                color = CharcoalGray,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Verified Pediatric Guide",
                                    fontSize = 11.sp,
                                    color = GoldAccent,
                                    fontWeight = FontWeight.SemiBold
                                )
                                TextButton(
                                    onClick = { viewModel.toggleSaveArticle(article.id) },
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("Unsave", color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


// ==========================================
// ==========================================
// 28. NOTIFICATION DETAILS VIEW SCREEN
// ==========================================
@Composable
fun NotificationDetailsScreen(viewModel: KalingAppViewModel) {
    val selectedNotif = viewModel.selectedNotification

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = SoftPink)
            }
            Text("Notification Dispatch Folder", fontWeight = FontWeight.Bold, color = SoftPink, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedNotif == null) {
            EmptyStatePlaceholder(
                title = "No Notification Selected",
                description = "Choose a communication alert card from your Alerts list to render its clinical summary detail page here.",
                icon = Icons.Default.Warning,
                actionLabel = "Back to Notification Alerts",
                onAction = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) }
            )
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, SoftPink.copy(alpha = 0.2f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(BlushPink, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                selectedNotif.category.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SoftPinkDark
                            )
                        }
                        Text(selectedNotif.time, fontSize = 11.sp, color = CharcoalText.copy(0.5f))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        selectedNotif.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = CharcoalText,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        selectedNotif.description,
                        fontSize = 14.sp,
                        color = CharcoalGray,
                        lineHeight = 20.sp
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SoftGray)

                    Text(
                        "Referral Preparation Checklist",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = SoftPinkDark
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Helpful checkboxes for referral guidance
                    listOf(
                        "Keep your maternal identification card ready.",
                        "Gather milk temperature logs if applicable.",
                        "Locate the nearest accredited facility.",
                        "Review current milk bank requirements."
                    ).forEach { step ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Check step",
                                tint = SoftPink,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(step, fontSize = 12.sp, color = CharcoalText.copy(0.8f))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = GoldAccentLight.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "🩺 Support Alert: All support guidelines are informational and follow pediatric standards.",
                                fontSize = 11.sp,
                                color = GoldAccent,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.navigateBack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SoftPink, contentColor = PureWhite),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Dismiss & Go Back", color = PureWhite, fontWeight = FontWeight.Bold)
            }
        }
    }
}
