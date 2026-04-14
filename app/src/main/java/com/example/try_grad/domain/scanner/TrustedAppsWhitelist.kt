package com.example.try_grad.domain.scanner

/**
 * Whitelist of trusted application publishers and well-known apps.
 *
 * RATIONALE:
 * ──────────────────────────────────────────────────────────────────────────────
 * Famous apps distributed through the Google Play Store (e.g. Google Maps,
 * WhatsApp, Instagram) legitimately need dangerous permissions to function
 * (location, camera, contacts, microphone). These permissions do NOT indicate
 * malicious intent when the app comes from a known, verified publisher.
 *
 * This whitelist suppresses the risk score for such apps so the scanner
 * focuses on genuinely unknown or suspicious packages.
 *
 * MATCHING RULES:
 * ──────────────────────────────────────────────────────────────────────────────
 * 1. TRUSTED_PACKAGE_PREFIXES — matches any app whose package name STARTS WITH
 *    the listed prefix. Used for entire publisher namespaces
 *    (e.g. "com.google." covers all Google apps).
 *
 * 2. TRUSTED_EXACT_PACKAGES — exact package name match for apps whose publisher
 *    prefix is not uniformly safe but this specific app is well-known.
 *
 * SCORE OVERRIDE:
 * ──────────────────────────────────────────────────────────────────────────────
 * Trusted apps receive a fixed score of TRUSTED_APP_SCORE (= 5) and are
 * classified as LOW risk. Their dangerous permissions list is still recorded
 * for informational purposes but does NOT affect the risk calculation.
 */
object TrustedAppsWhitelist {

    /**
     * Maximum risk score assigned to any trusted app.
     * Kept at 5 (not 0) so the app still shows up in the list but clearly
     * marked as trusted / low-risk.
     */
    const val TRUSTED_APP_SCORE = 5

    // ─── Publisher-level prefixes ──────────────────────────────────────────────
    val TRUSTED_PACKAGE_PREFIXES: List<String> = listOf(

        // ── Google ────────────────────────────────────────────────────────────
        "com.google.",
        "com.android.",        // AOSP / vanilla Android apps
        "android.",            // core Android framework packages

        // ── Meta (Facebook / Instagram / WhatsApp) ────────────────────────────
        "com.facebook.",
        "com.instagram.",
        "com.whatsapp.",
        "com.meta.",

        // ── Microsoft ─────────────────────────────────────────────────────────
        "com.microsoft.",
        "com.azure.",
        "com.skype.",

        // ── Apple ─────────────────────────────────────────────────────────────
        "com.apple.",

        // ── Amazon ────────────────────────────────────────────────────────────
        "com.amazon.",
        "com.audible.",
        "com.primevideo.",

        // ── Samsung ───────────────────────────────────────────────────────────
        "com.samsung.",
        "com.sec.",            // Samsung experience / system components

        // ── Twitter / X ───────────────────────────────────────────────────────
        "com.twitter.",
        "com.x.android.",

        // ── Snap ──────────────────────────────────────────────────────────────
        "com.snapchat.",

        // ── ByteDance (TikTok) ────────────────────────────────────────────────
        "com.zhiliaoapp.",     // TikTok
        "com.ss.android.",     // TikTok / CapCut family

        // ── Spotify ───────────────────────────────────────────────────────────
        "com.spotify.",

        // ── Netflix ───────────────────────────────────────────────────────────
        "com.netflix.",

        // ── Adobe ─────────────────────────────────────────────────────────────
        "com.adobe.",

        // ── Dropbox ───────────────────────────────────────────────────────────
        "com.dropbox.",

        // ── Slack ─────────────────────────────────────────────────────────────
        "com.Slack.",

        // ── Zoom ──────────────────────────────────────────────────────────────
        "us.zoom.",

        // ── LinkedIn ──────────────────────────────────────────────────────────
        "com.linkedin.",

        // ── PayPal ────────────────────────────────────────────────────────────
        "com.paypal.",

        // ── Uber ──────────────────────────────────────────────────────────────
        "com.ubercab.",
        "com.uber.",

        // ── Lyft ──────────────────────────────────────────────────────────────
        "me.lyft.",

        // ── Airbnb ────────────────────────────────────────────────────────────
        "com.airbnb.",

        // ── Telegram ──────────────────────────────────────────────────────────
        "org.telegram.",
        "org.thunderdog.",     // Telegram forks (e.g. Catogram)

        // ── Signal ────────────────────────────────────────────────────────────
        "org.thoughtcrime.",   // Signal

        // ── Viber ─────────────────────────────────────────────────────────────
        "com.viber.",

        // ── Discord ───────────────────────────────────────────────────────────
        "com.discord.",

        // ── Reddit ────────────────────────────────────────────────────────────
        "com.reddit.",

        // ── Pinterest ─────────────────────────────────────────────────────────
        "com.pinterest.",

        // ── Shazam ────────────────────────────────────────────────────────────
        "com.shazam.",

        // ── Duolingo ──────────────────────────────────────────────────────────
        "com.duolingo.",

        // ── Mozilla ───────────────────────────────────────────────────────────
        "org.mozilla.",

        // ── Opera ─────────────────────────────────────────────────────────────
        "com.opera.",

        // ── Alibaba / Alipay ──────────────────────────────────────────────────
        "com.alibaba.",
        "com.alipay.",

        // ── Huawei ────────────────────────────────────────────────────────────
        "com.huawei.",

        // ── OnePlus / OPPO ──────────────────────────────────────────────────
        "com.oneplus.",
        "com.coloros.",
        "com.oplus.",

        // ── Xiaomi ────────────────────────────────────────────────────────────
        "com.miui.",
        "com.xiaomi.",

        // ── Sony ──────────────────────────────────────────────────────────────
        "com.sony.",

        // ── LG ────────────────────────────────────────────────────────────────
        "com.lge.",

        // ── Motorola ──────────────────────────────────────────────────────────
        "com.motorola.",

        // ── Nokia ─────────────────────────────────────────────────────────────
        "com.hmdglobal.",

        // ── Banking (major global banks) ──────────────────────────────────────
        "com.chase.",
        "com.bankofamerica.",
        "com.wellsfargo.",
        "com.citi.",
        "com.barclays.",
        "com.hsbc.",
        "com.db.",             // Deutsche Bank
        "com.ing.",

        // ── Antivirus / Security vendors ──────────────────────────────────────
        "com.avast.",
        "com.avg.",
        "com.kaspersky.",
        "com.mcafee.",
        "com.symantec.",
        "com.norton.",
        "com.bitdefender.",
        "com.eset.",
        "com.malwarebytes."
    )

    // ─── Exact package names ───────────────────────────────────────────────────
    val TRUSTED_EXACT_PACKAGES: Set<String> = setOf(

        // Google Play family
        "com.android.vending",                       // Google Play Store
        "com.google.android.gms",                   // Google Play Services
        "com.google.android.gsf",                   // Google Services Framework

        // YouTube
        "com.google.android.youtube",
        "com.google.android.apps.youtube.music",

        // Maps & Navigation
        "com.google.android.apps.maps",
        "com.waze",                                  // Waze

        // Messaging
        "com.google.android.apps.messaging",         // Google Messages
        "com.google.android.talk",                   // Google Hangouts legacy

        // Social
        "com.linkedin.android",
        "com.tumblr",

        // Shopping
        "com.ebay.mobile",
        "com.etsy.android",
        "com.shopify.mobile",

        // Food & Delivery
        "com.doordash.diner",
        "com.ubereats",
        "com.grubhub.android",

        // Travel
        "com.booking",
        "com.expedia.bookings",
        "com.tripadvisor.tripadvisor",

        // Games (major studios)
        "com.supercell.clashofclans",
        "com.supercell.clashroyale",
        "com.king.candycrushsaga",
        "com.rovio.angrybirds",

        // Health & Fitness
        "com.fitbit.FitbitMobile",
        "com.garmin.android.apps.connectmobile",
        "com.nike.plusgps",
        "com.strava",

        // Music
        "com.pandora.android",
        "com.soundcloud.android",
        "com.deezer.android",
        "com.tidal.android",

        // Cloud Storage
        "com.box.android",
        "com.evernote",
        "com.onenote",

        // VPN (well-known providers)
        "com.nordvpn.android",
        "com.expressvpn.vpn",
        "com.protonvpn.android"
    )

    /**
     * Returns true if the given [packageName] belongs to a trusted publisher
     * or is a well-known exact package.
     */
    fun isTrusted(packageName: String): Boolean {
        // Exact match first (faster for known packages)
        if (packageName in TRUSTED_EXACT_PACKAGES) return true
        // Prefix match
        return TRUSTED_PACKAGE_PREFIXES.any { prefix ->
            packageName.startsWith(prefix)
        }
    }
}
